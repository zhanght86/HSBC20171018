
<%
	//**************************************************************************************************
	//�������ƣ�CardPrinteryFeeStatInit.jsp
	//�����ܣ�ӡˢ������ͳ�Ʊ���
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
%>

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
  try
  {
  	fm.reset();
  	fm.Printery.value = '';
  	
  	fm.CertifyCode.value = '';
  	fm.CertifyName.value = '';
	fm.PlanType.value = '';
  	fm.PlanTypeName.value = '';
  	
  	fm.PrintDateB.value = '';
  	fm.PrintDateE.value = '';
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
		iArray[1][0]="ӡˢ���κ�";         		 
		iArray[1][1]="120px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="ӡˢ������";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
    	iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="��֤����";          		 
		iArray[3][1]="80px";            		 
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
		iArray[6][0]="����";         		   
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="�ϼƽ��";          		   
		iArray[7][1]="60px";            		 
		iArray[7][2]=60;            			   
		iArray[7][3]=0;              			   

		iArray[8]=new Array();
		iArray[8][0]="ӡˢ����";          		   
		iArray[8][1]="80px";            		 
		iArray[8][2]=60;            			   
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

</script>
