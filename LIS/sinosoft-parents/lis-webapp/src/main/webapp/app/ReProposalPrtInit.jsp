<%
//�������ƣ�ReProposalPrtInit.jsp
//�����ܣ�
//�������ڣ�2003-04-3 11:10:36
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<%
GlobalInput globalInput = (GlobalInput)session.getValue("GI");
String strManageCom = globalInput.ManageCom;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    document.all('ContNo').value = '';
    document.all('ManageCom').value = <%=tGI.ComCode%>;
    document.all('Reason').value = '';
    document.all('NeedPay').value = '';
    document.all('BatchNo').value = '';
    //document.all('AgentGroup').value = '';
    //document.all('RiskCode').value = '';
    
  }
  catch(ex)
  {
    alert("��ReProposalPrtInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
  	manageCom = '<%= strManageCom %>';
    initInpBox();
	  initPolGrid();
  }
  catch(re)
  {
    alert("ReProposalPrtInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��

// ������Ϣ�б�ĳ�ʼ��
// ������Ϣ�б�ĳ�ʼ��
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
      iArray[1][0]="������";         		//����
      iArray[1][1]="180px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ӡˢ��";         		//����
      iArray[3][1]="180px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ��������";         		//����
      iArray[4][1]="160px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="ǩ������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      

	  	iArray[7]=new Array();
	  	iArray[7][0]="��ӡ���";
	  	iArray[7][1]="0px";
	  	iArray[7][2]=100;
	  	iArray[7][3]=3;
	  	
	  	iArray[8]=new Array();
	  	iArray[8][0]="��������";
	  	iArray[8][1]="0px";
	  	iArray[8][2]=100;
	  	iArray[8][3]=3;
	  	
	  	iArray[9]=new Array();
	  	iArray[9][0]="��ͥ����";
	  	iArray[9][1]="0px";
	  	iArray[9][2]=100;
	  	iArray[9][3]=3;

      
      PolGrid = new MulLineEnter( "fmSave" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canChk = 1;
      //PolGrid.canSel=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
