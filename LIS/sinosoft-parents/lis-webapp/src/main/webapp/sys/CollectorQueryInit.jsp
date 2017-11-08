<%
//程序名称：CollectorQueryInit.jsp
//程序功能：收费员基本信息查询
//创建日期：2005-09-30 11:10:36
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
 
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
   document.all('AgentCode').value= nullToEmpty("<%= tAgentCode %>");	
}	

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
  try
  {
	
		initCollectorGrid();
		initParam();
		LLCollectorGrid();
	
  }
  catch(re)
  {
    alert("CollectorQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCollectorGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="30px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

        iArray[1]=new Array();
        iArray[1][0]="业务员编码";         //列名
        iArray[1][1]="80px";         //宽度
        iArray[1][2]=100;         //最大长度
        iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="销售机构";         //列名
        iArray[2][1]="80px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="管理机构";         //列名
        iArray[3][1]="80px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[4]=new Array();
        iArray[4][0]="姓名";         //列名
        iArray[4][1]="60px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[5]=new Array();
        iArray[5][0]="性别";         //列名
        iArray[5][1]="40px";         //宽度
        iArray[5][2]=40;         //最大长度
        iArray[5][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[6]=new Array();
        iArray[6][0]="身份证号";         //列名
        iArray[6][1]="140px";         //宽度
        iArray[6][2]=100;         //最大长度
        iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[7]=new Array();
        iArray[7][0]="状态";         //列名
        iArray[7][1]="40px";         //宽度
        iArray[7][2]=100;         //最大长度
        iArray[7][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[8]=new Array();
        iArray[8][0]="电话";         //列名
        iArray[8][1]="80px";         //宽度
        iArray[8][2]=100;         //最大长度
        iArray[8][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[9]=new Array();
        iArray[9][0]="手机";         //列名
        iArray[9][1]="80px";         //宽度
        iArray[9][2]=100;         //最大长度
        iArray[9][3]=0;         //是否允许录入，0--不能，1--允许

  
        CollectorGrid = new MulLineEnter( "fm" , "CollectorGrid" ); 

        //这些属性必须在loadMulLine前
        CollectorGrid.mulLineCount = 0;   
        CollectorGrid.displayTitle = 1;
        CollectorGrid.canSel=0;
        CollectorGrid.locked=1;
	      CollectorGrid.hiddenPlus=1;
	      CollectorGrid.hiddenSubtraction=1;
        CollectorGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert("初始化CollectorGrid时出错："+ ex);
      }
    }
</script>
