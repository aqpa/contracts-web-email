<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit user</title>
</head>

<body>


<form action="editUserDetails" method="post">
    <pre>
    <input type="hidden" name="id" value="${userDetails.id}"/>
    Imię:  <input type="text" name="name" value="${userDetails.name}">
    Nazwisko:  <input type="text" name="surname" value="${userDetails.surname}">
    Pełna nazwa gospodarstwa:  <input type="text" name="fullName" value="${userDetails.fullName}">
    NIP:  <input type="text" name="nip" value="${userDetails.nip}">
    Stawka godzinowa: <input type="text" name="wage" value="${userDetails.wage}">
    <input type="hidden" name="userId" value="${userDetails.userId}"/>
    <a href="showPassword?id=${userDetails.userId}">Nowe hasło</a>


    <input type="Submit" value="zapisz"/>
        </pre>

</form>

</body>
</html>