<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupContDeleteInit.jsp
//�����ܣ��ŵ�����ɾ��
//�������ڣ�2004-12-06 11:10:36
//������  ��Zhangrong
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
function initInpBox()
{ 

  try
  {
    // ������ѯ����  
    fm.all('QGrpProposalNo').value = '';
    fm.all('QGrpContNo').value = '';
    fm.all('QManageCom').value = '';
    fm.all('QAgentCode').value = '';
    fm.all('QState').value = 'δǩ��';
    fm.all('QPrtNo').value = '';                                         
  }
  catch(ex)
  {
    alert("��inGroupContDeleteInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ����������Ϣ��ʾ��ĳ�ʼ��������¼���֣�
function initPolBox()
{ 
  try
  {                                   
	// ������ѯ����

  }
  catch(ex)
  {
    alert("��ContUWInit.jsp-->InitPolBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
 
    initGrpGrid();
  }
  catch(re)
  {
    alert("ContUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


// ������Ϣ�б�ĳ�ʼ��
function initGrpGrid()
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
      iArray[1][0]="���˺�ͬ����";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������˿ͻ���";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������������";         		//����
      iArray[3][1]="160px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;  
      
      iArray[4]=new Array();
      iArray[4][0]="�Ա�";         		//����
      iArray[4][1]="160px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[6]=new Array();
      iArray[6][0]="֤������";         		//����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[7]=new Array();
      iArray[7][0]="֤������";         		//����
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      
              			

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount = 0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 0;
      GrpGrid.canChk = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.loadMulLine(iArray);
           
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
