/***************************************************************
 * <p>ProName��LSQuotShowImpPremInput.js</p>
 * <p>Title��ѯ�۱��ѵ�����Ϣ</p>
 * <p>Description��ѯ�۱��ѵ�����Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-07-25
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ��Ϣ�б�
 */
function queryImpList() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotShowImpPremSql");
	tSQLInfo.setSqlId("LSQuotShowImpPremSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(fm.BacthNo.value);
	tSQLInfo.addSubPara(fm.ImpState.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), ImpInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ���ر��ѵ��������Ϣ
 */
function expErrorInfo() {
	
	
	var tSelNo = ImpInfoGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	
	var tImpState = ImpInfoGrid.getRowColData(tSelNo-1, 5);
	if (tImpState=="1") {
		alert("�����ε��뱣��ȫ����ȷ�������δ�����Ϣ��");
		return false;
	}
	var tBatchNo = ImpInfoGrid.getRowColData(tSelNo-1, 1);
	mOperate = "EXPERROR";
	//fm.encoding = "application/x-www-form-urlencoded";
	fm.action = "./LSQuotExpPremSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&ActivityID="+ tActivityID+"&BatchNo="+ tBatchNo;
	submitForm(fm);
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
		if (mOperate == "EXPERROR") {
			
			var tFileName = content.substring(content.lastIndexOf("/")+1);
			window.location = "../common/jsp/download.jsp?FilePath="+content+"&FileName="+tFileName;
			
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
		}
	}
}
