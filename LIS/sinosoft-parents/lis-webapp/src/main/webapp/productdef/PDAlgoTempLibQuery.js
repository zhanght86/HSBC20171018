//程序名称：PDAlgoTempLibQuery.js
//程序功能：算法模板库查询界面
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDAlgoTempLibQueryInputSql";
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
  var strSQL = "";
  var mySql=new SqlClass();
  var sqlid = "PDAlgoTempLibQueryInputSql1";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(fm.all("Type").value);//指定传入的参数
  mySql.addSubPara(fm.all("AlogTempCode").value);//指定传入的参数
  mySql.addSubPara(fm.all("AlogTempName").value);//指定传入的参数
  mySql.addSubPara(fm.all("Content").value);//指定传入的参数
  mySql.addSubPara(fm.all("Description").value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function retResult()
{
	var arrReturn = new Array();
	var tSel = Mulline9Grid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录，再点击返回按钮。"+"" );
	else
	{
	  if(Mulline9Grid.getSelNo() == 0)
	  {
	  	myAlert(""+"请选中某项，再点击返回按钮"+"");
	  	return;
	  }
	  
    	var Code = Mulline9Grid.getRowColData(Mulline9Grid.getSelNo()-1, 1);
	  try
	  {
	  	top.opener.focus();
	  	top.opener.afterQueryAlgo(Code);
	  	top.close();
	  }
	  catch(ex)
	  {
	  	myAlert(""+"关闭录入页面则退出系统，请重新登录"+"");
	  	window.open("PDCalFactorInput.jsp");
	  	top.close();
	  }
	}
}


