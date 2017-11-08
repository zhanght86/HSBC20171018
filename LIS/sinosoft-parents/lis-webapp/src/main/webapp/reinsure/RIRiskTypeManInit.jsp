<%@include file="/i18n/language.jsp"%>
<%
  //程序名称：RIRiskTypeManInit.jsp
  //程序功能：险种类型配置
  //创建日期：2011-07-30
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMul11Grid();
	}
	catch(re){
		myAlert("RIRiskTypeManInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initMul11Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种类型";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=2;

		iArray[2]=new Array();
		iArray[2][0]="类型名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="险种明细类型";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=2;

		iArray[4]=new Array();
		iArray[4][0]="明细类型名称";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="险种编号";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=2;

		iArray[6]=new Array();
		iArray[6][0]="险种名称";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		Mul11Grid = new MulLineEnter( "fm" , "Mul11Grid" ); 

		Mul11Grid.mulLineCount=1;
		Mul11Grid.displayTitle=1;
		Mul11Grid.canSel=0;
		Mul11Grid.canChk=0;
		Mul11Grid.hiddenPlus=0;
		Mul11Grid.hiddenSubtraction=0;

		Mul11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
