
<%
	//**************************************************************************************************
	//�������ƣ�CardLossInfoInit.jsp
	//�����ܣ���ʧ�嵥
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
  	fm.CertifyCode.value = '';
  	fm.CertifyName.value = '';
  	fm.CertifyClass.value = '';
  	fm.CertifyClassName.value = '';
  	fm.CertifyClass2.value = '';
  	fm.CertifyClass2Name.value = '';
  	fm.SendOutCom.value = '';
  	fm.ReceiveCom.value = '';  	
  	fm.MakeDateB.value = '<%= strCurDate %>';
  	fm.MakeDateE.value = '<%= strCurDate %>';
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
    initCardSendOutInfoGrid();    
  }
  catch(re)
  {
    alert("CardSendOutInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��֤��Ϣ�б�ĳ�ʼ��
function initCardSendOutInfoGrid()
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
		iArray[3][0]="��ʼ����";          		 
		iArray[3][1]="120px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;   
		           			   
		iArray[4]=new Array();
		iArray[4][0]="��ֹ����";          		 
		iArray[4][1]="120px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;   
		
		iArray[5]=new Array();
		iArray[5][0]="����";          		 
		iArray[5][1]="40px";            		 
		iArray[5][2]=85;            			   
		iArray[5][3]=0;   
				
		iArray[6]=new Array();
		iArray[6][0]="��ʧ����";        		   
		iArray[6][1]="80px";            		 
		iArray[6][2]=85;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="�Ǳ�����";          		 
		iArray[7][1]="80px";            		 
		iArray[7][2]=100;            		 	   
		iArray[7][3]=0;              			   
		
		iArray[8]=new Array();
		iArray[8][0]="��ֽ����";         		   
		iArray[8][1]="80px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;              			   
		
		iArray[9]=new Array();
		iArray[9][0]="��ʧ�߱���";          		   
		iArray[9][1]="80px";            		 
		iArray[9][2]=60;            			   
		iArray[9][3]=0;    
		
		iArray[10]=new Array();
		iArray[10][0]="��ʧ������";          		   
		iArray[10][1]="120px";            		 
		iArray[10][2]=60;            			   
		iArray[10][3]=0;   
		
	    CardSendOutInfoGrid = new MulLineEnter( "document" , "CardSendOutInfoGrid" ); 
	    CardSendOutInfoGrid.mulLineCount = 5;   
	    CardSendOutInfoGrid.displayTitle = 1;
	    CardSendOutInfoGrid.locked = 0;
	    CardSendOutInfoGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	    CardSendOutInfoGrid.hiddenPlus=1;
	    CardSendOutInfoGrid.hiddenSubtraction=1;
	    CardSendOutInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
