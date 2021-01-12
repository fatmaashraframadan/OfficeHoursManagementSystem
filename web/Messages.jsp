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

        <title>JSP Page</title>
    </head>
    <body>
        <%
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffmembers", "root", "root");
                Statement statement = con.createStatement();
                String username = request.getSession().getAttribute("session_username").toString();
                String type = request.getSession().getAttribute("session_type").toString();

                String confirmmessage = "";
                if (request.getSession().getAttribute("sendingconfirmationmess") != null) {
                    confirmmessage = request.getSession().getAttribute("sendingconfirmationmess").toString();

                }
                String sql = "SELECT * FROM staffmembers.message s INNER JOIN staffmembers.user c ON s.tousername = c.username AND c.username='" + username + "';";
                ResultSet rs = statement.executeQuery(sql);%>

        <form action="sendmessage" id="Messageform">
            <br><br>
            <label for="fname">message</label>
            <textarea class="lab" id="message" name="message" placeholder="write you message" 
                      rows="10" cols="30"></textarea>

            <td><input class="lab"type= "text" id="tousername" name="tousername" placeholder="write Staff or subject ID "/></td>

            <label for="cars">To:</label>
            <select id="To" name="To" >
                <option value="All">All staff of subject</option>
                <%
                    if (type.equals("0")) {
                %>
                <option value="TA"> Specific TA </option>   
                <%
                } else if (type.equals("1")) {
                %>
                <option value="TA">Specific TA / Student</option> 
                <%}%>
            </select>
            <input type="submit" value="send" class="update">
        </form>
        <br>
            <p style="color:black;"><% out.print(confirmmessage);
                session.setAttribute("sendingconfirmationmess", " ");%></p>  
        <br>
        <% int counter = 1;
            while (rs.next()) {
                if (counter == 1) {
        %>
        <form>
            <table cellspacing="5" border="0"></table>
            <table border="1">
                <tr>

                    <th> From</th>  
                    <th>Content</th> 

                </tr>

                <%counter++;
                    }
                %>
                <tr>

                    <td><%=rs.getString("fromusername")%></td>
                    <td><%=rs.getString("content")%></td>
                </tr>
                <%} %>
                <%if (counter > 1) {%>
            </table>

        </form>

        <% }
            } catch (Exception cnfe) {
                System.err.println("Exception: " + cnfe);
            }
        %>
    </body>
</html>
