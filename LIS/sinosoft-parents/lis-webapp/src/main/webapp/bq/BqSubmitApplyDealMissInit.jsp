<%
//�������ƣ�LLSubmitApplyDealMissInit.jsp
//�����ܣ��ʱ���Ϣ����ҳ��ؼ��ĳ�ʼ��
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">


//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
	try
    {
    	
    	initLPSubmitApplyGrid();  
    	initReLPSubmitApplyGrid();	
    	easyQueryClick();    
    	ReQueryClick();
     }
    catch(re)
    {
    	alert("BqSubmitApplyDealMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
//��ʼ��"���ճʱ�����"���
function initLPSubmitApplyGrid()   
	{
    	var iArray = new Array();   	
      	try
        {
				iArray[0]=new Array();
		    iArray[0][0]="���";    	 //����
		    iArray[0][1]="30px";          //�п�
		    iArray[0][2]=100;            //�����ֵ
		    iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[1]=new Array();
		    iArray[1][0]="�ʱ����";         		
		    iArray[1][1]="100px";         			
		    iArray[1][2]=10;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="��ȫ�����";         		
		    iArray[2][1]="0px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=3;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="���屣����";         		
		    iArray[3][1]="100px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="��ȫ��Ŀ";       
		    iArray[4][1]="80px";            	
		    iArray[4][2]=60;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="�ʱ���";        
		    iArray[5][1]="80px";            		
		    iArray[5][2]=80;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="�ʱ�״̬";        
		    iArray[6][1]="0px";            		
		    iArray[6][2]=60;            			  
		    iArray[6][3]=3;  
		    
		    iArray[7]=new Array();
		    iArray[7][0]="�ʱ�״̬";        
		    iArray[7][1]="80px";            		
		    iArray[7][2]=60;            			  
		    iArray[7][3]=0; 
		    
		    iArray[8]=new Array();
		    iArray[8][0]="�ʱ�����";        
		    iArray[8][1]="80px";            		
		    iArray[8][2]=60;            			  
		    iArray[8][3]=0;  
		    
		    iArray[9]=new Array();
		    iArray[9][0]="�ʱ�����";        
		    iArray[9][1]="80px";            		
		    iArray[9][2]=60;            			  
		    iArray[9][3]=0;  
		    
		    iArray[10]=new Array();
		    iArray[10][0]="���һ�δ�����";        
		    iArray[10][1]="100px";            		
		    iArray[10][2]=60;            			  
		    iArray[10][3]=0;  
		 		
		 		iArray[11]=new Array();
		    iArray[11][0]="���һ�δ�������";        
		    iArray[11][1]="100px";            		
		    iArray[11][2]=60;            			  
		    iArray[11][3]=0;     
		      		                  			 
		    LPSubmitApplyGrid= new MulLineEnter( "document" , "LPSubmitApplyGrid"); 
		    LPSubmitApplyGrid.mulLineCount = 5;
		    LPSubmitApplyGrid.displayTitle = 1;
		    LPSubmitApplyGrid.canSel = 1;      //�Ƿ����RadioBox
		    LPSubmitApplyGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		    LPSubmitApplyGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		    LPSubmitApplyGrid.selBoxEventFuncName = "LPSubmitApplyGridClick"; //����RadioBoxʱ��Ӧ����
		    LPSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }
//�������б��    
function initReLPSubmitApplyGrid()   
	{
    	var iArray = new Array();   	
      	try
        {
			iArray[0]=new Array();
		    iArray[0][0]="���";    	 //����
		    iArray[0][1]="30px";          //�п�
		    iArray[0][2]=100;            //�����ֵ
		    iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[1]=new Array();
		    iArray[1][0]="�ʱ����";         		
		    iArray[1][1]="100px";         			
		    iArray[1][2]=10;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="��ȫ�����";         		
		    iArray[2][1]="0px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=3;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="���屣����";         		
		    iArray[3][1]="100px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="��ȫ��Ŀ";       
		    iArray[4][1]="80px";            	
		    iArray[4][2]=60;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="�н���";        
		    iArray[5][1]="80px";            		
		    iArray[5][2]=80;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="�ʱ�״̬";        
		    iArray[6][1]="0px";            		
		    iArray[6][2]=60;            			  
		    iArray[6][3]=3;  
		    
		    iArray[7]=new Array();
		    iArray[7][0]="�ʱ�״̬";        
		    iArray[7][1]="80px";            		
		    iArray[7][2]=60;            			  
		    iArray[7][3]=0; 
		    
		    iArray[8]=new Array();
		    iArray[8][0]="�ʱ�����";        
		    iArray[8][1]="80px";            		
		    iArray[8][2]=60;            			  
		    iArray[8][3]=0;  
		    
		    iArray[9]=new Array();
		    iArray[9][0]="���һ�δ�������";        
		    iArray[9][1]="100px";            		
		    iArray[9][2]=60;            			  
		    iArray[9][3]=0;  
		      		                  			 
		    ReLPSubmitApplyGrid= new MulLineEnter( "document" , "ReLPSubmitApplyGrid"); 
		    ReLPSubmitApplyGrid.mulLineCount = 5;
		    ReLPSubmitApplyGrid.displayTitle = 1;
		    ReLPSubmitApplyGrid.canSel = 1;      //�Ƿ����RadioBox
		    ReLPSubmitApplyGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		    ReLPSubmitApplyGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		    ReLPSubmitApplyGrid.selBoxEventFuncName = "ReLPSubmitApplyGridClick"; //����RadioBoxʱ��Ӧ����
		    ReLPSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }    
</script>
