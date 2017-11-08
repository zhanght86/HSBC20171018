 <%
//程序名称：TempFeeInit.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  { 
       
  }
  catch(ex)
  {
    alert("在TempFeeInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在TempFeeInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    
    initBnfGrid();
    initSubInsuredGrid();
    initDutyGrid();   
    initPremGrid();
    initFactorGrid();
  
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 暂收费信息列表的初始化
function initRiskGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";          		//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][18]=300;
      //iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         			//列名
      iArray[2][1]="140px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="交费周期";      	   		//列名
      iArray[3][1]="100px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 

      iArray[4]=new Array();
      iArray[4][0]="计算方向";      	   		//列名
      iArray[4][1]="160px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 
               			//是否允许输入,1表示允许，0表示不允许

      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
      //这些属性必须在loadMulLine前
      RiskGrid.mulLineCount = 0;   
      RiskGrid.displayTitle = 1;
      RiskGrid.canSel = 1;
      RiskGrid.canChk=0
      RiskGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      RiskGrid. hiddenSubtraction=1;

      RiskGrid.loadMulLine(iArray);  
      
      RiskGrid.addOne("RiskGrid");     
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,1,'111302');
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,2,"[险种名称]");
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,3,"0");
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,4,"P");
     RiskGrid.addOne("RiskGrid");     
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,1,'211601');
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,2,"[险种名称]");
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,3,"0");
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,4,"P");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// 被保人信息列表的初始化
function initSubInsuredGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="连带被保人客户号";    	//列名
      iArray[1][1]="180px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][7]="showSubInsured";
      iArray[1][8]="['SubInsured']";        //是否使用自己编写的函数 

      iArray[2]=new Array();
      iArray[2][0]="姓名";         			//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="性别";         			//列名
      iArray[3][1]="140px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="出生日期";         		//列名
      iArray[4][1]="140px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="与被保人关系";         		//列名
      iArray[5][1]="160px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="Relation";

      SubInsuredGrid = new MulLineEnter( "fm" , "SubInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      SubInsuredGrid.mulLineCount = 0;   
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 受益人信息列表的初始化

function initBnfGrid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许
  
    iArray[1]=new Array();
    iArray[1][0]="受益人类别"; 		//列名
    iArray[1][1]="60px";		//列宽
    iArray[1][2]=40;			//列最大值
    iArray[1][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="BnfType";
    iArray[1][9]="受益人类别|notnull&code:BnfType";
   
    iArray[2]=new Array();
    iArray[2][0]="姓名"; 	//列名
    iArray[2][1]="30px";		//列宽
    iArray[2][2]=30;			//列最大值
    iArray[2][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[2][9]="姓名|len<=20"; //校验
  
    iArray[3]=new Array();
    iArray[3][0]="证件类型"; 		//列名
    iArray[3][1]="40px";		//列宽
    iArray[3][2]=40;			//列最大值
    iArray[3][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[3][4]="IDType";
    iArray[3][9]="证件类型|code:IDType";
  
    iArray[4]=new Array();
    iArray[4][0]="证件号码"; 		//列名
    iArray[4][1]="120px";		//列宽
    iArray[4][2]=80;			//列最大值
    iArray[4][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[4][9]="证件号码|len<=20";

    iArray[5]=new Array();
    iArray[5][0]="与被保人关系"; 	//列名
    iArray[5][1]="60px";		//列宽
    iArray[5][2]=60;			//列最大值
    iArray[5][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[5][4]="Relation";
    iArray[5][9]="与被保人关系|code:Relation";
  
    iArray[6]=new Array();
    iArray[6][0]="受益比例"; 		//列名
    iArray[6][1]="40px";		//列宽
    iArray[6][2]=40;			//列最大值
    iArray[6][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[6][9]="受益比例|num&len<=10";
  
    iArray[7]=new Array();
    iArray[7][0]="受益顺序"; 		//列名
    iArray[7][1]="40px";		//列宽
    iArray[7][2]=40;			//列最大值
    iArray[7][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[7][4]="OccupationType";
    iArray[7][9]="受益顺序|code:OccupationType";
  
    iArray[8]=new Array();
    iArray[8][0]="住址（填序号）"; 		//列名
    iArray[8][1]="160px";		//列宽
    iArray[8][2]=100;			//列最大值
    iArray[8][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[8][9]="住址|len<=80";
  
    iArray[9]=new Array();
    iArray[9][0]="速填"; 		//列名
    iArray[9][1]="30px";		//列宽
    iArray[9][2]=30;			//列最大值
    iArray[9][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[9][4]="customertype";
  
    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" ); 
    //这些属性必须在loadMulLine前
    BnfGrid.mulLineCount = 1; 
    BnfGrid.displayTitle = 1;
    BnfGrid.loadMulLine(iArray); 
  
    //这些操作必须在loadMulLine后面
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert(ex);
  }
}


// 特别约定信息列表的初始化
function initSpecGrid() {                               
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="0px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="特约类型";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="特约编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="特约内容";         		//列名
      iArray[3][1]="540px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="特约内容|len<=255";
      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" );
      //这些属性必须在loadMulLine前
      SpecGrid.mulLineCount = 1;   
      SpecGrid.displayTitle = 0;
      SpecGrid.hiddenPlus = 1;
      SpecGrid.hiddenSubtraction = 1;
      SpecGrid.loadMulLine(iArray);           
      //这些操作必须在loadMulLine后面
      //SpecGrid.setRowColData(1,1,"asdf");
      SpecGrid.setRowColData(0,1,"1");
      SpecGrid.setRowColData(0,2,"1");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//责任列表
function initDutyGrid()

{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="50px";            		//列宽
      iArray[1][2]=100;            			//列最大值
     iArray[1][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="DutyCode";
    iArray[1][9]="责任编码|notnull&code:DutyCode";

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保费";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交费年期";         			//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="年期单位";         			//列名
      iArray[6][1]="40px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许


      iArray[7]=new Array();
      iArray[7][0]="领取年期";         			//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="年期单位";         			//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;             			//是否允许输入,1表示允许，0表示不允许


      iArray[9]=new Array();
      iArray[9][0]="保险年期";         			//列名
      iArray[9][1]="40px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="年期单位";         			//列名
      iArray[10][1]="40px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="交费方式";         			//列名
      iArray[11][1]="40px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[12]=new Array();
      iArray[12][0]="管理费比例";         			//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=1;              			//是否允许输入,1表示允许，0表示不允许
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //这些属性必须在loadMulLine前
      DutyGrid.mulLineCount = 1;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
//保费项列表
function initPremGrid()
{
    var iArray = new Array();
      try 
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
       iArray[1][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="DutyCode";
    iArray[1][9]="责任编码|notnull&code:DutyCode";

      iArray[2]=new Array();
      iArray[2][0]="交费编码";         			//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
        iArray[2][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[2][4]="PlanCode";
    iArray[2][9]="交费编码|notnull&code:PlanCode";

      iArray[3]=new Array();
      iArray[3][0]="交费名称";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保费";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="分配比例";         			//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="关联帐户号";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许


      iArray[7]=new Array();
      iArray[7][0]="帐户分配比率";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[8]=new Array();
      iArray[8][0]="管理费比例";         			//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
	  
     
        if(typeof(window.spanPremGrid) == "undefined" )
        {
              //alert("out");
              return false;
        }
        else
        {
      			
      			
      			
      			//alert("in");
      	    PremGrid = new MulLineEnter( "fm" , "PremGrid" );
	  	    //这些属性必须在loadMulLine前
	 	     PremGrid.mulLineCount = 1;   
	 	     PremGrid.displayTitle = 1;
	 	     PremGrid.canChk = 1;
	 	     PremGrid.loadMulLine(iArray);  
     	 }

      
     }
     catch(ex)
    {
		return false;
    }
    return true;
}
// 要约信息列表的初始化
function initFactorGrid() {  
                             
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="要素类别";    	        //列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
       iArray[1][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="DutyCode";
    iArray[1][9]="要素类别|notnull&code:DutyCode";
      
      iArray[2]=new Array();
      iArray[2][0]="要素目标编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="要素计算编码";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=2;			//是否允许输入,1表示允许，0表示不允许
    	iArray[3][4]="DutyCode";
    iArray[3][9]="要素计算编码|notnull&code:DutyCode";

      iArray[4]=new Array();
      iArray[4][0]="要素内容";         		//列名
      iArray[4][1]="300px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="要素值";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=150;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="险种编码";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许  
      iArray[6][4]="RiskCode";

      FactorGrid = new MulLineEnter( "fm" , "FactorGrid" ); 
      //这些属性必须在loadMulLine前
      FactorGrid.mulLineCount = 1;   
      FactorGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      FactorGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}


  
</script>
