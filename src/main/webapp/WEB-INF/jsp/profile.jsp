<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="pic" value="/resources/images/user-profile.png"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container center_div mt-5">

    <div class="d-flex" >
        <div class="d-inline-block mr-5">
            <img src="${pic}" alt="profile-picture"/>
        </div>
        <div class="d-inline-block">
            <div>
                <p class="h5">
                    ${sessionScope.userName}
                </p>
                <p>
                    <c:choose>
                        <c:when test="${SESSION_ROLE eq ADMINISTRATOR}">
                            <button class="btm-xsm-red" disabled>
                                <fmt:message key="user.role.administrator" bundle="${lang}"/>
                            </button>
                        </c:when>
                        <c:when test="${SESSION_ROLE eq LIBRARIAN}">
                            <button class="btm-xsm-green" disabled>
                                <fmt:message key="user.role.librarian" bundle="${lang}"/>
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button class="btm-xsm-blue" disabled>
                                <fmt:message key="user.role.reader" bundle="${lang}"/>
                            </button>
                        </c:otherwise>
                    </c:choose>
                </p>

            </div>
        </div>
        <div class="container pos-relative">
            <a class="btn btn-primary btn-sm right-top" href="profile/update"><fmt:message key="app.button.update"
                                                                              bundle="${lang}"/></a>
        </div>

    </div>


    <hr/>

    <div class="col-md-8">
        <div class="tab-content profile-tab">
            <div class="tab-pane fade show active" role="tabpanel" aria-labelledby="home-tab">
                <div class="row">
                    <div class="col-md-6">
                        <label><fmt:message key="user.id" bundle="${lang}"/></label>
                    </div>
                    <div class="col-md-6">
                        <p>${sessionScope.userId}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <label><fmt:message key="user.name" bundle="${lang}"/></label>
                    </div>
                    <div class="col-md-6">
                        <p>${sessionScope.userName}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <label><fmt:message key="user.email" bundle="${lang}"/></label>
                    </div>
                    <div class="col-md-6">
                        <p>${sessionScope.userEmail}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
