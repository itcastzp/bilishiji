package com.example.demo.bilibili.plugin.service;

import com.example.demo.bilibili.Figure;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FigureDao extends JpaRepository<Figure, String> {

    List<Figure> findByName(String name);

    List<Figure> getFiguresBySeriesId(String seriesId,Pageable pageable);


    List<Figure> getFiguresBySeries_Name(String seriesName, Pageable pageable);


}
