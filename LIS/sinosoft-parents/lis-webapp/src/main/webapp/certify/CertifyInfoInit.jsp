
<%
	//**************************************************************************************************
	//�������ƣ�CertifyInfoInit.jsp
	//�����ܣ���ѯ��֤��״̬
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
  	fm.MakeDateB.value = nullToEmpty("<%= strCurDate %>");
  	fm.MakeDateE.value = nullToEmpty("<%= strCurDate %>");
  	fm.ManageCom.value = nullToEmpty("<%= tGI.ManageCom %>");
  }
  catch(ex)
  {
    alert("��CertifyInfoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
  
//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
  try
  {
    initInpBox();
    initCardInfoGrid();    
  }
  catch(re)
  {
    alert("CertifyInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��֤��Ϣ�б�ĳ�ʼ��
function initCardInfoGrid()
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
		iArray[3][0]="������";          		 
		iArray[3][1]="90px";            		 
		iArray[3][2]=90;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="������";        		   
		iArray[4][1]="90px";            		 
		iArray[4][2]=90;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="��ʼ��";          		 
		iArray[5][1]="150px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="��ֹ��";         		   
		iArray[6][1]="150px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="����";          		   
		iArray[7][1]="60px";            		 
		iArray[7][2]=60;            			   
		iArray[7][3]=0;              			   

		iArray[8]=new Array();
		iArray[8][0]="��֤״̬";         		   
		iArray[8][1]="90px";            		 
		iArray[8][2]=90;            			   
		iArray[8][3]=0;   
		
		iArray[9]=new Array();
		iArray[9][0]="������";         		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;  
	
		iArray[10]=new Array();
		iArray[10][0]="����Ա";         		   
		iArray[10][1]="60px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=0;      
		
		iArray[11]=new Array();
		iArray[11][0]="��������";       		  //����
		iArray[11][1]="80px";            		//�п�
		iArray[11][2]=100;            			  //�����ֵ
		iArray[11][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[12]=new Array();
		iArray[12][0]="����ʱ��";       		  //����
		iArray[12][1]="80px";            		//�п�
		iArray[12][2]=100;            			  //�����ֵ
		iArray[12][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������ 
		           
	    CardInfoGrid = new MulLineEnter( "document" , "CardInfoGrid" ); 
	    CardInfoGrid.mulLineCount = 5;   
	    CardInfoGrid.displayTitle = 1;
	    CardInfoGrid.locked = 1;
	    CardInfoGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	    //CardInfoGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
	    CardInfoGrid.hiddenPlus=1;
	    CardInfoGrid.hiddenSubtraction=1;
	    CardInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
