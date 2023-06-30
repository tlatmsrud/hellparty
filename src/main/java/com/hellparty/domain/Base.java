package com.hellparty.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
abstract public class Base {

    @CreatedDate
    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "CREATE_ID", nullable = false)
    private Long createId;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_ID")
    private Long updateId;
}
