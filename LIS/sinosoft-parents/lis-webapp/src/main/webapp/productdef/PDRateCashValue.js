//�������ƣ�PDRateCashValue.js
//�����ܣ����ݱ���ֽ��ֵ����
//�������ڣ�2009-3-17
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
var rateTableNamePrefix = "RATE_";
var cvTableNamePrefix = "CV_";
//����sql�����ļ� 
var tResourceName = "productdef.PDRateCashValueInputSql";

function submitForm()
{
  if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function submitForm2()
{
if(fm2.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm2.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else if (FlagStr == "close")
  {
      initForm();  
  } 
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();  
  }
  
  fm.action = "./PDRateCashValueSave.jsp";
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 

var Mulline12GridTurnPage = new turnPageClass(); 
var Mulline13GridTurnPage = new turnPageClass(); 
var Mulline14GridTurnPage = new turnPageClass(); 
var Mulline15GridTurnPage = new turnPageClass();
function addRate()
{ 
	var flag=0;
	for(var i=0;i<Mulline9Grid.mulLineCount;i++){
		if(Mulline9Grid.getChkNo(i)){
			flag=1;
			break;
		}
	}
  if(flag == 0)
  {
  	myAlert(""+"��ѡ��һ�к��ٵ�����"+"");
  	return;
  }
	//Mulline10Grid. clearData("Mulline10Grid");
  /*for(var i=0;i<Mulline9Grid.mulLineCount;i++){
		if(Mulline9Grid.getChkNo(i)){
			Mulline10Grid.addOne("Mulline10Grid");
			var count = Mulline10Grid.mulLineCount;
			Mulline10Grid.setRowColData(count-1,1,count.toString());
		  Mulline10Grid.setRowColData(count-1,2,Mulline9Grid.getRowColData(i,1));
		  Mulline10Grid.setRowColData(count-1,3,Mulline9Grid.getRowColData(i,2));
		}
	}*/
   var flag2 =0;
  for(var i=0;i<Mulline9Grid.mulLineCount;i++){
		if(Mulline9Grid.getChkNo(i))
		{
			flag2 =0;
			for(var j=0;j<Mulline10Grid.mulLineCount;j++)
			{
			 if(Mulline9Grid.getRowColData(i,1)==Mulline10Grid.getRowColData(j,2))
			  {
			  flag2 =1;
			  break;
			  }
			}
			if(flag2 ==0)
			{
			  Mulline10Grid.addOne("Mulline10Grid");
			  var count = Mulline10Grid.mulLineCount;
		      Mulline10Grid.setRowColData(count-1,1,count.toString());
		      Mulline10Grid.setRowColData(count-1,2,Mulline9Grid.getRowColData(i,1));
		      Mulline10Grid.setRowColData(count-1,3,Mulline9Grid.getRowColData(i,2));
		    }
		 }
	}
  
}
function createRateTable()
{
  if(Mulline10Grid.mulLineCount==0){
  	myAlert(""+"��ѡ�����ݱ�����Ҫ����ӣ�"+""); 
  	return;
  }
  
  if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  
  var rateTableName = rateTableNamePrefix + fm.all("RiskCode").value + "_" + fm.all("PayCode").value 
  						+ "_" + getMaxNo(Mulline11Grid, 3);
  fm.all("operator").value = "createRateTable";
  fm.all("newTableName").value = rateTableName;
  
  if(fm.all("PremDataTypeName").value == "")
  {
  	myAlert(""+"�����������Ͳ���Ϊ��"+"");
  	return;
  }

  fm.action = "./PDRateCashValueSave.jsp";
  submitForm();
}

function button310()
{
  showInfo = window.open("");
}
function deleteRateTable()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
 var selNo = Mulline11Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"��ѡ��һ�к��ٵ��"+"");
  	return;
  }
  
  fm.all("operator").value = "deleteRateTable";
  fm.all("newTableName").value = Mulline11Grid.getRowColData(selNo-1,3);
  
  fm.action = "./PDRateCashValueSave.jsp";
  submitForm();
}
function downloadRateTable()
{
if(fm.IsReadOnly.value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  var selNo = Mulline11Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"��ѡ��һ�к��ٵ��"+"");
  	return;
  }
  
  fm.action = "PDDealExcelSave.jsp";
  fm.newTableName.value = Mulline11Grid.getRowColData(selNo-1,3);
  fm.operator.value = "downloadRateTable";
  
  fm.submit();
}

function addCV()
{
  var selNo = Mulline12Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"��ѡ��һ�к��ٵ�����"+"");
  	return;
  }
  
  Mulline13Grid.addOne("Mulline13Grid");
  
  var count = Mulline13Grid.mulLineCount;
  
  Mulline13Grid.setRowColData(count-1,1,count.toString());
  Mulline13Grid.setRowColData(count-1,2,Mulline12Grid.getRowColData(selNo-1,1));
  Mulline13Grid.setRowColData(count-1,3,Mulline12Grid.getRowColData(selNo-1,2));
}
function createCashValue()
{
if(fm2.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }

  var cvTableName = cvTableNamePrefix+ fm.all("RiskCode").value  + "_" + getMaxNo(Mulline14Grid, 2) ;
  fm2.all("operator").value = "createCashValue";
  fm2.all("newTableName").value = cvTableName;
  fm2.all("RiskCode").value = fm.all("RiskCode").value;
  
  if(fm2.all("CashValueDataTypeName").value == "")
  {
  	myAlert(""+"�ֽ��ֵ�������Ͳ���Ϊ��"+"");
  	return;
  }
    
  fm2.action = "./PDRateCashValueSave.jsp";
  submitForm2();
}

function getMaxNo(obj, index)
{
	if(obj.mulLineCount > 0)
	{
		var tableName = obj.getRowColData(obj.mulLineCount - 1, index);
		var flag = tableName.lastIndexOf("_");
		
		var curNo = tableName.substr(flag+1, tableName.length);
		
		return parseInt(curNo) +1;
	}
	else
	{
		return 1;
	}
}

function deleteCVTable()
{
if(fm2.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  
  var selNo = Mulline14Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"��ѡ��һ�к��ٵ��"+"");
  	return;
  }
  
  fm2.all("operator").value = "deleteCVTable";
  fm2.all("RiskCode").value = Mulline14Grid.getRowColData(selNo-1,1);
  fm2.all("newTableName").value = Mulline14Grid.getRowColData(selNo-1,2);
  
  fm2.action = "./PDRateCashValueSave.jsp";
  submitForm2();
}
function downloadCVTable()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  var selNo = Mulline14Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"��ѡ��һ�к��ٵ��"+"");
  	return;
  }
  
  fm.action = "PDDealExcelSave.jsp";
  fm.all("newTableName").value = Mulline14Grid.getRowColData(selNo-1,2);
  fm.operator.value = "downloadCVTable";
  
  fm.submit();
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRateCashValueInputSql1";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("sqlid");//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function queryMulline11Grid()
{
   var strSQL = "";
   var rateTableName = rateTableNamePrefix + fm.all("RiskCode").value + "_" + fm.all("PayCode").value;
   var mySql=new SqlClass();
	 var sqlid = "PDRateCashValueInputSql2";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all("RiskCode").value);//ָ������Ĳ���
	 mySql.addSubPara(fm.all("PayCode").value);//ָ������Ĳ���
   var strSQL = mySql.getString();
   var result = easyExecSql(strSQL);
   if(result != null)
   {
   Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
   }
}
function queryMulline12Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRateCashValueInputSql3";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("sqlid");//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline12GridTurnPage.pageLineNum  = 3215;
   Mulline12GridTurnPage.queryModal(strSQL,Mulline12Grid);
}

function queryMulline14Grid()
{
   var strSQL = "";
   var cvTableName = cvTableNamePrefix + fm.all("RiskCode").value;
   var mySql=new SqlClass();
	 var sqlid = "PDRateCashValueInputSql4";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all("RiskCode").value);//ָ������Ĳ���
   var strSQL = mySql.getString();
   var result = easyExecSql(strSQL);
   if(result != null)
   {
   	Mulline14GridTurnPage.pageLineNum  = 3215;
   	Mulline14GridTurnPage.queryModal(strSQL,Mulline14Grid);
   }
}

function queryMulline15Grid(){
	var selNo = Mulline11Grid.getSelNo();
		
	var mySql=new SqlClass();
	var sqlid = "PDRateCashValueInputSql5";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(Mulline11Grid.getRowColData(selNo-1,3));//ָ������Ĳ���
	var sql15 = mySql.getString();

	Mulline15GridTurnPage.pageLineNum = 10;
	Mulline15GridTurnPage.queryModal(sql15,Mulline15Grid);
}
function ImportExcel()
{
if(uploadForm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  var selNo = Mulline11Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"��ѡ�����ݱ��ٵ������"+"");
  	return;
  }
  
  if(uploadForm.all("FileName").value == "")
  {
  	myAlert(""+"����ѡ����Ҫ�ϴ����ļ�"+"");
  	return;
  }
    
  var tableName = Mulline11Grid.getRowColData(selNo-1,3);
  
  if(!check(uploadForm.all("FileName").value, tableName))
  {
  	myAlert(""+"ֻ���ϴ�����Ϊ"+"'"+tableName+"'"+"��Excel�ļ�"+"");
  	return;
  }
  uploadForm.submit();
  
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
}

function ImportCVExcel()
{
if(uploadCV.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  var selNo = Mulline14Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"��ѡ���ֽ��ֵ���ٵ������"+"");
  	return;
  }
    
  if(uploadCV.all("FileName").value == "")
  {
  	myAlert(""+"����ѡ����Ҫ�ϴ����ļ�"+"");
  	return;
  }
  
  var tableName = Mulline14Grid.getRowColData(selNo-1,2);
  if(!check(uploadCV.all("FileName").value, tableName))
  {
  	myAlert(""+"ֻ���ϴ�����Ϊ"+"'"+tableName+"'"+"��Excel�ļ�"+"");
  	return;
  }

  uploadCV.submit();
  
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
}

function check(fileName, tableName)
{
	var len = fileName.length;
	var tableNameLen = tableName.length;
	
	var reg = new RegExp(tableName, "i");
	
	var index = fileName.search(reg);
	
	if(index == -1)
		return false;
	
	return true;
}



function afterUpload(FlagStr, Content)
{
	if(FlagStr == "Fail")
	{
		myAlert(Content);
		return false;
	}

	dealUpload.all("targetFileName").value = Content;
	dealUpload.all("IsUpload").value = "1";

	if(Content.indexOf(rateTableNamePrefix) > -1)
	{
		var selNo = Mulline11Grid.getSelNo();
		dealUpload.all("newTableName").value = Mulline11Grid.getRowColData(selNo-1,3);
	}
	else
	{
		var selNo = Mulline14Grid.getSelNo();
		dealUpload.all("newTableName").value = Mulline14Grid.getRowColData(selNo-1,2);
	}
	
	dealUpload.submit();
}

function afterdealUpload(FlagStr, Content)
{
	showInfo.close();

	if(dealUpload.all("IsUpload").value == "1")
	{
		dealUpload.all("IsUpload").value = "0";	
	}
	
	if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  
  queryMulline15Grid();
}