package com.hellparty.service;

import com.hellparty.domain.ProfileImageEntity;
import com.hellparty.enums.ImageType;
import com.hellparty.exception.ImageSaveException;
import com.hellparty.repository.ImageRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.imgscalr.Scalr;

/**
 * title        :
 * author       : sim
 * date         : 2023-08-01
 * description  :
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;
    private final static String FILE_SAVE_PATH = "C:/Users/sim";
    private final static String THUMBNAIL_PATH = "/thumbnail";
    public String saveImageAndReturnUrn(MultipartFile file, Long loginId, ImageType imageType) {

        try{

            String originFileName = file.getOriginalFilename();
            String ext = file.getContentType();
            String path = imageType.getPath();
            String thumbnailPath = imageType.getPath() + THUMBNAIL_PATH;
            String fileName = UUID.randomUUID() + "." + ext;

            saveImage(file, path, fileName);
            saveThumbnailImage(file, thumbnailPath, fileName);

            ProfileImageEntity imageEntity = new ProfileImageEntity(loginId, imageType, originFileName, fileName, path, thumbnailPath);

            imageRepository.save(imageEntity);

            return thumbnailPath + fileName;
        }catch(IOException e){
            e.printStackTrace();
            throw new ImageSaveException("이미지 저장 도중 에러가 발생하였습니다. 관리자에게 문의해주세요.");
        }

    }

    public void saveImage(MultipartFile file, String path, String fileName) throws IOException {

        String savedFilePath = FILE_SAVE_PATH + path;
        Files.createDirectories(Path.of(savedFilePath)); // 디렉터리 생성

        File dest = new File(savedFilePath, fileName);

        file.transferTo(dest);

        log.info("saved file :"+ savedFilePath);
    }

    public void saveThumbnailImage(MultipartFile file, String path, String fileName) throws IOException {
        String savedFilePath = FILE_SAVE_PATH + path;
        Files.createDirectories(Path.of(savedFilePath)); // 디렉터리 생성
        // 저장된 원본파일로부터 BufferedImage 객체를 생성합니다.
        BufferedImage srcImg = ImageIO.read(file.getInputStream());

        // 썸네일의 너비와 높이 입니다.
        int dw = 250, dh = 250;

        // 원본 이미지의 너비와 높이 입니다.
        int ow = srcImg.getWidth();
        int oh = srcImg.getHeight();

        // 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
        int nw = ow; int nh = (ow * dh) / dw;

        // 계산된 높이가 원본보다 높다면 crop이 안되므로
        // 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
        if(nh > oh) {
            nw = (oh * dw) / dh;
            nh = oh;
        }

        // 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
        BufferedImage cropImg = Scalr.crop(srcImg, (ow-nw)/2, (oh-nh)/2, nw, nh);

        // crop된 이미지로 썸네일을 생성합니다.
        BufferedImage destImg = Scalr.resize(cropImg, dw, dh);

        File thumbFile = new File(savedFilePath, fileName);
        ImageIO.write(destImg, file.getContentType(), thumbFile);

    }

    public void getImageByte(HttpServletResponse response, String path, String fileName){

        try{
            response.setContentType("image/png");
            File file = new File(FILE_SAVE_PATH+"/"+path+"/"+fileName);

            if(!file.exists()){
                log.error(FILE_SAVE_PATH+"/"+path+"/"+fileName);
                throw new FileNotFoundException("파일을 찾을 수 없습니다. 관리자에게 문의해주세요.");
            }

            InputStream inputStream = new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }
        }catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("싀벌");
        }

    }
}
