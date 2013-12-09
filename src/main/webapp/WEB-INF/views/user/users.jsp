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
                <td>

                   <c:if test="${user.isRegular}">
                        <a href='javascript:if(confirm("Are you sure to disable this user?(user userName)")){location.href="dis/${user.id}"}'>X</a>
                   </c:if>

               </td>
            </tr>
        </c:forEach>


    </table>

</div>


<%@ include file="../footer.jsp" %>

