<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PolFeeStatusInit.jsp
//程序功能：投保单状态查询
//创建日期：2003-07-07 11:10:36
//创建人  ：SXY
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
    document.all('ProposalNo').value = '';
    document.all('PrtNo').value = '';
  }
  catch(ex)
  {
    alert("在ProposalQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
}                                        

function initForm()
{
  try
  {
    initInpBox();   
    initSelBox();    
    initPolFeeGrid();
    initPolFeeStatuGrid()
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initPolFeeGrid()
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
      iArray[1][0]="保单号";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="印刷号";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种编码";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[4][5]="4";              	                //引用代码对应第几列，'|'为分割符
      iArray[4][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[4][18]=250;
      iArray[4][19]= 0 ;

      iArray[5]=new Array();
      iArray[5][0]="投保人";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="被保人";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PolFeeGrid = new MulLineEnter( "fm" , "PolFeeGrid" ); 
      //这些属性必须在loadMulLine前
      PolFeeGrid.mulLineCount = 3;   
      PolFeeGrid.displayTitle = 1;
      PolFeeGrid.locked = 1;
      PolFeeGrid.canSel = 1;
      PolFeeGrid.canChk = 0;
      PolFeeGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单状态列表的初始化
function initPolFeeStatuGrid()
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
      iArray[1][0]="投保单交费状态明细";         		//列名
      iArray[1][1]="400px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      PolFeeStatuGrid = new MulLineEnter( "fm" , "PolFeeStatuGrid" ); 
      //这些属性必须在loadMulLine前
      PolFeeStatuGrid.mulLineCount = 3;   
      PolFeeStatuGrid.displayTitle = 1;
      PolFeeStatuGrid.locked = 1;
      PolFeeStatuGrid.canSel = 0;
      PolFeeStatuGrid.canChk = 0;
      PolFeeStatuGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
