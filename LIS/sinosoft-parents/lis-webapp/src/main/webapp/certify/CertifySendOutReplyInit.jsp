
<%
	//程序名称：CertifySendOutInit.jsp
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
        
    fm.SendOutCom.value ='';
    fm.ReceiveCom.value ='';
    fm.note.value = '';
    
    fm.Operator.value = '<%= strOperator %>';
    fm.OperateDate.value = '<%= strOperateDate %>';
    fm.ReplyPerson.value = '<%= strOperator %>';
    fm.ReplyDate.value = '<%= strOperateDate %>';
    fm.ComCode.value = '<%= strComCode %>';
        
    fm.btnOp.disabled = false;
  } catch(ex) {
    alert("在CertifySendOutInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("CertifySendOutInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[0][1]="30px";        			//列宽
      iArray[0][2]=50;          			//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="申请号码";    	    //列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=180;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="单证编码";     		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[2][1]="50px";        			//列宽
      iArray[2][2]=80;          			//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      //iArray[1][4]="CertifyCode";     //是否引用代码:null||""为不引用
      //iArray[1][5]="1|2";             //引用代码对应第几列，'|'为分割符
      //iArray[1][6]="0|1";             //上面的列中放置引用代码中第几位值
      //iArray[1][9]="单证编码|code:CertifyCode&NOTNULL";

      iArray[3]=new Array();
      iArray[3][0]="单证名称";     		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[3][1]="150px";        		  //列宽
      iArray[3][2]=180;          			//列最大值
      iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="发放者库存";    	    //列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=180;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="接收者库存";    	    //列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=180;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="起始单号";    	    //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=180;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[6][9]="起始单号|INT";

      iArray[7]=new Array();
      iArray[7][0]="终止单号";    	    //列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=180;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[7][9]="终止单号|INT";

      iArray[8]=new Array();
      iArray[8][0]="数量";    	        //列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][9]="数量|INT";

      iArray[9]=new Array();
      iArray[9][0]="申请原因";    	    //列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=180;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="代理人编码";    	    //列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=180;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="所在机构";    	    //列名
      iArray[11][1]="120px";            		//列宽
      iArray[11][2]=180;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="操作员";    	    //列名
      iArray[12][1]="80";            		//列宽
      iArray[12][2]=180;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[13]=new Array();
      iArray[13][0]="操作日期";    	    //列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=180;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      CertifyList = new MulLineEnter( "document" , "CertifyList" );
      CertifyList.displayTitle = 1;
      CertifyList.hiddenSubtraction=1;
	  CertifyList.hiddenPlus=1;
      CertifyList.canSel=0;
      CertifyList.canChk=1;
      CertifyList.loadMulLine(iArray);
      CertifyList.delBlankLine("CertifyList");
    } catch(ex) {
      alert(ex);
    }
}

</script>
