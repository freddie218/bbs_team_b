<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Profile"/>

<%@ include file="../header.jsp" %>


<div id="userInformation">
    <table class="table">
        <tr>
            <th>Name</th>
            <th>Enable</th>
            <th>Disable</th>
        </tr>


        <c:forEach var="user" items="${users}" varStatus="row">
            <tr>
                <td> <c:out value="${user.userName}"/> </td>
                <td> <c:out value="${user.enabled}"/> </td>
                <c:choose>
                   <c:when test="${not empty return}">
                      <td>
                         <a href='javascript:if(confirm("Are you sure to disable this user?){location.href="dis/${user.userId}"}'>X</a>
                      </td>
                   </c:when>
                </c:choose>
            </tr>
        </c:forEach>


    </table>
</div>


<%@ include file="../footer.jsp" %>

