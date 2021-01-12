<%-- 
    Document   : Userhome
    Created on : Jan 1, 2021, 2:39:14 AM
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
    <link rel="icon" type="image/png" sizes="96x96" href="favicon-96x96.png">
    <script type="text/javascript">
        function sendajax() {
            var username = document.getElementById("username").value;


            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function ()
            {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                {
                    document.getElementById("show_response").innerHTML = xmlhttp.responseText;
                }
            }

            xmlhttp.open("GET", "GetContact?username=" + username, true);
            xmlhttp.send();
        }
    </script>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>

    <body>     
        <%
            String check = "";
            String checkstaff = "";
            if (request.getSession().getAttribute("checkFound") != null) {
                check = request.getSession().getAttribute("checkFound").toString();

            }
            if (request.getSession().getAttribute("checkstaffFound") != null) {
                checkstaff = request.getSession().getAttribute("checkstaffFound").toString();

            }
            String username = request.getSession().getAttribute("session_username").toString();
            String type = request.getSession().getAttribute("session_type").toString();

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffmembers", "root", "root");
            Statement statement = con.createStatement();

            String sql = "SELECT* FROM staffmembers.user WHERE username='" + username + "';";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();

        %>
        <h1 class="id">Welcome <%= rs.getString("name")%></h1>
        <%
            if (type.equals("0")) {
                session.setAttribute("session_tousername",null);
        %>
        <ul>
            <li><a href="Profile.jsp">Profile</a></li>
            
            <li><a href="#contact">Contact</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="meetings.jsp">Meetings</a></li>
            <li><a href="Notifications.jsp">Notifications</a></li>
            <li><a href="Messages.jsp">Messages</a></li>
            <li><a href="Logout">Logout</a></li>
        </ul>
        <div style="margin-left: 12%">
            <form >
                <label>Enter staff member username: </label><input id="username" name="username" placeholder="Staff member username"/>
                <br>
                <input class = "getcon" type="submit" value="View Office hours" formaction="GetOfficehoursSchedule.jsp" class="update">
                <input class = "getcon" type="button" value="Get Contact" onclick="sendajax()" class="update">
                <div id="show_response">  </div>
                    <p style="color:black;"><% out.print(checkstaff);
                        session.setAttribute("checkstaffFound", " ");%></p> 
            </form>
        </div>
        <div style="margin-left: 12%">
            <form >

                <label>Enter Subject ID: </label><input id="subjectID" name="subjectID" placeholder="Subject ID"/>
                <br>
                <input class = "getcon" type="submit" value="view staff" formaction="GetSubjectStaff.jsp" class="update">

                    <p style="color:black;"><% out.print(check);
                        session.setAttribute("checkFound", " ");%></p> 
            </form>
        </div>

                        <%} else{
        %>
        <ul>
            <li><a href="Profile.jsp">Profile</a></li>   
            <li><a href="Messages.">Messages</a></li>
            <li><a href="#notifications">Notifications</a></li>
            <li><a href="staffMeetings.jsp">Meetings</a></li>
            <li><a href="#officehours">Office Hours</a></li>
            <li><a href="#contact">Contact</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="Logout">Logout</a></li>
            
        </ul>
        <div style="margin-left: 12%">
            <form >
                <label>Enter student username: </label><input id="username" name="username"
                                                              placeholder="Student username"/>
                <input class = "getcon" type="button" value="Get Contact" onclick="sendajax()" 
                       class="update">
                <div id="show_response">  </div>
                <br>

            </form>
        </div>
        <% }%>

    </body>
</html>
