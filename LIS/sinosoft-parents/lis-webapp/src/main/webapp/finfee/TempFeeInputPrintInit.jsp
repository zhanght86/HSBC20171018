<%
//程序名称：TempFeeInputPrintInit.jsp
//程序功能：暂收费票据打印
//创建日期：2005-12-21 14:55
//创建人  ：关巍
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");

	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
%>

<script language="JavaScript">
function initForm()
{
	//alert("initForm");
  try
  {
    initChequeGrid();
    initCustomertGrid();
    initRNPremGrid();
		initElementtype();
  }
  catch(re)
  {
    alert("TempFeeInputPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//支票汇票暂收据号
function initChequeGrid()
{
	//alert("initChequeGrid11");
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户姓名";         	//列名
      iArray[1][1]="60px";            	//列宽
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="支票/汇票号码";     //列名
      iArray[2][1]="130px";            	//列宽
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号/投保单号/保全号";     //列名
      iArray[3][1]="130px";            	//列宽
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="出票单位";         	//列名
      iArray[4][1]="120px";            	//列宽
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交费日期";         	//列名
      iArray[5][1]="80px";            	//列宽
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="缴费金额";         	//列名
      iArray[6][1]="60px";            	//列宽
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="业务员编码";         //列名
      iArray[7][1]="80px";            	  //列宽
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="业务员姓名";        //列名
      iArray[8][1]="60px";            	 //列宽
      iArray[8][3]=0;              		 //是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="收费机构";        //列名
      iArray[9][1]="100px";            	 //列宽
      iArray[9][3]=0;              		 //是否允许输入,1表示允许，0表示不允许      

      ChequeGrid = new MulLineEnter( "fm" , "ChequeGrid" ); 
      //这些属性必须在loadMulLine前
      ChequeGrid.mulLineCount = 3;
      ChequeGrid.displayTitle = 1;
      ChequeGrid.locked = 1;
      ChequeGrid.canSel = 1;
      ChequeGrid.canChk = 0;
      ChequeGrid.loadMulLine(iArray);
      ChequeGrid.selBoxEventFuncName = "easyQueryAddClick";
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }	
}

//客户预存收据
function initCustomertGrid()
{
	//alert("initCustomertGrid");
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户编号";         	//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="100px";            	//列宽
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户姓名";         	//列名
      iArray[2][1]="150px";            	//列宽
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="缴费金额";         	//列名
      iArray[3][1]="120px";            	//列宽
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许?

      iArray[4]=new Array();
      iArray[4][0]="缴费方式";         	//列名
      iArray[4][1]="60px";            	//列宽
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="预存号";       	   	//列名
      iArray[5][1]="130px";            	//列宽
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="预存实付号";   	   	//列名
      iArray[6][1]="130px";            	//列宽
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="交费日期";         	//列名
      iArray[7][1]="80px";            	//列宽
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="收银员";         	  //列名
      iArray[8][1]="60px";             	//列宽
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      CustomertGrid = new MulLineEnter( "fm" , "CustomertGrid" ); 
      //这些属性必须在loadMulLine前
      CustomertGrid.mulLineCount = 3;
      CustomertGrid.displayTitle = 1;
      CustomertGrid.locked = 1;
      CustomertGrid.canSel = 1;
      CustomertGrid.canChk = 0;
      CustomertGrid.loadMulLine(iArray);
      CustomertGrid.selBoxEventFuncName = "easyQueryAddClick";
      }
      catch(ex)
      {
        alert(ex);
      }	
}

//预交续期保费收据
function initRNPremGrid()
{
    var iArray = new Array();

      try
      {
     // alert("faint");
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保人姓名";        //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="50px";            	//列宽
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="交费日期";         	//列名
      iArray[2][1]="70px";            	//列宽
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号";            //列名
      iArray[3][1]="150px";            	//列宽
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="预存号";            //列名
      iArray[4][1]="150px";            	//列宽
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交费金额";          //列名
      iArray[5][1]="60px";            	//列宽
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="操作员";         	//列名
      iArray[6][1]="60px";            	//列宽
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="交费人";       		//列名
      iArray[7][1]="50px";            	//列宽
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="身份证号";         	//列名
      iArray[8][1]="160px";            	//列宽
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      RNPremGrid = new MulLineEnter( "fm" , "RNPremGrid" ); 
      //这些属性必须在loadMulLine前
      RNPremGrid.mulLineCount = 3;
      RNPremGrid.displayTitle = 1;
      RNPremGrid.locked = 1;
      RNPremGrid.canSel = 1;
      RNPremGrid.canChk = 0;
      RNPremGrid.loadMulLine(iArray);
      RNPremGrid.selBoxEventFuncName = "easyQueryAddClick";
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
