<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <c:when test="${not empty updateProfileSuccess}">
            <div id="updateProfileSuccess" class="page-action updateProfile-success">
                <i class="messageInfo">Update Profile Successful!</i>
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
            <c:if test="${not empty isMyself}">
        <tr>
            <td><a href="<c:url value='/user/changePassword' />" class="brand">Change Password</a></td>
            <td><a href="<c:url value='/user/updateProfile' />" class="brand">Update Profile</a></td>
        </tr>
            </c:if>
    </table>
</div>



<H2> My Posts </H2>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Publish Time</th>
        <c:if test = "${not empty isMyself}">
        <th>Delete</th>
        </c:if>
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
            <td><c:out value="${post.authorName}" /></td>
            <td><c:out value="${post.createTimeString}"/></td>
            <c:if test = "${not empty isMyself}">
            <td>
            <a href='javascript:if(confirm("Are you sure to delete this post?(post title:${fn:escapeXml(post.title)})")){location.href="del/${post.postId}"}'>X</a>
            </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="../footer.jsp" %>

