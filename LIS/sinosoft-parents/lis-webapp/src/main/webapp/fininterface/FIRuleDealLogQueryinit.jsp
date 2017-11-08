 <% 
//程序名称：FIRuleDealLogQueryinit.jsp
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">                           

function initInpBox()
{
  try
  {
  	fm.reset();
		document.all('StartDay').value = '';
		document.all('EndDay').value = '';
		document.all('VersionNo').value = VersionNo;
  }
  catch(ex)
  {
    alert("FIRuleDealLogInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
   

function initForm()
{
  try
  {
    initInpBox();
    initFIRuleDealLogGrid();
  }
  catch(re)
  {
    alert("FIRuleDealLogInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//
function initFIRuleDealLogGrid()
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
      iArray[1][0]="版本编号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="校验批次号码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="数据源批次号码";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="校验事件点";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="校验结果";         		//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="校验人";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="校验计划";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="校验日期";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
    
      FIRuleDealLogGrid = new MulLineEnter( "document" , "FIRuleDealLogGrid" ); 
      FIRuleDealLogGrid.mulLineCount = 5;   
      FIRuleDealLogGrid.displayTitle = 1;
      FIRuleDealLogGrid.canSel=1;
      FIRuleDealLogGrid.locked = 1;	
			FIRuleDealLogGrid.hiddenPlus = 1;
			FIRuleDealLogGrid.hiddenSubtraction = 1;
      FIRuleDealLogGrid.loadMulLine(iArray);  
      FIRuleDealLogGrid.detailInfo="单击显示详细信息";
      

      //这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
