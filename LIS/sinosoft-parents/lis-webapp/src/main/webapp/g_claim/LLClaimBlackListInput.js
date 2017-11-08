/***************************************************************
 * <p>ProName��LLClaimBPOcheckInput.js</p>
 * <p>Title������������</p>
 * <p>Description������������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : gaodh
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();
 
/**
 * ��ѯ��ά���Ŀͻ���Ϣ�б�
 */
function queryCustomerList() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBlackListSql");
	tSQLInfo.setSqlId("LLClaimBlackListSql1");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	tSQLInfo.addSubPara(mOperator);
	tSQLInfo.addSubPara(mClmState);
	
	turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2);	
}

/**
 * ��ѯ�����˵ĵ����켣��Ϣ
 */
function showCustomerTrace() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	fm.RgtNo.value = CustomerListGrid.getRowColData(tSelNo,1);	
	fm.CustomerNo.value = CustomerListGrid.getRowColData(tSelNo,2);
	var tState = CustomerListGrid.getRowColData(tSelNo,8);
	fm.State.value = tState;
	if (tState=="0") {
		document.getElementById("confirmReason").style.display = "";
		document.getElementById("releaseReason").style.display = "none";
		fm.blackConform.style.display = "";
		fm.blackRelase.style.display = "none";
	} else {
		document.getElementById("confirmReason").style.display = "none";
		document.getElementById("releaseReason").style.display = "";
		fm.blackConform.style.display = "none";
		fm.blackRelase.style.display = "";		
	}
	
	if (mMode==1) {
		
		document.getElementById("blackConform").disabled=true;	
		document.getElementById("blackRelase").disabled=true;
	} else {
		document.getElementById("blackConform").disabled=false;	
		document.getElementById("blackRelase").disabled=false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBlackListSql");
	tSQLInfo.setSqlId("LLClaimBlackListSql2");
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(),CustomerStateListGrid,2);
	
}

/**
 * ������ȷ��
 */
function conformClick() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	if (fm.AdjustReason.value=="") {
		alert("����ԭ�򲻿�Ϊ�գ�");
		fm.AdjustReason.focus();
		fm.AdjustReason.style.background="#ffb900";
	}
			
	fm.Operate.value = "CONFIRM";	
	submitForm();	
}

/**
 * ȡ��������
 */
function releaseClick() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}

	var tState = CustomerListGrid.getRowColData(tSelNo,8);	
	fm.State.value = tState;
	
	if (fm.AdjustReason1.value=="") {
		alert("����ԭ�򲻿�Ϊ�գ�");
		fm.AdjustReason1.focus();
		fm.AdjustReason1.style.background="#ffb900";
	}		
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}	
			
	fm.Operate.value = "RELEASE";	
	submitForm();	
}

/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
		initForm();
	}	
}