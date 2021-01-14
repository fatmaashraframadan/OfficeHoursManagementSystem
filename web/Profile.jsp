<%-- 
    Document   : Profile
    Created on : Jan 1, 2021, 3:21:35 AM
    Author     : Nardin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>

<% Class.forName("com.mysql.cj.jdbc.Driver");//Class.forName("com.mysql.jdbc.Driver").newInstance(); %>
<!DOCTYPE html>
<html>
    <link type="text/css" rel="stylesheet" href="mycss.css">
    <script type="text/javascript">
        function sendajax() {
            var email = document.getElementById("email").value;
            var name = document.getElementById("name").value;
            var password = document.getElementById("password").value;
            var phonenumber = document.getElementById("phonenumber").value;

            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function ()
            {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                {
                    document.getElementById("show_response").innerHTML = xmlhttp.responseText;
                }
            }

            xmlhttp.open("GET", "UpdateProfile?email=" + email + "&name=" + name + "&phonenumber=" + phonenumber + "&password=" + password, true);
            xmlhttp.send();
        }
    </script> 

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile - Office Hours Management</title>

    </head>
    <body>

        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/staffmembers";
                String user = "root";
                String pass = "root";

                Connection con = null;
                con = DriverManager.getConnection(url, user, pass);

                Statement statement = con.createStatement();
                String username = request.getSession().getAttribute("session_username").toString();
                String sql = "SELECT * FROM staffmembers.user WHERE username='" + username + "';";
                ResultSet rs = statement.executeQuery(sql);
                String name = "";
                String password = "";
                String phonenumber = "";
                String Email = "";
                while (rs.next()) {
                    name = rs.getString("name");
                    password = rs.getString("password");
                    phonenumber = rs.getString("phonenumber");
                    Email = rs.getString("email");
                }
        %>

        <div class = "profile">
            <h1>Student ID : <%=username%></h1>
            <form class="Login" >
                <table class="tab1">
                    <tr>
                        <td><label >Name:                </label></td>
                        <td><input class="lab"type= "text" id="name" name="name" value="<%=name%>"/></td>

                    </tr>
                    <tr>
                        <td> <label >Password:            </label></td>
                        <td><input class="lab"type= "text" id="password" name="password" value="<%=password%>"/></td>

                    </tr>
       
                    <tr>
                        <td> <label >Phone Number:        </label></td>
                        <td><input class="lab"type= "text" id="phonenumber" name="phonenumber" value="<%=phonenumber%>"/></td>

                    </tr>
         
                    <tr>
                        <td><label >Email Address:       </label></td>
                        <td><input class="lab"type= "text" id="email" name="email" value="<%=Email%>"/></td>     
                    </tr>
           
                </table>
                <input type="button" value="Save Update" onclick="sendajax()" class="update">
                <div id="show_response">  </div>
            </form> 
        </div>
<a href="Userhome.jsp"><input class="Large" type="button" value="Back to Homepage"/></a>
        <%
            } catch (Exception cnfe) {
                System.err.println("Exception: " + cnfe);
            }
        %>
    </body>
</html>
