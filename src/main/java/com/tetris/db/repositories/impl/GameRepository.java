package com.tetris.db.repositories.impl;

import com.tetris.db.repositories.Repository;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class GameRepository implements Repository {


@SneakyThrows
    public Optional<Integer> getActiveGameId() {
        String SQL = "SELECT game_id FROM game WHERE is_finish = false ORDER BY game_id DESC";
        Statement statement = getConnection().createStatement();
        ResultSet resultSet =statement.executeQuery(SQL);
        if(resultSet.next()) {
            int activeGame = resultSet.getInt(1);
            Optional<Integer> activeGameO = Optional.of(activeGame);

            return activeGameO;
        }
        else
            {return null;}
    }
@SneakyThrows
    public int createNewGame() {
        String SQL = "INSERT INTO game (is_finish) VALUES (false);";
        Statement statement = getConnection().createStatement();
        statement.execute(SQL);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM game ORDER BY game_id DESC;");
        resultSet.next();
       return resultSet.getInt(1);

    }
@SneakyThrows
    public void finishGame(int gameId) {
    String SQL = "UPDATE game SET is_finish = true WHERE game_id = "+gameId+";";
    Statement statement = getConnection().createStatement();
    statement.execute(SQL);
    }

}
