<%
//Name: LLClaimSimpleInit.jsp
//function：
//author:
//Date:
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";
var mManageCom = "";
//接收报案页面传递过来的参数
function initParam()
{
    mCurrentDate = nullToEmpty("<%= CurrentDate %>");
    mNowYear = mCurrentDate.substring(0,4);
    mNowMonth = mCurrentDate.substring(5,7);
    mNowDay = mCurrentDate.substring(8,10);
    fm.all('RptNo').value = nullToEmpty("<%= RptNo %>");
    fm.all('Flag').value = nullToEmpty("<%= Flag %>");
    fm.all('Operator').value = nullToEmpty("<%= tG.Operator %>");
    mManageCom = nullToEmpty("<%= tG.ManageCom %>");
    fm.all('AllManageCom').value = mManageCom;             //取到登陆机构的全部代码 用于查询保单处理机构
    fm.all('ManageCom').value = mManageCom.substring(0,2); //取到登陆机构的前两个代码 用于查询医院机构
}

//把null的字符串转为空
function nullToEmpty(string)
{
  if ((string == "null") || (string == "undefined"))
  {
    string = "";
  }
  return string;
}

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSubReportGrid();
//    initSubMeansGrid();
    initMedFeeInHosInpGrid();
    initPolDutyCodeGrid();
    initClaimGrid();
    queryRegister();
    afterMatchDutyPayQuery();
    //登陆机构操作控制,登陆机构代码小于4位的不允许进行操作
/*
   if( fm.AllManageCom.value.length < 4){
   
    fm.addbutton.disabled=true;
    fm.updatebutton.disabled=true;
    fm.deletebutton.disabled=true;
    
    fm.addbutton2.disabled=true;
    fm.updateFeebutton.disabled=true;
    fm.deleteFeebutton.disabled=true;
    
    fm.QueryReport.disabled=true;
    fm.dutySet.disabled=true;
    
    fm.addUpdate.disabled=true;
    fm.saveUpdate.disabled=true;
    fm.simpleClaim.disabled=true;
    fm.simpleClaim2.disabled=true;
        
    
   }else{
    fm.addbutton.disabled=false;
    fm.updatebutton.disabled=false;
    fm.deletebutton.disabled=false;
    
    fm.addbutton2.disabled=false;
    fm.updateFeebutton.disabled=false;
    fm.deleteFeebutton.disabled=false;
    
    fm.QueryReport.disabled=false;
    fm.dutySet.disabled=false;
    fm.addUpdate.disabled=false;
    fm.saveUpdate.disabled=false;
    fm.simpleClaim.disabled=false;
    fm.simpleClaim2.disabled=false;
   }
  */
  }
  catch(re)
  {
    alert("在LLClaimConfirmInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

/**
  页面初始化
*/
function initInpBox()
{
  try
  {
      fm.saveUpdate.disabled = true;
  }
  catch(ex)
  {
    alert("在LLClaimConfirmInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


/**
  出险人信息初始化
*/
function initSubReportGrid()
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
        iArray[1][0]="客户编码"; //原事故者客户号
        iArray[1][1]="150px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户名"; //事故者姓名
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="性别";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="出生日期";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array()
        iArray[5][0]="VIP标志";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array()
        iArray[6][0]="证件类型";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=3;

        iArray[7]=new Array()
        iArray[7][0]="证件号码";
        iArray[7][1]="50px";
        iArray[7][2]=100;
        iArray[7][3]=3;

        iArray[8]=new Array()
        iArray[8][0]="证件类型编码";
        iArray[8][1]="50px";
        iArray[8][2]=100;
        iArray[8][3]=3;
        
        iArray[9]=new Array()
        iArray[9][0]="出险日期";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=3;
        
        SubReportGrid = new MulLineEnter("fm","SubReportGrid");
        SubReportGrid.mulLineCount = 0;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
        SubReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //响应的函数名，不加扩号
        SubReportGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
        SubReportGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        SubReportGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        SubReportGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}

//保项计算初始化
function initSubMeansGrid()
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
      iArray[1][0]="医院名称";
      iArray[1][1]="160px";
      iArray[1][2]=130;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="医院等级";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="开始日期";
      iArray[3][1]="100px";
      iArray[3][2]=600;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="结束日期";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="天数";
      iArray[5][1]="40px";
      iArray[5][2]=130;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="费用类型";
      iArray[6][1]="80px";
      iArray[6][2]=80;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="费用金额";
      iArray[7][1]="80px";
      iArray[7][2]=80;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="特殊标志";
      iArray[8][1]="80px";
      iArray[8][2]=80;
      iArray[8][3]=0;

      SubMeansGrid = new MulLineEnter("fm","SubMeansGrid");
      SubMeansGrid.mulLineCount = 0;        // 显示行数
      SubMeansGrid.displayTitle = 1;
      SubMeansGrid.locked = 0;
//        SubMeansGrid.canChk =1;               // 多选按钮：1 显示 ；0 隐藏（缺省值）
      SubMeansGrid.canSel =1;                   // 单选按钮：1 显示 ；0 隐藏（缺省值）
//      SubMeansGrid.selBoxEventFuncName ="PolDutyCodeGridClick"; //响应的函数名，不加扩号
//      SubMeansGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
      SubMeansGrid.hiddenPlus=1;        //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      SubMeansGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
      SubMeansGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}

//门诊
function initMedFeeInHosInpGrid()
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
        iArray[1][3]=1;

        iArray[2]=new Array();
        iArray[2][0]="帐单种类";
        iArray[2][1]="60px";
        iArray[2][2]=80;
        iArray[2][3]=2;
        iArray[2][4]='llcuredesc';      //设置要引用LDcode中的代码
//        iArray[2][5]="2|2";             //引用代码对应第几列，'|'为分割符
//        iArray[2][6]="1|0";

        iArray[3]=new Array();
        iArray[3][0]="医院名称";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=2;
        iArray[3][4]='commendhospital';      //设置要引用LDcode中的代码
        iArray[3][5]="3|4";             //引用代码对应第几列，'|'为分割符
        iArray[3][6]="1|0";
        iArray[3][15]="ManageCom";
        iArray[3][16]=fm.ManageCom.value;

        iArray[4]=new Array();
        iArray[4][0]="医院代码";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="医院等级";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="帐单号码";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=1;

        iArray[7]=new Array();
        iArray[7][0]="疾病名称";
        iArray[7][1]="100px";
        iArray[7][2]=10;
        iArray[7][3]=1;
        iArray[7][4]="ICDName";
        iArray[7][9]="疾病名称|len<=120";
        iArray[7][18]=300;

        iArray[8]=new Array();
        iArray[8][0]="疾病代码";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=2;
        iArray[8][4]="ICDCode";
        iArray[8][5]="8|7";
        iArray[8][6]="0|1";
        iArray[8][9]="ICD编码|len<=20";
        iArray[8][15]="ICDName";
        iArray[8][17]="2";
        iArray[8][18]=700;

        iArray[9]=new Array();
        iArray[9][0]="开始日期";
        iArray[9][1]="80px";
        iArray[9][2]=10;
        iArray[9][3]=1;

        iArray[10]=new Array();
        iArray[10][0]="结束日期";
        iArray[10][1]="80px";
        iArray[10][2]=10;
        iArray[10][3]=1;

        iArray[11]=new Array();
        iArray[11][0]="天数";
        iArray[11][1]="40px";
        iArray[11][2]=10;
        iArray[11][3]=0;

        iArray[12]=new Array();
        iArray[12][0]="费用名称";
        iArray[12][1]="60px";
        iArray[12][2]=10;
        iArray[12][3]=2;
        iArray[12][4]='llfeeitemtype';      //设置要引用LDcode中的代码
        iArray[12][5]="12|13";             //引用代码对应第几列，'|'为分割符
        iArray[12][6]="1|0";

        iArray[13]=new Array();
        iArray[13][0]="费用类型";
        iArray[13][1]="60px";
        iArray[13][2]=10;
        iArray[13][3]=3;

        iArray[14]=new Array();
        iArray[14][0]="原始费用";
        iArray[14][1]="60px";
        iArray[14][2]=10;
        iArray[14][3]=1;

        iArray[15]=new Array();
        iArray[15][0]="扣除费用";
        iArray[15][1]="60px";
        iArray[15][2]=10;
        iArray[15][3]=1;

        iArray[16]=new Array();
        iArray[16][0]="扣除原因";
        iArray[16][1]="80px";
        iArray[16][2]=10;
        iArray[16][3]=2;
        iArray[16][4]='deductreason';      //设置要引用LDcode中的代码
        iArray[16][5]="16|17";             //引用代码对应第几列，'|'为分割符
        iArray[16][6]="1|0";

        iArray[17]=new Array();
        iArray[17][0]="扣除代码";
        iArray[17][1]="80px";
        iArray[17][2]=10;
        iArray[17][3]=3;

        iArray[18]=new Array();
        iArray[18][0]="合理费用";
        iArray[18][1]="80px";
        iArray[18][2]=10;
        iArray[18][3]=0;

        iArray[19]=new Array();
        iArray[19][0]="社保赔付费用";
        iArray[19][1]="80px";
        iArray[19][2]=10;
        iArray[19][3]=1;
        
        iArray[20]=new Array();
        iArray[20][0]="住院起付线";
        iArray[20][1]="80px";
        iArray[20][2]=10;
        iArray[20][3]=1;

        iArray[21]=new Array();
        iArray[21][0]="扣除明细";
        iArray[21][1]="200px";
        iArray[21][2]=400;
        iArray[21][3]=1;
        
        iArray[22]=new Array();
        iArray[22][0]="账单费用明细";
        iArray[22][1]="100px";
        iArray[22][2]=100;
        iArray[22][3]=3;        

        MedFeeInHosInpGrid = new MulLineEnter("fm","MedFeeInHosInpGrid");
        MedFeeInHosInpGrid.mulLineCount = 0;
        MedFeeInHosInpGrid.displayTitle = 1;
        MedFeeInHosInpGrid.locked = 0;
        MedFeeInHosInpGrid.canChk =1;              //多选按钮，1显示，0隐藏
        MedFeeInHosInpGrid.canSel =0;              //单选按钮，1显示，0隐藏
        MedFeeInHosInpGrid.hiddenPlus=0;           //＋号，1隐藏，0显示
        MedFeeInHosInpGrid.hiddenSubtraction=0;    //－号：1隐藏，0显示
        MedFeeInHosInpGrid.selBoxEventFuncName = "getMedFeeInHosInpGrid"; //函数名称
        MedFeeInHosInpGrid.selBoxEventFuncParm ="";          //参数
        MedFeeInHosInpGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

//保项计算初始化
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
      iArray[1][0]="个人保单号";
      iArray[1][1]="100px";
      iArray[1][2]=130;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="理赔类型";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="险种编码";
      iArray[3][1]="60px";
      iArray[3][2]=600;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="保项编码";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="保项名称";
      iArray[5][1]="130px";
      iArray[5][2]=130;
      iArray[5][3]=0;


      iArray[6]=new Array();
      iArray[6][0]="责任起期";
      iArray[6][1]="80px";
      iArray[6][2]=80;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="责任止期";
      iArray[7][1]="80px";
      iArray[7][2]=80;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="宽限期";
      iArray[8][1]="60px";
      iArray[8][2]=60;
      iArray[8][3]=0;

      iArray[9]=new Array();
      iArray[9][0]="保额";
      iArray[9][1]="60px";
      iArray[9][2]=60;
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="年度红利";
      iArray[10][1]="60px";
      iArray[10][2]=60;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="终了红利";
      iArray[11][1]="60px";
      iArray[11][2]=60;
      iArray[11][3]=3;

      iArray[12]=new Array();
      iArray[12][0]="理算金额";
      iArray[12][1]="60px";
      iArray[12][2]=60;
      iArray[12][3]=0;

      iArray[13]=new Array();
      iArray[13][0]="给付代码";
      iArray[13][1]="60px";
      iArray[13][2]=60;
      iArray[13][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
      iArray[13][4]="llpayconclusion";    //是否引用代码: null或者" "为不引用
      iArray[13][5]="13|14";              //引用代码信息分别放在第13列和第14列，'|'为分割符
      iArray[13][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列


      iArray[14]=new Array();
      iArray[14][0]="给付名称";
      iArray[14][1]="60px";
      iArray[14][2]=60;
      iArray[14][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[15]=new Array();
      iArray[15][0]="拒付原因代码";
      iArray[15][1]="100px";
      iArray[15][2]=100;
      iArray[15][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
      iArray[15][4]="llprotestreason";    //是否引用代码: null或者" "为不引用
      iArray[15][5]="15|16";              //引用代码信息分别放在第13列和第14列，'|'为分割符
      iArray[15][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列

      iArray[16]=new Array();
      iArray[16][0]="拒付原因名称";
      iArray[16][1]="100px";
      iArray[16][2]=100;
      iArray[16][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[17]=new Array();
      iArray[17][0]="拒付依据";
      iArray[17][1]="60px";
      iArray[17][2]=60;
      iArray[17][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[18]=new Array();
      iArray[18][0]="特殊备注";
      iArray[18][1]="60px";
      iArray[18][2]=60;
      iArray[18][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏


      iArray[19]=new Array();
      iArray[19][0]="预付金额";
      iArray[19][1]="60px";
      iArray[19][2]=60;
      iArray[19][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[20]=new Array();
      iArray[20][0]="";
      iArray[20][1]="60px";
      iArray[20][2]=60;
      iArray[20][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[21]=new Array();
      iArray[21][0]="调整金额";
      iArray[21][1]="60px";
      iArray[21][2]=60;
      iArray[21][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[22]=new Array();
      iArray[22][0]="调整原因代码";
      iArray[22][1]="100px";
      iArray[22][2]=100;
      iArray[22][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[23]=new Array();
      iArray[23][0]="调整原因名称";
      iArray[23][1]="100px";
      iArray[23][2]=100;
      iArray[23][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[24]=new Array();
      iArray[24][0]="调整备注";
      iArray[24][1]="60px";
      iArray[24][2]=60;
      iArray[24][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[25]=new Array();
      iArray[25][0]="预付标志代码";
      iArray[25][1]="60px";
      iArray[25][2]=60;
      iArray[25][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[26]=new Array();
      iArray[26][0]="预付标志";
      iArray[26][1]="60px";
      iArray[26][2]=60;
      iArray[26][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[27]=new Array();
      iArray[27][0]="数据来源";
      iArray[27][1]="60px";
      iArray[27][2]=60;
      iArray[27][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[28]=new Array();
      iArray[28][0]="dutycode";
      iArray[28][1]="60px";
      iArray[28][2]=60;
      iArray[28][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      iArray[29]=new Array();
      iArray[29][0]="客户号";
      iArray[29][1]="60px";
      iArray[29][2]=60;
      iArray[29][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

        iArray[30]=new Array();
        iArray[30][0]="团体合同号";
        iArray[30][1]="60px";
        iArray[30][2]=60;
        iArray[30][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

        iArray[31]=new Array();
        iArray[31][0]="个单合同号";
        iArray[31][1]="60px";
        iArray[31][2]=60;
        iArray[31][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

        iArray[32]=new Array();
        iArray[32][0]="客户名";
        iArray[32][1]="60px";
        iArray[32][2]=60;
        iArray[32][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

      PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
      PolDutyCodeGrid.mulLineCount = 0;        // 显示行数
      PolDutyCodeGrid.displayTitle = 1;
      PolDutyCodeGrid.locked = 0;
//        PolDutyCodeGrid.canChk =1;               // 多选按钮：1 显示 ；0 隐藏（缺省值）
      PolDutyCodeGrid.canSel =1;                   // 单选按钮：1 显示 ；0 隐藏（缺省值）
        PolDutyCodeGrid.selBoxEventFuncName ="PolDutyCodeGridClick"; //响应的函数名，不加扩号
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

//赔案总金额
function initClaimGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="赔付金额";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="预付金额";
        iArray[2][1]="80px";
        iArray[2][2]=80;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="结算金额";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="最终赔付金额";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="拒赔金额";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=3;
        

        ClaimGrid = new MulLineEnter("fm","ClaimGrid");
        ClaimGrid.mulLineCount = 0;       // 显示行数
        ClaimGrid.displayTitle = 1;
        ClaimGrid.locked = 0;
    //  ClaimGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
        ClaimGrid.canSel =1;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
        ClaimGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        ClaimGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        ClaimGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>
