<%
//Creator :������
//Date :2003-04-18
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	String strOperator = globalInput.Operator;
	String managecom = globalInput.ManageCom;
	String strCurTime = PubFun.getCurrentDate();
%>
<script language="JavaScript">
function initForm()
{
  try
  {
    initInpBox();
    initCardPrintQueryGrid();
  }
  catch(re)
  {
    alert("CardPlanPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{
  try
  {       
    fm.managecom.value = '<%= managecom %>';
    fm.PrintOperator.value = '<%= strOperator %>';
    fm.PrintDate.value = '<%= strCurTime %>';
    
    document.all('CertifyCode').value = '';
    document.all('CertifyName').value = '';
    document.all('PlanType').value = '';
    document.all('PlanTypeName').value = '';  
  }
  catch(ex)
  {
    alert("��ʼ��ʱ���ִ��󣡣�����");
  }
}

function initCardPrintQueryGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="ӡˢ���κ�";
    iArray[1][1]="90px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="�ƻ�����";         			//����
    iArray[2][1]="50px";            		//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=2;
    iArray[2][10]="PlanType";						//����״̬
    iArray[2][11]="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤";

    iArray[3]=new Array();
    iArray[3][0]="��֤����";         	//����
    iArray[3][1]="60px";            	//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��֤����";         			//����
    iArray[4][1]="120px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="ӡˢ����";         			//����
    iArray[5][1]="60px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="��ʼ����";         			//����
    iArray[6][1]="150px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=2;                         //�Ƿ���������,1��ʾ����0��ʾ������
               			
    iArray[7]=new Array();
    iArray[7][0]="��ֹ����";         			//����
    iArray[7][1]="150px";            		//�п�
    iArray[7][2]=60;            			//�����ֵ
    iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="ӡˢ����";         			//����
    iArray[8][1]="60px";            		//�п�
    iArray[8][2]=60;            			//�����ֵ
    iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="ӡˢ������";         			//����
    iArray[9][1]="120px";            		//�п�
    iArray[9][2]=60;            			//�����ֵ
    iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="ӡˢ���绰";         			//����
    iArray[10][1]="80px";            		//�п�
    iArray[10][2]=60;            			//�����ֵ
    iArray[10][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="ӡˢ����ϵ��";         			//����
    iArray[11][1]="80px";            		//�п�
    iArray[11][2]=60;            			//�����ֵ
    iArray[11][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="ʹ�ý�ֹ����";         			//����
    iArray[12][1]="80px";            		//�п�
    iArray[12][2]=60;            			//�����ֵ
    iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������    
        
    CardPrintQueryGrid = new MulLineEnter( "document" , "CardPrintQueryGrid" );
    CardPrintQueryGrid.mulLineCount = 5;
    CardPrintQueryGrid.displayTitle = 1;
    CardPrintQueryGrid.canSel=1;
    CardPrintQueryGrid.canChk=1;
    CardPrintQueryGrid.hiddenPlus=1;
	CardPrintQueryGrid.hiddenSubtraction=1;
    CardPrintQueryGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("��ʼ����ѯ����б�ʱ����"+ex);
  }
}

</script>


