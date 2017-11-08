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
    alert("在GrpIssueInfoQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}
function initForm(){
  try{
    initInpBox();
	initIssueInfoGrid();
  }
  catch(re){
    alert("GrpIssueInfoQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initIssueInfoGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
      	iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      	iArray[0][1]="35px";            		//列宽
    	iArray[0][2]=10;            			//列最大值
      	iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许      	
		
	  	//管理机构代码
      	iArray[1]=new Array();
      	iArray[1][0]="管理机构代码";         			//列名
      	iArray[1][1]="80px";            		//列宽
      	iArray[1][2]=60;            			//列最大值
      	iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[1][4]="station";      	
      
   	  	//团体合同号
      	iArray[2]=new Array();
      	iArray[2][0]="团体合同号";         		//列名
      	iArray[2][1]="130px";            		//列宽
      	iArray[2][2]=60;            			//列最大值
      	iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许	  	
      	iArray[2][21]=2;
      
      	//投保单号
      	iArray[3]=new Array();
      	iArray[3][0]="投保单号";         			//列名
      	iArray[3][1]="130px";            		//列宽
      	iArray[3][2]=0;            				//列最大值
      	iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	iArray[3][21]=2;

  	  	//问题件类型
	  	iArray[4]=new Array();
      	iArray[4][0]="问题件类型";         		//列名
      	iArray[4][1]="65px";            		//列宽
      	iArray[4][2]=100;            			//列最大值
      	iArray[4][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[4][21]=2;
      
      	//问题件内容
	  	iArray[5]=new Array();
      	iArray[5][0]="问题件内容";         		//列名
      	iArray[5][1]="300px";            		//列宽
      	iArray[5][2]=100;            			//列最大值
      	iArray[5][3]=0; 						//是否允许输入,1表示允许，0表示不允许      
 
   	  	//录入人
 	  	iArray[6]=new Array();
      	iArray[6][0]="录入人员";         			//列名
      	iArray[6][1]="85px";            		//列宽
      	iArray[6][2]=100;            			//列最大值
      	iArray[6][3]=2; 						//是否允许输入,1表示允许，0表示不允许  
      	iArray[6][4]="UserCode"; 				//是否允许输入,1表示允许，0表示不允许  
      	iArray[6][21]=2;     	
      
      	//复核人员
      	iArray[7]=new Array();
      	iArray[7][0]="复核人员";         			//列名
      	iArray[7][1]="85px";            		//列宽
      	iArray[7][2]=100;            			//列最大值
      	iArray[7][3]=2;
      	iArray[7][4]="UserCode"; 				//是否允许输入,1表示允许，0表示不允许  
      	iArray[7][21]=2; 			
      
      	//录入日期
      	iArray[8]=new Array();
      	iArray[8][0]="录入日期";         			//列名
      	iArray[8][1]="80px";            		//列宽
      	iArray[8][2]=100;            			//列最大值
      	iArray[8][3]=0; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[8][21]=2;     
      
      	//复核日期
      	iArray[9]=new Array();
      	iArray[9][0]="复核日期";         		//列名
      	iArray[9][1]="80px";            		//列宽
      	iArray[9][2]=100;            			//列最大值
      	iArray[9][3]=0; 						//是否允许输入,1表示允许，0表示不允许  
      	iArray[9][21]=2;   
      
      	//签单日期
      	iArray[10]=new Array();
      	iArray[10][0]="签单日期";         		//列名
      	iArray[10][1]="80px";            		//列宽
      	iArray[10][2]=100;            			//列最大值
      	iArray[10][3]=0; 						//是否允许输入,1表示允许，0表示不允许 
      	iArray[10][21]=2;    
       
      	//操作位置
      	iArray[11]=new Array();
      	iArray[11][0]="操作位置";         		//列名
      	iArray[11][1]="65px";            		//列宽
      	iArray[11][2]=100;            			//列最大值
      	iArray[11][3]=2; 						//是否允许输入,1表示允许，0表示不允许    
      	iArray[11][4]="BackObj"; 				//是否允许输入,1表示允许，0表示不允许  	
      	iArray[11][21]=1;   			
      
      	//返回对象
      	iArray[12]=new Array();
      	iArray[12][0]="返回对象";         		//列名
      	iArray[12][1]="85px";            		//列宽
      	iArray[12][2]=100;            			//列最大值
      	iArray[12][3]=2; 						//是否允许输入,1表示允许，0表示不允许
      	iArray[12][4]="UserCode"; 				//是否允许输入,1表示允许，0表示不允许
      	iArray[12][21]=2;
      	
		IssueInfoGrid = new MulLineEnter( "document" , "IssueInfoGrid" );
		//这些属性必须在loadMulLine前
		IssueInfoGrid.mulLineCount = 5;
		IssueInfoGrid.displayTitle = 1;
		IssueInfoGrid.hiddenPlus = 1;
		IssueInfoGrid.hiddenSubtraction = 1;
		IssueInfoGrid.canSel = 1;
		IssueInfoGrid.canChk = 1;
		IssueInfoGrid.locked = 1;
		IssueInfoGrid.canChk = 0;
		IssueInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
