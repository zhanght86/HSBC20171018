<%
//程序名称：PrtAppEndorseInit.jsp
//程序功能：
//创建日期：2005-03-03 
//创建人  ：FanX
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
var mLoadFlag = "<%=request.getParameter("LoadFlag")%>";

//单击时查询
function reportDetailClick(cObj)
{
  	var ex,ey;
  	
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	divLPEdor2.style.left=ex;
  	divLPEdor2.style.top =ey;
    divLPEdor2.style.display ='';
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	 //document.all('ContType').value = '1'; 
  }
  catch(ex)
  {
    alert("在PEdorQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在PEdorQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function fillGrid()
{
   if (document.all('EdorAcceptNo').value != "")
   		easyQueryClick();

}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();   
    initPEdorAppGrid();
    fillGrid();
    if (mLoadFlag == "TASK")  
    {
    	document.all("EdorAcceptNo").value = "<%=request.getParameter("DetailWorkNo")%>";
    	easyQueryClick();
    }
  }
  catch(re)
  {
    alert("PEdorQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initPEdorAppGrid()
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
      iArray[1][0]="保全受理号";    	//列名
      iArray[1][1]="200px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="申请号";         			//列名
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="申请人名称";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="批改状态";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[5]=new Array();
      iArray[5][0]="柜面受理日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      


      PEdorAppGrid = new MulLineEnter( "fm" , "PEdorAppGrid" ); 
      //这些属性必须在loadMulLine前
      PEdorAppGrid.mulLineCount = 10;   
      PEdorAppGrid.displayTitle = 1;
      PEdorAppGrid.canSel=1;
      PEdorAppGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PEdorAppGrid.hiddenSubtraction=1;
      PEdorAppGrid.loadMulLine(iArray);  

      PEdorAppGrid.detailInfo="单击显示详细信息";
      //PEdorAppGrid.detailClick=reportDetailClick;
      //这些操作必须在loadMulLine后面
      //PEdorAppGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>