/***************************************************************
 * <p>ProName��GuaranteedRateInput.js</p>
 * <p>Title����֤���ʹ���</p>
 * <p>Description����֤���ʹ���</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2014-07-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬

var tSQLInfo = new SqlClass();

/**
 * �ύ����
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
	fm.action = "./GuaranteedRateSave.jsp";
	fm.submit(); //�ύ
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		queryClick();
	}
}

/**
 * ��ѯ
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_acc.RateSql");
	tSQLInfo.setSqlId("RateSql1");
	tSQLInfo.addSubPara(fm.QueryRiskCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), GuaranteedRateGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}

/**
 * ��ʾ����
 */
function showGuaranteedRateInfo() {
	
	var tSelNo = GuaranteedRateGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�������б���Ϣ��");
		return false;
	}
	
	fm.RiskCode.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 1);
	fm.RiskName.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 2);
	fm.InsuAccNo.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 3);
	fm.InsuAccName.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 4);
	fm.SRateDate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 5);
	fm.ARateDate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 6);
	fm.RateYear.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 7);
	fm.RateIntv.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 10);
	fm.RateIntvName.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 11);
	fm.Rate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 12);
	
	fm.StartDate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 8);
	fm.EndDate.value = GuaranteedRateGrid.getRowColData(tSelNo-1, 9);
}

/**
 * ����
 */
function insertClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "INSERT";
	submitForm();
}

/**
 * �޸�
 */
function updateClick() {
	
	var tSelNo = GuaranteedRateGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "UPDATE";
	submitForm();
}

/**
 * ɾ��
 */
function deleteClick() {
	
	var tSelNo = GuaranteedRateGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	
	if (!confirm("ȷ��Ҫɾ���÷�֧��Ŀ��")) {
		return false
	}
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * ����
 */
function resetClick() {
	
	clearInfo();
	initGuaranteedRateGrid();
}

/**
 * ���������
 */
function clearInfo() {
	
	fm.RiskCode.value = "";
	fm.RiskName.value = "";
	fm.InsuAccNo.value = "";
	fm.InsuAccName.value = "";
	fm.SRateDate.value = "";
	fm.ARateDate.value = "";
	fm.RateYear.value = "";
	fm.RateIntv.value = "";
	fm.RateIntvName.value = "";
	fm.Rate.value = "";
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {
	
	if (Field.name=="RiskCode") {
		
		fm.InsuAccNo.value = "";
		fm.InsuAccName.value = "";
	}
}

/**
 * ��ѯ�˻�֮ǰ��У��
 */
function beforQueryCode(obj, Field) {
	
	var tRiskCode = Field.value;
	if (tRiskCode==null || tRiskCode=="") {
		
		alert("����ѡ�����ֱ��룡");
		field.focus();
		return false;
	}
	
	return true;
}

/**
 * ��������
 */
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "���ֱ���^|��������^|�˻�����^|�˻�����^|Ӧ��������^|ʵ�ʹ�������^|��֤���^|���ʿ�ʼ����^|���ʽ�������^|�������ͱ���^|��������^|����^|¼����^|¼������";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_acc.RateSql");
	tSQLInfo.setSqlId("RateSql1");
	tSQLInfo.addSubPara(fm.QueryRiskCode.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
