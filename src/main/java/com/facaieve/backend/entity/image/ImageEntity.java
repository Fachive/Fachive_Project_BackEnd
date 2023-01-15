package com.facaieve.backend.entity.image;


import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ImageData")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageEntityId;
    @Column
    @Schema(name = "이미지 원본 파일명")
    private String fileName;
    @Column
    @Schema(name = "이미지 파일 형식(jpeg, png")
    private String imageFileType;

    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] imageData;

    @Builder
    public ImageEntity(String fileName, String imageFileType, byte[] imageData, UserEntity imgOwner) {
        this.fileName = fileName;
        this.imageFileType = imageFileType;
        this.imageData = imageData;
        this.profileImgOwner = imgOwner;
    }
    @OneToOne
    @JoinColumn(name = "imgOwner")
    @Schema(name = "이미지를 올린 사람")
    UserEntity profileImgOwner;

}
