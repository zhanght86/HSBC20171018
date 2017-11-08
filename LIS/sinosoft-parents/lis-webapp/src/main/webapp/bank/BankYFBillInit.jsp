<%
//程序名称：BankYSBillInit.jsp
//程序功能：
//创建日期：2003-3-26
//创建人  ：刘岩松
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
	GlobalInput tGlobalInput = new GlobalInput(); 
	tGlobalInput = (GlobalInput)session.getValue("GI");
%>                              

<script language="JavaScript">
var comCode = "<%=tGlobalInput.ComCode%>";
//单击时查询
function RegisterDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	divDetailInfo.style.left=ex;
  	divDetailInfo.style.top =ey;
    divDetailInfo.style.display ='';
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("在BankYSBillInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在BankYSBillInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
		initBillGrid();
  }
  catch(re)
  {
    alert("BankYSBillInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 报案信息列表的初始化
function initBillGrid()
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
      iArray[1][0]="批次号";    	//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=150;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="银行代码";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=150;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

　　　	iArray[3]=new Array();
      iArray[3][0]="总金额";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="总数量";         			//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      BillGrid = new MulLineEnter( "fm" , "BillGrid" ); 
      //这些属性必须在loadMulLine前
      BillGrid.mulLineCount = 10;   
      BillGrid.displayTitle = 1;
      BillGrid.canSel=1;
      BillGrid.locked = 1;
      BillGrid.loadMulLine(iArray);  
      BillGrid.detailInfo="单击显示详细信息";
      BillGrid.detailClick=RegisterDetailClick;
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
