<%
//程序名称：LLSubmitApplyDealMissInit.jsp
//程序功能：呈报信息处理页面控件的初始化
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
//接收参数
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
    	//initLLSubmitApplyGrid();  
    	//initReLLSubmitApplyGrid();	
    	//easyQueryClick();    
    	//ReQueryClick();
    	initLLSubmitApplyPool();
    	initReLLSubmitApplyPool();
    	jQuery("#privateSearch").click();
     }
    catch(re)
    {
    	alert("LLSubmitApplyDealMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

function initLLSubmitApplyPool(){
	var config = {
			//activityId : "0000005105",
			functionId : "10050001",
			//operator : operator,
			public : {
				show : false
			},
			private : {
				id : "LLSubmitApplyPool",
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "  赔案号   ", style : 1 ,colNo : 1},
							result1 : {title : "呈报序号", style : 3},
							result2 : {title : "呈报次数", style : 3},
							result3 : {title : "出险人客户号", style : 3},
							result4 : {title : "出险人名称", style : 3},
							result5 : {title : "呈报类型", style : 3},
							result6 : {title : "呈报原因", style : 3},
							result7 : {title : "分公司主任核赔员", style : 3},
							result8 : {title : "机构", style : 3}
						}
					}
				},
				resultTitle : "选中呈报的具体信息",
				result : {
					selBoxEventFuncName : "LLSubmitApplyGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and SubType ='0' and MngCom like '"+comcode+"%' order by  ClmNo"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {title : "  赔案号   ", style : 1 ,colNo : 1},
							result1 : {title : "呈报序号", style : 1 ,colNo : 2},
							result2 : {title : "呈报次数", style : 1 ,colNo : 3},
							result3 : {title : "出险人客户号", style : 1 ,colNo : 4},
							result4 : {title : "出险人名称", style : 1 ,colNo : 5},
							result5 : {title : "呈报类型", style : 1 ,colNo : 6},
							result6 : {title : "呈报原因", style : 1 ,colNo : 7},
							result7 : {title : "分公司主任核赔员", style : 3},
							result8 : {title : "机构", style : 3}
						}
					}
				}
			}
	}
	jQuery("#LLSubmitApplyPool").workpool(config);
	
}

function initReLLSubmitApplyPool(){
	var config = {
			//activityId : "0000005105",
			functionId : "10050001",
			//operator : operator,
			public : {
				show : false
			},
			private : {
				id : "ReLLSubmitApplyPool",
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "  赔案号   ", style : 1 ,colNo : 1},
							result1 : {title : "呈报序号", style : 3},
							result2 : {title : "呈报次数", style : 3},
							result3 : {title : "出险人客户号", style : 3},
							result4 : {title : "出险人名称", style : 3},
							result5 : {title : "呈报类型", style : 3},
							result6 : {title : "呈报原因", style : 3},
							result7 : {title : "分公司主任核赔员", style : 3},
							result8 : {title : "机构", style : 3}
						}
					}
				},
				resultTitle : "选中呈报的具体信息",
				result : {
					selBoxEventFuncName : "ReLLSubmitApplyGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and SubType ='2' and MngCom = '"+comcode+"' order by ClmNo"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {title : "  赔案号   ", style : 1 ,colNo : 1},
							result1 : {title : "呈报序号", style : 1 ,colNo : 2},
							result2 : {title : "呈报次数", style : 1 ,colNo : 3},
							result3 : {title : "出险人客户号", style : 1 ,colNo : 4},
							result4 : {title : "出险人名称", style : 1 ,colNo : 5},
							result5 : {title : "呈报类型", style : 1 ,colNo : 6},
							result6 : {title : "呈报原因", style : 1 ,colNo : 7},
							result7 : {title : "分公司主任核赔员", style : 3},
							result8 : {title : "机构", style : 3}
						}
					}
				}
			}	
	}
	jQuery("#ReLLSubmitApplyPool").workpool(config);
}
//初始化"接收呈报队列"表格
/*function initLLSubmitApplyGrid()   
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
		    iArray[1][0]="赔案号";         		
		    iArray[1][1]="160px";         			
		    iArray[1][2]=160;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="呈报序号";         		
		    iArray[2][1]="80px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=0;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="呈报次数";         		
		    iArray[3][1]="50px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="出险人客户号";       
		    iArray[4][1]="100px";            	
		    iArray[4][2]=100;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="出险人姓名";        
		    iArray[5][1]="100px";            		
		    iArray[5][2]=100;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="呈报类型";        
		    iArray[6][1]="80px";            		
		    iArray[6][2]=100;            			  
		    iArray[6][3]=0;  
		      
		    iArray[7]=new Array();
		    iArray[7][0]="呈报原因";        
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
		    iArray[10][0]="activityid";             //列名
		    iArray[10][1]="80px";                //列宽
		    iArray[10][2]=200;                  //列最大值
		    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许        		    
		      		                  			 
		      LLSubmitApplyGrid= new MulLineEnter( "fm" , "LLSubmitApplyGrid"); 
		      LLSubmitApplyGrid.mulLineCount = 0;
		      LLSubmitApplyGrid.displayTitle = 1;
		      LLSubmitApplyGrid.canSel = 1;      //是否出现RadioBox
		      LLSubmitApplyGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		      LLSubmitApplyGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		      LLSubmitApplyGrid.selBoxEventFuncName = "LLSubmitApplyGridClick"; //单击RadioBox时响应函数
		      LLSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }
//反馈队列表格    
function initReLLSubmitApplyGrid()   
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
		    iArray[1][0]="赔案号";         		
		    iArray[1][1]="160px";         			
		    iArray[1][2]=10;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="呈报序号";         		
		    iArray[2][1]="80px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=0;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="呈报次数";         		
		    iArray[3][1]="50px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="出险人客户号";       
		    iArray[4][1]="100px";            	
		    iArray[4][2]=100;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="出险人姓名";        
		    iArray[5][1]="100px";            		
		    iArray[5][2]=100;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="呈报类型";        
		    iArray[6][1]="80px";            		
		    iArray[6][2]=100;            			  
		    iArray[6][3]=0;  
		      
		    iArray[7]=new Array();
		    iArray[7][0]="呈报原因";        
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
		    iArray[10][0]="activityid";             //列名
		    iArray[10][1]="80px";                //列宽
		    iArray[10][2]=200;                  //列最大值
		    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许        		    
		      		                  			 
		      ReLLSubmitApplyGrid= new MulLineEnter( "fm" , "ReLLSubmitApplyGrid"); 
		      ReLLSubmitApplyGrid.mulLineCount = 0;
		      ReLLSubmitApplyGrid.displayTitle = 1;
		      ReLLSubmitApplyGrid.canSel = 1;      //是否出现RadioBox
		      ReLLSubmitApplyGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		      ReLLSubmitApplyGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		      ReLLSubmitApplyGrid.selBoxEventFuncName = "ReLLSubmitApplyGridClick"; //单击RadioBox时响应函数
		      ReLLSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }    
    */
</script>
