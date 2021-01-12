/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
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
@WebServlet(urlPatterns = {"/SignUpValidation"})
public class SignUpValidation extends HttpServlet {

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

            Class.forName("com.mysql.cj.jdbc.Driver");// Class.forName("com.mysql.jdbc.Driver");
            DataBase ob = new DataBase();
            Connection con = ob.Connect();

            Statement statement = con.createStatement();
            PreparedStatement ps = null;
            String UserEmail = request.getParameter("Email");
            String Name = request.getParameter("Name");
            String type = request.getParameter("userType");

            String sql = "SELECT * FROM staffmembers.user ;";
            ResultSet rs = statement.executeQuery(sql);

            boolean found = false;
            while (rs.next()) {
                if (UserEmail.equals(rs.getString("email"))) {
                    found = true;
                }
            }
            if (found) {
                out.print("This Email is already exist!");
            } else {
                //HttpSession session = request.getSession(true);
                //session.setAttribute("session_UserEmail", UserEmail);
                SendEmail sm = new SendEmail();
                String code = sm.getRandom();
                boolean sended = sm.Sendemail(UserEmail, Name, code);
                if (sended) {
                    //  session.setAttribute("session-authcode", code);
                    sql = "INSERT INTO staffmembers.user (password,email,name,type) VALUES"
                            + "('" + code + "','" + UserEmail + "','" + Name + "','" + type + "');";

                    statement.executeUpdate(sql);
                    out.print("Verification mail is sent to you successfuly ,Please check your email to know your temporary password !");
                }
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
        HttpSession s = request.getSession();
        String c = (String) s.getAttribute("captcha");
        String verifyCaptcha = request.getParameter("captcha");
        if (c.equals(verifyCaptcha)) {
            request.setAttribute("username", request.getParameter("username"));
            request.setAttribute("password", request.getParameter("password"));

            request.getRequestDispatcher("SignUp").forward(request, response);
        } else {
            request.setAttribute("Error", "InValid Captcha");
            request.getRequestDispatcher("SignUp").forward(request, response);
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
