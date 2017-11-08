
<script language="JavaScript">

    var PolGrid;

function initForm()
{
  try
  {
  	//alert('1');
    edorQuery();
    
  //alert('2');
    initInpBox();
    //alert('3');
    initPolGrid();
   // alert('4');
    queryCustomerInfo();
   // alert('5');
    queryPolInfo();
   // alert('6');
    queryOldManageCom();
  //  alert('7');
    queryNewManageCom();
  //  alert('8');
    initComCode();
  }
  catch(re)
  {
    alert("PEdorTypePRInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{
  try
  {
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    showOneCodeName('EdorType', 'EdorType', 'EdorTypeName');
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
  }
  catch(ex)
  {
    alert("在PEdorTypePRInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
        iArray[1][0]="被保险人号码";
        iArray[1][1]="70px";
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
        iArray[4][1]="250px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="保费标准";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="基本保额";
        iArray[6][1]="70px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][21]=3;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="生效日期";
        iArray[7][1]="70px";
        iArray[7][2]=100;
        iArray[7][3]=8;
        iArray[7][21]=3;

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

        PolGrid = new MulLineEnter( "fm" , "PolGrid" );
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 3;
        PolGrid.displayTitle = 1;
        PolGrid.canChk=0;
        PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面

    }
    catch(ex)
    {
        alert("在 PEdorTypePRInit.jsp --> initPolGrid 函数中发生异常: 初始化界面错误！");
    }
}

</script>
