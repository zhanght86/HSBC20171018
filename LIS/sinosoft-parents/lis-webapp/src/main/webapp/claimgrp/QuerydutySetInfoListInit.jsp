<%
//**************************************************************************************************
//Name：QuerydutySetInfoListInit.jsp
//Function：简易理赔__理算结果统计查询__初始化页面
//Author：pd
//Date: 2005-12-28
//Desc:
//**************************************************************************************************
%>
<%
  String CaseNo = request.getParameter("CaseNo");
  String customerName = request.getParameter("customerName");
  String customerNo = request.getParameter("customerNo");
%>
<script language="JavaScript" type="text/javascript">
  var turnPage = new turnPageClass();

/**
 *FUNCTION:initForm()
 *DESC :初始化__FORM__表单
 */
function initForm()
{
  try
  {
  fm.CaseNo.value="<%=CaseNo%>";
  fm.customerName.value="<%=customerName%>";
  fm.customerNo.value="<%=customerNo%>"

  initPolDutyKindGrid();
  initPolDutyCodeGrid();
  initList();
  }
  catch(ex)
  {
  alert("StandPayInfoListInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+ ex.message);
  }
}

//保单、理赔类型计算初始化
function initPolDutyKindGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";
      iArray[0][1]="30px";
      iArray[0][2]=10;
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="合同号";
      iArray[1][1]="105px";
      iArray[1][2]=105;
      iArray[1][3]=3;

      iArray[2]=new Array();
      iArray[2][0]="个人保单号";
      iArray[2][1]="105px";
      iArray[2][2]=105;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="理赔类型";
      iArray[3][1]="60px";
      iArray[3][2]=60;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="生效日期";
      iArray[4][1]="70px";
      iArray[4][2]=60;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="交至日期";
      iArray[5][1]="70px";
      iArray[5][2]=60;
      iArray[5][3]=0;


      iArray[6]=new Array();
      iArray[6][0]="险种代码";
      iArray[6][1]="60px";
      iArray[6][2]=60;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="险种名称";
      iArray[7][1]="140px";
      iArray[7][2]=140;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="理算金额";
      iArray[8][1]="60px";
      iArray[8][2]=60;
      iArray[8][3]=0;
        
        
      PolDutyKindGrid = new MulLineEnter("fm","PolDutyKindGrid");
      PolDutyKindGrid.mulLineCount = 10;       // 显示行数
      PolDutyKindGrid.displayTitle = 1;
      PolDutyKindGrid.locked = 0;
  //  PolDutyKindGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
      PolDutyKindGrid.canSel =1;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
      PolDutyKindGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyKindGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**
 *FUNCTION:initPolDutyCodeGrid()
 *DESC :初始化__保项计算__GRID
 */
function initPolDutyCodeGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";
      iArray[0][1]="30px";
      iArray[0][2]=10;
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="客户编码";
      iArray[1][1]="80px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="客户名";
      iArray[2][1]="60px";
      iArray[2][2]=130;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="个人保单号";
      iArray[3][1]="100px";
      iArray[3][2]=130;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="理赔类型";
      iArray[4][1]="60px";
      iArray[4][2]=60;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="险种编码";
      iArray[5][1]="60px";
      iArray[5][2]=600;
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="保项编码";
      iArray[6][1]="100px";
      iArray[6][2]=100;
      iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="保项名称";
      iArray[7][1]="130px";
      iArray[7][2]=130;
      iArray[7][3]=0;


      iArray[8]=new Array();
      iArray[8][0]="保险起期";
      iArray[8][1]="80px";
      iArray[8][2]=80;
      iArray[8][3]=0;

      iArray[9]=new Array();
      iArray[9][0]="责任起期";
      iArray[9][1]="80px";
      iArray[9][2]=80;
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="责任止期";
      iArray[10][1]="80px";
      iArray[10][2]=80;
      iArray[10][3]=0;

      iArray[11]=new Array();
      iArray[11][0]="保额";
      iArray[11][1]="60px";
      iArray[11][2]=60;
      iArray[11][3]=0;

      iArray[12]=new Array();
      iArray[12][0]="年度红利";
      iArray[12][1]="60px";
      iArray[12][2]=60;
      iArray[12][3]=3;

      iArray[13]=new Array();
      iArray[13][0]="终了红利";
      iArray[13][1]="60px";
      iArray[13][2]=60;
      iArray[13][3]=3;

      iArray[14]=new Array();
      iArray[14][0]="理算金额";
      iArray[14][1]="60px";
      iArray[14][2]=60;
      iArray[14][3]=0;

      iArray[15]=new Array();
      iArray[15][0]="给付代码";
      iArray[15][1]="60px";
      iArray[15][2]=60;
      iArray[15][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
      iArray[15][4]="llpayconclusion";    //是否引用代码: null或者" "为不引用
      iArray[15][5]="15|16";              //引用代码信息分别放在第13列和第14列，'|'为分割符
      iArray[15][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列


      iArray[16]=new Array();
      iArray[16][0]="给付名称";
      iArray[16][1]="60px";
      iArray[16][2]=60;
      iArray[16][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[17]=new Array();
      iArray[17][0]="拒付原因代码";
      iArray[17][1]="100px";
      iArray[17][2]=100;
      iArray[17][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
      iArray[17][4]="llprotestreason";    //是否引用代码: null或者" "为不引用
      iArray[17][5]="17|18";              //引用代码信息分别放在第13列和第14列，'|'为分割符
      iArray[17][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列

      iArray[18]=new Array();
      iArray[18][0]="拒付原因名称";
      iArray[18][1]="100px";
      iArray[18][2]=100;
      iArray[18][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[19]=new Array();
      iArray[19][0]="拒付依据";
      iArray[19][1]="60px";
      iArray[19][2]=60;
      iArray[19][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[20]=new Array();
      iArray[20][0]="特殊备注";
      iArray[20][1]="60px";
      iArray[20][2]=60;
      iArray[20][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏


      iArray[21]=new Array();
      iArray[21][0]="预付金额";
      iArray[21][1]="60px";
      iArray[21][2]=60;
      iArray[21][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[22]=new Array();
      iArray[22][0]="";
      iArray[22][1]="60px";
      iArray[22][2]=60;
      iArray[22][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[23]=new Array();
      iArray[23][0]="调整金额";
      iArray[23][1]="60px";
      iArray[23][2]=60;
      iArray[23][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[24]=new Array();
      iArray[24][0]="调整原因代码";
      iArray[24][1]="100px";
      iArray[24][2]=100;
      iArray[24][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[25]=new Array();
      iArray[25][0]="调整原因名称";
      iArray[25][1]="100px";
      iArray[25][2]=100;
      iArray[25][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[26]=new Array();
      iArray[26][0]="调整备注";
      iArray[26][1]="60px";
      iArray[26][2]=60;
      iArray[26][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[27]=new Array();
      iArray[27][0]="预付标志代码";
      iArray[27][1]="60px";
      iArray[27][2]=60;
      iArray[27][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[28]=new Array();
      iArray[28][0]="预付标志";
      iArray[28][1]="60px";
      iArray[28][2]=60;
      iArray[28][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[29]=new Array();
      iArray[29][0]="数据来源";
      iArray[29][1]="60px";
      iArray[29][2]=60;
      iArray[29][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[30]=new Array();
      iArray[30][0]="dutycode";
      iArray[30][1]="60px";
      iArray[30][2]=60;
      iArray[30][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
      PolDutyCodeGrid.mulLineCount = 10;        // 显示行数
      PolDutyCodeGrid.displayTitle = 1;
      PolDutyCodeGrid.locked = 0;
//        PolDutyCodeGrid.canChk =1;               // 多选按钮：1 显示 ；0 隐藏（缺省值）
      PolDutyCodeGrid.canSel =1;                   // 单选按钮：1 显示 ；0 隐藏（缺省值）
//        PolDutyCodeGrid.selBoxEventFuncName ="PolDutyCodeGridClick"; //响应的函数名，不加扩号
//        PolDutyCodeGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
      PolDutyCodeGrid.hiddenPlus=1;        //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyCodeGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}

</script>
