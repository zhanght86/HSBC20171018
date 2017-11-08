<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSubGInit.jsp
//程序功能：核保轨迹查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
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
  String tPolNo = "";
  tContNo = request.getParameter("ContNo");
  tPolNo = request.getParameter("PolNo");
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
    alert("在UWSubGInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo, tPolNo)
{
  try
  {
	initUWSubGrid();
	initHide(tContNo, tPolNo);
	easyQueryClick();
  }
  catch(re)
  {
    alert("UWSubGInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initUWSubGrid()
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
      iArray[1][0]="合同投保单号";    	//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";    	//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="核保顺序号";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="核保结论";         			//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][10] = "End";
      iArray[4][11] = "0|^1|拒保^2|延期^3|条件承保^4|通融承保^5|自动核保未通过^6|待上级核保^8|发核保通知书^9|正常承保^a|撤销投保单^b|保险计划变更^z|核保订正";
//      iArray[4][12] = "3";
//      iArray[4][13] = "1";


      iArray[5]=new Array();
      iArray[5][0]="核保意见";         		//列名
      iArray[5][1]="240px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="核保人";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="核保日期";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      UWSubGrid = new MulLineEnter( "fm" , "UWSubGrid" ); 
      //这些属性必须在loadMulLine前
      UWSubGrid.mulLineCount = 10;   
      UWSubGrid.displayTitle = 1;
      UWSubGrid.locked = 1;
      UWSubGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //UWSubGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo, tPolNo)
{
	document.all('ContNo').value = tContNo;
	document.all('PolNo').value = tPolNo;
}

</script>

<%
	// 初始化时的查询责任的函数
	//out.println("<script language=javascript>");
	//out.println("function queryUWSub()");
	//out.println("{");
	//out.println("}");
	//out.println("</script>");
%>

