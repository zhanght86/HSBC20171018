<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//            XinYQ     2007-06-05
%>


<script language="JavaScript">

function initForm()
{
    try
    {
        Edorquery();
        initInpBox();
        initCustomerGrid();
        initPolGrid();
        initCTFeePolGrid();  
        initCTFeeDetailGrid();
        getCustomerGrid();
        getPolGrid(document.all('ContNo').value);
        chkPol();  //上次被退保的险种打勾
        queryEdorReason();
        queryShouldMoney();   
        queryAdjustMoney();
        getContZTInfo();
        getCTFeePolGrid();
        getCTFeeDetailGrid();
        getAgentToAppntRelation("XT");
        queryLRInfo();

        initCustomerActBnfGrid();
        getCustomerActBnfGrid();
    }
    catch (ex)
    {
        alert("PEdorTypeXTInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+ex);
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
    catch (ex)
    {
        alert("在PEdorTypeXTInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initCustomerGrid()
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
        iArray[1][0]="客户号码";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        iArray[2][21]=2;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=8;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="140px";
        iArray[7][2]=200;
        iArray[7][3]=0;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //这些属性必须在loadMulLine前
        CustomerGrid.mulLineCount = 3;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.canSel=0;
        CustomerGrid.hiddenPlus=1;
        CustomerGrid.hiddenSubtraction=1;
        CustomerGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert(ex);
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
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="险种代码";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="100px";
        iArray[2][2]=400;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人姓名";
        iArray[3][1]="60px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="基本保额";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="保费标准";
        iArray[5][1]="60px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="健康加费";
        iArray[6][1]="60px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][21]=3;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="职业加费";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=7;
        iArray[7][21]=3;
        iArray[7][23]="0";

        iArray[8]=new Array();
        iArray[8][0]="保单号码";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="生效日期";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=8;
        
        iArray[10]=new Array();
        iArray[10][0]="交至日期";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=8;
        
        iArray[11]=new Array();
        iArray[11][0]="主险号码";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;
        
        iArray[12]=new Array();
        iArray[12][0]="币种";
        iArray[12][1]="60px";
        iArray[12][2]=100;
        iArray[12][3]=2;
        iArray[12][4]="currency";
        
        PolGrid = new MulLineEnter( "fm" , "PolGrid" );
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 0;
        PolGrid.displayTitle = 1;
        PolGrid.canChk=1;
        PolGrid.hiddenPlus=1;
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
        PolGrid.chkBoxEventFuncName="checkMainPol";

    }
    catch (ex)
    {
        alert(ex);
    }
}

function initCTFeePolGrid()
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
        iArray[1][0]="险种号";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="80px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="150px";
        iArray[3][2]=400;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="应退合计";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="实退合计";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";
        
        iArray[6]=new Array();
        iArray[6][0]="币种";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=2;
        iArray[6][4]="currency";

        CTFeePolGrid = new MulLineEnter("fm", "CTFeePolGrid");
        //这些属性必须在loadMulLine前
        CTFeePolGrid.mulLineCount = 0;
        CTFeePolGrid.displayTitle = 1;
        CTFeePolGrid.canChk=0;
        CTFeePolGrid.hiddenPlus=1;
        CTFeePolGrid.hiddenSubtraction=1;
        CTFeePolGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert(ex);
    }
}

function initCTFeeDetailGrid()
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
        iArray[1][0]="险种号";
        iArray[1][1]="80px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="120px";
        iArray[3][2]=300;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="费用类型";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="费用名称";
        iArray[5][1]="80px";
        iArray[5][2]=150;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="原费用金额";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][21]=3;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="调整后金额";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=2;
        //iArray[7][7]="formatOldAmnt"
        iArray[7][9]="调整金额|notnull&num&value>0";
        iArray[7][21]=3;
        iArray[7][23]="0";

        iArray[8]=new Array();
        iArray[8][0]="费用类型代码";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="费用名称代码";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="通知书号";
        iArray[10][1]="0px";
        iArray[10][2]=0;
        iArray[10][3]=3;
        
        //getflag控制
        iArray[11]=new Array();
        iArray[11][0]="收/付费";
        iArray[11][1]="60px";
        iArray[11][2]=0;
        iArray[11][3]=0;


        //getflag控制
        iArray[12]=new Array();
        iArray[12][0]="责任编码";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;
        
                //getflag控制
        iArray[13]=new Array();
        iArray[13][0]="缴费计划编码";
        iArray[13][1]="0px";
        iArray[13][2]=0;
        iArray[13][3]=3;
        
        iArray[14]=new Array();
        iArray[14][0]="币种";
        iArray[14][1]="60px";
        iArray[14][2]=100;
        iArray[14][3]=2;
        iArray[14][4]="currency";
        
        CTFeeDetailGrid = new MulLineEnter("fm", "CTFeeDetailGrid");
        CTFeeDetailGrid.mulLineCount = 0;
        CTFeeDetailGrid.displayTitle = 1;
        CTFeeDetailGrid.canChk=0;
        CTFeeDetailGrid.hiddenPlus=1;
        CTFeeDetailGrid.hiddenSubtraction=1;
        CTFeeDetailGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert(ex);
    }
}


function initCustomerActBnfGrid()
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
        iArray[1][0]="姓名";
        iArray[1][1]="93px";
        iArray[1][2]=150;
        iArray[1][3]=1;
        iArray[1][9]="姓名|NotNull&Len<=60";

         iArray[2]=new Array();
        iArray[2][0]="性别";
        iArray[2][1]="30px";
        iArray[2][2]=150;
        iArray[2][3]=2;
        iArray[2][4]="sex";

        iArray[3]=new Array();
        iArray[3][0]="出生日期";
        iArray[3][1]="80px";
        iArray[3][2]=150;
        iArray[3][3]=8;
        iArray[3][9] = "出生日期|Date";

        iArray[4]=new Array();
        iArray[4][0]="证件类型";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3] = 2;
        iArray[4][4] = "IDType";
        iArray[4][9] = "证件类型|NotNull&Code:IDType";

        iArray[5]=new Array();
        iArray[5][0]="证件号码";
        iArray[5][1]="77px";
        iArray[5][2]=100;
        iArray[5][3] = 1;
        iArray[5][9] = "证件号码|NotNull&Len<=20";


//        iArray[6]=new Array();
//        iArray[6][0]="与被保人关系";
//        iArray[6][1]="60px";
//        iArray[6][2]=150;
//        iArray[6][3]=2;
//        iArray[6][4] = "Relation";
//        iArray[6][9] = "与被保人关系|NotNull&Code:Relation"; 
      

        iArray[6] = new Array();
        iArray[6][0] = "领取比例";
        iArray[6][1] = "80px";
        iArray[6][2] = 100;
        iArray[6][3] = 1;
        iArray[6][9] = "领取比例|NotNull&Num&Value>=0&Value<=1";

        iArray[7]=new Array();
        iArray[7][0]="领取方式";
        iArray[7][1]="70px";
        iArray[7][2]=150;
        iArray[7][3]=2;
        iArray[7][9] = "领取方式|NotNull";
        iArray[7][10]="LGGetMode"; //名字最好有唯一性
        iArray[7][11]= "0|^1|现金^4|银行转账" ; 

        iArray[8] = new Array();
        iArray[8][0] = "银行编码";
        iArray[8][1] = "80px";
        iArray[8][2] = 100;
        iArray[8][3] = 2;
        iArray[8][4] = "bank";
        iArray[8][9] = "银行编码|Code:bank";
                        
        iArray[9] = new Array();
        iArray[9][0] = "银行帐号";
        iArray[9][1] = "120px";
        iArray[9][2] = 100;
        iArray[9][3] = 1;
        iArray[9][22]="confirmSecondInput1"
                        
        iArray[10] = new Array();
        iArray[10][0] = "银行帐户名";
        iArray[10][1] = "80px";
        iArray[10][2] = 100;
        iArray[10][3] = 1;
 

        CustomerActBnfGrid = new MulLineEnter( "fm" , "CustomerActBnfGrid" );
      //这些属性必须在loadMulLine前
      CustomerActBnfGrid.mulLineCount = 0;
      CustomerActBnfGrid.displayTitle = 1;
      CustomerActBnfGrid.canSel=0;
      CustomerActBnfGrid.canAdd=1;
      CustomerActBnfGrid.hiddenPlus=0;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CustomerActBnfGrid.hiddenSubtraction=0;
      CustomerActBnfGrid.loadMulLine(iArray);
      CustomerActBnfGrid.selBoxEventFuncName ="" ;
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>