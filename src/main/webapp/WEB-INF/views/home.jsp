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
    <c:forEach var="post" items="${postsWithLiked}" varStatus="row">
        <tr>
            <td>
                <a href="<c:url value='/posts/${post.key.postId}' />">
                    <c:out value="${post.key.title}"/>
                </a>
            </td>
            <td>
                <a href="<c:url value='/user/users/${post.key.authorName}' />">
                    <c:out value="${post.key.authorName}"/>
                </a>
            </td>
            <td><c:out value="${post.key.createTimeString}"/></td>
            <td>
                <c:if test="${not post.value}">
                    <a href='javascript:alert("you like this post");location.href="posts/like/${post.key.postId}";'>Like</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="footer.jsp" %>