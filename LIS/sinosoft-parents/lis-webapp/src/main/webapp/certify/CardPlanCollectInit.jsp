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
    initCardPlanQueryGrid();
    initCardPlanQueryGrid2();
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
    fm.InputMan.value = '<%= strOperator %>';
    fm.InputDate.value = '<%= strCurTime %>';

    document.all('PlanType').value = '';
    document.all('PlanTypeName').value = '';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
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
    iArray[1][0]="��֤����";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="��֤����";         			//����
    iArray[2][1]="120px";            		//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="ӡˢ����";         	//����
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=2;              			//2��ʾ�Ǵ���ѡ��
	iArray[3][9]="ӡˢ����|NOTNULL&NUM"; //�����ʽ������վ�����ʽ����
	iArray[3][22]="calcSumNum";

    iArray[4]=new Array();
    iArray[4][0]="�ֹ�˾�ϼ�ӡˢ��";         			//����
    iArray[4][1]="80px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�ܹ�˾ӡˢ��";         			//����
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][9]="�ܹ�˾ӡˢ��|NOTNULL&INT"; //�����ʽ������վ�����ʽ����
    iArray[5][22]="calcSumNum";
          
    iArray[6]=new Array();
    iArray[6][0]="ӡˢ������";         			//����
    iArray[6][1]="80px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="ӡˢ�ϼƽ��";         			//����
    iArray[7][1]="80px";            		//�п�
    iArray[7][2]=60;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    CardPlanQueryGrid = new MulLineEnter( "document" , "CardPlanQueryGrid" );
    CardPlanQueryGrid.mulLineCount = 0;
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

function initCardPlanQueryGrid2()
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
    iArray[1][0]="��֤����";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="��֤����";         			//����
    iArray[2][1]="120px";            		//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="ӡˢ����";         	//����
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=2;              			//2��ʾ�Ǵ���ѡ��
	iArray[3][9]="ӡˢ����|NOTNULL&NUM"; //�����ʽ������վ�����ʽ����
	iArray[3][22]="calcSumNum";

    iArray[4]=new Array();
    iArray[4][0]="�ֹ�˾ӡˢ��";         			//����
    iArray[4][1]="80px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�ܹ�˾ӡˢ��";         			//����
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][9]="�ܹ�˾ӡˢ��|NOTNULL&INT"; //�����ʽ������վ�����ʽ����
    iArray[5][22]="calcSumNum";
          
    iArray[6]=new Array();
    iArray[6][0]="ӡˢ������";         			//����
    iArray[6][1]="80px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="ӡˢ�ϼƽ��";         			//����
    iArray[7][1]="80px";            		//�п�
    iArray[7][2]=60;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="�ֹ�˾����";         			//����
    iArray[8][1]="80px";            		//�п�
    iArray[8][2]=60;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
    iArray[9]=new Array();
    iArray[9][0]="��ʼ��";         			//����
    iArray[9][1]="120px";            		//�п�
    iArray[9][2]=60;            			//�����ֵ
    iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[9][9]="��ʼ��|NUM";
    
    iArray[10]=new Array();
    iArray[10][0]="��ֹ��";         			//����
    iArray[10][1]="120px";            		//�п�
    iArray[10][2]=60;            			//�����ֵ
    iArray[10][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[10][9]="��ֹ��|NUM";
    
    CardPlanQueryGrid2 = new MulLineEnter( "document" , "CardPlanQueryGrid2" );
    CardPlanQueryGrid2.mulLineCount = 0;
    CardPlanQueryGrid2.displayTitle = 1;
    CardPlanQueryGrid2.canSel=1;
    CardPlanQueryGrid2.canChk=1;
    CardPlanQueryGrid2.hiddenPlus=1;
	CardPlanQueryGrid2.hiddenSubtraction=1;
    CardPlanQueryGrid2.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("��ʼ����������Ϣʱ����"+ex);
  }
}
</script>


