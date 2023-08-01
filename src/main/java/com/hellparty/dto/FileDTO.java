package com.hellparty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : FileDTO
 * author       : sim
 * date         : 2023-08-01
 * description  : 파일 관련 DTO 클래스
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileDTO {
    private String urn;
    private String fileName;
}
