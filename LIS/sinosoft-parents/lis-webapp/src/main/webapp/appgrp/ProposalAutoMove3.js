//ProposalAutoMove.js
var RiskType = "";
var sqlresourcename = "appgrp.ProposalAutoMove3Sql";
var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'"; 


		var sqlid1="ProposalAutoMove3Sql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara("1");
		

var arrScanType = easyExecSql(mySql1.getString());
 
/**
 * 为每个界面录入控件增加相应随动的事件，在ProposalInput.js中调用 
 **/
function setFocus() {
  
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
function goToArea11() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(263, 91); showPosition(588+hx, 121+hy, 639, 204); } } catch(e) {} 
  try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(263, 91); showPosition(588+hx, 121+hy, 639, 204); } } catch(e) {} 
  try { if (objName == "PolAppntDate") { goToPic(0); top.fraPic.scrollTo(474, 1445); showPosition(893+hx, 1548+hy, 270, 85); } } catch(e) {} 
  try { if (objName == "ManageCom") { goToPic(0); top.fraPic.scrollTo(486, 1565); showPosition(777+hx, 1650+hy, 381, 63); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(0); top.fraPic.scrollTo(233, 1579); showPosition(451+hx, 1656+hy, 323, 78); } } catch(e) {} 
  try { if (objName == "AgentName") { goToPic(0); top.fraPic.scrollTo(37, 1565); showPosition(164+hx, 1651+hy, 306, 59); } } catch(e) {} 
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 257); showPosition(49+hx, 301+hy, 252, 92); } } catch(e) {} 
//  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(39, 241); showPosition(76+hx, 350+hy, 306, 105); } } catch(e) {} 
//  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(39, 241); showPosition(297+hx, 340+hy, 653, 101); } } catch(e) {} 
//  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(39, 241); showPosition(305+hx, 280+hy, 257, 96); } } catch(e) {} 
//  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(229, 241); showPosition(878+hx, 386+hy, 329, 82); } } catch(e) {} 
//  try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(263, 91); showPosition(588+hx, 121+hy, 639, 204); } } catch(e) {} 
//  try { if (objName == "AgentBankCode") { goToPic(0); top.fraPic.scrollTo(263, 1538); showPosition(954+hx, 1575+hy, 274, 118); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(39, 241); showPosition(76+hx, 350+hy, 306, 105); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(39, 241); showPosition(297+hx, 340+hy, 653, 101); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(39, 241); showPosition(305+hx, 280+hy, 257, 96); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(229, 241); showPosition(878+hx, 386+hy, 329, 82); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(7, 319); showPosition(68+hx, 411+hy, 516, 50); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(364, 333); showPosition(580+hx, 409+hy, 278, 47); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(0, 234); showPosition(278+hx, 335+hy, 411, 54); } } catch(e) {} 
  try { if (objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(467, 248); showPosition(987+hx, 329+hy, 169, 56); } } catch(e) {} 
  try { if (objName == "AppntBankCode") { goToPic(0); top.fraPic.scrollTo(0, 743); showPosition(306+hx, 833+hy, 262, 46); } } catch(e) {} 
  try { if (objName == "AppntAccName") { goToPic(0); top.fraPic.scrollTo(0, 743); showPosition(80+hx, 835+hy, 249, 66); } } catch(e) {} 
  try { if (objName == "AppntBankAccNo") { goToPic(0); top.fraPic.scrollTo(433, 743); showPosition(645+hx, 820+hy, 497, 54); } } catch(e) {} 
  try { if (objName == "Remark") { goToPic(0); top.fraPic.scrollTo(0, 884); showPosition(67+hx, 981+hy, 480, 54); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 399); showPosition(41+hx, 477+hy, 270, 86); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(0, 413); showPosition(267+hx, 475+hy, 186, 46); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(459, 413); showPosition(851+hx, 530+hy, 296, 60); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(0, 455); showPosition(65+hx, 544+hy, 511, 77); } } catch(e) {} 
  try { if (objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(331, 469); showPosition(576+hx, 543+hy, 286, 43); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(459, 413); showPosition(851+hx, 462+hy, 294, 58); } } catch(e) {} 
  try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(474, 125); showPosition(692+hx, 162+hy, 474, 130); } } catch(e) {} 
  try { if (objName == "PolAppntDate") { goToPic(0); top.fraPic.scrollTo(474, 1445); showPosition(893+hx, 1548+hy, 270, 85); } } catch(e) {} 
  try { if (objName == "ManageCom") { goToPic(0); top.fraPic.scrollTo(486, 1565); showPosition(777+hx, 1650+hy, 381, 63); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(0); top.fraPic.scrollTo(233, 1579); showPosition(451+hx, 1656+hy, 323, 78); } } catch(e) {} 
  try { if (objName == "AgentName") { goToPic(0); top.fraPic.scrollTo(37, 1565); showPosition(164+hx, 1651+hy, 306, 59); } } catch(e) {} 
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(0, 257); showPosition(49+hx, 301+hy, 252, 92); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(8, 273); showPosition(67+hx, 375+hy, 228, 77); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(255, 259); showPosition(280+hx, 372+hy, 568, 51); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(0, 271); showPosition(272+hx, 294+hy, 217, 64); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(452, 273); showPosition(857+hx, 399+hy, 302, 58); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(7, 319); showPosition(68+hx, 411+hy, 516, 50); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(364, 333); showPosition(580+hx, 409+hy, 278, 47); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(0, 234); showPosition(278+hx, 335+hy, 411, 54); } } catch(e) {} 
  try { if (objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(467, 248); showPosition(987+hx, 329+hy, 169, 56); } } catch(e) {} 
  try { if (objName == "AppntBankCode") { goToPic(0); top.fraPic.scrollTo(0, 743); showPosition(306+hx, 833+hy, 262, 46); } } catch(e) {} 
  try { if (objName == "AppntAccName") { goToPic(0); top.fraPic.scrollTo(0, 743); showPosition(80+hx, 835+hy, 249, 66); } } catch(e) {} 
  try { if (objName == "AppntBankAccNo") { goToPic(0); top.fraPic.scrollTo(433, 743); showPosition(645+hx, 820+hy, 497, 54); } } catch(e) {} 
  try { if (objName == "Remark") { goToPic(0); top.fraPic.scrollTo(0, 884); showPosition(67+hx, 981+hy, 480, 54); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 399); showPosition(41+hx, 477+hy, 270, 86); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(0, 413); showPosition(267+hx, 475+hy, 186, 46); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(459, 413); showPosition(851+hx, 530+hy, 296, 60); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(0, 455); showPosition(65+hx, 544+hy, 511, 77); } } catch(e) {} 
  try { if (objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(331, 469); showPosition(576+hx, 543+hy, 286, 43); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(459, 413); showPosition(851+hx, 462+hy, 294, 58); } } catch(e) {} 
  try { if (objName == "AppntProvince") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(69+hx, 401+hy, 447, 86); } } catch(e) {} 
  try { if (objName == "AppntCity") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(68+hx, 410+hy, 478, 49); } } catch(e) {} 
  try { if (objName == "AppntDistrict") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(59+hx, 409+hy, 492, 52); } } catch(e) {} 
  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(467, 248); showPosition(968+hx, 287+hy, 187, 61); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(0, 448); showPosition(275+hx, 507+hy, 450, 79); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(194, 448); showPosition(273+hx, 506+hy, 599, 72); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(0, 450); showPosition(69+hx, 538+hy, 485, 65); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(0, 464); showPosition(66+hx, 540+hy, 520, 58); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(0, 464); showPosition(69+hx, 545+hy, 478, 49); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(486, 464); showPosition(975+hx, 468+hy, 188, 48); } } catch(e) {} 
  try { if (objName == "AppntProvince") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(69+hx, 401+hy, 447, 86); } } catch(e) {} 
  try { if (objName == "AppntCity") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(68+hx, 410+hy, 478, 49); } } catch(e) {} 
  try { if (objName == "AppntDistrict") { goToPic(0); top.fraPic.scrollTo(0, 320); showPosition(59+hx, 409+hy, 492, 52); } } catch(e) {} 
  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(467, 248); showPosition(968+hx, 287+hy, 187, 61); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(0, 448); showPosition(275+hx, 507+hy, 450, 79); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(194, 448); showPosition(273+hx, 506+hy, 599, 72); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(0, 450); showPosition(69+hx, 538+hy, 485, 65); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(0, 464); showPosition(66+hx, 540+hy, 520, 58); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(0, 464); showPosition(69+hx, 545+hy, 478, 49); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(486, 464); showPosition(975+hx, 468+hy, 188, 48); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(146, 281); showPosition(468+hx, 300+hy, 389, 71); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(146, 281); showPosition(468+hx, 300+hy, 389, 71); } } catch(e) {} 
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(146, 382); showPosition(429+hx, 474+hy, 365, 62); } } catch(e) {} 
  try { if (objName == "BankCode1") { goToPic(0); top.fraPic.scrollTo(0, 1579); showPosition(54+hx, 1623+hy, 414, 44); } } catch(e) {} 
  try { if (objName == "CounterCode") { goToPic(0); top.fraPic.scrollTo(229, 1579); showPosition(443+hx, 1622+hy, 421, 65); } } catch(e) {} 
  try { if (objName == "AgentBankCode") { goToPic(0); top.fraPic.scrollTo(263, 1538); showPosition(954+hx, 1575+hy, 274, 118); } } catch(e) {} 
  try { if (objName == "AgentBankCode") { goToPic(0); top.fraPic.scrollTo(263, 1538); showPosition(954+hx, 1575+hy, 274, 118); } } catch(e) {} 
  try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(0, 569); showPosition(109+hx, 577+hy, 254, 231); } } catch(e) {} 
}
function goToArea13() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(0, 528); showPosition(103+hx, 576+hy, 255, 201); } } catch(e) {} 
try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(75, 775); showPosition(117+hx, 862+hy, 199, 128); } } catch(e) {} 
try { if (objName == "BnfGrid3") { goToPic(0); top.fraPic.scrollTo(208, 775); showPosition(832+hx, 861+hy, 169, 116); } } catch(e) {} 
try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(263, 764); showPosition(988+hx, 852+hy, 218, 129); } } catch(e) {} 
try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(208, 775); showPosition(648+hx, 863+hy, 196, 117); } } catch(e) {} 
try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(75, 775); showPosition(442+hx, 858+hy, 138, 128); } } catch(e) {} 
try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(75, 775); showPosition(300+hx, 865+hy, 157, 120); } } catch(e) {} 
try { if (objName == "InsuYear") { goToPic(0); top.fraPic.scrollTo(0, 528); showPosition(325+hx, 582+hy, 234, 177); } } catch(e) {} 
try { if (objName == "PayIntv") { goToPic(0); top.fraPic.scrollTo(237, 693); showPosition(686+hx, 781+hy, 523, 77); } } catch(e) {} 
try { if (objName == "PayEndYear") { goToPic(0); top.fraPic.scrollTo(263, 528); showPosition(957+hx, 577+hy, 236, 198); } } catch(e) {} 
try { if (objName == "GetYear") { goToPic(0); top.fraPic.scrollTo(0, 705); showPosition(503+hx, 794+hy, 205, 41); } } catch(e) {} 
try { if (objName == "GetDutyKind") { goToPic(0); top.fraPic.scrollTo(0, 705); showPosition(107+hx, 790+hy, 401, 47); } } catch(e) {} 
try { if (objName == "Mult") { goToPic(0); top.fraPic.scrollTo(0, 528); showPosition(528+hx, 576+hy, 143, 195); } } catch(e) {} 
try { if (objName == "Amnt") { goToPic(0); top.fraPic.scrollTo(0, 528); showPosition(528+hx, 577+hy, 156, 201); } } catch(e) {} 
try { if (objName == "Prem") { goToPic(0); top.fraPic.scrollTo(141, 528); showPosition(807+hx, 571+hy, 169, 201); } } catch(e) {} 

try { if (objName == "SpecGrid3") { goToPic(0); top.fraPic.scrollTo(43, 805); showPosition(106+hx, 954+hy, 986, 70); } } catch(e) {} 
}

