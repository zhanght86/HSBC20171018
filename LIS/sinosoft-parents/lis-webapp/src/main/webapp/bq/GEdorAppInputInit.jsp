<%
//程序名称：GEdorAppInputInit.jsp
//程序功能：团单保全申请
%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
  String ValidDate = CurrentDate;
  String dayAfterCurrent = new FDate().getString(dt);
  String CurrentTime = PubFun.getCurrentTime();
%>
<script language="JavaScript">

//接收从工作流保全申请页面传递过来的参数
function initParam()
{
   document.all('EdorAcceptNo').value = nullToEmpty("<%= tEdorAcceptNo %>");
   document.all('MissionID').value    = nullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
   document.all('LoadFlag').value     = nullToEmpty("<%= tLoadFlag %>");
   document.all('UserCode').value     = nullToEmpty("<%= tUserCode %>");
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

//初始化页面元素
function initInpBox()
{
    try
    {
        document.all('EdorAppDate').value = '<%=CurrentDate%>';
        document.all('EdorType').value = '';
        document.all('EdorTypeName').value = '';
        document.all('EdorTypeCal').value = '';
        document.all('EdorTypeCalName').value = '';
        document.all('EdorItemAppDate').value = '<%=CurrentDate%>';
        document.all('EdorValiDate').value = '<%=CurrentDate%>';
        document.all('Operator').value = "<%=tG.Operator%>";
        document.all('EdorAppDate').value = '<%=CurrentDate%>';
        document.all('ContType').value ='2';
        document.all('currentDay').value = '<%=CurrentDate%>';
        document.all('dayAfterCurrent').value = '<%=dayAfterCurrent%>';
        //XinYQ modified on 2007-03-29
        document.getElementsByName("OtherNoType")[0].value = "4";
        document.getElementsByName("AppType")[0].value = "2";
        showOneCodeName("GEdorNoType", "OtherNoType", "OtherNoName");
        showOneCodeName("EdorAppType", "AppType", "AppTypeName");
        
        document.all('EdorType').disabled = false;
	      document.all('EdorTypeCal').disabled = false;
        //document.all('EdorValiDate').readOnly = false; 
        //document.all('EdorItemAppDate').readOnly = false;
    }
    catch(ex)
    {
      alert("在GEdorAppInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!"+ex);
    }
}
//初始化选择框
function initSelBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("在GEdorAppInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}
//页面初始化
function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();
    initInusredGrid();
    initPolGrid();
    initEdorItemGrid();
    initDiv();
    ContStateQuery();
  }
  catch(re)
  {
    alert("GEdorAppInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保全受理号";
        iArray[1][1]="0px";
        iArray[1][2]=100;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="批单号";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=3;
        
        iArray[3]=new Array();
        iArray[3][0]="批改类型";
        iArray[3][1]="200px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="批改算法";
        iArray[4][1]="120px";
        iArray[4][2]=100;
        iArray[4][3]=0;      
        
        iArray[5]=new Array();
        iArray[5][0]="批改类型显示级别";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=3;     
        
        iArray[6]=new Array();
        iArray[6][0]="团体保单号";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="保单号码";
        iArray[7][1]="120px";
        iArray[7][2]=100;
        iArray[7][3]=3;
        
        iArray[8]=new Array();
        iArray[8][0]="客户号码";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="保单险种号码";
        iArray[9][1]="100px";
        iArray[9][2]=100;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="柜面受理日期";
        iArray[10][1]="120px";
        iArray[10][2]=100;
        iArray[10][3]=0;
        
        iArray[11]=new Array();
        iArray[11][0]="生效日期";
        iArray[11][1]="120px";
        iArray[11][2]=100;
        iArray[11][3]=0;
        
        iArray[12]=new Array();
        iArray[12][0]="申请原因";
        iArray[12][1]="120px";
        iArray[12][2]=100;
        iArray[12][3]=0;
        
        iArray[13]=new Array();
        iArray[13][0]="申请原因代码";
        iArray[13][1]="80px";
        iArray[13][2]=100;
        iArray[13][3]=3;
                 
        iArray[14]=new Array();
        iArray[14][0]="补退费金额";
        iArray[14][1]="100px";
        iArray[14][2]=100;
        iArray[14][3]=3;
                 
        iArray[15]=new Array();
        iArray[15][0]="生成时间";
        iArray[15][1]="80px";
        iArray[15][2]=100;
        iArray[15][3]=3;
                 
        iArray[16]=new Array();
        iArray[16][0]="生成具体时间";
        iArray[16][1]="0px";
        iArray[16][2]=100;
        iArray[16][3]=3;
                 
        iArray[17]=new Array();
        iArray[17][0]="最后保存时间";
        iArray[17][1]="80px";
        iArray[17][2]=100;
        iArray[17][3]=3;
                 
        iArray[18]=new Array();
        iArray[18][0]="最后处理人";
        iArray[18][1]="80px";
        iArray[18][2]=100;
        iArray[18][3]=3;
                 
        iArray[19]=new Array();
        iArray[19][0]="处理状态";
        iArray[19][1]="120px";
        iArray[19][2]=100;
        iArray[19][3]=0;
        
        iArray[20]=new Array();
        iArray[20][0]="处理状态编码";
        iArray[20][1]="0px";
        iArray[20][2]=100;
        iArray[20][3]=3;

        iArray[21]=new Array();
        iArray[21][0]="批改类型编码";
        iArray[21][1]="0px";
        iArray[21][2]=100;
        iArray[21][3]=3;
        
        iArray[22]=new Array();
        iArray[22][0]="批改原因代码";
        iArray[22][1]="0px";
        iArray[22][2]=100;
        iArray[22][3]=3;
        
        iArray[23]=new Array();
        iArray[23][0]="批改原因";
        iArray[23][1]="0px";
        iArray[23][2]=100;
        iArray[23][3]=3;
        
        iArray[24]=new Array();
        iArray[24][0]="批改类型名称";
        iArray[24][1]="0px";
        iArray[24][2]=100;
        iArray[24][3]=3; 
        
        iArray[25]=new Array();
        iArray[25][0]="批改算法名称";
        iArray[25][1]="0px";
        iArray[25][2]=100;
        iArray[25][3]=3; 

      EdorItemGrid = new MulLineEnter( "document" , "EdorItemGrid" );
      //这些属性必须在loadMulLine前
      EdorItemGrid.mulLineCount = 5;
      EdorItemGrid.displayTitle = 1;
      EdorItemGrid.canSel =1;
      EdorItemGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //点击RadioBox时响应的JS函数
      EdorItemGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      EdorItemGrid.hiddenSubtraction=1;
      EdorItemGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initInusredGrid()
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
        iArray[1][0]="客户号码";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="姓名";
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
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="证件类型";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="证件号码";
        iArray[6][1]="290px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="保单号码";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="集体保单号码";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        InsuredGrid = new MulLineEnter( "document" , "InsuredGrid" );
        //这些属性必须在loadMulLine前
        InsuredGrid.mulLineCount = 5;
        InsuredGrid.displayTitle = 1;
        InsuredGrid.canChk=1;
        InsuredGrid.canSel =1;
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

function initPolGrid()
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
        iArray[1][0]="险种代码";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种号码";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人客户号";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="被保人姓名";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="保额/份数";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="期交保费";
        iArray[6][1]="70px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="生效日期"
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="交至日期";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="保单号码";
        iArray[9][1]="80px";
        iArray[9][2]=100;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="集体保单号码";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=3;

        PolGrid = new MulLineEnter( "document" , "PolGrid" );
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;
      PolGrid.displayTitle = 1;
	  PolGrid.canSel =1;
      PolGrid.canChk=1;
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

</script>
