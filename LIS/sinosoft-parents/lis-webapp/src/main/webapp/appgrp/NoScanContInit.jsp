<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ScanContInit.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		// ������ѯ����
		document.all('PrtNo').value = '';
	}
	catch(ex)
	{
		alert("��GroupUWAutoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initTr();
		initInpBox();
		initGrpGrid();
	}
	catch(re)
	{
		alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][0]="Ͷ������";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="ѯ�۱��";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		
		iArray[3]=new Array();
		iArray[3][0]="ѯ�۰汾��";
		iArray[3][1]="0px";
		iArray[3][2]=50;
		iArray[3][3]=0;
		

		iArray[4]=new Array();
		iArray[4][0]="�������";
		iArray[4][1]="120px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="��������";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="������";         	
		iArray[6][1]="100px";           
		iArray[6][2]=100;            	
		iArray[6][3]=0;              	
		
		iArray[7]=new Array();
		iArray[7][0]="�����������";  
		iArray[7][1]="0px";
		iArray[7][2]=200;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="�������������";
		iArray[8][1]="0px";
		iArray[8][2]=200;
		iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="�������Id";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 


		GrpGrid = new MulLineEnter( "fm" , "GrpGrid" );
		//��Щ���Ա�����loadMulLineǰ
		GrpGrid.mulLineCount = 10;
		GrpGrid.displayTitle = 1;
		GrpGrid.locked = 1;
		GrpGrid.canSel = 1;
		GrpGrid.canChk = 0;
		GrpGrid.hiddenSubtraction = 1;
		GrpGrid.hiddenPlus = 1;
		GrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
