/***************************************************************
 * <p>ProName��LLClaimControlImpInput.js</p>
 * <p>Title���������ο�����Ϣ����</p>
 * <p>Description���������ο�����Ϣ����</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-04-23
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��������
 */
function submitImport() {
	
	var filePath = uploadfm.UploadPath.value;
	
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
	if (fileName.length>=100) {
		
		alert("�ļ������Ȳ��ܳ���100��");
		return false;
	}
	
	var i = 0;
	var showStr = "���ڵ��룬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
	uploadfm.ImpGrpContNo.value = mGrpContNo;
	
	uploadfm.ImpOperate.value = "EXCELIMP";	
	
	uploadfm.action = "./LLClaimControlImpSave.jsp?OtherNoType=CM&OtherNo="+mGrpContNo+"&Operate="+uploadfm.ImpOperate.value+"&AttachType=09";
	uploadfm.submit();
}
/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
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
	}	
}
/**
 * ���ص���ģ��
 */
function getImportForm() {
	
	window.open("../template/LLClmControlImp.xlsx");
}
/**
 * ����
 */
function goBack() {

	top.close();
}
