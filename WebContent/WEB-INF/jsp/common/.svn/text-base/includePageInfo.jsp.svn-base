
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>

<script type="text/javascript">
	function setVar_${param.gridId}(){
		var queryCondition =  document.forms["${param.gridId}PageInfoForm"]["paging.queryCondition"].value;
		toPageUrl = "<c:url value='${param.url}'/>"+encodeURI(queryCondition);
		toPageRefreshObjectID = "${param.gridId}";
		toPageJsPreMethod = "${param.jsPreMethod}";
		toPageJsLastMethod = "${param.jsLastMethod}";
	}

	function myGridSort(e) {     
		columnSort = e.cellIndex;
		if(oldColumnSort&&columnSort!=oldColumnSort){
			systemlogsortAscending = false;
		}
			var data = e.rowIndex;        
		 if (columnSort != -1) {         
			 var propSort = layout[e.cellIndex].name;//   
			  //var sortby = layout[e.cellIndex].field;// 
			  var sortby = layout[e.cellIndex].id;// 
		  if (sortby) {     
				   var cells_length = layout.length;//         
				   if (true) {             //                  
					      systemlogsortAscending = !systemlogsortAscending; //            
					      var order = 'DESC';             
					      if (systemlogsortAscending){                 
					    	  order = 'ASC'; 
					      }
					      dojo.byId("sortby").value=sortby;
						  dojo.byId("order").value=order;
						  dojo.byId("cells_length").value=cells_length;
						  dojo.byId("columnSort").value=columnSort;
						  dojo.byId("propSort").value=propSort;
						  dojo.byId("sortAscending").value=systemlogsortAscending;
						  var form = document.getElementById("${param.formId}");
						  var url = form.action + "?sortby=" + sortby + "&order=" + order;	   
						  oldColumnSort=columnSort;       
					      loadGridDataAJAX("${param.formId}", "${param.gridId}", url,cells_length, columnSort, propSort, systemlogsortAscending);             						      						                       
		       		}  
			  }  
			} 
	}

	function cleanGridSort(sortby,order,cells_length, columnSort, propSort, sortAscending){
		dojo.byId("sortby").value=sortby;
		dojo.byId("order").value=order;
		dojo.byId("cells_length").value=cells_length;
		dojo.byId("columnSort").value=columnSort;
		dojo.byId("propSort").value=propSort;
		dojo.byId("sortAscending").value=sortAscending;
	}

	function setDefSort(gridId, sortby, order, cells_length, columnSort, propSort, sortAscending) {
		dojo.byId("sortby").value=sortby;
		dojo.byId("order").value=order;
		dojo.byId("cells_length").value=cells_length;
		dojo.byId("columnSort").value=columnSort;
		dojo.byId("propSort").value=propSort;
		dojo.byId("sortAscending").value=sortAscending;
		updateHeaderView(gridId, cells_length, columnSort, propSort,sortAscending);				
	}

	function checkMultiValue(divName,checkName){
		var iDs=dojo.query("[name='"+checkName+"']", divName); 
		var value=new Array();
		for(var i=0;i<iDs.length;i++){
			console.log(iDs[i].value);
			if(iDs[i].checked){
			 console.log("> "+iDs[i].value);
			 value.push(iDs[i].value);
			}
		}
	 	return value;
	} 

</script>
<table  cellspacing="0" cellpadding="0" width="100%">
   <tr>
      <td>
      &nbsp;
      </td>
   </tr>
</table>
<form id="${param.gridId}PageInfoForm" name="${param.gridId}PageInfoForm" method="post" onsubmit="setVar_${param.gridId}();pageGo();return false;">
		 <input type="hidden" name="doSearch" value="true" />
		 <input type="hidden" id="sortby" name="sortby" value=""/>
		 <input type="hidden" id="order" name="order" value=""/>
		 <input type="hidden" id="cells_length" name="cells_length" value=""/>
		 <input type="hidden" id="columnSort" name="columnSort" value=""/>
		 <input type="hidden" id="propSort" name="propSort" value=""/>
		 <input type="hidden" id="sortAscending" name="sortAscending" value=""/>
		 <table align="right" cellspacing="0" cellpadding="0" class="wds_oppm_page_table">
	      <tr>
	         <td>
				<fmt:message key="pageinfo.total" />
				<span id="${param.gridId}totalRecord">${dojoGridVO.paging.totalRecord}</span>
				<fmt:message key="pageinfo.center" />
				<span id="${param.gridId}size">${dojoGridVO.paging.size}</span>
				<fmt:message key="pageinfo.unit" />
			</td>
			<td>&nbsp;</td>
			<td>
				<a href="javascript:void(0);"  style="text-decoration: none;border-bottom: 0 solid;vertical-align: top;"  onclick="setVar_${param.gridId}();toFirst();"><img src="<c:url value='/common/wdsoppmstyle/images/fyicon_18.gif'/>" border="0" /></a>
		     </td>
		     <td>&nbsp;</td>
		     <td>
				<a href="javascript:void(0);"  style="text-decoration: none;border-bottom: 0 solid;vertical-align: top;" onclick="setVar_${param.gridId}();toPre();"><img src="<c:url value='/common/wdsoppmstyle/images/fyicon_19.gif'/>" border="0" /></a>
		     </td>
		     <td>&nbsp;</td>
		     <td>
		  			<fmt:message key="pageinfo.head" />&nbsp;
		  			<span id="${param.gridId}currentPageNum">${dojoGridVO.paging.current}</span>&nbsp;
		  			<fmt:message key="pageinfo.middle"/>&nbsp;
		  			<span id="${param.gridId}totalPageNum">${dojoGridVO.paging.total}</span>&nbsp;
		  			<fmt:message key="pageinfo.tail" />
		     </td>
		     <td>&nbsp;</td>
		     <td>
				<a href="javascript:void(0);" style="text-decoration: none;border-bottom: 0 solid;vertical-align: top;" onclick="setVar_${param.gridId}();toNext();"><img src="<c:url value='/common/wdsoppmstyle/images/fyicon_20.gif'/>" border="0" /></a>
		     </td>
		     <td>&nbsp;</td>
		     <td>
				<a href="javascript:void(0);" style="text-decoration: none; border-bottom: 0 solid;vertical-align: top;" onclick="setVar_${param.gridId}();toLast();"><img src="<c:url value='/common/wdsoppmstyle/images/fyicon_22.gif'/>" border="0" /></a>
	         </td>	
	         <td>&nbsp;</td>
	         <td>
				<input name="paging.current" style="height: 10px;width: 20px;font-size: 12px;vertical-align: top;" size="3" />
		     </td>
		     <td>&nbsp;</td>
		     <td>
				<input type="hidden" name="paging.size" value="${dojoGridVO.paging.size}"/>
				<input type="hidden" name="paging.queryCondition" value="${dojoGridVO.paging.queryCondition}"/>
				<a href="javascript:void(0);" style="text-decoration: none; border-bottom: 0 solid;vertical-align: top;" onclick="setVar_${param.gridId}();pageGo();"><img src="<c:url value='/common/wdsoppmstyle/images/fyicon_24.gif'/>" border="0" /></a>
				<INPUT type="hidden" name="commonValue" value="" />
		     </td>
		 </tr>
		</table>
</form>
