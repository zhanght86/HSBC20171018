<%
//�������ƣ�ProposalPrintInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<%
GlobalInput globalInput = (GlobalInput)session.getValue("GI");
String strManageCom = globalInput.ComCode;
String strCurDate =PubFun.getCurrentDate();
loggerDebug("ProposalPrintInit","strCurDate"+strCurDate);
%>
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		 document.all('ManageCom').value = <%=tGI.ComCode%>;
		 var tManageCode=<%=strManageCom%>;
     if(tManageCode!=86)
     {
    	 document.all("divErrorMInfo").style.display="none";
      }
		 //document.all('MakeDate').value = <%=strCurDate%>;
	}
	catch(ex)
	{
		alert("��ProposalPrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initContGrid();
		initErrorGrid();
		document.all('ManageCom').value = <%=strManageCom%>;
	}
	catch(re)
	{
		alert("ProposalPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

//����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
var ContGrid;

// ������Ϣ�б�ĳ�ʼ��
function initContGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";	//�п�
		iArray[0][2]=10;	//�����ֵ
		iArray[0][3]=0;	//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="180px";           
		iArray[1][2]=100;            	
		iArray[1][3]=0;
		
		
		iArray[2]=new Array();
		iArray[2][0]="Ͷ������";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="ӡˢ��";
		iArray[3][1]="180px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="Ͷ��������";
		iArray[4][1]="160px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="ǩ������";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="ǩ��ʱ��";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="��ӡ���";
		iArray[7][1]="0px";
		iArray[7][2]=100;
		iArray[7][3]=3;
		
		iArray[8]=new Array();
		iArray[8][0]="��������";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=3;
		
		iArray[9]=new Array();
		iArray[9][0]="��ͥ����";
		iArray[9][1]="0px";
		iArray[9][2]=100;
		iArray[9][3]=3;
		
		iArray[10]=new Array();
		iArray[10][0]="VIP�������";
		iArray[10][1]="0px";
		iArray[10][2]=100;
		iArray[10][3]=3;
		


		ContGrid = new MulLineEnter( "fm" , "ContGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ContGrid.mulLineCount = 5;
		ContGrid.displayTitle = 1;
		ContGrid.hiddenPlus = 1;
		ContGrid.hiddenSubtraction = 1;
		ContGrid.canSel = 0;
		ContGrid.canChk = 1;
		ContGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

var ContGrid;

// ������Ϣ�б�ĳ�ʼ��
function initErrorGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";	//�п�
		iArray[0][2]=10;	//�����ֵ
		iArray[0][3]=0;	//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="140px";           
		iArray[1][2]=100;            	
		iArray[1][3]=0;
		
		
		iArray[2]=new Array();
		iArray[2][0]="������Ϣ";
		iArray[2][1]="400px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="����ʱ��";
		iArray[4][1]="80px";
		iArray[4][2]=200;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="�������";
		iArray[5][1]="80px";
		iArray[5][2]=200;
		iArray[5][3]=0;

		ErrorGrid = new MulLineEnter( "fm" , "ErrorGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ErrorGrid.mulLineCount = 5;
		ErrorGrid.displayTitle = 1;
		ErrorGrid.hiddenPlus = 1;
		ErrorGrid.hiddenSubtraction = 1;
		ErrorGrid.canSel = 0;
		ErrorGrid.canChk = 0;
		ErrorGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
