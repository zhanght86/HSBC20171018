<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�WithdrawContInit.jsp
//�����ܣ�������ʼ��
//�������ڣ�2008-10-18
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                       
    document.all('PrtNo').value = '';  
    document.all('AgentCode').value = '';
    document.all('SaleChnl').value = '';
    document.all('ManageCom').value = '';
    document.all('AppntName').value = '';
    document.all('InsuredName').value = '';
    
    document.all('UWWithDReasonCode').value = '';
    document.all('UWWithDReason').value = '';
    document.all('Content').value = '';
  }
  catch(ex)
  {
    alert("��WithdrawContInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
} 

// ���������ĳ�ʼ��
function initHide()
{ 
try
  {                                   
    document.all('PrtNoH').value = '';   
    document.all('ContNoH').value = ''; 
  }
  catch(ex)
  {
    alert("��WithdrawContInit.jsp-->initHide�����з����쳣:��ʼ���������!");
  }   
}                                     

function initForm()
{
  try
  {    
	initInpBox();
	initHide();	
	initWithDContAllGrid();
	initWihtDContGrid();	
	//withdrawQueryClick();
		
  }
  catch(re)
  {
    alert("WithdrawContInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//������Ͷ������ϸ��Ϣ�б�
function initWihtDContGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ͬ��";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="0px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";    	//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ���˿ͻ���";         			//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="Ͷ��������";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��췢��";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=50;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="���������";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="��Լ";         			//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�ӷ�";         			//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������        
      
      iArray[10]=new Array();
      iArray[10][0]="���ռƻ����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[10][1]="120px";         			//�п�
      iArray[10][2]=10;          			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������       
      
      WihtDContGrid = new MulLineEnter( "document" , "WihtDContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      WihtDContGrid.mulLineCount = 5;   
      WihtDContGrid.displayTitle = 1;
      WihtDContGrid.locked = 1;
      WihtDContGrid.canSel = 1;
      WihtDContGrid.hiddenPlus = 1;
      WihtDContGrid.hiddenSubtraction = 1;
      WihtDContGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

//���д�����Ͷ�����б�
function initWithDContAllGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ͬ��";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="0px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";    	//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=130;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ��������";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����������";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="ҵ��Ա";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[5][1]="120px";         			//�п�
      iArray[5][2]=10;          			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�������";    	//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=130;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="ԭ�˱�����״̬";         			//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="������ʱ��";         			//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      WithDContAllGrid = new MulLineEnter( "document" , "WithDContAllGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      WithDContAllGrid.mulLineCount = 5;   
      WithDContAllGrid.displayTitle = 1;
      WithDContAllGrid.locked = 1;
      WithDContAllGrid.canSel = 1;
      WithDContAllGrid.hiddenPlus = 1;
      WithDContAllGrid.hiddenSubtraction = 1;
      WithDContAllGrid.loadMulLine(iArray);       
      WithDContAllGrid.selBoxEventFuncName = "showWihtDContInfo";
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


