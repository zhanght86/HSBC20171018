<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�InsuredUWInfoInit.jsp
//�����ܣ��˹��˱���������Ϣ
//�������ڣ�2005-01-04 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInsuredInfoBox()
{ 
  queryInsuredInfo();
  
  try
  {                    
		fm.all('ContNo').value = ContNo;
		fm.all('MissionID').value = MissionID;
		fm.all('SubMissionID').value = SubMissionID;
		fm.all('PrtNo').value = PrtNo;
  }
  catch(ex)
  {
    alert("��InsuredUWInfoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    //alert(1);
    initInsuredInfoBox();
    //alert(2);
    initRiskGrid();   
    //alert(3);
    initOldInfoGrid()
    //initPolGrid();    
    //alert(4);
    queryRiskInfo();
   
  
    initSendTrace();
    showDisDesb();
  }
  catch(re)
  {
    alert("��PManuUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initRiskGrid()
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
      iArray[1][0]="�������ֺ���";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ձ�������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����ֹ��";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[9]=new Array();
      iArray[9][0]="���Ѽ��(��)";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��������";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
           
      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskGrid.mulLineCount = 3;   
      RiskGrid.displayTitle = 1;
      RiskGrid.locked = 1;
      RiskGrid.canSel = 1;
      RiskGrid.hiddenPlus = 1;
      RiskGrid.hiddenSubtraction = 1;
      RiskGrid.loadMulLine(iArray);     
      
      RiskGrid. selBoxEventFuncName = "showRiskResult";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}




// ������Ϣ�б�ĳ�ʼ��
function initOldInfoGrid()
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
      iArray[1][0]="��������";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ʼʱ��";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��ȫ��Ϣ";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[6][7]="GoToBqInfo";
			iArray[6][14]="��ȫ��Ϣ";
			
      iArray[7]=new Array();
      iArray[7][0]="��ѯ������ϸ";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[7][7]="GoToLpInfoMx";
			iArray[7][14]="��ѯ������ϸ";
      
      iArray[8]=new Array();
      iArray[8][0]="��ѯ��ȫ��ϸ";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[8][7]="GoToBqInfoMx";
			iArray[8][14]="��ѯ��ȫ��ϸ";
       
      
      OldInfoGrid = new MulLineEnter( "fm" , "OldInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      OldInfoGrid.mulLineCount = 3;   
      OldInfoGrid.displayTitle = 1;
      OldInfoGrid.locked = 1;
      OldInfoGrid.canSel = 0;
      OldInfoGrid.hiddenPlus = 1;
      OldInfoGrid.hiddenSubtraction = 1;
      OldInfoGrid.loadMulLine(iArray);     
      
      OldInfoGrid.selBoxEventFuncName = "";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
