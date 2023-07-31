package com.hellparty.domain;

import com.hellparty.enums.ImageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * title        : ProfileImage
 * author       : sim
 * date         : 2023-08-01
 * description  : 프로필, 바디프로필 이미지 URL을 관리하는 엔티티
 */

@Entity
@Getter
@NoArgsConstructor
public class ProfileImageEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "IMAGE_TYPE")
    private ImageType imageType;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "ORIGIN_FILE_NAME")
    private String originFileName;
    @Column(name = "PATH")
    private String path;

    @Column(name = "THUMBNAIL_PATH")
    private String thumbnailPath;


    public ProfileImageEntity(Long memberId, ImageType imageType, String originFileName, String fileName, String path, String thumbnailPath){
        this.memberId = memberId;
        this.imageType = imageType;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.path = path;
        this.thumbnailPath = thumbnailPath;
    }
}
