package com.hellparty.repository;

import com.hellparty.domain.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        :
 * author       : sim
 * date         : 2023-08-01
 * description  :
 */
public interface ImageRepository extends JpaRepository<ProfileImageEntity, Long> {
}
