<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ProposalQueryInit.jsp
//程序功能：承保查询
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
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
  	//初始化既往赔案multiline
  	
  	initClaimGrid();
  	
  	//初始化赔案险种信息
  	initPolGrid();
  	
  	
  	//查询客户信息
  	queryPersonInfo();
	 
	  //查询理赔信息
	  queryClaim(); 
	  //alert(555);
  	
  }
  catch(re) {
    alert("ClaimQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initPolGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="30px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="赔案号";         	        //列名
          iArray[1][1]="100px";            		//列宽
          iArray[1][2]=10;            			//列最大值
          iArray[1][3]=0;  
                    
          iArray[2]=new Array();
          iArray[2][0]="保单号";         		//列名
          iArray[2][1]="110px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[3]=new Array();
          iArray[3][0]="险种（保项）";         		//列名
          iArray[3][1]="120px";            		//列宽
          iArray[3][2]=100;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[4]=new Array();
          iArray[4][0]="理赔结论";         		//列名
          iArray[4][1]="160px";            		//列宽
          iArray[4][2]=100;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
           
          iArray[5]=new Array();
          iArray[5][0]="赔付金额";         		//列名
          iArray[5][1]="120px";            		//列宽
          iArray[5][2]=200;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[6]=new Array();
          iArray[6][0]="经办人";         		//列名
          iArray[6][1]="100px";            		//列宽
          iArray[6][2]=200;            			//列最大值
          iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          //iArray[7]=new Array();
          //iArray[7][0]="经办人";         		//列名
          //iArray[7][1]="60px";            		//列宽
          //iArray[7][2]=100;            			//列最大值
          //iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          
          
          
          PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 

          //这些属性必须在loadMulLine前
          PolGrid.mulLineCount = 5;   
          PolGrid.displayTitle = 1;
          PolGrid.locked = 1;
          PolGrid.canSel = 1;
          PolGrid.hiddenPlus = 1;
          PolGrid.hiddenSubtraction = 1;
          //PolGrid.selBoxEventFuncName = "alert";
          PolGrid.loadMulLine(iArray);  
          
          //这些操作必须在loadMulLine后面
          //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}


function initClaimGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="30px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="赔案号";         		//列名
          iArray[1][1]="200px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
            
          iArray[2]=new Array();
          iArray[2][0]="出险人姓名";         		//列名
          iArray[2][1]="200px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[3]=new Array();
          iArray[3][0]="理赔日期";         		//列名
          iArray[3][1]="210px";            		//列宽
          iArray[3][2]=200;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="赔案状态";         		//列名
          iArray[4][1]="120px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0; 
           
                
          ClaimGrid = new MulLineEnter( "fm" , "ClaimGrid" ); 

          //这些属性必须在loadMulLine前
          ClaimGrid.mulLineCount = 5;   
          ClaimGrid.displayTitle = 1;
          ClaimGrid.locked = 1;
          ClaimGrid.canSel = 1;
          ClaimGrid.hiddenPlus = 1;
          ClaimGrid.hiddenSubtraction = 1;
          ClaimGrid.selBoxEventFuncName = "getPolInfo";
          ClaimGrid.loadMulLine(iArray);  
          
          //这些操作必须在loadMulLine后面
          //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}

 

// 输入框的初始化
function initInpBox()
{
}

// 下拉框的初始化
function initSelBox(){  
}                                        


</script>
                       

