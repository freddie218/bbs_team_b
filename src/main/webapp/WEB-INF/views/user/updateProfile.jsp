<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="updateProfile"/>

<%@ include file="../header.jsp" %>

<c:choose>
    <c:when test="${not empty updateProfileFailed}">
        <div id="updateProfileFailed" class="page-action updateProfile-failed">
            <i class="messageError">Update Profile Failed!</i>
        </div>
    </c:when>
</c:choose>

<div id="updateProfilePanel">
    <form class="form-horizontal" action="<c:url value='/user/updateProfile'/>" method="post">
        <div id="userInformation">
                <tr>
                     <label class="brand" for="username">Update Profile for</label>
                     <label class="brand">${user.userName}</label>
                </tr>
        </div>
       <hr />
        <div class="control-group">
            <label class="control-label" for="username">Username</label>
            <div class="controls">
                <input type="text" placeholder="username" id="new username" name="new username" />
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Update Profile</button>
            </div>
        </div>
    </form>
</div>

<%@ include file="../footer.jsp" %>