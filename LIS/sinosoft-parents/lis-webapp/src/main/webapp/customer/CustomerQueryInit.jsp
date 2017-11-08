
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
      iArray[4][0]="合同号码";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="业务号码";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="业务类型编码";         		//列名
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[6][10]="operationtype";              	        //引用代码："CodeName"为传入数据的名称
      iArray[6][11]="0|^1|新单|^2|续期|^3|保全|^4|赔案";           //"CodeContent" 是传入要下拉显示的代码
      iArray[6][12]="6|7";              	                //引用代码对应第几列，'|'为分割符
      iArray[6][13]="0|1";              	        //上面的列中放置引用代码中第几位值

      iArray[7]=new Array();
      iArray[7][0]="业务类型";         		//列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="操作类型";         		//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="其它号码";         		//列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="金额";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="币种";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="流水号";         		//列名
      iArray[12][1]="100px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许
                       
      iArray[13]=new Array();
      iArray[13][0]="状态";         		//列名
      iArray[13][1]="100px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
            
      iArray[14]=new Array();
      iArray[14][0]="借贷标志";         		//列名
      iArray[14][1]="100px";            		//列宽
      iArray[14][2]=100;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[15]=new Array();
      iArray[15][0]="交费方式";         		//列名
      iArray[15][1]="100px";            		//列宽
      iArray[15][2]=100;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许         
      
      iArray[16]=new Array();
      iArray[16][0]="操作日期";         		//列名
      iArray[16][1]="100px";            		//列宽
      iArray[16][2]=100;            			//列最大值
      iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许         
      
      iArray[17]=new Array();
      iArray[17][0]="操作时间";         		//列名
      iArray[17][1]="100px";            		//列宽
      iArray[17][2]=100;            			//列最大值
      iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许         
       
      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" ); 
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 5;   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.locked = 1;
      CustomerGrid.canSel = 0;
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

</script>
