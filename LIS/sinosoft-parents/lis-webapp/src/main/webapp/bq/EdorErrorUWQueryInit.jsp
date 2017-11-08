<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorErrorUWQueryInit.jsp
//程序功能：保全核保查询
//创建日期：2005-07-21 19:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                              
    document.all('EdorNo').value =top.opener.document.all('EdorNo').value;

  }
  catch(ex)
  {
    alert("在EdorErrorUWQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}



function initForm()
{
  try
  {
    initInpBox();
		initEdorMainGrid();
		initInfoGrid();
		queryEdorMain();
		showApproveInfo();
				
  }
  catch(re)
  {
    alert("在EdorErrorUWQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//批单信息初始化
function initEdorMainGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="初次核保编码";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="核保信息";         		//列名
      iArray[2][1]="400px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保全受理号";         		//列名
      iArray[3][1]="145px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="批单号";         		//列名
      iArray[4][1]="145px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[5]=new Array();
      iArray[5][0]="批改项目";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="保单号";         		//列名
      iArray[6][1]="145px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      


      
      EdorMainGrid = new MulLineEnter( "document" , "EdorMainGrid" ); 
      //这些属性必须在loadMulLine前
      EdorMainGrid.mulLineCount = 5;   
      EdorMainGrid.displayTitle = 1;
      EdorMainGrid.locked = 1;
      EdorMainGrid.canSel = 0;
      EdorMainGrid.canChk = 0;
      EdorMainGrid.hiddenPlus = 1;
      EdorMainGrid.hiddenSubtraction = 1;
      EdorMainGrid.loadMulLine(iArray);       
      }
      catch(ex)
      { 
        alert(ex);
      }
}

function initInfoGrid() //初始化保全信息队列
    {
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                              //列宽
      iArray[0][2]=30;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全受理号";
      iArray[1][1]="145px";
      iArray[1][2]=150;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="经办人";
      iArray[2][1]="70px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="审批人";
      iArray[3][1]="70px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="审批日期";
      iArray[4][1]="80px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="修改原因";
      iArray[5][1]="120px";
      iArray[5][2]=150;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="审批意见";
      iArray[6][1]="490px";
      iArray[6][2]=300;
      iArray[6][3]=0;


      InfoGrid = new MulLineEnter("document", "InfoGrid");
      //这些属性必须在loadMulLine前
      InfoGrid.mulLineCount = 5;
      InfoGrid.displayTitle = 1;
      InfoGrid.locked = 1;
      InfoGrid.canSel = 0;
      InfoGrid.canChk = 0;
      InfoGrid.hiddenPlus = 1;
      InfoGrid.hiddenSubtraction = 1;
      InfoGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面

    }
    catch(ex)
    {
        alert(ex);
    }
}



</script>
