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
  if(document.all('DBType').value =="ORACLE")
  {
 	 fm.OperateType.value = "INSERT||MAIN1";
 	}
 	if(document.all('DBType').value =="INFORMIX")
  {
 	 fm.OperateType.value = "INSERT||MAIN2";
 	}
 	if(document.all('DBType').value =="SQLSERVER")
  {
 	 fm.OperateType.value = "INSERT||MAIN3";
 	}
 	if(document.all('DBType').value =="WEBLOGICPOOL")
  {
 	 fm.OperateType.value = "INSERT||MAIN4";
 	}
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
  fm.action = './FIDataBaseLinkSave.jsp';
  document.getElementById("fm").submit(); //�ύ
}

function resetAgain()
{
	document.all('InterfaceCode').value = '';
  	document.all('InterfaceName').value = '';
  	document.all('DBType').value = '';
  	document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
    document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
    
    document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
    
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
    
     document.all('InterfaceCode').readOnly = false;
   	 ORACLEdiv.style.display='none';
		 INFORMIXdiv.style.display='none'; 
		 SQLSERVERdiv.style.display='none';
		 WEBLOGICPOOLdiv.style.display='none';
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
	var iHeight=350;     //�������ڵĸ߶�; 
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
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    if( fm.OperateType.value == "DELETE||MAIN1"||fm.OperateType.value == "DELETE||MAIN2"||fm.OperateType.value == "DELETE||MAIN3"||fm.OperateType.value == "DELETE||MAIN4")
    {
    document.all('InterfaceCode').value = '';
  	document.all('InterfaceName').value = '';
  	document.all('DBType').value = '';
  	document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
    document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
    
    document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
    
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
    
     document.all('InterfaceCode').readOnly = false;
   	 ORACLEdiv.style.display='none';
		 INFORMIXdiv.style.display='none'; 
		 SQLSERVERdiv.style.display='none';
		 WEBLOGICPOOLdiv.style.display='none';
    }
    fm.OperateType.value = "";
  }
  	
}


//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
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
   	if(document.all('DBType').value =="ORACLE")
  	{
 	 	fm.OperateType.value = "UPDATE||MAIN1";
 		}
 		if(document.all('DBType').value =="INFORMIX")
  	{
 		fm.OperateType.value = "UPDATE||MAIN2";
 		}
 		if(document.all('DBType').value =="SQLSERVER")
  	{
 	 	fm.OperateType.value = "UPDATE||MAIN3";
 		}
 		if(document.all('DBType').value =="WEBLOGICPOOL")
  	{
 	 	fm.OperateType.value = "UPDATE||MAIN4";
 		}
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

    fm.action = './FIDataBaseLinkSave.jsp';
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
  showInfo=window.open("./FrameFIDataBaseLinkQuery.jsp");
}

function deleteClick()
{
	if((document.all('InterfaceCode').value=="")||(document.all('InterfaceCode').value=="null"))
  {
    alert("����¼��ӿڱ��룡");
    return ;
  }
  if (confirm("��ȷʵҪɾ���ü�¼��"))
  {
  	if(document.all('DBType').value =="ORACLE")
  	{
 	 	fm.OperateType.value = "DELETE||MAIN1";
 		}
 		if(document.all('DBType').value =="INFORMIX")
  	{
 		fm.OperateType.value = "DELETE||MAIN2";
 		}
 		if(document.all('DBType').value =="SQLSERVER")
  	{
 	 	fm.OperateType.value = "DELETE||MAIN3";
 		}
 		if(document.all('DBType').value =="WEBLOGICPOOL")
  	{
 	 	fm.OperateType.value = "DELETE||MAIN4";
 		}
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

    fm.action = './FIDataBaseLinkSave.jsp';
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
		document.all('InterfaceCode').value = arrResult[0][0];
		document.all('InterfaceName').value = arrResult[0][1];
		document.all('DBType').value = arrResult[0][2];
		
		if(document.all('DBType').value =="ORACLE")
  	{		
  		ORACLEdiv.style.display='';
		 INFORMIXdiv.style.display='none'; 
		 SQLSERVERdiv.style.display='none';
		 WEBLOGICPOOLdiv.style.display='none';
		 
		 document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
    
    document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
    
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
    
		document.all('IP1').value = arrResult[0][3];
		document.all('Port1').value = arrResult[0][4];
		document.all('DBName1').value = arrResult[0][5];
		document.all('ServerName1').value = arrResult[0][6];
		document.all('UserName1').value = arrResult[0][7];
		document.all('PassWord1').value = arrResult[0][8];
		}
		if(document.all('DBType').value =="INFORMIX")
  	{		
  		ORACLEdiv.style.display='none';
		 INFORMIXdiv.style.display=''; 
		 SQLSERVERdiv.style.display='none';
		 WEBLOGICPOOLdiv.style.display='none';
		 
		 document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
    document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
    
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
    
		document.all('IP2').value = arrResult[0][3];
		document.all('Port2').value = arrResult[0][4];
		document.all('DBName2').value = arrResult[0][5];
		document.all('ServerName2').value = arrResult[0][6];
		document.all('UserName2').value = arrResult[0][7];
		document.all('PassWord2').value = arrResult[0][8];
		}
		if(document.all('DBType').value =="SQLSERVER")
  	{
  		 ORACLEdiv.style.display='none';
		 INFORMIXdiv.style.display='none'; 
		 SQLSERVERdiv.style.display='';
		 WEBLOGICPOOLdiv.style.display='none';
		 
		 document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
		 document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
 
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
    
		document.all('IP3').value = arrResult[0][3];
		document.all('Port3').value = arrResult[0][4];
		document.all('DBName3').value = arrResult[0][5];
		document.all('ServerName3').value = arrResult[0][6];
		document.all('UserName3').value = arrResult[0][7];
		document.all('PassWord3').value = arrResult[0][8];
		}
		if(document.all('DBType').value =="WEBLOGICPOOL")
  	{
  		ORACLEdiv.style.display='none';
		 INFORMIXdiv.style.display='none'; 
		 SQLSERVERdiv.style.display='none';
		 WEBLOGICPOOLdiv.style.display='';
		 
		  document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
		 document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
    
     document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
    
		document.all('IP4').value = arrResult[0][3];
		document.all('Port4').value = arrResult[0][4];
		document.all('DBName4').value = arrResult[0][5];
		document.all('ServerName4').value = arrResult[0][6];
		document.all('UserName4').value = arrResult[0][7];
		document.all('PassWord4').value = arrResult[0][8];
		}
		showCodeName(); 
		document.all('InterfaceCode').readOnly = true;
	}
}

function afterCodeSelect( cName, Filed)
{   
	if(fm.DBType.value=='ORACLE')
	{
		 ORACLEdiv.style.display='';
		 INFORMIXdiv.style.display='none'; 
		 SQLSERVERdiv.style.display='none';
		 WEBLOGICPOOLdiv.style.display='none';
		 
		 document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
    
    document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
    
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
	}
			
  if(fm.DBType.value=='INFORMIX')
  {
		 ORACLEdiv.style.display='none';
		 INFORMIXdiv.style.display=''; 
		 SQLSERVERdiv.style.display='none';
		 WEBLOGICPOOLdiv.style.display='none';
		 
		 document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
    document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
    
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
	}			
	
	if(fm.DBType.value=='SQLSERVER')
  {
		 ORACLEdiv.style.display='none';
		 INFORMIXdiv.style.display='none'; 
		 SQLSERVERdiv.style.display='';
		 WEBLOGICPOOLdiv.style.display='none';
		 
		 document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
		 document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
 
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
	}	
	
	if(fm.DBType.value=='WEBLOGICPOOL')
  {
		 ORACLEdiv.style.display='none';
		 INFORMIXdiv.style.display='none'; 
		 SQLSERVERdiv.style.display='none';
		 WEBLOGICPOOLdiv.style.display='';
		 
		 document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
		 document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
    
     document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
	}	

}

function beforeSubmit()
{
	if((document.all('InterfaceCode').value=="")||(document.all('InterfaceCode').value=="null"))
  {
    alert("����¼��ӿڱ��룡");
    return ;
  }
  if((document.all('DBType').value=="")||(document.all('DBType').value=="null"))
  {
    alert("����¼�����ݿ����ͣ�");
    return ;
  }
  if(document.all('DBType').value =="ORACLE")
  {
  	if((document.all('IP1').value=="")||(document.all('IP1').value==null))
  	{
  		alert("������IP��ַ��");
  		return;
  	}
  	if((document.all('Port1').value=="")||(document.all('Port1').value==null))
  	{
  		alert("������˿ںţ�");
  		return;
  	}
  	if((document.all('DBName1').value=="")||(document.all('DBName1').value==null))
  	{
  		alert("���������ݿ����ƣ�");
  		return;
  	}
  	if((document.all('UserName1').value=="")||(document.all('UserName2').value==null))
  	{
  		alert("�������û�����");
  		return;
  	}
  	if((document.all('PassWord1').value=="")||(document.all('PassWord2').value==null))
  	{
  		alert("���������룡");
  		return;
  	}
  }
  if(document.all('DBType').value =="INFORMIX")
  {
  	if((document.all('IP2').value=="")||(document.all('IP2').value==null))
  	{
  		alert("������IP��ַ��");
  		return;
  	}
  	if((document.all('Port2').value=="")||(document.all('Port2').value==null))
  	{
  		alert("������˿ںţ�");
  		return;
  	}
  	if((document.all('DBName2').value=="")||(document.all('DBName2').value==null))
  	{
  		alert("���������ݿ����ƣ�");
  		return;
  	}
  	if((document.all('ServerName2').value=="")||(document.all('ServerName2').value==null))
  	{
  		alert("������������ƣ�");
  		return;
  	}
  	if((document.all('UserName2').value=="")||(document.all('UserName2').value==null))
  	{
  		alert("�������û�����");
  		return;
  	}
  	if((document.all('PassWord2').value=="")||(document.all('PassWord2').value==null))
  	{
  		alert("���������룡");
  		return;
  	}
  }
  if(document.all('DBType').value =="SQLSERVER")
  {
  	if((document.all('IP3').value=="")||(document.all('IP3').value==null))
  	{
  		alert("������IP��ַ��");
  		return;
  	}
  	if((document.all('Port3').value=="")||(document.all('Port3').value==null))
  	{
  		alert("������˿ںţ�");
  		return;
  	}
  	if((document.all('DBName3').value=="")||(document.all('DBName3').value==null))
  	{
  		alert("���������ݿ����ƣ�");
  		return;
  	}
  	if((document.all('UserName3').value=="")||(document.all('UserName2').value==null))
  	{
  		alert("�������û�����");
  		return;
  	}
  	if((document.all('PassWord3').value=="")||(document.all('PassWord2').value==null))
  	{
  		alert("���������룡");
  		return;
  	}
  }
  if(document.all('DBType').value =="WEBLOGICPOOL")
  {
  	if((document.all('IP4').value=="")||(document.all('IP3').value==null))
  	{
  		alert("������IP��ַ��");
  		return;
  	}
  	if((document.all('Port4').value=="")||(document.all('Port3').value==null))
  	{
  		alert("������˿ںţ�");
  		return;
  	}
  	if((document.all('DBName4').value=="")||(document.all('DBName4').value==null))
  	{
  		alert("���������ݿ����ƣ�");
  		return;
  	}
  }
  return true;
}