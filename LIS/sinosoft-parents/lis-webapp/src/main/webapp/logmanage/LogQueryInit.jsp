<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
//返回按钮初始化
var str = "";
// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
	// 日志查询条件
	
    fm.all('OtherNo').value = '';
    fm.all('OtherNoType').value = '';
    fm.all('IsPolFlag').value = '';    
    fm.all('PrtNo').value = '';
    fm.all('ManageCom').value = '';
    fm.all('Operator').value = '';    
    fm.all('MakeDate').value = '';
    fm.all('MakeTime').value = '';
    fm.all('ModifyDate').value = '';    
    fm.all('ModifyTime').value = '';
    
    fm.all('tOtherNo').value = '';
    fm.all('tOtherNoType').value = '';
    fm.all('tIsPolFlag').value = '';    
    fm.all('tPrtNo').value = '';
    fm.all('tManageCom').value = '';
    fm.all('tOperator').value = '';    
    fm.all('tMakeDate').value = '';
    fm.all('tMakeTime').value = '';
    fm.all('tModifyDate').value = '';    
    fm.all('tModifyTime').value = '';    
    
  }
  catch(ex)
  {
    alert("在LogQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LogQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initLogGrid();
  }
  catch(re)
  {
    alert("LogQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 日志信息列表的初始化
function initLogGrid()
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
      iArray[1][0]="其它号码";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许     

      iArray[2]=new Array();
      iArray[2][0]="其它号码类型";         		//列名
      iArray[2][1]="70px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许     

      iArray[3]=new Array();
      iArray[3][0]="印刷号码";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="入机日期";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="最后一次修改日期";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      LogGrid = new MulLineEnter( "fm" , "LogGrid" ); 
      //这些属性必须在loadMulLine前
      LogGrid.mulLineCount = 10;   
      LogGrid.displayTitle = 1;
      LogGrid.locked = 1;
      LogGrid.canSel = 1;
      LogGrid.selBoxEventFuncName ="getLogDetail" ;     //点击RadioBox时响应的JS函数     
      LogGrid.hiddenPlus = 1;
      LogGrid.hiddenSubtraction = 1;
      LogGrid.loadMulLine(iArray);       
    
      //这些操作必须在loadMulLine后面
      //LogGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
