<%
//程序名称：RePrintInit.jsp
//程序功能：
//创建日期：2003-03-31
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           

<script language="JavaScript">


var PolGrid;          //定义为全局变量，提供给displayMultiline使用

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {
  	fm.reset();                                   
  }
  catch(ex)
  {
    alert("在RePrintInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
  	manageCom = "<%= strManageCom %>";
    initInpBox();
	  initPolGrid();
  }
  catch(re)
  {
    alert("RePrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initPolGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="通知书号";         		//列名
	  iArray[1][1]="160px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[2]=new Array();
	  iArray[2][0]="通知书类型";       		//列名
	  iArray[2][1]="100px";            	//列宽
	  iArray[2][2]=100;            			//列最大值
	  iArray[2][3]=2; 
    iArray[2][4]="PrintCode";              	        //是否引用代码:null||""为不引用
    iArray[2][5]="2";              	                //引用代码对应第几列，'|'为分割符
    iArray[2][9]="打印类型|code:PrintCode&NOTNULL";
    iArray[2][18]=250;
    iArray[2][19]= 0 ;
	
	  iArray[3]=new Array();
	  iArray[3][0]="投保单号码";       		//列名
	  iArray[3][1]="160px";            	//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[4]=new Array();
	  iArray[4][0]="管理机构";         	//列名
	  iArray[4][1]="80px";            	//列宽
	  iArray[4][2]=100;            			//列最大值
	  iArray[4][3]=2; 
    iArray[4][4]="station";              	        //是否引用代码:null||""为不引用
    iArray[4][5]="4";              	                //引用代码对应第几列，'|'为分割符
    iArray[4][9]="管理机构|code:station&NOTNULL";
    iArray[4][18]=250;
    iArray[4][19]= 0 ;
	
	  iArray[5]=new Array();
	  iArray[5][0]="代理人编码";        //列名
	  iArray[5][1]="100px";            	//列宽
	  iArray[5][2]=200;            			//列最大值
	  iArray[5][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
    iArray[6]=new Array();
	  iArray[6][0]="销售渠道";         	//列名
	  iArray[6][1]="100px";            	//列宽
	  iArray[6][2]=100;            			//列最大值
	  iArray[6][3]=2; 
	  iArray[6][4]="SaleChnl";              	        //是否引用代码:null||""为不引用
    iArray[6][5]="3";              	                //引用代码对应第几列，'|'为分割符
    iArray[6][9]="销售渠道|code:SaleChnl&NOTNULL";
    iArray[6][18]=250;
    iArray[6][19]= 0 ;	
	
	  PolGrid = new MulLineEnter( "fmSave" , "PolGrid" ); 
	  //这些属性必须在loadMulLine前
	  PolGrid.mulLineCount = 10;   
	  PolGrid.displayTitle = 1;
	  PolGrid.canSel = 1;
    PolGrid.locked = 1;
    PolGrid.hiddenPlus = 1;
    PolGrid.hiddenSubtraction = 1;
	  PolGrid.loadMulLine(iArray);  
	
	} catch(ex) {
		alert(ex);
	}
}

</script>