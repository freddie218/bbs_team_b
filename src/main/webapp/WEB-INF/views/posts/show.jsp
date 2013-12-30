<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" scope="request" value="Home"/>

<%@ include file="../header.jsp" %>

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
                <span> Author: </span>
                <a href="<c:url value='/user/users/${post.authorName}' />">
                    <B><c:out value="${post.authorName}"/></B>
                </a>
                <span style="margin-left:10px;"> Create Time: </span>
                <B><c:out value="${post.createTimeString}"/></B>
                <c:if test="${post.parentId==0}">
                    <span style="margin-left:10px;"> Like </span>
                    <B>${post.likedTimes}</B>
                    <span> times </span>
                    <a name="db_share" onclick='dbShareClick("${fn:escapeXml(post.title)}","${fn:escapeXml(post.content)}")' href="javascript:;">
                        <img src="http://img2.douban.com/pics/fw2douban_s.png" alt="推荐到豆瓣" />
                    </a>
                    <script type="text/javascript" src="http://widget.renren.com/js/rrshare.js"></script>
                    <a name="xn_share" onclick='rrShareClick("${fn:escapeXml(post.title)}","${fn:escapeXml(post.content)}")' type="icon_small" href="javascript:;" style="position:absolute;margin:3px 0 0 3px;"></a>
                </c:if>
            </div>
            <br/>
            <div id="postContent">
               <c:out value="${post.content}"/>
            </div>
            <div id="operations">
                <span style="margin-left:10px;"> Operations: </span>
                <c:if test="${post.parentId==0}">
                    <c:if test="${row.index==0}">
                         <c:if test="${not empty liked}">
                            <c:if test="${not liked}">
                                <B><a href='javascript:alert("you like this post");location.href="like/${post.postId};"'>Like</a></B>
                            </c:if>
                         </c:if>
                    </c:if>
                </c:if>
                <B>
                    <c:if test="${not empty isAuthor}">
                        <a href="javascript:void(0);" onclick="show_confirm('${post.postId}');">X</a>
                    </c:if>
                </B>
            </div>
        </c:forEach>
    </div>
    <br/>
    <c:choose>
        <c:when test="${not empty failed}">
            <div id="replyPostError" class="page-action create-error">
                <i>Content cannot be empty !</i>
            </div>
         </c:when>
         <c:otherwise>
             <div id="replyCreateHint" class="page-action">

             </div>
         </c:otherwise>
    </c:choose>

    <div id="createPanel">
        <form action="<c:url value='/posts/${postId}' />" method="post"
            onsubmit='return contentLegal(["Recontent"], "replyCreateHint", VIOLATIONS_WARNING);' >
            <input type="hidden" id="parentId" name="parentId" value="${mainPost.postId}" />
            <input type="hidden" id="title" name="title" value="Re: ${fn:escapeXml(mainPost.title)}" />
            <textarea style="background:white" name="content" id="Recontent"  placeholder="post content" cols="100" rows="6"></textarea>
            <br/>
            <br/>
            <button type="submit" class="btn">Create</button>
        </form>
    </div>


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
    <br/>
</div>
<script type="text/javascript" src="<c:url value='/scripts/bannedWords.js' />"></script>
<%@ include file="../footer.jsp" %>