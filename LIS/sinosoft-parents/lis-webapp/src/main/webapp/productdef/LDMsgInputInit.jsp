<%@include file="../i18n/language.jsp"%>
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

<script type="text/javascript">


	function initForm() {
		try {
			if(jEditFlag!=null&&jEditFlag=='1') 
			{
				document.getElementById('buttonForm').style.display="";
			}
			initQueryGrpGrid();
			queryClick();
	  fm.all("MsgLan").value=""; 
	  fm.all("MsgDetail").value="";
	  fm.all("MsgLanName").value="";
		 showOneCodeName('language','MsgLan','MsgLanName');;
	   showOneCodeName('multlantype','MsgType','MsgTypeName');;
			
		} catch (re) {
			myAlert("bomFunInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
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
			iArray[1][0] = "<%=bundle.getString("handword_language")%>"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "40px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			//iArray[1][4] = "language";
			
			iArray[2] = new Array();
			iArray[2][0] = "����"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[2][1] = "40px"; //�п�
			iArray[2][2] = 10; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			//iArray[2][4] = "msgtype";
			

			iArray[3] = new Array();
			iArray[3][0] = "��Ϣ"; //����
			iArray[3][1] = "200px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			QueryGrpGrid = new MulLineEnter("fm", "QueryGrpGrid");

			//��Щ���Ա�����loadMulLineǰ
			QueryGrpGrid.mulLineCount = 3;
			QueryGrpGrid.displayTitle = 1;
			QueryGrpGrid.canChk = 0;
			QueryGrpGrid.canSel = 1;
			QueryGrpGrid.locked = 1; //�Ƿ�������1Ϊ���� 0Ϊ������
			QueryGrpGrid.hiddenPlus = 1; //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
			QueryGrpGrid.hiddenSubtraction = 1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
			QueryGrpGrid.recordNo = 0; //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
			QueryGrpGrid.selBoxEventFuncName ="itemQuery";

			QueryGrpGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

</script>