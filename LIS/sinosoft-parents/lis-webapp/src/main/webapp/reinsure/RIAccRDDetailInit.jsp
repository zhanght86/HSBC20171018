<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RIAccRDInit.jsp
	//�����ܣ��ֳ����ζ���
	//�������ڣ�2011/6/16
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">
	function initInpBox() {
		fm.all('AccumulateDefNO').value ="<%=request.getParameter("AccumulateDefNO")%>";
	}

	function initForm() {
		try {
			initInpBox();
			initRIAccRiskDutyGrid();
			queryLink();
		} catch (re) {
			myAlert("RIAccRDInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}

	function initRIAccRiskDutyGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "���ִ���";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;
			iArray[1][4] = "";
			iArray[1][5] = "1|2"; //�����ô���ֱ���ڵ�1��2
			iArray[1][6] = "0|1";
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "��������";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "���δ���";
			iArray[3][1] = "60px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;
			iArray[3][4] = '';
			iArray[3][5] = "3|4"; //�����ô���ֱ���ڵ�1��2
			iArray[3][6] = "0|1";
			iArray[3][15] = 'riskcode';
			iArray[3][17] = '1';
			iArray[3][19] = 1;

			iArray[4] = new Array();
			iArray[4][0] = "��������";
			iArray[4][1] = "60px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			RIAccRiskDutyGrid = new MulLineEnter("fm", "RIAccRiskDutyGrid");

			RIAccRiskDutyGrid.mulLineCount = 0;
			RIAccRiskDutyGrid.displayTitle = 1;
			RIAccRiskDutyGrid.locked = 0;
			RIAccRiskDutyGrid.canSel = 1;
			RIAccRiskDutyGrid.selBoxEventFuncName = "showRiskDuty"; //��Ӧ�ĺ���������������   
			RIAccRiskDutyGrid.hiddenPlus = 1;
			RIAccRiskDutyGrid.hiddenSubtraction = 1;

			RIAccRiskDutyGrid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
