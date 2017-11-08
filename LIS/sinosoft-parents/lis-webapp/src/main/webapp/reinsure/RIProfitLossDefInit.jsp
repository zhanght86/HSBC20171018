<%@include file="/i18n/language.jsp"%>
 <%
  //程序名称：RIProfitLossDefInit.jsp
  //程序功能：盈余佣金定义
  //创建日期：2011/8/18
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm(){
	try{
		fm.RIProfitNo.value="";
		fm.RIProfitName.value="";
		fm.RIcomCode.value="";
		fm.RIcomName.value="";
		fm.ContNo.value="";
		fm.ContName.value="";
		fm.RIProfitDes.value="";
		fm.RelaType.value="1";
		fm.RelaTypeName.value="再保方案关联";
		
		initMul9Grid();
	}
	catch(re){
		myAlert("RIProfitLossDefInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initMul9Grid(){
	var iArray = new Array();
	try{

		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="再保方案编码";
		iArray[1][1]="90px";
		iArray[1][2]=100;
		iArray[1][3]=2;
		iArray[1][4]="riprecept"; 
		iArray[1][5]="1|2"; 	 			//将引用代码分别放在第1、2
		iArray[1][6]="0|1";	
		iArray[1][19]=1; 

		iArray[2]=new Array();
		iArray[2][0]="再保方案名称";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;


		Mul9Grid = new MulLineEnter( "fm" , "Mul9Grid" ); 

		Mul9Grid.mulLineCount=0;
		Mul9Grid.displayTitle=1;
		Mul9Grid.canSel=1;
		Mul9Grid.canChk=0;
		Mul9Grid.hiddenPlus=0;
		Mul9Grid.hiddenSubtraction=0;

		Mul9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
