<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDRiskDutyFactorInit.jsp
  //程序功能：责任录入要素定义
  //创建日期：2009-3-13
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.all("PayPlanCode").value = "<%=request.getParameter("payplancode")%>";
		queryDutyCode();
		initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();
		queryMulline10Grid();
		
		
		//添加保障录入要素
		initLMDutyParamGrid();
		queryLMDutyParam();
		initMulline12Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDRiskDutyFactorInit.jsp-->"+"初始化界面错误!"+re.message);
	}
}
function updateDisplayState()
{
 // rowCount:显示的字段数量
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1  and fieldcode<>'FACTORNAME'";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:下拉项对应的selectcode的数组
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1  and fieldcode<>'FACTORNAME' order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 for(var i = 0; i < rowCount; i++)
 {
   if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("RiskCode").value,null,"readonly"); 		 
	
	 }else if(Mulline9Grid.getRowColData(i,2) == "DUTYCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("DutyCode").value,null,"readonly"); 		 
	 }
	 else 
	 {
	 	var selNo = Mulline10Grid.getSelNo();
 		if(selNo==0 || selNo == null)
 		{
 			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
 		}else
 		{
 			if(Mulline9Grid.getRowColData(i,2) == "CALFACTOR")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,3),null,null); 
	 		}
	 		
	 		else
	 		{ 			
	 	 		var tDefaultValueSQL = "Select REGEXP_REPLACE(REGEXP_REPLACE("+Mulline9Grid.getRowColData(i,2)+",'\\|', '｜'),'\\^','＾') FROM "+fm.all("tableName").value+" where dutycode = '"+fm.all('DutyCode').value +"' and riskcode = '"+fm.all('RiskCode').value+"' and CALFACTOR = '"+Mulline10Grid.getRowColData(selNo-1,3)+"'";
   	 		var tContent = easyExecSql(tDefaultValueSQL);
   	 		var cData = null;
   	 		if(tContent!=null&&tContent[0][0]!="null")
   	 		{
   	 	 		cData = tContent[0][0];
   	 		}
     		Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);
     	}
    }
  }
  }
  judgeCalFactor();

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
		iArray[5][1]="150px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="业务人员备注";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=3;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
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
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="责任编码";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="计算要素";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="要素名称";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="计划要素类型";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=3;

		
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

function initMulline12Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="责任代码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="计算要素";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="要素名称";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=1;

		iArray[4]=new Array();
		iArray[4][0]="对应代码";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=2;
		iArray[4][4]="pd_tbstrinfo";


		iArray[5]=new Array();
		iArray[5][0]="代码名称";
		iArray[5][1]="50px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		

		Mulline12Grid = new MulLineEnter( "fm" , "Mulline12Grid" ); 
            
		Mulline12Grid.mulLineCount=0;
		Mulline12Grid.displayTitle=1;
		Mulline12Grid.canSel=0;
		Mulline12Grid.canChk=0;
		Mulline12Grid.hiddenPlus=0;
		Mulline12Grid.hiddenSubtraction=0;
		//Mulline12Grid.selBoxEventFuncName ="updateDisplayState";
		Mulline12Grid. addEventFuncName="setDutyCode";
		Mulline12Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initLMDutyParamGrid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="参数";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=2;
		iArray[1][4]="pd_calfactor";
		iArray[1][5]="1|2";
		iArray[1][6]="0|1";
		
		iArray[2]=new Array();
		iArray[2][0]="参数名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		//iArray[2][4]="pd_calfactor";
		//iArray[1][5]="1|2";

		iArray[3]=new Array();
		iArray[3][0]="参数位置";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=2;
		iArray[3][4]="pd_chooseflag";

		iArray[4]=new Array();
		iArray[4][0]="默认值";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=1;
		//iArray[4][4]="pd_tbstrinfo";


		iArray[5]=new Array();
		iArray[5][0]="是否可修改";
		iArray[5][1]="50px";
		iArray[5][2]=100;
		iArray[5][3]=2;
		iArray[5][4]="pd_yesno";
		
		iArray[6]=new Array();
		iArray[6][0]="说明";
		iArray[6][1]="50px";
		iArray[6][2]=100;
		iArray[6][3]=1;
		

		LMDutyParamGrid = new MulLineEnter( "fm" , "LMDutyParamGrid" ); 
            
		LMDutyParamGrid.mulLineCount=0;
		LMDutyParamGrid.displayTitle=1;
		LMDutyParamGrid.canSel=0;
		LMDutyParamGrid.canChk=0;
		LMDutyParamGrid.hiddenPlus=0;
		LMDutyParamGrid.hiddenSubtraction=0;
		//Mulline12Grid.selBoxEventFuncName ="updateDisplayState";
		//LMDutyParamGrid. addEventFuncName="setDutyCode";
		LMDutyParamGrid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
