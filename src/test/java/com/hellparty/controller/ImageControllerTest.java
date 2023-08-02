package com.hellparty.controller;

import attributes.TestFixture;
import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.enums.ImageType;
import com.hellparty.exception.FileProcessingException;
import com.hellparty.service.ImageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * title        :
 * author       : sim
 * date         : 2023-08-01
 * description  :
 */

@WebMvcTest(ImageController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class ImageControllerTest implements TestFixture {

    private MockMvc mockMvc;

    private MockMultipartFile mockImageMultipartFile;

    private MockMultipartFile mockTxtMultipartFile;
    private final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
    @MockBean
    private ImageService imageService;

    private FileInputStream fileInputStream = null;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) throws IOException {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .addFilter(encodingFilter)
                .build();

        File imageFile = ResourceUtils.getFile(TEST_IMAGE_FILE_PATH);
        mockImageMultipartFile = getMockMultipartFile(imageFile, MediaType.IMAGE_PNG_VALUE);

        File txtFile = ResourceUtils.getFile(TEST_TXT_FILE_PATH);
        mockTxtMultipartFile = getMockMultipartFile(txtFile, MediaType.TEXT_PLAIN_VALUE);

        given(imageService.saveImageAndReturnUrn(mockImageMultipartFile, LOGIN_MEMBER_ID, ImageType.PROFILE))
                .willReturn(IMAGE_FILE_DTO);

        given(imageService.saveImageAndReturnUrn(mockTxtMultipartFile, LOGIN_MEMBER_ID, ImageType.PROFILE))
                .willThrow(new FileProcessingException("지원하지 않는 확장자입니다. 다시 시도해주세요."));

        willDoNothing().given(imageService).sendImageFromUrn(mockHttpServletResponse, REQUEST_IMAGE_PATH, REQUEST_IMAGE_FILE_NAME);
    }

    MockMultipartFile getMockMultipartFile(File file, String mediaType) throws IOException {
        fileInputStream = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), mediaType, fileInputStream);
    }

    @AfterEach
    void end() throws IOException {
        if(fileInputStream != null){
            fileInputStream.close();
        }
    }
    @Test
    @TestMemberAuth
    @DisplayName("파일 저장 및 리턴 URN")
    void saveImageAndReturnUrn() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.multipart("/api/images/{imageType}", ImageType.PROFILE.name())
                        .file(mockImageMultipartFile)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .with(csrf().asHeader()))
                .andExpect(content().string(objectMapper.writeValueAsString(IMAGE_FILE_DTO)))
                .andDo(print())
                .andDo(
                        document("saveImageAndReturnUrn",
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                                ,pathParameters(
                                        parameterWithName("imageType").description("이미지 타입 (PROFILE / BODY_PROFILE"))
                                , responseFields(
                                        fieldWithPath("urn").description("이미지 URN 경로")
                                        ,fieldWithPath("fileName").description("파일명"))
                        )
                );
    }

    @Test
    @TestMemberAuth
    @DisplayName("지원하지 않는 확장자에 대한 파일 저장 및 리턴 URN - 500 에러 발생")
    void saveImageAndReturnUrnWithNotSupportedExtension() throws Exception {
        mockMvc.perform(
                multipart("/api/images/{imageType}", ImageType.PROFILE.name())
                                .file(mockTxtMultipartFile)
                                .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                                .with(csrf().asHeader()))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("지원하지 않는 확장자입니다. 다시 시도해주세요.")))
                .andDo(print());
    }

    @Test
    @DisplayName("원본 이미지 조회")
    void getOriginImage() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/images/{path}/{fileName}",REQUEST_IMAGE_PATH,REQUEST_IMAGE_FILE_NAME)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("getOriginImage"
                                ,requestHeaders(
                                        headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                                ,pathParameters(
                                        parameterWithName("path").description("이미지 경로")
                                        , parameterWithName("fileName").description("파일명"))
                        )
                );
    }

    @Test
    @DisplayName("썸네일 이미지 조회")
    void getThumbnailImage() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/images/{path}/thumbnail/{fileName}",REQUEST_IMAGE_PATH,REQUEST_IMAGE_FILE_NAME)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("getThumbnailImage"
                        ,requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        ,pathParameters(
                                parameterWithName("path").description("이미지 경로")
                                , parameterWithName("fileName").description("파일명"))
                        )
                );
    }

}