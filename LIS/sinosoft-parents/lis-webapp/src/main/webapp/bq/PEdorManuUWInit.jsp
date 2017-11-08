<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UnderwriteInit.jsp
//程序功能：个人人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
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
    document.all('QEdorNo').value = '';
    document.all('QContNo').value = '';
    document.all('QManageCom').value = '';
    document.all('Operator').value = operator;
    document.all('State').value = '1';
    codeSql="code";
	fm.EdorNo.value = "";
	fm.ContNo.value = "";
  }
  catch(ex)
  {
    alert("在PEdorManuUWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 保单基本信息显示框的初始化（单记录部分）
function initPolBox()
{ 

  try
  {                                   
	// 保单查询条件
    //document.all('EdorNo1').value = '';
    //document.all('ContNo1').value = '';
    //document.all('EdorType').value = '';
    //document.all('RiskCode').value = '';
    //document.all('RiskName').value = '';
    //document.all('PolNo').value = '';
    //document.all('InsuredNo').value = '';
    //document.all('InsuredName').value = '';
    //document.all('AppntNo').value = '';
    //document.all('AppntName').value = '';    
    //document.all('ChgPrem').value = '';
    //document.all('ChgAmnt').value = '';
    //document.all('GetMoney').value = '';
    //document.all('GetInterest').value = '';
    //document.all('ManageCom').value = '';
    //codeSql="code";
  }
  catch(ex)
  {
    alert("在在PEdorManuUWInit.jsp-->InitPolBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolBox();    
    initEdorMainGrid();    
    initEdorItemGrid();
    if(edorAcceptNo!=""){
        initQuery();
    }
    
  }
  catch(re)
  {
    alert("在PEdorManuUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initEdorMainGrid()
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
      iArray[1][0]="保全申请批单号";         		//列名
      iArray[1][1]="110px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="个人合同保单号";         		//列名
      iArray[2][1]="110px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="生效日期";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[4]=new Array();
      iArray[4][0]="柜面受理日期";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="变动保费";         	//列名
      iArray[5][1]="90px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="变动保额";         	//列名
      iArray[6][1]="90px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="补退费金额";         	//列名
      iArray[7][1]="90px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="补退费利息";         		//列名
      iArray[8][1]="70px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="管理机构";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[9][4]="station";              	        //是否引用代码:null||""为不引用
      iArray[9][5]="3";              	                //引用代码对应第几列，'|'为分割符
      iArray[9][9]="管理机构|code:station&NOTNULL";
      iArray[9][18]=250;
      iArray[9][19]= 0 ;

      iArray[10]=new Array();
      iArray[10][0]="工作流任务号";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="工作流子任务号";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
       
      
      EdorMainGrid = new MulLineEnter( "fm" , "EdorMainGrid" ); 
      //这些属性必须在loadMulLine前
      EdorMainGrid.mulLineCount = 3;   
      EdorMainGrid.displayTitle = 1;
      EdorMainGrid.locked = 1;
      EdorMainGrid.canSel = 1;
      EdorMainGrid.hiddenPlus = 1;
      EdorMainGrid.hiddenSubtraction=1;
      EdorMainGrid.loadMulLine(iArray);     
      
      EdorMainGrid. selBoxEventFuncName = "easyQueryAddClick";
      
      //这些操作必须在loadMulLine后面
      //EdorMainGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initEdorItemGrid()
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
		  iArray[1][0]="保全批单号";         		//列名
		  iArray[1][1]="120px";            		//列宽
		  iArray[1][2]=100;            			//列最大值
		  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		  iArray[2]=new Array();
		  iArray[2][0]="个人合同保单号";         		//列名
		  iArray[2][1]="120px";            		//列宽
		  iArray[2][2]=100;            			//列最大值
		  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		  iArray[3]=new Array();
		  iArray[3][0]="保全项目";         		//列名
		  iArray[3][1]="70px";            		//列宽
		  iArray[3][2]=100;            			//列最大值
		  iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		  iArray[4]=new Array();
		  iArray[4][0]="被保人";         		//列名
		  iArray[4][1]="90px";            		//列宽
		  iArray[4][2]=80;            			//列最大值
		  iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		  
		  iArray[5]=new Array();
		  iArray[5][0]="保单险种号";         		//列名
		  iArray[5][1]="90px";            		//列宽
		  iArray[5][2]=80;            			//列最大值
		  iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		  
		  iArray[6]=new Array();
		  iArray[6][0]="核保状态";         	//列名
		  iArray[6][1]="120px";            		//列宽
		  iArray[6][2]=80;            			//列最大值
		  iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		  
		  EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" ); 
		  //这些属性必须在loadMulLine前
		  EdorItemGrid.mulLineCount = 3;   
		  EdorItemGrid.displayTitle = 1;
		  EdorItemGrid.locked = 1;
		  EdorItemGrid.canSel = 1;
		  EdorItemGrid.hiddenPlus = 1;
		  EdorItemGrid.hiddenSubtraction=1;
		  EdorItemGrid.loadMulLine(iArray);       
		  EdorItemGrid.selBoxEventFuncName = "getEdorItemGridCho";
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>