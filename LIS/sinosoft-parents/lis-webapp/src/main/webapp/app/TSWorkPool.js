var turnPage = new turnPageClass();
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
		//if(trim(fm.PayDate.value)!="")
		//{
		//	tempfeeSQL=" and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =lwmission.missionprop1 and paydate='"+fm.PayDate.value+"')";
		//}
		
	    var sqlid1="TSWorkPoolSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.TSWorkPoolSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql1.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql1.addSubPara(comcode);//指定传入的参数
		mySql1.addSubPara(kindSql);//指定传入的参数
	    strSQL=mySql1.getString();	
		
//		strSQL = "select missionprop1,modifydate,modifytime,missionprop2,missionprop5,missionprop6,missionid,submissionid,activityid,missionprop7 from lwmission where 1=1 "
//				 + " and activityid = '0000001402' and processid = '0000000003'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop5','InputDate')
//				 + getWherePart('missionprop8','ManageCom',strOperate)
////				 + getWherePart('missionprop8','coolDatePicker')
//				 + " and missionprop8 like '" + comcode + "%%'"  //集中权限管理体现
//				 + " and defaultoperator is null "
//				 //+ " and missionprop6='"+trim(InputTime)+"' "
//				 + kindSql
//				 + " order by missionprop2,missionprop1";
//				 	 //alert(strSQL);
//				 	 //alert(strOperate);
	}
	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


function easyQueryClickSelf()
{
	// 书写SQL语句
	var strSQL = "";
	
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
	
	initSelfGrpGrid();
	if(type =="1")
	{
		
		var sqlid2="TSWorkPoolSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.TSWorkPoolSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(operator);//指定传入的参数
		mySql2.addSubPara(fm.PrtNo1.value);//指定传入的参数
		mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql2.addSubPara(comcode);//指定传入的参数
		mySql2.addSubPara(fm.InputDate1.value);//指定传入的参数
		mySql2.addSubPara(kindSql);//指定传入的参数
	    strSQL=mySql2.getString();	
		
//		strSQL = "select missionprop1,modifydate,modifytime,missionprop2,missionprop5,missionprop6,missionid,submissionid,activityid,missionprop7 from lwmission where 1=1 "
//				 + " and activityid = '0000001402' and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop1','PrtNo1',strOperate)
//				 + getWherePart('missionprop8','ManageCom',strOperate)
//				 + " and missionprop8 like '" + comcode + "%%'"  //集中权限管理体现
//				 + getWherePart('missionprop5','InputDate1')
//				 + kindSql
//				// + " and missionprop3 like '" + comcode + "%%'" //集中权限管理体现
//				// + " and missionprop6 ='"+ InputTime + "'"
//				 + " order by missionprop2,missionprop1";	
//				 //alert(strSQL) ;
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
    var	ManageCom = GrpGrid.getRowColData(checkFlag, 4);//alert("ManageCom"+ManageCom);
    var MissionID =GrpGrid.getRowColData(checkFlag, 7);//alert("MissionID"+MissionID);
    var SubMissionID =GrpGrid.getRowColData(checkFlag, 8);//alert("SubMissionID"+SubMissionID);
    var ActivityID =GrpGrid.getRowColData(checkFlag, 9);//alert("ActivityID"+ActivityID);
    var PolApplyDate=GrpGrid.getRowColData(checkFlag,3);
    var SubType =GrpGrid.getRowColData(checkFlag, 10);
    //var InputTime ="";
        //InputTime = GrpGrid.getRowColData(checkFlag, 8);
    
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //申请该印刷号
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);
	var NoType = type;
    //打开扫描件录入界面
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    window.open("./TSWorkPoolMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+manageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan&InputTime="+InputTime, "", sFeatures+sFeatures1);        
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
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 4);//alert("ManageCom"+ManageCom);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 7);//alert("MissionID"+MissionID);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 8);//alert("SubMissionID"+SubMissionID);
    var PolApplyDate=SelfGrpGrid.getRowColData(checkFlag,3);
    var SubType =SelfGrpGrid.getRowColData(checkFlag, 10);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 9);//alert("ActivityID"+ActivityID);
	//var InputTime ="";
		//InputTime =SelfGrpGrid.getRowColData(checkFlag, 8);//alert("InputTime: "+InputTime);
	  var NoType = type;
	  
	  	var sqlid3="TSWorkPoolSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.TSWorkPoolSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(prtNo);//指定传入的参数
	    var JudgeSql=mySql3.getString();	
	  
//	var JudgeSql="Select * from lwmission where missionprop1='"+ prtNo +"' and Activityid='0000001402'";
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
    		window.open("./TSWorkPoolMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+manageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan&InputTime="+InputTime, "", sFeatures+sFeatures1);        
 		}
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

	fm.MissionID.value = GrpGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = GrpGrid.getRowColData(selno, 8);
	fm.ActivityID.value = GrpGrid.getRowColData(selno, 9);
	
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
		easyQueryClick();// 刷新页面
		easyQueryClickSelf();// 刷新页面
	}
	else
	{//alert(230);
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
