<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWManuAddInit.jsp
//程序功能：人工核保加费
//创建日期：2005-07-16 11:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tContNo = "";
  String tPolNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tProposalNo = "";
  String tInsuredNo="";
  String tEdorNo="";
  String tEdorAcceptNo="";
tContNo = request.getParameter("ContNo");
tEdorNo = request.getParameter("EdorNo");
//tPolNo = request.getParameter("PolNo");
tMissionID = request.getParameter("MissionID");
tSubMissionID = request.getParameter("SubMissionID");
tInsuredNo = request.getParameter("InsuredNo");
tEdorAcceptNo = request.getParameter("EdorAcceptNo");
loggerDebug("EdorUWManuAddInit",tEdorAcceptNo);

%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";
var tRow = "";

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
	document.all('AddReason').value = '';
   }
  catch(ex)
  {
    alert("在EdorUWManuAddInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在EdorUWManuAddInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tEdorNo,tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo,tEdorAcceptNo)
{
  var str = "";
  try
  {
  fm.EdorAcceptNo.value = tEdorAcceptNo;
	initInpBox();
	initPolAddGrid();
	initHide(tEdorNo,tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo);
	QueryPolAddGrid(tEdorNo,tContNo,tInsuredNo);	
 }
  catch(re)
  {
   alert("在EdorUWManuAddInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
// 责任信息列表的初始化
function initUWSpecGrid(str)
{                              
    var iArray = new Array();
      
	try
	{
		//判断是团体加费还是个险加费，显示字段不同
		if (fm.OtherNoType.value == '4') //团体
		{

      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	        //列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[1][10] = "DutyCode";             			
      iArray[1][11] = str;
      iArray[1][12] = "1";
      iArray[1][19] = 1;	

      iArray[2]=new Array();
      iArray[2][0]="加费类型";    	        //列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择   
      iArray[2][10] = "PlanPay";             			
      iArray[2][11] = "0|^01|健康加费^02|职业加费^03|复效健康加费^04|复效职业加费";
      iArray[2][12] = "2";
      iArray[2][13] = "0";
      iArray[2][14] = "01";
      iArray[2][19]= 1 ;
             
      iArray[3]=new Array();
      iArray[3][0]="加费方式";         		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
      //iArray[3][4]="addmode";
      iArray[3][10] = "AddMode";             			
      iArray[3][11] = "0|^01|追溯加费|^02|下期加费";
      iArray[3][12] = "3";
      iArray[3][13] = "0";
      iArray[3][19]= 1 ;

      iArray[4]=new Array();
      iArray[4][0]="加费评点";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
      
      iArray[5]=new Array();
      iArray[5][0]="第二被保险人加费评点";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 

      iArray[6]=new Array();
      iArray[6][0]="加费对象";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;
      iArray[6][10] = "PayObject";             			
      iArray[6][11] = "0|^01|投保人^02|被保险人^03|多被保险人^04|第二被保险人";
      iArray[6][12] = "6";
      iArray[6][13] = "0";
      iArray[6][19]= 1 ;     
     
      iArray[7]=new Array();
      iArray[7][0]="加费金额";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
      iArray[7][7]="";
         
      iArray[8]=new Array();
      iArray[8][0]="加费编码";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
         
      iArray[9]=new Array();
      iArray[9][0]="加费起期";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
         
      iArray[10]=new Array();
      iArray[10][0]="交至日期";         	//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=100;            		//列最大值
      iArray[10][3]=0;              		//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
         
      iArray[11]=new Array();
      iArray[11][0]="加费止期";         	//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=100;            		//列最大值
      iArray[11][3]=0;              		//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
		
		}
		else
		{
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	        //列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[1][10] = "DutyCode";             			
      iArray[1][11] = str;
      iArray[1][12] = "1";
      iArray[1][19] = 1;	

      iArray[2]=new Array();
      iArray[2][0]="加费类型";    	        //列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择   
      iArray[2][10] = "PlanPay";             			
      iArray[2][11] = "0|^01|健康加费^02|职业加费^03|复效健康加费^04|复效职业加费";
      iArray[2][12] = "2";
      iArray[2][13] = "0";
      iArray[2][19]= 1 ;
             
      iArray[3]=new Array();
      iArray[3][0]="加费方式";         		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
      //iArray[3][4]="addmode";
      iArray[3][10] = "AddMode";             			
      iArray[3][11] = "0|^01|追溯加费|^02|下期加费";
      iArray[3][12] = "3";
      iArray[3][13] = "0";
      iArray[3][19]= 1 ;

      iArray[4]=new Array();
      iArray[4][0]="加费评点";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
      
      iArray[5]=new Array();
      iArray[5][0]="第二被保险人加费评点";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 

      iArray[6]=new Array();
      iArray[6][0]="加费对象";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=2;
      iArray[6][10] = "PayObject";             			
      iArray[6][11] = "0|^01|投保人^02|被保险人^03|多被保险人^04|第二被保险人";
      iArray[6][12] = "6";
      iArray[6][13] = "0";
      iArray[6][19]= 1 ;     
     
      iArray[7]=new Array();
      iArray[7][0]="加费金额";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
      iArray[7][7]="CalHealthAddFee";
         
      iArray[8]=new Array();
      iArray[8][0]="加费编码";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
         
      iArray[9]=new Array();
      iArray[9][0]="加费起期";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
         
      iArray[10]=new Array();
      iArray[10][0]="交至日期";         	//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=100;            		//列最大值
      iArray[10][3]=0;              		//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
         
      iArray[11]=new Array();
      iArray[11][0]="加费止期";         	//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=100;            		//列最大值
      iArray[11][3]=0;              		//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
		}
         

      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" ); 
      //这些属性必须在loadMulLine前                            
      SpecGrid.mulLineCount = 1;
      SpecGrid.canSel = 0;
      SpecGrid.displayTitle = 1;
      SpecGrid.loadMulLine(iArray);  
       //这些操作必须在loadMulLine后面
       
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initPolAddGrid()
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
      iArray[1][0]="批单号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批改类型";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="保单险种号";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="印刷号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="险种编码";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=80;            			//列最大值
      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[6][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[6][5]="4";              	                //引用代码对应第几列，'|'为分割符
      iArray[6][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[6][18]=250;
      iArray[6][19]=0 ;

      iArray[7]=new Array();
      iArray[7][0]="险种版本";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="投保人";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="被保人";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="保费";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="主险号";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //这些属性必须在loadMulLine前
      PolAddGrid.mulLineCount = 3;   
      PolAddGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.hiddenSubtraction = 1;
      PolAddGrid.loadMulLine(iArray);       
      PolAddGrid.selBoxEventFuncName = "getPolGridCho";
     
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tEdorNo,tPolNo,tContNo,tMissionID, tSubMissionID,tInsuredNo)
{        
  document.all('EdorNo').value = tEdorNo;
	document.all('PolNo').value = tPolNo;
	document.all('ContNo').value = tContNo;
	document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMissionID;
	document.all('InsuredNo').value = tInsuredNo;
	
//     var strSQL;
//     strSQL =  " select othernotype from lpedorapp where edoracceptno = '" + document.all('EdorAcceptNo').value + "' ";
    var strSQL = "";
    var sqlid1="EdorUWManuAddInitSql1";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.EdorUWManuAddInitSql"); //指定使用的properties文件名
    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    mySql1.addSubPara(tGrpContNo);//指定传入的参数
    strSQL=mySql1.getString();    
    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNoType.value  = brr[0][0];
    }


}

</script>


