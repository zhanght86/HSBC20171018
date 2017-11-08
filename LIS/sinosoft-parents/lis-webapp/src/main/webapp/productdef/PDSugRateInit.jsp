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
		document.getElementById("STARTDATE").value = "";
		document.getElementById("ENDDATE").value = "";
		document.getElementById("TYPE").value = "";
		document.getElementById("CURRENCY").value = "";
		document.getElementById("RATE").value = "";
		document.getElementById("CURRENCYName").value = "";
		document.getElementById("TYPEName").value = "";
		initMulline10Grid();
		queryMulline10Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDUMInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
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
		iArray[1][0]="险种代码";
		iArray[1][1]="40px";
		iArray[1][2]=100;
		iArray[1][3]=3;

		iArray[2]=new Array();
		iArray[2][0]="利率类型";
		iArray[2][1]="40px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="币种";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="利率";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="开始日期";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="结束日期";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		

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
		
		document.getElementById("TYPE").value = Mulline10Grid.getRowColData(selNo-1,2);
		document.getElementById("CURRENCY").value = Mulline10Grid.getRowColData(selNo-1,3);
		document.getElementById("RATE").value = Mulline10Grid.getRowColData(selNo-1,4);
		document.getElementById("STARTDATE").value = Mulline10Grid.getRowColData(selNo-1,5);
		document.getElementById("ENDDATE").value = Mulline10Grid.getRowColData(selNo-1,6);
		var sql="select tittle from PD_LMTypeMsg where id= '"+Mulline10Grid.getRowColData(selNo-1,2)+"'";
		var tNameArr = easyExecSql(sql,1,1,1);
		document.getElementById("TYPEName").value = tNameArr[0][0];
		showOneCodeName('sa_currency','CURRENCY','CURRENCYName');
		
	}
}
</script>

