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
//�����ļ��������޸ļ�����
function fileUpdateClick()
{
	try
	{
		document.all("FilePath").style.display='';
		document.all("FilePath1").style.display='none';
		document.all("fileQueryClick").style.display='none';
	}
	catch(ex)
	{
		alert(ex.message);
	}
}
//�����ļ��������޸ļ���ʾ
function fileUpdateClick1()
{
	try
	{
		document.all("FilePath").style.display='none';
		document.all("FilePath1").style.display='';
		document.all("fileQueryClick").style.display='';
	}
	catch(ex)
	{
		alert(ex.message);
	}
}
function submitForm()
{
	if(beforeSubmit() == false )
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
function beforeSubmit()
{
	if(verifyInput2() == false)
	{
		return false;
	}
	if(verifyTemplete() == false )
	{
		return false;
	}
	return true;
}
function afterSubmit(FlagStr,content)
{
	showInfo.close();
	if(FlagStr == "Fail")
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
}
function addClick()
{  
	mOperate = "INSERT||MAIN";
	if (document.all("TempleteID").value !="" )
	{
		alert("�Ѵ��ڸô�ӡģ����룬�����ã�")
		return false;
	}
	if(document.all("FilePath").value =="")
	{
		alert("�ļ�����Ϊ�գ�");
		return false;
	}
	submitForm();
	fileUpdateClick1();
}

//У��ģ�������������ļ������Ƿ���ͬ
function verifyTemplete()
{
	var filePath = document.all("FilePath").value;
	var filePath1 = document.all("FilePath1").value;
	var filePath2;
	var tTempleteName = document.all("TempleteName").value;
	var tLanguage = document.all("Language").value;
	var	tTempleteType = document.all("TempleteType").value;
	var tDefaultType = document.all("DefaultType").value;
	var tTempleteID = document.all("TempleteID").value;
	if (filePath != "")
	{
		filePath2 = filePath;
	}
	else 
	{
		filePath2 = filePath1;
	}
	var fileLastName = filePath2.substring(filePath2.lastIndexOf(".") + 1);
	var templeteTypeName = document.all("TempleteTypeName").value;
	if(fileLastName!= templeteTypeName)
	{
		alert("ģ�������������ļ����Ͳ�����!");
		return false;
	}
	if(mOperate == "INSERT||MAIN")
	{	
		var tResult1 = new Array();
		var sqlid1 = "LPrtTempleteInputSql1";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtTempleteInputSql");
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tTempleteName);
		mySql1.addSubPara(tLanguage);
		mySql1.addSubPara(tTempleteType);
		tResult1 = easyExecSql(mySql1.getString());
		if(tResult1 != null)
		{
			if( tResult1[0][0] == tTempleteName && tResult1[0][1] == tLanguage 
					&& tResult1[0][2] == tTempleteType )
			{
				alert("��ӡģ���Ѵ���!");
				return false;
			}
		}
		else
		{
			var tResult2 = new Array();
			var sqlid2 = "LPrtTempleteInputSql2";
			var mySql2 = new SqlClass();
			mySql2.setResourceName("print.LPrtTempleteInputSql");
			mySql2.setSqlId(sqlid2);
			mySql2.addSubPara(tTempleteName);
			tResult2 = easyExecSql(mySql2.getString());
			if( tDefaultType == "Y"  && tResult2[0][0] != "0")
			{
				alert("�ô�ӡ��Ŀ�Ѵ���Ĭ��ģ��!");
				return false;
			}
		}
	}
	if(mOperate == "UPDATE||MAIN")
	{	
		var tResult4 = new Array();
		var sqlid4 = "LPrtTempleteInputSql4";
		var mySql4 = new SqlClass();
		mySql4.setResourceName("print.LPrtTempleteInputSql");
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tTempleteName);
		mySql4.addSubPara(tLanguage);
		mySql4.addSubPara(tTempleteType);
		mySql4.addSubPara(tTempleteID);
		tResult4 = easyExecSql(mySql4.getString());
		if(tResult4 != null)
		{	
			if( tResult4[0][0] == tTempleteName && tResult4[0][1] == tLanguage 
					&& tResult4[0][2] == tTempleteType )
			{
				alert("��ӡģ���Ѵ���!");
				return false;
			}
		}
		else
		{
			var tResult3 = new Array();
			var sqlid3 = "LPrtTempleteInputSql3";
			var mySql3 = new SqlClass();
			mySql3.setResourceName("print.LPrtTempleteInputSql");
			mySql3.setSqlId(sqlid3);
			mySql3.addSubPara(tTempleteName);
			mySql3.addSubPara(tTempleteID);
			tResult3 = easyExecSql(mySql3.getString());
			if(tDefaultType == "Y" && tResult3[0][0] != "0")
			{
				alert("�ô�ӡ��Ŀ�Ѵ���Ĭ��ģ��!");
				return false;
			}
		}
	}
}
function deleteClick()
{
	if( verifyInput2() == false ) return false;
	if(document.all("TempleteID").value =="" )
	{
		alert("���Ȳ�ѯ��");
	}
	else
	{
		if (confirm("��ȷʵ��ɾ���ü�¼��?"))
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
			document.getElementById("fm").submit(); // �ύ
			resetForm();
		}
		else
		{
			alert("��ȡ����ɾ��������");
		}
	}
}
function resetForm()
{
	try
	{
		fm.reset();
		initForm();
		fileUpdateClick();
    }
	catch(ex)
	{
		alert("-->InitForm�����з����쳣:��ʼ���������!");
	} 
}
function updateClick()
{
	if(verifyInput2() == false)
	{
		return false;
	}
	if(document.all("TempleteID").value =="" )
	{
		alert("���Ȳ�ѯ��");
	}
	else
	{	
		if(confirm("��ȷʵ���޸ĸü�¼��?"))
		{
			mOperate = "UPDATE||MAIN";
			submitForm();
			fileUpdateClick1();
		}
		else
		{
			alert("��ȡ�����޸Ĳ�����");
		}
	}
}
function queryClick()
{
	mOperate = "QUERY||MAIN";
	showInfo = window.open("./LPrtTempleteQuery.jsp");
}
function afterQuery(arrQueryResult)
{
	var arrResult = new Array();
	if(arrQueryResult != null)
	{
		var sqlid5 = "LPrtTempleteInputSql5";
		var mySql5 = new SqlClass();
		mySql5.setResourceName("print.LPrtTempleteInputSql");
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(arrQueryResult[0][0]);
	 	arrResult = easyExecSql(mySql5.getString());
	 	fm.reset();
	 	document.all('TempleteID').value = arrResult[0][0];
	 	document.all('TempleteName').value = arrResult[0][1];
	 	document.all('Language').value = arrResult[0][2];
	 	document.all('LanguageType').value = arrResult[0][3];
	 	document.all('TempleteType').value = arrResult[0][4];
	 	document.all('TempleteTypeName').value = arrResult[0][5];
	 	document.all('OutputType').value = arrResult[0][6];
	 	document.all('OutputTypeName').value = arrResult[0][7];
	 	document.all('Output').value = arrResult[0][8];
	 	document.all('OutputName').value = arrResult[0][9];
	 	document.all('FilePath1').style.display='';
	 	document.all('FilePath').style.display='none';
	 	document.all("fileQueryClick").style.display ='';
	 	document.all('FilePath1').value = arrResult[0][10];
	 	document.all('DefaultType').value = arrResult[0][11];
	 	document.all('DefaultFlag').value = arrResult[0][12];
	 	document.all('PrintID').value = arrResult[0][13];
	 	document.all('TempleteID').readonly = true;
	}
}


