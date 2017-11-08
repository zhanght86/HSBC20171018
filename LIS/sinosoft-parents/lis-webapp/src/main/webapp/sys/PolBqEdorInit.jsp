<%
//程序名称：AllPBqQueryInit.jsp
//程序功能：
//创建日期：2002-12-16
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
     //添加页面控件的初始化。
%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
                              

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {   
	// 保单查询条件
    if (tflag=="0")
    {   
    	document.all('ContNo1').value = tContNo;
    	//alert(tContNo);
    }
    else 
    {
    	document.all('EdorAcceptNo').value = '';
    	document.all('ContNo1').value = tContNo;
    }
  }
  catch(ex)
  {
    alert("在PolBqEdorInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}


// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在AllPBqQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initBqEdorGrid();
	getBqEdorGrid();
		//if(tflag=="0") easyQueryClick();
  }
  catch(re)
  {
    alert("AllGBqQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    alert(re);
  }
}

function initBqEdorGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保全受理号";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="批单号码";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="批改类型";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="申请日期";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=8;
        
        iArray[5]=new Array();
        iArray[5][0]="生效日期";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=8;
                
        iArray[6]=new Array();
        iArray[6][0]="批改状态";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="操作员";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
      BqEdorGrid = new MulLineEnter( "document" , "BqEdorGrid" ); 
      //这些属性必须在loadMulLine前
      BqEdorGrid.mulLineCount = 5;   
      BqEdorGrid.displayTitle = 1;
      BqEdorGrid.canSel =1;
      BqEdorGrid.selBoxEventFuncName ="getBqEdorDetail" ;     //点击RadioBox时响应的JS函数
      BqEdorGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      BqEdorGrid.hiddenSubtraction=1;
      BqEdorGrid.loadMulLine(iArray);        
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
