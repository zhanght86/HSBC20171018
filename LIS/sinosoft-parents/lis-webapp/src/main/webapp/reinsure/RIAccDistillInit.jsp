<%@include file="/i18n/language.jsp"%>
<%
  //程序名称：RIAccDistillInit.jsp
  //程序功能：分出责任定义-数据采集
  //创建日期：2011/6/16
  //创建人  ：
  //更新记录：  更新人    更新日期     更新原因/内容
%>

<%
    String tAccDefNo=request.getParameter("AccNo");   
    String tAccDefName=request.getParameter("AccName");
    tAccDefName = new String((tAccDefName).getBytes("ISO-8859-1"),"UTF-8");

%> 

<script type="text/javascript">

function initForm()
{
	try{
		fm.AccDefNo.value ="<%=tAccDefNo%>";
		fm.AccDefName.value ="<%=tAccDefName%>";
		
		initMul9Grid();
	}
	catch(re){
		myAlert("RIAccDistillInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}


function initMul9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="费用类型";
		iArray[1][1]="60px";
		iArray[1][2]=50;
		iArray[1][3]=2;
	    iArray[1][4]="riathfeetype"; 
		iArray[1][5]="1|2"; 	 			//将引用代码分别放在第1、2
		iArray[1][6]="0|1";	
		iArray[1][19]=1; 

		iArray[2]=new Array();
		iArray[2][0]="费用名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="费用描述";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		Mul9Grid = new MulLineEnter( "fm" , "Mul9Grid" ); 

		Mul9Grid.mulLineCount=1;
		Mul9Grid.displayTitle=1;
		Mul9Grid.canSel=0;
		Mul9Grid.canChk=1;
		Mul9Grid.hiddenPlus=0;
		Mul9Grid.hiddenSubtraction=0;

		Mul9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

</script>
