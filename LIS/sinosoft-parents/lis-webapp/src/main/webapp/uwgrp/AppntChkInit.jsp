<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：AppntChkInit.jsp
//程序功能：投保人查重
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{
	//alert(11);
	fm.all('ProposalNo').value = tContNo;
	//alert(12);
	fm.all('Flag').value = tFlag;
	//alert(13);
	if(tFlag=="I")
	{
	//alert(14);
	  fm.all('InsuredNo').value =tInsuredNo;
	}   
}

// 下拉框的初始化
function initSelBox(){  
}                                        

function initForm()
{
  try 
  {

    //alert(1);
    initInpBox();
    //alert(2);
    initSelBox();
    //alert(3);    
    initPolGrid();
    //alert(4);
    initOPolGrid();
    
//    alert(LoadFlag);

    if(LoadFlag=="5"){
      fm.all('button1').style.display="none";    
      divCustomerUnionInfo.style.display = "none";	
    }
    if(LoadFlag=="25"){
      fm.all('button1').style.display="";    	
    }
    if(LoadFlag == "3")
    {
   
    divBtCustomerUnionSendIssue.style.display = '';
    divBtCustomerUnion.style.display = "none";
    }
    //alert(5);
    initQuery();

    //divCustomerUnion.style.display = "none";
  }
  catch(re) {
    alert("AppntChkInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var OPolGrid;
// 保单信息列表的初始化
function initOPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="姓名";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="证件类型";         		//列名
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="证件号码";         		//列名
      iArray[4][1]="200px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="其它证件类型";         		//列名
      iArray[5][1]="200px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="其它证件号码";         		//列名
      iArray[6][1]="200px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      OPolGrid = new MulLineEnter( "fm" , "OPolGrid" ); 
      //这些属性必须在loadMulLine前
      OPolGrid.mulLineCount = 1;   
      OPolGrid.displayTitle = 1;
      OPolGrid.locked = 1;
      OPolGrid.canSel = 0;
      OPolGrid.hiddenPlus = 1;
      OPolGrid.hiddenSubtraction = 1;
      OPolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}



var PolGrid;
// 保单信息列表的初始化
function initPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="姓名";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="证件类型";         		//列名
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="证件号码";         		//列名
      iArray[4][1]="200px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="其它证件类型";         		//列名
      iArray[5][1]="200px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="其它证件号码";         		//列名
      iArray[6][1]="200px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 1;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.selBoxEventFuncName="customerUnion";
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
