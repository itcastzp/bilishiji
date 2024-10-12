package com.example.demo.bilibili;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@ToString(exclude = "figureList")
@Table(name = "figure_series")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FigureSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private String url;

    private String imageUrl;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonManagedReference
    private List<Figure> figureList;


    @CreationTimestamp
    private Date creationTime;


    @UpdateTimestamp
    private Date modificationTime;


}
