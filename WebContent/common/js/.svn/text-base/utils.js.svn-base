/*==============================================================
 * THIS PRODUCT CONTAINS RESTRICTED MATERIALS OF IBM              *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * All Rights Reserved * Licensed Materials - Property of IBM     *
 * ============================================================   */

function changeParam(paramName){			
	if(paramName == null)
		return;

	var conditions = dojo.byId("searchConditions").getElementsByTagName("div");
	var amount = conditions.length;
	var i = 0;
	for(i=0; i < amount; i ++){
		conditions[i].style.display= "none";
	}
	var toDisplayDiv = dojo.byId("div_param_" + paramName);
	toDisplayDiv.style.display = "block";		
		
}

function handChangeSubMenu(root,j){
	var dds = root.getElementsByTagName('dd');
	for ( var i = 0; i < dds.length; i++) {
		dds[i].style.fontWeight = 'normal';
		dds[i].style.backgroundPosition = "200% 200%";
		dds[i].style.color = "#525252";
		if (i == j) {
			dds[i].style.backgroundColor="#ebebeb";
			//dds[i].style.backgroundImage = _mMenuMouseOverBg ;
		}else{
			dds[i].style.backgroundColor="white";
			//dds[i].style.backgroundImage = _mMenuDefaultBg ;
		}
	}
	dds[j].style.fontWeight = 'bold';
	dds[j].style.backgroundPosition = "10% 55%";			
}
			