<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="errorMessage"/>

<%@ include file="./header.jsp" %>

<div id="notLogin">
    <p>
         <i class="messageError">please login!</i>
    </p>
</div>

<%@ include file="./footer.jsp" %>