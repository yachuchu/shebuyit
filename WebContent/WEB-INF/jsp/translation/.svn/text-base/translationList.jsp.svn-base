<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Shebuyit System</title>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/js/deployJava.js'/>"></script>
<style type="text/css"> 
#menu{width:1000px;height:35px;float:right;margin:0px; clear:both; vertical-align: bottom;} 
#ul li{clear:both;display:inline; font-size: larger;} 
#myul li{float:none;display:inline;"} 
</style>
<script type="text/javascript">
<%@ include file="/common/js/common.js" %>
dojo.require("dojo.parser"); 
dojo.require("dijit.form.ComboBox");
dojo.require("dojo.io.iframe");

function searchTranslation(){
	window.location.href="<c:url value='/translation/search.do'/>";
}
</script>

</head>
<body>
	<!-- Page content -->
	<div>
	<ul id="myul" style="width:790px;">
	<c:forEach items="${translationList}" var="translation">  
          &nbsp;&nbsp;&nbsp;&nbsp;<li>${translation.zhName}&nbsp;&nbsp;${translation.enName}</li>			
    </c:forEach>	
	</ul>
	</div>
</body>
</html>
