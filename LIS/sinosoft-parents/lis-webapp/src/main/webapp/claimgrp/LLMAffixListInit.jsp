<%
//Name:LLMAffixListInit.jsp
//Function：单证生成及回销界面初始化程序
//Date：2005-05-21 17:44:28
//Author ：潘志豪
%>
<!--用户校验类-->
<%
  String claimTypes=request.getParameter("claimTypes");
  loggerDebug("LLMAffixListInit","claimTypes:"+claimTypes);
  String CaseNo= request.getParameter("CaseNo");
  loggerDebug("LLMAffixListInit",CaseNo);
  String whoNo= request.getParameter("whoNo");
  loggerDebug("LLMAffixListInit",whoNo);
  String Proc=request.getParameter("Proc");
  loggerDebug("LLMAffixListInit",Proc);
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
      fm.CaseNo.value="<%=CaseNo%>";
      fm.whoNo.value="<%=whoNo%>";
      fm.claimTypes.value="<%=claimTypes%>";
      fm.Proc.value="<%=Proc%>";
      if (fm.Proc.value=="Reg"){
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
        var Proc="<%=Proc%>";
        var isShow=new Array();
        var canChk=0;
        var canSel=0;
        switch(Proc){
        case "Rpt":
          isShow[0]=0;
          isShow[1]=0;
          isShow[2]=0;
          isShow[3]=3;
          isShow[4]=0;          
          isShow[5]=1;
          isShow[6]=3;
          isShow[7]=2;     
          isShow[8]=3;  
          isShow[9]=3;            
          canChk=1;
          canSel=0;             
          break;        
        case "RgtAdd":
          isShow[0]=0;
          isShow[1]=0;
          isShow[2]=0;
          isShow[3]=0;
          isShow[4]=0;          
          isShow[5]=1;
          isShow[6]=3;
          isShow[7]=0;     
          isShow[8]=3;               
          isShow[9]=3;                         
          canChk=1;
          canSel=0;             
          break;
        case "Rgt":
          isShow[0]=0;
          isShow[1]=0;
          isShow[2]=0;
          isShow[3]=2;
          isShow[4]=3;          
          isShow[5]=0;
          isShow[6]=1;
          isShow[7]=0; 
          isShow[8]=2;           
          isShow[9]=3;                     
          canChk=0;
          canSel=0;  
          break;
        default:           
        }

        iArray[0]=new Array();
        iArray[0][0]="序号";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=isShow[0];


        iArray[1]=new Array();
        iArray[1][0]="单证代码";
        iArray[1][1]="30px";
        iArray[1][2]=10;
        iArray[1][3]=isShow[1];

        
        iArray[2]=new Array();
        iArray[2][0]="单证名称";
        iArray[2][1]="80px";
        iArray[2][2]=10;
        iArray[2][3]=isShow[2];        
        
        iArray[3]=new Array();
        iArray[3][0]="是否已提交";
        iArray[3][1]="40px";
        iArray[3][2]=10;
        iArray[3][3]=isShow[3];  
        iArray[3][10]="ClmIsSub";
        iArray[3][11]="0|^1|是^2|否";      
        iArray[3][12] = "3";
        iArray[3][13] = "1";        

        iArray[4]=new Array();
        iArray[4][0]="是否必需";
        iArray[4][1]="40px";
        iArray[4][2]=10;
        iArray[4][3]=isShow[4];
        //iArray[4][10]="ClmIsNeed";
        //iArray[4][11]="0|^1|是^2|否";
        //iArray[4][12] = "4";
        //iArray[4][13] = "1";        
                
        iArray[5]=new Array();
        iArray[5][0]="单证件数";
        iArray[5][1]="30px";
        iArray[5][2]=10;
        iArray[5][3]=isShow[5];  
        iArray[5][14]=1;  

        iArray[6]=new Array();
        iArray[6][0]="缺少件数";
        iArray[6][1]="30px";
        iArray[6][2]=10;
        iArray[6][3]=isShow[6];  
                
        iArray[7]=new Array();
        iArray[7][0]="提交形式";
        iArray[7][1]="40px";
        iArray[7][2]=10;
        iArray[7][3]=isShow[7]; 
        if(isShow[7]=="2"){
          iArray[7][10]="ClmProperty";
          iArray[7][11]="0|^1|原件^2|复印件";      
          iArray[7][12] = "7";
          iArray[7][13] = "1";
          iArray[7][14]="原件";
        }

        iArray[8]=new Array();
        iArray[8][0]="是否退还原件";
        iArray[8][1]="40px";
        iArray[8][2]=10;
        iArray[8][3]=isShow[8]; 
        iArray[8][10]="ClmIsReturn";
        iArray[8][11]="0|^1|是^2|否";      
        iArray[8][12] = "8";
        iArray[8][13] = "1";
        //iArray[8][14]="否";                          

        iArray[9]=new Array();
        iArray[9][0]="附件号码";
        iArray[9][1]="40px";
        iArray[9][2]=10;
        iArray[9][3]=isShow[9]; 
        iArray[9][14]="00";
        
        AffixGrid = new MulLineEnter("fm","AffixGrid");
        AffixGrid.mulLineCount = 0;
        AffixGrid.displayTitle = 1;
        AffixGrid.locked = 0;
        AffixGrid.canChk =canChk;              //多选按钮，1显示，0隐藏
        AffixGrid.canSel =canSel;              //单选按钮，1显示，0隐藏
        AffixGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        AffixGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        //AffixGrid.selBoxEventFuncName = "getAffixGrid"; //函数名称
        //AffixGrid.selBoxEventFuncParm ="divLLInqCourse";        //参数
        AffixGrid.loadMulLine(iArray);
        
        fm.cols.value="9";
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
