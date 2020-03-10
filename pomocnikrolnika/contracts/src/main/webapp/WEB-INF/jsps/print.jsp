<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <head>
    <title>Dates</title>
</head>
<body>

<form action= ${method} method="post">
    <pre>
        <h2>Podaj zakres dat</h2>

    <input type="hidden" name="id" value="${id}"/>
    Data rozpoczęcia pracy <input type="text" name="startDate" value="${thisDay}">

    Data zakończenia pracy: <input type="text" name="endDate" value="${lastDay}">

        <input type="hidden" name="check" value="${check}"/>

    <input type="submit" value="drukuj"/>

</form>

</pre>
</body>
</html>