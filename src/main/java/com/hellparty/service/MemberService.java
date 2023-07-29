package com.hellparty.service;

import com.hellparty.domain.MemberEntity;
import com.hellparty.domain.MemberHealthEntity;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.MemberHealthDTO;
import com.hellparty.dto.SearchMemberDTO;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.exception.NotFoundException;
import com.hellparty.mapper.ExecDayMapper;
import com.hellparty.mapper.MemberMapper;
import com.hellparty.repository.MemberHealthRepository;
import com.hellparty.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * title        : Member Service
 * author       : sim
 * date         : 2023-07-01
 * description  : 사용자 서비스 클래스
 */

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberHealthRepository memberHealthRepository;
    private final MemberMapper memberMapper;

    /**
     * 기본 정보 조회
     * @param id - 사용자 ID
     * @return 사용자 기본정보
     */
    public MemberDTO getDetail(Long id){
        MemberEntity findMember = memberRepository.findById(id).orElseThrow(
                () -> new NotFoundException("사용자를 찾을 수 없습니다. 관리자에게 문의해주세요."));

        return memberMapper.memberEntityToDto(findMember);
    }

    /**
     * 사용자 헬스정보 조회
     * @param loginId - 로그인 ID
     * @return 사용자 헬스정보
     */
    public MemberHealthDTO getHealthDetail(Long loginId){
        MemberHealthEntity findMemberHealth = memberHealthRepository.findByMemberId(loginId)
                .orElseThrow(() -> new NotFoundException("입력된 헬스 정보가 없습니다. 헬스 정보를 입력해주세요."));

        return memberMapper.memberHealthEntityToDto(findMemberHealth);
    }

    /**
     * 사용자 기본정보 수정
     * @param loginId - 로그인 ID
     * @param request - 수정 데이터 DTO
     */
    public void updateDetail(Long loginId, MemberDTO.Update request){

        MemberEntity findMember = memberRepository.findById(loginId).orElseThrow(
                ()-> new NotFoundException("사용자를 찾을 수 없습니다. 관리자에게 문의해주세요."));

        findMember.updateMember(
                request.getNickname()
                ,request.getHeight()
                ,request.getWeight()
                ,request.getBirthYear()
                ,request.getSex()
                ,request.getMbti()
                ,request.getProfileUrl()
        );
    }

    /**
     * 사용자 헬스정보 수정
     * @param loginId - 로그인 ID
     * @param request - 수정 데이터 DTO
     */
    public void updateHealthDetail(Long loginId, MemberHealthDTO.Update request){

        Optional<MemberHealthEntity> result = memberHealthRepository.findByMemberId(loginId);

        if(result.isPresent()){
            result.get().UpdateMemberHealth(
                    request.getExecStartTime()
                    ,request.getExecEndTime()
                    ,request.getDiv()
                    ,request.getExecArea()
                    ,request.getGymAddress()
                    ,request.getSpclNote()
                    ,request.getBigThree()
                    ,request.getHealthMotto()
                    ,ExecDayMapper.dtoToEntity(request.getExecDay())
            );
        }else{
            MemberHealthEntity entity = MemberHealthEntity.builder()
                    .execStartTime(request.getExecStartTime())
                    .execEndTime(request.getExecEndTime())
                    .div(request.getDiv())
                    .execArea(request.getExecArea())
                    .gymAddress(request.getGymAddress())
                    .spclNote(request.getSpclNote())
                    .bigThree(request.getBigThree())
                    .healthMotto(request.getHealthMotto())
                    .execDay(ExecDayMapper.dtoToEntity(request.getExecDay()))
                    .build();

            memberHealthRepository.save(entity);
        }

    }

    /**
     * 사용자 상태 수정
     * @param loginId - 로그인 ID
     * @param status - 사용자 상태
     */
    public void updateExecStatus(Long loginId, ExecStatus status) {

        MemberEntity findMember = memberRepository.findById(loginId)
                .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다. 다시 로그인해주세요."));

        findMember.updateExecStatus(status);

    }

    /**
     * 사용자 파트너 구함 상태 수정
     * @param loginId - 로그인 ID
     * @param status - 파트너 구함 상태
     */
    public void updatePartnerFindStatus(Long loginId, PartnerFindStatus status){

        MemberEntity findMember = memberRepository.findById(loginId)
                .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다. 다시 로그인해주세요."));

        findMember.updatePartnerFindStatus(status);
    }

    /**
     * 사용자 검색 - 리스트
     * @param loginId - 사용자 ID
     * @param request - 검색 Dto
     * @return 사용자 리스트
     */
    public List<SearchMemberDTO.Summary> searchMemberList(Long loginId, SearchMemberDTO.Request request) {

        return memberRepository.searchMemberList(loginId, request);
    }

    /**
     * 사용자 검색 - 상세정보
     * @param memberId - 사용자 ID
     * @return 상세정보
     */
    public SearchMemberDTO.Detail searchMemberDetail(Long memberId) {

        return memberRepository.searchMemberDetail(memberId);
    }
}
