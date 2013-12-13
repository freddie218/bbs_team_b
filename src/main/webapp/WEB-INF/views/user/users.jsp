<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Profile"/>

<%@ include file="../header.jsp" %>


<div id="userInformation">
    <table class="table">
        <tr>
            <th>Name</th>
            <th>Enable</th>
            <th>Disable</th>
            <th>Role</th>
        </tr>


        <c:forEach var="user" items="${users}" varStatus="row">
            <tr>
                <td> <c:out value="${user.userName}"/> </td>
                <td> <c:out value="${user.enabled}"/> </td>
                <td>

                   <c:if test="${user.isRegular}">
                        <a href='javascript:if(confirm("The page at host:8080 say:\n Are you sure to disable this user?")){location.href="dis/${user.id}"}'>X</a>
                   </c:if>

               </td>
               <td>
               <c:out value="${user.userRole}"/>
                 <c:if test="${user.isRegular}">
                                        <a href='javascript:if(confirm("The page at localhost:8080 says:\n Are you sure to authorise this user?")){location.href="authorise/${user.id}"}'>↑</a>
                 </c:if>
               </td>
            </tr>
        </c:forEach>


    </table>

</div>


<%@ include file="../footer.jsp" %>

