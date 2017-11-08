<%@include file="/i18n/language.jsp"%>
<%
//程式名称：CostDataAcquisitionDefInputInit.jsp
//程式功能：凭证费用资料获取定义
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
    myAlert("在RIItemCalInit.jsp-->"+"InitInpBox函数中发生异常:初始化介面错误!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("在RIItemCalInit.jsp-->"+"InitSelBox函数中发生异常:初始化介面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initResultGrid();
  }
  catch(re)
  {
    myAlert("在RIItemCalInit.jsp-->"+"InitForm函数中发生异常:初始化介面错误!");
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
      iArray[1][0]="演算法定义编码";         		//列名
      iArray[1][1]="70px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="演算法定义名称";      	   		//列名
      iArray[2][1]="100px";            			//列宽
      iArray[2][2]=10;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[3]=new Array();
      iArray[3][0]="演算法类型";      	   		//列名
      iArray[3][1]="60px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="方案类型";      	   		//列名
      iArray[4][1]="130px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[5]=new Array();
      iArray[5][0]="累计风险编码";     
      iArray[5][1]="130px";         
      iArray[5][2]=10;            
      iArray[5][3]=0;              

      iArray[6]=new Array();
      iArray[6][0]="演算法类型编码";     
      iArray[6][1]="50px";         
      iArray[6][2]=10;            
      iArray[6][3]=3;              

      iArray[7]=new Array();
      iArray[7][0]="方案类型编码";     
      iArray[7][1]="50px";         
      iArray[7][2]=10;            
      iArray[7][3]=3;              
      
      iArray[8]=new Array();
      iArray[8][0]="累计风险编码";     
      iArray[8][1]="0px";         
      iArray[8][2]=10;            
      iArray[8][3]=3;        
           
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
      ResultGrid.selBoxEventFuncName ="getDetail"; //回应的函数名，不加扩号   
      }
      catch(ex)
      {
        myAlert(ex);
      }
}

</script>
