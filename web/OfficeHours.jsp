<%-- 
    Document   : OfficeHours
    Created on : Jan 13, 2021, 7:19:42 PM
    Author     : Maria
--%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="mycss.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Office Hours - Office Hours Management</title>
        <style>
            #Add{
                display: none;
            }
        </style>
    </head>
    <body>
        <%
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffmembers", "root", "root");
            Statement statement = con.createStatement();
            String username = request.getSession().getAttribute("session_username").toString();
            String sql = "Select * from staffmembers.officehours o INNER JOIN staffmembers.slot s ON "
                    + "o.slotid = s.slotid AND o.username = " + username + " ;";
            ResultSet rs = statement.executeQuery(sql);
            String OID = "";
            String SID = "";
        %>
        <form>
            <table cellspacing="5" border="1" style="height: 100%; width: 100%;">
                <tr>
                    <th>Office Hours ID</th>
                    <th>Location</th> 
                    <th>Status</th>
                    <th>Date</th>
                    <th>Start time</th>
                    <th>End time</th>
                    
                </tr>
                <% while (rs.next()) {
                        String status = rs.getString("online");
                        if (status.equals("1")) {
                            status = "Online";
                        } else {
                            status = "Offline";
                        }
                %>
                <tr>
                <input type="hidden"  name="officeHoursID" id="officeHoursID" value=<%= OID %>>
                <input type="hidden"  name="slotid" id="slotid" value=<%= SID %> >
                <!--<td><%= rs.getString("officehoursID")%> </td> -->
                <td><input type="radio" name=myradio value=<%=rs.getString("officehoursID")%> id="officehoursID">
                        <%=rs.getString("officehoursID")%> </td>
                
                <td><%= rs.getString("location")%></td> 
                
                <td>
                    <%= status %>
                </td>
                <td><%= rs.getString("date")%></td>
                <td><%= rs.getString("start")%></td>
                <td><%= rs.getString("end")%></td>
                
                </tr>
                <% }%>
            </table>
            <input class = "getcon" type="submit" value="Update" formaction="UpdateOfficeH.jsp">
                <input class = "getcon" type="submit" value="Delete" formaction="DeleteOfficeHour" >
                
            <!--<input class = "Large" type="submit" value="Add new Office Hour" formaction="AddOfficeHour" >-->
        </form>
            <form id="Add">
                <%
                    sql = "Select * from staffmembers.slot;";
                    rs = statement.executeQuery(sql);
                    %>
               Location:
               <input type="text" name="Location" placeholder="Enter location of your office hour.." value="">
               <input type="text" name="date" placeholder="Enter the date of your office hour.." value="">
               Status:
              <select id="status" name="status" >
                    <option value="1">Online</option> 
                    <option value="0">Offline</option>     
                </select> 
               Slot:
                <select id="slot" name="slot" >
                    <% while(rs.next()){ %>
                    <option value="<%= rs.getString("slotid") %>"> 
                  <%= (rs.getString("start") + " " + rs.getString("end"))%> </option> 
                    <% } %>
                </select> 
               <input type="submit" formaction="AddOfficeHour">
            </form>
            <button id="demobutton2" type="button" 
                    onclick="document.getElementById('Add').style.display = 'block'"> 
                Add new Office Hour
            </button>
        <br>
        <a href="Userhome.jsp"><input class="Large" type="button" value="Back to Homepage"/></a>
    </body>
</html>
