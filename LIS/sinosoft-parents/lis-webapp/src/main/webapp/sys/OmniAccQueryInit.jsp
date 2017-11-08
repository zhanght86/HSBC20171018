<%
//程序名称：
//程序功能：
//创建日期：
//创建人  ：Howie
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();
    initPolGrid2();
    initPolGrid3();
    easyQueryClick();
  }
  catch(re)
  {
    alert("OmniAccQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 输入框的初始化（单记录部分）
function initInpBox()
{
  try
  {
    // 保单查询条件
    document.all('ContNo').value = tContNo;
    document.all('PolNo').value = tPolNo;
    document.all('RiskCode').value = tRiskCode;
 //   var strsql = "select appntname,insuredname from lccont where contno = '"+tContNo+"'";
    
    	var sqlid1="OmniAccQueryInitSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.OmniAccQueryInitSql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(tContNo);//指定传入参数
	 	var strsql = mySql1.getString();
    
    var brr = easyExecSql(strsql);
    if(brr)
    {
       document.all('AppntName').value = brr[0][0];
       document.all('InsuredName').value = brr[0][1];
    }
  }
  catch(ex)
  {
    alert("在OmniQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
      iArray[1][0]="保险帐户号";                //列名
      iArray[1][1]="78px";                 //列宽
      iArray[1][2]=100;                     //列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="集体合同号";                //列名
      iArray[2][1]="100px";                 //列宽
      iArray[2][2]=200;                     //列最大值
      iArray[2][3]=3;                       //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="集体保单险种号";                //列名
      iArray[3][1]="100px";                 //列宽
      iArray[3][2]=200;                     //列最大值
      iArray[3][3]=3;                       //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="印刷号";                //列名
      iArray[4][1]="100px";                 //列宽
      iArray[4][2]=100;                     //列最大值
      iArray[4][3]=3;                       //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="险种编码";              //列名
      iArray[5][1]="60px";                  //列宽
      iArray[5][2]=100;                     //列最大值
      iArray[5][3]=2;
      iArray[5][4]="RiskCode";                          //是否引用代码:null||""为不引用
      iArray[5][5]="5";                                 //引用代码对应第几列，'|'为分割符
      iArray[5][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[5][18]=250;
      iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="账户类型";              //列名
      iArray[6][1]="80px";                  //列宽
      iArray[6][2]=100;                     //列最大值
      iArray[6][3]=3;                                   //是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="投资类型";              //列名
      iArray[7][1]="80px";                  //列宽
      iArray[7][2]=100;                     //列最大值
      iArray[7][3]=3;                                   //是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="被保人客户号";              //列名
      iArray[8][1]="80px";                  //列宽
      iArray[8][2]=100;                     //列最大值
      iArray[8][3]=3;                                   //是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="投保人客户号";              //列名
      iArray[9][1]="80px";                  //列宽
      iArray[9][2]=100;                     //列最大值
      iArray[9][3]=0;                                   //是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="账户所有者";               //列名
      iArray[10][1]="80px";                 //列宽
      iArray[10][2]=100;                        //列最大值
      iArray[10][3]=3;                                  //是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="累计交费";                 //列名
      iArray[11][1]="60px";                 //列宽
      iArray[11][2]=100;                        //列最大值
      iArray[11][3]=7;                                  //是否允许输入,1表示允许，0表示不允许
      iArray[11][23]="0";

      iArray[12]=new Array();
      iArray[12][0]="累计领取";                 //列名
      iArray[12][1]="60px";                 //列宽
      iArray[12][2]=100;                        //列最大值
      iArray[12][3]=7;                                  //是否允许输入,1表示允许，0表示不允许
      iArray[12][23]="0"; 

      iArray[13]=new Array();
      iArray[13][0]="上月账户金额";                 //列名
      iArray[13][1]="80px";                    //列宽
      iArray[13][2]=100;                        //列最大值
      iArray[13][3]=7;                                  //是否允许输入,1表示允许，0表示不允许
      iArray[13][23]="0";

      iArray[14]=new Array();
      iArray[14][0]="当前帐户金额";                 //列名
      iArray[14][1]="80px";                 //列宽
      iArray[14][2]=100;                        //列最大值
      iArray[14][3]=7;                                  //是否允许输入,1表示允许，0表示不允许
      iArray[14][23]="0";


      iArray[15]=new Array();
      iArray[15][0]="账户可领金额";                 //列名
      iArray[15][1]="80px";                 //列宽
      iArray[15][2]=100;                        //列最大值
      iArray[15][3]=7;                                  //是否允许输入,1表示允许，0表示不允许
      iArray[15][23]="0";

      iArray[16]=new Array();
      iArray[16][0]="管理机构";                 //列名
      iArray[16][1]="80px";                 //列宽
      iArray[16][2]=100;                        //列最大值
      iArray[16][3]=3;                                  //是否允许输入,1表示允许，0表示不允许

      iArray[17]=new Array();
      iArray[17][0]="操作员";               //列名
      iArray[17][1]="80px";                 //列宽
      iArray[17][2]=100;                        //列最大值
      iArray[17][3]=3;                                  //是否允许输入,1表示允许，0表示不允许


      iArray[18]=new Array();
      iArray[18][0]="初始费用";               //列名
      iArray[18][1]="80px";                 //列宽
      iArray[18][2]=100;                        //列最大值
      iArray[18][3]=7;                                  //是否允许输入,1表示允许，0表示不允许
      iArray[18][23]="0";
      
      iArray[19]=new Array();
			iArray[19][0]="币种";
			iArray[19][1]="60px";
			iArray[19][2]=100;
			iArray[19][3]=2;
			iArray[19][4]="currency";


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.selBoxEventFuncName = "showAccDetail";
      PolGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}



var PolGrid2;
// 保单信息列表的初始化
function initPolGrid2()
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
      iArray[1][0]="集体合同号码";
      iArray[1][1]="0px";
      iArray[1][2]=100;
      iArray[1][3]=3;

      iArray[2]=new Array();
      iArray[2][0]="集体保单号码";
      iArray[2][1]="0px";
      iArray[2][2]=200;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="管理机构";
      iArray[3][1]="0px";
      iArray[3][2]=200;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="交费计划编码";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="对应其它号码";
      iArray[5][1]="80px";
      iArray[5][2]=100;
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="账户归属属性";
      iArray[6][1]="80px";
      iArray[6][2]=100;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="账户类型";
      iArray[7][1]="80px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="账户结算方式";
      iArray[8][1]="100px";
      iArray[8][2]=100;
      iArray[8][3]=0;

      iArray[9]=new Array();
      iArray[9][0]="结算日期";
      iArray[9][1]="80px";
      iArray[9][2]=100;
      iArray[9][3]=8;

      iArray[10]=new Array();
      iArray[10][0]="结算时间";
      iArray[10][1]="80px";
      iArray[10][2]=100;
      iArray[10][3]=8;

      iArray[11]=new Array();
      iArray[11][0]="累计交费";
      iArray[11][1]="80px";
      iArray[11][2]=100;
      iArray[11][3]=7;
      iArray[11][23]="0";

      iArray[12]=new Array();
      iArray[12][0]="累计领取";
      iArray[12][1]="80px";
      iArray[12][2]=100;
      iArray[12][3]=7;
      iArray[12][23]="0";

      iArray[13]=new Array();
      iArray[13][0]="期初账户现金余额";
      iArray[13][1]="80px";
      iArray[13][2]=100;
      iArray[13][3]=7;
      iArray[13][23]="0";

      iArray[14]=new Array();
      iArray[14][0]="期初账户单位数";
      iArray[14][1]="80px";
      iArray[14][2]=100;
      iArray[14][3]=0;

      iArray[15]=new Array();
      iArray[15][0]="期初账户单位价格";
      iArray[15][1]="80px";
      iArray[15][2]=100;
      iArray[15][3]=7;
      iArray[15][23]="0";

      iArray[16]=new Array();
      iArray[16][0]="保险帐户现金余额";
      iArray[16][1]="80px";
      iArray[16][2]=100;
      iArray[16][3]=7;
      iArray[16][23]="0";

      iArray[17]=new Array();
      iArray[17][0]="保险帐户单位数";
      iArray[17][1]="80px";
      iArray[17][2]=100;
      iArray[17][3]=0;

      iArray[18]=new Array();
      iArray[18][0]="保险账户可领金额";
      iArray[18][1]="80px";
      iArray[18][2]=100;
      iArray[18][3]=7;
      iArray[18][23]="0";

      iArray[19]=new Array();
      iArray[19][0]="冻结金额";
      iArray[19][1]="80px";
      iArray[19][2]=100;
      iArray[19][3]=7;
      iArray[19][23]="0";

      iArray[20]=new Array();
      iArray[20][0]="状态";
      iArray[20][1]="40px";
      iArray[20][2]=100;
      iArray[20][3]=0;

      iArray[21]=new Array();
      iArray[21][0]="操作员";
      iArray[21][1]="80px";
      iArray[21][2]=100;
      iArray[21][3]=0;
      
      iArray[22]=new Array();
			iArray[22][0]="币种";
			iArray[22][1]="60px";
			iArray[22][2]=100;
			iArray[22][3]=2;
			iArray[22][4]="currency";

      PolGrid2 = new MulLineEnter("fm", "PolGrid2");
      //这些属性必须在loadMulLine前
      PolGrid2.mulLineCount = 0;
      PolGrid2.displayTitle = 1;
      PolGrid2.locked = 1;
      PolGrid2.canSel = 0;
      PolGrid2.hiddenSubtraction = 1;
      PolGrid2.hiddenPlus = 1;
      PolGrid2.selBoxEventFuncName = "";
      PolGrid2.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
          alert(ex);
      }
}

var PolGrid3;
// 保单信息列表的初始化
function initPolGrid3()
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
      iArray[1][0]="集体合同号码";
      iArray[1][1]="130px";
      iArray[1][2]=100;
      iArray[1][3]=3;

      iArray[2]=new Array();
      iArray[2][0]="集体保单险种号码";
      iArray[2][1]="130px";
      iArray[2][2]=100;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="合同号码";
      iArray[3][1]="130px";
      iArray[3][2]=100;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="保单险种号码";
      iArray[4][1]="0px";
      iArray[4][2]=200;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="流水号";
      iArray[5][1]="100px";
      iArray[5][2]=200;
      iArray[5][3]=3;


      iArray[6]=new Array();
      iArray[6][0]="保险帐户号码";
      iArray[6][1]="100px";
      iArray[6][2]=100;
      iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="险种编码";
      iArray[7][1]="100px";
      iArray[7][2]=100;
      iArray[7][3]=3;
      iArray[7][4]="RiskCode";
      iArray[7][5]="7";
      iArray[7][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[7][18]=250;
      iArray[7][19]= 0 ;

      iArray[8]=new Array();
      iArray[8][0]="交费计划编码";
      iArray[8][1]="100px";
      iArray[8][2]=100;
      iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="对应其它号码";
      iArray[9][1]="100px";
      iArray[9][2]=100;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="对应其它号码类型";
      iArray[10][1]="80px";
      iArray[10][2]=100;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="账户归属属性";
      iArray[11][1]="80px";
      iArray[11][2]=100;
      iArray[11][3]=3;

      iArray[12]=new Array();
      iArray[12][0]="金额类型";
      iArray[12][1]="120px";
      iArray[12][2]=100;
      iArray[12][3]=0;

      iArray[13]=new Array();
      iArray[13][0]="本次金额";
      iArray[13][1]="100px";
      iArray[13][2]=100;
      iArray[13][3]=7;
      iArray[13][23]="0";

      iArray[14]=new Array();
      iArray[14][0]="本次单位数";
      iArray[14][1]="80px";
      iArray[14][2]=100;
      iArray[14][3]=3;

      iArray[15]=new Array();
      iArray[15][0]="结算日期";
      iArray[15][1]="80px";
      iArray[15][2]=100;
      iArray[15][3]=8;

      iArray[16]=new Array();
      iArray[16][0]="状态";
      iArray[16][1]="40px";
      iArray[16][2]=100;
      iArray[16][3]=3;

      iArray[17]=new Array();
      iArray[17][0]="管理机构";
      iArray[17][1]="80px";
      iArray[17][2]=100;
      iArray[17][3]=3;

      iArray[18]=new Array();
      iArray[18][0]="管理费编码";
      iArray[18][1]="80px";
      iArray[18][2]=100;
      iArray[18][3]=3;

      iArray[19]=new Array();
      iArray[19][0]="账户批单号";
      iArray[19][1]="80px";
      iArray[19][2]=100;
      iArray[19][3]=3;

      iArray[20]=new Array();
      iArray[20][0]="批单号类型";
      iArray[20][1]="80px";
      iArray[20][2]=100;
      iArray[20][3]=3;

      iArray[21]=new Array();
      iArray[21][0]="操作员";
      iArray[21][1]="80px";
      iArray[21][2]=100;
      iArray[21][3]=3;

      iArray[22]=new Array();
      iArray[22][0]="修改日期";
      iArray[22][1]="80px";
      iArray[22][2]=100;
      iArray[22][3]=8;

      iArray[23]=new Array();
      iArray[23][0]="修改时间";
      iArray[23][1]="80px";
      iArray[23][2]=100;
      iArray[23][3]=3;

      iArray[24]=new Array();
      iArray[24][0]="结算利率";
      iArray[24][1]="80px";
      iArray[24][2]=100;
      iArray[24][3]=0;
      
      iArray[25]=new Array();
      iArray[25][0]="帐户价值";
      iArray[25][1]="80px";
      iArray[25][2]=100;
      iArray[25][3]=7;
      iArray[25][23]="0";
      
      iArray[26]=new Array();
			iArray[26][0]="币种";
			iArray[26][1]="60px";
			iArray[26][2]=100;
			iArray[26][3]=2;
			iArray[26][4]="currency";

      PolGrid3 = new MulLineEnter("fm", "PolGrid3");
      //这些属性必须在loadMulLine前
      PolGrid3.mulLineCount = 0;
      PolGrid3.displayTitle = 1;
      PolGrid3.locked = 1;
      PolGrid3.canSel = 0;
      PolGrid3.hiddenSubtraction=1;
      PolGrid3.hiddenPlus = 1;
      PolGrid3.selBoxEventFuncName = "";
      PolGrid3.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      }
      catch (ex)
      {
        alert(ex);
      }
}

</script>
