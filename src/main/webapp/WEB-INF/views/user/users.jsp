<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Profile"/>

<%@ include file="../header.jsp" %>


<div id="userInformation">
    <table class="table">
        <tr>
            <th>Name</th>
            <th>Enable</th>
        </tr>


        <c:forEach var="user" items="${users}" varStatus="row">
            <tr>
                <td> <c:out value="${user.userName}"/> </td>
                <td> <c:out value="${user.enabled}"/> </td>
            </tr>
        </c:forEach>


    </table>
</div>


<%@ include file="../footer.jsp" %>

