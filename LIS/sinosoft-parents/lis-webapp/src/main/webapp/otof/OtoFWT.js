//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	fm.all('distill').disabled = false;
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
	}
}


function SubmitForm()
{
	if(fm.all('ManageCom').value=="")
	{
		alert("��ѡ����ȡ�Ĺ��������");
		return false;
	}

	if(fm.all('VouchNo').value!="")
	{
			var i = fm.all('VouchNo').value;
			if(!isNumeric(i))
	    {
				alert("¼���ƾ֤��ű���Ϊ����");
				return false;
		  }
		  if(i>=9 || i<=0)
		  {
				alert("�ý���ֻ��ȡƾ֤��Ϊ1-8��ҵ��ƾ֤");
				return ;
		  }	
	}
	
	if(fm.all('Bdate').value=="" || fm.all('Edate').value=="")
	{
		alert("��¼����ֹ���ڣ�");
		return false;
	}
	
	fm.Opt.value="WT";
  var sdate = fm.all('Bdate').value;
	var edate = fm.all('Edate').value;
	if(dateDiff(sdate,edate,"D")>1)
	{
	  	if(window.confirm("��������ȡһ������ݣ���ȷ��������ȡ��?"))
	  	{	
	       fm.all('distill').disabled = true;
	       fm.submit();
	  	}
	}
	else
	{
	   fm.all('distill').disabled = true;
	   fm.submit();
	  		
	}
}



