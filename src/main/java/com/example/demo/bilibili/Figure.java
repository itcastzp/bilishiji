package com.example.demo.bilibili;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

@ToString(exclude = "series")
@Data
@Table(name = "figure")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Figure {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "series_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
    @JsonBackReference
    private FigureSeries series;

    private String url;


    private String imageUrl;

    @CreationTimestamp
    private Date creationTime;

    private int dr;

    @UpdateTimestamp
    private Date modificationTime;


}
