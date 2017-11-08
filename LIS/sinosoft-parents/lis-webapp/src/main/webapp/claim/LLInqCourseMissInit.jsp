<%
//程序名称：LLSubmitApplyDealMissInit.jsp
//程序功能：呈报信息处理页面控件的初始化
%>
<!--用户校验类-->
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

function initForm()
{
	try
    {
    	initParam();
    	//initLLInqApplyGrid();  
    	//easyQueryClick();   
    	initCourseInputPool();
     }
    catch(re)
    {
    	alert("LLSubmitApplyDealMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//modify by lzf
function initCourseInputPool(){
	var config = {
			//activityId : "0000005145",
			functionId : "10060002",
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
							result1 : {title : "调查序号", style : 3},
							result2 : {title : "出险人客户号", style : 3},
							result3 : {title : "出险人名称", style : 3},
							result4 : {title : "分配调查任务机构", style : 3},
							result5 : {title : "任务分配日期", style : 3},
							result6 : {title : "指定的调查人", style : 3},
							result7 : {title : "赔案号备份", style : 3},
							result8 : {title : "机构", style : 3}
						}
					}
				},
				resultTitle : "获取调查任务队列",
				result : {
					selBoxEventFuncName : "LLInqApplyGridClick",
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
								title : "指定的调查人",
								style : 1,
								ColNo : 7,
								colName : " (select username from lduser where usercode = t.DefaultOperator) ",
								rename : "username"
							},
							result0 : {title : " 赔案号     ", style : 1,colNo : 1},
							result1 : {title : "调查序号  ", style : 1,colNo : 2},
							result2 : {title : "出险人编码", style : 1,colNo : 3},
							result3 : {title : "出险人名称", style : 1,colNo : 4},
							result4 : {title : "任务分配机构", style : 1,colNo : 5},
							result5 : {title : "任务分配日期", style : 1,colNo : 6},
							result6 : {title : "指定的调查人", style : 3},
							result7 : {
								title : "赔案号备份", 
								style : 3,
								colName : "MissionProp8",
								rename : "clmnoback"
								},
							result8 : {title : "机构", style : 3}	
						}
					}
				}
			}
				
	}
	jQuery("#CourseInputPool").workpool(config);
	jQuery("#privateSearch").click();
}

//end

//初始化"接收呈报队列"表格
/*function initLLInqApplyGrid()   
	{
    	var iArray = new Array();   	
      	try
        {
			iArray[0]=new Array();
		    iArray[0][0]="序号";    	 //列名
		    iArray[0][1]="30px";          //列宽
		    iArray[0][2]=100;            //列最大值
		    iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许
		
		    iArray[1]=new Array();
		    iArray[1][0]="立案号";         		
		    iArray[1][1]="160px";         			
		    iArray[1][2]=160;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="调查序号";         		
		    iArray[2][1]="100px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=0;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="出险人编码";         		
		    iArray[3][1]="100px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="出险人姓名";       
		    iArray[4][1]="100px";            	
		    iArray[4][2]=100;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="任务分配机构";        
		    iArray[5][1]="80px";            		
		    iArray[5][2]=100;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="任务分配日期";        
		    iArray[6][1]="80px";            		
		    iArray[6][2]=100;            			  
		    iArray[6][3]=0;  
		      
		    iArray[7]=new Array();
		    iArray[7][0]="指定的调查人";        
		    iArray[7][1]="100px";            		
		    iArray[7][2]=100;            			  
		    iArray[7][3]=0;  
		    
		    iArray[8]=new Array();
		    iArray[8][0]="Missionid";             //列名
		    iArray[8][1]="100px";                //列宽
		    iArray[8][2]=200;                  //列最大值
		    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
		
		    iArray[9]=new Array();
		    iArray[9][0]="submissionid";             //列名
		    iArray[9][1]="100px";                //列宽
		    iArray[9][2]=200;                  //列最大值
		    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许             
		     		     
		    iArray[10]=new Array();
		    iArray[10][0]="activityid";             //列名o
		    iArray[10][1]="80px";                //列宽
		    iArray[10][2]=200;                  //列最大值
		    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许        		    

		    iArray[11]=new Array();
		    iArray[11][0]="受理日期";             //列名o
		    iArray[11][1]="80px";                //列宽
		    iArray[11][2]=200;                  //列最大值
		    iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许        		    
		      		                  			 
		      LLInqApplyGrid= new MulLineEnter( "document" , "LLInqApplyGrid"); 
		      LLInqApplyGrid.mulLineCount = 0;
		      LLInqApplyGrid.displayTitle = 1;
		      LLInqApplyGrid.canSel = 1;      //是否出现RadioBox
		      LLInqApplyGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		      LLInqApplyGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		      LLInqApplyGrid.selBoxEventFuncName = "LLInqApplyGridClick"; //单击RadioBox时响应函数
		      LLInqApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }
*/
</script>
