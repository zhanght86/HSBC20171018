<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		fm.qManageCom.value = comcode;
	}
	catch(ex)
	{
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initGrpGrid();
	}
	catch(re)
	{
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="ӡˢ��";
		iArray[1][1]="120px";
		iArray[1][2]=170;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="150px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���ֱ���";
		iArray[3][1]="60px";
		iArray[3][2]=200;
		iArray[3][3]=1;

		iArray[4]=new Array();
		iArray[4][0]="Ͷ���˿ͻ���";         	
		iArray[4][1]="100px";           
		iArray[4][2]=100;            	
		iArray[4][3]=3; 
		             	
		iArray[5]=new Array();
		iArray[5][0]="Ͷ��������";         	
		iArray[5][1]="70px";           
		iArray[5][2]=100;            	
		iArray[5][3]=0;              	
		
		iArray[6]=new Array();
		iArray[6][0]="�������";  
		iArray[6][1]="80px";
		iArray[6][2]=200;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="ҵ��Ա����";
		iArray[7][1]="80px";
		iArray[7][2]=200;
		iArray[7][3]=0;

      	iArray[8]=new Array();
      	iArray[8][0]="ҵ��Ա����";         		//����
      	iArray[8][1]="70px";            		//�п�
      	iArray[8][2]=60;            			//�����ֵ
      	iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      	iArray[9]=new Array();
      	iArray[9][0]="��ִɨ������";         		//����
      	iArray[9][1]="150px";            		//�п�
      	iArray[9][2]=60;            			//�����ֵ
      	iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      	//iArray[10]=new Array();
      	//iArray[10][0]="���ֱ���";         		//����
      	//iArray[10][1]="60px";            		//�п�
      	//iArray[10][2]=60;            			//�����ֵ
      	//iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 


		GrpGrid = new MulLineEnter( "document" , "GrpGrid" );
		//��Щ���Ա�����loadMulLineǰ
		GrpGrid.mulLineCount = 5;
		GrpGrid.displayTitle = 1;
		GrpGrid.locked = 1;
		GrpGrid.canSel = 1;
		GrpGrid.canChk = 0;
		GrpGrid.hiddenSubtraction = 1;
		GrpGrid.selBoxEventFuncName = "GoToInput";
		GrpGrid.hiddenPlus = 1;
		GrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
