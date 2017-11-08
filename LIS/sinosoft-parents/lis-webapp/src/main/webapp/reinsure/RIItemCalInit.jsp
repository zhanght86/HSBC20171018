<%@include file="/i18n/language.jsp"%>
<%
//程序名称：CostDataAcquisitionDefInputInit.jsp
//程序功能：凭证费用数据采集定义
//创建日期：2008-08-18
//创建人  ：范昕  
%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>


<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");
	
	if(globalInput == null) 
	{
		out.println(""+"网页超时，请重新登录"+"");
		return;
	}
	String strOperator = globalInput.Operator;
%>  
                       
<script type="text/javascript">
function initInpBox()
{ 
  try
  {     
    fm.reset();
    
  }
  catch(ex)
  {
    myAlert("在RIItemCalInit.jsp-->"+"初始化界面错误!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("在RIItemCalInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initResultGrid();
    initKResultGrid();
  }
  catch(re)
  {
    myAlert("在RIItemCalInit.jsp-->"+"初始化界面错误!");
  }
}

function initResultGrid()
 {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="算法定义编码";         		//列名
      iArray[1][1]="70px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="算法定义名称";      	   		//列名
      iArray[2][1]="100px";            			//列宽
      iArray[2][2]=10;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[3]=new Array();
      iArray[3][0]="算法类型";      	   		//列名
      iArray[3][1]="130px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="累计风险编码";      	   		//列名
      iArray[4][1]="60px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
          
      iArray[5]=new Array();
      iArray[5][0]="计算期间";      	   		//列名
      iArray[5][1]="60px";            			//列宽
      iArray[5][2]=10;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="计算方式";      	   		//列名
      iArray[6][1]="60px";            			//列宽
      iArray[6][2]=10;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      ResultGrid = new MulLineEnter( "fm" , "ResultGrid" ); 
      //这些属性必须在loadMulLine前
      //ResultGrid.mulLineCount = 1;   
      ResultGrid.displayTitle = 1;
      ResultGrid.locked = 1;
      ResultGrid.canSel = 1;
      ResultGrid.canChk = 0;
      ResultGrid.hiddenSubtraction = 1;
      ResultGrid.hiddenPlus = 1;
      ResultGrid.loadMulLine(iArray);  
      ResultGrid.selBoxEventFuncName ="queryDetial"; //响应的函数名，不加扩号   
      

      }
      catch(ex)
      {
        myAlert(ex);
      }
}

function initKResultGrid()
 {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="算法定义编码";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="明细算法编码";      	   		//列名
      iArray[2][1]="60px";            			//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[3]=new Array();
      iArray[3][0]="明细算法名称";      	   		//列名
      iArray[3][1]="130px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[4]=new Array();
      iArray[4][0]="明细算法类型";      	   		//列名
      iArray[4][1]="50px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[5]=new Array();
      iArray[5][0]="明细算法类型描述";      	   		//列名
      iArray[5][1]="70px";            			//列宽
      iArray[5][2]=10;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
            
      iArray[6]=new Array();
      iArray[6][0]="计算项编码";      	   		//列名
      iArray[6][1]="70px";            			//列宽
      iArray[6][2]=10;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="计算项名称";      	   		//列名
      iArray[7][1]="70px";            			//列宽
      iArray[7][2]=10;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="算法执行顺序";      	   		//列名
      iArray[8][1]="50px";            			//列宽
      iArray[8][2]=10;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="计算项类别";      	   		//列名
      iArray[9][1]="10px";            			//列宽
      iArray[9][2]=10;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="计算项计算类型";      	   		//列名
      iArray[10][1]="10px";            			//列宽
      iArray[10][2]=10;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="固定数字值";      	   		//列名
      iArray[11][1]="10px";            			//列宽
      iArray[11][2]=10;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[12]=new Array();
      iArray[12][0]="计算处理类";      	   		//列名
      iArray[12][1]="10px";            			//列宽
      iArray[12][2]=10;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[13]=new Array();
      iArray[13][0]="计算SQL算法";      	   		//列名
      iArray[13][1]="10px";            			//列宽
      iArray[13][2]=10;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[14]=new Array();
      iArray[14][0]="计算结果存储字段";      	   		//列名
      iArray[14][1]="10px";            			//列宽
      iArray[14][2]=10;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[15]=new Array();
      iArray[15][0]="描述";      	   		//列名
      iArray[15][1]="10px";            			//列宽
      iArray[15][2]=10;            			//列最大值
      iArray[15][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[16]=new Array();
      iArray[16][0]="备用字符串属性1";      	   		//列名
      iArray[16][1]="10px";            			//列宽
      iArray[16][2]=10;            			//列最大值
      iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[17]=new Array();
      iArray[17][0]="备用字符串属性2";      	   		//列名
      iArray[17][1]="10px";            			//列宽
      iArray[17][2]=10;            			//列最大值
      iArray[17][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[18]=new Array();
      iArray[18][0]="备用字符串属性3";      	   		//列名
      iArray[18][1]="10px";            			//列宽
      iArray[18][2]=10;            			//列最大值
      iArray[18][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      KResultGrid = new MulLineEnter( "fm" , "KResultGrid" ); 
      //这些属性必须在loadMulLine前
      //ResultGrid.mulLineCount = 1;   
      KResultGrid.displayTitle = 1;
      KResultGrid.locked = 1;
      KResultGrid.canSel = 1;
      KResultGrid.canChk = 0;
      KResultGrid.hiddenSubtraction = 1;
      KResultGrid.hiddenPlus = 1;
      KResultGrid.loadMulLine(iArray);  
      KResultGrid.selBoxEventFuncName ="DKmis"; //响应的函数名，不加扩号   
      

      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>