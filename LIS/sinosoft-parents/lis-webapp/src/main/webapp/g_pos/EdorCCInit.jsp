<%
/***************************************************************
 * <p>ProName:EdorCCInput.jsp</p>
 * <p>Title:  �����չ��������۱��</p>
 * <p>Description:�����չ��������۱��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-25
 ****************************************************************/
%>


<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
 		initButton();
		initInpBox();
		initParam();
		calInitPrem();

	} catch (re) {
		alert("��ʼ���������!");
	}
}


/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�
 */
function initInpBox() {
	
	try {
		
		initPolicyInfo();
		initSACount();
		initPrem();
		initReason();
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		if(tActivityID=='1800401002'){
			divButton01.style.display=''
		}else {
			divButton01.style.display='none'
		}
	} catch (ex) {
		alert("��ʼ����ť����");
	}
}

/**
 * ��null���ַ���תΪ��
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}
</script>
