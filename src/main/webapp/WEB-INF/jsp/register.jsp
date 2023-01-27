<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="icon" value="/resources/images/librarylil.png"/>
<c:set var="emailValidation" value="${requestScope.emailValidation}"/>
<c:set var="nameValidation" value="${requestScope.nameValidation}"/>
<c:set var="passwordValidation" value="${requestScope.passwordValidation}"/>
<c:set var="errorCode" value="${requestScope.errorCode}"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container center_div">
    <div class="container text-center login-box pt-3">
        <form class="container form-signin w-50 mt-5" action="register" method="POST" novalidate>
            <img src="${icon}" alt="library-logo" class="mb-3"/>
            <p class="h3 mb-3 font-weight-normal"><fmt:message key="app.register" bundle="${lang}"/></p>
            <c:choose>
                <c:when test="${not empty errorCode}">
                    <div class="alert alert-warning" role="alert">
                        <fmt:message key="${errorCode}" bundle="${lang}"/>
                    </div>
                </c:when>
            </c:choose>

            <label for="inputEmail" class="sr-only"><fmt:message key="user.email" bundle="${lang}"/></label>
            <input type="email" name="email" value="<c:out value="${param.email}"/>" id="inputEmail"
                   class="form-control mb-1 ${(not empty emailValidation) ? 'is-invalid' : ''}" placeholder="<fmt:message key="user.email" bundle="${lang}" />" required
                   autofocus>
            <c:choose>
                <c:when test="${not empty emailValidation}">
                    <p class="ml-2 text-danger text-left">
                        <small>
                            <fmt:message key="${emailValidation}" bundle="${lang}"/>
                        </small>
                    </p>
                </c:when>
            </c:choose>

            <label for="inputName" class="sr-only"><fmt:message key="user.name" bundle="${lang}"/></label>
            <input type="text" name="name" value="<c:out value="${param.name}"/>" id="inputName"
                   class="form-control mb-1 ${(not empty nameValidation) ? 'is-invalid' : ''}" placeholder="<fmt:message key="user.name" bundle="${lang}" />" required
                   autofocus>
            <c:choose>
                <c:when test="${not empty nameValidation}">
                    <p class="ml-2 text-danger text-left">
                        <small>
                            <fmt:message key="${nameValidation}" bundle="${lang}"/>
                        </small>
                    </p>
                </c:when>
            </c:choose>

            <label for="inputPassword" class="sr-only"><fmt:message key="user.password" bundle="${lang}"/></label>
            <input type="password" name="password" id="inputPassword" class="form-control mb-1 ${(not empty passwordValidation) ? 'is-invalid' : ''}"
                   placeholder="<fmt:message key="user.password" bundle="${lang}" />" required>
            <c:choose>
                <c:when test="${not empty passwordValidation}">
                    <p class="ml-2 text-danger text-left">
                        <small>
                            <fmt:message key="${passwordValidation}" bundle="${lang}"/>
                        </small>
                    </p>
                </c:when>
            </c:choose>

            <button class="btn btn-lg btn-primary btn-block mt-3" type="submit"><fmt:message key="app.register"
                                                                                             bundle="${lang}"/></button>
            <p class="mt-1 text-sm-right"><a href="<c:url value="login"/>"><fmt:message key="app.login"
                                                                                        bundle="${lang}"/></a></p>
        </form>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
