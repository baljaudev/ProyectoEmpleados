package dam.proyectoempleados.util;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBUtil {
    /**
     * Creado por ONUR BASKIRT el 22.02.2016.
     */
        //Declaramos el driver JDBC.
        private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

        //Connection
        private static Connection conn = null;

        //Connection String
        //String connStr = "jdbc:oracle:thin:Username/Password@IP:Port/SID";
        //Username=HR, Password=HR, IP=localhost, IP=1521, SID=xe
        private static final String connStr = "jdbc:oracle:thin:HR/HR@localhost:1521/xe";

        //Conectar a la base de datos.
        public static void dbConnect() throws SQLException, ClassNotFoundException {
            //Configuramos el driver JDBC de Oracle.
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                System.out.println("Where is your Oracle JDBC Driver?");
                e.printStackTrace();
                throw e;
            }

            System.out.println("Oracle JDBC Driver Registered!");

            //Establecemos la conexión con Oracle usando la cadena de conexión.
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
            CachedRowSetImpl crs = null;
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
                //Estamos usando CachedRowSet.
                crs = new CachedRowSetImpl();
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
