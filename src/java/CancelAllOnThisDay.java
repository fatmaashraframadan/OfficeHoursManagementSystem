/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Maria
 */
@WebServlet(urlPatterns = {"/CancelAllOnThisDay"})
public class CancelAllOnThisDay extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DataBase ob = new DataBase();
            Connection con = ob.Connect();
            HttpSession session = request.getSession(true);
            String username = request.getSession().getAttribute("session_username").toString();
            SendEmail sm = new SendEmail();
            String subject = "Meeting Cancelled";
            ArrayList<String> returned = new ArrayList<>();
            Statement statement = con.createStatement();
            String reservationid = request.getParameter("myradio");
            String sql = "SELECT * FROM staffmembers.reservation s INNER JOIN staffmembers.officehours b ON s.officehoursID "
                    + "= b.officehoursID INNER JOIN staffmembers.user c ON s.tousername = c.username AND c.username='"
                    + username + "' AND s.reservationID = '" + reservationid + "'INNER JOIN staffmembers.slot t ON b.slotid = t.slotid;";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            String date = rs.getString("date");
            rs.close();
            statement = con.createStatement();
            sql = "SELECT * FROM staffmembers.reservation s INNER JOIN staffmembers.officehours b ON s.officehoursID "
                    + "= b.officehoursID INNER JOIN staffmembers.user c ON s.tousername = c.username AND c.username='"
                    + username + "' INNER JOIN staffmembers.slot t ON b.slotid = t.slotid AND b.date = '" + date + "';";
            rs = statement.executeQuery(sql);

            String content = "";
            ArrayList<String> Users = new ArrayList<>();
            ArrayList<String> Contents = new ArrayList<>();
            while (rs.next()) {
                content = rs.getString("name") + "(" + rs.getString("username") + ") has cancelled "
                        + "the reservation on date " + rs.getString("date") + " from ["
                        + rs.getString("start") + " - " + rs.getString("end") + "]";
                String ResID = rs.getString("reservationID");
                String fromUser = rs.getString("fromusername");
                Users.add(fromUser);
                Contents.add(content);
                sql = "DELETE FROM staffmembers.reservation WHERE reservationID='" + ResID + "';";
                statement = con.createStatement();
                statement.executeUpdate(sql);

            }
            for (int i = 0; i < Users.size(); i++) {
                sql = "Select * from staffmembers.user where username='" + Users.get(i) + "';";
                rs = statement.executeQuery(sql);
                String email = "", name = "";
                String contentt = Contents.get(i);
                if (rs.next()) {
                    email = rs.getString("email");
                    name = rs.getString("name");
                }
                statement = con.createStatement();
                sql = "INSERT INTO staffmembers.notifications (toUsername, content) values ('"
                        + Users.get(i) + "','" + contentt + "');";
                statement.executeUpdate(sql);

                sm.Sendemail(email, subject, name, contentt);
            }

            out.println("<script type=\"text/javascript\">");
            out.println("window.alert('All meeting reservations on this day are cancelled successfully');");
            out.println("window.location.href=\"staffMeetings.jsp\";");
            out.println("</script>");

            //session.setAttribute("cancelationconfirmationmess", "Deleted Succesfully! ");
            //response.sendRedirect("staffMeetings.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CancelAllOnThisDay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CancelAllOnThisDay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CancelAllOnThisDay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CancelAllOnThisDay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
