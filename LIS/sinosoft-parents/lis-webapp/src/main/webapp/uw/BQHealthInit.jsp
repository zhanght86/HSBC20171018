<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWHealthInit.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		// ������ѯ����
		document.all('QContNo').value = '';
		document.all('QPrtSeq').value = '';
		document.all('QHandler').value = '';
	}
	catch(ex)
	{
		alert("��UWHealthInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
		alert("��UWHealthInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][0]="��ͬ��";
		iArray[1][1]="120px";
		iArray[1][2]=170;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���֪ͨ����ˮ��";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="������";         	
		iArray[4][1]="100px";           
		iArray[4][2]=100;            	
		iArray[4][3]=0;              	
		
		iArray[5]=new Array();
		iArray[5][0]="�����������";  
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="�������������";
		iArray[6][1]="0px";
		iArray[6][2]=200;
		iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="�������Id";      //����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[8]=new Array();
      iArray[8][0]="����˿ͻ�����";      //����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
    
      
		GrpGrid = new MulLineEnter( "document" , "GrpGrid" );
		//��Щ���Ա�����loadMulLineǰ
		GrpGrid.mulLineCount = 5;
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
