<%
//程序名称：LLSubmitApplyDealMissInit.jsp
//程序功能：呈报信息处理页面控件的初始化
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">


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
    	
    	initLPSubmitApplyGrid();  
    	initReLPSubmitApplyGrid();	
    	easyQueryClick();    
    	ReQueryClick();
     }
    catch(re)
    {
    	alert("BqSubmitApplyDealMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
//初始化"接收呈报队列"表格
function initLPSubmitApplyGrid()   
	{
    	var iArray = new Array();   	
      	try
        {
				iArray[0]=new Array();
		    iArray[0][0]="序号";    	 //列名
		    iArray[0][1]="30px";          //列宽
		    iArray[0][2]=100;            //列最大值
		    iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许
		
		    iArray[1]=new Array();
		    iArray[1][0]="呈报序号";         		
		    iArray[1][1]="100px";         			
		    iArray[1][2]=10;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="保全受理号";         		
		    iArray[2][1]="0px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=3;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="团体保单号";         		
		    iArray[3][1]="100px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="保全项目";       
		    iArray[4][1]="80px";            	
		    iArray[4][2]=60;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="呈报人";        
		    iArray[5][1]="80px";            		
		    iArray[5][2]=80;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="呈报状态";        
		    iArray[6][1]="0px";            		
		    iArray[6][2]=60;            			  
		    iArray[6][3]=3;  
		    
		    iArray[7]=new Array();
		    iArray[7][0]="呈报状态";        
		    iArray[7][1]="80px";            		
		    iArray[7][2]=60;            			  
		    iArray[7][3]=0; 
		    
		    iArray[8]=new Array();
		    iArray[8][0]="呈报日期";        
		    iArray[8][1]="80px";            		
		    iArray[8][2]=60;            			  
		    iArray[8][3]=0;  
		    
		    iArray[9]=new Array();
		    iArray[9][0]="呈报次数";        
		    iArray[9][1]="80px";            		
		    iArray[9][2]=60;            			  
		    iArray[9][3]=0;  
		    
		    iArray[10]=new Array();
		    iArray[10][0]="最近一次处理人";        
		    iArray[10][1]="100px";            		
		    iArray[10][2]=60;            			  
		    iArray[10][3]=0;  
		 		
		 		iArray[11]=new Array();
		    iArray[11][0]="最近一次处理日期";        
		    iArray[11][1]="100px";            		
		    iArray[11][2]=60;            			  
		    iArray[11][3]=0;     
		      		                  			 
		    LPSubmitApplyGrid= new MulLineEnter( "document" , "LPSubmitApplyGrid"); 
		    LPSubmitApplyGrid.mulLineCount = 5;
		    LPSubmitApplyGrid.displayTitle = 1;
		    LPSubmitApplyGrid.canSel = 1;      //是否出现RadioBox
		    LPSubmitApplyGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		    LPSubmitApplyGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		    LPSubmitApplyGrid.selBoxEventFuncName = "LPSubmitApplyGridClick"; //单击RadioBox时响应函数
		    LPSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }
//反馈队列表格    
function initReLPSubmitApplyGrid()   
	{
    	var iArray = new Array();   	
      	try
        {
			iArray[0]=new Array();
		    iArray[0][0]="序号";    	 //列名
		    iArray[0][1]="30px";          //列宽
		    iArray[0][2]=100;            //列最大值
		    iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许
		
		    iArray[1]=new Array();
		    iArray[1][0]="呈报序号";         		
		    iArray[1][1]="100px";         			
		    iArray[1][2]=10;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="保全受理号";         		
		    iArray[2][1]="0px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=3;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="团体保单号";         		
		    iArray[3][1]="100px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="保全项目";       
		    iArray[4][1]="80px";            	
		    iArray[4][2]=60;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="承接人";        
		    iArray[5][1]="80px";            		
		    iArray[5][2]=80;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="呈报状态";        
		    iArray[6][1]="0px";            		
		    iArray[6][2]=60;            			  
		    iArray[6][3]=3;  
		    
		    iArray[7]=new Array();
		    iArray[7][0]="呈报状态";        
		    iArray[7][1]="80px";            		
		    iArray[7][2]=60;            			  
		    iArray[7][3]=0; 
		    
		    iArray[8]=new Array();
		    iArray[8][0]="呈报日期";        
		    iArray[8][1]="80px";            		
		    iArray[8][2]=60;            			  
		    iArray[8][3]=0;  
		    
		    iArray[9]=new Array();
		    iArray[9][0]="最近一次处理日期";        
		    iArray[9][1]="100px";            		
		    iArray[9][2]=60;            			  
		    iArray[9][3]=0;  
		      		                  			 
		    ReLPSubmitApplyGrid= new MulLineEnter( "document" , "ReLPSubmitApplyGrid"); 
		    ReLPSubmitApplyGrid.mulLineCount = 5;
		    ReLPSubmitApplyGrid.displayTitle = 1;
		    ReLPSubmitApplyGrid.canSel = 1;      //是否出现RadioBox
		    ReLPSubmitApplyGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		    ReLPSubmitApplyGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		    ReLPSubmitApplyGrid.selBoxEventFuncName = "ReLPSubmitApplyGridClick"; //单击RadioBox时响应函数
		    ReLPSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }    
</script>
