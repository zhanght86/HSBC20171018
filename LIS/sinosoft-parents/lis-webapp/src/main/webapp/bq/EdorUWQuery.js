//***********************************************
//程序名称：EdorUWQuery.js
//程序功能：核保查询
//创建日期：2005-07-12 19:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}


/*********************************************************************
 *  查询合同信息 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function querycont() 
{
//   var strSql="select contno,(select concat(concat(trim(code),'-'),trim(codename) from ldcode where codetype = 'station' and code = ManageCom),"
//                   +" (select codename from ldcode where codetype = 'salechnl' and code = salechnl),"
//                   +" AgentCode,Remark "
//                   +" from LPCont where ContNo='"+ ContNo+ "'"
//                   +" and edorno = '"+EdorNo+"'";
   var strSql = "";
   var sqlid1="EdorUWQuerySql1";
   var mySql1=new SqlClass();
   mySql1.setResourceName("bq.EdorUWQuerySql"); //指定使用的properties文件名
   mySql1.setSqlId(sqlid1);//指定使用的Sql的id
   mySql1.addSubPara(ContNo);//指定传入的参数
   mySql1.addSubPara(EdorNo);//指定传入的参数
   strSql=mySql1.getString();
   var brr = easyExecSql(strSql);
   if(brr)
   {   
   	     brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNo.value  = brr[0][0];
         brr[0][1]==null||brr[0][1]=='null'?'0':fm.ManageCom.value  = brr[0][1];
         brr[0][2]==null||brr[0][2]=='null'?'0':fm.SaleChnl.value  = brr[0][2];
         brr[0][3]==null||brr[0][3]=='null'?'0':fm.AgentCode.value  = brr[0][3];
         brr[0][4]==null||brr[0][4]=='null'?'0':fm.Remark.value  = brr[0][4];
   }
   else
   {
//   	     strSql="select contno,(select concat(concat(trim(code),'-'),trim(codename)) from ldcode where codetype = 'station' and code = ManageCom),"
//   	               +" (select codename from ldcode where codetype = 'salechnl' and code = salechnl),"
//   	               +" AgentCode,Remark "
//                   +" from LCCont where ContNo='"+ ContNo+ "'";   	     
   	  var sqlid2="EdorUWQuerySql2";
      var mySql2=new SqlClass();
      mySql2.setResourceName("bq.EdorUWQuerySql"); //指定使用的properties文件名
      mySql2.setSqlId(sqlid2);//指定使用的Sql的id
      mySql2.addSubPara(ContNo);//指定传入的参数
      strSql=mySql2.getString();   	     
         brr = easyExecSql(strSql);  
         if(brr)
         {   
   	        brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNo.value  = brr[0][0];
            brr[0][1]==null||brr[0][1]=='null'?'0':fm.ManageCom.value  = brr[0][1];
            brr[0][2]==null||brr[0][2]=='null'?'0':fm.SaleChnl.value  = brr[0][2];
            brr[0][3]==null||brr[0][3]=='null'?'0':fm.AgentCode.value  = brr[0][3];
            brr[0][4]==null||brr[0][4]=='null'?'0':fm.Remark.value  = brr[0][4];
         } 
         else
         {
        	alert("合同信息查询失败！");
        	return "";
         }     
   }	
}

//查询被保人信息
function queryinsured()
{ 
//    var strSql = "select InsuredNo,Name,(select concat(concat(trim(code),'-'),trim(codename)) from ldcode where code = LPInsured.IDType and codetype = 'idtype'),IDNo"
//                      +" from lpinsured where 1=1"
//                      +" and ContNo='"+ContNo+"'"
//                      +" and EdorNo='"+EdorNo+"'"
//				      +" order by SequenceNo ";				      
    var strSql = "";
    var sqlid3="EdorUWQuerySql3";
    var mySql3=new SqlClass();
    mySql3.setResourceName("bq.EdorUWQuerySql"); //指定使用的properties文件名
    mySql3.setSqlId(sqlid3);//指定使用的Sql的id
    mySql3.addSubPara(ContNo);//指定传入的参数
    mySql3.addSubPara(EdorNo);//指定传入的参数
    strSql=mySql3.getString();    
	var brr = easyExecSql(strSql);
    if (brr)
    {
        initPolAddGrid();
        turnPage.queryModal(strSql, PolAddGrid);
    }
    else
    {   
//    	strSql = "select InsuredNo,Name,(select concat(concat(trim(code),'-'),trim(codename)) from ldcode where code = LCInsured.IDType and codetype = 'idtype'),IDNo"
//                      +" from lcinsured where 1=1"
//                      +" and ContNo='"+ContNo+"'"
//				      +" order by SequenceNo ";			      
    	var sqlid4="EdorUWQuerySql4";
        var mySql4=new SqlClass();
        mySql4.setResourceName("bq.EdorUWQuerySql"); //指定使用的properties文件名
        mySql4.setSqlId(sqlid4);//指定使用的Sql的id
        mySql4.addSubPara(ContNo);//指定传入的参数
        strSql=mySql4.getString();        
    	brr = easyExecSql(strSql);
    	if (brr)
        {
            initPolAddGrid();
            turnPage.queryModal(strSql, PolAddGrid);
        }
        else
        {	
           alert("被保人信息查询失败！");
    	   return "";
    	}
    }			  
}
//显示保全险种信息
function  showRiskInfo()
{
	
	var tSelNo = PolAddGrid.getSelNo()-1;
    var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);	
  
//	var strSql = "select a.polno,a.MainPolNo,a.riskcode,b.riskname,a.Prem,a.Amnt,a.CValiDate,a.EndDate,a.PayIntv,a.PayYears "
//	                        + " from LPPol a,lmrisk b where 1=1"
//							+ " and a.contno='"+ContNo+"'"
//							+ " and a.edorno='"+EdorNo+"'"
//							+ " and a.insuredno ='"+tInsuredNo+"'"
//							+ " and a.riskcode = b.riskcode";
	var strSql = "";
    var sqlid5="EdorUWQuerySql5";
    var mySql5=new SqlClass();
    mySql5.setResourceName("bq.EdorUWQuerySql"); //指定使用的properties文件名
    mySql5.setSqlId(sqlid5);//指定使用的Sql的id
    mySql5.addSubPara(ContNo);//指定传入的参数
    mySql5.addSubPara(EdorNo);//指定传入的参数
    mySql5.addSubPara(tInsuredNo);//指定传入的参数
    strSql=mySql5.getString();
	
    var brr=easyExecSql(strSql);
    if(brr)
    {
	    DivPolInfo.style.display = '';
	    initRiskGrid();						
	    turnPage.queryModal(strSql, RiskGrid);
	}
     else
	{
		DivPolInfo.style.display = 'none';
		return;
	}
} 

//险种核保轨迹查询
function showRiskResult()
{
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   
   window.open("./EdorRiskTraceMain.jsp?ContNo="+ContNo+"&PolNo="+tPolNo+"&EdorNo="+EdorNo,"", "");
	
}


//合同核保轨迹查询
function QueryContTrace()
{

	window.open("./EdorContTraceMain.jsp?ContNo="+ContNo+"&EdorNo="+EdorNo,"", ""); 
	
} 