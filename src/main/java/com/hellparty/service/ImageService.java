package com.hellparty.service;

import com.hellparty.domain.ProfileImageEntity;
import com.hellparty.dto.FileDTO;
import com.hellparty.enums.ImageType;
import com.hellparty.repository.ImageRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * title        : ImageService
 * author       : sim
 * date         : 2023-08-01
 * description  : ImageService
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final FileService fileService;

    private final ImageRepository imageRepository;

    private final static String THUMBNAIL_PATH = "/thumbnail";

    /**
     * 이미지 저장 및 Urn 경로 리턴
     * @param file - 클라이언트로부터 전달받은 MultipartFile 객체
     * @param loginId - 로그인 ID
     * @param imageType - 이미지 타입 (프로필 or 바디프로필)
     * @return 파일 DTO
     */
    public FileDTO saveImageAndReturnUrn(MultipartFile file, Long loginId, ImageType imageType) {

            String originFileName = file.getOriginalFilename();
            String path = imageType.getPath();
            String thumbnailPath = imageType.getPath() + THUMBNAIL_PATH;

            String fileName = fileService.generateUUIDByFileName(originFileName);

            fileService.saveImage(file, path, fileName);
            fileService.saveThumbnailImage(file, thumbnailPath, fileName);

            ProfileImageEntity imageEntity = new ProfileImageEntity(loginId, imageType, originFileName, fileName, path, thumbnailPath);
            imageRepository.save(imageEntity);

            return new FileDTO(thumbnailPath + fileName, fileName);
    }

    /**
     * 이미지 파일 전송
     * @param response - HttpServletResponse
     * @param path - 파일에 대한 urn 경로
     * @param fileName - 파일 명
     */
    public void sendFileFromUrn(HttpServletResponse response, String path, String fileName) {

        File findFile = fileService.findFile(path, fileName);
        String contentType = fileService.determineContentTypeByFileName(fileName);
        fileService.sendImage(response, contentType, findFile);
    }




}
