//ProposalAutoMove.js
var RiskType = "";

		var sqlid46="ContPolinputSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql46.setSqlId(sqlid46);//指定使用的Sql的id
		mySql46.addSubPara('1');//指定传入的参数
	    var strSql=mySql46.getString();	

//var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'"; 
var arrScanType = easyExecSql(strSql);

var SubType="";

		var sqlid47="ContPolinputSql47";
		var mySql47=new SqlClass();
		mySql47.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql47.setSqlId(sqlid47);//指定使用的Sql的id
		mySql47.addSubPara(prtNo);//指定传入的参数
	    var SubTypeSql=mySql47.getString();	

//var SubTypeSql = "select subtype from es_doc_main where busstype='TB' and subtype in('UA001','UA015') and doccode='"+prtNo+"' "; 
var SubTypeArr = easyExecSql(SubTypeSql);
if(SubTypeArr!=null){SubType=SubTypeArr[0][0];}

 
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
  try {//alert(92);
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
  
  if(SubType=='UA015')
  {
		//险种信息
		try { if (objName == "RiskCode") { goToPic(1); top.fraPic.scrollTo(0, 101); showPosition(146+hx, 120+hy, 354, 270); } } catch(e) {}
		try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(0, 1395); showPosition(53+hx, 1447+hy, 221, 236); } } catch(e) {}
		try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(39, 1424); showPosition(168+hx, 1448+hy, 192, 235); } } catch(e) {}
		try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(39, 1439); showPosition(279+hx, 1446+hy, 425, 239); } } catch(e) {}
		try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(233, 1411); showPosition(742+hx, 1443+hy, 259, 240); } } catch(e) {}
		try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(233, 1426); showPosition(891+hx, 1438+hy, 216, 251); } } catch(e) {}
		try { if (objName == "BnfGrid8") { goToPic(0); top.fraPic.scrollTo(263, 1398); showPosition(992+hx, 1440+hy, 250, 238); } } catch(e) {}
		try { if (objName == "BnfGrid11") { goToPic(0); top.fraPic.scrollTo(213, 1413); showPosition(611+hx, 1442+hy, 236, 240); } } catch(e) {}
		try { if (objName == "Amnt") { goToPic(1); top.fraPic.scrollTo(213, 101); showPosition(605+hx, 112+hy, 233, 269); } } catch(e) {}
		try { if (objName == "Prem") { goToPic(1); top.fraPic.scrollTo(213, 116); showPosition(893+hx, 123+hy, 299, 254); } } catch(e) {}
  	try { if (objName == "Mult") { goToPic(1); top.fraPic.scrollTo(80, 81); showPosition(626+hx, 104+hy, 219, 290); } } catch(e) {} 
  	try { if (objName == "InsuYear") { goToPic(1); top.fraPic.scrollTo(0, 96); showPosition(416+hx, 114+hy, 138, 281); } } catch(e) {} 
  	try { if (objName == "PayIntv") { goToPic(1); top.fraPic.scrollTo(52, 278); showPosition(81+hx, 389+hy, 1049, 60); } } catch(e) {} 
  	try { if (objName == "PayEndYear") { goToPic(1); top.fraPic.scrollTo(52, 293); showPosition(74+hx, 436+hy, 1113, 76); } } catch(e) {} 
  	try { if (objName == "GetDutyKind") { goToPic(1); top.fraPic.scrollTo(52, 439); showPosition(69+hx, 494+hy, 1122, 64); } } catch(e) {} 
  }
	else
	{
	  try { if (objName == "RiskCode") { goToPic(1); top.fraPic.scrollTo(102, 102); showPosition(189+hx, 121+hy, 271, 390); } } catch(e) {} 
	  try { if (objName == "InsuYear") { goToPic(1); top.fraPic.scrollTo(102, 111); showPosition(399+hx, 129+hy, 172, 377); } } catch(e) {} 
	  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(-2, 1460); showPosition(77+hx, 1464+hy, 179, 238); } } catch(e) {} 
	  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(-2, 1444); showPosition(195+hx, 1450+hy, 166, 277); } } catch(e) {} 
	  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(-2, 1444); showPosition(303+hx, 1455+hy, 355, 269); } } catch(e) {} 
	  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(-2, 1444); showPosition(749+hx, 1458+hy, 218, 269); } } catch(e) {} 
	  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(180, 1444); showPosition(914+hx, 1456+hy, 170, 273); } } catch(e) {} 
	  try { if (objName == "BnfGrid8") { goToPic(0); top.fraPic.scrollTo(265, 1444); showPosition(1028+hx, 1462+hy, 174, 263); } } catch(e) {} 
	  try { if (objName == "BnfGrid11") { goToPic(0); top.fraPic.scrollTo(222, 1441); showPosition(598+hx, 1451+hy, 192, 271); } } catch(e) {} 
	  try { if (objName == "PayIntv") { goToPic(1); top.fraPic.scrollTo(0, 375); showPosition(187+hx, 405+hy, 735, 184); } } catch(e) {} 
	  try { if (objName == "PayEndYear") { goToPic(1); top.fraPic.scrollTo(56, 437); showPosition(202+hx, 446+hy, 829, 231); } } catch(e) {} 
	  try { if (objName == "GetYear") { goToPic(1); top.fraPic.scrollTo(0, 444); showPosition(639+hx, 497+hy, 320, 122); } } catch(e) {} 
	  try { if (objName == "GetDutyKind") { goToPic(1); top.fraPic.scrollTo(0, 444); showPosition(208+hx, 495+hy, 510, 128); } } catch(e) {} 
	  try { if (objName == "Mult") { goToPic(1); top.fraPic.scrollTo(102, 85); showPosition(627+hx, 135+hy, 176, 370); } } catch(e) {} 
	  try { if (objName == "Amnt") { goToPic(1); top.fraPic.scrollTo(102, 85); showPosition(627+hx, 135+hy, 176, 370); } } catch(e) {} 
	  try { if (objName == "Prem") { goToPic(1); top.fraPic.scrollTo(249, 85); showPosition(890+hx, 134+hy, 312, 376); } } catch(e) {} 
	  //try { if (objName == "SpecGrid3") { goToPic(3); top.fraPic.scrollTo(44, 128); showPosition(88+hx, 191+hy, 1106, 155); } } catch(e) {} 
	}
}

/*** 个险随动控制函数11**/ 

function goToArea11() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }
alert(279);
  try { hiddenPosition(); } catch(e) {}
  if(SubType=='UA015')
  {
		try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(263, 183); showPosition(710+hx, 281+hy, 534, 114); } } catch(e) {}
		try { if (objName == "PolAppntDate") { goToPic(3); top.fraPic.scrollTo(262, 1331); showPosition(543+hx, 1383+hy, 702, 145); } } catch(e) {}
		try { if (objName == "ManageCom") { goToPic(3); top.fraPic.scrollTo(0, 1429); showPosition(28+hx, 1558+hy, 517, 74); } } catch(e) {}
		try { if (objName == "AgentCode") { goToPic(3); top.fraPic.scrollTo(0, 1444); showPosition(400+hx, 1463+hy, 460, 85); } } catch(e) {}
		try { if (objName == "AgentName") { goToPic(3); top.fraPic.scrollTo(0, 1444); showPosition(34+hx, 1448+hy, 476, 100); } } catch(e) {}
		try { if (objName == "BranchAttr") { goToPic(3); top.fraPic.scrollTo(263, 1390); showPosition(420+hx, 1560+hy, 822, 71); } } catch(e) {}
		try { if (objName == "RelationShip") { goToPic(3); top.fraPic.scrollTo(188, 1448); showPosition(270+hx, 1600+hy, 896, 69); } } catch(e) {}
		try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(156, 269); showPosition(191+hx, 381+hy, 465, 109); } } catch(e) {}
		try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(50, 551); showPosition(221+hx, 626+hy, 398, 133); } } catch(e) {}
		try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(50, 566); showPosition(206+hx, 704+hy, 409, 90); } } catch(e) {}
		try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(50, 345); showPosition(225+hx, 435+hy, 377, 96); } } catch(e) {}
		try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(50, 468); showPosition(219+hx, 566+hy, 406, 67); } } catch(e) {}
		try { if (objName == "AppntAge") { goToPic(0); top.fraPic.scrollTo(50, 468); showPosition(218+hx, 601+hy, 390, 73); } } catch(e) {}
		try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(50, 483); showPosition(224+hx, 492+hy, 385, 101); } } catch(e) {}
		try { if (objName == "AppntNativePlace") { goToPic(0); top.fraPic.scrollTo(50, 606); showPosition(192+hx, 754+hy, 430, 64); } } catch(e) {}
		try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(50, 765); showPosition(220+hx, 861+hy, 420, 71); } } catch(e) {}
		try { if (objName == "AppntLicenseType") { goToPic(0); top.fraPic.scrollTo(50, 780); showPosition(200+hx, 910+hy, 417, 99); } } catch(e) {}
		try { if (objName == "AppntProvince") { goToPic(0); top.fraPic.scrollTo(50, 1205); showPosition(197+hx, 1259+hy, 312, 66); } } catch(e) {}
		try { if (objName == "AppntCity") { goToPic(0); top.fraPic.scrollTo(50, 1220); showPosition(409+hx, 1265+hy, 318, 63); } } catch(e) {}
		try { if (objName == "AppntDistrict") { goToPic(0); top.fraPic.scrollTo(258, 1235); showPosition(593+hx, 1258+hy, 617, 61); } } catch(e) {}
		try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(222, 1212); showPosition(248+hx, 1312+hy, 944, 66); } } catch(e) {}
		try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(0, 1270); showPosition(214+hx, 1352+hy, 314, 76); } } catch(e) {}
		try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(0, 925); showPosition(237+hx, 975+hy, 370, 72); } } catch(e) {}
		try { if (objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(0, 940); showPosition(211+hx, 1015+hy, 425, 77); } } catch(e) {}
		try { if (objName == "AppntFax") { goToPic(0); top.fraPic.scrollTo(0, 955); showPosition(222+hx, 1050+hy, 402, 73); } } catch(e) {}
		try { if (objName == "AppntHomePhone") { goToPic(0); top.fraPic.scrollTo(0, 970); showPosition(208+hx, 1093+hy, 418, 65); } } catch(e) {}
		try { if (objName == "AppntGrpName") { goToPic(0); top.fraPic.scrollTo(0, 1086); showPosition(197+hx, 1129+hy, 436, 127); } } catch(e) {}
		try { if (objName == "AppntEMail") { goToPic(0); top.fraPic.scrollTo(0, 1101); showPosition(201+hx, 1212+hy, 432, 84); } } catch(e) {}
		try { if (objName == "PayMode") { goToPic(1); top.fraPic.scrollTo(152, 455); showPosition(171+hx, 525+hy, 964, 140); } } catch(e) {}
		try { if (objName == "AppntBankCode") { goToPic(1); top.fraPic.scrollTo(152, 470); showPosition(490+hx, 575+hy, 637, 54); } } catch(e) {}
		try { if (objName == "AppntAccName") { goToPic(1); top.fraPic.scrollTo(152, 485); showPosition(284+hx, 568+hy, 228, 53); } } catch(e) {}
		try { if (objName == "AppntBankAccNo") { goToPic(1); top.fraPic.scrollTo(152, 500); showPosition(257+hx, 596+hy, 723, 63); } } catch(e) {}
		try { if (objName == "SecPayMode") { goToPic(1); top.fraPic.scrollTo(158, 515); showPosition(180+hx, 614+hy, 961, 161); } } catch(e) {}
		try { if (objName == "SecAppntBankCode") { goToPic(1); top.fraPic.scrollTo(158, 530); showPosition(498+hx, 674+hy, 636, 68); } } catch(e) {}
		try { if (objName == "SecAppntAccName") { goToPic(1); top.fraPic.scrollTo(158, 545); showPosition(288+hx, 682+hy, 224, 46); } } catch(e) {}
		try { if (objName == "SecAppntBankAccNo") { goToPic(1); top.fraPic.scrollTo(158, 560); showPosition(265+hx, 703+hy, 689, 61); } } catch(e) {}
		try { if (objName == "Income0") { goToPic(1); top.fraPic.scrollTo(63, 791); showPosition(118+hx, 915+hy, 367, 47); } } catch(e) {}
		try { if (objName == "IncomeWay0") { goToPic(1); top.fraPic.scrollTo(63, 806); showPosition(433+hx, 916+hy, 542, 44); } } catch(e) {}
		try { if (objName == "AppntStature") { goToPic(2); top.fraPic.scrollTo(173, 97); showPosition(538+hx, 139+hy, 274, 81); } } catch(e) {}
		try { if (objName == "AppntAvoirdupois") { goToPic(2); top.fraPic.scrollTo(173, 112); showPosition(759+hx, 140+hy, 286, 84); } } catch(e) {}
		try { if (objName == "appnthealimpart") { goToPic(2); top.fraPic.scrollTo(72, 81); showPosition(224+hx, 178+hy, 109, 20); } } catch(e) {} 
		try { if (objName == "appntfinaimpart") { goToPic(1); top.fraPic.scrollTo(72, 857); showPosition(98+hx, 910+hy, -2, 6); } } catch(e) {} 
				
		try { if (objName == "Remark") { goToPic(3); top.fraPic.scrollTo(16, 41); showPosition(20+hx, 68+hy, 1220, 169); } } catch(e) {}
		try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(16, 654); showPosition(517+hx, 785+hy, 404, 71); } } catch(e) {}
		try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(16, 281); showPosition(526+hx, 415+hy, 399, 65); } } catch(e) {}
		try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(16, 554); showPosition(506+hx, 640+hy, 434, 107); } } catch(e) {}
		try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(16, 569); showPosition(531+hx, 708+hy, 367, 68); } } catch(e) {}
		try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(16, 411); showPosition(535+hx, 458+hy, 385, 58); } } catch(e) {}
		try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(16, 426); showPosition(550+hx, 557+hy, 356, 74); } } catch(e) {}
		try { if (objName == "InsuredAppAge") { goToPic(0); top.fraPic.scrollTo(16, 469); showPosition(534+hx, 600+hy, 376, 64); } } catch(e) {}
		try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(16, 412); showPosition(526+hx, 488+hy, 393, 110); } } catch(e) {}
		try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(16, 668); showPosition(540+hx, 749+hy, 364, 67); } } catch(e) {}
		try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(16, 683); showPosition(516+hx, 871+hy, 417, 62); } } catch(e) {}
		try { if (objName == "LicenseType") { goToPic(0); top.fraPic.scrollTo(16, 827); showPosition(530+hx, 898+hy, 377, 110); } } catch(e) {}
		try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(16, 1186); showPosition(221+hx, 1257+hy, 296, 67); } } catch(e) {}
		try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(16, 1201); showPosition(392+hx, 1257+hy, 355, 69); } } catch(e) {}
		try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(262, 1216); showPosition(600+hx, 1261+hy, 600, 59); } } catch(e) {}
		try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(197, 1231); showPosition(244+hx, 1317+hy, 931, 55); } } catch(e) {}
		try { if (objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(82, 1246); showPosition(218+hx, 1342+hy, 289, 91); } } catch(e) {}
		try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(124, 922); showPosition(517+hx, 972+hy, 415, 81); } } catch(e) {}
		try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(124, 937); showPosition(503+hx, 1022+hy, 460, 52); } } catch(e) {}
		try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(124, 952); showPosition(511+hx, 1055+hy, 426, 65); } } catch(e) {}
		try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(124, 1003); showPosition(513+hx, 1096+hy, 409, 58); } } catch(e) {}
		try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(124, 1018); showPosition(513+hx, 1131+hy, 424, 122); } } catch(e) {}
		try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(124, 1162); showPosition(524+hx, 1211+hy, 411, 78); } } catch(e) {}
		try { if (objName == "Income") { goToPic(1); top.fraPic.scrollTo(23, 832); showPosition(181+hx, 944+hy, 377, 56); } } catch(e) {}
		try { if (objName == "IncomeWay") { goToPic(1); top.fraPic.scrollTo(23, 847); showPosition(466+hx, 952+hy, 518, 43); } } catch(e) {}
		try { if (objName == "Stature") { goToPic(2); top.fraPic.scrollTo(23, 77); showPosition(79+hx, 122+hy, 296, 111); } } catch(e) {}
		try { if (objName == "Avoirdupois") { goToPic(2); top.fraPic.scrollTo(23, 92); showPosition(289+hx, 123+hy, 291, 105); } } catch(e) {}
  		try { if (objName == "insuhealimpart") { goToPic(2); top.fraPic.scrollTo(72, 81); showPosition(224+hx, 178+hy, 109, 20); } } catch(e) {} 
		try { if (objName == "insufinaimpart") { goToPic(1); top.fraPic.scrollTo(72, 857); showPosition(98+hx, 910+hy, -2, 6); } } catch(e) {} 
		
		try { if (objName == "BnfButton") { goToPic(0); top.fraPic.scrollTo(10, 1447); showPosition(709+hx, 1731+hy, -641, -298); } } catch(e) {} 
		try { if (objName == "RiskButton") { goToPic(1); top.fraPic.scrollTo(10, 80); showPosition(626+hx, 369+hy, -437, -272); } } catch(e) {} 

		
		try { if (objName == "RiskCode") { goToPic(1); top.fraPic.scrollTo(0, 101); showPosition(146+hx, 120+hy, 354, 270); } } catch(e) {}
	
  }
	else
	{
	  //投保人
	  try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(263, 201); showPosition(762+hx, 290+hy, 477, 143); } } catch(e) {} 
	  try { if (objName == "PolAppntDate") { goToPic(3); top.fraPic.scrollTo(202, 1339); showPosition(780+hx, 1423+hy, 448, 115); } } catch(e) {} 
	  try { if (objName == "ManageCom") { goToPic(3); top.fraPic.scrollTo(0, 1467); showPosition(67+hx, 1569+hy, 494, 89); } } catch(e) {} 
	  try { if (objName == "AgentCode") { goToPic(3); top.fraPic.scrollTo(0, 1416); showPosition(432+hx, 1465+hy, 468, 110); } } catch(e) {} 
	  try { if (objName == "AgentName") { goToPic(3); top.fraPic.scrollTo(0, 1416); showPosition(62+hx, 1471+hy, 494, 105); } } catch(e) {} 
	  try { if (objName == "BranchAttr") { goToPic(3); top.fraPic.scrollTo(263, 1478); showPosition(463+hx, 1569+hy, 779, 78); } } catch(e) {} 
	  try { if (objName == "RelationShip") { goToPic(3); top.fraPic.scrollTo(68, 1478); showPosition(314+hx, 1613+hy, 732, 103); } } catch(e) {} 
	  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(68, 340); showPosition(256+hx, 407+hy, 387, 109); } } catch(e) {} 
	  try { if (objName == "AppntAge") { goToPic(0); top.fraPic.scrollTo(68, 531); showPosition(232+hx, 544+hy, 440, 163); } } catch(e) {} 
	  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(68, 531); showPosition(232+hx, 544+hy, 440, 163); } } catch(e) {} 
	  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(68, 539); showPosition(224+hx, 619+hy, 419, 156); } } catch(e) {} 
	  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(68, 639); showPosition(228+hx, 696+hy, 421, 123); } } catch(e) {} 
	  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(68, 357); showPosition(244+hx, 443+hy, 412, 102); } } catch(e) {} 
	  try { if (objName == "AppntMarriage") { goToPic(0); top.fraPic.scrollTo(68, 431); showPosition(242+hx, 494+hy, 392, 123); } } catch(e) {} 
	  try { if (objName == "AppntNativePlace") { goToPic(0); top.fraPic.scrollTo(68, 639); showPosition(252+hx, 749+hy, 377, 93); } } catch(e) {} 
	  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(68, 788); showPosition(247+hx, 868+hy, 383, 96); } } catch(e) {} 
	  try { if (objName == "AppntLicenseType") { goToPic(0); top.fraPic.scrollTo(68, 813); showPosition(251+hx, 900+hy, 367, 138); } } catch(e) {} 
	  try { if (objName == "AppntProvince") { goToPic(0); top.fraPic.scrollTo(68, 1211); showPosition(237+hx, 1249+hy, 268, 126); } } catch(e) {} 
	  try { if (objName == "AppntCity") { goToPic(0); top.fraPic.scrollTo(68, 1211); showPosition(433+hx, 1251+hy, 325, 135); } } catch(e) {} 
	  try { if (objName == "AppntDistrict") { goToPic(0); top.fraPic.scrollTo(251, 1211); showPosition(673+hx, 1250+hy, 495, 129); } } catch(e) {} 
	  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(77, 1211); showPosition(245+hx, 1259+hy, 970, 164); } } catch(e) {} 
	  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(77, 1304); showPosition(192+hx, 1343+hy, 406, 117); } } catch(e) {} 
	  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(77, 921); showPosition(250+hx, 966+hy, 379, 110); } } catch(e) {} 
	  try { if (objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(77, 921); showPosition(250+hx, 1015+hy, 380, 96); } } catch(e) {} 
	  try { if (objName == "AppntFax") { goToPic(0); top.fraPic.scrollTo(77, 983); showPosition(249+hx, 1054+hy, 384, 93); } } catch(e) {} 
	  try { if (objName == "AppntHomePhone") { goToPic(0); top.fraPic.scrollTo(77, 983); showPosition(256+hx, 1089+hy, 355, 106); } } catch(e) {} 
	  try { if (objName == "AppntGrpName") { goToPic(0); top.fraPic.scrollTo(77, 1045); showPosition(233+hx, 1130+hy, 408, 159); } } catch(e) {} 
	  try { if (objName == "AppntEMail") { goToPic(0); top.fraPic.scrollTo(77, 1107); showPosition(241+hx, 1211+hy, 400, 112); } } catch(e) {} 
	  try { if (objName == "PayMode") { goToPic(1); top.fraPic.scrollTo(102, 512); showPosition(214+hx, 556+hy, 884, 234); } } catch(e) {} 
	  try { if (objName == "AppntBankCode") { goToPic(1); top.fraPic.scrollTo(254, 537); showPosition(504+hx, 600+hy, 567, 82); } } catch(e) {} 
	  try { if (objName == "AppntAccName") { goToPic(1); top.fraPic.scrollTo(128, 537); showPosition(217+hx, 595+hy, 325, 87); } } catch(e) {} 
	  try { if (objName == "AppntBankAccNo") { goToPic(1); top.fraPic.scrollTo(128, 537); showPosition(202+hx, 622+hy, 816, 100); } } catch(e) {} 
	  try { if (objName == "SecPayMode") { goToPic(1); top.fraPic.scrollTo(102, 614); showPosition(207+hx, 660+hy, 876, 218); } } catch(e) {} 
	  try { if (objName == "SecAppntBankCode") { goToPic(1); top.fraPic.scrollTo(154, 597); showPosition(543+hx, 692+hy, 536, 104); } } catch(e) {} 
	  try { if (objName == "SecAppntAccName") { goToPic(1); top.fraPic.scrollTo(154, 597); showPosition(283+hx, 690+hy, 268, 99); } } catch(e) {} 
	  try { if (objName == "SecAppntBankAccNo") { goToPic(1); top.fraPic.scrollTo(154, 597); showPosition(265+hx, 722+hy, 757, 106); } } catch(e) {} 
	  try { if (objName == "Income0") { goToPic(1); top.fraPic.scrollTo(27, 708); showPosition(269+hx, 792+hy, 314, 123); } } catch(e) {} 
	  try { if (objName == "IncomeWay0") { goToPic(1); top.fraPic.scrollTo(27, 708); showPosition(621+hx, 800+hy, 331, 116); } } catch(e) {} 
	  try { if (objName == "AppntStature") { goToPic(2); top.fraPic.scrollTo(107, 66); showPosition(544+hx, 124+hy, 263, 128); } } catch(e) {} 
	  try { if (objName == "AppntAvoirdupois") { goToPic(2); top.fraPic.scrollTo(107, 66); showPosition(776+hx, 121+hy, 252, 125); } } catch(e) {} 
	  try { if (objName == "Remark") { goToPic(3); top.fraPic.scrollTo(0, 43); showPosition(66+hx, 89+hy, 1162, 239); } } catch(e) {} 
	  try { if (objName == "appntfinaimpart") { goToPic(1); top.fraPic.scrollTo(44, 734); showPosition(153+hx, 827+hy, -61, -5); } } catch(e) {} 	
	  try { if (objName == "appnthealimpart") { goToPic(2); top.fraPic.scrollTo(44, 66); showPosition(318+hx, 172+hy, -193, -46); } } catch(e) {} 
	  //被保人
	  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(0, 705); showPosition(559+hx, 783+hy, 364, 105); } } catch(e) {} 
	  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(0, 357); showPosition(557+hx, 407+hy, 372, 111); } } catch(e) {} 
	  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(0, 548); showPosition(569+hx, 636+hy, 343, 142); } } catch(e) {} 
	  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(0, 641); showPosition(557+hx, 705+hy, 365, 111); } } catch(e) {} 
	  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(0, 407); showPosition(571+hx, 453+hy, 348, 104); } } catch(e) {} 
	  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(0, 514); showPosition(571+hx, 562+hy, 332, 157); } } catch(e) {} 
	  try { if (objName == "InsuredAppAge") { goToPic(0); top.fraPic.scrollTo(0, 514); showPosition(575+hx, 575+hy, 328, 125); } } catch(e) {} 
	  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(0, 415); showPosition(571+hx, 490+hy, 336, 145); } } catch(e) {} 
	  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(0, 705); showPosition(567+hx, 745+hy, 336, 114); } } catch(e) {} 
	  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(0, 767); showPosition(569+hx, 865+hy, 328, 107); } } catch(e) {} 
	  try { if (objName == "LicenseType") { goToPic(0); top.fraPic.scrollTo(0, 846); showPosition(578+hx, 902+hy, 311, 136); } } catch(e) {} 
	  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(0, 938); showPosition(578+hx, 979+hy, 311, 98); } } catch(e) {} 
	  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(0, 938); showPosition(566+hx, 1020+hy, 339, 97); } } catch(e) {} 
	  try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(0, 938); showPosition(565+hx, 1059+hy, 338, 93); } } catch(e) {} 
	  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(0, 938); showPosition(554+hx, 1099+hy, 341, 94); } } catch(e) {} 
	  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(0, 1062); showPosition(545+hx, 1130+hy, 378, 156); } } catch(e) {} 
	  try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(0, 1155); showPosition(552+hx, 1203+hy, 365, 125); } } catch(e) {} 	
	  try { if (objName == "Income") { goToPic(1); top.fraPic.scrollTo(8, 742); showPosition(328+hx, 850+hy, 210, 95); } } catch(e) {} 
	  try { if (objName == "IncomeWay") { goToPic(1); top.fraPic.scrollTo(8, 742); showPosition(654+hx, 855+hy, 284, 95); } } catch(e) {} 
	  try { if (objName == "Stature") { goToPic(2); top.fraPic.scrollTo(33, 66); showPosition(109+hx, 147+hy, 231, 103); } } catch(e) {} 
	  try { if (objName == "Avoirdupois") { goToPic(2); top.fraPic.scrollTo(33, 66); showPosition(331+hx, 142+hy, 207, 108); } } catch(e) {} 
	  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(68, 1211); showPosition(237+hx, 1249+hy, 268, 126); } } catch(e) {} 
	  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(68, 1211); showPosition(433+hx, 1251+hy, 325, 135); } } catch(e) {} 
	  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(251, 1211); showPosition(673+hx, 1250+hy, 495, 129); } } catch(e) {} 
	  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(77, 1211); showPosition(245+hx, 1259+hy, 970, 164); } } catch(e) {} 
	  try { if (objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(77, 1304); showPosition(192+hx, 1343+hy, 406, 117); } } catch(e) {} 
		try { if (objName == "insufinaimpart") { goToPic(1); top.fraPic.scrollTo(44, 734); showPosition(153+hx, 827+hy, -61, -5); } } catch(e) {} 	
	  try { if (objName == "insuhealimpart") { goToPic(2); top.fraPic.scrollTo(44, 66); showPosition(318+hx, 172+hy, -193, -46); } } catch(e) {} 
	  
	  try { if (objName == "BnfButton") { goToPic(0); top.fraPic.scrollTo(56, 1398); showPosition(61+hx, 1417+hy, 1177, 279); } } catch(e) {} 
		try { if (objName == "RiskButton") { goToPic(1); top.fraPic.scrollTo(33, 110); showPosition(202+hx, 130+hy, -133, 24); } } catch(e) {} 
	  	
	  try { if (objName == "RiskCode") { goToPic(1); top.fraPic.scrollTo(102, 102); showPosition(189+hx, 121+hy, 271, 390); } } catch(e) {} 
		
	}

}
/*** 银行险随动控制函数15**/
function goToArea121() {
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(263, 368); showPosition(829+hx, 439+hy, 359, 73); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(263, 539); showPosition(827+hx, 647+hy, 371, 121); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(263, 606); showPosition(831+hx, 718+hy, 355, 86); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(263, 384); showPosition(833+hx, 470+hy, 355, 87); } } catch(e) {} 
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(263, 473); showPosition(822+hx, 504+hy, 375, 126); } } catch(e) {} 
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(263, 659); showPosition(827+hx, 750+hy, 364, 93); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(263, 730); showPosition(831+hx, 825+hy, 352, 130); } } catch(e) {} 
  try { if (objName == "LicenseType") { goToPic(0); top.fraPic.scrollTo(263, 846); showPosition(831+hx, 892+hy, 357, 133); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(4, 1156); showPosition(231+hx, 1238+hy, 228, 88); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(4, 1156); showPosition(390+hx, 1248+hy, 195, 76); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(4, 1156); showPosition(538+hx, 1235+hy, 211, 96); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(0, 1156); showPosition(236+hx, 1232+hy, 952, 134); } } catch(e) {} 
  try { if (objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(18, 1218); showPosition(243+hx, 1323+hy, 232, 99); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(202, 904); showPosition(837+hx, 974+hy, 340, 81); } } catch(e) {} 
  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(241, 904); showPosition(829+hx, 1003+hy, 356, 87); } } catch(e) {} 
  try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(263, 904); showPosition(832+hx, 1051+hy, 357, 69); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(263, 966); showPosition(832+hx, 1088+hy, 349, 66); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(263, 1028); showPosition(831+hx, 1119+hy, 347, 133); } } catch(e) {} 
  try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(263, 1090); showPosition(831+hx, 1209+hy, 343, 78); } } catch(e) {} 
  try { if (objName == "Income") { goToPic(1); top.fraPic.scrollTo(0, 896); showPosition(282+hx, 953+hy, 203, 89); } } catch(e) {} 
  try { if (objName == "IncomeWay") { goToPic(1); top.fraPic.scrollTo(0, 921); showPosition(590+hx, 958+hy, 185, 73); } } catch(e) {} 
  try { if (objName == "Stature") { goToPic(2); top.fraPic.scrollTo(174, 75); showPosition(632+hx, 160+hy, 223, 91); } } catch(e) {} 
  try { if (objName == "Avoirdupois") { goToPic(2); top.fraPic.scrollTo(174, 75); showPosition(872+hx, 166+hy, 182, 76); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(263, 368); showPosition(829+hx, 439+hy, 359, 73); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(263, 539); showPosition(827+hx, 647+hy, 371, 121); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(263, 606); showPosition(831+hx, 718+hy, 355, 86); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(263, 384); showPosition(833+hx, 470+hy, 355, 87); } } catch(e) {} 
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(263, 473); showPosition(822+hx, 504+hy, 375, 126); } } catch(e) {} 
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(263, 659); showPosition(827+hx, 750+hy, 364, 93); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(263, 730); showPosition(831+hx, 825+hy, 352, 130); } } catch(e) {} 
  try { if (objName == "LicenseType") { goToPic(0); top.fraPic.scrollTo(263, 846); showPosition(831+hx, 892+hy, 357, 133); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(4, 1156); showPosition(231+hx, 1238+hy, 228, 88); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(4, 1156); showPosition(390+hx, 1248+hy, 195, 76); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(4, 1156); showPosition(538+hx, 1235+hy, 211, 96); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(0, 1156); showPosition(236+hx, 1232+hy, 952, 134); } } catch(e) {} 
  try { if (objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(18, 1218); showPosition(243+hx, 1323+hy, 232, 99); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(202, 904); showPosition(837+hx, 974+hy, 340, 81); } } catch(e) {} 
  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(241, 904); showPosition(829+hx, 1003+hy, 356, 87); } } catch(e) {} 
  try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(263, 904); showPosition(832+hx, 1051+hy, 357, 69); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(263, 966); showPosition(832+hx, 1088+hy, 349, 66); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(263, 1028); showPosition(831+hx, 1119+hy, 347, 133); } } catch(e) {} 
  try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(263, 1090); showPosition(831+hx, 1209+hy, 343, 78); } } catch(e) {} 
  try { if (objName == "Income") { goToPic(1); top.fraPic.scrollTo(0, 896); showPosition(282+hx, 953+hy, 203, 89); } } catch(e) {} 
  try { if (objName == "IncomeWay") { goToPic(1); top.fraPic.scrollTo(0, 921); showPosition(590+hx, 958+hy, 185, 73); } } catch(e) {} 
  try { if (objName == "Stature") { goToPic(2); top.fraPic.scrollTo(174, 75); showPosition(632+hx, 160+hy, 223, 91); } } catch(e) {} 
  try { if (objName == "Avoirdupois") { goToPic(2); top.fraPic.scrollTo(174, 75); showPosition(872+hx, 166+hy, 182, 76); } } catch(e) {} 
  
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(263, 505); showPosition(830+hx, 572+hy, 360, 126); } } catch(e) {}   
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(263, 705); showPosition(828+hx, 757+hy, 368, 82); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(263, 705); showPosition(828+hx, 757+hy, 368, 82); } } catch(e) {}   
  
}
function goToArea122() {
	//alert("ok");//第二被保人
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(198, 680); showPosition(870+hx, 793+hy, 325, 89); } } catch(e) {} 
  try { if (objName == "Name") { goToPic(0); top.fraPic.scrollTo(198, 332); showPosition(874+hx, 414+hy, 330, 94); } } catch(e) {} 
  try { if (objName == "IDType") { goToPic(0); top.fraPic.scrollTo(254, 580); showPosition(857+hx, 650+hy, 356, 127); } } catch(e) {} 
  try { if (objName == "IDNo") { goToPic(0); top.fraPic.scrollTo(254, 673); showPosition(866+hx, 712+hy, 350, 109); } } catch(e) {} 
  try { if (objName == "Sex") { goToPic(0); top.fraPic.scrollTo(254, 357); showPosition(871+hx, 453+hy, 348, 102); } } catch(e) {} 
  try { if (objName == "Birthday") { goToPic(0); top.fraPic.scrollTo(254, 481); showPosition(869+hx, 569+hy, 339, 129); } } catch(e) {} 
  try { if (objName == "InsuredAppAge") { goToPic(0); top.fraPic.scrollTo(263, 514); showPosition(868+hx, 554+hy, 337, 147); } } catch(e) {} 
  try { if (objName == "Marriage") { goToPic(0); top.fraPic.scrollTo(263, 415); showPosition(868+hx, 491+hy, 351, 141); } } catch(e) {} 
  try { if (objName == "NativePlace") { goToPic(0); top.fraPic.scrollTo(263, 705); showPosition(866+hx, 747+hy, 352, 109); } } catch(e) {} 
  try { if (objName == "OccupationCode") { goToPic(0); top.fraPic.scrollTo(263, 767); showPosition(870+hx, 872+hy, 338, 92); } } catch(e) {} 
  try { if (objName == "LicenseType") { goToPic(0); top.fraPic.scrollTo(263, 860); showPosition(868+hx, 900+hy, 342, 137); } } catch(e) {} 
  try { if (objName == "Mobile") { goToPic(0); top.fraPic.scrollTo(255, 860); showPosition(865+hx, 973+hy, 348, 106); } } catch(e) {} 
  try { if (objName == "GrpPhone") { goToPic(0); top.fraPic.scrollTo(263, 922); showPosition(860+hx, 1008+hy, 355, 109); } } catch(e) {} 
  try { if (objName == "Fax") { goToPic(0); top.fraPic.scrollTo(247, 922); showPosition(866+hx, 1052+hy, 341, 107); } } catch(e) {} 
  try { if (objName == "HomePhone") { goToPic(0); top.fraPic.scrollTo(263, 984); showPosition(866+hx, 1089+hy, 343, 106); } } catch(e) {} 
  try { if (objName == "GrpName") { goToPic(0); top.fraPic.scrollTo(263, 1077); showPosition(853+hx, 1128+hy, 371, 157); } } catch(e) {} 
  try { if (objName == "EMail") { goToPic(0); top.fraPic.scrollTo(263, 1170); showPosition(852+hx, 1216+hy, 359, 117); } } catch(e) {} 
  try { if (objName == "Income") { goToPic(1); top.fraPic.scrollTo(0, 793); showPosition(291+hx, 847+hy, 272, 107); } } catch(e) {} 
  try { if (objName == "IncomeWay") { goToPic(1); top.fraPic.scrollTo(0, 793); showPosition(639+hx, 846+hy, 306, 112); } } catch(e) {} 
  try { if (objName == "Stature") { goToPic(2); top.fraPic.scrollTo(141, 83); showPosition(538+hx, 127+hy, 256, 129); } } catch(e) {} 
  try { if (objName == "Avoirdupois") { goToPic(2); top.fraPic.scrollTo(141, 83); showPosition(783+hx, 124+hy, 241, 121); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(68, 1211); showPosition(237+hx, 1249+hy, 268, 126); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(68, 1211); showPosition(433+hx, 1251+hy, 325, 135); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(251, 1211); showPosition(673+hx, 1250+hy, 495, 129); } } catch(e) {} 
  try { if (objName == "PostalAddress") { goToPic(0); top.fraPic.scrollTo(77, 1211); showPosition(245+hx, 1259+hy, 970, 164); } } catch(e) {} 
  try { if (objName == "ZIPCODE") { goToPic(0); top.fraPic.scrollTo(77, 1304); showPosition(192+hx, 1343+hy, 406, 117); } } catch(e) {} 

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