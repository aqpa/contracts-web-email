<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> User Login </title>
</head>

<body>
<h2>Login</h2>
<form action="login" method="post">
<pre>
    Email: <input type="text" name="email">
    Hasło: <input type="text" name="password">

    <input type="submit" value="login"/>

    <a href="whatPassword">Zapomniałem hasła</a>

    ${msg}
    </pre>

</form>


</body>
</html>