<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWErrInit.jsp
//程序功能：人工核保未过原因查询
//创建日期：2005-06-23 15:10:36
//创建人  ：liurongxiao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tContNo = "";
  String tEdorNo = "";
  tContNo = request.getParameter("ContNo");
  tEdorNo = request.getParameter("EdorNo");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 

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
    alert("在EdorUWErrInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo, tEdorNo)
{
  try
  {
	
	initUWErrGrid();
	
	initHide(tContNo,tEdorNo);
	
	easyQueryClick();
	
  }
  catch(re)
  {
    alert("EdorUWErrInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initUWErrGrid()
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
      iArray[1][0]="批单号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="保单号";    	//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="核保顺序号";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="核保意见";         		//列名
      iArray[4][1]="340px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="核保日期";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      UWErrGrid = new MulLineEnter( "fm" , "UWErrGrid" ); 
      //这些属性必须在loadMulLine前
      UWErrGrid.mulLineCount = 10;   
      UWErrGrid.displayTitle = 1;
      UWErrGrid.locked = 1;
      UWErrGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo, tEdorNo)
{   
    
	document.all('ContNo').value=tContNo;
	document.all('EdorNo').value=tEdorNo;
}

</script>


