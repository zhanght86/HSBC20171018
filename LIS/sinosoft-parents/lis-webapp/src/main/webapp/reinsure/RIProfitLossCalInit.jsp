<%@include file="/i18n/language.jsp"%>
 <html>
<%
//name :LRProfitLossCalInit.jsp
//function : 
//Creator :zhangbin
//date :2007-3-14
%>

<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 	String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);                	               	
 %>
<script type="text/javascript">

function initInpBox()
{
	try
	{
		fm.RIcomCode.value="";
		fm.ContNo.value="";
		fm.CalYear.value="2010";
	}
	catch(ex)
	{
		myAlert("���г�ʼ���ǳ��ִ���");
	}
}
;

// �����б�ĳ�ʼ��
function initSelBox(){
  try{
  }
  catch(ex){
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
	try
	{
		initInpBox();
		initSelBox();
		initIncomeGrid();
		initPayoutGrid();
		
		queryAuditTask(); //����˲�ѯ
	}
	catch(re)
	{
		myAlert("3CertifyDescInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}

function initIncomeGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="����֧������";    //����
    iArray[1][1]="50px";            	//�п�
    iArray[1][2]=200;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="��������";
    iArray[2][1]="80px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    iArray[3]=new Array();
    iArray[3][0]="��������";     //����
    iArray[3][1]="100px";            	//�п�
    iArray[3][2]=200;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="¼������";     //����
    iArray[4][1]="50px";            	//�п�
    iArray[4][2]=200;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="����ֵ";     //����
    iArray[5][1]="50px";            	//�п�
    iArray[5][2]=200;            			//�����ֵ
    iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][9]="����ֵ|notnull&num";
    
    iArray[6]=new Array();
    iArray[6][0]="����ֵУ��";     //����
    iArray[6][1]="50px";            	//�п�
    iArray[6][2]=200;            			//�����ֵ
    iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="¼�����ͱ���";     //����
    iArray[7][1]="50px";            	//�п�
    iArray[7][2]=200;            			//�����ֵ
    iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="���κ�";     //����
    iArray[8][1]="50px";            	//�п�
    iArray[8][2]=50;            			//�����ֵ
    iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
       
    IncomeGrid = new MulLineEnter( "fm" , "IncomeGrid" );
    IncomeGrid.mulLineCount = 0;
    IncomeGrid.displayTitle = 1;
    IncomeGrid.hiddenPlus=1;   
    IncomeGrid.hiddenSubtraction=1; 
    //IncomeGrid.canSel=1;
    IncomeGrid.loadMulLine(iArray);
    IncomeGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

function initPayoutGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="֧�������";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=3;

    iArray[2]=new Array();
    iArray[2][0]="֧��������";     //����
    iArray[2][1]="100px";            	//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="¼������";     //����
    iArray[3][1]="50px";            	//�п�
    iArray[3][2]=200;            			//�����ֵ
    iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="֧��ֵ";     //����
    iArray[4][1]="50px";            	//�п�
    iArray[4][2]=200;            			//�����ֵ
    iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[4][9]="֧��ֵ|notnull&num";
    
    iArray[5]=new Array();
    iArray[5][0]="֧��ֵУ��";     //����
    iArray[5][1]="50px";            	//�п�
    iArray[5][2]=200;            			//�����ֵ
    iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    PayoutGrid = new MulLineEnter( "fm" , "PayoutGrid" );
    PayoutGrid.mulLineCount = 0;
    PayoutGrid.displayTitle = 1;
    PayoutGrid.hiddenPlus=1;        
    PayoutGrid.hiddenSubtraction=1; 
    //PayoutGrid.canSel=1;
    PayoutGrid.loadMulLine(iArray);
    PayoutGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex){
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

</script>


