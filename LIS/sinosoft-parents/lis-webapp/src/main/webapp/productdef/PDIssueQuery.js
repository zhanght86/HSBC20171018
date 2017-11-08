//程序名称：PDIssueQuery.js
//程序功能：问题件录入
//创建日期：2009-4-3
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;
//定义sql配置文件
var tResourceName = "productdef.PDIssueQueryInputSql";
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
var Mulline10GridTurnPage = new turnPageClass(); 
function ReplyIssue()
{
	 if(fm.all('RiskCode').value!=fm.all('hiddenRiskCode').value){
	 	  myAlert(""+"与选中险种不符！"+"");
			return false;
	 }
	 if(fm.all('BackPost').value!=fm.all('hiddenBackPost').value){
	 	  myAlert(""+"返回岗与当前岗位不符！"+"");
			return false;
	 }
	 if(fm.all('FindFlag').value=='1'){
	 	 myAlert(""+"没有权限执行该操作！"+"");
			return false;
	 }
 	 tSel = Mulline10Grid.getSelNo();
	 if(tSel==0||tSel==null)
	 {
			myAlert(""+"请先选择问题件信息！"+"");
			return false;
	 }
	 if(  Mulline10Grid.getRowColData(tSel - 1, 4) == '2' )
	 {
		myAlert(""+"该问题件已回复！"+"");
		return;	
	 }
	 if( fm.ReplyContDesc.value == "" )
	 {
	 	myAlert(""+"回复内容不能为空！"+"");
	 	return;
	 }
	 var tSerialNo = Mulline10Grid.getRowColData(tSel - 1, 1);
	 fm.all('SerialNo').value = tSerialNo;
	 fm.all('operator').value = 'modify';
	  fm.action="./PDIssueQuerySave.jsp"
	 submitForm();
}
function button55()
{
  top.opener.focus();
	top.close();
}

function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	document.getElementById('Replybutton').disabled=true;
	}

}

function QueryIssue()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDIssueQueryInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RiskCode.value);//指定传入的参数
	 mySql.addSubPara(fm.BackPost.value);//指定传入的参数
	 mySql.addSubPara(fm.IssueCont.value);//指定传入的参数
	 mySql.addSubPara(fm.IssueState.value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 10;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function DownLoadFile(){
	 tSel = Mulline10Grid.getSelNo();
	 if(tSel==0||tSel==null)
	 {
			myAlert(""+"请先选择问题件信息！"+"");
			return false;
	 }
	 
   var FilePath = Mulline10Grid.getRowColData(tSel - 1, 6);  
   if (FilePath==""||FilePath==null){
	   myAlert(""+"没有附件"+","+"不能进行下载操作！"+"")	
	   return false;
   }   
   //alert(FilePath);
   //var showStr="正在下载数据……";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
   //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
   fm.action = "./DownLoadSave.jsp?FilePath="+FilePath;
   fm.submit();
	}
