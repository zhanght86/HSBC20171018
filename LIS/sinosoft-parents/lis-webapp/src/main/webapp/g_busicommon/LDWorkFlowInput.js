/***************************************************************
 * <p>ProName��LDWorkFlowInput.js</p>
 * <p>Title������������</p>
 * <p>Description������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2015-11-09
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ����������
 */
function workFlowClick() {
	
	mOperate = "INSERT";
	
	//ϵͳ��У�鷽��
	if (!verifyInput()) {
		return false;
	}
	
	if (fm.SubType.value == "21001" || fm.SubType.value == "21002" || fm.SubType.value == "21003") {
		
		 if (fm.PropType.value == "") {
		 	alert("Ͷ�������Ͳ���Ϊ�գ�");
			return false;
		 }
		 
		 if (fm.PropType.value == "1" && fm.DocCode.value == "") {
		 	alert("ҵ��Ų���Ϊ�գ�");
			return false;
		 }
	} else if (fm.SubType.value == "22001") {
		
		if (fm.DocCode.value == "") {
			alert("ҵ��Ų���Ϊ�գ�");
			return false;
		}
	}
	
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
	var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
  
	showInfo.focus();
	fm.action = "./LDWorkFlowSave.jsp?Operate="+mOperate;
	fm.submit();
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
		var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
}

/**
 * ����ǰ̨ҳ�������������
 **/
function returnShowCodeList (value1, value2, value3) {

	if (value1=='subtypedetail') {
		
		if (isEmpty(fm.BussType)) {
			alert("����ѡ��ҵ�����ͣ�");
			return false;
		}
		
		return showCodeList(value1,value2,value3,null,fm.BussType.value,'busstype','1',180);
	}
}

/**
 * ������ѡ�����
 */
function afterCodeSelect(tSelectValue, tObj) {
	
	if (tSelectValue=='esbusstype') {
			
		DivDocCode.style.display="none";
		id1.style.display="none";
		id2.style.display="none";
		id3.style.display="none";
		id4.style.display="none";
		id5.style.display="none";
		id6.style.display="none";
		id7.style.display="none";
		id8.style.display="none";
		
		fm.SubType.value = "";
		fm.SubTypeName.value = "";
		fm.PropType.value = "";
		fm.PropTypeName.value = "";
		fm.DocCode.value = "";
	} else if (tSelectValue=='subtypedetail') {
		
		if (tObj.value == "21001" || tObj.value == "21002" || tObj.value == "21003") {
			
			DivDocCode.style.display="";
			id1.style.display="";
			id2.style.display="";
			id3.style.display="none";
			id4.style.display="none";
			id5.style.display="none";
			id6.style.display="none";
			id7.style.display="none";
			id8.style.display="none";
			
			fm.PropType.value = "";
			fm.PropTypeName.value = "";
			fm.DocCode.value = "";
		} else if (tObj.value == "22001") {
			
			DivDocCode.style.display="";
			id1.style.display="none";
			id2.style.display="none";
			id3.style.display="";
			id4.style.display="";
			id5.style.display="";
			id6.style.display="";
			id7.style.display="";
			id8.style.display="";
			
			fm.PropType.value = "";
			fm.PropTypeName.value = "";
			fm.DocCode.value = "";
		}
	} else if (tSelectValue=='proptype') {
		
		if (tObj.value == "1") {//��ӡ
			
			id3.style.display="";
			id4.style.display="";
			id5.style.display="";
			id6.style.display="";
			id7.style.display="none";
			id8.style.display="none";
			
			fm.DocCode.value = "";
		} else if (tObj.value == "2") {//Ԥӡ
			
			id3.style.display="none";
			id4.style.display="none";
			id5.style.display="";
			id6.style.display="";
			id7.style.display="";
			id8.style.display="";
			
			fm.DocCode.value = "";
		}
	}
}