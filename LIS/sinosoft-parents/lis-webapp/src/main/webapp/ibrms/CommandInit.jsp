<%
	//�������ƣ�CommandFunInit.jsp
	//�����ܣ���������ĳ�ʼ��
	//�������ڣ�2008-9-17
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
			alert("��CommandFunInit.jspInitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
		//	alert('1');
			initInpBox();
	//		alert('2');
			initSelBox();
	//		alert('3');
			initQueryGrpGrid();			
	//		alert('4');
		} catch (re) {
			alert("CommandFunInit.jspInitForm�����з����쳣:��ʼ���������!");
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
			iArray[1][0] = "���������"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "80px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[2] = new Array();
			iArray[2][0] = "����չʾ"; //����
			iArray[2][1] = "80px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[3] = new Array();
			iArray[3][0] = "����ʵ��"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[4] = new Array();
			iArray[4][0] = "��Ч��"; //����
			iArray[4][1] = "80px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "������������"; //����
			iArray[5][1] = "80px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[5][4] = "IbrmsCommandType";
			
			iArray[6] = new Array();
			iArray[6][0] = "����������"; //����
			iArray[6][1] = "80px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[6][4] = "IbrmsResulttype";

			iArray[7] = new Array();
			iArray[7][0] = "���Ӳ���������"; //����
			iArray[7][1] = "100px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[7][4] = "IbrmsParatype";

			iArray[8] = new Array();
			iArray[8][0] = "��������"; //����
			iArray[8][1] = "60px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 1; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			
			iArray[9] = new Array();
			iArray[9][0] = "������Ϣ"; //����
			iArray[9][1] = "180px"; //�п�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[10] = new Array();
			iArray[10][0] = "���������"; //����
			iArray[10][1] = "80px"; //�п�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[10][4] = "IbrmsCommType"; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			
		
			QueryGrpGrid = new MulLineEnter("document", "QueryGrpGrid");

			//��Щ���Ա�����loadMulLineǰ
			QueryGrpGrid.mulLineCount = 5;
			QueryGrpGrid.displayTitle = 1;
			QueryGrpGrid.canChk = 0;
			QueryGrpGrid.canSel = 1;
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
