
<%
	//**************************************************************************************************
	//�������ƣ�CardPlanInfoInit.jsp
	//�����ܣ���֤�ƻ���ѯ
	//�������ڣ� 2009-02-19
	//������  �� mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//**************************************************************************************************
%>
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
    initCardPlanDetailGrid();
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
    document.all('ManageCom').value = "<%= managecom %>";
    //document.all('PlanID').value = '';
    document.all('Grade').value = '';
    
    document.all('PlanType').value = '';
    document.all('PlanTypeName').value = '';   
     
    document.all('PlanState').value = '';
    document.all('PlanStateName').value = '';

    document.all('CertifyClass').value = '';
    document.all('CertifyClassName').value = '';
    
    document.all('CertifyClass2').value = '';
    document.all('CertifyClass2Name').value = '';
                
    document.all('MakeDateB').value = "<%= strCurTime %>";      
    document.all('MakeDateE').value = "<%= strCurTime %>";
  }
  catch(ex)
  {
    alert("���г�ʼ��ʱ���ִ���");
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
    iArray[1][1]="50px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="�ƻ�����";         			//����
    iArray[2][1]="50px";            		//�п�
    iArray[2][2]=60;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="������";         			//����
    iArray[3][1]="50px";            		//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="��������";         			//����
    iArray[4][1]="50px";            		//�п�
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
    alert("��ʼ����ѯ����б�ʱ����"+ex);
  }
}

function initCardPlanDetailGrid()
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
    iArray[2][0]="�������";         			//����
    iArray[2][1]="50px";            		//�п�
    iArray[2][2]=60;            			//�����ֵ
    iArray[2][3]=0;   						//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="�ƻ�����";         			//����
    iArray[3][1]="50px";            		//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��֤����";         			//����
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="��֤����";         			//����
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="��֤����";         			//����
    iArray[6][1]="60px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="��������";         			//����
    iArray[7][1]="50px";            		//�п�
    iArray[7][2]=60;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="��������";         			//����
    iArray[8][1]="50px";            		//�п�
    iArray[8][2]=60;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="��������";         			//����
    iArray[9][1]="50px";            		//�п�
    iArray[9][2]=60;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="����������";         			//����
    iArray[10][1]="75px";            		//�п�
    iArray[10][2]=75;            			//�����ֵ
    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="ӡˢ����";         			//����
    iArray[11][1]="50px";            		//�п�
    iArray[11][2]=60;            			//�����ֵ
    iArray[11][3]=0;   //�Ƿ���������,1��ʾ����0��ʾ������
               			
    iArray[12]=new Array();
    iArray[12][0]="�ܼ�";         			//����
    iArray[12][1]="60px";            		//�п�
    iArray[12][2]=60;            			//�����ֵ
    iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[13]=new Array();
    iArray[13][0]="�ƻ�״̬";         			//����
    iArray[13][1]="60px";            		//�п�
    iArray[13][2]=60;            			//�����ֵ
    iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
    iArray[14]=new Array();
    iArray[14][0]="��������";         			//����
    iArray[14][1]="80px";            		//�п�
    iArray[14][2]=60;            			//�����ֵ
    iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[15]=new Array();
    iArray[15][0]="��������";         			//����
    iArray[15][1]="80px";            		//�п�
    iArray[15][2]=60;            			//�����ֵ
    iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    CardPlanDetailGrid = new MulLineEnter( "document" , "CardPlanDetailGrid" );
    CardPlanDetailGrid.mulLineCount = 5;
    CardPlanDetailGrid.displayTitle = 1;
    CardPlanDetailGrid.canSel=1;
    CardPlanDetailGrid.canChk=0;
    CardPlanDetailGrid.hiddenPlus=1;
	CardPlanDetailGrid.hiddenSubtraction=1;	
    CardPlanDetailGrid.loadMulLine(iArray);
    CardPlanDetailGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    alert("��ʼ����ϸ��Ϣʱ����"+ex);
  }
}

</script>
