<%-- 
    Document   : Reply
    Created on : Jan 14, 2021, 9:48:08 PM
    Author     : Maria
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="mycss.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String toUser = request.getParameter("myradio");
        %>
        <form action="SendReply">

            <label  > To: </label>
            <input type="hidden" value="<%= toUser%>"  
                   id="ToEmail" name="ToEmail" style="border: none; background: none;"/> 
            <label > <%= toUser%> </label>
            <br>
            <textarea id="message" name="message" 
                      placeholder="write you message " 
                      rows="10" cols="30"></textarea>
            <br>
            <input type="submit" formaction="SendReply" value="Reply"/> 


        </form>
    </body>
</html>
