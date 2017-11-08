

//程序名称：PDContDefi.js
//程序功能：契约信息定义
//创建日期：2009-3-13
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDContDefiInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
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
  
  fm.action = "./PDContDefiSave.jsp";
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
function query()
{
  showInfo = window.open("");
}
function beginDefine()
{
	var selNo = Mulline9Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"请选中某行后，再点击"+"");
		return;
	}
	
  fm.action = "./PDSetSession.jsp?IsReadOnly=0&Operator=define"; 

  fm.submit();
}

function definAfterSetSession(FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  
  var selNo = Mulline9Grid.getSelNo();

	var parasPart =  
	"riskcode=" + Mulline9Grid.getRowColData(selNo-1,1)
	+ "&requdate="+Mulline9Grid.getRowColData(selNo-1,3)
	//+ "&riskname=" + Mulline9Grid.getRowColData(selNo-1,2)
	+"&missionid=" + Mulline9Grid.getRowColData(selNo-1,5)
	+ "&submissionid=" + Mulline9Grid.getRowColData(selNo-1,6)
	+ "&activityid=" + Mulline9Grid.getRowColData(selNo-1,7)
	+"&pdflag=1";
	
  var type = fm.all("Type").value; 
  if(type == "Cont")
  	{ showInfo = window.open("PDContDefiEntryMain.jsp?" + parasPart);  showInfo.focus(); }
  else if(type == "Claim")
  	showInfo = window.open("PDClaimDefiEntryMain.jsp?" + parasPart);
  else if(type == "Edor")  	
  	showInfo = window.open("PDEdorDefiEntryMain.jsp?" + parasPart);
  else if(type == "LFRisk")
  	showInfo = window.open("PDLFRiskMain.jsp?" + parasPart);
  else if(type == "Wage")
  	showInfo = window.open("PDWageCalEleMain.jsp?" + parasPart);
}
function queryDefing()
{
	  var mySql=new SqlClass();
	  var sqlid = "PDContDefiInputSql1";
	  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  	
  	var type = fm.all("Type").value;

  	if(type != null)
  	{
  		if (type == "Cont") {
  			sqlid = "PDContDefiInputSql2";
			} else if (type == "Claim") {
				sqlid = "PDContDefiInputSql3";
			} else if (type == "Edor") {
				sqlid = "PDContDefiInputSql4";
			} else if (type == "LFRisk") {
				sqlid = "PDContDefiInputSql5";
			}	
  	}
  	mySql.setSqlId(sqlid);//指定使用的Sql的id
	  mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
	  mySql.addSubPara(fm.all("RequDate").value);//指定传入的参数
		var sql = mySql.getString();
	Mulline9GridTurnPage.queryModal(sql,Mulline9Grid);
}

function queryDeployed()
{
	  var mySql=new SqlClass();
	  var sqlid = "PDContDefiInputSql6";
	  mySql.setResourceName(tResourceName); //指定使用的properties文件名
	  mySql.setSqlId(sqlid);//指定使用的Sql的id
	  mySql.addSubPara(fm.all("RiskCode2").value);//指定传入的参数
  	var sql = mySql.getString();
	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}

function button98()
{
	var selNo = Mulline10Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"请选中某行后，再点击"+"");
		return;
	}

  fm.action = "./PDSetSession.jsp?IsReadOnly=1&Operator=query"; 

  fm.submit();
}

function queryAfterSetSession(FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  
  var type = fm.all("Type").value; 
  
  var selNo = Mulline10Grid.getSelNo();
  
  if(type == "Cont")
  	{showInfo = window.open("PDContDefiEntryMain.jsp?riskcode=" + Mulline10Grid.getRowColData(selNo-1,1)); }
  else if(type == "Claim")
  	showInfo = window.open("PDClaimDefiEntryMain.jsp?riskcode=" + Mulline10Grid.getRowColData(selNo-1,1));
  else if(type == "Edor")
  	showInfo = window.open("PDEdorDefiEntryMain.jsp?riskcode=" + Mulline10Grid.getRowColData(selNo-1,1));
  else if(type == "LFRisk")
  	showInfo = window.open("PDLFRiskMain.jsp?riskcode=" + Mulline10Grid.getRowColData(selNo-1,1));
  else if(type == "Wage")
  	showInfo = window.open("PDWageCalEleMain.jsp?riskcode=" + Mulline10Grid.getRowColData(selNo-1,1));
}


