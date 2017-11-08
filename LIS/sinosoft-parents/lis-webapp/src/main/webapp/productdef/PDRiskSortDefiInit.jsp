<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDRiskSortDefiInit.jsp
  //程序功能：险种分类定义
  //创建日期：2009-3-12
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RequDate").value = "<%=request.getParameter("requdate")%>";
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		initMulline9Grid();
		queryMulline9Grid();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDRiskSortDefiInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}

function updateDisplayState()
{
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('PD_LMRisk') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('PD_LMRisk') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1);

 var j = 0;
 for(var i = 0; i < rowCount; i++)
 {
  Mulline9Grid.setRowColData(j,1,null,null,rowcode[i][1],"11");
  
  if(rowcode[i][1] != null && rowcode[i][1] != "")
  {
   j++;
  }
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
		iArray[1][0]="险种类别";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=2;
		/*iArray[1][4]="pd_RISKSORTTYPE";*/
		iArray[1][4]="pd_RISKSORTTYPE";


		iArray[2]=new Array();
		iArray[2][0]="险种类别类型值";
		iArray[2][1]="105px";
		iArray[2][2]=100;
		iArray[2][3]=2;

		iArray[3]=new Array();
		iArray[3][0]="备注";
		iArray[3][1]="30px";
		iArray[3][2]=100;
		iArray[3][3]=1;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=0;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=0;
		Mulline9Grid.hiddenSubtraction=0;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
