/***************************************************************
 * <p>ProName��LSQuotPlanImpInfoInput.js</p>
 * <p>Title��ѯ�۷���������Ϣ</p>
 * <p>Description��ѯ�۷���������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-07-16
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ����������Ϣ�б�
 */
function queryPlanImpList() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
	tSQLInfo.setSqlId("LSQuotPlanImpInfoSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(fm.BacthNo.value);
	tSQLInfo.addSubPara(fm.ImpState.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), PlanImpInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ������Ա�嵥��Ϣ
 */
function downloadClick() {
	
	var tSelNo = PlanImpInfoGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	
	var tImpState = PlanImpInfoGrid.getRowColData(tSelNo-1, 5);
	if (tImpState=="1") {
		alert("�����ε��뷽��ȫ����ȷ�������δ�����Ϣ��");
		return false;
	}
	var tBatchNo = PlanImpInfoGrid.getRowColData(tSelNo-1, 1);
	if (tQuotType=="00" && tTranProdType == "00") {//һ��ѯ��	��ͨ����
		
		fm.SheetName[0].value = "������Ϣ";
		fm.SheetName[1].value = "������ϸ��Ϣ";
		fm.SheetName[2].value = "������������";
		
		//������Ϣ
		var tPlanTitle = "�������^|��������^|��������^|������ʶ^|ְҵ����^|���ְҵ���^|���ְҵ���^|ְҵ����^|"
				+ "ְҵ���^|ְҵ�з���^|����^|�������^|�������^|ƽ������^|����^|�μ��籣ռ��^|"
				+ "��Ů����^|����ռ��^|���ѷ�̯��ʽ^|��ҵ����ռ��^|�����н^|�����н^|ƽ����н^|����˵��^|���������Ϣ";

		// ������ϸ��Ϣ
		var tPlanDetailTitle = "�������^|���ֱ���^|���α���^|��������^|�̶�����^|��н����^|��ͱ���^|��߱���^|������������^|��������/����/�ۿ�^|������������^|��ע^|" 
				+"���ⷽʽ^|�����^|�⸶����^|��������^|ÿ�ս�����^|�ȴ���^|ʹ�������˻��޶�^|������Ⱥ^|���޶�^|��������^|��������^|�Ƿ�α�׼��^|"
				+ "�ɷ��ڼ�^|�ɷѵ�λ^|סԺ�⸶��������^|���������Ϣ";
		
		// ������������
		var tRelaShareITitle = "�������^|���ֱ���^|���α���^|���������˱���ռ��^|�����������˱���ռ��^|" 
				+"���ⷽʽ^|�����^|�⸶����^|��������^|ÿ�ս�����^|�ȴ���^|ʹ�������˻��޶�^|������Ⱥ^|���޶�^|��������^|��������^|�Ƿ�α�׼��^|"
				+ "�ɷ��ڼ�^|�ɷѵ�λ^|סԺ�⸶��������^|���������Ϣ";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		fm.SheetTitle[2].value = tRelaShareITitle;
		
		//����
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql2");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//������ϸ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql3");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//������������
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql4");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="00" && tTranProdType == "01") {//һ��ѯ��	������

		fm.SheetName[0].value = "������Ϣ";
		fm.SheetName[1].value = "������ϸ��Ϣ";
		fm.SheetName[2].value = "Sheet1";//����������Sheetҳ
		
		//������Ϣ
		var tPlanTitle = "�������^|��������^|���Ѽ��㷽ʽ^|����^|����˵��^|���������Ϣ";

		// ������ϸ��Ϣ
		var tPlanDetailTitle = "�������^|���ֱ���^|���α���^|��������^|�̶�����^|��ͱ���^|��߱���^|������������^|��������/����/�ۿ�^|��ע^|" 
				+"���ⷽʽ^|�����^|�⸶����^|��������^|ÿ�ս�����^|�ȴ���^|ʹ�������˻��޶�^|������Ⱥ^|���޶�^|��������^|��������^|�Ƿ�α�׼��^|"
				+ "�ɷ��ڼ�^|�ɷѵ�λ^|סԺ�⸶��������^|���������Ϣ";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		
		//����
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql5");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//������ϸ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql6");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//�������������ã����������
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql7");
		tSQLInfo.addSubPara('2');
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="00" && tTranProdType == "02") {//һ��ѯ��	�˻�����

		fm.SheetName[0].value = "������Ϣ";
		fm.SheetName[1].value = "������ϸ��Ϣ";
		fm.SheetName[2].value = "Sheet1";//����������Sheetҳ
		
		//������Ϣ
		var tPlanTitle = "�������^|��������^|��������^|ְҵ����^|���ְҵ���^|���ְҵ���^|ְҵ����^|"
				+ "ְҵ���^|ְҵ�з���^|����^|�������^|�������^|ƽ������^|����^|�μ��籣ռ��^|"
				+ "��Ů����^|����ռ��^|���ѷ�̯��ʽ^|��ҵ����ռ��^|�����н^|�����н^|ƽ����н^|����˵��^|���������Ϣ";

		// ������ϸ��Ϣ
		var tPlanDetailTitle = "�������^|���ֱ���^|���α���^|��ʼ����^|����������^|��ע^|" 
				+"���ⷽʽ^|�����^|�⸶����^|��������^|ÿ�ս�����^|�ȴ���^|ʹ�������˻��޶�^|������Ⱥ^|���޶�^|��������^|��������^|�Ƿ�α�׼��^|"
				+ "�ɷ��ڼ�^|�ɷѵ�λ^|סԺ�⸶��������^|���������Ϣ";
		
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		
		//����
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql8");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//������ϸ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql9");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//�������������ã����������
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql7");
		tSQLInfo.addSubPara('2');
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="01" && tTranProdType == "00") {//��Ŀѯ��	��ͨ����
		
		fm.SheetName[0].value = "������Ϣ";
		fm.SheetName[1].value = "������ϸ��Ϣ";
		fm.SheetName[2].value = "������������";
		
		//������Ϣ
		var tPlanTitle = "�������^|��������^|�����ڼ�^|�����ڼ䵥λ^|��������^|������ʶ^|ְҵ����^|���ְҵ���^|���ְҵ���^|ְҵ����^|"
				+ "ְҵ���^|ְҵ�з���^|����^|�������^|�������^|ƽ������^|����^|�μ��籣ռ��^|"
				+ "��Ů����^|����ռ��^|���ѷ�̯��ʽ^|��ҵ����ռ��^|�����н^|�����н^|ƽ����н^|����˵��^|���������Ϣ";

		// ������ϸ��Ϣ
		var tPlanDetailTitle = "�������^|���ֱ���^|���α���^|��������^|�̶�����^|��н����^|��ͱ���^|��߱���^|������������^|��������/����/�ۿ�^|������������^|��ע^|" 
				+"���ⷽʽ^|�����^|�⸶����^|��������^|ÿ�ս�����^|�ȴ���^|ʹ�������˻��޶�^|������Ⱥ^|���޶�^|��������^|��������^|�Ƿ�α�׼��^|"
				+ "�ɷ��ڼ�^|�ɷѵ�λ^|סԺ�⸶��������^|���������Ϣ";
		
		// ������������
		var tRelaShareITitle = "�������^|���ֱ���^|���α���^|���������˱���ռ��^|�����������˱���ռ��^|" 
				+"���ⷽʽ^|�����^|�⸶����^|��������^|ÿ�ս�����^|�ȴ���^|ʹ�������˻��޶�^|������Ⱥ^|���޶�^|��������^|��������^|�Ƿ�α�׼��^|"
				+ "�ɷ��ڼ�^|�ɷѵ�λ^|סԺ�⸶��������^|���������Ϣ";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		fm.SheetTitle[2].value = tRelaShareITitle;
		
		//����
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql10");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//������ϸ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql11");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//������������
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql12");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="01" && tTranProdType == "01") {//��Ŀѯ��  ������

		fm.SheetName[0].value = "������Ϣ";
		fm.SheetName[1].value = "������ϸ��Ϣ";
		fm.SheetName[2].value = "Sheet1";//����������Sheetҳ
		
		//������Ϣ
		var tPlanTitle = "�������^|��������^|�����ڼ�^|�����ڼ䵥λ^|���Ѽ��㷽ʽ^|����^|�������^|�������^|��������^|ʩ������^������ϸ^|"
				+ "���̸���^|����״��˵��^|����˵��^|���������Ϣ";

		// ������ϸ��Ϣ
		var tPlanDetailTitle = "�������^|���ֱ���^|���α���^|��������^|�̶�����^|��ͱ���^|��߱���^|������������^|��������/����/�ۿ�^|��ע^|" 
				+"���ⷽʽ^|�����^|�⸶����^|��������^|ÿ�ս�����^|�ȴ���^|ʹ�������˻��޶�^|������Ⱥ^|���޶�^|��������^|��������^|�Ƿ�α�׼��^|"
				+ "�ɷ��ڼ�^|�ɷѵ�λ^|סԺ�⸶��������^|���������Ϣ";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		
		//����
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql13");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//������ϸ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql14");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//�������������ã����������
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql7");
		tSQLInfo.addSubPara('2');
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} else if (tQuotType=="01" && tTranProdType == "03") {//��Ŀѯ��	��������
		
		fm.SheetName[0].value = "������Ϣ";
		fm.SheetName[1].value = "������ϸ��Ϣ";
		fm.SheetName[2].value = "Sheet1";//����������Sheetҳ
		
		//������Ϣ
		var tPlanTitle = "�������^|��������^|�����ڼ�^|�����ڼ䵥λ^|��������^|������ʶ^|ְҵ����^|���ְҵ���^|���ְҵ���^|ְҵ����^|"
				+ "ְҵ���^|ְҵ�з���^|����^|�������^|�������^|ƽ������^|����^|�μ��籣ռ��^|"
				+ "��Ů����^|����ռ��^|���ѷ�̯��ʽ^|��ҵ����ռ��^|�����н^|�����н^|ƽ����н^|����˵��^|���������Ϣ";

		// ������ϸ��Ϣ
		var tPlanDetailTitle = "�������^|���ֱ���^|���α���^|��������^|�̶�����^|��н����^|��ͱ���^|��߱���^|������������^|��������/����/�ۿ�^|��ע^|" 
				+"���ⷽʽ^|�����^|�⸶����^|��������^|ÿ�ս�����^|�ȴ���^|ʹ�������˻��޶�^|������Ⱥ^|���޶�^|��������^|��������^|�Ƿ�α�׼��^|"
				+ "�ɷ��ڼ�^|�ɷѵ�λ^|סԺ�⸶��������^|���������Ϣ";
		
		fm.SheetTitle[0].value = tPlanTitle;
		fm.SheetTitle[1].value = tPlanDetailTitle;
		
		//����
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql15");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[0].value = tSQLInfo.getString();
		
		//������ϸ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql16");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tBatchNo);
		
		fm.SheetSql[1].value = tSQLInfo.getString();
		
		//�������������ã����������
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPlanImpInfoSql");
		tSQLInfo.setSqlId("LSQuotPlanImpInfoSql7");
		tSQLInfo.addSubPara('2');
		
		fm.SheetSql[2].value = tSQLInfo.getString();
		
	} 
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}


/**
 * �ύ����
 */
function submitForm(tForm) {
	
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
	tForm.submit();
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
