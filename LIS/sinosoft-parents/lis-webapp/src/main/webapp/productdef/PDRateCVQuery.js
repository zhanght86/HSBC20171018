//程序名称：PDAlgoTempLibQuery.js
//程序功能：算法模板库查询界面
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var QuerySQLCache="";
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDRateCVQueryInputSql";
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
	initMulline9Grid();
	
	 var mySql=new SqlClass();
	 var sqlid = "PDRateCVQueryInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id	 
	 mySql.addSubPara(QuerySQLCache);
	 mySql.addSubPara(fm.TableName.value);//指定传入的参数
  var strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
   QuerySQLCache="";
}
function retResult()
{
	  try
	  {
	  	top.opener.focus();
	  	top.close();
	  }
	  catch(ex)
	  {
	  	myAlert(""+"关闭录入页面则退出系统，请重新登录"+"");
	  	top.close();
	  }
}


