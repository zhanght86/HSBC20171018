<%
//�������ƣ�UpReportInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
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
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
 
		
  }
  catch(ex)
  {
    alert("��UpReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
 
  }
  catch(ex)
  {
    alert("��UpReportInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
  
    initInpBox();
    initSelBox();   
    initRiskGrid();      
  }
  catch(re)
  {
    alert("UpReportInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
 
// Ͷ������Ϣ�б�ĳ�ʼ��
// ������Ϣ�б�ĳ�ʼ��
function initRiskGrid()
{

	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ֱ���";
		iArray[1][1]="60px";
		iArray[1][2]=20;
		iArray[1][3]=2;
		iArray[1][4]="RiskCode";
		iArray[1][5]="1|2";
		iArray[1][6]="0|1";
		iArray[1][9]="���ֱ���|code:RiskCode&NOTNULL";
		iArray[1][18]=300;
		//iArray[1][19]=1;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="140px";
		iArray[2][2]=20;
		iArray[2][3]=0;
		//iArray[2][9]="��������|NOTNULL";

		iArray[3]=new Array();
		iArray[3][0]="�ɷѷ�ʽ";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=1;
		

		iArray[4]=new Array();
		iArray[4][0]="�ɷ�����";
		iArray[4][1]="60px";
		iArray[4][2]=20;
		iArray[4][3]=1;
		

		iArray[5]=new Array();
		iArray[5][0]="��ȡ��ʽ";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=1;
		

		iArray[6]=new Array();
		iArray[6][0]="��Ů����";
		iArray[6][1]="60px";
		iArray[6][2]=20;
		iArray[6][3]=1;
		
		iArray[7]=new Array();
		iArray[7][0]="��͸��˱���";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=1;
		

		iArray[8]=new Array();
		iArray[8][0]="��߸��˱���";
		iArray[8][1]="80px";
		iArray[8][2]=20;
		iArray[8][3]=1;
		

		iArray[9]=new Array();
		iArray[9][0]="��������ԭ��";
		iArray[9][1]="120px";
		iArray[9][2]=20;
		iArray[9][3]=1;
		

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" );
		//��Щ���Ա�����loadMulLineǰ
		RiskGrid.mulLineCount = 0;
		RiskGrid.displayTitle = 1;
		RiskGrid.canChk =1;
		RiskGrid. hiddenPlus=0;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		RiskGrid. hiddenSubtraction=0;
		RiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
