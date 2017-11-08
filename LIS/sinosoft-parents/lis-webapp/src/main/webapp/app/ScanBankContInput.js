
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

/*********************************************************************
 *  执行新契约扫描的EasyQuery
 *  描述:查询显示对象是扫描件.显示条件:扫描件已上载成功
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick()
{
	 //对输入的查询条件进行校验
	if( verifyInput2() == false ) 
	{
		return false;
	}
	// 书写SQL语句
	var strSQL = "";
	if(type =="1")
	{
		
		 var sqlid17="ContQuerySQL17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql17.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql17.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql17.addSubPara(comcode);//指定传入的参数
	    strSQL=mySql17.getString();	
		
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
//				 + " and activityid = '0000001099' "
//				 + " and processid = '0000000003'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " and defaultoperator is null "
//				 + " order by lwmission.missionprop1";
				 	 
	}
	if(type == "2")
	{
		
		var sqlid18="ContQuerySQL18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql18.setSqlId(sqlid18);//指定使用的Sql的id
		mySql18.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql18.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql18.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql18.addSubPara(comcode);//指定传入的参数
	    strSQL=mySql18.getString();	
		
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
//				 + " and activityid = '0000002099' "
//				 + " and processid = '0000000004'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " and defaultoperator is null "
//				 + " order by lwmission.missionprop1";
				 	 	
	}
	if(type =="3")
	{
		var tempfeeSQL="";//该语句拼入 交费日期 查询条件。
		if(trim(fm.PayDate.value)!="")
		{
			tempfeeSQL=" and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =lwmission.missionprop1 and paydate='"+fm.PayDate.value+"')";
		}
		
				var sqlid19="ContQuerySQL19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql19.setSqlId(sqlid19);//指定使用的Sql的id
		mySql19.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql19.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql19.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql19.addSubPara(comcode);//指定传入的参数
	    strSQL=mySql19.getString();	
		
		strSQL=strSQL+tempfeeSQL+ " order by missionprop2,missionprop1";
//		strSQL = "select missionprop1,missionprop3,missionprop2,missionid,submissionid,activityid,missionprop5 from lwmission where 1=1 "
//				 + " and activityid = '0000001099' and processid = '0000000003'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + " and missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " and missionprop5 in( '05','12')"
//				 + " and defaultoperator is null "
				 + tempfeeSQL
				 + " order by missionprop2,missionprop1";
	}
	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


function easyQueryClickSelf()
{
	// 初始化表格
	initSelfGrpGrid();
	// 书写SQL语句
	var strSQL = "";
	if(type =="1")
	{
		
		var sqlid20="ContQuerySQL20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql20.setSqlId(sqlid20);//指定使用的Sql的id
		mySql20.addSubPara(operator);//指定传入的参数
		mySql20.addSubPara(comcode);//指定传入的参数

	    strSQL=mySql20.getString();	
		
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
//				 + " and activityid = '0000001099' "
//				 + " and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'" //集中权限管理体现
//				 + " order by lwmission.missionprop1";	 
	}
	if(type == "2")
	{
		
		var sqlid21="ContQuerySQL21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		mySql21.addSubPara(operator);//指定传入的参数
		mySql21.addSubPara(comcode);//指定传入的参数

	    strSQL=mySql21.getString();	
		
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
//				 + " and activityid = '0000002099' "
//				 + " and processid = '0000000004'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " order by lwmission.missionprop1";
	}
	if(type =="3")
	{ 
	
			var sqlid22="ContQuerySQL22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql22.setSqlId(sqlid22);//指定使用的Sql的id
		mySql22.addSubPara(operator);//指定传入的参数
		mySql22.addSubPara(comcode);//指定传入的参数

	    strSQL=mySql22.getString();	
	
//		strSQL = "select missionprop1,missionprop3,missionprop2,missionid,submissionid,activityid,missionprop5 from lwmission where 1=1 "
//				 + " and activityid = '0000001099' and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + " and missionprop3 like '" + comcode + "%%'" //集中权限管理体现
//				 + " and missionprop5 in( '05','12')"
//				 + " order by missionprop2,missionprop1";	 
	}
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
function GoToInput()
{ //alert(type);
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
    var ActivityID = GrpGrid.getRowColData(checkFlag, 6);

    var SubType =GrpGrid.getRowColData(checkFlag, 7);

    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1;";
    //申请该印刷号
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //打开扫描件录入界面
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") 
    	if(type=="3")
    	{
			
						var sqlid23="ContQuerySQL23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql23.setSqlId(sqlid23);//指定使用的Sql的id
		mySql23.addSubPara(prtNo);//指定传入的参数

	    sql=mySql23.getString();	
			
    		//sql = "select missionprop5 from lwmission where activityid = '0000001099' and missionprop1 = '"+prtNo+"'";
			var brr = easyExecSql(sql );

    		if(!brr)
    		{
    			alert("扫描上传出错");
    			return;
    		}

    			window.open("./BankContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&SubType="+SubType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		
 			}
 			if(type=="2")
 			{
 				window.open("./GrpContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&scantype=scan", "", sFeatures+sFeatures1); 
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
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 6);

    var SubType = SelfGrpGrid.getRowColData(checkFlag, 7);

	  var NoType = type;

    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该印刷号
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //打开扫描件录入界面
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") 
    	if(type=="3")
    	{
			
		var sqlid24="ContQuerySQL24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql24.setSqlId(sqlid24);//指定使用的Sql的id
		mySql24.addSubPara(prtNo);//指定传入的参数

	    sql=mySql24.getString();	
			
    		//sql = "select missionprop5 from lwmission where activityid = '0000001099' and missionprop1 = '"+prtNo+"'";
			var brr = easyExecSql(sql );

    		if(!brr)
    		{
    			alert("扫描上传出错");
    			return;
    		}

    	
    			window.open("./BankContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&SubType="+SubType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		
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
 				window.open("./GrpContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1); 
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
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "ProposalApproveChk.jsp";
	document.getElementById("fm").submit(); //提交
}

function afterSubmit( FlagStr, content )
{
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
		easyQueryClick();	// 刷新页面
		easyQueryClickSelf();	// 刷新页面
	}
	else
	{
	    GoToInput();//进入录单页面
		easyQueryClick();	// 刷新页面
		easyQueryClickSelf();	// 刷新页面
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
