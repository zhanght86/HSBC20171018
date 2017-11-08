//程序名称：ScanContInput.js
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var strOperate = "like";
var sFeatures1 = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var showInfo ;

/*********************************************************************
 *  执行新契约扫描的EasyQuery
 *  描述:查询显示对象是扫描件.显示条件:扫描件已上载成功
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
/**function easyQueryClick()
{
	 initGrpGrid();//初始化表格
	 //对输入的查询条件进行校验
	if( verifyInput2() == false ) 
	{
		return false;
	}
	//机构控制<如果没有选择管理机构则使用登陆时的登陆机构>
	if(trim(fm.ManageCom.value)=="")
	{
		//alert("登陆机构代码："+manageCom);
		fm.ManageCom.value=manageCom;
	}
	// 书写SQL语句
	var strSQL = "";
	if(type =="1")
	{
		var tempfeeSQL="";//该语句拼入 交费日期 查询条件。
		if(trim(fm.PayDate.value)!="")
		{
			tempfeeSQL=" and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =lwmission.missionprop1 and paydate='"+fm.PayDate.value+"')";
		}
//		strSQL = "select missionprop1,missionprop3,missionprop2,missionid,submissionid,activityid,missionprop5 from lwmission where 1=1 "
//				 + " and activityid = '0000001099' and processid = '0000000003'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 //+ getWherePart('missionprop4','ScanOperator')
//				 + " and missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " and defaultoperator is null "
//				 + " and missionprop5 in ( '01','07','08','09','10','45','TB1001')"
//				 + tempfeeSQL
//				 + " order by missionprop2,missionprop1";
				 	 
	}
	if(type == "2")
	{
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
//				 + " and activityid = '0000002099' "
//				 + " and processid = '0000000004'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 //+ getWherePart('missionprop4','ScanOperator')
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " and defaultoperator is null "
//				 + " order by lwmission.missionprop1";
	}
	
	var sqlid="QueryOtherDocSql"+type;
	var mySql=new SqlClass();
	mySql.setResourceName("app.ScanContInputSql"); //指定使用的properties文件名
    mySql.setSqlId(sqlid);//指定使用的Sql的id
    mySql.addSubPara(fm.PrtNo.value);//指定传入的参数
    mySql.addSubPara(fm.InputDate.value);
    mySql.addSubPara(fm.ManageCom.value);
	mySql.addSubPara(comcode);
	if(type=="1"&&tempfeeSQL!=null&&tempfeeSQL!="")
	{
		mySql.addSubPara(tempfeeSQL);
	}
	else
		mySql.addSubPara("");
	if(type=="1")
	{
		mySql.addSubPara(" order by missionprop2,missionprop1");
	}
	strSQL=mySql.getString();
	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


function easyQueryClickSelf()
{
	// 书写SQL语句
	var strSQL = "";
	if(type =="1")
	{
//		strSQL = "select missionprop1,missionprop3,missionprop2,missionid,submissionid,activityid,missionprop5 from lwmission where 1=1 "
//				 + " and activityid = '0000001099' and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + " and missionprop3 like '" + comcode + "%%'" //集中权限管理体现
//				 + " and missionprop5 in ( '01','07','08','09','10','45','TB1001')"
//				 + " order by missionprop2,missionprop1";	 
	}
	if(type == "2")
	{
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
//				 + " and activityid = '0000002099' "
//				 + " and processid = '0000000004'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " order by lwmission.missionprop1";
	}
	
	var sqlid="QueryOtherDocSql2"+type;
	var mySql=new SqlClass();
	mySql.setResourceName("app.ScanContInputSql"); //指定使用的properties文件名
    mySql.setSqlId(sqlid);//指定使用的Sql的id
    mySql.addSubPara(operator);//指定传入的参数
    mySql.addSubPara(comcode);
	strSQL=mySql.getString();
	
	turnPage2.queryModal(strSQL, SelfGrpGrid);
	return true;
}

/*********************************************************************
 *  执行新契约扫描的“开始录入”
 *  描述:进入扫描随动页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
/**function GoToInput()
{
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = GrpGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("请选择要申请的扫描件！");
	      return;
	}
  	prtNo = GrpGrid.getRowColData(checkFlag, 1); 	
    var	ManageCom = GrpGrid.getRowColData(checkFlag, 2);
    var MissionID =GrpGrid.getRowColData(checkFlag, 4);
    var SubMissionID =GrpGrid.getRowColData(checkFlag, 5);
    var ActivityID =GrpGrid.getRowColData(checkFlag, 6);
    var PolApplyDate=GrpGrid.getRowColData(checkFlag,3);
    var SubType =GrpGrid.getRowColData(checkFlag, 7);
    
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1;";
    //申请该印刷号
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);
	var NoType = type;
    //打开扫描件录入界面
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") 
    	if(type=="1")
    	{
    		//sql = "select missionprop5 from lwmission where activityid = '0000001099' and missionprop1 = '"+prtNo+"'";
			var sqlid="QueryOtherDocSql3";
			var mySql=new SqlClass();
			mySql.setResourceName("app.ScanContInputSql"); //指定使用的properties文件名
		    mySql.setSqlId(sqlid);//指定使用的Sql的id
		    mySql.addSubPara(prtNo);//指定传入的参数
			sql=mySql.getString();
			
			var brr = easyExecSql(sql );

    		if(!brr)
    		{
    			alert("扫描上传出错");
    			return;
    		}
    		if(brr[0][0]=='01' || brr[0][0]=='45')//个单录入
    		{
    			window.open("./ContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}else if(brr[0][0]=='05')//银代录入
    		{
    			window.open("./BankContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}
 			}
 			if(type=="2")
 			{
 				window.open("./GrpContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&PolApplyDate="+PolApplyDate+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&SubType="+SubType+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1); 
 			} 
}*/

/*********************************************************************
 *  执行新契约扫描的“开始录入”
 *  描述:进入扫描随动页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
/**function InitGoToInput()
{ 
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = SelfGrpGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("请选择要申请的扫描件！");
	      return;
	}
  	prtNo = SelfGrpGrid.getRowColData(checkFlag, 1); 	
  //	alert("印刷号:"+prtNo);
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 2);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 4);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 5);
    var PolApplyDate=SelfGrpGrid.getRowColData(checkFlag,3);
    var SubType =SelfGrpGrid.getRowColData(checkFlag, 7);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 6);

	  var NoType = type;

    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该印刷号
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //打开扫描件录入界面
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
  
    if (strReturn == "1") {
    	if(type=="1")
    	{
    		//sql = "select missionprop5 from lwmission where activityid = '0000001099' and missionprop1 = '"+prtNo+"'";
			var sqlid="QueryOtherDocSql3";
			var mySql=new SqlClass();
			mySql.setResourceName("app.ScanContInputSql"); //指定使用的properties文件名
		    mySql.setSqlId(sqlid);//指定使用的Sql的id
		    mySql.addSubPara(prtNo);//指定传入的参数
			sql=mySql.getString();
			var brr = easyExecSql(sql );

    		if(!brr)
    		{
    			alert("扫描上传出错");
    			return;
    		}
    		//alert(brr[0][0]);
    		if(brr[0][0]=='01' ||brr[0][0]=='45'||brr[0][0]=='TB1001')//个单录入
    		{  //alert(111);
    			window.open("./ContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}else if(brr[0][0]=='05')//银代录入
    		{
    			window.open("./BankContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}
 			}
 			if(type=="2")
 			{
 				//var tsql="select * from ljtempfee where othernotype = '1' and otherno="+prtNo;
 				//var crr = easyExecSql(tsql);
 				//if(!crr)
 				//{
 				//  alert("未交费保单不能进行录入!")
 				//  return;
 				//} 
 				window.open("./GrpContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&PolApplyDate="+PolApplyDate+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&SubType="+SubType+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1); 
 			} 
 }
}

function ApplyUW()
{

	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}

	fm.MissionID.value = GrpGrid.getRowColData(selno, 4);
	fm.SubMissionID.value = GrpGrid.getRowColData(selno, 5);
	fm.ActivityID.value = GrpGrid.getRowColData(selno, 6);
	
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
lockScreen('lkscreen');  
	fm.action = "ProposalApproveChk.jsp";
	document.getElementById("fm").submit(); //提交
}

//数据提交后返回，同时刷新页面,失败则只返回错误信息，成功则完成后续动作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		easyQueryClick();// 刷新页面
		easyQueryClickSelf();// 刷新页面
	}
	else
	{
	    GoToInput();//进入录单页面
		easyQueryClick();// 刷新页面
		easyQueryClickSelf();// 刷新页面
	}

}


function showNotePad()
{

	var selno = SelfGrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = SelfGrpGrid.getRowColData(selno, 4);
	var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
	var NoType = type;
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}
*/
/*********************************************************************
 *  执行新契约扫描的“开始录入”
 *  描述:进入扫描随动页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GoToInput()
{ 
  var i = 0;
  var checkFlags;
  var state = "0";
	var checkFlag = PublicWorkPoolGrid.getSelNo() - 1;
	//alert("checkFlag=="+checkFlag);
	if (checkFlag < 0)
	{
	      alert("请选择要申请的扫描件！");
	      return;
	}
  	prtNo = PublicWorkPoolGrid.getRowColData(checkFlag, 1); 	
    var	ManageCom = PublicWorkPoolGrid.getRowColData(checkFlag, 2);
    var MissionID =PublicWorkPoolGrid.getRowColData(checkFlag, 6);
    var SubMissionID =PublicWorkPoolGrid.getRowColData(checkFlag, 7);
    var ActivityID =PublicWorkPoolGrid.getRowColData(checkFlag, 9);
    var PolApplyDate=PublicWorkPoolGrid.getRowColData(checkFlag,3);
    var SubType =PublicWorkPoolGrid.getRowColData(checkFlag, 5);
//    alert("prtNo=="+prtNo);
//    alert("ManageCom=="+ManageCom);
//    alert("MissionID=="+MissionID);
//    alert("SubMissionID=="+SubMissionID);
//    alert("ActivityID=="+ActivityID);
//    alert("PolApplyDate=="+PolApplyDate);
//    alert("SubType=="+SubType);
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1;";
    //申请该印刷号
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);
	var NoType = type;
    //打开扫描件录入界面
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") 
    	if(type=="1")
    	{
    		//sql = "select missionprop5 from lwmission where activityid = '0000001099' and missionprop1 = '"+prtNo+"'";
			var sqlid="QueryOtherDocSql3";
			var mySql=new SqlClass();
			mySql.setResourceName("app.ScanContInputSql"); //指定使用的properties文件名
		    mySql.setSqlId(sqlid);//指定使用的Sql的id
		    mySql.addSubPara(prtNo);//指定传入的参数
			sql=mySql.getString();
			
			var brr = easyExecSql(sql );

    		if(!brr)
    		{
    			alert("扫描上传出错");
    			return;
    		}
    		if(brr[0][0]=='01' || brr[0][0]=='45' ||brr[0][0]=='TB1001')//个单录入
    		{ //alert(111);
    			window.open("./ContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}else if(brr[0][0]=='05')//银代录入
    		{
    			window.open("./BankContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}
 			}
 			if(type=="2")
 			{
 				window.open("./GrpContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&PolApplyDate="+PolApplyDate+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&SubType="+SubType+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1); 
 			} 
}

/*********************************************************************
 *  执行新契约扫描的“开始录入”
 *  描述:进入扫描随动页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function InitGoToInput()
{  
    var i = 0;
    var checkFlags;
    var state = "0";
	var checkFlag = PrivateWorkPoolGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("请选择要申请的扫描件！");
	      return;
	}
  	prtNo = PrivateWorkPoolGrid.getRowColData(checkFlag, 1); 	
  	//alert("印刷号:"+prtNo);
    var	ManageCom = PrivateWorkPoolGrid.getRowColData(checkFlag, 2);
    var MissionID =PrivateWorkPoolGrid.getRowColData(checkFlag, 6);
    var SubMissionID =PrivateWorkPoolGrid.getRowColData(checkFlag, 7);
    var PolApplyDate=PrivateWorkPoolGrid.getRowColData(checkFlag,3);
    var SubType =PrivateWorkPoolGrid.getRowColData(checkFlag, 5);    
    var ActivityID = PrivateWorkPoolGrid.getRowColData(checkFlag, 9);
	var NoType = type;
	
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该印刷号
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //打开扫描件录入界面
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
  
    if (strReturn == "1") {
    	if(type=="1")
    	{
    		//sql = "select missionprop5 from lwmission where activityid = '0000001099' and missionprop1 = '"+prtNo+"'";
			var sqlid="QueryOtherDocSql3";
			var mySql=new SqlClass();
			mySql.setResourceName("app.ScanContInputSql"); //指定使用的properties文件名
		    mySql.setSqlId(sqlid);//指定使用的Sql的id
		    mySql.addSubPara(prtNo);//指定传入的参数
			sql=mySql.getString();
			var brr = easyExecSql(sql );
    		if(!brr)
    		{
    			alert("扫描上传出错");
    			return;
    		}
    		//alert(brr[0][0]);
    		if(brr[0][0]=='01' ||brr[0][0]=='45'||brr[0][0]=='TB1001')//个单录入
    		{  //alert(111);
    			window.open("./ContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}else if(brr[0][0]=='05')//银代录入
    		{
    			window.open("./BankContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}
 			}
 			if(type=="2")
 			{
 				//var tsql="select * from ljtempfee where othernotype = '1' and otherno="+prtNo;
 				//var crr = easyExecSql(tsql);
 				//if(!crr)
 				//{
 				//  alert("未交费保单不能进行录入!")
 				//  return;
 				//} 
 				window.open("./GrpContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&PolApplyDate="+PolApplyDate+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&SubType="+SubType+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1); 
 			} 
 }
}

function ApplyUW()
{

	var selno = PublicWorkPoolGrid.getSelNo()-1;
	//alert("selno=="+selno);
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}

	
//	PublicWorkPoolGrid.getRowColData(selno, 1);PrtNo  add by lzf
//	PublicWorkPoolGrid.getRowColData(selno, 2);ManageCom
//	PublicWorkPoolGrid.getRowColData(selno, 3);Date
//	PublicWorkPoolGrid.getRowColData(selno, 4);Operator
//	PublicWorkPoolGrid.getRowColData(selno, 5);SubType
//	PublicWorkPoolGrid.getRowColData(selno, 6);MissionID
//	PublicWorkPoolGrid.getRowColData(selno, 7);SubMissionID
//	PublicWorkPoolGrid.getRowColData(selno, 8);ProcessID
//	PublicWorkPoolGrid.getRowColData(selno, 9);ActivityID
	
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 6);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 7);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 9);
	
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	// var encodedStr = encodeURIComponent(showStr);
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
	fm.action = "ProposalApproveChk.jsp";
	document.getElementById("fm").submit(); //提交
}

//数据提交后返回，同时刷新页面,失败则只返回错误信息，成功则完成后续动作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
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
	}
	GoToInput();//进入录单页面
	jQuery("#privateSearch").click();
	jQuery("#publicSearch").click();
}


function showNotePad()
{

	var selno = SelfGrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = SelfGrpGrid.getRowColData(selno, 4);
	var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
	var NoType = type;
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}