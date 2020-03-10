<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add new</title>
</head>
<body>

<form action="addNew" method="post">
    <pre>
        <h2>Podaj dane pracownika</h2>

    <input type="hidden" name="userId" value="${userDetails.userId}"/>
    <input type="hidden" name="active" value="true"/>
    Imię: <input type="text" name="name">
    Nazwisko: <input type="text" name="surname">
    Paszport: <input type="text" name="passport">
    Adres: <input type="text" name="adress">
    <input type="hidden" name="idi" value="${userDetails.userId}"/>


    <input type="submit" value="zapisz"/>

</form>
${msg}

<a href="showActive?id=${userDetails.userId}">Pokaż aktywnych pracowników</a>
</pre>
</body>
</html>