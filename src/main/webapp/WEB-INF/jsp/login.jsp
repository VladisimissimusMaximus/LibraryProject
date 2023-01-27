<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="icon" value="/resources/images/librarylil.png"/>
<c:set var="errorCode" value="${requestScope.errorCode}"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container center_div">
    <div class="container text-center login-box pt-3">
        <form class="container form-signin w-50 mt-5" action="login" method="POST" novalidate>
            <img src="${icon}" alt="logo" class="mb-3"/>
            <p class="h3 mb-3 font-weight-normal"><fmt:message key="app.login" bundle="${lang}"/></p>
            <c:choose>
                <c:when test="${not empty errorCode}">
                    <div class="alert alert-warning" role="alert">
                        <fmt:message key="${errorCode}" bundle="${lang}"/>
                    </div>
                </c:when>
                <c:when test="${not empty param.registered}">
                    <div class="alert alert-success" role="alert">
                        <fmt:message key="app.registered" bundle="${lang}"/>
                    </div>
                </c:when>
            </c:choose>
            <label for="inputEmail" class="sr-only"><fmt:message key="user.email" bundle="${lang}"/></label>
            <input type="email" name="email" value="<c:out value="${param.email}"/>" id="inputEmail"
                   class="form-control mb-1" placeholder="<fmt:message key = "user.email" bundle = "${lang}" />"
                   required autofocus>
            <label for="inputPassword" class="sr-only"><fmt:message key="user.password" bundle="${lang}"/></label>
            <input type="password" name="password" id="inputPassword" class="form-control"
                   placeholder="<fmt:message key = "user.password" bundle = "${lang}" />" required>
            <button class="btn btn-lg btn-primary btn-block mt-3" type="submit"><fmt:message key="app.login"
                                                                                             bundle="${lang}"/></button>
            <p class="mt-1 text-sm-right"><a href="<c:url value="register"/>"><fmt:message key="app.login.createAccount"
                                                                                           bundle="${lang}"/></a></p>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
