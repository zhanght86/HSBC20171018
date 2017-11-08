
//程序名称：NoScanBankContInput.js
//程序功能：银代新契约无扫描保单录入
//创建日期：2005-07-18 11:10:36
//创建人  ：ccvip 
//更新记录：  更新人    更新日期     更新原因/内容

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;
 var sFeatures1 = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
 
/*********************************************************************
 *  执行新契约扫描的“开始录入”
 *  描述:进入无扫描录入页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ApplyInput()
{
	cPrtNo = fm.PrtNo.value;
	cManageCom = fm.ManageCom.value;

	if(cPrtNo == "")
	{
		alert("请录入投保单号！");
		return;
	}
	if(cManageCom == "")
	{
		alert("请录入管理机构！");
		return;		
	}
	//进行校验
  if( verifyInput2() == false ) return false;
	
	indx = cManageCom.indexOf(comcode);
	if(indx != 0){
		alert("您没有申请此管理机构的权限");
		return;
	}
	
	if(type=='2')//对于集体保单的申请
	{
		if (GrpbeforeSubmit() == false)
		{
		    alert("已存在该投保单号，请选择其他值!");
		    return false;		
		}
		//var tsql="select * from ljtempfee where othernotype = '1' and otherno="+cPrtNo;
 	  //var crr = easyExecSql(tsql);
 		//if(!crr)
 		//{
 		//  alert("未交费保单不能申请!");
 		//  fm.PrtNo.value=""; 
 		//	return; 
 		//} 		
	} 
	else if(type=='1'||type=='3')//对于个人保单的申请
	{
		var PrtNo = fm.PrtNo.value;
		var tSplitPrtNo = PrtNo.substring(0,4);
		if(!(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"))
		{
			alert("印刷号不符合银代印刷号格式！"); 
			return false;
		}
		if (beforeSubmit() == false)
		{
		    alert("已存在该投保单号，请选择其他值!");
		    return false;	 	
		}	
  	
	} 
	else if(type=='5')
	{ 
		if (TempbeforeSubmit() == false)
		{
		    alert("已存在该投保单号，请选择其他值!");
		    return false;		
		}			 
	}
	document.getElementById("fm").submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	
	easyQueryClick1();
}

/*********************************************************************
 *  执行新契约扫描的EasyQuery
 *  描述:查询显示对象是扫描件.显示条件:扫描件已上载成功
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick()
{
	// 初始化表格
	initGrpGrid();
 	//对输入的查询条件进行校验
	if( verifyInput2() == false ) 
	{
		return false;
	}
	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	if(type=='1')
	{
		
		var sqlid1="NoScanBankContInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql1.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql1.addSubPara(fm.Operator.value);//指定传入的参数
		mySql1.addSubPara( trim(comcode) );//指定传入的参数
	    strSQL=mySql1.getString();	
		
//	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'"
////				 + " and trim(defaultoperator) =trim('" + operator + "')"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //集中权限管理体现
//				 + " order by lwmission.missionprop1"
//				 ;	 
	}
	else if (type=='2')
 	{
		
		var sqlid2="NoScanBankContInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql2.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql2.addSubPara(fm.Operator.value);//指定传入的参数
		mySql2.addSubPara( trim(comcode) );//指定传入的参数
	    strSQL=mySql2.getString();	
		
// 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'"
////				 + " and trim(defaultoperator)=trim('" + operator + "')"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //集中权限管理体现
//				 + " order by lwmission.missionprop1"
//				 ;	 		 
	}
//银代录入
  else if(type=='3')
  {
  	var tempfeeSQL="";//该语句拼入 交费日期 查询条件。
	if(trim(fm.PayDate.value)!="")
	{
		tempfeeSQL=" and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =lwmission.missionprop1 and paydate='"+fm.PayDate.value+"')";
	}
	
		var sqlid3="NoScanBankContInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql3.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql3.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql3.addSubPara(fm.Operator.value);//指定传入的参数
		mySql3.addSubPara( trim(comcode) );//指定传入的参数
		mySql3.addSubPara( tempfeeSQL);//指定传入的参数
	    strSQL=mySql3.getString();	
	
//  	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'"
//				 + " and MissionProp5='TB05'" 
////				 + " and trim(defaultoperator) =trim('" + operator + "')"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //集中权限管理体现
//				 + tempfeeSQL
//				 + " order by lwmission.missionprop1" ;	 
  }  
	else if(type=='5')
	{
		
		var sqlid4="NoScanBankContInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(operator);//指定传入的参数
		mySql4.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql4.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql4.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql4.addSubPara(fm.Operator.value);//指定传入的参数
		mySql4.addSubPara( trim(comcode) );//指定传入的参数
	    strSQL=mySql4.getString();	
		
// 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001061' "
//				 + " and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " order by lwmission.missionprop1"
//				 ;	 			
	}

	turnPage1.queryModal(strSQL, GrpGrid);
	return true;
}

function easyQueryClick1()
{
	// 初始化表格
	initGrpGrid();
//alert("operator=="+operator);
	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	if(type=='3')
	{
		
		var sqlid5="NoScanBankContInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql5.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql5.addSubPara(fm.Operator.value);//指定传入的参数
		mySql5.addSubPara( trim(comcode) );//指定传入的参数
	    strSQL=mySql5.getString();	
		
//	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'"
//				 + " and missionprop5 = 'TB05'"  
////				 + " and trim(defaultoperator) =trim('" + operator + "')"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //集中权限管理体现
//				 + " order by lwmission.missionprop1"
//				 ;	  
	}
	else if (type=='2')
 	{
		
		var sqlid6="NoScanBankContInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql6.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql6.addSubPara(fm.Operator.value);//指定传入的参数
		mySql6.addSubPara( trim(comcode) );//指定传入的参数
	    strSQL=mySql6.getString();	
		
// 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //集中权限管理体现
//				 + " order by lwmission.missionprop1"
//				 ;	 		 
	}
	else if(type=='5')
	{
		
		var sqlid7="NoScanBankContInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(operator);//指定传入的参数
		mySql7.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql7.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql7.addSubPara(fm.Operator.value);//指定传入的参数
	    strSQL=mySql7.getString();	
		
// 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001061' "
//				 + " and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " order by lwmission.missionprop1"
//				 ;	 			
	}

	turnPage1.queryModal(strSQL, GrpGrid);
	return true;
}

function GoToInput()
{
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<GrpGrid.mulLineCount; i++) {
    if (GrpGrid.getSelNo(i)) { 
      checkFlag = GrpGrid.getSelNo();
      break;
    }
  }
  //alert(checkFlag);
  if (checkFlag) {
  	//alert("ok"); 
    var	prtNo = GrpGrid.getRowColData(checkFlag - 1, 1); 	
    var ManageCom = GrpGrid.getRowColData(checkFlag - 1, 2); 
    var MissionID = GrpGrid.getRowColData(checkFlag - 1, 5);
    var SubMissionID = GrpGrid.getRowColData(checkFlag - 1, 6);
  
    var ActivityID = GrpGrid.getRowColData(checkFlag - 1, 7);
    //alert(ActivityID); 
	  var NoType = "1"; 
	  
    //var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;
    //var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该投保单号
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);
    var strReturn="1";
    //打开无扫描录入界面 
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;";
    if (strReturn == "1") 
     if(type=='3') 
     {    		 
     	  sql = "select missionprop5 from lwmission where activityid = '0000001098' and missionprop1 = '"+prtNo+"'";
    		turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    		if(!turnPage.strQueryResult)
    		{
    			alert("查询该投保单出错！"); 
    			return;
    		}
    		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);     
         if(turnPage.arrDataCacheSet[0][0]=='TB05')//银代录入
    		{
    			window.open("./BankContInputNoScanMain.jsp?ScanFlag=0&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}else
    		{
    			window.open("./ContInputNoScanMain.jsp?    ScanFlag=0&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		} 
    }
    else if(type=='2')
    {
    	//var tsql="select * from ljtempfee where othernotype = '1' and otherno="+prtNo;
 	    //var crr = easyExecSql(tsql);
 		  //if(!crr)
 		  //{
 		  //alert("未交费保单不能进行录入!"); 
 			//return; 
 	  	//} 
 	  	
     	window.open("./ContPolInputNoScanMain.jsp?ScanFlag=0&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1);
    }	
    else if(type=='5')
    {
     	window.open("./FirstTrialMain.jsp?prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID, "", sFeatures+sFeatures1);
    }	
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
  
}
function beforeSubmit()
{
		var strSQL = "";
	
		var sqlid8="NoScanBankContInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql8.getString();	
		
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}
	
			var sqlid9="NoScanBankContInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql9.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001062' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
   		var sqlid10="NoScanBankContInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql10.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql10.getString();	
   
//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}	   	
}
function GrpbeforeSubmit()
{
	var strSQL = "";
	
	   	var sqlid11="NoScanBankContInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		mySql11.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql11.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//判断是否查询成功
	if (turnPage.strQueryResult) {
		  //alert('1');
	    return false;
	}

	   	var sqlid12="NoScanBankContInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql12.setSqlId(sqlid12);//指定使用的Sql的id
		mySql12.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql12.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql12.getString();	

//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//判断是否查询成功
	if (turnPage.strQueryResult) {
		  //alert('2');
	    return false;
	}	   	
}
//调整投保单显示形式
function initTr()
{
	 if(type=='1' || type=='2')
	 {
	 	 document.all['SubTitle'].style.display = '';
	 }
	 else if(type=='5')
	 {
	 	document.all['SubTitle'].style.display = "none";
	 }
}
function TempbeforeSubmit()
{
	var strSQL = "";
	
		var sqlid13="NoScanBankContInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql13.setSqlId(sqlid13);//指定使用的Sql的id
		mySql13.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql13.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001060' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}

		var sqlid14="NoScanBankContInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.NoScanBankContInputSql"); //指定使用的properties文件名
		mySql14.setSqlId(sqlid14);//指定使用的Sql的id
		mySql14.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql14.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql14.getString();	

//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}	 	 
}

function showNotePad()
{

	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = GrpGrid.getRowColData(selno, 5);
	var SubMissionID = GrpGrid.getRowColData(selno, 6);
	var ActivityID = GrpGrid.getRowColData(selno, 7);
	var PrtNo = GrpGrid.getRowColData(selno, 1);
	var NoType = type;
	if(PrtNo == null || PrtNo == "")
	{
		alert("投保单号为空！");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}