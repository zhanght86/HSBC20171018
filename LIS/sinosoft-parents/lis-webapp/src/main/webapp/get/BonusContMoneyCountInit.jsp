<%
//程序名称：
//程序功能：个人保单分红险系数计算
//创建日期：2008-11-09 17:55:57
//创建人  ：彭送庭
%>
<!--用户校验类-->

<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    //fm.FiscalYear.value = getCurrentDate("-").substring(0,4);
    //add by jiaqiangli 2009-09-04 不要初始化当前年份且是客户端的年份 此菜单兼具有强制分红的功能 
    fm.FiscalYear.value = '';
  }
  catch(ex)
  {
    alert("在BonusContMoneyCountInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                                       
// 被保人信息列表的初始化
function initLOBonusPolGrid()
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
      iArray[1][0]="保单号";    	//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=80;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="合同号";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=0;     
               			//是否允许输入,1表示允许，0表示不允许
      iArray[3]=new Array();
      iArray[3][0]="会计年度";         			//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="红利金额";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="分配标志";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="红利系数";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[7]=new Array();
      iArray[7][0]="应分日期";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="领取方式";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;       
       
      

      LOBonusPolGrid = new MulLineEnter( "document" , "LOBonusPolGrid" );
      LOBonusPolGrid.mulLineCount = 5;
      LOBonusPolGrid.displayTitle = 1;
      LOBonusPolGrid.canSel = 1;
      LOBonusPolGrid.canChk=0;
      LOBonusPolGrid.hiddenPlus=1;
      LOBonusPolGrid.hiddenSubtraction=1;

      LOBonusPolGrid.loadMulLine(iArray);

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
    initLOBonusPolGrid();
    //queryClick();
  }
  catch(re)
  {
    alert("BonusContMoneyCountInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


</script>
