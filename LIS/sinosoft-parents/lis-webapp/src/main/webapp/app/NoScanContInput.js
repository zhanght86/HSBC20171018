//程序名称：ScanContInput.js
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";


//【申请】
function ApplyInput()
{
	var cPrtNo = trim(SelfPoolQueryGrid.getRowColData(0,2));
	var cManageCom = trim(SelfPoolQueryGrid.getRowColData(0,1));
	
	
	if(cPrtNo == ""){
		alert("请录入投保单号！");
		return;
	}
	//校验所输入数据是否为数字！
	if(isNaN(cPrtNo)){
		alert("投保单号不能是非数字!");return false;
	}
	//校验所输入数据长度是否为14位!
	if(cPrtNo.length !=14){
		alert("投保单号必须为14位数字！");return false;
	}
	if(cManageCom == ""){
		alert("请录入管理机构！");
		return;		
	}
	//if( verifyInput2() == false ) return false;
	
	indx = cManageCom.indexOf(comcode);
	if(indx != 0){
		alert("您没有申请此管理机构的权限");
		return;
	}
	/*if(type=='2'){//对于集体保单的申请
		if (GrpbeforeSubmit1() == false){
			alert("已存在该投保单号，请选择其他值!");
			return false;		
		}
	}
	else*/ if(type=='1'){//对于个人保单的申请
		if (beforeSubmit(cPrtNo) == false){
			alert("已存在该投保单号，请选择其他值!");
			return false;		
		}
	}/*else if(type=='5'){ 
		if (TempbeforeSubmit1() == false){
		    alert("已存在该投保单号，请选择其他值!");
		    return false;		
		}
	}*/
	document.getElementById("fm").submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	//showInfo.close();
	//alert(FlagStr);
  if(FlagStr == "Fail")
  {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  }
  jQuery("#privateSearch").click();
}

function search1()
{
	var tPolProperty = fm.PolProperty.value;
	var mission1="";
	var kindSql="";
	if(tPolProperty!=null||tPolProperty!=""){
		if(trim(tPolProperty)==11){
			mission1="8611";
		}
		if(trim(tPolProperty)==16){
			mission1="8691";
		}
		if(trim(tPolProperty)==21){
			mission1="8671";
		}
	}
	if(mission1!=""){
		kindSql=" and missionprop1 like '"+mission1+"%%'";
	}
	if(trim(tPolProperty)==35){
		kindSql=" and missionprop1 like '8635%%' "
		//" or missionprop1 like '8625%%' or missionprop1 like '8615%%'";
	}
	
	//机构控制<如果没有选择管理机构则使用登陆时的登陆机构>
	if(trim(fm.ManageCom.value)==""){
		fm.ManageCom.value=manageCom;
	}

	var strSQL = "";
	var strOperate="like";
	if(type=='1'){
		var tempfeeSQL="";//该语句拼入 交费日期 查询条件。

		var sqlid77="ContPolinputSql77";
		var mySql77=new SqlClass();
		mySql77.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql77.setSqlId(sqlid77);//指定使用的Sql的id
		mySql77.addSubPara(operator);//指定传入的参数
		mySql77.addSubPara(kindSql);//指定传入的参数
		mySql77.addSubPara(fm.PrtNo.value);//指定传入的参数
		
		mySql77.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql77.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql77.addSubPara(fm.Operator.value);//指定传入的参数
		
		mySql77.addSubPara(trim(comcode));//指定传入的参数
		mySql77.addSubPara(tempfeeSQL);//指定传入的参数
	    strSQL=mySql77.getString();	
	}
	else if (type=='2')
 	{
		var sqlid78="ContPolinputSql78";
		var mySql78=new SqlClass();
		mySql78.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql78.setSqlId(sqlid78);//指定使用的Sql的id
		mySql78.addSubPara(operator);//指定传入的参数
		mySql78.addSubPara(kindSql);//指定传入的参数
		mySql78.addSubPara(fm.PrtNo.value);//指定传入的参数
		
		mySql78.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql78.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql78.addSubPara(fm.Operator.value);//指定传入的参数
		
		mySql78.addSubPara(trim(comcode));//指定传入的参数
	    strSQL=mySql78.getString();	
	}
	else if(type=='5'){
		var sqlid79="ContPolinputSql79";
		var mySql79=new SqlClass();
		mySql79.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql79.setSqlId(sqlid79);//指定使用的Sql的id
		mySql79.addSubPara(operator);//指定传入的参数
		mySql79.addSubPara(kindSql);//指定传入的参数
		mySql79.addSubPara(fm.PrtNo.value);//指定传入的参数
		
		mySql79.addSubPara(fm.InputDate.value);//指定传入的参数
		mySql79.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql79.addSubPara(fm.Operator.value);//指定传入的参数
		
	    strSQL=mySql79.getString();	
	}
	turnPage1.queryModal(strSQL, GrpGrid);
	return true;
}

function easyQueryClick1()
{
	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	if(type=='1')
	{
		
		var sqlid80="ContPolinputSql80";
		var mySql80=new SqlClass();
		mySql80.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql80.setSqlId(sqlid80);//指定使用的Sql的id

		mySql80.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql80.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql80.addSubPara(fm.Operator.value);//指定传入的参数
		mySql80.addSubPara(trim(comcode));//指定传入的参数
		
	    strSQL=mySql80.getString();	
	}
	else if (type=='2')
 	{
		
		var sqlid81="ContPolinputSql81";
		var mySql81=new SqlClass();
		mySql81.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql81.setSqlId(sqlid81);//指定使用的Sql的id

		mySql81.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql81.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql81.addSubPara(fm.Operator.value);//指定传入的参数
		mySql81.addSubPara(trim(comcode));//指定传入的参数
		
	    strSQL=mySql81.getString();	
	}
	else if(type=='5')
	{
		
		var sqlid82="ContPolinputSql82";
		var mySql82=new SqlClass();
		mySql82.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql82.setSqlId(sqlid82);//指定使用的Sql的id

        mySql82.addSubPara(operator);//指定传入的参数
		mySql82.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql82.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql82.addSubPara(fm.Operator.value);//指定传入的参数
		
	    strSQL=mySql82.getString();	
	}

	turnPage1.queryModal(strSQL, GrpGrid);
	return true;
}

//【开始录入】
function GoToInput(){
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<SelfPoolQueryGrid.mulLineCount; i++) {
    if (SelfPoolGrid.getSelNo(i)) { 
      checkFlag = SelfPoolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
    var	prtNo = SelfPoolGrid.getRowColData(checkFlag - 1, 1); 	
    var ManageCom = SelfPoolGrid.getRowColData(checkFlag - 1, 2); 
    var PolApplyDate=SelfPoolGrid.getRowColData(checkFlag-1,3);
    var MissionID = SelfPoolGrid.getRowColData(checkFlag - 1, 6);
    var SubMissionID = SelfPoolGrid.getRowColData(checkFlag - 1, 7);
    var ActivityID = SelfPoolGrid.getRowColData(checkFlag - 1, 9);
	  var NoType = type;
	  
    var strReturn="1";
    if (strReturn == "1") 
     if(type=='1')
     {    		
	 
    	 var sqlid83="ContPolinputSql83";
		var mySql83=new SqlClass();
		mySql83.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql83.setSqlId(sqlid83);//指定使用的Sql的id

        mySql83.addSubPara(prtNo);//指定传入的参数
	    sql=mySql83.getString();	
	 
     	  //sql = "select missionprop5 from lwmission where activityid = '0000001098' and missionprop1 = '"+prtNo+"'";
    		turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    		if(!turnPage.strQueryResult)
    		{
    			alert("扫描上传出错");
    			return;
    		}
    		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);     
         if(turnPage.arrDataCacheSet[0][0]=='05')//银代录入
    		{
    			window.open("./BankContInputNoScanMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);        
    		}else
    		{
    			window.open("./ContInputNoScanMain.jsp?ScanFlag=0&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);        
    		}
    }
    else if(type=='2')
    {
     	window.open("./ContPolInputNoScanMain.jsp?ScanFlag=0&prtNo="+prtNo+"&PolApplyDate="+PolApplyDate+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);
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
	
		 		var sqlid84="ContPolinputSql84";
		var mySql84=new SqlClass();
		mySql84.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql84.setSqlId(sqlid84);//指定使用的Sql的id

        mySql84.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql84.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}
	
		var sqlid85="ContPolinputSql85";
		var mySql85=new SqlClass();
		mySql85.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql85.setSqlId(sqlid85);//指定使用的Sql的id

        mySql85.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql85.getString();	
	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001062' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
   		var sqlid86="ContPolinputSql86";
		var mySql86=new SqlClass();
		mySql86.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql86.setSqlId(sqlid86);//指定使用的Sql的id

        mySql86.addSubPara(fm.PrtNo.value);//指定传入的参数
         mySql86.addSubPara(fm.PrtNo.value);//指定传入的参数
         
	    strSQL=mySql86.getString();	
   
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
	
	   		var sqlid87="ContPolinputSql87";
		var mySql87=new SqlClass();
		mySql87.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql87.setSqlId(sqlid87);//指定使用的Sql的id

        mySql87.addSubPara(fm.PrtNo.value);//指定传入的参数
         
	    strSQL=mySql87.getString();	
	
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

	   	var sqlid88="ContPolinputSql88";
		var mySql88=new SqlClass();
		mySql88.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql88.setSqlId(sqlid88);//指定使用的Sql的id

        mySql88.addSubPara(fm.PrtNo.value);//指定传入的参数
        mySql88.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql88.getString();	


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
	 	document.all['SubTitle'].style.display = 'none';
	 }
}
function TempbeforeSubmit()
{
	var strSQL = "";
	
		 var sqlid89="ContPolinputSql89";
		var mySql89=new SqlClass();
		mySql89.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql89.setSqlId(sqlid89);//指定使用的Sql的id

        mySql89.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql89.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001060' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	    return false;
	}

		 var sqlid90="ContPolinputSql90";
		var mySql90=new SqlClass();
		mySql90.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
		mySql90.setSqlId(sqlid90);//指定使用的Sql的id

        mySql90.addSubPara(fm.PrtNo.value);//指定传入的参数
        mySql90.addSubPara(fm.PrtNo.value);//指定传入的参数
	    strSQL=mySql90.getString();	


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

function assignPrtNo(param){
	fm.PrtNo.value = SelfPoolQueryGrid.getRowColData(0,2);
}

function afterCodeSelect( cCodeName, Field ){
	if (cCodeName=="station"){
		fm.ManageCom.value = SelfPoolQueryGrid.getRowColData(0,1);
	}
	if(cCodeName == "polproperty"){
		fm.ContType.value = SelfPoolQueryGrid.getRowColData(0,4);
	}
}


