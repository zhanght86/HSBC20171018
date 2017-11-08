<%
//程序名称：LLBnfInit.jsp
//程序功能：调查信息查询页面初始化
//创建日期：2005-05-10
//创建人  ：zhoulei
//更新记录：
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('BnfKind').value = nullToEmpty("<%= tBnfKind %>");
    document.all('insuredno').value = nullToEmpty("<%= tInsuredNo %>");
    document.all('oriBnfNo').value = "";
    
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

function initInpBox()
{
    try
    {
        fm.addButton.disabled = true;
        fm.updateButton.disabled = true;
        fm.deleteButton.disabled = true;
        fm.saveButton.disabled = true;

    }
    catch(ex)
    {
        alert("在LLBnfInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initSelBox()
{
    try
    {

    }
    catch(ex)
    {
        alert("在LLBnfInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
    try
    {
    	initParam();
        initInpBox();
        initSelBox();
        initLLBalanceGrid();
        queryLLBalanceGrid();
        initLLBnfGrid();
        queryBnfBatNo();
        
    }
    catch(re)
    {
        alert("LLBnfInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
//赔案保单名细表的初始化
function initLLBalanceGrid()
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
        iArray[1][0]="立案号";             //列名
        iArray[1][1]="160px";                //列宽
        iArray[1][2]=160;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="保单号";             //列名
        iArray[2][1]="200px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[3]=new Array();
        iArray[3][0]="分配项目编码";             //列名
        iArray[3][1]="60px";                //列宽
        iArray[3][2]=60;                  //列最大值
        iArray[3][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="分配项目";             //列名
        iArray[4][1]="160px";                //列宽
        iArray[4][2]=160;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="赔付金额";           //列名
        iArray[5][1]="200px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[6]=new Array();
        iArray[6][0]="GrpContNo";        //列名
        iArray[6][1]="100px";                //列宽
        iArray[6][2]=100;                  //列最大值
        iArray[6][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="GrpPolNo";             //列名
        iArray[7][1]="100px";                //列宽
        iArray[7][2]=200;                  //列最大值
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="ContNo";          //列名
        iArray[8][1]="100px";                //列宽
        iArray[8][2]=200;                  //列最大值
        iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[9]=new Array();
        iArray[9][0]="批次号";          //列名
        iArray[9][1]="100px";                //列宽
        iArray[9][2]=200;                  //列最大值
        iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[10]=new Array();
        iArray[10][0]="是否已分配";         //列名
        iArray[10][1]="100px";                //列宽
        iArray[10][2]=200;                  //列最大值
        iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[11]=new Array();
        iArray[11][0]="分配情况";         //列名
        iArray[11][1]="100px";                //列宽
        iArray[11][2]=200;                  //列最大值
        iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        LLBalanceGrid = new MulLineEnter( "fm" , "LLBalanceGrid" );
        //这些属性必须在loadMulLine前
        LLBalanceGrid.mulLineCount = 0;
        LLBalanceGrid.displayTitle = 1;
        LLBalanceGrid.locked = 1;
        LLBalanceGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLBalanceGrid.selBoxEventFuncName = "LLBalanceGridClick"; //点击RadioBox时响应的函数名
//        LLBalanceGrid.selBoxEventFuncParm =""; //响应函数的第二个参数
        LLBalanceGrid.hiddenPlus=1;
        LLBalanceGrid.hiddenSubtraction=1;
        LLBalanceGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

//理赔受益人账户表初始化
function initLLBnfGrid()
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
        iArray[1][0]="立案号";             //列名
        iArray[1][1]="160px";                //列宽
        iArray[1][2]=160;                  //列最大值
        iArray[1][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="保单号码";             //列名
        iArray[2][1]="100px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[3]=new Array();
        iArray[3][0]="被保险人号码";     //列名
        iArray[3][1]="100px";                //列宽
        iArray[3][2]=100;                  //列最大值
        iArray[3][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="受益人序号";        //列名
        iArray[4][1]="100px";                //列宽
        iArray[4][2]=100;                  //列最大值
        iArray[4][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="受益人号码";             //列名
        iArray[5][1]="100px";                //列宽
        iArray[5][2]=200;                  //列最大值
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="受益人姓名";          //列名
        iArray[6][1]="120px";                //列宽
        iArray[6][2]=200;                  //列最大值
        iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="领款人号码";         //列名
        iArray[7][1]="100px";                //列宽
        iArray[7][2]=200;                  //列最大值
        iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="领款人姓名";         //列名
        iArray[8][1]="120px";                //列宽
        iArray[8][2]=200;                  //列最大值
        iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[9]=new Array();
        iArray[9][0]="bnftype";         //列名
        iArray[9][1]="80px";                //列宽
        iArray[9][2]=200;                  //列最大值
        iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[10]=new Array();
        iArray[10][0]="bnfgrade";         //列名
        iArray[10][1]="80px";                //列宽
        iArray[10][2]=200;                  //列最大值
        iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[11]=new Array();
        iArray[11][0]="受益人与被保人关系";         //列名
        iArray[11][1]="80px";                //列宽
        iArray[11][2]=80;                  //列最大值
        iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[12]=new Array();
        iArray[12][0]="受益人性别";                //列名
        iArray[12][1]="80px";                //列宽
        iArray[12][2]=200;                  //列最大值
        iArray[12][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[13]=new Array();
        iArray[13][0]="受益人出生日期";         //列名
        iArray[13][1]="80px";                //列宽
        iArray[13][2]=200;                  //列最大值
        iArray[13][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[14]=new Array();
        iArray[14][0]="受益人证件类型";         //列名
        iArray[14][1]="80px";                //列宽
        iArray[14][2]=200;                  //列最大值
        iArray[14][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[15]=new Array();
        iArray[15][0]="受益人证件号码";         //列名
        iArray[15][1]="80px";                //列宽
        iArray[15][2]=200;                  //列最大值
        iArray[15][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[16]=new Array();
        iArray[16][0]="relationtopayee";         //列名
        iArray[16][1]="80px";                //列宽
        iArray[16][2]=200;                  //列最大值
        iArray[16][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[17]=new Array();
        iArray[17][0]="领款人性别";         //列名
        iArray[17][1]="80px";                //列宽
        iArray[17][2]=200;                  //列最大值
        iArray[17][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[18]=new Array();
        iArray[18][0]="领款人出生日期";       //列名
        iArray[18][1]="80px";                //列宽
        iArray[18][2]=200;                  //列最大值
        iArray[18][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[19]=new Array();
        iArray[19][0]="领款人证件类型";         //列名
        iArray[19][1]="80px";                //列宽
        iArray[19][2]=200;                  //列最大值
        iArray[19][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[20]=new Array();
        iArray[20][0]="领款人证件号码";         //列名
        iArray[20][1]="80px";                //列宽
        iArray[20][2]=200;                  //列最大值
        iArray[20][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[21]=new Array();
        iArray[21][0]="受益金额";         //列名
        iArray[21][1]="80px";                //列宽
        iArray[21][2]=200;                  //列最大值
        iArray[21][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[22]=new Array();
        iArray[22][0]="受益比例";          //列名
        iArray[22][1]="80px";                //列宽
        iArray[22][2]=200;                  //列最大值
        iArray[22][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[23]=new Array();
        iArray[23][0]="支付方式编码";          //列名
        iArray[23][1]="100px";                //列宽
        iArray[23][2]=200;                  //列最大值
        iArray[23][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[24]=new Array();
        iArray[24][0]="银行编码";          //列名
        iArray[24][1]="100px";                //列宽
        iArray[24][2]=200;                  //列最大值
        iArray[24][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[25]=new Array();
        iArray[25][0]="银行帐号";          //列名
        iArray[25][1]="100px";                //列宽
        iArray[25][2]=200;                  //列最大值
        iArray[25][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[26]=new Array();
        iArray[26][0]="银行帐户名";          //列名
        iArray[26][1]="100px";                //列宽
        iArray[26][2]=200;                  //列最大值
        iArray[26][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[27]=new Array();
        iArray[27][0]="分配项目编码";             //列名
        iArray[27][1]="60px";                //列宽
        iArray[27][2]=60;                  //列最大值
        iArray[27][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[28]=new Array();
        iArray[28][0]="支付方式";          //列名
        iArray[28][1]="100px";                //列宽
        iArray[28][2]=100;                  //列最大值
        iArray[28][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[29]=new Array();
        iArray[29][0]="原受益人序号";          //列名
        iArray[29][1]="10px";                //列宽
        iArray[29][2]=10;                  //列最大值
        iArray[29][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[30]=new Array();
        iArray[30][0]="备份未合并金额前的本项目的受益金额";          //列名
        iArray[30][1]="10px";                //列宽
        iArray[30][2]=10;                  //列最大值
        iArray[30][3]=3;                    //是否允许输入,1表示允许，0表示不允许
                
        LLBnfGrid = new MulLineEnter( "fm" , "LLBnfGrid" );
        //这些属性必须在loadMulLine前
        LLBnfGrid.mulLineCount = 0;
        LLBnfGrid.displayTitle = 1;
        LLBnfGrid.locked = 0;
        LLBnfGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLBnfGrid.selBoxEventFuncName = "LLBnfGridClick"; //点击RadioBox时响应的函数名
//        LLBnfGrid.selBoxEventFuncParm =""; //响应函数的第二个参数
        LLBnfGrid.hiddenPlus=1;
        LLBnfGrid.hiddenSubtraction=1;
        LLBnfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
