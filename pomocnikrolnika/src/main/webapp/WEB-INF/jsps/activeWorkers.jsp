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
<pre>
    <a href="showMenu?id=${userDetails.userId}">Wróć do menu</a>

    <a href="deactivateAll?id=${userDetails.userId}">Deaktywuj wszystkich</a>

    <a href="showAddNew?id=${userDetails.userId}">Dodaj pracownika</a>

    Wyszukaj pracownika z nieaktywnych
    <form action="searchWorker" method="post">Podaj całe albo część nazwiska pracownika: <input type="text" name="search"> <input type="submit" value="szukaj"/>
    <input type="hidden" name="userId" value="${userDetails.userId}"/></form>
    <a href="dates?id=${userDetails.userId}&method=printAllBeginning">Umowy dla wszystkich - początek pracy</a>                  <a href="datesTwo?id=${userDetails.userId}&method=printAllEnd">Rozliczenie dla wszystkich</a>
<form action="checked" method="post">
<input type="submit" name="beginning" value="Umowy dla zaznaczonych"/>                                     <input type="submit" name="end" value="Rozliczenie dla zaznaczonych"/>

<h2> Pracownicy: </h2>
<table>
<tr>
<th> zaznacz </th>
<th> liczba </th>
<th> nazwisko </th>
<th> imię </th>
<th> paszport </th>
</pre>

</tr>


<c:forEach items="${workers}" var="worker">
<tr>
    <td><input type="checkbox" name="check"value = "${i}"></td>
    <td>${i=i+1}</td>
    <td>${worker.surname}</td>
    <td>${worker.name}</td>
    <td>${worker.passport}</td>
      <td>  <a href="deactivateWorker?id=${worker.id}">dezaktywuj</a></td>
      <td>  <a href="showUpdateWorker?id=${worker.id}">edytuj</a></td>
      <td>  <a href="dates?id=${worker.id}&method=printBeginning">umowy</a></td>
      <td>  <a href="datesTwo?id=${worker.id}&method=printEnd">rozliczenie</a></td>
</tr>
    </pre>

</c:forEach>

</table>

<input type="hidden" name="id" value="${userDetails.userId}"/>
</form>




</body>
</html>