<%
//�������ƣ�menuInit.jsp
//�����ܣ�
//�������ڣ�2002-10-10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
function initInpBox()
{
	try
	{
		document.all("UserCode").value = "";
	}
	catch(ex)
	{
		alert("��menuGrpInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initQueryGrpGrid();
	}
	catch(re)
	{
		alert("menuGrpInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

function initQueryGrpGrid()
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
		iArray[1][0]="����Ա����";
		iArray[1][1]="100px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�˵������";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�˵���������";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		iArray[4]=new Array();
		iArray[4][0]="�˵�������";
		iArray[4][1]="200px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		QueryGrpGrid = new MulLineEnter( "document" , "QueryGrpGrid" );
		QueryGrpGrid.mulLineCount = 5;
		QueryGrpGrid.displayTitle = 1;
		QueryGrpGrid.canChk =0;
		QueryGrpGrid.canSel =1;
		QueryGrpGrid.locked =1;
		QueryGrpGrid.hiddenPlus=1;
		QueryGrpGrid.hiddenSubtraction=1;
		QueryGrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
