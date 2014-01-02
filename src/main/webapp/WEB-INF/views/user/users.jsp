<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Profile"/>

<%@ include file="../header.jsp" %>


<div id="userInformationPage">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Enable</th>
            <th>Disable</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}" varStatus="row">
            <tr>
                <td><a href="<c:url value='/user/users/${user.userName}' />">
                         <c:out value="${user.userName}"/>
                 </a> </td>
                <td> <c:out value="${user.enabled}"/> </td>
                <td>
                <c:if test="${user.isRegular}">

                  <c:if test="${user.enabled==true}">

                        <a href='javascript:if(confirm("The page at host:8080 say:\n Are you sure to disable this user?")){location.href="dis/${user.id}"}'>X</a>
                   </c:if>

                   </c:if>
               </td>
               <td>
               <c:out value="${user.userRole}"/>

                 <c:if test="${user.isRegular}">

                                        <a href='javascript:if(confirm("Are you sure to authorise this user?")){location.href="authorise/${user.id}"}'><span class="text-primary">&uarr;</span></a>
                 </c:if>

               </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../footer.jsp" %>

