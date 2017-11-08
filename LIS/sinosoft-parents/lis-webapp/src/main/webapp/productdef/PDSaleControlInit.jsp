<%@include file="/i18n/language.jsp"%>
<%
  //程序名称：PDSaleControlInit.jsp
  //程序功能：险种销售控制定义
  //创建日期：2009-3-17
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		initMulline9Grid();
		queryMulline9Grid();
		initMulline10Grid();
		queryMulline10Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getValue("IsReadOnly")%>";
		
		editCheck();
		isshowbutton();
	}
	catch(re){
		myAlert("PDSaleControlInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}

function editCheck()
{
	var count = Number(easyExecSql("select count(*) from PD_LCSetMealInfoMain where standbyflag1 != '1' and code = '" + fm.RiskCode.value + "' and codetype='cardsetmealinfo'"));
	if(count > 0)
	{
		fm.all("btnSave").disabled = true;
		//fm.all("btnEdit").disabled = true;
		fm.all("btnDele").disabled = true;				
	}
	else
	{
		fm.all("btnSave").disabled = false;
		//fm.all("btnEdit").disabled = false;
		fm.all("btnDele").disabled = false;				
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
		iArray[5][0]="字段说明";
		iArray[5][1]="200px";
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
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="管理机构";
		iArray[2][1]="105px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="销售渠道";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="停办日期";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=3;

		iArray[5]=new Array();
		iArray[5][0]="销售保额上限";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="销售份数上限";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=3;
		
		iArray[7]=new Array();
		iArray[7][0]="销售保费下限";
		iArray[7][1]="90px";
		iArray[7][2]=100;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="销售保额下限";
		iArray[8][1]="90px";
		iArray[8][2]=100;
		iArray[8][3]=3;

		iArray[9]=new Array();
		iArray[9][0]="销售份数下限";
		iArray[9][1]="90px";
		iArray[9][2]=100;
		iArray[9][3]=3;

		iArray[10]=new Array();
		iArray[10][0]="销售保费下限";
		iArray[10][1]="90px";
		iArray[10][2]=100;
		iArray[10][3]=3;
		
		iArray[11]=new Array();
		iArray[11][0]="幣種";
		iArray[11][1]="90px";
		iArray[11][2]=100;
		iArray[11][3]=0;
		
		iArray[12]=new Array();
		iArray[12][0]="起售日期";
		iArray[12][1]="90px";
		iArray[12][2]=100;
		iArray[12][3]=0;

		iArray[13]=new Array();
		iArray[13][0]="停售日期";
		iArray[13][1]="90px";
		iArray[13][2]=100;
		iArray[13][3]=0;


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
function updateDisplayState()
{
	//var selNo = Mulline10Grid.getSelNo();
	//Mulline9Grid.setRowColDataCustomize(0,4,fm.all("riskcode").value,null,null);
	//Mulline9Grid.setRowColDataCustomize(1,4,Mulline10Grid.getRowColData(selNo-1,2),null,null);
	//Mulline9Grid.setRowColDataCustomize(2,4,fm.all("riskcode").value,null,null);
	//Mulline9Grid.setRowColDataCustomize(3,4,fm.all("riskcode").value,null,null);
	//Mulline9Grid.setRowColDataCustomize(4,4,fm.all("riskcode").value,null,null);

//var selno=Mulline10Grid.getSelNo();

//Mulline10Grid.getRowColData(selNo-1,2)
 // rowCount:显示的字段数量
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:下拉项对应的selectcode的数组
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1); 

 
 for(var i = 0; i < rowCount; i++)
 {
     if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.RiskCode.value,null,"readonly"); 		 
	 }
	 else
	 {
	 	var selNo = Mulline10Grid.getSelNo();
 		if(selNo==0 || selNo == null)
 		{
 			Mulline9Grid.setRowColDataCustomize1(i,4,null,null,rowcode[i][1],null);    
 		}else
 		{
 			if(Mulline9Grid.getRowColData(i,2) == "MANAGECOMGRP")
	 		{
	 			
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,2),null,null); 		 
	 		}
	 		else if(Mulline9Grid.getRowColData(i,2) == "STARTDATE")
	 		{
	 			
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,12),null,null); 		 
	 		}
	 		else if(Mulline9Grid.getRowColData(i,2) == "CURRENCY")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,11),null,'currcode'); 		 
	 		}
	 		else if(Mulline9Grid.getRowColData(i,2) == "SALECHNL")
	 		{
	 			
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,3),null,"pd_salechnl"); 		 
	 		}else
	 		{ 			
	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where riskcode = '"+fm.all('RiskCode').value+"' and MANAGECOMGRP = '"+Mulline10Grid.getRowColData(selNo-1,2)+"'";
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
}
</script>
