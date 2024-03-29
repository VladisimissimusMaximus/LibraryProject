<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://company.com/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container mt-3 ml-3">
    <p class="h3 mb-4 text-center"><fmt:message key="operation.title" bundle="${lang}"/></p>
    <form class="d-flex justify-content-between">
        <div class="container nowrap">
            <div class="container text-center">
                <p class="h6 mb-4"><fmt:message key="selection.form.filter.title" bundle="${lang}"/></p>
            </div>
            <div class="container">
                <label for="filterByStatus"><fmt:message key="operation.status"
                                                        bundle="${lang}"/>:</label>
                <select id="filterByStatus" name="filterByStatus">
                    <option ${param.filterByStatus == '' ? 'selected' : ''} value>-</option>
                    <option value="ORDER" ${param.filterByStatus == 'ORDER' ? 'selected' : ''}>
                        ORDER
                    </option>
                    <option value="SUBSCRIPTION" ${param.filterByStatus == 'SUBSCRIPTION' ? 'selected' : ''}>
                        SUBSCRIPTION
                    </option>
                    <option value="READING_ROOM" ${param.filterByStatus == 'READING_ROOM' ? 'selected' : ''}>
                        READING_ROOM
                    </option>
                </select>
            </div>
        </div>
        <div class="container nowrap">
            <div class="container text-center">
                <p class="h6 mb-4"><fmt:message key="selection.form.sorting.title" bundle="${lang}"/></p>
            </div>
            <div class="container">
                <label for="selectSorting"><fmt:message key="selection.form.sorting.byColumn" bundle="${lang}"/>:</label>
                <select id="selectSorting" name="order">
                    <option ${order == '' ? 'selected' : ''} value>-</option>
                </select>
            </div>
        </div>

        <div class="container nowrap">
            <div class="container text-center">
                <p class="h6 mb-4"><fmt:message key="selection.form.paging.title" bundle="${lang}"/></p>
            </div>
            <div class="container">
                <label for="selectPaging"><fmt:message key="selection.form.paging.recordsPerPage"
                                                       bundle="${lang}"/>:</label>
                <select id="selectPaging" name="recordsPerPage">
                    <option value="5" ${recordsPerPage == '5' ? 'selected' : ''}>
                        5
                    </option>
                    <option value="10" ${recordsPerPage == '10' ? 'selected' : ''}>
                        10
                    </option>
                    <option value="20" ${recordsPerPage == '20' ? 'selected' : ''}>
                        20
                    </option>
                </select>
            </div>
        </div>
        <div class="container pos-relative">
            <input class="btn-info left-center" type="submit"
                   value="<fmt:message key="app.button.submit" bundle="${lang}"/>"/>
        </div>
    </form>
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
                    <th scope="col"><fmt:message key="operation.reader" bundle="${lang}"/></th>
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
                        <td><c:out value="${operation.user.email}"/></td>
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
                                <c:when test="${(ADMINISTRATOR == SESSION_ROLE || LIBRARIAN == SESSION_ROLE) && operation.status.name() == ORDER}">
                                    <a class="btn btn-success btn-sm"
                                       href="operations/approve?userId=${operation.user.id}&bookId=${operation.book.id}">
                                        <fmt:message key="app.button.approve" bundle="${lang}"/>
                                    </a>
                                </c:when>
                                <c:when test="${READER == SESSION_ROLE && operation.status.name() == SUBSCRIPTION}">
                                    <a class="btn btn-success btn-sm"
                                       href="operations/unsubscribe/${book.id}">
                                        <fmt:message key="app.button.payAndUnsubscribe" bundle="${lang}"/>
                                    </a>
                                </c:when>
                                <c:when test="${READER == SESSION_ROLE && operation.status.name() == READING_ROOM}">
                                    <a class="btn btn-success btn-sm"
                                       href="operations/return/${book.id}">
                                        <fmt:message key="app.button.return" bundle="${lang}"/>
                                    </a>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>

            </table>

            <div class="container d-flex justify-content-between">
                <div class="container text-center">
                    <c:if test="${currentPage > 1}">
                        <a href="operations?pageNumber=${currentPage - 1}&recordsPerPage=${param.recordsPerPage}&order=${order}&filterByStatus=${param.filterByStatus}"><--</a>
                    </c:if>
                    <c:forEach begin="1" end="${pagesTotal}" varStatus="pages">
                        <a class="mr-1 ${currentPage == pages.index ? 'btm-xsm-red' : ''}"
                           href="operations?pageNumber=${pages.index}&recordsPerPage=${param.recordsPerPage}&order=${order}&filterByStatus=${param.filterByStatus}">${pages.index}</a>
                    </c:forEach>
                    <c:if test="${currentPage < pagesTotal}">
                        <a href="operations?pageNumber=${currentPage + 1}&recordsPerPage=${param.recordsPerPage}&order=${order}&filterByStatus=${param.filterByStatus}">--></a>
                    </c:if>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <fmt:message key="app.noRecords" bundle="${lang}"/>
        </c:otherwise>
    </c:choose>

</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
