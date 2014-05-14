<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Shebuyit System</title>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/js/deployJava.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ckeditor/ckeditor.js'/>" ></script>

<script type="text/javascript" src="<c:url value='/kindeditor-4.0.1/kindeditor-min.js'/>"></script>
<script type="text/javascript">
<%@ include file="/common/js/common.js" %>
<%@ include file="/common/js/pageInfo.js" %>
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dojo.parser");
dojo.require("dijit.form.ComboBox");
dojo.require("dojo.io.iframe");
$(document).ready(function(){
	
	/* setup navigation, content boxes, etc... */
	Administry.setup();
	
	// validate form on keyup and submit
	var validator = $("#orderItemform").validate({
		rules: {
			sku: {
				required: true
			}
			
		},
		messages: {
			sku: {
				required: "<fmt:message key='required.sku'/>"
			}
			
		},
		// the errorPlacement has to take the layout into account
		errorPlacement: function(error, element) {
			error.insertAfter(element.parent().find('label:first'));
		},
		// specifying a submitHandler prevents the default submit, good for the demo
		submitHandler: function() {
						dojo.io.iframe.send({
			        		url: "<c:url value='/order/saveItem.do'/>",  
				       		form: dojo.byId("orderItemform"), 
				       		handleAs: "json",
				       		timeout:"15000",
				       		handle: function(response, ioArgs){
				       		if(response.returnCode == "000" || response.returnCode==000){	       					
				       			window.returnValue=response.returnCode; 
				       			window.close(); 
			       			}else{
			       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
			       				document.getElementById("errorBox").style.display = "block";
			       			}         
				   		},
				   		method: 'POST'
				   		});
		}

	});
	

});

function searchOrderItem(){
	window.location.href= "<c:url value='/item/search.do'/>";		
}

function hiddenViewMessage() {	
	document.getElementById("successBox").style.display = "none";
	document.getElementById("errorBox").style.display = "none";			
}
</script>

</head>
<body>
<!-- Header -->
		
	<div id="page">
		<!-- Wrapper -->
		<div align="center">
				<!-- Left column/section -->
				<section class="column width4 first">					

					<div class="clear">&nbsp;</div>	
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
					<c:choose>
		               <c:when test="${returnCode=='000'}">
		                  <div id="successBox" class="box box-success" >
							<table  cellspacing="0" cellpadding="0" width="100%" border="0">
								<tr>
								<td width="90%" align="left"><span><fmt:message key='common.success'/></span></td>
								<td width="10%" align="right"><a href="#" style="border-bottom: 0px solid;a:hover{ border-bottom: 0px; }" onclick="hiddenSuccessBox()"><img src="<c:url value='/img/cancel.png'/>"></a></td>
								</tr>
							</table>
							</div>
		               </c:when>
		               <c:when test="${returnCode!=null&&returnCode!='000'}">
		                   <div id="errorBox" class="box box-error">
								<table  cellspacing="0" cellpadding="0" width="100%" border="0">
									<tr>
									<td width="90%" align="left"><span id="errorBoxMesg">${returnCode}</span></td>
									<td width="10%" align="right"><a href="#" style="border-bottom: 0px solid;a:hover{ border-bottom: 0px; }" onclick="hiddenErrorBox()"><img src="<c:url value='/img/cancel.png'/>"></a></td>
									</tr>
								</table>
							</div>
		               </c:when>
		               <c:otherwise>
		                  
		               </c:otherwise>
		             </c:choose>		
					
						
					<div class="clear">&nbsp;</div> 

					
					<form id="orderItemform" method="post" action="<c:url value='/order/saveItem.do'/>">	
					<input type="hidden" id="orderId" class="" value="${orderId}" name="orderId"/>
							<c:if test="${item!=null}">
					        <p>
					        <label><fmt:message key='orderItem.orderItemId'/><fmt:message key='common.colon'/></label>
						    <input type="hidden" id="orderItemId" class="" value="${item.id}" name="orderItemId"/>
						    ${item.id}						    
						    </p>
						    </c:if>
						    
							<p>
							  <label class="required" for="sku"><fmt:message key='orderItem.sku'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="sku" class="" value="${item.sku}" name="sku"/>
							</p>
							
							<c:if test="${item!=null}">
							<p>
							  <label><fmt:message key='orderItem.price'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="price" class="" value="${item.price}" name="price"/>(<fmt:message key='common.rmb'/>)
							</p>
							</c:if>
							
							<p>
							  <label><fmt:message key='orderItem.taobaoPrice'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="taobao_price" class="" value="${item.taobao_price}" name="taobao_price"/>(<fmt:message key='common.rmb'/>)
							</p>
							
							<p>
							  <label><fmt:message key='orderItem.taobaoOrderNumber'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="taobao_order_number" class="" value="${item.taobao_order_number}" name="taobao_order_number"/>
							</p>
							
							<p>
							  <label><fmt:message key='orderItem.quantity'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="quantity" class="" value="${item.quantity}" name="quantity"/>
							</p>
							
																								
						    <p class="box">
						    <input type="submit" class="btn btn-green big" value="<fmt:message key='button.submit'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;						    
						    <input type="button" class="btn btn-green big" onclick="window.close();" value="<fmt:message key='button.close'/>"/>
						    </p>
							
					</form>
														 
				</section>
				<!-- End of Left column/section -->
				
				<!-- Right column/section -->
		  
				<!-- End of Right column/section -->
				
		</div>
		<!-- End of Wrapper -->
	</div>
	

<script type="text/javascript" SRC="<c:url value='/js/administry.js'/>"></script>
</body>
</html>
