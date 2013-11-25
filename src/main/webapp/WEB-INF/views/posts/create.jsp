<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="createPost"/>

<%@ include file="../header.jsp" %>

<style type="text/css">
    div #createPost-tip{
        padding-left:143px;
        padding-bottom:10px;
        height=30px;
        color:red;
        display:none;
    }
</style>

<script type="text/javascript" >
    function checkPostValid(){
        var otitle= createPost.title.value;
        var ocontent=createPost.content.value;
        if(otitle=="" || ocontent==""){
            document.getElementById("createPost-tip").style.display="block";
            document.getElementById("title").focus();
            return(false);
        }
        return(true);
    }
 </script>
<div id="createPanel">
    <form class="form-horizontal" name="createPost" action="<c:url value='/posts/create' />"
        method="post" onsubmit="return checkPostValid();">
        <!-- 帖子标题和内容不能为空，否则消息提示-->
        <div id="createPost-tip">
              <span >Title or content cannot be empty! </span>
        </div>
        <div class="control-group">
            <label class="control-label" for="title">Title</label>
            <div class="controls">
                <input type="text" placeholder="post title" id="title" name="title" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="content">Content</label>
            <div class="controls">
                <textarea placeholder="post content" id="content" name="content" cols="100" rows="6"></textarea>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Create</button>
            </div>
        </div>
    </form>
</div>

<%@ include file="../footer.jsp" %>