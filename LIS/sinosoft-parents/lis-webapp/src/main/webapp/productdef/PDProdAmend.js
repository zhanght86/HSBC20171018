//程序名称：PDProdAmend.js
//程序功能：产品修改
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDProdAmendInputSql";
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
function query()
{
	var mySql=new SqlClass();
  var sqlid = "PDProdAmendInputSql1";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
  mySql.addSubPara(fm.all("RequDate").value);//指定传入的参数
  mySql.addSubPara(fm.all("DeplDate").value);//指定传入的参数
  var sql = mySql.getString();
	Mulline9GridTurnPage.queryModal(sql,Mulline9Grid);
}
function button402()
{
  showInfo = window.open("");
}
function button403()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
   var sqlid = "PDProdAmendInputSql2";
   mySql.setResourceName(tResourceName); //指定使用的properties文件名
   mySql.setSqlId(sqlid);//指定使用的Sql的id
   mySql.addSubPara("sqlid");//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function IssueInput()
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中一条已上线产品后，再点击"+"");
		return;
	}
	var urlStr = "?riskcode=" + Mulline9Grid.getRowColData(selNo-1,1) + "&postcode=40&issuetype=3";
	var missionID = Mulline9Grid.getRowColData(selNo-1,5);
	var submissionID = Mulline9Grid.getRowColData(selNo-1,6);
	var activityID = Mulline9Grid.getRowColData(selNo-1,7);		
	urlStr += "&missionid="+missionID;
	urlStr += "&submissionid="+submissionID;
	urlStr += "&activityid="+activityID;	
  showInfo = window.open("PDIssueInputMain.jsp"+urlStr);
}
