//***********************************************
//程序名称：LLUWQueryClaim.js
//程序功能：投保人或者被保人理赔查询
//创建日期：2005-12-03 15:10:36
//创建人  ：万泽辉
//***********************************************
 

//全局变量
var showInfo;
var turnPage  = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="claimnb.LLUWQueryClaimInputSql";


/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent()
{
    top.close();	
}

/*********************************************************************
 *  函数说明：查询这个客户号下的所有的保单
 *  修改人  ：万泽辉
 *  修改日期： 2005/12/03
 *********************************************************************
 */

function queryClaim(){
	
	var strSQL = "";
    
    //查询立案以后信息
    /*strSQL = "(select a.rgtno,(select c.name from ldperson c where c.customerno = b.customerno),"
           + " b.accdate,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' "
           + " then '审核' when '40' then '审批' when '50' then '结案' when '60' then '完成' "
           + " when '70' then '关闭' end)"
           + " from llregister a,llcase b "
           + " where a.rgtno = b.caseno and b.caseno != '"+ClmNo+"'"
           + " and b.customerno = '"+fm.CustomerNo.value+"')" //出险人编码

    //联合查询报案信息
    strSQL = strSQL + " union "
           + "(select a.rptno,c.name,b.accdate,'报案'"
           + " from llreport a,llsubreport b,ldperson c "
           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
           + " and b.customerno = '"+fm.CustomerNo.value+"')" //出险人编码*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[ClmNo,fm.CustomerNo.value]);
    turnPage.queryModal(strSQL, ClaimGrid); 
    
    //如果没有理赔信息，则此按钮不可用
    if(ClaimGrid.mulLineCount == 0)
	{
	  	 fm.button1.disabled = true; 
	}
	if(ClaimGrid.mulLineCount < 5)
	{
	  	 fm.buttona.style.display="none";
	  	 fm.buttonb.style.display="none";
	  	 fm.buttonc.style.display="none";
	  	 fm.buttond.style.display="none";
	}
}

/*********************************************************************
 *  函数说明：查询客户号和客户姓名
 *  修改人  ：万泽辉
 *  修改时间：2005-10-26
 *********************************************************************
 */
function queryPersonInfo(){
    //var aSQL = "select customerno,name from ldperson where customerno='"+transferNo+"'";	
    var aSQL = wrapSql(tResourceName,"querysqldes2",[transferNo]);
    var arrResult         = easyExecSql(aSQL);
    fm.CustomerNo.value   = arrResult[0][0];
    fm.CustomerName.value = arrResult[0][1];
}

/*********************************************************************
 *  函数说明：将“既往赔案险种信息”列表中的“保单号”改为“合同号”
 *  修改人  ：万泽辉
 *  修改时间：2005-10-26
 *********************************************************************
 */

function getPolInfo(){
	
  var tSelNo    = ClaimGrid.getSelNo()-1;
  var tClmNo    = ClaimGrid.getRowColData(tSelNo,1);
  var tClmState = ClaimGrid.getRowColData(tSelNo,4);
  if(tClmState=="报案")
  {
  	  var strUrl="../claim/LLClaimQueryReportInputMain.jsp?claimNo="+tClmNo+"&phase=0";
  	  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  }
  else
  {
	  /*var aSQL = "select unique a.clmno,a.contno,(select b.riskname from lmrisk b where "
	           + " a.riskcode = b.riskcode),(select c.codename from ldcode c where "
	           + " c.codetype='llclaimconclusion' and a.givetype = c.code),a.realpay,a.operator "
	           + " from llclaimdetail a ,llregister b where a.clmno = b.rgtno and a.givetype"
	           + " in('0','1') and a.clmno='"+tClmNo+"'";*/
	  var aSQL = wrapSql(tResourceName,"querysqldes3",[tClmNo]);
	  turnPage2.queryModal(aSQL, PolGrid);
  }
  	
}

/*********************************************************************
 *  函数说明：影像资料查询
 *  修改人  ：万泽辉
 *  修改时间：2005-12-03
 *********************************************************************
 */
function showImage(){ 
	
	 var tsel=PolGrid.getSelNo()-1;
	 
	 if(tsel<0)
	 {
          alert("请选择保单!");
          return;	 
     }
     var ClmNo = PolGrid.getRowColData(tsel,1); //赔案号 Modify by zhaorx 2006-03-07
	 window.open("../claim/LLColQueryImageMain.jsp?claimNo="+ClmNo,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');    
    
} 
/*********************************************************************
 *  函数说明：既往理赔详细查询
 *  修改人  ：万泽辉
 *  修改时间：2005-12-03
 *********************************************************************
 */
function showDetail(){ 
	
	var tsel=PolGrid.getSelNo()-1;
	if(tsel<0)
	{
         alert("请选择赔案!");
   	     return;	 
    }
    var ClmNo=PolGrid.getRowColData(tsel,1); 
    if(ClmNo==null||ClmNo=="")
    {
    	alert("没有赔案险种信息！")
   	    return;
   	}
    else
	    window.open("../uw/ClaimDetailQueryMain.jsp?ClmNo="+ClmNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
} 

/*====================================================================
 *  函数说明：理赔核保查询
 *  修改人  ：万泽辉
 *  修改时间：2005-12-06
 *====================================================================
 */
 
function showClaimSecond()
{
	  var tSelNo    = ClaimGrid.getSelNo()-1;
	  if(tSelNo<0)
	  {
	  	   alert("请选择一条赔案！");
	  	   return;
	  }
	  var tCaseNo    = ClaimGrid.getRowColData(tSelNo,1);
	  var tInsuredNo = fm.CustomerNo.value;
	  var strUrl="../claim/LLDealUWsecondMain.jsp?CaseNo="+tCaseNo+"&InsuredNo="+tInsuredNo;    
	  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
}