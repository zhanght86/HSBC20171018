// ���ļ��а����ͻ�����Ҫ����ĺ������¼�
// window.onfocus=myonfocus;
var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass();
var CollectivityGridTurnPage = new turnPageClass(); 
var myCheckInsuAccNo = "";
var myCheckDate = "";
var sqlresourcename = "productdef.LDRiskToRateSql";

function easyQueryClick(){
	// ��ʼ�����
	initCollectivityGrid();	
  	var mySql1=new SqlClass();
	var sqlid1 = "LDRiskToRateSql1";
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.RateType.value);//ָ������Ĳ���
	var sql = mySql1.getString();
	CollectivityGridTurnPage.pageLineNum = 10; 
	CollectivityGridTurnPage.queryModal(sql,CollectivityGrid);
	if(CollectivityGrid.mulLineCount==0){
		myAlert(""+"�ò�Ʒû��������͵ķ��ʣ�"+"");
	}
}

function afterSubmit(FlagStr,content){
 	showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +content ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
// 	initForm();
}
 
function download()
{
	var checkrow = CollectivityGrid.getSelNo();
	if(checkrow==""||checkrow==null||checkrow=="null"){
		myAlert(""+"��ѡ��һ������"+"");
		return false;
	}
	var tableName = CollectivityGrid.getRowColData(checkrow-1,4);
	fm.action = "./MakeRateDataDisk.jsp?tableName="+tableName;
	fm.submit();
}

function textCounter(){
	var field=fm.Remark.value;	
	//field=field.replace("\r\n" ,"");
  var arr = field.split("\n");
  var perLine="";
  var characterCount=0;
  for(var i=0;i<arr.length;i++){
  	perLine=arr[i];
  	characterCount+=perLine.length;
  }
  if(characterCount>60){
  	myAlert(""+"����������ݹ����������������60�����֣����������źͻ��У�"+"");
  	return false;
  }
}

function fullInputClick()
{
  if(fmm.all("FileName").value == "")
  {
  	myAlert(""+"����ѡ����Ҫ�ϴ����ļ�"+"");
  	return;
	}
	if(fm.all("Remark").value == "")
  {
  	myAlert(""+"��¼����ԭ��"+"");
  	return;
	}
	var checkrow = CollectivityGrid.getSelNo();
	if(checkrow==""||checkrow==null||checkrow=="null"){
		myAlert(""+"��ѡ��һ������"+"");
		return false;
	}
	if(false==textCounter()){
		return false;
	}
	var TableName = CollectivityGrid.getRowColData(checkrow-1,4);
	var RiskCode = CollectivityGrid.getRowColData(checkrow-1,1);
 	var RiskName = CollectivityGrid.getRowColData(checkrow-1,2);
	var RateType = CollectivityGrid.getRowColData(checkrow-1,3);
	var Remark = fm.all("Remark").value;
  fmm.action="./LDRiskToRateTableSave.jsp?Remark="+Remark+"&RiskCode="+RiskCode+"&RiskName="+RiskName+"&RateType="+RateType+"&TableName="+TableName+"&ImportType=All";
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fmm.submit();
}

function incrementalClick()
{
  if(fmm.all("FileName").value == "")
  {
  	myAlert(""+"����ѡ����Ҫ�ϴ����ļ�"+"");
  	return;
	}
	if(fm.all("Remark").value == "")
  {
  	myAlert(""+"��¼����ԭ��"+"");
  	return;
	}
 var checkrow = CollectivityGrid.getSelNo();
	if(checkrow==""||checkrow==null||checkrow=="null"){
		myAlert(""+"��ѡ��һ������"+"");
		return false;
	}
	if(false==textCounter()){
		return false;
	}
	var TableName = CollectivityGrid.getRowColData(checkrow-1,4);
  var RiskCode = CollectivityGrid.getRowColData(checkrow-1,1);
 	var RiskName = CollectivityGrid.getRowColData(checkrow-1,2);
	var RateType = CollectivityGrid.getRowColData(checkrow-1,3);
	var Remark = fm.all("Remark").value;
  fmm.action="./LDRiskToRateTableSave.jsp?Remark="+Remark+"&RiskCode="+RiskCode+"&RiskName="+RiskName+"&RateType="+RateType+"&TableName="+TableName+"&ImportType=Add";
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fmm.submit();
}

function ShowRemark(){
	var sqlid2 = "LPRiskToRateSql3";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	var checkrow = CollectivityGrid.getSelNo();
	if(checkrow==""||checkrow==null||checkrow=="null"){
		myAlert(""+"��ѡ��һ������"+"");
		return false;
	}
	var RiskCode = CollectivityGrid.getRowColData(checkrow-1,1);
	var RateType = CollectivityGrid.getRowColData(checkrow-1,3);
	mySql1.addSubPara(RiskCode);//ָ������Ĳ���
	mySql1.addSubPara(RateType);//ָ������Ĳ���
	sql = mySql1.getString();
	var remark = easyExecSql(sql);
	if(remark==null)
		remark='';
	fm.all("Remark").value = remark;
}

function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert("toulian.js-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
	}
}
