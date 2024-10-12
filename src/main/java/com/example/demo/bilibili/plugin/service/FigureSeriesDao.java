package com.example.demo.bilibili.plugin.service;

import com.example.demo.bilibili.FigureSeries;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FigureSeriesDao extends JpaRepository<FigureSeries, String> {

     List<FigureSeries> findByName(String seriesName);

     @EntityGraph(attributePaths = {"figureList"})
     List<FigureSeries> findFigureSeriesBy(Pageable pageable);
}
