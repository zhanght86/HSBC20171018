<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2006-10-24
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
 GlobalInput tG = new GlobalInput();
 tG=(GlobalInput)session.getAttribute("GI");
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
%>

<script type="text/javascript">
function initInpBox()
{
  try
  { 
  }
  catch(ex)
  {
    myAlert("���г�ʼ���ǳ��ִ��󣡣�����");
  }
}

// �����б�ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initBillPrintListGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
  }
}

//������б�
function initBillPrintListGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="�˵����"; 
		iArray[1][1]="40px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 
		
		iArray[2]=new Array();
		iArray[2][0]="�˵�����";
		iArray[2][1]="40px";  
		iArray[2][2]=100; 
		iArray[2][3]=3;     

		iArray[3]=new Array();
		iArray[3][0]="�˵��ڴ�";
		iArray[3][1]="40px";  
		iArray[3][2]=100; 
		iArray[3][3]=3;  
				
		iArray[4]=new Array();
		iArray[4][0]="�ֱ���ͬ���";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=0; 

		iArray[5]=new Array();
		iArray[5][0]="�ֱ���˾���";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=0; 
		
		iArray[6]=new Array();
		iArray[6][0]="��ʼ����";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=0;   
		
		iArray[7]=new Array();
		iArray[7][0]="��ֹ����";
		iArray[7][1]="40px";  
		iArray[7][2]=100; 
		iArray[7][3]=0;   
		
		iArray[8]=new Array();
		iArray[8][0]="�˵�״̬";
		iArray[8][1]="50px";  
		iArray[8][2]=100; 
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="�˵�״̬����";
		iArray[9][1]="0px";  
		iArray[9][2]=100; 
		iArray[9][3]=3;
			
		iArray[10]=new Array();
		iArray[10][0]="�˵����κ�";
		iArray[10][1]="0px";  
		iArray[10][2]=100; 
		iArray[10][3]=3;
		
		iArray[11]=new Array();
		iArray[11][0]="�ŷN";
		iArray[11][1]="40px";  
		iArray[11][2]=100; 
		iArray[11][3]=0;
		
	  	BillPrintListGrid = new MulLineEnter( "fm" , "BillPrintListGrid" ); 
	  	//��Щ���Ա�����loadMulLineǰ
	  	BillPrintListGrid.mulLineCount = 0;   
	  	BillPrintListGrid.displayTitle = 1;
	  	BillPrintListGrid.locked = 0;
	  	BillPrintListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	  	BillPrintListGrid.canChk = 0; //�Ƿ����Ӷ�ѡ��,1Ϊ��ʾCheckBox�У�0Ϊ����ʾ (ȱʡֵ)
	  	BillPrintListGrid.selBoxEventFuncName ="showBill"; //��Ӧ�ĺ���������������   
	  	BillPrintListGrid.hiddenPlus=1; 
	  	BillPrintListGrid.hiddenSubtraction=1;
	  	BillPrintListGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}
</script>
