<%-- 
    Document   : Notifications
    Created on : Jan 12, 2021, 12:29:16 AM
    Author     : Nardin
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
        <title>Notifications - Office Hours Management</title>
    </head>
    <body>
        <h2 style="text-align: center; color:blue;"> Notifications </h2>
        <%
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/staffmembers";
            String user = "root";
            String pass = "root";
            Connection con = null;
            con = DriverManager.getConnection(url, user, pass);
            Statement statement = con.createStatement();
            String username = request.getSession().getAttribute("session_username").toString();
            String sql = " Select * from staffmembers.notifications where toUsername = '"
                    + username + "';";
            ResultSet rs = statement.executeQuery(sql);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String today = sdf.format(new Date()).toString();
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            Date todays = formater.parse(today);
            Date dateofT;
            int days;

        %>

        <ul class="noti">
            <% while (rs.next()) {
                    if (!(rs.getString("date") == null)) {
                        dateofT = formater.parse(rs.getString("date"));
                        days = (int) ((todays.getTime() - dateofT.getTime()) / (1000 * 60 * 60 * 24));
                        if (days == 0) {%>             
            <li class="noti-li"> <%= rs.getString("content")%> </li>
                <% }
                } else {%>
            <li class="noti-li"> <%= rs.getString("content")%> </li>
                <%}
                    }%>
        </ul>
        <br>
        <a href="Userhome.jsp"><input class="Large" type="button" value="Back to Homepage"/></a>
    </body>
</html>
