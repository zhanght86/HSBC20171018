<%@include file="/i18n/language.jsp"%>

<%
	//Creator :�ű�
	//Date :2007-2-7
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String CurrentDate = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
	String Operator = tG.Operator;
%>

<script type="text/javascript">
	function initInpBox() {
		try {
			fm.all("BFFlag").value = "01";
			fm.all("BFFlagName").value = "ҵ����";
		} catch (ex) {
			myAlert("���г�ʼ���ǳ��ִ��󣡣�����");
		}
	}

	// �����б�ĳ�ʼ��
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("2��LRGetBsnsDataInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
			initRIDataGrid();
			initRIDataDetailGrid();
			initRIDataUCoDetailGrid();
		} catch (re) {
			myAlert("3��LRGetBsnsDataInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}

	// �ӿ�ҵ������
	function initRIDataGrid() {
		var iArray = new Array();
		try {
			var i=0;
			
			iArray[i] = new Array();
			iArray[i][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[i][1] = "30px"; //�п�
			iArray[i][2] = 40; //�����ֵ
			iArray[i][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			i++;   			     

			iArray[i] = new Array();
			iArray[i][0] = "EventNo";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 3;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "������";
			iArray[i][1] = "80px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "ҵ������";
			iArray[i][1] = "60px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "ҵ����ϸ";
			iArray[i][1] = "60px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "���ֱ���";
			iArray[i][1] = "60px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "���α���";
			iArray[i][1] = "60px";
			iArray[i][2] = 100;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�����˿ͻ���";
			iArray[i][1] = "100px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "����������";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�������Ա�";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "��ǰ����";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "���̱�־";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "ְҵ���";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "������Ч��";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�������";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�˱����";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 3;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "���ϼƻ�";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 3;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�ٱ�/����";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�ٱ���ͬ���";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�ٱ��������";
			iArray[i][1] = "100px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�ֳ����α��";
			iArray[i][1] = "100px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="��׼����";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="EM�ӷѽ��";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="EM�ӷ�����";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="$/M�ӷѽ��";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="$/M�ӷ�����";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="%�ӷѽ��";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] ="%�ӷ�����";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "��ǰ���ձ���";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "��ǰ����";
			iArray[i][1] = "50px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "���ձ���仯��";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "ת������";
			iArray[i][1] = "45px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "ת������ձ���";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�ۼƱ���";
			iArray[i][1] = "50px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�ۼƷ��ձ���";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "�����";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "ҵ������";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "��������";
			iArray[i][1] = "80px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			iArray[i] = new Array();
			iArray[i][0] = "����״̬";
			iArray[i][1] = "60px";
			iArray[i][2] = 85;
			iArray[i][3] = 0;
			i++;

			RIDataGrid = new MulLineEnter("fm", "RIDataGrid");
			//��Щ���Ա�����loadMulLineǰ
			RIDataGrid.mulLineCount = 1;
			RIDataGrid.displayTitle = 1;
			RIDataGrid.locked = 0;
			RIDataGrid.canSel = 1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			RIDataGrid.selBoxEventFuncName = "queryDetail"; //��Ӧ�ĺ���������������   
			RIDataGrid.hiddenPlus = 1;
			RIDataGrid.hiddenSubtraction = 1;
			RIDataGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	// �ֱ���ϸ��Ϣ
	function initRIDataDetailGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 40; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������   			     

			iArray[1] = new Array();
			iArray[1][0] = "������";
			iArray[1][1] = "80px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "���ֱ���";
			iArray[2][1] = "80px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "���α���";
			iArray[3][1] = "80px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "�ֱ���˾����";
			iArray[4][1] = "80px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "�ֱ���˾����";
			iArray[5][1] = "100px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "�ֱ�����";
			iArray[6][1] = "80px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "�ֱ�����";
			iArray[7][1] = "80px";
			iArray[7][2] = 85;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "�ֱ�����";
			iArray[8][1] = "80px";
			iArray[8][2] = 85;
			iArray[8][3] = 0;

			iArray[9] = new Array();
			iArray[9][0] = "�ֱ��������";
			iArray[9][1] = "80px";
			iArray[9][2] = 85;
			iArray[9][3] = 0;
			
			iArray[10] = new Array();
			iArray[10][0] = "�ֱ�����";
			iArray[10][1] = "80px";
			iArray[10][2] = 85;
			iArray[10][3] = 0;
			
			iArray[11] = new Array();
			iArray[11][0] = "�ֱ�����";
			iArray[11][1] = "80px";
			iArray[11][2] = 85;
			iArray[11][3] = 0;
			
			iArray[12] = new Array();
			iArray[12][0] = "4�ڱ��ѱ��";
			iArray[12][1] = "80px";
			iArray[12][2] = 85;
			iArray[12][3] = 0;

			iArray[13] = new Array();
			iArray[13][0] = "�ֱ�Ӷ����";
			iArray[13][1] = "80px";
			iArray[13][2] = 85;
			iArray[13][3] = 0;

			iArray[14] = new Array();
			iArray[14][0] = "�ֱ�Ӷ��1";
			iArray[14][1] = "80px";
			iArray[14][2] = 85;
			iArray[14][3] = 0;

			iArray[15] = new Array();
			iArray[15][0] = "�ֱ�Ӷ��2";
			iArray[15][1] = "80px";
			iArray[15][2] = 85;
			iArray[15][3] = 0;

			iArray[16] = new Array();
			iArray[16][0] = "�ֱ�Ӷ�����";
			iArray[16][1] = "80px";
			iArray[16][2] = 85;
			iArray[16][3] = 0;

			iArray[17] = new Array();
			iArray[17][0] = "����̯�ؽ��";
			iArray[17][1] = "80px";
			iArray[17][2] = 85;
			iArray[17][3] = 0;

			iArray[18] = new Array();
			iArray[18][0] = "�ֱ�ҵ������";
			iArray[18][1] = "80px";
			iArray[18][2] = 85;
			iArray[18][3] = 0;
			
			iArray[19] = new Array();
			iArray[19][0] = "����״̬";
			iArray[19][1] = "60px";
			iArray[19][2] = 85;
			iArray[19][3] = 0;

			RIDataDetailGrid = new MulLineEnter("fm", "RIDataDetailGrid");
			//��Щ���Ա�����loadMulLineǰ
			RIDataDetailGrid.mulLineCount = 0;
			RIDataDetailGrid.displayTitle = 1;
			RIDataDetailGrid.locked = 0;
			RIDataDetailGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			//RIDataGrid.selBoxEventFuncName =""; //��Ӧ�ĺ���������������   
			RIDataDetailGrid.hiddenPlus = 1;
			RIDataDetailGrid.hiddenSubtraction = 1;
			RIDataDetailGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
	
	// ���ܹ����ֱ���ϸ��Ϣ
	function initRIDataUCoDetailGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 40; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������   			     

			iArray[1] = new Array();
			iArray[1][0] = "������";
			iArray[1][1] = "80px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "���ֱ���";
			iArray[2][1] = "80px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "���α���";
			iArray[3][1] = "80px";
			iArray[3][2] = 100;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "�ֱ���˾����";
			iArray[4][1] = "80px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "�ֱ���˾����";
			iArray[5][1] = "100px";
			iArray[5][2] = 100;
			iArray[5][3] = 0;

			iArray[6] = new Array();
			iArray[6][0] = "�ֱ�����";
			iArray[6][1] = "80px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "�ֱ�����";
			iArray[7][1] = "80px";
			iArray[7][2] = 85;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "�ֱ�����";
			iArray[8][1] = "80px";
			iArray[8][2] = 85;
			iArray[8][3] = 0;

			iArray[9] = new Array();
			iArray[9][0] = "�ֱ��������";
			iArray[9][1] = "80px";
			iArray[9][2] = 85;
			iArray[9][3] = 0;
			
			iArray[10] = new Array();
			iArray[10][0] = "�ֱ�����";
			iArray[10][1] = "80px";
			iArray[10][2] = 85;
			iArray[10][3] = 0;
			
			iArray[11] = new Array();
			iArray[11][0] = "�ֱ�����";
			iArray[11][1] = "80px";
			iArray[11][2] = 85;
			iArray[11][3] = 0;

			iArray[12] = new Array();
			iArray[12][0] = "�ֱ�Ӷ����";
			iArray[12][1] = "80px";
			iArray[12][2] = 85;
			iArray[12][3] = 0;

			iArray[13] = new Array();
			iArray[13][0] = "�ֱ�Ӷ��1";
			iArray[13][1] = "80px";
			iArray[13][2] = 85;
			iArray[13][3] = 0;

			iArray[14] = new Array();
			iArray[14][0] = "�ֱ�Ӷ��2";
			iArray[14][1] = "80px";
			iArray[14][2] = 85;
			iArray[14][3] = 0;
			
			iArray[15] = new Array();
			iArray[15][0] = "�ֱ�Ӷ��3";
			iArray[15][1] = "80px";
			iArray[15][2] = 85;
			iArray[15][3] = 0;
			
			iArray[16] = new Array();
			iArray[16][0] = "�ֱ�Ӷ��4";
			iArray[16][1] = "80px";
			iArray[16][2] = 85;
			iArray[16][3] = 0;

			iArray[17] = new Array();
			iArray[17][0] = "�ֱ�Ӷ�����";
			iArray[17][1] = "80px";
			iArray[17][2] = 85;
			iArray[17][3] = 0;

			iArray[18] = new Array();
			iArray[18][0] = "����̯�ؽ��";
			iArray[18][1] = "80px";
			iArray[18][2] = 85;
			iArray[18][3] = 0;

			iArray[19] = new Array();
			iArray[19][0] = "�ֱ�ҵ������";
			iArray[19][1] = "80px";
			iArray[19][2] = 85;
			iArray[19][3] = 0;
			
			iArray[20] = new Array();
			iArray[20][0] = "����״̬";
			iArray[20][1] = "60px";
			iArray[20][2] = 85;
			iArray[20][3] = 0;

			RIDataUCoDetailGrid = new MulLineEnter("fm", "RIDataUCoDetailGrid");
			//��Щ���Ա�����loadMulLineǰ
			RIDataUCoDetailGrid.mulLineCount = 0;
			RIDataUCoDetailGrid.displayTitle = 1;
			RIDataUCoDetailGrid.locked = 0;
			RIDataUCoDetailGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			//RIDataGrid.selBoxEventFuncName =""; //��Ӧ�ĺ���������������   
			RIDataUCoDetailGrid.hiddenPlus = 1;
			RIDataUCoDetailGrid.hiddenSubtraction = 1;
			RIDataUCoDetailGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}
</script>