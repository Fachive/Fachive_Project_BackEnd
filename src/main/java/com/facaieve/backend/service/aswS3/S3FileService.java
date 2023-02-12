package com.facaieve.backend.service.aswS3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.image.S3ImageInfoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;

@Service
@RequiredArgsConstructor

public class S3FileService implements FileServiceCRUD{

    private static final Logger LOG = LoggerFactory.getLogger(S3FileService.class);

    @Autowired
    private final AmazonS3 amazonS3;

    @Autowired
    S3ImageInfoRepository s3ImageInfoRepository;


    @Value("${cloud.aws.s3.bucket}")
    private String s3BucketName;
//    @Value("${custom.path.upload-images}")
//    private String uploadImagePath;

    @Async
    public String findByName(String fileName) {

        LOG.info("Downloading file with name {}", fileName);
        S3ObjectInputStream image = amazonS3.getObject(s3BucketName, fileName).getObjectContent();

        return findImgUrl(fileName);
    }



    @Override
    public String findImgUrl(String fileName) {
        String path = fileName;
        try{
            return amazonS3.getUrl(s3BucketName, path).toString();
        }catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.FILE_IS_NOT_EXIST_IN_BUCKET);
        }
    }

    @Override
    public List<S3ImageInfo> uploadMultiFileList(List<MultipartFile> multipartFiles) {

        List<S3ImageInfo> savedFileNamed = new ArrayList<>();

        for(MultipartFile multipartFile: multipartFiles){
            System.out.println("===================================================저장중");
            savedFileNamed.add(uploadMultiFile(multipartFile));
        }

        return savedFileNamed;
    }

    @Override
    @Transactional
    public List<String> deleteMultiFileList(List<String> multipartFilesURIes) {

        List<String> deletedFileNames  = new ArrayList<>();
        for(String fileURI: multipartFilesURIes){
            deletedFileNames.add(deleteMultiFile(fileURI));
        }
        return deletedFileNames;
    }

    @Override
    public List<String> getMultiFileList(List<String> fileNames) {//todo 파일 이름
        List<String> multiPartFileURIList = new ArrayList<>();
        for(String fileName: fileNames){
            multiPartFileURIList.add(findImgUrl(fileName));
        }
        return multiPartFileURIList;
    }

    @Override
    public void changeMultiFileListAtS3(List<String> multiParFilesURIes, List<MultipartFile> multipartFiles) {
        //new..

    }

    @Override
    //save multiPartFile and get the log
    @Async
    public S3ImageInfo uploadMultiFile(final MultipartFile multipartFile) {

//        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)                .withCannedAcl(CannedAccessControlList.PublicRead));

        try {
//            final File file = convertMultiPartFileToFile(multipartFile);
            final String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().indexOf(".")); //change the file name
            LOG.info("Uploading file with name {}", fileName);

            InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());
            final PutObjectRequest putObjectRequest =
                    new PutObjectRequest(s3BucketName, fileName, inputStream,null);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);//configure upload file permission
            PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);//now send the data to S3
            System.out.println("File " + fileName + " was uploaded.");
//            Files.delete(file.toPath()); // Remove the file locally created in the project folder
            String fileURI = findImgUrl(fileName);
            inputStream.close();//저장한 스트림 닫음

            return S3ImageInfo.builder().fileName(fileName).fileURI(fileURI).build();

        } catch (AmazonServiceException e) {
            LOG.error("Error {} occurred while uploading file", e.getLocalizedMessage());
            return null;
        } catch (IOException ex) {
            LOG.error("Error {} occurred while deleting temporary file", ex.getLocalizedMessage());
            return null;
        }
    }

    @Override
    @Async
    public String deleteMultiFile(String fileName){

        if(amazonS3.doesObjectExist(s3BucketName,fileName)){
            amazonS3.deleteObject(s3BucketName, fileName);
            return fileName;
        }
        else{
            throw new BusinessLogicException(ExceptionCode.FILE_IS_NOT_EXIST_IN_BUCKET);
        }
    }

    @Override
    public String getMultiPartFile(String multiPartFileName) {
       return findImgUrl(multiPartFileName);
    }
}
