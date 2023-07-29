package com.hellparty.repository.impl;

import com.hellparty.domain.MemberEntity;
import com.hellparty.dto.ExecDayDTO;
import com.hellparty.dto.SearchMemberDTO;
import com.hellparty.enums.MBTI;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.enums.Sex;
import com.hellparty.repository.custom.MemberRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import static com.hellparty.domain.QMemberEntity.memberEntity;
import static com.hellparty.domain.QMemberHealthEntity.memberHealthEntity;
import static com.hellparty.domain.QPartnerEntity.partnerEntity;

/**
 * title        : MemberRepositoryImpl
 * author       : sim
 * date         : 2023-07-29
 * description  : MemberRepositoryCustom 구현체 클래스
 */

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Long findMemberIdByEmail(String email) {
        return queryFactory.select(memberEntity.id)
                .from(memberEntity)
                .where(
                        eqEmail(email)
                ).fetchOne();
    }

    @Override
    public MemberEntity findByEmail(String email) {
        return queryFactory.selectFrom(memberEntity)
                .where(
                        eqEmail(email)
                ).fetchOne();
    }

    /**
     * 사용자 검색
     * @param loginId - 로그인 ID
     * @param request - 검색 Dto
     * @return 사용자 리스트
     */
    @Override
    public List<SearchMemberDTO.Summary> searchMemberList(Long loginId, SearchMemberDTO.Request request) {
        return queryFactory.select(
                        Projections.constructor(SearchMemberDTO.Summary.class
                                ,memberEntity.id
                                ,memberEntity.nickname
                                ,memberEntity.birthYear
                                ,memberEntity.sex
                                ,memberEntity.profileUrl
                                ,memberEntity.bodyProfileUrl
                                ,Projections.constructor(ExecDayDTO.class
                                        ,memberEntity.memberHealth.execDay.sun
                                        ,memberEntity.memberHealth.execDay.mon
                                        ,memberEntity.memberHealth.execDay.tue
                                        ,memberEntity.memberHealth.execDay.wed
                                        ,memberEntity.memberHealth.execDay.thu
                                        ,memberEntity.memberHealth.execDay.fri
                                        ,memberEntity.memberHealth.execDay.sat)
                                ,memberEntity.memberHealth.execStartTime
                                ,memberEntity.memberHealth.execEndTime))
                .from(memberEntity)
                .where(
                        eqAge(request.getFromAge(), request.getToAge())
                        ,eqSex(request.getSex())
                        ,eqMbti(request.getMbti())
                        //,eqExecStartTime(request.getExecStartTime()) TODO : Fix error
                        //,eqExecEndTime(request.getExecEndTime())
                        ,eqExecArea(request.getExecArea())
                        ,eqExecDay(request.getExecDay())
                        ,eqFindStatus(PartnerFindStatus.Y)
                        ,neMemberId(loginId)
                        ,notInPartner(loginId)
                ).fetch();
    }

    /**
     * 사용자 검색 - 상세조회
     * @param memberId - 사용자 ID
     * @return 검색된 사용자 상세정보
     */
    @Override
    public SearchMemberDTO.Detail searchMemberDetail(Long memberId) {

        return queryFactory.select(
                        Projections.constructor(SearchMemberDTO.Detail.class
                                , memberEntity.id
                                ,memberEntity.nickname
                                ,memberEntity.birthYear
                                ,memberEntity.height
                                ,memberEntity.weight
                                ,memberEntity.mbti
                                ,memberEntity.sex
                                ,Projections.constructor(ExecDayDTO.class
                                        ,memberEntity.memberHealth.execDay.sun
                                        ,memberEntity.memberHealth.execDay.mon
                                        ,memberEntity.memberHealth.execDay.tue
                                        ,memberEntity.memberHealth.execDay.wed
                                        ,memberEntity.memberHealth.execDay.thu
                                        ,memberEntity.memberHealth.execDay.fri
                                        ,memberEntity.memberHealth.execDay.sat)
                                ,memberEntity.memberHealth.execStartTime
                                ,memberEntity.memberHealth.execEndTime
                                ,memberEntity.memberHealth.div
                                ,memberEntity.memberHealth.execArea
                                ,memberEntity.memberHealth.gymAddress.placeName
                                ,memberEntity.memberHealth.gymAddress.address
                                ,memberEntity.memberHealth.gymAddress.x
                                ,memberEntity.memberHealth.gymAddress.y
                                ,memberEntity.memberHealth.spclNote
                                ,memberEntity.memberHealth.bigThree.benchPress
                                ,memberEntity.memberHealth.bigThree.squat
                                ,memberEntity.memberHealth.bigThree.deadlift
                                ,memberEntity.memberHealth.healthMotto))
                .from(memberEntity)
                .where(
                        eqMemberId(memberId))
                .fetchOne();
    }

    public BooleanExpression eqEmail(String email){
        return email == null ? null : memberEntity.email.eq(email);
    }

    public BooleanExpression eqAge(Integer fromAge, Integer toAge){
        return memberEntity.birthYear.between(LocalDate.now().getYear() - toAge, LocalDate.now().getYear() - fromAge );
    }

    public BooleanExpression eqSex(Sex sex){
        return sex == null ? null : memberEntity.sex.eq(sex);
    }

    public BooleanExpression eqMbti(MBTI mbti){
        return mbti == null ? null : memberEntity.mbti.eq(mbti);
    }

    public BooleanExpression eqExecStartTime(Time execStartTime){
        return execStartTime == null ? null : memberHealthEntity.execStartTime.after(execStartTime);
    }

    public BooleanExpression eqExecEndTime(Time execEndTime){
        return execEndTime == null ? null : memberHealthEntity.execEndTime.before(execEndTime);
    }

    public BooleanExpression eqExecArea(Long execArea){
        return execArea == null ? null : memberHealthEntity.execArea.eq(execArea);
    }

    public BooleanExpression neMemberId(Long memberId){
        return memberId == null ? null : memberEntity.id.ne(memberId);
    }

    public BooleanExpression eqFindStatus(PartnerFindStatus status){
        return status == null ? null : memberEntity.findStatus.eq(status);
    }

    public BooleanExpression eqMemberId(Long memberId){
        return memberId == null ? null : memberEntity.id.eq(memberId);
    }
    /**
     * 로그인 사용자의 파트너 제외
     * @param loginId - 로그인 ID
     * @return BooleanExpression
     */
    public BooleanExpression notInPartner(Long loginId){
        return memberEntity.notIn( JPAExpressions.select(partnerEntity.partner)
                .from(partnerEntity)
                .where(partnerEntity.member.id.eq(loginId)));
    }
    public BooleanExpression eqExecDay(ExecDayDTO execDay){
        return execDay == null ? null :
                memberEntity.memberHealth.execDay.sun.eq(execDay.isSun())
                .and(memberEntity.memberHealth.execDay.mon.eq(execDay.isMon()))
                .and(memberEntity.memberHealth.execDay.tue.eq(execDay.isTue()))
                .and(memberEntity.memberHealth.execDay.wed.eq(execDay.isWed()))
                .and(memberEntity.memberHealth.execDay.thu.eq(execDay.isThu()))
                .and(memberEntity.memberHealth.execDay.fri.eq(execDay.isFri()))
                .and(memberEntity.memberHealth.execDay.sat.eq(execDay.isSat()));
    }
}
