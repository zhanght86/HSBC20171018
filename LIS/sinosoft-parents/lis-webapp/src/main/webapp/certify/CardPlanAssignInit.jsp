
<%
	//Creator :������
	//Date :2003-04-18
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
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
    initCardPlanQueryGrid();
    initCardPlanListGrid();
  }
  catch(re)
  {
    alert("CertifyDescInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{
  try
  {       
    fm.managecom.value = '<%= managecom %>';
    fm.AppOperator.value = '<%= strOperator %>';
    fm.MakeDate.value = '<%= strCurTime %>';
    
    document.all('CertifyCode').value = '';
    document.all('PlanType').value = '';
  }
  catch(ex)
  {
    alert("���г�ʼ���ǳ��ִ��󣡣�����");
  }
}

function initCardPlanQueryGrid()
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
    iArray[1][0]="�ƻ���ʶ";
    iArray[1][1]="80px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="�ֹ�˾����";         			//����
    iArray[2][1]="60px";            		//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ֹ�˾����";         	//����
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=0;              			//2��ʾ�Ǵ���ѡ��

    iArray[4]=new Array();
    iArray[4][0]="������������";         			//����
    iArray[4][1]="60px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="����������";         			//����
    iArray[5][1]="60px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][9]="����������|NOTNULL&NUM&value>=0";  //�����ʽ
    
    CardPlanQueryGrid = new MulLineEnter( "document" , "CardPlanQueryGrid" );
    CardPlanQueryGrid.mulLineCount = 5;
    CardPlanQueryGrid.displayTitle = 1;
    CardPlanQueryGrid.canSel=1;
    CardPlanQueryGrid.canChk=1;
    CardPlanQueryGrid.hiddenPlus=1;
	CardPlanQueryGrid.hiddenSubtraction=1;
    CardPlanQueryGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("��ʼ����������Ϣʱ����"+ex);
  }
}

function initCardPlanListGrid()
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
    iArray[1][0]="�ֹ�˾����";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="�ֹ�˾����";         			//����
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��֤����";         	//����
    iArray[3][1]="60px";            	//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=0;              			//2��ʾ�Ǵ���ѡ��

    iArray[4]=new Array();
    iArray[4][0]="��֤����";         			//����
    iArray[4][1]="80px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�ܹ�˾���";         			//����
    iArray[5][1]="60px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="����������";         			//����
    iArray[6][1]="60px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="ӡˢ������";         			//����
    iArray[7][1]="60px";            		//�п�
    iArray[7][2]=60;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    CardPlanListGrid = new MulLineEnter( "document" , "CardPlanListGrid" );
    CardPlanListGrid.mulLineCount = 5;
    CardPlanListGrid.displayTitle = 1;
    CardPlanListGrid.canSel=1;
    CardPlanListGrid.canChk=0;
    CardPlanListGrid.hiddenPlus=1;
	CardPlanListGrid.hiddenSubtraction=1;
    CardPlanListGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("��ʼ����������Ϣʱ����"+ex);
  }
}
</script>


