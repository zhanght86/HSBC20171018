var showInfo;
var mDebug = "1";
var mEdorType;
var turnPage = new turnPageClass();
var turnPageCustomerContGrid = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeAMInputSql";

// ��ѯ��ַ���
function initInpRole()
{
	var tContNo = document.all('ContNo').value;
	// �ж����Ƿ���Ͷ����
	// var sql = " select 1 from lcappnt where contno = '" + tContNo + "' and
	// appntno ='"+document.all('CustomerNo').value+"'";
	var sql = "";
	var sqlid1 = "PEdorTypeAMInputSql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename); // ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);// ָ������Ĳ���
	mySql1.addSubPara(document.all('CustomerNo').value);
	sql = mySql1.getString();
	var mrr = easyExecSql(sql);
	if(mrr)
	{
		fm.AppntIsInsuredFlag.value = "0";
	}
	var sqlid2 = "PEdorTypeAMInputSql2";
	var mySql2 = new SqlClass();
	mySql2.setResourceName(sqlresourcename); // ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);// ָ������Ĳ���
	mySql2.addSubPara(document.all('CustomerNo').value);
	sql = mySql2.getString();

	mrr = easyExecSql(sql);
	if(mrr)
	{
		fm.AppntIsInsuredFlag.value = "1";
	}
	var tsql = "";
	var sqlid3 = "PEdorTypeAMInputSql3";
	var mySql3 = new SqlClass();
	mySql3.setResourceName(sqlresourcename); // ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);// ָ������Ĳ���
	mySql3.addSubPara(tContNo);
	tsql = mySql3.getString();

	mrr = easyExecSql(tsql);
	if(mrr)
	{
		fm.AppntIsInsuredFlag.value = "2";
	}
	var sqlid4 = "PEdorTypeAMInputSql4";
	var mySql4 = new SqlClass();
	mySql4.setResourceName(sqlresourcename); // ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);// ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(document.all('CustomerNo').value);// ָ������Ĳ���
	sql = mySql4.getString();

	mrr = easyExecSql(sql);
	if(mrr)
	{
		fm.AddressNo.value = mrr[0][0];

	}
	else
	{
		return false;
	}

}
function initInpCustomerInfo()
{

	var sStrRole;
	if(fm.AppntIsInsuredFlag.value == '0')
	{
		sStrRole = "lcappnt";
	}
	else
	{
		sStrRole = "lcinsured";
	}
	var tSQL = "";
	var sqlid5 = "PEdorTypeAMInputSql5";
	var mySql5 = new SqlClass();
	mySql5.setResourceName(sqlresourcename); // ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);// ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(sStrRole);// ָ������Ĳ���
	mySql5.addSubPara(fm.ContNo.value);
	mySql5.addSubPara(document.all('CustomerNo').value);
	tSQL = mySql5.getString();

	CustomerInfo = easyExecSql(tSQL);
	if(CustomerInfo != null)
	{
		DisPlyaCustomerInf(CustomerInfo);
	}

}
function initInpPCustomerInfo()
{

	var CustomerInfo;
	var tSQL;

	var sStrRole, sPStrRole;
	if(fm.AppntIsInsuredFlag.value == '0')
	{
		sStrRole = "lcappnt";
	}
	else
	{
		sStrRole = "lcinsured";
	}

	if(fm.AppntIsInsuredFlag.value == '0')
	{
		sPStrRole = "lpappnt";
	}
	else
	{
		sPStrRole = "lpinsured";
	}
	var sqlid6 = "PEdorTypeAMInputSql6";
	var mySql6 = new SqlClass();
	mySql6.setResourceName(sqlresourcename); // ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);// ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(sPStrRole);// ָ������Ĳ���
	mySql6.addSubPara(fm.ContNo.value);
	mySql6.addSubPara(fm.EdorNo.value);
	mySql6.addSubPara(document.all('CustomerNo').value);
	mySql6.addSubPara(fm.EdorNo.value);
	tSQL = mySql6.getString();

	CustomerInfo = easyExecSql(tSQL);
	if(CustomerInfo != null)
	{
		DisPlyaPCustomerInf(CustomerInfo);
	}
	else
	{
		var sqlid7 = "PEdorTypeAMInputSql7";
		var mySql7 = new SqlClass();
		mySql7.setResourceName(sqlresourcename); // ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);// ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(sStrRole);// ָ������Ĳ���
		mySql7.addSubPara(fm.ContNo.value);
		mySql7.addSubPara(document.all('CustomerNo').value);
		tSQL = mySql7.getString();

		CustomerInfo = easyExecSql(tSQL);
		if(CustomerInfo != null)
		{
			DisPlyaPCustomerInf(CustomerInfo);
		}
	}
}

function DisPlyaCustomerInf(CustomerInfo)
{

	CustomerInfo[0][0] == null || CustomerInfo[0][0] == 'null'? '0': fm.HomeAddress.value = CustomerInfo[0][0];
	CustomerInfo[0][1] == null || CustomerInfo[0][1] == 'null'? '0': fm.HomeZipCode.value = CustomerInfo[0][1];
	CustomerInfo[0][2] == null || CustomerInfo[0][2] == 'null'? '0': fm.Mobile.value = CustomerInfo[0][2];
	CustomerInfo[0][3] == null || CustomerInfo[0][3] == 'null'? '0': fm.GrpPhone.value = CustomerInfo[0][3];

	CustomerInfo[0][4] == null || CustomerInfo[0][4] == 'null'? '0': fm.EMail.value = CustomerInfo[0][4];
	CustomerInfo[0][5] == null || CustomerInfo[0][5] == 'null'? '0': fm.GrpName.value = CustomerInfo[0][5];

	CustomerInfo[0][6] == null || CustomerInfo[0][6] == 'null'? '0': fm.PostalAddress.value = CustomerInfo[0][6];
	CustomerInfo[0][7] == null || CustomerInfo[0][7] == 'null'? '0': fm.ZipCode.value = CustomerInfo[0][7];
	CustomerInfo[0][8] == null || CustomerInfo[0][8] == 'null'? '0': fm.AddressNo.value = CustomerInfo[0][8];
}

function DisPlyaPCustomerInf(CustomerInfo)
{
	CustomerInfo[0][0] == null || CustomerInfo[0][0] == 'null'? '0': fm.HomeAddress_New.value = CustomerInfo[0][0];
	CustomerInfo[0][1] == null || CustomerInfo[0][1] == 'null'? '0': fm.HomeZipCode_New.value = CustomerInfo[0][1];
	CustomerInfo[0][2] == null || CustomerInfo[0][2] == 'null'? '0': fm.Mobile_New.value = CustomerInfo[0][2];
	CustomerInfo[0][3] == null || CustomerInfo[0][3] == 'null'? '0': fm.GrpPhone_New.value = CustomerInfo[0][3];

	CustomerInfo[0][4] == null || CustomerInfo[0][4] == 'null'? '0': fm.EMail_New.value = CustomerInfo[0][4];
	CustomerInfo[0][5] == null || CustomerInfo[0][5] == 'null'? '0': fm.GrpName_New.value = CustomerInfo[0][5];

	CustomerInfo[0][6] == null || CustomerInfo[0][6] == 'null'? '0': fm.PostalAddress_New.value = CustomerInfo[0][6];
	CustomerInfo[0][7] == null || CustomerInfo[0][7] == 'null'? '0': fm.ZipCode_New.value = CustomerInfo[0][7];

}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag,MsgContent)
{
	try
	{
		showInfo.close();
	}
	catch (ex)
	{
	}
	DealFlag = DealFlag.toLowerCase();
	var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
	switch(DealFlag)
	{
		case "fail":
			MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
			//showInfo = showModalDialog(MsgPageURL, window,"status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			break;
		case "succ":
		case "success":
			MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
			//showInfo = showModalDialog(MsgPageURL, window,"status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			break;
		default:
			MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
			//showInfo = showModalDialog(MsgPageURL, window,"status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			break;
	}
}

/**
 * ����������
 */
function returnParent()
{
	try
	{
		top.close();
		top.opener.focus();
		top.opener.getEdorItemGrid();
	}
	catch (ex)
	{
	}
}

function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;;
	var ActivityID = '0000000003';
	var OtherNo = top.opener.document.all("OtherNo").value;;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}
	var varSrc = "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID+ "&ActivityID=" + ActivityID + "&PrtNo=" + OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp"+ varSrc, "���������±��鿴", "left");
}

function edorTypeAMSave()
{

	// ����У��
	if(!verifyInput2())
	{
		return false;
	}
	if(fm.PostalAddress_New.value == fm.PostalAddress.value
			&& fm.ZipCode_New.value == fm.ZipCode.value
			&& fm.HomeAddress_New.value == fm.HomeAddress.value
			&& fm.HomeZipCode_New.value == fm.HomeZipCode.value
			&& fm.Mobile_New.value == fm.Mobile.value
			&& fm.GrpPhone_New.value == fm.GrpPhone.value
			&& fm.EMail_New.value == fm.EMail.value
			&& fm.GrpName_New.value == fm.GrpName.value)
	{
		alert('�ͻ�����δ���������');
		return;
	}
	if(fm.GrpPhone_New.value == "" || fm.GrpPhone_New.value == null)
	{
		if(!confirm("������ϵ�绰Ϊ�գ��Ƿ����"))
		{
			return false
		}
	}
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "PEdorTypeAMSubmit.jsp";
	document.getElementById("fm").submit();
}
