<%
/**
 * Created by IntelliJ IDEA。
 * User: jiyt
 * Date: 2012-6-25
 * Time: 9:42:15
 */
%>

<!--用户校验类-->

<script language="JavaScript"> 
function initForm()
{
    try
    {   initbox();
        initToBusiTableGrid();
    }
    catch(re)
    {
        alert("LDWFMullineInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
function initbox()
{
	   fm.FunctionID.value        = "";
	   fm.FunctionIDName.value    = "";
	   fm.BusiNessTable.value     = "";
	   fm.BusiNessTableName.value = "";
	   fm.FieldCode.value         = "";
	   fm.FieldCodeName.value     = "";
	   fm.ColWidth.value          = "";
	   fm.ColSerialNo.value       = "";
	   fm.IsShow.value            = "";
	   fm.IsShowName.value        = ""; 	
}
function initToBusiTableGrid()
{  
	    var iArray = new Array();
	    try
	    {
	        iArray[0] = new Array();
	        iArray[0][0] = "序号";
	        iArray[0][1] = "30px";
	        iArray[0][2] = 10;
	        iArray[0][3] = 0;

	        iArray[1] = new Array();
	        iArray[1][0] = "functionid";
	        iArray[1][1] = "80px";
	        iArray[1][2] = 10;
	        iArray[1][3] = 3;

	        iArray[2] = new Array();
	        iArray[2][0] = "功能名称";
	        iArray[2][1] = "80px";
	        iArray[2][2] = 10;
	        iArray[2][3] = 0;

	        iArray[3] = new Array();
	        iArray[3][0] = "BusinessTable";
	        iArray[3][1] = "100px";
	        iArray[3][2] = 10;
	        iArray[3][3] = 3;

	        iArray[4] = new Array();
	        iArray[4][0] = "业务表";
	        iArray[4][1] = "80px";
	        iArray[4][2] = 10;
	        iArray[4][3] = 0;

	        iArray[5] = new Array();
	        iArray[5][0] = "字段";
	        iArray[5][1] = "80px";
	        iArray[5][2] = 10;
	        iArray[5][3] = 0;

	        iArray[6] = new Array();
	        iArray[6][0] = "字段名";
	        iArray[6][1] = "80px";
	        iArray[6][2] = 10;
	        iArray[6][3] = 0;

	        iArray[7] = new Array();
	        iArray[7][0] = "列宽";
	        iArray[7][1] = "80px";
	        iArray[7][2] = 10;
	        iArray[7][3] = 0;

	        iArray[8] = new Array();
	        iArray[8][0] = "列序号";
	        iArray[8][1] = "80px";
	        iArray[8][2] = 10;
	        iArray[8][3] = 0;
	        
	        iArray[9] = new Array();
	        iArray[9][0] = "IsShow";
	        iArray[9][1] = "80px";
	        iArray[9][2] = 10;
	        iArray[9][3] = 3;
	        
	        iArray[10] = new Array();
	        iArray[10][0] = "是否显示";
	        iArray[10][1] = "80px";
	        iArray[10][2] = 10;
	        iArray[10][3] = 0;

	        ToBusiTableGrid = new MulLineEnter("fm", "ToBusiTableGrid");
	        //这些属性必须在loadMulLine前
	        ToBusiTableGrid.mulLineCount = 5;
	        ToBusiTableGrid.displayTitle = 1;
	        ToBusiTableGrid.hiddenPlus = 1;
	        ToBusiTableGrid.hiddenSubtraction = 1;
	        ToBusiTableGrid.canSel = 1;
	        ToBusiTableGrid.selBoxEventFuncName = "showData";
	        ToBusiTableGrid.loadMulLine(iArray);
	    }
	    catch(ex)
	    {
	        alert(ex);
	    } 
	
}
</script>