package com.amazon.test.config;

import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
    private static final HikariDataSource dataSource;

    static {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        ds.setUsername("admin");
        ds.setPassword("admin");
        ds.setConnectionTimeout(60000);
        ds.setMaxLifetime(1800000);
        // configure other HikariCP settings (e.g., pool size)
        dataSource = ds;
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
