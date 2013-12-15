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
<style>
body{
  background:#eee;
  margin:0 auto;
  width:100%; 
  max-width:800px; 
}
#content{
  margin-left:auto;
  margin-right:auto;
}
.navbar ul li a:hover{
  background:#ddd;
}
#userInformation{
  width:240px;
  display:block;
}
#userInformation table{
  width:240px;
}
#userInformation table tr td{
  background:#f9f9f9;
  overflow:visible;
}
#userInformation table tr td:first-child {
  background:#ddd;
  width:100px;
  border-bottom:1px #fff solid;
}

#userInformation .changefield {
  margin:0px;
  list-style-type:none;
  overflow:hidden;
  width:240px;
}
#userInformation .changefield a{
  font-size: 14px;
  display:inline;
  padding-left: 7px;
  margin-left: 7px;
  border-left: 1px #000 solid; /*Vertical dividing line*/
}
#userInformation .changefield .first{
padding-left: 0px;
margin-left: 0px;
border-left: 0;
}
#postinfo{
  overflow:hidden;
  margin-top:50px;
  height: auto;
  width:600px;
}
#postinfo h2{
  font-size:24px;
}
#postinfo table tr td{
  background:#f9f9f9;
  max-width:100px;
}
#postinfo table tr:first-child{
  background:#ddd;
}
#postinfo table tr th{
  border-left:1px #fff solid;
}
#postinfo table tr th:first-child{
  border-left:0;
}
footer{        /* from bootstrap navbar-fixed-bottom */
    position: fixed; 
    left: 10px;
    bottom: 10px;
    z-index: 1030;
}
</style>
<div id="content">
<!-- x -->
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
            <a href='javascript:if(confirm("Are you sure to delete this post?(post title:${fn:escapeXml(post.title)})")){location.href="del/${post.postId}"}'>X</a>
            </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</div>
<%@ include file="../footer.jsp" %>

