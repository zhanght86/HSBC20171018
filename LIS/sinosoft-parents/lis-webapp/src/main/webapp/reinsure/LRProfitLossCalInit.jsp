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
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
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

// ������ĳ�ʼ��
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
		initLossUWListGrid();
		queryAuditTask(); //����˲�ѯ
	}
	catch(re)
	{
		myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
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
    iArray[1][0]="��������";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="��������";     //����
    iArray[2][1]="100px";            	//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="¼������";     //����
    iArray[3][1]="50px";            	//�п�
    iArray[3][2]=200;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="����ֵ";     //����
    iArray[4][1]="50px";            	//�п�
    iArray[4][2]=200;            			//�����ֵ
    iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[4][9]="����ֵ|notnull&num";
    
    iArray[5]=new Array();
    iArray[5][0]="����ֵУ��";     //����
    iArray[5][1]="50px";            	//�п�
    iArray[5][2]=200;            			//�����ֵ
    iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
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

//����б�
function initLossUWListGrid()
{
	var iArray = new Array();
      
      try
      {
	      iArray[0]=new Array();
	      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	      iArray[0][1]="50px";         			//�п�
	      iArray[0][2]=30;          				//�����ֵ
	      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
				
	      iArray[1]=new Array();
	      iArray[1][0]="�������������";   	//����
	      iArray[1][1]="70px";          		//�п�
	      iArray[1][2]=100;            			//�����ֵ
	      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[2]=new Array();
	      iArray[2][0]="�ٱ���ͬ����";    	//����
	      iArray[2][1]="100px";          		//�п�
	      iArray[2][2]=100;            			//�����ֵ
	      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
				
	      iArray[3]=new Array();
	      iArray[3][0]="�ٱ���ͬ����";      //����
	      iArray[3][1]="150px";          		//�п�
	      iArray[3][2]=100;            			//�����ֵ
	      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[4]=new Array();
	      iArray[4][0]="�ٱ���˾����";    	//����
	      iArray[4][1]="100px";          		//�п�
	      iArray[4][2]=100;            			//�����ֵ
	      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
				
	      iArray[5]=new Array();
	      iArray[5][0]="�ٱ���˾����";      //����
	      iArray[5][1]="150px";          		//�п�
	      iArray[5][2]=100;            			//�����ֵ
	      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	 			
	      iArray[6]=new Array();
	      iArray[6][0]="�������";      		//����
	      iArray[6][1]="60px";           		//�п�
	      iArray[6][2]=60;            			//�����ֵ
	      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[7]=new Array();
	      iArray[7][0]="���������ѽ��";		//����
	      iArray[7][1]="80px";           		//�п�
	      iArray[7][2]=80;            			//�����ֵ
	      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	      iArray[8]=new Array();
	      iArray[8][0]="״̬";		//����
	      iArray[8][1]="80px";           		//�п�
	      iArray[8][2]=80;            			//�����ֵ
	      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	      iArray[9]=new Array();
	      iArray[9][0]="״̬����";		//����
	      iArray[9][1]="80px";           		//�п�
	      iArray[9][2]=80;            			//�����ֵ
	      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      	      	      
	      LossUWListGrid = new MulLineEnter( "fm" , "LossUWListGrid" ); 
	      LossUWListGrid.mulLineCount = 0;   
	      LossUWListGrid.displayTitle = 1;
	      LossUWListGrid.canSel=1;
	      LossUWListGrid.locked = 1;	
		  LossUWListGrid.hiddenPlus = 1;
		  LossUWListGrid.hiddenSubtraction = 1;
	      LossUWListGrid.loadMulLine(iArray);  
	      LossUWListGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>


