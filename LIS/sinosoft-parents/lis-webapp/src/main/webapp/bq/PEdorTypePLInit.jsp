<%
//程序名称：PEdorTypePLInit.jsp
//程序功能：
//创建日期：2005-5-24 11:12上午
//创建人  ：Lihs
//更新记录：  更新人    更新日期     更新原因/内容
//             liurx    2005-06-28
%>
<%
   String mCurrentDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initPolGrid();
    getPolInfo();
    initCustomerGrid();
    chkPol();
    ShowCustomerInfo();
    querySignDate();
    var tDate;
    if(fm.Flag.value == "0")
    {
      tDate = '<%=mCurrentDate%>';
    }
    else
    {
    	//modify by liuxiaosong 保全复核的时候该字段导致错误 2007-1-15
      	tDate = document.all('EdorItemAppDate').value;      	    
    }
    //alert(tDate);
    ReportByLoseFormV(tDate); //判断是挂失还是解挂
  }
  catch(re)
  {
    alert("PEdorTypePLInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initInpBox()
{
  Edorquery();
  try
  {
     document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
     document.all('ContNo').value = top.opener.document.all('ContNo').value;
     document.all('EdorType').value = top.opener.document.all('EdorType').value;
     document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
     document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
     document.all('PolNo').value = top.opener.document.all('PolNo').value;
     document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
     document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
     showOneCodeName('PEdorType','EdorType','EdorTypeName');
  }
  catch(ex)
  {
     alert("在PEdorTypePLInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在PEdorTypePLInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=8;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="90px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="120px";
        iArray[7][2]=150;
        iArray[7][3]=0;

      CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 1;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canChk=0;
      CustomerGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CustomerGrid.hiddenSubtraction=1;
      CustomerGrid.loadMulLine(iArray);
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
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="被保人号";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="姓名";
        iArray[2][1]="70px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种代码";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="险种名称";
        iArray[4][1]="200px";
        iArray[4][2]=300;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="保费标准";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="基本保额";
        iArray[6][1]="70px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="生效日期";
        iArray[7][1]="70px";
        iArray[7][2]=100;
        iArray[7][3]=8;

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
				iArray[10][0]="币种";
				iArray[10][1]="60px";
				iArray[10][2]=100;
				iArray[10][3]=2;
				iArray[10][4]="currency";

      PolGrid = new MulLineEnter("fm", "PolGrid");
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
      PolGrid.canChk=0;
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