<%
//程序名称：EdorNoticePrintInit.jsp
//程序功能：通知书打印
//创建日期：2005-08-03 15:39:06
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('OtherNo').value = '';
	document.all('NoticeNo').value = '';
	document.all('NoticeType').value = '';
	document.all('ContNo').value = '';
	document.all('EdorAcceptNo').value = '';
	document.all('NoticeTypeName').value = '';
	document.all('ManageCom').value = comcode.substring(0,6);
		
  }
  catch(ex)
  {
    alert("在EdorNoticePrintInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
 
  }
  catch(ex)
  {
    alert("在EdorNoticePrintInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    var NoticeType = fm.NoticeType.value;
    initNoticeGrid(NoticeType);   
    initManageCom(); //初始化管理机构，最长截取6位   
  }
  catch(re)
  {
    alert("EdorNoticePrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 var NoticeGrid;          //定义为全局变量，提供给displayMultiline使用
// 投保单信息列表的初始化
function initNoticeGrid(tNoticeType)
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
	  iArray[1][0]="通知书号";         		//列名
	  iArray[1][1]="140px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  switch(tNoticeType)
	  {
	    case   "":
	    case "30":    //分红业绩报告书
	                  iArray[2]=new Array();
                	  iArray[2][0]="保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                      iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	    
	    
	    	          iArray[4]=new Array();
	                  iArray[4][0]="客户姓名";        //列名
	                  iArray[4][1]="80px";            	//列宽
                  	  iArray[4][2]=100;            			//列最大值
                  	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许

                      iArray[5]=new Array();
	                  iArray[5][0]="业务员姓名";        //列名
	                  iArray[5][1]="80px";            	//列宽
	                  iArray[5][2]=100;            		//列最大值
	                  iArray[5][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	    
	                  iArray[6]=new Array();
	                  iArray[6][0]="保单生效日";        //列名
	                  iArray[6][1]="80px";            	//列宽
                 	  iArray[6][2]=100;            		//列最大值
                	  iArray[6][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[7]=new Array();
	                  iArray[7][0]="保单状态";        //列名
	                  iArray[7][1]="0px";            	//列宽
                 	  iArray[7][2]=100;            		//列最大值
                	  iArray[7][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[8]=new Array();
	                  iArray[8][0]="分红会计年度";        //列名
	                  iArray[8][1]="100px";            	//列宽
                 	  iArray[8][2]=100;            		//列最大值
                	  iArray[8][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	                  break;
	    case "34":    //团体分红业绩报告书
	                  iArray[2]=new Array();
                	  iArray[2][0]="团体保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                      iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	    
	    
	    	          iArray[4]=new Array();
	                  iArray[4][0]="投保单位";        //列名
	                  iArray[4][1]="80px";            	//列宽
                  	  iArray[4][2]=100;            			//列最大值
                  	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许

                      iArray[5]=new Array();
	                  iArray[5][0]="业务员姓名";        //列名
	                  iArray[5][1]="80px";            	//列宽
	                  iArray[5][2]=100;            		//列最大值
	                  iArray[5][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	    
	                  iArray[6]=new Array();
	                  iArray[6][0]="保单生效日";        //列名
	                  iArray[6][1]="80px";            	//列宽
                 	  iArray[6][2]=100;            		//列最大值
                	  iArray[6][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[7]=new Array();
	                  iArray[7][0]="保单状态";        //列名
	                  iArray[7][1]="0px";            	//列宽
                 	  iArray[7][2]=100;            		//列最大值
                	  iArray[7][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[8]=new Array();
	                  iArray[8][0]="分红会计年度";        //列名
	                  iArray[8][1]="100px";            	//列宽
                 	  iArray[8][2]=100;            		//列最大值
                	  iArray[8][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	                  break;
	    case "35":    //团体分红业绩报告书人名清单
	                  iArray[2]=new Array();
                	  iArray[2][0]="团体保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                      iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	    
	    
	    	          iArray[4]=new Array();
	                  iArray[4][0]="投保单位";        //列名
	                  iArray[4][1]="80px";            	//列宽
                  	  iArray[4][2]=100;            			//列最大值
                  	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许

                      iArray[5]=new Array();
	                  iArray[5][0]="业务员姓名";        //列名
	                  iArray[5][1]="80px";            	//列宽
	                  iArray[5][2]=100;            		//列最大值
	                  iArray[5][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	    
	                  iArray[6]=new Array();
	                  iArray[6][0]="保单生效日";        //列名
	                  iArray[6][1]="80px";            	//列宽
                 	  iArray[6][2]=100;            		//列最大值
                	  iArray[6][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[7]=new Array();
	                  iArray[7][0]="保单状态";        //列名
	                  iArray[7][1]="0px";            	//列宽
                 	  iArray[7][2]=100;            		//列最大值
                	  iArray[7][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[8]=new Array();
	                  iArray[8][0]="分红会计年度";        //列名
	                  iArray[8][1]="100px";            	//列宽
                 	  iArray[8][2]=100;            		//列最大值
                	  iArray[8][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	                  break;
	    case "BQ31":
	    case "BQ32":              
	    case "BQ27":
	    case "BQ28":  
	    
	                  iArray[2]=new Array();
                	  iArray[2][0]="保全受理号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                      iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许

	    	          iArray[4]=new Array();
	                  iArray[4][0]="客户姓名";        //列名
	                  iArray[4][1]="80px";            	//列宽
                  	  iArray[4][2]=100;            			//列最大值
                  	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	                  break;                            //信息通知书   
	    case "BQ48":
	    case "BQ49":  
	    case "BQ51":  
	    case "BQ52":  
	                  iArray[2]=new Array();
                	  iArray[2][0]="保全受理号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                      iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	                  break;
        
    	case "41":
    	case "42":   
    	case "BQ21":
    	case "BQ22": 
    	case "BQ29":
    	case "BQ30": 
    	case "BQ34":
    	case "BQ38": 
    	case "BQ39": 
    	case "BQ10":
    	case "BQ17":
	                  iArray[2]=new Array();
                	  iArray[2][0]="保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                      iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许

	    	          iArray[4]=new Array();
	                  iArray[4][0]="客户姓名";        //列名
	                  iArray[4][1]="80px";            	//列宽
                  	  iArray[4][2]=100;            			//列最大值
                  	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	    
                      iArray[5]=new Array();
	                  iArray[5][0]="业务员姓名";        //列名
	                  iArray[5][1]="80px";            	//列宽
	                  iArray[5][2]=100;            		//列最大值
	                  iArray[5][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[6]=new Array();
	                  iArray[6][0]="保单生效日";        //列名
	                  iArray[6][1]="80px";            	//列宽
                 	  iArray[6][2]=100;            		//列最大值
                	  iArray[6][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[7]=new Array();
	                  iArray[7][0]="保单状态";        //列名
	                  iArray[7][1]="0px";            	//列宽
                 	  iArray[7][2]=100;            		//列最大值
                	  iArray[7][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	    
	                  break;
    	case "BQ18":
	                  iArray[2]=new Array();
                	  iArray[2][0]="保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                      iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许

	    	          iArray[4]=new Array();
	                  iArray[4][0]="客户姓名";        //列名
	                  iArray[4][1]="80px";            	//列宽
                  	  iArray[4][2]=100;            			//列最大值
                  	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	    
                      iArray[5]=new Array();
	                  iArray[5][0]="业务员姓名";        //列名
	                  iArray[5][1]="80px";            	//列宽
	                  iArray[5][2]=100;            		//列最大值
	                  iArray[5][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[6]=new Array();
	                  iArray[6][0]="保单生效日";        //列名
	                  iArray[6][1]="80px";            	//列宽
                 	  iArray[6][2]=100;            		//列最大值
                	  iArray[6][3]=0; 					//是否允许输入,1表示允许，0表示不允许

                      iArray[7]=new Array();
	                  iArray[7][0]="保单状态";        //列名
	                  iArray[7][1]="0px";            	//列宽
                 	  iArray[7][2]=100;            		//列最大值
                	  iArray[7][3]=0; 					//是否允许输入,1表示允许，0表示不允许
	                  break;       
	    
    	case "BQ00":
	                  iArray[2]=new Array();
                	  iArray[2][0]="保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                    iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许


                    iArray[4]=new Array();
	                  iArray[4][0]="结算日期";        //列名
	                  iArray[4][1]="120px";            	//列宽
	                  iArray[4][2]=100;            			//列最大值
	                  iArray[4][3]=0; 		
                    break;
    	case "BQ01":
	                  iArray[2]=new Array();
                	  iArray[2][0]="保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                    iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许


                    iArray[4]=new Array();
	                  iArray[4][0]="结算日期";        //列名
	                  iArray[4][1]="120px";            	//列宽
	                  iArray[4][2]=100;            			//列最大值
	                  iArray[4][3]=0; 		
	                  
                    break;
                    
        case "BQ71":            
		case "BQ72":
	                  iArray[2]=new Array();
                	  iArray[2][0]="保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	                  
	                  iArray[3]=new Array();
                	  iArray[3][0]="保全受理号";       		//列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                    iArray[4]=new Array();
	                  iArray[4][0]="管理机构";        //列名
	                  iArray[4][1]="120px";            	//列宽
	                  iArray[4][2]=100;            			//列最大值
	                  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许


                    iArray[5]=new Array();
	                  iArray[5][0]="通知书生成日期";        //列名
	                  iArray[5][1]="120px";            	//列宽
	                  iArray[5][2]=100;            			//列最大值
	                  iArray[5][3]=0; 		
	                  
                    break;    
	      	                         
	    default:      
	                  iArray[2]=new Array();
                	  iArray[2][0]="保单号";       		//列名
	                  iArray[2][1]="120px";            	//列宽
	                  iArray[2][2]=100;            			//列最大值
	                  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

                      iArray[3]=new Array();
	                  iArray[3][0]="管理机构";        //列名
	                  iArray[3][1]="120px";            	//列宽
	                  iArray[3][2]=100;            			//列最大值
	                  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许

	                  break;              
	  }
		
      NoticeGrid = new MulLineEnter( "fm" , "NoticeGrid" ); 
      //这些属性必须在loadMulLine前
      NoticeGrid.mulLineCount = 10;   
      NoticeGrid.displayTitle = 1;
      NoticeGrid.canSel = 1;
      NoticeGrid.hiddenPlus=1;
      NoticeGrid.hiddenSubtraction=1;
      NoticeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
