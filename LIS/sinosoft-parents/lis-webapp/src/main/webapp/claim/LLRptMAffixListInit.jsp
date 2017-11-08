<%
//Name:LLRptMAffixListInit.jsp
//Function：单证生成初始化程序
//Date：2005-05-21 17:44:28
//Author ：潘志豪
%>
<!--用户校验类-->
<%
  String claimTypes=request.getParameter("claimTypes");
  loggerDebug("LLRptMAffixListInit","claimTypes:"+claimTypes);
  String CaseNo= request.getParameter("CaseNo");
  loggerDebug("LLRptMAffixListInit",CaseNo);
  String whoNo= request.getParameter("whoNo");
  loggerDebug("LLRptMAffixListInit",whoNo);
  String Proc=request.getParameter("Proc");
  loggerDebug("LLRptMAffixListInit",Proc);
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
var turnPage = new turnPageClass(); 

function initForm()
{
    try
    {
    	initInpBox();
    	initAffixGrid();
	    initList();    	
    }
    catch(re)
    {
        alert("LLMAffixListInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//add by panzh 2005-05-24 start
function initInpBox()
{
    try
    {
      fm.RptNo.value="<%=CaseNo%>";
      fm.customerNo.value="<%=whoNo%>";
      fm.claimTypes.value="<%=claimTypes%>";
      fm.Proc.value="<%=Proc%>";
      if (fm.Proc.value=="Reg")
      {
        fm.add.style.display="none";
      }
    }
    catch(re)
    {
        alert("LLMAffixListInit.jsp-->initInpBox函数中发生异常:初始化界面错误!");
    }
}
//add by panzh 2005-05-24 end

//所需单证信息
function initAffixGrid()
{
    var iArray = new Array();
    try
    {        
        iArray[0]=new Array();
        iArray[0][0]="序号";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="单证代码";
        iArray[1][1]="30px";
        iArray[1][2]=10;
        iArray[1][3]=0;
     
        iArray[2]=new Array();
        iArray[2][0]="单证名称";
        iArray[2][1]="200px";
        iArray[2][2]=10;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="是否必需";
        iArray[3][1]="40px";
        iArray[3][2]=10;
        iArray[3][3]=2;   
        iArray[3][10]="NeedFlag";
        iArray[3][11]="0|^0|是^1|否";      
        iArray[3][14]="0";
                
        iArray[4]=new Array();
        iArray[4][0]="单证件数";
        iArray[4][1]="40px";
        iArray[4][2]=10;
        iArray[4][3]=1;  
        iArray[4][14]="1";  
                        
        iArray[5]=new Array();
        iArray[5][0]="提交形式";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=2;   
        iArray[5][10]="ClmProperty";
        iArray[5][11]="0|^0|原件^1|复印件";      
        iArray[5][14]="0";
        
        AffixGrid = new MulLineEnter("fm","AffixGrid");
        AffixGrid.mulLineCount = 0;
        AffixGrid.displayTitle = 1;
        AffixGrid.locked = 0;
        AffixGrid.canChk =1;              //多选按钮，1显示，0隐藏
        AffixGrid.canSel =0;              //单选按钮，1显示，0隐藏
        AffixGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        AffixGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        AffixGrid.loadMulLine(iArray);
        
        fm.cols.value="9";
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
