<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Profile"/>

<%@ include file="../header.jsp" %>

<div class="alter-success" id="message">
    <a class="close" data-dismiss="alter" href="#">&times;</a>
    $(message)
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

</div>

<a href="./ChangePassword">Change Password</a>

<%@ include file="../footer.jsp" %>