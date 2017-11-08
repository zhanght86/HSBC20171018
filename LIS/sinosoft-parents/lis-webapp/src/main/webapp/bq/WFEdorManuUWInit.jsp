<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2005-12-12
 * @direction: 保全人工核保初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

       // var AllGrid;     //全局变量, 共享工作队列
       // var SelfGrid;    //全局变量, 我的任务队列

        //总函数，初始化整个页面
        function initForm()
        {
            try
            {
            	initManuUWInputPool();
               // initAllGrid();
                //initSelfGrid();
               // queryPolSum();
              //  easyQueryClickSelf();
             //   initEdorCode();
            }
            catch (ex)
            {
                alert("在 WFEdorManuUWInit.jsp --> initForm 函数中发生异常: 初始化界面错误！ ");
            }
        }
function initManuUWInputPool(){
		var config = {
			functionId : "10020004",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"业务类别",//列的标题
								  style : 2,
								  colNo : 4,
								  width : "100px",
								  maxLength:150,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"edorcode",
								  colName:"edor_type",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and (select edortype from lpedoritem p where p.edoracceptno = t.missionprop1 and edortype like '" + value + "%') is not null ";
								  }
								  },
							 newcol1:{ 
								  title:"任务状态",//列的标题
								  style : 2,
								  colNo : 5,
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"uwactivitystatus",
								  colName:"MissionProp18",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  // addCondition:function (colName,value){//返回的sql条件为like
									//return " and " + colName + " like '" + value + "%' ";
								 // },defaultvalue:1
								  },
							newcol2:{ 
								  title:"管理机构",//列的标题
								  style : 2,
								  colNo : 6,
								  width : "70px",
								  colName:"MissionProp7",
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
							newcol3:{ 
								  title:"被保险人",//列的标题
								  colNo : 8,
								  style : 1,
								  width : "80px",
								  colName:"InsuredName"
								  }, 
							newcol4:{ 
								  title:"VIP客户",//列的标题
								  colNo : 9,
								  style : 1,
								  width : "70px"
								  //colName:"VIP"
								  }, 
							newcol5:{ 
								  title:"转核日期",//列的标题
								  colNo : 10,
								  style : 8,
								  width : "80px",
								  colName:"makedate"
								  },
							newcol6:{ 
								  title:"星级业务员",//列的标题
								  colNo : 13,
								  style : 1,
								  width : "70px"
								  //colName:"StarAgent"
								  },
							result0  : {title : " 保全受理号       ",width : "145px", style : 1,colNo : 2},  
							result1  : {title : " 保单号         ", width : "145px",style : 1,colNo : 1},            
							result2  : {title : " 申请号码类型     ",style : 3},  
							result3 : {title : " 保全申请人       ", width : "70px", style : 1,colNo : 3},  
							result4  : {title : " 申请方式         ",style : 3}, 
							result5  : {title : " 管理机构         ",style : 3},  
							result6  : {title : " 投保人           ",width : "70px", style : 1,colNo : 7},            
							result7  : {title : " 交费对应日       ",style : 3},           
							result8  : {title : " 最后回复日期       ", width : "90px", style : 8,colNo : 11},         
							result9  : {title : " 最后回复时间       ", width : "90px", style : 1,colNo : 12},           
							result10  : {title : " 保全核保状态      ",style : 3}           
						}
					}
				},
				resultTitle : "共享工作池",
				result : {
					selBoxEventFuncName : "applyMission",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and defaultoperator is null and manageCom like '"  + manageCom + "%%'"
					},
					mulLineCount : 3,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title:"业务类别",//列的标题
								style : 0,
								colNo : 1,
								width : "100px",
								colName : "(select edortype from lpedoritem p where p.edoracceptno = t.missionprop1) ",
								rename : "edor_type"
								},
							newCol1 : {
								title:"业务类别",//列的标题
								style : 0,
								colNo : 1,
								width : "100px",
								colName : "(select concat(concat(edortype , '-'),(select edorname from lmedoritem where appobj in ('I', 'B') "+
                   						  " and edorcode = p.edortype)) from lpedoritem p where p.edoracceptno = t.missionprop1) ",
								rename : "edor_type_name"
								},
							newcol2:{ 
								  title:"默认创建者",//列的标题
								  style : 3,
								  colName : "defaultoperator"
								  }, 
							newcol3:{ 
								  title:"被保险人",//列的标题
								  colNo : 6,
								  style : 0,
								  width : "80px",
								  colName:"(select name from lcinsured i where i.contno = t.missionprop2) ",
								  rename : "InsuredName"
								  }, 
							newcol4:{ 
								  title:"VIP客户",//列的标题
								  colNo : 4,
								  style : 0,
								  width : "70px",
								  colName:"''''",
								  rename : "VIP"
								  }, 
							newcol5:{ 
								  title:"转核日期",//列的标题
								  colNo : 8,
								  style : 0,
								  width : "80px",
								  colName:"makedate"
								  },
							newcol6:{ 
								  title:"星级业务员",//列的标题
								  colNo : 5,
								  style : 0,
								  width : "70px",
								  colName:"''''",
								  rename : "StarAgent"
								  },
							newcol7:{ 
								  title:"转核时间",//列的标题
								  colNo : 9,
								  style : 0,
								  width : "70px",
								  colName:"maketime"
								  },
							newcol8:{ 
								  title:"最近处理人",//列的标题
								  colNo : 14,
								  style : 0,
								  width : "70px",
								  colName:"lastOperator"
								  },
							newcol9:{ 
								  title:"受理日期",//列的标题
								  colNo : 15,
								  style : 0,
								  width : "60px",
								  colName:"(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1)",
                           		  rename : "edor_appdate"
								  },
							newcol10:{ 
								  title:"超过日期",//列的标题
								  colNo : 16,
								  style : 0,
								  width : "50px",
								  colName: "(select count(1) from ldcalendar where commondate > (select edorappdate " +
                  						   " from lpedoritem where edoracceptno = t.missionprop1) and workdateflag = 'Y')",
                           		  rename : "out_day"
								  },
							
							result0  : {title : " 保全受理号       ",width : "145px", style : 0,colNo : 3},  
							result1  : {title : " 保单号/客户号         ", width : "145px",style : 0,colNo : 2},            
							result2  : {title : " 申请号码类型     ",style : 3},  
							result3 : {title : " 保全申请人       ", width : "70px", style : 0,colNo : 12},  
							result4  : {title : " 申请方式         ",style : 3}, 
							result5  : {title : " 管理机构         ",width : "70px", style : 0,colNo : 13},  
							result6  : {title : " 投保人           ",width : "70px", style : 0,colNo : 7},            
							result7  : {title : " 交费对应日       ",style : 3},           
							result8  : {title : " 最后回复日期       ", width : "90px", style : 0,colNo : 10},           
							result9  : {title : " 最后回复时间       ", width : "90px", style : 0,colNo : 11},           
							result10  : {title : " 保全核保状态      ",style : 3}                  
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
								  title:"业务类别",//列的标题
								  style : 2,
								  colNo : 4,
								  width : "100px",
								  maxLength:150,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"edorcode",
								  colName:"edor_type",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and (select edortype from lpedoritem p where p.edoracceptno = t.missionprop1 and edortype like '" + value + "%') is not null ";
								  }
								  },
							 newcol1:{ 
								  title:"任务状态",//列的标题
								  style : 2,
								  colNo : 5,
								  maxLength:10,//允许输入最大长度相当于iArray[i][2]
								  refercode1:"uwactivitystatus",
								  colName:"MissionProp18",
								     addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  // addCondition:function (colName,value){//返回的sql条件为like
								//	return " and " + colName + " like '" + value + "%' ";
								//  },defaultvalue:1
								  },
							newcol2:{ 
								  title:"管理机构",//列的标题
								  style : 2,
								  colNo : 6,
								  width : "70px",
								  colName:"MissionProp7",
								  refercode1:"station",
								  addCondition:function (colName,value){//返回的sql条件为like
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
							newcol3:{ 
								  title:"被保险人",//列的标题
								  colNo : 8,
								  style : 1,
								  width : "80px",
								  colName:"InsuredName"
								  }, 
							newcol4:{ 
								  title:"VIP客户",//列的标题
								  colNo : 9,
								  style : 1,
								  width : "70px"
								  //colName:"VIP"
								  }, 
							newcol5:{ 
								  title:"转核日期",//列的标题
								  colNo : 10,
								  style : 8,
								  width : "80px",
								  colName:"makedate"
								  },
							newcol6:{ 
								  title:"星级业务员",//列的标题
								  colNo : 13,
								  style : 1,
								  width : "70px"
								  //colName:"StarAgent"
								  },
							result0  : {title : " 保全受理号       ",width : "145px", style : 1,colNo : 2},  
							result1  : {title : " 保单号         ", width : "145px",style : 1,colNo : 1},            
							result2  : {title : " 申请号码类型     ",style : 3},  
							result3 : {title : " 保全申请人       ", width : "70px", style : 1,colNo : 3},  
							result4  : {title : " 申请方式         ",style : 3}, 
							result5  : {title : " 管理机构         ",style : 3},  
							result6  : {title : " 投保人           ",style : 3,colNo : 7},            
							result7  : {title : " 交费对应日       ",style : 3},           
							result8  : {title : " 最后回复日期       ", width : "90px", style : 8,colNo : 11},         
							result9  : {title : " 最后回复时间       ", width : "90px", style : 1,colNo : 12},           
							result10  : {title : " 保全核保状态      ",style : 3}           
						}
					}
				},
				resultTitle : "我的任务",
				result : {
					selBoxEventFuncName : "GoToBusiDeal",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and trim(defaultoperator) ='"+operator+"' and manageCom like '"  + manageCom + "%%'" 
					},
					mulLineCount : 3,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title:"业务类别",//列的标题
								style : 0,
								colNo : 1,
								width : "100px",
								colName : "(select edortype from lpedoritem p where p.edoracceptno = t.missionprop1) ",
								rename : "edor_type"
								},
							newCol1 : {
								title:"业务类别",//列的标题
								style : 0,
								colNo : 1,
								width : "100px",
								colName : "(select concat(concat(edortype , '-' ),(select edorname from lmedoritem where appobj in ('I', 'B') "+
                   						  " and edorcode = p.edortype)) from lpedoritem p where p.edoracceptno = t.missionprop1) ",
								rename : "edor_type_name"
								},
							newcol2:{ 
								  title:"默认创建者",//列的标题
								  style : 3,
								  colName : "defaultoperator"
								  }, 
							newcol3:{ 
								  title:"被保险人",//列的标题
								  colNo : 6,
								  style : 0,
								  width : "80px",
								  colName:"(select name from lcinsured i where i.contno = t.missionprop2) ",
								  rename : "InsuredName"
								  }, 
							newcol4:{ 
								  title:"VIP客户",//列的标题
								  colNo : 4,
								  style : 0,
								  width : "70px",
								  colName:"''''",
								  rename : "VIP"
								  }, 
							newcol5:{ 
								  title:"转核日期",//列的标题
								  colNo : 7,
								  style : 0,
								  width : "80px",
								  colName:"makedate"
								  },
							newcol6:{ 
								  title:"星级业务员",//列的标题
								  colNo : 5,
								  style : 0,
								  width : "70px",
								  colName:"''''",
								  rename : "StarAgent"
								  },
							newcol7:{ 
								  title:"转核时间",//列的标题
								  colNo : 8,
								  style : 0,
								  width : "70px",
								  colName:"maketime"
								  },
							newcol8:{ 
								  title:"最近处理人",//列的标题
								  colNo : 13,
								  style : 0,
								  width : "70px",
								  colName:"lastOperator"
								  },
							newcol9:{ 
								  title:"受理日期",//列的标题
								  colNo : 14,
								  style : 0,
								  width : "60px",
								  colName:"(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1)",
                           		  rename : "edor_appdate"
								  },
							newcol10:{ 
								  title:"超过日期",//列的标题
								  colNo : 15,
								  style : 0,
								  width : "50px",
								  colName: "(select count(1) from ldcalendar where commondate > (select edorappdate " +
                  						   " from lpedoritem where edoracceptno = t.missionprop1) and workdateflag = 'Y')",
                           		  rename : "out_day"
								  },
							
							result0  : {title : " 保全受理号       ",width : "145px", style : 0,colNo : 3},  
							result1  : {title : " 保单号/客户号         ", width : "145px",style : 0,colNo : 2},            
							result2  : {title : " 申请号码类型     ",style : 3},  
							result3 : {title : " 保全申请人       ", width : "70px", style : 0,colNo : 11},  
							result4  : {title : " 申请方式         ",style : 3}, 
							result5  : {title : " 管理机构         ",width : "70px", style : 0,colNo : 12},  
							result6  : {title : " 投保人           ",width : "70px", style : 3},            
							result7  : {title : " 交费对应日       ",style : 3},           
							result8  : {title : " 最后回复日期       ", width : "90px", style : 0,colNo : 9},           
							result9  : {title : " 最后回复时间       ", width : "90px", style : 0,colNo : 10},           
							result10  : {title : " 保全核保状态      ",style : 3}                  
						}
					}
				}
					
			}
	};
	jQuery("#ManuUWInputPool").workpool(config);
	jQuery("#privateSearch").click();
	totalNumPublic();
}

	function totalNumPublic(){
		jQuery("#publicSearch").after("总单量：<input id ='totalNumPublic' type='readonly' />").bind("click",function(){
			var a = PublicWorkPoolGrid.mulLineCount;
			jQuery("#totalNumPublic").val(a);
		});
	}
        //共享工作队列查询 MultiLine 的初始化
        /*
        function initAllGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

				        iArray[1] = new Array();
                iArray[1][0] = "业务类别";
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;
                
                iArray[2] = new Array();
                iArray[2][0] = "保单号/客户号";
                iArray[2][1] = "145px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;
                
                iArray[3] = new Array();
                iArray[3][0] = "保全受理号";
                iArray[3][1] = "145px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "VIP客户";
                iArray[4][1] = "70px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

				        iArray[5] = new Array();
                iArray[5][0] = "星级业务员";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "被保险人";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "转核日期";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 8;
                
                iArray[8] = new Array();
                iArray[8][0] = "转核时间";
                iArray[8][1] = "80px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;                

                iArray[9] = new Array();
                iArray[9][0] = "最后回复日期";
                iArray[9][1] = "90px";
                iArray[9][2] = 100;
                iArray[9][3] = 8;

                iArray[10] = new Array();
                iArray[10][0] = "最后回复时间";
                iArray[10][1] = "90px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;

                iArray[11] = new Array();
                iArray[11][0] = "保全申请人";
                iArray[11][1] = "80px";
                iArray[11][2] = 100;
                iArray[11][3] = 0;

                iArray[12] = new Array();
                iArray[12][0] = "管理机构";
                iArray[12][1] = "70px";
                iArray[12][2] = 100;
                iArray[12][3] = 2;
                iArray[12][4] = "Station";
                iArray[12][18] = 236;

                iArray[13] = new Array();
                iArray[13][0] = "最近处理人";
                iArray[13][1] = "70px";
                iArray[13][2] = 100;
                iArray[13][3] = 0;


                iArray[14] = new Array();
                iArray[14][0] = "工作流任务号";
                iArray[14][1] = "0px";
                iArray[14][2] = 0;
                iArray[14][3] = 3;

                iArray[15] = new Array();
                iArray[15][0] = "工作流子任务号";
                iArray[15][1] = "0px";
                iArray[15][2] = 100;
                iArray[15][3] = 3;

                iArray[16] = new Array();
                iArray[16][0] = "工作流活动ID";
                iArray[16][1] = "0px";
                iArray[16][2] = 0;
                iArray[16][3] = 3;

                iArray[17] = new Array();
                iArray[17][0] = "工作流当前活动状态";
                iArray[17][1] = "0px";
                iArray[17][2] = 0;
                iArray[17][3] = 3;
                
                iArray[18] = new Array();
                iArray[18][0] = "受理日期";
                iArray[18][1] = "60px";
                iArray[18][2] = 0;
                iArray[18][3] = 8;

                iArray[19] = new Array();
                iArray[19][0] = "超过日期";
                iArray[19][1] = "50px";
                iArray[19][2] = 0;
                iArray[19][3] = 0;
                
            }
            catch (ex)
            {
                alert("在 WFEdorManuUWInit.jsp --> initAllGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                AllGrid = new MulLineEnter("fm", "AllGrid");
                AllGrid.mulLineCount = 3;
                AllGrid.displayTitle = 1;
                AllGrid.locked = 1;
                AllGrid.hiddenPlus = 1;
                AllGrid.hiddenSubtraction = 1;
                AllGrid.canChk = 0;
                AllGrid.canSel = 1;
                AllGrid.chkBoxEventFuncName = "";
                AllGrid.selBoxEventFuncName = "applyMission";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                AllGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 WFEdorManuUWInit.jsp --> AllGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        //我的任务队列查询 MultiLine 的初始化
        function initSelfGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

				iArray[1] = new Array();
                iArray[1][0] = "业务类别";
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;
                
                iArray[2] = new Array();
                iArray[2][0] = "保单号/客户号";
                iArray[2][1] = "145px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;
                
                iArray[3] = new Array();
                iArray[3][0] = "保全受理号";
                iArray[3][1] = "145px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "VIP客户";
                iArray[4][1] = "70px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

								iArray[5] = new Array();
                iArray[5][0] = "星级业务员";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "被保险人";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "转核日期";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                
                iArray[8] = new Array();
                iArray[8][0] = "转核时间";
                iArray[8][1] = "80px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;                

                iArray[9] = new Array();
                iArray[9][0] = "最后回复日期";
                iArray[9][1] = "90px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "最后回复时间";
                iArray[10][1] = "90px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;

                iArray[11] = new Array();
                iArray[11][0] = "保全申请人";
                iArray[11][1] = "80px";
                iArray[11][2] = 100;
                iArray[11][3] = 0;

                iArray[12] = new Array();
                iArray[12][0] = "管理机构";
                iArray[12][1] = "70px";
                iArray[12][2] = 100;
                iArray[12][3] = 2;
                iArray[12][4] = "Station";
                iArray[12][18] = 236;

                iArray[13] = new Array();
                iArray[13][0] = "最近处理人";
                iArray[13][1] = "70px";
                iArray[13][2] = 100;
                iArray[13][3] = 0;


                iArray[14] = new Array();
                iArray[14][0] = "工作流任务号";
                iArray[14][1] = "0px";
                iArray[14][2] = 0;
                iArray[14][3] = 3;

                iArray[15] = new Array();
                iArray[15][0] = "工作流子任务号";
                iArray[15][1] = "0px";
                iArray[15][2] = 100;
                iArray[15][3] = 3;

                iArray[16] = new Array();
                iArray[16][0] = "工作流活动ID";
                iArray[16][1] = "0px";
                iArray[16][2] = 0;
                iArray[16][3] = 3;

                iArray[17] = new Array();
                iArray[17][0] = "工作流当前活动状态";
                iArray[17][1] = "0px";
                iArray[17][2] = 0;
                iArray[17][3] = 3;
                
                iArray[18] = new Array();
                iArray[18][0] = "受理日期";
                iArray[18][1] = "60px";
                iArray[18][2] = 0;
                iArray[18][3] = 0;

                iArray[19] = new Array();
                iArray[19][0] = "超过日期";
                iArray[19][1] = "50px";
                iArray[19][2] = 0;
                iArray[19][3] = 0;
                
            }
            catch (ex)
            {
                alert("在 WFEdorManuUWInit.jsp --> initSelfGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                SelfGrid = new MulLineEnter("fm", "SelfGrid");
                SelfGrid.mulLineCount = 0;
                SelfGrid.displayTitle = 1;
                SelfGrid.locked = 1;
                SelfGrid.hiddenPlus = 1;
                SelfGrid.hiddenSubtraction = 1;
                SelfGrid.canChk = 0;
                SelfGrid.canSel = 1;
                SelfGrid.chkBoxEventFuncName = "";
                SelfGrid.selBoxEventFuncName = "GoToBusiDeal";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                SelfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 WFEdorManuUWInit.jsp --> initSelfGrid 函数中发生异常: 初始化界面错误！");
            }
        }
*/
    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

