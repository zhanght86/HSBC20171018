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
	//alert(saveflag);
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

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(0, 82); showPosition(56+hx, 174+hy, 744, 106); } } catch(e) {} 
  try { if (objName == "GrpNature") { goToPic(0); top.fraPic.scrollTo(247, 205); showPosition(752+hx, 285+hy, 422, 78); } } catch(e) {} 
  try { if (objName == "BusinessType") { goToPic(0); top.fraPic.scrollTo(0, 205); showPosition(388+hx, 276+hy, 411, 83); } } catch(e) {} 
  try { if (objName == "Peoples") { goToPic(0); top.fraPic.scrollTo(0, 205); showPosition(63+hx, 279+hy, 378, 75); } } catch(e) {} 
  try { if (objName == "Corporation") { goToPic(0); top.fraPic.scrollTo(0, 246); showPosition(65+hx, 310+hy, 383, 130); } } catch(e) {} 
  try { if (objName == "GrpAddress") { goToPic(0); top.fraPic.scrollTo(0, 123); showPosition(64+hx, 238+hy, 749, 81); } } catch(e) {} 
  try { if (objName == "GrpZipCode") { goToPic(0); top.fraPic.scrollTo(247, 123); showPosition(747+hx, 243+hy, 456, 77); } } catch(e) {} 
  try { if (objName == "LinkMan1") { goToPic(0); top.fraPic.scrollTo(0, 410); showPosition(63+hx, 499+hy, 444, 85); } } catch(e) {} 
  try { if (objName == "Phone1") { goToPic(0); top.fraPic.scrollTo(247, 410); showPosition(794+hx, 509+hy, 379, 79); } } catch(e) {} 
  try { if (objName == "E_Mail1") { goToPic(0); top.fraPic.scrollTo(0, 410); showPosition(68+hx, 545+hy, 429, 72); } } catch(e) {} 
  try { if (objName == "Department1") { goToPic(0); top.fraPic.scrollTo(0, 410); showPosition(445+hx, 507+hy, 402, 78); } } catch(e) {} 
  try { if (objName == "GetFlag") { goToPic(0); top.fraPic.scrollTo(0, 492); showPosition(51+hx, 656+hy, 902, 67); } } catch(e) {} 
  try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(0, 821); showPosition(63+hx, 886+hy, 478, 202); } } catch(e) {} 
  try { if (objName == "PayIntv") { goToPic(0); top.fraPic.scrollTo(0, 574); showPosition(57+hx, 727+hy, 752, 74); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(0, 82); showPosition(56+hx, 174+hy, 744, 106); } } catch(e) {} 
  try { if (objName == "GrpNature") { goToPic(0); top.fraPic.scrollTo(247, 205); showPosition(752+hx, 285+hy, 422, 78); } } catch(e) {} 
  try { if (objName == "BusinessType") { goToPic(0); top.fraPic.scrollTo(0, 205); showPosition(388+hx, 276+hy, 411, 83); } } catch(e) {} 
  try { if (objName == "Peoples") { goToPic(0); top.fraPic.scrollTo(0, 205); showPosition(63+hx, 279+hy, 378, 75); } } catch(e) {} 
  try { if (objName == "Corporation") { goToPic(0); top.fraPic.scrollTo(0, 246); showPosition(65+hx, 310+hy, 383, 130); } } catch(e) {} 
  try { if (objName == "GrpAddress") { goToPic(0); top.fraPic.scrollTo(0, 123); showPosition(64+hx, 238+hy, 749, 81); } } catch(e) {} 
  try { if (objName == "GrpZipCode") { goToPic(0); top.fraPic.scrollTo(247, 123); showPosition(747+hx, 243+hy, 456, 77); } } catch(e) {} 
  try { if (objName == "LinkMan1") { goToPic(0); top.fraPic.scrollTo(0, 410); showPosition(63+hx, 499+hy, 444, 85); } } catch(e) {} 
  try { if (objName == "Phone1") { goToPic(0); top.fraPic.scrollTo(247, 410); showPosition(794+hx, 509+hy, 379, 79); } } catch(e) {} 
  try { if (objName == "E_Mail1") { goToPic(0); top.fraPic.scrollTo(0, 410); showPosition(68+hx, 545+hy, 429, 72); } } catch(e) {} 
  try { if (objName == "Department1") { goToPic(0); top.fraPic.scrollTo(0, 410); showPosition(445+hx, 507+hy, 402, 78); } } catch(e) {} 
  try { if (objName == "GetFlag") { goToPic(0); top.fraPic.scrollTo(0, 492); showPosition(51+hx, 656+hy, 902, 67); } } catch(e) {} 
  try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(0, 821); showPosition(63+hx, 886+hy, 478, 202); } } catch(e) {} 
  try { if (objName == "PayIntv") { goToPic(0); top.fraPic.scrollTo(0, 574); showPosition(57+hx, 727+hy, 752, 74); } } catch(e) {} 
  try { if (objName == "Remark") { goToPic(2); top.fraPic.scrollTo(0, 41); showPosition(52+hx, 135+hy, 1100, 400); } } catch(e) {} 
  try { if (objName == "PolApplyDate") { goToPic(0); top.fraPic.scrollTo(114, 656); showPosition(144+hx, 829+hy, 929, 77); } } catch(e) {} 
  try { if (objName == "PolApplyDate") { goToPic(2); top.fraPic.scrollTo(114, 1507); showPosition(639+hx, 1569+hy, 450, 162); } } catch(e) {} 
  try { if (objName == "CValiDate") { goToPic(2); top.fraPic.scrollTo(215, 1466); showPosition(662+hx, 1623+hy, 442, 89); } } catch(e) {} 
  try { if (objName == "AgentCom") { goToPic(3); top.fraPic.scrollTo(59, 125); showPosition(426+hx, 250+hy, 445, 112); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(3); top.fraPic.scrollTo(0, 248); showPosition(106+hx, 364+hy, 782, 101); } } catch(e) {} 
  try { if (objName == "AgentType") { goToPic(3); top.fraPic.scrollTo(0, 291); showPosition(59+hx, 375+hy, 305, 183); } } catch(e) {} 
  try { if (objName == "CValiDate") { goToPic(0); top.fraPic.scrollTo(140, 744); showPosition(181+hx, 819+hy, 930, 92); } } catch(e) {} 
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