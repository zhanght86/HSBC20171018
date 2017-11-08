//程序名称：QueryUnderwrite.js
//程序功能：个人核保信息查询
//创建日期：2006-09-20 11:32
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var pflag = "1";  //保单类型 1.个人单
var aProposalNo = "";
var sFeatures1 = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
function queryAgent()
{
	//add by wangyc
	//reutrn;
	
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+manageCom+"&branchtype=1&queryflag=0","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
	  var cAgentCode = fm.AgentCode.value;  //保单号码
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   	
		 var sqlid1="QueryUnderwriteSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(cAgentCode);//指定传入的参数
		mySql1.addSubPara(document.all("ManageCom").value);//指定传入的参数
	    var strSQL=mySql1.getString();	
	
//	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' and a.managecom='"+document.all("ManageCom").value+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL); 
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][3]+"]");
    }
    else
    {
      fm.AgentCode.value ="";
    	fm.BranchAttr.value ="";
    	fm.AgentGroup.value ="";
  	  fm.AgentName.value ="";
     alert("编码为:["+cAgentCode+"]的业务员不存在，或者不在该管理机构下，或者已经离职， 请确认!");
    }
	}
}

function afterQuery2(arrResult)
{
	if(arrResult!=null)
	{
	fm.AgentCode.value = arrResult[0][0];
	//fm.BranchAttr.value = arrResult[0][10];
	//fm.AgentGroup.value = arrResult[0][1];
	fm.AgentName.value = arrResult[0][3];
	//fm.AgentManageCom.value = arrResult[0][2];
	//fm.ManageCom.value=arrResult[0][2];
	//showOneCodeName('comcode','ManageCom','ManageComName');
	//showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="操作成功";
  	
  	showInfo.close();
  	alert(showStr);
  	//parent.close();
  	
    //执行下一步操作
  }

}

//既往投保信息
function showApp(cindex)
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  var cContNo=fm.ContNoHide.value;
  var cAppntNo = fm.AppntNo.value;

 if (cindex == 1)
	  window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures1);
 else  if (cindex == 2)
 	 window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+document.all('InsuredNo').value+"&type=2","",sFeatures1);
 else
	  window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo,"",sFeatures1);

showInfo.close();

}   
      
function showUWReport()
{
    var tContNo = fm.ContNoHide.value ;
    if(tContNo !=""){
	window.open("./UWReportMain.jsp?ContNo="+tContNo+"&NoType=5&QueryFlag=5","window1",sFeatures1);
	  }else{
	     alert("请先选择一条记录！");
	  }

}      
      
function SpecQuery(){
	var tContNo = fm.ContNoHide.value;
    var tPrtNo = fm.PrtNoHide.value;
    var tInsuredNo = fm.InsuredNo.value;
	if(tContNo !=""&&tPrtNo!=""){		
	  window.open("../uw/UWManuSpecMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&InsuredNo="+tInsuredNo+"&QueryFlag=2","",sFeatures1);  	
     }else{
        alert("请先选择一条记录！");
     }									
	
}   

//体检查询
function QueryPe(){
	var PrtNo = fm.PrtNoHide.value;
	var tContNo = fm.ContNoHide.value;
	var cMissionID = "";
	var cSubMissionID = "";
	if(tContNo!=""){
		window.open("../uw/UWManuHealthMain.jsp?ContNo1="+tContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+PrtNo+"&otherFlag=1","window1",sFeatures1);
//		var newWindow = OpenWindowNew("../uw/UWManuHealthMain.jsp?ContNo1="+tContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+PrtNo+"&otherFlag=1","window1");
	}else{
		alert("请先选择保单!");  
	}
}
function HospitalQualityQuery(){
//等待需求
}

function QuestMistakeQuery(){
	var PrtNo = fm.PrtNoHide.value;
	var tContNo = fm.ContNoHide.value;
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("投保单号为空！");
		return;
	}

	var varSrc= "?PrtNo="+ PrtNo+"&otherFlag=1" ;//alert(varSrc);
	var newWindow = OpenWindowNew("../uw/MSQuestMistakeMain.jsp?Interface=../uw/MSQuestMistakeInput.jsp" + varSrc,"问题件差错记录","left");
	
//等待需求
}

//以往核保记录
function showOldUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cProposalNo=fm.PolNo.value;
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  if (cProposalNo != "")
  {
  	window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1","top=80,left=80,resizable=yes;"+sFeatures1);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");  	
  }
}

//当前核保记录
//function showNewUWSub()
//{
//  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
//
//  cProposalNo=fm.PolNo.value;
//  if (cProposalNo != "")
//  {
//  	window.open("./UWErrMain.jsp?ProposalNo1="+cProposalNo,"window1","top=80,left=80,resizable=yes ");
//  	showInfo.close();
//  }
//  else
//  {
//  	showInfo.close();
//  	alert("请先选择保单!");  
//  }
//}                      

//既往理赔查询
function queryClaim(){

    var ContNo  = fm.ContNoHide.value;
    if (document.all("ContNoHide").value =='' )
    {    	
        alert("请选择一条记录！");
        return;
    }
    var tContNo =document.all('ContNoHide').value;

	window.open("./ClaimQueryMain.jsp?ContNo="+tContNo+"&ContFlag=1","window1",sFeatures1);
	  
}

//既往保全查询
function queryEdor(){
    var ContNo  = fm.ContNoHide.value;
    if (document.all("ContNoHide").value =='' )
    {    	
        alert("请选择一条记录！");
        return;
    }
    var tContNo =document.all('ContNoHide').value;
    
	window.open("./EdorQueryMain.jsp?ContNo="+tContNo,"window1",sFeatures1);
	
}

//保单明细信息
function showPolDetail()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

  cPrtNo = fm.PrtNoHide.value;
  cContNo = fm.ContNoHide.value;
 // alert(cContNo);
    var BankFlag = "";
    var tSplitPrtNo = "";
    var SubType="";
  if (cContNo != "")
  {
   
     tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    	SubType = "03";
    }else{
    	BankFlag="1";
    	SubType = "01";
    }
  }
//  var BankFlag = document.all('SaleChnl').value;

//	alert(fm.QState.value);
       if(fm.QState.value == "0")   //签单前的保单
       {
  	 window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures1);
        }
       else if(fm.QState.value == "1" || fm.QState.value == "2")   //已经签单或已经销户的保单
       {
		  	 if(fm.QState.value == "1")   //已经签单
		  	 {
		  	    window.open("../sys/AllProQueryMain.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag+"&SubType="+SubType,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures1);	
		  	 }
		        if (fm.QState.value == "2")  //已经销户的保单
			 {
		  	    window.open("../sys/AllProQueryMain_B.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag+"&SubType="+SubType,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures1);
		  	 }
       }
       	
  else
  {  	
  	alert("请先选择保单!");	
  }

}            

//操作履历
function QueryRecord(){
    var tContNo = fm.PrtNo.value;
    if(tContNo!=""){
	window.open("./RecordQueryMain.jsp?ContNo="+document.all('PrtNo').value,"window1",sFeatures1);
	}else{
	   alert("没有合同信息！请选择一条记录");
	}
}

//自动核保提示查询
function showNewUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cContNo=fm.ContNoHide.value;

  	//window.open("./PUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	window.open("./UWErrMain.jsp?ContNo="+cContNo,"window1",sFeatures1);

  	showInfo.close();

}

//既往投保信息
function showApp(cindex)
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  var cContNo=fm.ContNoHide.value;
  var cAppntNo = fm.AppntNo.value;

 if (cindex == 1)
	  window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures1);
 else  if (cindex == 2)
 	 window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+document.all('InsuredNo').value+"&type=2","",sFeatures1);
 else
	  window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo,"",sFeatures1);

showInfo.close();

}

//被保人健康告知查询
function queryInsuredHealthImpart(){
    
    var tInsuredNo = fm.InsuredNo.value;
    if(tInsuredNo == null || tInsuredNo == '')
    {
      alert("没有被保人信息！");
      return;
    }
	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+tInsuredNo,"window1",sFeatures1);
}

//健康告知查询
function queryHealthImpart(){

    var tAppntNo = document.all('AppntNo').value;
    if(tAppntNo==""||tAppntNo ==null)
    {
       alert("没有投保人记录！");
    }else{
	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1",sFeatures1);
	}
}

//查询体检结果
function queryHealthReportResult(){
	var tContNo = fm.ContNoHide.value;
	var tFlag = "2";//不能再次保存体检结果
  if(tContNo!="")
  {
 window.open("./UWManuHealthQMain.jsp?ContNo="+tContNo+"&Flag="+tFlag,"window1",sFeatures1);
 }else{
    alert("请先选择一条记录！");
 }
}

//核保等待查询
function queryReason()
{
	if (document.all("ContNoHide").value =='' )
    {    	
        alert("请选择要查询的保单！");
        return;
    }
    var ContNo = document.all("ContNoHide").value;
    var tPrtNo = fm.PrtNoHide.value;
	
		var sqlid2="QueryUnderwriteSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(ContNo);//指定传入的参数
		mySql2.addSubPara(tPrtNo);//指定传入的参数
	    var aSQL=mySql2.getString();	
	
//	  var aSQL = " select 1 from lwmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2='"+ContNo+"' "
//	  			+" union"
//	  			+" select 1 from lbmission b where b.activityid='0000001100' and b.missionprop18 ='6' and b.MissionProp2='"+tPrtNo+"' ";	
	  var arrResult = easyExecSql(aSQL);
	  if(!arrResult)
	  {
	     alert("没有核保等待信息！");
	     return false;
	  }

	  window.open("./WaitReasonMain.jsp?ContNo="+ContNo+"&MissionID=''&SubMissionID=''&Type=2&OtherFlag=1","window1",sFeatures1);
	  
}

//查询生调结果
function queryRReportResult(){

	var tContNo = fm.ContNoHide.value;
	var tPrtNo = fm.PrtNoHide.value;
	var tFlag = "1";
  if(tContNo !=""){
 window.open("./RReportQueryMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&Flag="+tFlag+"&QueryFlag=2","window1",sFeatures1);
 }else{
    alert("请先选择一条记录！");
 }

}

//问题件查询
function QuestQuery()
{
	cContNo = fm.ContNoHide.value;  //保单号码


  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	window.open("./QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window1",sFeatures1);
  }
  else
  {
  	alert("请先选择保单!");
  }

}

function checkUWState(tState,tMissionID,tSubMissionID,tActivityID,tMessage)
{
		var sqlid3="QueryUnderwriteSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(tMissionID);//指定传入的参数
		mySql3.addSubPara(tSubMissionID);//指定传入的参数
		mySql3.addSubPara(tState);//指定传入的参数
		mySql3.addSubPara(tActivityID);//指定传入的参数
	    var tSQL=mySql3.getString();	
	
//	var tSQL = "select '1' from lwmission where missionid = '"+tMissionID+"' "
//	         + " and SubMissionID = '"+tSubMissionID+"' "
//	         + " and MissionProp18='"+tState+"'"
//	         + " and activityid='"+tActivityID+"' ";
	var arr = easyExecSql( tSQL );
	 if (arr) 
   {
         alert("该合同当前处于"+tState+"状态,不允许"+tMessage);   
         return false;   
   }
	 return true;
} 

 /*********************************************************************
 *  再保信息回复
 *  add by zhangxing 2005-08-10
 *
 *********************************************************************
 */
function showUpReportReply()
{
	  var tContNo = fm.ContNoHide.value;
 		var tMissionID = fm.MissionID.value;
 		var tSubMissionID = fm.SubMissionID.value;
 		var tFlag = "1";
 		var tPrtNo = fm.PrtNoHide.value;
 	if(tContNo!=""&&tPrtNo!=""){
	window.open("./UWManuUpReportReplyMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&Flag="+tFlag,"window1",sFeatures1);
	}else{
	  alert("请先选择一条记录！");
	}
}

/*********************************************************************
 *  显示暂收费信息
 *  add by heyq 2005-04-13
 *
 *********************************************************************
 */
function showTempFee()
{
  var tPrtno =document.all('PrtNoHide').value;
	var tAppntNo = document.all('AppntNo').value;
	var tAppntName = document.all('AppntName').value;
  if((tAppntNo!=""||tAppntNo==null)&&(tAppntName !=""||tAppntName == null)){
  window.open("../uw/UWTempFeeQry.jsp?Prtno=" + tPrtno + "&AppntNo=" + tAppntNo + "&AppntName=" +tAppntName,"",sFeatures1);
  }
}

//客户品质管理
function CustomerQuality() {
   
   var tContNo = fm.ContNoHide.value;
   var tPrtNo = fm.PrtNoHide.value;
  if (tContNo!="" && tPrtNo!="") {
  	window.open("../uw/UWCustomerQualityMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&QueryFlag=1", "window1",sFeatures1);
  }else{
    alert("请先选择一条记录！");
   }
}

//体检医院品质管理
function HospitalQuality() {

  var tContNo = fm.ContNoHide.value;
   var tPrtNo = fm.PrtNoHide.value;
  if (tContNo!="" && tPrtNo!="") {
  	window.open("../uw/UWHospitalQualityMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&QueryFlag=1", "window1",sFeatures1);
  }else{
    alert("请先选择一条记录！");
   }
}

//扫描件查询
function ScanQuery()
{
	
  if (document.all("ContNoHide").value =='' )
    {    	
        alert("请选择一条记录！");
        return;
    }
    
    var ContNo = document.all("ContNoHide").value;
		var tPrtNo = document.all("PrtNoHide").value;
    window.open("./ImageQueryMain.jsp?ContNo=" + tPrtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures1);     
}

function RiskAddFeeQuery(){
    var tContNo = fm.ContNoHide.value;
    var tInsuredNo = fm.InsuredNo.value;
    if(tContNo!=""&&tInsuredNo!=""){
       window.open("../app/RiskAddFeeQuery.jsp?ContNo="+tContNo+"&InsuredNo="+tInsuredNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;"+sFeatures1);
    }else{
       alert("请先选择一条记录！");
    }
}

//核保查询
function UWQuery(){
    var tContNo = fm.ContNoHide.value;
  //  alert("tContNo:"+tContNo);
    if(tContNo!=""){
	window.open("./UWQueryMain.jsp?ContType=1&ContNo="+tContNo,"window1",sFeatures1);
	}else{
	   alert("请先选择一条记录！");
	 }
}

//体检资料
function showHealth()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cProposalNo=fm.ProposalNo.value;
  if (cProposalNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	window.open("./UWManuHealthMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures1);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}           

//条件承保
function showSpec()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  cProposalNo=fm.PolNo.value;
  if (cProposalNo != "")
  {
  	window.open("./UWManuSpecQueryMain.jsp?ProposalNo1="+cProposalNo+"&Flag=3","window1",sFeatures1);  	
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}
                    


//-----------------------------------Beg
//EditBy:Chenrong
//Date:2006-4-27
//查询记事本
function checkNotePad(prtNo){
	
			var sqlid4="QueryUnderwriteSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(prtNo);//指定传入的参数
	    var strSQL=mySql4.getString();	
	
//	var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('NotePadButton').value='记事本（共"+arrResult[0][0]+"条）查询'");
	
}

//-----------------------------------Beg
//EditBy:ln
//Date:2008-12-3
//查询核保分析报告
function checkReport(ContNo){
	
		var sqlid5="QueryUnderwriteSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(ContNo);//指定传入的参数
		mySql5.addSubPara(ContNo);//指定传入的参数
		mySql5.addSubPara(ContNo);//指定传入的参数
	    var strSQL=mySql5.getString();	
	
//	var strSQL="select count(*) from LCUWReport where 1=1"
//		+ " and otherno in (select prtno from lccont where contno='"+ContNo+"'"
//		+ " union select '"+ContNo+"' from dual "
//		+ " union select proposalno from lcpol where contno='"+ContNo+"')"
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('ReportButton').value='核保分析报告（共"+arrResult[0][0]+"条）查询'");
	
}
//---------------------------------End         

/*********************************************************************
 *  初始化上报信息 new
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initUpper(ContNo)
{
	var tSql = "";

		var sqlid6="QueryUnderwriteSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(ContNo);//指定传入的参数
		mySql6.addSubPara(ContNo);//指定传入的参数
	   tSql=mySql6.getString();	

//	tSql = "select sendflag,uwflag,uwidea,upreason,uwcode from lcuwsendtrace where 1=1 "
//					+ " and otherno = '"+ContNo+"'"
//					+ " and othernotype = '1'"
//					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+ContNo+"')"
//					;
 //prompt('',tSql);
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  //判断是否查询成功
  if (turnPage.strQueryResult) {
		document.all('UpperReason').value =arrSelected[0][3];
		document.all('UpperUwUser').value =arrSelected[0][4];
		//DivLCSendTrance.style.display="";
		DivUpper.style.display="";
	}

}

/*********************************************************************
 *  初始化核保订正信息 new
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initRevise(ContNo)
{
	var tSql = "";

		var sqlid7="QueryUnderwriteSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(ContNo);//指定传入的参数
	   tSql=mySql7.getString();	

//	tSql = "select uwidea from lccuwsub where 1=1 "
//					+ " and contno = '"+ContNo+"' and passflag='z'"
//					+ " order by makedate desc,maketime desc" ;;
 //prompt('',tSql);
	var arr=easyExecSql(tSql);
  //判断是否查询成功
  if (arr!=null) {
	    	document.all('Revise').value = arr[0][0];	
      		DivRevise.style.display = "";
	}

}

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
//校验保单状态
function checkPolState()
{ 
  if(trim(fm.QState.value)!="0" && trim(fm.QState.value)!= "1" && trim(fm.QState.value)!= "4" )
 {
   alert("保单状态错误");
   return false;	
 }
   return true;
}

//初始化不用的按钮
function checkButtonUnuse()
{ 
  fm.Button2.disabled = true;
  fm.Button3.disabled = true;
}

/*********************************************************************
 *  点击保单后初始化合同信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initUW()
{
    var k = 1;
    
    var UWState = fm.UWStateHide.value;
    var ContNo = fm.ContNoHide.value;//alert("contno==== "+ ContNo);
    var PrtNo = fm.PrtNoHide.value;//alert("PrtNo==== "+ PrtNo);
    
    var tSqlHighAmnt = " select HighAmntFlag('"+ ContNo +"') from dual";
    var HighAmntFlag=easyExecSql(tSqlHighAmnt,1,0);
    document.all('HighAmntFlag').value = HighAmntFlag;
	
	//判断如果进行过订正，则显示订正原因 否则不显示
    initRevise(ContNo);
    
    //-------------------------------Beg
    //加入记事本查看条数显示
    checkNotePad(PrtNo);
    //-------------------------------End
    
    //-------------------------------Beg
    //加入核保分析报告查看条数显示
    checkReport(ContNo);
    //-------------------------------End
	// 初始化表格
	initPolAddGrid();
	//initPolBox();
	initPolRiskGrid();
//	initSendTrace();
	initUpper(ContNo);
	
	//divLCPol1.style.display= "none";
	
		var sqlid8="QueryUnderwriteSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(k);//指定传入的参数
		//mySql8.addSubPara(k);//指定传入的参数
		mySql8.addSubPara(ContNo);//指定传入的参数
	   strSQL=mySql8.getString();	
	
//		strSQL = "select insuredno,name,sex,(select max(insuredappage) from lcpol where contno=a.contno and insuredno=a.insuredno), "
//			       + " relationtoappnt,relationtomaininsured,nativeplace,(select codename from ldcode where code = a.IDType and codetype = 'idtype'),idno from lcinsured a where "+k+"="+k
//			       + " and contno='" + ContNo + "'"
//			       + " order by a.SequenceNo ";
  
	  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有未通过新契约核保的个人合同单！");
    return;
  }
	
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolAddGrid;

  //保存SQL语句
  turnPage.strQuerySql     = strSQL;

  //设置查询起始位置
  turnPage.pageIndex       = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("查询easyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

//tongmeng 2008-10-06 add
//如果只有一个被保人,默认选择
//alert("PolAddGrid:"+PolAddGrid.canSel+":"+PolAddGrid.mulLineCount);
if(PolAddGrid.canSel==1&&PolAddGrid.mulLineCount>1)
 {
 			//alert('@@');
 			document.all('PolAddGridSel')[0].checked=true;
 			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
 			getInsuredDetail();
 }
 if(PolAddGrid.canSel==1&&PolAddGrid.mulLineCount==1)
 {
 			//alert('@@');
 			document.all('PolAddGridSel').checked=true;
 			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
 			getInsuredDetail();
 }
 		
  //alert("contno21="+document.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+document.all('PrtNo').value);
  var arrSelected = new Array();

		var sqlid9="QueryUnderwriteSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(ContNo);//指定传入的参数
		mySql9.addSubPara(ContNo);//指定传入的参数
	    strSQL=mySql9.getString();	

	//strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark,(select nvl(sum(prem),0) from lcpol where contno='"+ContNo+"'  and uwflag not in ('1','2','a')  ),(select decode(sum(inputprem),null,'0',sum(inputprem)) from lcpoloriginal where prtno=lccont.prtno)from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	//document.all('ProposalContNo').value = arrSelected[0][0];
	document.all('PrtNo').value = arrSelected[0][1];
	document.all('ManageCom').value = arrSelected[0][2];
	document.all('SaleChnl').value = arrSelected[0][3];
	document.all('AgentCode').value = arrSelected[0][4];
	document.all('AgentGroup').value = arrSelected[0][5];
	document.all('AgentCode1').value = arrSelected[0][6];
	document.all('AgentCom').value = arrSelected[0][7];
	document.all('AgentType').value = arrSelected[0][8];
	document.all('ReMark').value = arrSelected[0][9];
	document.all('ShouldPayPrem').value = arrSelected[0][10];
	document.all('OldPayPrem').value = arrSelected[0][11];

		var sqlid10="QueryUnderwriteSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(ContNo);//指定传入的参数
	    strSQL=mySql10.getString();	

//	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag,lcappnt.OccupationCode,lcappnt.OccupationType,lcappnt.NativePlace from lcappnt,LDPerson where contno='"+ContNo+"'"
//		+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	//prompt("",strSQL);
	document.all('AppntName').value = arrSelected[0][0];
	document.all('AppntSex').value = arrSelected[0][1];
	quickGetName('Sex',fm.AppntSex,fm.AppntSexName);
	document.all('AppntBirthday').value = calAge(arrSelected[0][2]);
	document.all('AppntNo').value = arrSelected[0][3];
	document.all('AddressNo').value = arrSelected[0][4];
	document.all('VIPValue').value = arrSelected[0][5];
	quickGetName('VIPValue',fm.VIPValue,fm.VIPValueName);
	document.all('BlacklistFlag').value = arrSelected[0][6];
	quickGetName('blacklistflag',fm.BlacklistFlag,fm.BlacklistFlagName);

	document.all('OccupationCode').value = arrSelected[0][7];
//	quickGetName('OccupationCode',fm.OccupationCode,fm.OccupationCodeName);
	var OccupationCodeName = "";
	var arrOccupation
	
		var sqlid11="QueryUnderwriteSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		mySql11.addSubPara(fm.OccupationCode.value);//指定传入的参数
	    var OccupationCodeSql =mySql11.getString();	
	
//	var OccupationCodeSql = "select occupationname from ldoccupation where occupationcode='"+fm.OccupationCode.value+"'";
	var arrOccupation = easyExecSql(OccupationCodeSql);
	if(arrOccupation!=null){
		OccupationCodeName = arrOccupation[0][0];
	}
	fm.OccupationCodeName.value = OccupationCodeName;
	
	document.all('OccupationType').value = arrSelected[0][8];
	quickGetName('OccupationType',fm.OccupationType,fm.OccupationTypeName);
	
	document.all('NativePlace').value = arrSelected[0][9];
	quickGetName('NativePlace',fm.NativePlace,fm.NativePlaceName);

    //查询上报核保师工号
	
		var sqlid12="QueryUnderwriteSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql12.setSqlId(sqlid12);//指定使用的Sql的id
		mySql12.addSubPara(ContNo);//指定传入的参数
	    var osql =mySql12.getString();	
	
//    var osql = "select missionprop19 from lwmission a where a.activityid='0000001100' and a.missionprop1 ='"+ContNo+"'";
    var upperuwuser = easyExecSql(osql);
    document.all('UpperUwUser').value = upperuwuser;
  //查询投保人的身高、体重，有则显示
//  strSQL = "select case upper(impartparamname)"
//         + " when 'STATURE' then"
//         + " impartparam || 'cm'"
//         + " when 'AVOIRDUPOIS' then"
//         + " impartparam || 'kg'"
//         + " end"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and customernotype = '0'"
//         + " and impartcode in( '000','A0101')"
//         + " and impartver in ('02','A01')"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+ContNo+"'"
//         + " order by impartparamno ";  
		 
		var sqlid13="QueryUnderwriteSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql13.setSqlId(sqlid13);//指定使用的Sql的id
		mySql13.addSubPara(ContNo);//指定传入的参数
	    strSQL=mySql13.getString();			 
		 
  var arr=easyExecSql(strSQL);
	if(arr!=null){
		
		var sqlid14="QueryUnderwriteSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql14.setSqlId(sqlid14);//指定使用的Sql的id
		mySql14.addSubPara(ContNo);//指定传入的参数
	    var sql =mySql14.getString();		
		
//	  var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and customernotype = '0'"
//         + " and impartcode in( '000','A0101')"
//         + " and impartver = in ('02','A01')"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+ContNo+"'"
//         + " order by impartparamno ";
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('Stature').value=arr1[0][1];
			document.all('Weight').value=arr1[1][1];
			document.all('BMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
	}
	
	//alert(document.all('AppntNo').value);
    
    var tSumAmnt =0;
	
		var sqlid15="QueryUnderwriteSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql15.setSqlId(sqlid15);//指定使用的Sql的id
		mySql15.addSubPara(document.all('AppntNo').value);//指定传入的参数
	    strSQL=mySql15.getString();		
	
//    strSQL =  "SELECT nvl(healthyamnt2('" + document.all('AppntNo').value +"','1','1'),0) from dual ";
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('AppntSumLifeAmnt').value=arr1[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr1[0][0],2);
	}
	 
	 	var sqlid16="QueryUnderwriteSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql16.setSqlId(sqlid16);//指定使用的Sql的id
		mySql16.addSubPara(document.all('AppntNo').value);//指定传入的参数
	    strSQL=mySql16.getString();	
	 
//	 strSQL =  "SELECT nvl(healthyamnt2('" + document.all('AppntNo').value +"','2','1'),0) from dual ";
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('AppntSumHealthAmnt').value=arr2[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr2[0][0],2);
	}
	
		 var sqlid17="QueryUnderwriteSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addSubPara(document.all('AppntNo').value);//指定传入的参数
	    strSQL=mySql17.getString();	
	
//	 strSQL =  "SELECT nvl(healthyamnt2('" + document.all('AppntNo').value +"','3','1'),0) from dual ";
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('AppntMedicalAmnt').value=arr3[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
		 var sqlid18="QueryUnderwriteSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql18.setSqlId(sqlid18);//指定使用的Sql的id
		mySql18.addSubPara(document.all('AppntNo').value);//指定传入的参数
	    strSQL=mySql18.getString();	
	
//	strSQL =  "SELECT nvl(healthyamnt2('" + document.all('AppntNo').value +"','4','1'),0) from dual ";
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('AppntAccidentAmnt').value=arr4[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr4[0][0],2);
	}
	
    document.all('AppntSumAmnt').value=tSumAmnt;
    
    //累计保费 不包括趸交和不定期交保费   
	
		var sqlid19="QueryUnderwriteSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql19.setSqlId(sqlid19);//指定使用的Sql的id
		mySql19.addSubPara(document.all('AppntNo').value);//指定传入的参数
		mySql19.addSubPara(document.all('AppntNo').value);//指定传入的参数
		mySql19.addSubPara(document.all('AppntNo').value);//指定传入的参数
	    strSQL=mySql19.getString();	
	
//    strSQL =  "SELECT decode(round(sum(a_Prem),2),'','x',round(sum(a_Prem),2)) FROM"
//          + "(select (case"
//          + " when s_PayIntv = '1' then"
//          + " s_Prem*0.09"
//          + " when s_PayIntv = '3' then"
//          + " s_Prem*0.27"
//          + " when s_PayIntv = '6' then"
//          + " s_Prem*0.52"
//          + " when s_PayIntv = '12' then"
//          + " s_Prem"
//          + " end) a_Prem"          
//          + " FROM (select distinct payintv as s_PayIntv,"
//          + " sum(prem) as s_Prem"
//          + " from lcpol c where polno in(select polno "
//	          + " from lcpol a"
//	          + " where a.insuredno = '"+document.all('AppntNo').value+"'"
//	          + " or (a.appntno = '"+document.all('AppntNo').value+"' and a.riskcode in ('00115000','00115001'))"
//	          + " union"
//	          + " select b.polno"
//	          + " from lcinsuredrelated b"
//	          + " where b.customerno = '"+document.all('AppntNo').value+"')"
//          + " and c.payintv in ('1', '3','6','12')"
//          + " and c.uwflag not in ('1', '2', 'a')"
//          + " and c.appflag <> '4'" 
//          + " and not exists (select 'X'"
//	          + " from lccont"
//	          + " where ContNo = c.contno"
//	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
//          + " group by payintv))";
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	      // alert(arr5[0][0]);  
	      if(arr5[0][0]!='x')  
	          document.all('SumPrem').value=arr5[0][0];
	    }
  
    //查询年收入
	
			var sqlid20="QueryUnderwriteSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql20.setSqlId(sqlid20);//指定使用的Sql的id
		mySql20.addSubPara(ContNo);//指定传入的参数
	    var insql=mySql20.getString();	
	
//    var insql = "select impartparam from LCCustomerImpartParams where upper(impartparamname) in ('ANNUALINCOME','APPNTSALARY')  and customernotype = '0' and contno='"+ContNo+"'";
    //prompt("",insql);
    var incomeway = easyExecSql(insql);
    if(incomeway!=null && incomeway !="")
    {
       document.all('income').value = incomeway;
    }
        
  //进行按钮的明暗显示控制
  ctrlButtonDisabled(ContNo);

  return true;
}

// 查询按钮
function easyQueryClick()
{
    if(verifyInput() ==false)
       return false;
    // 初始化表格
    
    //校验必录项
    if((fm.QPrtNo.value==null||fm.QPrtNo.value=="")&&(fm.QContNo.value==null||fm.QContNo.value=="")&&(fm.QAppntNo.value==null||fm.QAppntNo.value=="")
    		&&(fm.QInsuredNo.value==null||fm.QInsuredNo.value=="")){
    	alert("请至少输入印刷号、保单号、投保人客户号、被保人客户号中的一项进行查询！");
    	return false;
    }
    
    initPolGrid();
    divLCPol1.style.display= "";
    divMain.style.display = "none";
	
	// 书写SQL语句
	k++;
	var mOperate = fm.Operator.value;
	var state = fm.QState.value;       //投保单所处状态 
	var strOperate = "like";
	var strSQL_add = "";
	
		var sqlid21="QueryUnderwriteSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		mySql21.addSubPara(fm.QContNo.value);//指定传入的参数
		mySql21.addSubPara(fm.QManageCom.value);//指定传入的参数
		mySql21.addSubPara(fm.QAgentCode.value);//指定传入的参数
		mySql21.addSubPara(fm.QAppntNo.value);//指定传入的参数
		mySql21.addSubPara(fm.QAppntName.value);//指定传入的参数
		
		mySql21.addSubPara(fm.QInsuredName.value);//指定传入的参数
		mySql21.addSubPara(fm.QInsuredNo.value);//指定传入的参数
		mySql21.addSubPara(fm.QState.value);//指定传入的参数
		mySql21.addSubPara(fm.QPrtNo.value);//指定传入的参数
		mySql21.addSubPara(comcode);//指定传入的参数
	    var strSQL=mySql21.getString();	
	
//	var strSQL = "select contno,prtno,appntname,(select riskname from lmriskapp where riskcode = (select riskcode from lcpol where prtno=a.prtno and insuredno=a.insuredno and polno=mainpolno and rownum='1')),appflag,'','',contno from lccont a where grpcontno  like '0000000000%'"
//		 + getWherePart( 'contno','QContNo' )
//		 + getWherePart( 'ManageCom','QManageCom', strOperate ) 
//		 + getWherePart( 'AgentCode','QAgentCode' )
//		 + getWherePart( 'AppntNo','QAppntNo' )
//		 + getWherePart( 'AppntName','QAppntName')
//		 + getWherePart( 'InsuredName','QInsuredName')
//		 + getWherePart( 'InsuredNo','QInsuredNo' )	 
//		 + getWherePart( 'AppFlag','QState')
//		 + getWherePart( 'PrtNo','QPrtNo');
//		 + " and ManageCom like '" + comcode + "%%'"; //集中权限管理体现	
	 if(document.all('QRiskCode').value !="")
		    {
		       strSQL = strSQL+" and exists(select 1 from lcpol where polno = (select min(polno) from lcpol where prtno=a.prtno and insuredno=a.insuredno) and riskcode ='"+document.all('QRiskCode').value+"')";
		    } 
	  /*
	   var strSQL = "select (select proposalcontno from lccont where contno=t.MissionProp2),"
	                + " t.MissionProp1,t.MissionProp7,"
	                + " (select riskname from lmriskapp where riskcode = (select riskcode from lcpol where prtno=t.MissionProp1 and rownum='1')),"
	                + " (select appflag from lccont where contno=t.MissionProp2),"
	                + " t.missionid,t.submissionid,t.MissionProp2,t.MissionProp18 "	                
	     + " from lwmission t where 1=1 "
		 + " and t.activityid in ('0000001100')"
		 + getWherePart( 't.MissionProp2','QContNo' )
		 + getWherePart( 't.MissionProp10','QManageCom', strOperate ) 
		 + getWherePart( 't.MissionProp4','QAgentCode' )
		 //+ getWherePart( 'Operator','Operator')
		 + getWherePart( 't.MissionProp6','QAppntNo')
		 + getWherePart( 't.MissionProp7','QAppntName')	 
		 + getWherePart( 't.MissionProp1','QPrtNo');
		 + " and ManageCom like '" + comcode + "%%'"; //集中权限管理体现	
	  if(document.all("QInsuredName").value!="")
			 {                                                                                                                                 
				strSQL = strSQL                                                                                                                                                    
				 			 + " and exists(select 'x' from lccont where contno = t.MissionProp2 and insuredname='"+document.all("QInsuredName").value+"')"; 	                                                                                                                                                                   
			 }
	 if(document.all('QRiskCode').value !="")
		    {
		       strSQL = strSQL+" and exists(select 1 from lcpol where polno = (select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1)) and riskcode ='"+document.all('QRiskCode').value+"')";
		    } 
		    if(document.all("QState").value!="")
			 {                                                                                                                                 
				strSQL = strSQL                                                                                                                                                    
				 			 + " and exists(select 'x' from lccont where contno = t.MissionProp2 and appflag='"+document.all("QState").value+"')"; 	                                                                                                                                                                   
			 }			 
			 if(document.all("QInsuredNo").value!="")
			 {                                                                                                                                 
				strSQL = strSQL                                                                                                                                                    
				 			 + " and exists(select 'x' from lccont where contno = t.MissionProp2 and insuredno='"+document.all("QInsuredNo").value+"')"; 	                                                                                                                                                                   
			 }
	    */
	    
	//    prompt('',strSQL);
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有符合查询条件个人保单！");
    return "";
  }
  turnPage.queryModal(strSQL,PolGrid);
  return true;
}

// 查询按钮
function easyQueryAddClick()
{
    //initInpBox();
	//检查查询条件中保单的状态
        if(!checkPolState())
        {
           return false;
        }
  
	// 书写SQL语句
    var tSelNo = PolGrid.getSelNo()-1;
	if( tSelNo < 0 )
	{
		alert("请选择一张保单！");
		return false;
	}
	var tMissionID = PolGrid.getRowColData(tSelNo, 6);
	var tSubMissionID = PolGrid.getRowColData(tSelNo, 7);
	var tContNo = PolGrid.getRowColData(tSelNo, 8);
	var tPrtNo = PolGrid.getRowColData(tSelNo, 2);
	var tUWState = PolGrid.getRowColData(tSelNo, 9);
	
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;
	fm.ContNoHide.value = tContNo;
	fm.PrtNoHide.value = tPrtNo;
	fm.UWStateHide.value = tUWState;
	
  	//初始化合同信息
  	initUW();
  	
  	//初始化不用的按钮
    checkButtonUnuse();

	divMain.style.display = "";
  return true;
}


function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initPolGrid();
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			}// end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}
	


//选择要人工核保保单
function getPolGrid()
{
  var tSelNo = PolGrid.getSelNo()-1;
  fm.ProposalNo.value =PolGrid.getRowColData(tSelNo,2);
  fm.ContNoHide.value = PolGrid.getRowColData(tSelNo , 11);
  fm.PrtNoHide.value = PolGrid.getRowColData(tSelNo,3);
  fm.PolNoHide.value = PolGrid.getRowColData(tSelNo,1);
	//alert("其他的操作begin");
	
	var aCount = PolAddGrid.getSelNo ();
	var tContNo="";
	//alert("选中的行是"+aCount);
	if(aCount<1)
	{
		alert("请选中投保单");
		return ;
	}
	else
	{
		var aPolNo  = PolAddGrid. getRowColData(aCount-1,1);
		aProposalNo = PolAddGrid. getRowColData(aCount-1,2);
		var tPrtNo = PolAddGrid.getRowColData(aCount-1,4);
		tContNo = PolAddGrid.getRowColData(aCount-1,11);
		
		var strSql = " select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,"
		            +" AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+tContNo+"' and conttype ='1'";
	    var arrResult = easyExecSql(strSql);
	    if(arrResult!=null)
	    {
	    fm.CPrtNo.value =arrResult[0][1];
	    fm.CManageCom.value = arrResult[0][2];
	    fm.CSaleChnl.value = arrResult[0][3]; 
	    fm.CAgentCode.value = arrResult[0][4];
	    //fm.CShouldPayPrem.value = arrResult[0][];
	    //fm.CBlacklistFlag.value = arrResult[0][9];
	    fm.CRemark.value = arrResult[0][9];
	    }
		
		var sqlid22="QueryUnderwriteSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql22.setSqlId(sqlid22);//指定使用的Sql的id
		mySql22.addSubPara(tContNo);//指定传入的参数
	    var strSQL=mySql22.getString();	
		
//	    var strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag,AppntNo from lcappnt,LDPerson where contno='"+tContNo+"'"
//		           + "and LDPerson.CustomerNo = LCAppnt.AppntNo";
		var arrResult1 = easyExecSql(strSQL);
		if(arrResult1!=null){
		fm.AppntNoHide.value =arrResult1[0][7];
		fm.CBlacklistFlag.value = arrResult1[0][6];
		fm.CVIPValue.value= arrResult1[0][5];
		}
	    }
	    var HighAmntFlagsql = "select HighAmntFlag('"+tPrtNo+"') from dual";
	    var tHighAmntFlag = easyExecSql(HighAmntFlagsql);
	    fm.CHighAmntFlag.value = tHighAmntFlag;
	    fm.ProposalContNo.value =arrResult[0][0];
	 //初始化投保人信息
	 initAppntMsg(tContNo);
	 initInsuredMsg(tContNo);
	 //初始化工作流信息
     initWorkFlow(tPrtNo);
     checkNotePad(tPrtNo);
}

function initWorkFlow(tPrtNo){
	
		var sqlid23="QueryUnderwriteSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql23.setSqlId(sqlid23);//指定使用的Sql的id
		mySql23.addSubPara(tPrtNo);//指定传入的参数
	    var tSql=mySql23.getString();	
	
//     var tSql = "select missionid,submissionid,activityid from lwmission where missionprop1 = '"+tPrtNo+"'";
     var arrResult = easyExecSql(tSql);
     if(arrResult!=null)
     {
        fm.MissionID.value = arrResult[0][0];
        fm.SubMissionID.value = arrResult[0][1];
        fm.ActivityID.value = arrResult[0][2];
     }
     //有些印刷号会同时拥有多个节点 
}

function initAppntMsg(tContNo){
	
	    var sqlid24="QueryUnderwriteSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql24.setSqlId(sqlid24);//指定使用的Sql的id
		mySql24.addSubPara(tContNo);//指定传入的参数
	    var strSQL=mySql24.getString();	
	
//    var strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag,lcappnt.OccupationCode,lcappnt.OccupationType,lcappnt.NativePlace from lcappnt,LDPerson where contno='"+tContNo+"'"
//		+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	var arrSelected = new Array();
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	if(arrSelected!=null)
	{
	   fm.AppntName.value =arrSelected[0][0];
	   fm.AppntSex.value  =arrSelected[0][1];
	   fm.AppntBirthday.value = arrSelected[0][2];
	   fm.OccupationCode.value = arrSelected[0][7];
	   fm.OccupationType.value = arrSelected[0][8];
	   fm.NativePlace.value =arrSelected[0][9];
	}
	 //查询年收入
	 
	 	  var sqlid25="QueryUnderwriteSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql25.setSqlId(sqlid25);//指定使用的Sql的id
		mySql25.addSubPara(tContNo);//指定传入的参数
	    var insql=mySql25.getString();	
	 
//    var insql = "select impartparam from LCCustomerImpartParams where upper(impartparamname) in ('ANNUALINCOME','APPNTSALARY')  and contno='"+tContNo+"'";
    //prompt("",insql);
    var incomeway = easyExecSql(insql);
    if(incomeway!=""&&incomeway !=null)
         fm.income.value = incomeway;
    
    //查询投保人的身高、体重，有则显示
  
  	 	  var sqlid26="QueryUnderwriteSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql26.setSqlId(sqlid26);//指定使用的Sql的id
		mySql26.addSubPara(tContNo);//指定传入的参数
	    strSQL=mySql26.getString();	
  
//  strSQL = "select case impartparamname"
//         + " when 'stature' then"
//         + " impartparam || 'cm'"
//         + " when 'avoirdupois' then"
//         + " impartparam || 'kg'"
//         + " end"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and customernotype = '0'"
//         + " and impartcode = '000'"
//         + " and impartver = '02'"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+tContNo+"'"
//         + " order by impartparamno ";
  var arr=easyExecSql(strSQL);
	if(arr！=null){
		
	    var sqlid27="QueryUnderwriteSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql27.setSqlId(sqlid27);//指定使用的Sql的id
		mySql27.addSubPara(tContNo);//指定传入的参数
	     var sql =mySql27.getString();	
		
//	  var sql = "select s impartparamname"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and customernotype = '0'"
//         + " and impartcode = '000'"
//         + " and impartver = '02'"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+tContNo+"'"
//         + " order by impartparamno ";
	   var arr1 = easyExecSql(sql);
	   if(arr1！=null)
		document.all('Stature').value=arr1[0][0];
		document.all('Weight').value=arr1[1][0];
		document.all('BMI').value=(arr1[0][0]/arr1[1][0]/arr1[1][0]);
	}
}

//控制人工核保界面按钮的明暗显示
function ctrlButtonDisabled(tContNo){
//alert(tContNo);
 //修改为在Function里直接执行函数，返回结果集合(二维数组:第一列为按钮名称，第二列为disabled属性)。
 //此处所隐藏的按钮于函数ctrlSigUWButton所描述的按钮不通 隐藏是根据需求隐藏的...《核保系统需求10.31-六》
    try
    {
	    var arrStr = easyExecSql("select ctrlSigUWButton('"+tContNo+"') from dual");
	   
	    if(arrStr!=null)
	    {
	    	for(var i=0; i<arrStr.length; i++)
	    	{
//	    		alert(arrStr[i][0]+":"+arrStr[i][1]+":"+arrStr[i][2]);
	    		try {
	    			if(arrStr[i][1]==0)
	    			{
	    				eval("document.all('"+arrStr[i][0]+"').disabled='true'");
	    			}
	    			else
	    			{  				
	    				eval("document.all('"+arrStr[i][0]+"').disabled=''");
	    			}
	    		} 
	    		catch(e) {}
	    		 
	    	}
	    }
    }
    catch(ex)
    {
    
    }     	
}

//  初始化核保师是否已查看了相应的信息标记数组
function initFlag(  tlength )
{
	// 标记核保师是否已查看了相应的信息
      showPolDetailFlag =  new Array() ;
      showAppFlag = new Array() ;
      showHealthFlag = new Array() ;
      QuestQueryFlag = new Array() ;

     var i=0;
	  for( i = 0; i < tlength ; i++ )
		{
			showPolDetailFlag[i] = 0;
			showAppFlag[i] = 0;
			showHealthFlag[i] = 0;
			QuestQueryFlag[i] = 0;
		}

	}

function initInsuredMsg(tContNo)
{
   //alert("initInsuredMsg()");
   
   	    var sqlid28="QueryUnderwriteSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql28.setSqlId(sqlid28);//指定使用的Sql的id
		mySql28.addSubPara(tContNo);//指定传入的参数
	    strSQL =mySql28.getString();	
   
//   strSQL = "select insuredno,name,sex,(select max(insuredappage) from lcpol where contno=a.contno and insuredno=a.insuredno), "
//			       + " relationtoappnt,relationtomaininsured,nativeplace,(select codename from ldcode where code = a.IDType and codetype = 'idtype'),idno from lcinsured a where 1=1"
//			       + " and contno='" + tContNo + "'"
//			       + " order by a.SequenceNo ";
   var arrResult = easyExecSql(strSQL);
   if(arrResult!=null)
   {
      turnPage.queryModal(strSQL,PolAddGrid);
   }
}

function getInsuredDetail(parm1,parm2)
{
   // var InsuredNo=document.all(parm1).all('PolAddGrid1').value;
   // alert("InsuredNo="+InsuredNo);
    var InsuredNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo() - 1, 1);
   // alert("InsuredNo="+InsuredNo);
    var ContNo = fm.ContNoHide.value;
    document.all('InsuredNo').value=InsuredNo;
    //被保人详细信息
	
	   	 var sqlid29="QueryUnderwriteSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql29.setSqlId(sqlid29);//指定使用的Sql的id
		mySql29.addSubPara(ContNo);//指定传入的参数
		mySql29.addSubPara(InsuredNo);//指定传入的参数
	    var ssql =mySql29.getString();	
	
//	 var ssql="select tt.impartparamname,tt.impartparam from LCCustomerImpartParams tt where tt.contno='"+ContNo+"' "
//		  +" and tt.customerno='"+InsuredNo+"' and tt.customernotype='1'"
//		  +" and tt.impartver in( '02','A01') and tt.impartcode in( '000','A0101')";
	 var ssArr=easyExecSql(ssql);
	try
	{
	  if(ssArr!=null)
	  {
	  	 document.all('InsuredStature').value=ssArr[0][1];//身高
		 document.all('InsuredWeight').value=ssArr[1][1];//体重	
		 document.all('InsuredBMI').value=Math.round(parseFloat(ssArr[1][1])/parseFloat(ssArr[0][1])/parseFloat(ssArr[0][1])*10000);
	  }	 	
	}catch(ex){};
	
	//计算被保人风险保额和年收入 2008-11-25 add ln
	//alert(InsuredNo); 
    var tSumAmnt =0;
	
		 var sqlid30="QueryUnderwriteSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql30.setSqlId(sqlid30);//指定使用的Sql的id
		mySql30.addSubPara(InsuredNo);//指定传入的参数
	    var strSQL =mySql30.getString();	
	
//    var strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','1','1'),0) from dual ";
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('InsuredSumLifeAmnt').value=arr1[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr1[0][0],2);
	}
	 
	 	var sqlid31="QueryUnderwriteSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql31.setSqlId(sqlid31);//指定使用的Sql的id
		mySql31.addSubPara(InsuredNo);//指定传入的参数
	    strSQL =mySql31.getString();	
	 
//	 strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','2','1'),0) from dual ";
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('InsuredSumHealthAmnt').value=arr2[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr2[0][0],2);
	}
	
		 var sqlid32="QueryUnderwriteSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql32.setSqlId(sqlid32);//指定使用的Sql的id
		mySql32.addSubPara(InsuredNo);//指定传入的参数
	    strSQL =mySql32.getString();	
	
//	 strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','3','1'),0) from dual ";
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('InsuredMedicalAmnt').value=arr3[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
		var sqlid33="QueryUnderwriteSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql33.setSqlId(sqlid33);//指定使用的Sql的id
		mySql33.addSubPara(InsuredNo);//指定传入的参数
	    strSQL =mySql33.getString();	
	
//	strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','4','1'),0) from dual ";
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('InsuredAccidentAmnt').value=arr4[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr4[0][0],2);
	}
	document.all('InsuredSumAmnt').value=tSumAmnt;
	
    //累计保费 不包括趸交和不定期交保费   
	
		var sqlid34="QueryUnderwriteSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql34.setSqlId(sqlid34);//指定使用的Sql的id
		mySql34.addSubPara(InsuredNo);//指定传入的参数
		mySql34.addSubPara(InsuredNo);//指定传入的参数
		mySql34.addSubPara(InsuredNo);//指定传入的参数
	    strSQL =mySql34.getString();	
	
//    strSQL =  "SELECT decode(round(sum(a_Prem),2),'','x',round(sum(a_Prem),2)) FROM"
//          + "(select (case"
//          + " when s_PayIntv = '1' then"
//          + " s_Prem*0.09"
//          + " when s_PayIntv = '3' then"
//          + " s_Prem*0.27"
//          + " when s_PayIntv = '6' then"
//          + " s_Prem*0.52"
//          + " when s_PayIntv = '12' then"
//          + " s_Prem"
//          + " end) a_Prem"          
//          + " FROM (select distinct payintv as s_PayIntv,"
//          + " sum(prem) as s_Prem"
//          + " from lcpol c where polno in(select polno "
//	          + " from lcpol a"
//	          + " where a.insuredno = '"+InsuredNo+"'"
//	          + " or (a.appntno = '"+InsuredNo+"' and a.riskcode in ('00115000','00115001'))"
//	          + " union"
//	          + " select b.polno"
//	          + " from lcinsuredrelated b"
//	          + " where b.customerno = '"+InsuredNo+"')"
//          + " and c.payintv in ('1', '3','6','12')"
//          + " and c.uwflag not in ('1', '2', 'a')"
//          + " and c.appflag <> '4'" 
//          + " and not exists (select 'X'"
//	          + " from lccont"
//	          + " where ContNo = c.contno"
//	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
//          + " group by payintv))";
    //prompt('',strSQL);
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	       //alert(arr5[0][0]);    
	       if(arr5[0][0]!='x')
	       	  document.all('InsuredSumPrem').value=arr5[0][0];
	    }
	      
    //查询年收入
	
		var sqlid35="QueryUnderwriteSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql35.setSqlId(sqlid35);//指定使用的Sql的id
		mySql35.addSubPara(InsuredNo);//指定传入的参数
		mySql35.addSubPara(ContNo);//指定传入的参数
	     var insql  =mySql35.getString();	
	
//    var insql = "select impartparam from LCCustomerImpartParams where upper(impartparamname) in ('ANNUALINCOME','APPNTSALARY')  and customernotype = '1' and customerno='"+InsuredNo+"' and contno='"+ContNo+"'";
    //prompt("",insql);
    var incomeway = easyExecSql(insql);
    if(incomeway!=null && incomeway !="")
    {
       document.all('Insuredincome').value = incomeway;
    }
	
			var sqlid36="QueryUnderwriteSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql36.setSqlId(sqlid36);//指定使用的Sql的id
		mySql36.addSubPara(ContNo);//指定传入的参数
		mySql36.addSubPara(InsuredNo);//指定传入的参数
	     strSQL =mySql36.getString();	
	
//    strSQL ="select a.sequenceno,a.relationtomaininsured,a.name,a.occupationcode,a.occupationtype,b.BlacklistFlag"
//           + " from LCInsured a,LDPerson b where a.ContNo = '"+ContNo+"' and a.InsuredNo='"+InsuredNo+"'"
//           + "and b.CustomerNo = a.InsuredNo";     
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    initPolRisk(ContNo,InsuredNo);
    
		var sqlid37="QueryUnderwriteSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql37.setSqlId(sqlid37);//指定使用的Sql的id
		mySql37.addSubPara(ContNo);//指定传入的参数
		mySql37.addSubPara(InsuredNo);//指定传入的参数
		mySql37.addSubPara(InsuredNo);//指定传入的参数
		mySql37.addSubPara(ContNo);//指定传入的参数
		mySql37.addSubPara(InsuredNo);//指定传入的参数
		
		mySql37.addSubPara(InsuredNo);//指定传入的参数
		mySql37.addSubPara(ContNo);//指定传入的参数
		mySql37.addSubPara(InsuredNo);//指定传入的参数
		mySql37.addSubPara(InsuredNo);//指定传入的参数
	     var sqlind =mySql37.getString();	
	
//     var sqlind = "select 1 from lcpol where contno<> '"+ContNo+"' " 
//	  + " and ((appntno = '"+InsuredNo+"' and riskcode='121301')"
//	  + " or insuredno = '"+InsuredNo+"' ) "
//	  + " and conttype='1' and appflag in ('1','4') "
//	  + " union "
//	  + " select 1 from lcpol where contno<> '"+ContNo+"' "
//	  + " and (insuredno = '"+InsuredNo+"' or (appntno = '"+InsuredNo+"' and riskcode='121301'))"
//	  + " and conttype='1' and appflag ='0' "
//	  + " union "
//	  + " select 1 from lcpol where contno<> '"+ContNo+"' "
//	  + " and (appntno = '"+InsuredNo+"' or insuredno = '"+InsuredNo+"') "
//	  + " and conttype='2' ";
	//prompt('',sqlind);
	var arrind1 = easyExecSql(sqlind);
	//判断是否查询成功
	if(arrind1==null){
	document.all('indButton2').disabled='true';
	}
	else{
	document.all('indButton2').disabled='';
	}
}


function DisplayInsured()
{
	//InsuredName/InsuredOccupationCode/InsuredOccupationType/Insuredincome
	  //alert("asdfasfsf");
	  try{document.all('SequenceNoCode').value= arrResult[0][0]; }catch(ex){};
	  try{document.all('RelationToMainInsured').value= arrResult[0][1]; }catch(ex){};
    try{document.all('InsuredName').value= arrResult[0][2]; }catch(ex){};
    try{document.all('InsuredOccupationCode').value= arrResult[0][3]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= arrResult[0][4]; }catch(ex){};
    document.all('InsuredBlacklistFlag').value = arrResult[0][5];
	quickGetName('blacklistflag',fm.InsuredBlacklistFlag,fm.InsuredBlacklistFlagName);
    //quickGetName('occupationCode',fm.InsuredOccupationCode,fm.InsuredOccupationCodeName);
	var OccupationCodeName = "";
	var arrOccupation
	
		var sqlid38="QueryUnderwriteSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql38.setSqlId(sqlid38);//指定使用的Sql的id
		mySql38.addSubPara(fm.InsuredOccupationCode.value);//指定传入的参数
	     var OccupationCodeSql =mySql38.getString();	
	
//	var OccupationCodeSql = "select occupationname from ldoccupation where occupationcode='"+fm.InsuredOccupationCode.value+"'";
	var arrOccupation = easyExecSql(OccupationCodeSql);
	if(arrOccupation!=null){
		OccupationCodeName = arrOccupation[0][0];
	}
	fm.InsuredOccupationCodeName.value = OccupationCodeName;
    quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    quickGetName('SequenceNo',fm.SequenceNoCode,fm.SequenceNoName);
    quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
}

function initPolRisk(contno,insuredno)
{
	//alert("contno:"+contno);
	
		var sqlid39="QueryUnderwriteSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql39.setSqlId(sqlid39);//指定使用的Sql的id
		mySql39.addSubPara(contno);//指定传入的参数
		mySql39.addSubPara(insuredno);//指定传入的参数
	     var strSQL =mySql39.getString();	
	
//	var strSQL = "select riskcode,(select riskname from lmrisk where riskcode=a.riskcode),"
//	         + " amnt,mult,insuyear || a.insuyearflag,payyears, "
//           + " payintv,standprem,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='02'),0) ,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='01'),0) ,"
//           + " polno ,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='03'),0) ,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='04'),0) "
//           + " from lcpol a "
//           + " where contno='"+contno+"' "
//           + " and insuredno='"+insuredno+"'";
   //prompt('',strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);0
    alert("没有未通过新契约核保的个人合同单！");
    return;
  }
	
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolRiskGrid;

  //保存SQL语句
  turnPage.strQuerySql     = strSQL;
  //设置查询起始位置
  turnPage.pageIndex       = 0;
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);        
}

var cacheWin=null;
function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObjEx(strCode,showObjc,showObjn);
}
//问题件查询
function QuestQuery()
{
  if (document.all("ContNoHide").value =='' )
    {    	
        alert("请选择要查询的保单！");
        return;
    }
	var cContNo = document.all("ContNoHide").value;  //保单号码    	
	window.open("./QuestQueryMain.jsp?ContNo="+cContNo+"&Flag=1","window1",sFeatures1);	
}

//生存调查报告查询
function RReportQuery()
{
  cProposalNo = fm.ProposalNo.value;  //保单号码
  if (cProposalNo != "")
  {			
     window.open("./RReportQueryMain.jsp?ProposalNo1="+cProposalNo,"",sFeatures1);
  }
  else
  {  	
     alert("请先选择保单!");  	
  }	
}

//保单撤销申请查询
function BackPolQuery()
{
  cProposalNo = fm.ProposalNo.value;  //保单号码
  if (cProposalNo != "")
  {			
     window.open("./BackPolQueryMain.jsp?ProposalNo1="+cProposalNo,"",sFeatures1);
  }
  else
  {  	
      alert("请先选择保单!");  	
  }	
}

//保险计划变更
function showChangePlan()
{
  var tPrtNo = fm.PrtNo.value;
  
  var tInsuredNo = document.all('InsuredNo').value;
  
		var sqlid40="QueryUnderwriteSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("uw.QueryUnderwriteSql"); //指定使用的properties文件名
		mySql40.setSqlId(sqlid40);//指定使用的Sql的id
		mySql40.addSubPara(tPrtNo);//指定传入的参数
		mySql40.addSubPara(tInsuredNo);//指定传入的参数
	     var tSql =mySql40.getString();	

//  var tSql = " select uwidea from lcinduwmaster where contno in "
//           + " (select contno from lccont where prtno='"+tPrtNo+"') and insuredno='"+tInsuredNo+"' ";
  var ChangeIdea = easyExecSql(tSql);
  if(ChangeIdea!=null&&ChangeIdea !="")
  {
    var urlStr="./UWMessagePage.jsp?content=" + ChangeIdea ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  }else{
     alert("没有变更结论！");
  }
}
/*********************************************************************
 *  修改保单生效日期
 *  add by zhangxing 2005-06-28
 *
 *********************************************************************
 */
 function ChangeCvalidate()
 {
 	var tContNo = fm.ContNoHide.value;
 	var tPrtNo = fm.PrtNoHide.value;
	if(tContNo !=""&&tPrtNo!=""){
	 window.open("./UWChangeCvalidateQMain.jsp?ContNo="+tContNo+"&prtNo="+tPrtNo+"&QueryFlag=1","window1",sFeatures1);
	 }else{
	   alert("请先选择一条记录！");
	 }

}

//附件险按钮隐藏函数
function hideAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "none";
	parent.fraInterface.divAddButton2.style.display= "none";
	parent.fraInterface.divAddButton3.style.display= "none";
	parent.fraInterface.divAddButton4.style.display= "none";	
}

//显示隐藏按钮
function showAddButton()
{	
	parent.fraInterface.divAddButton1.style.display= "";
	parent.fraInterface.divAddButton2.style.display= "";
	parent.fraInterface.divAddButton3.style.display= "";
	parent.fraInterface.divAddButton4.style.display= "";
}


function showNotePad()
{	
	var ActivityID = "0000001100";		
  var PrtNo = fm.PrtNoHide.value;
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("请先选择一条记录！");
		return;
	}
   var MissionID = "";
   var SubMissionID = "";
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType + "&UnSaveFlag=1&QueryFlag=1";
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"记事本查询","left");

}


