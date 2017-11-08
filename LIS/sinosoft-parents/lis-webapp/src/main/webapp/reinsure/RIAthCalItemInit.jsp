<%@include file="/i18n/language.jsp"%>
<%
  //程序名称：RIAthCalItemInit.jsp
  //程序功能：明细算法项定义
  //创建日期：2011/6/16
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMul10Grid();
	}
	catch(re){
		myAlert("RIAthCalItemInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initMul10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="计算项编码";
		iArray[1][1]="75px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="计算项名称";
		iArray[2][1]="75px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="参数类型";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="参数值";
		iArray[4][1]="45px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		Mul10Grid = new MulLineEnter( "fm" , "Mul10Grid" ); 

		Mul10Grid.mulLineCount=1;
		Mul10Grid.displayTitle=1;
		Mul10Grid.canSel=0;
		Mul10Grid.canChk=0;
		Mul10Grid.hiddenPlus=0;
		Mul10Grid.hiddenSubtraction=0;

		Mul10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
