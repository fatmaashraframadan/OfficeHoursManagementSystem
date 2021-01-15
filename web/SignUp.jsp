<%-- 
    Document   : SignUp
    Created on : Dec 31, 2020, 10:38:08 PM
    Author     : Nardin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link type="text/css" rel="stylesheet" href="mycss.css">

    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <script>
        function submitUserForm() {
            var response = grecaptcha.getResponse();
            console.log(response.length);
            if (response.length == 0) {
                document.getElementById('g-recaptcha-error').innerHTML = '<span style="color:red;">This field is required.</span>';
                return false;
            }
            return true;
        }

        function verifyCaptcha() {

            document.getElementById('g-recaptcha-error').innerHTML = '';

        }

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

            var response = grecaptcha.getResponse();
            // console.log(response.length);

            if (!grecaptcha.getResponse()) {
                alert("You need to prove that you're not a robot");
            } else {
                // document.getElementById('signupform').submit();
//alert("NOW here:");
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function ()
                {
                    if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
                    {
//alert("test here:");
                        document.getElementById("show_response").innerHTML = xmlhttp.responseText;
                    }
                }

//alert("test her222222222222e:");
                xmlhttp.open("GET", "SignUpValidation?Email=" + email + "&Name=" + name + "&userType=" + radio, true);
                xmlhttp.send();
            }
        }
    </script> 

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SignUp - Office Hours Management</title>

    </head>
    <body>
        <h1 class = "signh">SignUp</h1>

        <div class="signup">
            <form method = "post" id="signupform" onsubmit="return submitUserForm()" >

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
                <div class="g-recaptcha" data-sitekey="6LeE1CwaAAAAAMF01moBBUObnGqXO_OXywUstO1T"
                     data-callback="verifyCaptcha">
                </div>

                <div id="g-recaptcha-error">
                </div>

            </form>

            <form>
                <br>
                <input type="button" value="Create Account" onclick="sendajax()" class="register-button">
                <div id="show_response">  </div>
                <br>
            </form> 
            <br>
            <form>
                <button class = "loginbtnsignup" type="submit" formaction="Login.jsp">Login</button>
            </form>
        </div>

    </body>
</html>
