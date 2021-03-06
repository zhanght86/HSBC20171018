<%@include file="../i18n/language.jsp"%>

<%
  //程序名称：PDAccGuratRateInit.jsp
  //程序功能：保证利率录入
  //创建日期：2009-3-17
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.all("InsuAccNo").value = "<%=request.getParameter("insuaccno")%>";
		initMulline10Grid();
		queryMulline10Grid();
		initMulline9Grid();
		queryMulline9Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDAccGuratRateInit.jsp-->"+""+"初始化界面错误!");
	}
}
function afterRadioSelect()
{
	initMulline9Grid();
	queryMulline9Grid();
	updateDisplayState();
}

function updateDisplayState()
{
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('PD_LMAccGuratRate') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('PD_LMAccGuratRate') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1);
 
 for(var i = 0; i < rowCount; i++)
 {
 		if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
		{
			Mulline9Grid.setRowColDataCustomize(i,"4",fm.all("RiskCode").value,null,"readonly","11");
		}
		else if(Mulline9Grid.getRowColData(i,2) == "INSUACCNO")
		{
			Mulline9Grid.setRowColDataCustomize(i,"4",fm.all("InsuAccNo").value,null,"readonly","11"); 
		}
		else{ 
 	     var selNo = Mulline10Grid.getSelNo();
 	     if(selNo==0 || selNo == null)
 	     {
 	     		Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
 	     }else
 	     { 		
	      	if(Mulline9Grid.getRowColData(i,2) == "RATESTARTDATE")
	      	{
	     			Mulline9Grid.setRowColDataCustomize(i,"4",Mulline10Grid.getRowColData(selNo-1,3),null,"readonly","11"); 
	      	}
	      	else if(Mulline9Grid.getRowColData(i,2) == "RATEINTV")
	      	{
	     			Mulline9Grid.setRowColDataCustomize(i,"4",Mulline10Grid.getRowColData(selNo-1,4),null,"readonly","11"); 
	      	}
	      	else
	      	{
 	     	 		var tContentSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from PD_LMAccGuratRate where RISKCODE = '"+Mulline10Grid.getRowColData(selNo-1,1) +"' and INSUACCNO = '"+Mulline10Grid.getRowColData(selNo-1,2) +"' and RATESTARTDATE = '"+Mulline10Grid.getRowColData(selNo-1,3) +"' and RATEINTV = '"+Mulline10Grid.getRowColData(selNo-1,4) +"'";
        	  var tContent = easyExecSql(tContentSQL);
        	  if(tContent==null||tContent[0][0]=="null")
        	 {
	        		Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");
	        	}else
	        	{
	          	Mulline9Grid.setRowColDataCustomize(i,4,tContent[0][0],null,rowcode[i][1],"11");
	        	}
	       	}
 	     	}
 	    }
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
		iArray[1][0]="险种编码";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种账户代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="利率起始日期";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="利率类型";
		iArray[4][1]="50px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=1;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName ="afterRadioSelect"

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
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="属性代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="属性数据类型";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="属性值";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="官方字段描述";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="业务人员备注";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=1;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=0;
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
