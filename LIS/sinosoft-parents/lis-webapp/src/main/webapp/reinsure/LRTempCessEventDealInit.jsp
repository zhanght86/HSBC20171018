<%@include file="/i18n/language.jsp"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  

<script type="text/javascript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox(){
	try{ 
  }
  catch(ex){
    myAlert("��ReInsureInit.jsp-->"+"initInpBox�����з����쳣:��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox(){
  try{ 
  }
  catch(ex){
    myAlert("��ReInsureInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(){
  try{
	  initInpBox();
		initSelBox();
		initTempEventGrid();
		initTempCessPreceptGrid();
		initTempCessInputGrid();
		QueryTempCessEvent();
  }
  catch(re){
    myAlert("ReInsureInit.jsp-->"+"��ʼ���������!");
  }
}


// ������Ϣ�б�ĳ�ʼ��
function initTempEventGrid()
{
    var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          				//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�¼���";    				//����
      iArray[1][1]="190px";            	//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���屣����";        //����
      iArray[2][1]="140px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         	//����
      iArray[3][1]="70px";            	//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
      iArray[4]=new Array();
      iArray[4][0]="���α���";         	//����
      iArray[4][1]="70px";            	//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����������";         		  //����
      iArray[5][1]="80px";            	//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�¼�����";         	//����
      iArray[6][1]="60px";            	//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�¼����ͱ���";      //����
      iArray[7][1]="80px";            	//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�����˱���";      	//����
      iArray[8][1]="100px";            	//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="��ͬ��";      			//����
      iArray[9][1]="120px";            	//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempEventGrid = new MulLineEnter("fm","TempEventGrid"); 
      //��Щ���Ա�����loadMulLineǰ
      TempEventGrid.mulLineCount = 0;   
      TempEventGrid.displayTitle = 1;
      TempEventGrid.locked = 1;
      TempEventGrid.hiddenPlus = 1;
      TempEventGrid.canSel = 1;
      TempEventGrid.hiddenSubtraction = 1;
      TempEventGrid.selBoxEventFuncName = "showInputItem";
      TempEventGrid.loadMulLine(iArray);  
   }
   catch(ex){
     myAlert(ex);
   }
}

function initTempCessPreceptGrid(){
	var iArray = new Array();
    
    try{
	      iArray[0]=new Array();
	      iArray[0][0]="���";         			//����
	      iArray[0][1]="30px";         			//�п�
	      iArray[0][2]=10;          				//�����ֵ
	      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[1]=new Array();
	      iArray[1][0]="�ٷֺ�ͬ��";    		//����
	      iArray[1][1]="150px";            	//�п�
	      iArray[1][2]=200;            			//�����ֵ
	      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[2]=new Array();
	      iArray[2][0]="�ٷַ�����";        //����
	      iArray[2][1]="150px";            	//�п�
	      iArray[2][2]=200;            			//�����ֵ
	      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	      TempCessPreceptGrid = new MulLineEnter("fm","TempCessPreceptGrid"); 
	      //��Щ���Ա�����loadMulLineǰ
	      TempCessPreceptGrid.mulLineCount = 0;   
	      TempCessPreceptGrid.displayTitle = 1;
	      TempCessPreceptGrid.locked = 1;
	      TempCessPreceptGrid.hiddenPlus = 1;
	      TempCessPreceptGrid.canSel = 1;
	      TempCessPreceptGrid.hiddenSubtraction = 1;
	      TempCessPreceptGrid.loadMulLine(iArray);  
     }
	   catch(ex){
	     myAlert(ex);
	   }
}

// ������Ϣ�б�ĳ�ʼ��
function initTempCessInputGrid(){
    var iArray = new Array();
    
    try{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          				//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
      iArray[1]=new Array();
			iArray[1][0]="��˾����";
			iArray[1][1]="120px";  
			iArray[1][2]=100;  
			iArray[1][3]=2;    
			iArray[1][4]="lrcompany"; 
			iArray[1][5]="1|2"; 	 						//�����ô���ֱ���ڵ�1��2
			iArray[1][6]="0|1";									
			iArray[1][9]="��˾����|notnull";    
			iArray[1][19]=1; 
			
			iArray[2]=new Array();
			iArray[2][0]="��˾����";
			iArray[2][1]="100px";  
			iArray[2][2]=100;     
			iArray[2][3]=1;      
			
			iArray[3]=new Array();
			iArray[3][0]="�ٷַ�����";
			iArray[3][1]="130px";  
			iArray[3][2]=100;     
			iArray[3][3]=3;      
			//iArray[3][4]="lrtempprecept";
	  	//iArray[3][5]="3"; 	 
	    //iArray[3][6]="0";	 
			//iArray[1][9]="�ٷַ�����|notnull";    
			
      iArray[4]=new Array();
      iArray[4][0]="�ֱ��������";      //����
      iArray[4][1]="80px";            	//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
      iArray[5]=new Array();
      iArray[5][0]="�ֱ����ѱ���";      //����
      iArray[5][1]="80px";            	//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�ֱ�Ӷ�����";      //����
      iArray[6][1]="80px";            	//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��ǰ���ձ���";      //����
      iArray[7][1]="80px";            	//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�ֳ�����";      		//����
      iArray[8][1]="80px";            	//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�ֱ�����";      		//����
      iArray[9][1]="100px";            	//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="����̯��";      		//����
      iArray[10][1]="100px";            //�п�
      iArray[10][2]=100;            		//�����ֵ
      iArray[10][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������

      TempCessInputGrid = new MulLineEnter("fm","TempCessInputGrid"); 
      //��Щ���Ա�����loadMulLineǰ
      TempCessInputGrid.mulLineCount = 0;   
      TempCessInputGrid.displayTitle = 1;
      TempCessInputGrid.locked = 0;
      TempCessInputGrid.hiddenPlus = 0;
      TempCessInputGrid.canSel = 0;
      TempCessInputGrid.hiddenSubtraction = 0;
      TempCessInputGrid.loadMulLine(iArray);  
   }
   catch(ex){
     myAlert(ex);
   }
}

</script>
