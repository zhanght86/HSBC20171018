<%@include file="/i18n/language.jsp"%>

<%
	//Creator :�ű�
	//Date :2008-06-04
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String Operator = tG.Operator;
	String Comcode = tG.ManageCom;
	String CurrentDate = PubFun.getCurrentDate();
	String tCurrentYear = StrTool.getVisaYear(CurrentDate);
	String tCurrentMonth = StrTool.getVisaMonth(CurrentDate);
	String tCurrentDate = StrTool.getVisaDay(CurrentDate);
	String tFeeTableNo = request.getParameter("FeeTableNo");
%>
<script type="text/javascript">
function initInpBox() {
		try {
			fm.FeeTableNo.value = "<%=tFeeTableNo%>";
			var strSQL = "select a.feetablename from RIFeeRateTableDef a where FeeTableNo='"
					+ fm.FeeTableNo.value + "'";
			var arrResult = easyExecSql(strSQL);

			if (arrResult != null || arrResult != "") {
				fm.FeeTableName.value = arrResult[0][0];
			}
			fm.SaveDataName.value="RIFeeRateTable";
			fm.SaveDataNameName.value="ͨ�÷��ʱ�";
			fm.State.value = "02";
			fm.stateName.value = "δ��Ч";
		} catch (ex) {
			myAlert("���г�ʼ��ʱ���ִ���");
		}
	};

	// ������ĳ�ʼ��
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("��LRContInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
			initFeeRateBatchGrid();
			queryFeeRateBatch();

		} catch (re) {
			myAlert("ReContManageInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}

	//�ٱ���ͬǩ�����б��ʼ��
	function initFeeRateBatchGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���";
			iArray[0][1] = "30px";
			iArray[0][2] = 10;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "���ʱ���";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "���ʱ�����";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "���ʱ����κ�";
			iArray[3][1] = "80px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "���ʱ���Ч����";
			iArray[4][1] = "80px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "���ʱ�ʧЧ����";
			iArray[5][1] = "80px";
			iArray[5][2] = 200;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "�洢��";
			iArray[6][1] = "0px";
			iArray[6][2] = 200;
			iArray[6][3] = 3;

			iArray[7] = new Array();
			iArray[7][0] = "���ʱ�����";
			iArray[7][1] = "45px";
			iArray[7][2] = 200;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "���ʱ�״̬";
			iArray[8][1] = "0px";
			iArray[8][2] = 200;
			iArray[8][3] = 3;

			iArray[9] = new Array();
			iArray[9][0] = "���ʱ�״̬";
			iArray[9][1] = "45px";
			iArray[9][2] = 200;
			iArray[9][3] = 0;

			iArray[10] = new Array();
			iArray[10][0] = "�洢������";
			iArray[10][1] = "60px";
			iArray[10][2] = 200;
			iArray[10][3] = 0;

			FeeRateBatchGrid = new MulLineEnter("fm", "FeeRateBatchGrid");
			FeeRateBatchGrid.mulLineCount = 0;
			FeeRateBatchGrid.displayTitle = 1;
			FeeRateBatchGrid.canSel = 1;
			FeeRateBatchGrid.hiddenPlus = 1;
			FeeRateBatchGrid.hiddenSubtraction = 1;
			FeeRateBatchGrid.selBoxEventFuncName = "ShowBatch";
			FeeRateBatchGrid.loadMulLine(iArray);
		}

		catch (ex) {
			myAlert("��ʼ��ʱ����:" + ex);
		}
	}
</script>
