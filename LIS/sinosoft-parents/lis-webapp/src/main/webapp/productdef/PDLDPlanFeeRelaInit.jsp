<%
  //程序名称：PDLDPlanFeeRelaInit.jsp
  //程序功能：累加器关联账单配置
  //创建日期：2016-5-22
  //创建人  ：王海超
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
function initForm1()
{
	try{
		initBox();
		var riskcode="<%=request.getParameter("riskcode")%>";
		document.all("RiskCode").value=riskcode;
		document.all("RiskCode1").value=riskcode;
		var getdutycode="<%=request.getParameter("getdutycode")%>";
		document.all("GetDutyCode").value=getdutycode;
		document.all("GetDutyCode1").value=getdutycode;
		initMulline1Grid();
		queryFeeInfo();
	}
	catch(re){
		alert("PDLDPlanFeeRelaInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}
/*累加器关联账单信息*/
function initMulline1Grid(){
	var iArray = new Array();
	var i=0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="保障计划代码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		iArray[++i]=new Array();
		iArray[i][0]="保障计划代码名称";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="产品代码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="责任代码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="给付责任代码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;

		iArray[++i]=new Array();
		iArray[i][0]="账单费用项代码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="账单费用项代码名称";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="费用项代码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="费用项代码名称";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="是否扣除自付比例";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="是否扣除自付比例名称";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="自付比例";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="免赔额";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="保单年度金额上限";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="保单年度次数上限";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="保单年度天数上限";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="每次赔偿金额上限";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="每次赔偿天数上限";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="每天赔付金额上限";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="每天赔偿固定金额";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="等待期";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="等待期单位";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="等待期单位名称";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		Mulline1Grid = new MulLineEnter( "fm1" , "Mulline1Grid" ); 

		Mulline1Grid.mulLineCount=5;
		Mulline1Grid.displayTitle=1;
		Mulline1Grid.canSel=1;
		Mulline1Grid.canChk=0;
		Mulline1Grid.hiddenPlus=1;
		Mulline1Grid.hiddenSubtraction=1;
		Mulline1Grid.selBoxEventFuncName ="showFeeCodeInfo";
		Mulline1Grid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
