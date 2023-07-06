package com.hellparty.service;

import com.hellparty.domain.Member;
import com.hellparty.domain.MemberHealth;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.MemberHealthDTO;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.exception.BadRequestException;
import com.hellparty.exception.NotFoundException;
import com.hellparty.mapper.MemberMapper;
import com.hellparty.repository.MemberHealthRepository;
import com.hellparty.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
     * 이메일 존재 여부 체크
     * @param email - 이메일
     * @return 이메일 존재 여부
     */
    public boolean isExistMemberByEmail(String email){

        return memberRepository.existsMemberByEmail(email);
    }

    /**
     * 기본 정보 조회
     * @param id - 사용자 ID
     * @return 사용자 기본정보
     */
    public MemberDTO getDetail(Long id){
        Member findMember = memberRepository.findById(id).orElseThrow(
                () -> new NotFoundException("사용자를 찾을 수 없습니다.")
        );

        return memberMapper.memberEntityToDto(findMember);
    }

    /**
     * 사용자 헬스정보 조회
     * @param id - 사용자 ID
     * @return 사용자 헬스정보
     */
    public MemberHealthDTO getHealthDetail(Long id){
        MemberHealth findMemberHealth = memberHealthRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("입력된 헬스 정보가 없습니다. 헬스 정보를 입력해주세요."));

        return memberMapper.memberHealthEntityToDto(findMemberHealth);
    }

    /**
     * 사용자 기본정보 수정
     * @param id - 사용자 ID
     * @param request - 수정 데이터 DTO
     */
    public void updateDetail(Long id, MemberDTO.Update request){

        Member findMember = memberRepository.findById(id).get();

        findMember.updateMember(
                request.getNickname()
                ,request.getEmail()
                ,request.getHeight()
                ,request.getWeight()
                ,request.getAge()
                ,request.getSex()
                ,request.getMbti()
                ,request.getProfileUrl()
        );
    }

    /**
     * 사용자 헬스정보 수정
     * @param id - 사용자 ID
     * @param request - 수정 데이터 DTO
     */
    public void updateHealthDetail(Long id, MemberHealthDTO.Update request){

        if(!id.equals(request.getId())){
            throw new BadRequestException("잘못된 요청입니다. 다시 시도해주세요.");
        }

        MemberHealth memberHealth = memberMapper.memberHealthUpdateDtoToEntity(request);
        memberHealthRepository.save(memberHealth);
    }

    /**
     * 사용자 상태 수정
     * @param id - 사용자 ID
     * @param status - 사용자 상태
     */
    public void updateExecStatus(Long id, ExecStatus status) {

        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다. 다시 로그인해주세요."));

        findMember.updateExecStatus(status);

    }

    /**
     * 사용자 파트너 구함 상태 수정
     * @param id - 사용자 ID
     * @param status - 파트너 구함 상태
     */
    public void updatePartnerFindStatus(Long id, PartnerFindStatus status){

        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다. 다시 로그인해주세요."));

        findMember.updatePartnerFindStatus(status);
    }
}
