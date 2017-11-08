<%
//程序名称：LLInqConclusionMissInit.jsp
//程序功能：调查结论页面初始化
//创建日期：2005-06-27
//创建人  ：yuejw
//更新记录：
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
   String tCurrentDate = PubFun.getCurrentDate();
%>        
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>     
   
<script language="JavaScript">
var CurrentDate="<%=tCurrentDate%>";

//接收参数
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
function initInpBox()
{ 
    try
    {                                   


    }
    catch(ex)
    {
        alert("在LLInqConclusionInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
        alert("在LLInqConclusionInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initParam();
        //initLLInqConclusionGrid();  
        //LLInqConclusionGridQuery();
        initConclusionInputPool()
    }
    catch(re)
    {
        alert("LLInqConclusionInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
//modify by lzf 
function initConclusionInputPool(){
	var config = {
			//activityId : "0000005165",
			functionId : "10060003",
			operator : operator,
			public : {
				show : false
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : " 赔案号    ", style : 1,colNo : 1},
							result1 : {title : "结论序号 ", style : 3},
							result2 : {title : "调查批次 ", style : 3},
							result3 : {title : "发起机构", style : 3},
							result4 : {title : "调查机构", style : 3},
							result5 : {title : "汇总标志 ", style : 3},
							result6 : {title : "赔案号备份", style : 3},
							result7 : {title : "机构", style : 3}
						}
					}
				},
				resultTitle : "调查结论",
				result : {
					selBoxEventFuncName : "LLInqConclusionGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " order by AcceptedDate,ClmNo"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "受理日期",
								style : 3,
								colName : " (select accepteddate  from llregister  where rgtno = t.missionprop1) ",
								rename : "AcceptedDate"
							},
							newCol1 : {
								title : "出险人编码",
								style : 1,
								colNo : 6,
								colName : " (select max(customerno)  from llsubreport  where trim(subrptno) = t.missionprop1) ",
								rename : "customerno"
							},
							newCol2 : {
								title : "出险人姓名",
								style : 1,
								colNo : 7,
								colName : " (select name from ldperson where customerno in (select max(customerno) from llsubreport where trim(subrptno) = t.missionprop1)) ",
								rename : "name"
							},
							result0 : {title : " 赔案号     ", style : 1,colNo : 1},
							result1 : {title : "结论序号 ", style : 1,colNo : 2},
							result2 : {title : "调查批次 ", style : 1,colNo : 3},
							result3 : {title : "发起机构", style : 1,colNo : 4},
							result4 : {title : "调查机构", style : 1,colNo : 5},
							result5 : {title : "汇总标志 ", style : 3},
							result6 : {
								title : "赔案号备份", 
								style : 3,
								colName : "MissionProp7",
								rename : "clmnoback"
								},
							result7 : {title : "机构", style : 3}
						}
					}
				}
			}
			
	}
	jQuery("#ConclusionInputPool").workpool(config);
	jQuery("#privateSearch").click();
}


//调查申请表初始化
/*function initLLInqConclusionGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                //列宽
      iArray[0][2]=8;                  //列最大值
      iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="立案号";             //列名
      iArray[1][1]="160px";                //列宽
      iArray[1][2]=160;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="结论序号";             //列名
      iArray[2][1]="100px";                //列宽
      iArray[2][2]=80;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="调查批次";             //列名
      iArray[3][1]="80px";                //列宽
      iArray[3][2]=80;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="发起机构";             //列名
      iArray[4][1]="80px";                //列宽
      iArray[4][2]=80;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="调查机构";             //列名
      iArray[5][1]="80px";                //列宽
      iArray[5][2]=80;                  //列最大值
      iArray[5][3]=0; 
              
	  iArray[6]=new Array();
	  iArray[6][0]="Missionid";             //列名
	  iArray[6][1]="0px";                //列宽
	  iArray[6][2]=200;                  //列最大值
	  iArray[6][3]=3;                    //是否允许输入,1表示允许，0表示不允许
	
	  iArray[7]=new Array();
	  iArray[7][0]="submissionid";             //列名
	  iArray[7][1]="0px";                //列宽
	  iArray[7][2]=200;                  //列最大值
	  iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许             
	     		     
	  iArray[8]=new Array();
	  iArray[8][0]="activityid";             //列名
	  iArray[8][1]="0px";                //列宽
	  iArray[8][2]=200;                  //列最大值
	  iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许   
	  
	  iArray[9]=new Array();
	  iArray[9][0]="出险人编码";             //列名
	  iArray[9][1]="80px";                //列宽
	  iArray[9][2]=200;                  //列最大值
	  iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许   
	  
	  iArray[10]=new Array();
	  iArray[10][0]="出险人姓名";             //列名
	  iArray[10][1]="80px";                //列宽
	  iArray[10][2]=200;                  //列最大值
	  iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许   	  	 

	  iArray[11]=new Array();
	  iArray[11][0]="受理日期";             //列名
	  iArray[11][1]="80px";                //列宽
	  iArray[11][2]=200;                  //列最大值
	  iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许   	  	   

      LLInqConclusionGrid = new MulLineEnter( "document" , "LLInqConclusionGrid" ); 
      //这些属性必须在loadMulLine前
      LLInqConclusionGrid.mulLineCount = 0;   
      LLInqConclusionGrid.displayTitle = 1;
      LLInqConclusionGrid.locked = 1;
      LLInqConclusionGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLInqConclusionGrid.selBoxEventFuncName = "LLInqConclusionGridClick"; //点击RadioBox时响应的函数名
      LLInqConclusionGrid.hiddenPlus=1;
      LLInqConclusionGrid.hiddenSubtraction=1;
      LLInqConclusionGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}
*/
</script>
