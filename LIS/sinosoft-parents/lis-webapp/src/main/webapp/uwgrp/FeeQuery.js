//程序名称：Underwrite.js
//程序功能：个人人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var pflag = "1";  //保单类型 1.个人单未
var bUWNormChk = false;
var bContUWNormChk = false;

	var InsuredNo = "";

// 标记核保师是否已查看了相应的信息
var showPolDetailFlag ;
var showAppFlag ;
var showHealthFlag ;
var QuestQueryFlag ;

//提交，保存按钮对应操作
function confirmform()
{
	if(fm.Drawer.value=="")
	{
		alert("请录入领取人！");
		return;
	}
	//alert("ok");
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  fm.action = "./FeeManage.jsp";
  fm.submit(); //提交
 
}

//提交 ，修改按纽
function confirmform2()
{
	var NotCheck = false;
  for (i=0; i<HavePolGrid.mulLineCount; i++) {
    if (HavePolGrid.getChkNo(i)) {
      NotCheck = true;
      break;
    }
  }
  if (NotCheck) {
	var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  
  fm.action = "./FeeManageUpdate.jsp";
  fm.submit(); //提交
  }
  else {
    alert("请先选择一条暂交费信息，在选择框中打钩！");
  }
}

//提交 ，删除按纽
function confirmform3()
{
	var NotCheck = false;
  for (i=0; i<HavePolGrid.mulLineCount; i++) {
    if (HavePolGrid.getChkNo(i)) {
      NotCheck = true;
      break;
    }
  }
  if (NotCheck) {
	var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  
  fm.action = "./FeeManageDelete.jsp";
  fm.submit(); //提交
  }
  else {
    alert("请先选择一条暂交费信息，在选择框中打钩！");
  }
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  
    showInfo.close();
    easyQueryClick(cGrpContNo);
    easyQueryHaveClick(cGrpContNo);
 
		alert(content);


}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit2( FlagStr, content )
{
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    showInfo.close();
	//alert(content);
     //parent.close();
  }
  else
  {
  	showInfo.close();
	//alert(content);
	easyQueryClick();
  	//parent.close();
  }

}


function afterSubmit3( FlagStr, content )
{
	
	showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
    
  }
  else
  {

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //执行下一步操作
    alert(content);
  }
	 	
	divMain.style.display = "none";
	DivLCContButton.style.display="";
	DivLCCont.style.display="none";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	divContUWResult.style.display="";
	divLCPol2.style.display= "none";
	divLCPolButton.style.display= "none";
	divUWResult.style.display="none";
	
	
	
}

//提交后操作,服务器数据返回后执行的操作
function afterApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// 初始化表格
	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
	divLCPol2.style.display= "none";
	divLCPolButton.style.display= "none";
	divMain.style.display = "none";

  }
  else
  {
  		makeWorkFlow();
  }
}

function afterAddFeeApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// 初始化表格
 }
  else
  {
  	  var cPolNo=fm.ProposalNo.value;
	  var cMissionID =fm.MissionID.value;
	  var cSubMissionID =fm.SubMissionID.value;
	  var tSelNo = PolAddGrid.getSelNo()-1;
	  var cEdorNo = PolAddGrid.getRowColData(tSelNo,1);
	  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
	  var cEdorType = PolAddGrid.getRowColData(tSelNo,7);

	 if (cPrtNo != ""&& cEdorNo !="" && cMissionID != "" )
	  {
	  	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&EdorNo="+cEdorNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&EdorType="+cEdorType,"window1");
	  	showInfo.close();
	  }
	  else
	  {
	  	showInfo.close();
	  	alert("请先选择保单!");
	  }
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
		parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

/*********************************************************************
 *  选择核保结后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {

	    //alert("uwstate" + cCodeName + Field.value);
		if( cCodeName == "uwstate" ) {
			DoUWStateCodeSelect(Field.value);//loadFlag在页面出始化的时候声明
		}
}

/*********************************************************************
 *  根据不同的核保结论,处理不同的事务
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DoUWStateCodeSelect(cSelectCode) {

	if(trim(cSelectCode) == '6')//上保核保
	{
		 uwgrade = fm.all('UWGrade').value;
         appgrade= fm.all('AppGrade').value;
         if(uwgrade==null||uwgrade<appgrade)
         {
         	uwpopedomgrade = appgrade ;
         }
        else
         {
        	uwpopedomgrade = uwgrade ;
         }
        //alert(uwpopedomgrade);
        codeSql="#1# and Comcode like #"+ comcode+"%%#"+" and Edorpopedom > #"+uwpopedomgrade+"#"	;
        // alert(codeSql);
	}
	else
	codeSql="";
}


//既往投保信息
function showApp()
{

  var cProposalNo=fm.ProposalNo.value;
  var cInsureNo = fm.InsuredNo.value;
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (cProposalNo != "")
  {
    var tSelNo = PolAddGrid.getSelNo()-1;
  	showAppFlag[tSelNo] = 1 ;
  	window.open("../uwgrp/UWAppGMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,"window1");
  }
  else if (tContNo != "")
  {
  	window.open("../uwgrp/UWAppGMain.jsp?InsureNo1="+cInsureNo + "&GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  	alert("请先选择保单!");
  }
}

//以往核保记录
function showOldUWSub()
{
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWSubGMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  	alert("请先选择保单!");
  }
}

//当前核保记录
function showNewUWSub()
{
  var tSelNo = PolAddGrid.getSelNo();
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWErrGMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  	alert("请先选择保单!");
  }
}
function showNewPolUWSub()
{
  var tSelNo = PolAddGrid.getSelNo();
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
  	window.open("./UWErrMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  	alert("请先选择保单!");
  }
}

// 该投保单理赔给付查询
function ClaimGetQuery2()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolAddGrid.getRowColData(tSel - 1,2);
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);
	}
}
// 理赔给付查询
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cInsuredNo = fm.InsuredNo.value;
		if (cInsuredNo == "")
		    return;
		  window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo);
	}
}

//保单明细信息
function showPolDetail()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  cContNo=fm.all("ContNo").value ;
  cPrtNo=fm.all("PrtNo").value;
  cGrpContNo=fm.all("GrpContNo").value;
  if (cContNo != "")
  {
  	
  	mSwitch.deleteVar( "ContNo" );
  	mSwitch.addVar( "ContNo", "", cContNo );
  	mSwitch.updateVar("ContNo", "", cContNo);
  	mSwitch.deleteVar( "ProposalContNo" );
  	mSwitch.addVar( "ProposalContNo", "", cContNo );
  	mSwitch.updateVar("ProposalContNo", "", cContNo);
  	mSwitch.deleteVar( "PrtNo" );
  	mSwitch.addVar( "PrtNo", "", cPrtNo );
  	mSwitch.updateVar("PrtNo", "", cPrtNo);
  	mSwitch.deleteVar( "GrpContNo" );
  	mSwitch.addVar( "GrpContNo", "", cGrpContNo );
  	mSwitch.updateVar("GrpContNo", "", cGrpContNo);
  	window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&Auditing=1","window1");

  }
  else
  {
  	alert("请先选择保单!");
  }

}


//扫描件查询
function ScanQuery()
{
	var tSel = PolAddGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var prtNo = PolAddGrid.getRowColData(tSel - 1,3);
		if (prtNo == "")
		    return;
		  window.open("../sys/ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
	}
}


//体检资料查询
function showHealthQ()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo = fm.ContNo.value;
  
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	
  	window.open("./GrpUWManuHealthQMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo,"window1");
  	showInfo.close();

  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

// 项目明细查询
function ItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击明细查询按钮。" );
	else
	{
	    var cEdorNo = PolAddGrid.getRowColData(tSel - 1,1);
	    var cSumGetMoney = 	PolAddGrid.getRowColData(tSel - 1,8);

		if (cEdorNo == "")
		   {
		   	alert( "请先选择一条申请了保全项目的记录，再点击保全项目查询按钮。" );
		   	return;
		   }
		window.open("../sys/AllPBqItemQueryMain.jsp?EdorNo=" + cEdorNo + "&SumGetMoney=" + cSumGetMoney);

	}
}

//查看批单信息
function PrtEdor()
{
	var tSel = PolAddGrid.getSelNo();
    if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击批单查看按钮。" );
	else
		{
			var cEdorNo = PolAddGrid.getRowColData(tSel - 1,1);
			if (cEdorNo == "")
		   {
			   	alert( "请先选择一条申请了保全项目的记录，再点击保全明细查询按钮。" );
			   	return;
		   }
			fm.all('EdorNo').value = PolAddGrid.getRowColData(tSel - 1,1);
			fm.all('PolNo').value = PolAddGrid.getRowColData(tSel - 1,2);

			var taction = parent.fraInterface.fm.action;
			var ttarget = parent.fraInterface.fm.target;
			parent.fraInterface.fm.action = "../f1printgrp/EndorsementF1P.jsp";
			parent.fraInterface.fm.target="f1print";
			fm.submit();

			parent.fraInterface.fm.action = taction;
			parent.fraInterface.fm.target=ttarget;
			fm.all('EdorNo').value = '';
			fm.all('PolNo').value = '';
		}
}


//体检资料
function showHealth()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo = fm.ContNo.value;
  var cPrtNo = fm.PrtNo.value;
  var cGrpContNo = fm.GrpContNo.value;
  if (cContNo != "")
  {
  	window.open("./GrpUWManuHealthMain.jsp?ContNo1="+cContNo+"&PrtNo="+cPrtNo+"&GrpContNo="+cGrpContNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}
//既往体检资料
function showBeforeHealthQ()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo=fm.all('ContNo').value;
  //alert("ccontno"+cContNo);
  var strSql = "select InsuredNo from lccont where ContNo = '"+cContNo+"'";
  //alert(strSql);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
  var arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  var cInsuredNo= arrSelected[0][0];


	window.open("../uwgrp/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1");

 showInfo.close();	
}
//特约承保
function showSpec()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
	window.open("./UWGrpManuSpecMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  alert("请先选择保单!");
  }
  
}

//加费承保
function showAdd()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var tGrpContNo = fm.all("GrpContNo").value;
  var tContNo = fm.all("ContNo").value;
  var tPolNo = fm.all("PolNo").value;
  if (tGrpContNo != "" && tContNo != "")
  {
	window.open("./UWGrpManuAddMain.jsp?GrpContNo=" + tGrpContNo + "&ContNo=" + tContNo + "&PolNo=" + tPolNo,"window1");
  }
  else
  {
  alert("请先选择保单!");
  }
}

//生存调查报告
function showRReport()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  
  var cContNo = fm.ContNo.value;
  
 
  var cPrtNo = fm.PrtNo.value;
 
  if (cContNo != "")
  {
  	
  	window.open("./GrpUWManuRReportMain.jsp?GrpContNo="+cGrpContNo+"&ContNo1="+cContNo+"&PrtNo="+cPrtNo,"window1");
  	showInfo.close();
  }
      
 
}

//核保报告书
function showReport()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo=fm.ContNo.value;
  tUWIdea = fm.all('UWIdea').value;
  if (cContNo != "")
  {
  	window.open("../uwgrp/UWManuReportMain.jsp?ContNo="+cContNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}


//发催办通知书
function SendPressNotice()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fm.ProposalNo.value;
  cOtherNoType="00"; //其他号码类型
  cCode="06";        //单据类型

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  	 }
  else

  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}


//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow){
	if (cShow=="true"){
		cDiv.style.display="";
	}
	else{
		cDiv.style.display="none";
	}
}

var withdrawFlag = false;
//撤单申请查询,add by Minim
function withdrawQueryClick() {
	withdrawFlag = true;
	easyQueryClick();
}

// 查询按钮
function easyQueryClick(cGrpContNo){
	
	
	k++;

	var strSQL = "";
  
	strSQL = "select grppolno,grpcontno,riskcode,prem,0,0,'' from lcgrppol  where grpcontno='"+cGrpContNo+"'";
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	
	
  
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
	//设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = PolGrid;    
          
	//保存SQL语句
	turnPage.strQuerySql     = strSQL; 
  
	//设置查询起始位置
	turnPage.pageIndex       = 0;

	//在查询结果数组中取出符合页面显示大小设置的数组
	var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

	return true;
}


//查询已有的手续费

function easyQueryHaveClick(cGrpContNo)
{
	var strSql = "select grppolno,grpcontno,riskcode,FeeRate,fee,Drawer,countno,GetNoticeNo,DrawerID,Remark From lcFeeControl where grpcontno='"+cGrpContNo+"' order by GetNoticeNo";
	    turnPage.queryModal(strSql, HavePolGrid);
	
}
//点击mutline单选框触发事件
function easyQueryAddClick(parm1,parm2){
	// 书写SQL语句
	k++;
	//var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	//var state = fm.State.value;       //投保单所处状态
	var tProposalNo = "";
	var tPEdorNo = "";
	var strSQL = "";
	var ContNo = fm.all(parm1).all('PolGrid1').value;
	fm.all("ContNo").value = ContNo;

	// 初始化表格
	initPolAddGrid();
	initPolBox();

	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
//modify by zhangxing
	//strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.UWFlag,LCPol.AppntName,LCPol.InsuredName,LCPol.Mult,LCPol.Prem,LCPol.Amnt from LCPol where ContNo='"+ContNo+"'" + " and UWFlag not in ('0', '1', '4', '9')"
	strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.UWFlag,LCPol.AppntName,LCPol.InsuredName,LCPol.Mult,LCPol.Prem,LCPol.Amnt from LCPol where ContNo='"+ContNo+"'"
	  //查询SQL，返回结果字符串
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("没有未通过核保的个人险种单！");
		divContUWResult.style.display="";
		divLCPol2.style.display= "none";
		divUWResult.style.display="none";
		divLCPolButton.style.display= "";
	}
	else
	{
		divContUWResult.style.display="";
		divLCPol2.style.display= "";
		divUWResult.style.display="";
		divLCPolButton.style.display= "";

		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

		//设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = PolAddGrid;

		//保存SQL语句
		turnPage.strQuerySql     = strSQL;

		//设置查询起始位置
		turnPage.pageIndex       = 0;

		//在查询结果数组中取出符合页面显示大小设置的数组
		var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		initFlag(  arrDataSet.length );
		
		
	}

	var arrSelected = new Array();

//	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	strSQL = "select ProposalNo,PrtNo,AgentCode,GrpContNo,PolNo from lcpol where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	if (arrSelected != null)
	{
		fm.all('ProposalContNo').value = arrSelected[0][0];
		fm.all('PrtNo').value = arrSelected[0][1];
//		fm.all('ManageCom').value = arrSelected[0][2];
//		fm.all('SaleChnl').value = arrSelected[0][3];
		fm.all('AgentCode').value = arrSelected[0][2];
		fm.all('GrpContNo').value = arrSelected[0][3];
		fm.all('PolNo').value = arrSelected[0][4];
//		fm.all('AgentGroup').value = arrSelected[0][5];
//		fm.all('AgentCode1').value = arrSelected[0][6];
//		fm.all('AgentCom').value = arrSelected[0][7];
//		fm.all('AgentType').value = arrSelected[0][8];
//		fm.all('ReMark').value = arrSelected[0][9];
	}
	
	cGrpContNo = fm.all("GrpContNo").value;
	strSQL = "select Name,PostalAddress,ZipCode,Phone from lCGrpAppnt where GrpContNo='"+cGrpContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	if (arrSelected != null)
	{
		fm.all('GrpName').value = arrSelected[0][0];
		fm.all('PostalAddress').value = arrSelected[0][1];
		fm.all('ZipCode').value = arrSelected[0][2];
		fm.all('Phone').value = arrSelected[0][3];
	}
	
	strSQL = "select insuredname,insuredsex,insuredbirthday,insuredidtype,insuredidno,insuredno from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);	
	if (arrSelected != null)
	{
		fm.all('InsuredName').value = arrSelected[0][0];
		fm.all('InsuredSex').value = arrSelected[0][1];
		fm.all('InsuredBirthday').value = arrSelected[0][2];
		fm.all('InsuredIDType').value = arrSelected[0][3];
		fm.all('InsuredIDno').value = arrSelected[0][4];
		fm.all('InsuredNo').value = arrSelected[0][5];
		InsuredNo = arrSelected[0][5];
	}
	
	
	strSQL = "select OccupationType from lcinsured where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);	
	if (arrSelected != null)
	{
		fm.all('OccupationType').value = arrSelected[0][0];
	}

	return true;
}


function displayEasyResult( arrResult ){
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initPolGrid();

		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			}// end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//申请要人工核保保单
function checkDouble(tProposalNo){
	fm.PolNoHide.value = tProposalNo;
	fm.action = "./ManuUWApply.jsp";
	fm.submit();
}

//选择要人工核保保单
function getPolGridCho(){
	var cSelNo = PolAddGrid.getSelNo()-1;
	codeSql = "code";
	fm.action = "./GroupPolQuery.jsp";
	fm.submit();
}

function makeWorkFlow(){
	fm.action = "./ManuUWChk.jsp";
	fm.submit();
}

function checkBackPol(ProposalNo){
	var strSql = "select * from LCApplyRecallPol where ProposalNo='" + ProposalNo + "' and InputState='0'";
	var arrResult = easyExecSql(strSql);
	//alert(arrResult);
	
	if (arrResult != null) {
		return false;
	}
	return true;
}

//  初始化核保师是否已查看了相应的信息标记数组
function initFlag(  tlength )
{
	// 标记核保师是否已查看了相应的信息
      showPolDetailFlag =  new Array() ;
      showAppFlag = new Array() ;
      showHealthFlag = new Array() ;
      QuestQueryFlag = new Array() ;

     var i=0;
	  for( i = 0; i < tlength ; i++ )
		{
			showPolDetailFlag[i] = 0;
			showAppFlag[i] = 0;
			showHealthFlag[i] = 0;
			QuestQueryFlag[i] = 0;
		}

	}
//下核保结论
function manuchk(cContFlag)
{
	if (cContFlag == 1)
	{
		flag = fm.all('UWState').value;
		var ProposalNo = "";
		ProposalNo = fm.all('PolNo').value;

		if (ProposalNo == "")
		{
			alert("请先选择保单！");
			return;
		}
    if(PolAddGrid.getSelNo()==0)
    {
    	alert("请选择一个险种信息！");
    	return;
    }
		var MainPolNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 2);
		fm.tempgrpcontno.value=PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 3);
		fm.tempriskcode.value=PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 4);
    //alert("ok");
		if (trim(fm.UWState.value) == "") {
			alert("必须先录入核保结论！");
		return;
		}

		if (!checkBackPol(ProposalNo)) {
		  if (!confirm("该投保单有撤单申请，继续下此结论吗？")) return;
		}

		if (trim(fm.UWState.value) == "6") {
		  fm.UWOperater.value = operator;
		}

		var tSelNo = PolAddGrid.getSelNo()-1;

		var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]

		fm.action = "./UWManuNormGChk.jsp";
		fm.submit();
		bUWNormChk = true;
	}
	else if (cContFlag == 2)
	{
		flag = fm.all('ContUWState').value;
		var cContNo = "";
		cContNo = fm.all('ContNo').value;

		if (cContNo == "")
		{
			alert("请先选择保单！");
			return;
		}

		if (trim(fm.ContUWState.value) == "") {
			alert("必须先录入核保结论！");
		return;
		}


		if (trim(fm.ContUWState.value) == "6") {
		  fm.UWOperater.value = operator;
		}


		var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]

		fm.action = "./UWContManuNormGChk.jsp";
		fm.submit();
		bContUWNormChk = true;
	}
	else if (cContFlag == 3)
	{
		//alert("ok");
		flag = "9";
		var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		//[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]

		fm.action = "./UWBatchManuNormGChk.jsp";
		fm.submit();
		bContUWNormChk = true;
	}
}


//问题件回复
function QuestReply()
{
	cProposalNo = fm.ProposalNo.value;  //保单号码

	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestReplyMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");

	initInpBox();
    initPolBox();
	initPolGrid();

}

//问题件查询
function QuestQuery()
{
  cProposalNo = fm.ProposalNo.value;  //保单号码


  if (cProposalNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestQueryMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window2");
  }
  else
  {
  	alert("请先选择保单!");
  }

}

//生存调查报告查询
function RReportQuery()
{
  cContNo = fm.ContNo.value;  //保单号码

  
  
  if (cContNo != "")
  {			
	window.open("./RReportQueryMain.jsp?ContNo="+cContNo);
  }
  else
  {  	
  	alert("请先选择保单!");  	
  }	
}

//保单撤销申请查询
function BackPolQuery()
{
  cProposalNo = fm.ProposalNo.value;  //保单号码


  if (cProposalNo != "")
  {
	window.open("./BackPolQueryMain.jsp?ProposalNo1="+cProposalNo);
  }
  else
  {
  	alert("请先选择保单!");
  }
}

//催办超时查询
function OutTimeQuery()
{
  cProposalNo = fm.ProposalNo.value;  //保单号码


  if (cProposalNo != "")
  {
	window.open("./OutTimeQueryMain.jsp?ProposalNo1="+cProposalNo);
  }
  else
  {
  	alert("请先选择保单!");
  }
}

//保险计划变更
function showChangePlan()
{
  cProposalNo = fm.ProposalNo.value;  //保单号码
  cPrtNo = fm.PrtNoHide.value; //印刷号
  var cType = "ChangePlan";
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );

  if (cProposalNo != ""&&cPrtNo != "")
  {
  	 var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
     var arrResult = easyExecSql(strSql);
     if (arrResult != null) {
       var tInfo = "有未回复的问题件,确认要进行承保计划变更?";
       if(!window.confirm( tInfo ))
         	    return;
      }
    window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=3&Type="+cType+"&prtNo="+cPrtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

   }
  else
  {
  	alert("请先选择保单!");
  }
}

//保险计划变更结论录入显示
function showChangeResultView()
{
	fm.ChangeIdea.value = "";
	fm.PolNoHide.value = fm.ProposalNo.value;  //保单号码
	divUWResult.style.display= "none";
	divChangeResult.style.display= "";
}


//显示保险计划变更结论
function showChangeResult()
{
	fm.UWState.value = "b";
	fm.UWIdea.value = fm.ChangeIdea.value;

	cChangeResult = fm.UWIdea.value;

	if (cChangeResult == "")
	{
		alert("没有录入结论!");
	}
	else
	{
		var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
        var arrResult = easyExecSql(strSql);
        //alert(arrResult);
       if (arrResult != null) {
       var tInfo = "有未回复的问题件,确认要进行承保计划变更?";
       if(!window.confirm( tInfo )){
       	      HideChangeResult()
         	    return;
          }
       }
	  // manuchk();

    }
	//divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.edoruwstate.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}

//隐藏保险计划变更结论
function HideChangeResult()
{
	//divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	//fm.edoruwstate.value = "";
	//fm.UWIdea.value = "";
	//fm.UWPopedomCode.value = "";
}


function cancelchk()
{
	fm.all('edoruwstate').value = "";
	fm.all('UWPopedomCode').value = "";
	fm.all('UWIdea').value = "";
}

function setproposalno()
{
	var count = PolGrid.getSelNo();
	fm.all('ProposalNo').value = PolGrid.getRowColData(count - 1,1,PolGrid);
}

//附件险按钮隐藏函数
function hideAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "none";
	parent.fraInterface.divAddButton2.style.display= "none";
	parent.fraInterface.divAddButton3.style.display= "none";
	parent.fraInterface.divAddButton4.style.display= "none";
	parent.fraInterface.divAddButton5.style.display= "none";
	parent.fraInterface.divAddButton6.style.display= "none";
	parent.fraInterface.divAddButton7.style.display= "none";
	parent.fraInterface.divAddButton8.style.display= "none";
	parent.fraInterface.divAddButton9.style.display= "none";
	parent.fraInterface.divAddButton10.style.display= "none";
	//parent.fraInterface.fm.UWState.CodeData = "0|^4|通融承保^9|正常承保";
	//parent.fraInterface.UWResult.innerHTML="核保结论<Input class=\"code\" name=UWState CodeData = \"0|^4|通融/条件承保^9|正常承保\" ondblclick= \"showCodeListEx('UWState1',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState1',[this,''],[0,1]);\">";
}

//显示隐藏按钮
function showAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "";
	parent.fraInterface.divAddButton2.style.display= "";
	parent.fraInterface.divAddButton3.style.display= "";
	parent.fraInterface.divAddButton4.style.display= "";
	parent.fraInterface.divAddButton5.style.display= "";
	parent.fraInterface.divAddButton6.style.display= "";
	parent.fraInterface.divAddButton7.style.display= "";
	parent.fraInterface.divAddButton8.style.display= "";
	parent.fraInterface.divAddButton9.style.display= "";
	parent.fraInterface.divAddButton10.style.display= "";
	//parent.fraInterface.UWResult.innerHTML="核保结论<Input class=\"code\" name=UWState CodeData = \"0|^1|拒保^2|延期^4|通融/条件承保^6|待上级核保^9|正常承保^a|撤销投保单\" ondblclick= \"showCodeListEx('UWState',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState',[this,''],[0,1]);\">";
	//parent.fraInterface.fm.UWState.CodeData = "0|^1|拒保^2|延期^4|通融承保^6|待上级核保^9|正常承保^a|撤销投保单";
}

function showNotePad() {
  var cContNo = fm.ContNo.value;//PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 1);
  var PrtNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 3);

  if (cContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/UWNotePadMain.jsp?ContNo="+cContNo+"&PrtNo="+PrtNo+"&OperatePos=3", "window1");
  }
  else {
  	alert("请先选择保单!");
  }
}

function InitClick(){
	divSearch.style.display="";
	divLCPol1.style.display= "";
	DivLCContButton.style.display="none";
	DivLCCont.style.display="none";
	divLCPol2.style.display= "none";
	divLCPolButton.style.display= "none";
	divMain.style.display = "none";
	DivLCAppntInd.style.display="none";
	DivLCAppntIndButton.style.display="none";
	divUWResult.style.display="none";
	divContUWResult.style.display="none";
	var cGrpContNo = fm.all("GrpContNo").value
	fm.reset();
	initForm(cGrpContNo);
}

function goBack()
{
	top.close();
}

function InitUWFlag()
{   
	
	  var tSelNo = PolAddGrid.getSelNo()-1;;
	 
	  var tPolNo = PolAddGrid.getRowColData(tSelNo,1);
	  
	  var tSql = "select passflag ,uwidea from lcuwmaster where polno = '"+tPolNo+"'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
		fm.all('UWState').value = arrSelected[0][0];
		fm.all('UWIdea').value = arrSelected[0][1];
	
}
function amntAccumulate()
{
	var tcontno=fm.all('ContNo').value;
	//alert("tcontno"+tcontno);
	var strSql = "select InsuredNo from lccont where ProposalContNo = '"+tcontno+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	var tInsuredNo= arrSelected[0][0];
	//alert("arrResult"+tInsuredNo);
	
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+tInsuredNo,"window1");	
}
function queryHealthImpart(){
	
	var cContNo=fm.all('ContNo').value;
	var strSql = "select InsuredNo from lccont where ProposalContNo = '"+cContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	var tInsuredNo= arrSelected[0][0];
	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+tInsuredNo,"window1");
}
function queryProposal(){
	  
//	var cContNo = fm.all('GrpContNo').value;
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
//	var arr=easyExecSql(strsql);
//	
//	if(arr!=null){
//		fm.all('AppntNo').value=arr[0][0];
//	}
	
    window.open("./ProposalQueryMain.jsp?CustomerNo="+InsuredNo);

}
function queryNotProposal(){
  
//  var cContNo = fm.all('GrpContNo').value;
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
//	var arr=easyExecSql(strsql);
//	if(arr!=null){
//		fm.all('AppntNo').value=arr[0][0];
//	}


	window.open("./NotProposalQueryMain.jsp?CustomerNo="+InsuredNo);

}

