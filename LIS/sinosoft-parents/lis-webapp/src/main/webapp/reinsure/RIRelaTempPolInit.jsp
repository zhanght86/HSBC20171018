<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�LRRelaTempPolInit.jsp
	//�����ܣ��������α���
	//�������ڣ�2007-09-29 11:10:36
	//������  ��zhangbin
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%
	String tRIContNo = request.getParameter("RIContNo");
	String tRIPreceptNo = request.getParameter("RIPreceptNo");
	String tCalFeeType = request.getParameter("CalFeeType");
	String tCalFeeTerm = request.getParameter("CalFeeTerm");
	String tOperateNo = request.getParameter("OperateNo");
	String tCodeType = request.getParameter("CodeType");
	String tSerialNo = request.getParameter("SerialNo");
	String RIPolno = request.getParameter("RIPolno");
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(){
	fm.RIContNo.value ='<%=tRIContNo%>';
	fm.RIPreceptNo.value ='<%=tRIPreceptNo%>';
	fm.CalFeeType.value ='<%=tCalFeeType%>';
	fm.CalFeeTerm.value ='<%=tCalFeeTerm%>';
	fm.OperateNo.value ='<%=tOperateNo%>';
	fm.OperateType.value ='<%=tCodeType%>';
	fm.SerialNo.value ='<%=tSerialNo%>';
	fm.RIPolno.value ='<%=RIPolno%>';
		//divGrpTempInsuListTitle.style.display='';

		//������ֻ��������ת����־ 
		fm.DeTailFlag.value = "2"; //1-������ 2-������ 

	}

	// ������ĳ�ʼ��
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("��ReInsureInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			//initSelBox();
			initIndTempListGrid();
			initIndRelaListGrid();

			QueryPagePolInfo(); //��ʾ�ٷֱ�����Ϣ
		} catch (re) {
			myAlert("TempReInAnswerInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}

	function initIndTempListGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[1] = new Array();
			iArray[1][0] = "������"; //����
			iArray[1][1] = "80px"; //�п�
			iArray[1][2] = 100; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[2] = new Array();
			iArray[2][0] = "��������"; //����
			iArray[2][1] = "80px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[3] = new Array();
			iArray[3][0] = "���ִ���"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[4] = new Array();
			iArray[4][0] = "���α���"; //����
			iArray[4][1] = "60px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[5] = new Array();
			iArray[5][0] = "���M"; //����
			iArray[5][1] = "60px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[6] = new Array();
			iArray[6][0] = "���~"; //����
			iArray[6][1] = "60px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[7] = new Array();
			iArray[7][0] = "���ձ���"; //����
			iArray[7][1] = "60px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[8] = new Array();
			iArray[8][0] = "Ͷ������"; //����
			iArray[8][1] = "100px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[9] = new Array();
			iArray[9][0] = "�˱�����"; //����
			iArray[9][1] = "100px"; //�п�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[10] = new Array();
			iArray[10][0] = "��������code"; //����
			iArray[10][1] = "0px"; //�п�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������

			IndTempListGrid = new MulLineEnter("fm", "IndTempListGrid");
			//��Щ���Ա�����loadMulLineǰ
			IndTempListGrid.mulLineCount = 0;
			IndTempListGrid.displayTitle = 1;
			IndTempListGrid.locked = 1;
			IndTempListGrid.hiddenPlus = 1;
			IndTempListGrid.canSel = 0;
			IndTempListGrid.canChk = 1;
			IndTempListGrid.hiddenSubtraction = 1;
			IndTempListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	function initIndRelaListGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[1] = new Array();
			iArray[1][0] = "������"; //����
			iArray[1][1] = "80px"; //�п�
			iArray[1][2] = 100; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[2] = new Array();
			iArray[2][0] = "��������"; //����
			iArray[2][1] = "80px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[3] = new Array();
			iArray[3][0] = "���ִ���"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[4] = new Array();
			iArray[4][0] = "���α���"; //����
			iArray[4][1] = "60px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[5] = new Array();
			iArray[5][0] = "���M"; //����
			iArray[5][1] = "60px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[6] = new Array();
			iArray[6][0] = "���~"; //����
			iArray[6][1] = "60px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[7] = new Array();
			iArray[7][0] = "���ձ���"; //����
			iArray[7][1] = "60px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[8] = new Array();
			iArray[8][0] = "Ͷ������"; //����
			iArray[8][1] = "100px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[9] = new Array();
			iArray[9][0] = "�˱�����"; //����
			iArray[9][1] = "100px"; //�п�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[10] = new Array();
			iArray[10][0] = "��������code"; //����
			iArray[10][1] = "80px"; //�п�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[11] = new Array();
			iArray[11][0] = "�ٱ��������";//����
			iArray[11][1] = "60px"; //�п�
			iArray[11][2] = 100; //�����ֵ
			iArray[11][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			IndRelaListGrid = new MulLineEnter("fm", "IndRelaListGrid");
			//��Щ���Ա�����loadMulLineǰ
			IndRelaListGrid.mulLineCount = 0;
			IndRelaListGrid.displayTitle = 1;
			IndRelaListGrid.locked = 1;
			IndRelaListGrid.hiddenPlus = 1;
			IndRelaListGrid.canSel = 0;
			IndRelaListGrid.canChk = 1;
			IndRelaListGrid.hiddenSubtraction = 1;
			IndRelaListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	function initRelaListGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[1] = new Array();
			iArray[1][0] = "ӡˢ����"; //����
			iArray[1][1] = "100px"; //�п�
			iArray[1][2] = 100; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[2] = new Array();
			iArray[2][0] = "����Ͷ��������"; //����
			iArray[2][1] = "100px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[3] = new Array();
			iArray[3][0] = "���ϼƻ�"; //����
			iArray[3][1] = "60px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[4] = new Array();
			iArray[4][0] = "���˺�ͬ����"; //����
			iArray[4][1] = "100px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[5] = new Array();
			iArray[5][0] = "Ͷ�������ֺ���"; //����
			iArray[5][1] = "100px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[6] = new Array();
			iArray[6][0] = "����������"; //����
			iArray[6][1] = "60px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[7] = new Array();
			iArray[7][0] = "�����˺���"; //����
			iArray[7][1] = "100px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[8] = new Array();
			iArray[8][0] = "���ִ���"; //����
			iArray[8][1] = "70px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[9] = new Array();
			iArray[9][0] = "���δ���"; //����
			iArray[9][1] = "70px"; //�п�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[10] = new Array();
			iArray[10][0] = "������ʽ"; //����
			iArray[10][1] = "120px"; //�п�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[11] = new Array();
			iArray[11][0] = "�ٷֺ˱�����"; //����
			iArray[11][1] = "100px"; //�п�
			iArray[11][2] = 100; //�����ֵ
			iArray[11][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[12] = new Array();
			iArray[12][0] = "������ʽ���"; //����
			iArray[12][1] = "100px"; //�п�
			iArray[12][2] = 100; //�����ֵ
			iArray[12][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������

			RelaListGrid = new MulLineEnter("fm", "RelaListGrid");
			//��Щ���Ա�����loadMulLineǰ
			RelaListGrid.mulLineCount = 0;
			RelaListGrid.displayTitle = 1;
			RelaListGrid.locked = 1;
			RelaListGrid.hiddenPlus = 1;
			RelaListGrid.canSel = 0;
			RelaListGrid.canChk = 1;
			RelaListGrid.hiddenSubtraction = 1;
			RelaListGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
