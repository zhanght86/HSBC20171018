//ProposalAutoMove.js
var RiskType = "";

		  var sqlid1="ProposalAutoMove4Sql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalAutoMove4Sql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		//mySql1.addSubPara(tContNo);//指定传入的参数
	    var strSql=mySql1.getString();	

//var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'"; 
var arrScanType = easyExecSql(strSql);
 var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
 
/*** 为每个界面录入控件增加相应随动的事件，在ProposalInput.js中调用 **/
function setFocus() 
{  
	for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) 
	{ 
		if (typeof(LoadFlag)!="undefined" && LoadFlag==99) 
		{
			if (window.document.forms[0].elements[elementsNum].type == "text" 
			|| window.document.forms[0].elements[elementsNum].type == "textarea" ) 
			{ 
				window.document.forms[0].elements[elementsNum].onclick = custom;
				window.document.forms[0].elements[elementsNum].onfocus = debugShow;  
			}
		}
		else 
		{
			eval(" window.document.forms[0].elements[elementsNum].onfocus = goToArea" +fm.pagename.value);        		
		}     
	} 
}

/** * 显示红框，将控件框起来 **/
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

/** * 隐藏红框**/
function hiddenPosition() 
{
  top.fraPic.spanPosition.style.display = "none";
}

var hx = 0;
var hy = 0;
var goToLock = false;

var impart = false;


function goToImpart() 
{
  try 
  {
    goToPic(1); top.fraPic.scrollTo(120, 810);
    impart = false;
  }
  catch(e) 
  {}
}
function custom() 
{
  try 
  {
    objName = this.name;
    try { hiddenPosition(); } catch(e) {}  
    path = top.fraPic.centerPic.innerHTML;
    this.value = "try { if (objName == \"" + objName + path.substring(path.indexOf("=") + 4); 
  }
  catch(e) 
  {}
}


function debugShow() 
{
  try 
  {   
    objName = this.name;
    try { hiddenPosition(); } catch(e) {}
    eval(this.value);
  } 
  catch(e) 
  {}
}

function submitAutoMove(saveflag) 
{
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

function goToArea11() 
{
  objName = this.name;
  if (goToLock) 
  {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}
  //合同信息
  try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(264, 66); showPosition(698+hx, 135+hy, 546, 190); } } catch(e) {} 
  try { if (objName == "PolApplyDate") { goToPic(1); top.fraPic.scrollTo(264, 1345); showPosition(772+hx, 1465+hy, 416, 80); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(1); top.fraPic.scrollTo(248, 1449); showPosition(692+hx, 1566+hy, 500, 88); } } catch(e) {} 
  try { if (objName == "AgentName") { goToPic(1); top.fraPic.scrollTo(0, 1449); showPosition(197+hx, 1572+hy, 546, 62); } } catch(e) {} 
  try { if (objName == "TelephonistCode") { goToPic(1); top.fraPic.scrollTo(248, 1449); showPosition(707+hx, 1516+hy, 490, 76); } } catch(e) {} 
  try { if (objName == "TelephonistName") { goToPic(1); top.fraPic.scrollTo(0, 1449); showPosition(177+hx, 1516+hy, 560, 74); } } catch(e) {} 
  try { if (objName == "CSplit") { goToPic(1); top.fraPic.scrollTo(21, 1510); showPosition(205+hx, 1613+hy, 538, 92); } } catch(e) {} 
  //投保人
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(31, 212); showPosition(80+hx, 317+hy, 280, 120); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(89, 314); showPosition(128+hx, 403+hy, 480, 72); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(264, 314); showPosition(580+hx, 409+hy, 614, 70); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(173, 256); showPosition(317+hx, 321+hy, 272, 66); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(173, 256); showPosition(560+hx, 327+hy, 360, 62); } } catch(e) {} 
  try { if (objName == "AppntAge") { goToPic(0); top.fraPic.scrollTo(173, 256); showPosition(560+hx, 327+hy, 360, 62); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(89, 324); showPosition(127+hx, 449+hy, 824, 74); } } catch(e) {} 
  try { if (objName == "AppntOccupationType") { goToPic(0); top.fraPic.scrollTo(89, 324); showPosition(127+hx, 449+hy, 824, 74); } } catch(e) {} 
  try { if (objName == "AppntProvince") { goToPic(0); top.fraPic.scrollTo(89, 426); showPosition(105+hx, 493+hy, 644, 74); } } catch(e) {} 
  try { if (objName == "AppntCity") { goToPic(0); top.fraPic.scrollTo(89, 426); showPosition(105+hx, 493+hy, 644, 74); } } catch(e) {} 
  try { if (objName == "AppntDistrict") { goToPic(0); top.fraPic.scrollTo(89, 426); showPosition(105+hx, 493+hy, 644, 74); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(256, 441); showPosition(719+hx, 496+hy, 468, 72); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(256, 441); showPosition(883+hx, 452+hy, 306, 72); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(256, 307); showPosition(816+hx, 374+hy, 368, 62); } } catch(e) {} 
  try { if (objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(256, 307); showPosition(332+hx, 368+hy, 276, 72); } } catch(e) {} 
  try { if (objName == "AppntHomePhone") { goToPic(0); top.fraPic.scrollTo(256, 307); showPosition(586+hx, 364+hy, 284, 72); } } catch(e) {} 
  try { if (objName == "NewPayMode") { goToPic(0); top.fraPic.scrollTo(238, 1089); showPosition(306+hx, 1192+hy, 870, 68); } } catch(e) {} 
  try { if (objName == "NewBankCode") { goToPic(0); top.fraPic.scrollTo(238, 1089); showPosition(714+hx, 1238+hy, 468, 74); } } catch(e) {} 
  try { if (objName == "NewAccName") { goToPic(0); top.fraPic.scrollTo(238, 1089); showPosition(322+hx, 1232+hy, 470, 80); } } catch(e) {} 
  try { if (objName == "NewBankAccNo") { goToPic(0); top.fraPic.scrollTo(238, 1191); showPosition(322+hx, 1272+hy, 874, 72); } } catch(e) {} 
  try { if (objName == "SecPayMode") { goToPic(0); top.fraPic.scrollTo(238, 1177); showPosition(291+hx, 1316+hy, 896, 76); } } catch(e) {} 
  try { if (objName == "SecBankCode") { goToPic(0); top.fraPic.scrollTo(238, 1313); showPosition(730+hx, 1368+hy, 452, 68); } } catch(e) {} 
  try { if (objName == "SecAccName") { goToPic(0); top.fraPic.scrollTo(238, 1313); showPosition(298+hx, 1370+hy, 484, 62); } } catch(e) {} 
  try { if (objName == "SecBankAccNo") { goToPic(0); top.fraPic.scrollTo(238, 1313); showPosition(316+hx, 1410+hy, 868, 70); } } catch(e) {} 
  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(238, 205); showPosition(884+hx, 306+hy, 312, 88); } } catch(e) {} 
  try { if (objName == "InsuredName") { goToPic(0); top.fraPic.scrollTo(0, 493); showPosition(80+hx, 546+hy, 280, 70); } } catch(e) {} 
  try { if (objName == "InsuredIDType") { goToPic(0); top.fraPic.scrollTo(0, 493); showPosition(118+hx, 582+hy, 496, 78); } } catch(e) {} 
  try { if (objName == "InsuredIDNo") { goToPic(0); top.fraPic.scrollTo(243, 508); showPosition(577+hx, 577+hy, 606, 76); } } catch(e) {} 
  try { if (objName == "InsuredSex") { goToPic(0); top.fraPic.scrollTo(0, 461); showPosition(318+hx, 538+hy, 276, 74); } } catch(e) {} 
  try { if (objName == "InsuredBirthday") { goToPic(0); top.fraPic.scrollTo(264, 461); showPosition(564+hx, 542+hy, 362, 64); } } catch(e) {} 
  try { if (objName == "InsuredAppAge") { goToPic(0); top.fraPic.scrollTo(264, 461); showPosition(564+hx, 542+hy, 362, 64); } } catch(e) {} 
  try { if (objName == "InsuredOccupationCode") { goToPic(0); top.fraPic.scrollTo(76, 563); showPosition(128+hx, 632+hy, 812, 66); } } catch(e) {} 
  try { if (objName == "InsuredOccupationType") { goToPic(0); top.fraPic.scrollTo(76, 563); showPosition(128+hx, 632+hy, 812, 66); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(105, 597); showPosition(133+hx, 664+hy, 626, 80); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(105, 597); showPosition(133+hx, 664+hy, 626, 80); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(105, 597); showPosition(133+hx, 664+hy, 626, 80); } } catch(e) {} 
  try { if (objName == "InsuredPostalAddress") { goToPic(0); top.fraPic.scrollTo(264, 597); showPosition(702+hx, 670+hy, 492, 76); } } catch(e) {} 
  try { if (objName == "InsuredZipCode") { goToPic(0); top.fraPic.scrollTo(264, 597); showPosition(906+hx, 624+hy, 296, 66); } } catch(e) {} 
  try { if (objName == "InsuredMobile") { goToPic(0); top.fraPic.scrollTo(264, 454); showPosition(904+hx, 545+hy, 292, 66); } } catch(e) {} 
  try { if (objName == "InsuredGrpPhone") { goToPic(0); top.fraPic.scrollTo(264, 454); showPosition(904+hx, 545+hy, 292, 66); } } catch(e) {} 
  try { if (objName == "InsuredHomePhone") { goToPic(0); top.fraPic.scrollTo(264, 454); showPosition(904+hx, 545+hy, 292, 66); } } catch(e) {} 
  try { if (objName == "ProposalContNo") { goToPic(0); top.fraPic.scrollTo(264, 66); showPosition(698+hx, 135+hy, 546, 190); } } catch(e) {} 
  try { if (objName == "PolApplyDate") { goToPic(1); top.fraPic.scrollTo(264, 1345); showPosition(772+hx, 1465+hy, 416, 80); } } catch(e) {} 
  try { if (objName == "AgentCode") { goToPic(1); top.fraPic.scrollTo(248, 1449); showPosition(692+hx, 1566+hy, 500, 88); } } catch(e) {} 
  try { if (objName == "AgentName") { goToPic(1); top.fraPic.scrollTo(0, 1449); showPosition(197+hx, 1572+hy, 546, 62); } } catch(e) {} 
  try { if (objName == "TelephonistCode") { goToPic(1); top.fraPic.scrollTo(248, 1449); showPosition(707+hx, 1516+hy, 490, 76); } } catch(e) {} 
  try { if (objName == "TelephonistName") { goToPic(1); top.fraPic.scrollTo(0, 1449); showPosition(177+hx, 1516+hy, 560, 74); } } catch(e) {} 
  try { if (objName == "CSplit") { goToPic(1); top.fraPic.scrollTo(21, 1510); showPosition(205+hx, 1613+hy, 538, 92); } } catch(e) {} 
  try { if (objName == "AppntName") { goToPic(0); top.fraPic.scrollTo(31, 212); showPosition(80+hx, 317+hy, 280, 120); } } catch(e) {} 
  try { if (objName == "AppntIDType") { goToPic(0); top.fraPic.scrollTo(89, 314); showPosition(128+hx, 403+hy, 480, 72); } } catch(e) {} 
  try { if (objName == "AppntIDNo") { goToPic(0); top.fraPic.scrollTo(264, 314); showPosition(580+hx, 409+hy, 614, 70); } } catch(e) {} 
  try { if (objName == "AppntSex") { goToPic(0); top.fraPic.scrollTo(173, 256); showPosition(317+hx, 321+hy, 272, 66); } } catch(e) {} 
  try { if (objName == "AppntBirthday") { goToPic(0); top.fraPic.scrollTo(173, 256); showPosition(560+hx, 327+hy, 360, 62); } } catch(e) {} 
  try { if (objName == "AppntAge") { goToPic(0); top.fraPic.scrollTo(173, 256); showPosition(560+hx, 327+hy, 360, 62); } } catch(e) {} 
  try { if (objName == "AppntOccupationCode") { goToPic(0); top.fraPic.scrollTo(89, 324); showPosition(127+hx, 449+hy, 824, 74); } } catch(e) {} 
  try { if (objName == "AppntOccupationType") { goToPic(0); top.fraPic.scrollTo(89, 324); showPosition(127+hx, 449+hy, 824, 74); } } catch(e) {} 
  try { if (objName == "AppntProvince") { goToPic(0); top.fraPic.scrollTo(89, 426); showPosition(105+hx, 493+hy, 644, 74); } } catch(e) {} 
  try { if (objName == "AppntCity") { goToPic(0); top.fraPic.scrollTo(89, 426); showPosition(105+hx, 493+hy, 644, 74); } } catch(e) {} 
  try { if (objName == "AppntDistrict") { goToPic(0); top.fraPic.scrollTo(89, 426); showPosition(105+hx, 493+hy, 644, 74); } } catch(e) {} 
  try { if (objName == "AppntPostalAddress") { goToPic(0); top.fraPic.scrollTo(256, 441); showPosition(719+hx, 496+hy, 468, 72); } } catch(e) {} 
  try { if (objName == "AppntZipCode") { goToPic(0); top.fraPic.scrollTo(256, 441); showPosition(883+hx, 452+hy, 306, 72); } } catch(e) {} 
  try { if (objName == "AppntMobile") { goToPic(0); top.fraPic.scrollTo(256, 307); showPosition(816+hx, 374+hy, 368, 62); } } catch(e) {} 
  try { if (objName == "AppntGrpPhone") { goToPic(0); top.fraPic.scrollTo(256, 307); showPosition(332+hx, 368+hy, 276, 72); } } catch(e) {} 
  try { if (objName == "AppntHomePhone") { goToPic(0); top.fraPic.scrollTo(256, 307); showPosition(586+hx, 364+hy, 284, 72); } } catch(e) {} 
  try { if (objName == "AppntPhone"){ goToPic(0); top.fraPic.scrollTo(256, 307); showPosition(816+hx, 374+hy, 368, 62); } } catch(e) {} 

  //缴费信息
  try { if (objName == "NewPayMode") { goToPic(0); top.fraPic.scrollTo(238, 1089); showPosition(306+hx, 1192+hy, 870, 68); } } catch(e) {} 
  try { if (objName == "NewBankCode") { goToPic(0); top.fraPic.scrollTo(238, 1089); showPosition(714+hx, 1238+hy, 468, 74); } } catch(e) {} 
  try { if (objName == "NewAccName") { goToPic(0); top.fraPic.scrollTo(238, 1089); showPosition(322+hx, 1232+hy, 470, 80); } } catch(e) {} 
  try { if (objName == "NewBankAccNo") { goToPic(0); top.fraPic.scrollTo(238, 1191); showPosition(322+hx, 1272+hy, 874, 72); } } catch(e) {} 
  try { if (objName == "SecPayMode") { goToPic(0); top.fraPic.scrollTo(238, 1177); showPosition(291+hx, 1316+hy, 896, 76); } } catch(e) {} 
  try { if (objName == "SecBankCode") { goToPic(0); top.fraPic.scrollTo(238, 1313); showPosition(730+hx, 1368+hy, 452, 68); } } catch(e) {} 
  try { if (objName == "SecAccName") { goToPic(0); top.fraPic.scrollTo(238, 1313); showPosition(298+hx, 1370+hy, 484, 62); } } catch(e) {} 
  try { if (objName == "SecBankAccNo") { goToPic(0); top.fraPic.scrollTo(238, 1313); showPosition(316+hx, 1410+hy, 868, 70); } } catch(e) {} 
  //被保人
  try { if (objName == "RelationToAppnt") { goToPic(0); top.fraPic.scrollTo(238, 205); showPosition(884+hx, 306+hy, 312, 88); } } catch(e) {} 
  try { if (objName == "InsuredName") { goToPic(0); top.fraPic.scrollTo(0, 493); showPosition(80+hx, 546+hy, 280, 70); } } catch(e) {} 
  try { if (objName == "InsuredIDType") { goToPic(0); top.fraPic.scrollTo(0, 493); showPosition(118+hx, 582+hy, 496, 78); } } catch(e) {} 
  try { if (objName == "InsuredIDNo") { goToPic(0); top.fraPic.scrollTo(243, 508); showPosition(577+hx, 577+hy, 606, 76); } } catch(e) {} 
  try { if (objName == "InsuredSex") { goToPic(0); top.fraPic.scrollTo(0, 461); showPosition(318+hx, 538+hy, 276, 74); } } catch(e) {} 
  try { if (objName == "InsuredBirthday") { goToPic(0); top.fraPic.scrollTo(264, 461); showPosition(564+hx, 542+hy, 362, 64); } } catch(e) {} 
  try { if (objName == "InsuredAppAge") { goToPic(0); top.fraPic.scrollTo(264, 461); showPosition(564+hx, 542+hy, 362, 64); } } catch(e) {} 
  try { if (objName == "InsuredOccupationCode") { goToPic(0); top.fraPic.scrollTo(76, 563); showPosition(128+hx, 632+hy, 812, 66); } } catch(e) {} 
  try { if (objName == "InsuredOccupationType") { goToPic(0); top.fraPic.scrollTo(76, 563); showPosition(128+hx, 632+hy, 812, 66); } } catch(e) {} 
  try { if (objName == "InsuredProvince") { goToPic(0); top.fraPic.scrollTo(105, 597); showPosition(133+hx, 664+hy, 626, 80); } } catch(e) {} 
  try { if (objName == "InsuredCity") { goToPic(0); top.fraPic.scrollTo(105, 597); showPosition(133+hx, 664+hy, 626, 80); } } catch(e) {} 
  try { if (objName == "InsuredDistrict") { goToPic(0); top.fraPic.scrollTo(105, 597); showPosition(133+hx, 664+hy, 626, 80); } } catch(e) {} 
  try { if (objName == "InsuredPostalAddress") { goToPic(0); top.fraPic.scrollTo(264, 597); showPosition(702+hx, 670+hy, 492, 76); } } catch(e) {} 
  try { if (objName == "InsuredZipCode") { goToPic(0); top.fraPic.scrollTo(264, 597); showPosition(906+hx, 624+hy, 296, 66); } } catch(e) {} 
  try { if (objName == "InsuredMobile") { goToPic(0); top.fraPic.scrollTo(264, 454); showPosition(904+hx, 545+hy, 292, 66); } } catch(e) {} 
  try { if (objName == "InsuredGrpPhone") { goToPic(0); top.fraPic.scrollTo(264, 454); showPosition(904+hx, 545+hy, 292, 66); } } catch(e) {} 
  try { if (objName == "InsuredHomePhone") { goToPic(0); top.fraPic.scrollTo(264, 454); showPosition(904+hx, 545+hy, 292, 66); } } catch(e) {} 
  try { if (objName == "InsuredPhone"){ goToPic(0); top.fraPic.scrollTo(264, 454); showPosition(904+hx, 545+hy, 292, 66); } } catch(e) {} 
  //被保人告知信息
  try { if (objName == "HiddenInsuredImpart") { goToPic(1); top.fraPic.scrollTo(100, 500); showPosition(19+hx, 399+hy, 1182, 810); } } catch(e) {} 
  //险种信息
  try { if (objName == "HiddenInsuredPol") { goToPic(0); top.fraPic.scrollTo(55, 865); showPosition(74+hx, 893+hy, 1123, 236); } } catch(e) {} 
  try { if (objName == "HiddenLCBnf") { goToPic(0); top.fraPic.scrollTo(65, 679); showPosition(71+hx, 713+hy, 1126, 200); } } catch(e) {} 
  //险种信息
  try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(76, 870); showPosition(210+hx, 889+hy, 164, 244); } } catch(e) {} 

}

function goToArea13() 
{
  objName = this.name;
  if (goToLock) {
    objName = goToLock;
    goToLock = false;
  }

  try { hiddenPosition(); } catch(e) {}

  try { if (objName == "RiskCode") { goToPic(0); top.fraPic.scrollTo(73, 870); showPosition(203+hx, 884+hy, 194, 252); } } catch(e) {} 
  
  try { if (objName == "BnfGrid2") { goToPic(0); top.fraPic.scrollTo(0, 675); showPosition(92+hx, 713+hy, 178, 192); } } catch(e) {} 
  try { if (objName == "BnfGrid4") { goToPic(0); top.fraPic.scrollTo(227, 665); showPosition(781+hx, 713+hy, 192, 202); } } catch(e) {} 
  try { if (objName == "BnfGrid5") { goToPic(0); top.fraPic.scrollTo(227, 680); showPosition(945+hx, 712+hy, 242, 200); } } catch(e) {} 
  try { if (objName == "BnfGrid6") { goToPic(0); top.fraPic.scrollTo(227, 695); showPosition(587+hx, 717+hy, 224, 194); } } catch(e) {} 
  try { if (objName == "BnfGrid7") { goToPic(0); top.fraPic.scrollTo(73, 676); showPosition(351+hx, 720+hy, 144, 188); } } catch(e) {} 
  try { if (objName == "BnfGrid8") { goToPic(0); top.fraPic.scrollTo(73, 691); showPosition(219+hx, 709+hy, 162, 208); } } catch(e) {} 
  try { if (objName == "PayEndYear") { goToPic(0); top.fraPic.scrollTo(243, 876); showPosition(943+hx, 887+hy, 246, 240); } } catch(e) {} 
  try { if (objName == "Amnt") { goToPic(0); top.fraPic.scrollTo(139, 857); showPosition(475+hx, 887+hy, 150, 238); } } catch(e) {} 
  try { if (objName == "Prem") { goToPic(0); top.fraPic.scrollTo(139, 872); showPosition(783+hx, 876+hy, 188, 252); } } catch(e) {} 
  try { if (objName == "InsuYear") { goToPic(0); top.fraPic.scrollTo(94, 887); showPosition(338+hx, 891+hy, 162, 230); } } catch(e) {} 
  try { if (objName == "PayIntv") { goToPic(0); top.fraPic.scrollTo(131, 1052); showPosition(139+hx, 1148+hy, 1031, 56); } } catch(e) {} 
  try { if (objName == "GetYear") { goToPic(0); top.fraPic.scrollTo(107, 1287); showPosition(119+hx, 1431+hy, 1065, 80); } } catch(e) {} 
  try { if (objName == "SpecGrid3") { goToPic(0); top.fraPic.scrollTo(89, 1433); showPosition(107+hx, 1477+hy, 964, 204); } } catch(e) {} 
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



