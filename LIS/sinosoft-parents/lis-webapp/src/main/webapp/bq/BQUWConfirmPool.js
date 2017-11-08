var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var strOperate = "like";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.BQUWConfirmPoolSql";

/*********************************************************************
 *  执行新契约扫描的EasyQuery
 *  描述:查询显示对象是扫描件.显示条件:扫描件已上载成功
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick()
{
	 initGrpGrid();//初始化表格
	//机构控制<如果没有选择管理机构则使用登陆时的登陆机构>
	if(trim(fm.ManageCom.value)=="")
	{
		//alert("登陆机构代码："+manageCom);
		fm.ManageCom.value=manageCom;
	}
	// 书写SQL语句
	var strSQL = "";
/*		
		strSQL = "select missionprop6,missionprop5,missionprop2,missionprop1,missionprop3,missionid,submissionid,activityid,missionprop4,"
				 + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = lwmission.missionprop6),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = lwmission.missionprop6) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
				 + " from lwmission where 1=1 "
				 + " and activityid = '0000000330' and processid = '0000000000'"
				 + getWherePart('missionprop4','EdorNo')
				 + getWherePart('missionprop2','ContNo')
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop5','EdorType')
				 + " and defaultoperator is null "
				 + " order by (select edorappdate from lpedoritem where EdorAcceptNo = lwmission.missionprop6),missionprop2,missionprop1";
				 	//prompt("",strSQL);
				 	 //alert(strSQL);
				 	 //alert(strOperate);
*/	
	var sqlid1="BQUWConfirmPoolSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(curDay);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.ManageCom.value);
	mySql1.addSubPara(fm.EdorType.value);
	strSQL=mySql1.getString();
	//turnPage.queryModal(mySql.getString(),ManagerDataInfoGrid);// mySql.getString()就可以获得对应的SQL。
				 	 
	turnPage.queryModal(strSQL, GrpGrid);
	HighlightAllRow();
	return true;
}


function easyQueryClickSelf()
{
	// 书写SQL语句
	var strSQL = "";
/*		strSQL = "select missionprop6,missionprop5,missionprop2,missionprop1,missionprop3,missionid,submissionid,activityid,missionprop4, "
				 + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = lwmission.missionprop6),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = lwmission.missionprop6) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
				 + " from lwmission where 1=1 "
				 + " and activityid = '0000000330' and processid = '0000000000'"
				 + " and defaultoperator ='" + operator + "'"
				 + " and missionprop3 like '" + comcode + "%%'" //集中权限管理体现
				 //+ " and missionprop6='"+trim(InputTime)+"' "
				 + " order by (select edorappdate from lpedoritem where EdorAcceptNo = lwmission.missionprop6),missionprop2,missionprop1";	
				 //alert(strSQL) ;
				 //prompt("",strSQL);
*/
	var sqlid2="BQUWConfirmPoolSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(curDay);//指定传入的参数
	mySql2.addSubPara(operator);
	mySql2.addSubPara(comcode);
	strSQL=mySql2.getString();

	turnPage2.queryModal(strSQL, SelfGrpGrid);
	HighlightSelfRow();
	return true;
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,11);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PublicWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,11);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PrivateWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightAllRow(){
		for(var i=0; i<GrpGrid.mulLineCount; i++){
  	var days = GrpGrid.getRowColData(i,11);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		GrpGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<SelfGrpGrid.mulLineCount; i++){
  	var days = SelfGrpGrid.getRowColData(i,11);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrpGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
function GoToInput()
{
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = GrpGrid.getSelNo() - 1;
	if (checkFlag < 0){
	      alert("请选择要申请的保单！");
	      return;
	}
	//alert("checkFlag=="+checkFlag);
	var tEdorAcceptNo = SelfGrpGrid.getRowColData(checkFlag, 1);
  	var tEdorNo = SelfGrpGrid.getRowColData(checkFlag, 9); 	
  	var tEdorType = SelfGrpGrid.getRowColData(checkFlag, 2);
  	var tContNo = SelfGrpGrid.getRowColData(checkFlag, 3); 	
  	var tPrtSeq = SelfGrpGrid.getRowColData(checkFlag, 4); 	
  	//alert("印刷号:"+prtNo);
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 5);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 7);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 8);
    //var InputTime ="";
        //InputTime = GrpGrid.getRowColData(checkFlag, 8);
    
//    var JudgeSql="Select * from lwmission where missionprop2='"+ tContNo +"' and Activityid='"+ActivityID+"'";
	
	var sqlid3="BQUWConfirmPoolSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	mySql3.addSubPara(ActivityID);
	//strSQL=mySql3.getString();

    var arrJudge = easyExecSql(mySql3.getString());
    if(arrJudge==null){
    	alert("查询节点任务失败！");
    	return false;
    }
    
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

//    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该印刷号
	var NoType = type;
    //打开扫描件录入界面
//    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    window.open("./BQUWConfirmMain.jsp?ScanFlag=1&EdorAcceptNo="+tEdorAcceptNo+"&ContNo="+tContNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID, "", sFeatures);        
}
function InitGoToInput()
{ 
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = PrivateWorkPoolGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("请选择要申请的保单！");
	      return;
	}
	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(checkFlag, 1);
	 
  	var tEdorNo = PrivateWorkPoolGrid.getRowColData(checkFlag, 6); 	//alert("tEdorNo=="+tEdorNo);
  	var tEdorType = PrivateWorkPoolGrid.getRowColData(checkFlag, 2);//alert("tEdorType=="+tEdorType);
  	var tContNo = PrivateWorkPoolGrid.getRowColData(checkFlag, 3); 	
  	var tPrtSeq = PrivateWorkPoolGrid.getRowColData(checkFlag, 4); 	
  	//alert("印刷号:"+prtNo);
    var	ManageCom = PrivateWorkPoolGrid.getRowColData(checkFlag, 5);
    var MissionID =PrivateWorkPoolGrid.getRowColData(checkFlag, 10);
    var SubMissionID =PrivateWorkPoolGrid.getRowColData(checkFlag, 11);
    
    var ActivityID = PrivateWorkPoolGrid.getRowColData(checkFlag,13);

//	var JudgeSql="Select * from lwmission where missionprop2='"+ tContNo +"' and Activityid='"+ActivityID+"' ";
 
	var sqlid4="BQUWConfirmPoolSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tContNo);//指定传入的参数
	mySql4.addSubPara(ActivityID);
	//strSQL=mySql4.getString();
 
    var arrJudge = easyExecSql(mySql4.getString());
    if(arrJudge==null){
    	alert("查询节点任务失败！");
    	return false;
    }

    //var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

//    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该印刷号
    //var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //打开扫描件录入界面
//    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    		//alert(prtNo);
    window.open("./BQUWConfirmMain.jsp?ScanFlag=1&EdorAcceptNo="+tEdorAcceptNo+"&ContNo="+tContNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType, "", sFeatures);        
	  HighlightSelfRow();
}
/*
function InitGoToInput()
{ 
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = SelfGrpGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("请选择要申请的保单！");
	      return;
	}
	var tEdorAcceptNo = SelfGrpGrid.getRowColData(checkFlag, 1);
  	var tEdorNo = SelfGrpGrid.getRowColData(checkFlag, 9); 	//alert("tEdorNo=="+tEdorNo);
  	var tEdorType = SelfGrpGrid.getRowColData(checkFlag, 2);//alert("tEdorType=="+tEdorType);
  	var tContNo = SelfGrpGrid.getRowColData(checkFlag, 3); 	
  	var tPrtSeq = SelfGrpGrid.getRowColData(checkFlag, 4); 	
  	//alert("印刷号:"+prtNo);
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 5);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 7);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 8);

//	var JudgeSql="Select * from lwmission where missionprop2='"+ tContNo +"' and Activityid='"+ActivityID+"' ";
 
	var sqlid4="BQUWConfirmPoolSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tContNo);//指定传入的参数
	mySql4.addSubPara(ActivityID);
	//strSQL=mySql4.getString();
 
    var arrJudge = easyExecSql(mySql4.getString());
    if(arrJudge==null){
    	alert("查询节点任务失败！");
    	return false;
    }

    //var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

//    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该印刷号
    //var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //打开扫描件录入界面
//    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    		//alert(prtNo);
    window.open("./BQUWConfirmMain.jsp?ScanFlag=1&EdorAcceptNo="+tEdorAcceptNo+"&ContNo="+tContNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType, "", sFeatures);        
	  HighlightSelfRow();
}
*/
//申请按钮
function ApplyUW()
{

	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}
	//alert(PublicWorkPoolGrid.getRowColData(selno, 10));
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 10);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 11);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	
	//alert(156);
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
	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //提交
}
/*
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
	
	//alert(156);
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //提交
}
*/
//数据提交后返回，同时刷新页面,失败则只返回错误信息，成功则完成后续动作
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
//		showInfo = window.open (urlStr,name, 
//
//		"status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,ti
//
//		tlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight
//
//		+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		//wyc modify
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		//easyQueryClick();// 刷新页面
		jQuery("#publicSearch").click();
		jQuery("#privateSearch").click();
		//easyQueryClickSelf();// 刷新页面
	}
	else
	{
		jQuery("#publicSearch").click();
		jQuery("#privateSearch").click();
		//easyQueryClick();// 刷新页面
		//easyQueryClickSelf();// 刷新页面
	    //GoToInput();//进入录单页面
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
