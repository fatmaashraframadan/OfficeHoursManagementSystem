<%-- 
    Document   : SignUp
    Created on : Dec 31, 2020, 10:38:08 PM
    Author     : Nardin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link type="text/css" rel="stylesheet" href="mycss.css">

    <script type="text/javascript">
        function sendajax() {
            var email = document.getElementById("Email").value;
            var name = document.getElementById("Name").value;
            var radio;
            if (document.getElementById("staff").checked)
            {
                radio = "1";
            } else
            {
                radio = "0";
            }
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function ()
            {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                {
                    document.getElementById("show_response").innerHTML = xmlhttp.responseText;
                }
            }

            xmlhttp.open("GET", "SignUpValidation?Email=" + email + "&Name=" + name + "&userType=" + radio, true);
            xmlhttp.send();
        }
    </script> 

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SignUp</title>

    </head>
    <body>
        <h1 class = "signh">SignUp</h1>
        
        <div class="signup">
             <form method = "post">

                <div class="signup-div" >
                    
                    <input type="radio" name="userType" value="0" id="student"
                           class="register-switch-input" checked >
                    <label for="student" class="register-switch-label">Student</label>

                    <input type="radio" name="userType" value="1" id="staff" 
                           class="register-switch-input" >
                    <label for="staff" class="register-switch-label">Staff</label>
                </div>

                <input type="email" class="register-input" name="Email" id="Email" placeholder="Email address" />
                <br> <br>

                <input type="text" class="register-input" name ="Name" id="Name" placeholder="Full Name" />
                <br>
<!--
                <table>
                    <tr id = "cap">
                        <td><br><br><br><br><img src = "Captcha"></td>

                        <td><br><br><br><br><br><input type="text" name="captcha"></td>

                    </tr>

                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value = "Save"></td>
                    </tr>
                </table>  
-->
                <br>
                <input type="button" value="Create Account" onclick="sendajax()" class="register-button">
                <div id="show_response">  </div>
                <br>
                <button class = "loginbtnsignup" type="submit" formaction="Login.jsp">Login</button>
            </form>
        </div>

    </body>
</html>
