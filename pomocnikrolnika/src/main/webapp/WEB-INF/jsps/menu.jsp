<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
        <!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Menu</title>
</head>
<body>
<pre>
    ${userDetails.fullName}

    <a href="index.html">Wróć do strony startowej</a>

    <a href="showUpdateUserDetails?id=${userDetails.userId}">Zmień swoje dane</a>

    <a href="showActive?id=${userDetails.userId}">Pokaż aktywnych pracowników</a>






</pre>

</body>
</html>