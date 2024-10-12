package com.example.demo.bilibili.plugin.web;

import com.example.demo.bilibili.Figure;
import com.example.demo.bilibili.FigureSeries;
import com.example.demo.bilibili.plugin.service.FigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FigureController {
    @Autowired
    private FigureService figureService;

    @GetMapping("series")
    public List<FigureSeries> seriesList(Pageable page) {
        List<FigureSeries> figureSeries = figureService.getFigureSeries(page);
        return figureSeries;
    }

    @GetMapping("series/{id}")
    public List<Figure> figures(@PathVariable("id") String id, Pageable page) {

        return figureService.getFigureBySeriesId(id, page);
    }

}
