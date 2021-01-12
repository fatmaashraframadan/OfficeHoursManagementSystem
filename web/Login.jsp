

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link type="text/css" rel="stylesheet" href="mycss.css">
    <link rel="icon" type="image/png" sizes="96x96" href="favicon-96x96.png">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>


    </head>
    <body>
        <% try {

                String confirmmessage = " ";
                if (request.getSession().getAttribute("session-invaildmessage") != null) {
                    confirmmessage = request.getSession().getAttribute("session-invaildmessage").toString();
                }

        %>

        <h1 class = "signh">Login</h1>
        <div class="signup">
            <form  action="LoginValidation" >
                <div class="login-div" >
                    <input type="radio" name="userType" value="0" id="student"
                           class="register-switch-input" checked >

                    <label for="student" class="register-switch-label">Student</label>

                    <input type="radio" name="userType" value="1" id="staff" 
                           class="register-switch-input" >
                    <label for="staff" class="register-switch-label">Staff</label>
                </div>
                <input type="email" class="register-input" name="Email" placeholder="Email address" />
                <br> <br>
                <input type="password" class="register-input" name="Password" placeholder="Password" /><br><br>
                <input type="submit" value="Sign in"   class="register-button">
                <p style="color:#228B22;"><% out.print(confirmmessage);
                session.setAttribute("session-invaildmessage", " ");%></p> 
            </form>
        </div>

        <%

            } catch (Exception cnfe) {
                System.err.println("Exception: " + cnfe);
            }
        %>

    </body>
</html>
