<%
//程序名称：LLGrpLdPersonQueryInput.jsp
//程序功能：团体客户信息查询
//创建日期： 2008-10-27
//创建人  ：zhangzheng
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
String GrpContNo     = request.getParameter("GrpContNo");
String GrpCustomerNo = request.getParameter("GrpCustomerNo");
String GrpName       = StrTool.unicodeToGBK(request.getParameter("GrpName"));
String ManageCom     = request.getParameter("ManageCom");
%>

<script language="JavaScript">
var GrpContNo = '<%=GrpContNo%>';
GrpContNo= (GrpContNo=='null'?"":GrpContNo);
var GrpCustomerNo = '<%=GrpCustomerNo%>';
GrpCustomerNo= (GrpCustomerNo=='null'?"":GrpCustomerNo);
var GrpName = '<%=GrpName%>';
GrpName = (GrpName=='null'?"":GrpName);
var ManageCom = '<%=ManageCom%>';
ManageCom = (ManageCom=='null'?"":ManageCom);

function initInpBox()
{ 
    try
    {                                   
				fm.GrpContNo.value = GrpContNo;
				fm.CustomerNo.value = GrpCustomerNo;
				fm.GrpContName.value = GrpName;
				fm.ManageCom.value = ManageCom;
    }
    catch(ex)
    {
        alert("在LLGrpLDPersonQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
       alert("在LLGrpLDPersonQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initPersonGrid();  
    }
    catch(re)
    {
        alert("LLGrpLDPersonQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
// 保单信息列表的初始化
function initPersonGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                //列宽
      iArray[0][2]=10;                  //列最大值
      iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="被保人客户号";             //列名
      iArray[1][1]="130px";                //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="被保人姓名";             //列名
      iArray[2][1]="100px";                //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="性别代码";             //列名
      iArray[3][1]="0px";                //列宽
      iArray[3][2]=0;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="性别";             //列名
      iArray[4][1]="60px";                //列宽
      iArray[4][2]=0;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="出生日期";             //列名
      iArray[5][1]="80px";                //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="证件类型";             //列名
      iArray[6][1]="80px";                //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="证件号码";             //列名
      iArray[7][1]="120px";                //列宽
      iArray[7][2]=200;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="证件类型值";             //列名
      iArray[8][1]="80px";                //列宽
      iArray[8][2]=200;                  //列最大值
      iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="团体投保人客户号";             //列名
      iArray[9][1]="0px";                //列宽
      iArray[9][2]=0;                  //列最大值
      iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="团体投保人客户姓名";             //列名
      iArray[10][1]="0px";                //列宽
      iArray[10][2]=0;                  //列最大值
      iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="团体合同号";             //列名
      iArray[11][1]="0px";                //列宽
      iArray[11][2]=0;                  //列最大值
      iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      
      PersonGrid = new MulLineEnter( "fm" , "PersonGrid" ); 
      //这些属性必须在loadMulLine前
      PersonGrid.mulLineCount = 0;   
      PersonGrid.displayTitle = 1;
      PersonGrid.locked = 1;
      PersonGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
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
