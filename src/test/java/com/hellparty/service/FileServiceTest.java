package com.hellparty.service;

import attributes.TestFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    @BeforeEach
    void setUp() throws IOException {
        ReflectionTestUtils.setField(fileService, "fileSavePath", "C:/Users/sim/");
        ReflectionTestUtils.setField(fileService, "thumbHeight", 250);
        ReflectionTestUtils.setField(fileService, "thumbWeight", 250);

        File imageFile = ResourceUtils.getFile(TEST_IMAGE_FILE_PATH);
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
    void findFile() {
    }

    @Test
    void sendImage() {
    }

    @Test
    void createDirectories() {
    }

    @Test
    void determineContentTypeByFileName() {
    }

    @Test
    void validationImageExtension() {
    }

    @Test
    void getFileExtension() {
    }

    @Test
    void generateUUIDByFileName() {
    }
}