<%
/***************************************************************
 * <p>ProName��LCGrpUWInit.jsp</p>
 * <p>Title���˹��˱�</p>
 * <p>Description���˹��˱�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		initInpBox();
		initElements();
	
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ���������
 */
function initOtherParam() {

	try {
	} catch (ex) {
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
		fm.GrpPropNo.value=tGrpPropNo;
		fm.MissionID.value=tMissionID;
		fm.SubMissionID.value=tSubMissionID;
		fm.ActivityID.value=tActivityID;
		
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


</script>
