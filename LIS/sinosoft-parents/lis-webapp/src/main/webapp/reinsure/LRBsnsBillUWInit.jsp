<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2006-10-24
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 GlobalInput tG = new GlobalInput();
 tG=(GlobalInput)session.getAttribute("GI");
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
%>

<script type="text/javascript">
function initInpBox()
{
  try
  { 
		fm.StartDate.value='';
		fm.EndDate.value='';
		fm.ContNo.value='';
		fm.ContName.value='';
		fm.RIcomCode.value='';
		fm.RIcomName.value='';
  }
  catch(ex)
  {
    myAlert("��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
	try
	{
		initInpBox();
		initSelBox();
		initBatchListGrid();
		initBillDetailGrid();
		initAuditBillListGrid();
		queryAuditList(); //������˵�
		initBillUpdateGrid() // �˵��޸�
	  
	}
	catch(re)
	{
		myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
	}
}

//�����б�ĳ�ʼ��
function initBatchListGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="�˵����"; 
		iArray[1][1]="40px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 
		
		iArray[2]=new Array();
		iArray[2][0]="�˵�����";
		iArray[2][1]="40px";  
		iArray[2][2]=100; 
		iArray[2][3]=0;     

		iArray[3]=new Array();
		iArray[3][0]="�˵��ڴ�";
		iArray[3][1]="40px";  
		iArray[3][2]=100; 
		iArray[3][3]=0;  
				
		iArray[4]=new Array();
		iArray[4][0]="�ֱ���ͬ���";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=0; 
		
		iArray[5]=new Array();
		iArray[5][0]="�ֱ���˾���";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=0; 
		
		iArray[6]=new Array();
		iArray[6][0]="��ʼ����";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=0;   
		
		iArray[7]=new Array();
		iArray[7][0]="��ֹ����";
		iArray[7][1]="40px";  
		iArray[7][2]=100; 
		iArray[7][3]=0;   
		
		iArray[8]=new Array();
		iArray[8][0]="�˵�״̬";
		iArray[8][1]="50px";  
		iArray[8][2]=100; 
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="�˵�״̬����";
		iArray[9][1]="25px";  
		iArray[9][2]=100; 
		iArray[9][3]=3;
			
	  	BatchListGrid = new MulLineEnter( "fm" , "BatchListGrid" ); 
	  	//��Щ���Ա�����loadMulLineǰ
	  	BatchListGrid.mulLineCount = 0;   
	  	BatchListGrid.displayTitle = 1;
	  	BatchListGrid.locked = 0;
	  	BatchListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	  	BatchListGrid.canChk = 0; //�Ƿ����Ӷ�ѡ��,1Ϊ��ʾCheckBox�У�0Ϊ����ʾ (ȱʡֵ)
	  	BatchListGrid.selBoxEventFuncName ="queryDetial"; //��Ӧ�ĺ���������������   
	  	BatchListGrid.hiddenPlus=1; 
	  	BatchListGrid.hiddenSubtraction=1;
	  	BatchListGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

//�˵���ϸ��Ϣ 
function initBillDetailGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="�˵����"; 
		iArray[1][1]="40px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 

		iArray[2]=new Array();
		iArray[2][0]="��Ŀ����";
		iArray[2][1]="40px";  
		iArray[2][2]=100; 
		iArray[2][3]=3;   
										
		iArray[3]=new Array();
		iArray[3][0]="Details����ϸ��";
		iArray[3][1]="100px";  
		iArray[3][2]=100; 
		iArray[3][3]=0;     
		
		iArray[4]=new Array();
		iArray[4][0]="Debit���裩";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=0; 

		iArray[5]=new Array();
		iArray[5][0]="Credit������";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=0; 
				
		iArray[6]=new Array();
		iArray[6][0]="��������";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=3; 

		iArray[7]=new Array();
		iArray[7][0]="�˵�����";
		iArray[7][1]="40px";  
		iArray[7][2]=100; 
		iArray[7][3]=3;   

		iArray[8]=new Array();
		iArray[8][0]="������Ա";
		iArray[8][1]="40px";  
		iArray[8][2]=100; 
		iArray[8][3]=3;  
					
	  	BillDetailGrid = new MulLineEnter( "fm" , "BillDetailGrid" ); 
	  	//��Щ���Ա�����loadMulLineǰ
	  	BillDetailGrid.mulLineCount = 0;   
	  	BillDetailGrid.displayTitle = 1;
	  	BillDetailGrid.locked = 0;
	  	//TaskListGrid.selBoxEventFuncName =""; //��Ӧ�ĺ���������������   
	  	BillDetailGrid.hiddenPlus=1; 
	  	BillDetailGrid.hiddenSubtraction=1;
	  	BillDetailGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

//�˵���ϸ�޸�
function initBillUpdateGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     

		iArray[1]=new Array();
		iArray[1][0]="��ϸ����";
		iArray[1][1]="30px";  
		iArray[1][2]=30; 
		iArray[1][3]=2;   
    	iArray[1][4]="ridebcre"; 
		iArray[1][5]="1|2"; 	 			//�����ô���ֱ���ڵ�1��2
		iArray[1][6]="0|1";	
		iArray[1][19]=1; 
												
		iArray[2]=new Array();
		iArray[2][0]="Details����ϸ��";
		iArray[2][1]="60px";  
		iArray[2][2]=60; 
		iArray[2][3]=1;     
		
		iArray[3]=new Array();
		iArray[3][0]="Debit���裩";
		iArray[3][1]="30px";  
		iArray[3][2]=30; 
		iArray[3][3]=1; 

		iArray[4]=new Array();
		iArray[4][0]="Credit������";
		iArray[4][1]="30px";  
		iArray[4][2]=30; 
		iArray[4][3]=1;  

		BillUpdateGrid = new MulLineEnter( "fm" , "BillUpdateGrid" );
		BillUpdateGrid.mulLineCount = 1;
		BillUpdateGrid.displayTitle = 1;
		//RelateGrid.canSel=1;
		BillUpdateGrid.loadMulLine(iArray);
		BillUpdateGrid.detailInfo="������ʾ��ϸ��Ϣ";
    	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

//������б�
function initAuditBillListGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="�˵����"; 
		iArray[1][1]="40px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 
		
		iArray[2]=new Array();
		iArray[2][0]="�˵�����";
		iArray[2][1]="40px";  
		iArray[2][2]=100; 
		iArray[2][3]=0;     

		iArray[3]=new Array();
		iArray[3][0]="�˵��ڴ�";
		iArray[3][1]="40px";  
		iArray[3][2]=100; 
		iArray[3][3]=0;  
				
		iArray[4]=new Array();
		iArray[4][0]="�ֱ���ͬ���";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=0; 

		iArray[5]=new Array();
		iArray[5][0]="�ֱ���˾���";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=0; 
		
		iArray[6]=new Array();
		iArray[6][0]="��ʼ����";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=0;   
		
		iArray[7]=new Array();
		iArray[7][0]="��ֹ����";
		iArray[7][1]="40px";  
		iArray[7][2]=100; 
		iArray[7][3]=0;   
		
		iArray[8]=new Array();
		iArray[8][0]="�˵�״̬";
		iArray[8][1]="50px";  
		iArray[8][2]=100; 
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="�˵�״̬����";
		iArray[9][1]="25px";  
		iArray[9][2]=100; 
		iArray[9][3]=3;
			
	  	AuditBillListGrid = new MulLineEnter( "fm" , "AuditBillListGrid" ); 
	  	//��Щ���Ա�����loadMulLineǰ
	  	AuditBillListGrid.mulLineCount = 0;   
	  	AuditBillListGrid.displayTitle = 1;
	  	AuditBillListGrid.locked = 0;
	  	AuditBillListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	  	AuditBillListGrid.canChk = 0; //�Ƿ����Ӷ�ѡ��,1Ϊ��ʾCheckBox�У�0Ϊ����ʾ (ȱʡֵ)
	  	//TaskListGrid.selBoxEventFuncName =""; //��Ӧ�ĺ���������������   
	  	AuditBillListGrid.hiddenPlus=1; 
	  	AuditBillListGrid.hiddenSubtraction=1;
	  	AuditBillListGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}
</script>