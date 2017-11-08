<%
//程序名称：ProposalApproveModifyInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox(){   
}

// 下拉框的初始化
function initSelBox(){  
}                                        

function initForm()
{
  try {
    initInpBox();
    initSelBox(); 
    initIssuePool();
    //initSelfPolGrid();
    //initQuerySelf();   
    //initPolGrid();
  }
  catch(re) {
    alert("ProposalApproveModifyInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+ re.name + ":" + re.message);
  }
}

//add by lzf 2013-03-15
 function initIssuePool(){
	var config = {
			functionId : "10010004",
			//activityId : "0000001002",
			operator : operator,
			public : {
				//id : "IssuePublicPool",			
				query :{
					queryButton : {
						"1" : {title : "申  请" , func : "ApplyUW"}
					},
					arrayInfo : {
						col : {
							newcol0 : {
								title : "销售渠道 ",
								style : "2",
								colNo : 6,
								refercode1 : "salechnl",
								addCondition : function(colName,value){
									return " and exists(select 'x' from lccont where lccont.contno = t.missionprop1 and lccont.salechnl='"+ value +"')";
								}
							},
							newcol1 : {
								title : "问题件类型 ",
								style : "2",
								colNo : 7,
								refercode1 : "backobj",
								addCondition : function(colName,value){
									return " and exists(select 1 from lcissuepol,ldcode where t.missionprop1 = contno and codetype = 'backobj' and code  = '"+ value +"'  and backobjtype = comcode and standbyflag2 = othersign )";
								}
							},
							newcol2 : {
								title : "优先机构 ",
								style : "2",
								colNo : 8,
								refercode2 : "precom",
								refercodedata2 : "0|^1|优先机构|^2|非优先机构",
								addCondition : function(colName,value){
									if(value=="1"){
										return "and exists (select '1' from ldcom where t.MissionProp5=ldcom.comcode  and ldcom.comareatype1='1')";
									}
									if(value=="2"){
										return "and exists (select '1' from ldcom where t.MissionProp5=ldcom.comcode  and (ldcom.comareatype1<>'1' or ldcom.comareatype1 is null))";
									}
								}
							},
							result0 : {title : "合同号码",verify : "合同号码|num&len=14",style :"3"},
							result1 : {title : "印  刷  号  码",verify : "印刷号码|num&len=14",colNo :1,style :"1"},
							result2 : {title : "投保人",style :"3"},
							result3 : {title : "复核日期",style :"3"},
							result4 : {
								title : " 管理机构",
								colNo : 3,
								style : "2",
								refercode1 : "station",
								colName : "missionprop5",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								}
							},
							result5 : {
								title : " 业务员编码",
								colNo : 4,
								style : "2",
								referfunc1 : "queryAgent",
								referpara1 : "1"
							},
							result6 : {title : "营业部、营业组",style :"3"},
							result7 : {title : "录入位置",style :"3"},
							result8 : {
								title : "回复状态",
								colNo : 5,
								style : "3",
								refercode2 : "state",
								refercodedata2 : "0|^1|全部回复|^2|未回复",
								colName : "missionprop9",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								}
							},
							result9 : {title : " 回复日期 ",colNo :2,style :"8"},
							result10 : {title : "回复时间",style :"3"}
						}
					}
				},
				resultTitle :" 共享工作池 ",
				result :{
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : false,
						"4" : true
					},
					mulLineCount : 0,
					arrayInfo : {
						col :{
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "合同号",
								style : "3"
							},
							result1 : {
								title : " 印  刷  号 ",
								style : "0",
								colNo : 1
							},
							result2 : {
								title : "投保人",
								style : "3"
							},
							result3 : {
								title : "复核日期",
								style : "3"
							},
							result4 : {
								title : " 管理机构 ",
								style : "0",
								colNo : 3
							},
							result5 : {
								title : " 业务员编码",
								style : "3",
								colNo : 4
							},
							result6 : {
								title : "营业部、营业组",
								style : "3"
							},
							result7 : {
								title : "录入位置",
								style : "3"
							},
							result8 : {
								title : "是否有其他问题件",
								style : "0",
								colNo : 5
							},
							result9 : {
								title : " 回复日期  ",
								style : "0",
								colNo : 2
							},
							result10 : {
								title : "回复时间",
								style : "3"
							}
							
						}
					}
					
				}
			},
			private :{
				//id : "IssuePrivatePool",				
				query :{
					queryButton : {		
					},
					arrayInfo : {
						col : {
							newcol0 : {
								title : " 销售渠道",
								style : "2",
								colNo : 4,
								refercode1 : "salechnl",
								addCondition : function(colName,value){
									return " and exists(select 'x' from lccont where lccont.contno = t.missionprop1 and lccont.salechnl='"+ value +"')";
								}
							},
							newcol1 : {
								title : " 问题件类型",
								style : "2",
								colNo : 6,
								refercode1 : "backobj",
								addCondition : function(colName,value){
									return " and exists(select 1 from lcissuepol,ldcode where t.missionprop1 = contno and codetype = 'backobj' and code  = '"+ value +"'  and backobjtype = comcode and standbyflag2 = othersign )";
								}
							},
							
							result0 : {title : "合同号码",verify : "合同号码|num&len=14",style :"3"},
							result1 : {title : "  印 刷 号 码 ",verify : "印刷号码|num&len=14",colNo :1,style :"1"},
							result2 : {title : "投保人",style :"3"},
							result3 : {title : "复核日期",style :"3"},
							result4 : {
								title : " 管理机构",
								colNo : 3,
								style : "2",
								refercode1 : "station",
								colName : "missionprop5",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								}
							},
							result5 : {
								title : "业务员编码",								
								style : "3"
							},
							result6 : {title : "营业部、营业组",style :"3"},
							result7 : {title : "录入位置",style :"3"},
							result8 : {
								title : "回复状态",
								colNo : 5,
								style : "3",
								refercode2 : "state",
								refercodedata2 : "0|^1|全部回复|^2|未回复",
								colName : "missionprop9",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								}
							},
							result9 : {title : " 回复日期  ",colNo :2,style :"8"},
							result10 : {title : "回复时间",style :"3"}
						}
					}
				},
				resultTitle :" 待问题件修改投保单",
				result :{
					 selBoxEventFuncName:"InitmodifyClick",
					 selBoxEventFuncParam:"",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false
					},
					mulLineCount : 0,
					arrayInfo : {
						col :{
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "合同号",
								style : "3"
							},
							result1 : {
								title : " 印  刷  号 ",
								style : "0",
								colNo : 1
							},
							result2 : {
								title : "投保人",
								style : "3"
							},
							result3 : {
								title : "复核日期",
								style : "3"
							},
							result4 : {
								title : " 管理机构",
								style : "0",
								colNo : 3
							},
							result5 : {
								title : " 业务员编码",
								style : "0",
								colNo : 4
							},
							result6 : {
								title : "营业部、营业组",
								style : "3"
							},
							result7 : {
								title : "录入位置",
								style : "3"
							},
							result8 : {
								title : "是否有其他问题件",
								style : "0",
								colNo : 5
							},
							result9 : {
								title : " 回复日期 ",
								style : "0",
								colNo : 2
							},
							result10 : {
								title : "回复时间",
								style : "3"
							}
							
						}
					}
					
				}
      }
	};
	jQuery("#IssuePool").workpool(config);
	jQuery("#privateSearch").click();
}
//var PolGrid;
// 保单信息列表的初始化
/**function initPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="印刷号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="被保人";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="工作流任务号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="工作流子任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流活动Id";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许          

      iArray[8]=new Array();
      iArray[8][0]="回复日期";         		//列名
      iArray[8][1]="150px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="管理机构";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="业务员编码";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="营业部、营业组";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="是否有其他问题件";         		//列名
      iArray[12][1]="100px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
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
function initSelfPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="印刷号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="被保人";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="工作流任务号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="工作流子任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流活动Id";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许       

      iArray[8]=new Array();
      iArray[8][0]="回复日期";         		//列名
      iArray[8][1]="150px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="管理机构";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="业务员编码";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="营业部、营业组";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="是否有其他问题件";         		//列名
      iArray[12][1]="100px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      SelfPolGrid = new MulLineEnter( "fm" , "SelfPolGrid" ); 
      //这些属性必须在loadMulLine前
      SelfPolGrid.mulLineCount = 0;   
      SelfPolGrid.displayTitle = 1;
      SelfPolGrid.locked = 1;
      SelfPolGrid.canSel = 1;
      SelfPolGrid.hiddenPlus = 1;
      SelfPolGrid.hiddenSubtraction = 1;
      SelfPolGrid.selBoxEventFuncName = "InitmodifyClick";  
      SelfPolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //SelfPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}*/
</script>
