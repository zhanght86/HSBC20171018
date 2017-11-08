
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
		//document.all('PrtNo').value = opener.fm.PrtNo.value;
//alert("PrtNo"+document.all('PrtNo').value);
		//document.all('ContPlanName').value = "";
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
		initAccountInfoGrid();
		easyQueryClick()
	}
	catch(re)
	{
		alert("ContPlanInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}


//保险计划初始化
function initAccountInfoGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="子公司代码";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="子公司名称";
		iArray[2][1]="200px";
		iArray[2][2]=150;
		iArray[2][3]=0;
		iArray[2][10]="RiskCode";
		
		iArray[3]=new Array();
		iArray[3][0]="行业类别";
		iArray[3][1]="100px";
		iArray[3][2]= 60;
		iArray[3][3]= 0;
		
		iArray[4]=new Array();
		iArray[4][0]="单位性质";
		iArray[4][1]="100px";
		iArray[4][2]= 60;
		iArray[4][3]= 0;
		
		iArray[5]=new Array();
		iArray[5][0]="员工总数";
		iArray[5][1]="100px";
		iArray[5][2]= 60;
		iArray[5][3]= 0;
		
		iArray[6]=new Array();
		iArray[6][0]="单位地址编码";
		iArray[6][1]="100px";
		iArray[6][2]= 60;
		iArray[6][3]= 0;						
		
		AccountInfoGrid = new MulLineEnter( "fm" , "AccountInfoGrid" );
		//这些属性必须在loadMulLine前
		AccountInfoGrid.mulLineCount = 0;
		AccountInfoGrid.displayTitle = 1;
		AccountInfoGrid.hiddenPlus = 1;
		AccountInfoGrid.hiddenSubtraction = 1;
		AccountInfoGrid.canSel=1;
		AccountInfoGrid.selBoxEventFuncName = "ChooseAccount";
		AccountInfoGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
