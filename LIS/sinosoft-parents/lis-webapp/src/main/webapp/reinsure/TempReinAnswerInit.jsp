<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�TempReinAnswerInit.jsp
//�����ܣ��ٱ��ٷֻظ�
//�������ڣ�2007-09-29 11:10:36
//������  ��zhangbin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  
<%
	String tPrtNo			=request.getParameter("PrtNo"); 
	String tOpeFlag 	= request.getParameter("OpeFlag"); 
	String tContType 	= request.getParameter("ContType"); 
	String tAuditType = request.getParameter("AuditType"); 
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
	//������ֻ��������ת����־
	fmInfo.DeTailFlag.value = "1"; //1-������ 2-������
	fmInfo.ContType.value=<%=tContType%>; //1-���� 2-����
	fm.Remark.value="�ڴ�¼��ظ����";
  fmInfo.PrtNo.value="<%=tPrtNo%>";
	fmInfo.OpeFlag.value="<%=tOpeFlag%>";
	fm.AuditType.value="<%=tAuditType%>";
}

// �����б�ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("��ReInsureInit.jsp InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
	  initInpBox();
		initSelBox();
		initTaskLiskGrid();
		initAuditInfoGrid();
		if(fmInfo.ContType.value=='1'){ //����
			initRiskInfoGrid();
		}else{
			initGrpTempInsuListGrid();
			initGrpUWResultGrid();
		}
		initIndUWResultGrid();
		initSearchResultGrid();
		initReInsureAuditGrid();
  	initTempCess(); //��ʾ�����ٷ������¼
  	//showTempGrp(); //��ʾ�����ٷ������¼
  	showTask();
  	// ****  QueryIndRiskTask();
  }
  catch(re)
  {
    myAlert("TempReInAnswerInit.jsp InitForm�����з����쳣:��ʼ���������!");
  }
}

function initTaskLiskGrid(){
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          				//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";    			//����
      iArray[1][1]="60px";            	//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         	//����
      iArray[2][1]="60px";            	//�п�
      iArray[2][2]=100;  
      if(fm.AuditType.value=='04'){          			
      	iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      }else{
      	iArray[2][3]=0;
      }
      iArray[3]=new Array();
      iArray[3][0]="����״̬";         	//����
      iArray[3][1]="60px";            	//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ʹ���";         	//����
      iArray[4][1]="50px";            	//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      if(fm.AuditType.value=='03'){
      	iArray[5]=new Array();
      	iArray[5][0]="��ȫ���κ�";         	//����
      	iArray[5][1]="60px";            	//�п�
      	iArray[5][2]=100;            			//�����ֵ
      	iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	iArray[6]=new Array();
      	iArray[6][0]="��ȫ����";         	//����
      	iArray[6][1]="50px";            	//�п�
      	iArray[6][2]=100;            			//�����ֵ
      	iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      }
      if(fm.AuditType.value=='04'){
      	iArray[5]=new Array();
      	iArray[5][0]="�����";         	//����
      	iArray[5][1]="60px";            	//�п�
      	iArray[5][2]=100;            			//�����ֵ
      	iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	iArray[6]=new Array();
      	iArray[6][0]="�������";         	//����
      	iArray[6][1]="0px";            	//�п�
      	iArray[6][2]=100;            			//�����ֵ
      	iArray[6][3]=3;
      }
      
      TaskLiskGrid = new MulLineEnter( "fm" , "TaskLiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskLiskGrid.mulLineCount = 0;   
      TaskLiskGrid.displayTitle = 1;
      TaskLiskGrid.locked = 1;
      TaskLiskGrid.hiddenPlus = 1;
      TaskLiskGrid.canSel = 1;
      TaskLiskGrid.hiddenSubtraction = 1;
      TaskLiskGrid.selBoxEventFuncName = "QueryTaskInfo";
      TaskLiskGrid.loadMulLine(iArray);  
   }
   catch(ex)
   {
     myAlert(ex);
   }	
}

function initAuditInfoGrid(){
	var iArray = new Array();
  
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";         			//�п�
	  iArray[0][2]=10;          				//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array(); 
	  iArray[1][0]="�����˿ͻ���"; 			//����
	  iArray[1][1]="30px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
	  iArray[2]=new Array(); 
	  iArray[2][0]="����������";         	//����
	  iArray[2][1]="30px";            	//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[3]=new Array(); 
	  iArray[3][0]="���ֱ���";         	//����
	  iArray[3][1]="30px";            	//�п�
	  iArray[3][2]=100;            			//�����ֵ
	  iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
		iArray[4]=new Array();
	  iArray[4][0]="��������";     			//����
	  iArray[4][1]="50px";            	//�п�
	  iArray[4][2]=100;            			//�����ֵ
	  iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[5]=new Array();
	  iArray[5][0]="���δ���";     			//����
	  iArray[5][1]="0px";            	//�п�
	  iArray[5][2]=100;            			//�����ֵ
	  iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[6]=new Array();
	  iArray[6][0]="�Ժ˽��";     			//����
	  iArray[6][1]="80px";            	//�п�
	  iArray[6][2]=200;            			//�����ֵ
	  iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  AuditInfoGrid = new MulLineEnter( "fm" , "AuditInfoGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  AuditInfoGrid.mulLineCount = 0;   
	  AuditInfoGrid.displayTitle = 1;
	  AuditInfoGrid.locked = 1;
	  AuditInfoGrid.hiddenPlus = 1;
	  AuditInfoGrid.hiddenSubtraction = 1;
	  AuditInfoGrid.loadMulLine(iArray);  
  }  
  catch(ex){
    myAlert(ex);
  }
}

// ����ָ���嵥�ĳ�ʼ��
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
      iArray[2][0]="���ִ���";         	//����
      iArray[2][1]="80px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���α���";         	//����
      iArray[3][1]="60px";            	//�п�
      iArray[3][2]=100;            			//�����ֵ
      if(fmInfo.DeTailFlag.value=='1'){ //1-�����֣�
      	iArray[3][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }else{  													//2-������
      	iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }
      
      iArray[4]=new Array();
      iArray[4][0]="���~";         			//����
      iArray[4][1]="60px";            	//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      if(fm.AuditType.value=='01'){
      	iArray[5]=new Array();
      	iArray[5][0]="���ձ���";        //����
      	iArray[5][1]="60px";            //�п�
      	iArray[5][2]=100;            		//�����ֵ
      	iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	iArray[6]=new Array();
      	iArray[6][0]="�ۼƷ��ձ���"; 		//����
      	iArray[6][1]="0px";            	//�п�
      	iArray[6][2]=100;            		//�����ֵ
      	iArray[6][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
    	}
    	
      if(fm.AuditType.value=='03'){
      	iArray[5]=new Array();
      	iArray[5][0]="��ȫ���κ�";      //����
      	iArray[5][1]="60px";            //�п�
      	iArray[5][2]=100;            		//�����ֵ
      	iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	iArray[6]=new Array();
      	iArray[6][0]="��ȫ����"; 				//����
      	iArray[6][1]="60px";            //�п�
      	iArray[6][2]=100;            		//�����ֵ
      	iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }
      if(fm.AuditType.value=='04'){
      	iArray[5]=new Array();
      	iArray[5][0]="�����";       		//����
      	iArray[5][1]="60px";            //�п�
      	iArray[5][2]=100;            		//�����ֵ
      	iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	iArray[6]=new Array();
      	iArray[6][0]="������";     		//����
      	iArray[6][1]="60px";            //�п�
      	iArray[6][2]=100;            		//�����ֵ
      	iArray[6][3]=0; 
      	
      	iArray[7]=new Array();
	    iArray[7][0]="�������";        	//����
	    iArray[7][1]="50px";          	 	//�п�
	    iArray[7][2]=100;           		 	//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }else{
        iArray[7]=new Array();
	    iArray[7][0]="�˱�����";        	//����
	    iArray[7][1]="50px";          	 	//�п�
	    iArray[7][2]=100;           		 	//�����ֵ
	    iArray[7][3]=0;
      }
                 		     
        iArray[8]=new Array();
	    iArray[8][0]="�˱����۱���";    	//����
	    iArray[8][1]="0px";            		//�п�
	    iArray[8][2]=100;           		 	//�����ֵ
	    iArray[8][3]=3;            		 		//�Ƿ���������,1��ʾ����0��ʾ������
	    
      RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskInfoGrid.mulLineCount = 0;   
      RiskInfoGrid.displayTitle = 1;
      RiskInfoGrid.locked = 1;
      RiskInfoGrid.hiddenPlus = 1;
      RiskInfoGrid.canSel = 0;
      RiskInfoGrid.hiddenSubtraction = 1;
      RiskInfoGrid.selBoxEventFuncName = "QueryGrpNewInfo";
      RiskInfoGrid.loadMulLine(iArray); 
   }
   catch(ex)
   {
     myAlert(ex);
   }
}

//�����ٷ������б�
function initGrpTempInsuListGrid(){
	var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";         		//�п�
	  iArray[0][2]=10;          			//�����ֵ
	  iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="ӡˢ��";    		//����
	  iArray[1][1]="80px";            //�п�
	  iArray[1][2]=100;            		//�����ֵ
	  iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="����Ͷ������";  //����
	  iArray[2][1]="80px";            //�п�
	  iArray[2][2]=100;            		//�����ֵ
	  iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
	  iArray[3]=new Array();
	  iArray[3][0]="���屣����";	    //����
	  iArray[3][1]="60px";            //�п�
	  iArray[3][2]=100;            		//�����ֵ
	  iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	 	iArray[4]=new Array();
	  iArray[4][0]="�����ٷ�״̬";    //����
	  iArray[4][1]="60px";            //�п�
	  iArray[4][2]=100;            		//�����ֵ
	  iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  GrpTempInsuListGrid = new MulLineEnter( "fm" , "GrpTempInsuListGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  GrpTempInsuListGrid.mulLineCount = 0;   
	  GrpTempInsuListGrid.displayTitle = 1;
	  GrpTempInsuListGrid.locked = 1;
	  GrpTempInsuListGrid.hiddenPlus = 1;
	  GrpTempInsuListGrid.canSel = 1;
	  GrpTempInsuListGrid.hiddenSubtraction = 1;
	  GrpTempInsuListGrid.selBoxEventFuncName = "QueryGrpNewInfo"; 
	  GrpTempInsuListGrid.loadMulLine(iArray);  
  }
  catch(ex){
    myAlert(ex);
  }
}

//�ŵ��˱����
function initGrpUWResultGrid(){
	var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";         		//�п�
	  iArray[0][2]=10;          			//�����ֵ
	  iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="ӡˢ����";    		//����
	  iArray[1][1]="80px";            //�п�
	  iArray[1][2]=100;            		//�����ֵ
	  iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="����Ͷ��������";        //����
	  iArray[2][1]="80px";            //�п�
	  iArray[2][2]=100;            		//�����ֵ
	  iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[3]=new Array();
	  iArray[3][0]="���ϼƻ�";        //����
	  iArray[3][1]="60px";            //�п�
	  iArray[3][2]=100;            		//�����ֵ
	  iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[4]=new Array();
	  iArray[4][0]="�ٱ���ͬ";         		//����
	  iArray[4][1]="60px";            //�п�
	  iArray[4][2]=100;            		//�����ֵ
	  iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	  iArray[5]=new Array();
	  iArray[5][0]="�ٱ�����";         		//����
	  iArray[5][1]="60px";            //�п�
	  iArray[5][2]=100;            		//�����ֵ
	  iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[6]=new Array();
	  iArray[6][0]="�ٷ��Ժ˹���";         		//����
	  iArray[6][1]="60px";            //�п�
	  iArray[6][2]=100;            		//�����ֵ
	  iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[7]=new Array();
	  iArray[7][0]=" �Ժ˽��";    //����
	  iArray[7][1]="150px";            //�п�
	  iArray[7][2]=100;            		//�����ֵ
	  iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  GrpUWResultGrid = new MulLineEnter( "fm" , "GrpUWResultGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  GrpUWResultGrid.mulLineCount = 0;   
	  GrpUWResultGrid.displayTitle = 1;
	  GrpUWResultGrid.locked = 1;
	  GrpUWResultGrid.hiddenPlus = 1;
	  GrpUWResultGrid.canSel = 0;
	  GrpUWResultGrid.hiddenSubtraction = 1;
	  GrpUWResultGrid.loadMulLine(iArray);  
  }
  catch(ex){
    myAlert(ex);
  }
}
//���˺˱����
function initIndUWResultGrid(){
		var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="ӡˢ����";    		//����
	    iArray[1][1]="100px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="����Ͷ��������";        //����
	    iArray[2][1]="100px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���ϼƻ�";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="���˺�ͬ����";         		//����
	    iArray[4][1]="100px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	    iArray[5]=new Array();
	    iArray[5][0]="Ͷ�������ֺ���";         		//����
	    iArray[5][1]="100px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[6]=new Array();
	    iArray[6][0]="����������";         		//����
	    iArray[6][1]="60px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�����˺���";    //����
	    iArray[7][1]="100px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="���ִ���";         	//����
	    iArray[8][1]="80px";            //�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[9]=new Array();
	    iArray[9][0]="���δ���";         	//����
	    iArray[9][1]="80px";            //�п�
	    iArray[9][2]=100;            		//�����ֵ
	    iArray[9][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[10]=new Array();
	    iArray[10][0]="�ٱ���ͬ";      //����
	    iArray[10][1]="100px";            //�п�
	    iArray[10][2]=100;            	 //�����ֵ
	    iArray[10][3]=0;              	 //�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[11]=new Array();
	    iArray[11][0]="�ٱ�����";    //����
	    iArray[11][1]="100px";            //�п�
	    iArray[11][2]=100;            		//�����ֵ
	    iArray[11][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[12]=new Array();
	    iArray[12][0]="�ٷ��Ժ˹���";      //����
	    iArray[12][1]="100px";            //�п�
	    iArray[12][2]=100;            	 //�����ֵ
	    iArray[12][3]=0;              	 //�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[13]=new Array();
	    iArray[13][0]="�Ժ˽��";    //����
	    iArray[13][1]="200px";            //�п�
	    iArray[13][2]=200;            		//�����ֵ
	    iArray[13][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    IndUWResultGrid = new MulLineEnter( "fm" , "IndUWResultGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    IndUWResultGrid.mulLineCount = 0;   
	    IndUWResultGrid.displayTitle = 1;
	    IndUWResultGrid.locked = 1;
	    IndUWResultGrid.hiddenPlus = 1;
	    IndUWResultGrid.canSel = 0;
	    IndUWResultGrid.hiddenSubtraction = 1;
	    IndUWResultGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}


function initSearchResultGrid(){
		var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="ӡˢ����";    		//����
	    iArray[1][1]="100px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="����Ͷ��������";        //����
	    iArray[2][1]="100px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���ϼƻ�";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="���˺�ͬ����";         		//����
	    iArray[4][1]="100px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	    iArray[5]=new Array();
	    iArray[5][0]="Ͷ�������ֺ���";         		//����
	    iArray[5][1]="100px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[6]=new Array();
	    iArray[6][0]="����������";         		//����
	    iArray[6][1]="60px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�����˺���";    //����
	    iArray[7][1]="100px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="���ִ���";         	//����
	    iArray[8][1]="100px";            //�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[9]=new Array();
	    iArray[9][0]="���δ���";       	//����
	    iArray[9][1]="100px";           //�п�
	    iArray[9][2]=100;            		//�����ֵ
	    iArray[9][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[10]=new Array();
	    iArray[10][0]="�ٷֺ˱�����";    	//����
	    iArray[10][1]="100px";            //�п�
	    iArray[10][2]=100;            		//�����ֵ
	    iArray[10][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    SearchResultGrid = new MulLineEnter( "fm" , "SearchResultGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    SearchResultGrid.mulLineCount = 0;   
	    SearchResultGrid.displayTitle = 1;
	    SearchResultGrid.locked = 1;
	    SearchResultGrid.hiddenPlus = 1;
	    SearchResultGrid.canSel = 0;
    	SearchResultGrid.canChk = 0; 
	    SearchResultGrid.hiddenSubtraction = 1;
	    SearchResultGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}

function initReInsureAuditGrid(){
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
    	iArray[2][0]="������";
    	iArray[2][1]="60px";
    	iArray[2][2]=100;
    	iArray[2][3]=0;
    	
    	iArray[3]=new Array();
    	iArray[3][0]="���ͻظ���Ϣ";
    	iArray[3][1]="60px";
    	iArray[3][2]=5000;
    	iArray[3][3]=3;
			
    	iArray[4]=new Array();
    	iArray[4][0]="����ʱ��";
    	iArray[4][1]="80px";
    	iArray[4][2]=100;
    	iArray[4][3]=0;
    	
    	iArray[5]=new Array();
    	iArray[5][0]="����·��";         	
    	iArray[5][1]="300px";            	
    	iArray[5][2]=300;            			
    	iArray[5][3]=0;              			
    	       
    	iArray[6]=new Array();
    	iArray[6][0]="����/�ظ���־";
    	iArray[6][1]="80px";
    	iArray[6][2]=100;
    	iArray[6][3]=0;
    	
    	iArray[7]=new Array();
    	iArray[7][0]="����״̬";
    	iArray[7][1]="80px";
    	iArray[7][2]=100;
    	iArray[7][3]=0;
    	
    	ReInsureAuditGrid = new MulLineEnter( "fmInfo" , "ReInsureAuditGrid" ); 
    	ReInsureAuditGrid.muineCount = 0;   
    	ReInsureAuditGrid.displayTitle = 1;
    	ReInsureAuditGrid.locked = 1;
    	ReInsureAuditGrid.canSel = 1;
    	ReInsureAuditGrid.hiddenPlus = 1;
    	ReInsureAuditGrid.hiddenSubtraction = 1;
    	ReInsureAuditGrid.selBoxEventFuncName = "showAnswerIdea";
    	ReInsureAuditGrid.loadMulLine(iArray);  
    	
    }
    catch(ex)
    {
      myAlert(ex);
    }
}

</script>




