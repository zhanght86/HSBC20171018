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
       document.all('ManageCom').value = comcode.substring(0,6);
   }
   catch(ex)
   {
       alert("在PrtGrpEndorseInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
   }      
}

function initForm()
{
   try
   {
       initInpBox();  
	   initPEdorMainGrid();
	   initManageCom(); //初始化管理机构，最长截取6位   
   }
   catch(re)
   {
       alert("在PrtGrpEndorseInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
   }
}

// 用于批单级init函数
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
        iArray[1][1]="120px";            		//列宽
        iArray[1][2]=100;            			//列最大值
        iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[2]=new Array();
        iArray[2][0]="团体合同号";         			//列名
        iArray[2][1]="120px";            		//列宽
        iArray[2][2]=100;            			//列最大值
        iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
     		iArray[3]=new Array();
     		iArray[3][0]="申请人名称";         			//列名
     		iArray[3][1]="100px";            		//列宽
     		iArray[3][2]=60;            			//列最大值
     		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     		
     		iArray[4]=new Array();
     		iArray[4][0]="柜面受理日期";         		//列名
     		iArray[4][1]="100px";            		//列宽
     		iArray[4][2]=100;            			//列最大值
     		iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
     		
     		iArray[5]=new Array();
     		iArray[5][0]="申请时间";         		//列名
     		iArray[5][1]="100px";            		//列宽
     		iArray[5][2]=100;            			//列最大值
     		iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     		
     		iArray[6]=new Array();
     		iArray[6][0]="申请号";         		//列名
     		iArray[6][1]="0px";            		//列宽
     		iArray[6][2]=100;            			//列最大值
     		iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      	
      	iArray[7]=new Array();
      	iArray[7][0]="内部批单号";         		//列名
     	  iArray[7][1]="0px";            		//列宽
     	  iArray[7][2]=100;            			//列最大值
        iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        PEdorMainGrid = new MulLineEnter( "fm" , "PEdorMainGrid" ); 
        //这些属性必须在loadMulLine前
        PEdorMainGrid.mulLineCount = 10;   
        PEdorMainGrid.displayTitle = 1;
        PEdorMainGrid.canSel=1;
        PEdorMainGrid.hiddenPlus = 1;
        PEdorMainGrid.hiddenSubtraction = 1; 
        PEdorMainGrid.loadMulLine(iArray);  
        PEdorMainGrid.detailInfo="单击显示详细信息";
    }
    catch(ex)
    {
       alert(ex);
    }
}

</script>