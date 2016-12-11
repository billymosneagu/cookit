/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conection;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que tiene como funcion desconectarse conectarse al hosting donde se encuentra la base de datos
 * @author billy
 */
public class DBConnection {
    /**
     * URL de conexion 
     */
    private String bolt = "jdbc:neo4j:bolt://hobby-imhbhocjfnhggbkedaohifnl.dbs.graphenedb.com:24786";
    /**
     * Atributo de tipo conexion, que conecta java con la base de datos
     */
    private static Connection conn;

    private DBConnection() {

        try {
              Driver driver=new org.neo4j.jdbc.Driver();
              DriverManager.registerDriver(driver);

            conn = DriverManager.getConnection(bolt, "Cookit", "ZBqA8IW0SaIoxl90XA6V");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * Metodo estatico que nos evuelve una instancia unica de la conexion para poder trabajar sobre la misma sesion
     * @return 
     */
    public static Connection getConnection() {
        if (conn == null) {
            new DBConnection();
        }
        return conn;
    }
    /**
     * Metodo para cerrar la conexion
     */
    public static void closeConnection() {
        try {
            conn.close();
            conn=null;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
