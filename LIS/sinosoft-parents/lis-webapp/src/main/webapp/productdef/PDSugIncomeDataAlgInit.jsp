<%@include file="../i18n/language.jsp"%>


<%
  //程序名称：PDUMInit.jsp
  //程序功能：险种核保规则定义
  //创建日期：2009-3-14
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function init()
{
	
	document.getElementById("PROCEEDSCODE").value ="";
	document.getElementById("EXPRESSPROPERTY").value = "";
	document.getElementById("CALORDERNO").value = "";
	document.getElementById("CALEXPRESS").value = "";
	document.getElementById("RECURSIONINITVALUE").value = "";
	document.getElementById("REMARK").value = "";
	document.getElementById("RESULTPRECISION").value = "";
	document.getElementById("SHOWFLAG").value = "";
	document.getElementById("TERMS").value = "";
	document.getElementById("BACK").value = "";
	if(document.getElementById("TERMS").value == ""||document.getElementById("TERMS").value == null){
		document.getElementById("TERMS").value ="*";
	}
	initMulline10Grid();
	queryMulline10Grid();
	updateDisplayState();
	fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	
	try{
		if(operator=="update"){
			var sql="select PROCEEDSCODE,EXPRESSPROPERTY,CALORDERNO,CALEXPRESS,RECURSIONINITVALUE,REMARK,RESULTPRECISION,SHOWFLAG,TERMS,BACK from PD_ProceedsExpress where  RISKCODE='"+riskcode+"' and PROCEEDSCODE= '"+proceedscode+"' and TERMS= '"+termsStr+"'";
			var tNameArr = easyExecSql(sql,1,1,1); 
			document.getElementById("PROCEEDSCODE").value = tNameArr[0][0];
			document.getElementById("EXPRESSPROPERTY").value = tNameArr[0][1];
			document.getElementById("CALORDERNO").value = tNameArr[0][2];
			document.getElementById("CALEXPRESS").value = tNameArr[0][3];
			document.getElementById("RECURSIONINITVALUE").value = tNameArr[0][4];
			document.getElementById("REMARK").value = tNameArr[0][5];
			document.getElementById("RESULTPRECISION").value = tNameArr[0][6];
			document.getElementById("SHOWFLAG").value = tNameArr[0][7];
			document.getElementById("TERMS").value = tNameArr[0][8];
			document.getElementById("BACK").value = tNameArr[0][9];
			showOneCodeName('sugcaltype','EXPRESSPROPERTY','EXPRESSPROPERTYName');
			showOneCodeName('sugmaxtype','REMARK','REMARKName');
			showOneCodeName('sugflag','SHOWFLAG','SHOWFLAGName');
			document.getElementById("PROCEEDSCODE").setAttribute("readonly",true ,0);
			document.getElementById("TERMS").setAttribute("readonly",true ,0);
			document.getElementById("btnTerms").setAttribute("disabled",true ,0);
			
		}
		
	}
	catch(re){
		myAlert("PDSugIncomeDataAlginit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
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
		iArray[1][0]="收益项代码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="收益因子";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="备注说明";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		

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
		var name = Mulline10Grid.getRowColData(selNo-1,2);
		
		document.getElementById('hiddenItemCode').value= Mulline10Grid.getRowColData(selNo-1,1);
		document.getElementById('hiddenCalElement').value= Mulline10Grid.getRowColData(selNo-1,2);
	}
}
</script>

