<script type="text/JavaScript"><!--
var turnPage=new turnPageClass();
var turnPage2=new turnPageClass();
function initForm()
{
	initMainGrid();
	initDetailGrid();
	}
function initMainGrid() {

	var iArray = new Array();
	try {
		iArray[0] = new Array();
		iArray[0][0] = "���";
		iArray[0][1] = "30px"; 
		iArray[0][2] = 10; 
		iArray[0][3] = 0;

		iArray[1] = new Array();
		iArray[1][0] = "���κ�";
		iArray[1][1] = "100px"; 
		iArray[1][2] = 10; 
		iArray[1][3] = 0;
		
		iArray[2] = new Array();
		iArray[2][0] = "ҵ��ģ��"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[2][1] = "50px"; //�п�
		iArray[2][2] = 10; //�����ֵ
		iArray[2][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
		iArray[2][4] = "ibrmsbusiness";

		iArray[3] = new Array();
		iArray[3][0] = "������"; //����
		iArray[3][1] = "50px"; //�п�
		iArray[3][2] = 100; //�����ֵ
		iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

		iArray[4] = new Array();
		iArray[4][0] = "����ʱ��(��λ:S)"; //����
		iArray[4][1] = "100px"; //�п�
		iArray[4][2] = 100; //�����ֵ
		iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

		iArray[5] = new Array();
		iArray[5][0] = "�����־"; //����
		iArray[5][1] = "50px"; //�п�
		iArray[5][2] = 100; //�����ֵ
		iArray[5][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		iArray[5][4] = "";
		
		iArray[6] = new Array();
		iArray[6][0] = "����Ա"; //����
		iArray[6][1] = "50px"; //�п�
		iArray[6][2] = 100; //�����ֵ
		iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		
		iArray[7] = new Array();
		iArray[7][0] = "ִ������"; //����
		iArray[7][1] = "80px"; //�п�
		iArray[7][2] = 100; //�����ֵ
		iArray[7][3] = 8; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

		iArray[8] = new Array();
		iArray[8][0] = "ִ��ʱ��"; //����
		iArray[8][1] = "50px"; //�п�
		iArray[8][2] = 100; //�����ֵ
		iArray[8][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

		iArray[9] = new Array();
		iArray[9][0] = "���������"; //����
		iArray[9][1] = "80px"; //�п�
		iArray[9][2] = 100; //�����ֵ
		iArray[9][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		iArray[9][4] = "";

		/*iArray[9] = new Array();
		iArray[9][0] = "�����־"; //����
		iArray[9][1] = "80px"; //�п�
		iArray[9][2] = 100; //�����ֵ
		iArray[9][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		//iArray[9][4] = "ibrmsvalid";
		*/
		
		MainGrid = new MulLineEnter( "document" , "MainGrid" );
 //��Щ���Ա�����loadMulLineǰ
  MainGrid.mulLineCount = 5;
  MainGrid.displayTitle = 1;
  MainGrid.hiddenPlus = 1;
  MainGrid.hiddenSubtraction = 1;
  MainGrid.canSel= 1;
  MainGrid.canChk =0;
  MainGrid.selBoxEventFuncName="queryDetails";
  MainGrid.loadMulLine(iArray);
	} catch (ex) {
		alert(ex);
	}
}
function initDetailGrid()
{
	var iArray = new Array();
	try {
		iArray[0] = new Array();
		iArray[0][0] = "���";
		iArray[0][1] = "30px"; 
		iArray[0][2] = 10; 
		iArray[0][3] = 0;

		iArray[1] = new Array();
		iArray[1][0] = "���κ�";
		iArray[1][1] = "80px"; 
		iArray[1][2] = 10; 
		iArray[1][3] = 0;
		
		iArray[2] = new Array();
		iArray[2][0] = "ִ��˳���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[2][1] = "80px"; //�п�
		iArray[2][2] = 10; //�����ֵ
		iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
		//iArray[1][4] = "ibrmsresulttype";

		iArray[3] = new Array();
		iArray[3][0] = "����ģ���ʶ"; //����
		iArray[3][1] = "80px"; //�п�
		iArray[3][2] = 100; //�����ֵ
		iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		iArray[3][4] = "ibrmsbusiness";

		iArray[4] = new Array();
		iArray[4][0] = "�汾��"; //����
		iArray[4][1] = "30px"; //�п�
		iArray[4][2] = 100; //�����ֵ
		iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		iArray[4][4] = "ibrmstemplatelevel";

		iArray[5] = new Array();
		iArray[5][0] = "�����ʶ"; //����
		iArray[5][1] = "40px"; //�п�
		iArray[5][2] = 100; //�����ֵ
		iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		
		iArray[6] = new Array();
		iArray[6][0] = "�Ժ˽��"; //����
		iArray[6][1] = "80px"; //�п�
		iArray[6][2] = 100; //�����ֵ
		iArray[6][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		
		iArray[7] = new Array();
		iArray[7][0] = "�˹��˱�����"; //����
		iArray[7][1] = "80px"; //�п�
		iArray[7][2] = 100; //�����ֵ
		iArray[7][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		iArray[7][4] = "";
		
		iArray[8] = new Array();
		iArray[8][0] = "ִ���쳣��־"; //����
		iArray[8][1] = "80px"; //�п�
		iArray[8][2] = 100; //�����ֵ
		iArray[8][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		iArray[8][4] = "";

		
		DetailGrid = new MulLineEnter( "document" , "DetailGrid" );
 //��Щ���Ա�����loadMulLineǰ
  DetailGrid.mulLineCount = 5;
  DetailGrid.displayTitle = 1;
  DetailGrid.hiddenPlus = 1;
  DetailGrid.hiddenSubtraction = 1;
  DetailGrid.canSel= 1;
  DetailGrid.canChk =0;
  DetailGrid.loadMulLine(iArray);
	} catch (ex) {
		alert(ex);
	}
}

--></script>
