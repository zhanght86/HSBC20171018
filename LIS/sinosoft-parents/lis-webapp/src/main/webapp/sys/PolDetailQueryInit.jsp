<%
//程序名称：PolQueryInit.jsp
//程序功能：
//创建日期：2002-12-16
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
 <%
    //添加页面控件的初始化。
    GlobalInput globalInput = (GlobalInput)session.getValue("GI");
    if(globalInput == null) {
        out.println("session has expired");
        return;
    }
    String strOperator = globalInput.Operator;
%>
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{

  try
  {
    // 保单查询条件
  //  document.all('CustomerNo').value = tCustomerNo;
  //  document.all('Name').value = tName;
      document.all('ContNo').value = tContNo;

  }
  catch(ex)
  {
    alert("在PolDetailQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox()
{
  try
  {
//    setOption("t_sex","0=男&1=女&2=不详");
//    setOption("sex","0=男&1=女&2=不详");
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");
  }
  catch(ex)
  {
    alert("在PolDetailQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initPolGrid();
    initInsuredGrid();
    easyQueryClick();
    //returnParent();
    initContAndAppnt();
    QueryGetPolDate();
  }
  catch(re)
  {
    alert("PolDetailQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.message);
  }
}

var PolGrid;
// 保单信息列表的初始化
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
      iArray[1][0]="保单集体险种号";                //列名
      iArray[1][1]="120px";                 //列宽
      iArray[1][2]=100;                     //列最大值
      iArray[1][3]=3;                       //是否允许输入,1表示允许，0表示不允许


      iArray[2]=new Array();
      iArray[2][0]="保单个人险种号码";              //列名
      iArray[2][1]="120px";                 //列宽
      iArray[2][2]=100;                     //列最大值
      iArray[2][3]=3;                       //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保单号码";                //列名
      iArray[3][1]="100px";                 //列宽
      iArray[3][2]=100;                     //列最大值
      iArray[3][3]=3;                       //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="投保人姓名";                //列名
      iArray[4][1]="80px";                  //列宽
      iArray[4][2]=100;                     //列最大值
      iArray[4][3]=3;                       //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="被保人姓名";                //列名
      iArray[5][1]="80px";                  //列宽
      iArray[5][2]=200;                     //列最大值
      iArray[5][3]=0;                       //是否允许输入,1表示允许，0表示不允许


      iArray[6]=new Array();
      iArray[6][0]="险种编码";              //列名
      iArray[6][1]="80px";                  //列宽
      iArray[6][2]=200;                     //列最大值
      iArray[6][3]=0;                   //是否允许输入,1表示允许，0表示不允许


      iArray[7]=new Array();
      iArray[7][0]="险种名称";              //列名
      iArray[7][1]="80px";                  //列宽
      iArray[7][2]=200;                     //列最大值
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="出单机构";              //列名
      iArray[8][1]="80px";                  //列宽
      iArray[8][2]=100;                     //列最大值
      iArray[8][3]=0;                       //是否允许输入,1表示允许，0表示不允许
      iArray[8][3]=2;
      iArray[8][4]="station";                       //是否引用代码:null||""为不引用
      iArray[8][5]="6";                                 //引用代码对应第几列，'|'为分割符
      iArray[8][9]="出单机构|code:station&NOTNULL";
      iArray[8][18]=250;
      iArray[8][19]= 0 ;

      iArray[9]=new Array();
      iArray[9][0]="保单状态";              //列名
      iArray[9][1]="75px";                  //列宽
      iArray[9][2]=100;                     //列最大值
      iArray[9][3]=0;               //是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="保额";                 //列名
      iArray[10][1]="75px";                 //列宽
      iArray[10][2]=100;                        //列最大值
      iArray[10][3]=7;              //是否允许输入,1表示允许，0表示不允许
			iArray[10][23]="0";
			
      iArray[11]=new Array();
      iArray[11][0]="生效日期";                 //列名
      iArray[11][1]="90px";                 //列宽
      iArray[11][2]=100;                        //列最大值
      iArray[11][3]=8;                          //是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="保险止期";                 //列名
      iArray[12][1]="90px";                 //列宽
      iArray[12][2]=100;                        //列最大值
      iArray[12][3]=8;                          //是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="缴费止期";                 //列名
      iArray[13][1]="90px";                 //列宽
      iArray[13][2]=100;                        //列最大值
      iArray[13][3]=8;                          //是否允许输入,1表示允许，0表示不允许

      iArray[14]=new Array();
      iArray[14][0]="代理人编码";               //列名
      iArray[14][1]="80px";                 //列宽
      iArray[14][2]=100;                        //列最大值
      iArray[14][3]=3;                                  //是否允许输入,1表示允许，0表示不允许
      iArray[14][4]="AgentCode";                        //是否引用代码:null||""为不引用
      iArray[14][5]="11";                               //引用代码对应第几列，'|'为分割符
      iArray[14][9]="代理人编码|code:AgentCode&NOTNULL";
      iArray[14][18]=250;
      iArray[14][19]= 0 ;

      iArray[15]=new Array();
      iArray[15][0]="保单状态";                 //列名
      iArray[15][1]="60px";                 //列宽
      iArray[15][2]=100;                        //列最大值
      iArray[15][3]=3;                          //是否允许输入,1表示允许，0表示不允许
      iArray[15][4]="PolState";                         //是否引用代码:null||""为不引用
      iArray[15][5]="11";                               //引用代码对应第几列，'|'为分割符
      iArray[15][9]="保单状态|code:PolState&NOTNULL";
      iArray[15][18]=150;
      iArray[15][19]= 0 ;

      //iArray[16]=new Array();
      //iArray[16][0]="保单状态导致原因";               //列名
      //iArray[16][1]="0px";                    //列宽
      //iArray[16][2]=150;                      //列最大值
      //iArray[16][3]=3;                        //是否允许输入,1表示允许，0表示不允许
      //iArray[16][4]="PolStateReason";                         //是否引用代码:null||""为不引用
      //iArray[16][5]="11";                                 //引用代码对应第几列，'|'为分割符
      //iArray[16][9]="保单状态原因|code:PolStateReason&NOTNULL";
      //iArray[16][18]=150;
      //iArray[16][19]= 0 ;

      //iArray[16]=new Array();
      //iArray[16][0]="交费方式";               //列名
      //iArray[16][1]="60px";                   //列宽
      //iArray[16][2]=100;                      //列最大值
      //iArray[16][3]=0;                          //是否允许输入,1表示允许，0表示不允许

      iArray[16]=new Array();
      iArray[16][0]="交费形式";                 //列名
      iArray[16][1]="60px";                 //列宽
      iArray[16][2]=100;                        //列最大值
      iArray[16][3]=0;                            //是否允许输入,1表示允许，0表示不允许

      iArray[17]=new Array();
      iArray[17][0]="交费对应日";           //列名
      iArray[17][1]="90px";                   //列宽
      iArray[17][2]=100;                          //列最大值
      iArray[17][3]=8;                            //是否允许输入,1表示允许，0表示不允许

      iArray[18]=new Array();
      iArray[18][0]="保单状态";                 //列名
      iArray[18][1]="60px";                   //列宽
      iArray[18][2]=100;                          //列最大值
      iArray[18][3]=3;                            //是否允许输入,1表示允许，0表示不允许

      iArray[19]=new Array();
      iArray[19][0]="交费年期";                 //列名
      iArray[19][1]="60px";                   //列宽
      iArray[19][2]=100;                          //列最大值
      iArray[19][3]=0;                            //是否允许输入,1表示允许，0表示不允许

      iArray[20]=new Array();
      iArray[20][0]="保费总额";                 //列名
      iArray[20][1]="60px";                   //列宽
      iArray[20][2]=100;                          //列最大值
      iArray[20][3]=7;                            //是否允许输入,1表示允许，0表示不允许
			iArray[20][23]="0";
			
      iArray[21]=new Array();
      iArray[21][0]="基本保费";                 //列名
      iArray[21][1]="60px";                   //列宽
      iArray[21][2]=100;                          //列最大值
      iArray[21][3]=7;                            //是否允许输入,1表示允许，0表示不允许
      iArray[21][23]="0";

      iArray[22]=new Array();
      iArray[22][0]="健康加费";                 //列名
      iArray[22][1]="60px";                   //列宽
      iArray[22][2]=100;                          //列最大值
      iArray[22][3]=7;                            //是否允许输入,1表示允许，0表示不允许
			iArray[22][23]="0";
			
      iArray[23]=new Array();
      iArray[23][0]="职业加费";                 //列名
      iArray[23][1]="60px";                   //列宽
      iArray[23][2]=100;                          //列最大值
      iArray[23][3]=7;                          //是否允许输入,1表示允许，0表示不允许
      iArray[23][23]="0";

      iArray[24]=new Array();
      iArray[24][0]="投保年龄";                 //列名
      iArray[24][1]="60px";                     //列宽
      iArray[24][2]=100;                        //列最大值
      iArray[24][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[25]=new Array();
			iArray[25][0]="币种";
			iArray[25][1]="60px";
			iArray[25][2]=100;
			iArray[25][3]=2;
			iArray[25][4]="currency";


      PolGrid = new MulLineEnter( "document" , "PolGrid" );
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;

      PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}


// 被保人信息列表的初始化
function initInsuredGrid()
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
      iArray[1][0]="被保人姓名";
      iArray[1][1]="80px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="客户号";
      iArray[2][1]="100px";
      iArray[2][2]=120;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="性别";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="证件类型";
      iArray[4][1]="120px";
      iArray[4][2]=150;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="证件号码";
      iArray[5][1]="120px";
      iArray[5][2]=150;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="国籍";
      iArray[6][1]="80px";
      iArray[6][2]=100;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="职业等级";
      iArray[7][1]="80px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="出生日期";
      iArray[8][1]="80px";
      iArray[8][2]=100;
      iArray[8][3]=8;

      InsuredGrid = new MulLineEnter( "document" , "InsuredGrid" );
      //这些属性必须在loadMulLine前
      InsuredGrid.mulLineCount = 5;
      InsuredGrid.displayTitle = 1;
      InsuredGrid.locked = 1;
      InsuredGrid.canSel = 0;

      InsuredGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      InsuredGrid.hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>











