<%
//name :RateCardInput.jsp
//Creator :zz
//date :2008-06-20
//�������ʱ��ѯ����
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.bl.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
%>
<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('Riskcode').value = '';
    document.all('ProductPlan').value = '';
    document.all('InsuYear').value = '';
    document.all('InsuYearFlag').value = '';
    document.all('Prem').value = '';
  }
  catch(ex)
  {
    alert("RateCardQueryInit-initInpBox���г�ʼ���ǳ��ִ��󣡣�����");
  }
}


//��ʼ������
function initForm()
{
  try
  {
    initInpBox();
    initRateCardGrid();
  }
  catch(re)
  {
    alert("initForm:CertifyFeeQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initRateCardGrid()
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
      iArray[1][0]="���ֱ���";    	//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[4]=new Array();
      iArray[4][0]="�������ڵ�λ";         			//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����";         			//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=40;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����";         			//����
      iArray[6][1]="40px";            		//�п�
      iArray[6][2]=40;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[7]=new Array();
      iArray[7][0]="��Ʒ�ƻ�";         			//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=40;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      RateCardGrid = new MulLineEnter( "fm" , "RateCardGrid" ); 
      RateCardGrid.mulLineCount = 5;   
      RateCardGrid.displayTitle = 1;
	  RateCardGrid.hiddenSubtraction=1;
	  RateCardGrid.hiddenPlus=1;
      RateCardGrid.canSel=1;
      RateCardGrid.locked = 1;	
      RateCardGrid.loadMulLine(iArray);  
      RateCardGrid.detailInfo="������ʾ��ϸ��Ϣ";

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
