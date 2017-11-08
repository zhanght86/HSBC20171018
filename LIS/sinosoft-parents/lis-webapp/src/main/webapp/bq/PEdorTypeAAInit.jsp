<%
//PEdorTypeAAInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">
//单击时查询

function initInpBox()
{

  try
  {
    Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeAAInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeAAInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
       initInpBox();
       initCustomerGrid();
       initPolGrid();
       initMainPolGrid();
       getMainPolInfo();
       queryCustomerInfo();
       getPolInfo();
  }
  catch(re)
  {
     alert("PEdorTypeAAInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[0][2]=10;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种代码";                  //列名1
      iArray[1][1]="50px";                  //列宽
      iArray[1][2]=30;                      //列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";                  //列名2
      iArray[2][1]="100px";                 //列宽
      iArray[2][2]=50;                      //列最大值
      iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="原保额/原份数";                 //列名8
      iArray[3][1]="60px";                  //列宽
      iArray[3][2]=20;                      //列最大值
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="原保费";     //列名6
      iArray[4][1]="50px";                  //列宽
      iArray[4][2]=20;                      //列最大值
      iArray[4][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="更新后保额";     //列名6
      iArray[5][1]="60px";                  //列宽
      iArray[5][2]=30;                      //列最大值
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="更新后份数";     //列名6
      iArray[6][1]="50px";                  //列宽
      iArray[6][2]=30;                      //列最大值
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="加保保费";     //列名6
      iArray[7][1]="50px";                  //列宽
      iArray[7][2]=30;                      //列最大值
      iArray[7][3]=0;


      iArray[8]=new Array();
      iArray[8][0]="险种号";     //列名6
      iArray[8][1]="0px";                  //列宽
      iArray[8][2]=30;                     //列最大值
      iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="销售标记(按保额/份数)";     //列名6
      iArray[9][1]="100px";                  //列宽
      iArray[9][2]=30;                     //列最大值
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="销售标记(按保额/份数)";     //列名6
      iArray[10][1]="0px";                  //列宽
      iArray[10][2]=0;                     //列最大值
      iArray[10][3]=3;


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.canSel=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.selBoxEventFuncName ="";
      PolGrid.loadMulLine(iArray);
      PolGrid.detailInfo="单击显示详细信息";
      }
      catch(ex)
      {

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
      iArray[0][2]=10;                      //列最大值
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="客户号码";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="80px";                  //列宽
      iArray[1][2]=10;                      //列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="角色";                  //列名1
      iArray[2][1]="60px";                  //列宽
      iArray[2][2]=50;                      //列最大值
      iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="姓名";                  //列名2
      iArray[3][1]="60px";                  //列宽
      iArray[3][2]=60;                      //列最大值
      iArray[3][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="性别";              //列名8
      iArray[4][1]="60px";                  //列宽
      iArray[4][2]=30;                      //列最大值
      iArray[4][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="出生日期";     //列名6
      iArray[5][1]="60px";                  //列宽
      iArray[5][2]=50;                      //列最大值
      iArray[5][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="证件类型";              //列名8
      iArray[6][1]="100px";                 //列宽
      iArray[6][2]=30;                      //列最大值
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="证件号码";              //列名5
      iArray[7][1]="150px";                 //列宽
      iArray[7][2]=100;                     //列最大值
      iArray[7][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码引用



      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 0;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;
      CustomerGrid.hiddenPlus = 1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.selBoxEventFuncName ="";
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.detailInfo="单击显示详细信息";
      CustomerGrid.loadMulLine(iArray);
      }
      catch(ex)
      {

      }
}

//

function initMainPolGrid()
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
        iArray[1][0]="被保险人号码";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="姓名";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种代码";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="险种名称";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="保费标准";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="基本保额";
        iArray[6][1]="40px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="生效日期";
        iArray[7][1]="50px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="保单号码";
        iArray[8][1]="0px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="保险期间";
        iArray[9][1]="40px";
        iArray[9][2]=100;
        iArray[9][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="交费年期";
        iArray[10][1]="40px";
        iArray[10][2]=100;
        iArray[10][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="交至日期";
        iArray[10][1]="50px";
        iArray[10][2]=100;
        iArray[10][3]=0;

      MainPolGrid = new MulLineEnter( "fm" , "MainPolGrid" );
      //这些属性必须在loadMulLine前
      MainPolGrid.mulLineCount = 0;
      MainPolGrid.displayTitle = 1;
      //MainPolGrid.canChk=1;
      MainPolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      MainPolGrid.hiddenSubtraction=1;
      MainPolGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>