<%-- 
    Document   : GetOfficehoursSchedule
    Created on : Jan 10, 2021, 6:41:46 PM
    Author     : Nardin
--%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.*"%>
<%@page import=" javax.servlet.http.HttpServlet"%>
<%@page import ="javax.servlet.http.HttpServletRequest"%>
<%@page import= "javax.servlet.http.HttpServletResponse"%>
<%@page import ="javax.servlet.http.HttpSession"%>

<% Class.forName("com.mysql.jdbc.Driver").newInstance(); %>
<!DOCTYPE html>
<html>
    <link type="text/css" rel="stylesheet" href="mycss.css">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Office Hours Schedule - Office Hours Management</title>
    </head>

    <body>     
        <%
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffmembers", "root", "root");
                Statement statement = con.createStatement();
                String confirmmessage = " ";
                if (request.getSession().getAttribute("reservationconfirmationmess") != null) {
                    confirmmessage = request.getSession().getAttribute("reservationconfirmationmess").toString();
                
                }
                String staffusername;
                 String sql ;
                if(request.getSession().getAttribute("session_tousername") == null){
                    staffusername = request.getParameter("username");
                     sql = "SELECT * FROM staffmembers.officehours WHERE username='"+staffusername+"';";
                      ResultSet rs = statement.executeQuery(sql);
                      if(!(rs.next())){
                          session.setAttribute("checkstaffFound", "This user Not Found !");
                          response.sendRedirect("Userhome.jsp");
                      }
                      else
                       session.setAttribute("session_tousername", staffusername);
                         }
                staffusername= request.getSession().getAttribute("session_tousername").toString();
                 sql = "SELECT * FROM staffmembers.officehours s INNER JOIN staffmembers.slot d "
                         + "ON s.slotid = d.slotid AND s.username ='" + staffusername + "';";
                ResultSet rs = statement.executeQuery(sql);
                int counter = 1; 
                
                while (rs.next()) {
                    if (counter == 1) {
 
        %>
        <form action="reservation">
        <table cellspacing="5" border="1" style="height: 100%; width: 100%;">
            <tr>
                <th>Date</th> 
                <th>status</th> 
                <th>start</th> 
                <th>end</th> 
                <th>location</th> 
            </tr>

            <%counter++;
                }
            %>
            <tr>
                <td><input type="radio" name="myradio" id ="myradio" value=<%=rs.getString("officehoursID")%>>
                    <%=rs.getString("date")%>
                    <% if(rs.getString("online").equals("1")){%>
                <td >online</td><%}else{%>
                 <td>offline</td><%}%>
                <td><%=rs.getString("start")%></td>
                <td><%=rs.getString("end")%></td>
                <td><%=rs.getString("location")%></td>
            </tr>
            <%} %>
     <%if (counter > 1) {%>
            </table>
            <br>
              <input type="submit" value="reserve" class="update">
        </form>
         
        <% }%>
        <a href="Userhome.jsp"><input class="Large" type="button" value="Back to Homepage"/></a>
           
                <p style="color:black;"><% out.print(confirmmessage);
                session.setAttribute("reservationconfirmationmess", " ");%></p>   
        

                       <%
                } catch (Exception cnfe) {
                    System.err.println("Exception: " + cnfe);
                }
%>
    </body>
</html>
