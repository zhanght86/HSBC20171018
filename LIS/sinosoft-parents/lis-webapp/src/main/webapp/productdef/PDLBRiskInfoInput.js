//该文件中包含客户端需要处理的函数和事件
var showInfo;
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 

//提交，保存按钮对应操作
function submitForm()
{
	if( fm.RiskCode.value == null || fm.RiskCode.value == "" )
	{
		myAlert(""+"请选择要查询的险种！"+"");
		return;
	}
    fm.action = "./PDLBRiskInfoSave.jsp?riskcode=" + riskcode + "&batch=" + batchNo;
  	fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
}
