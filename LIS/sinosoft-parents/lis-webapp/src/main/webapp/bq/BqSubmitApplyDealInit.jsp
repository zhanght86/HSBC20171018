<%@page import="com.sinosoft.lis.pubfun.PubFun1"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
function initForm()
{
	try
    {   
        
       if(LoadFlag=="1") //呈报处理
       {
      
         DivSave.style.display='none';
         DivDeal.style.display='';
         DivSee.style.display='none';
         DivApp.style.display='none';
         easyQueryClick(); 
         initNewComCode();
       }
       if(LoadFlag=="2")
       {
       
         DivSave.style.display='';
         DivDeal.style.display='none';
         DivSee.style.display='none';
       	 initComCode();

       }
       if(LoadFlag=="3")
       {
       
         DivSave.style.display='none';
         DivDeal.style.display='none';
         DivSee.style.display='';
         DivApp.style.display='none';
         easyQueryClick(); 
       }
               
       if(LoadFlag=="5") //扫描呈报处理
       {
      
         DivSave.style.display='';
         DivDeal.style.display='none';
         DivSee.style.display='none';
         DivCPSee.style.display='';
       } 
     }
    catch(ex)
    {
    	alert("BqSubmitApplyDealInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

function initLPSubmitApplyTraceGrid()   
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
		    iArray[2][0]="流水号";         		
		    iArray[2][1]="50px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=0;              			
		
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
		    iArray[6][0]="承接人";        
		    iArray[6][1]="80px";            		
		    iArray[6][2]=60;            			  
		    iArray[6][3]=0;  
		    
		    iArray[7]=new Array();
		    iArray[7][0]="处理结果类型";        
		    iArray[7][1]="80px";            		
		    iArray[7][2]=60;            			  
		    iArray[7][3]=0; 
		    
		    iArray[8]=new Array();
		    iArray[8][0]="处理意见";        
		    iArray[8][1]="0px";            		
		    iArray[8][2]=60;            			  
		    iArray[8][3]=3;  
		    
		    iArray[9]=new Array();
		    iArray[9][0]="处理日期";        
		    iArray[9][1]="100px";            		
		    iArray[9][2]=60;            			  
		    iArray[9][3]=0;  
		 		    
		      		                  			 
		    LPSubmitApplyTraceGrid= new MulLineEnter( "document" , "LPSubmitApplyTraceGrid"); 
		    LPSubmitApplyTraceGrid.mulLineCount = 5;
		    LPSubmitApplyTraceGrid.displayTitle = 1;
		    LPSubmitApplyTraceGrid.canSel = 1;      //是否出现RadioBox
		    LPSubmitApplyTraceGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		    LPSubmitApplyTraceGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		    LPSubmitApplyTraceGrid.selBoxEventFuncName = "ShowDealIdea"; //单击RadioBox时响应函数
		    LPSubmitApplyTraceGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }

</script>
