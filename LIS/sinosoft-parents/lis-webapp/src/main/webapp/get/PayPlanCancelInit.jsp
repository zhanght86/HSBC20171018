<%
//程序名称：PayPlanCancelInit.jsp
//程序功能：
//创建日期：2005-7-6 11:24
//创建人  ：JL
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("在PayPlanCancelInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在PayPlanCancelInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initDiv()
{      
  divLCGet.style.display ='none';
  divCancelCommit.style.display ='none';
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initDiv();    
    initLJSGetGrid();
  }
  catch(re)
  {
    alert("PayPlanCancelInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
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
    iArray[1][0]="通知书号码";         		//列名
    iArray[1][1]="145px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="个人保单号码";         		//列名
    iArray[2][1]="145px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="团体保单号码";         		//列名
    iArray[3][1]="145px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="被保人姓名";         		//列名
    iArray[4][1]="70px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0; 
    
    iArray[5]=new Array();
    iArray[5][0]="领取金额";         		//列名
    iArray[5][1]="65px";            		//列宽
    iArray[5][2]=65;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="应领取日期";         		//列名
    iArray[6][1]="75px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="催付日期";         		//列名
    iArray[7][1]="75px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="操作员";         		//列名
    iArray[8][1]="60px";            		//列宽
    iArray[8][2]=60;            			//列最大值
    iArray[8][3]=0;                                   //是否允许输入,1表示允许，0表示不允许
    
    LJSGetGrid = new MulLineEnter( "document" , "LJSGetGrid" ); 
    
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

//单击时处理
function reportDetailClick(parm1,parm2)
{
  var ex,ey;
  ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  divCancelCommit.style.left=ex;
  divCancelCommit.style.top =ey;
  divCancelCommit.style.display = '';   //显示确认按扭
  document.all('CancelReason').value = '';    //清空
}

</script>
