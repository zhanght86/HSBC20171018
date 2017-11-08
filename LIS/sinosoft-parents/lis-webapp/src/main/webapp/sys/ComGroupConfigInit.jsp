<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
     //添加页面控件的初始化。
%>  
<script language="JavaScript">

function initForm()
{

  try
  { 
    initInpBox();    
    initComGroupMapGrid();  
    initComGroupGrid();
    //queryData('1');
    queryComGroupConfig();
    document.all('divOtherGrid').style.display="none";
    //alert("000000000000000000000");     
  }
  catch(re)
  {
    alert("在BonusRiskPreInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{ 
  try
  {     
    document.all('ComGroupCode').value = '';
    document.all('ComGroupName').value = '';
    document.all('GroupInfo').value = '';
   
  }
  catch(ex)
  {
    alert("在NBonusRiskPreInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
  
  function initComGroupMapGrid()
{                              
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="机构编码";         		//列名
      iArray[1][1]="50px";            		//列宽
      iArray[1][2]=50;            			//列最大值
      iArray[1][3]=1;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
     // iArray[1][4]="ComCode";  //引用代码:
		//	iArray[1][5]="1|2";
    //  iArray[1][6]="0|1";
   //   iArray[1][19]="1";
            
      iArray[2]=new Array();
      iArray[2][0]="机构名称";         		//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许



      ComGroupMapGrid = new MulLineEnter( "document" , "ComGroupMapGrid" ); 
      //这些属性必须在loadMulLine前
      ComGroupMapGrid.mulLineCount = 5;   
      ComGroupMapGrid.displayTitle = 1;
      ComGroupMapGrid.canSel =0;
      ComGroupMapGrid.hiddenPlus=0;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      ComGroupMapGrid.hiddenSubtraction=0;
      
      ComGroupMapGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
    function initComGroupGrid()
{                              
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="机构组编码";          	//列名
      iArray[1][1]="50px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
            
      iArray[2]=new Array();
      iArray[2][0]="机构组名称";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[3]=new Array();
      iArray[3][0]="机构组描述";         		//列名
      iArray[3][1]="200px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="批次号";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ComGroupGrid = new MulLineEnter( "document" , "ComGroupGrid" ); 
      //这些属性必须在loadMulLine前
      ComGroupGrid.mulLineCount = 5;   
      ComGroupGrid.displayTitle = 1;
      ComGroupGrid.canSel  =1;
      ComGroupGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      ComGroupGrid.hiddenSubtraction=1;
      ComGroupGrid. selBoxEventFuncName ="getComGroupDetail" ; 
      
      ComGroupGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
</script>
