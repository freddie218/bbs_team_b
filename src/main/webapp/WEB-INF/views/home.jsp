<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Home"/>
<c:set var="pageTitle" scope="request" value="Profile"/>

<%@ include file="header.jsp" %>

<table class="table">
    <thead>
    <tr class="darkgrey">
        <th>Title</th>
        <th>Author</th>
        <th>Publish Time</th>
        <th>Operations</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="post" items="${postsWithLiked}" varStatus="row" >
        <tr class="white">
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

<div class="searchField" >
<div class="title">Please fill search condition</div>
<form id="sandbox" class="form-horizontal form row" name="createPost" action="<c:url value='/' />" method="get" >
    <div class="form-inline">
        <div class="input-group col-lg-6">
            <label class="control-label" for="title">Title</label><br/>
            <input type="text" class="form-control input-large" placeholder="blabla" id="title" name="title" />
        </div>
        <div class="input-group col-lg-6">
            <label class="control-label" for="title">Content:</label><br/>
            <input type="text" class="form-control" placeholder="Content" id="content" name="content" />
        </div>
    </div>

    <div class="form-inline">
        <div class="input-group col-lg-6">
            <label class="control-label" for="title">Publish Time From</label><br/>
            <input type="text" class="span4 form-control" id="dp1" placeholder="12/13/2013" id="dp1" name="dp1">
        </div>
        <div class="input-group col-lg-6">
            <label class="control-label" for="title">Publish Time To</label><br/>
            <input type="text" class="span4 form-control" id="dp2" placeholder="12/25/2013" id="dp2" name="dp2">
        </div>
    </div>
    <div class="form-inline">
        <div class="input-group col-lg-6">
            <label class="control-label" for="title">Author</label><br/>
            <input type="text" class="form-control" id="author" id="author" name="author">
        </div>
        <div class="input-group col-lg-6">
            <br/>
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </div>
</form>
</div>
<clear:both>
<script type="text/javascript" src="<c:url value='/scripts/bootstrap-datepicker.js' />"></script>
<script type="text/javascript" src="<c:url value='/scripts/locales/bootstrap-datepicker.zh-CN.js' />"></script>

<script type="text/javascript">
$(document).ready(function(){
    $('#sandbox  #dp1').datepicker({
        daysOfWeekDisabled: "1",
        calendarWeeks: true,
        autoclose: true,
        todayHighlight: true
    });
    $('#sandbox #dp2').datepicker({
        daysOfWeekDisabled: "1",
        calendarWeeks: true,
        autoclose: true,
        todayHighlight: true
    });
});
</script>

<%@ include file="footer.jsp" %>

