package com.example.demo;

import com.example.demo.bilibili.Daum;
import com.example.demo.bilibili.FigureSeries;
import com.example.demo.bilibili.plugin.service.DaumDao;
import com.example.demo.bilibili.plugin.service.FigureSeriesDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)

//@Sql(scripts = "/entitygraph-data.sql")
//@Sql()
public class EntityGraphIntegrationTest {

    @Autowired
    private DaumDao itemRepo;


    @Autowired
    private FigureSeriesDao figureSeriesDao;


    @Test
    public void givenEntityGraph_whenCalled_shouldRetrunDefinedFields() {

        List<Daum> item = itemRepo.findByC2cItemsIdInOrderByShowPrice(List.of(83917131452L), PageRequest.of(0, 2));
        for (Daum daum : item) {
            System.out.println(daum.getDetailDtoList());
        }
        System.out.println(item);
    }
    @Test
    public void testFigureSeries() {

        List<FigureSeries> allSeries = figureSeriesDao.findFigureSeriesBy(PageRequest.of(0, 1));

    }

}