<%
//**************************************************************************************************
//Name：LPStateQueryInit.jsp
//Function：赔案状态查询页面
//Author： wangjm
//Date: 2006-4-7
//Desc: 
//**************************************************************************************************
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

    document.all('ClmNo').value = '';
    document.all('CustomerNo').value = '';
    document.all('GrpContNo').value = '';
    document.all('CustomerName').value = '';
    document.all('ManageCom').value = nullToEmpty(manageCom);
  }
  catch(ex)
  {
    alert("在ProposalQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在ProposalQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}   

//把null的字符串转为空
function nullToEmpty(string)
{
 if ((string == "null") || (string == "undefined"))
 {
  string = "";
 }
 return string;
}
function initForm()
{
  try
  { 
    initInpBox();
    initSelBox();   
    initClaimGrid();
    initClaimStateGrid();   
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 赔案信息列表的初始化
function initClaimGrid()
  {     
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            //列宽
      iArray[0][2]=10;            //列最大值
      iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="立案号";        //列名
      iArray[1][1]="80px";           //列宽
      iArray[1][2]=100;            //列最大值
      iArray[1][3]=0;              //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="单位名称";       //列名
      iArray[2][1]="120px";          //列宽
      iArray[2][2]=100;            //列最大值
      iArray[2][3]=0;              //是否允许输入,1表示允许，0表示不允许
      iArray[3]=new Array();
      iArray[3][0]="团体保单号";     //列名
      iArray[3][1]="80px";         //列宽
      iArray[3][2]=100;            //列最大值
      iArray[3][3]=0;              //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="报案/立案操作员";  //列名
      iArray[4][1]="80px";          //列宽
      iArray[4][2]=100;            //列最大值
      iArray[4][3]=0;              //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="管理机构";       //列名
      iArray[5][1]="80px";         //列宽
      iArray[5][2]=100;            //列最大值
      iArray[5][3]=0;              //是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="";             //列名
      iArray[6][1]="0px";         //列宽
      iArray[6][2]=100;            //列最大值
      iArray[6][3]=3;              //是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="";         //列名
      iArray[7][1]="0px";         //列宽
      iArray[7][2]=30;           //列最大值
      iArray[7][3]=3;            //是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="调查状态";             //列名
      iArray[8][1]="80px";         //列宽
      iArray[8][2]=100;            //列最大值
      iArray[8][3]=0;              //是否允许输入,1表示允许，0表示不允许      
      
      ClaimGrid = new MulLineEnter( "document" , "ClaimGrid" ); 
      //这些属性必须在loadMulLine前
      ClaimGrid.mulLineCount = 5;   
      ClaimGrid.displayTitle = 1;
      ClaimGrid.locked = 1;
      ClaimGrid.canSel = 1;
 //   ClaimGrid.canChk = 0;
      ClaimGrid.hiddenPlus = 1;
      ClaimGrid.hiddenSubtraction = 1;
      ClaimGrid.selBoxEventFuncName ="displayClick";
      ClaimGrid.loadMulLine(iArray);      
   }
    catch(ex)
    {
        alert(ex);
    }
}

// 赔案状态列表的初始化
function  initClaimStateGrid()
  {   
                           
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         //列宽
      iArray[0][2]=10;            //列最大值
      iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="赔案状态明细";     //列名
      iArray[1][1]="200px";           //列宽
      iArray[1][2]=100;            //列最大值
      iArray[1][3]=0;              //是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="案件类型";         //列名
      iArray[2][1]="200px";            //列宽
      iArray[2][2]=100;              //列最大值
      iArray[2][3]=0;                 //是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="复核操作员";      //列名
      iArray[3][1]="100px";          //列宽
      iArray[3][2]=100;            //列最大值
      iArray[3][3]=0;              //是否允许输入,1表示允许，0表示不允许

      ClaimStateGrid = new MulLineEnter( "document" , "ClaimStateGrid" ); 
      //这些属性必须在loadMulLine前
      ClaimStateGrid.mulLineCount = 5;   
      ClaimStateGrid.displayTitle = 1;
      ClaimStateGrid.locked = 1;
      ClaimStateGrid.hiddenPlus = 1;
      ClaimStateGrid.hiddenSubtraction = 1;
      ClaimStateGrid.canSel = 0;
      ClaimStateGrid.canChk = 0;
      ClaimStateGrid.loadMulLine(iArray);  
    }
      catch(ex)
      {
        alert(ex);
      }
}
</script>