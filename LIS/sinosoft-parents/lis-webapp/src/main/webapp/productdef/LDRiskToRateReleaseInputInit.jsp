<%@include file="../i18n/language.jsp"%>
<%
//程序名称：WFEdorNoscanAppInit.jsp
//程序功能：保全工作流-保全无扫描申请
//创建日期：2005-04-27 15:13:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//            XinYQ     2006-11-08   格式调整
//
%>
<%@page contentType="text/html;charset=GBK" %>

<script type="text/javascript">

var CollectivityGrid;
 var CollectivityGrid2;
function initForm()
{
    try
    {
        initCollectivityGrid();          //初始化价格信息列表
        
	    fm.all('RiskCode').value = '';
	    fm.all('RateType').value = '';
	    fm.all('RiskCodeName').value = '';
	    fm.all('RateTypeName').value = '';
	   fm.all('changeReason').value = '';
	   fm.all('auditConclusion').value = '';
        init();    
    }
    catch (ex)
    {
        myAlert("在 LOAccInputInit.jsp --> initForm 函数中发生异常:初始化界面错误！ ");
    }
}



function initCollectivityGrid()
{
    var iArray = new Array();
    try
    {
    	iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="35px";                    //列宽
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;
    
        iArray[1]=new Array();
        iArray[1][0]="产品编码";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[1][1]="25px";                    //列宽
        iArray[1][2]=30;                        //列最大值
        iArray[1][3]=0;                         //是否允许输入,1表示允许，0表示不允许


        iArray[2]=new Array();
        iArray[2][0]="产品名称";
        iArray[2][1]="80px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="费率类型";
        iArray[3][1]="25px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="费率表名";
        iArray[4][1]="40px";
        iArray[4][2]=100;
        iArray[4][3]=0;
         
        iArray[5]=new Array();
        iArray[5][0]="最后一次修改日期";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="最后一次修改人";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="最后一次审核人";
        iArray[7][1]="50px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="最后一次审核日期";
        iArray[8][1]="50px";
        iArray[8][2]=100;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="审核状态";
        iArray[9][1]="50px";
        iArray[9][2]=100;
        iArray[9][3]=0;
       
        
        CollectivityGrid = new MulLineEnter("fm", "CollectivityGrid");
        //这些属性必须在loadMulLine前
        CollectivityGrid.mulLineCount = 5;
        CollectivityGrid.displayTitle = 1;
        CollectivityGrid.locked = 1;				//如何锁定或解锁”+”和"--"标记
        CollectivityGrid.canSel = 1;				//Radio 单选框1 显示 ；0 隐藏（缺省值）
        CollectivityGrid.canChk = 0;				// 1为显示CheckBox列，0为不显示 (缺省值)
        CollectivityGrid.selBoxEventFuncName = "ShowPlan";  
        CollectivityGrid.hiddenPlus = 1;
        CollectivityGrid.hiddenSubtraction = 1;
        CollectivityGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        myAlert("在 LOAccInputInit.jsp --> initCollectivityGrid 函数中发生异常:初始化界面错误！ ");
    }
}


//function initForm1()
//{
//    try
//    {
//        initCollectivityGrid();          //初始化价格信息列表
//        
//	    fm.all('RiskCode').value = '';
//	    fm.all('RateType').value = '';
//	 
 //       easyQueryClick();
//        
//    }
//    catch (ex)
//    {
//        alert("在 LOAccInputInit.jsp  initForm 函数中发生异常:初始化界面错误！ ");
//    }
//}

</script>
