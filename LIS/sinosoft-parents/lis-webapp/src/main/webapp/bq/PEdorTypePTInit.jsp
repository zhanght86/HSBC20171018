
<script language="JavaScript">

function initForm()
{
    try
    {
        Edorquery();
        initInpBox();
        initPolGrid();
        initCustomerGrid();
        queryCustomerInfo();
        getPolInfo();
        QueryRiskDetail();
        queryLRInfo();
    }
    catch (ex)
    {
        alert("在 PEdorTypePTInit.jsp --> initForm 函数中发生异常:初始化界面错误！");
    }
}

function initInpBox()
{
    try
    {
        document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
        document.all('ContNo').value = top.opener.document.all('ContNo').value;
        document.all('PolNo').value = top.opener.document.all('PolNo').value;
        document.all('EdorType').value = top.opener.document.all('EdorType').value;
        document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
        document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
        document.all('InsuredNo').value =  top.opener.document.all('InsuredNo').value;
        document.all('Flag266').value = "NO";
        document.all('Flag267').value = "NO";
        document.all('SubScale').value = "NO";
        try {document.all('AppObj').value = top.opener.document.all('AppObj').value; } catch(ex){}
        showOneCodeName('EdorType','EdorType','EdorTypeName');
    }
    catch (ex)
    {
        alert("在 PEdorTypePTInit.jsp --> initInpBox 函数中发生异常:初始化界面错误！");
    }
}

//客户基本信息列表
function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                  //列宽
        iArray[0][2]=30;                      //列最大值
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="90px";
        iArray[2][2]=50;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="90px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="80px";
        iArray[4][2]=30;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=8;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="150px";
        iArray[7][2]=200;
        iArray[7][3]=0;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //这些属性必须在loadMulLine前
        CustomerGrid.mulLineCount = 0;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.canSel=0;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.selBoxEventFuncName = "";
        CustomerGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypePTInit.jsp --> initCustomerGrid 函数中发生异常:初始化界面错误！");
    }
}

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
        iArray[1][0]="险种代码";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="180px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人姓名";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="原保额/原份数";
        iArray[4][1]="90px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="原保费";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="减保后保额/份数";
        iArray[6][1]="90px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][21]=3;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="减保后份数";
        iArray[7][1]="0px";
        iArray[7][2]=0;
        iArray[7][3]=7;
        iArray[7][21]=3;
        iArray[7][23]="0";

        iArray[8]=new Array();
        iArray[8][0]="减保后保费";
        iArray[8][1]="90px";
        iArray[8][2]=100;
        iArray[8][3]=7;
        iArray[8][21]=3;
        iArray[8][23]="0";

        iArray[9]=new Array();
        iArray[9][0]="被保人号码";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="险种号";
        iArray[10][1]="0px";
        iArray[10][2]=0;
        iArray[10][3]=3;

        iArray[11]=new Array();
        iArray[11][0]="销售标记";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;
        
        //add by jiaqiangli 2008-11-20 PT区分标记
        iArray[12]=new Array();
        iArray[12][0]="PT区分标记";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="币种";
        iArray[13][1]="60px";
        iArray[13][2]=100;
        iArray[13][3]=2;
        iArray[13][4]="currency";

        PolGrid = new MulLineEnter("fm", "PolGrid");
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 0;
        PolGrid.displayTitle = 1;
        PolGrid.canSel=1;
        PolGrid.hiddenPlus = 1;
        PolGrid.hiddenSubtraction = 1;
        PolGrid.selBoxEventFuncName ="queryEdorInfo";
        PolGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypePTInit.jsp --> initPolGrid 函数中发生异常:初始化界面错误！");
    }
}

</script>
