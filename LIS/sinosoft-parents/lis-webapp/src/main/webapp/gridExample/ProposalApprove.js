
//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
/*********************************************************************
 *  进行投保单复核提交
 ****************************************************************** */
function approvePol1()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == null || tSel == 0 )
	{
		alert("请选择一张投保单后，再进行复核操作");
	}
	else
	{
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
		
		showSubmitFrame(mDebug);
		fm.submit(); //提交
	}
}

/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	
	//解除印刷号的锁定
    var prtNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos=承保复核&PolState=1003&Action=DELETE";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
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
	showApproveDetail();
  // 刷新查询结果
	easyQueryClick2();
	easyQueryClickSelf();		
}

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if (cShow=="true")
		cDiv.style.display="";
	else
		cDiv.style.display="none";  
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  显示投保单明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showPolDetail()
{
	var i = 0;
	var checkFlag = 0;
	for (i=0; i<PolGrid.mulLineCount; i++) 
	{
		if (PolGrid.getSelNo(i)) 
		{ 
		checkFlag = PolGrid.getSelNo();
		break;
		}
	}
	if (checkFlag) 
	{ 
	var cPolNo = PolGrid.getRowColData( checkFlag - 1, 1 );
	mSwitch.deleteVar( "PolNo" );
	mSwitch.addVar( "PolNo", "", cPolNo );
	window.open("./ProposalMain.jsp?LoadFlag=6","window1",sFeatures);
	}
	else 
	{
	alert("请先选择一条保单信息！"); 
	}
}           

/*********************************************************************
 *  调用EasyQuery查询保单
 **************************************************************** */
//【查  询】按钮－－－－－－查询符合条件的工作池队列
function easyQueryClick()
{
	bg.addParam("ContNo",fm.ProposalNo.value,'like');
	bg.addParam("ManageCom",fm.ManageCom.value,'like');
	bg.addParam("AgentCode",fm.AgentCode.value,'like');
	bg.addParam("MakeDate",fm.AgentCode.value,'like');
	if(document.all("SaleChnl").value!=""){
		bg.addParam(null," and exists(select 'x' from lccont where lccont.contno= a.missionprop1 and lccont.salechnl='"+document.all("SaleChnl").value+"')",null);	
	}
	bg.queryData();
}

/*********************************************************************
 *  调用EasyQuery查询保单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function easyQueryClick2()
{
	// 初始化表格
	initPolGrid();
	var strOperate="like";
	// 书写SQL语句
	var strSql = "";           
	
		var sqlid2="ProposalApproveSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.ProposalNo.value);//指定传入的参数
        mySql2.addSubPara(fm.PrtNo.value);//指定传入的参数
        mySql2.addSubPara(fm.MakeDate.value);//指定传入的参数
 
        mySql2.addSubPara(fm.AgentCode.value);//指定传入的参数
        mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
        mySql2.addSubPara(ComCode);//指定传入的参数
	    strSql=mySql2.getString();	
	                
//	strSql = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionprop6,lwmission.Missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1"
//		+ " and activityid = '0000001001'"
//		+ " and processid = '0000000003'"
//		+ getWherePart('lwmission.MissionProp1','ProposalNo',strOperate)
//		+ getWherePart('lwmission.MissionProp2','PrtNo',strOperate)
//		+ getWherePart('lwmission.MissionProp6','MakeDate',strOperate)
//		+ getWherePart('lwmission.MissionProp7','AgentCode',strOperate)
//		+ getWherePart('lwmission.MissionProp8','ManageCom',strOperate)
//		+ " and LWMission.MissionProp8 like '" + ComCode + "%%'"  //集中权限管理体现
//		+ " and defaultoperator is null "
//		+ " order by modifydate asc,modifytime asc";	
	turnPage.strQueryResult = easyQueryVer3(strSql, 1, 1, 1);  	
	//判断是否查询成功
	if (!turnPage.strQueryResult) 
	{
	//alert("没有满足条件的数据！");
	return "";
	}
	turnPage.queryModal(strSql, PolGrid);
}


//【查询  待复核保单 队列】
function easyQueryClickSelf()
{

	initSelfPolGrid();
	var strSql = "";                  
	
		var sqlid3="ProposalApproveSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(Operator);//指定传入的参数

	    strSql=mySql3.getString();	
	           
//   	strSql = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionprop6,lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.MissionProp8,lwmission.MissionProp7 from lwmission where 1=1"
//   			 + " and activityid = '0000001001'"
//   			 + " and processid = '0000000003'"
//			 + " and defaultoperator ='" + Operator + "'"
//			 + " order by modifydate asc,modifytime asc";				   
	turnPage2.queryModal(strSql, SelfPolGrid);
}
//【申  请】按钮－－－－将工作任务从工作池申请到个人队列
function ApplyUW()
{

	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}
	fm.MissionID.value = PolGrid.getRowColData(selno, 6);
	fm.SubMissionID.value = PolGrid.getRowColData(selno, 7);
	fm.ActivityID.value = PolGrid.getRowColData(selno, 8);
	//09-06-06  增加校验如果本单已被申请到个人池则返回错误提示 刷新界面
/*	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001001'"
					+ " and defaultoperator is not null";
*/					
	    var sqlid10="ProposalApproveSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(fm.MissionID.value);//指定传入的参数
		mySql10.addSubPara(fm.SubMissionID.value);//指定传入的参数
	    tOperatorSql=mySql10.getString();
	    
	var tOperator = easyExecSql(tOperatorSql);	
	if(tOperator){
		alert("本单已被其他人员申请到个人工作池！");
		easyQueryClick2();
		easyQueryClickSelf();
		return false;
	}
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	fm.action = "ProposalApproveChk.jsp";
	fm.submit(); //提交
}

function showApproveDetail() 
{ 
	var selno = PolGrid.getSelNo() - 1;
	if (selno <0)
	{
	alert("请选择要复核的投保单！");
	return;
	}  
	var tMissionID = PolGrid.getRowColData(selno, 6);
	var tSubMissionID= PolGrid.getRowColData(selno, 7);
	var tActivityID = PolGrid.getRowColData(selno, 8); 
	var polNo = PolGrid.getRowColData(selno, 1);
	var prtNo = PolGrid.getRowColData(selno, 2);
	var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='承保录单' or CreatePos='承保复核') and (PolState='1002' or PolState='1003')";
	var arrResult = easyExecSql(strSql);
	if (arrResult!=null && arrResult[0][1]!=Operator) 
	{
	//alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
	//return;
	}
	//锁定该印刷号
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=承保复核&PolState=1003&Action=INSERT";
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //配合以前实现过的页面功能，源于ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	//alert(polNo);	
	mSwitch.deleteVar("ApprovePolNo");
	mSwitch.addVar("ApprovePolNo", "", polNo);
	mSwitch.updateVar("ApprovePolNo", "", polNo);
	//alert(mSwitch.getVar("ApprovePolNo"));
	var strSql1="";
	
		var sqlid9="ProposalApproveSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(prtNo);//指定传入的参数

	    strSql1=mySql9.getString();	
	
//	strSql1="select case lmriskapp.riskprop"
//		+" when 'I' then '1'"
//		+" when 'G' then '2'"
//		+" when 'Y' then '3'"
//		+" when 'T' then '5'"
//		+" when 'C' then '1'"
//		+" when 'B' then '2'"
//		+" else '1' end"
//		+" from lmriskapp where riskcode in "
//		+"(select riskcode from lcpol where polno = mainpolno and contno = '"+prtNo+"')";
	var arrResult1 = easyExecSql(strSql1);
	if (arrResult1==null) 
	{
	alert("查询投保单险种信息失败！");
	return false;
	}
    var BankFlag = arrResult1[0][0];//用该标志判断，进入不同的页面
    
		var sqlid4="ProposalApproveSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(prtNo);//指定传入的参数

	    var strSql2=mySql4.getString();	
	
	//var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	
	var  tSplitPrtNo = prtNo.substring(0,4);
    var BankFlag = "";
    var SubType = "";
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    	SubType = "03";
    }else{
    	BankFlag="1";
    	SubType = "01";
    }
//        window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo=" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 3) + "&ContNo=" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 2) + "&BankFlag=" + BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

	EasyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=5&prtNo="+prtNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&BankFlag="+BankFlag+"&SubType="+SubType+"&NoType=1", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);    
}  
//【选择点击待复核保单队列】响应函数，进入复核页面
function InitshowApproveDetail() 
{ 
	var selno = SelfPolGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("请选择要复核的投保单！");
	      return;
	}  
	var tMissionID = SelfPolGrid.getRowColData(selno, 6);
	var tSubMissionID= SelfPolGrid.getRowColData(selno, 7);
	var ActivityID = SelfPolGrid.getRowColData(selno, 8);
	var polNo = SelfPolGrid.getRowColData(selno, 1);
	var prtNo = SelfPolGrid.getRowColData(selno, 2);
	var NoType = "1";
	// var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='承保录单' or CreatePos='承保复核') and (PolState='1002' or PolState='1003')";
	// var arrResult = easyExecSql(strSql);
	// if (arrResult!=null && arrResult[0][1]!=Operator) {
	//   alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
	//   return;
	// }
	//锁定该印刷号
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=承保复核&PolState=1003&Action=INSERT";
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=0;      //弹出窗口的宽度; 
	var iHeight=0;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   //配合以前实现过的页面功能，源于ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	//alert(polNo);	
	mSwitch.deleteVar("ApprovePolNo");
	mSwitch.addVar("ApprovePolNo", "", polNo);
	mSwitch.updateVar("ApprovePolNo", "", polNo);
	//alert(mSwitch.getVar("ApprovePolNo"));
	var strSql1="";
	
			var sqlid5="ProposalApproveSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(polNo);//指定传入的参数

	    strSql1=mySql5.getString();	
	
//	strSql1="select case lmriskapp.riskprop"
//		+" when 'I' then '1'"
//		+" when 'G' then '2'"
//		+" when 'Y' then '3'"
//		+" when 'T' then '5'"
//		+" when 'C' then '1'"
//		+" when 'B' then '2'"
//		+" else '1' end"
//		+" from lmriskapp where riskcode in "
//		+"(select riskcode from lcpol where polno = mainpolno and contno = '"+polNo+"')";
	//prompt("strSql1",strSql1);
	var arrResult1 = easyExecSql(strSql1);
	if (arrResult1==null) 
	{
	alert("查询投保单险种信息失败！");
	return false;
	}
    var BankFlag = arrResult1[0][0];//用该标志判断，进入不同的页面
  
  		var sqlid6="ProposalApproveSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(polNo);//指定传入的参数

	    var strSql2=mySql6.getString();	
  
	//var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	
	var  tSplitPrtNo = polNo.substring(0,4);
    var BankFlag = "";
    var SubType = "";
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    	SubType = "03";
    }else{
    	BankFlag="1";
    	SubType = "01";
    }
    
//    alert("BankFlag"+BankFlag);
//    alert("SubType"+SubType);
    
	easyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=5&prtNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType=TB10"+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
} 

/*********************************************************************
 *  进行投保单复核提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function passApprovePol() 
{
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

    //提交
    var polNo = mSwitch.getVar("ApprovePolNo");
    //alert(polNo);
	window.top.fraSubmit.window.location = "./ProposalApproveSave.jsp?polNo="+polNo+"&approveFlag=9";    
}

function refuseApprovePol() 
{
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		
	showSubmitFrame(mDebug);
    //提交
     var polNo = mSwitch.getVar("ApprovePolNo");
	window.top.fraSubmit.window.location = "./ProposalApproveSave.jsp?polNo="+polNo+"&approveFlag=1";    
}

//************************
var cflag = "5";          //问题件操作位置 5.复核

function QuestInput()
{
	
	cProposalNo = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 1);  //保单号码
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uw/QuestInputMain.jsp?ContNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	
	//initInpBox();
    	//initPolBox();
	//initPolGrid();
	
}

function QuestReply()
{
	cProposalNo = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 1);  //保单号码
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uw/QuestReplyMain.jsp?ContNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	
	//initInpBox();
  //initPolBox();
	//initPolGrid();
	
}

function QuestQuery()
{
	cProposalNo = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 1);  //保单号码
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	
	//initInpBox();
  //initPolBox();
	//initPolGrid();
	
}


function returnparent()
  {   
  	var backstr=document.all("ContNo").value;    
  	//alert(backstr+"backstr");   
  	mSwitch.deleteVar("ContNo");               
	mSwitch.addVar("ContNo", "", backstr);     
	mSwitch.updateVar("ContNo", "", backstr); 
  	location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType; 
}        

function showNotePad()
{

	var selno = SelfPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = SelfPolGrid.getRowColData(selno, 6);
	var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
	var ActivityID = SelfPolGrid.getRowColData(selno, 8);
	var PrtNo = SelfPolGrid.getRowColData(selno, 2);
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}
	var NoType = "1";
			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}



function queryAgent()
{
	if(document.all('AgentCode').value == "")	
	{
	//tongmeng 2007-09-10 modify 因为银代复核也使用该菜单，所以去掉对代理人展业类型的限制
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	//var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	//var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
    else
	{
		var cAgentCode = fm.AgentCode.value;  //保单号码
		//tongmeng 2007-09-10 modify 因为银代复核也使用该菜单，所以去掉对代理人展业类型的限制
		
		 var sqlid7="ProposalApproveSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.ProposalApproveSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(cAgentCode);//指定传入的参数
	    var strSQL=mySql7.getString();	
		
//		var strSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name from laagent a,latree b,labranchgroup c where 1=1 "
//		     + "and a.agentcode = b.agentcode " 
//		     + " and a.agentstate!='03' and a.agentstate!='04' and a.agentgroup = c.agentgroup and a.agentcode='"+cAgentCode+"'";
		var arrResult = easyExecSql(strSQL);
		if (arrResult != null) 
		{
			fm.AgentCode.value = arrResult[0][0];
		}
		else
		{
			
			alert("编码为:["+document.all('AgentCode').value+"]的业务员不存在，请确认!");
			fm.AgentCode.value="";
			return;
		}
	}
}

function afterQuery2(arrResult)
{
	if(arrResult!=null)
	{
  	//fm.AgentCode.value = arrResult[0][0];
  	//fm.BranchAttr.value = arrResult[0][93];
  	//fm.AgentGroup.value = arrResult[0][1];
  	//fm.AgentName.value = arrResult[0][5];
  	//fm.AgentManageCom.value = arrResult[0][2];
	fm.AgentCode.value = arrResult[0][0];
	}
}