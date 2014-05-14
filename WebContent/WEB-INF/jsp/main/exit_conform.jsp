<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page contentType="text/html; charset=utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"  href="<c:url value='/common/dojo-1.4.3/dijit/themes/tundra/tundra.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/common/dojo-1.4.3/dojo/resources/dojo.css'/>" />
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"
				djConfig="parseOnLoad: true, usePlainJson:true, extraLocale: ['en-us']">
</script>
<script type="text/javascript">
	dojo.require("dijit.Dialog");
	dojo.require("dijit.form.Button");       	
</script>
<script type="text/javascript">
	function showsExit(){
	   document.getElementById("exit_mp_div").style.visibility = "visible";
	   dijit.byId("exit_mp").show();
	}  
	
	function hideExit(){
	 	dijit.byId("exit_mp").hide();
	}
</script>
</head>
<body>
	  <div class="tundra" dojoType="dijit.Dialog" id="exit_mp" title="<fmt:message key='common.message.warning' />" style="text-align: left">      
		<div id="exit_mp_div" style="visibility: hidden;">
			<table style="width: 250px;height: 100px;">
			   <tr>
				 <td colspan="2" align="center">
				 <font style="font-size: 14px;"><fmt:message key='common.message.out' /></font>
				 </td>
			   </tr>			   
			   <tr>
			     <td colspan="2" align="center" height="30px">
			       <button dojoType="dijit.form.Button" onclick="exit_MP();"><fmt:message key='common.message.yes' /></button>&nbsp;&nbsp;&nbsp;&nbsp;
			       <button dojoType="dijit.form.Button" onclick="hideExit();"><fmt:message key='common.message.no' /></button>
			     </td>
			   </tr>
			</table>  
		</div>
      </div>	
</body>
</html>