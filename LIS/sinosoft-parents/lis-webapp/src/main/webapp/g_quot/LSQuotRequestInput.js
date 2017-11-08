/***************************************************************
 * <p>ProName��LSQuotRequestInput.js</p>
 * <p>Title��ҵ������</p>
 * <p>Description��ҵ������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotRequestSql");
	
	if (tActivityID == "0800100002") {//��֧
		tSQLInfo.setSqlId("LSQuotRequestSql1");
	} else {
		tSQLInfo.setSqlId("LSQuotRequestSql2");
	}
	
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), RequestGrid, 0, 1);
}

/**
 * ����
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * �޸�
 */
function modifyClick() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.SerialNo.value = RequestGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * ɾ��
 */
function deleteClick() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	fm.SerialNo.value = RequestGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * ����
 */
function saveClick() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.SerialNo.value = RequestGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "SAVE";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * չʾҵ��������ϸ
 */
function showDetail() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.RequestType.value = RequestGrid.getRowColData(tSelNo-1, 2);
	fm.RequestTypeName.value = RequestGrid.getRowColData(tSelNo-1, 3);
	fm.OtherTypeDesc.value = RequestGrid.getRowColData(tSelNo-1, 4);
	fm.RiskCode.value = RequestGrid.getRowColData(tSelNo-1, 5);
	fm.RiskName.value = RequestGrid.getRowColData(tSelNo-1, 6);
	fm.RequestDesc.value = RequestGrid.getRowColData(tSelNo-1, 7);
	fm.SubUWOpinion.value = RequestGrid.getRowColData(tSelNo-1, 8);
	fm.BranchUWOpinion.value = RequestGrid.getRowColData(tSelNo-1, 9);
	
	if (fm.RequestType.value == "00" || fm.RequestType.value == "01" ) {
		
		divOtherTypeDescTitle.style.display = "none";
		divOtherTypeDescInput.style.display = "none";
		divRiskTitle.style.display = "";
		divRiskInput.style.display = "";
		divTD1.style.display = "";
		divTD2.style.display = "";
		divTD3.style.display = "none";
		divTD4.style.display = "none";
	} else if (fm.RequestType.value == "09") {
		
		divOtherTypeDescTitle.style.display = "";
		divOtherTypeDescInput.style.display = "";
		divRiskTitle.style.display = "none";
		divRiskInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
		divTD3.style.display = "none";
		divTD4.style.display = "none";
	}
	
	var tSysPlanCode = RequestGrid.getRowColData(tSelNo-1, 10);
	var tPlanCode = RequestGrid.getRowColData(tSelNo-1, 11);
	
	if (tActivityID == "0800100001") {
		if ((tSysPlanCode==null || tSysPlanCode == "" )&&(tPlanCode==null || tPlanCode == "")) {
			
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "";
		} else {
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
		}
	}
}

/**
 * �ύ
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotRequestSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	fm.submit();
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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

/**
 * ��ѯ׼�ͻ���ϸ��Ϣ
 */
function queryDetail() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql2");
	tSQLInfo.addSubPara(tPreCustomerNo);
}

/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	if (mOperate=="INSERT" || mOperate=="UPDATE") {
		
		if (fm.RequestType.value=="00" || fm.RequestType.value=="01") {
			
			if (fm.RiskCode.value=="") {
				
				alert("���ֲ���Ϊ�գ�");
				return false;
			}
		}
		
		if (fm.RequestType.value=="09") {
			
			if (fm.OtherTypeDesc.value=="") {
				
				alert("����������������Ϊ�գ�");
				return false;
			}
		}
		
		if (fm.RequestDesc.value=="") {
			
			alert("ҵ���������ݲ���Ϊ�գ�");
			return false;
		}
		
		if (fm.RequestDesc.value.length>1500) {
			
			alert("ҵ���������ݹ�����");
			return false;
		}
	}
	
	if (mOperate=="SAVE") {
		
		if (tActivityID == "0800100002") {
			
			if (fm.SubUWOpinion.value=="") {
				
				alert("��֧��˾�˱��������Ϊ�գ�");
				return false;
			}
			
			if (fm.SubUWOpinion.value.length>1500) {
				
				alert("��֧��˾�˱����������");
				return false;
			}
		} else if (tActivityID == "0800100003") {
			
			if (fm.BranchUWOpinion.value=="") {
				
				alert("�ֹ�˾�˱��������Ϊ�գ�");
				return false;
			}
			
			if (fm.BranchUWOpinion.value.length>1500) {
				
				alert("�ֹ�˾�˱����������");
				return false;
			}
		}
	}
	
	return true;
}

/**
 * ������ѡ�����
 */
function afterCodeSelect(tSelectValue, tObj) {

	if (tSelectValue=='requesttype') {
		
		if (tObj.value == "00" || tObj.value == "01" ) {
			
			divOtherTypeDescTitle.style.display = "none";
			divOtherTypeDescInput.style.display = "none";
			fm.OtherTypeDesc.value = "";
			divRiskTitle.style.display = "";
			divRiskInput.style.display = "";
			fm.RiskCode.value = "";
			fm.RiskName.value = "";
			divTD1.style.display = "";
			divTD2.style.display = "";
			divTD3.style.display = "none";
			divTD4.style.display = "none";
		} else if (tObj.value == "09") {
			
			divOtherTypeDescTitle.style.display = "";
			divOtherTypeDescInput.style.display = "";
			fm.OtherTypeDesc.value = "";
			divRiskTitle.style.display = "none";
			divRiskInput.style.display = "none";
			fm.RiskCode.value = "";
			fm.RiskName.value = "";
			divTD1.style.display = "";
			divTD2.style.display = "";
			divTD3.style.display = "none";
			divTD4.style.display = "none";
		}
	}
}
