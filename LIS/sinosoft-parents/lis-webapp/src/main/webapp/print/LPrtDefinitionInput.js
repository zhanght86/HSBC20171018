var mOperate = "";
var showInfo;
window.onfocus = myonfocus;
function myonfocus()
{
	if(showInfo != null)
	{
		try
		{
			showInfo.focus();
		}
		catch (ex)
		{
			showInfo = null;
		}
	}
}
function submitForm()
{
	if(verifyInput2() == false )
	{
		return false;
	}
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all("fmtransact").value = mOperate;
	document.getElementById("fm").submit();
}
function showSubmitFrame(cDebug)
{
	if(cDebug == "1")
	{
		parent.fraMain.rows = "0,0,50,82,*";
	}
	else
	{
		parent.fraMain.rows = "0,0,0,82,*";
	}
}
function afterSubmit(FlagStr,content)
{
	showInfo.close();
	if(FlagStr == "Fail")
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");/
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
}
function resetForm()
{
	try
	{
		fm.reset();
		initForm();
    }
	catch(ex)
	{
		alert("-->InitForm�����з����쳣:��ʼ���������!");
	} 
}
function addClick()
{  
	mOperate = "INSERT||MAIN";
	if(document.all("PrintID").value != "")
	{
		alert("�Ѵ��ڴ�ӡ���룬�����ã�");
		return;
	}
	submitForm();
}
function deleteClick()
{
	if( verifyInput2() == false )
	{	  
		return false;
	}
	if(confirm("��ȷʵ��ɾ���ü�¼��?"))
	{
		var i = 0;
		var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus(); 
		fm.fmtransact.value = "DELETE||MAIN";
		document.getElementById("fm").submit(); //�ύ
		initForm();
	}
	else
	{
		alert("��ȡ����ɾ��������");
	}
}
function updateClick()
{
	if(verifyInput2() == false)
	{
		return false;
	}
	if(confirm("��ȷʵ���޸ĸü�¼��"))
	{
		mOperate = "UPDATE||MAIN";
		submitForm();
	}
	else
	{
		alert("��ȡ�����޸Ĳ�����");
	}
}
function queryClick()
{
	mOperate = "QUERY||MAIN";
	showInfo = window.open("./LPrtDefinitionQuery.jsp");
}
function afterQuery(arrQueryResult)
{
	var arrResult = new Array();
	if(arrQueryResult != null)
	{
		var sqlId1 = "LPrtDefinitionInputSql1";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtDefinitionInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlId1);
		mySql1.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
	 	arrResult = easyExecSql(mySql1.getString());
	 	document.all('PrintID').value = arrResult[0][0];
	 	document.all('PrintName').value = arrResult[0][1];
	 	document.all('PrintObject').value = arrResult[0][2];
	 	document.all('PrintObjectName').value = arrResult[0][3];
	 	document.all('PrintType').value = arrResult[0][4];
	 	document.all('PrintTypeName').value = arrResult[0][5];
	 	document.all('LanguageType').value = arrResult[0][6];
	 	document.all('LanguageTypeName').value = arrResult[0][7];
	 	document.all('PrintID').readOnly = true;
	}
}




