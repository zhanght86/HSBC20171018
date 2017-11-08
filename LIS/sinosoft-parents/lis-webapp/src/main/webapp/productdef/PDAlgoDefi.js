//程序名称：PDAlgoDefi.js
//程序功能：算法定义界面
//创建日期：2009-3-17
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDAlgoDefiInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
	if(document.getElementById("IsReadOnly").value == "1")
  {
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
    
	fm.all("AlgoCode").value = "";  
	fm.all("AlgoType").value = "";
	fm.all("AlgoContent").value = "";
	fm.all("AlgoDesc").value = "";
	fm.all("AlgoTypeName").value = "";
	
	queryMulline9Grid();
	queryMulline11Grid();
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
function save()
{
	if(fm.all("AlgoCode").value == "")
  {
  	myAlert(""+"算法编码不能为空"+"");
  	return;
  }
  if(fm.all("RiskCode").value == "")
  {
  	myAlert(""+"险种编码不能为空"+"");
  	return;
  }
  if(fm.all("AlgoType").value == "")
  {
  	myAlert(""+"算法类型不能为空"+"");
  	return;
  }
  if(fm.all("AlgoDesc").value == "")
  {
  	myAlert(""+"算法描述不能为空"+"");
  	return;
  }
  if(fm.AlgoType.value=='F'){
	  if(fm.all("MainAlgoCode").value == "")
	  {
	  	myAlert(""+"父算法编码不能为空"+"");
	  	return;
	  }
	  if(fm.all("SubAlgoGrade").value == "")
	  {
	  	myAlert(""+"子算法优先级不能为空"+"");
	  	return;
	  }
	}
	if(fm.all("AlgoContent").value==""){
		myAlert(""+"算法内容不能为空"+"");
	  return;
	}
 fm.all("operator").value="save";
 submitForm();
}
function update()
{

 fm.all("operator").value="update";
 if(fm.all("AlgoCode").value == "")
  {
  	myAlert(""+"算法编码不能为空"+"");
  	return;
  }
  if(fm.all("RiskCode").value == "")
  {
  	myAlert(""+"险种编码不能为空"+"");
  	return;
  }
  if(fm.all("AlgoType").value == "")
  {
  	myAlert(""+"算法类型不能为空"+"");
  	return;
  }
  if(fm.all("AlgoDesc").value == "")
  {
  	myAlert(""+"算法描述不能为空"+"");
  	return;
  }
	if(fm.AlgoType.value=='F'){
		if(fm.all("MainAlgoCode").value == ""){
		  	myAlert(""+"父算法编码不能为空"+"");
		  	return;
		}
		if(fm.all("SubAlgoGrade").value == ""){
		  	myAlert(""+"子算法优先级不能为空"+"");
		  	return;
		}
		//-------- add by jucy
	  	var strSQL = "";
		var mySql=new SqlClass();
		var sqlid = "PDAlgoDefiInputSql5";
		mySql.setResourceName(tResourceName); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara(fm.all("AlgoCode").value);//指定传入的参数
		arr = easyExecSql( mySql.getString() );
		if(arr!="F"){
			if(!confirm(""+"不允许修改算法编码为："+""+fm.all("AlgoCode").value+""+"，算法类型为："+""+arr[0][0]+""+"，此操作只会新增子/父算法的对应关系，是否继续此操作？"+"")){
		     	return false;
			};
			fm.all("operator").value="updateFac";
		}else{
			if(!confirm(""+"请选择：1、点击【确定】只会新增增子/父算法的对应关系。"+" "+"2、点击【取消】会修改除子/父算法编码外的其他信息。"+"")){
		     	fm.all("operator").value="update";
			}else{
				fm.all("operator").value="updateFac";
			}
		}
	  	//-------- end
	}
  if(fm.all("AlgoContent").value==""){
		myAlert(""+"算法内容不能为空"+"");
	  return;
	}
 submitForm();
}
function del()
{
 if(fm.all("AlgoCode").value == "")
  {
  	myAlert(""+"算法编码不能为空"+"");
  	return;
  }
  
 fm.all("operator").value="del";
 submitForm();
}
function queryAlgoTemp()
{
  showInfo = window.open("PDAlgoTempLibQueryMain.jsp");
}
function button326()
{
  showInfo = window.open("PDRateCVQueryMain.jsp?RiskCode=" + fm.all("RiskCode").value );
}
function query()
{
  var urlPara = "";
  if(fm.all("AlgoType").value != null)
  {
  	urlPara = "?algotype=" + fm.all("AlgoType").value;
  }
  
  showInfo = window.open("PDCalFactorQueryMain.jsp" + urlPara);
}
function button330()
{
  if(Mulline10Grid.getSelNo() == 0)
  {
  	myAlert(""+"请选中某行，再点击该按钮"+"");
  	return;
  }
  
  Mulline10Grid.delRadioTrueLine("Mulline10Grid");
}
function add()
{
  if(fm.all("SubAlgoCode").value == "")
  {
  	myAlert(""+"子算法编码不能为空"+"");
  	return;
  }
   if(fm.all("AlgoCode").value == "")
  {
  	myAlert(""+"算法编码不能为空"+"");
  	return;
  }
  
  fm.all("operator").value = "add";
  submitForm();
}
function subAlgoDefi()
{
if(fm.all("AlgoCode").value == "")
  {
  	myAlert(""+"算法编码不能为空"+"");
  	return;
  }
  
  var selNo = Mulline11Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"请先选中某行"+"");
  	return;
  }
  showInfo = window.open("PDSubAlgoDefiMain.jsp?riskcode="+fm.all("RiskCode").value+"&algocode=" + fm.all("AlgoCode").value + "&subalgocode=" + Mulline11Grid.getRowColData(selNo-1,3) + "&subalgoname=" + Mulline11Grid.getRowColData(selNo-1,2) + "&subalgograde=" + Mulline11Grid.getRowColData(selNo-1,7));
}
function test()
{
	var selNo = Mulline9Grid.getSelNo();
  if(selNo == 0)
  {
  	myAlert(""+"请先选中一个已保存算法"+"");
  	return;
  }
  fm.all("AlgoCode").value=Mulline9Grid.getRowColData(selNo-1,1);
  fm.all("operator").value = "test";
  submitForm();
}
function retResult()
{
  var tSel = Mulline9Grid.getSelNo();
	
	//if( tSel == 0 || tSel == null )
		//alert( "请先选择一条算法记录，再点击返回按钮。" );
	//else
	//{
	  var AlgoCode = Mulline9Grid.getRowColData(Mulline9Grid.getSelNo()-1, 1);
	  try
	  {
	  	//top.opener.afterQueryAlgo(AlgoCode);
	  	top.close();
	  	top.opener.focus();
	  }
	  catch(ex)
	  {
	  	myAlert(""+"关闭录入页面则退出系统，请重新登录"+"");
	  	window.open("PDAlgoDefiMain.jsp");
	  	top.close();
	  }
	//}
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDAlgoDefiInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function queryMulline11Grid()
{
	if(fm.all("AlgoCode").value!=null&&fm.all("AlgoCode").value!='')
	{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDAlgoDefiInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("AlgoCode").value);//指定传入的参数
	 //-------- add by jucy
	 var selNo = Mulline9Grid.getSelNo();
	 if(selNo > 0){
	 	mySql.addSubPara(Mulline9Grid.getRowColData(selNo-1,7));
	 }else{
	 	mySql.addSubPara();
	 }
	 //-------- end
   strSQL = mySql.getString();
   Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
  }
}

function afterQueryAlgo(AlgoCode)
{
	var mySql=new SqlClass();
	var sqlid = "PDAlgoDefiInputSql3";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(AlgoCode);//指定传入的参数
	var sql = mySql.getString();
	var arr = easyExecSql(sql);
	
	if(arr != null)
	{
		//fm.all("AlgoCode").value = arr[0][0];
		fm.all("AlgoDesc").value = arr[0][1];
		fm.all("AlgoContent").value = arr[0][2];
		fm.all("AlgoType").value = arr[0][3];	
		fm.all("AlgoTypeName").value = arr[0][4];			
	}
}

function afterQueryFactor(dicSelectedFactors)
{
	var mulLineCurrentCount = Mulline10Grid.mulLineCount;

	a = (new VBArray(dicSelectedFactors.Items())).toArray();   //获取条目。
   	s = "";
   	for (ii in a)                  //遍历该 dictionary。
   	{
   		Mulline10Grid.addOne();
   		Mulline10Grid.setRowColData(parseInt(ii) + mulLineCurrentCount,1,a[ii][0]);
   		Mulline10Grid.setRowColData(parseInt(ii) + mulLineCurrentCount,3,a[ii][3]);
 		Mulline10Grid.setRowColData(parseInt(ii) + mulLineCurrentCount,5,a[ii][4]);
   		Mulline10Grid.setRowColData(parseInt(ii) + mulLineCurrentCount,6,a[ii][5]);
   	}
}

function afterCodeSelect(cCodeName, Field) {
	if(cCodeName == "algotemptype") {
	  if(Field.value=="F"){
	  	fm.all("subAlgoDiv").style.display = '';
	  }else{
	  	fm.all("subAlgoDiv").style.display = 'none';
	  }
	}
	
}
function getRiskName(field){
	var mySql=new SqlClass();
	var sqlid = "PDAlgoDefiInputSql4";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(field.value);//指定传入的参数
	var sql = mySql.getString();
	var arr = easyExecSql(sql);
	if(arr != null){
		fm.MainAlgoName.value=arr[0][0];
	}
}
function isshowbutton()

{
	//	alert("进入方法");
	   var value=getQueryState1();
	if(value=='0'||value==0){
	//document.getElementById('savabuttonid').style.display = 'none';
	document.getElementById('baocun').disabled=true;
	document.getElementById('shanchu').disabled=true;
	document.getElementById('xiugai').disabled=true;
	}

}