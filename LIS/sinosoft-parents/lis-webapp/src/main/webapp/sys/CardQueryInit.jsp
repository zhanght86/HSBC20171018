<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CardQueryInit.jsp
//程序功能：卡单号与合同号相互查询初始化
//创建日期：2006-1-10 11:10:36
//创建人  ：renhj
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                        
<script language="JavaScript">

function initForm()
{
  try
  {			
    initmyGrpGrid();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initmyGrpGrid()
  {     
   			                      
    var iArray = new Array();
      
     
     try
      {
      
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      //iArray[1]=new Array();
      //iArray[1][0]="管理机构";         			//列名（此列为顺序号，列名无意义，而且不显示）
      //iArray[1][1]="50px";            		//列宽
      //iArray[1][2]=10;            			//列最大值
      //iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="卡单号";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="合同号";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      myGrpGrid = new MulLineEnter( "fm" , "myGrpGrid" ); 
      //这些属性必须在loadMulLine前
      myGrpGrid.mulLineCount =1;   
      myGrpGrid.displayTitle = 1;
      myGrpGrid.locked = 1;
      myGrpGrid.canSel = 1;
      myGrpGrid.canChk = 0;
      myGrpGrid.hiddenPlus = 1;
      myGrpGrid.hiddenSubtraction = 1;        
      myGrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
