<%
//程序名称：PEdorTypeRBInit.jsp
//更新记录：  更新人    更新日期     更新原因/内容
%>


<script language="JavaScript">

function initForm()
{
    try
    {
        EdorQuery();
        initInpBox();
        initEdorItemGrid();
        initLastEdorItemGrid();
        getEdorItemGrid();
        getLastEdorItemGrid();
        queryEdorReason();
    }
    catch (ex)
    {
        alert("在 PEdorTypeRBInit.jsp --> initForm 函数中发生异常:初始化界面错误！");
    }
}

function initInpBox()
{
    try
    {
        document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
        document.all('ContNo').value = top.opener.document.all('ContNo').value;
        document.all('EdorType').value = top.opener.document.all('EdorType').value;
        document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
        document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
        document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
        showOneCodeName('PEdorType','EdorType','EdorTypeName');
    }
    catch(ex)
    {
        alert("在 PEdorTypeRBInit.jsp --> initInpBox 函数中发生异常:初始化界面错误！");
    }
}

function initEdorItemGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保全受理号";
        iArray[1][1]="110px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="批单号";
        iArray[2][1]="0px";
        iArray[2][2]=0;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="批改类型";
        iArray[3][1]="60px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="批改类型显示级别";
        iArray[4][1]="0px";
        iArray[4][2]=0;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="集体合同号码";
        iArray[5][1]="0px";
        iArray[5][2]=0;
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="保单号码";
        iArray[6][1]="100px";
        iArray[6][2]=150;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="客户号码";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="保单险种号码";
        iArray[8][1]="110px";
        iArray[8][2]=150;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="柜面受理日期";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=8;
        iArray[9][21]=3;

        iArray[10]=new Array();
        iArray[10][0]="生效日期";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=8;
        iArray[10][21]=3;

        iArray[11]=new Array();
        iArray[11][0]="申请原因";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;

        iArray[12]=new Array();
        iArray[12][0]="申请原因代码";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="补退费金额";
        iArray[13][1]="60px";
        iArray[13][2]=100;
        iArray[13][3]=7;
        iArray[13][21]=3;
        iArray[13][23]="0";

        iArray[14]=new Array();
        iArray[14][0]="补退费利息";
        iArray[14][1]="60px";
        iArray[14][2]=100;
        iArray[14][3]=7;
        iArray[14][21]=3;
        iArray[14][23]="0";

        iArray[15]=new Array();
        iArray[15][0]="生成具体时间";
        iArray[15][1]="0px";
        iArray[15][2]=0;
        iArray[15][3]=3;

        iArray[16]=new Array();
        iArray[16][0]="最后保存时间";
        iArray[16][1]="0px";
        iArray[16][2]=0;
        iArray[16][3]=3;

        iArray[17]=new Array();
        iArray[17][0]="最后处理人";
        iArray[17][1]="0px";
        iArray[17][2]=0;
        iArray[17][3]=3;

        iArray[18]=new Array();
        iArray[18][0]="处理状态";
        iArray[18][1]="0px";
        iArray[18][2]=0;
        iArray[18][3]=3;

        iArray[19]=new Array();
        iArray[19][0]="处理状态编码";
        iArray[19][1]="0px";
        iArray[19][2]=0;
        iArray[19][3]=3;

        iArray[20]=new Array();
        iArray[20][0]="批改类型编码";
        iArray[20][1]="0px";
        iArray[20][2]=0;
        iArray[20][3]=3;
        
        iArray[21]=new Array();
				iArray[21][0]="币种";
				iArray[21][1]="60px";
				iArray[21][2]=100;
				iArray[21][3]=2;
				iArray[21][4]="currency";

        EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
        //这些属性必须在loadMulLine前
        EdorItemGrid.mulLineCount = 0;
        EdorItemGrid.displayTitle = 1;
        EdorItemGrid.canSel = 0;
        EdorItemGrid.hiddenPlus=1;
        EdorItemGrid.hiddenSubtraction=1;
        EdorItemGrid.selBoxEventFuncName ="getEdorItemDetail";
        EdorItemGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypeRBInit.jsp --> initEdorItemGrid 函数中发生异常:初始化界面错误！");
    }
}

function initLastEdorItemGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保全受理号";
        iArray[1][1]="110px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="批单号";
        iArray[2][1]="0px";
        iArray[2][2]=0;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="批改类型";
        iArray[3][1]="60px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="批改类型显示级别";
        iArray[4][1]="0px";
        iArray[4][2]=0;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="集体合同号码";
        iArray[5][1]="0px";
        iArray[5][2]=0;
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="保单号码";
        iArray[6][1]="100px";
        iArray[6][2]=150;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="客户号码";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="保单险种号码";
        iArray[8][1]="110px";
        iArray[8][2]=150;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="柜面受理日期";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=8;
        iArray[9][21]=3;

        iArray[10]=new Array();
        iArray[10][0]="生效日期";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=8;
        iArray[10][21]=3;

        iArray[11]=new Array();
        iArray[11][0]="申请原因";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;

        iArray[12]=new Array();
        iArray[12][0]="申请原因代码";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="补退费金额";
        iArray[13][1]="60px";
        iArray[13][2]=100;
        iArray[13][3]=7;
        iArray[13][21]=3;
        iArray[13][23]="0";

        iArray[14]=new Array();
        iArray[14][0]="补退费利息";
        iArray[14][1]="60px";
        iArray[14][2]=100;
        iArray[14][3]=7;
        iArray[14][21]=3;
        iArray[14][23]="0";

        iArray[15]=new Array();
        iArray[15][0]="生成具体时间";
        iArray[15][1]="0px";
        iArray[15][2]=0;
        iArray[15][3]=3;

        iArray[16]=new Array();
        iArray[16][0]="最后保存时间";
        iArray[16][1]="0px";
        iArray[16][2]=0;
        iArray[16][3]=3;

        iArray[17]=new Array();
        iArray[17][0]="最后处理人";
        iArray[17][1]="0px";
        iArray[17][2]=0;
        iArray[17][3]=3;

        iArray[18]=new Array();
        iArray[18][0]="处理状态";
        iArray[18][1]="0px";
        iArray[18][2]=0;
        iArray[18][3]=3;

        iArray[19]=new Array();
        iArray[19][0]="处理状态编码";
        iArray[19][1]="0px";
        iArray[19][2]=0;
        iArray[19][3]=3;

        iArray[20]=new Array();
        iArray[20][0]="批改类型编码";
        iArray[20][1]="0px";
        iArray[20][2]=0;
        iArray[20][3]=3;
        
        iArray[21]=new Array();
				iArray[21][0]="币种";
				iArray[21][1]="60px";
				iArray[21][2]=100;
				iArray[21][3]=2;
				iArray[21][4]="currency";

        LastEdorItemGrid = new MulLineEnter("fm", "LastEdorItemGrid");
        //这些属性必须在loadMulLine前
        LastEdorItemGrid.mulLineCount = 0;
        LastEdorItemGrid.displayTitle = 1;
        LastEdorItemGrid.canSel =0;
        LastEdorItemGrid.hiddenPlus=1;
        LastEdorItemGrid.hiddenSubtraction=1;
        LastEdorItemGrid.selBoxEventFuncName ="getEdorItemDetail" ;
        LastEdorItemGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypeRBInit.jsp --> initLastEdorItemGrid 函数中发生异常:初始化界面错误！");
    }
}

</script>
