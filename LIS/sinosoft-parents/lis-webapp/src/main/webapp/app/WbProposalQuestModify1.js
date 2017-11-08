//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

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
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		showSubmitFrame(mDebug);
		document.getElementById("fm").submit(); //提交
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
  try
  {
    showInfo.close();
  }
  catch(e){}
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
		return;
	}
	showApproveDetail();
  //刷新查询结果
	easyQueryClick();
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
	// 初始化表格
	initPolGrid();
	var strOperate="like";
	 var preCom=document.all('PreCom').value;//优先机构
	    var preComSQL='and 1=1';
	    
	    if(preCom != null && preCom != "")
	    {
	    	 preComSQL=" and exists (select '1' from ldcom where lwmission.missionprop13=ldcom.comcode and ldcom.comareatype1='1') ";
	    	 
	    	 if(preCom=='b')
	    		
	    		preComSQL=" and exists (select '1' from ldcom where lwmission.missionprop13=ldcom.comcode and (ldcom.comareatype1<>'1' or ldcom.comareatype1 is null)) ";
	    }
	// 书写SQL语句
	var strSql = "";               
//	strSql = "select lwmission.missionprop1,lwmission.missionprop15,lwmission.missionprop13,lwmission.missionprop3,lwmission.missionprop14,lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop16,lwmission.missionprop4 from lwmission where 1=1"
//			+ " and activityid = '"+mActivityID+"'"
//			//+ " and processid = '0000000003'"
//			+ "  "+preComSQL+"  "
//			+ getWherePart('lwmission.MissionProp1','PrtNo',strOperate)
//			+ getWherePart('lwmission.MissionProp15','RiskCode',strOperate)
//			//+ getWherePart('lwmission.MissionProp13','ManageCom',"=")
//			+ getWherePart('lwmission.MissionProp13','ManageCom',strOperate)
//			+ getWherePart('lwmission.MissionProp7','BPOID',strOperate)
//			+ " and LWMission.MissionProp13 like '" + ComCode + "%%'"  
//			+ " and defaultoperator is null ";
	  
	
	//销售渠道
	if(document.all("ScanDate").value!="")
	{
	  strSql = strSql + " and lwmission.MissionProp14<='"+document.all("ScanDate").value+"'";
	}
		
	var tPolProp = document.all('PolProp1').value;  //保单性质
	if(tPolProp != null && tPolProp != "")
	{
	  strSql = strSql + " and substr(lwmission.MissionProp1,3,2) = '"+tPolProp+"' ";
	}
   
//	strSql = strSql + " order by missionprop14 asc,missionprop16 asc";	
	
	var  PrtNo = window.document.getElementsByName(trim("PrtNo"))[0].value;
	  var  RiskCode = window.document.getElementsByName(trim("RiskCode"))[0].value;
	  var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
	  var  BPOID = window.document.getElementsByName(trim("BPOID"))[0].value;
	  
	  var sqlid1="WbProposalQuestModifySql1";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("app.WbProposalQuestModifySql");
	  mySql1.setSqlId(sqlid1);//指定使用SQL的id
	  mySql1.addSubPara(mActivityID);//指定传入参数
	  mySql1.addSubPara(preComSQL);//指定传入参数
	  mySql1.addSubPara(PrtNo);//指定传入参数
	  mySql1.addSubPara(RiskCode);//指定传入参数
	  mySql1.addSubPara(ManageCom);//指定传入参数
	  mySql1.addSubPara(BPOID);//指定传入参数
	  mySql1.addSubPara(ComCode);//指定传入参数
	  mySql1.addSubPara(document.all("ScanDate").value);
	  mySql1.addSubPara(tPolProp);
	  var strSql = mySql1.getString();
	
	
	turnPage.queryModal(strSql, PolGrid);
}

//【查询  待复核保单 队列】
function easyQueryClickSelf()
{
	var strOperate="like";
	initSelfPolGrid();
//	var strSql = "";                             
//   	strSql = "select lwmission.missionprop1,lwmission.missionprop15,lwmission.missionprop13,lwmission.missionprop3,lwmission.missionprop14,lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop16,lwmission.missionprop4 from lwmission where 1=1"
//   			 + " and activityid = '"+mActivityID+"'"
//   			// + " and processid = '0000000003'"
//			   + " and defaultoperator ='" + Operator + "'"
//			   + getWherePart('lwmission.MissionProp1','PrtNo1',strOperate)
//				 + getWherePart('lwmission.MissionProp15','RiskCode1',strOperate)
//				 //+ getWherePart('lwmission.MissionProp13','ManageCom1',"=")
//				 + getWherePart('lwmission.MissionProp13','ManageCom1',strOperate)
//			   + getWherePart('lwmission.MissionProp7','BPOID1',strOperate)
	 
	  var  PrtNo1 = window.document.getElementsByName(trim("PrtNo1"))[0].value;
	  var  RiskCode1 = window.document.getElementsByName(trim("RiskCode1"))[0].value;
	  var  ManageCom1 = window.document.getElementsByName(trim("ManageCom1"))[0].value;
	  var  BPOID1 = window.document.getElementsByName(trim("BPOID1"))[0].value;		   
			   
	  var sqlid2="WbProposalQuestModifySql2";
	  var mySql2=new SqlClass();
	  mySql2.setResourceName("app.WbProposalQuestModifySql");
	  mySql2.setSqlId(sqlid2);//指定使用SQL的id
	  mySql2.addSubPara(mActivityID);//指定传入参数
	  mySql2.addSubPara(Operator);//指定传入参数
	  mySql2.addSubPara(PrtNo1);//指定传入参数
	  mySql2.addSubPara(RiskCode1);//指定传入参数
	  mySql2.addSubPara(ManageCom1);//指定传入参数
	  mySql2.addSubPara(BPOID1);//指定传入参数
	  var strSql = mySql2.getString();
		//销售渠道
	if(document.all("ScanDate1").value!="")
	{
	  strSql = strSql + " and lwmission.MissionProp14<='"+document.all("ScanDate1").value+"'";
	}
		
	var tPolProp1 = document.all('PolProp11').value;  //保单性质
	if(tPolProp1 != null && tPolProp1 != "")
	{
	  strSql = strSql + " and substr(lwmission.MissionProp1,3,2) = '"+tPolProp1+"' ";
	}

	strSql = strSql + " order by missionprop14 asc,missionprop16 asc";			   
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
	//alert("fm.MissionID.value: "+fm.MissionID.value);
	//alert("fm.SubMissionID.value: "+fm.SubMissionID.value);
	//alert("fm.ActivityID.value: "+fm.ActivityID.value);
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

function showApproveDetail() 
{ 
	var selno = PolGrid.getSelNo() - 1;
	if (selno <0)
	{
	alert("请选择要处理的投保单！");
	return;
	}  
	var tMissionID = PolGrid.getRowColData(selno, 6);
	var tSubMissionID= PolGrid.getRowColData(selno, 7);
	var tActivityID = PolGrid.getRowColData(selno, 8); 
	var prtNo = PolGrid.getRowColData(selno, 1);
	
	
 // var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and CreatePos='"+tCreatePos+"'  and  PolState='"+tPolState+"'";
  
  var sqlid3="WbProposalQuestModifySql3";
  var mySql3=new SqlClass();
  mySql3.setResourceName("app.WbProposalQuestModifySql");
  mySql3.setSqlId(sqlid3);//指定使用SQL的id
  mySql3.addSubPara(prtNo);//指定传入参数
  mySql3.addSubPara(tCreatePos);//指定传入参数
  mySql3.addSubPara(tPolState);//指定传入参数
  var strSql = mySql3.getString();
  
  //prompt("",strSql);
  var arrResult = easyExecSql(strSql);
  //alert("arrResult[0][1]"+arrResult[0][1]);
  //alert("Operator"+Operator);
  if (arrResult!=null && arrResult[0][1] != Operator) {
    alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
    return;
  }
  
  //锁定该印刷号
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos="+tCreatePos+"&PolState="+tPolState+"&Action=INSERT";
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  mSwitch.deleteVar( "PrtNo" );
  mSwitch.addVar( "PrtNo", "", prtNo );
  
  var tType=PolGrid.getRowColData(PolGrid.getSelNo() - 1, 4);
  var tBussNoType=PolGrid.getRowColData(PolGrid.getSelNo() - 1, 10);

  //alert("tType:"+tType);
  //alert("tBussNoType:"+tBussNoType);
  window.open("./WbProposalInputMain1.jsp?LoadFlag="+tLoadFlag+"&DealType="+tType+"&prtNo="+prtNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&BussNoType="+tBussNoType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	//EasyScanWin = window.open("./ProposalApproveEasyScan.jsp?LoadFlag=5&prtNo="+prtNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&BankFlag="+BankFlag+"&SubType="+SubType+"&NoType=1", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");    
} 

function unlock(prtNo)
{
	//alert("prtNo"+prtNo);
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos="+tCreatePos+"&PolState="+tPolState+"&Action=DELETE";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	easyQueryClick();
	easyQueryClickSelf();	
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
	var tActivityID = SelfPolGrid.getRowColData(selno, 8); 
	var prtNo = SelfPolGrid.getRowColData(selno, 1);
	
	/*
  var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and CreatePos='"+tCreatePos+"'  and  PolState='"+tPolState+"'";
  //prompt("",strSql);
  var arrResult = easyExecSql(strSql);
  //alert("arrResult[0][1]"+arrResult[0][1]);
  //alert("Operator"+Operator);
  if (arrResult!=null && arrResult[0][1] != Operator) {
    alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
    return;
  }
  */
  //锁定该印刷号
  //tongmeng 2009-03-31 modify  
  //不用做此操作了...
  //var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos="+tCreatePos+"&PolState="+tPolState+"&Action=INSERT";
 // showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 

  mSwitch.deleteVar( "PrtNo" );
  mSwitch.addVar( "PrtNo", "", prtNo );
  
  var tType=SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 4);
  var tBussNoType=SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 10);

  //alert("tType:"+tType);
  //alert("tBussNoType:"+tBussNoType);
  window.open("./WbProposalInputMain1.jsp?LoadFlag="+tLoadFlag+"&DealType="+tType+"&prtNo="+prtNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&BussNoType="+tBussNoType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
} 



function queryAgent()
{
	if(document.all('AgentCode').value == "")	
	{
	//tongmeng 2007-09-10 modify 因为银代复核也使用该菜单，所以去掉对代理人展业类型的限制
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	//var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	//var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
    else
	{
		var cAgentCode = fm.AgentCode.value;  //保单号码
		//tongmeng 2007-09-10 modify 因为银代复核也使用该菜单，所以去掉对代理人展业类型的限制
//		var strSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name from laagent a,latree b,labranchgroup c where 1=1 "
//		     + "and a.agentcode = b.agentcode " //+" and a.branchtype in ('1','4') "
//		     + " and a.agentstate!='03' and a.agentstate!='04' and a.agentgroup = c.agentgroup and a.agentcode='"+cAgentCode+"'";
		
		  var sqlid4="WbProposalQuestModifySql4";
		  var mySql4=new SqlClass();
		  mySql4.setResourceName("app.WbProposalQuestModifySql");
		  mySql4.setSqlId(sqlid4);//指定使用SQL的id
		  mySql4.addSubPara(cAgentCode);//指定传入参数
		  var strSQL = mySql4.getString();
		  
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