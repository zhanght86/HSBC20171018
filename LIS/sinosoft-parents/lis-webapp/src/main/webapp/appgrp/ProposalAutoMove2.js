//ProposalAutoMove.js
var RiskType = "";
var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'"; 
var arrScanType = easyExecSql(strSql);
 
/**
 * 为每个界面录入控件增加相应随动的事件，在ProposalInput.js中调用 
 **/
function setFocus() {
  //alert(fm.pagename.value);
  for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
    if (typeof(LoadFlag)!="undefined" && LoadFlag==99) {
      if (window.document.forms[0].elements[elementsNum].type == "text" 
          || window.document.forms[0].elements[elementsNum].type == "textarea" ) { 
        window.document.forms[0].elements[elementsNum].onclick = custom;
        window.document.forms[0].elements[elementsNum].onfocus = debugShow;  
      }
    
    }
    else {
        eval(" window.document.forms[0].elements[elementsNum].onfocus = goToArea" +fm.pagename.value);        		
    }     
    

  } 
}

/** 
 * 显示红框，将控件框起来 
 **/
function showPosition(l, t, w, h) {
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
//	alert(saveflag);
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

function goToArea13() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(0, 1413); showPosition(67+hx, 1454+hy, 148, 194); } } catch(e) {} 
  try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(0, 1413); showPosition(163+hx, 1444+hy, 192, 212); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(0, 1413); showPosition(294+hx, 1443+hy, 249, 211); } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(0, 1413); showPosition(639+hx, 1434+hy, 252, 223); } } catch(e) {} 
  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(239, 1413); showPosition(834+hx, 1439+hy, 199, 214); } } catch(e) {} 
  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(239, 1413); showPosition(984+hx, 1445+hy, 205, 209); } } catch(e) {} 
  try { if (objName == "InsuYear") { goToPic(1); top.fraPic.scrollTo(0, 208); showPosition(358+hx, 227+hy, 183, 273); } } catch(e) {} 
  try { if (objName == "PayIntv") { goToPic(1); top.fraPic.scrollTo(0, 382); showPosition(88+hx, 492+hy, 848, 87); } } catch(e) {} 
  try { if (objName == "PayEndYear") { goToPic(1); top.fraPic.scrollTo(0, 432); showPosition(78+hx, 528+hy, 1033, 114); } } catch(e) {} 
  try { if (objName == "GetYear") { goToPic(1); top.fraPic.scrollTo(174, 315); showPosition(607+hx, 573+hy, 402, 136); } } catch(e) {} 
  try { if (objName == "GetDutyKind") { goToPic(1); top.fraPic.scrollTo(0, 557); showPosition(74+hx, 601+hy, 1094, 98); } } catch(e) {} 
  try { if (objName == "Mult") { goToPic(1); top.fraPic.scrollTo(115, 200); showPosition(627+hx, 220+hy, 186, 293); } } catch(e) {} 
  try { if (objName == "Amnt") { goToPic(1); top.fraPic.scrollTo(115, 200); showPosition(623+hx, 222+hy, 188, 273); } } catch(e) {} 
  try { if (objName == "Prem") { goToPic(1); top.fraPic.scrollTo(164, 224); showPosition(768+hx, 232+hy, 175, 270); } } catch(e) {} 
  try { if (objName == "SpecGrid3") { goToPic(3); top.fraPic.scrollTo(0, 133); showPosition(81+hx, 175+hy, 1117, 155); } } catch(e) {} 
  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(0, 1413); showPosition(67+hx, 1454+hy, 148, 194); } } catch(e) {} 
  try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(0, 1413); showPosition(163+hx, 1444+hy, 192, 212); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(0, 1413); showPosition(294+hx, 1443+hy, 249, 211); } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(0, 1413); showPosition(639+hx, 1434+hy, 252, 223); } } catch(e) {} 
  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(239, 1413); showPosition(834+hx, 1439+hy, 199, 214); } } catch(e) {} 
  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(239, 1413); showPosition(984+hx, 1445+hy, 205, 209); } } catch(e) {} 
  try { if (objName == "InsuYear") { goToPic(1); top.fraPic.scrollTo(0, 208); showPosition(358+hx, 227+hy, 183, 273); } } catch(e) {} 
  try { if (objName == "PayIntv") { goToPic(1); top.fraPic.scrollTo(0, 382); showPosition(88+hx, 492+hy, 848, 87); } } catch(e) {} 
  try { if (objName == "PayEndYear") { goToPic(1); top.fraPic.scrollTo(0, 432); showPosition(78+hx, 528+hy, 1033, 114); } } catch(e) {} 
  try { if (objName == "GetYear") { goToPic(1); top.fraPic.scrollTo(174, 315); showPosition(607+hx, 573+hy, 402, 136); } } catch(e) {} 
  try { if (objName == "GetDutyKind") { goToPic(1); top.fraPic.scrollTo(0, 557); showPosition(74+hx, 601+hy, 1094, 98); } } catch(e) {} 
  try { if (objName == "Mult") { goToPic(1); top.fraPic.scrollTo(115, 200); showPosition(627+hx, 220+hy, 186, 293); } } catch(e) {} 
  try { if (objName == "Amnt") { goToPic(1); top.fraPic.scrollTo(115, 200); showPosition(623+hx, 222+hy, 188, 273); } } catch(e) {} 
  try { if (objName == "Prem") { goToPic(1); top.fraPic.scrollTo(164, 224); showPosition(768+hx, 232+hy, 175, 270); } } catch(e) {} 
  try { if (objName == "SpecGrid3") { goToPic(3); top.fraPic.scrollTo(0, 133); showPosition(81+hx, 175+hy, 1117, 155); } } catch(e) {} 
  try { if (objName == "RiskCode") { goToPic(1); top.fraPic.scrollTo(0, 166); showPosition(217+hx, 213+hy, 178, 286); } } catch(e) {} 

  
  

}
/*** 个险随动控制函数11**/ 
function goToArea11() {
  objName = this.name;
	//alert(objName);
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "ReportNo") { goToPic(0); top.fraPic.scrollTo(0, 0); showPosition(289+hx, 121+hy, 266, 74); } } catch(e) {} 
  try { if (objName == "ReportNo") { goToPic(0); top.fraPic.scrollTo(0, 0); showPosition(289+hx, 121+hy, 266, 74); } } catch(e) {} 
  

}
/*** 银行险随动控制函数15**/
function goToArea121() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "ReportNo") { goToPic(0); top.fraPic.scrollTo(0, 0); showPosition(289+hx, 121+hy, 266, 74); } } catch(e) {} 
  try { if (objName == "ReportNo") { goToPic(0); top.fraPic.scrollTo(0, 0); showPosition(289+hx, 121+hy, 266, 74); } } catch(e) {} 
  
}
function goToArea122() {
	//alert("ok");//第二被保人
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "RelationToMainInsured") { goToPic(0); top.fraPic.scrollTo(487, 905); showPosition(846+hx, 934+hy, 359, 91); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(463, 365); showPosition(846+hx, 435+hy, 346, 75); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(463, 439); showPosition(845+hx, 471+hy, 350, 84); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(463, 589); showPosition(853+hx, 647+hy, 340, 120); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(463, 679); showPosition(854+hx, 721+hy, 344, 87); } } catch(e) {} 
  try { if (objName == "OccupationType") { goToPic(0); top.fraPic.scrollTo(463, 709); showPosition(847+hx, 788+hy, 349, 85); } } catch(e) {} 
  try { if (objName == "InsuredAppAge") { goToPic(0); top.fraPic.scrollTo(463, 515); showPosition(851+hx, 573+hy, 345, 109); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(487, 935); showPosition(854+hx, 967+hy, 349, 82); } } catch(e) {} 
  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(487, 935); showPosition(849+hx, 1005+hy, 360, 62); } } catch(e) {} 
  try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(487, 979); showPosition(847+hx, 1040+hy, 349, 65); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(487, 1039); showPosition(856+hx, 1078+hy, 339, 60); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(487, 1085); showPosition(855+hx, 1115+hy, 335, 128); } } catch(e) {} 
  try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(487, 1145); showPosition(845+hx, 1196+hy, 350, 80); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(65, 1175); showPosition(246+hx, 1232+hy, 907, 120); } } catch(e) {} 
  try { if (objName == "ZipCode") { goToPic(0); top.fraPic.scrollTo(0, 1175); showPosition(79+hx, 1330+hy, 1008, 112); } } catch(e) {} 
  try { if (objName == "RelationToMainInsured") { goToPic(0); top.fraPic.scrollTo(487, 905); showPosition(846+hx, 934+hy, 359, 91); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(463, 365); showPosition(846+hx, 435+hy, 346, 75); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(463, 439); showPosition(845+hx, 471+hy, 350, 84); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(463, 589); showPosition(853+hx, 647+hy, 340, 120); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(463, 679); showPosition(854+hx, 721+hy, 344, 87); } } catch(e) {} 
  try { if (objName == "OccupationType") { goToPic(0); top.fraPic.scrollTo(463, 709); showPosition(847+hx, 788+hy, 349, 85); } } catch(e) {} 
  try { if (objName == "InsuredAppAge") { goToPic(0); top.fraPic.scrollTo(463, 515); showPosition(851+hx, 573+hy, 345, 109); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(487, 935); showPosition(854+hx, 967+hy, 349, 82); } } catch(e) {} 
  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(487, 935); showPosition(849+hx, 1005+hy, 360, 62); } } catch(e) {} 
  try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(487, 979); showPosition(847+hx, 1040+hy, 349, 65); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(487, 1039); showPosition(856+hx, 1078+hy, 339, 60); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(487, 1085); showPosition(855+hx, 1115+hy, 335, 128); } } catch(e) {} 
  try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(487, 1145); showPosition(845+hx, 1196+hy, 350, 80); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(65, 1175); showPosition(246+hx, 1232+hy, 907, 120); } } catch(e) {} 
  try { if (objName == "ZipCode") { goToPic(0); top.fraPic.scrollTo(0, 1175); showPosition(79+hx, 1330+hy, 1008, 112); } } catch(e) {} 
   
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(83, 1203); showPosition(287+hx, 1254+hy, 234, 66); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(83, 1233); showPosition(459+hx, 1246+hy, 255, 83); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(431, 1219); showPosition(635+hx, 1256+hy, 251, 63); } } catch(e) {} 
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(456, 466); showPosition(863+hx, 527+hy, 302, 66); } } catch(e) {} 
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(487, 648); showPosition(852+hx, 763+hy, 322, 48); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(487, 720); showPosition(864+hx, 802+hy, 306, 48); } } catch(e) {} 
  try { if (objName == "LicenseType") { goToPic(0); top.fraPic.scrollTo(487, 779); showPosition(871+hx, 845+hy, 294, 66); } } catch(e) {} 
  
}
function goToArea22() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "ImpartGrid2") { goToPic(1); top.fraPic.scrollTo(65, 950); showPosition(110+hx, 959+hy, 696, 239); } } catch(e) {} 
  try { if (objName == "ImpartGrid2") { goToPic(1); top.fraPic.scrollTo(65, 950); showPosition(110+hx, 959+hy, 696, 239); } } catch(e) {} 
  

}

function goToArea21() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }
//alert(344);
  try { hiddenPosition(); } catch(e) {}

  //try { if (objName == "AgentType") { goToPic(2); top.fraPic.scrollTo(0, 252); showPosition(99+hx, 346+hy, 266, 190); } } catch(e) {} 
  //try { if (objName == "PolApplyDate") { goToPic(0); top.fraPic.scrollTo(65, 817); showPosition(79+hx, 882+hy, 682, 74); } } catch(e) {} 
  //try { if (objName == "CValiDate") { goToPic(1); top.fraPic.scrollTo(65, 817); showPosition(77+hx, 919+hy, 971, 84); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(0); top.fraPic.scrollTo(250, 252); showPosition(1200+hx, 441+hy, 280, 50); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(20, 1100); showPosition(100+hx, 1190+hy, 920, 90); } } catch(e) {} 
  try { if (objName == "GrpNature") { goToPic(0); top.fraPic.scrollTo(60, 1140); showPosition(123+hx, 1290+hy, 1300, 60); } } catch(e) {} 
  try { if (objName == "BusinessType") { goToPic(0); top.fraPic.scrollTo(0, 1180); showPosition(123+hx, 1330+hy, 350, 80); } } catch(e) {} 
  try { if (objName == "Peoples") { goToPic(0); top.fraPic.scrollTo(0, 1180); showPosition(590+hx, 1330+hy, 350, 80); } } catch(e) {} 
  try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(50, 1300); showPosition(680+hx, 1460+hy, 350, 80); } } catch(e) {} 
  //try { if (objName == "Corporation") { goToPic(0); top.fraPic.scrollTo(0, 258); showPosition(65+hx, 331+hy, 384, 140); } } catch(e) {} 
  try { if (objName == "GrpAddress") { goToPic(0); top.fraPic.scrollTo(20, 1140); showPosition(140+hx, 1230+hy, 920, 80); } } catch(e) {} 
  try { if (objName == "GrpZipCode") { goToPic(0); top.fraPic.scrollTo(290, 1140); showPosition(1100+hx, 1230+hy, 478, 82); } } catch(e) {} 
  try { if (objName == "LinkMan1") { goToPic(0); top.fraPic.scrollTo(0, 1360); showPosition(74+hx, 1410+hy, 507, 93); } } catch(e) {} 
  try { if (objName == "Phone1") { goToPic(0); top.fraPic.scrollTo(500, 1360); showPosition(1000+hx, 1410+hy, 418, 78); } } catch(e) {} 
  try { if (objName == "E_Mail1") { goToPic(0); top.fraPic.scrollTo(20, 1400); showPosition(250+hx, 1450+hy, 450, 92); } } catch(e) {} 
  try { if (objName == "Department1") { goToPic(0); top.fraPic.scrollTo(222, 1360); showPosition(578+hx, 1410+hy, 396, 101); } } catch(e) {} 
  try { if (objName == "LinkMan2") { goToPic(0); top.fraPic.scrollTo(0, 1440); showPosition(74+hx, 1490+hy, 507, 93); } } catch(e) {} 
  try { if (objName == "Phone2") { goToPic(0); top.fraPic.scrollTo(500, 1440); showPosition(1000+hx, 1490+hy, 418, 78); } } catch(e) {} 
  try { if (objName == "E_Mail2") { goToPic(0); top.fraPic.scrollTo(20, 1480); showPosition(250+hx, 1530+hy, 450, 92); } } catch(e) {} 
  try { if (objName == "Department2") { goToPic(0); top.fraPic.scrollTo(222, 1440); showPosition(578+hx, 1490+hy, 396, 101); } } catch(e) {} 
  try { if (objName == "GetFlag") { goToPic(0); top.fraPic.scrollTo(0, 1480); showPosition(100+hx, 1590+hy, 906, 82); } } catch(e) {} 
  try { if (objName == "BankCode") { goToPic(0); top.fraPic.scrollTo(0, 1520); showPosition(250+hx, 1630+hy, 453, 97); } } catch(e) {} 
  try { if (objName == "BankAccNo") { goToPic(0); top.fraPic.scrollTo(0, 1520); showPosition(650+hx, 1630+hy, 659, 89); } } catch(e) {} 
  //try { if (objName == "Remark") { goToPic(1); top.fraPic.scrollTo(0, 86); showPosition(74+hx, 150+hy, 734, 265); } } catch(e) {} 
  try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(0, 1760); showPosition(180+hx, 1771+hy, 386, 300); } } catch(e) {} 
  try { if (objName == "PayIntv") { goToPic(1); top.fraPic.scrollTo(65, 300); showPosition(350+hx, 460+hy, 1000, 80); } } catch(e) {} 
  //try { if (objName == "AgentType") { goToPic(2); top.fraPic.scrollTo(0, 252); showPosition(99+hx, 346+hy, 266, 190); } } catch(e) {} 
  try { if (objName == "PolApplyDate") { goToPic(1); top.fraPic.scrollTo(100, 100); showPosition(100+hx, 130+hy, 1000, 84); } } catch(e) {} 
  try { if (objName == "CValiDate") { goToPic(1); top.fraPic.scrollTo(120, 100); showPosition(430+hx, 170+hy, 1000, 84); } } catch(e) {} 
  //try { if (objName == "AgentCode") { goToPic(2); top.fraPic.scrollTo(0, 252); showPosition(499+hx, 351+hy, 380, 182); } } catch(e) {} 
  //try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(0, 129); showPosition(83+hx, 215+hy, 735, 90); } } catch(e) {} 
  //try { if (objName == "GrpNature") { goToPic(0); top.fraPic.scrollTo(254, 172); showPosition(752+hx, 313+hy, 438, 80); } } catch(e) {} 
  //try { if (objName == "BusinessType") { goToPic(0); top.fraPic.scrollTo(0, 258); showPosition(396+hx, 346+hy, 565, 121); } } catch(e) {} 
  //try { if (objName == "Corporation") { goToPic(0); top.fraPic.scrollTo(0, 258); showPosition(65+hx, 331+hy, 384, 140); } } catch(e) {} 
  //try { if (objName == "GrpAddress") { goToPic(0); top.fraPic.scrollTo(0, 172); showPosition(72+hx, 256+hy, 761, 99); } } catch(e) {} 
  //try { if (objName == "GrpZipCode") { goToPic(0); top.fraPic.scrollTo(254, 172); showPosition(744+hx, 265+hy, 478, 82); } } catch(e) {} 
  //try { if (objName == "LinkMan1") { goToPic(0); top.fraPic.scrollTo(0, 473); showPosition(74+hx, 533+hy, 507, 93); } } catch(e) {} 
  //try { if (objName == "Phone1") { goToPic(0); top.fraPic.scrollTo(222, 473); showPosition(787+hx, 535+hy, 418, 78); } } catch(e) {} 
  //try { if (objName == "E_Mail1") { goToPic(0); top.fraPic.scrollTo(0, 473); showPosition(81+hx, 573+hy, 450, 92); } } catch(e) {} 
  //try { if (objName == "Department1") { goToPic(0); top.fraPic.scrollTo(222, 473); showPosition(458+hx, 530+hy, 396, 101); } } catch(e) {} 
  //try { if (objName == "GetFlag") { goToPic(0); top.fraPic.scrollTo(0, 559); showPosition(72+hx, 685+hy, 906, 82); } } catch(e) {} 
  //try { if (objName == "BankCode") { goToPic(0); top.fraPic.scrollTo(0, 516); showPosition(58+hx, 644+hy, 453, 97); } } catch(e) {} 
  //try { if (objName == "BankAccNo") { goToPic(0); top.fraPic.scrollTo(0, 516); showPosition(444+hx, 649+hy, 419, 89); } } catch(e) {} 
  //try { if (objName == "Remark") { goToPic(1); top.fraPic.scrollTo(0, 86); showPosition(74+hx, 150+hy, 734, 265); } } catch(e) {} 
  //try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(0, 1204); showPosition(80+hx, 1241+hy, 326, 243); } } catch(e) {} 
  //try { if (objName == "PayIntv") { goToPic(0); top.fraPic.scrollTo(65, 602); showPosition(86+hx, 762+hy, 954, 90); } } catch(e) {} 
}



function   getvalue(w,h)
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