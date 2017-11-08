<%
//程序名称：
//程序功能：分红险系数输入界面
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
    fm.DistributeValue.value='';
    fm.DistributeRate.value = '';
    fm.BonusCoefSum.value = '';
    fm.FiscalYear.value = '';
  }
  catch(ex)
  {
    alert("在BonusRateInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                                       
// 被保人信息列表的初始化
function initLOBonusMainGrid(tFlag)
  {

      var iArray = new Array();
      try
      {
      iArray[0]=new Array();                                                   
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且?
      iArray[0][1]="30px";         			//列宽                                 
      iArray[0][2]=10;          			//列最大值                               
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
                                                                               
      iArray[1]=new Array();                                                   
      iArray[1][0]="会计年度";    	//列名                                     
      iArray[1][1]="100px";            		//列宽                               
      iArray[1][2]=80;            			//列最大值                             
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
                                                                               
      iArray[2]=new Array();                                                   
      iArray[2][0]="组别";         			//列名                                 
      iArray[2][1]="80px";            		//列宽                               
      iArray[2][2]=80;            			//列最大值                             
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
                                                                               
      iArray[3]=new Array();                                                   
      iArray[3][0]="可分配盈余";         			//列名                           
      iArray[3][1]="80px";            		//列宽                               
      iArray[3][2]=100;            			//列最大值                             
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
                                                                               
      iArray[4]=new Array();                                                   
      iArray[4][0]="红利分配比例";         		//列名                           
      iArray[4][1]="100px";            		//列宽                               
      iArray[4][2]=100;            			//列最大值                             
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
                                                                               
      iArray[5]=new Array();                                                   
      iArray[5][0]="红利结算时刻的红利系数和";         		//列名                           
      iArray[5][1]="180px";            		//列宽                               
      iArray[5][2]=100;            			//列最大值                             
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
                                                                                                                                                                                                               
                                                                               
      iArray[6]=new Array();                                                   
      iArray[6][0]="状态";         		//列名                                   
      iArray[6][1]="100px";            		//列宽                               
      iArray[6][2]=100;            			//列最大值                             
      iArray[6][3]=0;             			//是否允许输入,1表示允许，0表示不允许  


    
 
      LOBonusMainGrid = new MulLineEnter( "fm" , "LOBonusMainGrid" );
      //这些属性必须在loadMulLine前
      LOBonusMainGrid.mulLineCount = 0;
      LOBonusMainGrid.displayTitle = 1;
      LOBonusMainGrid.canSel = 1;
      LOBonusMainGrid.canChk=0;
      LOBonusMainGrid.hiddenPlus=1;
      LOBonusMainGrid.hiddenSubtraction=1;
      LOBonusMainGrid.selBoxEventFuncName="displayInfo";
      LOBonusMainGrid.loadMulLine(iArray);

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
    initLOBonusMainGrid(1);
    queryClick();
  }
  catch(re)
  {
    alert("BonusRateInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function displayAccClick()
{
	initLOBonusMainGrid(tFlag);
  fm.DistributeValue.value='';
  fm.DistributeRate.value = '';
  fm.BonusCoefSum.value = '';
  queryClick();
}
	
</script>
