
<%
	//程序名称：CertifyLossRegisterInit.jsp
	//程序功能：
	//创建日期：2002-08-07
	//创建人  ：周平
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	String strComCode = globalInput.ComCode;
	String strOperator = globalInput.Operator;
	String strOperateDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{
  try
  {
  	fm.reset( );    

    fm.Handler.value = '<%= strOperator %>';
    fm.HandleDate.value = '<%= strOperateDate %>';
    
    fm.Operator.value = '<%= strOperator %>';
    fm.OperateDate.value = '<%= strOperateDate %>';
    
    fm.ComCode.value = '<%= strComCode %>';
    fm.Reason.value = '';
       
    fm.btnOp.disabled = false;
  } catch(ex) {
    alert("在CertifyLossRegisterInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initCertifyList();
  }
  catch(re)
  {
    alert("CertifyLossRegisterInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 起始单号、终止单号信息列表的初始化
function initCertifyList()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="50px";        			//列宽
      iArray[0][2]=50;          			//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="单证编码";     		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="80px";        			//列宽
      iArray[1][2]=80;          			//列最大值
      iArray[1][3]=2;              		//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="CertifyCode";     //是否引用代码:null||""为不引用
      iArray[1][5]="1|2";             //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";             //上面的列中放置引用代码中第几位值
      iArray[1][9]="单证编码|code:CertifyCode&NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="单证名称";     		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[2][1]="180px";        		  //列宽
      iArray[2][2]=180;          			//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="起始单号";    	    //列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=180;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="起始单号|INT&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="终止单号";    	    //列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=180;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="终止单号|INT&NOTNULL";

      iArray[5]=new Array();
      iArray[5][0]="数量";    	        //列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][9]="数量|INT";

      CertifyList = new MulLineEnter( "document" , "CertifyList" );
      CertifyList.displayTitle = 1;
      CertifyList.loadMulLine(iArray);
      CertifyList.delBlankLine("CertifyList");
    } catch(ex) {
      alert(ex);
    }
}

</script>
