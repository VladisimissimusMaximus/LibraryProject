<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="errorCode" value="${requestScope.errorCode}"/>
<c:set var="nameValidation" value="${requestScope.nameValidation}"/>
<c:set var="authorValidation" value="${requestScope.authorValidation}"/>
<c:set var="publisherValidation" value="${requestScope.publisherValidation}"/>
<c:set var="publicationDateValidation" value="${requestScope.publicationDateValidation}"/>
<c:set var="countValidation" value="${requestScope.countValidation}"/>

<c:set var="name" value="${(not empty param.name) ? param.name : requestScope.name}"/>
<c:set var="author" value="${(not empty param.author) ? param.author : requestScope.author}"/>
<c:set var="publisher" value="${(not empty param.publisher) ? param.publisher : requestScope.publisher}"/>
<c:set var="publicationDate" value="${(not empty param.publicationDate) ? param.publicationDate : requestScope.publicationDate}"/>
<c:set var="count" value="${(not empty param.count) ? param.count : requestScope.count}"/>

<c:set var="action" value="${requestScope.action}" scope="request"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container center_div">
    <div class="container text-center mt-5">
        <p class="h3 mb-5 font-weight-normal"><fmt:message key="${action=='create' ? 'book.create' : 'book.edit'}" bundle="${lang}"/></p>
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
            <label for="inputName" class="sr-only"><fmt:message key="book.title" bundle="${lang}"/></label>
            <input type="text" name="name" value="${name}" id="inputName"
                   class="form-control mb-1 ${(not empty nameValidation) ? 'is-invalid' : ''}"
                   placeholder="<fmt:message key="book.title" bundle="${lang}" />" required
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
            <label for="inputAuthor" class="sr-only"><fmt:message key="user.email" bundle="${lang}"/></label>
            <input type="text" name="author" value="${author}" id="inputAuthor"
                   class="form-control mb-1 ${(not empty authorValidation) ? 'is-invalid' : ''}"
                   placeholder="<fmt:message key="book.author" bundle="${lang}" />" required
                   autofocus>
            <c:choose>
                <c:when test="${not empty authorValidation}">
                    <p class="ml-2 text-danger text-left">
                        <small>
                            <fmt:message key="${authorValidation}" bundle="${lang}"/>
                        </small>
                    </p>
                </c:when>
            </c:choose>

            <label for="inputPublisher" class="sr-only"><fmt:message key="book.publisher" bundle="${lang}"/></label>
            <input type="text" name="publisher" value="${publisher}" id="inputPublisher"
                   class="form-control mb-1 ${(not empty publisherValidation) ? 'is-invalid' : ''}"
                   placeholder="<fmt:message key="book.publisher" bundle="${lang}" />" required
                   autofocus>
            <c:choose>
                <c:when test="${not empty publisherValidation}">
                    <p class="ml-2 text-danger text-left">
                        <small>
                            <fmt:message key="${publisherValidation}" bundle="${lang}"/>
                        </small>
                    </p>
                </c:when>
            </c:choose>

            <label for="inputPublicationDate" class="sr-only"><fmt:message key="book.publicationDate" bundle="${lang}"/></label>
            <input type="date" name="publicationDate" value="${publicationDate}" id="inputPublicationDate"
                   class="form-control mb-1 ${(not empty publicationDateValidation) ? 'is-invalid' : ''}"
                   placeholder="<fmt:message key="book.publicationDate" bundle="${lang}" />" required
                   autofocus>
            <c:choose>
                <c:when test="${not empty publicationDateValidation}">
                    <p class="ml-2 text-danger text-left">
                        <small>
                            <fmt:message key="${publicationDateValidation}" bundle="${lang}"/>
                        </small>
                    </p>
                </c:when>
            </c:choose>

            <label for="inputCount" class="sr-only"><fmt:message key="book.count" bundle="${lang}"/></label>
            <input type="number" name="count" value="${count}" id="inputCount"
                   class="form-control mb-1 ${(not empty countValidation) ? 'is-invalid' : ''}"
                   placeholder="<fmt:message key="book.count" bundle="${lang}" />" required
                   autofocus>
            <c:choose>
                <c:when test="${not empty countValidation}">
                    <p class="ml-2 text-danger text-left">
                        <small>
                            <fmt:message key="${countValidation}" bundle="${lang}"/>
                        </small>
                    </p>
                </c:when>
            </c:choose>

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
