<%
/***************************************************************
 * <p>ProName��LCContPlanDetailInit.jsp</p>
 * <p>Title�� ������ϸ</p>
 * <p>Description�� ������ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<script language="JavaScript">
var OnPage = 1;//��ǰ��ʾ��ҳ��
var RowNum = 0;//��¼������
var PageNum = 0;//��¼����ҳ��
var StartNum = 1;//ҳ����ʾ��ʼ��¼��
var strQueryResult;

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
	
		if(initContPlanDetailPageInfo(tPolicyNo)){
			showContPlanDetail(fm, strQueryResult,StartNum, tContPlanType);
		}
		
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
