<%@include file="/i18n/language.jsp"%>
<%
  //程序名称：RIAccRDQueryInit.jsp
  //程序功能：分出责任定义-信息查询
  //创建日期：2011/6/16
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		initRIAccumulateGrid();
	}
	catch(re){
		myAlert("RIAccRDQueryInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initRIAccumulateGrid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="分出责任编码";
		iArray[1][1]="90px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="分出责任名称";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="分出责任状态";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="数据采集方案编码";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		RIAccumulateGrid = new MulLineEnter( "fm" , "RIAccumulateGrid" ); 

		RIAccumulateGrid.mulLineCount=1;
		RIAccumulateGrid.displayTitle=1;
		RIAccumulateGrid.canSel=1;
		RIAccumulateGrid.canChk=0;
		RIAccumulateGrid.hiddenPlus=1;
		RIAccumulateGrid.hiddenSubtraction=1;

		RIAccumulateGrid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
