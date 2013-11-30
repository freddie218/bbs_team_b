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

<div class="alter-success" id="message">
    <a class="close" data-dismiss="alter" href="#">&times;</a>
</div>

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

<H2> My Posts </H2>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Publish Time</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="post" items="${posts}" varStatus="row">
        <tr>
            <td>
                <a href="<c:url value='/posts/${post.postId}' />">
                    <c:out value="${post.title}"/>
                </a>
            </td>
            <td><c:out value="${post.authorName}"/></td>
            <td><c:out value="${post.createTime}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<%@ include file="../footer.jsp" %>

