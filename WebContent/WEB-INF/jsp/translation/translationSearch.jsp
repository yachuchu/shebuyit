<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<%@ page import="com.shebuyit.controller.TranslationController"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Shebuyit System</title>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/common/dojo-1.4.3/dojox/grid/resources/tundraGrid.css'/>" />  		
<link rel="stylesheet" type="text/css" href="<c:url value='/common/dojo-1.4.3/dijit/themes/tundra/tundra.css'/>" />		
<link rel=stylesheet href="<c:url value='/common/wdsoppmstyle/scatax_theme.css'/>">
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"
	djConfig="parseOnLoad: true, usePlainJson:true, extraLocale: ['en-us']">
</script>		
<script type="text/javascript">
		<%@ include file="/common/js/common.js" %>
		<%@ include file="/common/js/pageInfo.js" %>
		$(document).ready(function(){
        	
        	/* setup navigation, content boxes, etc... */
        	Administry.setup();

        });
		
		$(function() {
			$( "#datepicker" ).datepicker();
			$( "#datepicker1" ).datepicker();
		});

		dojo.require("dojo.data.ItemFileWriteStore");
	    dojo.require("dojox.grid.DataGrid");
	    dojo.require("dojo.parser");
	    dojo.require("dijit.form.ComboBox");
	    dojo.addOnLoad( function() {					
	    	//setDefSort("recordLogGrid", "collectionType", "DESC", 4, 0, '<fmt:message key="collection.type" />',false);
	    	
		});

	    var gridList = { identifier:'',  items:${dojoGridVO.jsonData}};
		var initStore=new dojo.data.ItemFileWriteStore({data:gridList});
		var layout = [
			{name:'<input type="checkbox" name="all" id="all" onclick="selectAll(this)">', width:2, get:getSelect},
			{id:'id', name:'<fmt:message key="common.id" />', width:3, field:"id"},
			{id:'zhName', name:'<fmt:message key="translation.zhName" />',width:'auto', get:getZhName},
			{id:'enName',name:'<fmt:message key="translation.enName" />', width:'auto', field:"enName"},			
			{id:'created_time',name:'<fmt:message key="translation.createdTime" />', width:'auto', field:"created_time"}	
		];	
		
		function getSelect(inRowIndex){
			var grid = dijit.byId("translationGrid");
			if (grid.getItem(inRowIndex)){
				return '<input type="checkbox" value="'+grid.getItem(inRowIndex).id+'" name="translationId" />';
			}
		}
		
		function getZhName(inRowIndex) {		 
			var grid = dijit.byId("translationGrid");
			var model = grid.getItem(inRowIndex);
			var zhName = model.zhName;
			var translationId = model.id;		
			if(model){					
				return "<a href='#' onclick='updateTranslation(\""+translationId+"\")'>"+zhName+"</a>";
			}
		}

		function updateTranslation(translationId) {
			window.location.href= "<c:url value='/translation/update.do?translationId="+translationId+"'/>";						
		}

		function addTranslation() {
			window.location.href= "<c:url value='/translation/add.do'/>";				
		}

		function loadGrid(formId, gridId) {
			//cleanGridSort("collectionType", "DESC", 4, 0, '<fmt:message key="collection.type" />',false);				
			var form = document.getElementById(formId);
			var url = form.action;				
			url = url + "?method=search";
			loadGridDataAJAX(formId, gridId, url, 6, 0, '<fmt:message key="common.id" />',false);
		} 
		
		function selectAll(obj){
			var ids = document.getElementsByName("translationId");
			dojo.forEach(ids, function(item){
				item.checked = obj.checked;
			});
		}	
		
		function deleteTranslation(){
			showProgressBar();
			var translationIds="";
			var _checks=checkMultiValue("translationGrid","translationId");
			if(_checks.length<=0){
				alert("<fmt:message key='translation.selectTranslationTip'/>");
				return;
			}
			translationIds=dojo.toJson(_checks);					
			if(confirm("<fmt:message key='common.message.enable'/>")){				     			    
		 		dojo.xhrGet({   
		       		 url: "<c:url value='/translation/delete.do?translationIds="+translationIds+"'/>",  
		       		 handleAs: "json",
		       		 preventCache: true, 
		       		 load: function(response){ 
		       			if(response.returnCode == "000" || response.returnCode==000){	
		       				window.location.href= "<c:url value='/translation/search.do'/>";
		       			}else{
		       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
		       				document.getElementById("errorBox").style.display = "block";
		       			} 
		       			hideProgressBar();
		       		 } 		   
		   		 });
			}																 
		}
	
	</script>
	</head>
	
	<body>
	<!-- Header -->
	<header id="top">
		<div class="wrapper">
			<div id="title"><span><fmt:message key="menu.logo" /></span></div>
			<!-- Top navigation -->
			<div id="topnav">
				<a href="<c:url value='/user/userInfo.do'/>"><img class="avatar" SRC="<c:url value='/img/user_32.png'/>" alt="" /></a>
				<fmt:message key="menu.logged_in_as" /> <b>${sessionScope.user.userName}</b>
				<span>|</span>  <a href="<c:url value='/login/logout.do'/>"><fmt:message key="menu.logout" /></a><br />
				
			</div>
			<!-- End of Top navigation -->
			<!-- Main navigation -->
			<nav id="menu">
				<ul class="sf-menu">
					<li><a HREF="<c:url value='/main/main.do'/>"><fmt:message key='navigation.home'/></a></li>
					<c:if test="${sessionScope.user.type=='admin'}">
					<li><a HREF="<c:url value='/shop/search.do'/>"><fmt:message key='navigation.shopManagement'/></a>
					</li>
					</c:if>
					<li><a HREF="<c:url value='/product/search.do'/>"><fmt:message key='navigation.productManagement'/></a>
						<ul>
							<li>
								<a HREF="<c:url value='/product/search.do'/>"><fmt:message key='navigation.products'/></a>
							</li>
							<li>
								<a HREF="<c:url value='/storage/search.do'/>"><fmt:message key='navigation.outofstockProducts'/></a>
							</li>	
							<li>
								<a HREF="<c:url value='/quick/search.do'/>"><fmt:message key='navigation.quickEdit'/></a>
							</li>				
						</ul>
                    </li>	
                   <li><a HREF="<c:url value='/order/search.do'/>"><fmt:message key='navigation.orderManagement'/></a>
						<ul>
							<li>
								<a HREF="<c:url value='/order/search.do'/>"><fmt:message key='navigation.orders'/></a>
							</li>	
							<li>
								<a HREF="<c:url value='/item/search.do'/>"><fmt:message key='navigation.orderItem'/></a>
							</li>				
						</ul>
                    </li>
                    <li   class="current"><a HREF="<c:url value='/translation/search.do'/>"><fmt:message key='navigation.system'/></a>
                    	<ul>
                    	
                    		<li   class="current">
								<a HREF="<c:url value='/translation/search.do'/>"><fmt:message key='navigation.translation'/></a>
							</li>
							<c:if test="${sessionScope.user.type=='admin'}">
							<li>
								<a HREF="<c:url value='/dict/search.do'/>"><fmt:message key='navigation.dict'/></a>
							</li>	
							<li>
								<a HREF="<c:url value='/user/search.do'/>"><fmt:message key='navigation.user'/></a>
							</li>
							</c:if>					
						</ul>
                    </li>	
                   		
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
		<div class="wrapper">
			<h1><fmt:message key="navigation.system" /> &rarr; <span><fmt:message key="navigation.translation" /></span></h1>
			<!-- Quick search box -->
			<form action="" method="get"><input class="" type="text" id="q" name="q" /></form>
		</div>
	</div>
	<!-- End of Page title -->
	
	<!-- Page content -->
	<div id="page">
	   <div class="wrapper">
	      <section class="column width6 first">
	   	
			<div class="clear">&nbsp;</div>
			
			<form id="searchTranslation" method="post" action="<c:url value='/translation/search.do'/>">	
			<input type="hidden" name="doSearch" value="true">
			<select id="translation_search_param" name="<%=TranslationController.SEARCH_PARAM %>" onChange="changeParam(this.options[this.options.selectedIndex].value);" style="font:12px">	
				<option lang="" value=""><fmt:message key="common.searcyby"/></option>  	       						      
	   			<option lang="" maxLength="20" value="<%=TranslationController.SEARCH_PARAM_ZHNAME %>"><fmt:message key="translation.zhName"/></option>  
	   			<option lang="" maxLength="20" value="<%=TranslationController.SEARCH_PARAM_ENNAME %>"><fmt:message key="translation.enName"/></option>          
	   		</select>:
	        <input type="text" id="" name="<%=TranslationController.SEARCH_PARAM_VALUE %>" lang=""  />	
	        
	        &nbsp;&nbsp;	 	
	       	           		       						
			
	        <input type="button" class="btn" value="<fmt:message key='button.search'/>" onclick="loadGrid('searchTranslation','translationGrid');"/>           					
		    <input type="button" class="btn" value="<fmt:message key='button.reset'/>" onclick="this.form.reset();resetQuery();"/>         							
			
			<br></br>
			<div style="text-align:right;">
			<input type="button" class="btn btn-green big" onclick="addTranslation()" value="<fmt:message key="button.add" />" />
			<input type="button" class="btn btn-green big" onclick="deleteTranslation()" value="<fmt:message key="button.delete" />" />
			</div>
			<div class="tundra" id="translationGrid" dojoType="dojox.grid.DataGrid" selectable="true" style="height: 290px;border:0px solid #777777"
			 store="initStore"  onHeaderCellClick="myGridSort" structure="layout" escapeHTMLInData="false"   
			 noDataMessage='<fmt:message key="grid.noData" />' autoHeight="true">
			</div>
			</form> 

			<jsp:include page="/WEB-INF/jsp/common/includePageInfo.jsp">
				<jsp:param name="gridId" value="translationGrid" />
				<jsp:param name="url" value="/translation/search.do" />
				<jsp:param name="formId" value="searchTranslation" />
			</jsp:include>			
			 <div class="clear">&nbsp;</div>
			</section>
			<!-- End of Left column/section -->
		</div>
		<!-- End of Wrapper -->
	 </div>
	<!-- End of Page content -->
	
	<!-- Page footer -->
	<footer id="bottom">
		<div class="wrapper">
			<p>* This system is based on HTML5. Firefox/Chrome supports better. We don't recommend IE.</p>
			<p>Copyright &copy; 2012 Pierson Capital Technology LLC,</p>
		</div>
	</footer>
	<!-- End of Page footer -->
	
	<!-- Animated footer -->
	<footer id="animated">

	</footer>
	<!-- End of Animated footer -->
	
	<!-- Scroll to top link -->
	<a href="#" id="totop">^ scroll to top</a>

<!-- Admin template javascript load -->
<script type="text/javascript" SRC="<c:url value='/js/administry.js'/>"></script>
</body>
</html>

