
<%
	//Name��CerifyImportTakeBackInit.jsp
	//Function����֤�������
	//Author��mw
	//Date: 2009-08-24
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">

<%
String strCurDate = PubFun.getCurrentDate();
%>

function initForm()
{
	initInpBox();
	initSerialInfoGrid();
	initCardImportInfoGrid();    
}

function initInpBox()
{   
	fm.StartDate.value = '<%= strCurDate %>';
  	fm.EndDate.value = '<%= strCurDate %>';
}

function initSerialInfoGrid()
{                               
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="���κ�";         		 
		iArray[1][1]="60px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="����Ա";          		 
		iArray[2][1]="60px";            		 
		iArray[2][2]=85;            			   
    	     iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="��������";          		 
		iArray[3][1]="70px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;   
		
		iArray[4]=new Array();
		iArray[4][0]="����ʱ��";          		 
		iArray[4][1]="70px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;   
		           
	    SerialInfoGrid = new MulLineEnter( "document" , "SerialInfoGrid" ); 
	    SerialInfoGrid.mulLineCount = 0;   
	    SerialInfoGrid.displayTitle = 1;
	    SerialInfoGrid.locked = 0;
	    SerialInfoGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	    SerialInfoGrid.hiddenPlus=1;
	    SerialInfoGrid.hiddenSubtraction=1;
	    SerialInfoGrid.selBoxEventFuncName = "showStatistics";
	    SerialInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ��֤��Ϣ�б�ĳ�ʼ��
function initCardImportInfoGrid()
{                               
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="��֤����";         		 
		iArray[1][1]="60px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="��֤����";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
    	iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="��֤��ʼ��";          		 
		iArray[3][1]="120px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="��֤��ֹ��";        		   
		iArray[4][1]="120px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="����";          		 
		iArray[5][1]="60px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="������";         		   
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="����Ա";          		   
		iArray[7][1]="60px";            		 
		iArray[7][2]=60;            			   
		iArray[7][3]=0;              			   

		iArray[8]=new Array();
		iArray[8][0]="����״̬";         		   
		iArray[8][1]="60px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;   
		
		iArray[9]=new Array();
		iArray[9][0]="��������";         		   
		iArray[9][1]="80px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;  

		iArray[10]=new Array();
		iArray[10][0]="����ʱ��";         		   
		iArray[10][1]="80px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=0;  
		
		iArray[11]=new Array();
		iArray[11][0]="�������";         		   
		iArray[11][1]="80px";            		 
		iArray[11][2]=100;            			   
		iArray[11][3]=0;  
		
		iArray[12]=new Array();
		iArray[12][0]="����ʧ��ԭ��";         		   
		iArray[12][1]="80px";            		 
		iArray[12][2]=100;            			   
		iArray[12][3]=0;  
		           
	    CardImportInfoGrid = new MulLineEnter( "document" , "CardImportInfoGrid" ); 
	    CardImportInfoGrid.mulLineCount = 0;   
	    CardImportInfoGrid.displayTitle = 1;
	    CardImportInfoGrid.locked = 0;
	    CardImportInfoGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	    CardImportInfoGrid.hiddenPlus=1;
	    CardImportInfoGrid.hiddenSubtraction=1;
	    CardImportInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
