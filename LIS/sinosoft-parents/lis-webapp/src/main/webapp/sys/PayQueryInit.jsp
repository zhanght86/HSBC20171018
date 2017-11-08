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
    initPayGrid();
    //initQuery();
}
function initQuery()
{
    initQueryGo();
}


// 保单信息列表的初始化
function initPayGrid()
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
        iArray[1][0]=" 暂交费收据号码";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="交费金额";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="投保人名称";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="收费银行帐号";
        iArray[4][1]="130px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="收费银行帐户名";
        iArray[5][1]="80px";
        iArray[5][2]=150;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="证件号码";
        iArray[6][1]="120px";
        iArray[6][2]=150;
        iArray[6][3]=0;

		iArray[7]=new Array();
        iArray[7][0]="交费日期";
        iArray[7][1]="80px";
        iArray[7][2]=150;
        iArray[7][3]=0;

        PayGrid = new MulLineEnter("fm", "PayGrid");
        //这些属性必须在loadMulLine前
        PayGrid.mulLineCount = 5;
        PayGrid.displayTitle = 1;
        PayGrid.locked = 1;
        PayGrid.canSel = 0;
        PayGrid.hiddenPlus = 1;
        PayGrid.hiddenSubtraction = 1;
        PayGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
