<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：UWAutoInit.jsp
	//程序功能：个人自动核保
	//创建日期：2002-06-19 11:10:36
	//创建人  ：WHN
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
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
    document.all('ManageCom').value = '';
    document.all('ContNo').value = '';
    document.all('AgentCode').value = '';
    document.all('RiskCode').value = '';
    document.all('SecPayMode').value = '';
    document.all('ContType').value = '';
  }
  catch(ex)
  {
    alert("在ProposalQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在ProposalQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
   initInpBox();
   //initContGrid();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initContGrid()
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
      iArray[1][0]="保单号码";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[2]=new Array();
	  iArray[2][0]="投保人";         		//列名
	  iArray[2][1]="160px";            		//列宽
	  iArray[2][2]=100;            			//列最大值
	  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
      //这些属性必须在loadMulLine前
      ContGrid.mulLineCount = 0;   
      ContGrid.displayTitle = 1;
      ContGrid.locked = 1;
      ContGrid.canSel = 0;
      ContGrid.canChk = 0;
      ContGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
