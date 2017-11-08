<%@include file="../i18n/language.jsp"%>

<%
  //程序名称：PDDutyGetAliveInit.jsp
  //程序功能：责任给付生存
  //创建日期：2009-3-16
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.all("GetDutyCode").value = "<%=request.getParameter("getdutycode3")%>";
		initMulline10Grid();
		//initMulline9Grid();
		queryMulline9Grid();
		queryMulline10Grid();
		updateDisplayState();
		
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDDutyGetAliveInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}

function initGetAliveDetail()
{
		initMulline10Grid();
		
		queryMulline10Grid();
		//document.getElementById('GETDUTYKIND').value= '';
		document.getElementById('GETINTV').value= '';
		document.getElementById('GETSTARTPERIOD').value= '';
		document.getElementById('GETSTARTUNIT').value= '';
		document.getElementById('STARTDATECALREF').value= '';
		document.getElementById('STARTDATECALMODE').value= '';
		document.getElementById('ENDDATECALREF').value= '';
		document.getElementById('ENDDATECALMODE').value= '';
		//document.getElementById('AFTERGET').value= '';
		document.getElementById('CALCODE').value= '';
		document.getElementById('GETENDPERIOD').value= '';
		document.getElementById('GETENDUNIT').value= '';
		document.getElementById('GETACTIONTYPE').value= '';
		document.getElementById('URGEGETFLAG').value= '';
		document.getElementById('MAXGETCOUNTTYPE').value= '';
		document.getElementById('NeedReCompute').value= '';
		
		document.getElementById('GETSTARTUNITName').value= '';
		document.getElementById('STARTDATECALREFName').value= '';
		document.getElementById('STARTDATECALMODEName').value= '';
		document.getElementById('ENDDATECALREFName').value= '';
		document.getElementById('ENDDATECALMODEName').value= '';
		//document.getElementById('AFTERGETName').value= '';
		document.getElementById('GETENDPERIODName').value= '';
		document.getElementById('GETACTIONTYPEName').value= '';
		document.getElementById('URGEGETFLAGName').value= '';
		document.getElementById('MAXGETCOUNTTYPEName').value= '';
		document.getElementById('NeedReComputeName').value= '';
}

function updateDisplayState()
{
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
	
	}
	else
	{
		var name = Mulline10Grid.getRowColData(selNo-1,12);
		var  tRiskCode=fm.all("RiskCode").value;
		initCalCodeMain(tRiskCode,name);
		
		document.getElementById('GETDUTYKIND').value= Mulline10Grid.getRowColData(selNo-1,3);
		document.getElementById('GETINTV').value= Mulline10Grid.getRowColData(selNo-1,4);
		document.getElementById('GETSTARTPERIOD').value= Mulline10Grid.getRowColData(selNo-1,5);
		document.getElementById('GETSTARTUNIT').value= Mulline10Grid.getRowColData(selNo-1,6);
		document.getElementById('STARTDATECALREF').value= Mulline10Grid.getRowColData(selNo-1,7);
		document.getElementById('STARTDATECALMODE').value= Mulline10Grid.getRowColData(selNo-1,8);
		document.getElementById('ENDDATECALREF').value= Mulline10Grid.getRowColData(selNo-1,9);
		document.getElementById('ENDDATECALMODE').value= Mulline10Grid.getRowColData(selNo-1,10);
		document.getElementById('AFTERGET').value= Mulline10Grid.getRowColData(selNo-1,11);
		document.getElementById('CALCODE').value= Mulline10Grid.getRowColData(selNo-1,12);
		
		document.getElementById('GETENDPERIOD').value= Mulline10Grid.getRowColData(selNo-1,13);
		document.getElementById('GETENDUNIT').value= Mulline10Grid.getRowColData(selNo-1,14);
		document.getElementById('GETACTIONTYPE').value= Mulline10Grid.getRowColData(selNo-1,15);
		document.getElementById('URGEGETFLAG').value= Mulline10Grid.getRowColData(selNo-1,16);
		document.getElementById('MAXGETCOUNTTYPE').value= Mulline10Grid.getRowColData(selNo-1,17);
		document.getElementById('NeedReCompute').value= Mulline10Grid.getRowColData(selNo-1,18);
		showOneCodeName('pd_payendyearflag','GETSTARTUNIT','GETSTARTUNITName');
		showOneCodeName('pd_payenddatecalref','STARTDATECALREF','STARTDATECALREFName');
		showOneCodeName('pd_startdatecalmode','STARTDATECALMODE','STARTDATECALMODEName');
		showOneCodeName('pd_payenddatecalref','ENDDATECALREF','ENDDATECALREFName');
		showOneCodeName('pd_enddatecalmode','ENDDATECALMODE','ENDDATECALMODEName');
		showOneCodeName('pd_afterget','AFTERGET','AFTERGETName');
		
		showOneCodeName('pd_payendyearflag','GETENDUNIT','GETENDPERIODName');
		showOneCodeName('pd_getactiontype','GETACTIONTYPE','GETACTIONTYPEName');
		showOneCodeName('pd_urgegetflag','URGEGETFLAG','URGEGETFLAGName');
		showOneCodeName('pd_maxgetcounttype','MAXGETCOUNTTYPE','MAXGETCOUNTTYPEName');
		showOneCodeName('pd_needrecompute','NeedReCompute','NeedReComputeName');
	}
}

function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="给付代码";
		iArray[1][1]="40px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="给付名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="给付责任类型";
		iArray[3][1]="40px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="给付间隔";
		iArray[4][1]="40px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="起领期间";
		iArray[5][1]="40px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="起领期间单位";
		iArray[6][1]="40px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="起领日期计算参照";
		iArray[7][1]="40px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="起领日期计算方式";
		iArray[8][1]="40px";
		iArray[8][2]=100;
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="止领日期计算参照";
		iArray[9][1]="40px";
		iArray[9][2]=100;
		iArray[9][3]=0;
		
		iArray[10]=new Array();
		iArray[10][0]="止领日期计算方式";
		iArray[10][1]="40px";
		iArray[10][2]=100;
		iArray[10][3]=0;
		
		
		iArray[11]=new Array();
		iArray[11][0]="给付后动作";
		iArray[11][1]="40px";
		iArray[11][2]=100;
		iArray[11][3]=0;
		
		iArray[12]=new Array();
		iArray[12][0]="算法编码";
		iArray[12][1]="40px";
		iArray[12][2]=100;
		iArray[12][3]=0;
		
		iArray[13]=new Array();
		iArray[13][0]="止领期间";
		iArray[13][1]="40px";
		iArray[13][2]=100;
		iArray[13][3]=0;
		
		iArray[14]=new Array();
		iArray[14][0]="止领期间单位";
		iArray[14][1]="40px";
		iArray[14][2]=100;
		iArray[14][3]=0;
		
		iArray[15]=new Array();
		iArray[15][0]="领取动作类型";
		iArray[15][1]="40px";
		iArray[15][2]=100;
		iArray[15][3]=0;
		
		iArray[16]=new Array();
		iArray[16][0]="催付标记";
		iArray[16][1]="40px";
		iArray[16][2]=100;
		iArray[16][3]=0;
		
		iArray[17]=new Array();
		iArray[17][0]="给付最大次数类型";
		iArray[17][1]="40px";
		iArray[17][2]=100;
		iArray[17][3]=0;
		
		iArray[18]=new Array();
		iArray[18][0]="领取时是否需要重算";
		iArray[18][1]="40px";
		iArray[18][2]=100;
		iArray[18][3]=0;

		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName ="updateDisplayState";

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
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
		iArray[1][0]="属性名称";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="属性代码";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="属性数据类型";
		iArray[3][1]="0px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="属性值";
		iArray[4][1]="110px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="官方字段描述";
		iArray[5][1]="300px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="业务人员备注";
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=3;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
