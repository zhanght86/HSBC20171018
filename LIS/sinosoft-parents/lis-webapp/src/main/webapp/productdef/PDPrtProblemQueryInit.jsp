<%@include file="../i18n/language.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">	

function initForm()
{
  try
  {
	  initRiskStateInfoGrid();
  }
  catch(re)
  {
      myAlert(""+"初始化界面错误!");
  }
}

//查询原通知书信息信息列表的初始化
function initRiskStateInfoGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="险种编码";         //列名
	  iArray[1][1]="60px";            	//列宽
	  iArray[1][2]=100;            		//列最大值
	  iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[2]=new Array();
	  iArray[2][0]="险种名称";       		//列名
	  iArray[2][1]="100px";            	//列宽
	  iArray[2][2]=100;            		//列最大值
	  iArray[2][3]=0; 
	
	  iArray[3]=new Array();
	  iArray[3][0]="问题件编码";       	//列名
	  iArray[3][1]="100px";            	//列宽
	  iArray[3][2]=100;            		//列最大值
	  iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	  iArray[4]=new Array();
	  iArray[4][0]="问题件内容";         	//列名
	  iArray[4][1]="120px";            	//列宽
	  iArray[4][2]=200;            		//列最大值
	  iArray[4][3]=0; 
	
	  iArray[5]=new Array();
	  iArray[5][0]="提出人";        		//列名
	  iArray[5][1]="50px";            	//列宽
	  iArray[5][2]=100;            		//列最大值
	  iArray[5][3]=0; 					//是否允许输入,1表示允许，0表示不允许
    	  
	  iArray[6]=new Array();
	  iArray[6][0]="处理人";        		//列名
	  iArray[6][1]="50px";            	//列宽
	  iArray[6][2]=200;            		//列最大值
	  iArray[6][3]=0; 					//是否允许输入,1表示允许，0表示不允许    	  
	  
	  iArray[7]=new Array();
	  iArray[7][0]="当前状态";        		//列名
	  iArray[7][1]="50px";            	//列宽
	  iArray[7][2]=200;            		//列最大值
	  iArray[7][3]=0; 					//是否允许输入,1表示允许，0表示不允许    	  

	  iArray[8]=new Array();
	  iArray[8][0]="提出时间";        		//列名
	  iArray[8][1]="60px";            	//列宽
	  iArray[8][2]=200;            		//列最大值
	  iArray[8][3]=0; 					//是否允许输入,1表示允许，0表示不允许    	  	  
	
	  RiskStateInfoGrid = new MulLineEnter( "fm" , "RiskStateInfoGrid" ); 
	  //这些属性必须在loadMulLine前
	  RiskStateInfoGrid.mulLineCount      = 0;   
	  RiskStateInfoGrid.displayTitle      = 1;
	  RiskStateInfoGrid.canSel            = 1;
      RiskStateInfoGrid.locked            = 1;
      RiskStateInfoGrid.hiddenPlus        = 1;
      RiskStateInfoGrid.hiddenSubtraction = 1;
	  RiskStateInfoGrid.loadMulLine(iArray);  	
	} 
	catch(ex) 
	{
		myAlert(ex);
	}
}

</script>