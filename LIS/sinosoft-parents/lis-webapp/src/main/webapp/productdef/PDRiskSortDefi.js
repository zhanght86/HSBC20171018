//程序名称：PDRiskSortDefi.js
//程序功能：险种分类定�?
//创建日期�?009-3-12
//该文件中包含客户端需要处理的函数和事�?
var turnPage = new turnPageClass();
var showInfo;
var tResourceName = "productdef.PDRiskSortDefiInputSql";
function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
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
function save()
{
 fm.all("operator").value="save";
 if(Mulline9Grid.mulLineCount==0){
 		fm.all("operator").value="del";
 }
 submitForm();
}
function returnParent()
{
  top.opener.focus();
	top.close();
}

function queryMulline9Grid()
{
    var mySql=new SqlClass();
   mySql.setResourceName(tResourceName); //指定使用的properties文件�?
	mySql.setSqlId("PDRiskSortDefiInputSql1");//指定使用的Sql的id
	mySql.addSubPara(fm.RiskCode.value);//指定传入的参�?
	var strSql = mySql.getString();
   Mulline9GridTurnPage.queryModal(strSql,Mulline9Grid);
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	//document.getElementById('save4').style.display = 'none';
	document.getElementById('savabutton').disabled=true;
	}

}