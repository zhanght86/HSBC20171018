<%
//程序名称：CaseReceiptInput.jsp
//程序功能：
//创建日期：2002-07-21 20:09:20
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
<%
  String tRiskCode="";
  String tRiskVersion="";
  tRiskCode = request.getParameter("RiskCode");
  tRiskVersion = request.getParameter("RiskVersion");
%>                            
<SCRIPT src="../common/cvar/CVar.js"></SCRIPT>
<script language="JavaScript">
var _Code_FIELDDELIMITER    = "|";            //域之间的分隔符
var _Code_RECORDDELIMITER   = "^";            //记录之间的分隔符

/*************************************************************
 *  将字符串解析成为一个数组
 *  参数  ：  strValue：需要解析的字符串
 *  返回值：  如果执行成功，则返回字符串数组，如果执行不成功，则返回false
 *************************************************************
 */
function decodeString(strValue)
{
	var i,i1,j,j1;
  var strValue;                         //存放服务器端返回的代码数据
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //存放初始化变量时用
  var t_Str;

  try
  {
    arrRecord = strValue.split(_Code_RECORDDELIMITER);  //拆分字符串，形成返回的数组
  
    t_Str     = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);
  
    if (t_Str!="0")                                     //如果不为0表示服务器端执行发生错误
    {
      return false;   
    }
  
    i1=arrRecord.length;
    for (i=1;i<i1;i++)
    {
      arrField  = arrRecord[i].split(_Code_FIELDDELIMITER); //拆分字符串,将每个纪录拆分为一个数组
      j1=arrField.length;
      arrCode[i-1] = new Array();
      for (j=0;j<j1;j++)
      {
        arrCode[i-1][j] = arrField[j];
      }
    }
  }
  catch(ex)
  {
    return false;
  }
  return arrCode; 
}
gVars =new CVar();
var arrStrReturn = new Array();

var arrDuty;
var arrDutyChoose;
var arrDutyPay;
var arrDutyGet;
var arrDutyGetAlive;
var arrDutyGetClm;
var arrDutyCtrl;

function initInpBox()
{ 

}

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
    alert("在CaseReceiptInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initDutyGrid();
    initPremGrid();
    initGetGrid();
    queryDuty(); //从后台查询数据，并且放在arrStrReturn 数组中
    decodeAll(); //将返回的字符串解析到对应的变量中
    displayAll(); //将返回的数据显示到对应的表格中
  }
  catch(re)
  {
    alert("CaseReceiptInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//将返回的字符串解析到对应的变量中
function decodeAll()
{
   arrDuty=decodeString(arrStrReturn[0]);
   arrDutyChoose=decodeString(arrStrReturn[1]);
   arrDutyPay=decodeString(arrStrReturn[2]);
   arrDutyGet=decodeString(arrStrReturn[3]);
   arrDutyGetAlive=decodeString(arrStrReturn[4]);
   arrDutyGetClm=decodeString(arrStrReturn[5]);
   arrDutyCtrl=decodeString(arrStrReturn[6]);
}

//将返回的数据显示到对应的表格中
function displayAll()
{
  fm.RiskCode.value="<%=tRiskCode%>";
  fm.RiskVersion.value="<%=tRiskVersion%>";
  var i;
  var iMax;
  i=0;
  iMax=0;
  //初始化责任的行列数据
  iMax=arrDuty.length;
  for (i=0;i<iMax;i++)
  {
    DutyGrid.addOne("DutyGrid",1);
    DutyGrid.setRowColData(i,1,arrDuty[i][0]);
    DutyGrid.setRowColData(i,2,arrDuty[i][1]);
    DutyGrid.setRowColData(i,5,arrDuty[i][2]);
    DutyGrid.setRowColData(i,6,arrDuty[i][6]);
  }
  //初始化保费项的行列数据
  iMax=arrDutyPay.length;
  for (i=0;i<iMax;i++)
  {
    PremGrid.addOne("PremGrid",1);
    PremGrid.setRowColData(i,2,arrDutyPay[i][0]);
    PremGrid.setRowColData(i,3,arrDutyPay[i][1]);
    PremGrid.setRowColData(i,4,arrDutyPay[i][8]);
  }
  //初始化给付项的行列数据
  iMax=arrDutyGet.length;
  for (i=0;i<iMax;i++)
  {
    GetGrid.addOne("GetGrid",1);
    GetGrid.setRowColData(i,2,arrDutyGet[i][0]);
    GetGrid.setRowColData(i,3,arrDutyGet[i][1]);
    GetGrid.setRowColData(i,4,arrDutyGet[i][4]);
  }
  
}
//责任列表
function initDutyGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         			//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保费";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交费年期";         			//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="领取年期";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //这些属性必须在loadMulLine前
      DutyGrid.mulLineCount = 1;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
//      DutyGrid.detailClick=queryPayAndGet;
      DutyGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
  }

//交费项目列表
function initPremGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="交费编码";    	//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费名称";    	//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="交费金额";    	//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PremGrid = new MulLineEnter( "fm" , "PremGrid" ); 
      //这些属性必须在loadMulLine前
      PremGrid.mulLineCount = 1;   
      PremGrid.displayTitle = 1;
      PremGrid.canChk = 1;
//      DutyGrid.detailClick=queryPayAndGet;
      PremGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
  }

//给付项目列表
function initGetGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="给付代码";         			//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="给付名称";    	//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="给付金额";    	//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      GetGrid = new MulLineEnter( "fm" , "GetGrid" ); 
      //这些属性必须在loadMulLine前
      GetGrid.mulLineCount = 1;   
      GetGrid.displayTitle = 1;
      GetGrid.canChk = 1;
//      DutyGrid.detailClick=queryPayAndGet;
      GetGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
  }

</script>

<%
	// 初始化时的查询责任的函数
	out.println("<script language=javascript>");
	out.println("function queryDuty()");
	out.println("{");
	//输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	String tStr="";
	loggerDebug("ChooseDutyInit","start submit...");
	if (!tRiskCode.equals(""))
		{
		// 责任信息
		LCPolSchema tLCPolSchema = new LCPolSchema();
    loggerDebug("ChooseDutyInit",tRiskCode);
    loggerDebug("ChooseDutyInit",tRiskVersion);
		tLCPolSchema.setRiskCode( tRiskCode );
		tLCPolSchema.setRiskVersion( tRiskVersion );
	
	  	// 准备传输数据 VData
		VData tVData = new VData();
	
		tVData.addElement( tLCPolSchema );
	
	  	// 数据传输
	  	ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
		if (tProposalQueryUI.submitData(tVData,"QUERY||CHOOSEDUTY") == false)
		{
	      Content = " 查询失败，原因是: " + tProposalQueryUI.mErrors.getError(0).errorMessage;
	      FlagStr = "Fail";
	      loggerDebug("ChooseDutyInit",Content);
		}
		else
		{
			tVData.clear();
			tVData = tProposalQueryUI.getResult();
			int i,iMax;
			iMax=tVData.size();
			//返回的数据的顺序为：
      //查询责任描述lmduty
      //查询责任关系表lmdutychoose
      //查询责任交费表lmdutypay
      //查询责任给付总表lmdutyget
      //查询责任给付（生存领取表）lmdutyalive
      //查询责任给付（案件领取表）lmdutygetclm
      //查询交费、给付、责任项是否可以修改表lmdutyctrl
      for (i=0;i<iMax;i++)
      {
        tStr=(String)tVData.get(i);
        out.println("arrStrReturn["+tStr.valueOf(i)+"]=\""+tStr.trim()+"\";");
      }
		} // end of if
	} // end of if
	out.println("}");
	out.println("</script>");
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
%>
