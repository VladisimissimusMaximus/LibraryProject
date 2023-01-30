<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://company.com/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container mt-3 ml-3">
    <p class="h3 mb-4 text-center"><fmt:message key="myoperations" bundle="${lang}"/></p>
    <c:choose>
        <c:when test="${not empty errorCode}">
            <div class="alert alert-warning" role="alert">
                <fmt:message key="${errorCode}" bundle="${lang}"/>
            </div>
        </c:when>
    </c:choose>

    <c:choose>
        <c:when test="${not empty operations}">
            <table class="table" aria-describedby="activities-table">
                <thead class="thead-light">
                <tr>
                    <th scope="col"><fmt:message key="book.title" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="book.author" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="operation.status" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="operation.startDate" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="operation.endDate" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="operation.debt" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="app.actions" bundle="${lang}"/></th>
                </tr>
                </thead>
                <c:forEach items="${operations}" var="operation">
                    <jsp:useBean id="operation" type="com.company.model.Operation"/>
                    <tr>
                        <td><c:out value="${operation.book.name}"/></td>
                        <td><c:out value="${operation.book.author}"/></td>
                        <td><c:out value="${operation.status}"/></td>
                        <td>${fn:formatDateTime(operation.startDate)}</td>
                        <td>
                            <c:choose>
                                <c:when test="${operation.status.name() == SUBSCRIPTION}">
                                    ${fn:appendDays(operation.startDate, operation.duration)}
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                                <c:choose>
                                    <c:when test="${operation.status.name() == SUBSCRIPTION}">
                                        ${fn:calculateDebt(operation.startDate, operation.duration)}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise>
                                </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${operation.status.name() == ORDER}">
                                    <a class="btn btn-danger btn-sm"
                                       href="operation/cancel/${operation.book.id}">
                                        <fmt:message key="app.button.cancel" bundle="${lang}"/>
                                    </a>
                                </c:when>
                                <c:when test="${READER == SESSION_ROLE && operation.status.name() == SUBSCRIPTION}">
                                    <a class="btn btn-success btn-sm"
                                       href="operation/unsub/${operation.book.id}">
                                        <fmt:message key="app.button.payAndUnsubscribe" bundle="${lang}"/>
                                    </a>
                                </c:when>
                                <c:when test="${READER == SESSION_ROLE && operation.status.name() == READING_ROOM}">
                                    <a class="btn btn-info btn-sm"
                                       href="operation/return/${operation.book.id}">
                                        <fmt:message key="app.button.return" bundle="${lang}"/>
                                    </a>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </c:when>

        <c:otherwise>
            <fmt:message key="app.noRecords" bundle="${lang}"/>
        </c:otherwise>
    </c:choose>

</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
