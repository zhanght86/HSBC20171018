<%
//程序名称：AllPBqQueryInit.jsp
//程序功能：
//创建日期：2002-12-16
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        String sYesterday = PubFun.calDate(PubFun.getCurrentDate(), -1, "D", null);
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sManageCom = tGlobalInput.ManageCom;
        tGlobalInput = null;
    %>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{

  try
  {
    // 保单查询条件
//    if (tflag=="0")
 //     document.all('PolNo').value = tPolNo;
//      alert(tPolNo);
//    else
//    {
//      document.all('PolNo').value = '';
//      document.all('EdorNo').value = '';
//      document.all('EdorType').value = '';
//      document.all('RiskCode').value = '';
//      document.all('InsuredNo').value = '';
//      document.all('EdorAppDate').value = '';
//      document.all('ManageCom').value = '<%--=tG.ManageCom--%>';

        document.all('EdorAcceptNo').value = '';
        document.all('OtherNo').value = '';
        document.all('OtherNoType').value = '';
        document.all('EdorAppName').value = '';
        document.all('AppType').value = '';
        document.all('EdorAppDate').value = '';
        document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
//    }
  }
  catch(ex)
  {
    alert("在AllPBqQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在AllPBqQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initPolGrid();
    //initManageCom(); //初始化管理机构，最长截取6位
        //if(tflag=="0") easyQueryClick();
  }
  catch(re)
  {
    alert("AllGBqQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    alert(re);
  }
}

// 保单信息列表的初始化
function initPolGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=10;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="受理号码";
      iArray[1][1]="130px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="客户/保单号";
      iArray[2][1]="130px";
      iArray[2][2]=100;
      iArray[2][3]=0;

//      iArray[3]=new Array();
//      iArray[3][0]="险种编码";
//      iArray[3][1]="80px";
//      iArray[3][2]=100;
//      iArray[3][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="号码类型";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="申请人姓名";
      iArray[4][1]="0px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="补/退费金额";
      iArray[5][1]="80px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="受理人";
      iArray[6][1]="0px";
      iArray[6][2]=100;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="确认人";
      iArray[7][1]="0px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="批改状态";
      iArray[8][1]="80px";
      iArray[8][2]=100;
      iArray[8][3]=0;

      iArray[9]=new Array();
      iArray[9][0]="操作岗位";
      iArray[9][1]="120px";
      iArray[9][2]=100;
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="操作员";
      iArray[10][1]="80px";
      iArray[10][2]=100;
      iArray[10][3]=0;

      iArray[11]=new Array();
      iArray[11][0]="批改类型";
      iArray[11][1]="120px";
      iArray[11][2]=100;
      iArray[11][3]=0;

      iArray[12]=new Array();
      iArray[12][0]="入机日期";
      iArray[12][1]="0px";
      iArray[12][2]=100;
      iArray[12][3]=3;

      iArray[13]=new Array();
      iArray[13][0]="入机时间";
      iArray[13][1]="0px";
      iArray[13][2]=100;
      iArray[13][3]=3;

      iArray[14]=new Array();
      iArray[14][0]="受理机构";
      iArray[14][1]="140px";
      iArray[14][2]=100;
      iArray[14][3]=0;

      PolGrid = new MulLineEnter("fm", "PolGrid");
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 10;
      PolGrid.displayTitle = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);
      PolGrid. selBoxEventFuncName = "PBqQueryClick";

      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
