/***************************************************************
 * <p>ProName��LSQuotQuestionInput.js</p>
 * <p>Title���ʾ����</p>
 * <p>Description���ʾ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-24
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var mAttachID = "";
var mSubAttachType = "";
var tSQLInfo = new SqlClass();
var s_img =0;
var b_img = 0;
var w  = 0;

/**
 * ��ѯ
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuesnaireSql");
	tSQLInfo.setSqlId("LSQuotQuesnaireSql1");
	tSQLInfo.addSubPara("QUOT");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), QuesnaireGrid,0,1);
}

/**
 * �ϴ�����
 */
function upLoadClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	
	mSubAttachType = fmupload.all('QuesnaireType').value;
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
	
	if (tExtenName!="gif") {
		alert("��֧��gif��ʽ���ļ����أ�");
		return false;
	}
	
	submitForm();
}

/**
 * ���ظ���
 */
function downLoadClick() {
	
	var tSelNo = QuesnaireGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ���ص��ʾ�");
		return false;
	}
	
	var tFilePath = QuesnaireGrid.getRowColData(tSelNo-1, 5);
	var tFileName = QuesnaireGrid.getRowColData(tSelNo-1, 3);
	
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}

/**
 * ɾ������
 */
function deleteClick() {
	
	var tSelNo = QuesnaireGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫɾ�����ʾ�");
		return false;
	}
	
	mAttachID = QuesnaireGrid.getRowColData(tSelNo-1, 1);
	mOperate = "DELETE";
	submitForm();
}

/**
 * �ϴ������ʾ�
 */
function PersonClick() {
	
	//������˺˱�����
	window.open("./LSQuotPastMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID+"&Flag=Quesnaire","������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
	fmupload.action = "../g_busicommon/LDAttachmentSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mOperate+"&AttachID="+mAttachID+"&AttachType=00&SubAttachType="+mSubAttachType;
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
		
		queryClick();
		fmupload.QuesnaireType.value = "";
		fmupload.QuesnaireTypeName.value = "";
		fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
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

/**
 * ��ѯ�ʾ�ͼƬ
 */
function showPages() {

	var tSelNo = QuesnaireGrid.getSelNo();
	if(tSelNo==0 || tSelNo==null) {
		alert("��ѡ��һ���ʾ���Ϣ��");
	}
	var tQuesnairePath = QuesnaireGrid.getRowColData(tSelNo-1, 5);
	var tFileType = QuesnaireGrid.getRowColData(tSelNo-1, 9);
	//	window.open("./ShowPictureMain.jsp?FileFullPath="+tQuesnairePath,"�ʾ����",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	if(tQuesnairePath!=null&&tQuesnairePath!="") {
		
		fm3.service.width = 1200;
		if(w!=0){
			fm3.service.width = w;
		}
		
		fm3.service.src = "../common/jsp/ShowPicture.jsp?FileFullPath="+ tQuesnairePath +"&FileType="+ tFileType;
		fm3.all('divPages').filters.item(0).rotation = "0";
		divPages.style.display = '';
		w = fm3.service.width;
		s_img =0;
		b_img = 0;
	} else {
		fm3.service.src = "";
		fm3.service.width= 0;
		divPages.style.display = 'none';
		alert("û��Ӱ���");
	}
}
