<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDLFRiskInit.jsp
  //程序功能：保监会报表描述
  //创建日期：2009-3-16
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript"><!--

function initForm()
{
	try{
		var riskcode = '<%=request.getParameter("riskcode")%>';
		var batchNo = '<%=request.getParameter("deploybatch")%>';
		initMulline9Grid();	
		
	}
	catch(re){
		alert("PDLFRiskInit.jspInitForm函数中发生异常:初始化界面错误!");
	}
}

function initMulline9Grid()
{
	var iArray = new Array();
	
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="产品代码";
		iArray[1][1]="80px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="修改申请日期";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="修改内容";
		iArray[3][1]="150px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="原值";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="现值";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=0;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
--></script>
