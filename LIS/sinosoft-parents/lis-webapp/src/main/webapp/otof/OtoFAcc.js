var showInfo;
var turnPage = new turnPageClass(); 

function easyprint()
{
	if(document.all('bDate').value=="")
	{
		alert("��ѡ����ʼ����");
		return ;
		}
			if(document.all('eDate').value=="")
	{
		alert("��ѡ����ֹ����");
		return ;
		}
			if(document.all('RiskCode').value=="")
	{
		alert("��ѡ�����ִ���");
		return ;
		}
			if(document.all('ManageCom').value=="")
	{
		alert("��ѡ��������");
		return ;
		}
		var bDate=document.all('bDate').value;
		var eDate=document.all('eDate').value;
		if(dateDiff(bDate,eDate,"D")<0)
		{
			 alert("��ʼ���ڲ��ܴ�����ֹ���ڣ�������ѡ��");
		   return ;
			}
	  fm.fmAction.value="Print";
	  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.getElementById("fm").submit(); //�ύ
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
			var iHeight=250;     //�������ڵĸ߶�; 
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

	if(document.all('bDate').value=="")
	{
		alert("��ѡ����ʼ����");
		return ;
		}
			if(document.all('eDate').value=="")
	{
		alert("��ѡ����ֹ����");
		return ;
		}

			if(document.all('ManageCom').value=="")
	 {
		alert("��ѡ��������");
		return ;
		}
		
		var bDate=document.all('bDate').value;
		var eDate=document.all('eDate').value;
		if(dateDiff(bDate,eDate,"D")>1)
    {
  	if(window.confirm("��������ȡһ������ݣ���ȷ��������ȡ��?"))
  	{
  		  fm.fmAction.value="Certifi";
        document.all('distill').disabled = true;
        document.getElementById("fm").submit();
  		}
  	}
  		else
  			{
  			fm.fmAction.value="Certifi";
        document.all('distill').disabled = true;
        document.getElementById("fm").submit();
  				}
  	

}

