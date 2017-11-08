<%
//程序名称：ReportQueryInit.jsp
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
  	divLPEdor2.style.left=ex;
  	divLPEdor2.style.top =ey;
    divLPEdor2.style.display ='';
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	 document.all('ContType').value = top.opener.document.all('ContType').value;
	 
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
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在PEdorQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
		initPEdorMainGrid();
		var EdorState;
		tEdorState=top.opener.document.all('EdorState').value;
  	if (tEdorState=='2')
  		divdetail.style.display = '';
  }
  catch(re)
  {
    alert("PEdorQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 报案信息列表的初始化
function initPEdorMainGrid()
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
      iArray[1][0]="批单号";    	//列名
      iArray[1][1]="200px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="保单号";         			//列名
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保全申请号";    	//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="生效日期";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[5]=new Array();
      iArray[5][0]="柜面受理日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
   


      PEdorMainGrid = new MulLineEnter( "fm" , "PEdorMainGrid" ); 
      //这些属性必须在loadMulLine前
      PEdorMainGrid.mulLineCount = 10;   
      PEdorMainGrid.displayTitle = 1;
      PEdorMainGrid.canSel=1;
      PEdorMainGrid.hiddenPlus = 1;
      PEdorMainGrid.hiddenSubtraction = 1;
      PEdorMainGrid.loadMulLine(iArray);  
      PEdorMainGrid.detailInfo="单击显示详细信息";
      PEdorMainGrid.detailClick=reportDetailClick;
      //这些操作必须在loadMulLine后面
      //SubReportGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>