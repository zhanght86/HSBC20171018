<%
//程序名称：AllGetQueryEdorDetailInit.jsp
//程序功能：
//创建日期：2002-12-16
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    document.all('ActuGetNo').value = tActuGetNo;
    document.all('SumGetMoney').value = tSumGetMoney;
  }
  catch(ex)
  {
    alert("在AllGetQueryEdorDetailInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
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
    alert("在AllGetQueryEdorDetailInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
		initPolGrid();
  }
  catch(re)
  {
    alert("AllGetQueryEdorDetailInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var PolGrid; 
// 保单信息列表的初始化
function initPolGrid()
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
      iArray[1][0]="实付号码";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批单号码";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号码";         		//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保单类型";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="补/退费金额";         		//列名
      iArray[5][1]="150px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="补/退费日期";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

			iArray[7]=new Array();
      iArray[7][0]="补/退费标志";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
			iArray[8]=new Array();
      iArray[8][0]="补/退费业务类型";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0; 									//是否允许输入,1表示允许，0表示不允许      

			iArray[9]=new Array();
      iArray[9][0]="补/退费财务类型";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0; 									//是否允许输入,1表示允许，0表示不允许      

			iArray[10]=new Array();
      iArray[10][0]="险种编码";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0; 									//是否允许输入,1表示允许，0表示不允许      
      
      iArray[11]=new Array();
      iArray[11][0]="单位名称";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0; 									//是否允许输入,1表示允许，0表示不允许      
       
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 0;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
