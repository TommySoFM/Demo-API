<%--
  Created by IntelliJ IDEA.
  User: tommy
  Date: 11/1/2020
  Time: 4:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <h2> Welcome to Home Page!</h2>
    <hr><br>

    User: <security:authentication property="principal.username"/>
    <br>
    Authority: <security:authentication property="principal.authorities"/>

    <br><br>

    <security:authorize access="hasRole('ADMIN')">
        <a href="${pageContext.request.contextPath}/admin">Admin Page</a>
    </security:authorize>

    <hr>

    <form:form method="post" action="${pageContext.request.contextPath}/logout">
        <input type="submit" value="Log-out">
    </form:form>
</body>
</html>
