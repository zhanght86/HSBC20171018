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
var pflag = "1";  //保单类型 1.个人单

// 标记核保师是否已查看了相应的信息
var showPolDetailFlag ;
var showAppFlag ;
var showHealthFlag ;
var QuestQueryFlag ;

//提交，保存按钮对应操作
function submitForm()
{
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

  var tSelNo = PolAddGrid.getSelNo();
  fm.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
  fm.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

  var tEdorUWState = fm.edoruwstate.value;
  var tUWDelay = fm.UWDelay.value;
  var tUWIdea = fm.UWIdea.value;
  var tUWPopedomCode = fm.UWPopedomCode.value;
  if(tEdorUWState == "")
   {
   	showInfo.close();
    alert("请先录入保全核保结论!");

  	return ;
  }
  if(tEdorUWState == "6" && tUWPopedomCode == "")
  {
  	showInfo.close();
   alert("若要上报核保,请选择上报级别!");

  	return ;
  }

  if(tEdorUWState == "2" && tUWDelay == "")
  {
  	showInfo.close();
   　alert("若要延期承保,请录入延长时间信息!");
  	return ;
  }
  fm.action = "./UWManuNormChk.jsp";
  fm.submit(); //提交
  //alert("submit");
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    //var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  {
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  	//var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	//showInfo.close();
  	//alert(content);
  	//parent.close();

    //执行下一步操作
  }

}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit2( FlagStr, content )
{
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    showInfo.close();
	alert(content);
     //parent.close();
  }
  else
  {
  	showInfo.close();
	alert(content);
	easyQueryClick();
  	//parent.close();
  }

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

  var cProposalNo=fm.ProposalNo.value;
  var cInsureNo = fm.InsuredNo.value;
  if (cProposalNo != "")
  {
    var tSelNo = PolAddGrid.getSelNo()-1;
  	showAppFlag[tSelNo] = 1 ;
  	//showModalDialog("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	window.open("../uwgrp/UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//以往核保记录
function showOldUWSub()
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
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  if (cProposalNo != "")
  {
  	window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//当前核保记录
function showNewUWSub()
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

  var tSelNo = PolAddGrid.getSelNo();
  cPolNo=fm.ProposalNo.value;
  if (cPolNo != "" )
  {
  	//window.open("./PEdorUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	window.open("./UWErrMain.jsp?ProposalNo1="+cPolNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
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

  cProposalNo=fm.ProposalNo.value;
  if (cProposalNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	showPolDetailFlag[tSelNo] = 1 ;
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cProposalNo );
  	mSwitch.updateVar("PolNo", "", cProposalNo);
  	var prtNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo() - 1, 2);
  	window.open("../sys/AllProQueryMain.jsp?LoadFlag=3","window1");

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
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	var tEdorNo = PolAddGrid.getRowColData(tSelNo,1);
  	showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1");
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
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;

  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	var tEdorNo = PolAddGrid.getRowColData(tSelNo,1);
  	showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//特约承保
function showSpec()
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

  var cPolNo=fm.ProposalNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  tUWIdea = fm.all('UWIdea').value;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cPolNo != ""&& cPrtNo !="" && cMissionID != "" )
  {
  	window.open("./UWManuSpecMain.jsp?PolNo="+cPolNo+"&PrtNo="+cPrtNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//加费承保
function showAdd()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var cPolNo=fm.ProposalNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cPrtNo != "" && cMissionID != "" )
  {
	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID,"window1");
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

  cProposalNo=fm.ProposalNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cProposalNo != ""  && cMissionID != "")
  {
  	window.open("./UWManuRReportMain.jsp?PolNo="+cProposalNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
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

//发核保通知书
function SendNotice()
{
  cProposalNo = fm.ProposalNo.value;
  fm.edoruwstate.value = "8";

  if (cProposalNo != "")
  {
	//manuchk();
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
	  var cMissionID =fm.MissionID.value;
	  var cSelNo = PolAddGrid.getSelNo()-1;
	  var cPrtNo = PolAddGrid.getRowColData(cSelNo,3);
	  fm.PrtNoHide.value = cPrtNo ;
	  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp1 = '" + cPrtNo +"'"
				 + " and LWMission.MissionProp2 = '"+ cProposalNo + "'"
//				 + " and LWMission.ActivityID = '0000001105'"
//				 + " and LWMission.ProcessID = '0000000003'"
				 + " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010024') "
				 + " and LWMission.MissionID = '" +cMissionID +"'";
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("不容许发放新的核保通知书,原因可能是:1.已发核保通知书,但未打印.2.未录入核保通知书内容.");
        fm.SubNoticeMissionID.value = "";
        return ;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    var tSubNoticeMissionID =   fm.SubNoticeMissionID.value ;
    if (cProposalNo != "" && cPrtNo != "" && cMissionID != "" && tSubNoticeMissionID != "" )
	  {
	  	showInfo.close();
	  	fm.action = "./UWManuSendNoticeChk.jsp";
	    fm.submit();
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("请先选择保单!");
      }
  }
  else
  {
  	alert("请先选择保单!");
  }
}

function SendHealth()
{
	//
	}
//发首交通知书
function SendFirstNotice()
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
  cCode="07";        //单据类型

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
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
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
function easyQueryClick()
{
	// 初始化表格
	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
    divLCPol2.style.display= "none";
    divMain.style.display = "none";

	// 书写SQL语句
	k++;
	var uwgradestatus = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //保单所处状态
	var strSQL = "";
	if (uwgradestatus == "1")//本级保单
	{
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp3,LWMission.MissionProp5,LWMission.MissionProp7,LWMission.MissionProp9 ,LWMission.MissionProp10 ,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //续保核保工作流
//				 + " and LWMission.ActivityID = '0000001100' " //续保核保工作流中的待人工核保任务节点
				 + " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010028') " //续保核保工作流中的待人工核保任务节点
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp8','QInsuredName' )
				 + getWherePart( 'LWMission.MissionProp4','QRiskCode' )
				 + getWherePart( 'LWMission.MissionProp2','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select EdorPopedom from LDUser where usercode = '"+mOperate+"') = (select AppGrade from LccUWMaster where ContNo = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //集中权限管理体现
	}
	else
	  if (uwgradestatus == "2")//下级保单
	  {
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp3,LWMission.MissionProp5,LWMission.MissionProp7,LWMission.MissionProp9 ,LWMission.MissionProp10 ,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //保全核保工作流
//				 + " and LWMission.ActivityID = '0000001100' " //保全核保工作流中的待人工核保任务节点
				 + " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010028') " //保全核保工作流中的待人工核保任务节点
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp8','QInsuredName' )
				 + getWherePart( 'LWMission.MissionProp4','QRiskCode' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') > (select AppGrade from LCCUWMaster where ContNo = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //集中权限管理体现
	   }
	   else //本级+下级保单
	   {
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp3,LWMission.MissionProp5,LWMission.MissionProp7,LWMission.MissionProp9 ,LWMission.MissionProp10 ,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //续保核保工作流
//				 + " and LWMission.ActivityID = '0000001100' " //续保核保工作流中的待人工核保任务节点
		
		 		+ " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010028') " //续保核保工作流中的待人工核保任务节点
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp8','QInsuredName' )
				 + getWherePart( 'LWMission.MissionProp4','QRiskCode' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') >= (select AppGrade from LccUWMaster where ContNo = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //集中权限管理体现
	}

     //alert(strSQL);
     var tOperator = fm.QOperator.value;
	if(state == "1")
	{
			strSQL = strSQL + " and  LWMission.ActivityStatus = '1'"
		      + " and ( LWMission.DefaultOperator is null or LWMission.DefaultOperator = '" + tOperator + "')";
	}
	if(state == "2")
	{
		strSQL = strSQL + " and  LWMission.ActivityStatus = '3'"
		      + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
	}
	if(state == "3")
	{
		strSQL = strSQL + " and  LWMission.ActivityStatus = '2'"
		    + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
	}


	if (withdrawFlag) {
	  //strSQL = strSQL + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0') ";
	  strSQL = "select LCPol.ProposalNo,LCPol.PrtNo,LMRisk.RiskName,LCPol.AppntName,LCPol.InsuredName "
           + " from LCPol,LCUWMaster,LMRisk where 10=10 "
           + " and LCPol.AppFlag='0'  "
           + " and LCPol.UWFlag not in ('1','2','a','4','9')  "
           + " and LCPol.grppolno = '00000000000000000000' and LCPol.contno = '00000000000000000000' "
           + " and LCPol.ProposalNo = LCPol.MainPolNo  and LCPol.ProposalNo= LCUWMaster.ProposalNo  "
           + " and LCUWMaster.appgrade <= (select UWPopedom from LDUser where usercode = '"+mOperate+"') "
           + " and LCPol.ManageCom like '" + manageCom + "%%'"
           + " and LMRisk.RiskCode = LCPol.RiskCode "
           + getWherePart( 'LCUWMaster.Operator','QOperator')
           + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0')";

	  withdrawFlag = false;
	}

	//alert(strSQL);
	//execEasyQuery( strSQL );
	  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有没通过续保核保的个人单！");
    return "";
  }

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
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;
}

//点击mutline单选框触发事件
function easyQueryAddClick(parm1,parm2)
{

	// 书写SQL语句
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //投保单所处状态
	var tProposalNo = "";
	var tPEdorNo = "";
	var strSQL = "";

	if(fm.all(parm1).all('InpPolGridSel').value == '1' ){
		//当前行第1列的值设为：选中
		tProposalNo = fm.all(parm1).all('PolGrid2').value;
		tPEdorNo = fm.all(parm1).all('PolGrid1').value;
	}

	fm.all('ContNo').value = fm.all(parm1).all('PolGrid2').value;
	fm.all('MissionID').value = fm.all(parm1).all('PolGrid7').value;
	fm.all('PrtNo').value = fm.all(parm1).all('PolGrid1').value;
	fm.all('SubMissionID').value = fm.all(parm1).all('PolGrid8').value;
	var ContNo = fm.all('ContNo').value;

	//alert("contno11="+fm.all('ContNo').value);
	//alert("PrtNo="+fm.all('PrtNo').value);

	if(state == "1")
	{
		checkDouble(tProposalNo);

	//生成工作流新节点
	}


	// 初始化表格
	initPolAddGrid();
	initPolBox();

	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	if (uwgrade == "1")
	{
		strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "+k+"="+k
				  + " and LCPol.Contno = '" + tProposalNo + "'";
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
	}
	else
	{
		strSQL = "select LCPol.ProposalNo,LCPol.MainPolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "+k+"="+k
				  + " and LCPol.contno = '" + tProposalNo + "'";
	}

	  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有没通过续保核保的个人单！");
    divLCPol1.style.display= "";
    divMain.style.display= "none";
    divLCPol2.style.display= "none";
    return "";
  }

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
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("查询easyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

  //alert("contno21="+fm.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+fm.all('PrtNo').value);

	var arrSelected = new Array();

	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('ProposalContNo').value = arrSelected[0][0];
	fm.all('PrtNo').value = arrSelected[0][1];
	fm.all('ManageCom').value = arrSelected[0][2];
	fm.all('SaleChnl').value = arrSelected[0][3];
	fm.all('AgentCode').value = arrSelected[0][4];
	fm.all('AgentGroup').value = arrSelected[0][5];
	fm.all('AgentCode1').value = arrSelected[0][6];
	fm.all('AgentCom').value = arrSelected[0][7];
	fm.all('AgentType').value = arrSelected[0][8];
	fm.all('ReMark').value = arrSelected[0][9];

	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo from lcappnt where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('AppntName').value = arrSelected[0][0];
	fm.all('AppntSex').value = arrSelected[0][1];
	fm.all('AppntBirthday').value = arrSelected[0][2];
	fm.all('AppntNo').value = arrSelected[0][3];
	fm.all('AddressNo').value = arrSelected[0][4];


  return true;
}


function displayEasyResult( arrResult )
{
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
function checkDouble(tProposalNo)
{
	fm.PolNoHide.value = tProposalNo;
	fm.action = "./ManuUWApply.jsp";
	fm.submit();

}

//选择要人工核保保单
function getPolGridCho()
{
	//setproposalno();

	var cSelNo = PolAddGrid.getSelNo()-1;

	codeSql = "code";
	fm.UWPopedomCode.value = "";
	fm.action = "./ManuUWCho.jsp";
	fm.submit();
}

function makeWorkFlow()
{
	fm.action = "./ManuUWChk.jsp";
	fm.submit();
}

function checkBackPol(ProposalNo) {
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
function manuchk()
{

	flag = fm.all('UWState').value;
	var ProposalNo = fm.all('ProposalNo').value;
	var MainPolNo = fm.all('MainPolNoHide').value;

	if (trim(fm.UWState.value) == "") {
    alert("必须先录入核保结论！");
    return;
  }

	if (!checkBackPol(ProposalNo)) {
	  if (!confirm("该投保单有撤单申请，继续下此结论吗？")) return;
	}

    if (trim(fm.UWState.value) == "6") {
      if(trim(fm.UWPopedomCode.value) !="")
         fm.UWOperater.value = fm.UWPopedomCode.value
      else
         fm.UWOperater.value = operator;
}

    var tSelNo = PolAddGrid.getSelNo()-1;

	if(fm.State.value == "1"&&(fm.UWState.value == "1"||fm.UWState.value == "2"||fm.UWState.value =="4"||fm.UWState.value =="6"||fm.UWState.value =="9"||fm.UWState.value =="a")) {
      if( showPolDetailFlag[tSelNo] == 0 || showAppFlag[tSelNo] == 0 || showHealthFlag[tSelNo] == 0 || QuestQueryFlag[tSelNo] == 0 ){
         var tInfo = "";
         if(showPolDetailFlag[tSelNo] == 0)
            tInfo = tInfo + " [投保单明细信息]";
         if(showAppFlag[tSelNo] == 0)
            tInfo = tInfo + " [既往投保信息]";
         if( PolAddGrid.getRowColData(tSelNo,1,PolAddGrid) == PolAddGrid.getRowColData(tSelNo ,2,PolAddGrid)) {
         	if(showHealthFlag[tSelNo] == 0)
              tInfo = tInfo + " [体检资料录入]";
         }
         if(QuestQueryFlag[tSelNo] == 0)
            tInfo = tInfo + " [问题件查询]";
         if ( tInfo != "" ) {
         	tInfo = "有重要信息:" + tInfo + " 未查看,是否要下该核保结论?";
         	if(!window.confirm( tInfo ))
         	    return;
             }

        }
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

	fm.action = "./UWManuNormChk.jsp";
	fm.submit();

	if (flag != "b"&&ProposalNo == MainPolNo)
	{
		initInpBox();
   		initPolBox();
		initPolGrid();
		initPolAddGrid();
	}
}

//function manuchk()
//{
//
//	flag = fm.all('UWState').value;
//	tUWIdea = fm.all('UWIdea').value;
//
//	//录入承保计划变更结论要区分主附险
//	if( flag == "b")
//	{
//		cProposalNo=fm.PolNoHide.value;
//	}
//	else
//	{
//		cProposalNo=fm.ProposalNo.value;
//	}
//
//	//alert("flag:"+flag);
//	if (cProposalNo == "")
//	{
//		alert("请先选择保单!");
//	}
//	else
//	{
//		if (flag == "0"||flag == "1"||flag == "4"||flag == "6"||flag == "9"||flag == "b")
//		{
//			showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//		}
//
//		if (flag == "2") //延期
//		{
//			//showModalDialog("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//
//		if (flag == "3") //条件承保
//		{
//			//showModalDialog("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//		if (flag == "7") //问题件录入
//		{
//			QuestInput();
//		}
//
//		if (flag != "b")
//		{
//		initInpBox();
//    		initPolBox();
//		initPolGrid();
//		initPolAddGrid();
//		}
//	}
//}

//问题件录入
function QuestInput()
{
	cProposalNo = fm.ProposalNo.value;  //保单号码

	var strSql = "select * from LCUWMaster where ProposalNo='" + cProposalNo + "' and ChangePolFlag ='1'";
    //alert(strSql);
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      var tInfo = "承保计划变更未回复,确认要录入新的问题件?";
   if(!window.confirm( tInfo )){
          return;
        }
      }
	window.open("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");

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
  cProposalNo = fm.ProposalNo.value;  //保单号码
  var cEdorNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 1);


  if (cProposalNo != "")
  {
	window.open("./RReportQueryMain.jsp?ProposalNo1="+cProposalNo);
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
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.edoruwstate.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}

//隐藏保险计划变更结论
function HideChangeResult()
{
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.edoruwstate.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
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
	divMain.style.display = "none";
	DivLCAppntInd.style.display="none";
	DivLCAppntIndButton.style.display="none";
	fm.reset();
	initForm();
}
