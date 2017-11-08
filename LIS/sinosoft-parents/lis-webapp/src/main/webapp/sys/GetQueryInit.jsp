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
    //initInpBox();
    initGetGrid();
    initQuery();
}
function initQuery()
{
    initQueryGo();
}



// 输入框的初始化（单记录部分）




// 保单信息列表的初始化
function initGetGrid()
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
        iArray[1][0]=" 账户名";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="总给付金额";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="领取人";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="领取人身份证号";
        iArray[4][1]="130px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="银行编码";
        iArray[5][1]="80px";
        iArray[5][2]=150;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="银行账户";
        iArray[6][1]="80px";
        iArray[6][2]=150;
        iArray[6][3]=0;


        GetGrid = new MulLineEnter("fm", "GetGrid");
        //这些属性必须在loadMulLine前
        GetGrid.mulLineCount = 5;
        GetGrid.displayTitle = 1;
        GetGrid.locked = 1;
        GetGrid.canSel = 0;
        GetGrid.hiddenPlus = 1;
        GetGrid.hiddenSubtraction = 1;
        GetGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
