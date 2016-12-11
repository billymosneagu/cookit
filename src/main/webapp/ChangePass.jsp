<%-- 
    Document   : ChangePass
    Created on : 08-dic-2016, 22:20:18
    Author     : billy
--%>

<%@page import="control.PasswordHash"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="MyStyle.css" rel="stylesheet" type="text/css"/>
    </head>
    <script>
        function valida() {
            var pass1 = document.getElementById("pass1").value;
            var pass2 = document.getElementById("pass2").value;
            if (pass1 === pass2) {
                return true;
            } else {
                var color = $("#same").css("opacity", "1");
                return false;

            }
        }
    </script>
    <body>
        <div class="row">
            <img class="imagenCabecera" src="http://udisglutenfree.com/wp-content/themes/udis-new/static/udis/images/header-cookies-brownies.jpg" />
        </div>
        <% PasswordHash cifrado = new PasswordHash();
            String emailPHPString = request.getParameter("emailPHP");
            if (emailPHPString != null) {
                String replaces = emailPHPString.replaceAll(" ", "+");
                String email = cifrado.decrypt(replaces, "qwertyuiopasdfgh", "qwertyuiopasdfgh");
                String comillas=email.replaceAll("\"", "");
                request.setAttribute("emailPHP", comillas);
            }
        %>
    <center>
        <form action="${pageContext.request.contextPath}/ServletUsuario?op=cambiarContrasenna" method="post" onsubmit="return valida()">
            <input type="hidden" name="email" value="<%= request.getAttribute("emailPHP")%>">
            <div class="row"><div class="col-md-5"></div><div class="col-md-2">Contraseña</div> <div class="col-md-2" id="same">Las contraseñas no coinciden</div><div class="col-md-3"></div></div>
            <div class="row"><input id="pass1" required="true" type="password" name="pass"></div>
            <div class="row">Repetir contraseña</div>
            <div class="row"><input id="pass2" required="true" type="password"></div>
            <div class="row"><input type="submit" value="Enviar"></div>
        </form>
    </center>
</body>
</html>
