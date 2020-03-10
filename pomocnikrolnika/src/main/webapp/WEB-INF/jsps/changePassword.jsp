<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Change password</title>
</head>

<body>


<form action="changePassword" method="post">
    <pre>

    <input type="hidden" name="userId" value="${userDetails.userId}"/>
    Hasło:  <input type="password" name="password">
    <input type="Submit" value="zapisz"/>
        </pre>

</form>

</body>
</html>