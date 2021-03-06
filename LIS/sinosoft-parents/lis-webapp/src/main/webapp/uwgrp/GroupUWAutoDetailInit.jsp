<%
//程序名称：GroupUWAutoDetailInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    fm.all('PrtNo').value = '';
    fm.all('ProposalGrpContNo').value = '';
    fm.all('ManageCom').value = '';
    fm.all('AgentCode').value = '';
    fm.all('AgentGroup').value = '';
    fm.all('RiskCode').value = '';
    //fm.all('RiskVersion').value = '';
  }
  catch(ex)
  {
    alert("在GroupUWAutoDetailInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在GroupUWAutoDetailInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    //initInpBox();
    //initSelBox(); 
	initGrpPolGrid();
	easyQueryClick()
  }
  catch(re)
  {
    alert("GroupUWAutoDetailInitt.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initGrpPolGrid()
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
      iArray[1][0]="集体投保单号";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="印刷号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="投保人名称";         		//列名
      iArray[4][1]="300px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="投保人客户号";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      iArray[6]=new Array();
      iArray[6][0]="保费";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
     
      GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
      //这些属性必须在loadMulLine前
      GrpPolGrid.mulLineCount = 0;   
      GrpPolGrid.displayTitle = 1;
      GrpPolGrid.locked = 1;
      GrpPolGrid.canSel = 1;
      GrpPolGrid.hiddenPlus = 1;
      GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
