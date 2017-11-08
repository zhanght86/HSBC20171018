var showInfo; 
var turnPage = new turnPageClass(); 
window.onfocus=myonfocus; 
var ImportPath; 

function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

function queryFeeRateBatch(){
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRFeeRateBatchInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRFeeRateBatchInputSql100");//指定使用的Sql的id
		mySql100.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	var strSQL=mySql100.getString();
	
	//var strSQL = "select x.X1,x.X2,x.X3,x.X4,x.X5,x.X6,decode(x.X7,'1','已导入','未导入'),x.X8,decode(x.X9,'1','有效','未生效') "
	           + " from ( select a.FeeTableNo X1,a.FeeTableName X2,a.BatchNo X3,a.InureDate X4,a.LapseDate X5,a.SaveDataName X6,(select 1 from RIFeeRateTableTrace c where exists (select * from RIFeeRateTable d where c.feetableno=d.feetableno and d.batchno=c.batchno ) and c.feetableno=a.feetableno and c.batchno=a.batchno) X7,a.State X8,(select SubStr(a.state,1,1) from RIFeeRateTableTrace c where c.feetableno = a.feetableno and c.batchno = a.batchno) X9 from RIFeeRateTableTrace a where a.FeeTableNo='"+fm.FeeTableNo.value+"') x order by x.X1,x.X3"
	;                                                                                                                                                                                                                                                                                                                                             
	turnPage.queryModal(strSQL, FeeRateBatchGrid);                                                                                                                                                                                                                                                                                                
}

//提交，保存按钮对应操作
function submitForm()
{
	fm.OperateType.value="INSERT";
	if(fm.State.value=='01')
	{
      var strSQL = "";
      var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRFeeRateBatchInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRFeeRateBatchInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	    strSQL=mySql101.getString();
     //strSQL = "select state from RIFeeRateTableDef where feetableno='"+fm.FeeTableNo.value+"'";
      turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      var tState=turnPage.arrDataCacheSet[0][0];
      if(tState=='02'||tState==''||tState==null)
      {
      	myAlert(""+"费率表状态为无效，批次状态需置为无效！"+"");
      	return false;
      }
  }    
	try 
	{
		if( verifyInput() == true) 
		{
			if (veriryInput3()==true)
			{
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action="./LRFeeRateBatchSave.jsp";
		  	fm.submit(); //提交
	  	}
	  }
  }catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	if(fm.State.value=='01')
	{
      var strSQL = "";
      var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRFeeRateBatchInputSql"); //指定使用的properties文件名
		mySql102.setSqlId("LRFeeRateBatchInputSql102");//指定使用的Sql的id
		mySql102.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	    strSQL=mySql102.getString();
      //strSQL = "select state from RIFeeRateTableDef where feetableno='"+fm.FeeTableNo.value+"'";
      turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      var tState=turnPage.arrDataCacheSet[0][0];
      if(tState=='02'||tState==''||tState==null)
      {
      	myAlert(""+"费率表状态为无效，批次状态需置为无效！"+"");
      	return false;
      }
  }    
	try 
	{
		if( verifyInput() == true) 
		{
			if (veriryInput3()==true)
			{
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action="./LRFeeRateBatchSave.jsp";
		  	fm.submit(); //提交
	  	}
	  }
  }catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function deleteClick(){
	fm.OperateType.value="DELETE";
	if(!confirm(""+"你确定要删除该费率表批次吗？"+"")){
		return false;
	}
	try {
		if(verifyInput() == true){
		  	var i = 0;
		  	var showStr=""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRFeeRateBatchSave.jsp";
		  	fm.submit(); //提交
	  }
  } catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function veriryInput3(){
	return true;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content)
{
  showInfo.close();
  if (FlagStr == "Fail" ) {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    
  } else { 
	  //content="保存成功！";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  if (fm.OperateType.value=="DELETE")
	  {
	  	resetForm();
	  }else{
	  	queryFeeRateBatch();
	  }
  }
}

function ShowBatch(){
	var tSel = FeeRateBatchGrid.getSelNo();
	fm.BatchNo.value= FeeRateBatchGrid.getRowColData(tSel-1,3);
	fm.InureDate.value= FeeRateBatchGrid.getRowColData(tSel-1,4);
	fm.LapseDate.value= FeeRateBatchGrid.getRowColData(tSel-1,5);
	fm.SaveDataName.value= FeeRateBatchGrid.getRowColData(tSel-1,6);
	fm.State.value= FeeRateBatchGrid.getRowColData(tSel-1,8);
	if(fm.State.value=='01'){
		fm.stateName.value=""+"有效"+"";
	}else{
		fm.stateName.value=""+"未生效"+"";
	}
	fm.SaveDataNameName.value=""+"通用费率表"+"";
	
}

function inputFeeRateBatch(){
	fm.all('BatchNo').value 	= '';    
	fm.all('InureDate').value  = '';
	fm.all('LapseDate').value 	= '';    
	fm.all('SaveDataName').value  = '';
	fm.all('SaveDataNameName').value  = '';
	fm.all('State').value  = '';
	fm.all('stateName').value  = '';
	fmImport.reset();
}
function FeeRateTableImp(){
	if(fmImport.FileName.value==""||fmImport.FileName.value==null)
	{
		myAlert(""+"录入导入文件路径！"+"");
		return false;
	}
	var i = 0;
  getImportPath();
  
  ImportFile = fmImport.all('FileName').value;  
  
  var showStr=""+"正在上载数据……"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fmImport.action = "./LRFeeRateImportSave.jsp?ImportPath="+ImportPath+"&FeeTableNo="+fm.FeeTableNo.value+"&BatchNo="+fm.BatchNo.value;
  fmImport.submit(); //提交
}

function getImportPath(){
  var strSQL = "";
 var mySql103=new SqlClass();
		mySql103.setResourceName("reinsure.LRFeeRateBatchInputSql"); //指定使用的properties文件名
		mySql103.setSqlId("LRFeeRateBatchInputSql102");//指定使用的Sql的id
	    mySql103.addSubPara("1");
	    strSQL=mySql103.getString();
 // strSQL = "select sysvarvalue from ldsysvar where sysvar='RIXmlPath'";
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    myAlert(""+"未找到上传路径"+"");
    return;
  }
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  ImportPath = turnPage.arrDataCacheSet[0][0];
}


//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
  }
} 

function ClosePage(){
	top.close();
} 

//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           
function alink(){
	window.location.href="../temp/reinsure/feerateimp/LRFeeRateImport.xls";
}

