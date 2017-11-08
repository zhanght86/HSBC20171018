<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
function initInpBox(){
  try{
    fm.ManageCom.value = comcode;
    fm.sManageComName.value = "";    
    fm.StartDate.value = "";
    fm.EndDate.value = "";
  }
  catch(ex){
    alert("在GrpSumInfoQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}
function initForm(){
  try{
    initInpBox();
	initSumInfoGrid();
  }
  catch(re){
    alert("GrpSumInfoQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initSumInfoGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
      	iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      	iArray[0][1]="35px";            		//列宽
    	iArray[0][2]=10;            			//列最大值
      	iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
	  	//险种代码
      	iArray[1]=new Array();
      	iArray[1][0]="险种代码";         			//列名
      	iArray[1][1]="85px";            		//列宽
      	iArray[1][2]=60;            			//列最大值
      	iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[1][4]="riskcode"; 
      	      
   	  	//管理机构代码
      	iArray[2]=new Array();
      	iArray[2][0]="管理机构代码";         		//列名
      	iArray[2][1]="85px";            		//列宽
      	iArray[2][2]=60;            			//列最大值
      	iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[2][21]=2; 

	  	//管理机构名称
      	iArray[3]=new Array();
      	iArray[3][0]="管理机构名称";         		//列名
      	iArray[3][1]="180px";            		//列宽
      	iArray[3][2]=60;            			//列最大值
      	iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      	//出单件数
      	iArray[4]=new Array();
      	iArray[4][0]="出单件数";         			//列名
      	iArray[4][1]="50px";            		//列宽
      	iArray[4][2]=0;            				//列最大值
      	iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  	  	//承保人数
	  	iArray[5]=new Array();
      	iArray[5][0]="承保人数";         			//列名
      	iArray[5][1]="50px";            		//列宽
      	iArray[5][2]=100;            			//列最大值
      	iArray[5][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      
      	//总保额
	  	iArray[6]=new Array();
      	iArray[6][0]="总保额";         			//列名
      	iArray[6][1]="65px";            		//列宽
      	iArray[6][2]=100;            			//列最大值
      	iArray[6][3]=0; 						//是否允许输入,1表示允许，0表示不允许      
 
   	  	//总保费
 	  	iArray[7]=new Array();
      	iArray[7][0]="总保费";         			//列名
      	iArray[7][1]="65px";            		//列宽
      	iArray[7][2]=100;            			//列最大值
      	iArray[7][3]=0; 						//是否允许输入,1表示允许，0表示不允许      	     
      	 
      	
		SumInfoGrid = new MulLineEnter( "document" , "SumInfoGrid" );
		//这些属性必须在loadMulLine前
		SumInfoGrid.mulLineCount = 5;
		SumInfoGrid.displayTitle = 1;
		SumInfoGrid.hiddenPlus = 1;
		SumInfoGrid.hiddenSubtraction = 1;
		SumInfoGrid.canSel = 1;
		SumInfoGrid.canChk = 1;
		SumInfoGrid.locked = 1;
		SumInfoGrid.canChk = 0;
		SumInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
