<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�RIUWTaskQueryInit.jsp
//�����ܣ��ٱ���������ѯ����
//�������ڣ�2008-10-20
//������  ��caoshuguo
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
	String tContType = request.getParameter("ContType"); 
	String tAuditType = request.getParameter("AuditType"); 
%> 
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">

function initInpBox(){ 
  try{
	//������ֻ��������ת����־
    fm.DeTailFlag.value = "1"; //1-������ 2-������
	//ContType   1-���� 2-����
	fm.ContType.value="<%=tContType%>";
	fm.AuditType.value="<%=tAuditType%>";

	if(fm.AuditType.value=='04'){
		fm.lcPrtNo.value="";
		fm.ClmNo.value ="";
		fm.lcStateType.value="";
		fm.lcStateName.value="";
	}else{
		fm.nbPrtNo.value="";
		fm.ContNo.value ="";
		fm.nbStateType.value ="";
		fm.nbStateName.value ="";		
	}	  	
  }catch(re){
    myAlert("RIUWTaskQueryInit.jsp-->"+"initInpBox�����з����쳣:��ʼ���������!");
  }
}

function initForm(){
  try{
    initInpBox();
    if(fm.AuditType.value=='04'){
    	divSearch.style.display='none';
    	divCLSearch.style.display='';
    }else{
    	divSearch.style.display='';
    	divCLSearch.style.display='none';
    }
	initTaskLiskGrid();     //�����б�
	initAuditInfoGrid();    //�˱�/�������б�
	if(fm.ContType.value=='1'){ //����	
		initRiskInfoGrid();     //������Ϣ
	}       
	initReInsureAuditGrid();    //�ٱ��������

  }catch(re){
    myAlert("RIUWTaskQueryInit.jsp-->"+"initForm�����з����쳣:��ʼ���������!");
  }
}

//�����б�
function initTaskLiskGrid(){
    var iArray = new Array();
    try{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";    			
      iArray[1][1]="80px";            	
      iArray[1][2]=100;            			
      iArray[1][3]=0;              			
	
      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         	
      iArray[2][1]="80px";            	
      iArray[2][2]=100;
      if(fm.AuditType.value=='04'){
      	iArray[2][3]=3; 
      }else{           			
      	iArray[2][3]=0;              			
      }
      iArray[3]=new Array();
      iArray[3][0]="����״̬";         	
      iArray[3][1]="80px";            	
      iArray[3][2]=100;            			
      iArray[3][3]=0;              			
      
      iArray[4]=new Array();
      iArray[4][0]="���ʹ���";         	
      iArray[4][1]="80px";            	
      iArray[4][2]=100;            			
      iArray[4][3]=0;              			
      
      if(fm.AuditType.value=='03'){
      	iArray[5]=new Array();
      	iArray[5][0]="��ȫ���κ�";         
      	iArray[5][1]="80px";            
      	iArray[5][2]=100;            		
      	iArray[5][3]=0;              			
      	
      	iArray[6]=new Array();
      	iArray[6][0]="��ȫ����";        
      	iArray[6][1]="80px";            
      	iArray[6][2]=100;            		
      	iArray[6][3]=0;              			
      }
      
      if(fm.AuditType.value=='04'){
      	iArray[5]=new Array();
      	iArray[5][0]="�����";         	
      	iArray[5][1]="80px";           
      	iArray[5][2]=100;            	
      	iArray[5][3]=0;              	
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
   }catch(ex){
     myAlert(ex);
   }	
}

//�˱�/�������б�
function initAuditInfoGrid(){
	var iArray = new Array();
    
    try{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[1]=new Array(); 
	  iArray[1][0]="�����˿ͻ���"; 			
	  iArray[1][1]="50px";            	
	  iArray[1][2]=100;            			
	  iArray[1][3]=0; 

      iArray[2]=new Array();
      iArray[2][0]="����������";    			
      iArray[2][1]="80px";            	
      iArray[2][2]=100;            			
      iArray[2][3]=0;              			

      iArray[3]=new Array();
      iArray[3][0]="���ִ���";         	
      iArray[3][1]="80px";            	
      iArray[3][2]=100;            			
      iArray[3][3]=0;              			
      
      iArray[4]=new Array();
      iArray[4][0]="���α���";         	    //����
      iArray[4][1]="0px";            	    //�п�
      iArray[4][2]=100;            			//�����ֵ
      if(fm.DeTailFlag.value=='1'){
      	iArray[4][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }else{                                //2��ʾ����ѡ��3��ʾ��һ�������ص�
      	iArray[4][3]=0;              			
      }	
		
      iArray[5]=new Array();
      iArray[5][0]="��������";         			
      iArray[5][1]="60px";            	
      iArray[5][2]=100;            			
      iArray[5][3]=0; 
             			     
      iArray[6]=new Array();
      iArray[6][0]="�Ժ˽���";         		
      iArray[6][1]="60px";            	
      iArray[6][2]=100;            			
      iArray[6][3]=0; 
      
      AuditInfoGrid = new MulLineEnter( "fm" , "AuditInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AuditInfoGrid.mulLineCount = 0;   
      AuditInfoGrid.displayTitle = 1;
      AuditInfoGrid.locked = 1;
      AuditInfoGrid.hiddenPlus = 1;
      AuditInfoGrid.canSel = 0;
      AuditInfoGrid.hiddenSubtraction = 1;
      AuditInfoGrid.loadMulLine(iArray);  
   }catch(ex){
     myAlert(ex);
   }
}

// ������Ϣ
function initRiskInfoGrid(){                               
    var iArray = new Array();
    
    try{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����˿ͻ���";    			
      iArray[1][1]="80px";            	
      iArray[1][2]=100;            		
      iArray[1][3]=0;              			

 	  iArray[2]=new Array();
      iArray[2][0]="��������";    		
      iArray[2][1]="80px";          
      iArray[2][2]=100;            		
      iArray[2][3]=0; 
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         
      iArray[3][1]="60px";            	
      iArray[3][2]=100;            		
      iArray[3][3]=0;              		

      
      iArray[4]=new Array();
      iArray[4][0]="���α���";         	//����
      iArray[4][1]="60px";            	//�п�
      iArray[4][2]=100;            			//�����ֵ
      if(fm.DeTailFlag.value=='1'){  //1-�����֣�
      	iArray[4][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }else{  													//2-������
      	iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }  
          
      iArray[5]=new Array();
      iArray[5][0]="���~";         			//����
      iArray[5][1]="70px";            	//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      if(fm.AuditType.value=='01'){
      	iArray[6]=new Array();
      	iArray[6][0]="���ձ���";        //����
      	iArray[6][1]="70px";            //�п�
      	iArray[6][2]=100;            		//�����ֵ
      	iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	iArray[7]=new Array();
      	iArray[7][0]="�ۼƷ��ձ���"; 		//����
      	iArray[7][1]="70px";            	//�п�
      	iArray[7][2]=100;            		//�����ֵ
      	iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }
    	
      if(fm.AuditType.value=='03'){
      	iArray[6]=new Array();
      	iArray[6][0]="��ȫ���κ�";      //����
      	iArray[6][1]="80px";            //�п�
      	iArray[6][2]=100;            		//�����ֵ
      	iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	iArray[7]=new Array();
      	iArray[7][0]="��ȫ����"; 				//����
      	iArray[7][1]="60px";            //�п�
      	iArray[7][2]=100;            		//�����ֵ
      	iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }
      if(fm.AuditType.value=='04'){
      	iArray[6]=new Array();
      	iArray[6][0]="�����";       		//����
      	iArray[6][1]="90px";            //�п�
      	iArray[6][2]=100;            		//�����ֵ
      	iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	iArray[7]=new Array();
      	iArray[7][0]="������";     		//����
      	iArray[7][1]="60px";            //�п�
      	iArray[7][2]=100;            		//�����ֵ
      	iArray[7][3]=0;  
      	
      	iArray[8]=new Array();
	    iArray[8][0]="�������";        	//����
	    iArray[8][1]="50px";          	 	//�п�
	    iArray[8][2]=100;           		 	//�����ֵ
	    iArray[8][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      }else{
        iArray[8]=new Array();
	    iArray[8][0]="�˱�����";        	//����
	    iArray[8][1]="50px";          	 	//�п�
	    iArray[8][2]=100;           		 	//�����ֵ
	    iArray[8][3]=0 ;
      }
      
      
	    
      RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskInfoGrid.mulLineCount = 0;   
      RiskInfoGrid.displayTitle = 1;
      RiskInfoGrid.locked = 1;
      RiskInfoGrid.hiddenPlus = 1;
      RiskInfoGrid.canSel = 0;
      RiskInfoGrid.hiddenSubtraction = 1;
      RiskInfoGrid.loadMulLine(iArray); 
   }catch(ex){
     myAlert(ex);
   }
}

function initReInsureAuditGrid(){
	var iArray = new Array();  
    try{
    	iArray[0]=new Array();
    	iArray[0][0]="���";
    	iArray[0][1]="30px";
    	iArray[0][2]=10;
    	iArray[0][3]=0;
    	
    	iArray[1]=new Array();
    	iArray[1][0]="�ܵ�Ͷ��������";
    	iArray[1][1]="100px";
    	iArray[1][2]=300;
    	if(fm.ContType.value=='2'){
    	  iArray[1][3]=0;
    	}else{
    	  iArray[1][3]=3;
    	}
    	
    	iArray[2]=new Array();
    	iArray[2][0]="��˴����";
    	iArray[2][1]="60px";
    	iArray[2][2]=100;
    	iArray[2][3]=0;
    	
    	iArray[3]=new Array();
    	iArray[3][0]="������";
    	iArray[3][1]="60px";
    	iArray[3][2]=100;
    	iArray[3][3]=0;
    	
    	iArray[4]=new Array();
    	iArray[4][0]="����ʱ��";         	
    	iArray[4][1]="100px";            	
    	iArray[4][2]=300;            			
    	iArray[4][3]=0;
    	
    	iArray[5]=new Array();
    	iArray[5][0]="����·��";         	
    	iArray[5][1]="150px";            	
    	iArray[5][2]=300;            			
    	iArray[5][3]=0              			
    	       
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
    	
    	iArray[8]=new Array();
    	iArray[8][0]="���ͻظ���Ϣ";
    	iArray[8][1]="0px";
    	iArray[8][2]=5000;
    	iArray[8][3]=3
    	
    	ReInsureAuditGrid = new MulLineEnter( "fm" , "ReInsureAuditGrid" ); 
    	ReInsureAuditGrid.muineCount = 0;   
    	ReInsureAuditGrid.displayTitle = 1;
    	ReInsureAuditGrid.locked = 1;
    	ReInsureAuditGrid.canSel = 1;
    	ReInsureAuditGrid.hiddenPlus = 1;
    	ReInsureAuditGrid.hiddenSubtraction = 1;
    	ReInsureAuditGrid.selBoxEventFuncName = "showAnswerIdea";
    	ReInsureAuditGrid.loadMulLine(iArray);  
    	
    }catch(ex){
      myAlert(ex);
    }
}

</script>

