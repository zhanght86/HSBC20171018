/***************************************************************
 * <p>ProName��LCInsuredUploadInput.js</p>
 * <p>Title����Ա�嵥����</p>
 * <p>Description����Ա�嵥����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-27
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ������Ϣ��ѯ
 */
function queryBatchInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql1");
	tSQLInfo.addSubPara(tGrpPropNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), BatchGrid,1,1);
}

/**
 * ������ϸ��Ϣ��ѯ
 */
function queryDetailInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	if (fm.State.value=="0") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("1");
	} else if (fm.State.value=="1") {
		tSQLInfo.addSubPara("1");
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), BatchDetailGrid,1,1);
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ������Ա�嵥
 */
function importClick() {
	
	if (tContPlanType=="02") {//�˻��Ͳ�Ʒ����
		
		tOperate = "UPLOAD3";
	} else {
		
		if (tUnFixedAmntFlag) {//�ǹ̶������
			
			tOperate = "UPLOAD2";
		} else {//һ�㵼��
			
			tOperate = "UPLOAD1";
		}
	}
	
	var filePath = fmupload.ImportPath.value;
	if(filePath == null || filePath == ""){
		alert("��ѡ�����ļ�·����");
		return false;
	}
	
	var indexFirst = filePath.lastIndexOf("\\");
	var indexLast = filePath.lastIndexOf(".xlsx");
	if(indexFirst < 0 || indexLast < 0 || indexLast <= indexFirst) {
		alert("�ļ�·�����Ϸ���ѡ����ļ���ʽ����ȷ��������ѡ��");
		return false;
	}
	
	var fileName = filePath.substring(indexFirst+1,indexLast);
	var tGrpPropIndex = fileName.lastIndexOf("-");
	if (tGrpPropIndex < 0) {
		alert("�ļ�������ʽ����ȷ��������������");
		return false;
	}
	if (tGrpPropNo != fileName.substring(0,tGrpPropIndex)) {
		alert("�ļ�����������Ͷ����ſ�ʼ��������������");
		return false;
	}
	
	var tSubOtherNo = fileName.substring(tGrpPropIndex+1, indexLast);
	if (isNaN(tSubOtherNo)) {
		
		alert("�ļ�������ʽ����ȷ���ļ�������Ͷ����ſ�ʼ�����Ϻ�ܣ��ټ�������ˮ���У�");
		return false;
	}
	
	if (fileName.length>=30) {
		
		alert("�ļ������Ȳ��ܳ���30��");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql3");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(fileName);
	
	var havaFile = easyExecSql(tSQLInfo.getString(),1,0);
	if (havaFile == null) {
		alert("�ļ��Ƿ��ѵ���У��ʧ�ܣ�");
		return false;
	} else if (havaFile[0][0] > 0) {
		alert("�������ļ��Ѿ�����,���������������ļ���");
		return false;
	}
	
	fmupload.action="./LCInsuredUploadSave.jsp?OtherNoType=LIST&OtherNo="+tGrpPropNo+"&SubOtherNo="+tSubOtherNo+"&UploadNode="+tActivityID+"&Operate="+tOperate+"&AttachType=09";
	submitForm(fmupload);
}

/**
 * ������Ա�嵥��Ϣ
 */
function downloadClick() {
	
	var tSelNo = BatchGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	
	var tBatchNo = BatchGrid.getRowColData(tSelNo-1, 2);
	
	fm.SheetName[0].value = "���������嵥";
	fm.SheetName[1].value = "�������嵥";
	
	//���������嵥����
	var tInsuredTitle = "���^|�����������˹�ϵ^|�������������^|������������^|֤������^|֤������^|�Ա�^|��������^|"
										+ "���շ���^|ְҵ����^|��������^|������^|�˺�^|����������ʡ^|������������^|��������^|"
										+ "�Ƿ�α�׼��^|�籣���^|ְ������^|��˾ʱ��^|����^|��н^|Ա����^|֤���Ƿ���^|֤����Ч��^|"
										+ "���ڷֹ�˾^|���ڲ���^|�������˱���^|������^|�籣��^|��������^|��������^|"
										+ "΢�ź�^|��ϵ�绰^|�ƶ��绰^|��ϵ��ַ(ʡ)^|��ϵ��ַ(��)^|��ϵ��ַ(��/��)^|��ϸ��ַ^|���������Ϣ";
	
	//�������嵥����
	var tBnfTitle = "�����������^|������˳��^|����^|֤������^|֤������^|�Ա�^|��������^|"
								+ "�뱻�����˹�ϵ^|�������(С��)^|���������Ϣ";
							
	fm.SheetTitle[0].value = tInsuredTitle;
	fm.SheetTitle[1].value = tBnfTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql4");
	tSQLInfo.addSubPara(tBatchNo);
	
	fm.SheetSql[0].value = tSQLInfo.getString();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
	tSQLInfo.setSqlId("LCInsuredUploadSql5");
	tSQLInfo.addSubPara(tBatchNo);
	
	fm.SheetSql[1].value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}

/**
 * �ύ����
 */
function submitForm(tForm) {
	
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

/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	return true;
}
