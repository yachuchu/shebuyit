function hiddenSuccessBox() {	
	$("#successBox").slideUp("normal");
} 

function hiddenErrorBox() {
	$("#errorBox").slideUp("normal");
} 


function changeLanguage(language) {
	dojo.xhrGet({   
   		 url: "<c:url value='/login/changeLanguage.do?language="+language+"'/>",  
   		 handleAs: "text",
   		 preventCache: true, 
   		 load: function(resp){  
   			if(resp == "000" || resp==000){
   			   window.location.reload();
   			}else{
   				document.getElementById("errorBoxMesg").innerHTML=resp;
   				document.getElementById("errorBox").style.display = "block";
   			}		            
   		 } 		   
	});				
}
