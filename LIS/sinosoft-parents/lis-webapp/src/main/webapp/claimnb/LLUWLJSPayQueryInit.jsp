<%
//�������ƣ�LLUWLJSPayQueryInit.jsp
//�����ܣ�
//�������ڣ�2006-01-05 
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
	    initLJSPayGrid();
		LJSPayQuery();
		initLJSPayPersonGrid();
		//LJSPayPersonGrid();
	}
	catch(re)
	{
		alert("initForm.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

var LJSPayGrid;          
// Ͷ������Ϣ�б�ĳ�ʼ��
function initLJSPayGrid()
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
		iArray[1][0]="֪ͨ�����";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="Ͷ���˿ͻ���";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="Ӧ���ܽ��(Ԫ)";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="120px";
		iArray[4][2]=200;
		iArray[4][3]=0;
	    
		iArray[5]=new Array();
		iArray[5][0]="�����˱���";
		iArray[5][1]="120px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="����";
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=3;

		LJSPayGrid = new MulLineEnter( "document" , "LJSPayGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
         
        LJSPayGrid.mulLineCount      = 5;   
	    LJSPayGrid.displayTitle      = 1;
	    LJSPayGrid.locked            = 1;
	    LJSPayGrid.canSel            = 1; 
	    LJSPayGrid.selBoxEventFuncName = "GetLJSPayPerson";
        LJSPayGrid.hiddenPlus        = 1;
	    LJSPayGrid.hiddenSubtraction = 1;
	    LJSPayGrid.loadMulLine(iArray); 
	}
	catch(ex)
	{
		 alert(ex);
	}
}

//Ӧ�ռӷ���Ϣ��ϸ�б�
var LJSPayPersonGrid;
function initLJSPayPersonGrid()
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
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���α���";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���Ѽƻ�����";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="120px";
		iArray[4][2]=200;
		iArray[4][3]=0;
	    
		iArray[5]=new Array();
		iArray[5][0]="��Ӧ�����";
		iArray[5][1]="120px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="ԭ��������";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;
        
        iArray[7]=new Array();
		iArray[7][0]="�ֽ�������";
		iArray[7][1]="100px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		 LJSPayPersonGrid = new MulLineEnter( "document" , "LJSPayPersonGrid" ); 
         //��Щ���Ա�����loadMulLineǰ
         
         LJSPayPersonGrid.mulLineCount      = 5;   
	     LJSPayPersonGrid.displayTitle      = 1;
	     LJSPayPersonGrid.locked            = 1;
	     LJSPayPersonGrid.canSel            = 0; 
	     LJSPayPersonGrid.hiddenPlus        = 1;
	     LJSPayPersonGrid.hiddenSubtraction = 1;
	     LJSPayPersonGrid.loadMulLine(iArray); 
	}
	catch(ex)
	{
		 alert(ex);
	}
}
</script>
