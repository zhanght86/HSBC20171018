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
    	
    	initLLSubmitApplyGrid();  
    	
    	easyQueryClick();    
    	
     }
    catch(re)
    {
    	alert("LLSubmitApplyDealMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
//��ʼ��"���ճʱ�����"���
function initLLSubmitApplyGrid()   
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
		    iArray[2][0]="�������";         		
		    iArray[2][1]="80px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=0;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="ɨ�����Ա";         		
		    iArray[3][1]="150px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="ɨ������";       
		    iArray[4][1]="100px";            	
		    iArray[4][2]=100;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="ɨ��ʱ��";        
		    iArray[5][1]="100px";            		
		    iArray[5][2]=100;            			  
		    iArray[5][3]=0;  
		      
		   
		      		                  			 
		      LLSubmitApplyGrid= new MulLineEnter( "fm" , "LLSubmitApplyGrid"); 
		      LLSubmitApplyGrid.mulLineCount = 0;
		      LLSubmitApplyGrid.displayTitle = 1;
		      LLSubmitApplyGrid.canSel = 1;      //�Ƿ����RadioBox
		      LLSubmitApplyGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		      LLSubmitApplyGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		      LLSubmitApplyGrid.selBoxEventFuncName = "LLSubmitApplyGridClick"; //����RadioBoxʱ��Ӧ����
		      LLSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }
    
 
</script>
