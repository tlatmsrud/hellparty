package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.enums.Extension;
import com.hellparty.exception.FileProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * title        : FileServiceTest
 * author       : sim
 * date         : 2023-08-08
 * description  : FileServiceTest
 */
class FileServiceTest implements TestFixture {

    private final FileService fileService = new FileService();

    private MockMultipartFile mockImageMultipartFile;

    private FileInputStream fileInputStream = null;

    private final MockHttpServletResponse servletResponse = new MockHttpServletResponse();

    private static File imageFile;


    @BeforeEach
    void setUp() throws IOException {
        ReflectionTestUtils.setField(fileService, "fileSavePath", FILE_SAVE_PATH);
        ReflectionTestUtils.setField(fileService, "thumbHeight", THUMB_HEIGHT);
        ReflectionTestUtils.setField(fileService, "thumbWeight", THUMB_WEIGHT);

        imageFile = ResourceUtils.getFile(TEST_IMAGE_FILE_PATH);
        mockImageMultipartFile = getMockMultipartFile(imageFile, MediaType.IMAGE_PNG_VALUE);

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
    @DisplayName("이미지 파일 저장하기")
    void saveImage() {
        assertDoesNotThrow(()->
                fileService.saveImage(mockImageMultipartFile, REQUEST_IMAGE_PATH, REQUEST_IMAGE_FILE_NAME));
    }

    @Test
    void saveThumbnailImage() {
        assertDoesNotThrow(()->
            fileService.saveThumbnailImage(mockImageMultipartFile, REQUEST_IMAGE_PATH+THUMBNAIL_PATH, REQUEST_IMAGE_FILE_NAME));
    }

    @Test
    @DisplayName("유효 파일에 대한 파일 찾기")
    void findFileWithValidFile() {
        assertThat(fileService.findFile(REQUEST_IMAGE_PATH, REQUEST_IMAGE_FILE_NAME))
                .isFile();
    }

    @Test
    @DisplayName("유효하지 않은 파일에 대한 파일 찾기")
    void findFileWithInvalidFileName() {
        assertThatThrownBy(() -> fileService.findFile(INVALID_IMAGE_FILE_NAME, REQUEST_IMAGE_FILE_NAME))
                .isInstanceOf(FileProcessingException.class);
    }


    @Test
    @DisplayName("클라이언에게 파일 전송")
    void sendImage() {
        assertDoesNotThrow(()
                -> fileService.sendImage(servletResponse, Extension.PNG.getContentType(), imageFile));
    }

    @Test
    @DisplayName("유효 경로에 대한 디렉토리 생성")
    void createDirectoriesWithValidPath() {
        assertDoesNotThrow(()
                ->fileService.createDirectories(FILE_SAVE_PATH+REQUEST_IMAGE_PATH));
    }

    @Test
    @DisplayName("지원하는 확장자를 가진 파일명에 대한 ContentType 조회")
    void determineContentTypeByFileNameWithSupportedExtension() {
        assertThat(
                fileService.determineContentTypeByFileName(TEST_IMAGE_FILE_NAME)
        ).isEqualTo(Extension.PNG.getContentType());
    }

    @Test
    @DisplayName("지원하지 않는 확장자를 가진 파일명에 대한 ContentType 조회")
    void determineContentTypeByFileNameWithNotSupportedExtension() {
        assertThatThrownBy(()->
                fileService.determineContentTypeByFileName(TEST_TXT_FILE_NAME)
        ).isInstanceOf(FileProcessingException.class);
    }

    @Test
    @DisplayName("이미지 파일 대한 확장자 유효성 검사")
    void validationImageExtensionWithImageFile() {
        assertDoesNotThrow(()->
                fileService.validationImageExtension(TEST_IMAGE_FILE_NAME));
    }

    @Test
    @DisplayName("이미지 파일 대한 확장자 유효성 검사")
    void validationImageExtensionWithTxtFile() {
        assertThatThrownBy(()->
                fileService.validationImageExtension(TEST_TXT_FILE_NAME))
                .isInstanceOf(FileProcessingException.class);
    }

    @Test
    @DisplayName("확장자가 있는 파일에 대한 확장자 조회")
    void getFileExtensionWithExtension() {
        assertThat(fileService.getFileExtension(TEST_IMAGE_FILE_NAME))
                .isEqualTo("png");


        assertThat(fileService.getFileExtension(TEST_TXT_FILE_NAME))
                .isEqualTo("txt");
    }

    @Test
    @DisplayName("확장자가 없는 파일에 대한 확장자 조회")
    void getFileExtensionWithNoExtension() {

        assertThatThrownBy(()->fileService.getFileExtension(NOT_EXIST_EXTENSION_FILE_NAME))
                .isInstanceOf(FileProcessingException.class);

    }

    @Test
    @DisplayName("UUID 생성")
    void generateUUIDByFileName() {
        assertThat(fileService.generateUUIDByFileName(TEST_IMAGE_FILE_NAME))
                .isNotEqualTo(TEST_IMAGE_FILE_NAME)
                .hasSizeGreaterThan(TEST_IMAGE_FILE_NAME.length());
    }
}