<%
//�������ƣ�OLDUserInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:42
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
//���ҳ��ؼ��ĳ�ʼ����
GlobalInput tG1 = new GlobalInput();
tG1=(GlobalInput)session.getValue("GI");
%>
<script src="./UserAdd.js"></script>
<script language="JavaScript">
function initInpBox()
{
	try
	{
		document.all('UserCode').value = '';
		document.all('UserName').value = '';
		document.all('ComCode').value = '';
		document.all('Password').value = '';
		document.all('PasswordConfirm').value = '';
		document.all('UserDescription').value = '';
		document.all('UserState').value = '';
		document.all('UWPopedom').value = '';
		document.all('ClaimPopedom').value = '';
		document.all('OtherPopedom').value = '';
		document.all('EdorPopedom').value = '';
		document.all('PopUWFlag').value = '';
		document.all('SuperPopedomFlag').value = '';
		document.all('Operator').value = '<%=tG1.Operator%>';
		document.all('ValidStartDate').value = '<%=PubFun.getCurrentDate()%>';
		document.all('ValidEndDate').value = '';
	}
	catch(ex)
	{
		alert("��UserInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initSelectMenuGrpGrid();
		initUnselectMenuGrpGrid();
		//��ʼ��hideMenuGrpGrid������unselectMenuGrpGrid��
		initHideMenuGrpGrid1();
		initUserGrid();
	}
	catch(re)
	{
		alert("OLDUserInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

function initUserGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";	//�п�
		iArray[0][2]=10;		//�����ֵ
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�û�����";
		iArray[1][1]="100px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�û�����";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�û�״̬";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�û�����";
		iArray[4][1]="200px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		
		iArray[5]=new Array();
		iArray[5][0]="��������";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�������";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		

		UserGrid = new MulLineEnter( "fm" , "UserGrid" );
		//��Щ���Ա�����loadMulLineǰ
		UserGrid.mulLineCount = 5;
		UserGrid.displayTitle = 1;
		UserGrid.canChk =0;
		UserGrid.canSel =1;
		UserGrid.locked =1;				//�Ƿ�������1Ϊ���� 0Ϊ������
		UserGrid.hiddenPlus=1;			//�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
		UserGrid.hiddenSubtraction=1;	//�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
		UserGrid.recordNo=0;			//���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
		UserGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function  initHideMenuGrpGrid1()
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
		iArray[1][0]="�˵�������";
		iArray[1][1]="140px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�˵�������";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		HideMenuGrpGrid1 = new MulLineEnter( "fm" , "HideMenuGrpGrid1" );
		HideMenuGrpGrid1.mulLineCount = 5;
		HideMenuGrpGrid1.displayTitle = 1;
		HideMenuGrpGrid1.canChk =1;
		HideMenuGrpGrid1.canSel =0;
		HideMenuGrpGrid1.locked =1;
		HideMenuGrpGrid1.hiddenPlus=1;
		HideMenuGrpGrid1.hiddenSubtraction=1;
		HideMenuGrpGrid1.recordNo=0;
		HideMenuGrpGrid1.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function  initSelectMenuGrpGrid()
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
		iArray[1][0]="�˵�������";
		iArray[1][1]="100px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�˵�������";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="����˵�";
		iArray[3][1]="70px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		iArray[3][7]="showSelectGridMenus";

		SelectMenuGrpGrid = new MulLineEnter( "fm" , "SelectMenuGrpGrid" );
		SelectMenuGrpGrid.mulLineCount = 5;
		SelectMenuGrpGrid.displayTitle = 1;
		SelectMenuGrpGrid.canChk =1;
		SelectMenuGrpGrid.canSel =0;
		SelectMenuGrpGrid.locked =1;
		SelectMenuGrpGrid.hiddenPlus=1;
		SelectMenuGrpGrid.hiddenSubtraction=1;
		SelectMenuGrpGrid.recordNo=0;
		SelectMenuGrpGrid.mulLineNum = 2;
		SelectMenuGrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function  initUnselectMenuGrpGrid()
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
		iArray[1][0]="�˵�������";
		iArray[1][1]="60px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�˵�������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;


		iArray[3]=new Array();
		iArray[3][0]="����˵�";
		iArray[3][1]="50px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		iArray[3][7]="showUnselectGridMenus";


		UnselectMenuGrpGrid = new MulLineEnter( "fm" , "UnselectMenuGrpGrid" );
		UnselectMenuGrpGrid.mulLineCount = 5;
		UnselectMenuGrpGrid.displayTitle = 1;
		UnselectMenuGrpGrid.canChk =1;
		UnselectMenuGrpGrid.canSel =0;
		UnselectMenuGrpGrid.locked =1;
		UnselectMenuGrpGrid.hiddenPlus=1;
		UnselectMenuGrpGrid.hiddenSubtraction=1;
		UnselectMenuGrpGrid.recordNo=0;
		UnselectMenuGrpGrid.mulLineNum = 2;
		UnselectMenuGrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
