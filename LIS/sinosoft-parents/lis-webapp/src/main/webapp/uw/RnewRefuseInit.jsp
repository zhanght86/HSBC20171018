<%
//�������ƣ�UWNoticeInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//���ҳ��ؼ��ĳ�ʼ����
GlobalInput globalInput = (GlobalInput)session.getValue("GI");

if(globalInput == null) {
	out.println("session has expired");
	return;
}

String strOperator = globalInput.Operator;
String strManageCom = globalInput.ComCode;
%>
<script language="JavaScript">
function initInpBox()
{
	try
	{
		document.all('ContNo').value = '';
		document.all('ManageCom').value = <%=strManageCom%>;
	}
	catch(ex)
	{
		alert("��UWNoticeInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initPolGrid();
	}
	catch(re)
	{
		alert("PEdorUWNoticeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
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
		iArray[1][0]="֪ͨ���";
		iArray[1][1]="180px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="������";
		iArray[2][1]="160px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�����˱���";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="֪ͨ������";
		iArray[4][1]="160px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="֪ͨ������";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="�������";
		iArray[6][1]="120px";
		iArray[6][2]=100;
		iArray[6][3]=2;
		iArray[6][4]="station";
		iArray[6][5]="3";
		iArray[6][9]="�������|code:station&NOTNULL";
		iArray[6][18]=250;
		iArray[6][19]= 0 ;

		iArray[7]=new Array();
	  iArray[7][0]="��������";         	//����
	  iArray[7][1]="100px";            	//�п�
	  iArray[7][2]=100;            			//�����ֵ
	  iArray[7][3]=2; 
	  iArray[7][4]="SaleChnl";              	        //�Ƿ����ô���:null||""Ϊ������
    iArray[7][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
    iArray[7][9]="��������|code:SaleChnl&NOTNULL";
    iArray[7][18]=250;
    iArray[7][19]= 0 ;	
		
		iArray[8]=new Array();
		iArray[8][0]="�������������";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="���������������";
		iArray[9][1]="0px";
		iArray[9][2]=100;
		iArray[9][3]=0;

		iArray[10]=new Array();
		iArray[10][0]="OldPrtSeq";
		iArray[10][1]="0px";
		iArray[10][2]=100;
		iArray[10][3]=3;
	

		PolGrid = new MulLineEnter( "fmSave" , "PolGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PolGrid.mulLineCount = 10;
		PolGrid.displayTitle = 1;
		PolGrid.canSel = 1;
		PolGrid.hiddenPlus=1;
		PolGrid.hiddenSubtraction=1;
		PolGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
