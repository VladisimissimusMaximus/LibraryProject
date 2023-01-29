<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages/resources" var="lang"/>
<c:set var="currentUrl" value="${pageContext.request.request.getAttribute('javax.servlet.forward.request_uri')}"/>

<nav class="navbar navbar-expand-md navbar-light bg-light py-0">
    <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
        <a class="navbar-brand text-black"><fmt:message key="app.title" bundle="${lang}"/></a>
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/home"><fmt:message key="app.home" bundle="${lang}"/></a>
            </li>
            <c:choose>
                <c:when test="${READER == SESSION_ROLE}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/catalogue"><fmt:message key="book.catalogue" bundle="${lang}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/profile/operation"><fmt:message key="myoperations" bundle="${lang}"/></a>
                    </li>
                </c:when>
                <c:when test="${ADMINISTRATOR == SESSION_ROLE || LIBRARIAN == SESSION_ROLE}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/users"><fmt:message key="user.title" bundle="${lang}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/catalogue"><fmt:message key="book.catalogue" bundle="${lang}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/catalogue/operation"><fmt:message key="book.operation.title" bundle="${lang}"/></a>
                    </li>
                </c:when>
            </c:choose>
        </ul>
        <c:choose>
            <c:when test="${not empty SESSION_ROLE}">
                <a class="nav-link btn" href="${pageContext.request.contextPath}/profile"><fmt:message key="app.profile" bundle="${lang}"/></a>
                <form method="POST" action="${pageContext.request.contextPath}/logout" class="form-inline my-2 my-sm-0 mr-2">
                    <input type="submit" class="btn btn-outline-success btn-sm"
                           value="<fmt:message key = "app.logout" bundle = "${lang}" />"/>
                </form>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-outline-success my-2 my-sm-0"><fmt:message key="app.register" bundle="${lang}"/></a>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-link my-2  my-sm-0 mr-3"><fmt:message key="app.login" bundle="${lang}"/></a>
            </c:otherwise>
        </c:choose>
        <form class="form-inline my-2 my-sm-0 " action="${currentUrl}">
            <select name="language" class="custom-select custom-select-sm" onchange="this.form.submit()">
                <option value="en" ${"en" == language ? "selected" : ""}>English</option>
                <option value="ua" ${"ua" == language ? "selected" : ""}>Українська</option>
            </select>
        </form>
    </div>
</nav>
