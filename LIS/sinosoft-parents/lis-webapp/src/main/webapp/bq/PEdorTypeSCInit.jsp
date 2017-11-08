<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2007-12-10 16:49:22
//创建人  ：PST 
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->


<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initLCCSpecGrid();
    initSpecBox();
    initLCPReSpecGrid();
    initPreSpecBox();
    initLPCSpecGrid();
    initPSpecBox();

  }
  catch(re)
  {
    alert("PEdorTypeSCInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{

  try
  {
    Edorquery();
    try { document.getElementsByName("AppObj")[0].value = top.opener.document.getElementsByName("AppObj")[0].value; } catch (ex) {}
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    try { document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value; } catch (ex) {}
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    fm.PolNo.value=top.opener.document.all('PolNo').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
    fm.Speccontent.value="";
  }
  catch(ex)
  {
    alert("在PEdorTypeSCInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!" + ex);
  }
}

function initLCPReSpecGrid()
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
        iArray[1][0]="合同号";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种号";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="保全申请号";
        iArray[3][1]="0px";
        iArray[3][2]=100;
        iArray[3][3]=0;



        iArray[4]=new Array();
        iArray[4][0]="特约内容";
        iArray[4][1]="400px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="流水号";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="打印流水号";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="特约类型";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=0;
                
        LCPReSpecGrid = new MulLineEnter( "fm" , "LCPReSpecGrid" );
        //这些属性必须在loadMulLine前
        LCPReSpecGrid.mulLineCount = 0;
        LCPReSpecGrid.displayTitle = 1;
        LCPReSpecGrid.canSel=0;
        LCPReSpecGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LCPReSpecGrid.hiddenSubtraction=1; 
        //LCPReSpecGrid.selBoxEventFuncName ="showSpecInfo";
        LCPReSpecGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLCCSpecGrid()
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
        iArray[1][0]="合同号";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种号";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="保全申请号";
        iArray[3][1]="0px";
        iArray[3][2]=100;
        iArray[3][3]=0;



        iArray[4]=new Array();
        iArray[4][0]="特约内容";
        iArray[4][1]="400px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="流水号";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="打印流水号";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="特约类型";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=0;
                
        LCCSpecGrid = new MulLineEnter( "fm" , "LCCSpecGrid" );
        //这些属性必须在loadMulLine前
        LCCSpecGrid.mulLineCount = 0;
        LCCSpecGrid.displayTitle = 1;
        LCCSpecGrid.canSel=1;
        LCCSpecGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LCCSpecGrid.hiddenSubtraction=1; 
        LCCSpecGrid.selBoxEventFuncName ="showSpecInfo";
        LCCSpecGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLPCSpecGrid()
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
        iArray[1][0]="合同号";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种号";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="保全申请号";
        iArray[3][1]="0px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="特约内容";
        iArray[4][1]="400px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="流水号";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="打印流水号";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=0;
 
        iArray[7]=new Array();
        iArray[7][0]="特约类型";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=0;       
        LPCSpecGrid = new MulLineEnter( "fm" , "LPCSpecGrid" );
        //这些属性必须在loadMulLine前
        LPCSpecGrid.mulLineCount = 0;
        LPCSpecGrid.displayTitle = 1;
        LPCSpecGrid.canSel=0;
        LPCSpecGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LPCSpecGrid.hiddenSubtraction=1; 
        LPCSpecGrid.selBoxEventFuncName ="showPSpecInfo";
        LPCSpecGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面
    }
    catch(ex)
    {
      alert(ex);
    }
}
</script>
