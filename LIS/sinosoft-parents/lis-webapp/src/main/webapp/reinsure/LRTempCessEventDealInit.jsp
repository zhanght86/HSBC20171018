<%@include file="/i18n/language.jsp"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  

<script type="text/javascript">

// 输入框的初始化（单记录部分）
function initInpBox(){
	try{ 
  }
  catch(ex){
    myAlert("在ReInsureInit.jsp-->"+"initInpBox函数中发生异常:初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox(){
  try{ 
  }
  catch(ex){
    myAlert("在ReInsureInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
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
    myAlert("ReInsureInit.jsp-->"+"初始化界面错误!");
  }
}


// 责任信息列表的初始化
function initTempEventGrid()
{
    var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          				//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="事件号";    				//列名
      iArray[1][1]="190px";            	//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="团体保单号";        //列名
      iArray[2][1]="140px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="险种编码";         	//列名
      iArray[3][1]="70px";            	//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
      iArray[4]=new Array();
      iArray[4][0]="责任编码";         	//列名
      iArray[4][1]="70px";            	//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="被保人姓名";         		  //列名
      iArray[5][1]="80px";            	//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="事件类型";         	//列名
      iArray[6][1]="60px";            	//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="事件类型编码";      //列名
      iArray[7][1]="80px";            	//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="被保人编码";      	//列名
      iArray[8][1]="100px";            	//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="合同号";      			//列名
      iArray[9][1]="120px";            	//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      TempEventGrid = new MulLineEnter("fm","TempEventGrid"); 
      //这些属性必须在loadMulLine前
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
	      iArray[0][0]="序号";         			//列名
	      iArray[0][1]="30px";         			//列宽
	      iArray[0][2]=10;          				//列最大值
	      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[1]=new Array();
	      iArray[1][0]="临分合同号";    		//列名
	      iArray[1][1]="150px";            	//列宽
	      iArray[1][2]=200;            			//列最大值
	      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[2]=new Array();
	      iArray[2][0]="临分方案号";        //列名
	      iArray[2][1]="150px";            	//列宽
	      iArray[2][2]=200;            			//列最大值
	      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      TempCessPreceptGrid = new MulLineEnter("fm","TempCessPreceptGrid"); 
	      //这些属性必须在loadMulLine前
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

// 责任信息列表的初始化
function initTempCessInputGrid(){
    var iArray = new Array();
    
    try{
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          				//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
      iArray[1]=new Array();
			iArray[1][0]="公司代码";
			iArray[1][1]="120px";  
			iArray[1][2]=100;  
			iArray[1][3]=2;    
			iArray[1][4]="lrcompany"; 
			iArray[1][5]="1|2"; 	 						//将引用代码分别放在第1、2
			iArray[1][6]="0|1";									
			iArray[1][9]="公司代码|notnull";    
			iArray[1][19]=1; 
			
			iArray[2]=new Array();
			iArray[2][0]="公司名称";
			iArray[2][1]="100px";  
			iArray[2][2]=100;     
			iArray[2][3]=1;      
			
			iArray[3]=new Array();
			iArray[3][0]="临分方案号";
			iArray[3][1]="130px";  
			iArray[3][2]=100;     
			iArray[3][3]=3;      
			//iArray[3][4]="lrtempprecept";
	  	//iArray[3][5]="3"; 	 
	    //iArray[3][6]="0";	 
			//iArray[1][9]="临分方案号|notnull";    
			
      iArray[4]=new Array();
      iArray[4][0]="分保保额变量";      //列名
      iArray[4][1]="80px";            	//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
			
      iArray[5]=new Array();
      iArray[5][0]="分保保费变量";      //列名
      iArray[5][1]="80px";            	//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="分保佣金变量";      //列名
      iArray[6][1]="80px";            	//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="当前风险保额";      //列名
      iArray[7][1]="80px";            	//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="分出保额";      		//列名
      iArray[8][1]="80px";            	//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="分保比例";      		//列名
      iArray[9][1]="100px";            	//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="理赔摊回";      		//列名
      iArray[10][1]="100px";            //列宽
      iArray[10][2]=100;            		//列最大值
      iArray[10][3]=1;              		//是否允许输入,1表示允许，0表示不允许

      TempCessInputGrid = new MulLineEnter("fm","TempCessInputGrid"); 
      //这些属性必须在loadMulLine前
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
