<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Login"/>

<%@ include file="header.jsp" %>

    <c:choose>
        <c:when test="${not empty error}">
            <div id="loginError" class="page-action login-error">
                Your login attempt was not successful.
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:when>
        <c:otherwise>
            <div id="loginHint" class="page-action">
                Login with your name and password
            </div>
        </c:otherwise>
    </c:choose>

	<form class="form-horizontal" name='f' action="<c:url value='j_spring_security_check' />" method="post">
        <div class="control-group">
            <label class="control-label" for="j_username">Username</label>
            <div class="controls">
                <input type='text' id="j_username" name='j_username' placeholder="Username" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="j_password">Password</label>
            <div class="controls">
                <input type="password" id="j_password" name="j_password" placeholder="Password" />
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Sign in</button>
            </div>
        </div>

        Don't have an account? <a href='<c:url value="/user/create" />'>Click here to register one!</a>
	</form>

<%@ include file="footer.jsp" %>