<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Miikoo System</title>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/js/login.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/js/invisible.js'/>"></script>
<script type="text/javascript">
<%@ include file="/common/js/common.js" %>
		dojo.require("dojo.data.ItemFileWriteStore");
		dojo.require("dojo.parser");
		dojo.require("dijit.form.ComboBox");
		dojo.require("dojo.io.iframe");
		$(document).ready(function(){
			
				/* setup navigation, content boxes, etc... */
				Administry.setup();
				
				// validate signup form on keyup and submit
				var validator = $("#loginform").validate({
					rules: {
						username: "required",
						password: "required",
					},
					messages: {
						username: "<fmt:message key='required.username' />",
						password: "<fmt:message key='required.password' />",
					},
					// the errorPlacement has to take the layout into account
					errorPlacement: function(error, element) {
						error.insertAfter(element.parent().find('label:first'));
					},
					// set new class to error-labels to indicate valid fields
					submitHandler: function() {
						dojo.io.iframe.send({
			        		url: "<c:url value='/login/login.do'/>",  
				       		form: dojo.byId("loginform"), 
				       		handleAs: "json",
				       		timeout:"15000",
				       		handle: function(response, ioArgs){
				       			
				       		if(response.returnCode == "000" || response.returnCode==000){
				       			
				       				window.location="<c:url value='/main/main.do'/>";	
				       		       			
			       			}else{
			       				document.getElementById("errorBoxMesg").innerHTML="<fmt:message key='common.loginError' />";	
			       				document.getElementById("errorBox").style.display = "block";
			       			}         
				   		},
				   		method: 'POST'
				   		});
		}
			});
	
		});
		
		function formSubmit(formName){
			dojo.io.iframe.send({
        		url: "<c:url value='/login/login.do'/>",  
	       		form: dojo.byId(formName), 
	       		handleAs: "json",
	       		timeout:"15000",
	       		handle: function(response, ioArgs){
	       		if(response.returnCode == "000" || response.returnCode==000){
	       				window.location="<c:url value='/main/main.do'/>";	
	       		       			
       			}else{
       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
       				document.getElementById("errorBox").style.display = "block";
       			}         
	   		},
	   		method: 'POST'
	   		});
		}
</script>
</head>
<body>
	<!-- Header -->
	<header id="top">
		<div class="wrapper-login">
			<!-- Title/Logo - can use text instead of image -->
			<div id="title"><span><fmt:message key="menu.logo" /></span></div>
			<!-- Main navigation -->
			<nav id="menu">
				<ul class="sf-menu">
					<li class="current"><a href="<c:url value='/login/view.do'/>"><fmt:message key="navigation.login" /></a></li>                
				</ul>
			</nav>	
			<!-- End of Main navigation -->
			<!-- Aside links -->
			<aside>	
			<c:choose>
               <c:when test="${sessionScope.GLOBAL_LANGUAGE=='zh'}">
                  <a href="#" onclick="changeLanguage('English')"><fmt:message key='common.english'/></a> &middot; <b><fmt:message key='common.chinese'/></b> 
               </c:when>
               <c:when test="${sessionScope.GLOBAL_LANGUAGE=='en'}">
                  <b><fmt:message key='common.english'/></b> &middot; <a href="#" onclick="changeLanguage('Chinese')"><fmt:message key='common.chinese'/></a> 
               </c:when>
               <c:otherwise>
                  <a href="#" onclick="changeLanguage('English')"><fmt:message key='common.english'/></a> &middot; <b><fmt:message key='common.chinese'/></b> 
               </c:otherwise>
             </c:choose>					
			</aside>
			<!-- End of Aside links -->
		</div>
	</header>
	<!-- End of Header -->
	<!-- Page title -->
	<div id="pagetitle">
		<div class="wrapper-login"></div>
	</div>
	<!-- End of Page title -->
	
	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
		<div class="wrapper-login">
				<!-- Login form -->
				<section class="full">					
					
					<h3><fmt:message key="navigation.login" /></h3>
					<div id="successBox" class="box box-success"  style="display:none;">
					<table  cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
						<td width="90%" align="left"><span><fmt:message key='common.success'/></span></td>
						<td width="10%" align="right"><a href="#" style="border-bottom: 0px solid;a:hover{ border-bottom: 0px; }" onclick="hiddenSuccessBox()"><img src="<c:url value='/img/cancel.png'/>"></a></td>
						</tr>
					</table>
					</div>
					<div id="errorBox" class="box box-error"  style="display:none;">
					<table  cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
						<td width="90%" align="left"><span id="errorBoxMesg"></span></td>
						<td width="10%" align="right"><a href="#" style="border-bottom: 0px solid;a:hover{ border-bottom: 0px; }" onclick="hiddenErrorBox()"><img src="<c:url value='/img/cancel.png'/>"></a></td>
						</tr>
					</table>
					</div>
					
					 <div class="clear">&nbsp;</div>
					<form id="loginform" method="post" action="<c:url value='/login/login.do'/>">

						<p>
							<label class="required" for="username"><fmt:message key='user.login.username'/><fmt:message key='common.colon'/></label><br/>
							<input type="text" id="username" class="full" value="" name="username"/>
						</p>
						
						<p>
							<label class="required" for="password"><fmt:message key='user.login.password'/><fmt:message key='common.colon'/></label><br/>
							<input type="password" id="password" class="full" value="" name="password"/>
						</p>
						<p>
							<input type="submit" class="btn btn-green big" value="<fmt:message key='button.login'/>"/>
					  </p>
						<div class="clear">&nbsp;</div>

					</form>
					
				</section>
				<!-- End of login form -->
				
		</div>
		<!-- End of Wrapper -->
	</div>
	<!-- End of Page content -->
	
	<!-- Page footer -->
	<footer id="bottom">
		<div class="wrapper-login">
			<p>Copyright &copy; 2012 Pierson Capital Technology LLC</p>
		</div>
	</footer>
	<!-- End of Page footer -->

<!-- User interface javascript load -->
<script type="text/javascript" SRC="<c:url value='/js/administry.js'/>"></script>
</body>
</html>