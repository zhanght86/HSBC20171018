//程序名称：BQMasterCenter.js
//程序功能：管理中心
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var cflag = "4";


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

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		top.close();
	}
}

// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass(); 
var queryBug = 1;
function initQuery() {
	// 初始化表格
	//initPolGrid();
	 
	// 书写SQL语句
/*	var strSql = "select ProposalNo,PrtNo,RiskCode,AppntName,InsuredName from LCPol where " + ++queryBug + "=" + queryBug
    				 + " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pi')=1 "
    				 + " and AppFlag='0' "                 //个人保单
    				 + " and GrpPolNo='" + grpPolNo + "'"  //集体单号，必须为20个0
    				 + " and ContNo='" + contNo + "'"      //内容号，必须为20个0
    				 + " and ManageCom like '" + comcode + "%%'"
    				 + " and uwflag <> '0'"
    				 //+ " and uwflag = '7'"
    				 + " and approveflag = '2'"
    				 //+ " and polno in (select proposalno from lcuwmaster where printflag in ('1','2'))"
    				 + " and PolNo in (select distinct proposalno from lcissuepol where ReplyResult is null and backobjtype = '4')";
*/
//		var strSql = " select missionprop8,missionprop9,missionprop2,missionprop3,missionid,submissionid,missionprop4 "
//							 + " from lwmission where activityid='0000000332' and processid='0000000000' ";
//							 + " and defaultoperator is null";
							    var  sqlid1="RnewMasterCenterSql0";
						 	 	var  mySql1=new SqlClass();
						 	 	mySql1.setResourceName("uw.RnewMasterCenterSql"); //指定使用的properties文件名
						 	 	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
						 	    var strSql2=mySql1.getString();
  //alert(strSql);
	turnPage.queryModal(strSql, PolGrid);
}

var mSwitch = parent.VD.gVSwitch;
function modifyClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cPolNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	
    urlStr = "./ProposalMain.jsp?LoadFlag=3";
    window.open(urlStr);
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
}

function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cProposalNo = "";
	  var cMissionId = "";
	  var cSubMissionId = "";
	  
	  for (i=0; i<SelfPolGrid.mulLineCount; i++) {
	    if (SelfPolGrid.getSelNo(i)) { 
	      checkFlag = SelfPolGrid.getSelNo();
	      break;
	    }
	  }
	  //QuestInputMain.jsp?ContNo="+cProposalNo+"&Flag=5&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020
	  if (checkFlag > 0) { 
	  	cProposalNo = SelfPolGrid.getRowColData(checkFlag - 1, 1); 	
	  	cMissionId  = SelfPolGrid.getRowColData(checkFlag - 1, 3); 	
	  	cSubMissionId = SelfPolGrid.getRowColData(checkFlag - 1, 4);	  	
	  	window.open("../uw/RnewQuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000007015","window1");
	  }
	  else {
	    alert("请先选择一条保单信息！"); 	    
	  }	
	
}

//【查  询】按钮－－－－－－查询符合条件的工作池队列
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	var strOperate="like";
	// 书写SQL语句
	var strSql = "";               

//	 strSql = " select missionprop2,missionprop3,missionid,submissionid,ActivityID "
//							 + " from lwmission where activityid=(select activityid from lwactivity where functionId = '10047015') and exists (select 1 from Lwprocess a  where  a.Processid=Processid and a.Busitype='1004') "
//							 + " and defaultoperator is null"
//							 + " and exists "
//						           +" (select 1 from lccont where ContNo=missionprop2 "
//						           + getWherePart('ManageCom','ComCode',strOperate)
//						           + getWherePart('ManageCom','ManageCom','like')
//						           +" )"		
//               +  getWherePart('MissionProp2','ContNo');
	    
	    var  ComCode = window.document.getElementsByName(trim("ComCode"))[0].value;
        var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
        var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
	    var  sqlid2="RnewMasterCenterSql1";
	 	var  mySql2=new SqlClass();
	 	mySql2.setResourceName("uw.RnewMasterCenterSql"); //指定使用的properties文件名
	 	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	 	mySql2.addSubPara(ComCode);//指定传入的参数
	 	mySql2.addSubPara(ManageCom);//指定传入的参数
	 	mySql2.addSubPara(ContNo);//指定传入的参数
	    var strSql=mySql2.getString();
  turnPage.queryModal(strSql, PolGrid); 
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
	fm.MissionID.value = PolGrid.getRowColData(selno, 3);
	fm.SubMissionID.value = PolGrid.getRowColData(selno, 4);
	fm.ActivityID.value = PolGrid.getRowColData(selno, 5);
	fm.hiddenContNo.value =  PolGrid.getRowColData(selno, 1);
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	//fm.action = "BQMasterCenterChk.jsp";
	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //提交
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
  /*var contNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+prtNo+"&CreatePos=保全管理中心&PolState=0332&Action=DELETE";
  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
*/	
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
  // 刷新查询结果
	easyQueryClickSelf();		
	easyQueryClick();
	//打开新窗口
		  var cProposalNo = fm.hiddenContNo.value;
	  	var cMissionId  = fm.MissionID.value; 	
	  	var cSubMissionId = fm.SubMissionID.value; 	
	  //	var cEdorNo = fm.EdorNo.value;
	  //	var cEdorType = fm.EdorType.value;
	  //	window.open("../uw/BQQuestQueryMain.jsp?EdorNo="+cEdorNo+"&EdorType="+cEdorType+"&ContNo="+cProposalNo+"&Flag=4&MissionID="+cMissionId+"&SubMissionID="+cSubMissionId+"&ActivityID=0000001020","window1");
}




//【查询  待复核管理 队列】
function easyQueryClickSelf()
{

	initSelfPolGrid();
//	var strOperate="like";
	                             
//   	var strSql = " select missionprop2,missionprop3,missionid,submissionid,missionprop4 "
//							 + " from lwmission where activityid=(select activityid from lwactivity where functionId = '10047015') and exists (select 1 from Lwprocess a  where  a.Processid=Processid and a.Busitype='1004') "
//			 + " and defaultoperator ='" + Operator + "' "
//			 + " and exists "
//						           +" (select 1 from lccont where ContNo=missionprop2  "
//						           + getWherePart('ManageCom','ComCode',strOperate)
//						           + getWherePart('ManageCom','ManageCom1','like')
//						           +" ) "		
//             +  getWherePart('MissionProp2','ContNo1')  
//			 + " order by modifydate asc,modifytime asc";
   	
   	var  ComCode = window.document.getElementsByName(trim("ComCode"))[0].value;
    var  ManageCom1 = window.document.getElementsByName(trim("ManageCom1"))[0].value;
    var  ContNo1 = window.document.getElementsByName(trim("ContNo1"))[0].value;
    var  sqlid3="RnewMasterCenterSql2";
 	var  mySql3=new SqlClass();
 	mySql3.setResourceName("uw.RnewMasterCenterSql"); //指定使用的properties文件名
 	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
 	mySql3.addSubPara(Operator);//指定传入的参数
 	mySql3.addSubPara(ComCode);//指定传入的参数
 	mySql3.addSubPara(ManageCom1);//指定传入的参数
 	mySql3.addSubPara(ContNo1);//指定传入的参数
    var strSql=mySql3.getString();
	turnPage2.queryModal(strSql, SelfPolGrid); 
}