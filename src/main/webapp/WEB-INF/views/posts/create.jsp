<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="createPost"/>

<%@ include file="../header.jsp" %>
<script type="text/javascript" >
    function checkPostValid(){
        var otitle= createPost.title.value;
        var ocontent=createPost.content.value;
        var reg =/^[\s]{0,}$/ ;
        if(reg.test(otitle) || reg.test(ocontent)){
            var postError = document.getElementById("replyPostError");
            postError.className="page-action create-error";
            postError.innerHTML="Title or content cannot be empty";
            document.getElementById("title").focus();
            return(false);
        }
        return(true);
    }
 </script>
 <br/>
 <c:choose>
     <c:when test="${not empty failed}">
         <div id="replyPostError" class="page-action create-error">
             <i>Title or content cannot be empty!</i>
         </div>
      </c:when>
      <c:otherwise>
          <div id="replyPostError" class="page-action">

          </div>
      </c:otherwise>
 </c:choose>
<div id="createPanel">
   <!-- <form class="form-horizontal" name="createPost" action="<c:url value='/posts/create' />"
        method="post" onsubmit="return checkPostValid();" >    -->
        <form class="form-horizontal" name="createPost" action="<c:url value='/posts/create' />" method="post"
         onsubmit='return contentLegal(["title", "content"], "replyPostError", VIOLATIONS_WARNING);' >
        <div class="control-group">
            <label class="control-label" for="title">Title</label>
            <div class="controls">
                <input type="text" class="form-control" placeholder="post title" id="title" name="title" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="content">Content</label>
            <div class="controls">
                <textarea class="form-control" placeholder="post content" id="content" name="content" cols="100" rows="6"></textarea>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Create</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="<c:url value='/scripts/bannedWords.js' />"></script>
<%@ include file="../footer.jsp" %>