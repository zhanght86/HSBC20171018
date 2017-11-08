
<%
	//程序名称：单证入库,添加页面控件的初始化。 CertifyGetInInit.jsp
	//程序功能：单证印刷后、单证发放到下级机构时，需要接收机构做单证入库操作
	//创建日期：2009-01-04
	//创建人  ：mw
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	String strOperator = globalInput.Operator;
	String managecom = globalInput.ManageCom;
	String strCurTime = PubFun.getCurrentDate();
%>

<script language="JavaScript">
function initForm()
{
  try
  {
    initInpBox();
    initCertifyListGrid();
  }
  catch(re)
  {
    alert("CertifyGetInInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{
  try
  {
  	fm.reset();
  	fm.managecom.value = '<%= managecom %>';
    fm.Operator.value = '<%= strOperator %>';
    fm.OperateDate.value = '<%= strCurTime %>';
    fm.operateFlag.value = "";//操作类型标志，INSERT为确认入库，CANCEL为拒绝入库
  } catch(ex) {
    alert("在CertifyGetInInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


// 待入库单证列表的初始化
function initCertifyListGrid()
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
      iArray[1][0]="单证编码";     		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="60px";        			//列宽
      iArray[1][2]=80;          			//列最大值
      iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      //iArray[1][4]="CertifyCode";     //是否引用代码:null||""为不引用
      //iArray[1][5]="1|2";             //引用代码对应第几列，'|'为分割符
      //iArray[1][6]="0|1";             //上面的列中放置引用代码中第几位值
      //iArray[1][9]="单证编码|code:CertifyCode&NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="单证名称";     		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[2][1]="120px";        		  //列宽
      iArray[2][2]=180;          			//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="发放机构";     		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[3][1]="80px";        		  //列宽
      iArray[3][2]=180;          			//列最大值
      iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="接收机构";     		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[4][1]="80px";        		  //列宽
      iArray[4][2]=180;          			//列最大值
      iArray[4][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="起始单号";    	    //列名
      iArray[5][1]="150px";            		//列宽
      iArray[5][2]=180;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][9]="起始单号|INT";

      iArray[6]=new Array();
      iArray[6][0]="终止单号";    	    //列名
      iArray[6][1]="150px";            		//列宽
      iArray[6][2]=180;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[6][9]="终止单号|INT";

      iArray[7]=new Array();
      iArray[7][0]="数量";    	        //列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=50;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[7][9]="数量|INT";

      iArray[8]=new Array();
      iArray[8][0]="单证来源";    	        //列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="印刷批次号";    	        //列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=50;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="回退或拒绝入库原因";    	        //列名
      iArray[10][1]="140px";            		//列宽
      iArray[10][2]=50;            			//列最大值
      iArray[10][3]=2;              			//表示是代码选择
      iArray[10][10]="cancelGetInReason";			//回退原因
      iArray[10][11]="0|^1|本级申请^2|上级调拨^3|发放错误^4|单证损毁^5|单证丢失";
      
      iArray[11]=new Array();
      iArray[11][0]="日期";    	        //列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=50;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      CertifyListGrid = new MulLineEnter( "document" , "CertifyListGrid" );
      CertifyListGrid.mulLineCount = 5; 
      CertifyListGrid.displayTitle = 1;
      CertifyListGrid.hiddenSubtraction=1;
	  CertifyListGrid.hiddenPlus=1;
      CertifyListGrid.canSel=0;
      CertifyListGrid.canChk=1;
      CertifyListGrid.loadMulLine(iArray);
    } catch(ex) {
      alert(ex);
    }
}

</script>
