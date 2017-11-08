
//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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

function easyQueryClick() {
	var strOperate="like";
	var addStr2 = " and 2=2 ";
	var addStr3 = " and 3=3 ";
	
	var preCom=document.all('PreCom').value;//优先机构
    var preComSQL=" and 1=1 ";
    if(preCom != null && preCom != "")
    {
    	 preComSQL=" and exists (select '1' from ldcom where lwmission.MissionProp5=ldcom.comcode  and ldcom.comareatype1='1') ";
    	 
    	 if(preCom=='b')
    		preComSQL=" and exists (select '1' from ldcom where lwmission.MissionProp5=ldcom.comcode  and (ldcom.comareatype1<>'1' or ldcom.comareatype1 is null)) ";
    }

		 var sqlid1="ProposalApproveModifySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(preComSQL);//指定传入的参数
		mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql1.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql1.addSubPara(fm.MakeDate.value);//指定传入的参数
		mySql1.addSubPara(fm.State.value);//指定传入的参数
		mySql1.addSubPara(comcode);//指定传入的参数
//	    strSql=mySql1.getString();	

//    strSql = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,'',lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop10||' '||lwmission.missionprop11 ,lwmission.missionprop5,lwmission.missionprop6,lwmission.missionprop7,decode(lwmission.missionprop9,'1','问题件已回复','2','问题件未回复') from lwmission where 1=1"
//   			 + " and activityid = '0000001002'"
//   			 + " and processid = '0000000003'" 
//   			 + " and defaultoperator is null "
//   			+ " "+preComSQL+"  "
//			 + getWherePart('lwmission.MissionProp1','ContNo',strOperate)
//			 + getWherePart('lwmission.MissionProp2','PrtNo',strOperate)
//			 + getWherePart('lwmission.MissionProp5','ManageCom',strOperate)  	
//			 + getWherePart('lwmission.MissionProp6','AgentCode',strOperate)  
//			 + getWherePart('lwmission.missionprop10','MakeDate',strOperate)  
//			 + getWherePart('lwmission.MissionProp9','State')  
//			 + " and lwmission.MissionProp5 like '" + comcode + "%%'";  //集中权限管理体现		 
			 if(document.all("SaleChnl").value!=""){
			 	addStr2 = " and exists(select 'x' from lccont where lccont.contno = lwmission.missionprop1 and lccont.salechnl='"+document.all("SaleChnl").value+"')";
			 	
			 }
			 if(fm.BackObj.value != "")
			 {
			 	addStr3 = " and exists(select 1 from lcissuepol,ldcode where lwmission.missionprop1 = contno and codetype = 'backobj' and code  = '"+fm.BackObj.value+"'  and backobjtype = comcode and standbyflag2 = othersign )" ;	
			 }
			 
//   		 strSql = strSql + " order by lwmission.missionprop10,lwmission.missionprop11 ";
	mySql1.addSubPara(addStr2);//指定传入的参数
	mySql1.addSubPara(addStr3);//指定传入的参数
	strSql=mySql1.getString();
	turnPage.queryModal(strSql, PolGrid);
}

function initQuerySelf() {
	var strOperate="like";
	var addStr4 = " and 4=4 ";
			 var sqlid2="ProposalApproveModifySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(operator);//指定传入的参数
		mySql2.addSubPara(fm.ContNo1.value);//指定传入的参数
		mySql2.addSubPara(fm.PrtNo1.value);//指定传入的参数
		mySql2.addSubPara(fm.ManageCom1.value);//指定传入的参数
		mySql2.addSubPara(fm.MakeDate1.value);//指定传入的参数
		mySql2.addSubPara(fm.State1.value);//指定传入的参数
		mySql2.addSubPara(comcode);//指定传入的参数
//	    strSql=mySql2.getString();	
	
//     	strSql = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop3,'',lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop10||' '||lwmission.missionprop11,lwmission.missionprop5,lwmission.missionprop6,lwmission.missionprop7, decode(lwmission.missionprop9,'1','问题件已回复','2','问题件未回复') from lwmission where 1=1"
//   			 + " and activityid = '0000001002'"
//   			 + " and processid = '0000000003'" 
//   			 + " and defaultoperator ='" + operator + "'"
//   			 + getWherePart('lwmission.MissionProp1','ContNo1',strOperate)
//			 	 + getWherePart('lwmission.MissionProp2','PrtNo1',strOperate)
//			   + getWherePart('lwmission.MissionProp5','ManageCom1',strOperate)  	
//			   + getWherePart('lwmission.missionprop10','MakeDate1',strOperate)  
//			   + getWherePart('lwmission.MissionProp9','State1')  
//			   + " and lwmission.MissionProp5 like '" + comcode + "%%'" ; //集中权限管理体现	
			   if(fm.BackObj1.value != "")
			   {
			 	 addStr4 = " and exists(select 1 from lcissuepol where lwmission.missionprop1 = contno and backobjtype = '"+fm.BackObj1.value+"') ";
			   }	 
//   			   strSql = strSql + " order by lwmission.missionprop10,lwmission.missionprop11 ";
  //prompt('',strSql);
	mySql2.addSubPara(addStr4);//指定传入的参数
	strSql=mySql2.getString();	
	turnPage2.queryModal(strSql, SelfPolGrid);
}
var mSwitch = parent.VD.gVSwitch;


/*function modifyClick() {
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
  	
    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
    var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);        
    var tmissionid=PolGrid.getRowColData(PolGrid.getSelNo() - 1, 5);     //missionid
    var tsubmissionid=PolGrid.getRowColData(PolGrid.getSelNo() - 1, 6);  //submissionid
    var ActivityID = fm.ActivityID.value;
	  var NoType = "1";
	  var tSplitPrtNo = prtNo.substring(0,4);
 //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"||tSplitPrtNo=="3110"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
	
		 var sqlid3="ProposalApproveModifySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(prtNo);//指定传入的参数
	     var strSql3=mySql3.getString();	
	
//    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
    var crrResult = easyExecSql(strSql3);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
    
    window.open("./ProposalEasyScan.jsp?LoadFlag=3&prtNo="+prtNo+"&MissionID="+tmissionid+"&SubMissionID="+tsubmissionid+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
}*/

/*function InitmodifyClick() {
	//alert(178);
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<SelfPolGrid.mulLineCount; i++) {
    if (SelfPolGrid.getSelNo(i)) { 
      checkFlag = SelfPolGrid.getSelNo();
      break;
    }
  }
  //alert(1);
  if (checkFlag) {
  
  
  	var cPolNo = SelfPolGrid.getRowColData(checkFlag - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  
    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
    var prtNo = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 2);  
  
    var tmissionid=SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 5);     //missionid
  
    var tsubmissionid=SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 6);  //submissionid
    
    var ActivityID = SelfPolGrid.getRowColData(SelfPolGrid.getSelNo() - 1, 7);
	  
	  var NoType = "1";
	  
	  var strSql="";
    //strSql="select salechnl from lccont where contno='"+prtNo+"'";
    //var 
    //strSql = "select case lmriskapp.riskprop"
    //        +" when 'I' then '1' "
    //        +" when 'D' then '1' "
	//        +" when 'G' then '2'"
	//        +" when 'Y' then '3'"
	//        +" when 'T' then '5'"
    //       + " end"
    //       + " from lmriskapp"
    //       + " where riskcode in (select riskcode"
    //       + " from lcpol"
    //       + " where polno = mainpolno"
    //       + " and prtno = '"+prtNo+"')"    
    //prompt("第一条",strSql);       
    //arrResult = easyExecSql(strSql);
    //if(arrResult==null){
    //    strSql = " select * from ("
    //           + " select case missionprop5"
    //           + " when '05' then '3'"
    //           + " when '12' then '3'"
    //           + " when '13' then '5'"
    //           + " else '1'"
    //           + " end"
    //           + " from lbmission"
    //           + " where missionprop1 = '"+prtNo+"'"
    //           + " and activityid = '0000001099'"
    //           + " union"
    //           + " select case missionprop5"
    //           + " when 'TB05' then '3'"
    //           + " when 'TB12' then '3'"
    //           + " when 'TB06' then '5'"
    //           + " else '1'"
    //           + " end"
    //           + " from lbmission"
    //           + " where missionprop1 = '"+prtNo+"'"
    //           + " and activityid = '0000001098'"
    //           + ") where rownum=1";prompt("",strSql); 
    //    arrResult = easyExecSql(strSql);         
    //}
    //var BankFlag = arrResult[0][0];
    
    
    //去掉原来的判断投保单类型的逻辑，修改为按印刷号来判断投保单类型
    var BankFlag = "";
    var tSplitPrtNo = prtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"||tSplitPrtNo=="3110"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
    //alert("BankFlag=="+BankFlag);
    
			 var sqlid4="ProposalApproveModifySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(prtNo);//指定传入的参数
	     var strSql2=mySql4.getString();	
	
//    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
     
    window.open("./ProposalEasyScan.jsp?LoadFlag=3&prtNo="+prtNo+"&MissionID="+tmissionid+"&SubMissionID="+tsubmissionid+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3" , "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
}*/

//问题件查询
function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cProposalNo = "";
	  var cflag = "2";
	  
	  for (i=0; i<SelfPolGrid.mulLineCount; i++) {
	    if (SelfPolGrid.getSelNo(i)) { 
	      checkFlag = SelfPolGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cProposalNo = SelfPolGrid.getRowColData(checkFlag - 1, 1); 
	    //alert(cProposalNo);
	  }
	  else {
	    alert("请先选择一条保单信息！"); 
	    return false;
	  }
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uw/QuestQueryMain.jsp?ContNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	
}

/*function ApplyUW()
{
    
    //校验是否可操作 是否含有其他问题件
     for (i=0; i<PolGrid.mulLineCount; i++) {
	    if (PolGrid.getSelNo(i)) { 
	      checkFlag = PolGrid.getSelNo();
	      if(PolGrid.getRowColData(checkFlag -1,12)=="有其他问题件")
	      {
	         alert("印刷号为："+PolGrid.getRowColData(checkFlag -1,1)+"的保单含有业务员问题件或机构问题件，不能申请！");
	         return false;
	      }
	    }
	  }
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}


	fm.MissionID.value = PolGrid.getRowColData(selno, 5);
	fm.SubMissionID.value = PolGrid.getRowColData(selno, 6);
	fm.ActivityID.value = PolGrid.getRowColData(selno, 7);
  
	//09-06-06  增加校验如果本单已被申请到个人池则返回错误提示 刷新界面
	
		var sqlid5="ProposalApproveModifySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(fm.MissionID.value);//指定传入的参数
		mySql5.addSubPara(fm.SubMissionID.value);//指定传入的参数
	     var tOperatorSql=mySql5.getString();	
	
//	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
//					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001002'"
//					+ " and defaultoperator is not null";
	var tOperator = easyExecSql(tOperatorSql);
	if(tOperator){
		alert("本单已被其他人员申请到个人工作池！");
		easyQueryClick();
		initQuerySelf();
		return false;
	}
	
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

lockScreen('lkscreen');  
	fm.action = "ProposalApproveChk.jsp";
	document.getElementById("fm").submit(); //提交
	

}

function afterSubmit( FlagStr, content )
{
	showInfo.close();
	unlockScreen('lkscreen');  

	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		// 刷新页面
		easyQueryClick();
		initQuerySelf();
	}
	else
	{	
		modifyClick();	//进入问题件修改页面
		// 刷新页面
		easyQueryClick();
		initQuerySelf();
	}
}

function showNotePad()
{

	var selno = SelfPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = SelfPolGrid.getRowColData(selno, 5);
	var SubMissionID = SelfPolGrid.getRowColData(selno, 6);
	var ActivityID = SelfPolGrid.getRowColData(selno, 7);
	var PrtNo = SelfPolGrid.getRowColData(selno, 2);
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}*/

// modify by lzf 2013-03-18
function ApplyUW()
{
    
    //校验是否可操作 是否含有其他问题件
     for (i=0; i<PublicWorkPoolGrid.mulLineCount; i++) {
	    if (PublicWorkPoolGrid.getSelNo(i)) { 
	      checkFlag = PublicWorkPoolGrid.getSelNo();
	      if(PublicWorkPoolGrid.getRowColData(checkFlag -1,12)=="有其他问题件")
	      {
	         alert("印刷号为："+PublicWorkPoolGrid.getRowColData(checkFlag -1,1)+"的保单含有业务员问题件或机构问题件，不能申请！");
	         return false;
	      }
	    }
	  }
	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}

   
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 12);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 15);
  
	//09-06-06  增加校验如果本单已被申请到个人池则返回错误提示 刷新界面
	
		var sqlid5="ProposalApproveModifySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(fm.MissionID.value);//指定传入的参数
		mySql5.addSubPara(fm.SubMissionID.value);//指定传入的参数
	     var tOperatorSql=mySql5.getString();	
	
//	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
//					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001002'"
//					+ " and defaultoperator is not null";
	var tOperator = easyExecSql(tOperatorSql);
	if(tOperator){
		alert("本单已被其他人员申请到个人工作池！");
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
		return false;
	}
	
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
		// 刷新页面
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
	}
	else
	{	
		modifyClick();	//进入问题件修改页面
		// 刷新页面
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
	}
}

function modifyClick() {
	  var i = 0;
	  var checkFlag = 0;
	  
	  for (i=0; i<PublicWorkPoolGrid.mulLineCount; i++) {
	    if (PublicWorkPoolGrid.getSelNo(i)) { 
	      checkFlag = PublicWorkPoolGrid.getSelNo();
	      break;
	    }
	  }
	  
	  if (checkFlag) { 
	  
	  	var cPolNo = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 1); 	
	  	mSwitch.deleteVar( "PolNo" );
	  	mSwitch.addVar( "PolNo", "", cPolNo );
	  	
	    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
	    var prtNo = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 1);        
	    var tmissionid=PublicWorkPoolGrid.getRowColData(checkFlag- 1, 12);     //missionid
	    var tsubmissionid=PublicWorkPoolGrid.getRowColData(checkFlag - 1, 13);  //submissionid
	    var ActivityID = PublicWorkPoolGrid.getRowColData(checkFlag- 1, 15);
		  var NoType = "1";
		  var tSplitPrtNo = prtNo.substring(0,4);
	 //8635、8625、8615走银代投保单界面，其余的都走个险界面
	    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"||tSplitPrtNo=="3110"){
	    	BankFlag="3";
	    }else{
	    	BankFlag="1";
	    }
		
			 var sqlid3="ProposalApproveModifySql3";
			var mySql3=new SqlClass();
			mySql3.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
			mySql3.setSqlId(sqlid3);//指定使用的Sql的id
			mySql3.addSubPara(prtNo);//指定传入的参数
		     var strSql3=mySql3.getString();	
		
//	    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
	    var crrResult = easyExecSql(strSql3);
	    var SubType="";
	    if(crrResult!=null){
	      SubType = crrResult[0][0];
	    }
	    
	    window.open("./ProposalEasyScan.jsp?LoadFlag=3&prtNo="+prtNo+"&MissionID="+tmissionid+"&SubMissionID="+tsubmissionid+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	  }
	  else {
	    alert("请先选择一条保单信息！"); 
	  }
	}

function InitmodifyClick() {
	//alert(178);
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PrivateWorkPoolGrid.mulLineCount; i++) {
    if (PrivateWorkPoolGrid.getSelNo(i)) { 
      checkFlag = PrivateWorkPoolGrid.getSelNo();
      break;
    }
  }
  //alert(1);
  if (checkFlag) {
  	var cPolNo = PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  
    var prtNo = PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 1);  
  
    var tmissionid=PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 12);     //missionid
  
    var tsubmissionid=PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 13);  //submissionid
    
    var ActivityID = PrivateWorkPoolGrid.getRowColData(checkFlag - 1, 15);
	  
	  var NoType = "1";
	  
	  var strSql="";    
    
    //去掉原来的判断投保单类型的逻辑，修改为按印刷号来判断投保单类型
    var BankFlag = "";
    var tSplitPrtNo = prtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"||tSplitPrtNo=="3110"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
 		var sqlid4="ProposalApproveModifySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(prtNo);//指定传入的参数
	     var strSql2=mySql4.getString();	
	
//    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
     
    window.open("./ProposalEasyScan.jsp?LoadFlag=3&prtNo="+prtNo+"&MissionID="+tmissionid+"&SubMissionID="+tsubmissionid+"&ActivityID="+ActivityID+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3" , "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
}

function showNotePad()
{

	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 12);
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 13);
	var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 15);
	var PrtNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}

function queryAgent()
{
  if(document.all('AgentCode').value == "")	{	 
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //保单号码
     // alert("cAgentCode=="+cAgentCode);
     if(cAgentCode.length!=8){
    	return;
      }	 
	}
}
function afterQuery2(arrResult)
{ 
  if(arrResult!=null)
  {
  PublicWorkPoolQueryGrid.setRowColData(0, 4, arrResult[0][0]);  
  }
}
//end by lzf 2013-03-18
/*function queryAgent()
{
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	//if(document.all('ManageCom').value==""){
	//	 alert("请先录入管理机构信息！");
	//	 return;
	//}
	//tongmeng 2007-09-13 modify
	//查询所有代理人
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //保单号码
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   	
		var sqlid6="ProposalApproveModifySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ProposalApproveModifySql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(cAgentCode);//指定传入的参数
	    var strSQL=mySql6.getString();	
	
//	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode " 
//	         + " and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL);
    //alert(arrResult);
    if (arrResult != null) 
    {
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	//fm.AgentGroup.value = arrResult[0][1];
//  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
     
      //if(fm.AgentManageCom.value != fm.ManageCom.value)
      //{
      //   	
    	//    fm.ManageCom.value = fm.AgentManageCom.value;
    	//    fm.ManageComName.value = fm.AgentManageComName.value;
    	//    //showCodeName('comcode','ManageCom','AgentManageComName');  //代码汉化
    	//   
      //}
      //showContCodeName();
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的业务员不存在，请确认!");
    }
   
	}
}

function afterQuery2(arrResult)
{
  alert("444444"+arrResult);
  if(arrResult!=null)
  {
//  	fm.AgentCode.value = arrResult[0][0];
//  	fm.BranchAttr.value = arrResult[0][93];
//  	fm.AgentGroup.value = arrResult[0][1];
//  	fm.AgentName.value = arrResult[0][5];
//  	fm.AgentManageCom.value = arrResult[0][2];
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	//fm.AgentGroup.value = arrResult[0][1];
  	 // fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];

  	//showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}*/
