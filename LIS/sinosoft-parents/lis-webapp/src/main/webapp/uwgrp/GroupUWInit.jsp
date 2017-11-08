<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupUWInit.jsp
//程序功能：集体人工核保
//创建日期：2002-06-19 11:10:36
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
    fmQuery.GrpProposalContNo.value = '';
//    fmQuery.GrpMainProposalNo.value = GrpMainPolNo;
    fmQuery.PrtNoHide.value = PrtNo;
    fmQuery.Operator.value = '<%= strOperator %>';    
  }
  catch(ex)
  {
    alert("在ContUWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 保单基本信息显示框的初始化（单记录部分）
function initPolBox()
{ 

  try
  {                                   
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
    //initInpBox();
    //initPolBox();
    //initPolGrid();
    fmQuery.GrpContNo.value = GrpContNo;
    initGrpGrid();
  
		initGrpPolFeeGrid();       
	
    querygrp();
    //tongmeng 2009-04-10 add
		//控制按钮灰暗
		//alert('1')
		ctrlButtonDisabled(GrpContNo,'a');
    showInfo1(); //原来是showInfo() 会与showInfo对象冲突,有时刷新会报错 应该是这个原因 
  
    makeWorkFlow();
    checkPerFHButton();
   
   //alert(fmQuery.SaleChnl.value);
   //if(fmQuery.SaleChnl.value=="04" || fmQuery.SaleChnl.value=="06" || fmQuery.SaleChnl.value=="07")
   //{
   //  fmQuery.fee.disabled='';
   //}else{
   //  fmQuery.fee.disabled=true;
   //}
   }
  catch(re)
  {
    alert("ContUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initPolGrid()
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
      iArray[1][0]="个人投保单号";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保人";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种版本";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="被保人";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="管理机构";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PolGrid = new MulLineEnter( "fmQuery" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
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
      iArray[1][0]="投保单号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="投保单印刷号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保单位";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种编码";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      //iArray[5]=new Array();
      //iArray[5][0]="核保结论";         		//列名
      //iArray[5][1]="60px";            		//列宽
      //iArray[5][2]=200;            			//列最大值
      //iArray[5][3]=2;  
      //iArray[5][10]="condtion";
      //iArray[5][11]="0|^1|拒保^s|上浮费率承保^x|下浮费率承保^r|特约承保－责任调整^m|特约承保－免赔额调^p|特约承保－赔付比例调整^q|特约承保－其它调整^9|标准承保";               			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="核保结论";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=2;  
      iArray[5][10]="condtion";
      iArray[5][11]="0|^1|拒保^4|通融承保^9|正常承保^5|自核不通过^s|上浮费率承保^x|下浮费率承保^r|特约承保－责任调整^m|特约承保－免赔额调^p|特约承保－赔付比例调整^q|特约承保－其它调整^z|核保订正类别";       

      iArray[6]=new Array();
      iArray[6][0]="管理机构";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[7]=new Array();
      iArray[7][0]="集体险种号码";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
       
        
      GrpGrid = new MulLineEnter( "fmQuery" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount = 3;   
      GrpGrid.displayTitle = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.selBoxEventFuncName = "getfee";          
      GrpGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //GrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
function initGrpPolFeeGrid()
{
      var iArray = new Array();
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="应交保费";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="应交保费";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="折扣费率";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="币种";         		    //列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      GrpPolFeeGrid = new MulLineEnter( "fmQuery" , "GrpPolFeeGrid" ); 
      GrpPolFeeGrid.mulLineCount = 1;   
      GrpPolFeeGrid.displayTitle = 1;
      GrpPolFeeGrid.hiddenPlus = 1;
      GrpPolFeeGrid.hiddenSubtraction = 1;
      GrpPolFeeGrid.locked = 1;
      GrpPolFeeGrid.canSel = 0;
      GrpPolFeeGrid.loadMulLine(iArray);    
}

</script>
