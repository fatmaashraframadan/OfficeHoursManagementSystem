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
@WebServlet(urlPatterns = {"/sendmessage"})
public class sendmessage extends HttpServlet {

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
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffmembers", "root", "root");
            Statement statement = con.createStatement();
            String message = request.getParameter("message");
            String ID = request.getParameter("tousername");
            String Selected = request.getParameter("To");
            String fromusername = request.getSession().getAttribute("session_username").toString();
            String type = request.getSession().getAttribute("session_type").toString();
            String sql;
            if(type.equals("0")){
                sql = "SELECT* FROM staffmembers.user WHERE username='" + ID + "';";
                ResultSet rs = statement.executeQuery(sql);
                rs.next();
                if(rs.getString("type").equals("0")){
                    session.setAttribute("sendingconfirmationmess", "This staff not found! ");
                    response.sendRedirect("Messages.jsp");
                }
            }
            else{
            if (Selected.equals("All")) {
                sql = "SELECT* FROM staffmembers.subjecttostaff WHERE subjectid='" + ID + "';";
                ResultSet rs = statement.executeQuery(sql);
                if (!(rs.next())) {
                    session.setAttribute("sendingconfirmationmess", "No subject Found! ");
                    response.sendRedirect("Messages.jsp");
                } else {
                    sql = "SELECT* FROM staffmembers.subjecttostaff WHERE subjectid='" + ID + "';";
                    rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        sql = "INSERT INTO staffmembers.message(fromusername,tousername,content) VALUES"
                                + "('" + fromusername + "','" + rs.getString("username") + "','" + message + "');";
                        statement = con.createStatement();
                        statement.executeUpdate(sql);
                    }

                    response.sendRedirect("Messages.jsp");
                }
            } else {
                sql = "SELECT* FROM staffmembers.user WHERE username='" + ID + "';";
                ResultSet rs = statement.executeQuery(sql);
                if (!(rs.next())) {
                    session.setAttribute("sendingconfirmationmess", "This staff not found! ");
                    response.sendRedirect("Messages.jsp");
                } else {
                    sql = "SELECT* FROM staffmembers.user WHERE username='" + ID + "';";
                    rs = statement.executeQuery(sql);
                    if (rs.next()) {
                        
                        sql = "INSERT INTO staffmembers.message(fromusername,tousername,content) VALUES"
                                + "('" + fromusername + "','" + rs.getString("username") + "','" + message + "');";
                        statement.executeUpdate(sql);
                    }
                    response.sendRedirect("Messages.jsp");

                }
            }

        }} catch (Exception ex) {
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
