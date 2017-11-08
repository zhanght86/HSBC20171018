<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：RecordQueryInit.jsp
//程序功能：操作履历查询
//创建日期：2005-06-23 11:10:36
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
  	//初始化操作履历multiline
  	initRecordGrid();
  	
  	//初始化健康告知信息
  	//initImpartGrid();
  	
  	//查询客户信息
  	//queryPersonInfo();
	  
	  //查询操作履历
	  queryRecord();
	   	
  }
  catch(re) {
    alert("RecordQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


//操作履历列表的初始化
function initRecordGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="40px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="印刷号";         		//列名
          iArray[1][1]="80px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[2]=new Array();
          iArray[2][0]="操作员";         		//列名
          iArray[2][1]="80px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[3]=new Array();
          iArray[3][0]="操作开始日期";         		//列名
          iArray[3][1]="100px";            		//列宽
          iArray[3][2]=200;            			//列最大值
          iArray[3][3]=8;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="操作结束日期";         		//列名
          iArray[4][1]="100px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=8;              			//是否允许输入,1表示允许，0表示不允许

          iArray[5]=new Array();
          iArray[5][0]="操作前状态";         		//列名
          iArray[5][1]="0px";            		//列宽
          iArray[5][2]=200;            			//列最大值
          iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[6]=new Array();
          iArray[6][0]="操作后状态";         		//列名
          iArray[6][1]="160px";            		//列宽
          iArray[6][2]=200;            			//列最大值
          iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[7]=new Array();
          iArray[7][0]="上报类型";         		//列名
          iArray[7][1]="160px";            		//列宽
          iArray[7][2]=100;            			//列最大值
          iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

               
          RecordGrid = new MulLineEnter( "document" , "RecordGrid" ); 

          //这些属性必须在loadMulLine前
          RecordGrid.mulLineCount = 5;   
          RecordGrid.displayTitle = 1;
          RecordGrid.locked = 1;
          RecordGrid.canSel = 0;
          RecordGrid.hiddenPlus = 1;
          RecordGrid.hiddenSubtraction = 1;
          RecordGrid.loadMulLine(iArray);  
          
          //这些操作必须在loadMulLine后面
          //RecordGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}

</script>
                       

