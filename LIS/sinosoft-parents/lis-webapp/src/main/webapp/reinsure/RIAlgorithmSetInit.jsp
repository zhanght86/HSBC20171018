<%@include file="/i18n/language.jsp"%>
<%
  //程序名称：RIAlgorithmSetInit.jsp
  //程序功能：方案算法集定义
  //创建日期：2011/6/16
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		initAlgLibMul11Grid();
		initAlgSetMul11Grid();
	}
	catch(re){
		myAlert("RIAlgorithmSetInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initAlgLibMul11Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="算法编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="算法名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="算法类型";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="业务类型";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="事件类型";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="算法描述";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		AlgLibMul11Grid = new MulLineEnter( "fm" , "AlgLibMul11Grid" ); 

		AlgLibMul11Grid.mulLineCount=1;
		AlgLibMul11Grid.displayTitle=1;
		AlgLibMul11Grid.canSel=1;
		AlgLibMul11Grid.canChk=0;
		AlgLibMul11Grid.hiddenPlus=1;
		AlgLibMul11Grid.hiddenSubtraction=1;

		AlgLibMul11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initAlgSetMul11Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="方案编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="算法编码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="算法名称";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="算法类型";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="业务类型";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="事件类型";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		AlgSetMul11Grid = new MulLineEnter( "fm" , "AlgSetMul11Grid" ); 

		AlgSetMul11Grid.mulLineCount=1;
		AlgSetMul11Grid.displayTitle=1;
		AlgSetMul11Grid.canSel=0;
		AlgSetMul11Grid.canChk=1;
		AlgSetMul11Grid.hiddenPlus=1;
		AlgSetMul11Grid.hiddenSubtraction=1;

		AlgSetMul11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
