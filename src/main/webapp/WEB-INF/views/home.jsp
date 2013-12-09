<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Home"/>

<%@ include file="header.jsp" %>

<table class="table table-striped">
    <thead>
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Publish Time</th>
        <th>Operations</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="post" items="${posts}" varStatus="row">
        <tr>
            <td>
                <a href="<c:url value='/posts/${post.postId}' />">
                    <c:out value="${post.title}"/>
                </a>
            </td>
            <td>
                <a href="<c:url value='/user/${post.authorName}' />">
                    <c:out value="${post.authorName}"/>
                </a>
            </td>
            <td><c:out value="${post.createTimeString}"/></td>
            <td>
                <a href='posts/like/${post.postId}'>Like</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="footer.jsp" %>