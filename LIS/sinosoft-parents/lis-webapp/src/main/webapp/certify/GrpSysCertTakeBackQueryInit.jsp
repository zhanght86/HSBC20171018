<%
//程序名称：SysCertTakeBackQueryInit.jsp
//程序功能：
//创建日期：2002-10-14 10:20:50
//创建人  ：CrtHtml程序创建
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
	  initSysCertifyGrid();
  }
  catch(re)
  {
    alert("SysCertTakeBackQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化SysCertifyGrid
 ************************************************************
 */
function initSysCertifyGrid()
{                               
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";				//列名
    iArray[0][1]="30px";     		//宽度
    iArray[0][2]=30;	        	//最大长度
    iArray[0][3]=0; 	        	//是否允许录入，0--不能，1--允许

    iArray[1]=new Array();
    iArray[1][0]="单证编码";		//列名
    iArray[1][1]="80px";        //宽度
    iArray[1][2]=100;         	//最大长度
    iArray[1][3]=0;         		//是否允许录入，0--不能，1--允许

    iArray[2]=new Array();
    iArray[2][0]="单证号码";      //列名
    iArray[2][1]="180px";         //宽度
    iArray[2][2]=100;         		//最大长度
    iArray[2][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[3]=new Array();
    iArray[3][0]="发放机构";	    //列名
    iArray[3][1]="80px";          //宽度
    iArray[3][2]=100;         		//最大长度
    iArray[3][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[4]=new Array();
    iArray[4][0]="接收机构";    	//列名
    iArray[4][1]="80px";          //宽度
    iArray[4][2]=100;         		//最大长度
    iArray[4][3]=0;         			//是否允许录入，0--不能，1--允许

    iArray[5]=new Array();
    iArray[5][0]="操作员";      	//列名
    iArray[5][1]="80px";          //宽度
    iArray[5][2]=100;         		//最大长度
    iArray[5][3]=0;         			//是否允许录入，0--不能，1--允许
    
    iArray[6]=new Array();
    iArray[6][0]="有效日期";      	//列名
    iArray[6][1]="80px";          //宽度
    iArray[6][2]=100;         		//最大长度
    iArray[6][3]=0;         			//是否允许录入，0--不能，1--允许
    
    iArray[7]=new Array();
    iArray[7][0]="经办人";      	//列名
    iArray[7][1]="80px";          //宽度
    iArray[7][2]=100;         		//最大长度
    iArray[7][3]=0;         			//是否允许录入，0--不能，1--允许
    
    iArray[8]=new Array();
    iArray[8][0]="经办日期";      	//列名
    iArray[8][1]="80px";          //宽度
    iArray[8][2]=100;         		//最大长度
    iArray[8][3]=0;         			//是否允许录入，0--不能，1--允许
    
    iArray[9]=new Array();
    iArray[9][0]="发放号";      	//列名
    iArray[9][1]="80px";          //宽度
    iArray[9][2]=100;         		//最大长度
    iArray[9][3]=0;         			//是否允许录入，0--不能，1--允许
    
    iArray[10]=new Array();
    iArray[10][0]="回收号";      	//列名
    iArray[10][1]="80px";          //宽度
    iArray[10][2]=100;         		//最大长度
    iArray[10][3]=0;         			//是否允许录入，0--不能，1--允许
    
    iArray[11]=new Array();
    iArray[11][0]="发放日期";      	//列名
    iArray[11][1]="80px";          //宽度
    iArray[11][2]=100;         		//最大长度
    iArray[11][3]=0;         			//是否允许录入，0--不能，1--允许
    
    iArray[12]=new Array();
    iArray[12][0]="回收日期";      	//列名
    iArray[12][1]="80px";          //宽度
    iArray[12][2]=100;         		//最大长度
    iArray[12][3]=0;         			//是否允许录入，0--不能，1--允许
    

    SysCertifyGrid = new MulLineEnter( "document" , "SysCertifyGrid" ); 

    //这些属性必须在loadMulLine前
    SysCertifyGrid.mulLineCount = 5;   
    SysCertifyGrid.displayTitle = 1;
    SysCertifyGrid.canSel=1;
    SysCertifyGrid.loadMulLine(iArray);  

  }
  catch(ex)
  {
    alert("初始化SysCertifyGrid时出错："+ ex);
  }
}
</script>
