<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <head>
        <title>Active Workers</title>
    </head>
<body>

${msg}

    <input type="hidden" name="userId" value="${userId}"/>

  <h2> Wybierz pracownika albo szukaj ponownie: </h2>

<form action="searchWorker" method="post">Podaj całe albo część nazwiska pracownika: <input type="text" name="search"> <input type="submit" value="szukaj"/>
    <input type="hidden" name="userId" value="${userId}"/></form>

<a href="showActive?id=${userId}">Pokaż aktywnych pracowników</a>
<pre>
<table>
<tr>
<th> imię </th>
<th> nazwisko </th>

</tr>
    <c:forEach items="${workersSearch}" var="sWorker">
<tr>
    <td>${sWorker.name}</td>
    <td>${sWorker.surname}</td>
    <td><a href="addSearchedWorker?id=${sWorker.id}">dodaj</a></td>
    </tr>
    </c:forEach>
    </table>

</pre>

<a href="showActive?id=${userId}">Pokaż aktywnych pracowników</a>

</body>
</html>