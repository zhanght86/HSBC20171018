<%
//程序名称：PEdorTypeFMInit.jsp
//程序功能：个人保全-缴费方式及期限变更
//创建日期：2005-05-16 09:05:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">

function initForm()
{
    try
    {
        initInpBox();
        initPolGrid();
        getPolGrid();
        QueryEdorInfo();
    }
    catch (ex)
    {
        alert("在 PEdorTypeFMInit.jsp --> initForm 函数中发生异常:初始化界面错误！ ");
    }
}

function initInpBox()
{
    try
    {
        EdorQuery();
        fm.EdorAcceptNo.value   = top.opener.fm.EdorAcceptNo.value;
        fm.EdorType.value       = top.opener.fm.EdorType.value;
        fm.EdorNo.value         = top.opener.fm.EdorNo.value;
        fm.ContNo.value         = top.opener.fm.ContNo.value;
        fm.InsuredNo.value      = top.opener.fm.InsuredNo.value;
        fm.PolNo.value          = top.opener.fm.PolNo.value;
        fm.EdorItemAppDate.value    = top.opener.fm.EdorItemAppDate.value;
    }
    catch (ex)
    {
        alert("在 PEdorTypeFMInit.jsp --> initInpBox 函数中发生异常:初始化界面错误！ ");
    }
}

function initPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="险种号";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="投保人";
        iArray[4][1]="0px";
        iArray[4][2]=100;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="被保人";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="投保年龄";
        iArray[6][1]="60px";
        iArray[6][2]=100;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="保费标准";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=7;
        iArray[7][23]="0";
        
        iArray[8]=new Array();
        iArray[8][0]="交费年期";
        iArray[8][1]="60px";
        iArray[8][2]=100;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="保额";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=7;
        iArray[9][23]="0";
        
        iArray[10]=new Array();
				iArray[10][0]="币种";
				iArray[10][1]="60px";
				iArray[10][2]=100;
				iArray[10][3]=2;
				iArray[10][4]="currency";

        PolGrid = new MulLineEnter("fm", "PolGrid");
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 0;
        PolGrid.displayTitle = 1;
        PolGrid.canSel =1;
        PolGrid.selBoxEventFuncName ="displayPolGrid" ;     //点击RadioBox时响应的JS函数
        PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypeFMInit.jsp --> initPolGrid 函数中发生异常:初始化界面错误！ ");
    }
}

</script>