<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UnderwriteInit.jsp
//程序功能：个人人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<%
//添加页面控件的初始化。
String tGrpContNo="";
tGrpContNo=request.getParameter("GrpContNo");

GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

if(tGI == null) {
	out.println("session has expired");
	return;
}

String strOperator = tGI.Operator;
%>                            

<script language="JavaScript">





function initForm()
{
  try
  {
    
       
    initPolGrid();   
    
		easyQueryClick(cGrpContNo);
  }
  catch(re)
  {
    alert("在ManuUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="集体险种号";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="团体投保单号";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  
      iArray[4]=new Array();
      iArray[4][0]="分保比例(小数)";         		//列名
      iArray[4][1]="70px";            		//列宽
      iArray[4][2]=200;                 	//列最大值
      iArray[4][3]=1;                     	//是否允许输入,1表示允许，0表示不允许
      
      

      iArray[5]=new Array();
      iArray[5][0]="摊回比例(小数)";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[6]=new Array();
      iArray[6][0]="再保机构";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=2;  
      iArray[6][10]="condtion";
      iArray[6][11]="0|^1|中国人寿再保险股份有限公司^2|其他";       

      
      iArray[7]=new Array();
      iArray[7][0]="临分手续费比例(小数)";         		//列名
      iArray[7][1]="90px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
        
  
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 0;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);     
  
      }
      catch(ex)
      {
        alert(ex);
      }
}



</script>
