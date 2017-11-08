/***************************************************************
 * <p>ProName��LJTempFeeOutConfirmInput.js</p>
 * <p>Title�������˷����</p>
 * <p>Description�������˷����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tOperate;

function queryInfo() {
	
	initOutPayOutInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	tSQLInfo.setSqlId("LJOutPayOutSql3");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgencyName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), OutPayOutInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}


function confirmSubmit() {
	
	fm.ConfirmSubmitButton.disabled = true;
	if (!checkConfirm()) {
		fm.ConfirmSubmitButton.disabled = false;
		return false;
	}
	
	var tSelNo = OutPayOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ���в��������ݣ�");
		return false;
	}
	var tApplyBatNo = OutPayOutInfoGrid.getRowColData(tSelNo, 1);
	tOperate = "CONFIRMSUBMIT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJOutPayOutConfirmSave.jsp?Operate="+ tOperate +"&ApplyBatNo="+ tApplyBatNo;
	submitForm(fm);
}

function checkConfirm() {
	
	var tSelNo = OutPayOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ���в��������ݣ�");
		return false;
	}
	
	var tBussNo = OutPayOutInfoGrid.getRowColData(tSelNo, 1);
	
	var tConfirmConclusion = fm.ConfirmConclusion.value;
	var tConfirmDesc = fm.ConfirmDesc.value;
	
	if (tConfirmConclusion==null || tConfirmConclusion=="") {
		alert("��˽��۲���Ϊ�գ�");
		return false;
	}
	
	if (tConfirmConclusion=="01") {
		
		if (tConfirmDesc==null || tConfirmDesc=="") {
			alert("��˽���Ϊ��ͨ��ʱ��������������Ϊ�գ�");
			return false;
		}
	}
	
	if (tConfirmDesc.length>300) {
		alert("��������Ӧ��300�����ڣ�");
		return false;
	}
	
	return true;
}

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}
	
	dealAfterSubmit(FlagStr);
}

/**
 * �ύ���غ���
 */
function dealAfterSubmit(tFlag) {

	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="CONFIRMSUBMIT") {
		
		fm.all("ConfirmSubmitButton").disabled = false;
		if (tFlag!="Fail") {
			
			initOutPayOutInfoGrid();
			fm.ConfirmConclusion.value = "";
			fm.ConfirmConclusionName.value = "";
			fm.ConfirmDesc.value = "";
		}
	}
}
