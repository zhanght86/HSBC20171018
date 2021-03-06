// 该文件中包含客户端需要处理的函数和事件
// window.onfocus=myonfocus;
var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass();
var myCheckInsuAccNo = "";
var myCheckDate = "";
var sqlresourcename = "productdef.LDRiskToRateSql";

function easyQueryClick(){
	// 初始化表格
	initCollectivityGrid();	
	var mySql1=new SqlClass();
	var sqlid1 = "LPRiskToRateSql2";
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.RiskCode.value);//指定传入的参数
	mySql1.addSubPara(fm.RateType.value);//指定传入的参数
	var sql = mySql1.getString();
	turnPage.pageLineNum = 10; 
	turnPage.queryModal(sql,CollectivityGrid);
	if(CollectivityGrid.mulLineCount==0){
		myAlert(""+"该产品没有这个类型的费率！"+"");
	}
}

function ShowRemark(){
	var sqlid2 = "LPRiskToRateSql3";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid2);//指定使用的Sql的id
	var checkrow = CollectivityGrid.getSelNo();
	if(checkrow==""||checkrow==null||checkrow=="null"){
		myAlert(""+"请选择一条数据"+"");
		return false;
	}
	var RiskCode = CollectivityGrid.getRowColData(checkrow-1,1);
	var RateType = CollectivityGrid.getRowColData(checkrow-1,3);
	mySql1.addSubPara(RiskCode);//指定传入的参数
	mySql1.addSubPara(RateType);//指定传入的参数
	sql = mySql1.getString();
	var remark = easyExecSql(sql);
	if(remark==null)
		remark='';
	fm.all("Remark").value = remark;
}

function download(){
	var checkrow = CollectivityGrid.getSelNo();
	if(checkrow==""||checkrow==null||checkrow=="null"){
		myAlert(""+"请选择一条数据"+"");
		return false;
	}
	var tableName = CollectivityGrid.getRowColData(checkrow-1,4);
	tableName = "PD_"+tableName;
	fm.action = "./MakeRateDataDisk.jsp?tableName="+tableName;
	fm.submit();
}

function textCounter(){
	var field=fm.AuditConclusion.value;	
	//field=field.replace("\r\n" ,"");
  var arr = field.split("\n");
  var perLine="";
  var characterCount=0;
  for(var i=0;i<arr.length;i++){
  	perLine=arr[i];
  	characterCount+=perLine.length;
  }
  if(characterCount>60){
  	myAlert(""+"您输入的内容过长，请您最多输入60个汉字（包括标点符号和换行）"+"");
  	return false;
  }
}

function ThroughClick(){
	var checkrow = CollectivityGrid.getSelNo();
	if(checkrow==""||checkrow==null||checkrow=="null"){
		myAlert(""+"请选择一条数据"+"");
		return false;
	}
	if(fm.all("AuditConclusion").value == "")
  {
  	myAlert(""+"请录入审核结论"+"");
  	return;
	}
	if(false==textCounter()){
		return false;
	}
	var TableName = CollectivityGrid.getRowColData(checkrow-1,4);
	var RiskCode = CollectivityGrid.getRowColData(checkrow-1,1);
 	var RiskName = CollectivityGrid.getRowColData(checkrow-1,2);
	var RateType = CollectivityGrid.getRowColData(checkrow-1,3);
	var AuditConclusion = fm.all("AuditConclusion").value;
	fm.action="./LDRiskToRateAuditSave.jsp?AuditConclusion="+AuditConclusion+"&RiskCode="+RiskCode+"&RiskName="+RiskName+"&RateType="+RateType+"&TableName="+TableName+"&AuditFlag=1";
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fm.submit();
}

function PreventClick(){
	var checkrow = CollectivityGrid.getSelNo();
	if(checkrow==""||checkrow==null||checkrow=="null"){
		myAlert(""+"请选择一条数据"+"");
		return false;
	}
	if(fm.all("AuditConclusion").value == "")
  {
  	myAlert(""+"请录入变更原因"+"");
  	return;
	}
	if(false==textCounter()){
		return false;
	}
	var TableName = CollectivityGrid.getRowColData(checkrow-1,4);
	var RiskCode = CollectivityGrid.getRowColData(checkrow-1,1);
 	var RiskName = CollectivityGrid.getRowColData(checkrow-1,2);
	var RateType = CollectivityGrid.getRowColData(checkrow-1,3);
	var AuditConclusion = fm.all("AuditConclusion").value;
	fm.action="./LDRiskToRateAuditSave.jsp?AuditConclusion="+AuditConclusion+"&RiskCode="+RiskCode+"&RiskName="+RiskName+"&RateType="+RateType+"&TableName="+TableName+"&AuditFlag=2";
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fm.submit();
}

 function afterSubmit(FlagStr,content){
    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +content ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
// 	initForm();
}

 function init(){

}

function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert("toulian.js-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
	}
}