<%
/***************************************************************
 * <p>ProName��LDWorkFlowInit.jsp</p>
 * <p>Title������������</p>
 * <p>Description������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2015-11-09
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
	} catch (re) {
		alert("��ʼ���������");
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
		
		DivDocCode.style.display="none";
		id1.style.display="none";
		id2.style.display="none";
		id3.style.display="none";
		id4.style.display="none";
		id5.style.display="none";
		id6.style.display="none";
		id7.style.display="none";
		id8.style.display="none";
		
		fm.ScanManageCom.value = "";
		fm.ScanManageComName.value = "";
		fm.BussType.value = "";
		fm.BussTypeName.value = "";
		fm.SubType.value = "";
		fm.SubTypeName.value = "";
		fm.PropType.value = "";
		fm.PropTypeName.value = "";
		fm.DocCode.value = "";
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		
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
