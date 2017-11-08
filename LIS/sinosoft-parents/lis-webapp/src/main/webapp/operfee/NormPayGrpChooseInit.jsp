<%
//程序名称：NormPayGrpChooseInit.jsp
//程序功能：
//创建日期：2002-10-08 08:49:52
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%> 
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

function initInpBox()
{ 
  try
  { 
    document.all('GrpPolNo').value = '';     
    document.all('GrpContNo').value = '';
    document.all('PaytoDate').value = '';
    document.all('SumDuePayMoney').value = '';  
    document.all('PayDate').value = '<%=CurrentDate%>';
    document.all('ManageFeeRate').value = '';
    document.all('PolNo').value=''; //隐藏的号码，用于保存集体保单号用于提交       
  }
  catch(ex)
  {
    alert("在NormPayGrpChooseInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
 
  }
  catch(ex)
  {
    alert("在NormPayGrpChooseInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initNormPayGrpChooseGrid(); 
  }
  catch(re)
  {
    alert("NormPayGrpChooseInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

 
//初始化表格
   var NormPayGrpChooseGrid;
function initNormPayGrpChooseGrid()
{
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单号码";    	                //列名
      iArray[1][1]="140px";            		        //列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="责任编码";    	                //列名
      iArray[2][1]="60px";            		        //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费计划编码 ";         		//列名
      iArray[3][1]="60px";            		        //列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="交费计划编码名称 ";         		//列名
      iArray[4][1]="100px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="被保人姓名";         		//列名
      iArray[5][1]="80px";            		        //列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="应交金额";          		//列名
      iArray[6][1]="100px";            		        //列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="实交金额";          		//列名
      iArray[7][1]="100px";            		        //列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="保存标记";          		//列名
      iArray[8][1]="50px";            		        //列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
              
      iArray[9]=new Array();
      iArray[9][0]="被保人性别";         		//列名
      iArray[9][1]="50px";            		        //列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="出生日期";         		//列名
      iArray[10][1]="80px";            		        //列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="客户内部号码";          		//列名
      iArray[11][1]="150px";            		        //列宽
      iArray[11][2]=150;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
      NormPayGrpChooseGrid = new MulLineEnter( "fm" , "NormPayGrpChooseGrid" ); 
      //这些属性必须在loadMulLine前
      NormPayGrpChooseGrid.mulLineCount = 5;     
      NormPayGrpChooseGrid.displayTitle = 1;
      NormPayGrpChooseGrid.canChk = 1;
      NormPayGrpChooseGrid.hiddenPlus=1;   //隐藏"+"按钮
      NormPayGrpChooseGrid.hiddenSubtraction=1; //隐藏"-"按钮
      NormPayGrpChooseGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
  }

</script>