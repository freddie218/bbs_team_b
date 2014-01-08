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

<div id="content">

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
    </table>        
    <c:if test="${not empty isMyself}">
    <div class="changefield">
      <a href="<c:url value='/user/changePassword' />" class="brand first">Change Password</a> 
      <a href="<c:url value='/user/updateProfile' />" class="brand">Update Profile</a>
    </div>
    </c:if>
</div>


<div id="postinfo">
<H2> My Posts </H2>
<table class="table table-striped">
    <thead>
    <tr>
        <th class="first">Title</th>
        <th>Author</th>
        <th>Publish Time</th>
        <c:if test = "${not empty isMyself}">
        <th>Operations</th>
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
            <c:set var="search" value="\'" />
            <c:set var="replace" value="\\\'" />
            <c:set var="title_fixed" value="${fn:replace(post.title, search, replace)}"/>
            <c:set var="search" value="\"" />
            <c:set var="replace" value="\\\"" />
            <c:set var="title_fixed" value="${fn:replace(title_fixed, search, replace)}"/>
            <a href='javascript:if(confirm("Are you sure to delete this post?(post title:${fn:escapeXml(title_fixed)})")){location.href="del/${post.postId}"}'>X</a>
            </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</div>
<%@ include file="../footer.jsp" %>

