<%
//程序名称：
//程序功能：个人保全申请
//创建日期：2005-04-26 16:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
    String CurrentDate = PubFun.getCurrentDate();
    Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
    String ValidDate = CurrentDate;
    String dayAfterCurrent = new FDate().getString(dt);
    String CurrentTime = PubFun.getCurrentTime();
%>


<script language="JavaScript">

    var CustomerContGrid, PolGrid, InusredGrid, RiskGrid, EdorItemGrid;

//页面初始化
function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initCustomerContGrid();
        initPolGrid();
        initInusredGrid();
        initRiskGrid();
        initEdorItemGrid();
        initInternalSwitchChnl();
        initDiv();
        //如果是客户重要资料变更，项目类型写死，无奈之举！
        initCM();
        //ContStateQuery();
    }
    catch (ex)
    {
        alert("在 PEdorAppInputInit.jsp --> initForm 函数中发生异常:初始化界面错误！ ");
    }
}

//接收从工作流保全申请页面传递过来的参数
function initParam()
{
   document.all('EdorAcceptNo').value = NullToEmpty("<%= tEdorAcceptNo %>");
   document.all('MissionID').value    = NullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value = NullToEmpty("<%= tSubMissionID %>");
   document.all('LoadFlag').value     = NullToEmpty("<%= tLoadFlag %>");
   document.all('UserCode').value     = NullToEmpty("<%= tUserCode %>");
   document.all('OtherNo').value     = NullToEmpty("<%= tOtherNo %>");
   document.all('ActivityID').value     = NullToEmpty("<%= tActivityID %>");
}

function initInternalSwitchChnl() {
	var sCodeData = "";
	sCodeData = "0|^01|投诉转办|^02|理赔转办|^03|核保转办|^04|新契约转办|^OO|其他";
	try { document.getElementsByName("InternalSwitchChnl")[0].CodeData = sCodeData; } catch (ex) {}
}

//初始化页面元素
function initInpBox()
{
    try
    {
        document.all('EdorAppDate').value = '<%=CurrentDate%>';
        document.all('EdorMakeDate').value = '<%=CurrentDate%>';
        document.all('EdorType').value = '';
        document.all('EdorTypeName').value = '';
        document.all('CValiDate').value = '';
        document.all('EdorItemAppDate').value = '<%=CurrentDate%>';
        //document.all('EdorValiDate').value = '<%=CurrentDate%>';
        document.all('Operator').value = "<%=tG.Operator%>";
        document.all('EdorAppDate').value = '<%=CurrentDate%>';
        document.all('ContType').value ='1';
        document.all('currentDay').value = '<%=CurrentDate%>';
        document.all('dayAfterCurrent').value = '<%=dayAfterCurrent%>';
    }
    catch (ex)
    {
        alert("在 PEdorInputInit.jsp --> initInpBox 函数中发生异常:初始化界面错误！ ");
    }
}

 function initApproveTrackGrid()
{                               
    			var iArray = new Array();
      
      		try
      		{
       			iArray[0]=new Array();
       			iArray[0][0]="序号";         //列名
       			iArray[0][1]="30px";         //列名
       			iArray[0][2]=100;         //列名
       			iArray[0][3]=0;         //列名
       			
       			iArray[1]=new Array();
       			iArray[1][0]="审批结论";         //列名
       			iArray[1][1]="60px";         //宽度
       			iArray[1][2]=100;         //最大长度
       			iArray[1][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[2]=new Array();
       			iArray[2][0]="修改原因";         //列名
       			iArray[2][1]="60px";         //宽度
       			iArray[2][2]=100;         //最大长度
       			iArray[2][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[3]=new Array();
       			iArray[3][0]="审批日期";         //列名
       			iArray[3][1]="60px";         //宽度
       			iArray[3][2]=100;         //最大长度
       			iArray[3][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[4]=new Array();
       			iArray[4][0]="审批时间";         //列名
       			iArray[4][1]="60px";         //宽度
       			iArray[4][2]=100;         //最大长度
       			iArray[4][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[5]=new Array();
       			iArray[5][0]="审批人";         //列名
       			iArray[5][1]="50px";         //宽度
       			iArray[5][2]=100;         //最大长度
       			iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
 
       			iArray[6]=new Array();
       			iArray[6][0]="是否为差错件";         //列名
       			iArray[6][1]="60px";         //宽度
       			iArray[6][2]=100;         //最大长度
       			iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[7]=new Array();
       			iArray[7][0]="审批意见";         //列名
       			iArray[7][1]="0px";         //宽度
       			iArray[7][2]=100;         //最大长度
       			iArray[7][3]=3;         //是否允许录入，0--不能，1--允许

     		
       		ApproveTrackGrid = new MulLineEnter( "fm" , "ApproveTrackGrid" ); 
       		
       		//这些属性必须在loadMulLine前
       		//AgentGrid.mulLineCount = 1;   
       		ApproveTrackGrid.displayTitle = 1;
       		ApproveTrackGrid.canSel=1;
//     		ApproveTrackGrid.canChk=0;
       		ApproveTrackGrid.locked=1;
	     		ApproveTrackGrid.hiddenPlus=1;
	     		ApproveTrackGrid.hiddenSubtraction=1;
	     		ApproveTrackGrid.selBoxEventFuncName = "ShowApproveInfo"; //单击RadioBox时响应函数
       		ApproveTrackGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化ApproveTrackGrid时出错："+ ex);
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
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保全受理号";
        iArray[1][1]="0px";
        iArray[1][2]=0;
        iArray[1][3]=3;

        iArray[2]=new Array();
        iArray[2][0]="批单号";
        iArray[2][1]="0px";
        iArray[2][2]=0;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="批改类型";
        iArray[3][1]="120px";
        iArray[3][2]=200;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="批改类型显示级别";
        iArray[4][1]="0px";
        iArray[4][2]=0;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="集体合同号码";
        iArray[5][1]="0px";
        iArray[5][2]=0;
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="保单号码";
        iArray[6][1]="110px";
        iArray[6][2]=150;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="客户号码";
        iArray[7][1]="70px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="保单险种号码";
        iArray[8][1]="120px";
        iArray[8][2]=150;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="柜面受理日期";
        iArray[9][1]="70px";
        iArray[9][2]=100;
        iArray[9][3]=8;
        iArray[9][21]=3;
        iArray[9][23]=1;

        iArray[10]=new Array();
        iArray[10][0]="生效日期";
        iArray[10][1]="70px";
        iArray[10][2]=100;
        iArray[10][3]=8;
        iArray[10][21]=3;
        iArray[10][23]=1;

        iArray[11]=new Array();
        iArray[11][0]="申请原因";
        iArray[11][1]="70px";
        iArray[11][2]=100;
        iArray[11][3]=0;

        iArray[12]=new Array();
        iArray[12][0]="申请原因代码";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="补退费金额";
        iArray[13][1]="0px";
        iArray[13][2]=0;
        iArray[13][3]=3;

        iArray[14]=new Array();
        iArray[14][0]="生成时间";
        iArray[14][1]="0px";
        iArray[14][2]=0;
        iArray[14][3]=3;

        iArray[15]=new Array();
        iArray[15][0]="生成具体时间";
        iArray[15][1]="0px";
        iArray[15][2]=0;
        iArray[15][3]=3;

        iArray[16]=new Array();
        iArray[16][0]="最后保存时间";
        iArray[16][1]="0px";
        iArray[16][2]=0;
        iArray[16][3]=3;

        iArray[17]=new Array();
        iArray[17][0]="最后处理人";
        iArray[17][1]="0px";
        iArray[17][2]=0;
        iArray[17][3]=3;

        iArray[18]=new Array();
        iArray[18][0]="处理状态";
        iArray[18][1]="70px";
        iArray[18][2]=100;
        iArray[18][3]=0;

        iArray[19]=new Array();
        iArray[19][0]="处理状态编码";
        iArray[19][1]="0px";
        iArray[19][2]=0;
        iArray[19][3]=3;

        iArray[20]=new Array();
        iArray[20][0]="批改类型编码";
        iArray[20][1]="0px";
        iArray[20][2]=0;
        iArray[20][3]=3;

        EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
        //这些属性必须在loadMulLine前
        EdorItemGrid.mulLineCount = 5;
        EdorItemGrid.displayTitle = 1;
        EdorItemGrid.canSel =1;
        EdorItemGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //点击RadioBox时响应的JS函数
        EdorItemGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        EdorItemGrid.hiddenSubtraction=1;
        EdorItemGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorAppInputInit.jsp --> initEdorItemGrid 函数中发生异常:初始化界面错误！ ");
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
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="客户角色";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户号";
        iArray[2][1]="90px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="90px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="90px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="90px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="140px";
        iArray[7][2]=200;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="保单号码";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="集体保单号码";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="排列序号";
        iArray[10][1]="0px";
        iArray[10][2]=0;
        iArray[10][3]=3;

        InsuredGrid = new MulLineEnter("fm", "InsuredGrid");
        //这些属性必须在loadMulLine前
        InsuredGrid.mulLineCount = 5;
        InsuredGrid.displayTitle = 1;
        InsuredGrid.canChk=0;
        InsuredGrid.canSel =1;
        InsuredGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        InsuredGrid.hiddenSubtraction=1;
        InsuredGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面
      }
      catch (ex)
      {
          alert("在 PEdorAppInputInit.jsp --> initInsuredGrid 函数中发生异常:初始化界面错误！ ");
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
        iArray[2][0]="险种号码";
        iArray[2][1]="130px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人客户号";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="被保人姓名";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="基本保额/份数";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="保费标准";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="生效日期"
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        iArray[7][21]=3;

        iArray[8]=new Array();
        iArray[8][0]="交费对应日";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=0;
        iArray[8][21]=3;

        iArray[9]=new Array();
        iArray[9][0]="保单号码";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="集体保单号码";
        iArray[10][1]="0px";
        iArray[10][2]=0;
        iArray[10][3]=3;

        PolGrid = new MulLineEnter( "fm" , "PolGrid" );
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 5;
        PolGrid.displayTitle = 1;
        PolGrid.canChk=1;
        PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorAppInputInit.jsp --> initPolGrid 函数中发生异常:初始化界面错误！ ");
    }
}

function initCustomerContGrid()
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
        iArray[1][0]="保单号";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="投保人";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="业务员";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="生效日期";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="签收日期";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="承保状态";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        iArray[7][21]=2;

        iArray[8]=new Array();
        iArray[8][0]="是否参与变更";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        CustomerContGrid = new MulLineEnter("fm", "CustomerContGrid");
        //这些属性必须在loadMulLine前
        CustomerContGrid.mulLineCount = 3;
        CustomerContGrid.displayTitle = 1;
        CustomerContGrid.canChk=0;
        CustomerContGrid.canSel =1;
        CustomerContGrid.selBoxEventFuncName ="showContStateInfo" ;     //点击RadioBox时响应的JS函数
        CustomerContGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        CustomerContGrid.hiddenSubtraction=1;
        CustomerContGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorAppInputInit.jsp --> initCustomerContGrid 函数中发生异常:初始化界面错误！ ");
    }
}

function initRiskGrid()
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
        iArray[2][1]="160px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人姓名";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="基本保额";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]=1;

        iArray[5]=new Array();
        iArray[5][0]="份数";
        iArray[5][1]="60px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;
        

        iArray[6]=new Array();
        iArray[6][0]="保费标准";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][21]=3;
        iArray[6][23]=1;
        
        iArray[7]=new Array();
        iArray[7][0]="币种";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=2;
        iArray[7][4]="currency";
        
        iArray[8]=new Array();
        iArray[8][0]="健康加费";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=7;
        iArray[8][21]=3;
        iArray[8][23]="0";

        iArray[9]=new Array();
        iArray[9][0]="职业加费";
        iArray[9][1]="80px";
        iArray[9][2]=100;
        iArray[9][3]=7;
        iArray[9][21]=3;
        iArray[9][23]="0";

        iArray[10]=new Array();
        iArray[10][0]="险种号码";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=3;

        iArray[11]=new Array();
        iArray[11][0]="交费对应日";
        iArray[11][1]="80px";
        iArray[11][2]=100;
        iArray[11][3]=0;
        iArray[11][21]=3;

        RiskGrid = new MulLineEnter("fm", "RiskGrid");
        //这些属性必须在loadMulLine前
        RiskGrid.mulLineCount = 5;
        RiskGrid.displayTitle = 1;
        RiskGrid.canChk=0;
        RiskGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        RiskGrid.hiddenSubtraction=1;
        RiskGrid.loadMulLine(iArray);
      }
      catch (ex)
      {
          alert("在 PEdorAppInputInit.jsp --> initRiskGrid 函数中发生异常:初始化界面错误！ ");
      }
}

</script>
