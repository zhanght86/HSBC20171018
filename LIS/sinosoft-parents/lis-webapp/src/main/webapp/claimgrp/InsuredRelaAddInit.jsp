<%
//程序名称：GrpContPolInit.jsp
//程序功能：
//创建日期：2006-04-10 17:09
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
function nullToEmpty(string)
{
    if ((string == null) || (string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

function initInpBox()
{
	try 
	{
		document.all('GrpContNo').value = nullToEmpty("<%=request.getParameter("GrpContNo")%>");
		document.all('PrtNo').value = nullToEmpty("<%=request.getParameter("PrtNo")%>");
		document.all('ScanType').value = nullToEmpty("<%=request.getParameter("scantype")%>");
		document.all('ManageCom').value = nullToEmpty("<%=tGI.ManageCom%>");
	}
	catch(ex)
	{
		alert("在GroupPolInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
    	initInpBox();
		initRiskGrid();
		initMultiAgentGrid();		
		initQuery();
	}
	catch(re)
	{
		alert("GroupPolInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re);
	}
}

// 险种信息列表的初始化
function initRiskGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种编码";
		iArray[1][1]="60px";
		iArray[1][2]=20;
		iArray[1][3]=2;
		iArray[1][4]="RiskCode";
		iArray[1][5]="1|2";
		iArray[1][6]="0|1";
		iArray[1][9]="险种编码|code:RiskCode&NOTNULL";
		iArray[1][18]=300;
		//iArray[1][19]=1;

		iArray[2]=new Array();
		iArray[2][0]="险种名称";
		iArray[2][1]="160px";
		iArray[2][2]=20;
		iArray[2][3]=0;
		//iArray[2][9]="险种名称|NOTNULL";

		iArray[3]=new Array();
		iArray[3][0]="交费期间";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=1;
		iArray[3][9]="保费合计|NUM&NOTNULL";

		iArray[4]=new Array();
		iArray[4][0]="应保人数";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;
		iArray[4][9]="预计交费金额|NUM&NOTNULL";

		iArray[5]=new Array();
		iArray[5][0]="参保人数";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		iArray[5][9]="交费金额|NUM&NOTNULL";

		iArray[6]=new Array();
		iArray[6][0]="保费/保额合计";
		iArray[6][1]="120px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		iArray[6][9]="保费合计|NUM&NOTNULL";

		RiskGrid = new MulLineEnter( "document" , "RiskGrid" );
		//这些属性必须在loadMulLine前
		RiskGrid.mulLineCount = 5;
		RiskGrid.displayTitle = 1;
		RiskGrid.canChk =1;
		RiskGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		RiskGrid. hiddenSubtraction=1;
		RiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initMultiAgentGrid(){
    var iArray = new Array();
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="业务员代码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][7]="queryAgentGrid";  　//双击调用查询业务员的函数          

      iArray[2]=new Array();
      iArray[2][0]="业务员姓名";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="所属机构";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="所属分部";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="业绩比例(小数)";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=150;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="所属分部/辖区";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 


      MultiAgentGrid = new MulLineEnter( "document" , "MultiAgentGrid" ); 
      //这些属性必须在loadMulLine前
      MultiAgentGrid.mulLineCount = 5;   
      MultiAgentGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MultiAgentGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
	
}	
</script>
