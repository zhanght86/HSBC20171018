<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>  
<script language="JavaScript">

function initForm()
{

  try
  { 
    initInpBox();    
    initProductSaleTraceGrid();  
    initProductSaleConfigGrid();
    queryRiskConfig();
    //queryData('1');
    //alert("000000000000000000000");     
  }
  catch(re)
  {
    alert("在BonusRiskPreInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{ 
  try
  {     
    document.all('RiskCode').value = '';
    document.all('ComGroup').value = '';
    document.all('ComGroupName').value = '';
    document.all('RiskCodeName').value = '';
    document.all('SaleChnl').value = '';
    document.all('SaleChnlName').value = '';
    document.all('PolApplyDateStart').value = '';
    document.all('PolApplyDateEnd').value = '';
    document.all('ScanDateStart').value = '';
    document.all('ScanDateEnd').value = '';
    document.all('ScanTimeStart').value = '';
    document.all('ScanTimeEnd').value = '';
   
  }
  catch(ex)
  {
    alert("在NBonusRiskPreInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
  
  function initProductSaleTraceGrid()
{                              
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";          	//列名
      iArray[1][1]="50px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
            
      iArray[2]=new Array();
      iArray[2][0]="销售渠道";         		//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="机构组编码";          	//列名
      iArray[3][1]="50px";      	      	//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
            
      iArray[4]=new Array();
      iArray[4][0]="投保起期";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="投保止期";         		//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="扫描起期";         		//列名
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="扫描起始时间";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      iArray[8]=new Array();
      iArray[8][0]="扫描止期";         		//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="扫描截止时间";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="操作人员";         		//列名
      iArray[10][1]="50px";            		//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="上次修改日期";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=20;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="上次修改时间";         		//列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=20;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[13]=new Array();
      iArray[13][0]="修改日期";         		//列名
      iArray[13][1]="50px";            		//列宽
      iArray[13][2]=20;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[14]=new Array();
      iArray[14][0]="修改时间";         		//列名
      iArray[14][1]="50px";            		//列宽
      iArray[14][2]=20;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      ProductSaleTraceGrid = new MulLineEnter( "document" , "ProductSaleTraceGrid" ); 
      //这些属性必须在loadMulLine前
      ProductSaleTraceGrid.mulLineCount = 5;   
      ProductSaleTraceGrid.displayTitle = 1;
      ProductSaleTraceGrid.canSel =0;
      ProductSaleTraceGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      ProductSaleTraceGrid.hiddenSubtraction=1;
      
      ProductSaleTraceGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
    function initProductSaleConfigGrid()
{                              
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";          	//列名
      iArray[1][1]="50px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
            
      iArray[2]=new Array();
      iArray[2][0]="销售渠道";         		//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="机构组编码";          	//列名
      iArray[3][1]="50px";      	      	//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
            
      iArray[4]=new Array();
      iArray[4][0]="投保起期";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="投保止期";         		//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="扫描起期";         		//列名
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="扫描起始时间";         		//列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      iArray[8]=new Array();
      iArray[8][0]="扫描止期";         		//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="扫描截止时间";         		//列名
      iArray[9][1]="50px";            		//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="操作人员";         		//列名
      iArray[10][1]="50px";            		//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="修改日期";         		//列名
      iArray[11][1]="50px";            		//列宽
      iArray[11][2]=20;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="修改时间";         		//列名
      iArray[12][1]="50px";            		//列宽
      iArray[12][2]=20;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      ProductSaleConfigGrid = new MulLineEnter( "document" , "ProductSaleConfigGrid" ); 
      //这些属性必须在loadMulLine前
      ProductSaleConfigGrid.mulLineCount = 5;   
      ProductSaleConfigGrid.displayTitle = 1;
      ProductSaleConfigGrid.canChk  =0;
      ProductSaleConfigGrid.canSel =1;
      ProductSaleConfigGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      ProductSaleConfigGrid.hiddenSubtraction=1;
      ProductSaleConfigGrid. selBoxEventFuncName ="getConfigDetail" ; 
      
      ProductSaleConfigGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
</script>
