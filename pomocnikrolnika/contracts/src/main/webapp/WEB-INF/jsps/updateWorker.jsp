<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update worker</title>
</head>
<body>

<form action="updateWorker" method="post">
    <pre>
        <h2>Zmień dane pracownika</h2>


    <input type="hidden" name="id" value="${worker.id}"/>
    <input type="hidden" name="active" value="true"/>
    Imię: <input type="text" name="name" value="${worker.name}">
    Nazwisko: <input type="text" name="surname" value="${worker.surname}">
    Paszport: <input type="text" name="passport" value="${worker.passport}">
    Adres: <input type="text" name="adress" value="${worker.adress}">
    <input type="hidden" name="userId" value="${worker.userId}"/>
    <input type="hidden" name="idi" value="${worker.userId}"/>

    <input type="submit" value="zapisz"/>

</form>
${msg}

<a href="showActive?id=${worker.userId}">Pokaż aktywnych pracowników</a>
</pre>
</body>
</html>