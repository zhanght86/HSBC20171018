//Creator :���	
//Date :2008-08-21

var showInfo;
var mDebug="0";
var arrDataSet;
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null) //shwoInfo��ʲô��
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



function RulesVersionQuery()
{
	window.open("./FrameVersionRuleQuery.jsp");
}

function RulesVersionTraceQuery()
{
	var VersionNo = document.all('VersionNo').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��");
  	return;
  }
  showInfo=window.open("./FrameRulesVersionTraceQuery.jsp?VersionNo=" + VersionNo +"");
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('VersionNo').value = arrResult[0][0];
		document.all('StartDate').value = arrResult[0][1];
		document.all('EndDate').value = arrResult[0][2];
		document.all('VersionReMark').value = arrResult[0][3];
		document.all('VersionState').value = arrResult[0][5];
		
		document.all('Maintenanceno').value = '';
		document.all('MaintenanceReMark').value = '';
		document.all('MaintenanceState').value = '';
		document.all('TraceVersionNo').value = '';
	}
}

function afterQuery1(arrQueryResult)
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('Maintenanceno').value = arrResult[0][0];
		document.all('MaintenanceReMark').value = arrResult[0][3];
		document.all('MaintenanceState').value = arrResult[0][2];
		document.all('TraceVersionNo').value = arrResult[0][1];
	}
}

function addVersion()
{
	 if((fm.StartDate.value=="")||(fm.StartDate.value=="null"))
  {
    alert("����¼����Ч���ڣ�����");
    return false;
  }
  if((fm.VersionNo.value!=""))
	{
		alert("�汾����Ѵ��ڣ���ˢ��ҳ����ٽ��д˲�����")
		return false;
	}
	if((fm.VersionReMark.value=="")||(fm.VersionReMark.value=="null"))
  {
    alert("����¼��汾����������");
    return false;
  }
	if (confirm("��ȷʵ�������Ӱ汾�Ĳ�����?"))
  {
    fm.OperateType.value = "addVersion";
    var i = 0;
    var showStr="������Ӱ汾�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ���˱��β�����");
  }
}

function deleteVersion()
{
	if((fm.VersionNo.value=="")||(fm.VersionNo.value=="null"))
  {
    alert("�����Ƚ��а汾��Ϣ��ѯ��");
    return false;
  }
	if (confirm("��ȷʵ�����ɾ���汾�Ĳ�����?"))
  {
    fm.OperateType.value = "deleteVersion";
    var i = 0;
    var showStr="����ɾ���汾�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ���˱��β�����");
  }
}

function applyAmend()
{
	if((fm.VersionNo.value=="null")||(fm.VersionNo.value==''))
  {
  	alert("����¼��汾���");
  	return false;
  }
  if((fm.MaintenanceReMark.value=="null")||(fm.MaintenanceReMark.value==''))
  {
  	alert("��¼��ά������");
  	return false;
  }
  if((fm.Maintenanceno.value!=""))
	{
		alert("ά������Ѵ��ڣ���ˢ��ҳ����ٽ��д˲�����")
		return false;
	}
	if (confirm("��ȷ��Ҫ�ύ�����޸ĵ�������?"))
  {
    fm.OperateType.value = "applyAmend";
    var i = 0;
    var showStr="���������޸ģ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ���˱��β�����");
  }
}

function CompleteAmend()
{
	if((fm.VersionNo.value=="null")||(fm.VersionNo.value==''))
  {
  	alert("����¼��汾���");
  	return false;
  }
	if((fm.Maintenanceno.value=="null")||(fm.Maintenanceno.value==''))
  {
		alert("���Ƚ��в������汾�켣��ѯ");
  	return false;
  }
  if (confirm("��ȷ��Ҫ�ύ�����޸ĵ�������?"))
  {
    fm.OperateType.value = "CompleteAmend";
    var i = 0;
    var showStr="���������޸ģ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ���˱��β�����");
  }
}

function cancelAmend()
{
	if((fm.VersionNo.value=="null")||(fm.VersionNo.value==''))
  {
  	alert("������Ӱ汾������������汾ά���켣��ѯ");
  	return false;
  }
	if((fm.Maintenanceno.value=="null")||(fm.Maintenanceno.value==''))
  {
		alert("���Ƚ��в������汾�켣��ѯ");
  	return false;
  }

  if (confirm("��ȷ��Ҫ�ύ�����޸ĵ�������?"))
  {
    fm.OperateType.value = "cancelAmend";
    var i = 0;
    var showStr="���������޸ģ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ���˱��β�����");
  }
}

function afterSubmit( FlagStr, content )
{
	showInfo.close();	  
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		mOperate="";
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    mOperate="";
  }
  if(fm.OperateType.value = "cancelAmend")
  {
  	document.all('Maintenanceno').value = '';
  	document.all('TraceVersionNo').value = '';
  	document.all('MaintenanceState').value = '';
  	document.all('MaintenanceReMark').value = '';
  }
  if(fm.OperateType.value = "deleteVersion")
  {
  	document.all('VersionNo').value = '';
		document.all('StartDate').value = '';
		document.all('EndDate').value = '';
		document.all('VersionReMark').value = '';
		document.all('VersionState').value = '';
  	document.all('Maintenanceno').value = '';
  	document.all('TraceVersionNo').value = '';
  	document.all('MaintenanceState').value = '';
  	document.all('MaintenanceReMark').value = '';
  }
}