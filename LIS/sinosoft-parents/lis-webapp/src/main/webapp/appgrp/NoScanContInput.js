//程序名称：ScanContInput.js
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;
var sqlresourcename = "appgrp.NoScanContInputSql";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
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
	//alert(comcode);
	if(indx != 0 && comcode!="%"){
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
	else if(type=='1')//对于个人保单的申请
	{
		if (beforeSubmit() == false)
		{
		    alert("已存在该投保单号，请选择其他值!");
		    return false;		
		}	
  	
	}else if(type=='5')
	{ 
		if (TempbeforeSubmit() == false)
		{
		    alert("已存在该投保单号，请选择其他值!");
		    return false;		
		}			 
	}
	lockScreen('lkscreen');  
	document.getElementById("fm").submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	//wangxw 20100920
	if( FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{ 
		easyQueryClick1();		
	}

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
//alert("operator=="+operator);
	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	lockScreen('lkscreen');
	//alert(type);
	//alert(comcode);
	if(type=='1')
	{
		/*
	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000001098' "
				 + " and missionprop5 = 'TB01'"
				 + " and processid = '0000000003'"
				 + " and trim(defaultoperator) =trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)
				 //+ " and lwmission.MissionProp3 like '" + trim(comcode) + "%'"  //集中权限管理体现
				 + " order by lwmission.missionprop1"
				 ;	
		*/		 
var sqlid1="NoScanContInputSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(operator); 
mySql1.addSubPara(fm.PrtNo.value);
mySql1.addSubPara(fm.InputDate.value);
mySql1.addSubPara(fm.ManageCom.value);
mySql1.addSubPara(fm.Operator.value);
strSQL =mySql1.getString(); 
	}
	else if (type=='2')
 	{
 		if(comcode=="%")
 		{/*
 			strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000002098' "
				 + " and processid = '0000000004'"
				 + " and trim(defaultoperator)=trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)		 
				 + " order by lwmission.missionprop1"
				 ;
				 */
var sqlid2="NoScanContInputSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(operator); 
mySql2.addSubPara(fm.PrtNo.value);
mySql2.addSubPara(fm.InputDate.value);
mySql2.addSubPara(fm.ManageCom.value);
mySql2.addSubPara(fm.Operator.value);
strSQL =mySql2.getString(); 				 
				 	 
 		}else
 			{
 				/*
 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000002098' "
				 + " and processid = '0000000004'"
				 + " and trim(defaultoperator)=trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)	
				  + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //集中权限管理体现		 
				 + " order by lwmission.missionprop1"
				 ;	 
*/

//alert( "fm.AskpriceNo.value"+ fm.AskpriceNo.value )
//alert( "fm.AskNo.value"+ fm.AskNo.value )
						//wangxw 输入询价编号和询价版本号查询
						
						if( (fm.AskpriceNo.value != null && fm.AskpriceNo.value != "") 
									|| (fm.AskNo.value != null && fm.AskNo.value != "")   ){
								if( ( fm.AskpriceNo.value + fm.AskNo.value == fm.AskpriceNo.value ) ||	( fm.AskpriceNo.value + fm.AskNo.value == fm.AskNo.value )	)
										alert( "请同时输入询价编号和询价版本号联合查询！" )
								else{
										var sqlid3_15="NoScanContInputSql15";
										var mySql3_15=new SqlClass();
										mySql3_15.setResourceName(sqlresourcename);
										mySql3_15.setSqlId(sqlid3_15);
										mySql3_15.addSubPara(operator); 
										mySql3_15.addSubPara(fm.AskpriceNo.value)
										mySql3_15.addSubPara(fm.AskNo.value);
										mySql3_15.addSubPara(fm.InputDate.value);
										mySql3_15.addSubPara(fm.ManageCom.value);
										mySql3_15.addSubPara(fm.Operator.value);
										mySql3_15.addSubPara(trim(comcode));
										mySql3_15.addSubPara(fm.PrtNo.value); //传值为空时此条件自动隐藏，防止输入PrtNo与查出的不一致
										strSQL =mySql3_15.getString();
								}		
						}
						else {
								var sqlid3_3="NoScanContInputSql3";
								var mySql3_3=new SqlClass();
								mySql3_3.setResourceName(sqlresourcename);
								mySql3_3.setSqlId(sqlid3_3);
								mySql3_3.addSubPara(operator); 
								mySql3_3.addSubPara(fm.PrtNo.value);
								mySql3_3.addSubPara(fm.InputDate.value);
								mySql3_3.addSubPara(fm.ManageCom.value);
								mySql3_3.addSubPara(fm.Operator.value);
								mySql3_3.addSubPara(trim(comcode));
								strSQL =mySql3_3.getString();
						}

 
				}
				 //alert(strSQL);
	}
	else if(type=='5')
	{
		/*
 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000001061' "
				 + " and processid = '0000000003'"
				 + " and defaultoperator ='" + operator + "'"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)
				 + " order by lwmission.missionprop1"
				 ;	 			
	*/
var sqlid4="NoScanContInputSql4";
var mySql4=new SqlClass();
mySql4.setResourceName(sqlresourcename);
mySql4.setSqlId(sqlid4);
mySql4.addSubPara(operator); 
mySql4.addSubPara(fm.PrtNo.value);
mySql4.addSubPara(fm.InputDate.value);
mySql4.addSubPara(fm.ManageCom.value);
mySql4.addSubPara(fm.Operator.value);
strSQL =mySql4.getString();
	
	}

//alert(strSQL)
	turnPage1.queryModal(strSQL, GrpGrid,1,1);
	unlockScreen('lkscreen');  
	//GrpGrid.setPageMark(turnPage1);
	//alert("ok");
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
	if(type=='1')
	{
		/*
	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000001098' "
				 + " and processid = '0000000003'"
				 + " and trim(defaultoperator) =trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				
				 
				 + getWherePart('missionprop4','Operator',strOperate)
				 //+ " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //集中权限管理体现
				 + " order by lwmission.missionprop1"
				 ;	 
	*/
var sqlid5="NoScanContInputSql5";
var mySql5=new SqlClass();
mySql5.setResourceName(sqlresourcename);
mySql5.setSqlId(sqlid5);
mySql5.addSubPara(operator); 
mySql5.addSubPara(fm.PrtNo.value);
mySql5.addSubPara(fm.Operator.value);
strSQL =mySql5.getString();
	
	}
	else if (type=='2')
 	{
 		/*
 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000002098' "
				 + " and processid = '0000000004'"
//				 + " and trim(defaultoperator)=trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 
				
				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%'"  //集中权限管理体现
				 + " order by lwmission.missionprop1"
				 ;	 
				 
		 //alert(	strSQL);	 
		*/	
	var sqlid6="NoScanContInputSql6";
var mySql6=new SqlClass();
mySql6.setResourceName(sqlresourcename);
mySql6.setSqlId(sqlid6); 
mySql6.addSubPara(fm.PrtNo.value);
mySql6.addSubPara(fm.Operator.value);
strSQL =mySql6.getString();
	
	
	}
	else if(type=='5')
	{
		/*
 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000001061' "
				 + " and processid = '0000000003'"
				 + " and defaultoperator ='" + operator + "'"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)
				 + " order by lwmission.missionprop1"
				 ;	 			
	*/
		var sqlid7="NoScanContInputSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7); 
mySql7.addSubPara(operator); 
mySql7.addSubPara(fm.PrtNo.value);
mySql7.addSubPara(fm.ManageCom.value);
mySql7.addSubPara(fm.Operator.value);
strSQL =mySql7.getString();
	
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
  
  if (checkFlag) { 
    var	prtNo = GrpGrid.getRowColData(checkFlag - 1, 1);
    var	AskpriceNo = GrpGrid.getRowColData(checkFlag - 1, 2); 	
    var	AskNo = GrpGrid.getRowColData(checkFlag - 1, 3);
    var ManageCom = GrpGrid.getRowColData(checkFlag - 1, 4); 
    var MissionID = GrpGrid.getRowColData(checkFlag - 1, 7);
    var SubMissionID = GrpGrid.getRowColData(checkFlag - 1, 8);
  
    var ActivityID = GrpGrid.getRowColData(checkFlag - 1, 9);
	  var NoType = type;
	  
    //var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;
    //var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该投保单号
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);
    var strReturn="1";
    //打开扫描件录入界面
    //sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") 
     if(type=='1')
     {    		
     	  sql = "select missionprop5 from lwmission where activityid = '0000001098' and missionprop1 = '"+prtNo+"'";
    		turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    		if(!turnPage.strQueryResult)
    		{
    			alert("扫描上传出错");
    			return;
    		}
    		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);     
         if(turnPage.arrDataCacheSet[0][0]=='05')//银代录入
    		{
    			window.open("./BankContInputNoScanMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&scantype=scan", "", sFeatures);        
    		}else
    		{
    			window.open("./ContInputNoScanMain.jsp?ScanFlag=0&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);        
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
 	  	//alert(AskpriceNo+AskNo)
     	window.open("./ContPolInputNoScanMain.jsp?ScanFlag=0&AskpriceNo="+AskpriceNo+"&AskNo="+AskNo+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);
    }	
    else if(type=='5')
    {
     	window.open("./FirstTrialMain.jsp?prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID, "", sFeatures);
    }	
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
  
}
function beforeSubmit()
{
	var strSQL = "";
	/*
	strSQL = "select missionprop1 from lwmission where 1=1 "
				 + " and activityid = '0000001098' "
				 + " and processid = '0000000003'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";
*/
		var sqlid8="NoScanContInputSql8";
var mySql8=new SqlClass();
mySql8.setResourceName(sqlresourcename);
mySql8.setSqlId(sqlid8); 
mySql8.addSubPara(fm.PrtNo.value); 


	turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);  
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}
	/*
	strSQL = "select missionprop1 from lwmission where 1=1 "
				 + " and activityid = '0000001062' "
				 + " and processid = '0000000003'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	*/
var sqlid9="NoScanContInputSql9";
var mySql9=new SqlClass();
mySql9.setResourceName(sqlresourcename);
mySql9.setSqlId(sqlid9); 
mySql9.addSubPara(fm.PrtNo.value); 	
	
	turnPage.strQueryResult  = easyQueryVer3(mySql9.getString(), 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   /*
	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	*/
var sqlid10="NoScanContInputSql10";
var mySql10=new SqlClass();
mySql10.setResourceName(sqlresourcename);
mySql10.setSqlId(sqlid10); 
mySql10.addSubPara(fm.PrtNo.value); 	
mySql10.addSubPara(fm.PrtNo.value); 	
	
	turnPage.strQueryResult  = easyQueryVer3(mySql10.getString(), 1, 0, 1);
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}	   	
}
function GrpbeforeSubmit()
{
	var strSQL = "";
	/*
	strSQL = "select missionprop1 from lwmission where 1=1 "
				 + " and activityid = '0000002098' "
				 + " and processid = '0000000004'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";
	*/
	var sqlid11="NoScanContInputSql11";
var mySql11=new SqlClass();
mySql11.setResourceName(sqlresourcename);
mySql11.setSqlId(sqlid11); 
mySql11.addSubPara(fm.PrtNo.value); 	

	
	turnPage.strQueryResult  = easyQueryVer3(mySql11.getString(), 1, 0, 1); 
	//判断是否查询成功
	if (turnPage.strQueryResult) {
		  //alert('1');
	    return false;
	}

	//strSQL = "select prtno from lcgrpcont where prtno = '" + fm.PrtNo.value + "'";

	var sqlid12="NoScanContInputSql12";
var mySql12=new SqlClass();
mySql12.setResourceName(sqlresourcename);
mySql12.setSqlId(sqlid12);
mySql12.addSubPara(fm.PrtNo.value); 	

	turnPage.strQueryResult  = easyQueryVer3(mySql12.getString(), 1, 0, 1); 
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
	 	document.all['SubTitle'].style.display = 'none';
	 }
}
function TempbeforeSubmit()
{
	var strSQL = "";
	/*
	strSQL = "select missionprop1 from lwmission where 1=1 "
				 + " and activityid = '0000001060' "
				 + " and processid = '0000000003'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";
	*/
		var sqlid13="NoScanContInputSql13";
var mySql13=new SqlClass();
mySql13.setResourceName(sqlresourcename);
mySql13.setSqlId(sqlid113); 
mySql13.addSubPara(fm.PrtNo.value); 	
	
	turnPage.strQueryResult  = easyQueryVer3(mySql13.getString(), 1, 0, 1);  
  
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}
/*
	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	*/
		var sqlid14="NoScanContInputSql14";
var mySql14=new SqlClass();
mySql14.setResourceName(sqlresourcename);
mySql14.setSqlId(sqlid114); 
mySql14.addSubPara(fm.PrtNo.value); 	
mySql14.addSubPara(fm.PrtNo.value);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql14.getString(), 1, 0, 1);  
  
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
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}