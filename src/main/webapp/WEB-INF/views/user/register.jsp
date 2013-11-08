<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Register"/>

<%@ include file="../header.jsp" %>

<div id="registerPanel">
    <form class="form-horizontal" action="<c:url value='/user/create' />" method="post">
        <div class="control-group">
            <label class="control-label" for="username">Username</label>
            <div class="controls">
                <input type="text" placeholder="user name" id="username" name="username" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password">Password</label>
            <div class="controls">
                <input type="password" placeholder="password" id="password" name="password" />
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Create</button>
            </div>
        </div>
    </form>
</div>

<%@ include file="../footer.jsp" %>