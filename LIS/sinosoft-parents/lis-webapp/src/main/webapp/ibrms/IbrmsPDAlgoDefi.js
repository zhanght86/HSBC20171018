var tResourceName = 'ibrms.IbrmsPDAlgoDefiSql';

function initRuleCode()
{
	var tRuleCode = document.getElementById("RuleName").value;
	var tModulCode = document.getElementById("ModulName").value;
	
	//alert(tModulCode);
	//alert(tRuleCode);
	
	if((tRuleCode==null||tRuleCode==''||tRuleCode=='null')&&tModulCode!=null&&tModulCode!='')
	{
		//alert('submit');
		fm2.submit();
	}
}


function afterInitRuleCode(tCode)
{
	document.getElementById("RuleName").value = tCode;
	jRuleName = tCode;
}



//���л���ҳʱ,�������ҵ���߼�
function onRiskTabChange(tPage)
{
	queryRuleState();
	tCurrentProcess = tPage;
	//alert(tCurrentProcess);
	//��ѯLRtemplatet��
	//
	var riskcode="";
	try{riskcode=fm1.all("riskcode").value;}catch(ex){}
	var RuleTemplateName = document.getElementById("RuleState").value;
	//var tSQL = "select ID from LRTemplatet where rulename='"+jRuleName+"' "
	//         + " union "
	//         + " select ID from LRTemplate where rulename='"+jRuleName+"' ";
		var sqlid1="IbrmsPDAlgoDefiSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(jRuleName);//ָ������Ĳ���
		mySql1.addSubPara(jRuleName);//ָ������Ĳ���
	
	  var strSql=mySql1.getString();	
	  
	var tLRTemplate_ID = easyExecSql(strSql);
	
//	alert(tLRTemplate_ID);
//	alert(tPage);
		//����tab���л�
	if(tPage=='1')
	{
		//
		if(RuleTemplateName=="")
		{
				//onRiskTabChange(2);
				//return;
		}
		tab1c.style.display="";
		tab2c.style.display="none";
		tab3c.style.display="none";
		tab4c.style.display="none";
		//document.getElementById("NewRiskPlan").src = " ";
		//������Ϣ��ѯ
		if(tLRTemplate_ID==null||typeof(tLRTemplate_ID)=="undefined"||tLRTemplate_ID=='null')
		{
			//alert('1');
			divRiskCalDetail.style.display="none";
			document.getElementById("IbrmsDetail").src = "";
		}
		else
		{
			//alert('2');
			var tUrl = "";
//			alert(jRuleType);
			if(jRuleType=='1')
			{
				tUrl =  "./RuleMakeNew.jsp?flag=0&RuleName="+jRuleName+"&LRTemplate_ID="+tLRTemplate_ID+"&LRTemplateName="+RuleTemplateName+"&IntegerFlag=1&riskcode="+riskcode;
			}
			else
			{
				tUrl =  "./RuleMake.jsp?flag=0&RuleName="+jRuleName+"&LRTemplate_ID="+tLRTemplate_ID+"&LRTemplateName="+RuleTemplateName+"&IntegerFlag=1";

			}
			document.getElementById("IbrmsDetail").src = tUrl;
		}
		document.getElementById("CurProcess").value = "������Ϣ";
		divRiskCalDetail.style.display="";
		
	}
	if(tPage=='2')
	{
		if(RuleTemplateName=='LRTemplate')
		{
			alert('�����Ѿ�����,��������/�޸�!');
			onRiskTabChange(1);
			return;
		}
		
		tab1c.style.display="none";
		tab2c.style.display="";
		tab3c.style.display="none";
		tab4c.style.display="none";
		//tongmeng 2011-02-15 modify
		//�ж��Ƿ��Ѿ������.���������Ļ�,��ʾ�㷨��ϸ��Ϣ,���û�б���,����ʾ
		var tFlag = 1;
		if(tLRTemplate_ID!=null&&!typeof(tLRTemplate_ID)!="undefined")
		{
			tFlag = 4;
		}
//		alert(jRuleType);
		var tURL = "";
		if(jRuleType=='1')
			{
		tURL="./RuleMakeNew.jsp?"+
    	"flag="+tFlag+
    	"&RuleName="+jRuleName+
    	"&Creator="+jCreator+
    	"&RuleStartDate="+jRuleStartDate+
    	"&RuleEndDate="+jRuleEndDate+
    	//"&TempalteLevel="+jTempalteLevel+
    	"&TempalteLevel=01"+
    	"&Business="+jBusiness+
    	"&State="+jState+
    	"&Valid="+jValid+
    	"&LRTemplate_ID="+tLRTemplate_ID+
    	"&LRTemplateName="+RuleTemplateName
    	+"&IntegerFlag=1&riskcode="+riskcode;
    }
    else
    {
    	tURL="./RuleMake.jsp?"+
    	"flag="+tFlag+
    	"&RuleName="+jRuleName+
    	"&Creator="+jCreator+
    	"&RuleStartDate="+jRuleStartDate+
    	"&RuleEndDate="+jRuleEndDate+
    	//"&TempalteLevel="+jTempalteLevel+
    	"&TempalteLevel=01"+
    	"&Business="+jBusiness+
    	"&State="+jState+
    	"&Valid="+jValid+
    	"&LRTemplate_ID="+tLRTemplate_ID+
    	"&LRTemplateName="+RuleTemplateName
    	+"&IntegerFlag=1";
    }
    	
    	document.getElementById("IbrmsMake").src = tURL;
    	
    	document.getElementById("CurProcess").value = "������/�޸�";
	}
	if(tPage=='3')
	{
		if(RuleTemplateName=='LRTemplate')
		{
			alert('�����Ѿ�����,���������!');
			onRiskTabChange(1);
			return;
		}
		if(RuleTemplateName=="")
		{
			  alert('����δ����,���������!');
				onRiskTabChange(2);
				return;
		}
		
		tab1c.style.display="none";
		tab2c.style.display="none";
		tab3c.style.display="";
		tab4c.style.display="none";
		//http://127.0.0.1:8080/lisnational/ibrms/ruleTest.jsp?template=00000000000000000947&javaClass=com.sinosoft.ibrms.RuleUI&method=AutoUWIndUIForTest
	 var turl = "";
	  if(jRuleType=='1')
		{
	   turl = "./ruleTest.jsp?template="+tLRTemplate_ID+"&javaClass=com.sinosoft.ibrms.RuleUI&method=AutoUWIndUIForTest&IntegerFlag=1";
		}
		else
		{
			turl = "./ruleTest.jsp?template="+tLRTemplate_ID+"&javaClass=com.sinosoft.ibrms.RuleUI&method=AutoUWIndUIForTest";
		}
	  document.getElementById("IbrmsTest").src = turl;
	  
	  document.getElementById("CurProcess").value = "�������";
	}
	if(tPage=='4')
	{
		if(tLRTemplate_ID==null||typeof(tLRTemplate_ID)=="undefined"||tLRTemplate_ID=='null')
		{
			//�������δ����,����Ҫ���뷢����ǩ
			alert('���ڹ��򱣴���ٽ��з���!');
			onRiskTabChange(2);
			return;
		}
		if(RuleTemplateName=='LRTemplate')
		{
			document.getElementById("ruleDeploy").disabled = true;
			document.getElementById("downloadTemplate1").disabled = true;
			document.getElementById("downloadTemplate2").disabled = true;
			document.getElementById("ImportExcel1").disabled = true;
			document.getElementById("ImportExcel2").disabled = true;
			document.getElementById("FileName").disabled = true;
			
			
			document.getElementById("ruleUnDeploy").disabled = false;
		}
		else
		{
			document.getElementById("ruleDeploy").disabled = false;
			document.getElementById("downloadTemplate1").disabled = false;
			document.getElementById("downloadTemplate2").disabled = false;
			document.getElementById("ImportExcel1").disabled = false;
			document.getElementById("ImportExcel2").disabled = false;
		  document.getElementById("FileName").disabled = false;
				
			document.getElementById("ruleUnDeploy").disabled = true;
		}
		document.getElementById("TempalteID").value = tLRTemplate_ID;
		
		tab1c.style.display="none";
		tab2c.style.display="none";
		tab3c.style.display="none";
		tab4c.style.display="";
		document.getElementById("CurProcess").value = "������/����/�޶�";
	}
	
	//unlockScreen('lkscreen');
}

function nextProcess()
{
	var tGo = tCurrentProcess + 1;
	if(tGo<=tMaxProcess)
	{
		onRiskTabChange(tGo)
	}
	else
	{
		 alert('�Ѿ������һ��');
		 return false;
	}
}

function preProcess()
{
	var tGo = tCurrentProcess - 1;
	if(tGo>=1)
	{
		onRiskTabChange(tGo)
	}
	else
	{
		 alert('�Ѿ��ǵ�һ��');
		 return false;
	}
}

function endProcess()
{
	//CalCode
	//var tCalCode = .document.getElementById("CalCode").value
	
	var tExeCommand = "";
	if(jRuleInputName!=null&&jRuleInputName!="")
	{
		try{
		 tExeCommand = "top.opener.document.getElementById('"+jRuleInputName+"').value='"+jRuleName+"'";
		 eval(tExeCommand);
		}
		catch(e)
		{
		}
	 //tExeCommand = "top.opener.document.getElementById('CalCode').value='"+jRuleName+"'";
	//alert(tExeCommand);
	
	//alert(top.opener.document.getElementById("CalCode").value);
	}
	 top.close();
	 top.opener.focus();
}

function deploy()
{
	
	document.getElementById("BtnFlag").value = "7";
	submitForm();
	
}

function unDeploy()
{
	
	document.getElementById("BtnFlag").value = "z";
	submitForm();
	
}

function submitForm()
{
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm1.action="./IbrmsPDAlgoDefiSave.jsp";
	fm1.submit();		
}

function afterSubmit(FlagStr, content) {
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var showStr = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ showStr;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	onRiskTabChange('1');
}

function queryRuleState()
{
	//test
	var tRuleName = document.getElementById("RuleName").value;
	//tRuleName = 'test';
//	var tSQL_State = "select 'LRTemplatet' from lrtemplatet where rulename ='"+tRuleName+"' " 
//	               + " union  "
//	               + "select 'LRTemplate' from lrtemplate where rulename ='"+tRuleName+"' ";
	var sqlid2="IbrmsPDAlgoDefiSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(jRuleName);//ָ������Ĳ���
		mySql2.addSubPara(jRuleName);//ָ������Ĳ���
	
	  var tSQL_State=mySql2.getString();	
	var tRuleState = easyExecSql(tSQL_State);               
	//alert("tRuleState.length:tRuleState"+tRuleState.length+":"+tRuleState);      
	
	var tRuleStateName = "LRTemplate";
	if(tRuleState)
	{
	
		if(tRuleState.length<1)
		{
	    	tRuleStateName = "LRTemplatet";   
		}
		else if(tRuleState.length==1)
		{
			tRuleStateName=tRuleState[0][0];
			//document.getElementById("RuleState").value = tRuleState[0][0];
		}
		document.getElementById("RuleState").value = tRuleStateName;
		if(tRuleStateName=='LRTemplate')
		{
			tRuleStateName = "�ѷ���";
		}
		else
		{
			tRuleStateName = "δ����";
		}
		document.getElementById("RuleStateCN").value = tRuleStateName;
	}
	else
	{
		document.getElementById("RuleStateCN").value = "δ����";
		document.getElementById("RuleState").value = "";
	}
	
}

//tongmeng 2011-03-02 add
//֧�ֹ�����ģ�嵼��
function downloadTemplate()
{
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm1.action="./RuleResultDealExcelSave.jsp";
	
	fm1.submit();
	
}

function ImportExcel()
{
  if(fmResult.all("FileName").value == "")
  {
  	alert("����ѡ����Ҫ�ϴ����ļ�");
  	return;
  }
  //alert("document.getElementById('TempalteID').value:"+document.getElementById('TempalteID').value);
  document.getElementById('UploadTempalteID').value = document.getElementById('TempalteID').value;
  fmResult.action="./RuleResultImportExcelSave.jsp?UploadTempalteID="+document.getElementById('UploadTempalteID').value+"&ImportType=All";
  
  
  
  var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C";
  urlStr += "&ajaxflag=true&ajaxurl=ibrms/IBRMSShowProgress.jsp&params=rulename:"+jRuleName+"::action:all";
  urlStr += "&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();

  fmResult.submit();
}

function ImportExcelAdd()
{
  if(fmResult.all("FileName").value == "")
  {
  	alert("����ѡ����Ҫ�ϴ����ļ�");
  	return;
  }
  //alert("document.getElementById('TempalteID').value:"+document.getElementById('TempalteID').value);
  document.getElementById('UploadTempalteID').value = document.getElementById('TempalteID').value;
  fmResult.action="./RuleResultImportExcelSave.jsp?UploadTempalteID="+document.getElementById('UploadTempalteID').value+"&ImportType=Add";
  
  
  
  var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C";
   urlStr += "&ajaxflag=true&ajaxurl=ibrms/IBRMSShowProgress.jsp&params=rulename:"+jRuleName+"::action:add";
  urlStr += "&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();

  fmResult.submit();
}


function downloadData()
{
	var tTemplateID = document.getElementById("TempalteID").value;
	fmResult.action = "./MakeRuleDataDisk.jsp?templateID="+tTemplateID;
	fmResult.submit();
}
