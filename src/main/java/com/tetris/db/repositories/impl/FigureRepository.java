package com.tetris.db.repositories.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetris.db.repositories.Repository;
import com.tetris.game.Figure;
import lombok.SneakyThrows;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FigureRepository implements Repository {
    public static void main(String[] args) {
        FigureRepository figureRepository = new FigureRepository();
    //figureRepository.saveNewFigure(5432, 1);
      //  List<Figure> list = figureRepository.getFiguresByGameId(5432);
      //  figureRepository.getFiguresByGameId(1);
    }
    @SneakyThrows
    public void saveNewFigure(int gameId, int figureId) {
        Statement statement = getConnection().createStatement();
       ResultSet resultSet= statement.executeQuery("SELECT count(game_id) FROM figures WHERE game_id = " + gameId + ";");
        resultSet.next();
        if(resultSet.getInt("count") > 0){
            String SQL = "UPDATE figures SET figures_id = (SELECT figures_id FROM figures WHERE game_id = " + gameId + ") || ARRAY [" + figureId + "] WHERE game_id = "+gameId+";";
            statement.execute(SQL);
        }
        else {
            String SQL = "INSERT INTO figures " +
                    "(game_id, figures_id)" +
                    "VALUES" +
                    " ("+gameId+", '{"+figureId+"}')";
            statement.execute(SQL);
        }


    }
    @SneakyThrows
    public List<Figure> getFiguresByGameId(int gameId) {
        List<Figure> figures = new ArrayList<>();
        String SQL = "SELECT figures_id FROM figures WHERE game_id = "+gameId+";";
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
            resultSet.next();
            Array arr = resultSet.getArray("figures_id");
            Integer [] urls = arr != null ? (Integer[]) arr.getArray() : null;
        for(int i:urls){
            figures.add(getFigureById(i));
        }

        return figures;
    }
    @SneakyThrows
    public Figure getFigureById(int id){
        String SQL = "SELECT type FROM figure_type WHERE figure_id ="+id+";";
        Statement statement = getConnection().createStatement();
        ResultSet resultSet =statement.executeQuery(SQL);
        resultSet.next();
        String jsonString = resultSet.getString(1);
        ObjectMapper objectMapper = new ObjectMapper();
        Figure figure = objectMapper.readValue(jsonString, Figure.class);
        return figure;
    }
}
