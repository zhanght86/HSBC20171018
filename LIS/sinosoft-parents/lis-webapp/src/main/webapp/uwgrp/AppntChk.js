//***********************************************
//�������ƣ�AppntChk.js
//�����ܣ�Ͷ���˲���
//�������ڣ�2002-11-23 17:06:57
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var cflag = "4";

/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit(FlagStr,content)
{
  //alert(FlagStr);
	if (FlagStr == "Fail" )
	{             
	  showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		//[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
	}
	if (FlagStr == "Succ")
	{
	  showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		//[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
		if (fm.SureFlag.value == "Union")
		{
			initForm();	
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}


// ��ѯ��ť

function initQuery() 
{
	fm.LoadFlag.value = LoadFlag;
	
	fm.action = "./AppntChkQuery.jsp";
	fm.submit();	
}

function sendCustomerUnionIssue()
{
 
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  	//[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.action =  "./AppntChkUnionQuestInputSave.jsp";
	fm.submit();	
}

function customerUnion()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
		alert( "����ѡ����ͬ��Ͷ����" );
		return;
	}	

//	divCustomerUnion.style.display="";
	
	var CustomerNo_OLD = OPolGrid.getRowColData(0, 1);
	var CustomerNo_NEW = PolGrid.getRowColData(selno, 1);
	var CustomerName = PolGrid.getRowColData(selno, 2);
	
	fm.CustomerNo_OLD.value = CustomerNo_OLD;
	fm.CustomerNo_NEW.value = CustomerNo_NEW;
	fm.CustomerName.value = CustomerName;
}

function customerUnionSubmit()
{

  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  	//[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fm.SureFlag.value = "Union";
	
	fm.action =  "./AppntChkCustomerUnionSave.jsp";
	fm.submit();	
}

function showCustomerUnionIssueSend()
{ 

	strSql =" select cont from lis.ldcodemod where codetype = 'Question' and code = '99' ";
	  
	var brr = easyExecSql(strSql );
	if ( brr )
	{
		brr[0][0]==null||brr[0][0]=='null'?'0':fm.CUIContent.value  = brr[0][0];		
	}	
	
	divCustomerUnionIssue.style.display = "";
	divBtCustomerUnion.style.display = "none";
	divBtCustomerUnionSendIssue.style.display = "";
	divSendNotice.style.display = "";

}

function showCustomerUnion()
{
	divCustomerUnionIssue.style.display = "none";
	divBtCustomerUnion.style.display = "";
	divBtCustomerUnionSendIssue.style.display = "none";	
}
function exchangeCustomerNo()
{
	var exchangeValue="";
	for(i = 0; i < fm.exchangeRadio.length; i++)
	{   
		if(fm.exchangeRadio[i].checked)
		{ 
			exchangeValue = fm.exchangeRadio[i].value;
			break;                         
		}
	}		

	if(exchangeValue == "1")
	{
		var selno = PolGrid.getSelNo()-1;
		if (selno <0)
		{
			alert( "����ѡ����ͬ��Ͷ����" );
			return;
		}	
			
		var CustomerNo_OLD = OPolGrid.getRowColData(0, 1);
		var CustomerNo_NEW = PolGrid.getRowColData(selno, 1);
		var CustomerName = PolGrid.getRowColData(selno, 2);
		
		fm.CustomerNo_OLD.value = CustomerNo_OLD;
		fm.CustomerNo_NEW.value = CustomerNo_NEW;
		
	}
	if(exchangeValue == "-1")
	{
		var selno = PolGrid.getSelNo()-1;
		if (selno <0)
		{
			alert( "����ѡ����ͬ��Ͷ����" );
			return;
		}	
			
		var CustomerNo_NEW = OPolGrid.getRowColData(0, 1);
		var CustomerNo_OLD = PolGrid.getRowColData(selno, 1);
		var CustomerName = PolGrid.getRowColData(selno, 2);
		
		fm.CustomerNo_OLD.value = CustomerNo_OLD;
		fm.CustomerNo_NEW.value = CustomerNo_NEW;
			
	}
}

function sendNotice()
{
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	
	fm.action =  "./AppntNoticeSave.jsp";
	fm.submit();	
	
}