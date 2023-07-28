package com.hellparty.mapper;

import com.hellparty.domain.embedded.ExecDay;
import com.hellparty.dto.ExecDayDTO;

/**
 * title        : ExecDay Mapper 클래스
 * author       : sim
 * date         : 2023-07-28
 * description  : ExecDay DTO, Entity에 대한 Mapper 클래스
 */
public class ExecDayMapper {

    public static ExecDay dtoToEntity(ExecDayDTO execDayDto){
        return new ExecDay(
                execDayDto.isSun()
                , execDayDto.isMon()
                , execDayDto.isTue()
                , execDayDto.isWed()
                , execDayDto.isThu()
                , execDayDto.isFri()
                , execDayDto.isSat());
    }
}
