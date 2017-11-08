
<%
	//**************************************************************************************************
	//Name：ClaimRgtConclusionInit.jsp
	//Function：立案结论打印
	//Author：mw
	//Date:   2009-04-22
	//**************************************************************************************************
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                        
    document.all('ManageCom').value = '<%=tG.ManageCom%>';
  }
  catch(ex)
  {
    alert("在ClaimRgtConclusionInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
	//setOption("t_sex","0=男&1=女&2=不详");       
  }
  catch(ex)
  {
    alert("在ClaimRgtConclusionInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
   try
  {
    initInpBox();
    initSelBox(); 
 	initQueryResultGrid();
 	//easyQueryClick()
   }	
  catch(re)
  {
    alert("EndorGrpQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initQueryResultGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="立案号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户号码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="客户姓名";         		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="立案结论编码";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="立案结论";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="结案日期";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="投保单位";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      QueryResultGrid = new MulLineEnter( "fm" , "QueryResultGrid" ); 
      QueryResultGrid.mulLineCount = 10;   
      QueryResultGrid.displayTitle = 1;
      QueryResultGrid.locked = 0;
      QueryResultGrid.canSel = 1;
      QueryResultGrid.hiddenPlus=1;
	  QueryResultGrid.hiddenSubtraction=1;
      QueryResultGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
