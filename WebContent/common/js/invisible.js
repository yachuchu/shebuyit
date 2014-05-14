/*log level */
var logLevel = 0;

/*
 * applet will call this function to set some value or show some information on
 * web page
 */
function calledByApplet(tpe, cmd, vle) {
	/**/
}
/* call functions inside of applet */
function callFunctionsInApplet(tpe, cmd, vle) {
	document.getElementById("MobikeyApplet").calledByJavaScript(tpe, cmd, vle);
}

/* log method */
function log(level, message) {
	if (level >= logLevel)
		alert(message);
}

function mobikeyStartAuthentication(pblock) {
	/*
	 * // Called by the applet when the pin block is ready.
	 * document.getElementById("pinBlock").value = pinBlock;
	 * document.getElementById("form1").submit();
	 */
	//
	// dojo style ajax
	dojo.xhrGet( {
		url : "<c:url value='/login/usblogin.do'/>",
		handleAs : "text",
		timeout : 5000,
		load : loginCallBack,
		err : function(response, ioArgs) {
			alert("err.");
		},
		content : {
			pinBlock : pblock
		}
	});
}

function replaceDefaultAdmin(admin) {
	var mobikey = document.getElementById("MobikeyApplet");
	var res = mobikey.replaceDefaultAdmin(admin);
	if ("ok" == res) {
		alert("Default Admin card is being replaced for security reason. Please login again.");
		window.location = "<c:url value='/login/usbview.do'/>";
	}
	else
		alert("Replacing default admin failed." + res);
}

function loginCallBack(txt) {
	if (txt == "ok") {
		window.location = "<c:url value='/main/main.do'/>";
	} else {
		var res = txt.split(":")
		if (res[0] == "rep") {
			// replace admin card
			var newadmin = res[1];
			replaceDefaultAdmin(newadmin);
		} else if (res[0] == "err") {
			window.location = "error.jsp?msg=" + res[1];
		}
		return;
	}
}
