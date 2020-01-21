package com.tetris.db.repositories.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetris.db.repositories.Repository;
import com.tetris.game.Figure;
import com.tetris.model.Point;

import lombok.SneakyThrows;



import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseCreating implements Repository {
    public static void main(String[] args) {
        DataBaseCreating db = new DataBaseCreating();
        db.createDataBase();
        db.ClassicFigureCreating1();
        db.ClassicFigureCreating2();
    }

    @SneakyThrows
    public void createDataBase(){
        String creatingGameTable = "CREATE TABLE IF NOT EXISTS game" +
                "(game_id SERIAL PRIMARY KEY, is_finish bool);";

       /* String creatingFigureTypeTable = "CREATE TABLE IF NOT EXISTS figure_type" +
                "(figure_id SERIAL PRIMARY KEY, " +
                "points integer[][2], pivot integer[2], " +
                "start_point integer[2]);";*/

        String creatingFigureTypeTable = "CREATE TABLE IF NOT EXISTS figure_type" +
                "(figure_id SERIAL PRIMARY KEY, " +
                "type VARCHAR)";

        String figuresTable = "CREATE TABLE IF NOT EXISTS figures " +
                "(game_id integer PRIMARY KEY, " +
                "figures_id integer[]);";
        String movesTable = "CREATE TABLE IF NOT EXISTS moves " +
                "(game_id INTEGER PRIMARY KEY, " +
                "moves text[]);";
        Statement statement = getConnection().createStatement();
        PreparedStatement preparedStatement = getConnection().prepareStatement(creatingGameTable);
        preparedStatement.execute();
        statement.execute(creatingFigureTypeTable);
        statement.execute(figuresTable);
        statement.execute(movesTable);

        }
        @SneakyThrows
        public void ClassicFigureCreating1(){
          //  String SQL = "INSERT INTO figure_type (points, pivot, start_point) VALUES ('{{1,2},{1,1},{2,2},{3,1}}', '{1,2}', '{10,5}');";
            List<Point> pointList = new ArrayList<>();
            pointList.add(new Point(0,1));
            pointList.add(new Point(1,1));
            pointList.add(new Point(2,1));
            pointList.add(new Point(2,2));
            Figure figure = new Figure(pointList, new Point(2,1), new Point(10,5));
            String SQL = "INSERT INTO figure_type (type) VALUES (?)";
            PreparedStatement ps = getConnection().prepareStatement(SQL);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsString = objectMapper.writeValueAsString(figure);
            ps.setObject(1,jsString);
            ps.execute();
        }
@SneakyThrows
    public void ClassicFigureCreating2(){
        //  String SQL = "INSERT INTO figure_type (points, pivot, start_point) VALUES ('{{1,2},{1,1},{2,2},{3,1}}', '{1,2}', '{10,5}');";
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(1,1));
        pointList.add(new Point(1,2));
        pointList.add(new Point(2,1));
        pointList.add(new Point(2,3));
        Figure figure = new Figure(pointList, new Point(1,2), new Point(10,5));
        String SQL = "INSERT INTO figure_type (type) VALUES (?)";
        PreparedStatement ps = getConnection().prepareStatement(SQL);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsString = objectMapper.writeValueAsString(figure);
        ps.setObject(1,jsString);
        ps.execute();
    }

    }

