
<%
	//**************************************************************************************************
	//�������ƣ�CertifyDesInfoInit.jsp
	//�����ܣ���֤��Ϣ��ѯ
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
  	
  	fm.State.value = '';
  	fm.StateName.value = '';   
  	 	
  	fm.CertifyClass.value = '';
  	fm.CertifyClassName.value = '';
  	
  	fm.CertifyClass2.value = '';
  	fm.CertifyClass2Name.value = '';
	
  	fm.MakeDateB.value = '';
  	fm.MakeDateE.value = '';
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
		iArray[3][0]="��֤����";          		 
		iArray[3][1]="80px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="����ʱ��";        		   
		iArray[4][1]="80px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="ͣ��ʱ��";          		 
		iArray[5][1]="80px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   

	    CardSendOutInfoGrid = new MulLineEnter( "document" , "CardSendOutInfoGrid" ); 
	    CardSendOutInfoGrid.mulLineCount = 5;   
	    CardSendOutInfoGrid.displayTitle = 1;
	    CardSendOutInfoGrid.locked = 1;
	    CardSendOutInfoGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
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
