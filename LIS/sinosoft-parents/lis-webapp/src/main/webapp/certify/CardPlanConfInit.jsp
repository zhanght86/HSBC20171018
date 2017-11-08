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
    initCardPlanQueryDetailGrid();
    initCardPlanPackGrid();
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
    fm.ConOperator.value = '<%= strOperator %>';
    fm.MakeDate.value = '<%= strCurTime %>';
  }
  catch(ex)
  {
    alert("���г�ʼ��ʱ���ִ��󣡣�����");
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
    iArray[1][0]="�������";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="�ƻ�����";         			//����
    iArray[2][1]="60px";            		//�п�
    iArray[2][2]=60;            			//�����ֵ
    iArray[2][3]=2;
    iArray[2][10]="PlanType";						//����״̬
    iArray[2][11]="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤";    

    iArray[3]=new Array();
    iArray[3][0]="������";         			//����
    iArray[3][1]="60px";            		//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="��������";         			//����
    iArray[4][1]="60px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    CardPlanQueryGrid = new MulLineEnter( "document" , "CardPlanQueryGrid" );
    CardPlanQueryGrid.mulLineCount = 5;
    CardPlanQueryGrid.displayTitle = 1;
    CardPlanQueryGrid.canSel=1;
    CardPlanQueryGrid.hiddenPlus=1;
	CardPlanQueryGrid.hiddenSubtraction=1;
	CardPlanQueryGrid.selBoxEventFuncName = "showDetail";
    CardPlanQueryGrid.loadMulLine(iArray);
    CardPlanQueryGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    alert("��ʼ�����������Ϣʱ����"+ex);
  }
}

function initCardPlanQueryDetailGrid()
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
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="��֤����";         			//����
    iArray[2][1]="60px";            		//�п�
    iArray[2][2]=60;            			//�����ֵ
    iArray[2][3]=2;
    iArray[2][10]="CertifyClass";						//����״̬
    iArray[2][11]="0|^D|��Ҫ�м۵�֤^B|��Ҫ�հ׵�֤^P|��ͨ��֤";  

    iArray[3]=new Array();
    iArray[3][0]="��֤����";         			//����
    iArray[3][1]="60px";            		//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="��֤����";         			//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=80;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="��֤����";         			//����
    iArray[5][1]="60px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="��������";         			//����
    iArray[6][1]="60px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="��������";         			//����
    iArray[7][1]="60px";            		//�п�
    iArray[7][2]=60;            			//�����ֵ
    iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[7][9]="��������|NOTNULL&INT&value>=0";  //�����ʽ

    iArray[8]=new Array();
    iArray[8][0]="�ܼ�";         			//����
    iArray[8][1]="60px";            		//�п�
    iArray[8][2]=60;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
    CardPlanQueryDetailGrid = new MulLineEnter( "document" , "CardPlanQueryDetailGrid" );
    CardPlanQueryDetailGrid.mulLineCount = 5;
    CardPlanQueryDetailGrid.displayTitle = 1;
	CardPlanQueryDetailGrid.canSel=1;
    CardPlanQueryDetailGrid.canChk=1;
    CardPlanQueryDetailGrid.hiddenPlus=1;
	CardPlanQueryDetailGrid.hiddenSubtraction=1;
    CardPlanQueryDetailGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("��ʼ�����������Ϣʱ����"+ex);
  }
}

function initCardPlanPackGrid()
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
    iArray[1][0]="�ƻ�����";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=2;
    iArray[1][10]="PlanType";						//����״̬
    iArray[1][11]="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤";    

    iArray[2]=new Array();
    iArray[2][0]="��֤����";
    iArray[2][1]="60px";
    iArray[2][2]=100;
    iArray[2][3]=2;
    iArray[2][10]="CertifyClass";						//����״̬
    iArray[2][11]="0|^D|��Ҫ�м۵�֤^B|��Ҫ�հ׵�֤^P|��ͨ��֤";  
    
    iArray[3]=new Array();
    iArray[3][0]="��֤����";     		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[3][1]="60";        			//�п�
    iArray[3][2]=80;          			//�����ֵ
    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��֤����";         			//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="��֤����";         			//����
    iArray[5][1]="60px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="��������";         			//����
    iArray[6][1]="60px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�ܼ�";         			//����
    iArray[7][1]="60px";            		//�п�
    iArray[7][2]=60;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    CardPlanPackGrid = new MulLineEnter( "document" , "CardPlanPackGrid" );
    CardPlanPackGrid.mulLineCount = 5;
    CardPlanPackGrid.displayTitle = 1;
	CardPlanPackGrid.canSel=1;
    CardPlanPackGrid.hiddenPlus=1;
	CardPlanPackGrid.hiddenSubtraction=1;	
    CardPlanPackGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("��ʼ�����������Ϣʱ����"+ex);
  }
}
</script>


