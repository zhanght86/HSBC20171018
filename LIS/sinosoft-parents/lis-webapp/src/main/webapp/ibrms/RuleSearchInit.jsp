<script type="text/JavaScript">
function initForm()
{
	initBOM();
	initQueryGrpGrid();
	
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
			iArray[1][0] = "��������"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[1][1] = "80px"; //�п�
			iArray[1][2] = 10; //�����ֵ
			iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
			//iArray[1][4] = "ibrmsresulttype";

			iArray[2] = new Array();
			iArray[2][0] = "ҵ��ģ��"; //����
			iArray[2][1] = "80px"; //�п�
			iArray[2][2] = 100; //�����ֵ
			iArray[2][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "���򼶱�"; //����
			iArray[3][1] = "80px"; //�п�
			iArray[3][2] = 100; //�����ֵ
			iArray[3][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "��Ч����"; //����
			iArray[4][1] = "80px"; //�п�
			iArray[4][2] = 100; //�����ֵ
			iArray[4][3] = 8; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "ʧЧ����"; //����
			iArray[5][1] = "80px"; //�п�
			iArray[5][2] = 100; //�����ֵ
			iArray[5][3] = 8; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[5][4] = "IbrmsCommandType";
			
			iArray[6] = new Array();
			iArray[6][0] = "������"; //����
			iArray[6][1] = "80px"; //�п�
			iArray[6][2] = 100; //�����ֵ
			iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[7] = new Array();
			iArray[7][0] = "״̬"; //����
			iArray[7][1] = "100px"; //�п�
			iArray[7][2] = 100; //�����ֵ
			iArray[7][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[7][4] = "ibrmsstate";
			
			iArray[8] = new Array();
			iArray[8][0] = "��������"; //����
			iArray[8][1] = "180px"; //�п�
			iArray[8][2] = 100; //�����ֵ
			iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[8][4] = "ibrmsvalid";
		
		    iArray[9] = new Array();
			iArray[9][0] = "id"; //����
			iArray[9][1] = "50px"; //�п�
			iArray[9][2] = 100; //�����ֵ
			iArray[9][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

			iArray[10] = new Array();
			iArray[10][0] = "��Դ����"; //����
			iArray[10][1] = "100px"; //�п�
			iArray[10][2] = 100; //�����ֵ
			iArray[10][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			
     QueryGrpGrid = new MulLineEnter( "document" , "QueryGrpGrid" );
     //��Щ���Ա�����loadMulLineǰ
      QueryGrpGrid.mulLineCount = 5;
      QueryGrpGrid.displayTitle = 1;
      QueryGrpGrid.hiddenPlus = 1;
      QueryGrpGrid.hiddenSubtraction = 1;
      QueryGrpGrid.canSel= 1;
      QueryGrpGrid.canChk =0;
      QueryGrpGrid.loadMulLine(iArray);
			
			
		} catch (ex) {
			alert(ex);
		}
	}
function initBOM() {
	try {
		fm.BOM.value = "";
		//var bomsql = "select * from lrbom";
		var mySql1=new SqlClass();
		mySql1.setResourceName("ibrms.RuleSearchSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("RuleSearchSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara("");//ָ������Ĳ���
		
		
	  var bomsql =mySql1.getString();	
		
		var tem = easyQueryVer3(bomsql, 1, 1, 1);
		if (tem == false) {
			fm.BOM.CodeData = '';
			alert("ҳ���ʼ��BOM��ʱ��������ҹ���Ա��");
		} else {
			fm.BOM.CodeData = tem;
		}

	} catch (ex) {

	}
}
</script>
