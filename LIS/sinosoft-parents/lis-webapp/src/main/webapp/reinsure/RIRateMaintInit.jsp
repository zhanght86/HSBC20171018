<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RIRateMaintInit.jsp
	//�����ܣ�����ά��
	//�������ڣ�2011/6/17
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">
	function initForm() {
		try {
			initMul10Grid();
		} catch (re) {
			myAlert("RIRateMaintInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}

	function initMul10Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "���ʱ��";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 3;

			iArray[2] = new Array();
			iArray[2][0] = "����";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "������";
			iArray[3][1] = "60px";
			iArray[3][2] = 100;
			iArray[3][3] = 3;

			iArray[4] = new Array();
			iArray[4][0] = "��������";
			iArray[4][1] = "60px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "����ֵ";
			iArray[5][1] = "45px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "��Ч����";
			iArray[6][1] = "60px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "ʧЧ����";
			iArray[7][1] = "60px";
			iArray[7][2] = 100;
			iArray[7][3] = 0;

			Mul10Grid = new MulLineEnter("fm", "Mul10Grid");

			Mul10Grid.mulLineCount = 0;
			Mul10Grid.displayTitle = 1;
			Mul10Grid.canSel = 1;
			Mul10Grid.hiddenPlus=1;
			Mul10Grid.hiddenSubtraction=1;
			Mul10Grid.selBoxEventFuncName = "showFormInfo";
			Mul10Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
