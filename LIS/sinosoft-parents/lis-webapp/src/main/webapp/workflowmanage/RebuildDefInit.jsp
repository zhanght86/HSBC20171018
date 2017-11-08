<%
/*******************************************************************************
 * <p>Title       : 任务分配</p>
 * <p>Description : 用来将系统中已经申请的任务分配给其它员工</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : 中科软科技股份有限公司</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        :
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
%>
<script language="JavaScript">
function initInpBox()
{ 
  try
  {               
      document.all('BusiType').value= '';  
      document.all('ProcessID').value= '';             
      document.all('BusiTypeName').value= '';  
      document.all('ProcessName').value= '';              
  }
  catch(ex)
  {
    alert("在AccountRecReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
function initForm()
{
  try
  {
    initInpBox();
    initWorkFlowGrid();
  }
  catch(re)
  {
    alert("初始化界面错误!");
  }
}
function initWorkFlowGrid()
{

    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="过程ID";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="100px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="过程名称";    	                //列名
      iArray[2][1]="100px";            		        //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="业务类型";    	                //列名
      iArray[3][1]="45px";            		        //列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[4]=new Array();
      iArray[4][0]="业务类型";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[4][1]="60px";         			//列宽
      iArray[4][2]=10;          			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="状态";    	                //列名
      iArray[5][1]="80px";            		        //列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[6]=new Array();
      iArray[6][0]="系统标志";    	                //列名
      iArray[6][1]="100px";            		        //列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
     //jiyongtian  版本号 2012-7-12
      iArray[7]=new Array();
      iArray[7][0]="版本号";    	                //列名
      iArray[7][1]="100px";            		        //列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      WorkFlowGrid = new MulLineEnter( "fm" , "WorkFlowGrid" );

      //这些属性必须在loadMulLine前
      WorkFlowGrid.mulLineCount = 5;
      WorkFlowGrid.displayTitle = 1;
      WorkFlowGrid.canChk =0;
      WorkFlowGrid.canSel =1;
      WorkFlowGrid.locked =1;            //是否锁定：1为锁定 0为不锁定
      WorkFlowGrid.hiddenPlus=1;        //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏
      WorkFlowGrid.hiddenSubtraction=1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏
      WorkFlowGrid.recordNo=0;         //设置序号起始基数为10，如果要分页显示数据有用
      WorkFlowGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }
  
</script>
