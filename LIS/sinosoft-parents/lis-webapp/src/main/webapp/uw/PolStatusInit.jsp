<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PolStatusInit.jsp
//程序功能：保单状态查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<script src="../common/javascript/Common.js" type=""></script>
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript" type="">

// 输入框的初始化（单记录部分）
function initInpBox()
{

  try
  {
	// 保单查询条件
	//alert(PrtNo);
	if(PrtNo == null||PrtNo == 'null')
  { 
   //alert(1);
   document.all('ProposalContNo').value = '';
  }
  else
  {
    //alert(2);
    document.all('ProposalContNo').value = PrtNo;
  }
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
    initSelBox();
    initPolGrid();
    initPolStatuGrid();
    if(PrtNo != null&&PrtNo != 'null')
    {
     easyQueryClick();
     getStatus();
    }
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="印刷号(未签单)/合同号(已签单)";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="总单印刷号";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="印刷号";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="管理机构";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[5]=new Array();
      iArray[5][0]="代理人编码";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="投保人";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.canChk = 0;
      PolGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert();
      }
}

// 保单状态列表的初始化
function initPolStatuGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="状态明细";         		//列名
      iArray[1][1]="300px";           		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

			iArray[2]=new Array();
      iArray[2][0]="操作员";         		//列名
      iArray[2][1]="100px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PolStatuGrid = new MulLineEnter( "fm" , "PolStatuGrid" );
      //这些属性必须在loadMulLine前
      PolStatuGrid.mulLineCount = 5;
      PolStatuGrid.displayTitle = 1;
      PolStatuGrid.locked = 1;
      PolStatuGrid.canSel = 0;
      PolStatuGrid.canChk = 0;
      PolStatuGrid.hiddenPlus = 1;
      PolStatuGrid.hiddenSubtraction = 1;
      PolStatuGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //PolStatuGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert();
      }
}
</script>
