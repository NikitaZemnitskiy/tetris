package com.tetris.db.repositories.impl;

import com.tetris.db.repositories.Repository;
import com.tetris.game.handler.MoveEvent;
import lombok.SneakyThrows;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveRepository implements Repository {
    public static void main(String[] args) {
            MoveRepository mr = new MoveRepository();
        System.out.println(mr.getListMoveEvent(1).get(0));
        System.out.println(mr.getListMoveEvent(1).get(1));
        System.out.println(mr.getListMoveEvent(1).get(2));
    }
@SneakyThrows
    public void saveNewMoveEvent(int gameId, MoveEvent event) {
        Statement statement = getConnection().createStatement();
        ResultSet resultSet= statement.executeQuery("SELECT count(game_id) FROM moves WHERE game_id = " + gameId + ";");
        resultSet.next();
        if(resultSet.getInt("count") > 0){
            System.out.println("count>0");
            String SQL = "UPDATE moves SET moves = (SELECT moves FROM moves WHERE game_id = " + gameId + ") || ARRAY ['" + event + "'] WHERE game_id = "+gameId+";";
            statement.execute(SQL);
        }
        else {
            System.out.println("count<0");
            String SQL = "INSERT INTO moves " +
                    "(game_id, moves)" +
                    "VALUES" +
                    " ("+gameId+", '{"+event+"}')";
            statement.execute(SQL);
        }

    }
    @SneakyThrows
    public List<MoveEvent> getListMoveEvent(int gameId){
        List<MoveEvent> moveEventList = new ArrayList<>();
        String SQL = "SELECT moves FROM moves WHERE game_id ="+gameId+";";
        Statement statement = getConnection().createStatement();
        ResultSet rs = statement.executeQuery(SQL);
         rs.next();
         Array arr = rs.getArray("moves");
         MoveEvent [] urls = arr != null ? (MoveEvent[]) arr.getArray() : null;
         for(MoveEvent me:urls){
             System.out.println(me);
             moveEventList.add(me);
         }
        return moveEventList;
    }


}
