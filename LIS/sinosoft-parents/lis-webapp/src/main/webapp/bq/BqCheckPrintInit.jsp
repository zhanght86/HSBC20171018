<%
//程序名称：BqCheckPrintInit.jsp.jsp
//程序功能：通知书打印
//创建日期：2005-08-03 15:39:06
//创建人  ：liurx
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
	String OtherNo = request.getParameter("OtherNo");
	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
	if(comcode.length < 6)
	{
	    alert("登陆机构为总公司或分公司，不能进行发票打印！");
	    return false;
	}
	document.all('ManageCom').value = comcode.substring(0,6);	
//	var QueryCode = "select code from ldcode where codetype = 'bqcheck' and trim(comcode) = '" + comcode.substring(1,4) + "'";
	
	 var  comcode7 = comcode.substring(1,4);
	 var sqlid7="BqCheckPrintSql7";
	 var mySql7=new SqlClass();
	 mySql7.setResourceName("bq.BqCheckPrintSql");
	 mySql7.setSqlId(sqlid7);//指定使用SQL的id
	 mySql7.addSubPara(comcode7);//指定传入参数
	 var QueryCode = mySql7.getString();
	
	var tResult = easyExecSql(QueryCode);
	if(tResult == null)
	{
	    alert("查询该登陆机构所用保全发票代码失败！");
	    return false;
	}
	document.all('CertifyCode').value = tResult[0][0];
	
	document.all('InvoiceType').value ="BQ35";            //默认打个人发票
	document.all('InvoiceName').value ="保全个人收据";     
	document.all('ChequeType').value ="BQ35";               
  }
  catch(ex)
  {
    alert("在BqCheckPrintInit.jsp.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
     document.all('EdorAcceptNo').value = '<%=OtherNo%>';
     if (document.all('EdorAcceptNo').value == 'null')
     document.all('EdorAcceptNo').value = '';
  }
  catch(ex)
  {
    alert("在BqCheckPrintInit.jsp.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initNoticeGrid();
    initManageCom(); //初始化管理机构，最长截取6位   
  }
  catch(re)
  {
    alert("BqCheckPrintInit.jsp.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 var NoticeGrid;          //定义为全局变量，提供给displayMultiline使用
// 投保单信息列表的初始化
function initNoticeGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="通知书号";         		//列名
	  iArray[1][1]="0px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
  
	  iArray[2]=new Array();
    iArray[2][0]="保全受理号";       		//列名
	  iArray[2][1]="120px";            	//列宽
	  iArray[2][2]=100;            			//列最大值
	  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

    iArray[3]=new Array();
	  iArray[3][0]="保单号";        //列名
	  iArray[3][1]="120px";            	//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	    
	    
	  iArray[4]=new Array();
	  iArray[4][0]="批改项目";        //列名
	  iArray[4][1]="80px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
	  iArray[5][0]="管理机构";        //列名
	  iArray[5][1]="80px";            	//列宽
	  iArray[5][2]=100;            		//列最大值
	  iArray[5][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	    
	  iArray[6]=new Array();
	  iArray[6][0]="业务员姓名";        //列名
	  iArray[6][1]="80px";            	//列宽
    iArray[6][2]=100;            		//列最大值
    iArray[6][3]=0; 					//是否允许输入,1表示允许，0表示不允许

	
      NoticeGrid = new MulLineEnter( "fm" , "NoticeGrid" ); 
      //这些属性必须在loadMulLine前
      NoticeGrid.mulLineCount = 10;   
      NoticeGrid.displayTitle = 1;
      NoticeGrid.canSel = 1;
      NoticeGrid.hiddenPlus=1;
      NoticeGrid.hiddenSubtraction=1;
      NoticeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
