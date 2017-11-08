<%
  //程序名称：PDEdorZT1Init.jsp
  //程序功能：具体保全项目定义2
  //创建日期：2009-3-16
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">

function initForm()
{
	try{
		document.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		document.all("EdorType").value = "<%=request.getParameter("edortype")%>";
		queryCodeName();
		initMulline10Grid();
		queryMulline10Grid();
		initMulline9Grid();
		queryMulline9Grid();
		initMulline11Grid();
		queryMulline11Grid();
		updateDisplayState();
		document.all("IsReadOnly").value = "<%=session.getValue("IsReadOnly")%>";
	}
	catch(re){
		alert("PDEdorCalInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}
function updateDisplayState()
{
 // rowCount:显示的字段数量
//  var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+document.all("tableName").value+"') and isdisplay = 1";
 var mySql=new SqlClass();
 mySql.setResourceName("productdef.PDEdorCalInputSql"); //指定使用的properties文件名
 mySql.setSqlId("PDEdorCalInputSql6");//指定使用的Sql的id
 mySql.addSubPara(document.all("tableName").value);
 var sql = mySql.getString();
var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:下拉项对应的selectcode的数组
//  sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+document.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 mySql=new SqlClass();
 mySql.setResourceName("productdef.PDEdorCalInputSql"); //指定使用的properties文件名
 mySql.setSqlId("PDEdorCalInputSql7");//指定使用的Sql的id
 mySql.addSubPara(document.all("tableName").value);
 sql = mySql.getString();
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 for(var i = 0; i < rowCount; i++)
 {
     if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,"<%=request.getParameter("riskcode")%>",null,"readonly"); 		 
	 }else if(Mulline9Grid.getRowColData(i,2) == "EDORTYPE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,"<%=request.getParameter("edortype")%>",null,"readonly"); 		 
	 }
	 else
	 {
	 	var selNo = Mulline11Grid.getSelNo();
 		if(selNo==0 || selNo == null)
 		{
 			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
 		}else
 		{
 			if(Mulline9Grid.getRowColData(i,2) == "DUTYCODE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline11Grid.getRowColData(selNo-1,2),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "CALTYPE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline11Grid.getRowColData(selNo-1,4),null,"readonly"); 		 
	 		}
	 		else
	 		{ 			
// 	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+document.all("tableName").value+" where dutycode = '"+Mulline11Grid.getRowColData(selNo-1,2) +"' and riskcode = '"+document.all('RiskCode').value+"' and EDORTYPE = '"+Mulline11Grid.getRowColData(selNo-1,3)+"' and CALTYPE = '"+Mulline11Grid.getRowColData(selNo-1,4)+"'";
	 	 		 mySql=new SqlClass();
	 	 		 mySql.setResourceName("productdef.PDEdorCalInputSql"); //指定使用的properties文件名
	 	 		 mySql.setSqlId("PDEdorCalInputSql8");//指定使用的Sql的id
	 	 		 mySql.addSubPara(Mulline9Grid.getRowColData(i,2));
	 	 		mySql.addSubPara(document.all("tableName").value);
	 	 		mySql.addSubPara(Mulline11Grid.getRowColData(selNo-1,2));
	 	 		mySql.addSubPara(document.all('RiskCode').value);
	 	 		mySql.addSubPara(Mulline11Grid.getRowColData(selNo-1,3));
	 	 		mySql.addSubPara(Mulline11Grid.getRowColData(selNo-1,4));
	 	 		var tDefaultValueSQL = mySql.getString();
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
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="责任代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="责任缴费代码";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="责任缴费名称";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=0;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
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
		iArray[3][3]=0;

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

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=0;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
function initMulline11Grid()
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
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="保全类型";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="计算类型";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="算法编码";
		iArray[5][1]="120px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
		Mulline11Grid.selBoxEventFuncName ="updateDisplayState";

		Mulline11Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
</script>
