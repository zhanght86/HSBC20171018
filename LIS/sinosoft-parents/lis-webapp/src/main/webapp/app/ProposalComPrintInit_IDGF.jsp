<%
//程序名称：ProposalComPrintInit_IDGF.jsp
//程序功能：
//创建日期：2006-10-26 12:03
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
  String strManageCom = globalInput.ComCode;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {
  }
  catch(ex)
  {
    alert("在ProposalComPrintInit_IDGF.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
  	
    initInpBox();
    initPolGrid();
    document.all('ManageCom').value = <%=strManageCom%>;
    var tManageCode=<%=strManageCom%>;
    //alert(tManageCode);
    if(tManageCode!="86")
    {
    	 document.all("divErrorMInfo").style.display="none";
    	}
  }
  catch(re)
  {
    alert("ProposalComPrintInit_IDGF.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 var PolGrid;          //定义为全局变量，提供给displayMultiline使用
// 保单信息列表的初始化
function initPolGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="机构";         		//列名
      iArray[1][1]="200px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[2]=new Array();
      iArray[2][0]="件数";         		//列名
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0; 

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.canSel = 0;
      PolGrid.canChk = 1;
 
      PolGrid.loadMulLine(iArray);
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// 保单信息列表的初始化
function initErrorGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";	//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";	//列宽
		iArray[0][2]=10;	//列最大值
		iArray[0][3]=0;	//是否允许输入,1表示允许，0表示不允许

		iArray[1]=new Array();
		iArray[1][0]="保单号";
		iArray[1][1]="140px";           
		iArray[1][2]=100;            	
		iArray[1][3]=0;
		
		
		iArray[2]=new Array();
		iArray[2][0]="出错信息";
		iArray[2][1]="400px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="出错日期";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="出错时间";
		iArray[4][1]="80px";
		iArray[4][2]=200;
		iArray[4][3]=0;
		
		
		iArray[5]=new Array();
		iArray[5][0]="管理机构";
		iArray[5][1]="80px";
		iArray[5][2]=200;
		iArray[5][3]=0;

		ErrorGrid = new MulLineEnter( "fm" , "ErrorGrid" );
		//这些属性必须在loadMulLine前
		ErrorGrid.mulLineCount = 5;
		ErrorGrid.displayTitle = 1;
		ErrorGrid.hiddenPlus = 1;
		ErrorGrid.hiddenSubtraction = 1;
		ErrorGrid.canSel = 0;
		ErrorGrid.canChk = 0;
		ErrorGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
