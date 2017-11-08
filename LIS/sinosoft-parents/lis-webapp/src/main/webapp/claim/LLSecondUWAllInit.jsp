<%
	//程序名称：LLSecondUWAllInit.jsp
	//程序功能：理赔人工核保获取队列
	//创建日期：2005-1-28 11:10:36
	//创建人  ：zhangxing
	//更新记录：  更新人  yuejw  更新日期     更新原因/内容
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>            
<script language="JavaScript">

//接受上页传入的数据 
function initParam()
{
	document.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //记录操作员
    document.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //记录登陆机构
    document.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //记录管理机构
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

function initForm()
{
	try
	{
		initParam(); //
		//initLLCUWBatchGrid();  //
		//initSelfLLCUWBatchGrid();
		//initSelfLLCUWBatchGridQuery();  //  
		initSecondUWAllPool();
	}
	catch(re)
	{
		alert("在UWInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.name +"::::"+re.message);
	}
}

//modify by lzf
function initSecondUWAllPool(){
	var config = {
			//activityId : "0000005520",
			functionId : "10030020",
			operator : operator,
			public : {
				query : {
					queryButton : {
						"1" : {
							title : "重  置",
							func : "cancleClick"
						}
					},
					arrayInfo : {
						col : {
							result0 : {title : "    立案号 ", style : 1, colNo : 2 },
							result1 : {title : "  批次号 ", style : 1, colNo : 1 },
							result2 : {title : "出险人号码", style : 1, colNo : 4 },
							result3 : {title : " 出险人", style : 1, colNo : 5 },
							result4 : {
								title : "赔案相关标志", 
								style : 2, 
								colNo : 7,
								refercode2 : "qclaimrelflag",
								refercodedata2 : "0|3^0|相关^1|不相关",
								addCondition : function(colName,value){
									return " and t.missionprop5 = '"+value+"'";
								}
								},
							result5 : {
								title : " 转核日期", 
								style : 8, 
								colNo : 3,
								refercode1 : "theCurrentDate",
								addCondition : function(colName,value){
									return " and t.missionprop6 <= '"+value+"'";
								}
								},
							result6 : {title : "VIP客户", style : 3 },
							result7 : {title : "星级业务员", style : 3 },
							result8 : {title : "报案人", style : 3},
							result9 : {
								title : " 管理机构",
								style : 2,
								colNo : 6,
								refercode1 : "station",
								colName : "missionprop20",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
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
							newCol0 : {
								title : "核转次数",
								style : 0,
								colNo : 3,
								colName : "(select (case count(distinct batno) when 0 then 0 else count(distinct batno) end) from llcuwbatch where caseno = t.missionprop1 ) ",
								rename : "uwcount"
							},
							result0 : {title : "    立案号 ", style : 0, colNo : 1 },
							result1 : {title : "  批次号 ", style : 0, colNo : 2 },
							result2 : {title : "  客户号 ", style : 0, colNo : 9 },
							result3 : {title : " 出险人", style : 0, colNo : 7 },
							result4 : {
								title : "赔案相关标志", 
								style : 3
								},
							result5 : {title : " 转核日期", style : 0,colNo : 8 },
							result6 : {title : "VIP客户", style : 0 , colNo :4 },
							result7 : {title : "星级业务员", style : 0 , colNo :5 },
							result8 : {title : " 报案人", style : 0 , colNo :6 },
							result9 : {
								title : " 管理机构",
								style : 0,
								colNo : 10
							    }
						}
					}
				}
			},
			midContent : {
				id : "MidContent",
				show : true,
				html : "<INPUT class=cssButton VALUE='申请任务' TYPE=button onclick=applyClick()>"
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "    立案号 ", style : 1, colNo : 2 },
							result1 : {title : "  批次号 ", style : 1, colNo : 1 },
							result2 : {title : " 出险人号码", style : 1, colNo : 4 },
							result3 : {title : " 出险人", style : 1, colNo : 5 },
							result4 : {
								title : "赔案相关标志", 
								style : 2, 
								colNo : 7,
								refercode2 : "qclaimrelflag",
								refercodedata2 : "0|3^0|相关^1|不相关",
								addCondition : function(colName,value){
									return " and t.missionprop5 = '"+value+"'";
								}
								},
							result5 : {
								title : " 转核日期", 
								style : 8, 
								colNo : 3,
								refercode1 : "theCurrentDate",
								addCondition : function(colName,value){
									return " and t.missionprop6 <= '"+value+"'";
								}	
							},
							result6 : {title : "VIP客户", style : 3 },
							result7 : {title : "星级业务员", style : 3 },
							result8 : {title : " 报案人", style : 3},
							result9 : {
								title : " 管理机构",
								style : 2,
								colNo : 6,
								refercode1 : "station",
								colName : "missionprop20",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
							    }
						}
					}
				},
				resultTitle : "个人工作池",
				result : {
					 selBoxEventFuncName:"SelfLLCUWBatchGridClick",
					 selBoxEventFuncParam:"",
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
							newCol0 : {
								title : " 核转次数",
								style : 0,
								colNo : 3,
								colName : "(select (case count(distinct batno) when 0 then 0 else count(distinct batno) end) from llcuwbatch where caseno = t.missionprop1 ) ",
								rename : "uwcount"
							},
							result0 : {title : "    立案号 ", style : 0, colNo : 1 },
							result1 : {title : "  批次号 ", style : 0, colNo : 2 },
							result2 : {title : "出险人号码", style : 0, colNo : 9 },
							result3 : {title : " 出险人", style : 0, colNo : 7 },
							result4 : {
								title : "赔案相关标志", 
								style : 3
								},
							result5 : {title : " 转核日期", style : 0,colNo : 8 },
							result6 : {title : "VIP客户", style : 0 , colNo :4 },
							result7 : {title : "星级业务员", style : 0 , colNo :5 },
							result8 : {title : " 报案人", style : 0 , colNo :6 },
							result9 : {
								title : " 管理机构",
								style : 0,
								colNo : 10
							    }
						}
					}
				}
			}
	};
	jQuery("#SecondUWAllPool").workpool(config);
	jQuery("#privateSearch").click();
}

//end 

// 保单信息列表的初始化
/*function initLLCUWBatchGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名 （此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=30;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许       			 
		
		iArray[1]=new Array();
		iArray[1][0]="立案号";         		 
		iArray[1][1]="160px";            		 
		iArray[1][2]=100;            			 
		iArray[1][3]=0;              			 
	
		iArray[2]=new Array();
		iArray[2][0]="批次号";         		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=100;            			 
		iArray[2][3]=0;  
		            			 
		iArray[3]=new Array();
	    iArray[3][0]="转核次数";              // 隐藏 “赔案相关标志”
	    iArray[3][1]="60px";                
	    iArray[3][2]=200;                  
	    iArray[3][3]=0;   
	            			 
		iArray[4]=new Array();
		iArray[4][0]="VIP客户";         		 
		iArray[4][1]="50px";            		 
		iArray[4][2]=100;            			 
		iArray[4][3]=0;              			 
		
		iArray[5]=new Array();
		iArray[5][0]="星级业务员";         		 
		iArray[5][1]="50px";            		 
		iArray[5][2]=100;            			 
		iArray[5][3]=0;              			 
	
		iArray[6]=new Array();
		iArray[6][0]="报案人";         		 
		iArray[6][1]="100px";            		 
		iArray[6][2]=100;            			 
		iArray[6][3]=0;              			 
		
		iArray[7]=new Array();
		iArray[7][0]="出险人";         		 
		iArray[7][1]="100px";            		 
		iArray[7][2]=100;            			 
		iArray[7][3]=0;              			    
	
		iArray[8]=new Array();                                                       
		iArray[8][0]="转核日期";         		                                      
		iArray[8][1]="80px";            		                                    
		iArray[8][2]=100;            			                                  
		iArray[8][3]=0;       
		
		iArray[9]=new Array();                                                       
		iArray[9][0]="转核时间";         		                                      
		iArray[9][1]="80px";            		                                    
		iArray[9][2]=100;            			                                  
		iArray[9][3]=0; 		       			    
	
	    iArray[10]=new Array();
	    iArray[10][0]="客户号";              
	    iArray[10][1]="80px";                 
	    iArray[10][2]=200;                   
	    iArray[10][3]=0;                           
	
	    iArray[11]=new Array();
	    iArray[11][0]="管理机构";              
	    iArray[11][1]="80px";                 
	    iArray[11][2]=200;                   
	    iArray[11][3]=0;                           
	    
	    iArray[12]=new Array();
	    iArray[12][0]="MissionID";              
	    iArray[12][1]="0px";                 
	    iArray[12][2]=200;                   
	    iArray[12][3]=3;                      
	    
	    iArray[13]=new Array();
	    iArray[13][0]="SubMissionID";              // 隐藏 “赔案相关标志”
	    iArray[13][1]="0px";                
	    iArray[13][2]=200;                  
	    iArray[13][3]=3;                            
	
		iArray[14]=new Array();
	    iArray[14][0]="ActivityID";              // 隐藏 “赔案相关标志”
	    iArray[14][1]="0px";                
	    iArray[14][2]=200;                  
	    iArray[14][3]=3;                    
	
		LLCUWBatchGrid = new MulLineEnter( "fm" , "LLCUWBatchGrid" ); 
		//这些属性必须在loadMulLine前
		LLCUWBatchGrid.mulLineCount = 0;   
		LLCUWBatchGrid.displayTitle = 1;
		LLCUWBatchGrid.locked = 1;
		LLCUWBatchGrid.canSel = 1;
		LLCUWBatchGrid.hiddenPlus = 1;
		LLCUWBatchGrid.hiddenSubtraction = 1;
		LLCUWBatchGrid.loadMulLine(iArray);     
//		LLCUWBatchGrid.selBoxEventFuncName = "LLCUWBatchGridClick";
		//这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}

// 保单信息列表的初始化
function initSelfLLCUWBatchGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名 （此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=30;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许       			 
		
		iArray[1]=new Array();
		iArray[1][0]="立案号";         		 
		iArray[1][1]="160px";            		 
		iArray[1][2]=100;            			 
		iArray[1][3]=0;              			 
	
		iArray[2]=new Array();
		iArray[2][0]="批次号";         		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=100;            			 
		iArray[2][3]=0;  

		iArray[3]=new Array();
	    iArray[3][0]="转核次数";              // 隐藏 “赔案相关标志”
	    iArray[3][1]="60px";                
	    iArray[3][2]=200;                  
	    iArray[3][3]=0;   
	            			 
		iArray[4]=new Array();
		iArray[4][0]="VIP客户";         		 
		iArray[4][1]="50px";            		 
		iArray[4][2]=100;            			 
		iArray[4][3]=0;              			 
		
		iArray[5]=new Array();
		iArray[5][0]="星级业务员";         		 
		iArray[5][1]="50px";            		 
		iArray[5][2]=100;            			 
		iArray[5][3]=0;              			 
	
		iArray[6]=new Array();
		iArray[6][0]="报案人";         		 
		iArray[6][1]="100px";            		 
		iArray[6][2]=100;            			 
		iArray[6][3]=0;              			 
		
		iArray[7]=new Array();
		iArray[7][0]="出险人";         		 
		iArray[7][1]="100px";            		 
		iArray[7][2]=100;            			 
		iArray[7][3]=0;              			    
	
		iArray[8]=new Array();                                                       
		iArray[8][0]="转核日期";         		                                      
		iArray[8][1]="80px";            		                                    
		iArray[8][2]=100;            			                                  
		iArray[8][3]=0;          
		
		iArray[9]=new Array();                                                       
		iArray[9][0]="转核时间";         		                                      
		iArray[9][1]="80px";            		                                    
		iArray[9][2]=100;            			                                  
		iArray[9][3]=0; 				    			    
	
	    iArray[10]=new Array();
	    iArray[10][0]="客户号";              
	    iArray[10][1]="80px";                 
	    iArray[10][2]=200;                   
	    iArray[10][3]=0;                           
	
	    iArray[11]=new Array();
	    iArray[11][0]="管理机构";              
	    iArray[11][1]="80px";                 
	    iArray[11][2]=200;                   
	    iArray[11][3]=0;                           
	    
	    iArray[12]=new Array();
	    iArray[12][0]="MissionID";              
	    iArray[12][1]="0px";                 
	    iArray[12][2]=200;                   
	    iArray[12][3]=3;                      
	    
	    iArray[13]=new Array();
	    iArray[13][0]="SubMissionID";              // 隐藏 “赔案相关标志”
	    iArray[13][1]="0px";                
	    iArray[13][2]=200;                  
	    iArray[13][3]=3;                            
	
		iArray[14]=new Array();
	    iArray[14][0]="ActivityID";              // 隐藏 “赔案相关标志”
	    iArray[14][1]="0px";                
	    iArray[14][2]=200;                  
	    iArray[14][3]=3;   

	    iArray[15]=new Array();
	    iArray[15][0]="赔案相关标志";              // 隐藏 “赔案相关标志”
	    iArray[15][1]="60px";                
	    iArray[15][2]=200;                  
	    iArray[15][3]=3;   

	
		SelfLLCUWBatchGrid = new MulLineEnter( "fm" , "SelfLLCUWBatchGrid" ); 
		//这些属性必须在loadMulLine前
		SelfLLCUWBatchGrid.mulLineCount =0;   
		SelfLLCUWBatchGrid.displayTitle = 1;
		SelfLLCUWBatchGrid.locked = 1;
		SelfLLCUWBatchGrid.canSel = 1;
		SelfLLCUWBatchGrid.hiddenPlus = 1;
		SelfLLCUWBatchGrid.hiddenSubtraction = 1;
		SelfLLCUWBatchGrid.loadMulLine(iArray);     
		SelfLLCUWBatchGrid.selBoxEventFuncName = "SelfLLCUWBatchGridClick";
		//这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}
*/

</script>
