<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Supl
//更新记录：  更新人    更新日期     更新原因/内容 
%>
<!--用户校验类-->

<%

%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
    //Edorquery();
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeCTInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeDBInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initCustomerGrid();
    initPolGrid();
    initCustomerBnfGrid();
    initCustomerActBnfGrid();
    getCustomerGrid();
    getCustomerBnfGrid();
    getCustomerActBnfGrid();
  }
  catch(re)
  {
    alert("PEdorTypeDBInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re);
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
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="150px";
        iArray[2][2]=300;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="帐户类型";
        iArray[3][1]="130px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="基本保额";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="保费标准";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="可领金额";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="最大领至日期";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=8;

        iArray[8]=new Array();
        iArray[8][0]="保单号码";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;
        
        
        iArray[9]=new Array();
        iArray[9][0]="帐户编码";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="实际金额（可更改）";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=7;
        iArray[10][9]="实际金额|NotNull&NUM";
        
        //begin zbx 20110517
        iArray[11]=new Array();
				iArray[11][0]="币种";
				iArray[11][1]="60px";
				iArray[11][2]=100;
				iArray[11][3]=2;
				iArray[11][4]="currency";
		//end zbx 20110517

      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.canChk=0;
      PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      
        fm.action="./PEdorTypeLGCount.jsp";
        fm.submit();

      }
      catch(ex)
      {
        alert(ex);
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
        iArray[1][1]="93px";
        iArray[1][2]=150;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="92px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="98px";
        iArray[3][2]=150;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="77px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="77px";
        iArray[5][2]=100;
        iArray[5][3]=8;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="115px";
        iArray[6][2]=150;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="115px";
        iArray[7][2]=150;
        iArray[7][3]=0;

      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 3;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;
      //PolGrid.canChk=1;
      CustomerGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CustomerGrid.hiddenSubtraction=1;
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.selBoxEventFuncName ="" ;
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initCustomerBnfGrid()
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
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="性别";
        iArray[2][1]="60px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="出生日期";
        iArray[3][1]="70px";
        iArray[3][2]=150;
        iArray[3][3]=8;

        iArray[4]=new Array();
        iArray[4][0]="证件类型";
        iArray[4][1]="77px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="证件号码";
        iArray[5][1]="77px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="受益人类型";
        iArray[6][1]="115px";
        iArray[6][2]=150;
        iArray[6][3]=0;
        

        iArray[7]=new Array();
        iArray[7][0]="与被保人关系";
        iArray[7][1]="80px";
        iArray[7][2]=150;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="受益顺序";
        iArray[8][1]="50px";
        iArray[8][2]=150;
        iArray[8][3]=0;
        

        iArray[9]=new Array();
        iArray[9][0]="受益比例";
        iArray[9][1]="50px";
        iArray[9][2]=150;
        iArray[9][3]=0;

        iArray[10] = new Array();
        iArray[10][0] = "银行编码";
        iArray[10][1] = "80px";
        iArray[10][2] = 100;
        iArray[10][3] = 0;
      
                        
        iArray[11] = new Array();
        iArray[11][0] = "银行帐号";
        iArray[11][1] = "120px";
        iArray[11][2] = 100;
        iArray[11][3] = 0;
                        
        iArray[12] = new Array();
        iArray[12][0] = "银行帐户名";
        iArray[12][1] = "80px";
        iArray[12][2] = 100;
        iArray[12][3] = 0;

        CustomerBnfGrid = new MulLineEnter( "fm" , "CustomerBnfGrid" );
      //这些属性必须在loadMulLine前
      CustomerBnfGrid.mulLineCount = 0;
      CustomerBnfGrid.displayTitle = 1;
      CustomerBnfGrid.canSel=0;
      //PolGrid.canChk=1;
      CustomerBnfGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CustomerBnfGrid.hiddenSubtraction=1;
      CustomerBnfGrid.loadMulLine(iArray);
      CustomerBnfGrid.selBoxEventFuncName ="" ;
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
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


        iArray[6]=new Array();
        iArray[6][0]="与被保人关系";
        iArray[6][1]="60px";
        iArray[6][2]=150;
        iArray[6][3]=2;
        iArray[6][4] = "Relation";
        iArray[6][9] = "与被保人关系|NotNull&Code:Relation"; 
      

        iArray[7] = new Array();
        iArray[7][0] = "领取比例";
        iArray[7][1] = "80px";
        iArray[7][2] = 100;
        iArray[7][3] = 1;
        iArray[7][9] = "领取比例|NotNull&Num&Value>=0&Value<=1";

        iArray[8]=new Array();
        iArray[8][0]="领取方式";
        iArray[8][1]="70px";
        iArray[8][2]=150;
        iArray[8][3]=2;
        iArray[8][9] = "领取方式|NotNull";
        iArray[8][10]="LGGetMode1"; //名字最好有唯一性
        iArray[8][11]= "0|^1|现金^4|银行转账^9|网银代付" ; 

        iArray[9] = new Array();
        iArray[9][0] = "银行编码";
        iArray[9][1] = "80px";
        iArray[9][2] = 100;
        iArray[9][3] = 2;
        iArray[9][4] = "bank";
        iArray[9][9] = "银行编码|Code:bank";
                        
        iArray[10] = new Array();
        iArray[10][0] = "银行帐号";
        iArray[10][1] = "120px";
        iArray[10][2] = 100;
        iArray[10][3] = 1;
        iArray[10][22]="confirmSecondInput1"
                        
        iArray[11] = new Array();
        iArray[11][0] = "银行帐户名";
        iArray[11][1] = "80px";
        iArray[11][2] = 100;
        iArray[11][3] = 1;
 

        CustomerActBnfGrid = new MulLineEnter( "fm" , "CustomerActBnfGrid" );
      //这些属性必须在loadMulLine前
      CustomerActBnfGrid.mulLineCount = 3;
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