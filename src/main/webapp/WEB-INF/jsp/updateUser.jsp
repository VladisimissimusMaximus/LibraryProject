<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="errorCode" value="${requestScope.errorCode}"/>
<c:set var="emailValidation" value="${requestScope.emailValidation}"/>
<c:set var="nameValidation" value="${requestScope.nameValidation}"/>
<c:set var="name" value="${(not empty param.name) ? param.name : requestScope.name}"/>
<c:set var="email" value="${(not empty param.email) ? param.email : requestScope.email}"/>
<c:set var="role" value="${(not empty param.role) ? param.role : requestScope.role}"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container center_div">
    <div class="container text-center mt-5">
        <p class="h3 mb-5 font-weight-normal"><fmt:message key="user.edit" bundle="${lang}"/></p>
    </div>
    <div class="container login-form">
        <form class="container w-50" action="" method="POST">
            <c:choose>
                <c:when test="${not empty errorCode}">
                    <div class="alert alert-warning" role="alert">
                        <fmt:message key="${errorCode}" bundle="${lang}"/>
                    </div>
                </c:when>
            </c:choose>
            <label for="inputName" class="sr-only"><fmt:message key="user.name" bundle="${lang}"/></label>
            <input type="text" name="name" value="${name}" id="inputName"
                   class="form-control mb-1 ${(not empty nameValidation) ? 'is-invalid' : ''}"
                   placeholder="<fmt:message key="user.name" bundle="${lang}" />" required
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
            <label for="inputEmail" class="sr-only"><fmt:message key="user.email" bundle="${lang}"/></label>
            <input type="email" name="email" value="${email}" id="inputEmail"
                   class="form-control mb-1 ${(not empty emailValidation) ? 'is-invalid' : ''}"
                   placeholder="<fmt:message key="user.email" bundle="${lang}" />" required
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

            <c:choose>
                <c:when test="${ADMINISTRATOR == SESSION_ROLE}">
                    <select name="role" class="custom-select" required>

                        <option value="READER" ${"READER" == role ? "selected" : ""}><fmt:message
                                key="user.role.reader" bundle="${lang}"/></option>
                        <option value="ADMINISTRATOR" ${"ADMINISTRATOR" == role ? "selected" : ""}><fmt:message
                                key="user.role.administrator" bundle="${lang}"/></option>
                        <option value="LIBRARIAN" ${"LIBRARIAN" == role ? "selected" : ""}><fmt:message
                                key="user.role.librarian" bundle="${lang}"/></option>
                    </select>
                </c:when>
            </c:choose>

            <div class="container d-flex justify-content-between mt-1">
                <a class="btn btn-info" onclick="history.back()"><fmt:message key="app.back"
                                                                              bundle="${lang}"/></a>
                <button class="btn btn-primary" type="submit"><fmt:message key="app.button.update"
                                                                           bundle="${lang}"/></button>
            </div>

        </form>
    </div>
</div>


<jsp:include page="fragments/footer.jsp"/>
<body>

</body>
</html>
