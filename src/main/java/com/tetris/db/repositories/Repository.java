package com.tetris.db.repositories;

import com.tetris.db.ConnectionFactory;

import java.sql.Connection;

public interface Repository {

    default Connection getConnection() {
        return ConnectionFactory.getConnection();
    }

}
