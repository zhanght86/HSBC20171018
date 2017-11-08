<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：WithdrawContInit.jsp
//程序功能：撤单初始化
//创建日期：2008-10-18
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                       
    document.all('PrtNo').value = '';  
    document.all('AgentCode').value = '';
    document.all('SaleChnl').value = '';
    document.all('ManageCom').value = '';
    document.all('AppntName').value = '';
    document.all('InsuredName').value = '';
    
    document.all('UWWithDReasonCode').value = '';
    document.all('UWWithDReason').value = '';
    document.all('Content').value = '';
  }
  catch(ex)
  {
    alert("在WithdrawContInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
} 

// 隐藏输入框的初始化
function initHide()
{ 
try
  {                                   
    document.all('PrtNoH').value = '';   
    document.all('ContNoH').value = ''; 
  }
  catch(ex)
  {
    alert("在WithdrawContInit.jsp-->initHide函数中发生异常:初始化界面错误!");
  }   
}                                     

function initForm()
{
  try
  {    
	initInpBox();
	initHide();	
	initWithDContAllGrid();
	initWihtDContGrid();	
	//withdrawQueryClick();
		
  }
  catch(re)
  {
    alert("WithdrawContInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//待撤销投保单详细信息列表
function initWihtDContGrid()
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
      iArray[1][0]="合同号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="0px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="印刷号";    	//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人客户号";         			//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="投保人姓名";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="体检发放";         			//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="生调发放";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=50;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="问题件发放";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="特约";         			//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="加费";         			//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许        
      
      iArray[10]=new Array();
      iArray[10][0]="保险计划变更";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[10][1]="120px";         			//列宽
      iArray[10][2]=10;          			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许       
      
      WihtDContGrid = new MulLineEnter( "document" , "WihtDContGrid" ); 
      //这些属性必须在loadMulLine前                            
      WihtDContGrid.mulLineCount = 5;   
      WihtDContGrid.displayTitle = 1;
      WihtDContGrid.locked = 1;
      WihtDContGrid.canSel = 1;
      WihtDContGrid.hiddenPlus = 1;
      WihtDContGrid.hiddenSubtraction = 1;
      WihtDContGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

//所有待撤销投保单列表
function initWithDContAllGrid()
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
      iArray[1][0]="合同号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="0px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="印刷号";    	//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=130;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人姓名";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="被保人姓名";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="业务员";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[5][1]="120px";         			//列宽
      iArray[5][2]=10;          			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="管理机构";    	//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=130;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="原核保任务状态";         			//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="最后结论时间";         			//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      
      WithDContAllGrid = new MulLineEnter( "document" , "WithDContAllGrid" ); 
      //这些属性必须在loadMulLine前
      WithDContAllGrid.mulLineCount = 5;   
      WithDContAllGrid.displayTitle = 1;
      WithDContAllGrid.locked = 1;
      WithDContAllGrid.canSel = 1;
      WithDContAllGrid.hiddenPlus = 1;
      WithDContAllGrid.hiddenSubtraction = 1;
      WithDContAllGrid.loadMulLine(iArray);       
      WithDContAllGrid.selBoxEventFuncName = "showWihtDContInfo";
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


