<%
//程序名称：ProposalQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">

function initForm()
{
    initInpBox();
    initCustomerGrid();
    initPolGrid();
    initDisplayButton();
    initQuery();
    initManageCom(); //初始化管理机构，最长截取6位
    
}

function initDisplayButton()
{
    tDisplay = <%=tDisplay%>;
    //alert(tDisplay);
    if(tDisplay == "0")
    {
        fm.Return.style.display='none';
        fm.ReturnBack.style.display='none';
    }
    else if(tDisplay=="1"||tDisplay=="2")
    {
        fm.Return.style.display='';
        fm.ReturnBack.style.display='';
    }
}

function initQuery()
{
    try
    {
        //alert("asdfsdaf"+top.opener.document.all('ContNo'));
        var tContNo = top.opener.document.all('ContNo').value;
        //alert(tContNo);
        if (tContNo!=""&&tContNo!=null)
        {
            document.all('ContNo').value = tContNo;
            easyQueryClick();
        }
     }
     catch(ex)
     {
     }
}
// 输入框的初始化（单记录部分）
function initInpBox()
{

  try
  {
    // 保单查询条件
      document.all('IdCardNo').value = '';
      document.all('CustomerNo').value = '';
      document.all('ContNo').value = '';
      document.all('ManageCom').value = '';
      document.all('AgentCode').value = '';
      document.all('AgentGroup').value = '';
      document.all('GrpContNo').value = '';
      document.all('ProposalContNo').value = '';
      document.all('AppntName').value = '';
      document.all('InsuredNo').value = '';
      document.all('InsuredName').value = '';
      document.all('PayLocation').value = '';
  }
  catch(ex)
  {
      alert("在 AllProposalQueryInit.jsp --> InitInpBox 函数中发生异常:初始化界面错误!");
  }
}

// 客户信息列表的初始化
function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                  //列宽
        iArray[0][2]=30;                      //列最大值
        iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="客户号";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户属性";
        iArray[2][1]="50px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="30px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="100px";
        iArray[5][2]=150;
        iArray[5][3]=8;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="130px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        CustomerGrid = new MulLineEnter("document", "CustomerGrid");
        //这些属性必须在loadMulLine前
        CustomerGrid.mulLineCount = 5;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.locked = 1;
        //CustomerGrid.canSel = 1;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
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
        iArray[0][2]=30;                      //列最大值
        iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保单号码";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="印刷号";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="投保人";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="集体保单号";
        iArray[4][1]="130px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="投保单号";
        iArray[5][1]="0px";
        iArray[5][2]=150;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="被保人号";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="被保人姓名";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        PolGrid = new MulLineEnter("document", "PolGrid");
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 5;
        PolGrid.displayTitle = 1;
        PolGrid.locked = 1;
        PolGrid.canSel = 1;
        PolGrid.hiddenPlus = 1;
        PolGrid.hiddenSubtraction = 1;
        PolGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
