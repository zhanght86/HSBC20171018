<%
//�������ƣ�LDPersonQueryInit.jsp
//�����ܣ��ͻ����������ͻ���ѯ��
//�������ڣ�2005-06-20
//������  ��wangyan
//���¼�¼��������    ��������     ����ԭ��/����
%>

<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		// ������ѯ����
		document.all('CustomerNo').value = '';
		document.all('Name').value = '';
		document.all('Sex').value = '';
		document.all('Birthday').value = '';
		document.all('IDType').value = '';
		document.all('IDNo').value = '';
	}
	catch(ex)
	{
		alert("��LDPersonQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initPersonGrid();
	}
	catch(re)
	{
		alert("LDPersonQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initPersonGrid()
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
		iArray[1][0]="�ͻ�����";
		iArray[1][1]="160px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="����";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�Ա�";
		iArray[3][1]="60px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="100px";
		iArray[4][2]=200;
		iArray[4][3]=0;              		
		
		iArray[5]=new Array();
		iArray[5][0]="֤������";         	
		iArray[5][1]="60px";            	
		iArray[5][2]=200;            		
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="֤������";
		iArray[6][1]="140px";
		iArray[6][2]=200;
		iArray[6][3]=0;

		PersonGrid = new MulLineEnter( "fm" , "PersonGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PersonGrid.mulLineCount = 10;
		PersonGrid.displayTitle = 1;
		PersonGrid.locked = 1;
		PersonGrid.canSel = 1;
		PersonGrid.hiddenPlus=1;
		PersonGrid.hiddenSubtraction=1;
		PersonGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
