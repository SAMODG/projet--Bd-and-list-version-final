package com.monsite.gestioncahierdette.core.bd;


import java.sql.*;

public interface Database {
    void openConnexion() ;

    void closeConnexion() ;

    ResultSet executeSelect() throws SQLException;

    int executeUpdate() throws SQLException;

    void initPreparedStatement(String sql) throws SQLException;
     
}
