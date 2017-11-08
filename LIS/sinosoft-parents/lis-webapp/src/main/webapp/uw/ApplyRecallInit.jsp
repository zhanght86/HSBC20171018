<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ApplyRecallInit.jsp
//程序功能：
//创建日期：
//创建人  :
//更新记录：  更新人    更新日期     更新原因/内容
%>                      

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {    
	// 保单查询条件
    document.all('PrtNoSearch').value = '';
    document.all('ContNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
                                     
    document.all('PrtNo').value = '';
    document.all('Content').value = '录入撤单说明';

  }
  catch(ex)
  {
    alert("ApplyRecallInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}                                      

function initForm(tPrtNo,tFlag)
{
  try
  {
	initInpBox();	
	initPolGrid();
  }
  catch(re)
  {
    alert("ApplyRecallInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单号";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    
      iArray[2]=new Array();
      iArray[2][0]="印刷号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="投保人";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="录入人员";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="录入日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="复核人员";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="复核日期";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="核保人员";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="核保日期";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="核保结论";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=2;              			//是否允许输入,1表示允许，0表示不允许   
      iArray[10][4]="UWState";              	        //是否引用代码:null||""为不引用
      iArray[10][5]="13";              	                //引用代码对应第几列，'|'为分割符
      iArray[10][9]="核保结论|code:UWState&NOTNULL";
      iArray[10][18]=250;
      iArray[10][19]= 0 ;
            
      iArray[11]=new Array();
      iArray[11][0]="扫描时间";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许        
      
      iArray[12]=new Array();
      iArray[12][0]="保费";         		//列名
      iArray[12][1]="100px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许        
      
      iArray[13]=new Array();
      iArray[13][0]="体检标志";         		//列名
      iArray[13][1]="100px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许        
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


