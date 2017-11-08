<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
function initInpBox(){
  try{
    fm.ManageCom.value = comcode;
    fm.sManageCom.value = "";   
    fm.StartDate.value = "";
    fm.EndDate.value = "";
    fm.GrpContNo.value = "";
    fm.GrpName.value = "";
    fm.sRiskCode.value = "";
    fm.sBlacklist.value = "";
  }
  catch(ex){
    alert("GrpPlanInfoQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}
function initForm(){
  try{
    initInpBox();
	initClaimInfoGrid();
  }
  catch(re){
    alert("GrpPlanInfoQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initClaimInfoGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
      	iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      	iArray[0][1]="35px";            		//列宽
    	iArray[0][2]=10;            			//列最大值
      	iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
	  	//管理机构代码
      	iArray[1]=new Array();
      	iArray[1][0]="管理机构代码";         		//列名
      	iArray[1][1]="80px";            		//列宽
      	iArray[1][2]=60;            			//列最大值
      	iArray[1][21]=2;       	
      
   	  	//管理机构名称
      	iArray[2]=new Array();
      	iArray[2][0]="管理机构名称";         		//列名
      	iArray[2][1]="150px";            		//列宽
      	iArray[2][2]=60;            			//列最大值
      	iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  	//团单合同号
      	iArray[3]=new Array();
      	iArray[3][0]="团单合同号";         		//列名
      	iArray[3][1]="130px";            		//列宽
      	iArray[3][2]=60;            			//列最大值
      	iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[3][21]=2;
      
      	//险种代码
      	iArray[4]=new Array();
      	iArray[4][0]="险种代码";         			//列名
      	iArray[4][1]="80px";            		//列宽
      	iArray[4][2]=0;            				//列最大值
      	iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[4][21]=2;

  	  	//险种名称
	  	iArray[5]=new Array();
      	iArray[5][0]="险种名称";         			//列名
      	iArray[5][1]="300px";            		//列宽
      	iArray[5][2]=100;            			//列最大值
      	iArray[5][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      
      	//被保人人数
	  	iArray[6]=new Array();
      	iArray[6][0]="被保人人数";         		//列名
      	iArray[6][1]="85px";            		//列宽
      	iArray[6][2]=100;            			//列最大值
      	iArray[6][3]=0; 						//是否允许输入,1表示允许，0表示不允许 
      	iArray[6][21]=2;     
 
   	  	//投保单位代码
 	  	iArray[7]=new Array();
      	iArray[7][0]="投保单位代码";         		//列名
      	iArray[7][1]="85px";            		//列宽
      	iArray[7][2]=100;            			//列最大值
      	iArray[7][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[7][21]=2;
      	
      	//投保单位名称
 	  	iArray[8]=new Array();
      	iArray[8][0]="投保单位名称";         		//列名
      	iArray[8][1]="200px";            		//列宽
      	iArray[8][2]=100;            			//列最大值
      	iArray[8][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	
      	//行业类别名称
 	  	iArray[9]=new Array();
      	iArray[9][0]="行业类别名称";         		//列名
      	iArray[9][1]="150px";            		//列宽
      	iArray[9][2]=100;            			//列最大值
      	iArray[9][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	
      	//累计保费
 	  	iArray[10]=new Array();
      	iArray[10][0]="累计保费";         		//列名
      	iArray[10][1]="80px";            		//列宽
      	iArray[10][2]=100;            			//列最大值
      	iArray[10][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	
      	//累计赔付金额
 	  	iArray[11]=new Array();
      	iArray[11][0]="累计赔付金额";         		//列名
      	iArray[11][1]="80px";            		//列宽
      	iArray[11][2]=100;            			//列最大值
      	iArray[11][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	
      	//生效日期
 	  	iArray[12]=new Array();
      	iArray[12][0]="生效日期";         		//列名
      	iArray[12][1]="80px";            		//列宽
      	iArray[12][2]=100;            			//列最大值
      	iArray[12][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[12][21]=2;
      	
      	//投保单位黑名单标记
 	  	iArray[13]=new Array();
      	iArray[13][0]="投保单位黑名单标记";         //列名
      	iArray[13][1]="65px";            		//列宽
      	iArray[13][2]=100;            			//列最大值
      	iArray[13][3]=2; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[13][4]="BlacklistFlag";      	
      	
		ClaimInfoGrid = new MulLineEnter( "document" , "ClaimInfoGrid" );
		//这些属性必须在loadMulLine前
		ClaimInfoGrid.mulLineCount = 5;
		ClaimInfoGrid.displayTitle = 1;
		ClaimInfoGrid.hiddenPlus = 1;
		ClaimInfoGrid.hiddenSubtraction = 1;
		ClaimInfoGrid.canSel = 1;
		ClaimInfoGrid.canChk = 1;
		ClaimInfoGrid.locked = 1;
		ClaimInfoGrid.canChk = 0;
		ClaimInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
