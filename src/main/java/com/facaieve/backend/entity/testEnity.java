package com.facaieve.backend.entity;

import javax.persistence.*;import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
public class testEnity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test")
    Long testId;
}
