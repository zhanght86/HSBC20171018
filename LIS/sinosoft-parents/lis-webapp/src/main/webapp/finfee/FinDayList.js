//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//提交，保存按钮对应操作
function submitForm()
{
	if (!verifyInput())
	{
		return;
	}
  var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	lockButton();
  document.getElementById("fm").submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  unlockButton();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //执行下一步操作
    //fileDownload();
  }
}

//锁定按钮
function lockButton()
{
  document.all('fmtransact').value = "create";

	document.all('TempPrint').disabled = true;
	document.all('AdvancePrint').disabled = true;
	document.all('PremPrint').disabled = true;
	document.all('ClaimPrint').disabled = true;
	document.all('EdorDuePrint').disabled = true;
	document.all('LiveGetPrint').disabled = true;
	document.all('OtherDuePrint').disabled = true;
	document.all('ActuPayPrint').disabled = true;
//	document.all('EdorGetPrint').disabled = true;
	document.all('DueYEPrint').disabled = true;
	document.all('AdvanceGetYEPrint').disabled = true;
	document.all('AirPolPrint').disabled=true;
}
//解锁按钮
function unlockButton()
{
	document.all('TempPrint').disabled = false;
	document.all('AdvancePrint').disabled = false;
	document.all('PremPrint').disabled = false;
	document.all('ClaimPrint').disabled = false;
	document.all('EdorDuePrint').disabled = false;
	document.all('LiveGetPrint').disabled = false;
	document.all('OtherDuePrint').disabled = false;
	document.all('ActuPayPrint').disabled = false;
//	document.all('EdorGetPrint').disabled = false;
	document.all('DueYEPrint').disabled = false;
	document.all('AdvanceGetYEPrint').disabled = false;
	document.all('AirPolPrint').disabled=false;
}

//暂收清单
function PrintTemp()
{
	fm.PrintType.value = "Temp"; 
	submitForm();
}

//预收清单
function PrintAdvance()
{
	fm.PrintType.value = "Advance"; 
	submitForm();
}

//保费收入清单
function PrintPrem()
{
	fm.PrintType.value = "Prem"; 
	submitForm();
}

//赔款支出清单
function PrintClaim()
{
	fm.PrintType.value = "Claim"; 
	submitForm();
}

//保费应付清单
function PrintEdorDue()
{
	fm.PrintType.value = "EdorDuePay"; 
	submitForm();
}

//领取给付清单
function PrintLiveGet()
{
	fm.PrintType.value = "LiveGet"; 
	submitForm();
}

//其他应付清单
function PrintOtherDue()
{
	fm.PrintType.value = "OtherDuePay"; 
	submitForm();
}

//业务实付清单
function PrintActuPay()
{
	fm.PrintType.value = "ActuPay"; 
	submitForm();
}

//保全收费清单
function PrintEdorGet()
{
	fm.PrintType.value = "EdorGet"; 
	submitForm();
}

//应付余额清单
function PrintDueYE()
{
	fm.PrintType.value = "DuePayYE"; 
	submitForm();
}

//预收余额清单
function PrintAdvanceGetYE()
{	
	fm.PrintType.value = "AdvanceGetYE"; 
	submitForm();
}
//健康委托
function PrintGLFY()
{
	fm.PrintType.value = "GLFY"; 
	submitForm();
}
//冲消航意险清单
function PrintAirPol()
{
	fm.PrintType.value = "AirPol"; 
	submitForm();
}
//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}

var filePath;
//选择进行文件下载
function fileDownload() 
{
	//var strSql = "select SysVarValue from LDSysVar where SysVar = 'ShowListPath'";
	var mysql=new SqlClass();
	mysql.setResourceName("finfee.FinDayListSql");
	mysql.setSqlId("ShowListPath");
	mysql.addSubPara("1");
	//alert(mysql.getString());
	
	fPath = easyExecSql(mysql.getString());					//easyQueryVer3(strSql,1,1,1);
  if(fPath == null || fPath == "")
  {
  	alert("无法获得报表下载路径！");
  	return;
  }

  document.all('Url').value = fPath;
  document.all('fmtransact').value = "download";
  document.getElementById("fm").submit(); //提交
}

//选择进行文件下载
function downAfterSubmit(cfilePath,cflag) 
{
	var comcode = document.all('ManageCom').value;
	if(comcode == null || comcode == "")
	{
	  alert("必须录入管理机构代码！");
	  return false;	
	}
	
	var listIndex = document.all('ListType').value;
	if(listIndex == null || listIndex == "")
	{
	  alert("必须选择下载的清单类型！");
	  return false;	
	}
	
//	var ListItem = new Array('Temp','Advance','Prem','Claim','EdorDuePay','LiveGet','OtherDuePay',
//									'ActuPay','EdorGet','DuePayYE','AdvanceGetYE','AirPol');
	var ListItem = new Array('Temp', 'Advance', 'Prem', 'Claim','EdorDuePay', 'LiveGet', 'OtherDuePay', 'ActuPay', 
				 'DuePayYE','AdvanceGetYE', 'AirPol','GLFY');

	fName = ListItem[listIndex] + "List_" + comcode + "_" + document.all('Operator').value + ".xls.z";
	
  fileUrl.href = cfilePath + fName;
  //测试用
  //fileUrl.href = "/temp/" + fName;
  try
  {
  	fm.Down.disabled = true;
    fileUrl.click();
  	fm.Down.disabled = false;
  	document.all('ListType').value="";
  }
  catch(ex)
  {
    //fileUrl.click();
    alert(ex.message);
  }
}
