package com.example.demo.bilibili.plugin.service.impl;

import com.example.demo.bilibili.Figure;
import com.example.demo.bilibili.FigureSeries;
import com.example.demo.bilibili.plugin.service.FigureDao;
import com.example.demo.bilibili.plugin.service.FigureSeriesDao;
import com.example.demo.bilibili.plugin.service.FigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FigureServiceImpl implements FigureService {

    @Autowired
    public FigureSeriesDao figureSeriesDao;
    @Autowired
    public FigureDao figureDao;

    @Override
    public List<FigureSeries> getFigureSeries(Pageable pageable) {
        return figureSeriesDao.findFigureSeriesBy(pageable);
    }

    @Override
    public List<Figure> getFiguresByName(String figureName) {
        return figureDao.findByName(figureName);
    }

    @Override
    public List<Figure> getAllFigures() {
        List<Figure> figures = figureDao.findAll();
        return figures;
    }

    @Override
    public Optional<Figure> getFiguresById(String figureId) {
        return figureDao.findById(figureId);

    }

    @Override
    public List<Figure> getFigureBySeriesId(String seriesId, Pageable pageable) {

        return figureDao.getFiguresBySeriesId(seriesId, pageable);

    }

    @Override
    public List<Figure> getFiguresBySeriesName(String seriesName, Pageable pageable) {
        return figureDao.getFiguresBySeries_Name(seriesName, pageable);
    }
}
