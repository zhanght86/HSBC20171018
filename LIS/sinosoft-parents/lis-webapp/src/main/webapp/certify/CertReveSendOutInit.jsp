<%
//程序名称：CertReveSendOutInit.jsp
//程序功能：
//创建日期：2002-10-08
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	String strOperator = globalInput.Operator;
	String strCurDate = PubFun.getCurrentDate();
	
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
  	fm.reset();
    fm.Handler.value = '<%= strOperator %>';    
    fm.HandleDate.value = '<%= strCurDate %>';
    
    fm.Operator.value = '<%= strOperator %>';
    fm.curTime.value = '<%= strCurDate %>';
  }
  catch(ex)
  {
    alert("在CertReveSendOutInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("CertReveSendOutInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 单证列表的初始化
function initCertifyList()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="50px";        				//列宽
      iArray[0][2]=50;          				//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

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
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=180;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="起始单号|INT&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="终止单号";    	    //列名
      iArray[4][1]="180px";            		//列宽
      iArray[4][2]=180;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="终止单号|INT&NOTNULL";

      iArray[5]=new Array();
      iArray[5][0]="数量";    	        //列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="回退原因";    	    //列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=50;            			//列最大值
      iArray[6][3]=0;              			//表示是代码选择
      iArray[6][9]="回退原因|NOTNULL";
      iArray[6][10]="Reason";			//回退原因
      iArray[6][11]="0|^1|本级申请|^2|上级调拨";
      iArray[6][19]= 1 ; //1是需要强制刷新
      
      CertifyList = new MulLineEnter( "document" , "CertifyList" ); 
      //这些属性必须在loadMulLine前
      CertifyList.displayTitle = 1;
      CertifyList.loadMulLine(iArray);  
      
    } catch(ex) {
      alert(ex);
    }
}

</script>
