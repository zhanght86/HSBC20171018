//�������ƣ�PDTestDeploy.js
//�����ܣ���Ʒ�����뷢��
//�������ڣ�2009-3-18
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
//����sql�����ļ�
var tResourceName = "productdef.PDTestDeployInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fm.action = './PDTestDeploySave.jsp';
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
  	var mySql=new SqlClass();
	var sqlid = "PDTestDeployInputSql1";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.all("ReleasePlatform").value);//ָ������Ĳ���
    var sql = mySql.getString();
	var arr = easyExecSql(sql);
    
	if(arr == null)
	{
		myAlert(""+"��Ʒ��Ϣ�ѳɹ���������������Ϣ���������޷��������ϵͳ�����ֻ���"+"");

		return;
	}
//	if(fm.all("ReleasePlatform").value=='LIS')
// window.open(arr[0][0]);
    
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
function query()
{
	var mySql=new SqlClass();
  var sqlid = "PDTestDeployInputSql2";
  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  var RiskCode=fm.RiskCode.value;
  if(RiskCode.substring(0,1)=="#"){
	  mySql.addSubPara("");//ָ������Ĳ���  
	  RiskCode=RiskCode.substring(1,RiskCode.length);
	  mySql.addSubPara(fm.RequDate.value);//ָ������Ĳ���
	  mySql.addSubPara(RiskCode);
	  Mulline9GridTurnPage.pageLineNum  = 32767;
	  }else{
		  mySql.addSubPara(RiskCode);//ָ������Ĳ���  
		  mySql.addSubPara(fm.RequDate.value);//ָ������Ĳ���
		  mySql.addSubPara("") 
		 Mulline9GridTurnPage.pageLineNum  = 10;
	  }
 
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
	var selNo = Mulline9Grid.mulLineCount;
	var flag=false;
	for(var i=0;i<selNo;i++){
		if(Mulline9Grid.getChkNo(i)){flag=true;break;}
	}
	if(!flag)
	{
		myAlert(""+"��ѡ��ĳ��Ʒ���ٵ��"+"");
		return;
	}
	if(!verifyInput())
	  {
	  	return;
	  }
//	if(fm.all("pd_release").value=="LIS"&&fm.all("pd_noy").value==""){
//		alert("��ѡ���Ƿ���ҪPD������");
//		return;
//	}else if(fm.all("pd_release").value=="Proposal"){
//		alert("������ϵͳ������PD������");
//	}
	
	if(document.getElementById('deployReason').value == ''){
		myAlert(""+"����ԭ����Ϊ�գ�"+"");
		return;
	}
	/*
	fm.RiskCode.value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.RequDate.value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.MissionID.value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.SubMissionID.value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.ActivityID.value = Mulline9Grid.getRowColData(selNo-1,7);
			*/
	//���뷢��ƽ̨�Ĵ��룬���ڴ���dblink�ı�����
	//���뷢��ƽ̨�����ͣ���������������������й���������ת
	var mySql=new SqlClass();
  	var sqlid = "PDTestDeployInputSql6";
 	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  	mySql.addSubPara(fm.all("pd_release").value);
  	var a = easyExecSql(mySql.getString());
	fm.all("ReleasePlatform").value = a[0][0];
	fm.all("SysType").value =  a[0][2];
	fm.all("EnvType").value =  a[0][3];
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
	
	fm.RiskCode.value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.RequDate.value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.MissionID.value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.SubMissionID.value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.ActivityID.value = Mulline9Grid.getRowColData(selNo-1,7);
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
  var sqlid = "PDTestDeployInputSql3";
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
    fm.RiskCode.value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.RequDate.value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.MissionID.value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.SubMissionID.value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.ActivityID.value = Mulline9Grid.getRowColData(selNo-1,7);
	
    showInfo = window.open("PDIssueInputMain.jsp?RiskCode=" + fm.RiskCode.value + "&postcode=00&issuetype=2&missionid="+fm.MissionID.value+"&submissionid="+fm.SubMissionID.value+"&activityid="+fm.ActivityID.value);
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
  showInfo = window.open("PDIssueQueryMain.jsp?RiskCode=" + fm.RiskCode.value + "&postcode=00&issuetype=1");
}

function showTestPlanList(){
	try{
	var rowNum=Mulline9Grid.getSelNo();	
	var iArrayIndex = Number(rowNum) - Number(1);
	var iArray=Mulline9Grid.getRowData(iArrayIndex);
	var tfileName = iArray[0];
	fm.RiskCode.value=Mulline9Grid.getRowData(iArrayIndex)[0];
	fm.RequDate.value=Mulline9Grid.getRowData(iArrayIndex)[2];
	}catch(ex){}
	/*
	var mySql=new SqlClass();
  var sqlid = "PDTestDeployInputSql4";
  mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(tfileName);//ָ������Ĳ���
	var sql = mySql.getString();
	
    turnPage1.queryModal(sql, Mulline11Grid);
    
    var rowNum=Mulline11Grid.mulLineCount;
    var mySql2=new SqlClass();
	  var sqlid2 = "PDTestDeployInputSql5";
	  mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	  mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	  mySql2.addSubPara(tfileName);//ָ������Ĳ���
    var querySQL = mySql2.getString();
    var crr = easyExecSql(querySQL);
    
    for(var i = 0; i < rowNum; i ++){
    	if(crr[i][0] == 1){
    		Mulline11Grid.checkBoxSel(i + 1);
    	}
    }	*/
}

function saveTestState(){
	fm.action = 'PDTestPlanDepolySave.jsp';
	fm.submit();
}

function showDeployReason(){
	document.getElementById("deployReasonID").style.display = '';
}

function selectpd_release(value){
	if(value=="LIS"){
		//��ʾ�Ƿ���ҪPD������
		document.getElementById("pd_noyStrdiv").style.display = '';
		document.getElementById("pd_noydiv").style.display = '';
	}else{
		//�����Ƿ���ҪPD������
		document.getElementById("pd_noyStrdiv").style.display = 'none';
		document.getElementById("pd_noydiv").style.display = 'none';
	}
	showDeployReason();
}
