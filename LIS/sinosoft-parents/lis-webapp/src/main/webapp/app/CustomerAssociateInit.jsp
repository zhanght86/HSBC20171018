<%
//程序名称：ClientAssociateInit.jsp
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox(){ }

function initForm()
{
  try
  {
	  initCustomerPool();
    //initInpBox();
    //initClientListGrid();
    //initOPolGrid();
    //easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ClientAssociateInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//add by lzf 2013-03-18
function initCustomerPool(){
	var config = {
		functionId : "10010056",
		//activityId : "0000001404",
		operator : operator,
		public : {
			query : {
				queryButton : {
					"1" : {title : "申  请" , func : "ApplyUW"}
				},
				arrayInfo : {
					col : {
						newCol0:{
							title : " 初审日期  ",
							refercode1 : "firsttrialdate",
							addCondition : function(colName, value) {
								return " and exists (select 1 from lccont where contno= t.missionprop1 and firsttrialdate = to_date('"
										+ value +"','yyyy-mm-dd'))";
										
							},
							style : "8"						
							},
						result0 : {title : "投保单号",verify : "投保单号|num&len=14",style :"3"},
						result1 : {title : " 印刷号   ",verify : "印刷号|num&len=14",style :"1",colNo : 1},
						result2 : {
							title : " 险种编码 ",
							style :"2",
							colNo : 5,
							refercode1 : "riskcode",
							colName : "missionprop3",
							addCondition : function(colName,value){
								return " and "+ colName +" like '" + value
								+ "%' ";
							}
							
							},
						result3 : {title : "投保人姓名",style :"1"},
						result4 : {title : "被保人姓名",style :"1"},
						result5 : {
							title : " 扫描日期  ",
							style :"8",
							colNo : 4,
							refercode1 : "makedate",
							addCondition : function(colName,value){
								return " and exists (select 1 from es_doc_main where doccode = t.missionprop1 and makedate =to_date('"+ value +"','yyyy-mm-dd'))";
							}
							
							},
						result6 : {title : "投保人客户号",style :"1"},
						result7 : {title : "被保人客户号",style :"1"},
						result8 : {
							title : " 管理机构  ",
							style :"2",
							colNo : 2,						
							refercode1 : "station",
							colName : "missionprop5",
							addCondition : function(colName , value){
								return " and "+ colName +" like '" + value
								+ "%' ";
							   }
							},
						result9 : {title : "代理人编码",style :"1",colNo : 6}
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
					col :{
						col5 : "0",
						col6 : "0",
						col7 : "0",
						newCol0:{
							title : " 初审日期  ",
							colName : "(select max(firsttrialdate) from lccont where contno= t.missionprop1)",
							rename : "firsttrialdate",							
							style : "8"						
							},
						result0 : {
							title : " 投保单号  ",
							style : "1",
							colNo : 1
						},
						result1 : {
							title : " 印  刷  号 ",
							style : "1",
							colNo : 2
						},
						result2 : {
							title : "险种编码",
							style : "1",
							colNo : 3
						},
						result3 : {
							title : " 投保人 ",
							style : "1",
							colNo : 4
						},
						result4 : {
							title : " 被保人 ",
							style : "1",
							colNo : 5
						},
						result5 : {
							title : " 扫描日期 ",
							style : "8",
							colNo : 6
						},
						result6 : {
							title : "投保人客户号",
							style : "1",
							colNo : 7
						},
						result7 : {
							title : "被保人客户号",
							style : "1",
							colNo : 8
						},
						result8 : {
							title : "管理机构",
							style : "3"
						},
						result9 : {
							title : "代理人编码 ",
							style : "3"
						}
						
					}
				}
			}
		},
		private : {
			query : {
				queryButton : {
				
				},
				arrayInfo : {
					col : {
						newCol0:{
							title : " 初审日期  ",
							refercode1 : "firsttrialdate",
							addCondition : function(colName, value) {
								return " and exists (select 1 from lccont where contno= t.missionprop1 and firsttrialdate = to_date('"
										+ value +"','yyyy-mm-dd'))";
										
							},
							style : "8"						
							},
						result0 : {title : "投保单号",verify : "投保单号|num&len=14",style :"3"},
						result1 : {title : " 印刷号   ",verify : "印刷号|num&len=14",style :"1",colNo : 1},
						result2 : {
							title : " 险种编码 ",
							style :"2",
							colNo : 5,
							refercode1 : "riskcode",
							colName : "missionprop3",
							addCondition : function(colName,value){
								return " and "+ colName +" like '" + value
								+ "%' ";
							}
							
							},
						result3 : {title : "投保人姓名",style :"1"},
						result4 : {title : "被保人姓名",style :"1"},
						result5 : {
							title : " 扫描日期  ",
							style :"8",
							colNo : 4,
							refercode1 : "makedate",
							addCondition : function(colName,value){
								return " and exists (select 1 from es_doc_main where doccode = t.missionprop1 and makedate =to_date('"+ value +"','yyyy-mm-dd'))";
							}
							
							},
						result6 : {title : "投保人客户号",style :"1"},
						result7 : {title : "被保人客户号",style :"1"},
						result8 : {
							title : " 管理机构  ",
							style :"2",
							colNo : 2,						
							refercode1 : "station",
							colName : "missionprop5",
							addCondition : function(colName , value){
								return " and "+ colName +" like '" + value
								+ "%' ";
							   }
							},
						result9 : {title : "代理人编码",style :"1",colNo : 6}
					}
				}
				
			},
			resultTitle : "个人工作池",
			result : {
				selBoxEventFuncName : "displayMissionInfo",
				selBoxEventFuncParam : "",
				condition : {
					"0" : false,
					"1" : false,
					"2" : false,
					"5" : "and managecom like'"
						+ comcode
						+ "%'  order by prtno"
				},
				mulLineCount : 0,
				arrayInfo : {
					col :{
						col5 : "0",
						col6 : "0",
						col7 : "0",
						newCol0:{
							title : " 初审日期  ",
							colName : "(select max(firsttrialdate) from lccont where contno= t.missionprop1)",
							rename : "firsttrialdate",							
							style : "0"						
							},
						result0 : {
							title : " 投保单号  ",
							style : "0",
							colNo : 1
						},
						result1 : {
							title : " 印  刷  号 ",
							style : "0",
							colNo : 2
						},
						result2 : {
							title : "险种编码",
							style : "0",
							colNo : 3
						},
						result3 : {
							title : " 投保人 ",
							style : "0",
							colNo : 4
						},
						result4 : {
							title : " 被保人 ",
							style : "0",
							colNo : 5
						},
						result5 : {
							title : " 扫描日期 ",
							style : "0",
							colNo : 6
						},
						result6 : {
							title : "投保人客户号",
							style : "3"
						},
						result7 : {
							title : "被保人客户号",
							style : "3"
						},
						result8 : {
							title : "管理机构",
							style : "3"
						},
						result9 : {
							title : "代理人编码",
							style : "3"
						}						
					}
				}
			}
		}
		
	};
	jQuery("#CustomerPool").workpool(config);
	jQuery("#privateSearch").click();
}
//end by lzf
/**function initClientListGrid()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		    //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";        			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单号";    	        //列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="印刷号";    	        //列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[3]=new Array();
      iArray[3][0]="险种编码"; 	            //列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="投保人"; 	            //列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;  

      iArray[5]=new Array();
      iArray[5][0]="被保人"; 	            //列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="初审日期"; 	            //列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=8;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[7]=new Array();
      iArray[7][0]="扫描日期"; 	            //列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=8;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="投保人客户号码"; 	            //列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;  
      
      iArray[9]=new Array();
      iArray[9][0]="被保人客户号码"; 	            //列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=1;  

	  iArray[10]=new Array();
      iArray[10][0]="管理机构";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="管理机构";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="工作流任务号";      //列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=200;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[13]=new Array();
      iArray[13][0]="工作流子任务号";    //列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=200;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[14]=new Array();
      iArray[14][0]="工作流活动Id";      //列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
      
      ClientListGrid = new MulLineEnter( "fm" , "ClientListGrid" ); 
      //这些属性必须在loadMulLine前
      ClientListGrid.mulLineCount = 0;   
      ClientListGrid.displayTitle = 1;
      ClientListGrid.canSel = 1;
      ClientListGrid.loadMulLine(iArray);  
      
      //ClientList.selBoxEventFuncName = "customerUnion";
       } 
      catch(ex) {
      alert(ex);
    }
}

function initOPolGrid() {                               
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
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="印刷号";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0; 
      
      
      iArray[4]=new Array();
      iArray[4][0]="投保人";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="被保人";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="初审日期";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=8;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[7]=new Array();
      iArray[7][0]="扫描日期";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="投保人客户号码";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="被保人客户号码号码";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[10]=new Array();
      iArray[10][0]="管理机构";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="管理机构";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="工作流任务号";      //列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=200;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[13]=new Array();
      iArray[13][0]="工作流子任务号";    //列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=200;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[14]=new Array();
      iArray[14][0]="工作流活动Id";      //列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
      
      OPolGrid = new MulLineEnter( "fm" , "OPolGrid" ); 
      //这些属性必须在loadMulLine前
      OPolGrid.mulLineCount = 1;   
      OPolGrid.displayTitle = 1;
      OPolGrid.locked = 1;
      OPolGrid.canSel = 1;
      OPolGrid.hiddenPlus = 1;
      OPolGrid.hiddenSubtraction = 1;
      OPolGrid.loadMulLine(iArray); 
      
      OPolGrid. selBoxEventFuncName = "displayMissionInfo"; 
      
      //这些操作必须在loadMulLine后面
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}*/


</script>
