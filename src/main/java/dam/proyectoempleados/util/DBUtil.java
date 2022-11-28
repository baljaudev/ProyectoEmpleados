package dam.proyectoempleados.util;


import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class DBUtil {
    /**
     * Creado por ONUR BASKIRT el 22.02.2016.
     */
        //Connection
        private static Connection conn = null;

        //Connection String
        private static final String connStr = "jdbc:sqlite:db.db";

        //Conectar a la base de datos.
        public static void dbConnect() throws SQLException, ClassNotFoundException {

            //Establecemos la conexión con SQLITE usando la cadena de conexión.
            try {
                conn = DriverManager.getConnection(connStr);
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console" + e);
                e.printStackTrace();
                throw e;
            }
        }

        //Cerramos la conexión con la base de datos.
        public static void dbDisconnect() throws SQLException {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e){
                throw e;
            }
        }

        //Base de datos, ejecutamos la Query.
        public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
            //Declaramos statement, resultSet y CachedResultSet como nulos.
            Statement stmt = null;
            ResultSet resultSet = null;
            CachedRowSet crs;
            try {
                //Conectamos a la base de datos. (Establecemos la conexión con Oracle).
                dbConnect();
                System.out.println("Select statement: " + queryStmt + "\n");

                //Creamos el statement.
                stmt = conn.createStatement();

                //Ejecutamos la select (query) operation.
                resultSet = stmt.executeQuery(queryStmt);

                //CoachedRowSet Implementation
                //Para evitar el error "java.sql.SQLRecoverableException: Closed Connection: next"
                //Estamos usando CachedRowSet. //ESTO NO FUNCIONA
                //Hemos cambiado el crs = new CachedRowSetImpl(); por la clase RowSetProvider de la linea 80
                crs = RowSetProvider.newFactory().createCachedRowSet();
                crs.populate(resultSet);
            } catch (SQLException e) {
                System.out.println("Problem occurred at executeQuery operation : " + e);
                throw e;
            } finally {
                if (resultSet != null) {
                    //Cerramos el resultSet.
                    resultSet.close();
                }
                if (stmt != null) {
                    //Cerramos el statement.
                    stmt.close();
                }
                //Cerramos la conexión con la base de datos.
                dbDisconnect();
            }
            //Return CachedRowSet
            return crs;
        }

        //Ejecutamos la Query de actualización (Update/Insert/Delete).
        public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
            //Declaramos statement como nulo.
            Statement stmt = null;
            try {
                //Conectamos a la base de datos. (Establecemos la conexión con Oracle).
                dbConnect();
                //Create Statement
                stmt = conn.createStatement();
                //Ejecutamos la operación executeUpdate con la sentencia sql dada.
                stmt.executeUpdate(sqlStmt);
            } catch (SQLException e) {
                System.out.println("Problem occurred at executeUpdate operation : " + e);
                throw e;
            } finally {
                if (stmt != null) {
                    //Close statement
                    stmt.close();
                }
                //Close connection
                dbDisconnect();
            }
        }
    }
