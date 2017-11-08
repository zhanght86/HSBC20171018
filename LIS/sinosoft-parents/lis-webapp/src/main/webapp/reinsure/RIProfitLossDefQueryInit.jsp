<%@include file="/i18n/language.jsp"%>
 <%
  //程序名称：RIProfitLossDefQueryInit.jsp
  //程序功能：盈余佣金定义查询
  //创建日期：2011/8/20
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm(){
	try{
		initMulDefQueryGrid();
	}
	catch(re){
		myAlert("RIProfitLossDefQueryInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initMulDefQueryGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="盈余佣金编码";
		iArray[1][1]="105px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="盈余佣金名称";
		iArray[2][1]="105px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="再保公司";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="再保合同";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="关联类型";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;


		MulDefQueryGrid = new MulLineEnter( "fm" , "MulDefQueryGrid" ); 

		MulDefQueryGrid.mulLineCount=1;
		MulDefQueryGrid.displayTitle=1;
		MulDefQueryGrid.canSel=1;
		MulDefQueryGrid.canChk=0;
		MulDefQueryGrid.hiddenPlus=1;
		MulDefQueryGrid.hiddenSubtraction=1;

		MulDefQueryGrid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
