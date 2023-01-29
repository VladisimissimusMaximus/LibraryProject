<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="errorCode" value="${requestScope.errorCode}"/>
<c:set var="durationValidation" value="${requestScope.durationValidation}"/>
<c:set var="duration" value="${(not empty param.duration) ? param.duration : requestScope.duration}"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container center_div">
    <div class="container text-center mt-5">
        <p class="h3 mb-5 font-weight-normal"><fmt:message key="operation.subscribe" bundle="${lang}"/></p>
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
            <div class="form-group">
                <label for="title"><fmt:message key="book.title" bundle="${lang}"/></label>
                <input type="text" name="title" value="${title}" id="title"
                       class="form-control}"
                       placeholder="<fmt:message key="book.title" bundle="${lang}" />" disabled>

            </div>

            <div class="form-group">
                <label for="cost"><fmt:message key="operation.subscription.cost" bundle="${lang}"/></label>
                <input type="text" name="cost" value="${cost}" id="cost"
                       class="form-control" disabled>

            </div>

            <div class="form-group">
                <label for="duration"><fmt:message key="operation.duration" bundle="${lang}"/></label>
                <input type="number" name="duration" value="${duration}" id="duration"
                       class="form-control" autofocus required>
                <c:choose>
                    <c:when test="${not empty durationValidation}">
                        <p class="ml-2 text-danger text-left">
                            <small>
                                <fmt:message key="${durationValidation}" bundle="${lang}"/>
                            </small>
                        </p>
                    </c:when>
                </c:choose>
            </div>

            <div class="container d-flex justify-content-between mt-1">
                <a class="btn btn-info" onclick="history.back()"><fmt:message key="app.back"
                                                                              bundle="${lang}"/></a>
                <button class="btn btn-primary" type="submit"><fmt:message key="app.button.submit"
                                                                           bundle="${lang}"/></button>
            </div>

        </form>
    </div>
</div>


<jsp:include page="fragments/footer.jsp"/>
<body>

</body>
</html>
