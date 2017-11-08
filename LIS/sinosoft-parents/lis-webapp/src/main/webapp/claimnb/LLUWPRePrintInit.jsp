<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：RePrintInit.jsp
//程序功能：
//创建日期：2003-03-31
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	String strManageCom = globalInput.ManageCom;
%>                            

<script language="JavaScript">
var strManageCom = <%=strManageCom%>;
//var PolGrid;          //定义为全局变量，提供给displayMultiline使用

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
      
}

function initForm()
{
  try
  {
  	//manageCom = '<%= strManageCom %>';
    initInpBox();
	 // initPolGrid();
	 initUWPRePrintPool();
  }
  catch(re)
  {
    alert("RePrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.name +":"+re.message);
  }
}

function initUWPRePrintPool(){
	var config = {
			//activityId : "0000005552",
			functionId : "10030030",
			//operator : operator,
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newCol0 : {
								title : "  起始日期",
								style : 8,
								colNo : 3,
								refercode1 : "startday",
								addCondition : function(colName ,value){
									return " and t.missionprop7 >= '"+value+"'";
								}
							},
							newCol1 : {
								title : "  结束日期",
								style : 8,
								colNo : 4,
								refercode1 : "endday",
								addCondition : function(colName ,value){
									return " and t.missionprop7 <= '"+value+"'";
								}
							},
							result0 : {title : "印刷号" , style : 3},
							result1 : {
								title : "   保单号码 " , 
								style : 1,
								colNo : 1,
								refercode1 : "contno",
								addCondition : function(colName,value){
									return " and t.missionprop2 like '"+value+"%'";
								}
								},
							result2 : {title : "核保通知书打印编码" , style : 3},
							result3 : {title : "管理机构" , style : 3},
							result4 : {title : "代理人编码" , style : 3},
							result5 : {title : "打印日期" , style : 3},
							result6 : {title : "打印人" , style : 3},
							result7 : {title : "赔案号" , style : 3},
							result8 : {title : "批次号" , style : 3},
							result9 : {title : "核保通知书流水号" , style : 3},
							result10 : {title : "核保通知书流水号" , style : 1,colNo : 2}
						}
					}
				},
				resultTitle : "补打通知书信息",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and ManageCom like '"+strManageCom+"%' and code ='LP90'"
					},
					mulLineCount : 10,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {title : "印刷号" , style : 3},
							result1 : {title : "   保单号码 " , style : 1,colNo : 3},
							result2 : {title : "打印类型" , style : 1,colNo : 2},
							result3 : {title : "管理机构" , style : 1,colNo : 4},
							result4 : {title : "代理人编码" , style : 1,colNo : 5},
							result5 : {title : "打印日期" , style : 3},
							result6 : {title : "打印人" , style : 3},
							result7 : {title : "赔案号" , style : 3},
							result8 : {title : "批次号" , style : 3},
							result9 : {title : "核保通知书流水号" , style : 3},
							result10 : {title : "通知书流水号" , style : 1,colNo : 1}
						}
					}
				}
			},
			private : {
				show : false
			}
	};
	jQuery("#UWPRePrintPool").workpool(config);
}

// 保单信息列表的初始化
/*function initPolGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="通知书流水号";         		//列名
	  iArray[1][1]="160px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[2]=new Array();
	  iArray[2][0]="打印类型";       		//列名
	  iArray[2][1]="100px";            	//列宽
	  iArray[2][2]=100;           			//列最大值
	  iArray[2][3]=0;
	  
	  iArray[3]=new Array();
	  iArray[3][0]="立案号";        //列名
	  iArray[3][1]="0px";            	//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许 
	  
	  iArray[4]=new Array();
	  iArray[4][0]="批次号";        //列名
	  iArray[4][1]="0px";            	//列宽
	  iArray[4][2]=100;            			//列最大值
	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许  
	
	  iArray[5]=new Array();
	  iArray[5][0]="保单号";       		//列名
	  iArray[5][1]="160px";            	//列宽
	  iArray[5][2]=100;            			//列最大值
	  iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[6]=new Array();
	  iArray[6][0]="管理机构";         	//列名
	  iArray[6][1]="80px";            	//列宽
	  iArray[6][2]=100;            			//列最大值
	  iArray[6][3]=0;
	
	  iArray[7]=new Array();
	  iArray[7][0]="代理人编码";        //列名
	  iArray[7][1]="100px";            	//列宽
	  iArray[7][2]=200;            			//列最大值
	  iArray[7][3]=0; 									//是否允许输入,1表示允许，0表示不允许    
	  
	  iArray[8]=new Array();
	  iArray[8][0]="missionid";         	//列名
	  iArray[8][1]="0px";            	//列宽
	  iArray[8][2]=100;            			//列最大值
	  iArray[8][3]=0;
	
	  iArray[9]=new Array();
	  iArray[9][0]="submissionid";        //列名
	  iArray[9][1]="0px";            	//列宽
	  iArray[9][2]=100;            			//列最大值
	  iArray[9][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[10]=new Array();
	  iArray[10][0]="activityid";        //列名
	  iArray[10][1]="0px";            	//列宽
	  iArray[10][2]=100;            			//列最大值
	  iArray[10][3]=0; 									//是否允许输入,1表示允许，0表示不允许 	 

	
	  PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
	  //这些属性必须在loadMulLine前
	  PolGrid.mulLineCount = 10;   
	  PolGrid.displayTitle = 1;
	  PolGrid.canSel = 1;
      PolGrid.locked = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
	  PolGrid.loadMulLine(iArray);  
	
	} catch(ex) {
		alert(ex);
	}
}
*/
</script>
