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
    
    document.all('PlanID').value = '';
    document.all('CertifyCode').value = '';
    document.all('PlanType').value = '';
    document.all('PlanState').value = '';
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
    iArray[1][1]="120px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="��֤����";         			//����
    iArray[2][1]="60px";            		//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2][4]="CertifyCode";     //�Ƿ����ô���:null||""Ϊ������
    iArray[2][5]="2|3";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
    iArray[2][6]="0|1";             //��������з������ô����еڼ�λֵ
    iArray[2][9]="��֤����|code:CertifyCode&NOTNULL";
    
    iArray[3]=new Array();
    iArray[3][0]="�ƻ�����";         	//����
    iArray[3][1]="60px";            	//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=2;              			//2��ʾ�Ǵ���ѡ��
    iArray[3][10]="PlanType";						//����״̬
    iArray[3][11]="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤";
    
    iArray[4]=new Array();
    iArray[4][0]="��֤����";         			//����
    iArray[4][1]="60px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="��������";         			//����
    iArray[5][1]="60px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="�ܼ�";         			//����
    iArray[6][1]="60px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="������";         			//����
    iArray[7][1]="60px";            		//�п�
    iArray[7][2]=60;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="�������";         			//����
    iArray[8][1]="60px";            		//�п�
    iArray[8][2]=60;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="�ƻ�״̬";         			//����
    iArray[9][1]="60px";            		//�п�
    iArray[9][2]=60;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="�������";         			//����
    iArray[10][1]="60px";            		//�п�
    iArray[10][2]=60;            			//�����ֵ
    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    CardPlanQueryGrid = new MulLineEnter( "document" , "CardPlanQueryGrid" );
    CardPlanQueryGrid.mulLineCount = 5;
    CardPlanQueryGrid.displayTitle = 1;
	CardPlanQueryGrid.canSel=1;
    CardPlanQueryGrid.canChk=1;
    CardPlanQueryGrid.hiddenPlus=1;
	CardPlanQueryGrid.hiddenSubtraction=1;	
	//CardPlanQueryGrid.setRowColClass((tFillNo-1),tLineCount,'warn');
    CardPlanQueryGrid.loadMulLine(iArray);
    CardPlanQueryGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    alert("��ʼ�����������Ϣʱ����"+ex);
  }
}

</script>


