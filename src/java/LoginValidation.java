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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Nardin
 */
@WebServlet(urlPatterns = {"/LoginValidation"})
public class LoginValidation extends HttpServlet {

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

            Class.forName("com.mysql.cj.jdbc.Driver");//Class.forName("com.mysql.jdbc.Driver");
            DataBase ob = new DataBase();
            Connection con = ob.Connect();

            //  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffmembers", "root", "root");
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM staffmembers.user;";
            ResultSet rs = statement.executeQuery(sql);
            String Email = request.getParameter("Email");
            String password = request.getParameter("Password");
            String type = request.getParameter("userType");
            String email;
            String password1;

            boolean check = false;
            HttpSession session = request.getSession(true);
            while (rs.next()) {
                email = rs.getString("email");
                password1 = rs.getString("password");
                if (email.equals(Email) && password1.equals(password) && type.equals(rs.getString("type"))) {
                    session.setAttribute("session_username", rs.getString("username"));
                    session.setAttribute("session_name", rs.getString("name"));
                    String name = rs.getString("name");
                    session.setAttribute("session_useremail", Email);
                    session.setAttribute("session_password", password1);
                    session.setAttribute("session_type", type);
                    response.sendRedirect("Userhome.jsp");

                    check = true;
                    
                    
                    statement = con.createStatement();
                    sql = " Select * from staffmembers.notifications where toUsername = '"
                            + rs.getString("username") + "';";
                    rs = statement.executeQuery(sql);
                    int counter = 0;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String today = sdf.format(new Date()).toString();
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            Date todays = formater.parse(today);
            Date dateofT;
            int days;
            SendEmail sm = new SendEmail();
                    while (rs.next()) {
                        if (!(rs.getString("date") == null)) {
                            dateofT = formater.parse(rs.getString("date"));
                            days = (int) ((todays.getTime() - dateofT.getTime()) / (1000 * 60 * 60 * 24));
                            if (days == 0) {
                                String Content = rs.getString("content");
                                String subject = "Meeting due Today";
                                sm.Sendemail(Email, subject, name, Content );
                                
                            }
                        } 
                    }
                }

            }
            if (!check) {
                session.setAttribute("session-invaildmessage", "Email or password is incorrect");
                response.sendRedirect("Login.jsp");
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
