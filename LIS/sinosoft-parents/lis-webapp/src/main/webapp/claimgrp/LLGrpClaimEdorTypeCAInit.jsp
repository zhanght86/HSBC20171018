
<script language="JavaScript">  

function initInpBox() 
{
	try
	{
	  
	 fm.all('GrpContNo').value = '<%= tGrpContNo %>';
	 fm.all('RptNo').value = '<%=tRptNo%>';
	}
	catch(ex)
	{
	  alert("在LLGrpClaimEdorTypeCAInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}                                     

function initForm()
{
	initInpBox();
	initLPAccMoveGrid();
	getTotalAcc();
	getAcc();
}


function initLPAccMoveGrid()
{
	var iArray = new Array();
	try
	{
	  iArray[0]=new Array();
	  iArray[0][0]="序号";
	  iArray[0][1]="30px";
	  iArray[0][2]=10;
	  iArray[0][3]=0;  
	  
	  iArray[1]=new Array();
	  iArray[1][0]="被保人客户号";
	  iArray[1][1]="80px";
	  iArray[1][2]=10;
	  iArray[1][3]=0;	  
	  
	  iArray[2]=new Array();
	  iArray[2][0]="被保人姓名";
	  iArray[2][1]="120px";
	  iArray[2][2]=10;
	  iArray[2][3]=0;	  
	  
	  iArray[3]=new Array();
	  iArray[3][0]="出生日期";
	  iArray[3][1]="80px";
	  iArray[3][2]=10;
	  iArray[3][3]=0;
	  
	  iArray[4]=new Array();
	  iArray[4][0]="客户性别";
	  iArray[4][1]="60px";
	  iArray[4][2]=10;
	  iArray[4][3]=0;	  
	  
	  iArray[5]=new Array();
	  iArray[5][0]="累计赔付金额";
	  iArray[5][1]="120px";
	  iArray[5][2]=10;
	  iArray[5][3]=0;	
	    
	  iArray[6]=new Array();
	  iArray[6][0]="转移金额";
	  iArray[6][1]="120px";
	  iArray[6][2]=10;
	  iArray[6][3]=1;
	  iArray[6][9]="转移金额|NOTNULL";
	  
	  iArray[7]=new Array();
	  iArray[7][0]="账户金额";
	  iArray[7][1]="120px";
	  iArray[7][2]=10;
	  iArray[7][3]=0;
	  
	  
	  iArray[8]=new Array();
	  iArray[8][0]="申请日期";
	  iArray[8][1]="80px";
	  iArray[8][2]=10;
	  iArray[8][3]=0;
	  
	  iArray[9]=new Array();
	  iArray[9][0]="合同号";
	  iArray[9][1]="50px";
	  iArray[9][2]=10;
	  iArray[9][3]=3;
	  
	  iArray[10]=new Array();
	  iArray[10][0]="帐户类型";
	  iArray[10][1]="120px";
	  iArray[10][2]=10;
	  iArray[10][3]=3;
	  
	  iArray[11]=new Array();
	  iArray[11][0]="交费计划编码";
	  iArray[11][1]="50px";
	  iArray[11][2]=10;
	  iArray[11][3]=3;
	  
	  
	  iArray[12]=new Array();
	  iArray[12][0]="险种号码";
	  iArray[12][1]="0px";
	  iArray[12][2]=10;
	  iArray[12][3]=3;
	  

	  
	  
	  LPAccMoveGrid=new MulLineEnter( "fm" , "LPAccMoveGrid" );
	  LPAccMoveGrid.hiddenPlus = 1;
	  LPAccMoveGrid.hiddenSubtraction = 1;
	  LPAccMoveGrid.canChk =1;
	  LPAccMoveGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{
	   alert("在LLGrpClaimEdorTypeCAInit.jsp-->initLPAccMoveGrid函数中发生异常:初始化界面错误!");
	}
}

</script>
