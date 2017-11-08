
<%
//程序名称：FinFeeSureInit.jsp
//程序功能：到帐确认
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    //添加页面控件的初始化。
    //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";   

    String CurrentDate = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
	GlobalInput tGI = new GlobalInput(); //repair:
	tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
	String ManageCom = tGI.ComCode;
%>                            

<script language="JavaScript">
	
var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
var contNo = "<%=tContNo%>";          //个人单的查询条件.
var operator = "<%=tGI.Operator%>";   //记录操作员
var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
	try
	{                                   	
	  
	}
	catch(ex)
	{
		alert("在ProposalQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在ProposalQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initCustomerGrid();
	  initCustomer1Grid();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initCustomerGrid()
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
      iArray[1][0]="客户账户号码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户号码";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="客户姓名";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="币种";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=2;
      iArray[4][4]="Currency";              	        //是否引用代码:null||""为不引用
      iArray[4][9]="币种|code:Currency";;              			//是否允许输入,1表示允许，0表示不允许
      
      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" ); 
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 5;   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.locked = 1;
      CustomerGrid.canSel = 1;
      CustomerGrid.canChk = 0;
      CustomerGrid.hiddenPlus = 1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.loadMulLine(iArray);  
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
// 保单信息列表的初始化
function initCustomer1Grid()
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
      iArray[1][0]="客户账户号码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户号码";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="客户姓名";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="币种";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=2;
      iArray[4][4]="Currency";              	        //是否引用代码:null||""为不引用
      iArray[4][9]="币种|code:Currency";;              			//是否允许输入,1表示允许，0表示不允许
      
      Customer1Grid = new MulLineEnter( "fm" , "Customer1Grid" ); 
      //这些属性必须在loadMulLine前
      Customer1Grid.mulLineCount = 5;   
      Customer1Grid.displayTitle = 1;
      Customer1Grid.locked = 1;
      Customer1Grid.canSel = 1;
      Customer1Grid.canChk = 0;
      Customer1Grid.hiddenPlus = 1;
      Customer1Grid.hiddenSubtraction = 1;
      Customer1Grid.loadMulLine(iArray);  
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
