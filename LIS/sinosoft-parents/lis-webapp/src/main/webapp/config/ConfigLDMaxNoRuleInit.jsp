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
    initLDMaxNoElement();
  }
  catch(re)
  {
    alert("ConfigNativePlaceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initMaxNoGrid() {  
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="10px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  
      iArray[1]=new Array();
      iArray[1][0]="属性编码";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="属性名称";         		//列名
      iArray[2][1]="40px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="属性值";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

			iArray[4]=new Array();
      iArray[4][0]="修改标记";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="显示缩略标记";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="号段元素ID";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      MaxNoGrid = new MulLineEnter( "fm" , "MaxNoGrid" ); 
      //这些属性必须在loadMulLine前
      MaxNoGrid.mulLineCount = 5;   
      MaxNoGrid.displayTitle = 1;
      MaxNoGrid.hiddenPlus = 1;
      MaxNoGrid.hiddenSubtraction = 1;
      //ImpartGrid.tableWidth   ="500px";
      MaxNoGrid.loadMulLine(iArray);  

    }
    catch(ex) {
      alert(ex);
    }
}

</script>
