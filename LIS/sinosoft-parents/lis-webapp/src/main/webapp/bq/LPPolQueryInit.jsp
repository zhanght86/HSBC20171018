<%
//程序名称：LPPolQueryInit.jsp
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
	  divLPPol2.style.left=ex;
	  divLPPol2.style.top =ey;
	  divLPPol2.style.display ='';
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {               
		document.all('PolNo').value='';
		document.all('CustomerNo').value='';
		try{
		document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
		document.all('EdorType').value = top.opener.document.all('EdorType').value;
		document.all('GrpPolNo').value = top.opener.document.all('GrpPolNo').value;

		}catch (e)
		{}

  }
  catch(ex)
  {
    alert("在LPPolQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LPPolQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
//       alert('aaa');                    

    initInpBox();
    initSelBox();  
//      alert('bbb');                    

    initLPPolGrid();
  }
  catch(ex)
  {
    alert("LPPolQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 报案信息列表的初始化
function initLPPolGrid()
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
      iArray[2][0]="投保单号码";         		//列名                                                     
      iArray[2][1]="150px";            			//列宽                                                     
      iArray[2][2]=100;            			//列最大值                                                 
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
      iArray[3]=new Array();                                                                                       
      iArray[3][0]="被保人客户号";         		//列名                                                     
      iArray[3][1]="200px";            			//列宽                                                     
      iArray[3][2]=100;            			//列最大值                                                 
      iArray[3][3]=0;             			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
      iArray[4]=new Array();                                                                                       
      iArray[4][0]="被保人姓名";         		//列名                                                     
      iArray[4][1]="150px";            			//列宽                                                     
      iArray[4][2]=100;            			//列最大值                                                 
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
                                                                                                                   
      iArray[5]=new Array();                                                                                       
      iArray[5][0]="险种编码";         		//列名                                                     
      iArray[5][1]="100px";            			//列宽                                                     
      iArray[5][2]=100;            			//列最大值                                                 
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
                                                                                                                   
      iArray[6]=new Array();                                                                                       
      iArray[6][0]="起保日期";         			//列名                                                     
      iArray[6][1]="100px";            			//列宽                                                     
      iArray[6][2]=100;            			//列最大值                                                 
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
      
      LPPolGrid = new MulLineEnter( "fm" , "LPPolGrid" ); 
      //这些属性必须在loadMulLine前
     
      LPPolGrid.mulLineCount = 10;   
      LPPolGrid.displayTitle = 1;
      LPPolGrid.canSel=1;
      LPPolGrid.loadMulLine(iArray);  
      LPPolGrid.detailInfo="单击显示详细信息";
      LPPolGrid.detailClick=reportDetailClick;
      //这些操作必须在loadMulLine后面
      //SubReportGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>