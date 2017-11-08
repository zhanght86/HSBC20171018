<%
//程序名称：PEdorTypeIPInit.jsp
//程序功能：
//创建日期：2007-04-16
//创建人  ：tp
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%
    
%>                            

<script language="JavaScript">
	
	var mgVar = 0;
function initInpBox()
{
  try
  {
  	mgVar = 0;
    Edorquery();
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    showOneCodeName('PEdorType','EdorType','EdorTypeName');
    //    chkPol();  //上次被退保的险种打勾

    getContZTInfo();
    getAgentToAppntRelation("IP");
    
    //initCTType();
  }
  catch(ex)
  {
    alert("在PEdorTypeIPInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


function initForm()
{
  try
  {

    initInpBox();
    initCustomerGrid();
    initPolGrid();
    initInsuAccGrid();
    initPolGridb(mgVar);

    getCustomerGrid();
    
    getPolGrid(document.all('ContNo').value);

    getPolGridb(document.all('ContNo').value);
   
  }
  catch (ex)
  {
  alert("误 "+ex.message);
  alert("PEdorTypeIPInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
        iArray[2][1]="200px";
        iArray[2][2]=300;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人姓名";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="帐户单位数";
        iArray[4][1]="0px";
        iArray[4][2]=0;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="保费标准";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="健康加费";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="职业加费";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=7;
        iArray[7][23]="0";

        iArray[8]=new Array();
        iArray[8][0]="保单号码";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
				iArray[9][0]="币种";
				iArray[9][1]="60px";
				iArray[9][2]=100;
				iArray[9][3]=2;
				iArray[9][4]="currency";


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.canSel=1;
      PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolGrid.hiddenSubtraction=1;
      PolGrid.selBoxEventFuncName ="getInsuAcc" ;
      PolGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}



//追加记录
function initPolGridb(mgVar)
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
        iArray[2][2]=300;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="交费账户";
        iArray[3][1]="0px";
        iArray[3][2]=100;
        iArray[3][3]=3;
        
        iArray[4]=new Array();
        iArray[4][0]="交费账户名称";
        iArray[4][1]="0px";
        iArray[4][2]=100;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="投资账户";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="投资账户名称";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="账户单位数";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="账户预估金额";
        iArray[8][1]="60px";
        iArray[8][2]=100;
        iArray[8][3]=7;
        iArray[8][23]="0";

        iArray[9]=new Array();
        iArray[9][0]="保单号码";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="单位卖出价格";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=7;
        iArray[10][23]="0";
        
        iArray[11]=new Array();
        iArray[11][0]="帐户比例";
        iArray[11][1]="0px";
        iArray[11][2]=100;
        iArray[11][3]=3;
        
 				iArray[12]=new Array();
				iArray[12][0]="币种";
				iArray[12][1]="40px";
				iArray[12][2]=100;
				iArray[12][3]=2;
				iArray[12][4]="currency";
				
				iArray[13]=new Array();
				iArray[13][0]="追加金额";
				iArray[13][1]="60px";
				iArray[13][2]=100;
				iArray[13][3]=7;
				iArray[13][23]="0";
				
				iArray[14]=new Array();
				iArray[14][0]="管理费";
				iArray[14][1]=mgVar+"px";
				iArray[14][2]=100;
				iArray[14][3]=7;
				iArray[14][23]="0";

      PolGridb = new MulLineEnter( "fm" , "PolGridb" );
      //这些属性必须在loadMulLine前
      PolGridb.mulLineCount = 0;
      PolGridb.displayTitle = 1;
      PolGridb.canChk=1;
      PolGridb.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolGridb.hiddenSubtraction=1;
      //PolGridb.selBoxEventFuncName ="getInsuAccb" ;
      PolGridb.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}





function initInsuAccGrid()
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
        iArray[2][2]=300;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="交费账户";
        iArray[3][1]="0px";
        iArray[3][2]=100;
        iArray[3][3]=3;
        
        iArray[4]=new Array();
        iArray[4][0]="交费账户名称";
        iArray[4][1]="0px";
        iArray[4][2]=100;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="投资账户";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="投资账户名称";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="账户单位数";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="账户预估金额";
        iArray[8][1]="60px";
        iArray[8][2]=100;
        iArray[8][3]=7;
        iArray[8][23]="0";

        iArray[9]=new Array();
        iArray[9][0]="保单号码";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="单位卖出价格";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=7;
        iArray[10][23]="0";
        
        iArray[11]=new Array();
        iArray[11][0]="帐户比例";
        iArray[11][1]="0px";
        iArray[11][2]=100;
        iArray[11][3]=3;
        
 				iArray[12]=new Array();
				iArray[12][0]="币种";
				iArray[12][1]="40px";
				iArray[12][2]=100;
				iArray[12][3]=2;
				iArray[12][4]="currency";
				
				iArray[13]=new Array();
				iArray[13][0]="追加金额";
				iArray[13][1]="60px";
				iArray[13][2]=100;
				iArray[13][3]=7;



      InsuAccGrid = new MulLineEnter( "fm" , "InsuAccGrid" );
      //这些属性必须在loadMulLine前
      InsuAccGrid.mulLineCount = 0;
      InsuAccGrid.displayTitle = 1;
      InsuAccGrid.canSel=0;
      InsuAccGrid.canChk=1;
      InsuAccGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      InsuAccGrid.hiddenSubtraction=1;
      InsuAccGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

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



</script>