
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fatma
 */
public class DataBase {

    String url = "jdbc:mysql://localhost:3306/staffmembers";
    String user = "root";
    String pass = "root";

    public DataBase() {

    }

    Connection Connect() {

        Connection con = null;
        try {
            con = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

}
