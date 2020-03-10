<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register User</title>
</head>
<body>

<form action="registerUser" method="post">
    <pre>
        <h2>Zarejestruj się</h2>
    Email: <input type="email" name="email">
    Hasło: <input type="password" name="password">
    Potwierdź hasło: <input type="password" name="confirmedPassword">

    <input type="submit" value="zarejestruj"/>
        ${msg}
        </pre>
</form>

</body>
</html>