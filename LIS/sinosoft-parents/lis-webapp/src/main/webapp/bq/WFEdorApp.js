//程序名称：WFEdorApp.js
//程序功能：保全工作流-保全申请
//创建日期：2005-04-27 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql=new SqlClass();
/*********************************************************************
 *  查询工作流共享池
 *  描述:查询工作流共享池
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickAll()
{
	// 初始化表格
	initAllGrid();

	// 书写SQL语句
	var strSQL = "";
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.WFEdorApp");
    mySql.setSqlId("WFEdorAppSql2");
    mySql.addSubPara(curDay);
    mySql.addSubPara(fm.EdorAcceptNo.value);
    mySql.addSubPara(fm.ManageCom.value);
    mySql.addSubPara(fm.InputDate.value);
		mySql.addSubPara(manageCom);
		
	turnPage3.queryModal(mySql.getString(), AllGrid);
	
	HighlightAllRow();
	return true;
}

/*********************************************************************
 *  查询我的任务队列
 *  描述: 查询我的任务队列
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickSelf()
{
	// 初始化表格
	initSelfGrid();
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.WFEdorApp");
    mySql.setSqlId("WFEdorAppSql1");
    mySql.addSubPara(curDay);
    mySql.addSubPara(operator);
    	
	turnPage2.queryModal(mySql.getString(), SelfGrid);
	HighlightSelfRow();
	return true;
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PublicWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PrivateWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightAllRow(){
		for(var i=0; i<AllGrid.mulLineCount; i++){
  	var days = AllGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<SelfGrid.mulLineCount; i++){
  	var days = SelfGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
/*********************************************************************
 *  跳转到具体的业务处理页面
 *  描述: 跳转到具体的业务处理页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function GoToBusiDeal()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	var tPrtNo = PrivateWorkPoolGrid.getRowColData(selno, 10);
	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 13);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 14);		
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 16);
	var tDefaultOperator = PrivateWorkPoolGrid.getRowColData(selno, 11);
	var tLoadFlag = "scanApp";
	if(tDefaultOperator=='' || tDefaultOperator==null)
	{
		tLoadFlag='approveModify';
	}
	var varSrc = "&ScanFlag=1&prtNo=" + tPrtNo + "&ActivityID=" + tActivityID + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	
	//var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	var newWindow = OpenWindowNew("../bq/PEdorScanAppMain.jsp?" + varSrc,"保全扫描申请受理","left");

}
 /*
function GoToBusiDeal()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	var tPrtNo = SelfGrid.getRowColData(selno, 1);
	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 2);
	var tMissionID = SelfGrid.getRowColData(selno, 5);
	var tSubMissionID = SelfGrid.getRowColData(selno, 6);		
	var tActivityID = SelfGrid.getRowColData(selno, 7);
		var tDefaultOperator = SelfGrid.getRowColData(selno, 9);
	var tLoadFlag = "scanApp";
	if(tDefaultOperator=='' || tDefaultOperator==null)
	{
		tLoadFlag='approveModify';
	}
	var varSrc = "&ScanFlag=1&prtNo=" + tPrtNo + "&ActivityID=" + tActivityID + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	
	//var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	var newWindow = OpenWindowNew("../bq/PEdorScanAppMain.jsp?" + varSrc,"保全扫描申请受理","left");

}
*/
/*********************************************************************
 *  影像复制
 *  描述: 影像复制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function GoToImageCopy()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	
//	var tPrtNo = SelfGrid.getRowColData(selno, 1);
//	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 2);
//	var tMissionID = SelfGrid.getRowColData(selno, 5);
//	var tSubMissionID = SelfGrid.getRowColData(selno, 6);		
//	var tLoadFlag = "scanApp";
//	var varSrc = "&ScanFlag=1&prtNo=" + tPrtNo + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	
	//var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	//var newWindow = OpenWindowNew("../bq/WFEdorAppImageCopy.jsp?" + varSrc,"复制影像","left");

	var showStr="正在复制影像，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "../bq/WFEdorAppImageCopy.jsp?EdorAcceptNo=" + tEdorAcceptNo;
	document.getElementById("fm").submit(); //提交
}
 /*
function GoToImageCopy()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	
	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 2);
	
//	var tPrtNo = SelfGrid.getRowColData(selno, 1);
//	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 2);
//	var tMissionID = SelfGrid.getRowColData(selno, 5);
//	var tSubMissionID = SelfGrid.getRowColData(selno, 6);		
//	var tLoadFlag = "scanApp";
//	var varSrc = "&ScanFlag=1&prtNo=" + tPrtNo + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	
	//var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	//var newWindow = OpenWindowNew("../bq/WFEdorAppImageCopy.jsp?" + varSrc,"复制影像","left");

	var showStr="正在复制影像，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  

	fm.action = "../bq/WFEdorAppImageCopy.jsp?EdorAcceptNo=" + tEdorAcceptNo;
	document.getElementById("fm").submit(); //提交
}
*/
/*********************************************************************
 *  申请任务
 *  描述: 申请任务
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function applyMission()
{

	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的任务！");
	      return;
	}
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 14);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 16);
	
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("选择任务为空！");
	      return;
	}
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //提交
}
/* 
function applyMission()
{

	var selno = AllGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的任务！");
	      return;
	}
	fm.MissionID.value = AllGrid.getRowColData(selno, 5);
	fm.SubMissionID.value = AllGrid.getRowColData(selno, 6);
	fm.ActivityID.value = AllGrid.getRowColData(selno, 7);
	
	if (fm.MissionID.value == null || fm.MissionID.value == "")
	{
	      alert("选择任务为空！");
	      return;
	}
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //提交
}
*/
/*********************************************************************
 *  后台执行完毕反馈信息
 *  描述: 后台执行完毕反馈信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();

	if (FlagStr == "Succ" )	
	{
	  	//刷新查询结果
		//easyQueryClickAll();
		//easyQueryClickSelf();	
		jQuery("#publicSearch").click();	
		jQuery("#privateSearch").click();	
	}
    else
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
    }
}

/*********************************************************************
 *  打开工作流记事本查看页面
 *  描述: 打开工作流记事本查看页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function showNotePad()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	//begin zbx 20110511 
	//var MissionID = SelfGrid.getRowColData(selno, 4);
	//var SubMissionID = SelfGrid.getRowColData(selno, 5);
	//var ActivityID = SelfGrid.getRowColData(selno, 6);
    var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 13);
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 14);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 16);
	//end zbx 20110511 
	
	var OtherNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var OtherNoType = "1";
	
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}
	if(OtherNo==null || OtherNo== "")
	{
		mySql=new SqlClass();
    	mySql.setResourceName("bq.WFEdorApp");
    	mySql.setSqlId("WFEdorAppSql3");
    	mySql.addSubPara(MissionID);
    	OtherNo=easyExecSql(mySql.getString());
	}	
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	

}
 /*
function showNotePad()
{
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	//begin zbx 20110511 
	//var MissionID = SelfGrid.getRowColData(selno, 4);
	//var SubMissionID = SelfGrid.getRowColData(selno, 5);
	//var ActivityID = SelfGrid.getRowColData(selno, 6);
    var MissionID = SelfGrid.getRowColData(selno, 5);
	var SubMissionID = SelfGrid.getRowColData(selno, 6);
	var ActivityID = SelfGrid.getRowColData(selno, 7);
	//end zbx 20110511 
	
	var OtherNo = SelfGrid.getRowColData(selno, 1);
	var OtherNoType = "1";
	
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}
	if(OtherNo==null || OtherNo== "")
	{
		mySql=new SqlClass();
    	mySql.setResourceName("bq.WFEdorApp");
    	mySql.setSqlId("WFEdorAppSql3");
    	mySql.addSubPara(MissionID);
    	OtherNo=easyExecSql(mySql.getString());
	}	
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	

}
*/
