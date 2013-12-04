<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="createSuccess"/>

<%@ include file="../header.jsp" %>

<div id="successPanel">
    <p>
        Woohoo, User <span class="username">${user.userName}</span> has been created successfully!
    </p>
</div>

<%@ include file="../footer.jsp" %>