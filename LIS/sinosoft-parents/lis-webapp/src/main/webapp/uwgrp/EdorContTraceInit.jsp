<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ContTraceInit.jsp 
//程序功能：合同核保轨迹查询  
//创建日期：2005-06-27 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
     //添加页面控件的初始化。
%>     
<script language="JavaScript">

function initForm()
{
  try  
  {

  	//初始化操合同核保轨迹multiline

  	initContTraceGrid();   

	  //查询核保轨迹
	  queryContTrace();      
  } 
  catch(re) {
    alert("ContTraceInit.jsp-->InitForm函数中发生异常:初始化界面错误!"); 
  } 
}


//险种核保轨迹列表的初始化
function initContTraceGrid(){
    var iArray = new Array();
       
      try 
      {   
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="40px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="险种保单号";         		//列名 
          iArray[1][1]="0px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
          
          iArray[2]=new Array();
          iArray[2][0]="操作员";         		//列名
          iArray[2][1]="220px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
          
          iArray[3]=new Array();
          iArray[3][0]="操作日期";         		//列名
          iArray[3][1]="220px";            		//列宽
          iArray[3][2]=200;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
            
          iArray[4]=new Array();
          iArray[4][0]="核保结论代码";         		//列名
          iArray[4][1]="220px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
          
          
          iArray[5]=new Array();
          iArray[5][0]="核保结论";         		//列名
          iArray[5][1]="220px";            		//列宽
          iArray[5][2]=200;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
          
        
       
          ContTraceGrid = new MulLineEnter( "fm" , "ContTraceGrid" ); 

          //这些属性必须在loadMulLine前
          
          ContTraceGrid.mulLineCount = 10;     
          ContTraceGrid.displayTitle = 1;
          ContTraceGrid.locked = 1;
          ContTraceGrid.canSel = 0;
          ContTraceGrid.hiddenPlus = 1;
          ContTraceGrid.hiddenSubtraction = 1;
          ContTraceGrid.loadMulLine(iArray);  
          
          //这些操作必须在loadMulLine后面
          //ContTraceGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
} 
 
</script>
                       

