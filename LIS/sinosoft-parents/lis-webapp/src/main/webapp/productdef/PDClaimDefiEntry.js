//�������ƣ�PDClaimDefiEntry.js
//�����ܣ�������Ϣ¼�����
//�������ڣ�2009-3-16
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
//����sql�����ļ�
var tResourceName = "productdef.PDClaimDefiEntryInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
if(fm.IsReadOnly.value == "1")
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

function QueryBaseInfo()
{

	window.open("PDRiskDefiMain.jsp?LoadFlagButton=0&pdflag=0&riskcode=" + fm.RiskCode.value+"&requdate="+fm.RequDate.value);
}

var Mulline9GridTurnPage = new turnPageClass(); 
function button194()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"��ѡ��һ�����ָ�������"+" "+"��"+"");
		return;
	}
  showInfo = window.open("PDDutyGetClmMain.jsp?riskcode=" + fm.RiskCode.value+"&pdflag="+fm.PdFlag.value+"&getdutycode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button195()
{
  showInfo = window.open("");
}
function button196()
{
  showInfo = window.open("");
}
function button197()
{
  showInfo = window.open("");
}
function button198()
{
  showInfo = window.open("");
}
function btnClaimDone()
{
  var alStr = checkRules("12"); 
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  
  fm.operator.value = "claim";
  submitForm();
}

function queryMulline9Grid()
{
	var mySql=new SqlClass();
  var sqlid = "PDClaimDefiEntryInputSql1";
  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(fm.all('RiskCode').value);//ָ������Ĳ���
  strSQL = mySql.getString();
  Mulline9GridTurnPage.pageLineNum  = 3215;
  Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function IssueQuery()
{
  showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.RiskCode.value + "&pdflag="+fm.PdFlag.value+"&postcode=12&issuetype=0");
}
function IssueInput()
{
  showInfo = window.open("PDIssueInputMain.jsp?riskcode=" +fm.RiskCode.value + "&pdflag="+fm.PdFlag.value+"&postcode=12&issuetype=0&missionid="+fm.MissionID.value+"&submissionid="+fm.SubMissionID.value+"&activityid="+fm.ActivityID.value);
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	document.getElementById('savabutton1').disabled=true;
	document.getElementById('savabutton2').disabled=true;
	}

}
/*
 * 配置累加器跳转
 */
function button199()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		alert("请选择一条险种给付责任 ！");
		return;
	}
  showInfo = window.open("PDLCalculatorMain.jsp?riskcode=" + document.all("RiskCode").value+"&getdutycode="+Mulline9Grid.getRowColData(selNo-1,3));
}