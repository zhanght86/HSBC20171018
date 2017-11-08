<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupContDeleteInit.jsp
//程序功能：团单整单删除
//创建日期：2004-12-06 11:10:36
//创建人  ：Zhangrong
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {
    // 保单查询条件  
    fm.all('QGrpProposalNo').value = '';
    fm.all('QGrpContNo').value = '';
    fm.all('QManageCom').value = '';
    fm.all('QAgentCode').value = '';
    fm.all('QState').value = '未签单';
    fm.all('QPrtNo').value = '';                                         
  }
  catch(ex)
  {
    alert("在inGroupContDeleteInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 保单基本信息显示框的初始化（单记录部分）
function initPolBox()
{ 
  try
  {                                   
	// 保单查询条件

  }
  catch(ex)
  {
    alert("在ContUWInit.jsp-->InitPolBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
 
    initGrpGrid();
  }
  catch(re)
  {
    alert("ContUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


// 保单信息列表的初始化
function initGrpGrid()
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
      iArray[1][0]="个人合同号码";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="被保险人客户号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保险人姓名";         		//列名
      iArray[3][1]="160px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;  
      
      iArray[4]=new Array();
      iArray[4][0]="性别";         		//列名
      iArray[4][1]="160px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="出生日期";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[6]=new Array();
      iArray[6][0]="证件类型";         		//列名
      iArray[6][1]="120px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[7]=new Array();
      iArray[7][0]="证件号码";         		//列名
      iArray[7][1]="120px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      
              			

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount = 0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 0;
      GrpGrid.canChk = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.loadMulLine(iArray);
           
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
