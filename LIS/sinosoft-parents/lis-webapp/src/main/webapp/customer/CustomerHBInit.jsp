
<%
//�������ƣ�FinFeeSureInit.jsp
//�����ܣ�����ȷ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    //���ҳ��ؼ��ĳ�ʼ����
    //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";   

    String CurrentDate = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
	GlobalInput tGI = new GlobalInput(); //repair:
	tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
	String ManageCom = tGI.ComCode;
%>                            

<script language="JavaScript">
	
var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
var operator = "<%=tGI.Operator%>";   //��¼����Ա
var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
	try
	{                                   	
	  
	}
	catch(ex)
	{
		alert("��ProposalQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}      
}

// ������ĳ�ʼ��
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
    alert("��ProposalQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initCustomerGrid();
	  initCustomer1Grid();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initCustomerGrid()
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
      iArray[1][0]="�ͻ��˻�����";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�����";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�ͻ�����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=2;
      iArray[4][4]="Currency";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[4][9]="����|code:Currency";;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CustomerGrid.mulLineCount = 5;   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.locked = 1;
      CustomerGrid.canSel = 1;
      CustomerGrid.canChk = 0;
      CustomerGrid.hiddenPlus = 1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.loadMulLine(iArray);  
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
// ������Ϣ�б�ĳ�ʼ��
function initCustomer1Grid()
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
      iArray[1][0]="�ͻ��˻�����";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�����";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ͻ�����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=2;
      iArray[4][4]="Currency";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[4][9]="����|code:Currency";;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      Customer1Grid = new MulLineEnter( "fm" , "Customer1Grid" ); 
      //��Щ���Ա�����loadMulLineǰ
      Customer1Grid.mulLineCount = 5;   
      Customer1Grid.displayTitle = 1;
      Customer1Grid.locked = 1;
      Customer1Grid.canSel = 1;
      Customer1Grid.canChk = 0;
      Customer1Grid.hiddenPlus = 1;
      Customer1Grid.hiddenSubtraction = 1;
      Customer1Grid.loadMulLine(iArray);  
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
