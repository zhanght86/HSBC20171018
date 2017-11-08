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



//当切换分页时,处理相关业务逻辑
function onRiskTabChange(tPage)
{
	queryRuleState();
	tCurrentProcess = tPage;
	//alert(tCurrentProcess);
	//查询LRtemplatet表
	//
	var riskcode="";
	try{riskcode=fm1.all("riskcode").value;}catch(ex){}
	var RuleTemplateName = document.getElementById("RuleState").value;
	//var tSQL = "select ID from LRTemplatet where rulename='"+jRuleName+"' "
	//         + " union "
	//         + " select ID from LRTemplate where rulename='"+jRuleName+"' ";
		var sqlid1="IbrmsPDAlgoDefiSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(jRuleName);//指定传入的参数
		mySql1.addSubPara(jRuleName);//指定传入的参数
	
	  var strSql=mySql1.getString();	
	  
	var tLRTemplate_ID = easyExecSql(strSql);
	
//	alert(tLRTemplate_ID);
//	alert(tPage);
		//处理tab的切换
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
		//规则信息查询
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
		document.getElementById("CurProcess").value = "规则信息";
		divRiskCalDetail.style.display="";
		
	}
	if(tPage=='2')
	{
		if(RuleTemplateName=='LRTemplate')
		{
			alert('规则已经发布,不允许定义/修改!');
			onRiskTabChange(1);
			return;
		}
		
		tab1c.style.display="none";
		tab2c.style.display="";
		tab3c.style.display="none";
		tab4c.style.display="none";
		//tongmeng 2011-02-15 modify
		//判断是否已经保存过.如果保存过的话,显示算法明细信息,如果没有保存,不显示
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
    	
    	document.getElementById("CurProcess").value = "规则定义/修改";
	}
	if(tPage=='3')
	{
		if(RuleTemplateName=='LRTemplate')
		{
			alert('规则已经发布,不允许测试!');
			onRiskTabChange(1);
			return;
		}
		if(RuleTemplateName=="")
		{
			  alert('规则未发布,不允许测试!');
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
	  
	  document.getElementById("CurProcess").value = "规则测试";
	}
	if(tPage=='4')
	{
		if(tLRTemplate_ID==null||typeof(tLRTemplate_ID)=="undefined"||tLRTemplate_ID=='null')
		{
			//如果规则未保存,不需要进入发布标签
			alert('请在规则保存后再进行发布!');
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
		document.getElementById("CurProcess").value = "规则导入/发布/修订";
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
		 alert('已经是最后一步');
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
		 alert('已经是第一步');
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
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
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
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var showStr = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ showStr;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
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
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(jRuleName);//指定传入的参数
		mySql2.addSubPara(jRuleName);//指定传入的参数
	
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
			tRuleStateName = "已发布";
		}
		else
		{
			tRuleStateName = "未发布";
		}
		document.getElementById("RuleStateCN").value = tRuleStateName;
	}
	else
	{
		document.getElementById("RuleStateCN").value = "未定义";
		document.getElementById("RuleState").value = "";
	}
	
}

//tongmeng 2011-03-02 add
//支持规则表的模板导入
function downloadTemplate()
{
	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm1.action="./RuleResultDealExcelSave.jsp";
	
	fm1.submit();
	
}

function ImportExcel()
{
  if(fmResult.all("FileName").value == "")
  {
  	alert("请先选择需要上传的文件");
  	return;
  }
  //alert("document.getElementById('TempalteID').value:"+document.getElementById('TempalteID').value);
  document.getElementById('UploadTempalteID').value = document.getElementById('TempalteID').value;
  fmResult.action="./RuleResultImportExcelSave.jsp?UploadTempalteID="+document.getElementById('UploadTempalteID').value+"&ImportType=All";
  
  
  
  var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C";
  urlStr += "&ajaxflag=true&ajaxurl=ibrms/IBRMSShowProgress.jsp&params=rulename:"+jRuleName+"::action:all";
  urlStr += "&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();

  fmResult.submit();
}

function ImportExcelAdd()
{
  if(fmResult.all("FileName").value == "")
  {
  	alert("请先选择需要上传的文件");
  	return;
  }
  //alert("document.getElementById('TempalteID').value:"+document.getElementById('TempalteID').value);
  document.getElementById('UploadTempalteID').value = document.getElementById('TempalteID').value;
  fmResult.action="./RuleResultImportExcelSave.jsp?UploadTempalteID="+document.getElementById('UploadTempalteID').value+"&ImportType=Add";
  
  
  
  var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C";
   urlStr += "&ajaxflag=true&ajaxurl=ibrms/IBRMSShowProgress.jsp&params=rulename:"+jRuleName+"::action:add";
  urlStr += "&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
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
