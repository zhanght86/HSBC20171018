//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
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

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if (!verifyInput())
	{
		return;
	}
  var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	lockButton();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  unlockButton();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //ִ����һ������
    //fileDownload();
  }
}

//������ť
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
//������ť
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

//�����嵥
function PrintTemp()
{
	fm.PrintType.value = "Temp"; 
	submitForm();
}

//Ԥ���嵥
function PrintAdvance()
{
	fm.PrintType.value = "Advance"; 
	submitForm();
}

//���������嵥
function PrintPrem()
{
	fm.PrintType.value = "Prem"; 
	submitForm();
}

//���֧���嵥
function PrintClaim()
{
	fm.PrintType.value = "Claim"; 
	submitForm();
}

//����Ӧ���嵥
function PrintEdorDue()
{
	fm.PrintType.value = "EdorDuePay"; 
	submitForm();
}

//��ȡ�����嵥
function PrintLiveGet()
{
	fm.PrintType.value = "LiveGet"; 
	submitForm();
}

//����Ӧ���嵥
function PrintOtherDue()
{
	fm.PrintType.value = "OtherDuePay"; 
	submitForm();
}

//ҵ��ʵ���嵥
function PrintActuPay()
{
	fm.PrintType.value = "ActuPay"; 
	submitForm();
}

//��ȫ�շ��嵥
function PrintEdorGet()
{
	fm.PrintType.value = "EdorGet"; 
	submitForm();
}

//Ӧ������嵥
function PrintDueYE()
{
	fm.PrintType.value = "DuePayYE"; 
	submitForm();
}

//Ԥ������嵥
function PrintAdvanceGetYE()
{	
	fm.PrintType.value = "AdvanceGetYE"; 
	submitForm();
}
//����ί��
function PrintGLFY()
{
	fm.PrintType.value = "GLFY"; 
	submitForm();
}
//�����������嵥
function PrintAirPol()
{
	fm.PrintType.value = "AirPol"; 
	submitForm();
}
//��ʾfrmSubmit��ܣ���������
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

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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
//ѡ������ļ�����
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
  	alert("�޷���ñ�������·����");
  	return;
  }

  document.all('Url').value = fPath;
  document.all('fmtransact').value = "download";
  document.getElementById("fm").submit(); //�ύ
}

//ѡ������ļ�����
function downAfterSubmit(cfilePath,cflag) 
{
	var comcode = document.all('ManageCom').value;
	if(comcode == null || comcode == "")
	{
	  alert("����¼�����������룡");
	  return false;	
	}
	
	var listIndex = document.all('ListType').value;
	if(listIndex == null || listIndex == "")
	{
	  alert("����ѡ�����ص��嵥���ͣ�");
	  return false;	
	}
	
//	var ListItem = new Array('Temp','Advance','Prem','Claim','EdorDuePay','LiveGet','OtherDuePay',
//									'ActuPay','EdorGet','DuePayYE','AdvanceGetYE','AirPol');
	var ListItem = new Array('Temp', 'Advance', 'Prem', 'Claim','EdorDuePay', 'LiveGet', 'OtherDuePay', 'ActuPay', 
				 'DuePayYE','AdvanceGetYE', 'AirPol','GLFY');

	fName = ListItem[listIndex] + "List_" + comcode + "_" + document.all('Operator').value + ".xls.z";
	
  fileUrl.href = cfilePath + fName;
  //������
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
