package com.hellparty.repository.impl;

import com.hellparty.dto.ExecDayDTO;
import com.hellparty.dto.PartnerFindDTO;
import com.hellparty.enums.MBTI;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.enums.Sex;
import com.hellparty.repository.custom.PartnerFindRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import static com.hellparty.domain.QMemberEntity.memberEntity;
import static com.hellparty.domain.QMemberHealthEntity.memberHealthEntity;
import static com.hellparty.domain.QPartnerEntity.partnerEntity;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-12
 * description  :
 */

@RequiredArgsConstructor
public class PartnerFindRepositoryImpl implements PartnerFindRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PartnerFindDTO.Summary> searchPartnerCandidateList(Long loginId, PartnerFindDTO.Search request) {

        return queryFactory.select(
                Projections.constructor(PartnerFindDTO.Summary.class
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
                        ,eqExecStartTime(request.getExecStartTime())
                        ,eqExecEndTime(request.getExecEndTime())
                        ,eqExecArea(request.getExecArea())
                        ,eqExecDay(request.getExecDay())
                        ,eqFindStatus(PartnerFindStatus.Y)
                        ,neMemberId(loginId)
                        ,notInMember(
                                JPAExpressions.select(partnerEntity.partner)
                                        .from(partnerEntity)
                                        .where(partnerEntity.member.id.eq(loginId))
                        )
                ).fetch();


    }

    @Override
    public PartnerFindDTO.Detail getPartnerCandidateDetail(Long memberId) {
        return queryFactory.select(
                Projections.constructor(PartnerFindDTO.Detail.class
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

    public BooleanExpression eqAge(Integer fromAge, Integer toAge){
        return memberEntity.birthYear.between(LocalDate.now().getYear() - fromAge, LocalDate.now().getYear() - toAge);
    }

    public BooleanExpression eqSex(Sex sex){
        return sex == null ? null : memberEntity.sex.eq(sex);
    }

    public BooleanExpression eqMbti(MBTI mbti){
        return mbti == null ? null : memberEntity.mbti.eq(mbti);
    }

    public BooleanExpression eqExecStartTime(Time execStartTime){
        return execStartTime == null ? null : memberHealthEntity.execStartTime.goe(execStartTime);
    }

    public BooleanExpression eqExecEndTime(Time execEndTime){
        return execEndTime == null ? null : memberHealthEntity.execEndTime.loe(execEndTime);
    }

    public BooleanExpression eqExecArea(Long execArea){
        return execArea == null ? null : memberHealthEntity.execArea.eq(execArea);
    }

    public BooleanExpression neMemberId(Long memberId){
        return memberId == null ? null : memberEntity.id.ne(memberId);
    }

    public BooleanExpression eqMemberId(Long memberId){
        return memberId == null ? null : memberEntity.id.eq(memberId);
    }

    public BooleanExpression eqFindStatus(PartnerFindStatus status){
        return status == null ? null : memberEntity.findStatus.eq(status);
    }

    public BooleanExpression notInMember(JPQLQuery query){
        return memberEntity.notIn(query);
    }
    public BooleanExpression eqExecDay(ExecDayDTO execDay){
        if(execDay == null){
            return null;
        }

        return memberEntity.memberHealth.execDay.sun.eq(execDay.isSun())
                .and(memberEntity.memberHealth.execDay.mon.eq(execDay.isMon()))
                .and(memberEntity.memberHealth.execDay.tue.eq(execDay.isTue()))
                .and(memberEntity.memberHealth.execDay.wed.eq(execDay.isWed()))
                .and(memberEntity.memberHealth.execDay.thu.eq(execDay.isThu()))
                .and(memberEntity.memberHealth.execDay.fri.eq(execDay.isFri()))
                .and(memberEntity.memberHealth.execDay.sat.eq(execDay.isSat()));
    }
}
