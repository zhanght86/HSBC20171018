<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDRiskInsuAccInit.jsp
  //程序功能：险种账户定义
  //创建日期：2009-3-12
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		initMulline9Grid();
		initMulline10Grid();
		queryMulline9Grid();
		queryMulline10Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDRiskInsuAccInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}
function afterRadioSelect(){
	initMulline10Grid();
	queryMulline10Grid();
	updateDisplayState();
}
	function updateDisplayState()
{               
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('PD_LMRiskInsuAcc') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);               
 var rowCount = result[0][0];               
                
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('PD_LMRiskInsuAcc') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1);               

 for(var i = 0; i < rowCount; i++)
 {
 	var selNo = Mulline9Grid.getSelNo();
 	if(selNo==0 || selNo == null)
 	{
 		Mulline10Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");   
 		 if(Mulline10Grid.getRowColData(i,2) == "DUECURRENY")
	 	{
			Mulline10Grid.setRowColDataCustomize(i,"4",'',null,"currcode","11"); 
	 	}
 	}else
 	{
 		if(Mulline10Grid.getRowColData(i,2) == "INSUACCNO")
	 	{	
			Mulline10Grid.setRowColDataCustomize(i,"4",Mulline9Grid.getRowColData(selNo-1,2)); 
	 	}else if(Mulline10Grid.getRowColData(i,2) == "DUECURRENY")
	 	{	
	 		var tCurrCodeSQL = "select "+Mulline10Grid.getRowColData(i,2)+" from PD_LMRiskInsuAcc where INSUACCNO = '"+Mulline9Grid.getRowColData(selNo-1,2) +"'";
   	  		var tCurrCode = easyExecSql(tCurrCodeSQL);
			Mulline10Grid.setRowColDataCustomize(i,"4",tCurrCode[0][0],null,"currcode","11"); 
	 	}
	 	else 
	 	{
 	 var tContentSQL = "select "+Mulline10Grid.getRowColData(i,2)+" from PD_LMRiskInsuAcc where INSUACCNO = '"+Mulline9Grid.getRowColData(selNo-1,2) +"'";
   	  var tContent = easyExecSql(tContentSQL);
   	  if(tContent==null||tContent[0][0]=="null")
   	 {
	   	Mulline10Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");
	   }else
	   {
	     Mulline10Grid.setRowColDataCustomize(i,4,tContent[0][0],null,rowcode[i][1],"11");
	   }
	  }
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
		iArray[0][2]=30;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种代码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种账户代码";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="险种账户名称";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.selBoxEventFuncName ="afterRadioSelect"
		

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=30;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="属性名称";
		iArray[1][1]="60px";
		iArray[1][2]=60;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="属性代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="属性数据类型";
		iArray[3][1]="0px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="属性值";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="字段说明";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="业务人员备注";
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=3;
		
		
		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=1;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=0;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.displayPageMark=0;
		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
