<%@ page contentType="text/html"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${language}"/>
<fmt:setBundle basename = "messages/resources" var = "lang"/>

<footer class="footer fixed-bottom">
    <div class="container">
        <span class="text-muted">
            <fmt:message key="app.footer" bundle="${lang}"/>
        </span>
    </div>
</footer>