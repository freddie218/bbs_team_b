<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="changePassword"/>

<%@ include file="../header.jsp" %>

<div id="changePasswordPanel">
    <form class="form-horizontal" action="<c:url value='/user/changePassword' />" method="post">
        <div class="control-group">
            <label class="control-label" for="old password">Old Password</label>
            <div class="controls">
                <input type="password" placeholder="old password" id="old password" name="old password" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="new password">New Password</label>
            <div class="controls">
                <input type="password" placeholder="new password" id="new password" name="new password" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="confirm password">Confirm Password</label>
            <div class="controls">
                <input type="password" placeholder="confirm password" id="confirm password" name="confirm password" />
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Change Password</button>
            </div>
        </div>
    </form>
</div>

<%@ include file="../footer.jsp" %>