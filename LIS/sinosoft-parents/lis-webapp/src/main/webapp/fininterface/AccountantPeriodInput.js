var showInfo;
var mDebug="1";
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

//Click�¼������������ͼƬʱ�����ú���
function submitForm()
{
	 
  if (!beforeSubmit()) //beforeSubmit()����
  {
  	return false;
  }
  fm.OperateType.value = "INSERT||MAIN";
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.action = './AccountantPeriodSave.jsp';
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
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
    fm.OperateType.value = "";
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    if(fm.OperateType.value == "DELETE||MAIN")
    {
    	document.all('Year').value = '';
    	document.all('Month').value = '';  
  		document.all('StartDay').value = '';
    	document.all('EndDay').value='';           
    	document.all('State').value = '';
    	document.all('StateName').value = ''; 
    	
    	document.all('Year').readOnly = false;
    }
    fm.OperateType.value = "";
  }      
}



//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
	if (!beforeSubmit()) //beforeSubmit()����
  {
  	return false;
  }
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
   {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './AccountantPeriodSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ�����޸Ĳ�����");
  }
}
//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //fm.OperateType.value="QUERY";
  //fm.CertifyCode_1.value = "";
  window.open("./FrameAccountantPeriodQuery.jsp");
}

function deleteClick()
{
	if((fm.Year.value=="")||(fm.Year.value=="null"))
  {
    alert("�����ϢΪ�գ�����");
    return false;
  }
  if((fm.Month.value=="")||(fm.Month.value=="null"))
  {
    alert("�¶���ϢΪ�գ�����");
    return false;
  }
  if (confirm("��ȷʵҪɾ���ü�¼��"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ�����������Ľ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './AccountantPeriodSave.jsp';
    document.getElementById("fm").submit();//�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("���Ѿ�ȡ�����޸Ĳ�����");
  }
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('Year').value = arrResult[0][0];
		document.all('Month').value = arrResult[0][1];
		document.all('StartDay').value = arrResult[0][2];
		document.all('EndDay').value = arrResult[0][3];
		document.all('Operator').value = arrResult[0][6];
		document.all('State').value = arrResult[0][4];
		showCodeName(); 
		document.all('Year').readOnly = true;
	}
}

function beforeSubmit()
{
	if((fm.Year.value=="")||(fm.Year.value=="null"))
  {
    alert("����¼����ȣ�����");
    return false;
  }
  if((fm.Month.value=="")||(fm.Month.value=="null"))
  {
    alert("����¼���¶ȣ�����");
    return false;
  }
  if((fm.StartDay.value=="")||(fm.StartDay.value=="null"))
  {
  	alert("��������������ʼ���ڣ�");
  	return false;
  }
  if((fm.EndDay.value=="")||(fm.EndDay.value=="null"))
  {
  	alert("�����������Ľ������ڣ�");
  	return false;
  }
  if((fm.State.value==""||fm.State.value=="null"))
  {
  	alert("������������״̬��");
  	return false;
  }
  if(trim(document.all('Year').value).length!=4)
	{  
  	alert("��������λ���ֵ���ȣ�");
  	return false;
	}
 if(trim(document.all('Month').value).length!=2)
	{  
  	alert("��������λ���ֵ��¶ȣ�");
  	return false;
	}
	if(fm.EndDay.value <= fm.StartDay.value)
	{
		alert("�¶�ֹ�ڲ��ô��ڻ�����¶����ڣ�");
		return false;
	}
  return true;
}

function resetAgain()
{
			document.all('Year').value = '';
    	document.all('Month').value = '';  
  		document.all('StartDay').value = '';
    	document.all('EndDay').value='';           
    	document.all('State').value = '';
    	document.all('StateName').value = ''; 
    	
    	document.all('Year').readOnly = false;
}