var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var strOperate = "like";
var sFeatures1 = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

/*********************************************************************
 *  执行新契约扫描的EasyQuery
 *  描述:查询显示对象是扫描件.显示条件:扫描件已上载成功
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick()
{
	var tPolProperty = fm.state.value;
	var mission1="";
	var kindSql="";
	//alert(tPolProperty);
	if(tPolProperty!=null||tPolProperty!=""){
		if(trim(tPolProperty)==51){
			mission1="8651";
		}
		if(trim(tPolProperty)==11){
			mission1="8611";
		}
		if(trim(tPolProperty)==16){
			mission1="8616";
		}
		if(trim(tPolProperty)==21){
			mission1="8621";
		}if(trim(tPolProperty)==61){
			mission1="8661";
		}
		
	}
	if(mission1!=""){
		kindSql=" and missionprop1 like '"+mission1+"%%'";
	}
	if(trim(tPolProperty)==35){
		kindSql=" and (missionprop1 like '8635%%' or missionprop1 like '8625%%' or missionprop1 like '8615%%') ";
	}
	 initGrpGrid();//初始化表格
	 //对输入的查询条件进行校验
	//if( verifyInput2() == false ) 
	//{
	//	return false;
	//}
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
		//var tempfeeSQL="";//该语句拼入 交费日期 查询条件。
		//if(trim(fm.InputDate.value)!="")
		//{
		//	tempfeeSQL=" and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =lwmission.missionprop1 and paydate='"+fm.PayDate.value+"')";
		//}
		
//		strSQL = "select missionprop1,'','',missionprop9,MissionProp7,missionid,submissionid,activityid,missionprop5 from lwmission where 1=1 "
//				 + " and activityid in (select a.activityid from lwactivity a where a.functionid = '10010053') "
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop7','InputDate')
//				 + getWherePart('missionprop9','ManageCom',strOperate)
//				 + " and missionprop9 like '" + comcode + "%%'" //集中权限管理体现
//				 //+ getWherePart('missionprop8','coolDatePicker')
//				 //+ " and missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " and defaultoperator is null "
//				 + " and missionprop6='"+trim(InputTime)+"' "
//				 + kindSql
//				 + " order by missionprop2,missionprop1";
//				 	//prompt("",strSQL);
//				 	 //alert(strSQL);
//				 	 //alert(strOperate);
		
		    var  PrtNo = window.document.getElementsByName(trim("PrtNo"))[0].value;
		  	var  InputDate = window.document.getElementsByName(trim("InputDate"))[0].value;
		  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
			var sqlid1="DSScanContSql0";
			var mySql1=new SqlClass();
			mySql1.setResourceName("app.DSScanContSql"); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara(PrtNo);//指定传入的参数
			mySql1.addSubPara(InputDate);//指定传入的参数
			mySql1.addSubPara(ManageCom);//指定传入的参数
			mySql1.addSubPara(comcode);//指定传入的参数
			mySql1.addSubPara(trim(InputTime));//指定传入的参数
			mySql1.addSubPara(kindSql);//指定传入的参数
			 strSQL=mySql1.getString();
	}
	if(type == "2")
	{
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
//				 + " and activityid in (select a.activityid from lwactivity a where a.functionid = '20010011') "
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 //+ getWherePart('missionprop4','ScanOperator')
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " and defaultoperator is null "
//				 + " order by lwmission.missionprop1";
		
	    var  PrtNo = window.document.getElementsByName(trim("PrtNo"))[0].value;
	  	var  InputDate = window.document.getElementsByName(trim("InputDate"))[0].value;
	  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
		var sqlid2="DSScanContSql1";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.DSScanContSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(PrtNo);//指定传入的参数
		mySql2.addSubPara(InputDate);//指定传入的参数
		mySql2.addSubPara(ManageCom);//指定传入的参数
		mySql2.addSubPara(comcode);//指定传入的参数
		 strSQL=mySql2.getString();
	}
	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


function easyQueryClickSelf()
{
	
	var tPolProperty = fm.state1.value;
	var mission1="";
	var kindSql="";
	//alert(tPolProperty);
	if(tPolProperty!=null||tPolProperty!=""){
		if(trim(tPolProperty)==51){
			mission1="8651";
		}
		if(trim(tPolProperty)==11){
			mission1="8611";
		}
		if(trim(tPolProperty)==16){
			mission1="8616";
		}
		if(trim(tPolProperty)==21){
			mission1="8621";
		}if(trim(tPolProperty)==61){
			mission1="8661";
		}
		
	}
	if(mission1!=""){
		kindSql=" and missionprop1 like '"+mission1+"%%'";
	}
	if(trim(tPolProperty)==35){
		kindSql=" and (missionprop1 like '8635%%' or missionprop1 like '8625%%' or missionprop1 like '8615%%') ";
	}
	 initSelfGrpGrid();//初始化表格
	//机构控制<如果没有选择管理机构则使用登陆时的登陆机构>
	if(trim(fm.ManageCom1.value)=="")
	{
		//alert("登陆机构代码："+manageCom);
		fm.ManageCom1.value=manageCom;
	}
	
	
	// 书写SQL语句
	var strSQL = "";
	if(type =="1")
	{
//		strSQL = "select missionprop1,modifyDate,modifyTime,missionprop9,MissionProp7,missionid,submissionid,activityid,missionprop5 from lwmission where 1=1 "
//				 + " and activityid in (select a.activityid from lwactivity a where a.functionid = '10010053')"
//				 + " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop1','PrtNo1',strOperate)
//				 + getWherePart('missionprop7','InputDate1')
//				 + getWherePart('missionprop9','ManageCom',strOperate)
//				 + " and missionprop9 like '" + comcode + "%%'" //集中权限管理体现
//				 + " and missionprop6='"+trim(InputTime)+"' "
//				 + kindSql
//				 + " order by missionprop2,missionprop1";	
//				 //alert(strSQL) ;
		  
		    var  PrtNo1 = window.document.getElementsByName(trim("PrtNo1"))[0].value;
		  	var  InputDate1 = window.document.getElementsByName(trim("InputDate1"))[0].value;
		  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
			var sqlid3="DSScanContSql2";
			var mySql3=new SqlClass();
			mySql3.setResourceName("app.DSScanContSql"); //指定使用的properties文件名
			mySql3.setSqlId(sqlid3);//指定使用的Sql的id
			mySql3.addSubPara(operator);//指定传入的参数
			mySql3.addSubPara(PrtNo1);//指定传入的参数
			mySql3.addSubPara(InputDate1);//指定传入的参数
			mySql3.addSubPara(ManageCom);//指定传入的参数
			mySql3.addSubPara(comcode);//指定传入的参数
			mySql3.addSubPara(trim(InputTime));//指定传入的参数
			mySql3.addSubPara(kindSql);//指定传入的参数
			strSQL=mySql3.getString();
		
				
	}
	if(type == "2")
	{
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
//				 + " and activityid in (select a.activityid from lwactivity a where a.functionid = '20010011') "
//				 + " and defaultoperator ='" + operator + "'"
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //集中权限管理体现
//				 + getWherePart('missionprop1','PrtNo1',strOperate)
//				 + getWherePart('missionprop7','InputDate1')
////				 + " and missionprop9 like '" + comcode + "%%'" //集中权限管理体现
//				 + " and missionprop6='"+trim(InputTime)+"' "
//				 + kindSql
//				 + " order by lwmission.missionprop1";
		
		var  PrtNo1 = window.document.getElementsByName(trim("PrtNo1"))[0].value;
	  	var  InputDate1 = window.document.getElementsByName(trim("InputDate1"))[0].value;
		var sqlid4="DSScanContSql3";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.DSScanContSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(operator);//指定传入的参数
		mySql4.addSubPara(comcode);//指定传入的参数
		mySql4.addSubPara(PrtNo1);//指定传入的参数
		mySql4.addSubPara(InputDate1);//指定传入的参数
		mySql4.addSubPara(trim(InputTime));//指定传入的参数
		mySql4.addSubPara(kindSql);//指定传入的参数
		strSQL=mySql4.getString();
	}
	
	// prompt("",strSQL);
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
    var	ManageCom = GrpGrid.getRowColData(checkFlag, 4);
    var MissionID =GrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =GrpGrid.getRowColData(checkFlag, 7);
    var ActivityID =GrpGrid.getRowColData(checkFlag, 8);
    var PolApplyDate=GrpGrid.getRowColData(checkFlag,3);
    var SubType =GrpGrid.getRowColData(checkFlag, 9);
    //var InputTime ="";
        //InputTime = GrpGrid.getRowColData(checkFlag, 8);
    
    //var JudgeSql="Select * from lwmission where missionprop1='"+ prtNo +"' and Activityid='"+ActivityID+"' and missionprop6='"+InputTime+"'";
    var sqlid5="DSScanContSql4";
	var mySql5=new SqlClass();
	mySql5.setResourceName("app.DSScanContSql"); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(PrtNo);//指定传入的参数
	mySql5.addSubPara(ActivityID);//指定传入的参数
	mySql5.addSubPara(InputTime);//指定传入的参数
	var JudgeSql=mySql5.getString();
    var arrJudge = easyExecSql(JudgeSql);
    if(arrJudge==null){
    	alert("查询节点任务失败！");
    	return false;
    }
    
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
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
    		//sql = "select missionprop5 from lwmission where activityid in (select a.activityid from lwactivity a where a.functionid = '10010053') and missionprop1 = '"+prtNo+"'";
    		    var sqlid6="DSScanContSql5";
    			var mySql6=new SqlClass();
    			mySql6.setResourceName("app.DSScanContSql"); //指定使用的properties文件名
    			mySql6.setSqlId(sqlid6);//指定使用的Sql的id
    			mySql6.addSubPara(PrtNo);//指定传入的参数
    			 sql=mySql6.getString();
			var brr = easyExecSql(sql );
    		//alert(brr[0][0]);
    		window.open("./DSContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan&InputTime="+InputTime, "", sFeatures+sFeatures1);        
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
  	//alert("印刷号:"+prtNo);
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 4);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 7);
    var PolApplyDate=SelfGrpGrid.getRowColData(checkFlag,3);
    var SubType =SelfGrpGrid.getRowColData(checkFlag, 9);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 8);
	//var InputTime ="";
		//InputTime =SelfGrpGrid.getRowColData(checkFlag, 8);//alert("InputTime: "+InputTime);
	var NoType = type;

	//var JudgeSql="Select * from lwmission where missionprop1='"+ prtNo +"' and Activityid='"+ActivityID+"' and missionprop6='"+InputTime+"'";
	    var sqlid7="DSScanContSql6";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.DSScanContSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(PrtNo);//指定传入的参数
		mySql7.addSubPara(ActivityID);//指定传入的参数
		mySql7.addSubPara(InputTime);//指定传入的参数
		var JudgeSql=mySql7.getString();
    var arrJudge = easyExecSql(JudgeSql);
    if(arrJudge==null){
    	alert("查询节点任务失败！");
    	return false;
    }

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
    		//alert(prtNo);
    		//sql = "select missionprop5 from lwmission where activityid in (select a.activityid from lwactivity a where a.functionid = '10010053') and missionprop1 = '"+prtNo+"'";
    		var sqlid8="DSScanContSql7";
 			var mySql8=new SqlClass();
 			mySql8.setResourceName("app.DSScanContSql"); //指定使用的properties文件名
 			mySql8.setSqlId(sqlid8);//指定使用的Sql的id
 			mySql8.addSubPara(PrtNo);//指定传入的参数
 			 sql=mySql8.getString();
			var brr = easyExecSql(sql);
    		//alert(brr[0][0]);
    		window.open("./DSContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan&InputTime="+InputTime, "", sFeatures+sFeatures1);        
 		}
 }
}
//申请按钮
function ApplyUW()
{

	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}

	fm.MissionID.value = GrpGrid.getRowColData(selno, 6);
	fm.SubMissionID.value = GrpGrid.getRowColData(selno, 7);
	fm.ActivityID.value = GrpGrid.getRowColData(selno, 8);
	
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
