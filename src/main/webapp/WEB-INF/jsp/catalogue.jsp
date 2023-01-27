<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://company.com/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="orderColumn" value="${sessionScope.order}"/>
<c:set var="recordsPerPage" value="${sessionScope.paging.recordsPerPage}"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container mt-3 ml-3">
    <p class="h3 mb-4 text-center"><fmt:message key="book.catalogue" bundle="${lang}"/></p>
    <form class="d-flex justify-content-between">

        <div class="container nowrap">
            <div class="container text-center">
                <p class="h6 mb-4"><fmt:message key="selection.form.sorting.title" bundle="${lang}"/></p>
            </div>
            <div class="container">
                <label for="selectSorting"><fmt:message key="selection.form.sorting.byColumn"
                                                        bundle="${lang}"/>:</label>
                <select id="selectSorting" name="order">
                    <option ${orderColumn == '' ? 'selected' : ''} value>-</option>
                    <option value="title" ${orderColumn == 'BY_BOOK_TITLE' ? 'selected' : ''}>
                        <fmt:message key="book.title" bundle="${lang}"/>
                    </option>
                    <option value="userCount" ${orderColumn == 'BY_BOOK_AUTHOR' ? 'selected' : ''}>
                        <fmt:message key="book.author" bundle="${lang}"/>
                    </option>
                    <option value="publisher" ${orderColumn == 'BY_BOOK_PUBLISHER' ? 'selected' : ''}>
                        <fmt:message key="book.publisher" bundle="${lang}"/>
                    </option>
                    <option value="userCount" ${orderColumn == 'BY_BOOK_PUBLICATION_DATE' ? 'selected' : ''}>
                        <fmt:message key="book.publicationDate" bundle="${lang}"/>
                    </option>

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
        <c:when test="${not empty books}">
            <table class="table" aria-describedby="activities-table">
                <thead class="thead-light">
                <tr>
                    <th scope="col"><fmt:message key="book.title" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="book.author" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="book.publisher" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="book.publicationDate" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="book.count" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="app.actions" bundle="${lang}"/></th>
                </tr>
                </thead>
                <c:forEach items="${books}" var="book">
                    <jsp:useBean id="book" type="com.company.model.Book"/>
                    <tr>
                        <td><c:out value="${book.name}"/></td>
                        <td><c:out value="${book.author}"/></td>
                        <td><c:out value="${book.publisher}"/></td>
                        <td>${fn:formatDate(book.publicationDate)}</td>
                        <td>${book.count}</td>
                        <td>
                            <c:choose>
                                <c:when test="${ADMINISTRATOR == SESSION_ROLE}">
                                    <a class="btn btn-info btn-sm"
                                       href="catalogue/update/${book.id}">
                                        <fmt:message key="app.button.update" bundle="${lang}"/>
                                    </a>
                                    <a class="btn btn-danger btn-sm"
                                       href="catalogue/delete/${book.id}">
                                        <fmt:message key="app.button.delete" bundle="${lang}"/>
                                    </a>
                                </c:when>
                                <c:when test="${READER == SESSION_ROLE}">
                                    <a class="btn btn-success btn-sm"
                                       href="catalogue/subscribe/${book.id}">
                                        <fmt:message key="app.button.subscribe" bundle="${lang}"/>
                                    </a>
                                    <a class="btn btn-info btn-sm"
                                       href="catalogue/read/${book.id}">
                                        <fmt:message key="app.button.read" bundle="${lang}"/>
                                    </a>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>

            </table>

            <div class="container d-flex justify-content-between">
                <c:choose>
                    <c:when test="${ADMINISTRATOR == SESSION_ROLE}">
                        <form action="catalogue" method="POST" class="d-inline">
                            <input class="btn btn-success btn-sm" type="submit"
                                   value="<fmt:message key="app.button.create" bundle="${lang}"/>"/>
                        </form>
                    </c:when>
                </c:choose>

                <div class="container text-center">
                    <c:if test="${currentPage > 1}">
                        <a href="catalogue?pageNumber=${currentPage - 1}&recordsPerPage=${param.recordsPerPage}"><--</a>
                    </c:if>
                    <c:forEach begin="1" end="${pagesTotal}" varStatus="pages">
                        <a class="mr-1 ${currentPage == pages.index ? 'btm-xsm-red' : ''}"
                           href="catalogue?pageNumber=${pages.index}&recordsPerPage=${param.recordsPerPage}">${pages.index}</a>
                    </c:forEach>
                    <c:if test="${currentPage < pagesTotal}">
                        <a href="catalogue?pageNumber=${currentPage + 1}&recordsPerPage=${param.recordsPerPage}">--></a>
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
