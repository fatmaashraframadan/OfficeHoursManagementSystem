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
            DataBase ob = new DataBase();
            Connection con = ob.Connect();
            Statement statement = con.createStatement();
            String message = request.getParameter("message");
            String ID = request.getParameter("tousername");
            String Selected = request.getParameter("To");
            String fromusername = request.getSession().getAttribute("session_username").toString();

            String type = request.getSession().getAttribute("session_type").toString();
            String sql;

            boolean check = false;
            SendEmail sm = new SendEmail();
            String subject = "New Message";
            if (type.equals("0")) {
                sql = "SELECT* FROM staffmembers.user WHERE username='" + ID + "';";
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                    String newType = rs.getString("type");
                    if (newType.equals("0")) {
                        check = true;
                        out.println("<script type=\"text/javascript\">");
                    out.println("window.alert('This staff member is not found!');");
                    out.println("window.location.href=\"Messages.jsp\";");
                    out.println("</script>");
                        //session.setAttribute("sendingconfirmationmess", "This staff not found! ");
                        //response.sendRedirect("Messages.jsp");
                        
                    }
                    
                }
            }
            if (check == false) {
                
                if (Selected.equals("All")) {

                    sql = "SELECT* FROM staffmembers.subjecttostaff WHERE subjectid='" + ID + "';";
                    ResultSet rs = statement.executeQuery(sql);
                    if (!(rs.next())) {
                        //session.setAttribute("sendingconfirmationmess", "No subject Found! ");
                       //response.sendRedirect("Messages.jsp");
                        out.println("<script type=\"text/javascript\">");
                    out.println("window.alert('No subject with this ID was Found!');");
                    out.println("window.location.href=\"Messages.jsp\";");
                    out.println("</script>");
                    } else {
                        sql = "SELECT* FROM staffmembers.subjecttostaff s INNER JOIN staffmembers.user u ON "
                                + "s.username = u.username  AND s.subjectid='" + ID + "';";
                        rs = statement.executeQuery(sql);
                        while (rs.next()) {
                            sql = "INSERT INTO staffmembers.message(fromusername,tousername,content) VALUES"
                                    + "('" + fromusername + "','" + rs.getString("username") + "','" + message + "');";
                            statement = con.createStatement();
                            statement.executeUpdate(sql);
                            String email = rs.getString("email");
                            String name = rs.getString("name");
                            statement.close();
                            statement = con.createStatement();
                            sql = "Select * from staffmembers.user where username='" + fromusername + "';";
                            rs = statement.executeQuery(sql);
                            String  Name = "";
                            if (rs.next()) {
                                Name = rs.getString("name");
                            }
                            sm.Sendemail(email, subject, name, (Name + "(" +fromusername + "): " + message));
                        }

                        //response.sendRedirect("Messages.jsp");
                        out.println("<script type=\"text/javascript\">");
                    out.println("window.alert('Your message was sent successfully');");
                    out.println("window.location.href=\"Messages.jsp\";");
                    out.println("</script>");
                    }
                } else {
                    sql = "SELECT * FROM staffmembers.user WHERE username='" + ID + "';";
                    ResultSet rs = statement.executeQuery(sql);
                    if (!(rs.next())) {
                        out.println("<script type=\"text/javascript\">");
                    out.println("window.alert('This staff member was not found!');");
                    out.println("window.location.href=\"Messages.jsp\";");
                    out.println("</script>");
                        //session.setAttribute("sendingconfirmationmess", "This staff not found! ");
                        //response.sendRedirect("Messages.jsp");
                    } else {

                        statement = con.createStatement();
                        sql = "SELECT * FROM staffmembers.user WHERE username='" + ID + "';";
                        rs = statement.executeQuery(sql);
                        if (rs.next()) {

                            sql = "INSERT INTO staffmembers.message(fromusername,tousername,content) VALUES"
                                    + "('" + fromusername + "','" + rs.getString("username") + "','" + message + "');";
                            String userName = rs.getString("username");
                            String email = rs.getString("email");
                            String name = rs.getString("name");
                            statement = con.createStatement();
                            statement.executeUpdate(sql);
                            sql = "Select * from staffmembers.user where username='" + fromusername + "';";
                            rs = statement.executeQuery(sql);
                            String  Name = "";
                            if (rs.next()) {
                                Name = rs.getString("name");
                            }

                            sm.Sendemail(email, subject, name, (Name + "(" +fromusername + "): " + message));
                        }
                        //response.sendRedirect("Messages.jsp");
                        out.println("<script type=\"text/javascript\">");
                    out.println("window.alert('Your message was sent successfully');");
                    out.println("window.location.href=\"Messages.jsp\";");
                    out.println("</script>");

                    }
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
