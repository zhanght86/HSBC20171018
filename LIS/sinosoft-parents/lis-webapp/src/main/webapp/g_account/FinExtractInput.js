/***************************************************************
 * <p>ProName��FinExtractInput.js</p>
 * <p>Title����Ʒ�¼��ȡ</p>
 * <p>Description����Ʒ�¼��ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬

var tSQLInfo = new SqlClass();

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
	fm.Operate.value = mOperate;
	fm.action = "./FinExtractSave.jsp";
	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content, tFilePath, tFileName) {
	
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
		
		if (mOperate=="download") {
			window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName
		}
	}	
}

/**
 * ��ѯ��Ʒ�¼��Ϣ
 */
function queryData() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	var tStartDate = fm.StartDate.value;
	var tEndDate = fm.EndDate.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinExtractSql");
	tSQLInfo.setSqlId("FinExtractSql1");
	tSQLInfo.addSubPara(tStartDate);
	tSQLInfo.addSubPara(tEndDate);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinExtractGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("û�в�ѯ�����ݣ�");
		return false;
	}
	
}

//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	var tStartDate = fm.StartDate.value;
	var tEndDate = fm.EndDate.value;
	
	//�������
	var tTitle = "SourceBatchID^|AccountingDate^|CurrencyCode^|CurrencyConversionDate^|CurrencyConversionRate^|CurrencyConversionType^|EnteredDr^|EnteredCr^|AccountedDr^|AccountedCr^|ActualFlag^|Segment1^|Segment2^|Segment3^|Segment4^|Segment5^|Segment6^|Segment7^|Segment8^|Segment9^|LineDescription^|Attribute1^|Attribute2^|Attribute3^|Attribute4^|Attribute5^|Attribute6^|Attribute7^|Attribute8^|Attribute9^|Attribute10^|Attribute11^|Attribute12^|Attribute13^|Attribute14^|Attribute15";
	//��������SQL
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinExtractSql");
	tSQLInfo.setSqlId("FinExtractSql2");
	tSQLInfo.addSubPara(tStartDate);
	tSQLInfo.addSubPara(tEndDate);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}

/**
 * ��ȡ����
 */
function extractData() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "extract";
	submitForm();
}

/**
 * ɾ������
 */
function deleteData() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "delete";
	submitForm();
}

/**
 * ���ز���ӿ��ļ�
 */
function downloadData() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate = "download";
	submitForm();
}
