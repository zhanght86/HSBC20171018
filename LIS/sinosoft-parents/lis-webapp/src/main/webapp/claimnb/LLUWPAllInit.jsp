<%
//程序名称：UWNoticeInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//添加页面控件的初始化。
GlobalInput globalInput = (GlobalInput)session.getValue("GI");

if(globalInput == null) {
	out.println("session has expired");
	return;
}

String strOperator = globalInput.Operator;
String strManageCom = globalInput.ComCode;
%>
<script language="JavaScript">
function initInpBox()
{
	try
	{
		document.all('ContNo').value = '';
		document.all('ManageCom').value = <%=strManageCom%>;
	}
	catch(ex)
	{
		alert("在LLUWPAllInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initPolGrid();
		//initUWPAllPool()
	}
	catch(re)
	{
		alert("在LLUWPAllInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.name +":"+re.message);
	}
}
/**
function initUWPAllPool(){
	var config = {
			//activityId : "0000005551",
			functionId : "10030029",
			//operator : operator,
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "印刷号" , style : 3},
							result1 : {title : "   保单号码 " , style : 1,colNo : 1},
							result2 : {title : "通知书流水号" , style : 3},
							result3 : {title : "代理人编码" , style : 3},
							result4 : {title : "通知书代码" , style : 3},
							result5 : {
								title : "   管理机构  " , 
								style : 2,
								colNo : 2,
								refercode1 : "station",
							    addCondition : function(colName ,value){
							    	return " and t.missionprop7 like '"+value+"%'";
							    }	
							},
							result6 : {title : "赔案号" , style : 3},
							result7 : {title : "批次号" , style : 3},
							result8 : {title : "客户号" , style : 3},
							result9 : {title : "客户类型" , style : 3},
							result10 : {title : "合同销售渠道" , style : 3},
							result11 : {title : "旧体检通知书流水号" , style : 3}
						}
					}
				},
				resultTitle : "投保单信息",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and code like 'LP%' and code ='LP90'"
					},
					mulLineCount :  10,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {title : "印刷号" , style : 3},
							result1 : {title : "  保单号码   " , style : 1,colNo : 2},
							result2 : {title : "  流水号  " , style : 1,colNo : 1},
							result3 : {title : " 代理人编码 " , style : 1,colNo : 3},
							result4 : {title : "通知书代码" , style : 3},
							result5 : {title : " 管理机构 " , style : 1,colNo : 4},
							result6 : {title : "赔案号" , style : 3},
							result7 : {title : "批次号" , style : 3},
							result8 : {title : "客户号" , style : 3},
							result9 : {title : "客户类型" , style : 3},
							result10 : {title : "合同销售渠道" , style : 3},
							result11 : {title : "旧体检通知书流水号" , style : 3}
						}
					}
				}
			},
			private : {
				show : false
			}
	};
	jQuery("#UWPAllPool").workpool(config);
}
*/
var PolGrid;          //定义为全局变量，提供给displayMultiline使用
// 投保单信息列表的初始化
function initPolGrid()
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
		iArray[1][0]="流水号";
		iArray[1][1]="180px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="保单号码";
		iArray[2][1]="160px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="营业员编码";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="通知书类型";
		iArray[4][1]="0px";
		iArray[4][2]=200;
		iArray[4][3]=0;
	    
		iArray[5]=new Array();
		iArray[5][0]="管理机构";
		iArray[5][1]="120px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="保单印刷号";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=3;
		
		iArray[7]=new Array();
		iArray[7][0]="工作流任务编码";
		iArray[7][1]="0px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="工作流子任务编码";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="OldPrtSeq";
		iArray[9][1]="0px";
		iArray[9][2]=100;
		iArray[9][3]=3;
	
		iArray[10]=new Array();
		iArray[10][0]="赔案号";
		iArray[10][1]="0px";
		iArray[10][2]=100;
		iArray[10][3]=0;
		
		iArray[11]=new Array();
		iArray[11][0]="批次号";
		iArray[11][1]="0px";
		iArray[11][2]=100;
		iArray[11][3]=0;

		PolGrid = new MulLineEnter( "fmSave" , "PolGrid" );
		//这些属性必须在loadMulLine前
		PolGrid.mulLineCount = 10;
		PolGrid.displayTitle = 1;
		PolGrid.canSel = 1;
		PolGrid.hiddenPlus=1;
		PolGrid.hiddenSubtraction=1;
		PolGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
