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
		document.getElementById('ID').value= "";
		document.getElementById('PRIFITCODE').value= "";
		document.getElementById('NAME').value="";
		document.getElementById('SHOWORDER').value= "";
		document.getElementById("PARENTID").value = "";
		document.getElementById("PRIFITCODEName").value = "";
		document.getElementById("PARENTIDName").value = "";
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
		iArray[1][0]="ID";
		iArray[1][1]="40px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="表头收益项代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="表头中文名称";
		iArray[3][1]="40px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="顺序";
		iArray[4][1]="40px";
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
		var idStr = Mulline10Grid.getRowColData(selNo-1,1);
		document.getElementById('ID').value= idStr;
		document.getElementById('PRIFITCODE').value= Mulline10Grid.getRowColData(selNo-1,2);
		document.getElementById('NAME').value= Mulline10Grid.getRowColData(selNo-1,3);
		document.getElementById('SHOWORDER').value= Mulline10Grid.getRowColData(selNo-1,4);
		var sql="select ParentID from PD_LMRiskProfitHead where  RISKCODE='"+riskcode+"' and id= '"+idStr+"'";
		var tNameArr = easyExecSql(sql,1,1,1); 
		document.getElementById("PARENTID").value = tNameArr[0][0];
		var sql2="select Id,Name from PD_LMRiskProfitHead  where riskcode='"+riskcode+"' and id='"+document.getElementById('ID').value+"'";
		var tNameArr2 = easyExecSql(sql2,1,1,1); 
		if(tNameArr2!=""&&tNameArr2!=null){
			document.getElementById('PARENTIDName').value = tNameArr2[0][1];
		}
		
	}
}
</script>

