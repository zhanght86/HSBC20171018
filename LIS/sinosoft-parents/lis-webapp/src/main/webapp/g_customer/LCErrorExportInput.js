/***************************************************************
 * <p>ProName��LCErrorExportInput.js</p>
 * <p>Title��������Ϣ����</p>
 * <p>Description��������Ϣ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-07-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ����������Ϣ��ѯ
 */
function queryClick() {
	if(fm.StartImportDate.value>fm.EndImportDate.value){
		alert("��������Ӧ���ڵ���ֹ��!");
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LCErrorExportSql");
	tSQLInfo.setSqlId("LCErrorExportSql1");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(fm.StartImportDate.value);
	tSQLInfo.addSubPara(fm.EndImportDate.value);
	turnPage1.queryModal(tSQLInfo.getString(), BatchGrid,1,1);
	if(!turnPage1.strQueryResult){
		alert("δ��ѯ��������������Ϣ!");
		return false;
	}
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
	
	var tBatchNo = BatchGrid.getRowColData(tSelNo-1, 1);
	
	fm.SheetName.value = "���֤��ϴ�嵥";
	
	//�������˷�����Ϣ
	var tInsuredTitle = "���^|������������^|�Ա�^|��������^|֤������^|֤������^|"
										+ "��������^|����ʧ��ԭ��";
							
	fm.SheetTitle.value = tInsuredTitle;
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LCErrorExportSql");
	tSQLInfo.setSqlId("LCErrorExportSql2");
	tSQLInfo.addSubPara(tBatchNo);
	
	fm.SheetSql.value = tSQLInfo.getString();

	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
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
