/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Maria
 */
@WebServlet(urlPatterns = {"/AddOfficeHour"})
public class AddOfficeHour extends HttpServlet {

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
            Statement statement = con.createStatement();
            String username = request.getSession().getAttribute("session_username").toString();
            String location = request.getParameter("Location");
            String status = request.getParameter("status");
            String slotid = request.getParameter("slot");
            String date = request.getParameter("date");
            String dateValidation[] = date.split("/");
            boolean check = false;
            if (Integer.parseInt(dateValidation[0]) <= 31 && Integer.parseInt(dateValidation[1]) <= 12
                    && dateValidation[2].length() == 4) {
                check = true;
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("window.alert('Date entered is incorrect!! Please enter it in the following format (dd/mm/yyyy) ');");
                out.println("window.location.href=\"OfficeHours.jsp\";");
                out.println("</script>");
            }
            if (check) {
                String sql = "Insert into staffmembers.officehours (username,location,online,slotid,date) values ('" + username
                        + "','" + location + "','" + status + "','" + slotid + "','" + date + "');";
                statement.executeUpdate(sql);

                out.println("<script type=\"text/javascript\">");
                out.println("window.alert('Office hour added successfully!');");
                out.println("window.location.href=\"OfficeHours.jsp\";");
                out.println("</script>");
            }
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
            Logger.getLogger(AddOfficeHour.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddOfficeHour.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddOfficeHour.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddOfficeHour.class.getName()).log(Level.SEVERE, null, ex);
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
