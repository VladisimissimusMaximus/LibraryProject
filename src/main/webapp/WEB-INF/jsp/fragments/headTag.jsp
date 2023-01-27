<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${ not empty param.language }">
        <c:set var="language" value="${param.language}" scope="request" />
    </c:when>
    <c:otherwise>
        <c:set var="language" value="${sessionScope.language}" scope="request" />
    </c:otherwise>
</c:choose>
<fmt:setLocale value = "${language}" scope="request"/>
<fmt:setBundle basename = "messages/resources" var = "lang" scope="request"/>
<c:set var="READER" value="READER" scope="request"/>
<c:set var="ADMINISTRATOR" value="ADMINISTRATOR" scope="request"/>
<c:set var="LIBRARIAN" value="LIBRARIAN" scope="request"/>
<c:set var="SESSION_ROLE" value="${sessionScope.userRole}" scope="session"/>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title><fmt:message key = "app.title" bundle = "${lang}" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>