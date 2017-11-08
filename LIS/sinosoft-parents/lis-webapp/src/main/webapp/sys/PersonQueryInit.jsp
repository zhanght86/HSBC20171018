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
    initAddressGrid();
    initAccountGrid();
    initDisplayButton();
    //initQuery();
    //initManageCom();//初始化管理机构，最长截取6位
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
    
      document.all('Name').value = '';
      document.all('Sex').value = '';
      document.all('BIRTHDAY').value = '';
      document.all('IDNo').value = '';
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
        iArray[1][1]="100px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="单位编码";
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
        iArray[5][3]=0;

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

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //这些属性必须在loadMulLine前
        CustomerGrid.mulLineCount = 5;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.locked = 1;
        CustomerGrid.canSel = 1;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.loadMulLine(iArray);
        
        CustomerGrid.selBoxEventFuncName = "QueryClick2";
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 保单信息列表的初始化
function initAddressGrid()
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
        iArray[1][0]="地址号码";
        iArray[1][1]="100px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="通讯地址";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="通讯电话";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="家庭地址";
        iArray[4][1]="120px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="家庭电话";
        iArray[5][1]="80px";
        iArray[5][2]=150;
        iArray[5][3]=0;


		iArray[6]=new Array();
        iArray[6][0]="手机号码";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
		
        AddressGrid = new MulLineEnter("fm", "AddressGrid");
        //这些属性必须在loadMulLine前
        AddressGrid.mulLineCount = 5;
        AddressGrid.displayTitle = 1;
        AddressGrid.locked = 1;
        AddressGrid.canSel = 0;
        AddressGrid.hiddenPlus = 1;
        AddressGrid.hiddenSubtraction = 1;
        AddressGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


// 保单信息列表的初始化
function initAccountGrid()
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
        iArray[1][0]="是否默认账户";
        iArray[1][1]="100px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="银行编码";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="银行帐号";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="银行帐户名";
        iArray[4][1]="130px";
        iArray[4][2]=150;
        iArray[4][3]=0;


        AccountGrid = new MulLineEnter("fm", "AccountGrid");
        //这些属性必须在loadMulLine前
        AccountGrid.mulLineCount = 5;
        AccountGrid.displayTitle = 1;
        AccountGrid.locked = 1;
        AccountGrid.canSel = 0;
        AccountGrid.hiddenPlus = 1;
        AccountGrid.hiddenSubtraction = 1;
        AccountGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
