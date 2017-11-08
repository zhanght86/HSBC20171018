<%
//程序名称：LDUWUserInput.jsp
//程序功能：
//创建日期：2005-01-24 18:15:01
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
    

  }
  catch(ex)
  {
    alert("在LDUWUserInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LDUWUserInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        
function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    getMaxGrade();
    //initUWResultGrid();
    //initUWMaxAmountGrid();  现在的权限表中已经可以判断这些数据啦  
  }
  catch(re)
  {
    alert("LDUWUserInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initUWResultGrid()
{                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         		//列名
    iArray[0][1]="30px";         		//列名
    iArray[0][3]=0;         		//列名
    //iArray[0][4]="station";         		//列名
    
    iArray[1]=new Array();
    iArray[1][0]="核保结论代码";  
    iArray[1][1]="80px";  
    iArray[1][3]=2;
    iArray[1][4]="hebaoquanxian";
    //iArray[10][5]="1|2|3";
    iArray[1][5]="1|2";
    iArray[1][6]="0|1";
    iArray[1][15]="codetype";
    //alert(document.all('tt').value);
    iArray[1][16]="#"+tt+"#";
    //iArray[1][4]='hebaoquanxian';
    //iArray[1][9]="核保结论代码|code:contuwstate&NOTNULL";
    //iArray[1][5]="1|2";  //引用代码对应第几列，'|'为分割符
    //iArray[1][6]="0|1"; //上面的列中放置引用代码中第几位值
    //edit by yaory
    //iArray[1][10] = "uwstate';
    //iArray[1][11] = "0|^1|拒保^2|延期^4|标准体^9|次标准体";
    //iArray[1][12]="1|2";  //引用代码对应第几列，'|'为分割符
    //iArray[1][13]="0|1"; //上面的列中放置引用代码中第几位值
    iArray[1][18] = 150;
   

    iArray[2]=new Array();
    iArray[2][0]="核保结论";  
    iArray[2][1]="100px";  
    iArray[2][3]=0;       
    iArray[2][9]="核保结论|NOTNULL";     

    
    UWResultGrid = new MulLineEnter( "document" , "UWResultGrid" ); 
    //这些属性必须在loadMulLine前

    UWResultGrid.mulLineCount = 5;   
    UWResultGrid.displayTitle = 1;
    //LDUWUserGrid.selBoxEventFuncName = "showOne";

    UWResultGrid.loadMulLine(iArray);  
    //这些操作必须在loadMulLine后面
    //LDUWUserGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

function initUWMaxAmountGrid()
{                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         		
    iArray[0][1]="30px";         	
    iArray[0][3]=0;         		

    iArray[1]=new Array();
	iArray[1][0]="险种代码";  
	iArray[1][1]="100px";  
	iArray[1][3]=1; 
	iArray[1][9]="险种代码|NOTNULL"; 
  
    iArray[2]=new Array();
  	iArray[2][0]="险种名称";  
  	iArray[2][1]="100px";  
  	iArray[2][3]=1; 
  	iArray[2][9]="险种名称|NOTNULL";
  		
  	iArray[3]=new Array();
  	iArray[3][0]="保额上限（个险）-保费上限（团险）";  
  	iArray[3][1]="150px";  
  	iArray[3][3]=1;   
  	iArray[3][9]="保额上限|NOTNULL"; 
  	

		

	    
    UWMaxAmountGrid = new MulLineEnter( "document" , "UWMaxAmountGrid" ); 
    //这些属性必须在loadMulLine前
    UWMaxAmountGrid. hiddenPlus=1;
    UWMaxAmountGrid. hiddenSubtraction=1;
    UWMaxAmountGrid.mulLineCount = 5;   
    UWMaxAmountGrid.displayTitle = 1;
    //LDUWUserGrid.selBoxEventFuncName = "showOne";

	UWMaxAmountGrid.loadMulLine(iArray);  
    //这些操作必须在loadMulLine后面
    //LDUWUserGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}


</script>
