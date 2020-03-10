
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Deactivate</title>
</head>
<body>
<pre>
<form action="deactivate" method="post">
Czy dani pracownicy będą jeszcze pracować?

Tak <input type="radio" name="type" value="tak"/>
Nie <input type="radio" name="type" value="nie"/>

<input type="hidden" name="userId" value="${userId}"/>
<input type="hidden" name="list" value="${list}"/>


<input type="submit" value="potwierdź"/>

    </pre>
</form>

</body>
</html>
