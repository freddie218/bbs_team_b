<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Home"/>

<%@ include file="../header.jsp" %>


<<<<<<< HEAD
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
                              <a href='javascript:alert("you like this post");location.href="like/${post.postId};"'>Like</a>
                         </c:if>
                     </c:if>
                </td>
                <td>
                     <c:if test="${not empty isAuthor}">
                         <a href="javascript:void(0);" onclick="show_confirm('${post.postId}');">X</a>
                     </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
=======
 <style>
 body{
    background:#eee;
    margin:0 auto;
    width:800px;
 }
 #createPanel{
    width:760px;
    margin:0 auto;
>>>>>>> 4f016c0f60e0cc97ae65f01cdf9ba23dbb92ccd0

 }
 #content{
   background:#eee;
   margin:0 auto;
   width:800px;
   width:80%;
 }
 .navbar ul li a:hover{
   background:#ddd;
 }
 #postTable{
   background:#eee;

 }
 #postTitle{
  text-align:center;
  background:#eee;

 }
 #postTitleRe{
   background:#eee;
   text-align:center;
  }
 #postContent{
  background:#ddd;
 }
 #postInFo{
  background:#eee;
  margin:0 auto;
  text-align:center;
 }
 #textarea{
   background:#fff;

 }
</style>

<div id="content">
  <div id="postTable">
    <c:forEach var="post" items="${posts}" varStatus="row">
      <div id="postTitle">
        <c:if test="${post.parentId==0}">
          <h1><c:out value="${post.title}"/></h1>
        </c:if>
      </div>
       <div id="postTitleRe">
          <c:if test="${post.parentId!=0}">
               <h3><c:out value="${post.title}"/></h3>
          </c:if>
       </div>
          <div id="postInFo">
               Author:
            <a href="<c:url value='/user/users/${post.authorName}' />">
               <B><c:out value="${post.authorName}"/></B>
            </a>
               &nbsp Create Time:
               <B><c:out value="${post.createTimeString}"/></B>
          </div>
              </br>
           <div id="postContent">
               <c:out value="${post.content}"/>
           </div>
    </c:forEach>
  </div>

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
            <textarea style="background:white" name="content" id="content"  placeholder="post content" cols="100" rows="6"></textarea>
          </br>
          </br>
        <button type="submit" class="btn">Create</button>
    </form>
 </div>

<<<<<<< HEAD
<script type="text/javascript">
function show_confirm(deletePostId)
{
      document.deletePostForm.deleteReplyPost.value = deletePostId;
      document.deletePostForm.submit();
}
</script>
<form  name="deletePostForm" action="<c:url value='/posts/del' />" method="post">
     <input type="hidden" id="deleteReplyPost" name="deleteReplyPost" >
</form>

=======
  </br>
</div>
>>>>>>> 4f016c0f60e0cc97ae65f01cdf9ba23dbb92ccd0
<%@ include file="../footer.jsp" %>