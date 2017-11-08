<%
//程序名称：LDPersonQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    document.all('CustomerNo').value = '';
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('Birthday').value = '';
    document.all('IDType').value = '';
    document.all('IDNo').value = '';
    
  }
  catch(ex)
  {
    alert("在LDPersonQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在LDPersonQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initPersonGrid();
  }
  catch(re)
  {
    alert("LDPersonQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initPersonGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="50px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号码";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="姓名";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="性别";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10] = "Sex";
      iArray[3][11] = "0|^0|男^1|女^2|不详";
      iArray[3][12] = "3";
      iArray[3][19] = "0";		

      iArray[4]=new Array();
      iArray[4][0]="出生日期";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="证件类型";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=2; 
      iArray[5][4]="IDType";              	        //是否引用代码:null||""为不引用
      iArray[5][5]="3";              	                //引用代码对应第几列，'|'为分割符
      iArray[5][9]="证件类型|code:IDType&NOTNULL";
      iArray[5][18]=250;
      iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="证件号码";         		//列名
      iArray[6][1]="140px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      


      
      PersonGrid = new MulLineEnter( "fm" , "PersonGrid" ); 
      //这些属性必须在loadMulLine前
      PersonGrid.mulLineCount = 10;   
      PersonGrid.displayTitle = 1;
      PersonGrid.locked = 1;
      PersonGrid.canSel = 1;
      PersonGrid.hiddenPlus=1;
      PersonGrid.hiddenSubtraction=1;
      PersonGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
