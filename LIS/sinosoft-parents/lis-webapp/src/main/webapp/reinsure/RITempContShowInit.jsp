<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RITempContShowInit.jsp
	//�����ܣ��ٷ���˲�ѯ
	//�������ڣ�2011-11-09
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">
	//�����ĳ�ʼ��������¼���֣�
	function initInpBox() {

		//������ֻ��������ת����־ 
		fm.DeTailFlag.value = "2"; //1-������ 2-������ 
	}

	// �����б�ĳ�ʼ��
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("��ReInsureInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			//initInpBox();
			//initSelBox();
			initIndTempToalListGrid();
			initIndTempListGrid();
			//��ʾ�����ٷ������¼
			//QueryPolInfo();

		} catch (re) {
			myAlert("LRTempInsuManInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
		//ReInsureAudit();
		//QueryReInsureAudit();
	}
	//����������Ϣ
	function initIndTempToalListGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //���������О����̖�������o���x�����Ҳ��@ʾ��
			iArray[0][1] = "30px"; //�Ќ�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[1] = new Array();
			iArray[1][0] = "�ܵ�Ͷ��������"; //����
			iArray[1][1] = "0px"; //�Ќ�
			iArray[1][2] = 100; //�����ֵ
			iArray[1][3] = 3; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[2] = new Array();
			iArray[2][0] = "Ͷ��������"; //����
			iArray[2][1] = "100px"; //�Ќ�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[3] = new Array();
			iArray[3][0] = "����״̬"; //����
			iArray[3][1] = "60px"; //�Ќ�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[4] = new Array();
			iArray[4][0] = "���ֺ�"; //����
			iArray[4][1] = "60px"; //�Ќ�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[5] = new Array();
			iArray[5][0] = "������"; //����
			iArray[5][1] = "60px"; //�Ќ�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[6] = new Array();
			iArray[6][0] = "���M"; //����
			iArray[6][1] = "60px"; //�Ќ�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[7] = new Array();
			iArray[7][0] = "���~"; //����
			iArray[7][1] = "60px"; //�Ќ�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[8] = new Array();
			iArray[8][0] = "���ձ���"; //����
			iArray[8][1] = "60px"; //�Ќ�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[9] = new Array();
			iArray[9][0] = "��������"; //����
			iArray[9][1] = "60px"; //�Ќ�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[10] = new Array();
			iArray[10][0] = "����״̬����״̬����"; //����
			iArray[10][1] = "0px"; //�Ќ�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 3; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[11] = new Array();
			iArray[11][0] = "���к�"; //����
			iArray[11][1] = "0px"; //�Ќ�
			iArray[11][2] = 100; //�����ֵ
			iArray[11][3] = 3; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			IndTempToalListGrid = new MulLineEnter("fm", "IndTempToalListGrid");
			IndTempToalListGrid.mulLineCount = 0;
			IndTempToalListGrid.displayTitle = 1;
			IndTempToalListGrid.locked = 1;
			IndTempToalListGrid.hiddenPlus = 1;
			IndTempToalListGrid.canSel = 1;
			IndTempToalListGrid.canChk = 0;
			IndTempToalListGrid.selBoxEventFuncName = "listSelect";
			IndTempToalListGrid.hiddenSubtraction = 1;
			IndTempToalListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
	//���˱�����Ϣ
	function initIndTempListGrid() {
		var iArray = new Array();
		try {

			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[1] = new Array();
			iArray[1][0] = "������"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "30px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[2] = new Array();
			iArray[2][0] = "�������ֺ�"; //����
			iArray[2][1] = "50px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[3] = new Array();
			iArray[3][0] = "��������"; //����
			iArray[3][1] = "50px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[4] = new Array();
			iArray[4][0] = "���ִ���"; //����
			iArray[4][1] = "50px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[5] = new Array();
			iArray[5][0] = "���α���"; //����
			iArray[5][1] = "50px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[6] = new Array();
			iArray[6][0] = "���M"; //����
			iArray[6][1] = "50px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[7] = new Array();
			iArray[7][0] = "���~"; //����
			iArray[7][1] = "50px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[8] = new Array();
			iArray[8][0] = "���ձ���"; //����
			iArray[8][1] = "60px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[9] = new Array();
			iArray[9][0] = "�˱�����"; //����
			iArray[9][1] = "30px"; //�п�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[10] = new Array();
			iArray[10][0] = "�˱������"; //����
			iArray[10][1] = "60px"; //�п�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[11] = new Array();
			iArray[11][0] = "�ٷֺ�ͬ"; //����
			iArray[11][1] = "100px"; //�Ќ�
			iArray[11][2] = 100; //�����ֵ
			iArray[11][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			iArray[12] = new Array();
			iArray[12][0] = "�ٷַ���"; //����
			iArray[12][1] = "100px"; //�Ќ�
			iArray[12][2] = 100; //�����ֵ
			iArray[12][3] = 0; //�Ƿ����Sݔ��,1��ʾ���S��0��ʾ�����S

			IndTempListGrid = new MulLineEnter("fm", "IndTempListGrid");
			//��Щ���Ա�����loadMulLineǰ
			IndTempListGrid.mulLineCount = 0;
			IndTempListGrid.displayTitle = 1;
			IndTempListGrid.locked = 1;
			IndTempListGrid.hiddenPlus = 1;
			IndTempListGrid.canSel = 0;
			IndTempListGrid.canChk = 0;
			//IndTempListGrid.selBoxEventFuncName = "listSelect"; 
			IndTempListGrid.hiddenSubtraction = 1;
			IndTempListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
