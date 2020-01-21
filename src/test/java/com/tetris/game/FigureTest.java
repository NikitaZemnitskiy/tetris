package com.tetris.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetris.model.Point;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class FigureTest {

    ObjectMapper mapper = new ObjectMapper();

    @org.junit.jupiter.api.Test
    void testJsonSerialization() throws IOException {

        Figure f = fixture();
        String json = mapper.writeValueAsString(f);
        log.info(json);
        Figure f2 = mapper.readValue(json, Figure.class);
        assertEquals(f,f2);
    }

    static Figure fixture() {
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(0,1));
        pointList.add(new Point(1,1));
        pointList.add(new Point(2,1));
        pointList.add(new Point(2,2));
        Figure figure = new Figure(pointList, new Point(2,1), new Point(10,5));
        return figure;
    }

}