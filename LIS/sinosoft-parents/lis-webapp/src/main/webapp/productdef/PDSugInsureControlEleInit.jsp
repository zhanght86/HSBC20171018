<%@include file="../i18n/language.jsp"%>


<%
  //程序名称：PDUMInit.jsp
  //程序功能：险种核保规则定义
  //创建日期：2009-3-14
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		document.getElementById("CONTROLCODE").value = "";
		document.getElementById("CONTROLVALUE").value = "";
		document.getElementById("FUNCTIONTYPE").value = "";
		document.getElementById("RELACODE").value = "";
		document.getElementById("RELASHOWFLAG").value = "";
		document.getElementById("RELAVALUESQL").value = "";
		document.getElementById("RELASHOWVALUE").value = "";
		
		document.getElementById("CONTROLCODEName").value = "";
		document.getElementById("FUNCTIONTYPEName").value = "";
		document.getElementById("RELACODEName").value = "";
		document.getElementById("RELASHOWFLAGName").value = "";
		initMulline10Grid();
		queryMulline10Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDSugInsureControlEleInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
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
		iArray[1][0]="控件ID";
		iArray[1][1]="40px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="控件值";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="过滤类型";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="影响控件ID";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		

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

function updateDisplayState(){
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0){
	
	}
	else{
		
		var ControlCodestr = Mulline10Grid.getRowColData(selNo-1,1);
		var ControlValuestr = Mulline10Grid.getRowColData(selNo-1,2);
		var FunctionTypestr = Mulline10Grid.getRowColData(selNo-1,3);
		var RelaCodestr = Mulline10Grid.getRowColData(selNo-1,4);
		var sql="select RelaShowFlag,RelaValueSql,RelaShowValue from PD_LMRiskShowRela where  RiskCode='"+riskcode+"' and ControlCode='"+ControlCodestr+"' and ControlValue='"+ControlValuestr+"' and FunctionType='"+FunctionTypestr+"' and RelaCode='"+RelaCodestr+"'";
		var tNameArr = easyExecSql(sql,1,1,1); 
		document.getElementById("CONTROLCODE").value = ControlCodestr;
		document.getElementById("CONTROLVALUE").value = ControlValuestr;
		document.getElementById("FUNCTIONTYPE").value = FunctionTypestr;
		document.getElementById("RELACODE").value = RelaCodestr;
		document.getElementById("RELASHOWFLAG").value = tNameArr[0][0];
		document.getElementById("RELAVALUESQL").value = tNameArr[0][1];
		document.getElementById("RELASHOWVALUE").value = tNameArr[0][2];
		showOneCodeName('controltype','FUNCTIONTYPE','FUNCTIONTYPEName');
		if(document.getElementById("RELASHOWFLAG").value != null&& document.getElementById("RELASHOWFLAG").value != ""){
			showOneCodeName('controlexporimp','RELASHOWFLAG','RELASHOWFLAGName');
		}
		
		var sql1 = "select templateid from PDT_RiskTypeTemplate where risktype = '"+riskcode+"' and standbyflag1 = '02'";
  		var tempArr1 = easyExecSql(sql1,1,1,1);
  		var sql2 = "select colcode,colname,optionflag,colproperties from PDT_RiskShowCol  where templateid='"+tempArr1[0][0]+"' and colcode = '"+document.getElementById("CONTROLCODE").value+"'  and showtype='Risk' order by colorder";
  		var tNameArr2 = easyExecSql(sql2,1,1,1);
  		document.getElementById("CONTROLCODEName").value = tNameArr2[0][1];
  		var sql3 = "select colcode,colname,optionflag,colproperties from PDT_RiskShowCol  where templateid='"+tempArr1[0][0]+"' and colcode = '"+document.getElementById("RELACODE").value+"'  and showtype='Risk' order by colorder";
  		var tNameArr3 = easyExecSql(sql3,1,1,1);
  		document.getElementById("RELACODEName").value = tNameArr3[0][1];
	}
}
</script>

