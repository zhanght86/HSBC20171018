<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>                
<%
//�������ƣ�TempReinSendInit.jsp
//�����ܣ��ٷ���˷���
//�������ڣ�2007-02-22 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
  String tPolNo = ""; 
  String tOperate 			= request.getParameter("OperateNo");
  String tCodeType 			= request.getParameter("CodeType");
  String tAuditCode 		= request.getParameter("AuditCode");
  String tAuditAffixCode= request.getParameter("AuditAffixCode");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">

function initInpBox()
{ 
	try                 
  {
  	fm.Remark.value						= "�ڴ�¼���걨���"; 
  	fmImport.DeTailFlag.value = "1"; //1-������ 2-������ 
  	fmImport.OpeData.value		= "<%=tOperate%>"; 
  	fmImport.OpeFlag.value		= "<%=tCodeType%>"; 
  	fm.GrpContNo.value 				= "<%=tOperate%>"; 
  	fm.Remark.value						= "�ڴ�¼���걨���"; 
  	//fmImport.AutoAnswer.value='1'; //0:ϵͳ�Զ��ظ� 1:��������
  }
  catch(ex)
  {
    myAlert("��ReInsureInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

//�����б��ĳ�ʼ��
function initSelBox()
{
}                                        

function initForm(tContNo, tPolNo)
{
  try
  {
		initInpBox();
		initGrpUWResultGrid();
		initSearchResultGrid();
		initIndUWResultGrid();
		initReInsureGrid();
		
		initRiskInfoGrid();
		initClaimInfoGrid();
		initReInsureAuditGrid();
		
  }
  catch(re)
  {
    myAlert("ReInsureInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
  }
}

//����ָ���嵥�ĳ�ʼ��
function initReInsureGrid()
{                               
    var iArray = new Array();
    
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         			//�п�
	    iArray[0][2]=10;          				//�����ֵ
	    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[1]=new Array(); 
	    iArray[1][0]="�����˿ͻ���"; 			//����
	    iArray[1][1]="30px";            	//�п�
	    iArray[1][2]=100;            			//�����ֵ
	    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
				
	    iArray[2]=new Array(); 
	    iArray[2][0]="����������";         	//����
	    iArray[2][1]="30px";            	//�п�
	    iArray[2][2]=100;            			//�����ֵ
	    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[3]=new Array(); 
	    iArray[3][0]="���ֱ���";         	//����
	    iArray[3][1]="30px";            	//�п�
	    iArray[3][2]=100;            			//�����ֵ
	    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
	    
			iArray[4]=new Array();
	    iArray[4][0]="��������";     			//����
	    iArray[4][1]="50px";            	//�п�
	    iArray[4][2]=100;            			//�����ֵ
	    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[5]=new Array();
	    iArray[5][0]="���δ���";     			//����
	    iArray[5][1]="0px";            	//�п�
	    iArray[5][2]=100;            			//�����ֵ
	    iArray[5][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[6]=new Array();
	    iArray[6][0]="�Ժ˽��";     			//����
	    iArray[6][1]="80px";            	//�п�
	    iArray[6][2]=200;            			//�����ֵ
	    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
	
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

////�ŵ��˱����
function initGrpUWResultGrid(){
	var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="ӡˢ����";    		//����
	    iArray[1][1]="80px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="����Ͷ��������";  //����
	    iArray[2][1]="80px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���ϼƻ�";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="�ٱ���ͬ";        //����
	    iArray[4][1]="60px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
			
	    iArray[5]=new Array();
	    iArray[5][0]="�ٱ�����";        //����
	    iArray[5][1]="60px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
			
	    iArray[6]=new Array();
	    iArray[6][0]="�ٷ��Ժ˹���";    //����
	    iArray[6][1]="150px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]=" �Ժ˽��";   		//����
	    iArray[7][1]="200px";           //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
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
	    iArray[0][0]="���";
	    iArray[0][1]="30px";
	    iArray[0][2]=10;
	    iArray[0][3]=0;
	
	    iArray[1]=new Array();
	    iArray[1][0]="ӡˢ����";
	    iArray[1][1]="100px";           //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="����Ͷ��������";  //����
	    iArray[2][1]="100px";           //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���ϼƻ�";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="���˺�ͬ����";    //����
	    iArray[4][1]="100px";           //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	
	    iArray[5]=new Array();
	    iArray[5][0]="Ͷ�������ֺ���";  //����
	    iArray[5][1]="100px";           //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[6]=new Array();
	    iArray[6][0]="����������";      //����
	    iArray[6][1]="80px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�����˺���";    	//����
	    iArray[7][1]="100px";           //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="���ִ���";        //����
	    iArray[8][1]="80px";            //�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[9]=new Array();
	    iArray[9][0]="���δ���";        //����
	    iArray[9][1]="80px";            //�п�
	    iArray[9][2]=100;            		//�����ֵ
	    iArray[9][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[10]=new Array();
	    iArray[10][0]="�ٱ���ͬ";      	//����
	    iArray[10][1]="100px";          //�п�
	    iArray[10][2]=100;            	//�����ֵ
	    iArray[10][3]=3;              	//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[11]=new Array();
	    iArray[11][0]="�ٱ�����";    		//����
	    iArray[11][1]="100px";          //�п�
	    iArray[11][2]=100;            	//�����ֵ
	    iArray[11][3]=3;              	//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[12]=new Array();
	    iArray[12][0]="�ٷ��Ժ˹���";   //����
	    iArray[12][1]="150px";          //�п�
	    iArray[12][2]=100;            	//�����ֵ
	    iArray[12][3]=0;              	//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[13]=new Array();
	    iArray[13][0]="�Ժ˽��";    		//����
	    iArray[13][1]="200px";          //�п�
	    iArray[13][2]=200;            	//�����ֵ
	    iArray[13][3]=0;              	//�Ƿ���������,1��ʾ������0��ʾ������
	
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
	    iArray[0][0]="���";
	    iArray[0][1]="30px";
	    iArray[0][2]=10;
	    iArray[0][3]=0;
	
	    iArray[1]=new Array();
	    iArray[1][0]="ӡˢ����";
	    iArray[1][1]="100px";
	    iArray[1][2]=100;
	    iArray[1][3]=0;
			
	    iArray[2]=new Array();
	    iArray[2][0]="����Ͷ��������";
	    iArray[2][1]="100px";
	    iArray[2][2]=100;
	    iArray[2][3]=0;
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���ϼƻ�";
	    iArray[3][1]="60px";
	    iArray[3][2]=100;
	    iArray[3][3]=0;
	    
	    iArray[4]=new Array();
	    iArray[4][0]="���˺�ͬ����";
	    iArray[4][1]="100px";
	    iArray[4][2]=100;
	    iArray[4][3]=0;
			
	    iArray[5]=new Array();
	    iArray[5][0]="Ͷ�������ֺ���";
	    iArray[5][1]="100px";
	    iArray[5][2]=100;
	    iArray[5][3]=0;
			
	    iArray[6]=new Array();
	    iArray[6][0]="����������";
	    iArray[6][1]="60px";
	    iArray[6][2]=100;
	    iArray[6][3]=0;
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�����˺���";
	    iArray[7][1]="100px";
	    iArray[7][2]=100;
	    iArray[7][3]=0;
	    
	    iArray[8]=new Array();
	    iArray[8][0]="���ִ���";
	    iArray[8][1]="100px";
	    iArray[8][2]=100;
	    iArray[8][3]=0;
	    
	    iArray[9]=new Array();
	    iArray[9][0]="���δ���";
	    iArray[9][1]="100px";
	    iArray[9][2]=100;
	    iArray[9][3]=0;
	    
	    iArray[10]=new Array();
	    iArray[10][0]="�ٷֺ˱�����";
	    iArray[10][1]="100px";
	    iArray[10][2]=100;
	    iArray[10][3]=0;
			
			iArray[11]=new Array();
	    iArray[11][0]="�ٷֺ˱����۴���";
	    iArray[11][1]="100px";
	    iArray[11][2]=100;
	    iArray[11][3]=3;
			
	    SearchResultGrid = new MulLineEnter( "fm" , "SearchResultGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    SearchResultGrid.mulLineCount = 0;   
	    SearchResultGrid.displayTitle = 1;
	    SearchResultGrid.locked = 1;
	    SearchResultGrid.hiddenPlus = 1;
	    SearchResultGrid.canSel = 0;
    	SearchResultGrid.canChk = 1; 
	    SearchResultGrid.hiddenSubtraction = 1;
	    SearchResultGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}

//// ����ָ���嵥�ĳ�ʼ��
function initRiskInfoGrid(){
    var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="��������";    		//����
	    iArray[1][1]="80px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="���ִ���";        //����
	    iArray[2][1]="80px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���α���";        //����
	    iArray[3][1]="0px";            	//�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="����";         		//����
	    iArray[4][1]="0px";            	//�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[5]=new Array();
	    iArray[5][0]="���M";         		//����
	    iArray[5][1]="60px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[6]=new Array();
	    iArray[6][0]="���~";         		//����
	    iArray[6][1]="60px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[7]=new Array();
	    iArray[7][0]="���ձ���";        //����
	    iArray[7][1]="60px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="�ۼƷ��ձ���";    //����
	    iArray[8][1]="0px";           	//�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[9]=new Array();
	    iArray[9][0]="Ͷ������";        //����
	    iArray[9][1]="0px";           	//�п�
	    iArray[9][2]=100;            		//�����ֵ
	    iArray[9][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[10]=new Array();
	    iArray[10][0]="�˱�����";        //����
	    iArray[10][1]="50px";          	 //�п�
	    iArray[10][2]=100;           		 //�����ֵ
	    iArray[10][3]=0;            		 //�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[11]=new Array();
	    iArray[11][0]="�˱����۱���";    //����
	    iArray[11][1]="0px";            //�п�
	    iArray[11][2]=100;           		 //�����ֵ
	    iArray[11][3]=3;            		 //�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[12]=new Array();
	    iArray[12][0]="�˱����۱��뱸��";//����
	    iArray[12][1]="0px";           //�п�
	    iArray[12][2]=100;           		 //�����ֵ
	    iArray[12][3]=3;             		 //�Ƿ���������,1��ʾ������0��ʾ������
	    
	    if(<%=tCodeType%>=='03'){
	    	iArray[13]=new Array();
	    	iArray[13][0]="��ȫ���κ�";   //����
	    	iArray[13][1]="100px";        //�п�
	    	iArray[13][2]=100;            //�����ֵ
	    	iArray[13][3]=0;              //�Ƿ���������,1��ʾ������0��ʾ������
	    	
	    	iArray[14]=new Array();
	    	iArray[14][0]="��������";     //����
	    	iArray[14][1]="60px";         //�п�
	    	iArray[14][2]=100;            //�����ֵ
	    	iArray[14][3]=0;              //�Ƿ���������,1��ʾ������0��ʾ������
	  	}
	  	
	    RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    RiskInfoGrid.mulLineCount = 0;   
	    RiskInfoGrid.displayTitle = 1;
	    RiskInfoGrid.locked = 1;
	    RiskInfoGrid.hiddenPlus = 1;
	    RiskInfoGrid.canSel = 0;
	    RiskInfoGrid.canChk = 1;
	    RiskInfoGrid.hiddenSubtraction = 1;
	    RiskInfoGrid.selBoxEventFuncName = "showRule"; 
	    RiskInfoGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}

//// ������Ϣ�嵥�ĳ�ʼ��
function initClaimInfoGrid(){
    var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="������������"; 		//����
	    iArray[1][1]="80px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[2]=new Array();
	    iArray[2][0]="�����˿ͻ���";   //����
	    iArray[2][1]="80px";           //�п�
	    iArray[2][2]=100;           		//�����ֵ
	    iArray[2][3]=0;             		//�Ƿ���������,1��ʾ������0��ʾ������

	    iArray[3]=new Array();
	    iArray[3][0]="������";    			//����
	    iArray[3][1]="80px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
	
	    iArray[4]=new Array();
	    iArray[4][0]="���ִ���";        //����
	    iArray[4][1]="80px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[5]=new Array();
	    iArray[5][0]="���α���";        //����
	    iArray[5][1]="60px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    if(fmImport.DeTailFlag.value=='1'){
	       iArray[5][3]=3;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    }else{
	       iArray[5][3]=0;
	    }
	    iArray[6]=new Array();
	    iArray[6][0]="�⸶���";        //����
	    iArray[6][1]="60px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�����";         	//����
	    iArray[7][1]="80px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="�ٱ�����״̬";    //����
	    iArray[8][1]="0px";            //�п�
	    iArray[8][2]=100;           		 //�����ֵ
	    iArray[8][3]=3;
	    
	    iArray[9]=new Array();
	    iArray[9][0]="�������";        //����
	    iArray[9][1]="50px";          	 //�п�
	    iArray[9][2]=100;           		 //�����ֵ
	    iArray[9][3]=0;            		 //�Ƿ���������,1��ʾ������0��ʾ������
	    
	    iArray[10]=new Array();
	    iArray[10][0]="������۱���";    //����
	    iArray[10][1]="0px";            //�п�
	    iArray[10][2]=100;           		 //�����ֵ
	    iArray[10][3]=3;
	        	    
	    ClaimInfoGrid = new MulLineEnter( "fm" , "ClaimInfoGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    ClaimInfoGrid.mulLineCount = 0;   
	    ClaimInfoGrid.displayTitle = 1;
	    ClaimInfoGrid.locked = 1;
	    ClaimInfoGrid.hiddenPlus = 1;
	    ClaimInfoGrid.canSel = 0;
	    ClaimInfoGrid.canChk = 1;
	    ClaimInfoGrid.hiddenSubtraction = 1;
	    ClaimInfoGrid.selBoxEventFuncName = "showRule"; 
	    ClaimInfoGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}
//���뷢����Ϣ
function initReInsureAuditGrid()
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
    	iArray[2][0]="������";
    	iArray[2][1]="60px";
    	iArray[2][2]=100;
    	iArray[2][3]=0;
    	
    	iArray[3]=new Array();
    	iArray[3][0]="���ͻظ���Ϣ";
    	iArray[3][1]="60px";
    	iArray[3][2]=100;
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

