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
		iArray[1][0]="险种编码";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="发布类型";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="发布环境";
		iArray[3][1]="80px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="发布版本";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="脚本名称";
		iArray[5][1]="240px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="脚本路径";
		iArray[6][1]="260px";
		iArray[6][2]=100;
		iArray[6][3]=0;		
		
		iArray[7]=new Array();
		iArray[7][0]="发布日期";
		iArray[7][1]="120px";
		iArray[7][2]=100;
		iArray[7][3]=0;	
		
		iArray[8]=new Array();
		iArray[8][0]="流水号";
		iArray[8][1]="80px";
		iArray[8][2]=100;
		iArray[8][3]=3;
		
		iArray[9]=new Array();
		iArray[9][0]="备注";
		iArray[9][1]="80px";
		iArray[9][2]=100;
		iArray[9][3]=3;	
						
		
		MullineGrid = new MulLineEnter( "fm" , "MullineGrid" ); 

		MullineGrid.mulLineCount=0;
		MullineGrid.displayTitle=1;
		MullineGrid.canSel=0;
		MullineGrid.canChk=1;
		MullineGrid.hiddenPlus=1;
		MullineGrid.hiddenSubtraction=1;
		//MullineGrid.selBoxEventFuncName ="showList";
		MullineGrid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
</html>
