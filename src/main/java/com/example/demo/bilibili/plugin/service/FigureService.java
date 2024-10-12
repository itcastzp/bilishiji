package com.example.demo.bilibili.plugin.service;

import com.example.demo.bilibili.Figure;
import com.example.demo.bilibili.FigureSeries;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FigureService {


    List<FigureSeries> getFigureSeries(Pageable pageable);



    List<Figure> getFiguresByName(String figureName);


    List<Figure> getAllFigures();


    Optional<Figure> getFiguresById(String figureId);

    List<Figure> getFigureBySeriesId(String seriesId, Pageable pageable);

    List<Figure> getFiguresBySeriesName(String seriesName,Pageable pageable);
}
