<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>                      

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

<script type="text/javascript">
	
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
		initMsgGrid(); 
	}
	catch(re)
	{
		myAlert("发生异常:初始化界面错误!");
	}
}

// 保单卡号信息的查询
function initMsgGrid()
{                               
	var iArray = new Array();
	
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="<%=bundle.getString("M0000047081")%>";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=30;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		
		iArray[1]=new Array();
		iArray[1][0]="信息号";         		//列名
		iArray[1][1]="80px";            	//列宽
		iArray[1][2]=100;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		
		iArray[2]=new Array();
		iArray[2][0]="<%=bundle.getString("ldcode_language_zh")%>";         		//列名
		iArray[2][1]="80px";            		//列宽
		iArray[2][2]=100;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许            
		
	
		iArray[3]=new Array();                                                       
		iArray[3][0]="<%=bundle.getString("ldcode_language_ja")%>";         		//列名                                     
		iArray[3][1]="80px";            		//列宽                                   
		iArray[3][2]=100;            			//列最大值                                 
		iArray[3][3]=0;             			//是否允许输入,1表示允许，0表示不允许   
		
	  
		MsgGrid = new MulLineEnter( "fm" , "MsgGrid" ); 
		MsgGrid.mulLineCount =5;   
		MsgGrid.displayTitle = 1;
		MsgGrid.locked = 0;
	  MsgGrid.canChk = 0;
    MsgGrid.canSel = 1;
		MsgGrid.hiddenPlus = 1;
		MsgGrid.hiddenSubtraction = 1;
		MsgGrid.selBoxEventFuncName ="MsgGridClick";
		MsgGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		myAlert(ex);
	}
}
</script>