package com.example.demo.bilibili;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "figure_brand")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FigureBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private String brand;

    private String url;

    private String imageUrl;


    private String description;

    @OneToMany
    private List<FigureSeries> figureSeriesList;

    @CreationTimestamp
    private Date creationTime;


    @UpdateTimestamp
    private Date modificationTime;



}
