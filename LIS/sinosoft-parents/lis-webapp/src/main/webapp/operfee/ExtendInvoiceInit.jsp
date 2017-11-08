<%
//程序名称：ExtendInvoiceInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
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
	if(session.getAttribute("AutoCheckon")!=null && !session.getAttribute("AutoCheckon").equals("null"))
	{
	}
	else
	{
	  session.setAttribute("AutoCheckon","");
	}
  String ContNo = request.getParameter("OtherNo");
  String TempFeeNo = request.getParameter("TempFeeNo");
	String strOperator = globalInput.Operator;
	String strManagecom = globalInput.ManageCom;

%>
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('ContNo').value = '<%=ContNo%>';
    document.all('GetNoticeNo').value = '<%=TempFeeNo%>';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('ChequNo').value = '<%=session.getAttribute("AutoCheckon")%>';
    if (document.all('GetNoticeNo').value == 'null')
    document.all('GetNoticeNo').value = '';
    if (document.all('ContNo').value == 'null')
    document.all('ContNo').value = '';
		document.all('PrintType').value = '01';
    document.all('PrintTypeName').value = '正常发票打印';
  }
  catch(ex)
  {
    alert("在ExtendInvoiceInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


function initManageCom()
{
  try
  {
  	<!--	var QueryCode = "select code from ldcode where codetype = 'XQCheck' and trim(comcode) = '" + (<%=strManagecom%>+'').substring(1,6) + "'"; -->
  		var QueryCode = "";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("operfee.ExtendInvoiceInitSql");
		mySql1.setSqlId("ExtendInvoiceInitSql1");
		mySql1.addSubPara((<%=strManagecom%>+'').substring(1,6));
		QueryCode = mySql1.getString();
			var tResult = easyExecSql(QueryCode);
			if(tResult == null)
			{
				<!--	QueryCode = "select code from ldcode where codetype = 'XQCheck' and trim(comcode) = '" + (<%=strManagecom%>+'').substring(1,4) + "'"; -->
					var mySql2 = new SqlClass();
					mySql2.setResourceName("operfee.ExtendInvoiceInitSql");
					mySql2.setSqlId("ExtendInvoiceInitSql2");
					mySql2.addSubPara((<%=strManagecom%>+'').substring(1,4));
					strSql = mySql2.getString();
					QueryCode = easyExecSql(QueryCode);
					if(tResult == null)
					{
							alert("系统中没有该登陆机构的续期单证类型。");
							return false;
					}
			}

			document.all('CertifyCode').value = tResult[0][0];

	}
	catch(ex)
	{
  		alert("在ExtendInvoiceInit.jsp-->initManageCom函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
  try
  {
   //初始化单证的管理机构
   	initManageCom();
    initContGrid();
    initInpBox();
  }
  catch(re)
  {
    alert("ExtendInvoiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initContGrid()
  {
    var iArray = new Array();

      try
      {
     iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="合同号";         		//列名
    iArray[1][1]="120px";            	//列宽
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


    iArray[2]=new Array();
    iArray[2][0]="交费通知书号";         		//列名
    iArray[2][1]="120px";            	//列宽
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


    iArray[3]=new Array();
    iArray[3][0]="交费日期";         		//列名
    iArray[3][1]="60px";            	//列宽
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="缴费金额";         		//列名
    iArray[4][1]="0px";            	//列宽
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  iArray[5]=new Array();
    iArray[5][0]="流水号";         		//列名
    iArray[5][1]="60px";            	//列宽
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
    iArray[6]=new Array();
    iArray[6][0]="payno";         		//列名
    iArray[6][1]="0px";            	//列宽
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="payno";         		//列名
    iArray[7][1]="0px";            	//列宽
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="managecom";         		//列名
    iArray[8][1]="0px";            	//列宽
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ContGrid = new MulLineEnter( "fm" , "ContGrid" );
      //这些属性必须在loadMulLine前
      ContGrid.mulLineCount = 3;
      ContGrid.displayTitle = 1;
      ContGrid.locked = 1;
      ContGrid.canSel = 1;
      ContGrid.canChk = 0;
      ContGrid.loadMulLine(iArray);  
      //ContGrid.selBoxEventFuncName = "easyQueryAddClick";
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
