//�������ƣ�PDProdAmend.js
//�����ܣ���Ʒ�޸�
//�������ڣ�2009-3-18
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
//����sql�����ļ�
var tResourceName = "productdef.PDProdAmendInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
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
function query()
{
	var mySql=new SqlClass();
  var sqlid = "PDProdAmendInputSql1";
  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(fm.all("RiskCode").value);//ָ������Ĳ���
  mySql.addSubPara(fm.all("RequDate").value);//ָ������Ĳ���
  mySql.addSubPara(fm.all("DeplDate").value);//ָ������Ĳ���
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
   mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
   mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
   mySql.addSubPara("sqlid");//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function IssueInput()
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"��ѡ��һ�������߲�Ʒ���ٵ��"+"");
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
