<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Home"/>

<%@ include file="../header.jsp" %>

<table class="table">
    <thead>
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Publish Time</th>
            <th>Content</th>
            <th>Liked</th>
            <th>Operations</th>
        </tr>
    </thead>

    <tbody>
        <c:forEach var="post" items="${posts}" varStatus="row">
            <tr>
                <td><c:out value="${post.title}"/></td>
                <td>
                    <a href="<c:url value='/user/${post.authorName}' />">
                        <c:out value="${post.authorName}"/>
                    </a>
                </td>
                <td><c:out value="${post.createTimeString}"/></td>
                <td><c:out value="${post.content}"/></td>
                <td>
                     <c:if test="${row.index==0}">
                        ${post.likedTimes} times
                     </c:if>
                </td>
                <td>
                     <c:if test="${row.index==0}">
                         <c:if test="${not liked}">
                              <a href='like/${post.postId}'>Like</a>
                         </c:if>
                     </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</br>
 <c:choose>
      <c:when test="${not empty failed}">
          <div id="createPostFailed" class="page-action createPost-failed">
              <i class="messageError">Content cannot be empty!</i>
          </div>
      </c:when>
  </c:choose>
<div id="createPanel">
    <form action="<c:url value='/posts/${postId}' />" method="post">
        <input type="hidden" id="parentId" name="parentId" value="${mainPost.postId}" />
        <input type="hidden" id="title" name="title" value="Re: ${mainPost.title}" />
        <textarea name="content" id="content"  placeholder="post content" cols="100" rows="6"></textarea>
        <button type="submit" class="btn">Create</button>
    </form>
</div>

</br>

<%@ include file="../footer.jsp" %>