<%
//程序名称：TempFeeQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 查询条件
    document.all('ContNo').value = '';
    document.all('MainPolNo').value = '';
    document.all('RiskCode').value = '';
  }
  catch(ex)
  {
    alert("在IndiDueFeeListInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在IndiDueFeeListInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initIndiDueFeeListGrid();
  }
  catch(re)
  {
    alert("在IndiDueFeeListInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 
// 保单信息列表的初始化
function initIndiDueFeeListGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            		//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单号";   		        //列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            		//列最大值
      iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种号";   		        //列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            		//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      
      iArray[3]=new Array();
      iArray[3][0]="险种编码";          		//列名
      iArray[3][1]="60px";      	      		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[3][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[3][5]="1|2";              	                //引用代码对应第几列，'|'为分割符
      iArray[3][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[3][18]=300;
      
      
      iArray[4]=new Array();
      iArray[4][0]="缴费间隔";		        //列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=60;            		//列最大值
      iArray[4][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="续期/续保";		        //列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=60;            		//列最大值
      iArray[5][3]=0;  
      
      iArray[6]=new Array();
      iArray[6][0]="催收产生状态";         		//列名
      iArray[6][1]="150px";            		//列宽
      iArray[6][2]=200;            	        //列最大值
      iArray[6][3]=0;                   	//是否允许输入,1表示允许，0表示不允许


      iArray[7]=new Array();
      iArray[7][0]="交至日期";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            	        //列最大值
      iArray[7][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="终交日期";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=200;            	        //列最大值
      iArray[8][3]=0;                   	//是否允许输入,1表示允许，0表示不允许


      iArray[9]=new Array();
      iArray[9][0]="是否交至日期60日内做保全";         		//列名
      iArray[9][1]="180px";            		//列宽
      iArray[9][2]=200;            	        //列最大值
      iArray[9][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="是否死亡报案";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=200;            	        //列最大值
      iArray[10][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="代理人编码";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=200;            	        //列最大值
      iArray[11][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      IndiDueQueryGrid = new MulLineEnter( "fm" , "IndiDueQueryGrid" ); 
      //这些属性必须在loadMulLine前
      IndiDueQueryGrid.mulLineCount = 6;   
      IndiDueQueryGrid.displayTitle = 1;
      IndiDueQueryGrid.hiddenPlus = 1;
      IndiDueQueryGrid.hiddenSubtraction = 1;
      IndiDueQueryGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>