package com.hellparty.repository;

import com.hellparty.domain.ChattingRoomEntity;
import com.hellparty.repository.custom.ChattingRoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-26
 * description  :
 */
public interface ChattingRoomRepository extends JpaRepository<ChattingRoomEntity, Long>
        , ChattingRoomRepositoryCustom {
}
