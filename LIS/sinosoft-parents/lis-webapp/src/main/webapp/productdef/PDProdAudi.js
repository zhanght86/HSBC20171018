//程序名称：PDProdAudi.js
//程序功能：产品审核
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDProdAudiInputSql";
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
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 


function queryAudit()
{
	var mySql=new SqlClass();
  var sqlid = "PDProdAudiInputSql1";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
  mySql.addSubPara(fm.all("RequDate").value);//指定传入的参数
  var sql = mySql.getString();
	Mulline9GridTurnPage.queryModal(sql,Mulline9Grid);
}
function query()
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
  
  	var selNo = Mulline10Grid.getSelNo();
	
	var parasPart =  
	"riskcode=" + Mulline10Grid.getRowColData(selNo-1,1)
	+ "&requdate="+Mulline10Grid.getRowColData(selNo-1,3)
	+ "&riskname=" + Mulline10Grid.getRowColData(selNo-1,2)
	;

	showInfo = window.open("PDProdAudiDetailMain.jsp?" + parasPart);
}

function beginAudit()
{
	var selNo = Mulline9Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"请选中某行后，再点击"+"");
		return;
	}
	
  fm.action = "./PDSetSession.jsp?IsReadOnly=1&IsReadOnlyFirstOpen=0&Operator=define"; 

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
//	+ "&riskname=" + Mulline9Grid.getRowColData(selNo-1,2)	
	+"&missionid=" + Mulline9Grid.getRowColData(selNo-1,5)
	+ "&submissionid=" + Mulline9Grid.getRowColData(selNo-1,6)
	+ "&activityid=" + Mulline9Grid.getRowColData(selNo-1,7)
	+"&pdflag=0";
	showInfo = window.open("PDProdAudiDetailMain.jsp?" + parasPart);
}

function queryDeployed()
{
	var mySql=new SqlClass();
  var sqlid = "PDProdAudiInputSql2";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(fm.all("RiskCode2").value);//指定传入的参数
  	var sql = mySql.getString();
	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}
