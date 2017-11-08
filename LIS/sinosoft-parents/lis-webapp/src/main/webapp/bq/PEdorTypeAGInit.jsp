<%
//程序名称：PEdorTypeAGInit.jsp
//程序功能：
//创建日期：2005-6-16
%>
<!--用户校验类-->


<script language="JavaScript">

function initForm()
{
    try
    {
        Edorquery();
        initInputBox();
        initPolGrid();
        initBonusGrid();
        queryPolInfo();
        queryCustomerInfo();
        queryPolGrid();
        queryBonusGrid();
        queryBankInfo();
    }
    catch (ex)
    {
        alert("在 PEdorTypeAGInit.jsp --> initForm 上面函数中发生异常！ ");
    }
}

function initInputBox()
{
    try
    {
        document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
        document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
        document.all('EdorType').value = top.opener.document.all('EdorType').value;
        document.all('ContNo').value = top.opener.document.all('ContNo').value;
        document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
        document.all('PolNo').value = top.opener.document.all('PolNo').value;
        document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
        document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
        document.all('AppObj').value = top.opener.document.all('AppObj').value;
        showOneCodeName('EdorType','EdorType','EdorTypeName');
    }
    catch (ex)
    {
        alert("在 PEdorTypeAGInit.jsp --> initInputBox 中部函数中发生异常！ ");
    }
    try
    {
        if (fm.AppObj.value == "G")
        {
            divAppntInfo.style.display = "none";
        }
    }
    catch (ex)
    {
        alert("在 PEdorTypeAGInit.jsp --> initInputBox 中部函数中发生异常！ ");
    }
}

//险种信息列表
function initPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                  //列宽
        iArray[0][2]=30;                     //列最大值
        iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="领取项目";
        iArray[1][1]="170px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="给付期数";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        iArray[2][21]=3;

        iArray[3]=new Array();
        iArray[3][0]="给付金额";
        iArray[3][1]="120px";
        iArray[3][2]=150;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="给付通知书号码";
        iArray[4][1]="0px";
        iArray[4][2]=0;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="责任编码";
        iArray[5][1]="120px";
        iArray[5][2]=150;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="给付责任编码";
        iArray[6][1]="0px";
        iArray[6][2]=0;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="给付责任类型";
        iArray[7][1]="0px";
        iArray[7][2]=0;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="险种编码";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="给付分类";
        iArray[9][1]="120px";
        iArray[9][2]=150;
        iArray[9][3]=0;

        PolGrid = new MulLineEnter("fm", "PolGrid");
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 0;
        PolGrid.displayTitle = 1;
        PolGrid.canChk=0;
        PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        PolGrid.hiddenSubtraction=1;
        PolGrid.selBoxEventFuncName ="" ;
        PolGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert("在 PEdorTypeAGInit.jsp --> initPolGrid 函数中发生异常: 初始化界面错误！");
    }
}

//红利信息列表
function initBonusGrid()
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
        iArray[1][0]="红利领取日期";
        iArray[1][1]="160px";
        iArray[1][2]=200;
        iArray[1][3]=0;
        iArray[1][21]=3;

        iArray[2]=new Array();
        iArray[2][0]="会计年度";
        iArray[2][1]="160px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="给付金额";
        iArray[3][1]="160px";
        iArray[3][2]=200;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="给付分类";
        iArray[4][1]="160px";
        iArray[4][2]=200;
        iArray[4][3]=0;

        BonusGrid = new MulLineEnter("fm", "BonusGrid");
        //这些属性必须在loadMulLine前
        BonusGrid.mulLineCount = 0;
        BonusGrid.displayTitle = 1;
        BonusGrid.canChk=0;
        BonusGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        BonusGrid.hiddenSubtraction=1;
        BonusGrid.selBoxEventFuncName ="";
        BonusGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypeAGInit.jsp --> initPolGrid 函数中发生异常: 初始化界面错误！");
    }
}

</script>