<%
//�������ƣ�ReGroupPolPrintInit.jsp
//�����ܣ�
//�������ڣ�2002-11-26
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
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
    document.all('PrtNo').value = '';
    document.all('GrpContNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
    document.all('RiskCode').value = '';
    document.all('RiskVersion').value = '';
    document.all('GrpName').value = '';
  }
  catch(ex)
  {
    alert("��ReGroupPolPrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
		initGrpPolGrid();
		manageCom = '<%= strManageCom %>';
		initRiskVersion();
  }
  catch(re)
  {
    alert("ReGroupPolPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpPolGrid()
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
      iArray[1][0]="���屣����";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�������ֱ���";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ��������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��Ч����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      GrpPolGrid = new MulLineEnter( "fmSave" , "GrpPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpPolGrid.mulLineCount =5;   
      GrpPolGrid.displayTitle =1;
      GrpPolGrid.canChk = 1;
      GrpPolGrid.canSel = 0;
      GrpPolGrid.hiddenPlus = 1;
		  GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
