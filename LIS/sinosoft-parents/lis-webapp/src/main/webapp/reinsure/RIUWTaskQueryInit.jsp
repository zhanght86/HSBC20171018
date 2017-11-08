<%@include file="/i18n/language.jsp"%>
<%
//程序名称：RIUWTaskQueryInit.jsp
//程序功能：再保审核任务查询功能
//创建日期：2008-10-20
//创建人  ：caoshuguo
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
	String tContType = request.getParameter("ContType"); 
	String tAuditType = request.getParameter("AuditType"); 
%> 
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">

function initInpBox(){ 
  try{
	//针对险种或针对责任转换标志
    fm.DeTailFlag.value = "1"; //1-到险种 2-到责任
	//ContType   1-个险 2-团险
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
    myAlert("RIUWTaskQueryInit.jsp-->"+"initInpBox函数中发生异常:初始化界面错误!");
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
	initTaskLiskGrid();     //任务列表
	initAuditInfoGrid();    //核保/核赔结果列表
	if(fm.ContType.value=='1'){ //个险	
		initRiskInfoGrid();     //任务信息
	}       
	initReInsureAuditGrid();    //再保审核任务

  }catch(re){
    myAlert("RIUWTaskQueryInit.jsp-->"+"initForm函数中发生异常:初始化界面错误!");
  }
}

//任务列表
function initTaskLiskGrid(){
    var iArray = new Array();
    try{
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="印刷号";    			
      iArray[1][1]="80px";            	
      iArray[1][2]=100;            			
      iArray[1][3]=0;              			
	
      iArray[2]=new Array();
      iArray[2][0]="投保单号";         	
      iArray[2][1]="80px";            	
      iArray[2][2]=100;
      if(fm.AuditType.value=='04'){
      	iArray[2][3]=3; 
      }else{           			
      	iArray[2][3]=0;              			
      }
      iArray[3]=new Array();
      iArray[3][0]="任务状态";         	
      iArray[3][1]="80px";            	
      iArray[3][2]=100;            			
      iArray[3][3]=0;              			
      
      iArray[4]=new Array();
      iArray[4][0]="发送次数";         	
      iArray[4][1]="80px";            	
      iArray[4][2]=100;            			
      iArray[4][3]=0;              			
      
      if(fm.AuditType.value=='03'){
      	iArray[5]=new Array();
      	iArray[5][0]="保全批次号";         
      	iArray[5][1]="80px";            
      	iArray[5][2]=100;            		
      	iArray[5][3]=0;              			
      	
      	iArray[6]=new Array();
      	iArray[6][0]="保全类型";        
      	iArray[6][1]="80px";            
      	iArray[6][2]=100;            		
      	iArray[6][3]=0;              			
      }
      
      if(fm.AuditType.value=='04'){
      	iArray[5]=new Array();
      	iArray[5][0]="理赔号";         	
      	iArray[5][1]="80px";           
      	iArray[5][2]=100;            	
      	iArray[5][3]=0;              	
      }
      
      TaskLiskGrid = new MulLineEnter( "fm" , "TaskLiskGrid" ); 
      //这些属性必须在loadMulLine前
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

//核保/核赔结果列表
function initAuditInfoGrid(){
	var iArray = new Array();
    
    try{
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[1]=new Array(); 
	  iArray[1][0]="被保人客户号"; 			
	  iArray[1][1]="50px";            	
	  iArray[1][2]=100;            			
	  iArray[1][3]=0; 

      iArray[2]=new Array();
      iArray[2][0]="被保人姓名";    			
      iArray[2][1]="80px";            	
      iArray[2][2]=100;            			
      iArray[2][3]=0;              			

      iArray[3]=new Array();
      iArray[3][0]="险种代码";         	
      iArray[3][1]="80px";            	
      iArray[3][2]=100;            			
      iArray[3][3]=0;              			
      
      iArray[4]=new Array();
      iArray[4][0]="责任编码";         	    //列名
      iArray[4][1]="0px";            	    //列宽
      iArray[4][2]=100;            			//列最大值
      if(fm.DeTailFlag.value=='1'){
      	iArray[4][3]=3;              		//是否允许输入,1表示允许，0表示不允许
      }else{                                //2表示代码选择，3表示这一列是隐藏的
      	iArray[4][3]=0;              			
      }	
		
      iArray[5]=new Array();
      iArray[5][0]="险种名称";         			
      iArray[5][1]="60px";            	
      iArray[5][2]=100;            			
      iArray[5][3]=0; 
             			     
      iArray[6]=new Array();
      iArray[6][0]="自核结论";         		
      iArray[6][1]="60px";            	
      iArray[6][2]=100;            			
      iArray[6][3]=0; 
      
      AuditInfoGrid = new MulLineEnter( "fm" , "AuditInfoGrid" ); 
      //这些属性必须在loadMulLine前
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

// 任务信息
function initRiskInfoGrid(){                               
    var iArray = new Array();
    
    try{
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="被保人客户号";    			
      iArray[1][1]="80px";            	
      iArray[1][2]=100;            		
      iArray[1][3]=0;              			

 	  iArray[2]=new Array();
      iArray[2][0]="被保险人";    		
      iArray[2][1]="80px";          
      iArray[2][2]=100;            		
      iArray[2][3]=0; 
      
      iArray[3]=new Array();
      iArray[3][0]="险种编码";         
      iArray[3][1]="60px";            	
      iArray[3][2]=100;            		
      iArray[3][3]=0;              		

      
      iArray[4]=new Array();
      iArray[4][0]="责任编码";         	//列名
      iArray[4][1]="60px";            	//列宽
      iArray[4][2]=100;            			//列最大值
      if(fm.DeTailFlag.value=='1'){  //1-到险种，
      	iArray[4][3]=3;              		//是否允许输入,1表示允许，0表示不允许
      }else{  													//2-到责任
      	iArray[4][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      }  
          
      iArray[5]=new Array();
      iArray[5][0]="保額";         			//列名
      iArray[5][1]="70px";            	//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      if(fm.AuditType.value=='01'){
      	iArray[6]=new Array();
      	iArray[6][0]="风险保额";        //列名
      	iArray[6][1]="70px";            //列宽
      	iArray[6][2]=100;            		//列最大值
      	iArray[6][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      	
      	iArray[7]=new Array();
      	iArray[7][0]="累计风险保额"; 		//列名
      	iArray[7][1]="70px";            	//列宽
      	iArray[7][2]=100;            		//列最大值
      	iArray[7][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      }
    	
      if(fm.AuditType.value=='03'){
      	iArray[6]=new Array();
      	iArray[6][0]="保全批次号";      //列名
      	iArray[6][1]="80px";            //列宽
      	iArray[6][2]=100;            		//列最大值
      	iArray[6][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      	
      	iArray[7]=new Array();
      	iArray[7][0]="保全类型"; 				//列名
      	iArray[7][1]="60px";            //列宽
      	iArray[7][2]=100;            		//列最大值
      	iArray[7][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      }
      if(fm.AuditType.value=='04'){
      	iArray[6]=new Array();
      	iArray[6][0]="理赔号";       		//列名
      	iArray[6][1]="90px";            //列宽
      	iArray[6][2]=100;            		//列最大值
      	iArray[6][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      	
      	iArray[7]=new Array();
      	iArray[7][0]="理赔金额";     		//列名
      	iArray[7][1]="60px";            //列宽
      	iArray[7][2]=100;            		//列最大值
      	iArray[7][3]=0;  
      	
      	iArray[8]=new Array();
	    iArray[8][0]="核赔结论";        	//列名
	    iArray[8][1]="50px";          	 	//列宽
	    iArray[8][2]=100;           		 	//列最大值
	    iArray[8][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      }else{
        iArray[8]=new Array();
	    iArray[8][0]="核保结论";        	//列名
	    iArray[8][1]="50px";          	 	//列宽
	    iArray[8][2]=100;           		 	//列最大值
	    iArray[8][3]=0 ;
      }
      
      
	    
      RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
      //这些属性必须在loadMulLine前
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
    	iArray[0][0]="序号";
    	iArray[0][1]="30px";
    	iArray[0][2]=10;
    	iArray[0][3]=0;
    	
    	iArray[1]=new Array();
    	iArray[1][0]="总单投保单号码";
    	iArray[1][1]="100px";
    	iArray[1][2]=300;
    	if(fm.ContType.value=='2'){
    	  iArray[1][3]=0;
    	}else{
    	  iArray[1][3]=3;
    	}
    	
    	iArray[2]=new Array();
    	iArray[2][0]="审核次序号";
    	iArray[2][1]="60px";
    	iArray[2][2]=100;
    	iArray[2][3]=0;
    	
    	iArray[3]=new Array();
    	iArray[3][0]="发送人";
    	iArray[3][1]="60px";
    	iArray[3][2]=100;
    	iArray[3][3]=0;
    	
    	iArray[4]=new Array();
    	iArray[4][0]="发送时间";         	
    	iArray[4][1]="100px";            	
    	iArray[4][2]=300;            			
    	iArray[4][3]=0;
    	
    	iArray[5]=new Array();
    	iArray[5][0]="上载路径";         	
    	iArray[5][1]="150px";            	
    	iArray[5][2]=300;            			
    	iArray[5][3]=0              			
    	       
    	iArray[6]=new Array();
    	iArray[6][0]="发送/回复标志";
    	iArray[6][1]="80px";
    	iArray[6][2]=100;
    	iArray[6][3]=0;
    	
    	iArray[7]=new Array();
    	iArray[7][0]="任务状态";
    	iArray[7][1]="80px";
    	iArray[7][2]=100;
    	iArray[7][3]=0;
    	
    	iArray[8]=new Array();
    	iArray[8][0]="发送回复信息";
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

