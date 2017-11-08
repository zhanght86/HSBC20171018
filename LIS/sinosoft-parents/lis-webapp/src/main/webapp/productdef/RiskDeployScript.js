//程序名称：RiskCalDef.js
//程序功能：规则引擎算法控制
//创建日期：2011-10-19
//该文件中包含客户端需要处理的函数和事件
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
//var Mulline9GridTurnPage = new turnPageClass(); 
var tResourceName = "productdef.RiskDeployScripInputSql";
var turnPage = new turnPageClass();
function submitForm()
{
	if(!verifyForm('fm'))
	{
		return false;
	}
	// var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
//if(fm.all("operator").value == "del")
//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fm.submit();
}

function afterSubmit( FlagStr, content )
{

  if(FlagStr == "del"){
	  myAlert(content);
	  initForm();
  }else{
	  if (FlagStr == "Fail" )
	  {               
	  myAlert(content);
	  }  
  }
}
function query()
{
	 ///var riskcode = document.getElementById('riskcode').value;
	 var mySql=new SqlClass();
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 var sqlid = "RiskDeployInputSql1";
	 var DeployType=fm.all("DeployType").value;
	 var DeployName=fm.all("DeployName").value;
	 var DeployDateR=fm.all("DeployDateR").value;
	 var DeployDateL=fm.all("DeployDateL").value;
	 var DeployEnv=fm.all("DeployEnv").value;
	 var RiskCode=fm.all("RiskCode").value;
	 if(RiskCode.substring(0,1)=="#"){
		 RiskCode=RiskCode.substring(1,RiskCode.length);
		 mySql.addSubPara("");
		 mySql.addSubPara(DeployType);
		 mySql.addSubPara(DeployEnv);
		 mySql.addSubPara(DeployName);
		 mySql.addSubPara(DeployDateL);
		 mySql.addSubPara(DeployDateR);
		 mySql.addSubPara(RiskCode);
		 turnPage.pageLineNum  = 1024;
	 }else{
		 mySql.addSubPara(RiskCode);
		 mySql.addSubPara(DeployType);
		 mySql.addSubPara(DeployEnv);
		 mySql.addSubPara(DeployName);
		 mySql.addSubPara(DeployDateL);
		 mySql.addSubPara(DeployDateR);
		 mySql.addSubPara("");
		 turnPage.pageLineNum  = 10;
	 }
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
   var strSQL = mySql.getString();

   turnPage.queryModal(strSQL,MullineGrid);

}
function save()
  {fm.all("operator").value = "download";
  submitForm(); 
  }
function afterCodeSelect( cCodeName, Field )
{

}
function del()
{
	fm.all("operator").value = "del";
	  submitForm(); 	
}
