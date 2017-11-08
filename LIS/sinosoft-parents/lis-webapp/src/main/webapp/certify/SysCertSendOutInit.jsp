<%
//程序名称：SysCertSendOutInit.jsp
//程序功能：
//创建日期：2002-08-07
//创建人  ：周平
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try {                                   
    fm.reset();
    
  } catch(ex) {
    alert("在SysCertSendOutInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try {
    initInpBox();
    initCertifyList();
    
  } catch(ex) {
    alert("SysCertSendOutInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    
  }
}

// 单证号码列表的初始化
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
      iArray[1][0]="单证号码"; 	        //列名
      iArray[1][1]="280px";            		//列宽
      iArray[1][2]=280;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][9]="单证号码|INT";

      iArray[2]=new Array();
      iArray[2][0]="有效日期";    	    //列名
      iArray[2][1]="280px";            		//列宽
      iArray[2][2]=280;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][9]="有效日期|DATE";     //日期字段的校验

      CertifyList = new MulLineEnter( "document" , "CertifyList" );
      //这些属性必须在loadMulLine前
	  CertifyList.mulLineCount =5;
	  CertifyList.canSel = 1;
	 
      CertifyList.displayTitle = 1;
      CertifyList.loadMulLine(iArray);
      CertifyList.delBlankLine("CertifyList");
	  
	
	  
	  
    } catch(ex) {
      alert(ex);
    }
}
</script>
