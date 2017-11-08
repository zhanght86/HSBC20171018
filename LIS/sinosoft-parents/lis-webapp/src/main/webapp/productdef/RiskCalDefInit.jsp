<%@include file="../i18n/language.jsp"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	//String strOperator = globalInput.Operator;
%>
<script type="text/javascript">
function initForm()
{
initMullineGrid();
}

function initMullineGrid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="算法类型";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="算法编码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="险种编码";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="算法描述";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="状态";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="ID";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=3;
		
		MullineGrid = new MulLineEnter( "fm" , "MullineGrid" ); 

		MullineGrid.mulLineCount=0;
		MullineGrid.displayTitle=1;
		MullineGrid.canSel=1;
		MullineGrid.canChk=0;
		MullineGrid.hiddenPlus=1;
		MullineGrid.hiddenSubtraction=1;
		MullineGrid.selBoxEventFuncName ="showList";
		MullineGrid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
</html>
