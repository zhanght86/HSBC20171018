
<%
	//�������ƣ�bomFunInit.jsp
	//�����ܣ���BOM�ʹ����ĳ�ʼ��
	//�������ڣ�2008-8-13
	//������  ��
	//���¼�¼��
%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
	function initInpBox() {
		try {

		} catch (ex) {

		}
	}

	function initSelBox() {
		try {

		} catch (ex) {
			alert("��bomFunInit.jspInitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
			initQueryGrpGrid();
		} catch (re) {
			alert("bomFunInit.jspInitForm�����з����쳣:��ʼ���������!");
		}
	}

	function initQueryGrpGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0;
		
			iArray[1] = new Array();
			iArray[1][0] = "BOMӢ����"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "80px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[2] = new Array();
			iArray[2][0] = "BOM������"; //����
			iArray[2][1] = "80px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[3] = new Array();
			iArray[3][0] = "��BOM"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			0
			iArray[4] = new Array();
			iArray[4][0] = "��BOM�ֶ�"; //����
			iArray[4][1] = "80px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[5] = new Array();
			iArray[5][0] = "��BOM�ֶ�"; //����
			iArray[5][1] = "80px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[6] = new Array();
			iArray[6][0] = "BOM���"; //����
			iArray[6][1] = "60px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[7] = new Array();
			iArray[7][0] = "ҵ��ģ��"; //����
			iArray[7][1] = "60px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[7][4] = "IbrmsBusiness";

			iArray[8] = new Array();
			iArray[8][0] = "BOM������Ϣ"; //����
			iArray[8][1] = "120px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[9] = new Array();
			iArray[9][0] = "BOM��Ӧ��"; //����
			iArray[9][1] = "240px"; //�п�
			iArray[9][2] = 140; //�����ֵ
			iArray[9][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[10] = new Array();
			iArray[10][0] = "��Ч��"; //����
			iArray[10][1] = "50px"; //�п�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[10][4] = "IbrmsValid";

			QueryGrpGrid = new MulLineEnter("document", "QueryGrpGrid");

			//��Щ���Ա�����loadMulLineǰ
			QueryGrpGrid.mulLineCount = 5;
			QueryGrpGrid.displayTitle = 1;
			QueryGrpGrid.canChk = 1;  
			QueryGrpGrid.canSel = 0;
			QueryGrpGrid.locked = 1; //�Ƿ�������1Ϊ���� 0Ϊ������
			QueryGrpGrid.hiddenPlus = 1; //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
			QueryGrpGrid.hiddenSubtraction = 1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
			QueryGrpGrid.recordNo = 0; //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
			
			QueryGrpGrid.loadMulLine(iArray);
		} catch (ex) {
			alert(ex);
		}
	}
</script>
