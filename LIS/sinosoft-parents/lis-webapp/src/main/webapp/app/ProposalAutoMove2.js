//ProposalAutoMove.js
var RiskType = "";

		var sqlid8="ProposalApproveSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(tContNo);//指定传入的参数
	    var strSql=mySql8.getString();	

//var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'"; 
var arrScanType = easyExecSql(strSql);
 
/**
 * 为每个界面录入控件增加相应随动的事件，在ProposalInput.js中调用 
 **/
function setFocus() {
	//alert(fm.pagename.value);
  
  for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 
  	//alert(LoadFlag);
    if (typeof(LoadFlag)!="undefined" && LoadFlag==99) {
      
      if (window.document.forms[0].elements[elementsNum].type == "text" 
          || window.document.forms[0].elements[elementsNum].type == "textarea" ) { 
        window.document.forms[0].elements[elementsNum].onclick = custom;
        window.document.forms[0].elements[elementsNum].onfocus = debugShow;  
      }
    
    }
    else {
    	//alert(LoadFlag);
        eval(" window.document.forms[0].elements["+elementsNum+"].onfocus = goToArea" +fm.pagename.value);        		
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
  
//try { if (objName == "") { goToPic(0); top.fraPic.scrollTo(237, 129); showPosition(650+hx, 235+hy, 558, 143); } } catch(e) {} 
//try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(0, 871); showPosition(80+hx, 1028+hy, 256, 72); } } catch(e) {} 
try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(0, 953); showPosition(84+hx, 999+hy, 249, 85); } } catch(e) {} 
try { if (objName == "Mult") { goToPic(0); top.fraPic.scrollTo(0, 871); showPosition(278+hx, 1022+hy, 205, -917); } } catch(e) {} 
try { if (objName == "Prem") { goToPic(0); top.fraPic.scrollTo(0, 953); showPosition(258+hx, 1061+hy, 566, 75); } } catch(e) {} 
   

}
/*** 个险随动控制函数11**/ 
function goToArea11() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "ContCardNo") { goToPic(0); top.fraPic.scrollTo(0, 52); showPosition(374+hx, 200+hy, 412, 88); } } catch(e) {} 
  try { if (objName == "PolAppntDate") { goToPic(0); top.fraPic.scrollTo(250, 1089); showPosition(852+hx, 1147+hy, 351, 97); } } catch(e) {} 
  try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(264, 66); showPosition(785+hx, 215+hy, 369, 85); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(0); top.fraPic.scrollTo(51, 1017); showPosition(332+hx, 1209+hy, 293, 80); } } catch(e) {} 
  try { if (objName == "AgentName") { goToPic(0); top.fraPic.scrollTo(51, 1017); showPosition(106+hx, 1211+hy, 245, 72); } } catch(e) {} 
  try { if (objName == "AgentManageCom") { goToPic(0); top.fraPic.scrollTo(51, 1017); showPosition(609+hx, 1211+hy, 326, 67); } } catch(e) {} 
  try { if (objName == "BranchAttr") { goToPic(0); top.fraPic.scrollTo(250, 1017); showPosition(915+hx, 1215+hy, 255, 60); } } catch(e) {} 
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(115, 468); showPosition(134+hx, 531+hy, 628, 62); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(74, 468); showPosition(297+hx, 588+hy, 188, 81); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(74, 468); showPosition(465+hx, 589+hy, 288, 82); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(115, 468); showPosition(143+hx, 556+hy, 412, 77); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(250, 494); showPosition(470+hx, 563+hy, 281, 73); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(207, 534); showPosition(455+hx, 620+hy, 298, 89); } } catch(e) {} 
  try { if (objName == "AppntOccupationType") { goToPic(0); top.fraPic.scrollTo(207, 534); showPosition(242+hx, 631+hy, 264, 78); } } catch(e) {} 
  try { if (objName == "AppntGrpName") { goToPic(0); top.fraPic.scrollTo(116, 534); showPosition(132+hx, 699+hy, 632, 76); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(183, 567); showPosition(333+hx, 741+hy, 829, 90); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(183, 567); showPosition(754+hx, 772+hy, 297, 61); } } catch(e) {} 
  try { if (objName == "AppntPhone") { goToPic(0); top.fraPic.scrollTo(207, 534); showPosition(530+hx, 673+hy, 643, 67); } } catch(e) {} 
  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(120, 656); showPosition(125+hx, 670+hy, 380, 69); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(235, 458); showPosition(731+hx, 522+hy, 450, 77); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(235, 458); showPosition(723+hx, 593+hy, 211, 86); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(235, 458); showPosition(886+hx, 597+hy, 295, 74); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(235, 458); showPosition(728+hx, 560+hy, 178, 68); } } catch(e) {} 
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(235, 458); showPosition(883+hx, 558+hy, 303, 86); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(250, 559); showPosition(887+hx, 623+hy, 298, 95); } } catch(e) {} 
  try { if (objName == "OccupationType") { goToPic(0); top.fraPic.scrollTo(250, 581); showPosition(707+hx, 625+hy, 252, 109); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(261, 538); showPosition(724+hx, 696+hy, 463, 82); } } catch(e) {} 
  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(94, 656); showPosition(102+hx, 827+hy, 188, 90); } } catch(e) {} 
  try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(94, 656); showPosition(251+hx, 817+hy, 311, 141); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(250, 702); showPosition(482+hx, 816+hy, 266, 124); } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(250, 702); showPosition(705+hx, 822+hy, 504, 117); } } catch(e) {} 
  try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(51, 821); showPosition(103+hx, 902+hy, 222, 105); } } catch(e) {} 
  try { if (objName == "Mult") { goToPic(0); top.fraPic.scrollTo(51, 821); showPosition(310+hx, 902+hy, 451, 100); } } catch(e) {} 
  

}

/*** 银行险随动控制函数15**/
function goToArea121() {
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
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(318, 448); showPosition(556+hx, 505+hy, 341, 122); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(83, 1203); showPosition(287+hx, 1254+hy, 234, 66); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(83, 1233); showPosition(459+hx, 1246+hy, 255, 83); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(431, 1219); showPosition(635+hx, 1256+hy, 251, 63); } } catch(e) {} 
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(456, 466); showPosition(863+hx, 527+hy, 302, 66); } } catch(e) {} 
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(487, 648); showPosition(852+hx, 763+hy, 322, 48); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(487, 720); showPosition(864+hx, 802+hy, 306, 48); } } catch(e) {} 
  try { if (objName == "LicenseType") { goToPic(0); top.fraPic.scrollTo(487, 779); showPosition(871+hx, 845+hy, 294, 66); } } catch(e) {} 
  
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