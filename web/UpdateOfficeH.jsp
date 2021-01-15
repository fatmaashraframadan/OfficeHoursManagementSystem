<%-- 
    Document   : UpdateOfficeH
    Created on : Jan 14, 2021, 11:46:47 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/staffmembers";
            String user = "root";
            String pass = "root";

            Connection con = null;
            con = DriverManager.getConnection(url, user, pass);

            Statement statement = con.createStatement();
            String username = request.getSession().getAttribute("session_username").toString();
            String Oid = request.getParameter("myradio");
            String SID = "";
            String status = "";
            String sql = "Select * from staffmembers.officehours o INNER JOIN staffmembers.slot s ON"
                    + " o.slotid = s.slotid AND o.username ='" + username + "' AND o.officehoursID"
                    + "= '" + Oid +"';";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                SID = rs.getString("slotid");
                status = rs.getString("online");

        %>
    <form>
        <table>       
<input type="hidden"  name="officeHoursID" id="officeHoursID" value=<%= Oid %>>
                <input type="hidden"  name="slotid" id="slotid" value=<%= SID %> >
            <td><%=Oid%></td>

            <td><input name="location" type="text" value=<%= rs.getString("location")%>></td> 

            <td>
                <select id="status" name="status" >
                    <%
                        if (status.equals("Online")) {
                    %>
                    <option value="1">Online</option> 
                    <option value="0">Offline</option>
                    <% } else {%>
                    <option value="0">Offline</option>
                    <option value="1">Online</option>
                    <% }%>
                </select>
            </td>
            <td><input name="date" id="date" type="text" value=<%= rs.getString("date")%>></td>
            <%  sql = "Select * from staffmembers.slot;";
             rs = statement.executeQuery(sql);%>
                <td>
                 <select id="slot" name="slot" >
                    <% while(rs.next()){ %>
                    <option value="<%= rs.getString("slotid") %>"> <%= 
                   (rs.getString("start") + " " + rs.getString("end"))%> </option> 
                    <% } %>
                </select> 
            </td>
            <%}%>
          </table>
          <input type="submit" value="Update" formaction="UpdateOfficeHour">
    </form>
</body>
</html>
