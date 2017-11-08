<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ScanContInit.jsp
//程序功能：个单新契约扫描件保单录入 
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
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
    document.all('PrtNo').value ="";
    document.all('ManageCom').value ="";
    document.all('InputDate').value ="";
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
    initScanPool();

    //initSelfGrpGrid();
   // initGrpGrid();
    //easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
//add by lzf
var config;
var fId;
if(type == "1"){//个单有扫
	fId = "0000001099";	
}else if(type == "2"){//团单有扫
	fId = "0000002099";
}

function initScanPool() {
	var config = {
		activityId : fId,
		//functionId : "10010002",
		operator :  operator,
		public : {			
			query : {	
				queryButton : {							
				},
				arrayInfo : {
					col : {		
						newCol0:{
							title : " 交费日期  ",					
							refercode1 : "paydate",
							addCondition : function(colName, value) {
								return " and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =t.missionprop1 and paydate=to_date('"
										+ value +"','yyyy-mm-dd'))";
										
							},
							style : "8"						
							},

						result0 : {
							title : " 投保单号  ",
							verify : "投保单号|num&len=14",
							colNo : 1,
							style : "1"
						},
						result2 : {
							title : " 管理机构  ",
							colNo : 2,
							refercode1 : "station",
							addCondition : function(colName, value) {
								return " and " + colName + " like '" + value
										+ "%' ";
							}
						},
						result1 : {
							title: " 扫描日期  ",
							style : "8",
							colNo : 3
						},
						
						result3 : {
							title: "  操  作  员    ",
							style : "3"
						},
						result4 : {
							title: "单证类型",
							style : 3
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
					"4" : true,
					"5" : "and managecom like'"
						+ comcode
						+ "%'  order by prtno"
				},
				mulLineCount : 0,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",
						result0 : {
							title : " 投保单号  ",
							colNo : 1,
							style : "1"
						},
						result2 : {
							title : " 管理机构  ",
							colNo : "2"
						},
						result1 : {
							title : " 扫描日期  ",
							style : "8",
							colNo : 3
						},
						
						result3 : {
							style : "3"
						},
						result4 : {							
							style : "3"
						}
					}
				}
			}
		},
		private :{
			query : {
				queryButton : {		
				},
				arrayInfo : {
					col : {		
						
						result0 : {
							title : " 投保单号  ",
							verify : "投保单号|num&len=14",
							colNo : 1,
							style : 1
						},
						result2 : {
							title : " 管理机构  ",
							colNo : 2,
							refercode1 : "station",
							addCondition : function(colName, value) {
								return " and " + colName + " like '" + value
										+ "%' ";
							}
						},
						result1 : {
							title: " 扫描日期 ",
							style : 8,
							colNo : 3
						},
						
						result3 : {
							title: "操作员",
							style : "3",
							colNo : 4
						},
						result4 : {
							title: "单证类型",
							style : 3,
							colNo : 5
						}
					}
				}
			},
			result : {
				 selBoxEventFuncName:"InitGoToInput",
				 selBoxEventFuncParam:"",
				condition : {
					"0" : false,
					"1" : false,
					"2" : false,
					"5" : "and managecom like'"
						+ comcode
						+ "%'  order by prtno"
				},
				mulLineCount : 10,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",
						result0 : {
							title : " 投保单号  ",
							colNo : 1
						},
						result2 : {
							title : " 管理机构  ",
							colNo : 2
						},
						result1 : {
							title : " 扫描日期  ",
							style : 8,
							colNo : 3
						},
						
						result3 : {
							title: "操作员",
							style : "3"
						},
						result4 : {
							title: "单证类型",  
							style : "3"
						}
					}
				}
			}						
        },
        midContent :{//the content between two pools
			id : "MidContent",
			show : true,
			html : "<a href='javascript:void(0)' class=button onclick='ApplyUW();'>申  请</a>"
		}
	};

	jQuery("#ScanPool").workpool(config);
	jQuery("#privateSearch").click();
}

/**function initScanPrivatePool() {
	var config = {
		activityId : "0000001099",
		//functionId : "10010002",
		operator : operator,
		public : {			
			show : false		
			},
		private : {
			id : "ScanPrivatePool",
			show : true,
			resultTitle : "扫描件信息",
			query : {
				arrayInfo : {
					col : {		
						
						result0 : {
							title : "投保单号",
							verify : "投保单号|num&len=14",
							colNo : 1,
							style : 1
						},
						result2 : {
							title : "管理机构",
							colNo : 2,
							refercode1 : "station",
							addCondition : function(colName, value) {
								return " and " + colName + " like '" + value
										+ "%' ";
							}
						},
						result1 : {
							title: "扫描日期",
							style : 8,
							colNo : 3
						},
						
						result3 : {
							title: "操作员",
							style : "3",
							colNo : 4
						},
						result4 : {
							title: "单证类型",
							style : 3,
							colNo : 5
						}
					}
				}
			},
			result : {
				 selBoxEventFuncName:"InitGoToInput",
				 selBoxEventFuncParam:"",
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
							title : "投保单号",
							colNo : 1
						},
						result2 : {
							title : "管理机构",
							colNo : 2
						},
						result1 : {
							title : "扫描日期",
							style : 8,
							colNo : 3
						},
						
						result3 : {
							title: "操作员",
							style : "3"
						},
						result4 : {
							title: "单证类型",
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
	jQuery("#ScanPrivatePool").workpool(config);
	jQuery("#privateSearch").click();
}
// 保单信息列表的初始化
/**function initGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单号";         	//列名
      iArray[1][1]="140px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="管理机构";         	//列名
      iArray[2][1]="120px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="扫描日期";         	//列名
      iArray[3][1]="200px";            	//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=8;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="工作流任务号";      //列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="工作流子任务号";    //列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="工作流活动Id";      //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[7]=new Array();
      iArray[7][0]="单证类型";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许    

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount =0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;        
      GrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initSelfGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单号";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="管理机构";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="扫描日期";         		//列名
      iArray[3][1]="200px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=8;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="工作流任务号";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="工作流子任务号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="工作流活动Id";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="单证类型";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许
             

      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrpGrid.mulLineCount =0;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.canChk = 0;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;   
      SelfGrpGrid.selBoxEventFuncName = "InitGoToInput";     
      SelfGrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
*/
</script>