<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QueryNoticeInit.jsp
//程序功能：已发放通知书查询
//创建日期：2006-11-17 17:05
//创建人  ：haopan
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
 
  	//初始化通知书查询multiline
  	initNoticeGrid();  
  	

	  //查询已发放通知书
	  queryNotice();
   
	   
  } 
  catch(re) {
    alert("QueryNoticeInit.jsp-->InitForm函数中发生异常:初始化界面错误!"); 
  }
}

//已发放通知书列表的初始化
function initNoticeGrid(){
    var iArray = new Array();
       
      try 
      {   
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="40px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="打印流水号";         		//列名 
          iArray[1][1]="120px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许  

          
          iArray[2]=new Array();
          iArray[2][0]="通知书类型";         		//列名
          iArray[2][1]="180px";            		//列宽
          iArray[2][2]=200;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
          
     
          iArray[3]=new Array();
          iArray[3][0]="打印状态";         		//列名
          iArray[3][1]="180px";            		//列宽
          iArray[3][2]=200;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
          
          
        
       
          NoticeGrid = new MulLineEnter( "fm" , "NoticeGrid" ); 

          //这些属性必须在loadMulLine前
          
          NoticeGrid.mulLineCount = 10;     
          NoticeGrid.displayTitle = 1;
          NoticeGrid.locked = 1;
          NoticeGrid.canSel = 0;
          NoticeGrid.hiddenPlus = 1;
          NoticeGrid.hiddenSubtraction = 1;
          NoticeGrid.loadMulLine(iArray);  
          
					//这些操作必须在loadMulLine后面
          //NoticeGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}
 
</script>
