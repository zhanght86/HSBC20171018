//�������ƣ�PDTestDeploy.js
//�����ܣ���Ʒ�����뷢��
//�������ڣ�2009-3-18
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline8GridTurnPage = new turnPageClass(); 
//����sql�����ļ�
var tResourceName = "productdef.PublicInfoDeployInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fm.action = './PublicInfoDeploySave.jsp';
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
  if(showInfo != null)
  {
	  showInfo.close();
  }

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }else if(FlagStr == "Successs"){
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
  }
  else
  {
  	/*
  	var mySql=new SqlClass();
	  var sqlid = "PDTestDeployInputSql1";
	  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	  mySql.addSubPara(fm.all("ReleasePlatform").value);//ָ������Ĳ���
    var sql = mySql.getString();
	var arr = easyExecSql(sql);
    
	
	if(arr == null)
	{
		alert("��Ʒ��Ϣ�ѳɹ���������������Ϣ���������޷��������ϵͳ�����ֻ���");

		return;
	}
    window.open(arr[0][0]);
    
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    */
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
    initForm();
   
  } 
}

function query()
{
	
	var mySql=new SqlClass();
  var sqlid = "PublicInfoDeployInputSql2";
  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  //mySql.addSubPara(fm.all("RiskCode").value);//ָ������Ĳ���
  mySql.addSubPara('1');//ָ������Ĳ���
 
  var sql = mySql.getString();

	Mulline9GridTurnPage.queryModal(sql,Mulline9Grid);
	
}
function button406()
{
  showInfo = window.open("");
}
function button407()
{
  showInfo = window.open("");
}
function button408()
{
  showInfo = window.open("");
}
function btnDeploy()
{
	var selNo = Mulline9Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"��ѡ����Ҫ���������ݺ��ٵ��"+"");
		return;
	}
	var selNo2 = Mulline10Grid.getSelNo();
	
	if(selNo2 == 0)
	{
		myAlert(""+"��ѡ�з���ƽ̨���ٵ��"+"");
		return;
	}
	
	
	fm.all("RuleType").value = Mulline9Grid.getRowColData(selNo-1,1);
	//���뷢��ƽ̨�Ĵ��룬���ڴ���dblink�ı�����
	//���뷢��ƽ̨�����ͣ���������������������й���������ת
	fm.all("ReleasePlatform").value = Mulline10Grid.getRowColData(selNo2-1,1);
	fm.all("SysType").value = Mulline10Grid.getRowColData(selNo2-1,3);
	fm.all("EnvType").value = Mulline10Grid.getRowColData(selNo2-1,4);
	
	fm.all("operator").value = "testdeploy";
	
	submitForm();
}

function btnDeployIssue()
{
	var selNo = Mulline9Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"��ѡ��ĳ��Ʒ���ٵ��"+"");
		return;
	}
	
	fm.all("RiskCode").value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.all("RequDate").value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.all("MissionID").value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.all("SubMissionID").value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.all("ActivityID").value = Mulline9Grid.getRowColData(selNo-1,7);
	//���뷢��ƽ̨�Ĵ��룬���ڴ���dblink�ı�����
	//���뷢��ƽ̨�����ͣ���������������������й���������ת
	fm.all("ReleasePlatform").value ="" ;
	fm.all("PlatformType").value = "3";
	
	fm.all("operator").value = "testdeploy";
	
	submitForm();
}
function queryMulline10Grid()
{           
  var mySql=new SqlClass();
  var sqlid = "PublicInfoDeployInputSql3";
  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  mySql.addSubPara("sqlid");//ָ������Ĳ���
  var sql = mySql.getString();
	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}
function IssueInput()
{
	var selNo = Mulline9Grid.getSelNo();	
    if(selNo == 0)
    {
	   myAlert(""+"���Ȳ�ѯѡ��һ����Ʒ���ٵ��"+"");
	   return;
    }
    fm.all("RiskCode").value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.all("RequDate").value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.all("MissionID").value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.all("SubMissionID").value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.all("ActivityID").value = Mulline9Grid.getRowColData(selNo-1,7);
	
    showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=30&issuetype=2&missionid="+fm.all("MissionID").value+"&submissionid="+fm.all("SubMissionID").value+"&activityid="+fm.all("ActivityID").value);
}


//��Ʒ������Ϣ�������
function baseInfoDone()
{
  fm.all("operator").value="workflow";
  
  var alStr = checkRules("00");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  
  if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  fm.action = "./PDRiskDefiSave.jsp";
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();  
}

//��Լ����������
function button119()
{
  fm.all("IsDealWorkflow").value = "1";
  fm.all("operator").value = "cont";
  
  var alStr = checkRules("10");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }

  if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  fm.action = "./PDContDefiEntrySave.jsp";
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit(); 
}

// ���⹤��������
function btnClaimDone()
{
   var alStr = checkRules("12"); 
   if(alStr != "")
   {
  	  myAlert(alStr);
  	  return;
   }
            
   fm.all("operator").value = "claim";
   fm.action = "./PDContDefiEntrySave.jsp";
   submitForm();
}

function btnEdorDone()
{
  var alStr = checkRules("11");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }

    fm.all("operator").value = "edor";
	fm.action = "./PDContDefiEntrySave.jsp";
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

//��˹���������
function btnAuditDone()
{
  	fm.all("operator").value = "audit";
	if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  fm.action = "./PDProdAudiDetailSave.jsp";
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}
function IssueQuery()
{
  showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=30&issuetype=1");
}


function saveTestState(){
	fm.action = 'PDTestPlanDepolySave.jsp';
	fm.submit();
}

function showDeployReason(){
	document.getElementById("deployReasonID").style.display = '';
}
function queryrule()
{
	var RiskCode=fm.all("RiskCode").value;
	var UWCODE=fm.all("UWCODE").value;
	var RELAPOLTYPE=fm.all("RELAPOLTYPE").value;
	var UWTYPE=fm.all("UWTYPE").value;
	var STANDBYFLAG2=fm.all("STANDBYFLAG2").value;
	var STANDBYFLAG1=fm.all("STANDBYFLAG1").value;
	  var mySql=new SqlClass();
	  var sqlid = "PublicInfoDeployInputSql6";
	if(UWCODE.substring(0,1)=="#"){
		sqlid="PublicInfoDeployInputSql7";
		UWCODE=UWCODE.substring(1,UWCODE.length);
		  mySql.addSubPara(UWCODE);
		  mySql.addSubPara(UWCODE);
		  Mulline8GridTurnPage.pageLineNum  = 1024;
	}else{
		  mySql.addSubPara(UWCODE);
		  mySql.addSubPara(RiskCode);
		  mySql.addSubPara(UWTYPE);
		  mySql.addSubPara(RELAPOLTYPE);
		  mySql.addSubPara(STANDBYFLAG2);
		  mySql.addSubPara(STANDBYFLAG1);
		  mySql.addSubPara(UWCODE);
		  mySql.addSubPara(RiskCode);
		  mySql.addSubPara(UWTYPE);
		  mySql.addSubPara(RELAPOLTYPE);
		  mySql.addSubPara(STANDBYFLAG2);
		  mySql.addSubPara(STANDBYFLAG1);
		  Mulline8GridTurnPage.pageLineNum  = 15;
	}	
	  mySql.setResourceName(tResourceName); 
	  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id


	  var sql = mySql.getString();

		Mulline8GridTurnPage.queryModal(sql,Mulline8Grid);
	
}
function uwDeploy()
{
	if(!inituwDeploy())return false;
var selNo = Mulline8Grid.mulLineCount;
var flag=false;
for(var i=0;i<selNo;i++){
	if(Mulline8Grid.getChkNo(i)){flag=true;break;}
}
if(!flag)
{
	myAlert(""+"��ѡ�в�Ʒ���ٵ��"+"");
	return;
}
fm.all("operator").value="UWDEPLOY";
submitForm();
}
function uwDeploySelect()
{
	if(!inituwDeploy())return false;
	fm.all("operator").value="UWDEPLOYSELECT";
	var RiskCode=fm.all("RiskCode").value;
	var UWCODE=fm.all("UWCODE").value;
	var RELAPOLTYPE=fm.all("RELAPOLTYPE").value;
	var UWTYPE=fm.all("UWTYPE").value;
	var STANDBYFLAG2=fm.all("STANDBYFLAG2").value;
	var STANDBYFLAG1=fm.all("STANDBYFLAG1").value;	
	  var mySql=new SqlClass();
	  var sqlid = "PublicInfoDeployInputSql6";
	  mySql.setResourceName(tResourceName); 
	  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id

	  mySql.addSubPara(UWCODE);
	  mySql.addSubPara(RiskCode);
	  mySql.addSubPara(UWTYPE);
	  mySql.addSubPara(RELAPOLTYPE);
	  mySql.addSubPara(STANDBYFLAG2);
	  mySql.addSubPara(STANDBYFLAG1);
	  mySql.addSubPara(UWCODE);
	  mySql.addSubPara(RiskCode);
	  mySql.addSubPara(UWTYPE);
	  mySql.addSubPara(RELAPOLTYPE);
	  mySql.addSubPara(STANDBYFLAG2);
	  mySql.addSubPara(STANDBYFLAG1);
	  var sql = mySql.getString();
	  fm.all("SQL").value=sql;
	submitForm();	
}
function inituwDeploy()
{
//	var selNo = Mulline9Grid.getSelNo();
	
	//if(selNo == 0)
	//{
	//	alert("��ѡ����Ҫ���������ݺ��ٵ��");
	//	return;
	//}
	var selNo2 = Mulline10Grid.getSelNo();
	
	if(selNo2 == 0)
	{
		myAlert(""+"��ѡ�з���ƽ̨���ٵ��"+"");
		return false;
	}
	
	
	//fm.all("RuleType").value = Mulline9Grid.getRowColData(selNo-1,1);
	//���뷢��ƽ̨�Ĵ��룬���ڴ���dblink�ı�����
	//���뷢��ƽ̨�����ͣ���������������������й���������ת
	fm.all("ReleasePlatform").value = Mulline10Grid.getRowColData(selNo2-1,1);
	fm.all("SysType").value = Mulline10Grid.getRowColData(selNo2-1,3);
	fm.all("EnvType").value = Mulline10Grid.getRowColData(selNo2-1,4);
	return true;
}