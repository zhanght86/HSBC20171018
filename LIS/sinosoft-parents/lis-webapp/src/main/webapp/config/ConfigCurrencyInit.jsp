<%
//程序名称：LDCodeInput.jsp
//程序功能：
//创建日期：2005-01-26 13:18:17
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>                            
<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('CurCountry').value = "";
  }
  catch(ex)
  {
    alert("ConfigNativePlaceInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
  }
  catch(ex)
  {
    alert("ConfigNativePlaceInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        
function initForm()
{
  try
  {
   // initInpBox();
   // initSelBox();   
    initCurrencyGrid();
    queryCurrencyGrid();
  }
  catch(re)
  {
    alert("ConfigNativePlaceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCurrencyGrid() {  
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  
      iArray[1]=new Array();
      iArray[1][0]="币种名称";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][9]="币种名称|notnull";

      iArray[2]=new Array();
      iArray[2][0]="币种缩写";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][9]="币种缩写|notnull";
      
      iArray[3]=new Array();
      iArray[3][0]="币种编码";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="币种编码|notnull";

			iArray[4]=new Array();
      iArray[4][0]="有效标记";         		//列名
      iArray[4][1]="20px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][4]="valiflag";
      iArray[4][9]="有效标记|notnull&code:valiflag";
			iArray[4][19]="1";
			

      CurrencyGrid = new MulLineEnter( "document" , "CurrencyGrid" ); 
      //这些属性必须在loadMulLine前
      CurrencyGrid.mulLineCount = 5;   
      CurrencyGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      CurrencyGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

</script>
