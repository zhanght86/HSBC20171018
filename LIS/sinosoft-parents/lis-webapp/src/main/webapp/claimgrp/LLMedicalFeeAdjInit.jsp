<%
//Name:LLMedicalFeeAdjInit.jsp
//function：
//author: quyang
//Date: 2005-07-05
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
	
//接收报案页面传递过来的参数
function initParam()
{
    fm.all('ClmNo2').value = nullToEmpty("<%= tRptNo %>");
}

//把null的字符串转为空
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
    alter("在LLMedicalFeeAdjInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

/**
	页面初始化
*/
function initInpBox()
{
  try
  {
  }
  catch(ex)
  {
    alter("在LLMedicalFeeAdjInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

/**=========================================================================
    医疗费用调整显示信息
   =========================================================================
*/
function initMedFeeInHosInpGrid()
{
    var iArray = new Array();
    try
    {
		iArray[0]=new Array();
        iArray[0][0]="序号";           
        iArray[0][1]="30px";           
        iArray[0][2]=10;               
        iArray[0][3]=0;                
                                       
        iArray[1]=new Array();         
        iArray[1][0]="赔案号";         
        iArray[1][1]="100px";           
        iArray[1][2]=10;               
        iArray[1][3]=0;                
                                       
                                       
        iArray[2]=new Array();         
        iArray[2][0]="费用类型";       
        iArray[2][1]="80px";          
        iArray[2][2]=10;               
        iArray[2][3]=0;                
                                       
                                       
        iArray[3]=new Array();         
        iArray[3][0]="费用代码";       
        iArray[3][1]="80px";          
        iArray[3][2]=10;               
        iArray[3][3]=0;                
                                       
        iArray[4]=new Array();         
        iArray[4][0]="费用名称";       
        iArray[4][1]="100px";          
        iArray[4][2]=10;               
        iArray[4][3]=0;                
                                       
        iArray[5]=new Array();         
        iArray[5][0]="序号";           
        iArray[5][1]="0px";            
        iArray[5][2]=10;               
        iArray[5][3]=3;                
  if (fm.OperationType.value == 'A' || fm.OperationType.value == 'B'){                                     
        iArray[6]=new Array();         
        iArray[6][0]="医院编号";       
        iArray[6][1]="80px";           
        iArray[6][2]=10;               
        iArray[6][3]=0;                
                                       
                                       
        iArray[7]=new Array();         
        iArray[7][0]="医院名称";       
        iArray[7][1]="80px";           
        iArray[7][2]=10;               
        iArray[7][3]=0;                
                                       
        iArray[8]=new Array();         
        iArray[8][0]="医院等级";       
        iArray[8][1]="80px";           
        iArray[8][2]=10;               
        iArray[8][3]=0;                
                                       
        iArray[9]=new Array();         
        iArray[9][0]="开始时间";       
        iArray[9][1]="80px";           
        iArray[9][2]=10;               
        iArray[9][3]=0;                
                                       
                                       
        iArray[10]=new Array();        
        iArray[10][0]="结束时间";      
        iArray[10][1]="80px";          
        iArray[10][2]=10;              
        iArray[10][3]=0;               
                                       
        iArray[11]=new Array();        
        iArray[11][0]="天数";          
        iArray[11][1]="30px";          
        iArray[11][2]=10;              
        iArray[11][3]=0;   

		iArray[12]=new Array();        
        iArray[12][0]="原始金额";      
        iArray[12][1]="80px";          
        iArray[12][2]=10;              
        iArray[12][3]=0;               
                                       
        iArray[13]=new Array();        
        iArray[13][0]="调整金额";      
        iArray[13][1]="80px";          
        iArray[13][2]=10;              
        iArray[13][3]=0;  
  }else if(fm.OperationType.value == 'C'){

	    iArray[6]=new Array();         
        iArray[6][0]="医院编号";       
        iArray[6][1]="0px";           
        iArray[6][2]=10;               
        iArray[6][3]=3;                
                                       
                                       
        iArray[7]=new Array();         
        iArray[7][0]="医院名称";       
        iArray[7][1]="0px";           
        iArray[7][2]=10;               
        iArray[7][3]=3;                
                                       
        iArray[8]=new Array();         
        iArray[8][0]="医院等级";       
        iArray[8][1]="0px";           
        iArray[8][2]=10;               
        iArray[8][3]=3;                
                                       
        iArray[9]=new Array();         
        iArray[9][0]="开始时间";       
        iArray[9][1]="0px";           
        iArray[9][2]=10;               
        iArray[9][3]=3;                
                                       
                                       
        iArray[10]=new Array();        
        iArray[10][0]="结束时间";      
        iArray[10][1]="0px";          
        iArray[10][2]=10;              
        iArray[10][3]=3;               
                                       
        iArray[11]=new Array();        
        iArray[11][0]="天数";          
        iArray[11][1]="0px";          
        iArray[11][2]=10;              
        iArray[11][3]=3;   
//                                     
        iArray[12]=new Array();        
        iArray[12][0]="原始金额";      
        iArray[12][1]="0px";          
        iArray[12][2]=10;              
        iArray[12][3]=3;               
                                       
        iArray[13]=new Array();        
        iArray[13][0]="调整金额";      
        iArray[13][1]="0px";          
        iArray[13][2]=10;              
        iArray[13][3]=3;     
		
  }else if (fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E'){
		iArray[6]=new Array();         
        iArray[6][0]="医院编号";       
        iArray[6][1]="0px";           
        iArray[6][2]=10;               
        iArray[6][3]=3;                
                                       
                                       
        iArray[7]=new Array();         
        iArray[7][0]="医院名称";       
        iArray[7][1]="0px";           
        iArray[7][2]=10;               
        iArray[7][3]=3;                
                                       
        iArray[8]=new Array();         
        iArray[8][0]="医院等级";       
        iArray[8][1]="0px";           
        iArray[8][2]=10;               
        iArray[8][3]=3;                
                                       
        iArray[9]=new Array();         
        iArray[9][0]="开始时间";       
        iArray[9][1]="0px";           
        iArray[9][2]=10;               
        iArray[9][3]=3;                
                                       
                                       
        iArray[10]=new Array();        
        iArray[10][0]="结束时间";      
        iArray[10][1]="0px";          
        iArray[10][2]=10;              
        iArray[10][3]=3;               
                                       
        iArray[11]=new Array();        
        iArray[11][0]="天数";          
        iArray[11][1]="0px";          
        iArray[11][2]=10;              
        iArray[11][3]=3;   
//                                     
        iArray[12]=new Array();        
        iArray[12][0]="原始金额";      
        iArray[12][1]="80px";          
        iArray[12][2]=10;              
        iArray[12][3]=0;               
                                       
        iArray[13]=new Array();        
        iArray[13][0]="调整金额";      
        iArray[13][1]="80px";          
        iArray[13][2]=10;              
        iArray[13][3]=0; 
  }
                                       
        iArray[14]=new Array();        
        iArray[14][0]="调整原因";      
        iArray[14][1]="100px";           
        iArray[14][2]=10;              
        iArray[14][3]=0;   
//		iArray[14][4]='lldutyadjreason';      //设置要引用LDcode中的代码
//        iArray[14][5]="14|21";             //引用代码对应第几列，'|'为分割符
//        iArray[14][6]="1|0";
                                       
        iArray[15]=new Array();        
        iArray[15][0]="调整备注";      
        iArray[15][1]="100px";           
        iArray[15][2]=10;              
        iArray[15][3]=0;               
   if(fm.OperationType.value == 'C'){                                    
        iArray[16]=new Array();        
        iArray[16][0]="伤残类型";      
        iArray[16][1]="100px";           
        iArray[16][2]=10;              
        iArray[16][3]=0;               
		                       
		iArray[17]=new Array();        
        iArray[17][0]="伤残代码";      
        iArray[17][1]="100px";           
        iArray[17][2]=10;              
        iArray[17][3]=0;               
                                       
		iArray[18]=new Array();        
        iArray[18][0]="伤残级别名称";  
        iArray[18][1]="100px";           
        iArray[18][2]=10;              
        iArray[18][3]=0;               
                                       
		iArray[19]=new Array();        
        iArray[19][0]="残疾给付比例";  
        iArray[19][1]="100px";           
        iArray[19][2]=10;              
        iArray[19][3]=0;               
                                       
		iArray[20]=new Array();        
        iArray[20][0]="实际给付比例";  
        iArray[20][1]="100px";           
        iArray[20][2]=10;              
        iArray[20][3]=0;               
   }else{
		iArray[16]=new Array();        
        iArray[16][0]="伤残类型";      
        iArray[16][1]="0px";           
        iArray[16][2]=10;              
        iArray[16][3]=3;               
		                       
		iArray[17]=new Array();        
        iArray[17][0]="伤残代码";      
        iArray[17][1]="0px";           
        iArray[17][2]=10;              
        iArray[17][3]=3;               
                                       
		iArray[18]=new Array();        
        iArray[18][0]="伤残级别名称";  
        iArray[18][1]="0px";           
        iArray[18][2]=10;              
        iArray[18][3]=3;               
                                       
		iArray[19]=new Array();        
        iArray[19][0]="残疾给付比例";  
        iArray[19][1]="0px";           
        iArray[19][2]=10;              
        iArray[19][3]=3;               
                                       
		iArray[20]=new Array();        
        iArray[20][0]="实际给付比例";  
        iArray[20][1]="0px";           
        iArray[20][2]=10;              
        iArray[20][3]=3;
   }

        iArray[21]=new Array();        
        iArray[21][0]="调整原因";      
        iArray[21][1]="0px";           
        iArray[21][2]=10;              
        iArray[21][3]=3;

		iArray[22]=new Array();         
        iArray[22][0]="医院等级";       
        iArray[22][1]="0px";           
        iArray[22][2]=10;               
        iArray[22][3]=3; 

        
        MedFeeInHosInpGrid = new MulLineEnter("fm","MedFeeInHosInpGrid");
        MedFeeInHosInpGrid.mulLineCount = 0;
        MedFeeInHosInpGrid.displayTitle = 1;
        MedFeeInHosInpGrid.locked = 0;
//      MedFeeInHosInpGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeInHosInpGrid.canSel =1;              //单选按钮，1显示，0隐藏
        MedFeeInHosInpGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        MedFeeInHosInpGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        MedFeeInHosInpGrid.selBoxEventFuncName = "getMedFeeInHosInpGrid"; //函数名称
//        MedFeeInHosInpGrid.selBoxEventFuncParm ="";          //参数        
        MedFeeInHosInpGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
