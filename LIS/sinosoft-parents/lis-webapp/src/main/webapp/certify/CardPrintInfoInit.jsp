
<%
	//**************************************************************************************************
	//�������ƣ�CardPrintInfoInit.jsp
	//�����ܣ���֤ӡˢ��ѯ
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
	fm.ManageCom.value = nullToEmpty("<%= tGI.ManageCom %>");
  	fm.MakeDateB.value =nullToEmpty("<%= strCurDate %>");
  	fm.MakeDateE.value = nullToEmpty("<%= strCurDate %>");
  }
  catch(ex)
  {
    alert("��ʼ���������!");
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
    initCardPrintInfoGrid();    
  }
  catch(re)
  {
    alert("CardPrintInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
		iArray[2][0]="�ƻ�����";          		 
		iArray[2][1]="60px";            		 
		iArray[2][2]=85;            			   
    	iArray[2][3]=2;
    	iArray[2][10]="PlanType";						//����״̬
    	iArray[2][11]="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤";
		
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
		iArray[5][0]="��λ";          		 
		iArray[5][1]="60px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="ӡˢ����";         		   
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="��ʼ����";          		   
		iArray[7][1]="120px";            		 
		iArray[7][2]=60;            			   
		iArray[7][3]=0;              			   

		iArray[8]=new Array();
		iArray[8][0]="��ֹ����";         		   
		iArray[8][1]="120px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;   
		
		iArray[9]=new Array();
		iArray[9][0]="ӡˢ����";         		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;  
	
		iArray[10]=new Array();
		iArray[10][0]="ӡˢ������";         		   
		iArray[10][1]="100px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=0;      
		
		iArray[11]=new Array();
		iArray[11][0]="ʹ�ý�ֹ����";       		  //����
		iArray[11][1]="80px";            		//�п�
		iArray[11][2]=100;            			  //�����ֵ
		iArray[11][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[12]=new Array();
		iArray[12][0]="ӡˢ����";       		  //����
		iArray[12][1]="80px";            		//�п�
		iArray[12][2]=100;            			  //�����ֵ
		iArray[12][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������ 
		
		iArray[13]=new Array();
		iArray[13][0]="״̬";       		  //����
		iArray[13][1]="80px";            		//�п�
		iArray[13][2]=100;            			  //�����ֵ
		iArray[13][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������ 
		    
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
