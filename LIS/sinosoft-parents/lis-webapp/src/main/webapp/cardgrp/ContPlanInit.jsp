
<%
//程序名称：GrpHealthFactoryQueryInit.jsp
//程序功能：
//创建日期：2004-08-30
//创建人  ：sunxy
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{
	try
	{
		fm.all('ContPlanCode').value = "";
		fm.all('ContPlanName').value = "";
		fm.all('RiskCode').value = "";
		fm.all('PlanSql').value = "";
		fm.all('MainRiskCode').value="";
	}
	catch(ex)
	{
		alert("在ContPlanInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initContPlanCodeGrid();
		initContPlanDutyGrid();
		initContPlanGrid();
		if(this.LoadFlag=="4"||this.LoadFlag=="16")
		{
			divRiskPlanSave.style.display="none";
		}
		if(this.LoadFlag=="11")
		{
		 divRiskPlanSave.style.display="none";
		 divRiskPlanRela.style.display="none";
		}
		if(this.LoadFlag=="18")
		{
		divRiskPlanSave.style.display="none";
		}
		easyQueryClick();
		initContPlan();
	}
	catch(re)
	{
		alert("ContPlanInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

var ContPlanGrid;




// 要约信息列表的初始化
function initContPlanGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种名称";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种编码";
		iArray[2][1]="0px";
		iArray[2][2]=200;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="责任编码";
		iArray[3][1]="0px";
		iArray[3][2]= 60;
		iArray[3][3]= 3;
		iArray[3][10]="DutyCode";

		iArray[4]=new Array();
		iArray[4][0]="险种责任";
		iArray[4][1]="180px";
		iArray[4][2]=60;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="计算要素";
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=3;
		iArray[5][10]="FactorCode";

		iArray[6]=new Array();
		iArray[6][0]="要素名称";
		iArray[6][1]="80px";
		iArray[6][2]=150;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="要素说明";
		iArray[7][1]="200px";
		iArray[7][2]=150;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="要素值";
		iArray[8][1]="50px";
		iArray[8][2]=150;
		iArray[8][3]=1;

		iArray[9]=new Array();
		iArray[9][0]="特别说明";
		iArray[9][1]="80px";
		iArray[9][2]=150;
		iArray[9][3]=1;

		iArray[10]=new Array();
		iArray[10][0]="险种版本";
		iArray[10][1]="0px";
		iArray[10][2]=10;
		iArray[10][3]=3;

		iArray[11]=new Array();
		iArray[11][0]="集体保单险种号码";
		iArray[11][1]="40px";
		iArray[11][2]=10;
		iArray[11][3]=3;

		iArray[12]=new Array();
		iArray[12][0]="主险编码";
		iArray[12][1]="0px";
		iArray[12][2]=10;
		iArray[12][3]=3;

		iArray[13]=new Array();
		iArray[13][0]="类型";
		iArray[13][1]="0px";
		iArray[13][2]=10;
		iArray[13][3]=3;

		iArray[14]=new Array();
		iArray[14][0]="计算方法";
		iArray[14][1]="0px";
		iArray[14][2]=10;
		iArray[14][3]=3;

		ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" );
		//这些属性必须在loadMulLine前
		ContPlanGrid.mulLineCount = 0;
		ContPlanGrid.displayTitle = 1;
		ContPlanGrid.hiddenPlus = 1;
		ContPlanGrid.hiddenSubtraction = 0;
		ContPlanGrid.canChk=0;
		ContPlanGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}

//责任信息初始化
function initContPlanDutyGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="责任编码";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="责任名称";
		iArray[2][1]="120px";
		iArray[2][2]=150;
		iArray[2][3]=0;
		iArray[2][10]="RiskCode";

		iArray[3]=new Array();
		iArray[3][0]="责任类型";
		iArray[3][1]="120px";
		iArray[3][2]= 60;
		iArray[3][3]= 0;

		iArray[4]=new Array();
		iArray[4][0]="类型名称";
		iArray[4][1]="120px";
		iArray[4][2]=60;
		iArray[4][3]=0;

		ContPlanDutyGrid = new MulLineEnter( "fm" , "ContPlanDutyGrid" );
		//这些属性必须在loadMulLine前
		ContPlanDutyGrid.mulLineCount = 0;
		ContPlanDutyGrid.displayTitle = 1;
		ContPlanDutyGrid.hiddenPlus = 1;
		ContPlanDutyGrid.hiddenSubtraction = 1;
		ContPlanDutyGrid.canChk=1;
		ContPlanDutyGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert(ex);
	}
}

//保险计划初始化
function initContPlanCodeGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="计划编码";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="计划名称";
		iArray[2][1]="260px";
		iArray[2][2]=150;
		iArray[2][3]=0;
		iArray[2][10]="RiskCode";
		
		iArray[3]=new Array();
		iArray[3][0]="分类说明";
		iArray[3][1]="0px";
		iArray[3][2]= 60;
		iArray[3][3]= 3;
		
		iArray[4]=new Array();
		iArray[4][0]="可保人数";
		iArray[4][1]="120px";
		iArray[4][2]= 60;
		iArray[4][3]= 0;
		
		ContPlanCodeGrid = new MulLineEnter( "fm" , "ContPlanCodeGrid" );
		//这些属性必须在loadMulLine前
		ContPlanCodeGrid.mulLineCount = 0;
		ContPlanCodeGrid.displayTitle = 1;
		ContPlanCodeGrid.hiddenPlus = 1;
		ContPlanCodeGrid.hiddenSubtraction = 1;
		ContPlanCodeGrid.canSel=1;
		ContPlanCodeGrid.selBoxEventFuncName = "ShowContPlan";
		ContPlanCodeGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}


// 要约信息列表的初始化
function initContPlanGridyry() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种名称";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种编码";
		iArray[2][1]="0px";
		iArray[2][2]=150;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="责任编码";
		iArray[3][1]="0px";
		iArray[3][2]= 60;
		iArray[3][3]= 3;
		iArray[3][10]="DutyCode";

		iArray[4]=new Array();
		iArray[4][0]="险种责任";
		iArray[4][1]="180px";
		iArray[4][2]=60;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="计算要素";
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=3;
		iArray[5][10]="FactorCode";

		iArray[6]=new Array();
		iArray[6][0]="要素名称";
		iArray[6][1]="80px";
		iArray[6][2]=150;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="要素说明";
		iArray[7][1]="200px";
		iArray[7][2]=150;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="要素值";
		iArray[8][1]="50px";
		iArray[8][2]=150;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="特别说明";
		iArray[9][1]="80px";
		iArray[9][2]=150;
		iArray[9][3]=0;

		iArray[10]=new Array();
		iArray[10][0]="险种版本";
		iArray[10][1]="0px";
		iArray[10][2]=10;
		iArray[10][3]=3;

		iArray[11]=new Array();
		iArray[11][0]="集体保单险种号码";
		iArray[11][1]="40px";
		iArray[11][2]=10;
		iArray[11][3]=3;

		iArray[12]=new Array();
		iArray[12][0]="主险编码";
		iArray[12][1]="0px";
		iArray[12][2]=10;
		iArray[12][3]=3;

		iArray[13]=new Array();
		iArray[13][0]="类型";
		iArray[13][1]="0px";
		iArray[13][2]=10;
		iArray[13][3]=3;

		iArray[14]=new Array();
		iArray[14][0]="计算方法";
		iArray[14][1]="0px";
		iArray[14][2]=10;
		iArray[14][3]=3;

		ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" );
		//这些属性必须在loadMulLine前
		ContPlanGrid.mulLineCount = 0;
		ContPlanGrid.displayTitle = 1;
		ContPlanGrid.hiddenPlus = 1;
		ContPlanGrid.hiddenSubtraction = 0;
		ContPlanGrid.canChk=0;
		ContPlanGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
