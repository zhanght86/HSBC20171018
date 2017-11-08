<%@include file="/i18n/language.jsp"%>
<%
  //程序名称：RICataRiskInit.jsp
  //程序功能：巨灾报表
  //创建日期：2011-6-29
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMul13Grid();
	}
	catch(re){
		myAlert("RICataRiskInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initMul13Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="巨灾费率";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="维护日期";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="流水号";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=3;


		Mul13Grid = new MulLineEnter( "fm" , "Mul13Grid" ); 

		Mul13Grid.mulLineCount=1;
		Mul13Grid.displayTitle=1;
		Mul13Grid.canSel=1;
		Mul13Grid.hiddenPlus=1;
		Mul13Grid.hiddenSubtraction=1;
		Mul13Grid.selBoxEventFuncName ="showCateRate"; //响应的函数名，不加扩号   
		Mul13Grid.loadMulLine(iArray);
		


	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
