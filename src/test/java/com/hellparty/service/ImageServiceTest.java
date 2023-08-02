package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.domain.ProfileImageEntity;
import com.hellparty.dto.FileDTO;
import com.hellparty.enums.ImageType;
import com.hellparty.exception.FileProcessingException;
import com.hellparty.repository.ImageRepository;
import com.nimbusds.common.contenttype.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * title        : ImageServiceTest
 * author       : sim
 * date         : 2023-08-01
 * description  : ImageServiceTest
 */

class ImageServiceTest implements TestFixture {

    private final ImageRepository imageRepository = mock(ImageRepository.class);
    private final FileService fileService = mock(FileService.class);
    private final ImageService imageService = new ImageService(fileService, imageRepository);
    private MockMultipartFile mockImageMultipartFile;
    private MockMultipartFile mockTxtMultipartFile;
    private final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
    private FileInputStream fileInputStream;

    @BeforeEach
    void setUp() throws IOException {

        File imageFile = ResourceUtils.getFile(TEST_IMAGE_FILE_PATH);
        mockImageMultipartFile = getMockMultipartFile(imageFile, MediaType.IMAGE_PNG_VALUE);

        File txtFile = ResourceUtils.getFile(TEST_TXT_FILE_PATH);
        mockTxtMultipartFile = getMockMultipartFile(txtFile, MediaType.TEXT_PLAIN_VALUE);

        given(fileService.generateUUIDByFileName(TEST_IMAGE_FILE_NAME)).willReturn(TEST_IMAGE_UUID);

        given(fileService.generateUUIDByFileName(TEST_TXT_FILE_NAME)).willReturn(TEST_TXT_UUID);

        willThrow(new FileProcessingException("지원하지 않는 확장자입니다. 다시 시도해주세요.")).given(fileService)
                .saveImage(eq(mockTxtMultipartFile), any(String.class), any(String.class));

        given(fileService.findFile(REQUEST_IMAGE_PATH, REQUEST_IMAGE_FILE_NAME))
                .willReturn(new File(REQUEST_IMAGE_PATH, REQUEST_IMAGE_FILE_NAME));

        given(fileService.determineContentTypeByFileName(REQUEST_IMAGE_FILE_NAME))
                .willReturn(ContentType.IMAGE_PNG.getType());
    }

    @AfterEach
    void end() throws IOException {
        if(fileInputStream != null){
            fileInputStream.close();
        }
    }

    MockMultipartFile getMockMultipartFile(File file, String mediaType) throws IOException {
        fileInputStream = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), mediaType, fileInputStream);
    }

    @Test
    @DisplayName("이미지 업로드 테스트")
    void saveImageAndReturnUrn() {
        FileDTO fileDTO = imageService.saveImageAndReturnUrn(mockImageMultipartFile, LOGIN_MEMBER_ID, ImageType.PROFILE);

        assertThat(fileDTO.getFileName()).isEqualTo(TEST_IMAGE_FILE_NAME);

        verify(fileService).generateUUIDByFileName(any(String.class));
        verify(fileService).saveImage(any(MultipartFile.class), any(String.class), any(String.class));
        verify(fileService).saveThumbnailImage(any(MultipartFile.class), any(String.class), any(String.class));
        verify(imageRepository).save(any(ProfileImageEntity.class));
    }

    @Test
    @DisplayName("텍스트 파일 업로드 테스트 - FileProcessingException")
    void saveImageAndReturnUrnWithFileProcessingException() {
        assertThatThrownBy(() -> imageService.saveImageAndReturnUrn(mockTxtMultipartFile, LOGIN_MEMBER_ID, ImageType.PROFILE))
                .isInstanceOf(FileProcessingException.class);

        verify(fileService).generateUUIDByFileName(any(String.class));
        verify(fileService).saveImage(any(MultipartFile.class), any(String.class), any(String.class));
    }

    @Test
    @DisplayName("파일 전송")
    void sendFileFromUrn(){
        imageService.sendImageFromUrn(mockHttpServletResponse, REQUEST_IMAGE_PATH, REQUEST_IMAGE_FILE_NAME);
        verify(fileService).sendImage(eq(mockHttpServletResponse), any(String.class), any(File.class));
    }
}