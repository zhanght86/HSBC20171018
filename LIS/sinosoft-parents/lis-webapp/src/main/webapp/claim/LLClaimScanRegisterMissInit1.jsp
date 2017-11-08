<%
//程序名称：LLClaimRegisterMissInit.jsp
//程序功能：
//创建日期：2005-6-6 11:30
//创建人  ：zl
//更新记录：
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
   String tCurrentDate = PubFun.getCurrentDate();
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
var CurrentDate="<%=tCurrentDate%>";

//接收参数
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
    document.all('CurDate').value = CurrentDate;

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

// 输入框的初始化
function initInpBox()
{

  try
  {                                   
  }
  catch(ex)
  {
    alert("在LLClaimRegisterMissInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("sex","0=男&1=女&2=不详");        
  }
  catch(ex)
  {
    alert("在LLClaimRegisterMissInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
   /* initLLClaimRegisterGrid();
    initSelfLLClaimRegisterGrid();
    queryGrid();
    querySelfGrid();
    */ 
    initScanClaimPool();
  }
  catch(re)
  {
    alert("LLClaimRegisterMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.name +":"+re.message);
  }
}

 function initScanClaimPool(){
	 var sql1 = "";
	 var sql2 = "";
	 if(_DBT==_DBO){
		 sql1 = "(case (select 1 from LLRegisterIssue where IssueConclusion='01' And rgtno=t.missionprop1 and rownum=1) when 1 then '通过' else '未通过' end) ";
	 }else if(_DBT==_DBM){
		 sql1 = "(case (select 1 from LLRegisterIssue where IssueConclusion='01' And rgtno=t.missionprop1 limit 0,1) when 1 then '通过' else '未通过' end) ";
	 }
     if(_DBT==_DBO){
		 sql2 = "(case (select 1 from LLRegisterIssue where IssueConclusion='01' And rgtno=t.missionprop1 and rownum=1) when 1 then 1 else 0 end) ";
	 }else if(_DBT==_DBM){
		 sql2 = "(case (select 1 from LLRegisterIssue where IssueConclusion='01' And rgtno=t.missionprop1 limit 0,1) when 1 then 1 else 0 end) ";
	 }
	var config = {
		//activityID : "0000005010",	
		functionId : "10030002",
		operator : operator,
		public : {
			show : true,
			query :{
				queryButton : {
					"1" : {
						title : "申  请",
						func : "Apply"
					}
				},
				arrayInfo : {
					col : {							
						result0 : {
							title : " 立  案  号 ",
							style : 1,
							colNo : 1
							},
						result1 : {title : "案件状态",style : 3},
						result2 : {title : "事前扫描单证类型",style : 3},
						result3 : {
							title : "  申请书号  ",
							style : 1,
							colNo : 2
							},
						result4 : {
							title : " 扫描日期 ",
							style : 8,
							colNo : 3
							},
						result5 : {title : "扫描时间",style : 3},
						result6 : {
							title : " 扫描人员 ",
							style : 1,
							colNo : 5
							},
						result7 : {title : "初审结论",style : 3},
						result8 : {title : "初审人员",style : 3},
						result9 : {title : "立案时间",style : 3},
						result10 : {
							title : " 扫描机构 ",
							style : 1,
							colNo : 4,
							refercode1 : "station",
							colName : "missionprop20",
							addCondition : function(colName,value){
								return " and " + colName + " like '" + value
								+ "%'" ;
							}
							}						
					}
				}
			},
			resultTitle : "共享池",
			result : {
				condition : {
					"0" : false,
					"1" : false,
					"2" : false,
					"3" : false,
					"4" : true,
					"5" : "and not exists (select 1 from LLRegisterIssue where RgtNo=RgtNo1 and IssueConclusion in ('02','03') and IssueReplyDate is null)"
						+ " and MngCom like '" +comcode
						+ "%' order by RgtNo1"
				},
				mulLineCount : 0,
				arrayInfo : {
				  col : {
					col5 : "0",
					col6 : "0",
					col7 : "0",					
					newCol0 : {
						title : " 初审结论 ",
						colName : "(case (select 1 from LLRegisterIssue where IssueConclusion='01' And rgtno=t.missionprop1) when 1 then '通过' else '未通过' end) ",
						rename : "firstconclusion",
						style : 1,
						colNo : 6
					},
					result0 : {
						title : " 立  案  号 ",
						style : 1,
						colNo : 1,
						rename : "RgtNo1"
						},
					result1 : {title : "案件状态",style : 3},
					result2 : {title : "事前扫描单证类型",style : 3},
					result3 : {
						title : " 申请书号   ",
						style : 1,
						colNo : 5
						},
					result4 : {
						title : " 扫描日期 ",
						style : 1,
						colNo : 2
						},
					result5 : {title : "扫描时间",style : 3},
					result6 : {
						title : " 扫描人员 ",
						colName : "(select username from lduser where usercode = t.Missionprop11) ",
						rename : "ScanOperator",
						style : 1,
						colNo : 4
						},
					result7 : {title : "初审结论",style : 3},
					result8 : {title : "初审人员",style : 3},
					result9 : {title : "立案时间",style : 3},
					result10 : {
						title : " 扫描机构 ",
						style : 1,
						colNo : 3
						}
				  }
				}
			}
		},
		private : {
            query :{
				queryButton : {},
				arrayInfo : {
					col : {
						result0 : {
							title : " 立  案  号  ",
							style : 1,
							colNo : 1
							},
						result1 : {title : "案件状态",style : 3},
						result2 : {title : "事前扫描单证类型",style : 3},
						result3 : {title : "事前扫描单证号",style : 3},
						result4 : {
							title : "扫描日期",
							style : 3
							},
						result5 : {title : "扫描时间",style : 3},
						result6 : {
							title : "扫描人员",
							style : 3
							},
						result7 : {title : "初审结论",style : 3},
						result8 : {title : "初审人员",style : 3},
						result9 : {title : "立案时间",style : 3},
						result10 : {
							title : "扫描机构",
							style : 3
							}
					}
				}
			},
			resultTitle : "工作队列",
			result : {
				selBoxEventFuncName : "SelfLLClaimRegisterGridClick1",
				selBoxEventFuncParam : "",
				condition : {
					"0" : false,
					"1" : false,
					"2" : false,
					"5" : "and MngCom like'"
						+ comcode
						+ "%'  order by rgtno"
				},
				mulLineCount : 0,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",						
						newCol0 : {
							title : "初审结论 ",
							colName : sql1,
							rename : "firstconclusion",
							style : 1,
							colNo : 3
						},
						newCol1 : {
							title : "初审结论标志 ",
							colName : sql2,
							rename : "firstconclusionflag",
							style : 3
						},
						newCol2 : {
							title : "是否立案 ",
							colName : "'未立案'",
							rename : "isclaim",
							style : 1,
							colNo : 2
						},
						newCol3 : {
							title : "是否立案标志 ",
							colName : "0",
							rename : "isclaimflag",
							style : 3
						},
						newCol4 : {
							title : "客户号",
							colName : "' '",
							rename : "customerno",
							style : 1,
							colNo : 7
						},
						newCol5 : {
							title : "姓  名",
							colName : "' '",
							rename : "customername",
							style : 1,
							colNo : 8
						},					
						result0 : {
							title : " 立  案  号 ",
							style : 1,
							colNo : 1
							},
						result1 : {title : "案件状态",style : 3},
						result2 : {title : "事前扫描单证类型",style : 3},
						result3 : {
							title : " 申请书号   ",
							style : 1,
							colNo : 9
							},
						result4 : {
							title : " 扫描日期 ",
							style : 1,
							colNo : 4
							},
						result5 : {title : "扫描时间",style : 3},
						result6 : {
							title : " 扫描人员",
							colName : "(select username from lduser where usercode = t.Missionprop11) ",
							rename : "ScanOperator",
							style : 1,
							colNo : 6
							},
						result7 : {title : "初审结论",style : 3},
						result8 : {title : "初审人员",style : 3},
						result9 : {title : "立案时间",style : 3},
						result10 : {
							title : " 扫描机构 ",
							style : 1,
							colNo : 5
							}
					}
				}
			}
		}
	}
	jQuery("#ScanClaimPool").workpool(config);
	jQuery("#publicSearch").click();
	jQuery("#privateSearch").click();	
}

//清空页面
/*function emptyForm() 
{
	//emptyFormElements();     //清空页面所有输入框，在COMMON.JS中实现
   
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
	spanDutyGrid.innerHTML="";
}
*/
// 报案信息列表的初始化
/*function initLLClaimRegisterGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=10;                  //列最大值
    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="立案号";             //列名
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="扫描日期";             //列名
    iArray[2][1]="70px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="扫描机构";             //列名
    iArray[3][1]="70px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="扫描人员";             //列名
    iArray[4][1]="60px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="申请书号";             //列名
    iArray[5][1]="150px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="初审结论";             //列名
    iArray[6][1]="60px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0; 


    iArray[7]=new Array();
    iArray[7][0]="初审结论标志";             //列名
    iArray[7][1]="0px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=3; 

    
    iArray[8]=new Array();
    iArray[8][0]="Missionid";             //列名
    iArray[8][1]="0px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="submissionid";             //列名
    iArray[9][1]="0px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="activityid";             //列名
    iArray[10][1]="0px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许            
    
    
    LLClaimRegisterGrid = new MulLineEnter( "fm" , "LLClaimRegisterGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimRegisterGrid.mulLineCount = 0;   
    LLClaimRegisterGrid.displayTitle = 1;
    LLClaimRegisterGrid.locked = 0;
//    LLClaimRegisterGrid.canChk = 1;
    LLClaimRegisterGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
//        LLClaimRegisterGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项     
    LLClaimRegisterGrid.hiddenPlus=1;
    LLClaimRegisterGrid.hiddenSubtraction=1;
    LLClaimRegisterGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 报案信息列表的初始化
function initSelfLLClaimRegisterGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=10;                  //列最大值
    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="立案号";             //列名
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="是否立案";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="是否立案标志";             //列名
    iArray[3][1]="0px";                //列宽
    iArray[3][2]=100;                  //列最大值
    iArray[3][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="初审结论";             //列名
    iArray[4][1]="100px";                //列宽
    iArray[4][2]=100;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="初审结论标志";             //列名
    iArray[5][1]="0px";                //列宽
    iArray[5][2]=100;                  //列最大值
    iArray[5][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="扫描日期";             //列名
    iArray[6][1]="100px";                //列宽
    iArray[6][2]=100;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="扫描机构";             //列名
    iArray[7][1]="100px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="扫描人员";             //列名
    iArray[8][1]="100px";                //列宽
    iArray[8][2]=500;                  //列最大值
    iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="客户号";             //列名
    iArray[9][1]="100px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="客户姓名";             //列名
    iArray[10][1]="80px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[11]=new Array();
    iArray[11][0]="申请书号";             //列名
    iArray[11][1]="80px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=0; 
    
    iArray[12]=new Array();
    iArray[12][0]="Missionid";             //列名
    iArray[12][1]="0px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[13]=new Array();
    iArray[13][0]="submissionid";             //列名
    iArray[13][1]="0px";                //列宽
    iArray[13][2]=200;                  //列最大值
    iArray[13][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[14]=new Array();
    iArray[14][0]="activityid";             //列名
    iArray[14][1]="0px";                //列宽
    iArray[14][2]=200;                  //列最大值
    iArray[14][3]=3;                    //是否允许输入,1表示允许，0表示不允许      

    iArray[15]=new Array();
    iArray[15][0]="activityid";             //列名
    iArray[15][1]="0px";                //列宽
    iArray[15][2]=200;                  //列最大值
    iArray[15][3]=3;                    //是否允许输入,1表示允许，0表示不允许                  
    
    SelfLLClaimRegisterGrid = new MulLineEnter( "fm" , "SelfLLClaimRegisterGrid" ); 
    //这些属性必须在loadMulLine前
    SelfLLClaimRegisterGrid.mulLineCount = 0;   
    SelfLLClaimRegisterGrid.displayTitle = 1;
    SelfLLClaimRegisterGrid.locked = 0;
//    SelfLLClaimRegisterGrid.canChk = 1;
    SelfLLClaimRegisterGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    SelfLLClaimRegisterGrid.selBoxEventFuncName ="SelfLLClaimRegisterGridClick"; //响应的函数名，不加扩号
//        SelfLLClaimRegisterGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项    
    SelfLLClaimRegisterGrid.hiddenPlus=1;
    SelfLLClaimRegisterGrid.hiddenSubtraction=1;
    SelfLLClaimRegisterGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
