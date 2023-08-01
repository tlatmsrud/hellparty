package com.hellparty.controller;

import com.hellparty.annotation.LoginMemberId;
import com.hellparty.dto.FileDTO;
import com.hellparty.enums.ImageType;
import com.hellparty.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * title        : ImageController
 * author       : sim
 * date         : 2023-08-01
 * description  : 이미지 첨부 컨트롤러 클래스
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    /**
     * 이미지 저장 및 URN 응답
     * @param file - MultipartFile 이미지 파일
     * @param loginId - 로그인 ID
     * @param imageType - 이미지 타입 (프로필 이미지 or 바디프로필 이미지)
     * @return URN
     */
    @PostMapping("/{imageType}")
    @ResponseStatus(HttpStatus.CREATED)
    public FileDTO saveImageAndReturnUrn(@RequestParam("file") MultipartFile file, @LoginMemberId Long loginId
            , @PathVariable("imageType") ImageType imageType){
        return imageService.saveImageAndReturnUrn(file, loginId, imageType);
    }

    /**
     * 원본 이미지 조회
     * @param response - HttpServletResponse
     * @param path - 파일경로
     * @param fileName - 파일명
     */
    @GetMapping("/{path}/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    public void getOriginImage(HttpServletResponse response,
                           @PathVariable("path") String path, @PathVariable("fileName") String fileName){
        imageService.sendFileFromUrn(response, path, fileName);
    }


    /**
     * 썸네일 이미지 조회
     * @param response - HttpServletResponse
     * @param path - 파일경로
     * @param fileName - 파일명
     */
    @GetMapping("/{path}/thumbnail/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    public void getThumbnailImage(HttpServletResponse response,
                         @PathVariable("path") String path, @PathVariable("fileName") String fileName){
        imageService.sendFileFromUrn(response, "thumbnail/" + path, fileName);
    }
}
