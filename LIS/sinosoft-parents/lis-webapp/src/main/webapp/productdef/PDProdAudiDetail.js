//�������ƣ�PDProdAudiDetail.js
//�����ܣ���Ʒ�����ϸ
//�������ڣ�2009-3-18
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
//����sql�����ļ�
var tResourceName = "productdef.PDProdAudiDetailInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
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
var Mulline10GridTurnPage = new turnPageClass(); 

function button391()
{
	showInfo = window.open("PDRiskDefiMain.jsp?riskcode=" + fm.RiskCode.value +"&requdate="+fm.RequDate.value+"&pdflag="+fm.PdFlag.value);
}
function button392()
{
  showInfo = window.open("PDContDefiEntryMain.jsp?riskcode=" + fm.RiskCode.value+"&requdate="+fm.RequDate.value+"&pdflag="+fm.PdFlag.value);
}
function button393()
{
  showInfo = window.open("PDEdorDefiEntryMain.jsp?riskcode=" + fm.RiskCode.value+"&requdate="+fm.RequDate.value+"&riskname="+fm.all("RiskName").value+"&pdflag="+fm.PdFlag.value);
}
function button394()
{
  showInfo = window.open("PDClaimDefiEntryMain.jsp?riskcode=" + fm.RiskCode.value+"&requdate="+fm.RequDate.value+"&riskname="+fm.all("RiskName").value+"&pdflag="+fm.PdFlag.value);
}
function button395()
{
  window.open("PDLFRiskInput.jsp?riskcode="+fm.RiskCode.value+"&requdate="+fm.RequDate.value+"&pdflag="+fm.PdFlag.value);
}
function button396()
{
  showInfo = window.open("");
}
function button397()
{
  showInfo = window.open("");
}
function button398()
{
  showInfo = window.open("");
}
function button399()
{
  showInfo = window.open("");
}
function btnAuditDone()
{
  	fm.all("operator").value = "audit";
	submitForm();
}
function button401()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
   var sqlid = "PDProdAudiDetailInputSql1";
   mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
   mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
   mySql.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
   var sqlid = "PDProdAudiDetailInputSql2";
   mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
   mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
   mySql.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 10;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function IssueQuery()
{
  showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.RiskCode.value + "&postcode=20&issuetype=1"+"&pdflag="+fm.PdFlag.value);
}
function IssueInput()
{
  showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.RiskCode.value + "&postcode=20&issuetype=1&missionid="+fm.MissionID.value+"&submissionid="+fm.SubMissionID.value+"&activityid="+fm.ActivityID.value+"&pdflag="+fm.PdFlag.value);
}
