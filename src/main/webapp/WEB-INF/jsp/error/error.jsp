<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="icon" value="/resources/images/sad-face.png"/>
<html>
<jsp:include page="../fragments/headTag.jsp"/>
<body>
<div class="mt-4 d-flex justify-content-center">
    <div>
        <img src="${icon}" alt="logo" class="mb-3 rounded mx-auto d-block"/>
        <p class="h4 text-center"><fmt:message key = "${param.errorCode}.title" bundle = "${lang}" /></p>
        <p class="text-center mt-4"><fmt:message key = "${param.errorCode}.message" bundle = "${lang}" /></p>
    </div>
</div>
</body>
</html>
