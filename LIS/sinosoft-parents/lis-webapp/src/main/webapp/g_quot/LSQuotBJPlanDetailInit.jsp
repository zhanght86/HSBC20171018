<%
/***************************************************************
 * <p>ProName��LSQuotBJPlanDetailInit.jsp</p>
 * <p>Title�����۵���ӡ--���۷�����ϸ</p>
 * <p>Description�����۵���ӡ--���۷�����ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-20
 ****************************************************************/
%>
<script language="JavaScript">
var OnPage = 1;//��ǰ��ʾ��ҳ��
var RowNum = 0;//��¼������
var PageNum = 0;//��¼����ҳ��
var StartNum = 1;//ҳ����ʾ��ʼ��¼��
var strQueryResult;
var tShow = 1;//������Ѱ�ťչʾ

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tPrint = getPrintState();//modify by songsz 20150210 �޸ĸ��ֶ�Ϊȫ�ֱ���
		initQuotPlanDetailPageInfo(tOfferListNo);
		showQuotPlanDetail(fm, strQueryResult,StartNum, tQuotType, tTranProdType, tShow,tPrint,tQuotQuery);
		
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
