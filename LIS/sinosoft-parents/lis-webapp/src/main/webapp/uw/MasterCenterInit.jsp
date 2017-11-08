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
       String Operator = tGlobalInput.Operator;
       String ComCode = tGlobalInput.ComCode;
%>                            

<script language="JavaScript">

//初始化页面传递过来的参数
function initParam()
{
   document.all('Operator').value = nullToEmpty("<%= Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= ComCode %>");
    
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

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
	// 保单查询条件
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    //document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
    
  }
  catch(ex)
  {
  	alert("第一个函数");
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
  	alert("第二个函数");
    alert("在ProposalApproveInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                       

function initForm()
{
  try
  {
    initParam();
	initInpBox();  
	initSelBox();  
	initManagePublicPool();
	initManagePrivatePool();
	//initPolGrid();
	//initSelfPolGrid();	
	//easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalApproveInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+ re.name + ":" + re.message);
  }
}
 
//add by lzf 2013-03-14
function initManagePublicPool() {	
		var config = {		
		functionId : "10010010",
		operator : Operator,
		public : {
			id : "ManPublicPool",
			show : true,
			query : {
				queryButton : {
					"1" : {
						title : "申  请",
						func : "ApplyUW"
					}
				},
				arrayInfo : {
					col : {
						newcol0 : {
							title : "管理机构",
							style : "2",
							refercode1 : "station",
							colName : "operatecom",
							addCondition : function(colName, value) {
								return " and "+ colName +" like '" + value
										+ "%' ";
							}
						},
						result1 : {
							title : "印刷号",
							verify : "印刷号|num&len=14",
							colNo : 2,
							style : "1"
						},						
						result2 : {
							title : "投保人",
						    style : "3",
						    colNo : 3
						},
						result0 : {
							title : "合同号",
							style : "3",
							colNo : 1
						},
						result3 : {
							title : "问题件批次号",
						    style : "3",
						    colNo : 4 
						    }
					}
				}
			},
			resultTitle : "共享工作池",
			result : {				
				condition : {
					"0" : false,
					"1" : false,
					"2" : false,
					"3" : false,
					"4" : true
				},
				mulLineCount : 0,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",
						result1 : {
							title : "印刷号",
							verify : "印刷号|num&len=14",
							colNo : 2,
							style : "0"
						},					
						result2 : {
							title : "投保人",
						    style : "0",
						    colNo : 3
						},
						result0 : {
							title : "合同号",
							style : "3",
							colNo : 1
						},
						result3 : {
							title : "问题件批次号",
						    style : "3",
						    colNo : 4 
						}
					}
				}
			}
		},
		private : {	
			show : false			
		}
	};
	jQuery("#ManPublicPool").workpool(config);

}

function initManagePrivatePool(){
	var config = {		
			functionId : "10010010",
			operator : Operator,
			public : {
               show : false
			},
			private : {
				id : "ManPrivatePool",		
				show : true,
				query : {
					queryButton : {
						
					},
					arrayInfo : {
						col : {	
							newcol0 : {
								title : "管理机构",	
								style : "2",
								refercode1 : "station",
								colName : "operatecom",
								addCondition : function(colName, value) {
									return " and "+ colName +" like '" + value
											+ "%' ";
								}
							},
							result1 : {
								title : "印刷号",
								verify : "印刷号|num&len=14",
								colNo : 2,
								style : "1"
							},						
							result0 : {
								title : "合同号",
								verify : "合同号|num&len=14",
								colNo : 1,
								style : "3"
							},
							result2 : {
								title : "投保人",
								colNo : 3,
								style : "3"
							},
							result3 : {
								title : "问题件批次号",
								colNo : 4,
								style : "3"
							}
						}
					}
				},
				resultTitle : "待处理保单",
				result : {
					 selBoxEventFuncName : "QuestQuery",
					 selBoxEventFuncParam : "",					
					condition : {
						"0" : false,
						"1" : false,
						"2" : false
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result1 : {
								title : "印刷号",
								colNo : 2,
								style : "0"
							},						
							result2 : {
								title : "投保人",
								colNo : 3,
								style : "0"
							},
							result0 : {
								title : "合同号",
								colNo : 1,
								style : "3"
							},
							result3 : {
								title : "问题件批次号",
								colNo : 4,
								style : "3"
							}
						}
					}				
				}
			}
		};
		jQuery("#ManPrivatePool").workpool(config);
		jQuery("#privateSearch").click();
}
// 保单信息列表的初始化
/**function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="50px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保人";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="工作流任务号";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="工作流子任务号";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[5]=new Array();
      iArray[5][0]="工作流活动Id";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
      	alert("第三个函数");
        alert(ex);
      }
}
*/
// 个人保单信息列表的初始化
/**function initSelfPolGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="50px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保人";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="工作流任务号";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="工作流子任务号";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[5]=new Array();
      iArray[5][0]="工作流活动Id";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      SelfPolGrid = new MulLineEnter( "fm" , "SelfPolGrid" ); 
      //这些属性必须在loadMulLine前
      SelfPolGrid.mulLineCount = 0;   
      SelfPolGrid.displayTitle = 1;
      SelfPolGrid.locked = 1;
      SelfPolGrid.canSel = 1;
      SelfPolGrid.hiddenPlus = 1;
      SelfPolGrid.hiddenSubtraction = 1;
      SelfPolGrid.selBoxEventFuncName = "QuestQuery";    
      SelfPolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
*/



</script>
