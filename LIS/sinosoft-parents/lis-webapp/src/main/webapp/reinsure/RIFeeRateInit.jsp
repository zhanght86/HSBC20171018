<%@include file="/i18n/language.jsp"%>

<%
	//Creator :�ű�
	//Date :2006-08-21
%>

<script type="text/javascript">
	function initInpBox() {
		try {
			fm.FeeTableNo.value = "";
			fm.FeeTableName.value = "";
			fm.FeeTableType.value = "";
			fm.ReMark.value = "";
			fm.State.value = "02";
			fm.stateName.value = "δ��Ч";
			fm.FeeTableType.value = "";
			fm.FeeTableTypeName.value = "";
			fm.UseType.value = "";
			fm.UseTypeName.value = "";

		} catch (ex) {
			myAlert("���г�ʼ���ǳ��ִ���");
		}
	};

	// �����б�ĳ�ʼ��
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
			initTableClumnDefGrid();
			verifyFeeRateTableImp();
		} catch (re) {
			myAlert("ReContManageInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}

	//�ٱ���ͬǩ�����б��ʼ��
	function initTableClumnDefGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���";
			iArray[0][1] = "30px";
			iArray[0][2] = 10;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "���ʱ��ֶ�";
			iArray[1][1] = "60px";
			iArray[1][2] = 100;
			iArray[1][3] = 1;
			iArray[1][9] = "���ʱ��ֶ���|notnull";
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "������ֶ���";
			iArray[2][1] = "60px";
			iArray[2][2] = 100;
			iArray[2][3] = 2;
			iArray[2][4] = "ripolrecordcols";
			iArray[2][5] = "2"; //�����ô���ֱ���ڵ�1��2
			iArray[2][6] = "0|1";
			iArray[2][9] = "������ֶ�|notnull";
			iArray[2][19] = 1;

			iArray[3] = new Array();
			iArray[3][0] = "���ʱ��ֶ�������";
			iArray[3][1] = "80px";
			iArray[3][2] = 100;
			iArray[3][3] = 2;
			iArray[3][4] = "rifeecoltype";
			iArray[3][5] = "3|4"; //�����ô���ֱ���ڵ�1��2
			iArray[3][6] = "1|0";
			iArray[3][19] = 1;

			iArray[4] = new Array();
			iArray[4][0] = "���ʱ��ֶ�����";
			iArray[4][1] = "80px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;
			iArray[4][9] = "������ֶ�|notnull";
			iArray[4][19] = 1;

			iArray[5] = new Array();
			iArray[5][0] = "�̶�ֵӳ���ֶ���"; //����
			iArray[5][1] = "80px"; //�п�
			iArray[5][2] = 200; //�����ֵ
			iArray[5][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������   
			iArray[5][4] = "riimpcols";
			iArray[5][5] = "5|6"; //�����ô���ֱ���ڵ�1��2
			iArray[5][6] = "0|1";
			iArray[5][19] = 1;

			iArray[6] = new Array();
			iArray[6][0] = "�̶�ֵӳ���ֶ�";
			iArray[6][1] = "0px";
			iArray[6][2] = 200;
			iArray[6][3] = 3;
			iArray[6][19] = 1;

			iArray[7] = new Array();
			iArray[7][0] = "��������ӳ���ֶ���"; //����
			iArray[7][1] = "80px"; //�п�
			iArray[7][2] = 60; //�����ֵ
			iArray[7][3] = 2; //2��ʾ�Ǵ���ѡ��
			iArray[7][4] = "riimpcols";
			iArray[7][5] = "7|8"; //�����ô���ֱ���ڵ�1��2
			iArray[7][6] = "0|1";
			iArray[7][19] = 1;

			iArray[8] = new Array();
			iArray[8][0] = "���������ֶ�ӳ��";
			iArray[8][1] = "0px";
			iArray[8][2] = 200;
			iArray[8][3] = 3;
			iArray[8][19] = 1;

			iArray[9] = new Array();
			iArray[9][0] = "���������ֶ�ӳ����"; //����
			iArray[9][1] = "90px"; //�п�
			iArray[9][2] = 60; //�����ֵ
			iArray[9][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
			iArray[9][4] = "riimpcols";
			iArray[9][5] = "9|10"; //�����ô���ֱ���ڵ�1��2
			iArray[9][6] = "0|1";
			iArray[9][19] = 1;

			iArray[10] = new Array();
			iArray[10][0] = "���������ֶ�ӳ��";
			iArray[10][1] = "0px";
			iArray[10][2] = 200;
			iArray[10][3] = 3;
			iArray[10][19] = 1;

			TableClumnDefGrid = new MulLineEnter("fm", "TableClumnDefGrid");
			TableClumnDefGrid.mulLineCount = 1;
			TableClumnDefGrid.displayTitle = 1;
			//  TableClumnDefGrid.locked = 0;
			TableClumnDefGrid.canSel = 0;
			//  TableClumnDefGrid.hiddenPlus=0; 
			//  TableClumnDefGrid.hiddenSubtraction=1;
			TableClumnDefGrid.loadMulLine(iArray);
		}

		catch (ex) {
			myAlert("��ʼ��ʱ����:" + ex);
		}
	}
</script>
