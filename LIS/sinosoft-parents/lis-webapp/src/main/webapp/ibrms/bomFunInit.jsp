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
		var tResourceName1 = 'ibrms.bomFunInitSql';
		
	function initInpBox() {
		try {	
			// ��BOM
			//alert('1');
			document.all('fBOM').value = "";
			//document.all('PreCheck').value = "";
			
			var eName = document.all("eName").value;
			
			var sqlid1="bomFunInitSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName(tResourceName1); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara('1');
	  
	  
			if(eName==''){
				//var fbomsql = "select name,cnname from (select * from lrbom where name<>' ')";
			}else{
				//var fbomsql = "select name,cnname from (select * from lrbom where name<>'"+eName+"')";
				mySql1.addSubPara(eName);//ָ������Ĳ���
			}			
				var strSql=mySql1.getString();	
			var tem = easyQueryVer3(strSql,1,1,1);
			if(tem==false){
				document.all('fBOM').CodeData = '';
			}else{
				document.all('fBOM').CodeData = tem;
			}
			
			
			
		} catch (ex) {

		}
	}

	function initSelBox() {
		try {
		
		} catch (ex) {
			alert("��bomFunInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm(operate) {
		try {
			initInpBox();
			initSelBox();
			if (operate == '1'){
			}
			else{
			initQueryGrpGrid();
			initItemGrid();
			initValid();
			}
		} catch (re) {
			alert("bomFunInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
		}
	}

	function initItemGrid() {

		var iArray = new Array();

		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 10; //�����ֵ
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "����Ӣ����"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "100px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "BOM"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[2][1] = "100px"; //�п�
			iArray[2][2] = 10; //�����ֵ
			iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[3] = new Array();
			iArray[3][0] = "����������"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[4] = new Array();
			iArray[4][0] = "���Ӵ�"; //����
			iArray[4][1] = "70px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][4] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			
			iArray[5] = new Array();
			iArray[5][0] = "���������"; //����
			iArray[5][1] = "80px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[5][4] = "IbrmsIsHierarchical";
			
			iArray[6] = new Array();
			iArray[6][0] = "��������"; //����
			iArray[6][1] = "70px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[6][4] = "IbrmsIsBase";
			
			iArray[7] = new Array();
			iArray[7][0] = "��������ȡֵ����"; //����
			iArray[7][1] = "100px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[7][4] = "IbrmsSourceType";
			
			iArray[8] = new Array();
			iArray[8][0] = "��������ȡֵ"; //����
			iArray[8][1] = "120px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			
			iArray[9] = new Array();
			iArray[9][0] = "������������"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[9][1] = "80px"; //�п�
			iArray[9][2] = 10; //�����ֵ
			iArray[9][3] = 2;
			iArray[9][4] = "IbrmsCommandType";

			iArray[10] = new Array();
			iArray[10][0] = "����ԤУ��"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[10][1] = "70px"; //�п�
			iArray[10][2] = 10; //�����ֵ
			iArray[10][3] = 2;
			iArray[10][4] = "IbrmsPreCheck";
			iArray[10][15] = "IbrmsPreCheck";
			iArray[10][17] = "9"; //������9�е�ֵ

			iArray[11] = new Array();
			iArray[11][0] = "��Ч��"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[11][1] = "50px"; //�п�
			iArray[11][2] = 10; //�����ֵ
			iArray[11][3] = 2;
			iArray[11][4] = "IbrmsValid";

			iArray[12] = new Array();
			iArray[12][0] = "��������"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[12][1] = "180px"; //�п�
			iArray[12][2] = 10; //�����ֵ
			iArray[12][3] = 0;

			ItemGrid = new MulLineEnter("document", "ItemGrid");
			
			//��Щ���Ա�����loadMulLineǰ
			ItemGrid.mulLineCount = 5;
			ItemGrid.displayTitle = 1;
   	    	ItemGrid.canChk = 0;
			ItemGrid.canSel = 1;
			ItemGrid.locked = 1; //�Ƿ�������1Ϊ���� 0Ϊ������
			ItemGrid.hiddenPlus = 1; //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
			ItemGrid.hiddenSubtraction = 1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
			ItemGrid.recordNo = 0; //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������

			ItemGrid.loadMulLine(iArray);
		} catch (ex) {
			alert(ex);
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
			iArray[8][1] = "200px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[9] = new Array();
			iArray[9][0] = "BOM��Ӧ��"; //����
			iArray[9][1] = "240px"; //�п�
			iArray[9][2] = 100; //�����ֵ
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
			QueryGrpGrid.canChk = 0;
			QueryGrpGrid.canSel = 1;
			QueryGrpGrid.locked = 1; //�Ƿ�������1Ϊ���� 0Ϊ������
			QueryGrpGrid.hiddenPlus = 1; //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
			QueryGrpGrid.hiddenSubtraction = 1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
			QueryGrpGrid.recordNo = 0; //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
			QueryGrpGrid.selBoxEventFuncName ="itemQuery";

			QueryGrpGrid.loadMulLine(iArray);
		} catch (ex) {
			alert(ex);
		}
	}

function initValid(){
	try {
		fm.Valid.value = "1";
		fm.Valid.readOnly = "true";
		fm.ValidName.value = "��Ч";
		fm.ValidName.readOnly = "true";
	} catch (e) {
		alert("״̬��Ϣ��ʼ������");
	}	
}	
</script>
