<%
//程序名称：PEdorTypeNSInit.jsp
//程序功能：
//创建日期：2005-07-20
//创建人  ：lizhuo
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">

function initForm()
{
  try
  {
    Edorquery();
    initInpBox();
    initPolGrid();
    initNewPolGrid();
    getPolInfo();
    getNewPolInfo();
    queryCustomerInfo();
    initImpartGrid();
    showimpart();
    showAddNewTypeInfo();
  }
  catch(re)
  {

    alert("PEdorTypeNSInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{
  try
  {
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('MainPolNo').value = top.opener.document.all('PolNo').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    
    document.all('CureCvaliDate').value     = NullToEmpty("<%= CureCvaliDate %>");
    document.all('PreCvaliDate').value        = NullToEmpty("<%= PreCvaliDate %>");
    document.all('NextCvaliDate').value     = NullToEmpty("<%= NextCvaliDate %>");
    document.all('CurDate').value     = NullToEmpty("<%= tCurDate %>");  
    	   
    //存放主险保单号
    //alert(document.all('MainPolNo').value );
    showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeNSInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
        iArray[1][0]="被保人客户号";
        iArray[1][1]="0px";
        iArray[1][2]=150;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="姓名";
        iArray[2][1]="40px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种代码";
        iArray[3][1]="60px";
        iArray[3][2]=150;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="险种名称";
        iArray[4][1]="158px";
        iArray[4][2]=200;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="保费标准";
        iArray[5][1]="60px";
        iArray[5][2]=150;
        iArray[5][3]=0;
        iArray[5][21]=0;

        iArray[6]=new Array();
        iArray[6][0]="基本保额";
        iArray[6][1]="60px";
        iArray[6][2]=150;
        iArray[6][3]=0;
        iArray[6][21]=0;

        iArray[7]=new Array();
        iArray[7][0]="生效日期";
        iArray[7][1]="60px";
        iArray[7][2]=150;
        iArray[7][3]=0;
        iArray[7][21]=0;

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
        iArray[10][0]="保险期间";
        iArray[10][1]="60px";
        iArray[10][2]=0;
        iArray[10][3]=0;
        

        iArray[11]=new Array();
        iArray[11][0]="交费年期";
        iArray[11][1]="60px";
        iArray[11][2]=0;
        iArray[11][3]=0;        
 
        
        iArray[12]=new Array();
        iArray[12][0]="交至日期";
        iArray[12][1]="60px";
        iArray[12][2]=0;
        iArray[12][3]=0;        

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
        alert(ex);
      }
}

function initNewPolGrid()
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
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="160px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="保费标准";
        iArray[3][1]="60px";
        iArray[3][2]=150;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="基本保额";
        iArray[4][1]="60px";
        iArray[4][2]=150;
        iArray[4][3]=0;
        iArray[4][21]=3;

        iArray[5]=new Array();
        iArray[5][0]="生效日期";
        iArray[5][1]="60px";
        iArray[5][2]=150;
        iArray[5][3]=1;


        iArray[6]=new Array();
        iArray[6][0]="保单号码";
        iArray[6][1]="0px";
        iArray[6][2]=0;
        iArray[6][3]=0;                 

        iArray[7]=new Array();
        iArray[7][0]="集体保单号码";
        iArray[7][1]="0px";
        iArray[7][2]=0;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="险种号";
        iArray[8][1]="100px";
        iArray[8][2]=0;
        iArray[8][3]=0;
        
        
        
        iArray[9]=new Array();
        iArray[9][0]="保险期间";
        iArray[9][1]="60px";
        iArray[9][2]=0;
        iArray[9][3]=0;
        

        iArray[10]=new Array();
        iArray[10][0]="交费年期";
        iArray[10][1]="60px";
        iArray[10][2]=0;
        iArray[10][3]=0;        
 
        
        iArray[11]=new Array();
        iArray[11][0]="交至日期";
        iArray[11][1]="60px";
        iArray[11][2]=0;
        iArray[11][3]=0;   

        
 //       iArray[12]=new Array();      
 //       iArray[12][0]="新增编码";         		//列名      
 //       iArray[12][1]="60px";            		//列宽      
 //       iArray[12][2]=60;            			//列最大值      
 //       iArray[12][3]=2;              			//是否允许输入,1表示允许，0表示不允许      
 //       iArray[12][4]="NewAddType";      
 //       iArray[12][5]="12|13";      
 //       iArray[12][6]="0|1";      
 //       iArray[12][9]="新增编码|len<=5";      
 //       iArray[12][15]="newaddtype";      
 //       iArray[12][17]="1";      
 //       iArray[12][18]=700;      
 //             
 //       iArray[13]=new Array();      
 //       iArray[13][0]="新增类型";         		//列名      
 //       iArray[13][1]="60px";            		//列宽      
 //       iArray[13][2]=200;            			//列最大值      
 //       iArray[13][3]=1;              			//是否允许输入,1表示允许，0表示不允许      



        NewPolGrid = new MulLineEnter( "fm" , "NewPolGrid" );
        //这些属性必须在loadMulLine前
        NewPolGrid.mulLineCount = 0;
        NewPolGrid.displayTitle = 1;
        NewPolGrid.canSel=1;
        NewPolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        NewPolGrid.hiddenSubtraction=1;
        NewPolGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initImpartGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="impartver";
      //add by jiaqiangli 2009-03-12 保全只取健康告知与财务告知
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A01#,#A02#)";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      //iArray[2][16]="#02#";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 0;
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {

    }
}


</script>
