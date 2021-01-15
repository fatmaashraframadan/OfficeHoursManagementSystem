<%-- 
    Document   : meetings
    Created on : Jan 11, 2021, 10:17:08 PM
    Author     : Nardin
--%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link type="text/css" rel="stylesheet" href="mycss.css">
    <head>
        <title>Meetings - Office Hours Management</title>
    </head>
    <body>
        <%
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffmembers", "root", "root");
                Statement statement = con.createStatement();
                String username = request.getSession().getAttribute("session_username").toString();
                String confirmmessage = "";
                if (request.getSession().getAttribute("cancelationconfirmationmess") != null) {
                    confirmmessage = request.getSession().getAttribute("cancelationconfirmationmess").toString();

                }
                String sql = "SELECT * FROM staffmembers.reservation s INNER JOIN staffmembers.officehours b ON s.officehoursID = b.officehoursID INNER JOIN staffmembers.user c ON s.fromusername = c.username AND c.username='" + username + "'  INNER JOIN staffmembers.slot t ON b.slotid = t.slotid;";
                ResultSet rs = statement.executeQuery(sql);
                int counter = 1;
                while (rs.next()) {
                    if (counter == 1) {
        %>

        <header>
            <h1> Meetings </h1>
        </header>
        <form action="CancelMeeting">
            <table cellspacing="5" border="0" style="height: 100%; width: 100%;"></table>
            <table border="1">
                <tr>
                    <th>Meeting ID</th> 
                    <th> username</th>  
                    <th>From</th> 
                    <th>To</th>
                    <th>Date</th>
                    <th>Location</th>
                    <th>Status</th>
                </tr>

                <%counter++;
                    }
                %>
                <tr>
                    <td><input type="radio" name=myradio value=<%=rs.getString("reservationID")%>>
                        <%=rs.getString("reservationID")%>
                    <td><%=rs.getString("tousername")%></td>
                    <td><%=rs.getString("start")%></td>
                    <td><%=rs.getString("end")%></td>
                    <td><%=rs.getString("date")%></td>
                    <td><%=rs.getString("location")%>
                    </td>  <% if (rs.getString("online").equals("1")) {%>
                    <td >online</td><%} else {%>
                    <td>offline</td><%}%>
                </tr>
                <%} %>
                <%if (counter > 1) {%>
            </table>
            <br>
            <input  class = "getcon" type="submit" value="Cancel" class="update">

        </form>
        <br>

        <% } else {
                out.println("<script type=\"text/javascript\">");
                out.println("window.alert('No reserved meetings to display');");
                out.println("window.location.href=\"Userhome.jsp\";");
                out.println("</script>");
            }%>

            <p style="color:black;"><% out.print(confirmmessage);
                session.setAttribute("cancelationconfirmationmess", " ");%></p>  
        <a href="Userhome.jsp"><input class="Large" type="button" value="Back to Homepage"/></a>
            <%
                } catch (Exception cnfe) {
                    System.err.println("Exception: " + cnfe);
                }
            %>
    </body>
</html>
