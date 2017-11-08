//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var k=0;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
/*********************************************************************
 *  执行待新单复核单的EasyQuery
 *  描述:查询显示对象是主险保单.显示条件:主险或任一附加险未新单复核
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick()
{
	k++;
	// 初始化表格
	initGrpGrid();
	// 书写SQL语句
	var strSQL = "";
	var srtOperate ="like";

	//strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate from LCGrpCont where 1=1 "
	//			 +" and ApproveFlag=0 "
	//			 + getWherePart( "PrtNo",'PrtNo',srtOperate )
	//			 + getWherePart( 'ProposalGrpContNo','GrpProposalNo',srtOperate )
	//			 + getWherePart( 'ManageCom','ManageCom',srtOperate )
	//			 + getWherePart( 'AgentCode','AgentCode',srtOperate )
	//			 + getWherePart( 'AgentGroup','AgentGroup',srtOperate )
	//			 + getWherePart( 'SaleChnl','SaleChnl',srtOperate );
	//			 + "order by makedate,maketime";
if(comcode=="%")
{
	strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.lastoperator from lwmission where 1=1 "
				 + " and activityid = '0000011001' "
				 + " and processid = '0000000011'"
				 + " and defaultoperator is null "
				 + getWherePart('missionprop1','GrpProposalNo',srtOperate)
				 //+ getWherePart('missionprop2','PrtNo',srtOperate)
				 //+ getWherePart('missionprop3','SaleChnl',srtOperate)
				 //+ getWherePart('missionprop5','AgentCode',srtOperate)
				 + getWherePart('missionprop4','ManageCom',srtOperate)
				 //+ getWherePart('missionprop6','AgentGroup',srtOperate)
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%'";  //集中权限管理体现					 
				 + " order by lwmission.missionprop2"						//根据印刷号排序
				 ;	
}else{
	strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop7,lwmission.missionprop8,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.lastoperator from lwmission where 1=1 "
				 + " and activityid = '0000011001' "
				 + " and processid = '0000000011'"
				 + " and defaultoperator is null "
				 + getWherePart('missionprop1','GrpProposalNo',srtOperate)
				 //+ getWherePart('missionprop2','PrtNo',srtOperate)
				 //+ getWherePart('missionprop3','SaleChnl',srtOperate)
				 //+ getWherePart('missionprop5','AgentCode',srtOperate)
				 + getWherePart('missionprop4','ManageCom',srtOperate)
				 //+ getWherePart('missionprop6','AgentGroup',srtOperate)
				 + " and LWMission.MissionProp4 like '" + comcode + "%%'" //集中权限管理体现					 
				 + " order by lwmission.missionprop2"						//根据印刷号排序
				 ;	
				 
		}		 
				 //alert(	strSQL);		 	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}

function easyQueryClickSelf()
{
	k++;
	// 初始化表格
	initSelfGrpGrid();
	// 书写SQL语句
	var strSQL = "";

	strSQL = "select decode(A.x, 0, '已点击待核',1, '已回复问题件待核','有未回复问题件'),A.y,A.z,A.m,A.n,A.o,A.p,A.q,A.r,A.s,A.t,A.u from (select case when (select count(*) from lcgrpissuepol where grpcontno=a.missionprop2 )=0 then 0 "
	        +" when (select count(*) from lcgrpissuepol where grpcontno=a.missionprop2 and replyresult is null )=0 then 1 else 2 end x,"
	             + "a.missionprop4 y,a.missionprop2 z,a.missionprop1 m,a.missionprop3 n,a.missionprop7 o,a.indate p,"
	             + "a.missionprop8 q,a.missionid r,a.submissionid s,a.activityid t,a.lastoperator u from lwmission a where 1=1 "
				 + " and activityid = '0000011001' "
				 + " and processid = '0000000011'"
				 + " and defaultoperator ='" + operator + "'"
				 + getWherePart('missionprop1','GrpProposalNoSelf')
				 //+ getWherePart('missionprop2','PrtNo')
				 //+ getWherePart('missionprop3','SaleChnl')
				 //+ getWherePart('missionprop5','AgentCode')
				 + getWherePart('missionprop4','ManageComSelf')
				 //+ getWherePart('missionprop6','AgentGroup')
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%'";  //集中权限管理体现					 
				 + " ) A order by A.x desc"						//根据印刷号排序
				 ;		
				 //alert("ok");	
	turnPage2.queryModal(strSQL, SelfGrpGrid);
	return true;
}
/*********************************************************************
 *  显示EasyQuery的结果
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
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
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
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
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}
function showApproveDetail() { 
	//alert("ok");
  if (GrpGrid.getSelNo() == 0) {
    alert("请先选择一条保单信息！");
    return false;
  } 
  var polNo = GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 1);
  var prtNo = GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 2);
  var cMissionID=GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 6);
  var cSubMissionID = GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 7);
  var ActivityID = GrpGrid.getRowColData(GrpGrid.getSelNo() - 1, 8);
  
/*var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='承保录单' or CreatePos='承保复核') and (PolState='1002' or PolState='1003')";
  var arrResult = easyExecSql(strSql);
  if (arrResult!=null && arrResult[0][1]!=Operator) {
    alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
    return;
  }
  //锁定该印刷号
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=承保复核&PolState=1003&Action=INSERT";
  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
*/
  //配合以前实现过的页面功能，源于ProposalMain.jsp
  mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);

	
	mSwitch.deleteVar("GrpContNo");
	mSwitch.addVar("GrpContNo", "", polNo);
	mSwitch.updateVar("GrpContNo", "", polNo);
  
  //var tsql="select subtype From es_doc_main where doccode='"+polNo+"' order by subtype";
  //				var crr = easyExecSql(tsql);
 //				var ttype="";
 //				//alert(crr);
 //				if(crr!=null)
 //				{
 //				if(crr[0][0]=="1000")
 //				{
 //				 ttype="TB1002";
 //				}else{
 //				 ttype="TB1003";
 //				}
 //			}else{
 //			ttype="TB1002";
 //		}
 var strSql2="select missionprop5 from lbmission where activityid='0000011099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
 				//alert(ttype);
  easyScanWin = window.open("../cardgrp/GroupPolApproveInfo.jsp?LoadFlag=4&SubType="+SubType+"&prtNo="+polNo+"&polNo="+polNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ActivityID="+ActivityID+"&NoType=2","", sFeatures);    
  
}

function InitshowApproveDetail() { 
  if (SelfGrpGrid.getSelNo() == 0) {
    alert("请先选择一条保单信息！");
    return false;
  } 
  var polNo = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 4);
  var prtNo = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 3);
  var cMissionID=SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 9);
  var cSubMissionID = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 10);
  
  var ActivityID = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 11);
  var NoType = "2";
    
/*var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='承保录单' or CreatePos='承保复核') and (PolState='1002' or PolState='1003')";
  var arrResult = easyExecSql(strSql);
  if (arrResult!=null && arrResult[0][1]!=Operator) {
    alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
    return;
  }
  //锁定该印刷号
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=承保复核&PolState=1003&Action=INSERT";
  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
*/
  //配合以前实现过的页面功能，源于ProposalMain.jsp
  mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);

	
	mSwitch.deleteVar("GrpContNo");
	mSwitch.addVar("GrpContNo", "", polNo);
	mSwitch.updateVar("GrpContNo", "", polNo);
  
  
  //var tsql="select subtype From es_doc_main where doccode='"+polNo+"' order by subtype";
 //				var crr = easyExecSql(tsql);
 //				var tsubtype="";
 //				//alert(crr);
 //				if(crr!=null)
 //				{
 //				if(crr[0][0]=="1000")
 //				{
 //				 tsubtype="TB1002";
 //				}else{
 //				 tsubtype="TB1003";
 //				}
 //			}else{
 //			 tsubtype="TB1002";
 //		}
 var strSql2="select missionprop5 from lbmission where activityid='0000011099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
 				
 				
//alert(polNo);
  easyScanWin = window.open("./GroupPolApproveInfo.jsp?ManageCom="+manageCom+"&SubType="+SubType+"&prtNo="+polNo+"&LoadFlag=4&polNo="+polNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType,"", sFeatures);    
  
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
	//showApproveDetail();
}

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
		easyQueryClick();
		easyQueryClickSelf();
	}else
	  {
	     showApproveDetail();
	  }
	
  // 刷新查询结果
	//showApproveDetail();	
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