<%--
  Created by IntelliJ IDEA.
  User: tommy
  Date: 11/1/2020
  Time: 4:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Admin Home Page</title>
</head>
<body>
    <h2>Welcome to Admin Home Page</h2>

    <hr><br>

    <security:authentication property="principal.username"/> <br>
    <security:authentication property="principal.authorities"/>
    <hr>

    <br><br>
    <a href="${pageContext.request.contextPath}/">Return to Main Page</a>
    <hr>

    <form:form method="post" action="${pageContext.request.contextPath}/logout">
        <input type="submit" value="Log-out">
    </form:form>


</body>
</html>
