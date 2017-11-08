<%
//程序名称：
//程序功能：万能利率输入界面
//创建日期：2007-11-09 17:55:57
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
    fm.RiskCode.value = '';
    fm.BalaDate.value = '';    
  }
  catch(ex)
  {
    alert("在InsuAccBalaInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                                       
// 被保人信息列表的初始化
function initLMInsuAccRateGrid()
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
      iArray[1][0]="险种编码";    	//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=80;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=0;     
               			//是否允许输入,1表示允许，0表示不允许
      iArray[3]=new Array();
      iArray[3][0]="险种编码";         			//列名
      iArray[3][1]="130px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="帐户编码";         			//列名
      iArray[4][1]="70px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="生效日期";         		//列名
      iArray[5][1]="75px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="结算日期";         		//列名
      iArray[6][1]="75px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[7]=new Array();
      iArray[7][0]="金额";         		//列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="金额类型";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;       
      
      iArray[9]=new Array();                                                   
      iArray[9][0]="帐户价值";         		//列名                             
      iArray[9][1]="75px";            		//列宽                                 
      iArray[9][2]=100;            			//列最大值                               //这些属性必须在loadMulLine前
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      

      LMInsuAccRateGrid = new MulLineEnter( "document" , "LMInsuAccRateGrid" );
      LMInsuAccRateGrid.mulLineCount = 5;
      LMInsuAccRateGrid.displayTitle = 1;
      LMInsuAccRateGrid.canSel = 1;
      LMInsuAccRateGrid.canChk=0;
      LMInsuAccRateGrid.hiddenPlus=1;
      LMInsuAccRateGrid.hiddenSubtraction=1;

      LMInsuAccRateGrid.loadMulLine(iArray);

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
    initLMInsuAccRateGrid();
    //queryClick();
  }
  catch(re)
  {
    alert("InsuAccBalaInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


</script>
