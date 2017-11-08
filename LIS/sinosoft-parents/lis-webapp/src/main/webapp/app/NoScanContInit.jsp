<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ScanContInit.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{
	try
	{
		// 保单查询条件
		document.all('PrtNo').value = '';
	}
	catch(ex)
	{
		alert("在GroupUWAutoInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initSelfPool();
	}
	catch(re)
	{
		alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!错误信息是："+re.name + ": " + re.message);
	}
}

var config;
var fId;
if(type == "1"){//个单无扫
	fId = "10010001";
}else if(type == "2"){//团单无扫
	fId = "20010010";
}else if(type == "5"){//新契约初审确认节点的functionid暂时为空
	fId = "";
}
function initSelfPool() {
	config = {
		//activityId : "0000001098,0000001099",
		//activityId : aId,
		//functionId : "10010001",
		functionId : fId,
		operator : operator,
		public : {
			show : false
		},
		private : {
			id : "SelfPool",
			query : {
				queryButton : {
					"1" : {
						title : "申  请",
						func : "ApplyInput"
					}
				},
				arrayInfo : {
					col : {
						newcol0 : {
							title : "保单性质",
							colName : "missionprop1",
							addCondition : function(colName, value) {
								switch (value) {
								case "11":
									value = "8611";
									break;
								case "16":
									value = "8691";
									break;
								case "21":
									value = "8671";
									break;
								case "35":
									value = "8635";
									break;
								}
								;
								return " and " + colName + " like '" + value
										+ "%' ";
							},
							maxLength : 10,
							refercode1 : "polproperty",
							style : 2
						},
						result0 : {
							title : "投保单号",
							verify : "投保单号|num&len=14",
							colNo : 2,
							style : "1",
							blurfunc : "assignPrtNo",
							blurpara : "0"     //不知道这个参数有啥用
						},
						result1 : {
							title: "申请日期",
							style : 8,
							colNo : 3
						},
						result2 : {
							title : "管理机构",
							colNo : 1,
							refercode1 : "station",
							addCondition : function(colName, value) {
								return " and " + colName + " like '" + value
										+ "%' ";
							}
						},
						result3 : {
							style : 3
						},
						result4 : {
							style : 3,
							defaultvalue : "TB01"
						}
					}
				}
			},
			result : {
				condition : {
					"0" : false,
					"1" : false,
					"2" : false
				},
				mulLineCount : 10,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",
						result0 : {
							title : "投保单号"
						},
						result1 : {
							title : "申请日期",
							style : 0,
							colNo : 3
						},
						result2 : {
							title : "管理机构",
							colNo : 2
						},
						result4 : {
							style : "3"
						}
					}
				},
				condition : {
					"5" : "and managecom like'"
							+ comcode
							+ "%'  order by prtno"
				}
			}
		}
	};
	//config.activityId = "0000001061";
	//config = {functionId:"10010001",operator:operator};
	jQuery("#SelfPool").workpool(config);
}
</script>
