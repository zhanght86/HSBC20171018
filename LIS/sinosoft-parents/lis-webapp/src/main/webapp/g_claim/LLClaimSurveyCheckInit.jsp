<%
/***************************************************************
 * <p>ProName��LLSurveyReviewCheckInit.jsp</p>
 * <p>Title������¼������</p>
 * <p>Description������¼������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<script language="JavaScript">
/**
 * ��ʼ������

 */
function initForm() {
	
	try {
		initParam();
		initInpBox();
		initButton();
		
		//initPublicPoolGrid(); 
		initPrivatePoolGrid(); 
		
		QueryUwInfo();
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�

 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		
	} catch (ex) {
		alert("��ʼ����ť����");
	}
}

/**
 * ��null���ַ���תΪ��

 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}
//��ʼ��"�������"���
function initPublicPoolGrid()   
{
  var iArray = new Array();
  var  i=0;
  try
  {
		iArray[i]=new Array();
    iArray[i][0]="���";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�������";          
    iArray[i][1]="80px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="������/������";            
    iArray[i][1]="130px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

    iArray[i]=new Array();
    iArray[i][0]="��������";        
    iArray[i][1]="60px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

		iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";          
    iArray[i][1]="120px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=0;                     

    iArray[i]=new Array();
    iArray[i][0]="����������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="��������";          
    iArray[i][1]="60px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="����ԭ��";          
    iArray[i][1]="180px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢�����";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="�Ƿ���ص���";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0;     
		      		                  			 
    PublicPoolGrid= new MulLineEnter( "fm" , "PublicPoolGrid"); 
    PublicPoolGrid.mulLineCount = 1;
    PublicPoolGrid.displayTitle = 1;
    PublicPoolGrid.canSel = 1;      //�Ƿ����RadioBox
    PublicPoolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    PublicPoolGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    PublicPoolGrid.loadMulLine(iArray);      
     }
    catch(ex)
    { 
    	alert(ex); 
    }
    }
  
function initPrivatePoolGrid()
{
	
var iArray = new Array();
  var  i=0;
  try
  {
		iArray[i]=new Array();
    iArray[i][0]="���";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�������";          
    iArray[i][1]="80px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="������/������";            
    iArray[i][1]="130px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

    iArray[i]=new Array();
    iArray[i][0]="��������";        
    iArray[i][1]="60px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

		iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";          
    iArray[i][1]="120px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=0;                     

    iArray[i]=new Array();
    iArray[i][0]="����������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="��������";          
    iArray[i][1]="60px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="����ԭ��";          
    iArray[i][1]="180px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="���鷢�����";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="�Ƿ���ص���";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0;    
		      		                  			 
    PrivatePoolGrid= new MulLineEnter( "fm" , "PrivatePoolGrid"); 
    PrivatePoolGrid.mulLineCount = 0;
    PrivatePoolGrid.displayTitle = 1;
    PrivatePoolGrid.canSel = 1;      //�Ƿ����RadioBox
    PrivatePoolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    PrivatePoolGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    PrivatePoolGrid.selBoxEventFuncName = "ShowSurveyReviewEntry";//����RadioBoxʱ��Ӧ����
    PrivatePoolGrid.loadMulLine(iArray);      
	}catch(ex) { 
    	alert(ex); 
   }
}
</script>