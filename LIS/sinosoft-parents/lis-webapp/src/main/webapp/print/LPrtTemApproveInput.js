var mOperate = "";
var turnPage = new turnPageClass();
var showInfo;
var mySql1;
window.onfocus = myonfocus;
function myonfocus() 
{
	if (showInfo != null) 
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
function easyQueryClick() 
{
	if (document.all("State").value == "") 
	{
		alert("��ѡ������״̬!")
		return;
	}
	else
	{	
		try 
		{
			var sqlId1 = "LPrtTemApproveInputSql1";
			mySql1 = new SqlClass();
			mySql1.setResourceName("print.LPrtTemApproveInputSql");
			mySql1.setSqlId(sqlId1);
			mySql1.addSubPara(document.all("TempleteName").value);
			mySql1.addSubPara(document.all("TempleteType").value);
			mySql1.addSubPara(document.all("Language").value);
			mySql1.addSubPara(document.all("State").value);
			turnPage.queryModal(mySql1.getString(), LPrtTempleteGrid);
		} 
		catch (ex) 
		{
			alert(ex.message);
		}
	}
	DivApprove.style.display = 'none';
}
// �������ģ������
function templeteApprove() 
{
	if(verifyInput2() == false)
	{
		return false;
	}
	var tSel = LPrtTempleteGrid.getSelNo();
	if (tSel == 0 || tSel == null) 
	{
		alert("��ѡ��һ����¼���ٽ���ģ��������");
	} 
	else 
	{
		try 
		{
			document.all("TempleteID").value = LPrtTempleteGrid.getRowColData(tSel-1 ,1);
			mOperate = "UPDATE||MAIN";
			submitForm();
	    } 
		catch (ex) 
		{
			alert("ģ����������" + ex.message);
		}
	}
}
function resetForm()
{
	try 
	{
		var sqlId1 = "LPrtTemApproveInputSql1";
		mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtTemApproveInputSql");
		mySql1.setSqlId(sqlId1);
		mySql1.addSubPara(document.all("TempleteName").value);
		mySql1.addSubPara(document.all("TempleteType").value);
		mySql1.addSubPara(document.all("Language").value);
		mySql1.addSubPara(document.all("State").value);
		turnPage.queryModal(mySql1.getString(), LPrtTempleteGrid);
	} 
	catch (ex) 
	{
		alert(ex.message);
	}
	DivApprove.style.display = 'none';
}
function submitForm()
{
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
	resetForm();
}
function getQueryResult() 
{
	DivApprove.style.display = '';
	var arrResult = new Array();
	tSel = LPrtTempleteGrid.getSelNo();
	document.all("TempleteID").value = LPrtTempleteGrid.getRowColData(tSel-1 ,1);
	var tTempleteID = document.all("TempleteID").value;
	var sqlId2 = "LPrtTemApproveInputSql2";
	mySql2 = new SqlClass();
	mySql2.setResourceName("print.LPrtTemApproveInputSql");
	mySql2.setSqlId(sqlId2);
	mySql2.addSubPara(tTempleteID);
	arrResult = easyExecSql(mySql2.getString());
	document.all("State1").value = arrResult[0][0];
	document.all("ApproveFlag").value = arrResult[0][1];
	document.all("Remark").value = arrResult[0][2];
}