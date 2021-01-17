/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
@WebServlet(urlPatterns = {"/reservation"})
public class reservation extends HttpServlet {

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
            HttpSession session = request.getSession(true);
            Class.forName("com.mysql.cj.jdbc.Driver");
            DataBase ob = new DataBase();
            Connection con = ob.Connect();
            Statement statement = con.createStatement();
            SendEmail sm = new SendEmail();
            String subject = "A new meeting reserved!";
            String radio = request.getParameter("myradio");
            String sql = "SELECT* FROM staffmembers.reservation WHERE officehoursID='" + radio + "';";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                out.println("<script type=\"text/javascript\">");
                out.println("window.alert('Already reserved !!!');");
                out.println("window.location.href=\"GetOfficehoursSchedule.jsp\";");
                out.println("</script>");
                //session.setAttribute("reservationconfirmationmess", "Already reserved !! ");
                //response.sendRedirect("GetOfficehoursSchedule.jsp");

            } else {
                String tousername = request.getSession().getAttribute("session_tousername").toString();
                String fromusernamer = request.getSession().getAttribute("session_username").toString();
                String fromName = request.getSession().getAttribute("session_name").toString();
                sql = "INSERT INTO staffmembers.reservation (fromusername,tousername,officehoursID) VALUES"
                        + "('" + fromusernamer + "','" + tousername + "','" + radio + "');";
                statement.executeUpdate(sql);
                sql = "SELECT * FROM "
                        + "staffmembers.officehours b INNER JOIN staffmembers.slot t ON t.slotid = b.slotid "
                        + " AND b.officehoursID ='" + radio + "' INNER JOIN staffmembers.user c ON c.username = b.username;";
                rs = statement.executeQuery(sql);
                rs.next();
                String content = fromName + "(" + fromusernamer + ") has reserved "
                        + "the office hour on date " + rs.getString("date") + " from ["
                        + rs.getString("start") + " - " + rs.getString("end") + "]";

                //A student has reserved a meeting
                statement = con.createStatement();
                sql = "INSERT INTO staffmembers.notifications (toUsername, content) values ('"
                        + tousername + "','" + content + "');";
                statement.executeUpdate(sql);
                sql = "Select * from staffmembers.user where username='" + tousername + "';";
                ResultSet rs1 = statement.executeQuery(sql);
                String email = "", name = "";
                if (rs1.next()) {
                    email = rs1.getString("email");
                    name = rs1.getString("name");
                }
                sm.Sendemail(email, subject, name, content);

                //Notification on day of the meeting(to student)
                content = "You have a meeting today with "
                        + rs.getString("name") + "(" + tousername + ")" + " from ["
                        + rs.getString("start") + " - " + rs.getString("end") + "]";
                statement = con.createStatement();
                sql = "INSERT INTO staffmembers.notifications (toUsername, content, date) values ('"
                        + fromusernamer + "','" + content + "','" + rs.getString("date") + "');";
                statement.executeUpdate(sql);

                //Notification on day of the meeting(to staff member)
                content = "You have a meeting today with " + fromName + "(" + fromusernamer + ") "
                        + " from [" + rs.getString("start") + " - " + rs.getString("end") + "]";
                statement = con.createStatement();
                sql = "INSERT INTO staffmembers.notifications (toUsername, content,date) values ('"
                        + tousername + "','" + content + "','" + rs.getString("date") + "');";
                statement.executeUpdate(sql);
                out.println("<script type=\"text/javascript\">");
                out.println("window.alert('Reservation Done Successfully!');");
                out.println("window.location.href=\"GetOfficehoursSchedule.jsp\";");
                out.println("</script>");

                //session.setAttribute("reservationconfirmationmess", "Reservation Done Successfully! ");
                //response.sendRedirect("GetOfficehoursSchedule.jsp");
            }
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
