<%
//程序名称：GEdorAutoUWInit.jsp
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
//单击时查询
function reportDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	divEdorAutoUW.style.left=ex;
  	divEdorAutoUW.style.top =ey;
	divEdorAutoUW.style.display ='';
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	document.all('EdorNo').value='';
	document.all('GrpContNo').value = '';
  }
  catch(ex)
  {
    alert("在PGrpEdorUWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在PGrpEdorUWInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();  
      
    initPGrpEdorMainGrid();
  }
  catch(re)
  {
    alert("PGrpEdorUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 报案信息列表的初始化
function initPGrpEdorMainGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         		//列宽
      iArray[0][2]=10;          		//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="批单号";    		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            		//列最大值
      iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="集体保单号";         	//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            		//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="申请日期";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            		//列最大值
      iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[4]=new Array();
      iArray[4][0]="生效日期";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            		//列最大值
      iArray[4][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交退费金额";         	//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            		//列最大值
      iArray[5][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      PGrpEdorMainGrid = new MulLineEnter( "fm" , "PGrpEdorMainGrid" ); 
      //这些属性必须在loadMulLine前
      PGrpEdorMainGrid.mulLineCount = 10;   
      PGrpEdorMainGrid.displayTitle = 1;
      PGrpEdorMainGrid.canSel =0;
      PGrpEdorMainGrid.canChk = 1;
      PGrpEdorMainGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PGrpEdorMainGrid.hiddenSubtraction=1;
      PGrpEdorMainGrid.detailInfo="单击显示详细信息";
      PGrpEdorMainGrid.detailClick=reportDetailClick;
      PGrpEdorMainGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
