<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDScheRateCalFactorInit.jsp
  //程序功能：费率表要素库
  //创建日期：2009-3-17
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("FactorName").value = "";
		fm.all("FactorCode").value = "";
		fm.all("FactorDataType").value = "";
		fm.all("FactorType").value = "";
		
		fm.all("FactorCode").readOnly = false;
		fm.all("btnSave").disabled = false;	
		initMulline9Grid();
	}
	catch(re){
		myAlert("PDScheRateCalFactorInit.jsp-->"+"初始化界面错误!");
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
		iArray[1][0]="要素名称";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="要素代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="要素数据类型";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="要素属性";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.selBoxEventFuncName = "fill";

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function fill()
{
	var selRow = Mulline9Grid.getSelNo();
	
	fm.all("FactorName").value = Mulline9Grid.getRowColData(selRow-1,1);
	fm.all("FactorCode").value = Mulline9Grid.getRowColData(selRow-1,2);	
	fm.all("FactorDataType").value = Mulline9Grid.getRowColData(selRow-1,3);
	fm.all("FactorType").value = Mulline9Grid.getRowColData(selRow-1,4);
	
	fm.all("FactorCode").readOnly = true;
	fm.all("btnSave").disabled = true;	
}
</script>
