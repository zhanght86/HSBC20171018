<script type="text/JavaScript">
function initForm()
{
	}
function initQueryGrpGrid() {

	var iArray = new Array();
	try {
		iArray[0] = new Array();
		iArray[0][0] = "���";
		iArray[0][1] = "30px"; 
		iArray[0][2] = 10; 
		iArray[0][3] = 0;

		iArray[1] = new Array();
		iArray[1][0] = "����"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[1][1] = "80px"; //�п�
		iArray[1][2] = 10; //�����ֵ
		iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
		//iArray[1][4] = "ibrmsresulttype";

		iArray[2] = new Array();
		iArray[2][0] = "ҵ��ģ��"; //����
		iArray[2][1] = "80px"; //�п�
		iArray[2][2] = 100; //�����ֵ
		iArray[2][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		iArray[2][4] = "ibrmsbusiness";

		iArray[3] = new Array();
		iArray[3][0] = "У�鱣������"; //����
		iArray[3][1] = "80px"; //�п�
		iArray[3][2] = 100; //�����ֵ
		iArray[3][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		iArray[3][4] = "ibrmstemplatelevel";

		iArray[4] = new Array();
		iArray[4][0] = "ͨ������"; //����
		iArray[4][1] = "80px"; //�п�
		iArray[4][2] = 100; //�����ֵ
		iArray[4][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		
		iArray[5] = new Array();
		iArray[5][0] = "У��ͨ����"; //����
		iArray[5][1] = "80px"; //�п�
		iArray[5][2] = 100; //�����ֵ
		iArray[5][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
		
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

</script>
