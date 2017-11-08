<%
//程序名称：ProposalApproveInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
    //添加页面控件的初始化。
%>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{

    try
    {
        // 保单查询条件
        document.all('ProposalNo').value = '';
        document.all('ManageCom').value = '';
        document.all('AgentCode').value = '';
        //document.all('AgentGroup').value = '';
    }
    catch(ex)
    {
        alert("在ProposalQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

// 下拉框的初始化
function initSelBox()
{
    try
    {
        //    setOption("t_sex","0=男&1=女&2=不详");
        //    setOption("sex","0=男&1=女&2=不详");
        //    setOption("reduce_flag","0=正常状态&1=减额交清");
        //    setOption("pad_flag","0=正常&1=垫交");
    }
    catch(ex)
    {
        alert("在ProposalQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();
        initUWUpReportPool();
        //initPolGrid();
        //initSelfPolGrid();

        //easyQueryClickSelf();

    }
    catch(re)
    {
        alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.name +":"+re.message);
    }
}

//add by lzf 2013-03-19
function initUWUpReportPool(){
	var config = {
			functionId : "10010040",
			//activityId : "0000001140",
			operator : Operator,
			public : {
				query : {
					queryButton : {
						"1" : {title : "申  请" , func : "ApplyUW"}
					},
					arrayInfo : {
						col : {
							newcol0 : {
								title : " 呈报日期 ",
								style : "8",
								colNo : 4,
								refercode1 : "makedate",
								addCondition : function(colName,value){
									return " and t.makedate = to_date('"+ value +"','yyyy-mm-dd')";
								}
							},
							result0 : {
								title : " 印刷号  ",
								verify : "印刷号|num&len=14",
								style :"1",
								colNo : 1
								},
							result1 : {
								title : "业务员编码",
								style : "2",
								colNo : 3,
								referfunc1 : "queryAgent",
								referpara1 : "[0],[3]"
								},
							result2 : {title : "代理人组别编码",style :"3"},
							result3 : {title : "代理人展业机构编码",style :"3"},
							result4 : {
								title : " 管理机构",
								colNo : 2,
								style : "2",
								refercode1 : "station",
								colName : "MissionProp6",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								   }
								}
			
						}
					}
				},
				resultTitle : "共享公共池",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : false,
						"4" : true,
						"5" : "and managecom like'"
							+ ComCode
							+ "%'  order by contno"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newcol0 : {
								title : " 呈报日期 ",
								style : "1",
								colNo : 3,
								colName : "makedate",
								rename : "makedate"
							},
							newcol1 : {
								title : "核保员代码",
								style : "1",
								colNo : 4,
								colName : "(select usercode from LCReinsureReport where contno=t.missionprop1)",
								rename : "usercode"
							},
							newcol2 : {
								title : " 被保人姓名",
								style : "1",
								colNo : 6,
								colName : "(select insuredname from lccont where contno = t.missionprop1)",
								rename : "insuredname"
							},
							newcol3 : {
								title : " 扫描日期 ",
								style : "1",
								colNo : 7,
								colName : "(select (case min(c.makedate) when null then '无扫描件' else to_char(min(c.makedate),'yyyy-mm-dd') end) from es_doc_main c where c.doccode= t.missionprop1)",
								rename : "scandate"
							},
							result0 : {
								title : " 印刷号  ",
								verify : "印刷号|num&len=14",
								style :"1",
								colNo : 1
								},
							result1 : {
								title : " 业务员编码",
								style : "1",
								colNo : 2
								},
							result2 : {title : "代理人组别编码",style :"3"},
							result3 : {title : "代理人展业机构编码",style :"3"},
							result4 : {
								title : " 管理机构",
								colNo : 5
								}
						}
					}
					
				}
			},
			private : {
				query : {
					arrayInfo : {
						col : {
							newcol0 : {
								title : " 呈报日期   ",
								style : "8",
								colNo : 4,
								refercode1 : "makedate",
								addCondition : function(colName,value){
									return " and t.makedate = to_date('"+ value +"','yyyy-mm-dd')";
								}
							},
							result0 : {
								title : "  印刷号   ",
								verify : "印刷号|num&len=14",
								style :"1",
								colNo : 1
								},
							result1 : {
								title : " 业务员编码 ",
								style : "2",
								colNo : 3,
								referfunc1 : "queryAgent",
								referpara1 : "[0],[3]"
								},
							result2 : {title : "代理人组别编码",style :"3"},
							result3 : {title : "代理人展业机构编码",style :"3"},
							result4 : {
								title : " 管理机构  ",
								colNo : 2,
								style : "2",
								refercode1 : "station",
								colName : "MissionProp6",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								   }
								}
						}
					}
				},
				resultTitle : "待处理保单",
				result : {
					selBoxEventFuncName:"InitshowApproveDetail",
					selBoxEventFuncParam:"",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : "and managecom like'"
							+ ComCode
							+ "%'  order by contno"
					},
					mulLineCount : 10,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newcol0 : {
								title : " 呈报日期 ",
								style : "1",
								colNo : 3,
								colName : "makedate",
								rename : "makedate"
							},
							newcol1 : {
								title : "核保员代码",
								style : "1",
								colNo : 4,
								colName : "(select usercode from LCReinsureReport where contno=t.missionprop1)",
								rename : "usercode"
							},
							newcol2 : {
								title : " 被保人姓名",
								style : "1",
								colNo : 6,
								colName : "(select insuredname from lccont where contno = t.missionprop1)",
								rename : "insuredname"
							},
							newcol3 : {
								title : " 扫描日期  ",
								style : "1",
								colNo : 7,
								colName : "(select (case min(c.makedate) when null then '无扫描件' else to_char(min(c.makedate),'yyyy-mm-dd') end) from es_doc_main c where c.doccode= t.missionprop1)",
								rename : "scandate"
							},
							result0 : {
								title : "  印刷号   ",
								verify : "印刷号|num&len=14",
								style :"1",
								colNo : 1
								},
							result1 : {
								title : " 业务员编码",
								style : "1",
								colNo : 2
								},
							result2 : {title : "代理人组别编码",style :"3"},
							result3 : {title : "代理人展业机构编码",style :"3"},
							result4 : {
								title : " 管理机构 ",
								colNo : 5
								}
						}
					}
				}
				
			}
	};
	jQuery("#UWUpReportPool").workpool(config);
	jQuery("#privateSearch").click();
}
//end by lzf
// 保单信息列表的初始化
/*function initPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";
        //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";
        //列宽
        iArray[0][2] = 10;
        //列最大值
        iArray[0][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[1] = new Array();
        iArray[1][0] = "印刷号";
        //列名
        iArray[1][1] = "150px";
        //列宽
        iArray[1][2] = 100;
        //列最大值
        iArray[1][3] = 0;
        //是否允许输入,1表示允许，0表示不允许


        iArray[2] = new Array();
        iArray[2][0] = "被调查人客户号码";
        //列名
        iArray[2][1] = "80px";
        //列宽
        iArray[2][2] = 100;
        //列最大值
        iArray[2][3] = 3;
        //是否允许输入,1表示允许，0表示不允许

        iArray[3] = new Array();
        iArray[3][0] = "业务员编码";
        //列名
        iArray[3][1] = "80px";
        //列宽
        iArray[3][2] = 100;
        //列最大值
        iArray[3][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[4] = new Array();
        iArray[4][0] = "呈报日期";
        //列名
        iArray[4][1] = "80px";
        //列宽
        iArray[4][2] = 100;
        //列最大值
        iArray[4][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[5] = new Array();
        iArray[5][0] = "工作流任务号";
        //列名
        iArray[5][1] = "0px";
        //列宽
        iArray[5][2] = 200;
        //列最大值
        iArray[5][3] = 3;
        //是否允许输入,1表示允许，0表示不允许

        iArray[6] = new Array();
        iArray[6][0] = "工作流子任务号";
        //列名
        iArray[6][1] = "0px";
        //列宽
        iArray[6][2] = 200;
        //列最大值
        iArray[6][3] = 3;
        //是否允许输入,1表示允许，0表示不允许

        iArray[7] = new Array();
        iArray[7][0] = "工作流活动Id";
        //列名
        iArray[7][1] = "0px";
        //列宽
        iArray[7][2] = 60;
        //列最大值
        iArray[7][3] = 3;
        //是否允许输入,1表示允许，0表示不允许

        iArray[8] = new Array();
        iArray[8][0] = "核保员代码";
        //列名
        iArray[8][1] = "100px";
        //列宽
        iArray[8][2] = 60;
        //列最大值
        iArray[8][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[9] = new Array();
        iArray[9][0] = "管理机构";
        //列名
        iArray[9][1] = "60px";
        //列宽
        iArray[9][2] = 60;
        //列最大值
        iArray[9][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[10] = new Array();
        iArray[10][0] = "被保人姓名";
        //列名
        iArray[10][1] = "60px";
        //列宽
        iArray[10][2] = 60;
        //列最大值
        iArray[10][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[11] = new Array();
        iArray[11][0] = "主险代码";
        //列名
        iArray[11][1] = "0px";
        //列宽
        iArray[11][2] = 60;
        //列最大值
        iArray[11][3] = 0;
        //是否允许输入,1表示允许，0表示不允许
        
        iArray[12] = new Array();
        iArray[12][0] = "扫描日期";
        //列名
        iArray[12][1] = "80px";
        //列宽
        iArray[12][2] = 100;
        //列最大值
        iArray[12][3] = 0;
        //是否允许输入,1表示允许，0表示不允许
        
        iArray[13] = new Array();
        iArray[13][0] = "投保单号";
        //列名
        iArray[13][1] = "0px";
        //列宽
        iArray[13][2] = 100;
        //列最大值
        iArray[13][3] = 0;
        //是否允许输入,1表示允许，0表示不允许


        PolGrid = new MulLineEnter("fm", "PolGrid");
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 5;
        PolGrid.displayTitle = 1;
        PolGrid.locked = 1;
        PolGrid.canSel = 1;
        PolGrid.hiddenPlus = 1;
        PolGrid.hiddenSubtraction = 1;
        PolGrid.loadMulLine(iArray);

        //这些操作必须在loadMulLine后面
        //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 保单信息列表的初始化
function initSelfPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";
        //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";
        //列宽
        iArray[0][2] = 10;
        //列最大值
        iArray[0][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[1] = new Array();
        iArray[1][0] = "印刷号";
        //列名
        iArray[1][1] = "150px";
        //列宽
        iArray[1][2] = 100;
        //列最大值
        iArray[1][3] = 0;
        //是否允许输入,1表示允许，0表示不允许


        iArray[2] = new Array();
        iArray[2][0] = "被调查人客户号码";
        //列名
        iArray[2][1] = "80px";
        //列宽
        iArray[2][2] = 100;
        //列最大值
        iArray[2][3] = 3;
        //是否允许输入,1表示允许，0表示不允许

        iArray[3] = new Array();
        iArray[3][0] = "业务员编码";
        //列名
        iArray[3][1] = "80px";
        //列宽
        iArray[3][2] = 100;
        //列最大值
        iArray[3][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[4] = new Array();
        iArray[4][0] = "呈报日期";
        //列名
        iArray[4][1] = "80px";
        //列宽
        iArray[4][2] = 100;
        //列最大值
        iArray[4][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[5] = new Array();
        iArray[5][0] = "工作流任务号";
        //列名
        iArray[5][1] = "0px";
        //列宽
        iArray[5][2] = 200;
        //列最大值
        iArray[5][3] = 3;
        //是否允许输入,1表示允许，0表示不允许

        iArray[6] = new Array();
        iArray[6][0] = "工作流子任务号";
        //列名
        iArray[6][1] = "0px";
        //列宽
        iArray[6][2] = 200;
        //列最大值
        iArray[6][3] = 3;
        //是否允许输入,1表示允许，0表示不允许

        iArray[7] = new Array();
        iArray[7][0] = "工作流活动Id";
        //列名
        iArray[7][1] = "0px";
        //列宽
        iArray[7][2] = 60;
        //列最大值
        iArray[7][3] = 3;
        //是否允许输入,1表示允许，0表示不允许

        iArray[8] = new Array();
        iArray[8][0] = "核保员代码";
        //列名
        iArray[8][1] = "100px";
        //列宽
        iArray[8][2] = 60;
        //列最大值
        iArray[8][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[9] = new Array();
        iArray[9][0] = "管理机构";
        //列名
        iArray[9][1] = "60px";
        //列宽
        iArray[9][2] = 60;
        //列最大值
        iArray[9][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[10] = new Array();
        iArray[10][0] = "被保人姓名";
        //列名
        iArray[10][1] = "60px";
        //列宽
        iArray[10][2] = 60;
        //列最大值
        iArray[10][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[11] = new Array();
        iArray[11][0] = "主险代码";
        //列名
        iArray[11][1] = "0px";
        //列宽
        iArray[11][2] = 60;
        //列最大值
        iArray[11][3] = 0;
        //是否允许输入,1表示允许，0表示不允许
        
        iArray[12] = new Array();
        iArray[12][0] = "扫描日期";
        //列名
        iArray[12][1] = "80px";
        //列宽
        iArray[12][2] = 100;
        //列最大值
        iArray[12][3] = 0;
        //是否允许输入,1表示允许，0表示不允许
        
        iArray[13] = new Array();
        iArray[13][0] = "投保单号";
        //列名
        iArray[13][1] = "0px";
        //列宽
        iArray[13][2] = 100;
        //列最大值
        iArray[13][3] = 0;
        //是否允许输入,1表示允许，0表示不允许
        


        SelfPolGrid = new MulLineEnter("fm", "SelfPolGrid");
        //这些属性必须在loadMulLine前
        SelfPolGrid.mulLineCount = 5;
        SelfPolGrid.displayTitle = 1;
        SelfPolGrid.locked = 1;
        SelfPolGrid.canSel = 1;
        SelfPolGrid.hiddenPlus = 1;
        SelfPolGrid.hiddenSubtraction = 1;
        SelfPolGrid.selBoxEventFuncName = "InitshowApproveDetail";

        SelfPolGrid.loadMulLine(iArray);

        //这些操作必须在loadMulLine后面
        //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
// 被保人信息列表的初始化
function initSubInsuredGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";
        //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";
        //列宽
        iArray[0][2] = 10;
        //列最大值
        iArray[0][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[1] = new Array();
        iArray[1][0] = "连带被保人客户号";
        //列名
        iArray[1][1] = "180px";
        //列宽
        iArray[1][2] = 100;
        //列最大值
        iArray[1][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[1][7] = "showSubInsured";
        iArray[1][8] = "['SubInsured']";
        //是否使用自己编写的函数

        iArray[2] = new Array();
        iArray[2][0] = "姓名";
        //列名
        iArray[2][1] = "160px";
        //列宽
        iArray[2][2] = 100;
        //列最大值
        iArray[2][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[3] = new Array();
        iArray[3][0] = "性别";
        //列名
        iArray[3][1] = "140px";
        //列宽
        iArray[3][2] = 60;
        //列最大值
        iArray[3][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[4] = new Array();
        iArray[4][0] = "出生日期";
        //列名
        iArray[4][1] = "140px";
        //列宽
        iArray[4][2] = 100;
        //列最大值
        iArray[4][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[5] = new Array();
        iArray[5][0] = "与被保人关系";
        //列名
        iArray[5][1] = "160px";
        //列宽
        iArray[5][2] = 100;
        //列最大值
        iArray[5][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[5][4] = "Relation";

        SubInsuredGrid = new MulLineEnter("document", "SubInsuredGrid");
        //这些属性必须在loadMulLine前
        SubInsuredGrid.mulLineCount = 5;
        SubInsuredGrid.displayTitle = 1;
        //SubInsuredGrid.tableWidth = 200;
        SubInsuredGrid.loadMulLine(iArray);

        //这些操作必须在loadMulLine后面
        //SubInsuredGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 受益人信息列表的初始化
function initBnfGrid() {
    var iArray = new Array();

    try {
        iArray[0] = new Array();
        iArray[0][0] = "序号";
        //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";
        //列宽
        iArray[0][2] = 10;
        //列最大值
        iArray[0][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[1] = new Array();
        iArray[1][0] = "受益人类别";
        //列名
        iArray[1][1] = "80px";
        //列宽
        iArray[1][2] = 40;
        //列最大值
        iArray[1][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[1][4] = "BnfType";
        iArray[1][9] = "受益人类别|notnull&code:BnfType";

        iArray[2] = new Array();
        iArray[2][0] = "受益人姓名";
        //列名
        iArray[2][1] = "80px";
        //列宽
        iArray[2][2] = 40;
        //列最大值
        iArray[2][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[2][9] = "受益人姓名|len<=20";
        //校验

        iArray[3] = new Array();
        iArray[3][0] = "性别";
        //列名
        iArray[3][1] = "30px";
        //列宽
        iArray[3][2] = 100;
        //列最大值
        iArray[3][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[3][4] = "sex";
        iArray[3][9] = "性别|code:sex";
        //校验

        iArray[4] = new Array();
        iArray[4][0] = "证件类型";
        //列名
        iArray[4][1] = "60px";
        //列宽
        iArray[4][2] = 80;
        //列最大值
        iArray[4][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[4][4] = "IDType";
        iArray[4][9] = "证件类型|code:IDType";

        iArray[5] = new Array();
        iArray[5][0] = "证件号码";
        //列名
        iArray[5][1] = "150px";
        //列宽
        iArray[5][2] = 80;
        //列最大值
        iArray[5][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[5][9] = "证件号码|len<=20";

        iArray[6] = new Array();
        iArray[6][0] = "出生日期";
        //列名
        iArray[6][1] = "80px";
        //列宽
        iArray[6][2] = 80;
        //列最大值
        iArray[6][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[6][9] = "出生日期|date";


        iArray[7] = new Array();
        iArray[7][0] = "与被保人关系";
        //列名
        iArray[7][1] = "90px";
        //列宽
        iArray[7][2] = 100;
        //列最大值
        iArray[7][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[7][4] = "Relation";
        iArray[7][9] = "与被保人关系|code:Relation";

        iArray[8] = new Array();
        iArray[8][0] = "受益比例";
        //列名
        iArray[8][1] = "60px";
        //列宽
        iArray[8][2] = 80;
        //列最大值
        iArray[8][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[8][9] = "受益比例|num&len<=10";

        iArray[9] = new Array();
        iArray[9][0] = "受益顺序";
        //列名
        iArray[9][1] = "60px";
        //列宽
        iArray[9][2] = 80;
        //列最大值
        iArray[9][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[9][4] = "OccupationType";
        iArray[9][9] = "受益顺序|code:OccupationType";

        iArray[10] = new Array();
        iArray[10][0] = "联系地址";
        //列名
        iArray[10][1] = "400px";
        //列宽
        iArray[10][2] = 240;
        //列最大值
        iArray[10][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[10][9] = "通讯地址|len<=80";

        iArray[11] = new Array();
        iArray[11][0] = "邮编";
        //列名
        iArray[11][1] = "60px";
        //列宽
        iArray[11][2] = 80;
        //列最大值
        iArray[11][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[11][9] = "邮编|zipcode";

        iArray[12] = new Array();
        iArray[12][0] = "电话";
        //列名
        iArray[12][1] = "100px";
        //列宽
        iArray[12][2] = 80;
        //列最大值
        iArray[12][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[12][9] = "电话|len<=18";

        BnfGrid = new MulLineEnter("document", "BnfGrid");
        //这些属性必须在loadMulLine前
        BnfGrid.mulLineCount = 5;
        BnfGrid.displayTitle = 1;
        BnfGrid.loadMulLine(iArray);

        //这些操作必须在loadMulLine后面

    } catch(ex) {
        alert(ex);
    }
}

// 告知信息列表的初始化
function initImpartGrid() {
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";
        //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";
        //列宽
        iArray[0][2] = 10;
        //列最大值
        iArray[0][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[1] = new Array();
        iArray[1][0] = "告知客户类型";
        //列名
        iArray[1][1] = "90px";
        //列宽
        iArray[1][2] = 100;
        //列最大值
        iArray[1][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[1][4] = "CustomerType";
        iArray[1][9] = "告知客户类型|len<=18";

        iArray[2] = new Array();
        iArray[2][0] = "告知编码";
        //列名
        iArray[2][1] = "60px";
        //列宽
        iArray[2][2] = 100;
        //列最大值
        iArray[2][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[2][4] = "ImpartCode";
        iArray[2][9] = "告知编码|len<=4";

        iArray[3] = new Array();
        iArray[3][0] = "告知版别";
        //列名
        iArray[3][1] = "60px";
        //列宽
        iArray[3][2] = 200;
        //列最大值
        iArray[3][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[3][4] = "ImpartVer";
        iArray[3][9] = "告知版别|len<=5";

        iArray[4] = new Array();
        iArray[4][0] = "告知内容";
        //列名
        iArray[4][1] = "360px";
        //列宽
        iArray[4][2] = 200;
        //列最大值
        iArray[4][3] = 1;
        //是否允许输入,1表示允许，0表示不允许

        iArray[5] = new Array();
        iArray[5][0] = "填写内容";
        //列名
        iArray[5][1] = "200px";
        //列宽
        iArray[5][2] = 200;
        //列最大值
        iArray[5][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[5][9] = "填写内容|len<=200";

        ImpartGrid = new MulLineEnter("document", "ImpartGrid");
        //这些属性必须在loadMulLine前
        ImpartGrid.mulLineCount = 5;
        ImpartGrid.displayTitle = 1;
        //ImpartGrid.tableWidth   ="500px";
        ImpartGrid.loadMulLine(iArray);

        //这些操作必须在loadMulLine后面
        //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 特别约定信息列表的初始化
function initSpecGrid() {
    var iArray = new Array();
    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";
        //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";
        //列宽
        iArray[0][2] = 10;
        //列最大值
        iArray[0][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[1] = new Array();
        iArray[1][0] = "特约类型";
        //列名
        iArray[1][1] = "60px";
        //列宽
        iArray[1][2] = 100;
        //列最大值
        iArray[1][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[1][4] = "SpecType";
        iArray[1][9] = "特约类型|len<=5";

        iArray[2] = new Array();
        iArray[2][0] = "特约编码";
        //列名
        iArray[2][1] = "60px";
        //列宽
        iArray[2][2] = 100;
        //列最大值
        iArray[2][3] = 2;
        //是否允许输入,1表示允许，0表示不允许
        iArray[2][4] = "SpecCode";
        iArray[2][9] = "特约编码|len<=5";

        iArray[3] = new Array();
        iArray[3][0] = "特约内容";
        //列名
        iArray[3][1] = "540px";
        //列宽
        iArray[3][2] = 200;
        //列最大值
        iArray[3][3] = 1;
        //是否允许输入,1表示允许，0表示不允许
        iArray[3][9] = "特约内容|len<=255";

        SpecGrid = new MulLineEnter("document", "SpecGrid");
        //这些属性必须在loadMulLine前
        SpecGrid.mulLineCount = 5;
        SpecGrid.displayTitle = 1;
        SpecGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面
        //SpecGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}

//责任列表
function initDutyGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";
        //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";
        //列宽
        iArray[0][2] = 10;
        //列最大值
        iArray[0][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[1] = new Array();
        iArray[1][0] = "责任编码";
        //列名
        iArray[1][1] = "100px";
        //列宽
        iArray[1][2] = 100;
        //列最大值
        iArray[1][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[2] = new Array();
        iArray[2][0] = "责任名称";
        //列名
        iArray[2][1] = "160px";
        //列宽
        iArray[2][2] = 100;
        //列最大值
        iArray[2][3] = 0;
        //是否允许输入,1表示允许，0表示不允许

        iArray[3] = new Array();
        iArray[3][0] = "保费";
        //列名
        iArray[3][1] = "80px";
        //列宽
        iArray[3][2] = 100;
        //列最大值
        iArray[3][3] = 1;
        //是否允许输入,1表示允许，0表示不允许

        iArray[4] = new Array();
        iArray[4][0] = "保额";
        //列名
        iArray[4][1] = "80px";
        //列宽
        iArray[4][2] = 100;
        //列最大值
        iArray[4][3] = 1;
        //是否允许输入,1表示允许，0表示不允许

        iArray[5] = new Array();
        iArray[5][0] = "交费年期";
        //列名
        iArray[5][1] = "80px";
        //列宽
        iArray[5][2] = 100;
        //列最大值
        iArray[5][3] = 1;
        //是否允许输入,1表示允许，0表示不允许

        iArray[6] = new Array();
        iArray[6][0] = "领取年期";
        //列名
        iArray[6][1] = "80px";
        //列宽
        iArray[6][2] = 100;
        //列最大值
        iArray[6][3] = 1;
        //是否允许输入,1表示允许，0表示不允许

        DutyGrid = new MulLineEnter("document", "DutyGrid");
        //这些属性必须在loadMulLine前
        DutyGrid.mulLineCount = 5;
        DutyGrid.displayTitle = 1;
        DutyGrid.canChk = 1;
        DutyGrid.loadMulLine(iArray);

        //这些操作必须在loadMulLine后面
        //DutyGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}


function emptyForm() {
    //emptyFormElements();     //清空页面所有输入框，在COMMON。JS中实现

    initSubInsuredGrid();
    initBnfGrid();
    initImpartGrid();
    initSpecGrid();
    initDutyGrid();

    //SubInsuredGrid.clearData();
    //BnfGrid.clearData();
    //ImpartGrid.clearData();
    //SpecGrid.clearData();
    //DutyGrid.clearData();
    spanDutyGrid.innerHTML = "";
}


</script>
