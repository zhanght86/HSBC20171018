<%
//�������ƣ�FeeInvoiceInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
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
	
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('PayNo').value = '';
    document.all('IncomeNo').value = '';
    document.all('IncomeType').value = '';
    document.all('PayDate').value = '';
    document.all('MngCom').value = '<%=tG.ManageCom%>';
    document.all('AgentCode').value = '';
  }
  catch(ex)
  {
    alert("��FeeInvoiceInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��FeeInvoiceInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("FeeInvoiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="�����վݺ���";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ʵ�ձ��";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��ͬ��";         		//����
      iArray[3][1]="160px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][10] = "NoType1";
   //   iArray[3][11] = "0|^1|���屣����^2|���˱�����^3|���ĺ�^4|��ͬͶ������^5|����Ͷ������^6|����Ͷ������^7|��ͬӡˢ��^8|����ӡˢ��^9|����ӡˢ��";
      iArray[4][11] = "0|^1|�����ͬ��^2|���˺�ͬ��^3|��ͥ�����ͬ��^10|��ȫ�շѶ�Ӧ�����ĺ�";
      iArray[4][12] = "3";
      iArray[4][18]=300;
      iArray[4][19] = "0";

      iArray[5]=new Array();
      iArray[5][0]="��ʵ�����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="ȷ������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  	iArray[8]=new Array();
      iArray[8][0]="����Ա";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
      
	  iArray[9]=new Array();
      iArray[9][0]="�������";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=2; 
      iArray[9][4]="station";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[9][5]="8";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
     // iArray[9][9]="��������|code:station&NOTNULL";
      iArray[9][18]=250;
      iArray[9][19]= 0 ;      

	  	iArray[10]=new Array();
      iArray[10][0]="�����˱���";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=2; 									//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[10][4]="AgentCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[10][5]="9";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
     // iArray[10][9]="�����˱���|code:AgentCode&NOTNULL";
      iArray[10][18]=250;
      iArray[10][19]= 0 ;      

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
