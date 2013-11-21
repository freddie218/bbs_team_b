<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="ChangePassword"/>

<%@ include file="../header.jsp" %>

<div class="alert-warning alert-block" id="message">
    <a class="close" data-dismiss="alert" href="#">&times;</a>
    Profile
</div>

<script type="text/javascript" >
	function check_validate(){
		var pass = changePassword.password.value;
		var new_pass = changePassword.newPassword.value;
		var cfm_pass = changePassword.confirmPassword.value;
		if (pass == "" || new_pass == "" || cfm_pass == ""){
		    alert("Password or new password should not be blank!");
		    document.getElementById("password").focus();
		    return(false);
		    }
		if ( new_pass != cfm_pass){
		    alert("Please confirm new password input 2 times are identical to each other! ");
		    document.getElementById("newPassword").focus();
            return(false);
		    }
		if (new_pass == pass){
		    alert("New password should not be same with the old one!");
		    document.getElementById("newPassword").focus();
            return(false);
		    }
		if (new_pass.length < 6) {
		    alert("New password should be 6-16 characters!");
            document.getElementById("newPassword").focus();
            return(false);
		    }
		alert("change successfully!");
		return(true);
	}

</script>

<form class="form-horizontal" method="post" name="ChangePassword" onsubmit="return check_validate();">
    <fieldset>
    <legend>Change Password for ${user.userName}</legend>
    <div class="control-group">
        <label class="control-label" for="inputPassword">Password:</label>
        <div class="controls">
            <input type="password" id="password" name="password" placeholder="Password" >
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="newPassword">New Password:</label>
        <div class="controls">
            <input type="password" id="newPassword" name="newPassword" placeholder="New Password" maxlength="16" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="confirmPassword">Confirm Password:</label>
        <div class="controls">
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm New Password" maxlength="16" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"></label>
        <div class="controls">
            <button type="submit" class="btn btn-primary" >Change</button>
        </div>
    </div>
    </fieldset>
</form>

<%@ include file="../footer.jsp" %>