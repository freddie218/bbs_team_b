<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="changePassword"/>

<%@ include file="../header.jsp" %>
<style type="text/css">
    body{
         background:#EEEEEE;
    }
    #changePasswordPanel{
         max-width:650px;
         margin-left: auto;
         margin-right: auto;
    }

    #changePasswordPanel .title{
         font-size: 20px;
         padding:5px 0px 5px 0px;
    }


    #changePasswordPanel .form-horizontal{
         background:#DDDDDD;
         padding:20px 20px 20px 20px;
    }

    #changePasswordPanel .control-group {
         padding:5px 0px 5px 0px;
    }
    #changePasswordPanel .control-group div{
         display:inline ;
    }
    #changePasswordPanel .control-group .info{
        color: blue ;
    }

</style>

<div id="changePasswordPanel">
    <div class='title'>Change Password</div>
    <form class="form-horizontal" action="<c:url value='/user/changePassword' />" method="post">

        <div class="control-group">
            <label class="control-label" for="old password">Old Password</label>
            <div>
                <input type="password" placeholder="old password" id="old password" name="old password" />
            </div>
            <div class='info'>Input your password here.</div>
        </div>
        <div class="control-group">
            <label class="control-label" for="new password">New Password</label>
            <div class="controls">
                <input type="password" placeholder="new password" id="new password" name="new password" />
            </div>
            <div class='info'>New password here.</div>
        </div>
        <div class="control-group">
            <label class="control-label" for="confirm password">Confirm Password</label>
            <div class="controls">
                <input type="password" placeholder="confirm password" id="confirm password" name="confirm password" />
            </div>
            <div class='info'>Confirm new password here.</div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Change</button>
            </div>
        </div>
    </form>
</div>

<%@ include file="../footer.jsp" %>