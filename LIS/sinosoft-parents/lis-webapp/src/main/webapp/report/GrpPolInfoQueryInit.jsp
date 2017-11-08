<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
function initInpBox(){
  try{
    fm.ManageCom.value = comcode;
    fm.sManageCom.value = "";
    //fm.sAgentCode.value = "";
    fm.StartDate.value = "";
    fm.EndDate.value = "";
    fm.sSaleChnl.value = "";
    fm.sAgentType.value = "";
    fm.sRiskType.value = "";
    fm.sRiskCode.value = "";
    fm.sBlacklist.value = "";
    fm.sInsuranceMoney.value = ""; 
    fm.sql.value = ""; 
  }
  catch(ex){
    alert("在GrpPolInfoQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}
function initForm(){
  try{
    initInpBox();
	initPolInfoGrid();
  }
  catch(re){
    alert("GrpPolInfoQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initPolInfoGrid(){
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
      	iArray[2][1]="80px";            		//列宽
      	iArray[2][2]=60;            			//列最大值
      	iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[2][21]=2; 

	  	//销售渠道
      	iArray[3]=new Array();
      	iArray[3][0]="销售渠道";         			//列名
      	iArray[3][1]="55px";            		//列宽
      	iArray[3][2]=60;            			//列最大值
      	iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许,3表示下拉
      	iArray[3][4]="SaleChnl";				//下拉选择值      	
      	
      
      	//二级渠道分类
      	iArray[4]=new Array();
      	iArray[4][0]="二级渠道";         			//列名
      	iArray[4][1]="55px";            		//列宽
      	iArray[4][2]=0;            				//列最大值
      	iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许,3表示下拉
      	iArray[4][4]="AgentType";				//下拉选择值      	

  	  	//团体合同号
	  	iArray[5]=new Array();
      	iArray[5][0]="团体合同号";         		//列名
      	iArray[5][1]="130px";            		//列宽
      	iArray[5][2]=100;            			//列最大值
      	iArray[5][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[5][21]=2; 
      
      	//投保单号
	  	iArray[6]=new Array();
      	iArray[6][0]="投保单号";         			//列名
      	iArray[6][1]="130px";            		//列宽
      	iArray[6][2]=100;            			//列最大值
      	iArray[6][3]=0; 						//是否允许输入,1表示允许，0表示不允许  
      	iArray[6][21]=2;     
 
   	   //投保单位名称
   	 	iArray[7]=new Array();
      	iArray[7][0]="投保单位名称";         			//列名
      	iArray[7][1]="120px";            		//列宽
      	iArray[7][2]=100;            			//列最大值
      	iArray[7][3]=0; 						//是否允许输入,1表示允许，0表示不允许     
      
   	 
   	 
   	 
   	  	//险种代码
 	  	iArray[8]=new Array();
      	iArray[8][0]="险种代码";         			//列名
      	iArray[8][1]="100px";            		//列宽
      	iArray[8][2]=100;            			//列最大值
      	iArray[8][3]=0; 						//是否允许输入,1表示允许，0表示不允许 
      	iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许,3表示下拉
      	iArray[8][4]="RiskCode";				//下拉选择值      	
      	
      	//总保额
      	iArray[9]=new Array();
      	iArray[9][0]="总保额";         			//列名
      	iArray[9][1]="90px";            		//列宽
      	iArray[9][2]=100;            			//列最大值
      	iArray[9][3]=0; 			
      
      	//总保费
      	iArray[10]=new Array();
      	iArray[10][0]="总保费";         			//列名
      	iArray[10][1]="80px";            		//列宽
      	iArray[10][2]=100;            			//列最大值
      	iArray[10][3]=0; 						//是否允许输入,1表示允许，0表示不允许     
      
      	//被保险人人数
      	iArray[11]=new Array();
      	iArray[11][0]="被保险人人数";         		//列名
      	iArray[11][1]="85px";            		//列宽
      	iArray[11][2]=100;            			//列最大值
      	iArray[11][3]=0; 						//是否允许输入,1表示允许，0表示不允许     
      
      	//生效日
      	iArray[12]=new Array();
      	iArray[12][0]="生效日";         			//列名
      	iArray[12][1]="80px";            		//列宽
      	iArray[12][2]=100;            			//列最大值
      	iArray[12][3]=0; 						//是否允许输入,1表示允许，0表示不允许 
      	iArray[12][21]=2;     
       
      	//签单日
      	iArray[13]=new Array();
      	iArray[13][0]="签单日";         			//列名
      	iArray[13][1]="80px";            		//列宽
      	iArray[13][2]=100;            			//列最大值
      	iArray[13][3]=0; 						//是否允许输入,1表示允许，0表示不允许 
      	iArray[13][21]=2;     
      
      	//代理人编码
      	iArray[14]=new Array();
      	iArray[14][0]="代理人编码";         		//列名
      	iArray[14][1]="85px";            		//列宽
      	iArray[14][2]=100;            			//列最大值
      	iArray[14][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	//iArray[13][3]=2;              		//是否允许输入,1表示允许，0表示不允许,3表示下拉
      	//iArray[13][4]="AgentCode";			//下拉选择值  
      	iArray[14][21]=2;    
      
      	//代理人姓名
      	iArray[15]=new Array();
      	iArray[15][0]="代理人姓名";         		//列名
      	iArray[15][1]="85px";            		//列宽
      	iArray[15][2]=100;            			//列最大值
      	iArray[15][3]=0; 						//是否允许输入,1表示允许，0表示不允许  
      
      	//险种类别
      	iArray[16]=new Array();
      	iArray[16][0]="险种类别";         		//列名
      	iArray[16][1]="55px";            		//列宽
      	iArray[16][2]=100;            			//列最大值
      	iArray[16][3]=2; 						//是否允许输入,1表示允许，0表示不允许  
      	iArray[16][4]="RiskType";
      
      	//黑名单标记
      	iArray[17]=new Array();
      	iArray[17][0]="黑名单标记";         		//列名
      	iArray[17][1]="80px";            		//列宽
      	iArray[17][2]=100;            			//列最大值
      	iArray[17][3]=2; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[17][4]="Blacklist";       	
      	
		
		PolInfoGrid = new MulLineEnter( "document" , "PolInfoGrid" );
		//这些属性必须在loadMulLine前
		PolInfoGrid.mulLineCount = 5;
		PolInfoGrid.displayTitle = 1;
		PolInfoGrid.hiddenPlus = 1;
		PolInfoGrid.hiddenSubtraction = 1;
		PolInfoGrid.canSel = 1;
		PolInfoGrid.canChk = 1;
		PolInfoGrid.locked = 1;
		PolInfoGrid.canChk = 0;
		PolInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
