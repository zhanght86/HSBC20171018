<%
//�������ƣ�LLUWNoticeInit.jsp
//�����ܣ�
//�������ڣ�2005-12-05 
//������  �������
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//���ҳ��ؼ��ĳ�ʼ����
GlobalInput globalInput = (GlobalInput)session.getValue("GI");

if(globalInput == null) 
{
	out.println("session has expired");
	return;
}

String strOperator = globalInput.Operator;
String strManageCom = globalInput.ComCode;
%>
<script language="JavaScript">

function initForm()
{
	try
	{
	    initNoticeGrid();
		NoticeQuery();
	}
	catch(re)
	{
		alert("initForm.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

var NoticeGrid;          
// Ͷ������Ϣ�б�ĳ�ʼ��
function initNoticeGrid()
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
		iArray[1][0]="��ӡ��ˮ��";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="160px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="֪ͨ����";
		iArray[3][1]="80px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		

		iArray[4]=new Array();
		iArray[4][0]="֪ͨ������";
		iArray[4][1]="180px";
		iArray[4][2]=200;
		iArray[4][3]=0;
	    
		iArray[5]=new Array();
		iArray[5][0]="ӪҵԱ����";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="�����������";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		 NoticeGrid = new MulLineEnter( "document" , "NoticeGrid" ); 
         //��Щ���Ա�����loadMulLineǰ
         
         NoticeGrid.mulLineCount      = 5;   
	     NoticeGrid.displayTitle      = 1;
	     NoticeGrid.locked            = 1;
	     NoticeGrid.canSel            = 1; 
	     NoticeGrid.hiddenPlus        = 1;
	     NoticeGrid.hiddenSubtraction = 1;
	     NoticeGrid.loadMulLine(iArray); 
	}
	catch(ex)
	{
		 alert(ex);
	}
}
</script>
