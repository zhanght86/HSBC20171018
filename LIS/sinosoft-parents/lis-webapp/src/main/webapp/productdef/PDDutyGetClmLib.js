//程序名称：PDDutyGetClmLib.js
//程序功能：给付赔付库
//创建日期：2009-3-17
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	
//定义sql配置文件
var tResourceName = "productdef.PDDutyGetClmLibInputSql";
function returnParent()
{
	if(Mulline9Grid.getRowColData(fm.all("GetDutyCode2RowOfMulLine").value,4) == "")
	{
		myAlert(""+"请选中某个责任给付赔付库代码，再点击返回"+"");
		return;
	}
	top.opener.afterQuery(Mulline9Grid.getRowColData(fm.all("GetDutyCode2RowOfMulLine").value,4));	
	
	top.opener.focus();		top.close();
}
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
    
	initButtonDisplay();    
    query();
    updateDisplayState();
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
function query()
{
  var wherePart = getWherePart('getdutycode2',"GetDutyCode2")
  	+ getWherePart('getdutyname2',"GetDutyName","like")
  	+ getWherePart('getdutykind2',"GetDutyKind2")
  	+ getWherePart('riskcode',"RiskCode")
  	+ getWherePart('getdutycode',"GetDutyCode")
  	+ getWherePart('getdutykind',"GetDutyKind")  	  	  	
  	;

    var mySql=new SqlClass();
    var sqlid = "PDDutyGetClmLibInputSql1";
    mySql.setResourceName(tResourceName); //指定使用的properties文件名
    mySql.setSqlId(sqlid);//指定使用的Sql的id
    mySql.addSubPara(fm.all("GetDutyCode2").value);//指定传入的参数
    mySql.addSubPara(fm.all("GetDutyName").value);//指定传入的参数
    mySql.addSubPara(fm.all("GetDutyKind2").value);//指定传入的参数
    mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
    mySql.addSubPara(fm.all("GetDutyCode").value);//指定传入的参数
    mySql.addSubPara(fm.all("GetDutyKind").value);//指定传入的参数
    var sql = mySql.getString();
  	Mulline11GridTurnPage.queryModal(sql,Mulline11Grid);
  	
  	var mySql2=new SqlClass();
    var sqlid2 = "PDDutyGetClmLibInputSql2";
    mySql2.setResourceName(tResourceName); //指定使用的properties文件名
    mySql2.setSqlId(sqlid2);//指定使用的Sql的id
    mySql2.addSubPara(fm.all("GetDutyCode2").value);//指定传入的参数
    mySql2.addSubPara(fm.all("GetDutyName").value);//指定传入的参数
    mySql2.addSubPara(fm.all("GetDutyKind2").value);//指定传入的参数
    mySql2.addSubPara(fm.all("RiskCode").value);//指定传入的参数
    mySql2.addSubPara(fm.all("GetDutyCode").value);//指定传入的参数
    mySql2.addSubPara(fm.all("GetDutyKind").value);//指定传入的参数
  	sql = mySql2.getString();
  	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}

function queryRela()
{
	var selNo = Mulline11Grid.getSelNo();
	
	var mySql=new SqlClass();
  var sqlid = "PDDutyGetClmLibInputSql3";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(Mulline11Grid.getRowColData(selNo - 1, 1));//指定传入的参数
 	sql = mySql.getString();
  	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}
function button365()
{
  showInfo = window.open("");
}
function save()
{
 fm.all("operator").value="save";
 submitForm();
}
function update()
{
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 fm.all("operator").value="del";
 submitForm();
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
   var sqlid = "PDDutyGetClmLibInputSql4";
   mySql.setResourceName(tResourceName); //指定使用的properties文件名
   mySql.setSqlId(sqlid);//指定使用的Sql的id
   mySql.addSubPara("sqlid");//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   strSQL = "";Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryMulline11Grid()
{
   var strSQL = "";
   strSQL = "";Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}
