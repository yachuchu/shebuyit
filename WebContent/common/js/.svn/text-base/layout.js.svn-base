
/************************************
 * Global vars
 ************************************/
var g_pageContextPath = $g('contextPath').value;
var g_oXml;

var content = $g('contentBody');
/***************************************
 * Tools funcs
 ***************************************/
function $g(idStr) {
	return document.getElementById(idStr);
}

function $gVs(idStr) {
	return $g(idStr).style.display;
}

//#LEFT_MENU===========
var _leftMenu = $g('leftMenu');

//#MAIN_CONTENT========
var _staticArea = $g('staticArea');
var _staticIframe = $g('staticIframe');

//#STATIC_LINKS========
var _help
var _logout

//#style and tags Constant
var _DD = "dd";
var _DT = "dt";
var _DL = "dl";

var _IFRAME_MIN_HEIGHT = '600px';
var _IFRAME_MAX_HEIGHT = '800px';
var _FULL_PERCENT = '100%';

//#coreFunctions======
/**************************************************************
 * Create xmlHTTPRequest OBJECT [createXmlHttp]
 * @return 
 **************************************************************/
createXmlHttp = function() {
	if (typeof XMLHttpRequest != "undefined") {
		return new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		var aVersion = [ "MSXML2.XMLHttp.5.0", "MSXML2.XMLHttp.4.0",
				"MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp", "Microsoft.XMLHttp" ];
		for ( var i = 0; i < aVersion.length; i++) {
			try {
				var oXmlHttp = new ActiveXObject(aVersion[i]);
				return oXmlHttp;
			} catch (oError) {
			}
		}
	}
	throw new Error("Can not create the XMLHTTP OBJECT");
}

//get User Function Catalog As XML
function getUserXMLCatalog() {
	var id = Math.random();
	var oXml = createXmlHttp();
	oXml.open("GET", document.getElementById("contextPath").value+ "/main/menu.do?tem= "+Math.random(), false);
	oXml.onreadystatechange = function() {
		if (oXml.readyState == 4) {		
			if (oXml.status == 200) {
				
				$g("leftMenu").innerHTML = oXml.responseText;				
				//initUser();
				resetLeftMenu(0);
				handClickMainMenu(0);
				handClickSubMenu(0);
			}
		}
	}
	oXml.send(null);
}

function showFrame(){
	var saas = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? saas.IE = s[1] : (s = ua
					.match(/firefox\/([\d.]+)/)) ? saas.FF = s[1] : (s = ua
					.match(/chrome\/([\d.]+)/)) ? saas.CHROME = s[1] : (s = ua
					.match(/opera.([\d.]+)/)) ? saas.OPERA = s[1] : (s = ua
					.match(/version\/([\d.]+).*safari/)) ? saas.safari = s[1] : 0;
           		
	if (saas.FF) {
		document.getElementById("staticArea").style.paddingTop = "114px";
		document.getElementById("left").style.width = "171px";
	}  
}

function initUser() {	
	_staticIframe.src = '/token/seed/create.do';
	document.getElementById("staticIframe").style.width = "100%";
	hideLeftMenu();	
	showStaticIframe(_staticIframe.src);
	showLeftMenu();
	adjustFrame(getCurrentIframe());
}

function XMLMenuTOHTMLTag(xmlObj) {
	$g("leftMenu").innerHTML = xmlObj.responseText;
	resetLeftMenu(0);
}

// *********************************************LEFT MENU
var _bgPosMainShow = "2% 55%";
var _bgPosMainHide = "200% 200%"
var _bgPosSubShow = "10% 55%";
var _bgPosSubHide = "200% 200%";

//var _mMenuMouseOverBgColor = "#b5b5b5";
//var _mMenuDefaultBgColor = "#dedede";
var _mMenuMouseOverBg = "url('../images/menu_Click_176.jpg')";
var _mMenuDefaultBg = "url('../images/menu_Normal_176.jpg')";

var _currentMainMenuIndex = 0;
var _currentSubMenuIndex = 0;

function resetLeftMenu(mode) {
	var root = $g('leftMenu');
	var dts = root.getElementsByTagName('dt');
	var dds = root.getElementsByTagName('dd');
	
	for ( var i = 0; i < dts.length; i++) {
		dts[i].style.backgroundPosition = _bgPosMainHide;
		dts[i].style.fontWeight = 'normal';
		dts[i].style.fontSize = '12';
		dts[i].style.color = "black";
		dts[i].style.paddingTop = "1px";
		
		if (mode == 0) {
			dts[i].onclick = function(event) {
				clickMainMenu(this, event);
			}
		}
	}
	for ( var j = 0; j < dds.length; j++) {
		dds[j].style.display = 'none';
		dds[j].style.fontWeight = 'normal';
		dds[j].style.fontSize = '11';
		dds[j].style.backgroundPosition = _bgPosSubHide;
		dds[j].style.backgroundColor = "white";
		dds[j].style.borderColor = "#dedede";
		dds[j].style.borderWidth = "1px";
		dds[j].style.borderStyle = "solid";
		dds[j].style.color = "#525252";		
		if (mode == 0) {
			dds[j].onclick = function(event) {
				clickSubMenu(this, event);
			}
		}
	}
}

function clickMainMenu(obj, evt) {
 
	var dds = obj.parentNode.getElementsByTagName("dd");
	var dls = obj.parentNode.parentNode.getElementsByTagName("dl");
	var root = $g('leftMenu');
	var dts = root.getElementsByTagName('dt');

	if (dds[0].style.display != 'none') {
		resetLeftMenu(1);
	} else {
		resetLeftMenu(1);
		obj.style.fontWeight = 'bold';
		obj.style.backgroundPosition = _bgPosMainShow;
		for ( var i = 0; i < dls.length; i++) {
			if (dls[i] == obj.parentNode) {
				_currentMainMenuIndex = i;				
			}
		}
		
		for ( var i = 0; i < dds.length; i++) {

			dds[i].style.backgroundPosition = _bgPosSubHide;
			dds[i].style.fontWeight = 'normal';
			dds[i].style.display = 'block';
			dds[i].style.color = "#525252";
		}
	}
	
	var index = _currentMainMenuIndex
	for(var i = 0 ; i < dts.length ;i++){		
		if(index == i){
			dts[i].style.backgroundImage = _mMenuMouseOverBg ;
			dts[i].style.color = "white";
		}else{
			dts[i].style.backgroundImage = _mMenuDefaultBg ;
			dts[i].style.color = "black";
		}
	}
}

function clickSubMenu(obj, evt) {	
	
	var dds = obj.parentNode.getElementsByTagName('dd');
    
	for ( var i = 0; i < dds.length; i++) {
		dds[i].style.fontWeight = 'normal';
		dds[i].style.backgroundPosition = _bgPosSubHide;
		dds[i].style.color = "#525252";
		if (dds[i] == obj) {
			_currentSubMenuIndex = i;
			dds[i].style.backgroundColor="#ebebeb";
			//dds[i].style.backgroundImage = _mMenuMouseOverBg ;
		}else{
			dds[i].style.backgroundColor="white";
			//dds[i].style.backgroundImage = _mMenuDefaultBg ;
		}
	}
	obj.style.fontWeight = 'bold';
	obj.style.backgroundPosition = _bgPosSubShow;
	
	
	var url = obj.getElementsByTagName('input')[1].value; 
	_staticIframe.src=url;
	showStaticIframe(_staticIframe.src);
}

function handClickMainMenu(i){
	var root = $g('leftMenu');
	var dts = root.getElementsByTagName('dt');
	clickMainMenu(dts[i]);
}

function handClickSubMenu(i){
	var root = $g('leftMenu');
	var dds = root.getElementsByTagName('dd');
	clickSubMenu(dds[i]);
}

window.onload = function() {	
	getUserXMLCatalog();
	showFrame();
}