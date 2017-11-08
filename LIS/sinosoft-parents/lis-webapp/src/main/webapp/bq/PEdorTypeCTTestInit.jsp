<%
//程序名称：PEdorTypeCTTestInit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Supl
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%
  String CurrentDate = PubFun.getCurrentDate();
%>                         

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {        

    fm.ContNo.value = "";
    fm.EdorItemAppDate.value = nullToEmpty("<%= CurrentDate %>");  
    
  }
  catch(ex)
  {
    alert("在PEdorTypeCTInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
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

function initSelBox()
{  
  try                 
  {  
  }
  catch(ex)
  {
    alert("在PEdorTypeCTInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    
  }
  catch(re)
  {
    alert("PEdorTypeCTInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInusredGrid()

{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="100px";
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
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;        
        
        
        iArray[5]=new Array();
        iArray[5][0]="保单号码";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=3;
        
        iArray[6]=new Array();
        iArray[6][0]="集体保单号码";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
        //这些属性必须在loadMulLine前
        InsuredGrid.mulLineCount = 0;   
        InsuredGrid.displayTitle = 1;
        //alert(fm.DisplayType.value);        
        InsuredGrid.canChk=1;                                                                  
        InsuredGrid.canSel =1;                                                                 
        InsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数

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
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="险种代码";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="200px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人姓名";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="基本保额";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="标准保费";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;        
        
        iArray[6]=new Array();
        iArray[6][0]="健康加费";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="职业加费";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="保单号码";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;
        
        
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
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

function initCustomerGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="70px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="30px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="40px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="60px";
        iArray[7][2]=100;
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


function initCTFeeDetailGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="险种号";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="费用名称";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="费用金额";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=0;        
        
        iArray[6]=new Array();
        iArray[6][0]="费用类型";
        iArray[6][1]="40px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        
      CTFeeDetailGrid = new MulLineEnter( "fm" , "CTFeeDetailGrid" ); 
      //这些属性必须在loadMulLine前
      CTFeeDetailGrid.mulLineCount = 0;   
      CTFeeDetailGrid.displayTitle = 1;
      CTFeeDetailGrid.canChk=0;
      CTFeeDetailGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CTFeeDetailGrid.hiddenSubtraction=1;
      CTFeeDetailGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initCTFeePolGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="险种号";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="150px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="退费合计";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        
      CTFeePolGrid = new MulLineEnter( "fm" , "CTFeePolGrid" ); 
      //这些属性必须在loadMulLine前
      CTFeePolGrid.mulLineCount = 0;   
      CTFeePolGrid.displayTitle = 1;
      CTFeePolGrid.canChk=0;
      CTFeePolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CTFeePolGrid.hiddenSubtraction=1;
      CTFeePolGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>