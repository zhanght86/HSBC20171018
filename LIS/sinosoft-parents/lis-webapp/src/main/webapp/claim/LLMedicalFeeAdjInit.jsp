<%
//Name:LLMedicalFeeAdjInit.jsp
//function��
//author: quyang
//Date: 2005-07-05
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
	
//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('ClmNo2').value = nullToEmpty("<%= tRptNo %>");
}

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
    initParam();
    initInpBox();
  //  initSubReportGrid();
  //  initLLClaimTypeGrid();
  //  initLLClaimInsItemGrid();
  //  initMedFeeInHosInpGrid();
  //  initQuery();
  }
  catch(re)
  {
    alter("��LLMedicalFeeAdjInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

/**
	ҳ���ʼ��
*/
function initInpBox()
{
  try
  {
  }
  catch(ex)
  {
    alter("��LLMedicalFeeAdjInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

/**=========================================================================
    ҽ�Ʒ��õ�����ʾ��Ϣ
   =========================================================================
*/
function initMedFeeInHosInpGrid()
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
        iArray[1][0]="�ⰸ��";         
        iArray[1][1]="100px";           
        iArray[1][2]=10;               
        iArray[1][3]=0;                
                                       
                                       
        iArray[2]=new Array();         
        iArray[2][0]="��������";       
        iArray[2][1]="80px";          
        iArray[2][2]=10;               
        iArray[2][3]=0;                
                                       
                                       
        iArray[3]=new Array();         
        iArray[3][0]="���ô���";       
        iArray[3][1]="80px";          
        iArray[3][2]=10;               
        iArray[3][3]=0;                
                                       
        iArray[4]=new Array();         
        iArray[4][0]="��������";       
        iArray[4][1]="100px";          
        iArray[4][2]=10;               
        iArray[4][3]=0;                
                                       
        iArray[5]=new Array();         
        iArray[5][0]="���";           
        iArray[5][1]="0px";            
        iArray[5][2]=10;               
        iArray[5][3]=3;                
  if (fm.OperationType.value == 'A' || fm.OperationType.value == 'B'){                                     
        iArray[6]=new Array();         
        iArray[6][0]="ҽԺ���";       
        iArray[6][1]="80px";           
        iArray[6][2]=10;               
        iArray[6][3]=0;                
                                       
                                       
        iArray[7]=new Array();         
        iArray[7][0]="ҽԺ����";       
        iArray[7][1]="80px";           
        iArray[7][2]=10;               
        iArray[7][3]=0;                
                                       
        iArray[8]=new Array();         
        iArray[8][0]="ҽԺ�ȼ�";       
        iArray[8][1]="80px";           
        iArray[8][2]=10;               
        iArray[8][3]=0;                
                                       
        iArray[9]=new Array();         
        iArray[9][0]="��ʼʱ��";       
        iArray[9][1]="80px";           
        iArray[9][2]=10;               
        iArray[9][3]=0;                
                                       
                                       
        iArray[10]=new Array();        
        iArray[10][0]="����ʱ��";      
        iArray[10][1]="80px";          
        iArray[10][2]=10;              
        iArray[10][3]=0;               
                                       
        iArray[11]=new Array();        
        iArray[11][0]="����";          
        iArray[11][1]="30px";          
        iArray[11][2]=10;              
        iArray[11][3]=0;   

		iArray[12]=new Array();        
        iArray[12][0]="ԭʼ���";      
        iArray[12][1]="80px";          
        iArray[12][2]=10;              
        iArray[12][3]=0;               
                                       
        iArray[13]=new Array();        
        iArray[13][0]="�������";      
        iArray[13][1]="80px";          
        iArray[13][2]=10;              
        iArray[13][3]=0;  
  }else if(fm.OperationType.value == 'C'){

	    iArray[6]=new Array();         
        iArray[6][0]="ҽԺ���";       
        iArray[6][1]="0px";           
        iArray[6][2]=10;               
        iArray[6][3]=3;                
                                       
                                       
        iArray[7]=new Array();         
        iArray[7][0]="ҽԺ����";       
        iArray[7][1]="0px";           
        iArray[7][2]=10;               
        iArray[7][3]=3;                
                                       
        iArray[8]=new Array();         
        iArray[8][0]="ҽԺ�ȼ�";       
        iArray[8][1]="0px";           
        iArray[8][2]=10;               
        iArray[8][3]=3;                
                                       
        iArray[9]=new Array();         
        iArray[9][0]="��ʼʱ��";       
        iArray[9][1]="0px";           
        iArray[9][2]=10;               
        iArray[9][3]=3;                
                                       
                                       
        iArray[10]=new Array();        
        iArray[10][0]="����ʱ��";      
        iArray[10][1]="0px";          
        iArray[10][2]=10;              
        iArray[10][3]=3;               
                                       
        iArray[11]=new Array();        
        iArray[11][0]="����";          
        iArray[11][1]="0px";          
        iArray[11][2]=10;              
        iArray[11][3]=3;   
//                                     
        iArray[12]=new Array();        
        iArray[12][0]="ԭʼ���";      
        iArray[12][1]="0px";          
        iArray[12][2]=10;              
        iArray[12][3]=3;               
                                       
        iArray[13]=new Array();        
        iArray[13][0]="�������";      
        iArray[13][1]="0px";          
        iArray[13][2]=10;              
        iArray[13][3]=3;     
		
  }else if (fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E'){
		iArray[6]=new Array();         
        iArray[6][0]="ҽԺ���";       
        iArray[6][1]="0px";           
        iArray[6][2]=10;               
        iArray[6][3]=3;                
                                       
                                       
        iArray[7]=new Array();         
        iArray[7][0]="ҽԺ����";       
        iArray[7][1]="0px";           
        iArray[7][2]=10;               
        iArray[7][3]=3;                
                                       
        iArray[8]=new Array();         
        iArray[8][0]="ҽԺ�ȼ�";       
        iArray[8][1]="0px";           
        iArray[8][2]=10;               
        iArray[8][3]=3;                
                                       
        iArray[9]=new Array();         
        iArray[9][0]="��ʼʱ��";       
        iArray[9][1]="0px";           
        iArray[9][2]=10;               
        iArray[9][3]=3;                
                                       
                                       
        iArray[10]=new Array();        
        iArray[10][0]="����ʱ��";      
        iArray[10][1]="0px";          
        iArray[10][2]=10;              
        iArray[10][3]=3;               
                                       
        iArray[11]=new Array();        
        iArray[11][0]="����";          
        iArray[11][1]="0px";          
        iArray[11][2]=10;              
        iArray[11][3]=3;   
//                                     
        iArray[12]=new Array();        
        iArray[12][0]="ԭʼ���";      
        iArray[12][1]="80px";          
        iArray[12][2]=10;              
        iArray[12][3]=0;               
                                       
        iArray[13]=new Array();        
        iArray[13][0]="�������";      
        iArray[13][1]="80px";          
        iArray[13][2]=10;              
        iArray[13][3]=0; 
  }
                                       
        iArray[14]=new Array();        
        iArray[14][0]="����ԭ��";      
        iArray[14][1]="100px";           
        iArray[14][2]=10;              
        iArray[14][3]=0;   
//		iArray[14][4]='lldutyadjreason';      //����Ҫ����LDcode�еĴ���
//        iArray[14][5]="14|21";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
//        iArray[14][6]="1|0";
                                       
        iArray[15]=new Array();        
        iArray[15][0]="������ע";      
        iArray[15][1]="100px";           
        iArray[15][2]=10;              
        iArray[15][3]=0;               
   if(fm.OperationType.value == 'C'){                                    
        iArray[16]=new Array();        
        iArray[16][0]="�˲�����";      
        iArray[16][1]="100px";           
        iArray[16][2]=10;              
        iArray[16][3]=0;               
		                       
		iArray[17]=new Array();        
        iArray[17][0]="�˲д���";      
        iArray[17][1]="100px";           
        iArray[17][2]=10;              
        iArray[17][3]=0;               
                                       
		iArray[18]=new Array();        
        iArray[18][0]="�˲м�������";  
        iArray[18][1]="100px";           
        iArray[18][2]=10;              
        iArray[18][3]=0;               
                                       
		iArray[19]=new Array();        
        iArray[19][0]="�м���������";  
        iArray[19][1]="100px";           
        iArray[19][2]=10;              
        iArray[19][3]=0;               
                                       
		iArray[20]=new Array();        
        iArray[20][0]="ʵ�ʸ�������";  
        iArray[20][1]="100px";           
        iArray[20][2]=10;              
        iArray[20][3]=0;               
   }else{
		iArray[16]=new Array();        
        iArray[16][0]="�˲�����";      
        iArray[16][1]="0px";           
        iArray[16][2]=10;              
        iArray[16][3]=3;               
		                       
		iArray[17]=new Array();        
        iArray[17][0]="�˲д���";      
        iArray[17][1]="0px";           
        iArray[17][2]=10;              
        iArray[17][3]=3;               
                                       
		iArray[18]=new Array();        
        iArray[18][0]="�˲м�������";  
        iArray[18][1]="0px";           
        iArray[18][2]=10;              
        iArray[18][3]=3;               
                                       
		iArray[19]=new Array();        
        iArray[19][0]="�м���������";  
        iArray[19][1]="0px";           
        iArray[19][2]=10;              
        iArray[19][3]=3;               
                                       
		iArray[20]=new Array();        
        iArray[20][0]="ʵ�ʸ�������";  
        iArray[20][1]="0px";           
        iArray[20][2]=10;              
        iArray[20][3]=3;
   }

        iArray[21]=new Array();        
        iArray[21][0]="����ԭ��";      
        iArray[21][1]="0px";           
        iArray[21][2]=10;              
        iArray[21][3]=3;

		iArray[22]=new Array();         
        iArray[22][0]="ҽԺ�ȼ�";       
        iArray[22][1]="0px";           
        iArray[22][2]=10;               
        iArray[22][3]=3; 

        
        MedFeeInHosInpGrid = new MulLineEnter("document","MedFeeInHosInpGrid");
        MedFeeInHosInpGrid.mulLineCount = 5;
        MedFeeInHosInpGrid.displayTitle = 1;
        MedFeeInHosInpGrid.locked = 0;
//      MedFeeInHosInpGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeInHosInpGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeInHosInpGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeInHosInpGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        MedFeeInHosInpGrid.selBoxEventFuncName = "getMedFeeInHosInpGrid"; //��������
//        MedFeeInHosInpGrid.selBoxEventFuncParm ="";          //����        
        MedFeeInHosInpGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
