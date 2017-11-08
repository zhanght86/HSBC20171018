<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：RnewSecUWAllInit.jsp
//程序功能：续期续保二次核保
//创建日期：2005-01-29 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	if(globalInput == null) 
	{
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript"><!--

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                  
  }
  catch(ex)
  {
    alert("在UWInit.jspInitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {

    //initInpBox();
    //initPolGrid();  
    //initAllPolGrid();    

    //easyQueryClick();
    initRnewSecUWAllInputPool();
  }
  catch(re)
  {
    alert("在UWInit.jspInitForm函数中发生异常:初始化界面错误!");
  }
}
function initRnewSecUWAllInputPool(){
	var config = {
			functionId : "10047001",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"管理机构",//列的标题
								  style : 2,
								  colNo : 3,
								  width : "100px",
								  colName:"MissionProp6",
								  maxLength:60,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							 newcol1:{ 
								  title:"核保任务状态",//列的标题
								  style : 2,
								  colNo : 4,
								  width : "80px",
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"uwstate",
								  colName:"MissionProp12",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  },defaultvalue:1
								  },
							newcol2:{ 
								  title:"星级业务员",//列的标题
								  style : 1,
								  colNo : 5,
								  width : "70px"
								  }, 
							newcol3:{ 
								  title:"VIP客户",//列的标题
								  colNo : 6,
								  style : 1,
								  width : "70px"
								  }, 
								  
							result0  : {title : " 印刷号       ", style : 3},  
							result1  : {title : " 合同号       ",width : "145px",style : 1,colNo : 1},  
							result2  : {title :" 投保单号   ",style : 3 },            
							result3  : {title : "险种     ",style : 3},  
							result4  : {title : " 险种名称       ",  style : 3},  
							result5  : {title : " 管理机构     ",style : 3}, 
							result6  : {title : " 客户号     ",width : "100px",style : 1,colNo : 2},  
							result7  : {title : " 投保人        ",width : "80px",style : 1,colNo : 7},            
							result8  : {title : " 被保人编码           ",style : 3},           
							result9  : {title : " 被保人    ",width : "80px" ,style : 1, colNo : 8 },         
							result10  : {title : " 转核日期     ",width : "70px" ,style : 8,colNo : 9 },           
							result11  : {title : " 核保状态     ", style : 3 },           
							result12  : {title : " 核保员说明      ", style : 3},           
							result13  : {title :" 最后回复日期      ",width : "70px" , style : 8,colNo : 10},           
							result14  : {title : " 最后回复时间     ",style : 3}         
						}
					}
				},
				resultTitle : "共享工作池",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"4" : true,
						"5" : "  and manageCom like '"  + manageCom + "%%' "
					},
					mulLineCount : 3,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							 newcol0:{ 
								  title:"核保任务状态",//列的标题
								  style : 3,
								  refercode1:"uwstate",
								  colName:"MissionProp12",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol1:{ 
								  title:"星级业务员",//列的标题
								  style : 0,
								  colNo : 3,
								  width : "70px",
								  colName:"''''",
								  rename : "Star"
								  }, 
							newcol2:{ 
								  title:"VIP客户",//列的标题
								  colNo : 2,
								  style : 0,
								  width : "70px",
								  colName:"''''",
								  rename : "VIP"
								  }, 
							newcol3:{ 
								  title:"转核时间",//列的标题
								  colNo : 6,
								  style : 0,
								  width : "70px",
								  colName:"maketime",
								  rename : "maketime"
							},
							newcol4:{ 
								  title:"最近处理人",//列的标题
								  colNo : 11,
								  style : 0,
								  width : "70px",
								  colName:"lastoperator",
								  rename : "l_operator"
								  },
							newcol5:{ 
								  title:"默认操作员",//列的标题
								  style : 3,
								  colName:"defaultoperator"
								  },
							result0  : {title : " 印刷号       ", style : 3},  
							result1  : {title : " 合同号       ",width : "145px",style : 0,colNo : 1},  
							result2  : {title :" 投保单号   ",style : 3 },            
							result3  : {title : "险种     ",style : 3},  
							result4  : {title : " 险种名称       ",  style : 3},  
							result5  : {title : " 管理机构     ",width : "100px",style : 0, colNo : 10}, 
							result6  : {title : " 客户号     ",width : "100px",style : 0,colNo : 9},  
							result7  : {title : " 投保人        ",style : 3},            
							result8  : {title : " 被保人编码           ",style : 3 },           
							result9  : {title : " 被保人    ",width : "80px" ,style : 0, colNo : 4 },         
							result10  : {title : " 转核日期    ",width : "70px" ,style : 0,colNo : 5 },           
							result11  : {title : " 核保状态     ", style : 3 },           
							result12  : {title : " 核保员说明      ", style : 3 },           
							result13  : {title :" 最后回复日期      ",width : "70px" , style : 0,colNo : 8},           
							result14  : {title : " 最后回复时间     ", width : "70px",style : 0, colNo : 7}        
						}
					 }
					}
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"管理机构",//列的标题
								  style : 2,
								  colNo : 3,
								  width : "100px",
								  colName:"MissionProp6",
								  maxLength:60,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							 newcol1:{ 
								  title:"核保任务状态",//列的标题
								  style : 2,
								  colNo : 4,
								  width : "80px",
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"uwstate",
								  colName:"MissionProp12",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  },defaultvalue:1
								  },
							newcol2:{ 
								  title:"星级业务员",//列的标题
								  style : 1,
								  colNo : 5,
								  width : "70px"
								  }, 
								
							newcol3:{ 
								  title:"VIP客户",//列的标题
								  colNo : 6,
								  style : 1,
								  width : "70px"
								  }, 
							result0  : {title : " 印刷号       ", style : 3},  
							result1  : {title : " 合同号       ",width : "145px",style : 1,colNo : 1},  
							result2  : {title :" 投保单号   ",style : 3 },            
							result3  : {title : "险种     ",style : 3},  
							result4  : {title : " 险种名称       ",  style : 3},  
							result5  : {title : " 管理机构     ",style : 3}, 
							result6  : {title : " 客户号     ",width : "100px",style : 1,colNo : 2},  
							result7  : {title : " 投保人        ",width : "80px",style : 1,colNo : 7},            
							result8  : {title : " 被保人编码           ",style : 3},           
							result9  : {title : " 被保人    ",width : "80px" ,style : 1, colNo : 8 },         
							result10  : {title : " 转核日期     ",width : "70px" ,style : 8,colNo : 9 },           
							result11  : {title : " 核保状态     ", style : 3 },           
							result12  : {title : " 核保员说明      ", style : 3},           
							result13  : {title :" 最后回复日期      ",width : "70px" , style : 8,colNo : 10},           
							result14  : {title : " 最后回复时间     ",style : 3}        
						}
					}
				},
				resultTitle : "个人工作池",
				result : {
					selBoxEventFuncName : "IniteasyQueryAddClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : operator,
						"5" : " and trim(defaultoperator) ='"+operator+"' and manageCom like '"  + manageCom + "%%'" 
					},
					mulLineCount : 3,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							 newcol0:{ 
								  title:"核保任务状态",//列的标题
								  style : 3,
								  refercode1:"uwstate1",
								  colName:"MissionProp12",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol1:{ 
								  title:"星级业务员",//列的标题
								  style : 0,
								  colNo : 3,
								  width : "70px",
								  colName:"''''",
								  rename : "Star"
								  }, 
							newcol2:{ 
								  title:"VIP客户",//列的标题
								  colNo : 2,
								  style : 0,
								  width : "70px",
								  colName:"''''",
								  rename : "VIP"
								  }, 
							newcol3:{ 
								  title:"转核时间",//列的标题
								  colNo : 6,
								  style : 0,
								  width : "70px",
								  colName:"maketime",
								  rename : "maketime"
							},
							newcol4:{ 
								  title:"最近处理人",//列的标题
								  colNo : 9,
								  style : 0,
								  width : "70px",
								  colName:"lastoperator",
								  rename : "l_operator"
								  },
							newcol5:{ 
								  title:"默认操作员",//列的标题
								  style : 3,
								  colName:"defaultoperator"
								  },
							result0  : {title : " 印刷号       ", style : 3},  
							result1  : {title : " 合同号       ",width : "145px",style : 0,colNo : 1},  
							result2  : {title :" 投保单号   ",style : 3 },            
							result3  : {title : "险种     ",style : 3},  
							result4  : {title : " 险种名称       ",  style : 3},  
							result5  : {title : " 管理机构     ",width : "100px",style : 0, colNo : 10}, 
							result6  : {title : " 客户号     ",width : "100px",style : 0,colNo : 9},  
							result7  : {title : " 投保人        ",style : 3},            
							result8  : {title : " 被保人编码           ",style : 3 },           
							result9  : {title : " 被保人    ",width : "80px" ,style : 0, colNo : 4 },         
							result10  : {title : " 转核日期    ",width : "70px" ,style : 0,colNo : 5 },           
							result11  : {title : " 核保状态     ", style : 3 },           
							result12  : {title : " 核保员说明      ", style : 3 },           
							result13  : {title :" 最后回复日期      ",width : "70px" , style : 0,colNo : 8},           
							result14  : {title : " 最后回复时间     ", width : "70px",style : 0, colNo : 7}                     
						}
						}
					}
			},
			midContent : { 
			id : 'MidContent',
			show : true,
			html : '<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="applyTheMission()">'
			}
	};
	jQuery("#RnewSecUWAllInputPool").workpool(config);
	jQuery("#privateSearch").click();
	totalNumPublic();
}

function totalNumPublic(){
		jQuery("#publicSearch").after("总单量：<input id ='totalNumPublic' type='readonly' />").bind("click",function(){
			var a = PublicWorkPoolGrid.mulLineCount;
			jQuery("#totalNumPublic").val(a);
		});
	}
// 保单信息列表的初始化
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单号";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="VIP客户";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="星级业务员";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="被保险人";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="转核日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许       
      
      iArray[6]=new Array();
      iArray[6][0]="转核时间";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许       

      iArray[7]=new Array();                                                       
      iArray[7][0]="最后回复时间";         		//列名                                     
      iArray[7][1]="100px";            		//列宽                                   
      iArray[7][2]=100;            			//列最大值                                 
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[8]=new Array();
      iArray[8][0]="最后回复日期";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="客户号";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="管理机构";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="最近处理人";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[12] = new Array();
      iArray[12][0] = "工作流任务号";
      iArray[12][1] = "0px";
      iArray[12][2] = 0;
      iArray[12][3] = 3;

      iArray[13] = new Array();
      iArray[13][0] = "工作流子任务号";
      iArray[13][1] = "0px";
      iArray[13][2] = 100;
      iArray[13][3] = 3;

      iArray[14] = new Array();
      iArray[14][0] = "工作流活动ID";
      iArray[14][1] = "0px";
      iArray[14][2] = 0;
      iArray[14][3] = 3;

      iArray[15] = new Array();
      iArray[15][0] = "工作流当前活动状态";
      iArray[15][1] = "0px";
      iArray[15][2] = 0;
      iArray[15][3] = 3;
      
      iArray[16] = new Array();
      iArray[16][0] = "印刷号";
      iArray[16][1] = "0px";
      iArray[16][2] = 0;
      iArray[16][3] = 3;
      
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray); 
      
      PolGrid.selBoxEventFuncName = "IniteasyQueryAddClick";
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


// 保单信息列表的初始化
function initAllPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单号";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="VIP客户";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="星级业务员";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="被保险人";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="转核日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许          
      
      iArray[6]=new Array();
      iArray[6][0]="转核时间";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[7]=new Array();                                                       
      iArray[7][0]="最后回复时间";         		//列名                                     
      iArray[7][1]="100px";            		//列宽                                   
      iArray[7][2]=100;            			//列最大值                                 
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[8]=new Array();
      iArray[8][0]="最后回复日期";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="客户号";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="管理机构";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="最近处理人";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[12] = new Array();
      iArray[12][0] = "工作流任务号";
      iArray[12][1] = "0px";
      iArray[12][2] = 0;
      iArray[12][3] = 3;

      iArray[13] = new Array();
      iArray[13][0] = "工作流子任务号";
      iArray[13][1] = "0px";
      iArray[13][2] = 100;
      iArray[13][3] = 3;

      iArray[14] = new Array();
      iArray[14][0] = "工作流活动ID";
      iArray[14][1] = "0px";
      iArray[14][2] = 0;
      iArray[14][3] = 3;

      iArray[15] = new Array();
      iArray[15][0] = "工作流当前活动状态";
      iArray[15][1] = "0px";
      iArray[15][2] = 0;
      iArray[15][3] = 3;
      
      iArray[16] = new Array();
      iArray[16][0] = "印刷号";
      iArray[16][1] = "0px";
      iArray[16][2] = 0;
      iArray[16][3] = 3;
      
      AllPolGrid = new MulLineEnter( "fm" , "AllPolGrid" ); 
      //这些属性必须在loadMulLine前
      AllPolGrid.mulLineCount = 3;   
      AllPolGrid.displayTitle = 1;
      AllPolGrid.locked = 1;
      AllPolGrid.canSel = 1;
      AllPolGrid.hiddenPlus = 1;
      AllPolGrid.hiddenSubtraction = 1;
      AllPolGrid.loadMulLine(iArray); 
      //AllPolGrid.selBoxEventFuncName = "ApplyUW";   
      //AllPolGrid.selBoxEventFuncName = "easyQueryAddClick";   

      }
      catch(ex)
      {
        alert(ex);
      }
}

--></script>