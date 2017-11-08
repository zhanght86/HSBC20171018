<%
//程序名称：LJSGetDrawInput.jsp
//程序功能：
//创建日期：2002-07-19 11:48:25
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<!--%@include file="../common/jsp/UsrCheck.jsp"%-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
function initInpBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("在LJSGetDrawInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
//    setOption("t_sex","0=男&1=女&2=不详");
//    setOption("sex","0=男&1=女&2=不详");
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");
  }
  catch(ex)
  {
    alert("在LJSGetDrawInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}
// 被保人信息列表的初始化
function initGetDrawGrid()
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
      iArray[1][0]="领取通知书号";    	//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=80;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="个人保单号";    	//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="姓名";         			//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="责任类型";         			//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="给付责任类型";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="上次领至日期";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="本次领至日期";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="领取金额";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      SubPayGrid = new MulLineEnter( "fm" , "SubPayGrid" );
      //这些属性必须在loadMulLine前
      //SubPayGrid.mulLineCount = 10;
      SubPayGrid.displayTitle = 1;
      SubPayGrid.canSel = 0;
      //SubPayGrid.canChk=1;
      SubPayGrid.hiddenPlus = 1; 
    	SubPayGrid.hiddenSubtraction = 1;
      //SubPayGrid.chkBoxEventFuncName="CalSumMoney";      
      SubPayGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      //SubPayGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLJSGetGrid()
{
  var iArray = new Array();
    
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="25px";            		//列宽
    iArray[0][2]=10;            			//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[1]=new Array();
    iArray[1][0]="团体保单号码";         		//列名
    iArray[1][1]="145px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="个人保单号码";         		//列名
    iArray[2][1]="145px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="被保人姓名";         		//列名
    iArray[3][1]="145px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="累计未领取金额";         		//列名
    iArray[4][1]="70px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0; 
    
    iArray[5]=new Array();
    iArray[5][0]="操作员";         		//列名
    iArray[5][1]="65px";            		//列宽
    iArray[5][2]=65;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    

    LJSGetGrid = new MulLineEnter( "fm" , "LJSGetGrid" ); 
    
    //这些属性必须在loadMulLine前
    //LJSGetGrid.mulLineCount = 10;   
    LJSGetGrid.displayTitle = 1;
    LJSGetGrid.locked = 1;
    LJSGetGrid.canSel = 1;
    LJSGetGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    LJSGetGrid.hiddenSubtraction=1;    //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    LJSGetGrid.selBoxEventFuncName ="reportDetailClick";
    LJSGetGrid.loadMulLine(iArray); 
      
    //这些操作必须在loadMulLine后面
    //LJSGetGrid.setRowColData(1,1,"1");
  }
  catch(ex)
  {
    alert(ex);
  }
}
function initForm()
{
  try
  {
    initInpBox();
    // initSelBox();
    initGetDrawGrid();
  }
  catch(re)
  {
    alert("LJSGetDrawInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>
