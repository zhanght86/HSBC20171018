var showInfo;


function OtoFReYCom()
{
			var comcode = fm.all('ManageCom').value;
			var bdate = fm.all('Bdate').value;
	    var edate = fm.all('Edate').value;
	    
		if(comcode == null || comcode == "")
		{
			alert("��¼����������");
			return;
		}

	  if (bdate==""||bdate==null || edate=="" ||edate==null)
	  {
	      alert("��¼����ֹ���ڣ�!");
	      return ;
	  }

	if(fm.AccountDate.value=="")
	  {
	  	    alert("����¼��������ڣ�");
		    return false;	
	  }


  
	  if (confirm("��ȷʵ������ò���ƾ֤��?"))
	  {
			  var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
			  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
			  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			  fm.Opt.value="YComm";
			  fm.submit(); 
	  }
}

function OtoFRePrem()
{
			var comcode = fm.all('ManageCom').value;
			var bdate = fm.all('Bdate').value;
	    var edate = fm.all('Edate').value;
	    
		if(comcode == null || comcode == "")
		{
			alert("��¼����������");
			return;
		}

	  if (bdate==""||bdate==null || edate=="" ||edate==null)
	  {
	      alert("��¼����ֹ���ڣ�!");
	      return ;
	  }

	if(fm.AccountDate.value=="")
	  {
	  	    alert("����¼��������ڣ�");
		    return false;	
	  }


  
	  if (confirm("��ȷʵ������ò���ƾ֤��?"))
	  {
			  var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
			  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
			  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			  fm.Opt.value="Prem";
			  fm.submit(); 
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