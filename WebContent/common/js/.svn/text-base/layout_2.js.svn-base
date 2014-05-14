/*==============================================================
 * THIS PRODUCT CONTAINS RESTRICTED MATERIALS OF IBM              *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * All Rights Reserved * Licensed Materials - Property of IBM     *
 * ============================================================   */

function hideLeftMenu(){
	content.style.backgroundPosition="-1000px";
	content.style.paddingLeft ="0px";
	$g('left').style.display='none';
}

function showLeftMenu(){
	content.style.backgroundPosition="176px";
	content.style.paddingLeft ="178px";
	$g('left').style.display='';
}

function paserStyleWidthToInt(obj){
	if(obj){
		var w = parseInt(obj.style.width.replace('px',''));
		return w;	
	}
	else{
		return 0;
	}
}

function parseStyleHeightToInt(obj){
	if(obj){
		var h = parseInt(obj.style.height.replace('px',''));
		// alert(h);
		return h;
	}
	else
	return 0;
}

function adjustFrame(theFrame){
	var _left = $g('left');
	var statusBar = $g(statusBar);
	if(theFrame){
		var _frame = $g(theFrame).getElementsByTagName('iframe')[0]; 
		var theHeight = document.body.offsetHeight;
		var theWidth = document.body.offsetWidth;
 		 
			if($gVs('mainNaviLogoBar')=='none'&& $gVs('mainNaviContentoBar')=='none' &&$gVs('footerHTML')=='none'){						
				_frame.height = theHeight - 30;					
			}else{
				_frame.height =	theHeight - 62 -30 -40-12-10-5-3;
			}
							
			if(_left.style.display!='none'){
				_frame.width =theWidth -182;
			}
			else{
				_frame.width =theWidth -2;
			}
	}
}

function getBodyHeight(doc){
  if(saas.IE) return doc.body.offsetHeight;
  else return doc.body.scrollHeight;
}

function whenIframeSrcChange(){
		//adjustFrame(obj);
		top.document.body.scrollTop =0;
}

function showStaticIframe(theURL){
	if(_staticIframe.src!=theURL){
		_staticIframe.src = theURL;
	}
	//_staticIframe.src = "main/init.jsp?theURL="+theURL;		
	 	
	adjustFrame('_sIframe');
	//document.getElementById('directContentBox').style.display='';
	_staticIframe.style.display='';
	$g('_sIframe').style.display='';
}

function hideStaticIframe(){
	_staticIframe.style.display='none';
	$g('_sIframe').style.display='none';
	document.getElementById('directContentBox').style.display='none';
}

window.onresize = function (){
	adjustFrame(getCurrentIframe());
}

function getCurrentIframe(){
	var o = document.getElementsByTagName('iframe');
	for(var i=0;i<o.length;i++){
		if(o[i].parentNode.style.display!='none'){
			return o[i].parentNode.id.toString();
		}
	}
}

var leftMenu_flag=""; 

function hideFoot(){document.getElementById('footerHTML').style.display='none';}

function showFoot(){document.getElementById('footerHTML').style.display='';}

function showHeader(){
	document.getElementById('mainNaviLogoBar').style.display='';
	document.getElementById('mainNaviContentoBar').style.display='';
	document.getElementById('mainNavigator').style.height='121px';
}
	
function hideHeader(){
	document.getElementById('mainNaviLogoBar').style.display='none';
	document.getElementById('mainNaviContentoBar').style.display='none';
	document.getElementById('mainNavigator').style.height='30px';
}

var scrollTopFlag=0;
var sh;
var doScroll=function(){
	if(scrollTopFlag==0){

	scrollTopFlag = setInterval(setScrollTop,100);
	}else{
		
		clearInterval(sh);
	}
}

var setScrollTop=function(){
	top.document.body.scrollTop=0;
}

var fullScreenMode = false;
function full(){
	if (!fullScreenMode) {
		fullScreen();
	} else {
		cancleFullScreen();
	}
}

function fullScreen(){
	leftMenu_flag=document.getElementById('left').style.display;
	hideLeftMenu();
	hideFoot();
	hideHeader();
	scrollTopFlag=0;
	doScroll();
	var oo = $g('fullScreenBtn');
	oo.getElementsByTagName('span')[0].style.display='none';
	oo.getElementsByTagName('span')[1].style.display='';
	oo.getElementsByTagName('span')[2].style.display='';
	$g('contentBody').style.paddingTop='30px';
	$g('contentBody').style.paddingBottom='0px';

	fullScreenMode=true;
	adjustFrame(getCurrentIframe());
}

function cancleFullScreen(){
	showFoot();
	scrollTopFlag=1;
	doScroll();
	
	if(leftMenu_flag!='none'){
		showLeftMenu();
	}
		$g('contentBody').style.paddingTop='122px';
	fullScreenMode=false;
	showHeader();
	var oo = $g('fullScreenBtn');
	oo.getElementsByTagName('span')[0].style.display='';
	oo.getElementsByTagName('span')[1].style.display='none';
	oo.getElementsByTagName('span')[2].style.display='none';
	adjustFrame(getCurrentIframe());
}

// log for OP: invoke servlet for log
function logAndOpen(userid, tenantid, instanceid, url_) {
	invokeLogServe(userid, tenantid, instanceid);
	var app = $g(instanceid);
	app.target = "_blank";
	app.href = url_;
}

function invokeLogServe(userid, tenantid, instanceid) {
	var oXml = createXmlHttp();
	oXml.open("GET", document.getElementById("contextPath").value + "/UserAccessLogServlet?userid=" + userid
			+ "&tenantid=" + tenantid + "&instanceid=" + instanceid, true);
	oXml.send(null);
}