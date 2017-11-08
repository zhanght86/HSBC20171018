<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  

<script type="text/javascript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
	
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  { 
  }
  catch(ex)
  {
    myAlert("在ReInsureInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try{
	  initInpBox();
		initSelBox();
		initRiskInfoGrid();
		initPreceptGrid();
  }
  catch(re){
    myAlert("ReInsureInit.jsp-->"+"初始化界面错误!");
  }
}


// 责任信息列表的初始化
function initRiskInfoGrid()
{                               
    var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          				//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="被保险人";    			//列名
      iArray[1][1]="80px";            	//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种编码";         	//列名
      iArray[2][1]="80px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="责任编码";         	//列名
      iArray[3][1]="60px";            	//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
      iArray[4]=new Array();
      iArray[4][0]="保额";         			//列名
      iArray[4][1]="60px";            	//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="保费";         			//列名
      iArray[5][1]="60px";            	//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="险种编码";         		//列名
      iArray[6][1]="80px";            	//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="保单状态";         	//列名
      iArray[7][1]="80px";            	//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="保单状态编码";      //列名
      iArray[8][1]="80px";            	//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
     	
     	iArray[9]=new Array();
      iArray[9][0]="ProposalNo";        //列名
      iArray[9][1]="80px";            	//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="临分结论";         //列名
      iArray[10][1]="120px";            //列宽
      iArray[10][2]=100;            		//列最大值
      iArray[10][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="保单号码";       //列名
      iArray[11][1]="120px";            //列宽
      iArray[11][2]=100;            		//列最大值
      iArray[11][3]=3;              		//是否允许输入,1表示允许，0表示不允许
      
     	iArray[12]=new Array();
     	iArray[12][0]="被保险人编码";     //列名
     	iArray[12][1]="120px";            //列宽
     	iArray[12][2]=100;            		//列最大值
     	iArray[12][3]=3;              		//是否允许输入,1表示允许，0表示不允许

      RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
      //这些属性必须在loadMulLine前
      RiskInfoGrid.mulLineCount = 0;   
      RiskInfoGrid.displayTitle = 1;
      RiskInfoGrid.locked = 1;
      RiskInfoGrid.hiddenPlus = 1;
      RiskInfoGrid.canSel = 1;
      RiskInfoGrid.hiddenSubtraction = 1;
      RiskInfoGrid.selBoxEventFuncName = "showPrecept";
      RiskInfoGrid.loadMulLine(iArray);  
   }
   catch(ex){
     myAlert(ex);
   }
}

function initPreceptGrid(){
	var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         		//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="再保合同编码";    //列名
      iArray[1][1]="80px";            //列宽
      iArray[1][2]=100;            		//列最大值
      iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="再保方案编码";    //列名
      iArray[2][1]="80px";            //列宽
      iArray[2][2]=100;            		//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="累计方案编码";    //列名
      iArray[3][1]="80px";            //列宽
      iArray[3][2]=100;            		//列最大值
      iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="临分标志";    //列名
      iArray[4][1]="60px";            //列宽
      iArray[4][2]=100;            		//列最大值
      iArray[4][3]=3;              		//是否允许输入,1表示允许，0表示不允许

      PreceptGrid = new MulLineEnter( "fm" , "PreceptGrid" ); 
      //这些属性必须在loadMulLine前
      PreceptGrid.mulLineCount = 0;   
      PreceptGrid.displayTitle = 1;
      PreceptGrid.locked = 1;
      PreceptGrid.hiddenPlus = 1;
      PreceptGrid.canChk = 1; 
      //PreceptGrid.canSel = 1;
      PreceptGrid.hiddenSubtraction = 1;
      //PreceptGrid.selBoxEventFuncName = "";
      PreceptGrid.loadMulLine(iArray);  
   }
   catch(ex){
     myAlert(ex);
   }
}

</script>
