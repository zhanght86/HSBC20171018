<%
/***************************************************************
 * <p>ProName��LSQuotPlanAllDetailInit.jsp</p>
 * <p>Title��������ϸһ��</p>
 * <p>Description��������ϸһ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-06
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
		
		if (tMark == "1") {
			document.all("divConfluence").style.display = "none";
			document.all("divTurnPage").style.display = "none";
		}
		tTranProdType = getProdType(tQuotNo, tQuotBatNo);
		initPubDetailPageInfo(tQuotNo, tQuotBatNo,tSysPlanCode,tPlanCode,tMark);
		showAllPlanDetail(fm, strQueryResult,StartNum, tQuotType, tTranProdType, tActivityID);
		
		if ((tActivityID=="0800100002" || tActivityID=="0800100003" || tActivityID=="0800100004") && tTranProdType!="02") {
			
			document.all("divImp").style.display = "";
		} else {
			document.all("divImp").style.display = "none";
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
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
		tSQLInfo.setSqlId("LSQuotAllDetailSql1");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tTotalArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tTotalArr != null) {
			
			fm.SumPrem.value = tTotalArr[0][0];
			fm.SumNonMedPrem.value = tTotalArr[0][1];
			fm.SumMedPrem.value = tTotalArr[0][2];
			fm.SumPerIllPrem.value = tTotalArr[0][3];
		}
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
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
