//程序名称：MasterCenter.js
//程序功能：管理中心
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var cflag = "4";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// 数据返回父窗口
//function returnParent()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "请先选择一条记录，再点击返回按钮。" );
//	else
//	{
//		try
//		{
//			arrReturn = getQueryResult();
//			top.opener.afterQuery( arrReturn );
//		}
//		catch(ex)
//		{
//			alert( "没有发现父窗口的afterQuery接口。" + ex );
//		}
//		top.close();
//	}
//}

// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass(); 
var queryBug = 1;
//function initQuery() {
//	// 初始化表格
//	//initPolGrid();
//	 
//	// 书写SQL语句
///*	var strSql = "select ProposalNo,PrtNo,RiskCode,AppntName,InsuredName from LCPol where " + ++queryBug + "=" + queryBug
//    				 + " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pi')=1 "
//    				 + " and AppFlag='0' "                 //个人保单
//    				 + " and GrpPolNo='" + grpPolNo + "'"  //集体单号，必须为20个0
//    				 + " and ContNo='" + contNo + "'"      //内容号，必须为20个0
//    				 + " and ManageCom like '" + comcode + "%%'"
//    				 + " and uwflag <> '0'"
//    				 //+ " and uwflag = '7'"
//    				 + " and approveflag = '2'"
//    				 //+ " and polno in (select proposalno from lcuwmaster where printflag in ('1','2'))"
//    				 + " and PolNo in (select distinct proposalno from lcissuepol where ReplyResult is null and backobjtype = '4')";
//*/
//
//		 var sqlid1="MasterCenter1";
//		var mySql1=new SqlClass();
//		mySql1.setResourceName("uw.MasterCenterSql"); //指定使用的properties文件名
//		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
////		mySql1.addSubPara(tContNo);//指定传入的参数
//	    var strSql =mySql1.getString();	
//
////		var strSql = " select missionprop1,missionprop3,missionid,submissionid,missionprop4 "
////							 + " from lwmission where activityid='0000001020' and processid='0000000003' ";
////							 + " and defaultoperator is null";
//  //alert(strSql);
//	turnPage.queryModal(strSql, PolGrid);
//}

//var mSwitch = parent.VD.gVSwitch;
//function modifyClick() {
//  var i = 0;
//  var checkFlag = 0;
//  
//  for (i=0; i<PolGrid.mulLineCount; i++) {
//    if (PolGrid.getSelNo(i)) { 
//      checkFlag = PolGrid.getSelNo();
//      break;
//    }
//  }
//  
//  if (checkFlag) { 
//  	var cPolNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
//  	mSwitch.deleteVar( "PolNo" );
//  	mSwitch.addVar( "PolNo", "", cPolNo );
//  	
//    urlStr = "./ProposalMain.jsp?LoadFlag=3";
//    window.open(urlStr,"",sFeatures);
//  }
//  else {
//    alert("请先选择一条保单信息！"); 
//  }
//}

//function QuestQuery()
//{
//	  var i = 0;
//	  var checkFlag = 0;
//	  var cProposalNo = "";
//	  var cMissionId = "";
//	  var cSubMissionId = "";
//	  
//	  for (i=0; i<SelfPolGrid.mulLineCount; i++) {
//	    if (SelfPolGrid.getSelNo(i)) { 
//	      checkFlag = SelfPolGrid.getSelNo();
//	      break;
//	    }
//	  }
//	  //QuestInputMain.jsp?ContNo="+cProposalNo+"&Flag=5&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020
//	  if (checkFlag > 0) { 
//	  	cProposalNo = SelfPolGrid.getRowColData(checkFlag - 1, 1); 	
//	  	cMissionId  = SelfPolGrid.getRowColData(checkFlag - 1, 3); 	
//	  	cSubMissionId = SelfPolGrid.getRowColData(checkFlag - 1, 4); 	
//	  	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020&MasterCenter=Y","window1",sFeatures);
//	  }
//	  else {
//	    alert("请先选择一条保单信息！"); 	    
//	  }
//	
//	
//	
//}

//【查  询】按钮－－－－－－查询符合条件的工作池队列
/*function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	var strOperate="like";
	// 书写SQL语句
	var strSql = "";
	var addStr = " and 1=1 ";               

		 var sqlid2="MasterCenter2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.MasterCenterSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.ComCode.value);//指定传入的参数
		mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql2.addSubPara(fm.PrtNo.value);//指定传入的参数
//	    strSql=mySql2.getString();	


//	 strSql = " select missionprop1,missionprop3,missionid,submissionid,ActivityID "
//							 + " from lwmission where activityid='0000001020' and processid='0000000003' "
//							 + " and defaultoperator is null"
//							 + " and exists "
//						           +" (select 1 from lccont where ContNo=missionprop1 "
//						           + getWherePart('ManageCom','ComCode',strOperate)
//						           + getWherePart('ManageCom','ManageCom','like')
//						           +" )"		
//               +  getWherePart('MissionProp2','PrtNo');
       if (trim(fm.ManageCom.value) == "" || fm.ManageCom.value == null)
           addStr = addStr;
       else
				    addStr = " and exists "
				        +" (select 1 from lccont where ContNo=missionprop1 and ManageCom='"+fm.all('ManageCom').value+"')";		
	
	mySql2.addSubPara(addStr);//指定传入的参数
	strSql=mySql2.getString();	
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查询到满足条件的数据！");
    return false;
    }
//查询成功则拆分字符串，返回二维数组
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = arrDataSet;
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}
*/
////【申  请】按钮－－－－将工作任务从工作池申请到个人队列
//function ApplyUW()
//{   
//
//	var selno = PolGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("请选择要申请的投保单！");
//	      return;
//	}
//	fm.MissionID.value = PolGrid.getRowColData(selno, 3);
//	fm.SubMissionID.value = PolGrid.getRowColData(selno, 4);
//	fm.ActivityID.value = PolGrid.getRowColData(selno, 5);
//	fm.hiddenContNo.value =  PolGrid.getRowColData(selno, 1);
//	//alert("aaaaaaa--->"+PolGrid.getRowColData(selno, 5));
//	//09-06-06  增加校验如果本单已被申请到个人池则返回错误提示 刷新界面
//	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
//					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001020'"
//					+ " and defaultoperator is not null";
//	var tOperator = easyExecSql(tOperatorSql);
//	if(tOperator){
//		alert("本单已被其他人员申请到个人工作池！");
//		easyQueryClickSelf();		
//		easyQueryClick();
//		return false;
//	}
//	var i = 0;
//	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//	lockScreen('lkscreen');  
//	fm.action = "MasterCenterChk.jsp";
//	document.getElementById("fm").submit(); //提交
//}
//
//
//
///*********************************************************************
// *  投保单复核的提交后的操作,服务器数据返回后执行的操作
// *  参数  ：  无
// *  返回值：  无
// *********************************************************************
// */
//function afterSubmit( FlagStr, content )
//{
//	unlockScreen('lkscreen');  
//	showInfo.close();
//	
//	//解除印刷号的锁定
//  var prtNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
//  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos=承保复核&PolState=1003&Action=DELETE";
//  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
//	
//	if (FlagStr == "Fail" )
//	{             
//		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//		return;
//	}
//  // 刷新查询结果
//	easyQueryClickSelf();		
//	easyQueryClick();
//	//打开新窗口
//		  var	cProposalNo = fm.hiddenContNo.value;
//	  	var cMissionId  = fm.MissionID.value; 	
//	  	var cSubMissionId = fm.SubMissionID.value; 	
//	  	var ActivityID = fm.ActivityID.value;
//	  	
//	  	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID="+ActivityID+"&MasterCenter=Y","window1",sFeatures);
//}


//modify by lzf

var mSwitch = parent.VD.gVSwitch;
function modifyClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<ManPublicPoolGrid.mulLineCount; i++) {
    if (ManPublicPoolGrid.getSelNo(i)) { 
      checkFlag = ManPublicPoolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cPolNo = ManPublicPoolGrid.getRowColData(checkFlag - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	
    urlStr = "./ProposalMain.jsp?LoadFlag=3";
    window.open(urlStr,"",sFeatures);
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
}

function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cProposalNo = "";
	  var cMissionId = "";
	  var cSubMissionId = "";
	  var cActivityId = "";
	  
	  for (i=0; i<ManPrivatePoolGrid.mulLineCount; i++) {
	    if (ManPrivatePoolGrid.getSelNo(i)) { 
	      checkFlag = ManPrivatePoolGrid.getSelNo();
	      break;
	    }
	  }	 
	  //QuestInputMain.jsp?ContNo="+cProposalNo+"&Flag=5&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020
	  if (checkFlag > 0) { 
	  	cProposalNo = ManPrivatePoolGrid.getRowColData(checkFlag - 1, 1); 	
	  	cMissionId  = ManPrivatePoolGrid.getRowColData(checkFlag - 1, 5); 	
	  	cSubMissionId = ManPrivatePoolGrid.getRowColData(checkFlag - 1, 6); 
	  	cActivityId = ManPrivatePoolGrid.getRowColData(checkFlag - 1, 8);
	  	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID="+cActivityId+"&MasterCenter=Y","window1",sFeatures);
	  }
	  else {
	    alert("请先选择一条保单信息！"); 	    
	  }
	
	
	
}
//数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = ManPublicPoolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		top.close();
	}
}

//【申  请】按钮－－－－将工作任务从工作池申请到个人队列
function ApplyUW()
{   

	var selno = ManPublicPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}	
	fm.MissionID.value = ManPublicPoolGrid.getRowColData(selno, 5);
	fm.SubMissionID.value = ManPublicPoolGrid.getRowColData(selno, 6);
	fm.ActivityID.value = ManPublicPoolGrid.getRowColData(selno, 8);
	fm.hiddenContNo.value =  ManPublicPoolGrid.getRowColData(selno, 1);
	//alert("aaaaaaa--->"+PolGrid.getRowColData(selno, 5));
	//09-06-06  增加校验如果本单已被申请到个人池则返回错误提示 刷新界面
//	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
//					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid in (select a.activityid from lwactivity a where a.functionid = '10010010')"
//					+ " and defaultoperator is not null";
	
	var  sqlid1="MasterCenter4";
 	var  mySql1=new SqlClass();
 	mySql1.setResourceName("uw.MasterCenterSql"); //指定使用的properties文件名
 	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 	mySql1.addSubPara(fm.MissionID.value);//指定传入的参数
 	mySql1.addSubPara(fm.SubMissionID.value);//指定传入的参数
 	var tOperatorSql=mySql1.getString();
	var tOperator = easyExecSql(tOperatorSql);
	if(tOperator){
		alert("本单已被其他人员申请到个人工作池！");
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
		return false;
	}
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	lockScreen('lkscreen');  
	fm.action = "MasterCenterChk.jsp";
	document.getElementById("fm").submit(); //提交
}



/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
	
	//解除印刷号的锁定
  var prtNo = ManPublicPoolGrid.getRowColData(ManPublicPoolGrid.getSelNo()-1, 2);
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos=承保复核&PolState=1003&Action=DELETE";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		return;
	}
  // 刷新查询结果
	jQuery("#privateSearch").click();
	jQuery("#publicSearch").click();
	//打开新窗口
		  var	cProposalNo = fm.hiddenContNo.value;
	  	var cMissionId  = fm.MissionID.value; 	
	  	var cSubMissionId = fm.SubMissionID.value; 	
	  	var ActivityID = fm.ActivityID.value;
	  	
	  	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID="+ActivityID+"&MasterCenter=Y","window1",sFeatures);
}
