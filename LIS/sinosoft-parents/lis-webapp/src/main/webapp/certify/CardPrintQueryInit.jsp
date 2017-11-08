<%
//程序名称：CardPrintQueryInit.jsp
//程序功能：
//创建日期：2002-10-28 10:20:50
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

function initForm()
{
  try
  {
	  initCertifyPrintGrid();
  }
  catch(re)
  {
    alert("OLZCardPrintQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化CertifyPrintGrid
 ************************************************************
 */
function initCertifyPrintGrid()
{                               
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";			//列名
    iArray[0][1]="30px";     //列名
    iArray[0][2]=60;	        //列名
    iArray[0][3]=0; 	        //列名

    iArray[1]=new Array();
    iArray[1][0]="印刷批次号";		//列名
    iArray[1][1]="180px";         //宽度
    iArray[1][2]=100;         		//最大长度
    iArray[1][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[2]=new Array();
    iArray[2][0]="单证编码";      //列名
    iArray[2][1]="80px";         //宽度
    iArray[2][2]=100;         		//最大长度
    iArray[2][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[3]=new Array();
    iArray[3][0]="管理机构";	    //列名
    iArray[3][1]="80px";         //宽度
    iArray[3][2]=100;         		//最大长度
    iArray[3][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[4]=new Array();
    iArray[4][0]="定单操作员";    //列名
    iArray[4][1]="80px";         //宽度
    iArray[4][2]=100;         		//最大长度
    iArray[4][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[5]=new Array();
    iArray[5][0]="定单日期";      //列名
    iArray[5][1]="80px";         //宽度
    iArray[5][2]=100;         		//最大长度
    iArray[5][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[6]=new Array();
    iArray[6][0]="起始号";        //列名
    iArray[6][1]="140px";         //宽度
    iArray[6][2]=100;         		//最大长度
    iArray[6][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[7]=new Array();
    iArray[7][0]="终止号";        //列名
    iArray[7][1]="140px";         //宽度
    iArray[7][2]=100;         		//最大长度
    iArray[7][3]=0;         			//是否允许录入，0--不能，1--允许

    CertifyPrintGrid = new MulLineEnter( "fm" , "CertifyPrintGrid" ); 

    //这些属性必须在loadMulLine前
    CertifyPrintGrid.mulLineCount = 0;   
    CertifyPrintGrid.displayTitle = 1;
    CertifyPrintGrid.canSel=1;
    CertifyPrintGrid.hiddenPlus = 1;
    CertifyPrintGrid.hiddenSubtraction = 1;
    CertifyPrintGrid.loadMulLine(iArray);  

  }
  catch(ex)
  {
    alert("初始化CertifyPrintGrid时出错："+ ex);
  }
}
</script>
