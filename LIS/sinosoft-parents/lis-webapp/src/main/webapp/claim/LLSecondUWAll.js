//程序名称：LLSecondUWAllAll.js
//程序功能：理赔人工核保获取队列
//创建日期：2005-1-28 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人 yuejw   更新日期     更新原因/内容
var showInfo;
var mDebug="0";
var flag;  
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//查询按钮------查询[理赔人工核保队列]
function queryClick()
{
	initLLCUWBatchGridQuery();
}
//重置按钮------重新填写查询条件
//function cancleClick()
//{
//	fm.QCaseNo.value="";
//	fm.QInsuredNo.value="";
//	fm.QInsuredName.value="";
//	fm.QClaimRelFlag.value="";
//	fm.QClaimRelFlagName.value="";
//	
//}

//重置按钮------重新填写查询条件-----20130608 lzf
function cancleClick()
{
	PublicWorkPoolQueryGrid.setRowColData(0, 2,"");
	PublicWorkPoolQueryGrid.setRowColData(0, 4,"");
	PublicWorkPoolQueryGrid.setRowColData(0, 5,"");
	PublicWorkPoolQueryGrid.setRowColData(0, 7,"");
}

//查询[理赔人工核保队列]------共享池
function initLLCUWBatchGridQuery()
{

//	var strSQL = "select missionprop1,missionprop2,"
//		   + " (select decode(sum(count(distinct batno)),"
//		   + "	'',"
//		   + "' ',"
//		   + " sum(count(distinct batno)))"
//		   + " from llcuwbatch"
//		   + " where caseno = a.missionprop1"
//		   + " group by batno),"
//		   + " '','',missionprop9,missionprop4,"
//	       + " makedate,maketime,missionprop3,missionprop20, "
//	       + " missionid,submissionid,activityid,missionprop5"
//		   + "  from lwmission a where 1=1 "
//           + " and activityid = '0000005505' "
//           + " and processid = '0000000005'"
//           + " and DefaultOperator is null"
//           + " and activitystatus = '1' "
//           + getWherePart( 'missionprop1' ,'QCaseNo')
//           + getWherePart( 'missionprop2' ,'BatNo')
//           + getWherePart( 'missionprop3' ,'QInsuredNo')
//           + getWherePart( 'missionprop4' ,'QInsuredName','like')
//           + getWherePart( 'missionprop5' ,'QClaimRelFlag')
//           + getWherePart( 'makedate' ,'theCurrentDate','<=')
//           + getWherePart( 'missionprop20' ,'ManageCom','like')  //<当前机构>
//           + " order by missionprop2";	
		   
		   
		var sqlid1="LLSecondUWAllSQL1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("claim.LLSecondUWAllSQL"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.QCaseNo.value);//指定传入的参数
		mySql1.addSubPara(fm.BatNo.value);//指定传入的参数
		mySql1.addSubPara(fm.QInsuredNo.value);//指定传入的参数
		
		mySql1.addSubPara(fm.QInsuredName.value);//指定传入的参数
		mySql1.addSubPara(fm.QClaimRelFlag.value);//指定传入的参数
		mySql1.addSubPara(fm.theCurrentDate.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
	    var strSQL =mySql1.getString();	
		   
		   
		   
		   
    turnPage.pageLineNum=10;
	turnPage.queryModal(strSQL, LLCUWBatchGrid);
}

//查询[理赔人工核保队列]------个人队列
function initSelfLLCUWBatchGridQuery() {
	
		var sqlid2="LLSecondUWAllSQL2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("claim.LLSecondUWAllSQL"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.Operator.value);//指定传入的参数
		mySql2.addSubPara(fm.QCaseNo2.value);//指定传入的参数
		mySql2.addSubPara(fm.BatNo2.value);//指定传入的参数
		mySql2.addSubPara(fm.QInsuredNo2.value);//指定传入的参数
		
		mySql2.addSubPara(fm.QInsuredName2.value);//指定传入的参数
		mySql2.addSubPara(fm.QClaimRelFlag2.value);//指定传入的参数
		mySql2.addSubPara(fm.theCurrentDate2.value);//指定传入的参数
		mySql2.addSubPara(fm.ManageCom2.value);//指定传入的参数
	    var strSQL =mySql2.getString();	
	
	
//	var strSQL = "select missionprop1,missionprop2,"
//		   + " (select decode(sum(count(distinct batno)),"
//		   + "	'',"
//		   + "' ',"
//		   + " sum(count(distinct batno)))"
//		   + " from llcuwbatch"
//		   + " where caseno = a.missionprop1"
//		   + " group by batno),"
//		   + " '','',missionprop9,missionprop4,"
//	       + " makedate,maketime,missionprop3,missionprop20, "
//	       + " missionid,submissionid,activityid,missionprop5"
//		   + "  from lwmission a where 1=1 "
//           + " and activityid = '0000005505' "
//           + " and processid = '0000000005'"
//           + " and activitystatus = '1' "
//           + " and DefaultOperator ='"+fm.Operator.value+"'"
//           + getWherePart( 'missionprop1' ,'QCaseNo2')
//           + getWherePart( 'missionprop2' ,'BatNo2')
//           + getWherePart( 'missionprop3' ,'QInsuredNo2')
//           + getWherePart( 'missionprop4' ,'QInsuredName2','like')
//           + getWherePart( 'missionprop5' ,'QClaimRelFlag2')
//           + getWherePart( 'makedate' ,'theCurrentDate2','<=')
//           + getWherePart( 'missionprop20' ,'ManageCom2','like')  //<当前机构>
//           + " order by missionprop2";	//prompt("",strSQL);
    turnPage2.pageLineNum=10;
	turnPage2.queryModal(strSQL, SelfLLCUWBatchGrid);
}

//[申请任务]按钮
//function applyClick()
//{
//	//09-06-02  增加当前操作员是否有核保权限的校验
//	
//		var sqlid3="LLSecondUWAllSQL3";
//		var mySql3=new SqlClass();
//		mySql3.setResourceName("claim.LLSecondUWAllSQL"); //指定使用的properties文件名
//		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
//		mySql3.addSubPara(fm.Operator.value);//指定传入的参数
//
//	    var tSql =mySql3.getString();	
//	
////	var tSql = "select 1 from lduwuser where usercode='"+fm.Operator.value+"' and uwtype='1'";
//	var arr = easyExecSql(tSql);
//	if(!arr){
//		alert("当前用户没有核保权限！");
//		return false;
//	}
//	var tSel = LLCUWBatchGrid.getSelNo()-1;
//	if(tSel<0)
//	{
//		alert("请选择一条任务记录");
//		return;	
//	}
//	fm.MissionID.value = LLCUWBatchGrid.getRowColData(tSel, 12);//alert("fm.MissionID.value"+fm.MissionID.value);
//	fm.SubMissionID.value=LLCUWBatchGrid.getRowColData(tSel, 13);//alert("fm.SubMissionID.value"+fm.SubMissionID.value);
//	fm.ActivityID.value= LLCUWBatchGrid.getRowColData(tSel, 14);//alert("fm.ActivityID.value"+fm.ActivityID.value);
//	fm.SBatNo.value=LLCUWBatchGrid.getRowColData(tSel, 2);//alert("fm.ActivityID.value"+fm.ActivityID.value);
//	fm.SCaseNo.value=LLCUWBatchGrid.getRowColData(tSel, 1);//alert("fm.ActivityID.value"+fm.ActivityID.value);
//	//09-06-06  增加校验如果本单已被申请到个人池则返回错误提示 刷新界面
//	
//		var sqlid4="LLSecondUWAllSQL4";
//		var mySql4=new SqlClass();
//		mySql4.setResourceName("claim.LLSecondUWAllSQL"); //指定使用的properties文件名
//		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
//		mySql4.addSubPara(fm.MissionID.value);//指定传入的参数
//		mySql4.addSubPara(fm.SubMissionID.value);//指定传入的参数
//
//	    var tOperatorSql =mySql4.getString();	
//	
////	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
////					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000005505'"
////					+ " and defaultoperator is not null";
//	var tOperator = easyExecSql(tOperatorSql);
//	if(tOperator){
//		alert("本单已被其他人员申请到个人工作池！");
//		initLLCUWBatchGridQuery();
//		initSelfLLCUWBatchGridQuery();
//		return false;
//	}
//	fm.action = "./LLSecondUWAllSave.jsp";
//	submitForm(); //提交
//}

//modify by lzf 20130608
function applyClick()
{
	//09-06-02  增加当前操作员是否有核保权限的校验
	
		var sqlid3="LLSecondUWAllSQL3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("claim.LLSecondUWAllSQL"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(fm.Operator.value);//指定传入的参数

	    var tSql =mySql3.getString();	

	var arr = easyExecSql(tSql);
	if(!arr){
		alert("当前用户没有核保权限！");
		return false;
	}
	var tSel = PublicWorkPoolGrid.getSelNo()-1;
	if(tSel<0)
	{
		alert("请选择一条任务记录");
		return;	
	}
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(tSel, 12);//alert("fm.MissionID.value"+fm.MissionID.value);
	fm.SubMissionID.value=PublicWorkPoolGrid.getRowColData(tSel, 13);//alert("fm.SubMissionID.value"+fm.SubMissionID.value);
	fm.ActivityID.value= PublicWorkPoolGrid.getRowColData(tSel, 15);//alert("fm.ActivityID.value"+fm.ActivityID.value);
	fm.SBatNo.value=PublicWorkPoolGrid.getRowColData(tSel, 2);//alert("fm.SBatNo.value"+fm.SBatNo.value);
	fm.SCaseNo.value=PublicWorkPoolGrid.getRowColData(tSel, 1);//alert("fm.SCaseNo.value"+fm.SCaseNo.value);return false;
	//09-06-06  增加校验如果本单已被申请到个人池则返回错误提示 刷新界面
	
		var sqlid4="LLSecondUWAllSQL4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("claim.LLSecondUWAllSQL"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(fm.MissionID.value);//指定传入的参数
		mySql4.addSubPara(fm.SubMissionID.value);//指定传入的参数

	    var tOperatorSql =mySql4.getString();	

	var tOperator = easyExecSql(tOperatorSql);
	if(tOperator){
		alert("本单已被其他人员申请到个人工作池！");
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
		return false;
	}
	fm.action = "./LLSecondUWAllSave.jsp"; 
	submitForm(); //提交
}

/*******************选中个人队列的一条记录，  描述:进入核保界面**************** */
//function SelfLLCUWBatchGridClick()
//{
//	var tSel = SelfLLCUWBatchGrid.getSelNo()-1;
//	var tCaseNo = SelfLLCUWBatchGrid.getRowColData(tSel,1); //赔案号
//	var tBatNo = SelfLLCUWBatchGrid.getRowColData(tSel,2); 	//批次号
//	var tInsuredNo = SelfLLCUWBatchGrid.getRowColData(tSel,10);   //出险人客户号 
//	var tClaimRelFlag = "1";//SelfLLCUWBatchGrid.getRowColData(tSel,11); 	//赔案相关标志
//	var tMissionid = SelfLLCUWBatchGrid.getRowColData(tSel,12);  	//任务ID 
//	var tSubmissionid = SelfLLCUWBatchGrid.getRowColData(tSel,13);  	 //子任务ID 
//	var tActivityid = SelfLLCUWBatchGrid.getRowColData(tSel,14); 	 //节点号 	
//	//alert(tMissionid);
//	var strURL="./LLSecondUWALLInputMain.jsp";
//	strURL += "?CaseNo=" + tCaseNo ;	//赔案号
//    strURL += "&BatNo=" + tBatNo ; //批次号			    
//	strURL += "&InsuredNo=" + tInsuredNo ;   //出险人客户号 
//	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //赔案相关标志 	
//	strURL += "&Missionid=" + tMissionid ;   //任务ID 
//	strURL += "&Submissionid=" + tSubmissionid ;   //子任务ID 
//	strURL += "&Activityid=" + tActivityid ;   //节点号 	
//	//prompt("",strURL);
//	window.open(strURL,"",sFeatures);
//}


//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

/*******  提交后操作,服务器数据返回后执行的操作，进入 核保操作页面 ****************************/
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	
	if (FlagStr == "Fail" )
	{                 
		alert(content);
		//initLLCUWBatchGridQuery();
		//initSelfLLCUWBatchGridQuery();
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
	}
	else
	{ 
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	//	applySucc();  //modify wyc
		//initLLCUWBatchGridQuery();
		//initSelfLLCUWBatchGridQuery();
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
	}

}
//modify by lzf 20130608
function applySucc()
{
	var tSel = PublicWorkPoolGrid.getSelNo()-1;
	var tCaseNo = PublicWorkPoolGrid.getRowColData(tSel,1); //赔案号
	var tBatNo = PublicWorkPoolGrid.getRowColData(tSel,2); 	//批次号
	var tInsuredNo = PublicWorkPoolGrid.getRowColData(tSel,9);   //出险人客户号 
	var tClaimRelFlag = "1";//LLCUWBatchGrid.getRowColData(tSel,11); 	//赔案相关标志
	var tMissionid =PublicWorkPoolGrid.getRowColData(tSel,12);  	//任务ID 
	var tSubmissionid =PublicWorkPoolGrid.getRowColData(tSel,13);  	 //子任务ID 
	var tActivityid = PublicWorkPoolGrid.getRowColData(tSel,15); 	 //节点号 	
	
	var strURL="./LLSecondUWALLInputMain.jsp";
	strURL += "?CaseNo=" + tCaseNo ;	//赔案号
    strURL += "&BatNo=" + tBatNo ; //批次号			    
	strURL += "&InsuredNo=" + tInsuredNo ;   //出险人客户号 
	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //赔案相关标志 	
	strURL += "&Missionid=" + tMissionid ;   //任务ID 
	strURL += "&Submissionid=" + tSubmissionid ;   //子任务ID 
	strURL += "&Activityid=" + tActivityid ;   //节点号 	
	window.open(strURL,"",sFeatures);
}
function SelfLLCUWBatchGridClick()
{
	var tSel = PrivateWorkPoolGrid.getSelNo()-1;
	var tCaseNo = PrivateWorkPoolGrid.getRowColData(tSel,1); //赔案号
	var tBatNo = PrivateWorkPoolGrid.getRowColData(tSel,2); 	//批次号
	var tInsuredNo = PrivateWorkPoolGrid.getRowColData(tSel,9);   //出险人客户号 
	var tClaimRelFlag = "1";//SelfLLCUWBatchGrid.getRowColData(tSel,11); 	//赔案相关标志
	var tMissionid = PrivateWorkPoolGrid.getRowColData(tSel,12);  	//任务ID 
	var tSubmissionid = PrivateWorkPoolGrid.getRowColData(tSel,13);  	 //子任务ID 
	var tActivityid = PrivateWorkPoolGrid.getRowColData(tSel,15); 	 //节点号 	
	//alert(tMissionid);
	var strURL="./LLSecondUWALLInputMain.jsp";
	strURL += "?CaseNo=" + tCaseNo ;	//赔案号
    strURL += "&BatNo=" + tBatNo ; //批次号			    
	strURL += "&InsuredNo=" + tInsuredNo ;   //出险人客户号 
	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //赔案相关标志 	
	strURL += "&Missionid=" + tMissionid ;   //任务ID 
	strURL += "&Submissionid=" + tSubmissionid ;   //子任务ID 
	strURL += "&Activityid=" + tActivityid ;   //节点号 	
	//prompt("",strURL);
	window.open(strURL,"",sFeatures);
}

//end by lzf
/*******************共享池任务申请成功, 描述:进入核保界面**************** */
//function applySucc()
//{
//	var tSel = LLCUWBatchGrid.getSelNo()-1;
//	var tCaseNo = LLCUWBatchGrid.getRowColData(tSel,1); //赔案号
//	var tBatNo = LLCUWBatchGrid.getRowColData(tSel,2); 	//批次号
//	var tInsuredNo = LLCUWBatchGrid.getRowColData(tSel,10);   //出险人客户号 
//	var tClaimRelFlag = "1";//LLCUWBatchGrid.getRowColData(tSel,11); 	//赔案相关标志
//	var tMissionid =LLCUWBatchGrid.getRowColData(tSel,12);  	//任务ID 
//	var tSubmissionid =LLCUWBatchGrid.getRowColData(tSel,13);  	 //子任务ID 
//	var tActivityid = LLCUWBatchGrid.getRowColData(tSel,14); 	 //节点号 	
//	
//	var strURL="./LLSecondUWALLInputMain.jsp";
//	strURL += "?CaseNo=" + tCaseNo ;	//赔案号
//    strURL += "&BatNo=" + tBatNo ; //批次号			    
//	strURL += "&InsuredNo=" + tInsuredNo ;   //出险人客户号 
//	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //赔案相关标志 	
//	strURL += "&Missionid=" + tMissionid ;   //任务ID 
//	strURL += "&Submissionid=" + tSubmissionid ;   //子任务ID 
//	strURL += "&Activityid=" + tActivityid ;   //节点号 	
//	window.open(strURL,"",sFeatures);
//}

//function queryClick2(){
//		var strSQL = "select missionprop1,missionprop2,'','',missionprop9,missionprop4,"
//	       + " makedate,missionprop3,missionprop20, "
//	       + " missionid,submissionid,activityid,missionprop5,"
//	       + " decode((select sum(count(distinct batno))"
//	       + " from llcuwbatch"
//	       + " where '1236998690000' = '1236998690000'"
//	       + " and caseno = a.missionprop1"
//	       + " group by batno),1,'',(select sum(count(distinct batno))"
//	       + " from llcuwbatch"
//	       + " where '1236998690000' = '1236998690000'"
//	       + " and caseno = a.missionprop1"
//	       + " group by batno),'')"
//	       //+ " case activitystatus when '1' then '未人工核保' when '3' then '核保已回复' "
//	       //+ " when '2' then '核保未回复'  when '4' then '再保未回复' when '5' then '再保已回复' end,"
//	       //+ " missionprop3,missionprop4,"
//		   //+ " case missionprop5 when '0' then '相关' when '1' then '不相关' end,"
//		   //+ " missionprop20,missionid,submissionid,activityid, missionprop5 from lwmission where 1=1 "
//		   + "  from lwmission a where 1=1 "
//           + " and activityid = '0000005505' "
//           + " and processid = '0000000005'"
//           + " and activitystatus = '1' "
//           + " and DefaultOperator ='"+fm.Operator.value+"'"
//           + getWherePart( 'missionprop1' ,'QCaseNo2')
//           + getWherePart( 'missionprop2' ,'BatNo2')
//           + getWherePart( 'missionprop3' ,'QInsuredNo2')
//           + getWherePart( 'missionprop4' ,'QInsuredName2','like')
//           + getWherePart( 'missionprop5' ,'QClaimRelFlag2')
//           + getWherePart( 'makedate' ,'theCurrentDate2','<=')
//           + getWherePart( 'missionprop20' ,'ManageCom2','like')  //<当前机构>
//           + " order by missionprop1,missionprop2";	
//	turnPage2.pageLineNum=5;
//	turnPage2.queryModal(strSQL, SelfLLCUWBatchGrid);
//}