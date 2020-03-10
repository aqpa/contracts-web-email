<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Details</title>
</head>
<body>

<form action="userDetails" method="post">
    <pre>
        <h2>Podaj swoje dane</h2>
    Imię: <input type="text" name="name">
    Nazwisko: <input type="text" name="surname">
    Pełna nazwa gospodarstwa (np. Gospodarstwo sadowniczo - ogrodnicze Jan Kowalski, Zawady 1, 05-650 Chynów): <input type="text" name="fullName">
    NIP: <input type="text" name="nip">
    Stawka godzinowa: <input type="text" name="wage" value="17">
    <input type="hidden" name="userId" value="${user.id}"/>





    <input type="submit" value="zapisz"/>
        ${msg}
        </pre>
</form>

</body>
</html>