<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ReInsureInit.jsp
//�����ܣ��˹��˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��zhangbin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  
<%
	String tPrtNo=request.getParameter("PrtNo");
	String tOpeFlag = request.getParameter("OpeFlag");
	String tProposalNo = request.getParameter("ProposalNo");
	String tDutyCode = request.getParameter("DutyCode");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
	fm.Remark.value="�ڴ�¼��ظ����";
  fmInfo.PrtNo.value="<%=tPrtNo%>";
	fmInfo.OpeFlag.value="<%=tOpeFlag%>";
	fmInfo.ProposalNo.value="<%=tProposalNo%>";
	fmInfo.DutyCode.value="<%=tDutyCode%>";
	fm.AuditType.value="<%=tOpeFlag%>";
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  { 
  }
  catch(ex)
  {
    myAlert("��ReInsureInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
	  initInpBox();
		initSelBox();
		initRiskInfoGrid();
		initEdorInfoGrid();
		initClaimInfoGrid();
		initReInsureAuditGrid();
  	initTempCess();
  }
  catch(re)
  {
    myAlert("ReInsureInit.jsp-->"+"��ʼ���������!");
  }
  	//ReInsureAudit();
  	//QueryReInsureAudit();
}


// ������Ϣ�б�ĳ�ʼ��
function initRiskInfoGrid()
{                               
    var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          				//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";    			//����
      iArray[1][1]="80px";            	//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         	//����
      iArray[2][1]="80px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���α���";         	//����
      iArray[3][1]="60px";            	//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="60px";            	//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����";         			//����
      iArray[5][1]="60px";            	//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�ٱ�����״̬";      //����
      iArray[6][1]="80px";            	//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="���ֱ���";         		//����
      iArray[7][1]="80px";            	//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="���ʹ���";         	//����
      iArray[8][1]="80px";            	//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�ٱ�����״̬����";  //����
      iArray[9][1]="80px";            	//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ����
      
      RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskInfoGrid.mulLineCount = 0;   
      RiskInfoGrid.displayTitle = 1;
      RiskInfoGrid.locked = 1;
      RiskInfoGrid.hiddenPlus = 1;
      RiskInfoGrid.canSel = 1;
      RiskInfoGrid.hiddenSubtraction = 1;
      RiskInfoGrid.selBoxEventFuncName = "showTaskInfo";
      RiskInfoGrid.loadMulLine(iArray);  
   }
   
   catch(ex)
   {
     myAlert(ex);
   }
}

function initEdorInfoGrid()
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
      iArray[1][0]="��������";    	//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���α���";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
      iArray[4]=new Array();
      iArray[4][0]="��ȫ���κ�";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��ȫ����";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      
      iArray[8]=new Array();
      iArray[8][0]="�ٱ�����״̬";      //����
      iArray[8][1]="80px";            	//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="���ֱ���";         		//����
      iArray[9][1]="20px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="���ʹ���";         //����
      iArray[10][1]="80px";            	//�п�
      iArray[10][2]=100;            		//�����ֵ
      iArray[10][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="�ٱ�����״̬����";         //����
      iArray[11][1]="80px";            	//�п�
      iArray[11][2]=100;            		//�����ֵ
      iArray[11][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������

      EdorInfoGrid = new MulLineEnter( "fm" , "EdorInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      EdorInfoGrid.mulLineCount = 0;   
      EdorInfoGrid.displayTitle = 1;
      EdorInfoGrid.locked = 1;
      EdorInfoGrid.hiddenPlus = 1;
      EdorInfoGrid.canSel = 1;
      EdorInfoGrid.hiddenSubtraction = 1;
      EdorInfoGrid.selBoxEventFuncName = "showTaskInfo";
      EdorInfoGrid.loadMulLine(iArray);  
   }
   catch(ex){
     myAlert(ex);
   }
}

// ������Ϣ�б�ĳ�ʼ��
function initClaimInfoGrid()
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
      iArray[1][0]="��������";    	//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���α���";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
      iArray[4]=new Array();
      iArray[4][0]="�ⰸ��";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="ʵ����";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�ٱ�����״̬";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="���ֱ���";         		//����
      iArray[7][1]="20px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="���ʹ���";         		//����
      iArray[8][1]="20px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�ٱ�����״̬����";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ClaimInfoGrid = new MulLineEnter( "fm" , "ClaimInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ClaimInfoGrid.mulLineCount = 0;   
      ClaimInfoGrid.displayTitle = 1;
      ClaimInfoGrid.locked = 1;
      ClaimInfoGrid.hiddenPlus = 1;
      ClaimInfoGrid.canSel = 1;
      ClaimInfoGrid.hiddenSubtraction = 1;
      ClaimInfoGrid.selBoxEventFuncName = "showTaskInfo";
      ClaimInfoGrid.loadMulLine(iArray);  
   }
   
   catch(ex)
   {
     myAlert(ex);
   }
}

function initReInsureAuditGrid(){
    var iArray = new Array();
      
    try{
    	iArray[0]=new Array();
    	iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    	iArray[0][1]="30px";         			//�п�
    	iArray[0][2]=10;          				//�����ֵ
    	iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[1]=new Array();
    	iArray[1][0]="��������";    			//����
    	iArray[1][1]="80px";            	//�п�
    	iArray[1][2]=100;            			//�����ֵ
    	iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[2]=new Array();
    	iArray[2][0]="���ֱ���";         	//����
    	iArray[2][1]="80px";            	//�п�
    	iArray[2][2]=100;            			//�����ֵ
    	iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[3]=new Array();
    	iArray[3][0]="���α���";         	//����
    	iArray[3][1]="80px";            	//�п�
    	iArray[3][2]=100;            			//�����ֵ
    	iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[4]=new Array();
    	iArray[4][0]="�������";         	//����
    	iArray[4][1]="60px";            	//�п�
    	iArray[4][2]=100;            			//�����ֵ
    	iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	
    	iArray[5]=new Array();
    	iArray[5][0]="������";         		//����
    	iArray[5][1]="60px";            	//�п�
    	iArray[5][2]=100;            			//�����ֵ
    	iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[6]=new Array();
    	iArray[6][0]="����/�ظ����";     //����
    	iArray[6][1]="60px";            	//�п�
    	iArray[6][2]=100;            			//�����ֵ
    	iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[7]=new Array();
    	iArray[7][0]="����ʱ��";         	//����
    	iArray[7][1]="80px";            	//�п�
    	iArray[7][2]=100;            			//�����ֵ
    	iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[8]=new Array();
    	iArray[8][0]="����·��";         	//����
    	iArray[8][1]="100px";            	//�п�
    	iArray[8][2]=100;            			//�����ֵ
    	iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[9]=new Array();
    	iArray[9][0]="����/�ظ���־";     //����
    	iArray[9][1]="100px";            	//�п�
    	iArray[9][2]=100;            			//�����ֵ
    	iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[10]=new Array();
    	iArray[10][0]="�ٱ�����״̬����";  //����
    	iArray[10][1]="80px";            	//�п�
    	iArray[10][2]=100;            			//�����ֵ
    	iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	ReInsureAuditGrid = new MulLineEnter( "fm" , "ReInsureAuditGrid" ); 
    	//��Щ���Ա�����loadMulLineǰ
    	ReInsureAuditGrid.mulLineCount = 0;   
    	ReInsureAuditGrid.displayTitle = 1;
    	ReInsureAuditGrid.locked = 1;
    	ReInsureAuditGrid.canSel = 1;
    	ReInsureAuditGrid.hiddenPlus = 1;
    	ReInsureAuditGrid.hiddenSubtraction = 1;
    	ReInsureAuditGrid.selBoxEventFuncName = "showAnswerIdea";
    	ReInsureAuditGrid.loadMulLine(iArray);  
    }
    
    catch(ex){
      myAlert(ex);
    }
}

</script>




