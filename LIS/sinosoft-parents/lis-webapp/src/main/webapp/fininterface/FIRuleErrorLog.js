//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var confirmFlag=false;

var todayDate = new Date();
var sdate = todayDate.getDate();
var smonth= todayDate.getMonth() +1;
var syear= todayDate.getYear();
var sToDate = syear + "-" +smonth+"-"+sdate

//�ύ�����水ť��Ӧ����
function submitForm()
{   
   
   //���Բ����ڶ�Ӧ��ҵ�����
   //if(fm.OtherNo.value == null || fm.OtherNo.value == ""){   		
   //		alert("�������Ӧ��ҵ�����");
   //		return ;   	
   //} 


  
  var strSQL = "";
 
   if(1==1){
   	strSQL = getSqSQL();
   }
 
  fm.strSQLValue.value = strSQL;          
  turnPage.queryModal(strSQL, ErrLogGrid);
  if(ErrLogGrid.mulLineCount < 1){
  	alert("û���ҵ���Ӧ������");
  	return ;  	
  } 

}

function getSqSQL(){
/**
	var tSQL = "select distinct a.RuleDealBatchNo,(select b.rulename from FIBusinessRuleDef b where b.ruleid = a.RuleID),a.RuleDealResult,(select username from lduser where usercode=a.DealOperator),a.RuleDealDate,a.RuleID from FIBusinessRuleDealLog a where 1=1 and a.ruledealresult = 'Fail' and exists(select 'X' from fibusinessruledealerrlog g where g.ruleid = a.ruleid and a.ruledealbatchno = g.aserialno)  " ;
	  if(fm.StartDate.value!='')
  {
  	tSQL = tSQL + " and a.RuleDealDate >='"+fm.StartDate.value+"' ";
  }
  if(fm.EndDate.value!=''){
  	tSQL = tSQL + " and a.RuleDealDate <='"+fm.EndDate.value+"' ";
  }
  */
  var mySql1=new SqlClass();
	  mySql1.setResourceName("fininterface.FIRuleErrorLogSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql1.setSqlId("FIRuleErrorLogSql1");//ָ��ʹ�õ�Sql��id
	  mySql1.addSubPara(1);//ָ������Ĳ���
	  mySql1.addSubPara(fm.StartDate.value);//ָ������Ĳ���
	  mySql1.addSubPara(fm.EndDate.value);//ָ������Ĳ���
  var tSQL= mySql1.getString();
	return tSQL;
}
function getSqSQLErr(){
	
	
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRuleErrorLogSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("FIRuleErrorLogSql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.RuleDealBatchNo.value);//ָ������Ĳ���
	var tSQL= mySql2.getString();
	tSQL = "select  distinct c.aserialno,(select b.rulename from FIBusinessRuleDef b where b.ruleid = c.RuleID),c.errinfo,(select username from lduser where usercode=d.DealOperator),d.RuleDealDate from FIBusinessRuleDealErrLog c ,FIBusinessRuleDealLog d where c.aserialno='"+fm.RuleDealBatchNo.value+"'and c.aserialno=d.RuleDealBatchNo and  c.ruleid = d.ruleid and  d.ruledealresult = 'Fail' ";
	return tSQL;
}

function printFinFeeToExcel()
{
	var tNo = ErrLogGrid.getSelNo();
   if(ErrLogGrid.mulLineCount < 1){
  	   alert("û���ҵ���Ӧ������");
  	   return ;  	
   }
    	if (tNo==0||tNo==null)
	{
		alert("��ѡ��һ����¼");
		return;
	}
	 var strSQL1 = "";
	var tRuleDealBatchNo=ErrLogGrid.getRowColData(tNo-1,1);
	var flag=ErrLogGrid.getRowColData(tNo-1,3);
	if(flag=='Succ')
	{
		alert("�����ξ�У��û�в���");
	}
	if(flag=='Fail')
	{
		fm.RuleDealBatchNo.value=tRuleDealBatchNo;
		strSQL1 = getSqSQLErr();
    fm.strSQLValueErr.value = strSQL1 + " and c.ruleid = '" + ErrLogGrid.getRowColData(tNo-1,6) + "' ";   
    //alert(fm.strSQLValueErr.value);
    //return false;
    fm.method="post";
	  fm.action = "./FIRuleErrorLogToExcel.jsp";
	    fm.target="fraSubmit";
	    document.getElementById("fm").submit(); 
   }
	   
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}     

function returnParent()
{
    top.close();

}

function clearshowInfo()
{  
  
  showInfo.close();
}

function mf()
{  
 //fm.
}