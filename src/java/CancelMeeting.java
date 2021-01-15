/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nardin
 */
@WebServlet(urlPatterns = {"/CancelMeeting"})
public class CancelMeeting extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DataBase ob = new DataBase();
            Connection con = ob.Connect();
            Statement statement = con.createStatement();
            HttpSession session = request.getSession(true);
            SendEmail sm = new SendEmail();
            String subject = "Meeting Cancelled";
            String reservationid = request.getParameter("myradio");
            String type = request.getSession().getAttribute("session_type").toString();
            String fromUser = request.getSession().getAttribute("session_username").toString();
            String sql;
            ResultSet rs;
            String toUser;
            String content;
            if (type.equals("1")) {
                sql = "SELECT * FROM staffmembers.reservation s INNER JOIN "
                        + "staffmembers.officehours b ON s.officehoursID = b.officehoursID "
                        + "INNER JOIN staffmembers.user c ON s.tousername = c.username "
                        + "INNER JOIN staffmembers.slot t "
                        + "ON b.slotid = t.slotid AND s.reservationID ='" + reservationid + "';";
                rs = statement.executeQuery(sql);
                rs.next();
                toUser = rs.getString("fromusername");
                content = rs.getString("name") + "(" + fromUser + ") has cancelled "
                        + "the reservation on date " + rs.getString("date") + " from ["
                        + rs.getString("start") + " - " + rs.getString("end") + "]";
                sql = "Select * from staffmembers.user where username='" + toUser + "';";
                rs = statement.executeQuery(sql);
                String email = "", name = "";
                if (rs.next()) {
                    email = rs.getString("email");
                    name = rs.getString("name");
                }
                sql = "INSERT INTO staffmembers.notifications (toUsername, content) values ('"
                        + toUser + "','" + content + "');";
                statement.executeUpdate(sql);

                sm.Sendemail(email, subject, name, content);
            } else {
                sql = "SELECT * FROM staffmembers.reservation s INNER JOIN "
                        + "staffmembers.officehours b ON s.officehoursID = b.officehoursID "
                        + "INNER JOIN staffmembers.user c ON s.fromusername = c.username "
                        + "INNER JOIN staffmembers.slot t "
                        + "ON b.slotid = t.slotid AND s.reservationID ='" + reservationid + "';";
                rs = statement.executeQuery(sql);
                rs.next();
                toUser = rs.getString("tousername");
                content = rs.getString("name") + "(" + fromUser + ") has cancelled "
                        + "the reservation on date " + rs.getString("date") + " from ["
                        + rs.getString("start") + " - " + rs.getString("end") + "]";
                sql = "INSERT INTO staffmembers.notifications (toUsername, content) values ('"
                        + toUser + "','" + content + "');";
                statement.executeUpdate(sql);
                sql = "Select * from staffmembers.user where username='" + toUser + "';";
                rs = statement.executeQuery(sql);
                String email = "", name = "";
                if (rs.next()) {
                    email = rs.getString("email");
                    name = rs.getString("name");
                }

                sm.Sendemail(email, subject, name, content);
            }
            sql = "DELETE FROM staffmembers.reservation WHERE reservationID='" + reservationid + "';";
            statement.executeUpdate(sql);

            out.println("<script type=\"text/javascript\">");
            out.println("window.alert('Meeting reservation cancelled successfully');");
            if (type.equals("1")) {
                out.println("window.location.href=\"staffMeetings.jsp\";");
            } else {
                out.println("window.location.href=\"meetings.jsp\";");
            }
            out.println("</script>");

            //session.setAttribute("cancelationconfirmationmess", "Deleted Succesfully! ");
            //response.sendRedirect("meetings.jsp");
        } catch (Exception ex) {
            ex.printStackTrace();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
