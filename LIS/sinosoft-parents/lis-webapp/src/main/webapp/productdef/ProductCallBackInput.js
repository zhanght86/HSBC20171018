//该文件中包含客户端需要处理的函数和事件
var showInfo;
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 

//提交，保存按钮对应操作
function submitForm()
{
	if( fm.RiskCode.value == null || fm.RiskCode.value == "" )
	{
		myAlert(""+"请选择要查询的险种！"+"");
		return;
	}
    fm.action = "./PDLBRiskInfoSave.jsp";
  	fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
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
  } 
}

function queryMulline10(){
	var riskCode = document.getElementById("RiskCode").value;
	
	if( fm.RiskCode.value == null || fm.RiskCode.value == "" )
	{
		myAlert(""+"请选择要查询的险种！"+"");
		return;
	}
	
	var sql = "select g.riskcode, g.deployversion, g.reason,g.deploydate, g.standbyflag8, g.opeartor, g.deploybatch from PD_DeployTrack g where g.riskcode = '" + riskCode + "'" + getWherePart('deploydate','StartDate', ">=") + getWherePart('deploydate','EndDate', "<=") + " order by to_number(g.deployversion)"  ;

	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}

//查看选中版本与当前版本的区别
function modifyInfoQuery()
{	
	var selNo = Mulline10Grid.getSelNo();
	
	if(selNo == 0){
		myAlert(''+"请先选择一个版本！"+'');
	}
	
	//获得批次号
	var batchNo = Mulline10Grid.getRowColData(selNo - 1, 7);
	
	var riskCode = Mulline10Grid.getRowColData(selNo - 1, 1);
	document.getElementById('modifyInfoID').style.display = '';
	
	//显示
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
 	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
 	
    fm.action = "./PDLBRiskInfoSave.jsp?riskcode=" + riskCode + "&batch=" + batchNo;
  	fm.submit(); //提交
}

//版本回退
function productBack(){
	//获得选中的行
	var selNo = Mulline10Grid.getSelNo();
	
	if(selNo == 0){
		myAlert(''+"请先选择一个版本！"+'');
	}
	
	//获得批次号
	var batchNo = Mulline10Grid.getRowColData(selNo - 1, 7);
	var riskCode = Mulline10Grid.getRowColData(selNo - 1, 1);
	
	//显示
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
 	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
 	
	fm.action = "./ProductCallBackSave.jsp?riskcode=" + riskCode + "&batch=" + batchNo;
	fm.submit();
}

//产品信息查询
function queryRiskInfo(){
	fm.action = "PDIntegratedQueryInput.jsp";
	fm.target = "";
	fm.submit();
}
