<script language="JavaScript">

                     

function initForm()
{
  try
  {
  	fm.EdorAcceptNo.value=EdorAcceptNo;
		easyQueryClickAll();
  }
  catch(re)
  {
    alert("AllNoticeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化AgentGrid
 ************************************************************
 */

    
function initAllNoticeGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="30px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

        iArray[1]=new Array();
        iArray[1][0]="保全受理号";         //列名
        iArray[1][1]="120px";         //宽度
        iArray[1][2]=100;         //最大长度
        iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="个人保单号";         //列名
        iArray[2][1]="120px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="客户号";         //列名
        iArray[3][1]="80px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=0;         //是否允许录入，0--不能，1--允许
 
        
        iArray[4]=new Array();
        iArray[4][0]="问题件类型Code";         //列名
        iArray[4][1]="0px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=3;         //是否允许录入，0--不能，1--允许

        iArray[5]=new Array();
        iArray[5][0]="问题件类型";         //列名
        iArray[5][1]="80px";         //宽度
        iArray[5][2]=100;         //最大长度
        iArray[5][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[6]=new Array();
        iArray[6][0]="经办人";         //列名
        iArray[6][1]="50px";         //宽度
        iArray[6][2]=100;         //最大长度
        iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[7]=new Array();
        iArray[7][0]="管理机构";         //列名
        iArray[7][1]="60px";         //宽度
        iArray[7][2]=100;         //最大长度
        iArray[7][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[8]=new Array();
        iArray[8][0]="下发日期";         //列名
        iArray[8][1]="80px";         //宽度
        iArray[8][2]=100;         //最大长度
        iArray[8][3]=0;         //是否允许录入，0--不能，1--允许

     		iArray[9]=new Array();
      	iArray[9][0]="问题";         		//列名
      	iArray[9][1]="0px";            		//列宽
      	iArray[9][2]=200;            			//列最大值
      	iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      	iArray[10]=new Array();
      	iArray[10][0]="回复";         		//列名
      	iArray[10][1]="0px";            		//列宽
      	iArray[10][2]=60;            			//列最大值
      	iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许
        
  
        AllNoticeGrid = new MulLineEnter( "fm" , "AllNoticeGrid" ); 
        AllNoticeGrid.displayTitle = 1;
        AllNoticeGrid.canSel=1;
        AllNoticeGrid.locked=1;
	      AllNoticeGrid.hiddenPlus=1;
	      AllNoticeGrid.hiddenSubtraction=1;
	      AllNoticeGrid.selBoxEventFuncName = "ShowInfo";      
        AllNoticeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化AllNoticeGrid时出错："+ ex);
      }
    }


</script>
