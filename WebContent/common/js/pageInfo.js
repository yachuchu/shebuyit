
dojo.require("dojo.fx");
dojo.addOnLoad(function() {
var messages = dojo.query('.messageContainer');
	dojo.forEach(messages, function(divtag, i){
		  var textToShow = '';
		  var flag = 'false';
		  if(divtag.firstChild.firstChild && divtag.firstChild.firstChild.nodeName == '#text' 
			  && divtag.firstChild.firstChild.innerHTML != ''){
			  textToShow = divtag.firstChild.firstChild.nodeValue;
			  flag = 'true';
		  }
		  if(divtag.firstChild.firstChild && divtag.firstChild.firstChild.nodeName == 'SPAN' 
			  && divtag.firstChild.firstChild.firstChild && divtag.firstChild.firstChild.firstChild.innerHTML != ''){
			  textToShow = divtag.firstChild.firstChild.firstChild.nodeValue;
			  flag = 'true';
		  }
		  if(flag == 'true'){
			  divtag.innerHTML='<span class="wds_oppm_util_global_message_custom" >' + textToShow + '</span>';
			  dojo.fadeOut( {
					node : divtag.firstChild,
					duration : 5000
					}).play();
		  		}
		});
}); 
function updateCompIdM(tipid,msg){
	dojo.style(tipid,"opacity","0"); // hide it 
	dojo.byId(tipid).innerHTML=msg;
	var anim1 = dojo.fadeIn({ node: tipid, duration:700 });
	anim1.play();
	var anim = dojo.animateProperty({
		node:tipid,
		duration:3000,
		properties: { 
		opacity: { start:1, end:0 }
		},
		delay:2000 // be careful of this trailing comma, it breaks IE.
	});
		dojo.removeClass(tipid,'wds_oppm_util_global_message');
		dojo.addClass(tipid,'wds_oppm_util_global_message_custom');
		anim.play();
		dojo.connect(tipid,"onEnd",function(){
			dojo.byId(tipid).innerHTML="";
		}); 
}
var toPageUrl = "";
var toPageRefreshObjectID = "";
var response = null;
function pageGo() {
	if(toPageUrl == null || toPageUrl == "" || toPageRefreshObjectID == null || toPageRefreshObjectID == "") {
		
		return false;
	}
	var pageNumber = document.forms[toPageRefreshObjectID + "PageInfoForm"]["paging.current"].value;
	var totalPageNum = dojo.byId(toPageRefreshObjectID + "totalPageNum").innerHTML;
	var curPageNum = dojo.byId(toPageRefreshObjectID + "currentPageNum").innerHTML;
	if(parseInt(totalPageNum) == 0){
		return false;
	}
	if(dojo.trim(pageNumber) == '' || isNaN(pageNumber)){
		dojoMessageBox("<fmt:message key='confirm.dialog.title.tip'/>","<fmt:message key='pageinfo.inputInvalid'/>");
		return false;
	}
	
	if(pageNumber.indexOf('.') != -1 || pageNumber.indexOf('e') != -1){
		dojoMessageBox("<fmt:message key='confirm.dialog.title.tip'/>","<fmt:message key='pageinfo.inputInvalid'/>");
		return false;
	}
	if(parseInt(pageNumber) > parseInt(totalPageNum) || parseInt(pageNumber) <= 0) {
		dojoMessageBox("<fmt:message key='confirm.dialog.title.tip'/>","<fmt:message key='pageinfo.inputInvalid'/>");
		return false;
	}
	var cells_length= dojo.byId("cells_length").value;
	var columnSort=dojo.byId("columnSort").value;
	var propSort=dojo.byId("propSort").value;
	var sortAscending=dojo.byId("sortAscending").value;
	loadGridDataAJAX(toPageRefreshObjectID + "PageInfoForm", toPageRefreshObjectID , toPageUrl,cells_length, columnSort, propSort, sortAscending);
}

function loadGridDataAJAX(formID, gridID , actionUrl,cells_length, columnSort, propSort,sortAscending){
	showProgressBar();	
	dojo.xhrPost({
		url: actionUrl,
		handleAs: "json", 
		form: formID,
		load: function(resp, ioArgs){
			response = resp.data;
			
			document.forms[gridID + "PageInfoForm"]["paging.current"].value = "";
			
			if(typeof(resp) == "string") {
				hideProgressBar();
				//dojoMessageBox("<fmt:message key='confirm.dialog.title.tip'/>","<fmt:message key='session.timeout'/>");				
				return false;
			}
			try{
				dojo.byId(gridID + "currentPageNum").value = "";
				var toPageRefreshObject = dijit.byId(gridID);
				var gridItems = {items:resp.data};
				toPageRefreshObject.setStore(new dojo.data.ItemFileWriteStore({data:gridItems}));
				dojo.byId(gridID + "totalRecord").innerHTML = resp.paging.totalRecord;
				dojo.byId(gridID + "size").innerHTML = resp.paging.size;
				dojo.byId(gridID + "currentPageNum").innerHTML = resp.paging.current;
				dojo.byId(gridID + "totalPageNum").innerHTML = resp.paging.total;
				document.forms[gridID + "PageInfoForm"]["paging.size"].value = resp.paging.size;
				document.forms[gridID + "PageInfoForm"]["paging.queryCondition"].value = resp.paging.queryCondition;
				//used when order by header
				updateHeaderView(gridID, cells_length, columnSort, propSort,sortAscending);
				hideProgressBar();
			}catch(e){
				hideProgressBar();
				//dojoMessageBox("<fmt:message key='confirm.dialog.title.tip'/>","<fmt:message key='session.timeout'/>");
			}
			return resp;
		},
		error: function(resp,sss){
			hideProgressBar();
			//dojoMessageBox("<fmt:message key='confirm.dialog.title.tip'/>","<fmt:message key='session.timeout'/>");
		}
	});
}

function toFirst(){
	var totalPageNum =dojo.byId(toPageRefreshObjectID + "totalPageNum").innerHTML; 
	document.forms[toPageRefreshObjectID + "PageInfoForm"]["paging.current"].value = "1";
	dojo.byId(toPageRefreshObjectID +"currentPageNum").value = "1";
	if(!isNaN(totalPageNum) && parseInt(totalPageNum) > 1) {
		pageGo();
	}
}	
function toPre() {
	var curPageNum = dojo.byId(toPageRefreshObjectID + "currentPageNum").innerHTML;
	if(!isNaN(curPageNum) && parseInt(curPageNum) > 1) {
		dojo.byId(toPageRefreshObjectID +"currentPageNum").value;
		document.forms[toPageRefreshObjectID + "PageInfoForm"]["paging.current"].value = parseInt(curPageNum) - 1;
		pageGo();
	}
}
function toNext() {
	var curPageNum = dojo.byId(toPageRefreshObjectID + "currentPageNum").innerHTML;
	var totalPageNum = dojo.byId(toPageRefreshObjectID + "totalPageNum").innerHTML;
	if(!isNaN(curPageNum) && parseInt(totalPageNum) > parseInt(curPageNum)) {
		document.forms[toPageRefreshObjectID + "PageInfoForm"]["paging.current"].value = parseInt(curPageNum) + 1;
		pageGo();
	}
}
function toLast() {
	var totalPageNum =dojo.byId(toPageRefreshObjectID + "totalPageNum").innerHTML; 
	if(!isNaN(totalPageNum) && parseInt(totalPageNum) > 1) {
		document.forms[toPageRefreshObjectID + "PageInfoForm"]["paging.current"].value = totalPageNum;
		pageGo();
	}
}


var systemlogsortAscending = false;
var oldColumnSort;
function updateHeaderView(gridID, cells_length, columnSort, propSort,sortAscending) { 

   	var docnObj = dojo.byId(gridID).getElementsByTagName("th");        
   	for ( var i = 0; i < cells_length; i++) {         
       	var docnObjName = layout[i].name;         
       	var ret = [ '<div class="dojoxGridSortNode' ];         
       	if (i == columnSort) {                                   
	       	 ret = ret.concat( [ 
	   				       	 ' ', 
			       	 (sortAscending == true) ? 'dojoxGridSortUp' : 'dojoxGridSortDown',
			       	 '"> <div class="dojoxGridArrowButtonChar">',                     
			    (sortAscending == true) ? '��' : '��',                     
			    '</div ><div class="dojoxGridArrowButtonNode" ></div >' ]);             
		    ret = ret.concat( [ propSort, '</div >' ]);           
	    } else {             
		    ret.push('">');             
		    ret = ret.concat( [ docnObjName, '</div >' ]);                 
	    }         
      docnObj[i].innerHTML = ret.join(" ");     
  } 
}


/**
*Show the progress bar at AJAX call.
*/
dojo.require("dijit.Dialog");
function showProgressBar() {
	var progressDlg = dijit.byId("progressDlg");
	if (!progressDlg) {
		var progressDlgContent = dojo.byId("progressDlgContent");
		if (!progressDlgContent) {
			progressDlgContent = document.createElement("div");			
			progressDlgContent.setAttribute("id", "progressDlgContent");
			document.body.appendChild(progressDlgContent);
			var progressImg = document.createElement("img");
			progressImg.setAttribute("src", "<c:url value='/imgs/ajaxLoader.gif'/>");
			progressDlgContent.appendChild(progressImg);
			//progressDlgContent.appendChild(document.createTextNode("<fmt:message key='progressbar.hint'/>"));
		}
		progressDlg = new dijit.Dialog({
			id: "progressDlg",
			duration: 10,
			"class" : "tundra"
			}, progressDlgContent);
		progressDlg.titleBar.parentNode.removeChild(progressDlg.titleBar);	//remove dialog's title pane
	}
	progressDlg.show();
}

/**
*Hide the progress bar at the end of AJAX call.
*/
function hideProgressBar() {
	var progressDlg = dijit.byId("progressDlg");
	if (progressDlg) {
		progressDlg.hide();
	}
}
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.LTrim = function() {
	return this.replace(/(^\s*)/g, "");
};
String.prototype.RTrim = function() {
	return this.replace(/(\s*$)/g, "");
};
function dojoConfirm(title,content,func) {
	var cont = "<table width='280' align='center'><tr><td align='right' valign='top' height='30' width='80'><img src='<c:url value='/images/tip.jpg'/>' width='30' height='23'/></td><td align='left' valign='middle' style='font-size:12px;'>"+content+"</td></tr>" +
	  		   "<tr><td colspan='2' align='center' valign='bottom'> <input type='button' id='yesButton' value=\"<fmt:message key='button.confirm'/>\"/>&nbsp;&nbsp;" +
	  		   "<input type='button' id='noButton' value=\"<fmt:message key='button.cancel'/>\"/></td></tr></table>";
    var cfDia = dijit.byId("confirmDialog");
	if (!cfDia) {
		var pane = dojo.byId("confirmDialog");
		pane = document.createElement("div");
		pane.setAttribute("id", "confirmDialog");
		document.body.appendChild(pane);

		cfDia = new dijit.Dialog( {
			title : title,
			"class" : "tundra"
		}, pane);
	}
	cfDia.setContent(cont);
	var yesButton = dojo.byId('yesButton');
	var noButton = dojo.byId('noButton');
	yesButton.onclick = function() {
		cfDia.hide();
		func('yes');
	};
	noButton.onclick = function() {
		cfDia.hide();
		func('no');
	};
	cfDia.show();
}
function dojoMessageBox(title,content) {
	var cont = "<table width='280' align='center' ><tr><td align='left' valign='top' height='30' width='80'><img src='<c:url value='/images/tip.gif'/>' /></td><td align='left' valign='middle' style='font-size:12px;'>"+content+"</td></tr>" +
	  		   "<tr><td colspan='2' align='center' valign='bottom'> <input type='button' id='yesButton4Message' value=\"<fmt:message key='button.confirm'/>\"/>&nbsp;&nbsp;" +"</td></tr></table>";
    var messageDialog = dijit.byId("messageDialog");
	if (!messageDialog) {
		var pane = dojo.byId("messageDialog");
		pane = document.createElement("div");
		pane.setAttribute("id", "messageDialog");
		document.body.appendChild(pane);

		messageDialog = new dijit.Dialog( {
			title : title,
			"class" : "tundra"
		}, pane);
	}
	messageDialog.setContent(cont);
	var yesButton = dojo.byId('yesButton4Message');
	yesButton.onclick = function() {
		messageDialog.hide();
	};
	messageDialog.show();
}

var Master = {
		ID:"dialog",
		confirmID:"confirm",
		showDialog:function(title,href,onDownLoad,args){
			var dialog = dijit.byId(Master.ID);
			if(!dialog){
				dialog = new dijit.Dialog({id : Master.ID,title : title,href : href,loadingMessage:'Loading...'});
			}else{
				dialog.setAttribute("title", title);
				dialog.setAttribute("loadingMessage", "Loading...");
				dialog.setHref(href);
				dialog.refresh();
			}
			dialog.onLoad = function(){
				dialog.layout();
				onDownLoad(Master.ID, args);
			};
			dialog.show();
		},
		closeDialog:function(id){
			var dialog = dijit.byId(id);
			if(dialog){
				dialog.hide();
			}
		},
		action:function(actionUrl,callBack,operateForm){
			//showProgressBar();
			dojo.xhrPost({
				url: actionUrl,
				handleAs: "json",
				form: operateForm,
				load: function(response, ioArgs){
					try{
						callBack(response);
						hideProgressBar();
					}catch(e){
						hideProgressBar();
						dojoMessageBox("<fmt:message key='confirm.dialog.title.tip'/>","<fmt:message key='session.timeout'/>");
					}
				},
				error: function(resp,sss){
					hideProgressBar();
					dojoMessageBox("<fmt:message key='confirm.dialog.title.tip'/>","<fmt:message key='session.timeout'/>");
				}
			});
		}
};