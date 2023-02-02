package com.facaieve.backend.service.aswS3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.waiters.AmazonS3Waiters;
import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.exception.BusinessLogicException;
import com.facaieve.backend.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class S3FileService implements FileServiceCRUD{

    private static final Logger LOG = LoggerFactory.getLogger(S3FileService.class);

    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String s3BucketName;
//    @Value("${custom.path.upload-images}")
//    private String uploadImagePath;



//    //multiPartFile to java file obj
//    private File convertMultiPartFileToFile(final MultipartFile multipartFile) throws IOException {
//
//        //todo jar 배포시 발생하는 오류에 대해서 블로그에 작성할것
//        final File file = new File(uploadImagePath,multipartFile.getOriginalFilename());//application context file name return
//        file.setWritable(true); //쓰기가능설정
//        file.setReadable(true);	//읽기가능설정
//
//        try (final FileOutputStream outputStream = new FileOutputStream(file,true)) {//todo true 로 설정해서 해당 경로로 파일 생성되게 만듦
//            outputStream.write(multipartFile.getBytes());
//        } catch (IOException e) {
//            LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
//        }
//
//        return file;
//    }

    // @Async annotation ensures that the method is executed in a different thread
    // and get the S3 obj with file's name
    @Async
    public S3ObjectInputStream findByName(String fileName) {

        LOG.info("Downloading file with name {}", fileName);
        return amazonS3.getObject(s3BucketName, fileName).getObjectContent();

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
    public List<PostImageDto> uploadMultiFileList(List<MultipartFile> multipartFiles) {

        List<PostImageDto> savedFileNamed = new ArrayList<>();

        for(MultipartFile multipartFile: multipartFiles){
            System.out.println("===================================================저장중");
            savedFileNamed.add(uploadMultiFile(multipartFile));
        }
        return savedFileNamed;
    }

    @Override
    public List<String> deleteMultiFileList(List<String> multipartFilesURIes) {

        List<String> deletedFileNames  = new ArrayList<>();
        for(String fileURI: multipartFilesURIes){
            deletedFileNames.add(deleteMultiFile(fileURI));
        }
        return deletedFileNames;
    }

    @Override
    public List<String> getMultiFileList(List<String> fileNames) {
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
    public PostImageDto uploadMultiFile(final MultipartFile multipartFile) {

//        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)                .withCannedAcl(CannedAccessControlList.PublicRead));

        try {
//            final File file = convertMultiPartFileToFile(multipartFile);
            final String fileName = UUID.randomUUID() + "_" + multipartFile.getName();//change the file name
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
           return PostImageDto.builder().fileName(fileName).fileURI(fileURI).build();



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
