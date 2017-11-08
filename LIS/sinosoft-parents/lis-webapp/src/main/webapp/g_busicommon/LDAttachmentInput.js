/***************************************************************
 * <p>ProName��LDAttachmentInput.js</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var mAttachID = "";
var mAttachType = "";
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {
	
	
	var tQuotUpLoadNode = "";
	if (tOtherNoType=="QUOT") {// ����ѯ�۽ڵ�����
		
		if (tUploadNode=="0800100001") {//ѯ��¼��
			
			tQuotUpLoadNode = " and a.uploadnode in('0800100001') ";
		} else if (tUploadNode=="0800100002") {//�к�
			
			tQuotUpLoadNode = " and a.uploadnode in('0800100001','0800100002') ";
		} else if (tUploadNode=="0800100003") {//�ֺ�
			
			tQuotUpLoadNode = " and a.uploadnode in('0800100001','0800100002','0800100003') ";
		} 
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_busicommon.LDAttachmentSql");
	tSQLInfo.setSqlId("LDAttachmentSql1");
	tSQLInfo.addSubPara(tOtherNoType);
	tSQLInfo.addSubPara(tOtherNo);
	if (tSubOtherNo==null || tSubOtherNo=="" || tSubOtherNo=="null") {
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara(tSubOtherNo);
	}
	tSQLInfo.addSubPara(tQuotUpLoadNode);
	
	turnPage1.queryModal(tSQLInfo.getString(), AttachmentGrid,1,1);
}

/**
 * �ϴ�����
 */
function upLoadClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	
	mAttachType = fmupload.all('AttachType').value;
	var tFileName = fmupload.all('UploadPath').value;
	var tExtenName = "";
	
	if (tFileName.indexOf("\\")>0) {
		tFileName = tFileName.substring(tFileName.lastIndexOf("\\")+1);
	}
	if (tFileName.indexOf("/")>0 ) {
		tFileName = tFileName.substring(tFileName.lastIndexOf("/")+1);
	}
	
	if (tFileName.indexOf(".")>0 ) {
		tExtenName = tFileName.substring(tFileName.lastIndexOf(".")+1);
	}
	submitForm();
}

/**
 * ���ظ���
 */
function downLoadClick() {
	
	var tSelNo = AttachmentGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ���صĸ�����");
		return false;
	}
	
	var tFilePath = AttachmentGrid.getRowColData(tSelNo-1, 5);
	var tFileName = AttachmentGrid.getRowColData(tSelNo-1, 3);
	
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}

/**
 * ɾ������
 */
function deleteClick() {
	
	var tSelNo = AttachmentGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫɾ���ĸ�����");
		return false;
	}
	
	mAttachID = AttachmentGrid.getRowColData(tSelNo-1, 1);
	mOperate = "DELETE";
	submitForm();
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
	fmupload.action = "./LDAttachmentSave.jsp?OtherNoType="+tOtherNoType+"&OtherNo="+tOtherNo+"&SubOtherNo="+tSubOtherNo+"&UploadNode="+tUploadNode+"&Operate="+mOperate+"&AttachID="+mAttachID+"&AttachType="+mAttachType;
	fmupload.submit();
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
	}
	queryClick();
	fmupload.AttachType.value = "";
	fmupload.AttachTypeName.value = "";
	fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
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
