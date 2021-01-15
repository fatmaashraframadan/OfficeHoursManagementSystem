<%-- 
    Document   : viewReservations
    Created on : Jan 13, 2021, 8:16:54 PM
    Author     : Maria
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="mycss.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Reservations - Office Hours Management</title>
    </head>
    <body>
        <%
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffmembers", "root", "root");
            Statement statement = con.createStatement();
            String username = request.getSession().getAttribute("session_username").toString();
            String officehourid = request.getParameter("slot");
            String sql = "SELECT * FROM staffmembers.reservation s INNER JOIN staffmembers.officehours b ON s.officehoursID = b.officehoursID INNER JOIN staffmembers.user c ON s.tousername = c.username AND c.username='" + username + "'  INNER JOIN staffmembers.slot t ON b.slotid = t.slotid AND s.officehoursID ='" + officehourid + "';";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                String status = rs.getString("online");
                if (status.equals("1")) {
                    status = "Online";
                } else {
                    status = "Offline";
                }
        %>
        <header>
            <h1> Reservations </h1>
        </header>

        <table cellspacing="5" border="1" style="height: 100%; width: 100%;">
            <tr>
                <th>Reservation ID</th>
                <th>From</th>
                <th>Location</th>
                <th>Status</th>
            </tr>
            <tr>
                <td><%= rs.getString("reservationID")%></td>
                <td><%= rs.getString("fromusername")%></td>
                <td><%= rs.getString("location")%></td>
                <td><%= status%></td>
            </tr>
        </table>
        <br>
        <a href="Userhome.jsp"><input class="Large" type="button" value="Back to Homepage"/></a>
            <%} else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("window.alert('No reserved meetings to display');");
                    out.println("window.location.href=\"staffMeetings.jsp\";");
                    out.println("</script>");
                }%>
    </body>
</html>
