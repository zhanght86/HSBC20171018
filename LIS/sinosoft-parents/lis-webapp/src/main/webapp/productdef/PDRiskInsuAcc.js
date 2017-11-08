//程序名称：PDRiskInsuAcc.js
//程序功能：险种账户定义
//创建日期：2009-3-12
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;
//定义sql配置文件
var tResourceName = "productdef.PDRiskInsuAccInputSql";
function checkNullable()
{
  var GridObject = Mulline10Grid;
  var rowCount = GridObject.mulLineCount;
  
  for(var i = 0; i < rowCount; i++)
  {
    var PropName = GridObject.getRowColData(i,1);
    var PropValue = GridObject.getRowColData(i,4);
    
  	if(PropName.indexOf("[*]") > -1)
  	{
  		if(PropValue == null || PropValue == "")
  		{
  			PropName = PropName.substring(3,PropName.length);
  		
  			myAlert("\"" + PropName + ""+"\"不能为空"+"");
  			
  			return false;
  		}
  	}
  }
  
  return true;
}

function submitForm(){
	if(fm.all("IsReadOnly").value == "1"){
  		myAlert(""+"您无权执行此操作"+"");
  		return;
	}
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	fm.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
  } 
}

function setGuarInteRate()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一个险种账户！"+"");
		return;
	}
	//alert(Mulline9Grid.getRowColData(selNo-1,2));
	//alert("here");
	window.open("PDAccGuratRateMain.jsp?riskcode=" + fm.all("RiskCode").value+"&insuaccno=" + Mulline9Grid.getRowColData(selNo-1,2));
}

var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
function save(){
	if(!checkNullable()){
		return false;
	}
	//-------- add by jucy
	var checkRiskToAccNo = Mulline10Grid.getRowColData(1, 4);
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDRiskInsuAccInputSql3";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(checkRiskToAccNo);//指定传入的参数
	arr = easyExecSql( mySql.getString() );
	if(arr!=null&&arr!=""){
		if(!confirm(""+"保险账户号码："+""+arr[0][0]+""+"，已存在，不允许新增修改保险账户信息，是否只增加产品与保险账户的对应关系？"+"")){
	     	return;
		}
	}
	
	var accType=Mulline10Grid.getRowColData(3, 4);
	if("007"==accType){
		var mySql2=new SqlClass();
		var sql = "PDRiskInsuAccInputSql4";
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sql);//指定使用的Sql的id
		mySql2.addSubPara(checkRiskToAccNo);//指定传入的参数
		mySql2.addSubPara(accType);
		arr = easyExecSql( mySql2.getString() );
		if(arr==null||arr==""){
			myAlert(""+"不能新增没有的基金账户！"+"");
			return false;
		}
	}
	//-------- end
	fm.all("operator").value="save";
	
	submitForm();
}
function update(){
	
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert(""+"请选择一条记录！"+"");
		return;
	}
	//-------- add by jucy
	if(!checkNullable()){
		return false;
	}
	var checkRiskToAccNo = Mulline10Grid.getRowColData(1, 4);
	var checkAccNo = Mulline9Grid.getRowColData(selNo-1, 2);
	if(checkRiskToAccNo != checkAccNo){
		myAlert(""+"保险账户号码为："+""+checkAccNo+""+"，系统只允许修改除保险账户号码以外的其他账户基本信息。"+"");
		Mulline10Grid.setRowColData(1, 4,checkAccNo);
		return false;
	}
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDRiskInsuAccInputSql3";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(checkRiskToAccNo);//指定传入的参数
	arr = easyExecSql( mySql.getString() );
	if(arr!=null&&arr!=""){
		var checkAccType = arr[0][1];
		if(checkAccType=='007'){
			if(!confirm(""+"保险账户号码："+""+arr[0][0]+""+"，账户类型为：007"+"-"+"基金账户，如修改基金账户基本信息，可能会影响该基金在系统中的处理，是否继续修改？"+"")){
		     	return false;
			};
		}
	}
	//-------- end
	
	fm.all("operator").value="update";
	submitForm();
}
function del(){
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert(""+"请选择一条记录！"+"");
		return;
	}
	//-------- add by jucy
	if(!checkNullable()){
		return false;
	}
	var checkRiskToAccNo = Mulline10Grid.getRowColData(1, 4);
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDRiskInsuAccInputSql3";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(checkRiskToAccNo);//指定传入的参数
	arr = easyExecSql( mySql.getString() );
	if(arr!=null&&arr!=""){
		var checkAccType = arr[0][1];
		if(checkAccType=='007'){
			if(!confirm(""+"保险账户号码："+""+arr[0][0]+""+"，账户类型为：007"+"-"+"基金账户，此操作会删除此基金账户的全部信息，是否继续此操作？"+"")){
		     	return false;
			};
		}
	}
	//-------- end
	
	fm.all("operator").value="del";
	submitForm();
}
//-------- add by jucy
function delrel(){
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert(""+"请选择一条记录！"+"");
		return;
	}
	if(!checkNullable()){
		return false;
	}
	var checkAccNo = Mulline9Grid.getRowColData(selNo-1, 2);
	if(!confirm(""+"当前操作会删除险种代码："+""+fm.all("RiskCode").value+""+"，与保险账户号码："+""+checkAccNo+""+"的关联关系，是否继续此操作？"+"")){
     	return false;
	};
	fm.all("operator").value="delrel";
	submitForm();
	
}
//-------- end



function returnParent()
{
  top.opener.focus();
	top.close();
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskInsuAccInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskInsuAccInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara("0");//指定传入的参数
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	//document.getElementById('savabuttonid').style.display = 'none';
	document.getElementById('savabutton1').disabled=true;
	document.getElementById('savabutton2').disabled=true;
	document.getElementById('savabutton3').disabled=true;
	document.getElementById('savabutton4').disabled=true;
	//document.getElementById('savabutton4').disabled=true;
	}

}