
<%
	//**************************************************************************************************
	//�������ƣ�CardStatInit.jsp
	//�����ܣ���֤�ۺϱ���
	//�������ڣ� 2009-02-18
	//������  �� mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//**************************************************************************************************
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
<%
	String strCurDate = PubFun.getCurrentDate();
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	String strOperator = globalInput.Operator;
	String managecom = globalInput.ManageCom;
%>
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
  try
  {
  	fm.reset();  	
  	fm.makedateB.value = "<%= strCurDate %>";
  	fm.makedateE.value = "<%= strCurDate %>";
  }
  catch(ex)
  {
    alert("��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initCardPrintInfoGrid();  
    initCardPrintInfo2Grid();   
  }
  catch(re)
  {
    alert("CardSendOutInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��֤��Ϣ�б�ĳ�ʼ��
function initCardPrintInfoGrid()
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
		iArray[1][0]="��������";         		 
		iArray[1][1]="60px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="��������";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
    	iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="��֤����";          		 
		iArray[3][1]="60px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="��֤����";        		   
		iArray[4][1]="120px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="����";          		 
		iArray[5][1]="60px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="�����������";         		   
		iArray[6][1]="70px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;   

		iArray[7]=new Array();
		iArray[7][0]="�ϼ���������";         		   
		iArray[7][1]="70px";            		 
		iArray[7][2]=100;            			   
		iArray[7][3]=0;  
				           			   
		iArray[8]=new Array();
		iArray[8][0]="�ܾ�������";         		   
		iArray[8][1]="70px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;    

	    CardPrintInfoGrid = new MulLineEnter( "document" , "CardPrintInfoGrid" ); 
	    CardPrintInfoGrid.mulLineCount = 5;   
	    CardPrintInfoGrid.displayTitle = 1;
	    CardPrintInfoGrid.locked = 1;
	    CardPrintInfoGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	    CardPrintInfoGrid.hiddenPlus=1;
	    CardPrintInfoGrid.hiddenSubtraction=1;
	    CardPrintInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

function initCardPrintInfo2Grid()
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
		iArray[1][0]="��������";         		 
		iArray[1][1]="60px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="��������";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
    	iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="��֤����";          		 
		iArray[3][1]="60px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="��֤����";        		   
		iArray[4][1]="120px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="�Զ�����";          		 
		iArray[5][1]="60px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="�ֹ�����";         		   
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;   
		           			   
		iArray[7]=new Array();
		iArray[7][0]="ʹ������";         		   
		iArray[7][1]="60px";            		 
		iArray[7][2]=100;            			   
		iArray[7][3]=0;    
		
		iArray[8]=new Array();
		iArray[8][0]="ͣ������";         		   
		iArray[8][1]="60px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;    
		
		iArray[9]=new Array();
		iArray[9][0]="��ʧ";         		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;    
		
		iArray[10]=new Array();
		iArray[10][0]="����";         		   
		iArray[10][1]="60px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=0;    

	    CardPrintInfo2Grid = new MulLineEnter( "document" , "CardPrintInfo2Grid" ); 
	    CardPrintInfo2Grid.mulLineCount = 5;   
	    CardPrintInfo2Grid.displayTitle = 1;
	    CardPrintInfo2Grid.locked = 1;
	    CardPrintInfo2Grid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	    CardPrintInfo2Grid.hiddenPlus=1;
	    CardPrintInfo2Grid.hiddenSubtraction=1;
	    CardPrintInfo2Grid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
