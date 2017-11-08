<%
//程序名称：ProposalApproveInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
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
    //document.all('ProposalNo').value = '';
    // document.all('ManageCom').value = '';
    //document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
  }
  catch(ex)
  {
    alert("在ProposalApproveInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在ProposalApproveInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
	initInpBox();  
	initSelBox();     
	initWorkPool();
	jQuery("#publicSearch").click();
	jQuery("#privateSearch").click();
  }
  catch(re)
  {
    alert("ProposalApproveInit.jsp-->InitForm函数中发生异常:初始化界面错误!错误信息是："+re.name + ": " + re.message);
  }
}

function initWorkPool(){
	var config = {
			//activityId : "0000001001",
			functionId : "10010003",
			operator : Operator,
			public : {
				query : {
					queryButton : {
					},
					arrayInfo : {
						col : {
							result0 : {	title : "合同号码",style : "3" },//blurfunc : "assignPrtNo",//blurpara : "0"},
							result1 : {	title :"   印刷号   ",	style : "1",colNo : "1"},
							result2 : {	title : "投保人号码",style : "3"},
							result3 : {	title: "   投保人   ",style : "3"},
							result4 : {	title: "录入员",	style : "3"},
							result5 : {	title:"录单日期",style : "8",colNo :3 },
							result6 : { title:"业务员编码",referfunc1 : "queryAgent",referpara1 : "[0],[4]",colNo : 4 },
							result7 : { title : "管理机构",	colNo : 2,	refercode1 : "station",addCondition : function(colName, value) {return " and " + colName + " like '" + value+ "%' ";}}, 
							newCol1 : { title :"销售渠道" ,colName : "missionprop1",	addCondition : function(colName, value) {
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
									};
									return " and " + colName + " like '" + value
											+ "%' ";
								},
								maxLength : 0,
								refercode1 : "polproperty",
								style : 2
							}
						}	 
					}
				},//end of arrayInfo
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : false,
						"4" : true
					},
					mulLineCount : 00,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "合同号码",
								style : 3
							},
							result1 : {
								title : "   印刷号   ",
								colNo : "1"
							},
							result2 : {
								title : "投保人号码",
								style : 3
							},
							result3 : {
								title : "   投保人   ",
								colNo : "2"
							},
							result4 : {
								title : "录入员",
								colNo : "3"
							},
							result5 : {
								title:"录单日期",
								style : "8",
								colNo : 4
							},
							result6:{
								title:"业务员编码",
								colNo : 6
							},
							result7:{
								title : "管理机构",
								colNo : 5,
								refercode1 : "station",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
							}//end of result7
						}//end of col
					}//end of arrayInfo
				}//end of result
			},//end of public
			private : {
				query : {
					queryButton : {
					},
					arrayInfo : {
						col : {
							result0 : {	title : "合同号码",style : "3" },//blurfunc : "assignPrtNo",//blurpara : "0"},
							result1 : {	title :"   印刷号   ",	style : "1",colNo : "1"},
							result2 : {	title : "投保人号码",style : 3},
							result3 : {	title: "   投保人   ",style : "3"},
							result4 : {	title: "录入员",	style : "3"},
							result5 : {	title:"录单日期",style : "8",colNo :3 },
							result6 : { title:"业务员编码",referfunc1 : "queryAgent",referpara1 : "1",colNo : 4 },
							result7 : { title : "管理机构",	colNo : 2,	refercode1 : "station",addCondition : function(colName, value) {return " and " + colName + " like '" + value+ "%' ";}}, 
							newCol1 : { title :"销售渠道" ,colName : "missionprop1",	addCondition : function(colName, value) {
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
									};
									return " and " + colName + " like '" + value
											+ "%' ";
								},
								maxLength : 0,
								refercode1 : "polproperty",
								style : 2
							}
						}	 
					}
				},//end of arrayInfo
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false
					},
					mulLineCount : 00,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "合同号码",
								style : 3
							},
							result1 : {
								title : "   印刷号   ",
								colNo : "1"
							},
							result2 : {
								title : "投保人号码",
								style : 3
							},
							result3 : {
								title : "   投保人   ",
								colNo : "2"
							},
							result4 : {
								title : "录入员",
								colNo : "3"
							},
							result5 : {
								title:"录单日期",
								style : "0",
								colNo : 4
							},
							result6:{
								title:"业务员编码",
								colNo : 6
							},
							result7:{
								title : "管理机构",
								colNo : 5,
								refercode1 : "station",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
							}
						}
					}
				}
			},
			midContent :{//the content between two pools
				id : "MidContent",
				show : true,
				html : "<INPUT class=cssButton id=riskbutton VALUE='申  请' TYPE=button onClick=ApplyUW()>"
			}
		};//end of config
		jQuery("#WorkPool").workpool(config);
}




</script>
