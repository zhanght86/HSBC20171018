<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ReInsureInit.jsp
//�����ܣ��˹��˱�δ��ԭ���ѯ
//�������ڣ�2007-02-22 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
<%
  String tOperate = "";
  String tCodeType = "";
  String tPolNo="";
  tOperate = request.getParameter("OperateNo");
  tCodeType = request.getParameter("CodeType");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
	try                 
  {
  	fmImport.OpeData.value="<%=tOperate%>";
  	fmImport.OpeFlag.value="<%=tCodeType%>";
  	//fmImport.OpeData.value="6020060803000002";
  	//fmImport.OpeFlag.value="03";
  	
  	//fmImport.OpeData.value="4100000100";
  	//fmImport.OpeFlag.value="04";
  	
  	fm.Remark.value="�ڴ�¼��ظ����";
  }
  catch(ex)
  {
    myAlert("��ReInsureInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{  
  
}                                        

function initForm(tContNo, tPolNo)
{
  try
  {
		initInpBox();
		initReInsureGrid();
	
		initRiskInfoGrid();
		initClaimInfoGrid();
		
		initHide(tContNo, tPolNo);
		
		initReInsureAuditGrid();
		assgnOperate();
		
  	//���ղ������ͷ����ѯ
		//
  	//ReInsureAudit();
  	//QueryReInsureAudit();
  }
  catch(re)
  {
    myAlert("ReInsureInit.jsp-->"+"��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initReInsureGrid()
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
	    iArray[1][0]="�������";    			//����
	    iArray[1][1]="80px";            	//�п�
	    iArray[1][2]=100;            			//�����ֵ
	    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[2]=new Array(); 
	    iArray[2][0]="��������";         	//����
	    iArray[2][1]="100px";            	//�п�
	    iArray[2][2]=100;            			//�����ֵ
	    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[3]=new Array(); 
	    iArray[3][0]="ͨ����־";         	//����
	    iArray[3][1]="100px";            	//�п�
	    iArray[3][2]=100;            			//�����ֵ
	    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	    
			iArray[4]=new Array();
	    iArray[4][0]="�ٱ���������";     	//����
	    iArray[4][1]="100px";            	//�п�
	    iArray[4][2]=100;            			//�����ֵ
	    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	    ReInsureGrid = new MulLineEnter( "fm" , "ReInsureGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    ReInsureGrid.mulLineCount = 0;   
	    ReInsureGrid.displayTitle = 1;
	    ReInsureGrid.locked = 1;
	    ReInsureGrid.hiddenPlus = 1;
	    ReInsureGrid.hiddenSubtraction = 1;
	    ReInsureGrid.loadMulLine(iArray);  
  	}  
    catch(ex){
      myAlert(ex);
    }
}



// ������Ϣ�б�ĳ�ʼ��
function initRiskInfoGrid(){
    var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="��������";    		//����
	    iArray[1][1]="80px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="���ֱ���";        //����
	    iArray[2][1]="80px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���α���";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="����";         		//����
	    iArray[4][1]="60px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	    iArray[5]=new Array();
	    iArray[5][0]="����";         		//����
	    iArray[5][1]="60px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[6]=new Array();
	    iArray[6][0]="����";         		//����
	    iArray[6][1]="60px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�ٱ�����״̬";    //����
	    iArray[7][1]="80px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="���ֱ���";         	//����
	    iArray[8][1]="100px";            //�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[9]=new Array();
	    iArray[9][0]="��ȫ���κ�";         	//����
	    iArray[9][1]="100px";            //�п�
	    iArray[9][2]=100;            		//�����ֵ
	    iArray[9][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[10]=new Array();
	    iArray[10][0]="��������";      //����
	    iArray[10][1]="60px";            //�п�
	    iArray[10][2]=100;            	 //�����ֵ
	    iArray[10][3]=0;              	 //�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[11]=new Array();
	    iArray[11][0]="�ٱ�����״̬����";    //����
	    iArray[11][1]="80px";            //�п�
	    iArray[11][2]=100;            		//�����ֵ
	    iArray[11][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    RiskInfoGrid.mulLineCount = 0;   
	    RiskInfoGrid.displayTitle = 1;
	    RiskInfoGrid.locked = 1;
	    RiskInfoGrid.hiddenPlus = 1;
	    RiskInfoGrid.canSel = 1;
	    RiskInfoGrid.hiddenSubtraction = 1;
	    RiskInfoGrid.selBoxEventFuncName = "showRule"; 
	    RiskInfoGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initClaimInfoGrid(){
    var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="��������";    		//����
	    iArray[1][1]="80px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="���ֱ���";        //����
	    iArray[2][1]="80px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���α���";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="�⸶���";        //����
	    iArray[4][1]="60px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[5]=new Array();
	    iArray[5][0]="�ٱ�����״̬";    //����
	    iArray[5][1]="80px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[6]=new Array();
	    iArray[6][0]="���ֱ���";         	//����
	    iArray[6][1]="100px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�ⰸ��";         	//����
	    iArray[7][1]="80px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="�ٱ�����״̬����";    //����
	    iArray[8][1]="80px";            //�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    ClaimInfoGrid = new MulLineEnter( "fm" , "ClaimInfoGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    ClaimInfoGrid.mulLineCount = 0;   
	    ClaimInfoGrid.displayTitle = 1;
	    ClaimInfoGrid.locked = 1;
	    ClaimInfoGrid.hiddenPlus = 1;
	    ClaimInfoGrid.canSel = 1;
	    ClaimInfoGrid.hiddenSubtraction = 1;
	    ClaimInfoGrid.selBoxEventFuncName = "showRule"; 
	    ClaimInfoGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}


/**
	���뷢����Ϣ
*/
function initReInsureAuditGrid()
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
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
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
      iArray[9][1]="80px";            	//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      ReInsureAuditGrid = new MulLineEnter( "fm" , "ReInsureAuditGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ReInsureAuditGrid.mulLineCount = 0;   
      ReInsureAuditGrid.displayTitle = 1;
      ReInsureAuditGrid.locked = 1;
      ReInsureAuditGrid.canSel = 1;
      ReInsureAuditGrid.hiddenPlus = 1;
      ReInsureAuditGrid.hiddenSubtraction = 1;
      ReInsureAuditGrid.selBoxEventFuncName = "showInfomation";
      ReInsureAuditGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        myAlert(ex);
      }
}

function initHide(tContNo, tPolNo)
{
	fm.all('ContNo').value=tContNo;
}

</script>


