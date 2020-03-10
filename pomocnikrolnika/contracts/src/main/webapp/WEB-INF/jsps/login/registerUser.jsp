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
    Email: <input type="text" name="email">
    Hasło: <input type="text" name="password">
    Potwierdź hasło: <input type="text" name="confirmedPassword">

    <input type="submit" value="zarejestruj"/>
        ${msg}
        </pre>
</form>

</body>
</html>