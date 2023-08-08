package com.hellparty.service;

import com.hellparty.enums.Extension;
import com.hellparty.exception.FileProcessingException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * title        : FileService
 * author       : sim
 * date         : 2023-08-01
 * description  : 파일 처리에 대한 서비스 클래스
 */

@Service
@Slf4j
public class FileService {

    @Value("${file.save-path}")
    private static String fileSavePath;

    @Value("${file.thumbnail.height}")
    private static int thumbHeight;

    @Value("${file.thumbnail.weight}")
    private static int thumbWeight;
    private final static int BUFFER_SIZE = 1000;
    /**
     * 원본 이미지 저장
     * @param file - 파일
     * @param path - 저장 경로 (파일명 제외)
     * @param fileName - 파일명
     */
    public void saveImage(MultipartFile file, String path, String fileName) {

        validationImageExtension(fileName);
        String savedFilePath = fileSavePath + path;
        try{
            createDirectories(savedFilePath);
            File dest = new File(savedFilePath, fileName);

            file.transferTo(dest);
            log.info("saved file :"+ savedFilePath);
        }catch (IOException e){
            e.printStackTrace();
            log.error("Failed save fileName :"+fileName +", path : "+savedFilePath);
            throw new FileProcessingException(fileName+" 이미지 저장 도중 에러가 발생하였습니다. 관리자에게 문의해주세요.");
        }


    }


    /**
     * 썸네일 이미지 저장
     * @param file - 파일
     * @param path - 저장 경로 (파일명 제외)
     * @param fileName - 파일명
     */
    public void saveThumbnailImage(MultipartFile file, String path, String fileName) {

        validationImageExtension(fileName);
        String savedFilePath = fileSavePath + path;

        try{
            createDirectories(savedFilePath); // 디렉터리 생성

            BufferedImage srcImg = ImageIO.read(file.getInputStream());

            // 원본 이미지의 너비와 높이 입니다.
            int ow = srcImg.getWidth();
            int oh = srcImg.getHeight();

            // 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
            int nw = ow; int nh = (ow * thumbHeight) / thumbWeight;

            // 계산된 높이가 원본보다 높다면 crop이 안되므로
            // 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
            if(nh > oh) {
                nw = (oh * thumbWeight) / thumbHeight;
                nh = oh;
            }

            // 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
            BufferedImage cropImg = Scalr.crop(srcImg, (ow-nw)/2, (oh-nh)/2, nw, nh);

            // crop된 이미지로 썸네일을 생성합니다.
            BufferedImage destImg = Scalr.resize(cropImg, thumbWeight, thumbHeight);

            File thumbFile = new File(savedFilePath, fileName);
            ImageIO.write(destImg, fileName, thumbFile);

        }catch (IOException e){
            e.printStackTrace();
            log.error("Failed save fileName :"+fileName +", path : "+savedFilePath);
            throw new FileProcessingException(fileName+" 썸네일 이미지 저장 도중 에러가 발생하였습니다. 관리자에게 문의해주세요.");
        }

    }

    /**
     * 경로와 파일명을 통한 파일 찾기
     * @param path - 경로
     * @param fileName - 파일명
     * @return 찾은 File 객체
     */
    public File findFile( String path, String fileName){
        Path filePath = Paths.get(fileSavePath, path);
        File file = new File(filePath.toString(), fileName);

        if(!file.exists()){
            log.error("file Not Found !! [ file path : "+ fileSavePath + ", file name : "+ fileName +" ]");
            throw new FileProcessingException("이미지 파일을 찾을 수 없습니다. 관리자에게 문의해주세요.");
        }

        return file;
    }
    /**
     * 이미지 파일을 ServletOutputStream을 통해 전송.
     * @param response - ServletResponse
     * @param imageFile - 이미지 파일 객체
     */
    public void sendImage(HttpServletResponse response, String contentType, File imageFile){

        response.setContentType(contentType);
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(imageFile);
            ServletOutputStream outputStream = response.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }

        }catch(IOException e){
            e.printStackTrace();
            throw new FileProcessingException("이미지 파일 전송 도중 에러가 발생하였습니다. 관리자에게 문의해주세요.");
        }finally {
            try{
                if(inputStream != null){
                    inputStream.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * path 경로에 대한 디렉토리 생성
     * @param path - 디렉토리에 대한 풀경로
     */
    public void createDirectories(String path) {
        try {
            Files.createDirectories(Path.of(path)); // 디렉터리 생성
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileProcessingException(path+" 경로에 대한 디렉토리 생성 중 에러가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    }

    /**
     * 확장자에 대한 ContentType 추출
     * @param fileName - 파일명
     * @return ContentType
     */
    public String determineContentTypeByFileName(String fileName){

        String extension = getFileExtension(fileName);

        if(!Extension.isExtension(extension)){
            throw new FileProcessingException("지원하지 않는 확장자입니다. 다시 시도해주세요.");
        }
        return Extension.valueOf(extension.toUpperCase()).getContentType();
    }

    /**
     * 이미지 파일 확장자에 대한 validation 체크
     * @param fileName - 파일명
     */
    public void validationImageExtension(String fileName){

        String extension = getFileExtension(fileName);

        if(Extension.isExtension(extension)){
            throw new FileProcessingException("지원하지 않는 확장자입니다. 다시 시도해주세요.");
        }
    }
    /**
     * 파일명에 대한 확장자 추출
     * @param fileName - 파일명
     * @return 확장자 (. 제외)
     */
    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        if(dotIndex == -1) { // "."이 존재하는 경우
            throw new FileProcessingException("확장자가 없는 파일입니다. 확장자가 있는 파일로 다시 시도해주세요.");
        }
        return fileName.substring(dotIndex);
    }

    /**
     * UUID 생성
     * @param fileName 파일명
     * @return 확장자 포함된 UUID
     */
    public String generateUUIDByFileName(String fileName){
        return UUID.randomUUID() + "." + getFileExtension(fileName);
    }
}
