//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

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


/*********************************************************************
 *  集体投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	unlockScreen('lkscreen');  
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
  // 刷新查询结果
	easyQueryClick();
	easyQueryClickSelf();		
	}else
	{
	   returnParent(); 
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

// 数据进入投保单详细信息
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpGrid.getSelNo();
	var i = 0;
	var checkFlag = 0;
	var cGrpProposalNo = "";
	var cGrpPrtNo = "";
	var cflag = "2";
	var cMissionID = "";
	var cSubMissionID = "";
	  for (i=0; i<GrpGrid.mulLineCount; i++) {
	    if (GrpGrid.getSelNo(i)) { 
	      checkFlag = GrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = GrpGrid.getRowColData(checkFlag - 1, 2);
	  	cMissionID = GrpGrid.getRowColData(checkFlag - 1, 6);
	  	cSubMissionID = GrpGrid.getRowColData(checkFlag - 1, 7);
	  	ActivityID = GrpGrid.getRowColData(checkFlag - 1, 8);
	  }
	  else {
	    alert("请先选择一条保单信息！"); 
	    return false;
	  }
	  if(trim(cGrpPrtNo)==''||trim(cGrpProposalNo)=='')
	  {
	    alert("请先选择一条有效保单信息！"); 
	    return false;
	  }
	  
	  
	 // var tsql="select subtype From es_doc_main where doccode='"+cGrpProposalNo+"' order by subtype";
 	//			var crr = easyExecSql(tsql);
 	//			var tsubtype="";
 	//			//alert(crr);
 	//			if(crr!=null)
 	//			{
 	//			if(crr[0][0]=="1000")
 	//			{
 	//			 tsubtype="TB1002";
 	//			}else{
 	//			 tsubtype="TB1003";
 	//			}
 	//		}else{
 	//		 tsubtype="TB1002";
 	//	}
 	var strSql2="select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"+cGrpProposalNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	 window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=13&SubType="+SubType+"&prtNo="+cGrpProposalNo+"&polNo="+cGrpProposalNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ActivityID="+ActivityID+"&NoType=2" ,"window1",sFeatures);
	
}



function InitreturnParent()
{
	var arrReturn = new Array();
	var tSel = SelfGrpGrid.getSelNo();
	var i = 0;
	var checkFlag = 0;
	var cGrpProposalNo = "";
	var cGrpPrtNo = "";
	var cflag = "2";
	var cMissionID = "";
	var cSubMissionID = "";
	  for (i=0; i<SelfGrpGrid.mulLineCount; i++) {
	    if (SelfGrpGrid.getSelNo(i)) { 
	      checkFlag = SelfGrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = SelfGrpGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = SelfGrpGrid.getRowColData(checkFlag - 1, 2);
	  	cMissionID = SelfGrpGrid.getRowColData(checkFlag - 1, 6);
	  	cSubMissionID = SelfGrpGrid.getRowColData(checkFlag - 1, 7);
	  	
	  	var ActivityID = SelfGrpGrid.getRowColData(checkFlag - 1, 8);
    	var NoType = "2";
	  	
	  	
	  }
	  else {
	    alert("请先选择一条保单信息！"); 
	    return false;
	  }
	  if(trim(cGrpPrtNo)==''||trim(cGrpProposalNo)=='')
	  {
	    alert("请先选择一条有效保单信息！"); 
	    return false;
	  }
	  
	  
	  // var tsql="select subtype From es_doc_main where doccode='"+cGrpProposalNo+"' order by subtype";
 		//		var crr = easyExecSql(tsql);
 		//		var tsubtype="";
 		//		//alert(crr);
 		//		if(crr!=null)
 		//		{
 		//		if(crr[0][0]=="1000")
 		//		{
 		//		 tsubtype="TB1002";
 	//			}else{
 	//			 tsubtype="TB1003";
 	//			}
 	//		}else{
 	//		 tsubtype="TB1002";
 	//	}
 	var strSql2="select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"+cGrpProposalNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	 window.open("./GroupPolApproveInfo.jsp?LoadFlag=13&SubType="+SubType+"&prtNo="+cGrpProposalNo+"&polNo="+cGrpProposalNo+"&GrpPrtNo="+cGrpPrtNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType,"window1",sFeatures);
	
}
function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// 查询按钮
var queryBug = 1;
function easyQueryClick()
{
	// 初始化表格
	initGrpGrid();
	if (contNo==null||contNo=='')
		contNo = "";
	//alert(contNo);
	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	/*
	 strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate from LCGrpCont where 1=1"
	           +" and ApproveFlag=1"
	           + getWherePart( 'PrtNo' )
		   + getWherePart( 'ProposalGrpContNo','GrpProposalNo' )
		   + getWherePart( 'ManageCom' )
		   + getWherePart( 'AgentCode' )
		   + getWherePart( 'AgentGroup' )
		   + getWherePart( 'SaleChnl' );
	           + "order by makedate,maketime";
	           */
	           
	    if(comcode=="%")
	    {
	  strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
				 + " and activityid = '0000002002' "
				 + " and processid = '0000000004'"		
				 + " and defaultoperator is null "		 
				 + getWherePart('missionprop1','GrpProposalNo',strOperate)
				 //+ getWherePart('missionprop2','PrtNo',strOperate)
				 //+ getWherePart('missionprop3','SaleChnl',strOperate)
				 //+ getWherePart('missionprop5','AgentCode',strOperate)
				 //+ getWherePart('missionprop4','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','AgentGroup',strOperate)
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%%'";  //集中权限管理体现					 
				 + " order by lwmission.missionprop2"
				 ;
				}else{
				strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
				 + " and activityid = '0000002002' "
				 + " and processid = '0000000004'"		
				 + " and defaultoperator is null "		 
				 + getWherePart('missionprop1','GrpProposalNo',strOperate)
				 //+ getWherePart('missionprop2','PrtNo',strOperate)
				 //+ getWherePart('missionprop3','SaleChnl',strOperate)
				 //+ getWherePart('missionprop5','AgentCode',strOperate)
				 //+ getWherePart('missionprop4','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','AgentGroup',strOperate)
				 + " and LWMission.MissionProp4 like '" + comcode + "%%'"  //集中权限管理体现					 
				 + " order by lwmission.missionprop2"
				 ;	
					
				}
	turnPage.queryModal(strSQL, GrpGrid);

}

function easyQueryClickSelf()
{
	// 初始化表格
	initSelfGrpGrid();
	if (contNo==null||contNo=='')
		contNo = "";

	var strSQL = "";

	  strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid from lwmission where 1=1 "
				 + " and activityid = '0000002002' "
				 + " and processid = '0000000004'"	
				 + " and defaultoperator ='" + operator + "'"			 
				 + getWherePart('missionprop1','GrpProposalNoSelf')
				 //+ getWherePart('missionprop2','PrtNo')
				 //+ getWherePart('missionprop3','SaleChnl')
				 //+ getWherePart('missionprop5','AgentCode')
				 //+ getWherePart('missionprop4','ManageCom')
				 //+ getWherePart('missionprop6','AgentGroup')
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%%'";  //集中权限管理体现					 
				 + " order by lwmission.missionprop2"
				 ;

	turnPage2.queryModal(strSQL, SelfGrpGrid);
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initGrpGrid();
		//HZM 到此修改
		GrpGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpGrid.loadMulLine(GrpGrid.arraySave);		
		//HZM 到此修改
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			//GrpGrid.addOne();
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
			  GrpGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		//GrpGrid.delBlankLine();
	} // end of if
}

/*********************************************************************
 *  团体单的问题件查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cGrpProposalNo = "";
	  var cflag = "2";
	  
	  for (i=0; i<GrpGrid.mulLineCount; i++) {
	    if (GrpGrid.getSelNo(i)) { 
	      checkFlag = GrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpGrid.getRowColData(checkFlag - 1, 1);
	  }
	  else {
	    alert("请先选择一条主险保单信息！"); 
	    return false;
	  }
	  if(cGrpProposalNo==null||trim(cGrpProposalNo)=='')
	  {
	    alert("请先选择一条有效保单信息！"); 
	    return false;
	  }
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	 window.open("../uwgrp/QuestQueryMain.jsp?ProposalNo1="+cGrpProposalNo+"&Flag="+cflag,"",sFeatures);
	
}


/*********************************************************************
 *  复核修改完成后确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ModifyMakeSure(){
	  var i = 0;
	  var checkFlag = 0;
	  var cGrpProposalNo = "";
	  var cGrpPrtNo="";
	  var cflag = "2";
	  
	  for (i=0; i<GrpGrid.mulLineCount; i++) {
	    if (GrpGrid.getSelNo(i)) { 
	      checkFlag = GrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = GrpGrid.getRowColData(checkFlag - 1, 2);
	  }
	  else {
	    alert("请先选择一条保单信息！"); 
	    return false;
	  }
	  if(trim(cGrpPrtNo)==''||trim(cGrpProposalNo)=='')
	  {
	    alert("请先选择一条有效保单信息！"); 
	    return false;
	  }
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	 window.open("./GrpApproveModifyMakeSure.jsp?GrpProposalNo="+trim(cGrpProposalNo)+"&GrpPrtNo="+trim(cGrpPrtNo),"window1");
	
	}
	
function ApplyUW()
{
  //只能是四位区站操作
	var tLine=manageCom.length;
	if(tLine<4)
	{
		alert("只能在四位代码的机构进行系统操作！");
		return false;
	}
	
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
	fm.action = "../uwgrp/ManuUWAllChk.jsp";
	document.getElementById("fm").submit();//提交
	//showInfo.close();
	//returnParent();
}	

function showNotePad()
{

	var selno = SelfGrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = SelfGrpGrid.getRowColData(selno, 6);
	var SubMissionID = SelfGrpGrid.getRowColData(selno, 7);
	var ActivityID = SelfGrpGrid.getRowColData(selno, 8);
	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
	var NoType = "2";
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}