/***************************************************************
 * <p>ProName��EdorInsuredDealInput.js</p>
 * <p>Title����ȫ��Ա�嵥����</p>
 * <p>Description����ȫ��Ա�嵥����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��Ա�嵥��ѯ
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
	tSQLInfo.setSqlId("EdorInsuredDealSql1");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.State.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), InsuredListGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ѡ��ɾ��
 */
function selDelete() {
	
	fm.Operate.value = "SELDEL";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorInsuredDealSave.jsp";
	submitForm(fm);
}

/**
 * ����ɾ��
 */
function conDelete() {
	
	fm.Operate.value = "CONDEL";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorInsuredDealSave.jsp";
	submitForm(fm);
}

/**
 * ȫ��ɾ��
 */
function allDelete() {
	
	fm.Operate.value = "ALLDEL";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorInsuredDealSave.jsp";
	submitForm(fm);
}

/**
 * ��Ա�嵥У��
 */
function insuredListCheck() {
	
	fm.Operate.value = "CHECKLIST";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorInsuredDealSave.jsp";
	submitForm(fm);
}

/**
 * ������Ϣ��ѯ
 */
function queryBatchInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
	tSQLInfo.setSqlId("EdorInsuredDealSql2");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), BatchGrid, 1, 1);
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
	var tEdorAppNoIndex = fileName.lastIndexOf("-");
	if (tEdorAppNoIndex < 0) {
		alert("�ļ�������ʽ����ȷ��������������");
		return false;
	}
	if (tEdorAppNo != fileName.substring(0,tEdorAppNoIndex)) {
		alert("�ļ����������Ա�ȫ����ſ�ʼ��������������");
		return false;
	}
	
	var tSubOtherNo = fileName.substring(tEdorAppNoIndex+1, indexLast);
	if (isNaN(tSubOtherNo)) {
		
		alert("�ļ�������ʽ����ȷ���ļ������Ա�ȫ����ſ�ʼ�����Ϻ�ܣ��ټ�������ˮ���У�");
		return false;
	}
	
	if (fileName.length>=30) {
		
		alert("�ļ������Ȳ��ܳ���30��");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
	tSQLInfo.setSqlId("EdorInsuredDealSql3");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(fileName);
	
	var havaFile = easyExecSql(tSQLInfo.getString(),1,0);
	if (havaFile == null) {
		alert("�ļ��Ƿ��ѵ���У��ʧ�ܣ�");
		return false;
	} else if (havaFile[0][0] > 0) {
		alert("�������ļ��Ѿ�����,���������������ļ���");
		return false;
	}
	
	fmupload.action="./EdorInsuredUploadSave.jsp?OtherNoType=LIST&OtherNo="+tEdorAppNo+"&SubOtherNo="+tSubOtherNo+"&UploadNode="+tActivityID+"&Operate="+tOperate+"&AttachType=09";
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
	
	fm.SheetName[0].value = "�����Ա�嵥";
	fm.SheetName[1].value = "�������嵥";
	
	//�����Ա�嵥����
	var tInsuredTitle = "��ȫ��Ŀ^|���^|�����������˹�ϵ^|�������������^|��������������^|����������֤������^|"
										+ "ԭ������������^|ԭ��������֤������^|������������^|֤������^|֤������^|�Ա�^|��������^|"
										+ "��Ч����^|���շ���^|ְҵ����^|��������^|������^|�˺�^|����������ʡ^|������������^|"
										+ "��������^|�Ƿ�α�׼��^|�籣���^|ְ������^|��˾ʱ��^|����^|��н^|Ա����^|"
										+ "֤���Ƿ���^|֤����Ч��^|���ڷֹ�˾^|���ڲ���^|�������˱���^|�����ͻ�Ⱥ����^|"
										+ "������^|�籣��^|��������^|��������^|΢�ź�^|��ϵ�绰^|�ƶ��绰^|"
										+ "��ϵ��ַ(ʡ)^|��ϵ��ַ(��)^|��ϵ��ַ(��/��)^|��ϸ��ַ^|���������Ϣ";
	
	//�������嵥����
	var tBnfTitle = "�����������^|������˳��^|����^|֤������^|֤������^|�Ա�^|��������^|"
								+ "�뱻�����˹�ϵ^|�������(С��)^|���������Ϣ";
							
	fm.SheetTitle[0].value = tInsuredTitle;
	fm.SheetTitle[1].value = tBnfTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
	tSQLInfo.setSqlId("EdorInsuredDealSql4");
	tSQLInfo.addSubPara(tBatchNo);
	
	fm.SheetSql[0].value = tSQLInfo.getString();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
	tSQLInfo.setSqlId("EdorInsuredDealSql5");
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
		queryClick();
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
	
	if (fm.Operate.value=="SELDEL") {
		
		var tCount = InsuredListGrid.mulLineCount;
		var tFlag = false;
		
		for (var i=0;i<tCount;i++) {
			
			if (InsuredListGrid.getChkNo(i)) {
				
				var tSerialNo = InsuredListGrid.getRowColData(i, 1);
				if (tSerialNo!=null && tSerialNo!="") {
					tFlag = true;
				}
			}
		}
		
		if (!tFlag) {
			alert("��ѡ�д�ɾ������Ϣ��");
			return false;
		}
	} else if (fm.Operate.value=="CONDEL") {
		
		if (fm.EdorType.value=="" && fm.InsuredName.value=="" && fm.InsuredIDNo.value=="" && fm.State.value=="") {
			alert("��ѯ��������ȫΪ�գ�");
			return false;
		}
	}
	
	return true;
}
