<%
//程序名称：UWReportInit.jsp
//程序功能：
//创建日期：2008-10-15 
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">                                      

function initForm()
{
  try
  {    
    if(sQueryFlag == null || sQueryFlag == '')
    {}
    else if(sQueryFlag == '1') //查询
    	fm.Add.disabled = true;
    	
	initParam();
	initReportGrid();
	//alert(1);
	initQuery();
	//alert(2);
  }
  catch(re)
  {
    alert("UWReportInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initReportGrid() {                              
  var iArray = new Array();
    
  try  {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			 //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			    //列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //alert("OperatePos== "+OperatePos+"  !!");
    if(OperatePos == null || OperatePos==''||OperatePos=='null' ||OperatePos == 1)
    {
    iArray[1]=new Array();
    iArray[1][0]="印刷号";    	     //列名
    iArray[1][1]="90px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    }
    if(OperatePos == 3)
    {
    iArray[1]=new Array();
    iArray[1][0]="合同号";    	     //列名
    iArray[1][1]="90px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    }
    if(OperatePos == 4)
    {
    iArray[1]=new Array();
    iArray[1][0]="赔案号";    	     //列名
    iArray[1][1]="90px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    }
    iArray[2]=new Array();
    iArray[2][0]="核保分析报告内容";         			//列名
    iArray[2][1]="280px";            		//列宽
    iArray[2][2]=800;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="录入员";         			//列名
    iArray[3][1]="50px";            		//列宽
    iArray[3][2]=40;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                             
    iArray[4]=new Array();
    iArray[4][0]="录入日期";         			//列名
    iArray[4][1]="80px";            		//列宽
    iArray[4][2]=40;            			//列最大值
    iArray[4][3]=8;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="录入时间";         			//列名
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=50;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="序列号";         			//列名
    iArray[6][1]="0px";            		//列宽
    iArray[6][2]=50;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    ReportGrid = new MulLineEnter( "fm" , "ReportGrid" ); 
    //这些属性必须在loadMulLine前                         
    ReportGrid.mulLineCount = 0;
    ReportGrid.displayTitle = 1;
    ReportGrid.canSel = 1;
    ReportGrid.hiddenPlus = 1;
    ReportGrid.hiddenSubtraction = 1;
    ReportGrid.loadMulLine(iArray); 
    ReportGrid.selBoxEventFuncName = "showContent";
 
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
