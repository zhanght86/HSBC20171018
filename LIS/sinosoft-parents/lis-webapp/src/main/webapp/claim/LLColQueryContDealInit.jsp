<%
//Name:LLMedicalFeeInpInit.jsp
//function??
//author:续涛
//Date:2005.07.13
%>

<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>


<script language="JavaScript">
/**=========================================================================
    Form载入页面时进行初始化
   =========================================================================
*/
function initForm()
{
    try
    {
        initInpBox();                   //初始化表格
        initContNoReferGrid()           //赔案未涉及的合同
        initContReferGrid()             //赔案涉及的合同
        initContPolReferGrid()          //赔案涉及的合同险种
        initContCalResultGrid()         //计算结果
        initLLContStateGrid()           //理赔合同终止信息        
        initQuery()                     //初始查询        

    }
    catch(re)
    {
        alter("LLClaimContDealInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

/**=========================================================================
    页面初始化
   =========================================================================
*/
function initInpBox()
{
    try
    {                                       
//      MedFeeInHosInp.style.display="none";
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}




/**=========================================================================
    赔案未涉及的保单
   =========================================================================
*/
function initContNoReferGrid()
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
        iArray[1][0]="集体合同号码";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="合同号";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="总单类型";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=3;
        
        iArray[4]=new Array();
        iArray[4][0]="投保人客户号";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="投保人名称";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="投保人性别";
        iArray[6][1]="0px";
        iArray[6][2]=10;
        iArray[6][3]=3; 
        
        
        iArray[7]=new Array();
        iArray[7][0]="投保人出生日期";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="被保人客户号";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="被保人名称";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="被保人性别";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
                
        iArray[11]=new Array();
        iArray[11][0]="被保人出生日期";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="投保日期";
        iArray[12][1]="80px";
        iArray[12][2]=80;
        iArray[12][3]=0;

        iArray[13]=new Array();
        iArray[13][0]="签单机构";
        iArray[13][1]="80px";
        iArray[13][2]=80;
        iArray[13][3]=0;        

        iArray[14]=new Array();
        iArray[14][0]="签单日期";
        iArray[14][1]="80px";
        iArray[14][2]=80;
        iArray[14][3]=0;  

        iArray[15]=new Array();
        iArray[15][0]="保单生效日";
        iArray[15][1]="80px";
        iArray[15][2]=80;
        iArray[15][3]=0;  

        iArray[16]=new Array();
        iArray[16][0]="投保标志";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=0;  
        
                                        
        ContNoReferGrid = new MulLineEnter("document","ContNoReferGrid");
        ContNoReferGrid.mulLineCount = 5;
        ContNoReferGrid.displayTitle = 1;
        ContNoReferGrid.locked = 0;
//      ContNoReferGrid.canChk =1;              //多选按钮，1显示，0隐藏
        ContNoReferGrid.canSel =1;              //单选按钮，1显示，0隐藏
        ContNoReferGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        ContNoReferGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
//      ContNoReferGrid.selBoxEventFuncName = "getMedFeeClinicInpGrid"; //函数名称
//      ContNoReferGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
        ContNoReferGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContNoReferGrid函数中发生异常:初始化界面错误!");
    }
}

/**=========================================================================
    赔案涉及的合同
   =========================================================================
*/
function initContReferGrid()
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
        iArray[1][0]="集体合同号码";
        iArray[1][1]="120px";
        iArray[1][2]=120;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="合同号";
        iArray[2][1]="120px";
        iArray[2][2]=120;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="总单类型";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=3;
        
        iArray[4]=new Array();
        iArray[4][0]="投保人客户号";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="投保人名称";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="投保人性别";
        iArray[6][1]="0px";
        iArray[6][2]=10;
        iArray[6][3]=3; 
        
        
        iArray[7]=new Array();
        iArray[7][0]="投保人出生日期";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="被保人客户号";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="被保人名称";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="被保人性别";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
                
        iArray[11]=new Array();
        iArray[11][0]="被保人出生日期";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="投保日期";
        iArray[12][1]="80px";
        iArray[12][2]=80;
        iArray[12][3]=0;

        iArray[13]=new Array();
        iArray[13][0]="签单机构";
        iArray[13][1]="80px";
        iArray[13][2]=80;
        iArray[13][3]=0;        

        iArray[14]=new Array();
        iArray[14][0]="签单日期";
        iArray[14][1]="80px";
        iArray[14][2]=80;
        iArray[14][3]=0;  

        iArray[15]=new Array();
        iArray[15][0]="保单生效日";
        iArray[15][1]="80px";
        iArray[15][2]=80;
        iArray[15][3]=0;  

        iArray[16]=new Array();
        iArray[16][0]="投保标志";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=0;  
        
        iArray[17]=new Array();
        iArray[17][0]="结算标志";
        iArray[17][1]="60px";
        iArray[17][2]=60;
        iArray[17][3]=3;  
        
        iArray[18]=new Array();
        iArray[18][0]="相关标志";
        iArray[18][1]="80px";
        iArray[18][2]=80;
        iArray[18][3]=0; 
                
                                                
        ContReferGrid = new MulLineEnter("document","ContReferGrid");
        ContReferGrid.mulLineCount = 5;
        ContReferGrid.displayTitle = 1;
        ContReferGrid.locked = 0;
//      ContReferGrid.canChk =1;              //多选按钮，1显示，0隐藏
        ContReferGrid.canSel =1;              //单选按钮，1显示，0隐藏
        ContReferGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        ContReferGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        ContReferGrid.selBoxEventFuncName = "getContReferGrid"; //函数名称
        ContReferGrid.selBoxEventFuncParm ="ContDeal";          //参数        
        ContReferGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContReferGrid函数中发生异常:初始化界面错误!");
    }
}


/**=========================================================================
    赔案涉及的合同
   =========================================================================
*/
function initContPolReferGrid()
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
        iArray[1][0]="集体合同号码";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=3;

        
        iArray[2]=new Array();
        iArray[2][0]="集体保单险种号";
        iArray[2][1]="120px";
        iArray[2][2]=120;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="合同号码";
        iArray[3][1]="120px";
        iArray[3][2]=120;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="保单险种号";
        iArray[4][1]="120px";
        iArray[4][2]=120;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="总单类型";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="主险保单号码";
        iArray[6][1]="0px";
        iArray[6][2]=10;
        iArray[6][3]=3; 
        
        
        iArray[7]=new Array();
        iArray[7][0]="险种编码";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;  
        
        iArray[8]=new Array();
        iArray[8][0]="被保人客户号";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="被保人名称";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="被保人性别";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
                
        iArray[11]=new Array();
        iArray[11][0]="被保人出生日期";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="投保日期";
        iArray[12][1]="80px";
        iArray[12][2]=80;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="签单机构";
        iArray[13][1]="80px";
        iArray[13][2]=80;
        iArray[13][3]=0;        

        iArray[14]=new Array();
        iArray[14][0]="签单日期";
        iArray[14][1]="80px";
        iArray[14][2]=80;
        iArray[14][3]=0;  

        iArray[15]=new Array();
        iArray[15][0]="保单生效日";
        iArray[15][1]="80px";
        iArray[15][2]=80;
        iArray[15][3]=0;  

        iArray[16]=new Array();
        iArray[16][0]="投保标志";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=0;  
        
                
                                                
        ContPolReferGrid = new MulLineEnter("document","ContPolReferGrid");
        ContPolReferGrid.mulLineCount = 5;
        ContPolReferGrid.displayTitle = 1;
        ContPolReferGrid.locked = 0;
//      ContPolReferGrid.canChk =1;              //多选按钮，1显示，0隐藏
        ContPolReferGrid.canSel =1;              //单选按钮，1显示，0隐藏
        ContPolReferGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        ContPolReferGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        ContPolReferGrid.selBoxEventFuncName = "getContPolReferGrid"; //函数名称
        ContPolReferGrid.selBoxEventFuncParm ="ContDeal";          //参数        
        ContPolReferGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContReferGrid函数中发生异常:初始化界面错误!");
    }
}

/**=========================================================================
    合同终止信息
   =========================================================================
*/
function initLLContStateGrid()
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
        iArray[1][0]="合同号";
        iArray[1][1]="120px";
        iArray[1][2]=120;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="被保人编号";
        iArray[2][1]="150px";
        iArray[2][2]=150;
        iArray[2][3]=3;        
        
        iArray[3]=new Array();
        iArray[3][0]="险种保单号";
        iArray[3][1]="120px";
        iArray[3][2]=120;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="状态类型";
        iArray[4][1]="150px";
        iArray[4][2]=150;
        iArray[4][3]=3;
        
        iArray[5]=new Array();
        iArray[5][0]="";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="状态";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0; 
        
                              
        iArray[7]=new Array();
        iArray[7][0]="生效时间";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;  
        
        iArray[8]=new Array();
        iArray[8][0]="结束时间";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
                                        
        iArray[9]=new Array();
        iArray[9][0]="";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=3; 
        
        iArray[10]=new Array();
        iArray[10][0]="合同处理结论";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=0;
                
        iArray[11]=new Array();
        iArray[11][0]="";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;
        
        iArray[12]=new Array();
        iArray[12][0]="理赔处理状态";
        iArray[12][1]="100px";
        iArray[12][2]=100;
        iArray[12][3]=0;
        
                                                                                
        LLContStateGrid = new MulLineEnter("document","LLContStateGrid");
        LLContStateGrid.mulLineCount = 5;
        LLContStateGrid.displayTitle = 1;
        LLContStateGrid.locked = 0;
//      LLContStateGrid.canChk =1;              //多选按钮，1显示，0隐藏
        LLContStateGrid.canSel =1;              //单选按钮，1显示，0隐藏
        LLContStateGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        LLContStateGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
//        LLContStateGrid.selBoxEventFuncName = "getContReferGrid"; //函数名称
//        LLContStateGrid.selBoxEventFuncParm ="ContDeal";          //参数        
        LLContStateGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContCalResultGrid函数中发生异常:初始化界面错误!");
    }
}


/**=========================================================================
    赔案涉及的合同计算的结果
   =========================================================================
*/
function initContCalResultGrid()
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
        iArray[1][0]="业务类型";
        iArray[1][1]="40px";
        iArray[1][2]=40;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="业务类型名称";
        iArray[2][1]="150px";
        iArray[2][2]=150;
        iArray[2][3]=3;        
        
        iArray[3]=new Array();
        iArray[3][0]="子业务类型";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="子业务类型名称";
        iArray[4][1]="150px";
        iArray[4][2]=150;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="保单号码";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="险种编码";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0; 
        
                              
        iArray[7]=new Array();
        iArray[7][0]="给付日期";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="计算金额";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
                                                                                        
        iArray[9]=new Array();
        iArray[9][0]="原始金额";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=3;     

        iArray[10]=new Array();
        iArray[10][0]="历史调整原因";
        iArray[10][1]="80px";
        iArray[10][2]=80;
        iArray[10][3]=3;
                                                                                
        ContCalResultGrid = new MulLineEnter("document","ContCalResultGrid");
        ContCalResultGrid.mulLineCount = 5;
        ContCalResultGrid.displayTitle = 1;
        ContCalResultGrid.locked = 0;
//      ContCalResultGrid.canChk =1;              //多选按钮，1显示，0隐藏
        ContCalResultGrid.canSel =1;              //单选按钮，1显示，0隐藏
        ContCalResultGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        ContCalResultGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        ContCalResultGrid.selBoxEventFuncName = "getContCalResultGrid"; //函数名称
        ContCalResultGrid.selBoxEventFuncParm ="ContDeal";              //参数        
        ContCalResultGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContCalResultGrid函数中发生异常:初始化界面错误!");
    }
}

function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}
</script>
