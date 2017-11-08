<%
  //程序名称：PDLCalculatorInit.jsp
  //程序功能：累加器配置
  //创建日期：2016-5-12
  //创建人  ：W-HC
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
/*页面初始化*/
function initForm()
{
	try{
		//页面初始化
		initBox();
		/*获取页面跳转传递的参数*/
		var riskcode="<%=request.getParameter("riskcode")%>";
		var getdutycode="<%=request.getParameter("getdutycode2")%>";
		document.all("RiskCode").value=riskcode;
		document.all("GetDutyCode").value=getdutycode;
		initMulline1Grid();
		initMulline2Grid();
		initMulline3Grid();
		/*查询该给付责任以上层级的累加器*/
		queryLCalculatorInfo();
		/*查询该给付责任层级的累加器*/
		queryLCalculatorInfo1();
	}
	catch(re){
		alert("PDLCalculatorInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}



/*本给付责任层信息*/
function initMulline2Grid(){
	var iArray = new Array();
	var i=0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="累加器编码";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="累加器层级";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="明细流水号";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="险种编码";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="责任编码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		
		iArray[++i]=new Array();
		iArray[i][0]="给付责任编码";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="给付责任类型";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="累加器步骤";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		Mulline2Grid = new MulLineEnter( "fm" , "Mulline2Grid" ); 

		Mulline2Grid.mulLineCount=0;
		Mulline2Grid.displayTitle=1;
		Mulline2Grid.canSel=1;
		Mulline2Grid.canChk=0;
		Mulline2Grid.hiddenPlus=1;
		Mulline2Grid.hiddenSubtraction=1;
		Mulline2Grid.selBoxEventFuncName ="showLCalculatorData";

		Mulline2Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
/*本给付责任层以上的累加器信息信息*/
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
		iArray[i][0]="累加器编码";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="累加器层级";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="险种编码";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="责任编码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		
		iArray[++i]=new Array();
		iArray[i][0]="给付责任编码";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="给付责任类型";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		

		Mulline1Grid = new MulLineEnter( "fm" , "Mulline1Grid" ); 

		Mulline1Grid.mulLineCount=0;
		Mulline1Grid.displayTitle=1;
		Mulline1Grid.canSel=1;
		Mulline1Grid.canChk=0;
		Mulline1Grid.hiddenPlus=1;
		Mulline1Grid.hiddenSubtraction=1;
		Mulline1Grid.selBoxEventFuncName ="showLCalculatorDataInfo";
		Mulline1Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
function initMulline3Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="账单编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=1;
		iArray[1][4]="pd_llmedfeetype";
		iArray[1][5]="1";     //引用代码对应第几列，'|'为分割符
	    iArray[1][6]="0";    //上面的列中放置引用代码中第几位值

		Mulline3Grid= new MulLineEnter( "fm" , "Mulline3Grid" ); 

		Mulline3Grid.mulLineCount=0;
		Mulline3Grid.displayTitle=1;
		Mulline3Grid.canSel=0;
		Mulline3Grid.canChk=0;
		Mulline3Grid.hiddenPlus=0;
		Mulline3Grid.hiddenSubtraction=0; 
		Mulline3Grid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
//页面初始化
function initBox(){
	document.all("FlagStr").value='';
	document.getElementById('CalculatorCode').value='';
	document.getElementById('CalculatorName').value='';
	document.getElementById('CtrlFactorValue').value='';
	document.getElementById('CalCode').value='';
	document.getElementById('DefaultValue').value='';
	document.getElementById('CalOrder').value='';
	document.getElementById('CalculatorCodeAfter').value='';
	document.getElementById('ValidPeriod').value='';
	document.getElementById('selfPayPercent').value='';
	document.getElementById('IfPayMoney').value='';
	$("input.codeno").val("");
	$("input.codename").val("");
	$("input.coolDatePicker").val("");
	initCalCodeMain('','');
}
</script>
