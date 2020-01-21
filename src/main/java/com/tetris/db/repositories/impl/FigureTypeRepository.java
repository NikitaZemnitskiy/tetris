package com.tetris.db.repositories.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetris.db.repositories.Repository;
import com.tetris.game.Figure;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class FigureTypeRepository implements Repository {

@SneakyThrows
    public Set<Figure> getFigures()  {
        Set<Figure>figures = new HashSet<>();
        String SQL = "SELECT type FROM figure_type";
        Statement statement = getConnection().createStatement();
        ResultSet rs = statement.executeQuery(SQL);
        ObjectMapper objectMapper = new ObjectMapper();
        while (rs.next()){
            String jsonString = rs.getString("type");
            Figure figure = objectMapper.readValue(jsonString, Figure.class);
            figures.add(figure);
        }
        return figures;
    }
}
