<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Profile"/>

<%@ include file="../header.jsp" %>

<c:choose>
    <c:when test="${not empty failed}">
        <div id="changePasswordFailed" class="page-action changePassword-failed">
            <i class="messageError">Change Password Failed!</i>
        </div>
    </c:when>
    <c:when test="${not empty success}">
        <div id="changePasswordSuccess" class="page-action changePassword-success">
            <i class="messageInfo">Change Password Successful!</i>
        </div>
    </c:when>
</c:choose>

<div id="userInformation">
    <table class="table">
        <tr>
            <td>Name</td>
            <td>${user.userName}</td>
        </tr>
        <tr>
            <td>Enable</td>
            <td>${user.enabled}</td>
        </tr>
        <tr>
            <td><a href="<c:url value='/user/changePassword' />" class="brand">Change Password</a></td>
        </tr>
    </table>

</div>

<%@ include file="../footer.jsp" %>