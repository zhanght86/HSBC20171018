<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
function initInpBox(){
  try{
    fm.ManageCom.value = comcode;
    fm.sManageComName.value = "";    
    fm.StartDate.value = "";
    fm.EndDate.value = "";
    fm.ContPlanCode.value = "";    
  }
  catch(ex){
    alert("GrpPlanInfoQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}
function initForm(){
  try{
    initInpBox();
	initPlanInfoGrid();
  }
  catch(re){
    alert("GrpPlanInfoQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initPlanInfoGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
      	iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      	iArray[0][1]="35px";            		//列宽
    	iArray[0][2]=10;            			//列最大值
      	iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
	  	//管理机构
      	iArray[1]=new Array();
      	iArray[1][0]="管理机构";         			//列名
      	iArray[1][1]="150px";            		//列宽
      	iArray[1][2]=60;            			//列最大值
      	iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
   	  	//管理机构代码
      	iArray[2]=new Array();
      	iArray[2][0]="管理机构代码";         		//列名
      	iArray[2][1]="100px";            		//列宽
      	iArray[2][2]=60;            			//列最大值
      	iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[2][21]=2; 

	  	//团体合同号
      	iArray[3]=new Array();
      	iArray[3][0]="团体合同号";         		//列名
      	iArray[3][1]="150px";            		//列宽
      	iArray[3][2]=60;            			//列最大值
      	iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[3][21]=2; 
      
      	//投保单号
      	iArray[4]=new Array();
      	iArray[4][0]="投保单号";         			//列名
      	iArray[4][1]="150px";            		//列宽
      	iArray[4][2]=0;            				//列最大值
      	iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[4][21]=2;

  	  	//被保人姓名
	  	iArray[5]=new Array();
      	iArray[5][0]="被保人姓名";         		//列名
      	iArray[5][1]="80px";            		//列宽
      	iArray[5][2]=100;            			//列最大值
      	iArray[5][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      
      	//性别
	  	iArray[6]=new Array();
      	iArray[6][0]="性别";         			//列名
      	iArray[6][1]="35px";            		//列宽
      	iArray[6][2]=100;            			//列最大值
      	iArray[6][3]=2; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[6][4]="sex";         	
 
   	  	//出生日期
 	  	iArray[7]=new Array();
      	iArray[7][0]="出生日期";         			//列名
      	iArray[7][1]="80px";            		//列宽
      	iArray[7][2]=100;            			//列最大值
      	iArray[7][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[7][21]=2; 
      	//iArray[7][10]="RateType";
      	//iArray[7][11]="0|^D|日利率^M|月利率^Y|年利率";  
      
      	//套餐计划编码
      	iArray[8]=new Array();
      	iArray[8][0]="套餐计划编码";         		//列名
      	iArray[8][1]="80px";            		//列宽
      	iArray[8][2]=100;            			//列最大值
      	iArray[8][3]=2; 
      	iArray[8][4]="ContPlanCode";		      
      
      	//保费
      	iArray[9]=new Array();
      	iArray[9][0]="保费";         			//列名
      	iArray[9][1]="80px";            		//列宽
      	iArray[9][2]=100;            			//列最大值
      	iArray[9][3]=0; 						//是否允许输入,1表示允许，0表示不允许 
      	    
      
      	//份数
      	iArray[10]=new Array();
      	iArray[10][0]="份数";         			//列名
      	iArray[10][1]="55px";            		//列宽
      	iArray[10][2]=100;            			//列最大值
      	iArray[10][3]=0; 						//是否允许输入,1表示允许，0表示不允许     
       
      	//生效日期
      	iArray[11]=new Array();
      	iArray[11][0]="生效日期";         		//列名
      	iArray[11][1]="80px";            		//列宽
      	iArray[11][2]=100;            			//列最大值
      	iArray[11][3]=0; 						//是否允许输入,1表示允许，0表示不允许 
      	iArray[11][21]=2;    
      
      	//签单日期
      	iArray[12]=new Array();
      	iArray[12][0]="签单日期";         		//列名
      	iArray[12][1]="80px";            		//列宽
      	iArray[12][2]=100;            			//列最大值
      	iArray[12][3]=0; 						//是否允许输入,1表示允许，0表示不允许 
      	iArray[12][21]=2;    
      	      	 
		PlanInfoGrid = new MulLineEnter( "document" , "PlanInfoGrid" );
		//这些属性必须在loadMulLine前
		PlanInfoGrid.mulLineCount = 5;
		PlanInfoGrid.displayTitle = 1;
		PlanInfoGrid.hiddenPlus = 1;
		PlanInfoGrid.hiddenSubtraction = 1;
		PlanInfoGrid.canSel = 1;
		PlanInfoGrid.canChk = 1;
		PlanInfoGrid.locked = 1;
		PlanInfoGrid.canChk = 0;
		PlanInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
