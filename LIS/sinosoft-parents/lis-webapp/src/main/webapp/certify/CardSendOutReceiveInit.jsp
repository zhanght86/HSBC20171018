
<%
	//**************************************************************************************************
	//�������ƣ�CardSendOutInfoInit.jsp
	//�����ܣ����ŵ�֤�嵥
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
    initCardSendOutReceiveGrid();    
  }
  catch(re)
  {
    alert("CardSendOutInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��֤��Ϣ�б�ĳ�ʼ��
function initCardSendOutReceiveGrid()
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
		iArray[1][0]="������";          		   
		iArray[1][1]="80px";            		 
		iArray[1][2]=60;            			   
		iArray[1][3]=0;              			   

		iArray[2]=new Array();
		iArray[2][0]="������";         		   
		iArray[2][1]="80px";            		 
		iArray[2][2]=100;            			   
		iArray[2][3]=0;   
		
		iArray[3]=new Array();
		iArray[3][0]="����������";         		   
		iArray[3][1]="100px";            		 
		iArray[3][2]=100;            			   
		iArray[3][3]=0;       
		
		iArray[4]=new Array();
		iArray[4][0]="��֤����";         		 
		iArray[4][1]="60px";            		 
		iArray[4][2]=100;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="��֤����";          		 
		iArray[5][1]="120px";            		 
		iArray[5][2]=85;            			   
    	iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="��֤��ʼ��";          		 
		iArray[6][1]="120px";            		 
		iArray[6][2]=85;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="��֤��ֹ��";        		   
		iArray[7][1]="120px";            		 
		iArray[7][2]=85;            			   
		iArray[7][3]=0;              			   
		
		iArray[8]=new Array();
		iArray[8][0]="����";          		 
		iArray[8][1]="60px";            		 
		iArray[8][2]=100;            		 	   
		iArray[8][3]=0;              			   
		
		iArray[9]=new Array();
		iArray[9][0]="��������";         		   
		iArray[9][1]="80px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;              			   
		
		iArray[10]=new Array();
		iArray[10][0]="����������ǩ�֡�����";         		   
		iArray[10][1]="140px";            		 
		iArray[10][2]=120;            			   
		iArray[10][3]=0;  

		iArray[11]=new Array();
		iArray[11][0]="����ʱ��";         		   
		iArray[11][1]="80px";            		 
		iArray[11][2]=80;            			   
		iArray[11][3]=0;      

		iArray[12]=new Array();
		iArray[12][0]="����";         		   
		iArray[12][1]="40px";            		 
		iArray[12][2]=40;            			   
		iArray[12][3]=0;  

		iArray[13]=new Array();
		iArray[13][0]="����";         		   
		iArray[13][1]="40px";            		 
		iArray[13][2]=40;            			   
		iArray[13][3]=0;  

		iArray[14]=new Array();
		iArray[14][0]="��ʧ";         		   
		iArray[14][1]="40px";            		 
		iArray[14][2]=40;            			   
		iArray[14][3]=0;  

		iArray[15]=new Array();
		iArray[15][0]="����ʱ��";         		   
		iArray[15][1]="80px";            		 
		iArray[15][2]=100;            			   
		iArray[15][3]=0;       

		iArray[16]=new Array();
		iArray[16][0]="����������ǩ�֡�����";         		   
		iArray[16][1]="140px";            		 
		iArray[16][2]=120;            			   
		iArray[16][3]=0;       

 
		           
		CardSendOutReceiveGrid = new MulLineEnter( "document" , "CardSendOutReceiveGrid" ); 
		CardSendOutReceiveGrid.mulLineCount = 5;   
		CardSendOutReceiveGrid.displayTitle = 1;
		CardSendOutReceiveGrid.locked = 1;
		CardSendOutReceiveGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	    CardSendOutReceiveGrid.hiddenPlus=1;
	    CardSendOutReceiveGrid.hiddenSubtraction=1;
	    CardSendOutReceiveGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
