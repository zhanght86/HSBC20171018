//ProposalAutoMove.js
 
/**
 * 为每个界面录入控件增加相应随动的事件，在ProposalInput.js中调用 
 **/
function setFocus() {
  //alert("4");
  for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
    if (typeof(LoadFlag)!="undefined" && LoadFlag==99) {
      if (window.document.forms[0].elements[elementsNum].type == "text" 
          || window.document.forms[0].elements[elementsNum].type == "textarea" ) { 
        window.document.forms[0].elements[elementsNum].onclick = custom;
        window.document.forms[0].elements[elementsNum].onfocus = debugShow;  
      }
    
    }
    else {
    		//alert("3");
        eval(" window.document.forms[0].elements[elementsNum].onfocus = goToArea11");        		
    }     
    

  } 
}

/** * 显示红框，将控件框起来 *****/
function showPosition(l, t, w, h) 
{
  //alert(l + " " + t + " " + w + " " + h);
  top.fraPic.spanPosition.style.display = "";
  top.fraPic.spanPosition.style.left = l;
  top.fraPic.spanPosition.style.top = t;
  top.fraPic.Rect.width = w;
  top.fraPic.Rect.height = h;  
  getvalue(w,h);
}

/** 
 * 隐藏红框
 **/
function hiddenPosition() {
  top.fraPic.spanPosition.style.display = "none";
}

var hx = 0;
var hy = 0;
var goToLock = false;

var impart = false;

function goToImpart() {
  try {
    goToPic(1); top.fraPic.scrollTo(120, 810);
    impart = false;
  }
  catch(e) {}
}
function custom() {
  try {
    objName = this.name;
    //alert(objName);
    
    try { hiddenPosition(); } catch(e) {}
    
    //top.fraPic.centerPic.innerHTML = "try { if (objName == \"\") { goToPic(1); top.fraPic.scrollTo(0, 0); showPosition(192+hx, 83+hy, 285, 115); } } catch(e) {} ";
    path = top.fraPic.centerPic.innerHTML;
    this.value = "try { if (objName == \"" + objName + path.substring(path.indexOf("=") + 4);
    //alert(path);  
  }
  catch(e) {}
}

function debugShow() {
  try {   
    objName = this.name;
    
    try { hiddenPosition(); } catch(e) {}
    
    eval(this.value);
  } catch(e) {}
}

function submitAutoMove(saveflag) {
  try {
     	if(saveflag=="11")//团体投保页面
     	{
         fm.autoMoveFlag.value ="goToArea11";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }   
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("定制完成！");
     	}
     	if(saveflag=="121")//团单第一被保人页面
     	{
         fm.autoMoveFlag.value ="goToArea121";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }    
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("定制完成！");
     	}
     	if(saveflag=="122")//团单第二被保人页面
     	{
         fm.autoMoveFlag.value ="goToArea122";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }    
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("定制完成！");
     	}
     	if(saveflag=="123")//团单第三被保人页面
     	{
         fm.autoMoveFlag.value ="goToArea123";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }    
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("定制完成！");
     	}              
     	if(saveflag=="13")//团体险种页面,不同险种对应不同页面
     	{
         fm.autoMoveFlag.value ="goToArea13"+RiskType;
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }
         
         
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("定制完成！");
     	}     	     	
     	if(saveflag=="21")
     	{
         fm.autoMoveFlag.value = "goToArea21";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("定制完成！");
        }
     	if(saveflag=="22")
     	{
         fm.autoMoveFlag.value = "goToArea22";
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("定制完成！");
        }
     	if(saveflag=="23")
     	{
         fm.autoMoveFlag.value = "goToArea23"+RiskType;
         fm.autoMoveValue.value = "";
         
         for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
           if (window.document.forms[0].elements[elementsNum].value.indexOf("try {") == 0)
             fm.autoMoveValue.value = fm.autoMoveValue.value + window.document.forms[0].elements[elementsNum].value + "|";
         }
         fm.action = "./ProposalAutoMoveCustomSubmit.jsp";
         fm.submit();
         alert("定制完成！");
        }                
  } catch(e) {}
}  
/*** 团险随动控制函数12**/

//function goToArea11() {
//  objName = this.name;
//  if (goToLock) {
//    objName = goToLock;
//    goToLock = false;
//  }
//
//  try { hiddenPosition(); } catch(e) {}
//
//  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 362); showPosition(284+hx, 431+hy, 291, 47); } } catch(e) {} 
//  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(0, 376); showPosition(280+hx, 468+hy, 297, 46); } } catch(e) {} 
//  try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(0, 376); showPosition(295+hx, 517+hy, 266, 59); } } catch(e) {} 
//  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 362); showPosition(284+hx, 431+hy, 291, 47); } } catch(e) {} 
//  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(0, 376); showPosition(280+hx, 468+hy, 297, 46); } } catch(e) {} 
//  try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(0, 376); showPosition(295+hx, 517+hy, 266, 59); } } catch(e) {} 
//  
//
//}
function goToArea13() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}
  

}

/*** 个险随动控制函数11**/ 

function goToArea11() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}
  
////////////////////////////////////////////////////////////////////

try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(175, 849); showPosition(184+hx, 951+hy, 971, 786); } } catch(e) {} 
try { if (objName == "RiskCodeName") { goToPic(0); top.fraPic.scrollTo(175, 849); showPosition(184+hx, 951+hy, 971, 786); } } catch(e) {} 

try { if (objName == "ExpPrem") { goToPic(0); top.fraPic.scrollTo(175, 849); showPosition(184+hx, 951+hy, 971, 786); } } catch(e) {} 
try { if (objName == "ExpAmnt") { goToPic(0); top.fraPic.scrollTo(175, 849); showPosition(184+hx, 951+hy, 971, 786); } } catch(e) {} 

try { if (objName == "PayIntv") { goToPic(0); top.fraPic.scrollTo(175, 849); showPosition(184+hx, 951+hy, 971, 786); } } catch(e) {} 
try { if (objName == "PayIntvName") { goToPic(0); top.fraPic.scrollTo(175, 849); showPosition(184+hx, 951+hy, 971, 786); } } catch(e) {} 

try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(175, 333); showPosition(182+hx, 352+hy, 969, 100); } } catch(e) {} 
try { if (objName == "GrpNature") { goToPic(0); top.fraPic.scrollTo(175, 355); showPosition(196+hx, 421+hy, 479, 120); } } catch(e) {} 
try { if (objName == "Asset") { goToPic(0); top.fraPic.scrollTo(175, 370); showPosition(187+hx, 467+hy, 485, 110); } } catch(e) {} 
try { if (objName == "GrpNatureName") { goToPic(0); top.fraPic.scrollTo(175, 355); showPosition(196+hx, 421+hy, 479, 120); } } catch(e) {} 

try { if (objName == "BusinessType") { goToPic(0); top.fraPic.scrollTo(263, 344); showPosition(660+hx, 436+hy, 556, 97); } } catch(e) {} 
try { if (objName == "BusinessTypeName") { goToPic(0); top.fraPic.scrollTo(263, 344); showPosition(660+hx, 436+hy, 556, 97); } } catch(e) {} 

try { if (objName == "Peoples") { goToPic(0); top.fraPic.scrollTo(263, 359); showPosition(666+hx, 465+hy, 517, 102); } } catch(e) {} 
try { if (objName == "BankAccNo1") { goToPic(0); top.fraPic.scrollTo(5, 374); showPosition(173+hx, 511+hy, 492, 94); } } catch(e) {} 

try { if (objName == "GrpAddress") { goToPic(0); top.fraPic.scrollTo(5, 389); showPosition(167+hx, 398+hy, 1043, 101); } } catch(e) {} 
try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(263, 472); showPosition(655+hx, 579+hy, 533, 97); } } catch(e) {} 
try { if (objName == "GrpZipCode") { goToPic(0); top.fraPic.scrollTo(263, 487); showPosition(659+hx, 506+hy, 547, 94); } } catch(e) {} 

try { if (objName == "LinkMan1") { goToPic(0); top.fraPic.scrollTo(0, 471); showPosition(158+hx, 548+hy, 526, 95); } } catch(e) {} 
try { if (objName == "Phone1") { goToPic(0); top.fraPic.scrollTo(0, 486); showPosition(160+hx, 582+hy, 526, 96); } } catch(e) {} 
try { if (objName == "Department1") { goToPic(0); top.fraPic.scrollTo(263, 501); showPosition(647+hx, 543+hy, 551, 108); } } catch(e) {} 

try { if (objName == "LinkMan2") { goToPic(0); top.fraPic.scrollTo(0, 471); showPosition(158+hx, 548+hy, 526, 95); } } catch(e) {} 
try { if (objName == "Phone2") { goToPic(0); top.fraPic.scrollTo(0, 486); showPosition(160+hx, 582+hy, 526, 96); } } catch(e) {} 
try { if (objName == "Department2") { goToPic(0); top.fraPic.scrollTo(263, 501); showPosition(647+hx, 543+hy, 551, 108); } } catch(e) {} 

try { if (objName == "GetFlag") { goToPic(1); top.fraPic.scrollTo(150, 827); showPosition(172+hx, 946+hy, 951, 122); } } catch(e) {} 
try { if (objName == "GetFlagName") { goToPic(1); top.fraPic.scrollTo(150, 827); showPosition(172+hx, 946+hy, 951, 122); } } catch(e) {} 

try { if (objName == "AgentCode") { goToPic(3); top.fraPic.scrollTo(115, 1327); showPosition(165+hx, 1387+hy, 251, 236); } } catch(e) {} 
try { if (objName == "AgentName") { goToPic(3); top.fraPic.scrollTo(115, 1378); showPosition(350+hx, 1404+hy, 352, 228); } } catch(e) {} 
try { if (objName == "BranchAttr") { goToPic(3); top.fraPic.scrollTo(115, 1393); showPosition(632+hx, 1407+hy, 303, 220); } } catch(e) {} 

try { if (objName == "PolApplyDate") { goToPic(0); top.fraPic.scrollTo(0, 588); showPosition(134+hx, 686+hy, 570, 96); } } catch(e) {} 
try { if (objName == "Remark") { goToPic(3); top.fraPic.scrollTo(0, 927); showPosition(125+hx, 962+hy, 839, 229); } } catch(e) {} 
try { if (objName == "CValiDate") { goToPic(3); top.fraPic.scrollTo(261, 1028); showPosition(755+hx, 1163+hy, 442, 94); } } catch(e) {} 
try { if (objName == "PrtNo") { goToPic(0); top.fraPic.scrollTo(261, 151); showPosition(716+hx, 208+hy, 454, 144); } } catch(e) {} 
}
/*** 银行险随动控制函数15**/
function goToArea121() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

}
function goToArea122() {
	//alert("ok");//第二被保人
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

 
}
function goToArea22() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

 

}
 //获取控件的值，并显示出来
function getvalue(w,h)
{
try
{
	if(LoadFlag=="4"||LoadFlag=="5")
	{
	top.fraPic.RectCont.style.display ="";	
	top.fraPic.RectCont.width=w;
	top.fraPic.RectCont.height=h;  
	}
	top.fraPic.RectCont.value=eval("fm."+document.activeElement.name+"Name.value"); 
	top.fraPic.RectCont.value=document.activeElement.value+"—"+top.fraPic.RectCont.value; 
}
catch(ex)
{	
	if(LoadFlag=="4"||LoadFlag=="5")
	{
	top.fraPic.RectCont.style.display ="";	
	top.fraPic.RectCont.width=w;
	top.fraPic.RectCont.height=h;  
	} 
	top.fraPic.RectCont.value=document.activeElement.value;
} 
}