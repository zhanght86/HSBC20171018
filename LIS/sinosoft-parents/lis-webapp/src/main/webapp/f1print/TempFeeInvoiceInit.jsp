<%
//�������ƣ�TempFeeInvoiceInit.jsp
//�����ܣ�
//�������ڣ�2009-10-27 15:39:06
//������  ��yanglh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
    String curDate = PubFun.getCurrentDate();
	String StartDate = PubFun.calDate(curDate,-60,"D","2001-01-01");
	String EndDate = PubFun.calDate(curDate,30,"D","2001-01-01");
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('ContNo').value = '';
    document.all('AgentCode').value = '';

    document.all('StartDate').value = '<%=StartDate%>';
	document.all('EndDate').value = '<%=EndDate%>';

  }
  catch(ex)
  {
    alert("��TempFeeInvoiceInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��TempFeeInvoiceInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();   
    initPolGrid(); 
  }
  catch(re)
  {
    alert("TempFeeInvoiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ӧ������";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�ɴ�";         		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="40px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[7]=new Array();
	  iArray[7][0]="�¶�����";         		//����
	  iArray[7][1]="40px";            		//�п�
	  iArray[7][2]=100;            			//�����ֵ
	  iArray[7][3]=0; 

	  iArray[8]=new Array();
	  iArray[8][0]="������";         		//����
	  iArray[8][1]="50px";            		//�п�
	  iArray[8][2]=200;            			//�����ֵ
	  iArray[8][3]=0; 

	  iArray[9]=new Array();
	  iArray[9][0]="����רԱ";         		//����
	  iArray[9][1]="50px";            		//�п�
	  iArray[9][2]=100;            			//�����ֵ
	  iArray[9][3]=0; 
      
      iArray[10]=new Array();
      iArray[10][0]="���ձ�������";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

	  iArray[11]=new Array();
      iArray[11][0]="֪ͨ���";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=3; 									//�Ƿ���������,1��ʾ����0��ʾ������
      
	  iArray[12]=new Array();
      iArray[12][0]="������";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=3; 


	      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 10;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>