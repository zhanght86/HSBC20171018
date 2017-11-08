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
		document.getElementById("ID").value = "";
		document.getElementById("CONTENT").value = "";
		document.getElementById("TITTLE").value = "";
		document.getElementById("BAK1").value = "";
		document.getElementById("TYPE").value = "";
		document.getElementById("RELATION").value = "";
		document.getElementById("RELATIONName").value = "";
		document.getElementById("FILENAME").value = "";
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
		iArray[1][0]="类型代码";
		iArray[1][1]="40px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="类型名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="文件夹名称";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="相关性";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="备注";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		

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
		
		var idstr = Mulline10Grid.getRowColData(selNo-1,1);
		document.getElementById("ID").value = idstr;
		document.getElementById("TITTLE").value = Mulline10Grid.getRowColData(selNo-1,2);
		document.getElementById("FILENAME").value = Mulline10Grid.getRowColData(selNo-1,3);
		document.getElementById("RELATION").value = Mulline10Grid.getRowColData(selNo-1,4);
		document.getElementById("BAK1").value = Mulline10Grid.getRowColData(selNo-1,5);
		var sql="select content from PD_LMTypeMsg where  id='"+idstr+"'";
		var tNameArr = easyExecSql(sql,1,1,1); 
		document.getElementById("CONTENT").value = tNameArr[0][0];
		showOneCodeName('sugrelatype','RELATION','RELATIONName');
		
	}
}
</script>

		
	}
}
</script>

