<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RIDataModifyInit.jsp
	//�����ܣ�ҵ�����ݵ���
	//�������ڣ�2011-07-30
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">
	function initForm() {
		try {
			initMul11Grid();
			initMul13Grid();
		} catch (re) {
			myAlert("RIDataModifyInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}

	function initMul11Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;
			
			iArray[1] = new Array();
            iArray[1][0] = "�¼����";
            iArray[1][1] = "60px";
            iArray[1][2] = 100;
            iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "��ͬ���";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "�������˱��";
			iArray[3][1] = "90px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "������������";
			iArray[4][1] = "90px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "�ֳ�����";
			iArray[5][1] = "60px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "����";
			iArray[6][1] = "30px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "������";
			iArray[7][1] = "30px";
			iArray[7][2] = 100;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "ҵ������";
			iArray[8][1] = "60px";
			iArray[8][2] = 100;
			iArray[8][3] = 0;

			Mul11Grid = new MulLineEnter("fm", "Mul11Grid");

			Mul11Grid.mulLineCount = 0;
			Mul11Grid.displayTitle = 1;
			Mul11Grid.canSel = 1;
			Mul11Grid.canChk = 0;
			Mul11Grid.hiddenPlus = 1;
			Mul11Grid.hiddenSubtraction = 1;

			Mul11Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
	function initMul13Grid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���";
			iArray[0][1] = "30px";
			iArray[0][2] = 100;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "�ֶδ���";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 2;
			iArray[1][4] = "ridatamodifycol";
			iArray[1][5] = "1|2"; //�����ô���ֱ���ڵ�1��2
			iArray[1][6] = "0|1";
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "�ֶ�����";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "ԭʼ��ֵ";
			iArray[3][1] = "60px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "�޸���ֵ";
			iArray[4][1] = "60px";
			iArray[4][2] = 100;
			iArray[4][3] = 1;

			Mul13Grid = new MulLineEnter("fm", "Mul13Grid");
			Mul13Grid.mulLineCount = 0;
			Mul13Grid.displayTitle = 1;
			Mul13Grid.canSel = 0;
			Mul13Grid.canChk = 0;
			Mul13Grid.hiddenPlus = 0;
			Mul13Grid.hiddenSubtraction = 0;
			Mul13Grid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
