<%
//程序名称：ReportQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
//单击时查询
function reportDetailClick(cObj)
{
	  var ex,ey;
	  ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
	  ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
	  divLCPol2.style.left=ex;
	  divLCPol2.style.top =ey;
	  divLCPol2.style.display ='';
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	document.all('PolNo').value='';
  }
  catch(ex)
  {
    alert("在LCPolQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  //123
  }
  catch(ex)
  {
    alert("在LCPolQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
     
    initInpBox();
    initSelBox();  
    initLCPolGrid();
  }
  catch(ex)
  {
    alert("PEdorQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 报案信息列表的初始化
function initLCPolGrid()
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
      iArray[1][0]="保单号";    			//列名                                                     
      iArray[1][1]="150px";            			//列宽                                                     
      iArray[1][2]=100;            			//列最大值                                                 
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
      iArray[2]=new Array();                                                                                       
      iArray[2][0]="总单合同号";         		//列名                                                     
      iArray[2][1]="150px";            			//列宽                                                     
      iArray[2][2]=100;            			//列最大值                                                 
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
      iArray[3]=new Array();                                                                                       
      iArray[3][0]="集体保单号";         		//列名                                                     
      iArray[3][1]="150px";            			//列宽                                                     
      iArray[3][2]=100;            			//列最大值                                                 
      iArray[3][3]=0;             			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
      iArray[4]=new Array();                                                                                       
      iArray[4][0]="投保单号码";         		//列名                                                     
      iArray[4][1]="150px";            			//列宽                                                     
      iArray[4][2]=100;            			//列最大值                                                 
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
                                                                                                                   
      iArray[5]=new Array();                                                                                       
      iArray[5][0]="投保人姓名";         		//列名                                                     
      iArray[5][1]="80px";            			//列宽                                                     
      iArray[5][2]=100;            			//列最大值                                                 
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
                                                                                                                   
      iArray[6]=new Array();                                                                                       
      iArray[6][0]="险种编码";         			//列名                                                     
      iArray[6][1]="100px";            			//列宽                                                     
      iArray[6][2]=100;            			//列最大值                                                 
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
     
      iArray[7]=new Array();                                                                                       
      iArray[7][0]="起保日期";         			//列名                                                     
      iArray[7][1]="100px";            			//列宽                                                     
      iArray[7][2]=100;            			//列最大值                                                 
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用 
      
       iArray[8]=new Array();                                                                                       
      iArray[8][0]="送达日期";         			//列名                                                     
      iArray[8][1]="100px";            			//列宽                                                     
      iArray[8][2]=100;            			//列最大值                                                 
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用 
      LCPolGrid = new MulLineEnter( "fm" , "LCPolGrid" ); 
      //这些属性必须在loadMulLine前
     
      LCPolGrid.mulLineCount = 10;   
      LCPolGrid.displayTitle = 1;
      LCPolGrid.canSel=1;
      LCPolGrid.loadMulLine(iArray);  
      LCPolGrid.detailInfo="单击显示详细信息";
      LCPolGrid.detailClick=reportDetailClick;
      //这些操作必须在loadMulLine后面
      //SubReportGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>