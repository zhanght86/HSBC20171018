<%
//Creator :ln
//Date :2008-06-30
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.bl.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
%>
<script language="JavaScript">
function init()
{
  try
  {
    
    document.all('checkRate').value = '';
    document.all('checkMax').value = '';
    document.all('BussNo').value = '';
    document.all('ManageCom').value = '';

  }
  catch(ex)
  {
    alert("ProposalSpotCheckInit.jsp——()初始化失败");
  }
}
;

//function RegisterDetailClick(cObj)
//{
  	//var ex,ey;
  	//ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	//ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	//divDetailInfo.style.left=ex;
  	//divDetailInfo.style.top =ey;
    //divDetailInfo.style.display ='';
//}

var CheckGrid;
// 财务单信息列表的初始化
function initCheckGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="外包公司";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="queryBPOID";              	//是否引用代码:null||""为不引用

      iArray[2]=new Array();
      iArray[2][0]="管理机构";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="comcode";              	//是否引用代码:null||""为不引用

      iArray[3]=new Array();
      iArray[3][0]="抽检比例";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="抽检上限";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="备注";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      CheckGrid = new MulLineEnter( "fm" , "CheckGrid" ); 
      //这些属性必须在loadMulLine前
      CheckGrid.mulLineCount = 0;   
      CheckGrid.displayTitle = 1;
      CheckGrid.locked = 0;
      CheckGrid.canSel = 1;
      CheckGrid.hiddenPlus = 0;
      CheckGrid.hiddenSubtraction = 1;
      CheckGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initForm()
{
  try
  {
    init();
  //  initCheckGrid();
  }
  catch(re)
  {
    alert("ProposalSpotCheckInit.jsp——InitForm()初始化失败");
  }
}

</script>


