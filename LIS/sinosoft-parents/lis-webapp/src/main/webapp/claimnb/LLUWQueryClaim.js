//***********************************************
//�������ƣ�LLUWQueryClaim.js
//�����ܣ�Ͷ���˻��߱����������ѯ
//�������ڣ�2005-12-03 15:10:36
//������  �������
//***********************************************
 

//ȫ�ֱ���
var showInfo;
var turnPage  = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="claimnb.LLUWQueryClaimInputSql";


/*********************************************************************
 *  ������һҳ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function returnParent()
{
    top.close();	
}

/*********************************************************************
 *  ����˵������ѯ����ͻ����µ����еı���
 *  �޸���  �������
 *  �޸����ڣ� 2005/12/03
 *********************************************************************
 */

function queryClaim(){
	
	var strSQL = "";
    
    //��ѯ�����Ժ���Ϣ
    /*strSQL = "(select a.rgtno,(select c.name from ldperson c where c.customerno = b.customerno),"
           + " b.accdate,(case a.clmstate when '10' then '����' when '20' then '����' when '30' "
           + " then '���' when '40' then '����' when '50' then '�᰸' when '60' then '���' "
           + " when '70' then '�ر�' end)"
           + " from llregister a,llcase b "
           + " where a.rgtno = b.caseno and b.caseno != '"+ClmNo+"'"
           + " and b.customerno = '"+fm.CustomerNo.value+"')" //�����˱���

    //���ϲ�ѯ������Ϣ
    strSQL = strSQL + " union "
           + "(select a.rptno,c.name,b.accdate,'����'"
           + " from llreport a,llsubreport b,ldperson c "
           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
           + " and b.customerno = '"+fm.CustomerNo.value+"')" //�����˱���*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[ClmNo,fm.CustomerNo.value]);
    turnPage.queryModal(strSQL, ClaimGrid); 
    
    //���û��������Ϣ����˰�ť������
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
 *  ����˵������ѯ�ͻ��źͿͻ�����
 *  �޸���  �������
 *  �޸�ʱ�䣺2005-10-26
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
 *  ����˵�������������ⰸ������Ϣ���б��еġ������š���Ϊ����ͬ�š�
 *  �޸���  �������
 *  �޸�ʱ�䣺2005-10-26
 *********************************************************************
 */

function getPolInfo(){
	
  var tSelNo    = ClaimGrid.getSelNo()-1;
  var tClmNo    = ClaimGrid.getRowColData(tSelNo,1);
  var tClmState = ClaimGrid.getRowColData(tSelNo,4);
  if(tClmState=="����")
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
 *  ����˵����Ӱ�����ϲ�ѯ
 *  �޸���  �������
 *  �޸�ʱ�䣺2005-12-03
 *********************************************************************
 */
function showImage(){ 
	
	 var tsel=PolGrid.getSelNo()-1;
	 
	 if(tsel<0)
	 {
          alert("��ѡ�񱣵�!");
          return;	 
     }
     var ClmNo = PolGrid.getRowColData(tsel,1); //�ⰸ�� Modify by zhaorx 2006-03-07
	 window.open("../claim/LLColQueryImageMain.jsp?claimNo="+ClmNo,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');    
    
} 
/*********************************************************************
 *  ����˵��������������ϸ��ѯ
 *  �޸���  �������
 *  �޸�ʱ�䣺2005-12-03
 *********************************************************************
 */
function showDetail(){ 
	
	var tsel=PolGrid.getSelNo()-1;
	if(tsel<0)
	{
         alert("��ѡ���ⰸ!");
   	     return;	 
    }
    var ClmNo=PolGrid.getRowColData(tsel,1); 
    if(ClmNo==null||ClmNo=="")
    {
    	alert("û���ⰸ������Ϣ��")
   	    return;
   	}
    else
	    window.open("../uw/ClaimDetailQueryMain.jsp?ClmNo="+ClmNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
} 

/*====================================================================
 *  ����˵��������˱���ѯ
 *  �޸���  �������
 *  �޸�ʱ�䣺2005-12-06
 *====================================================================
 */
 
function showClaimSecond()
{
	  var tSelNo    = ClaimGrid.getSelNo()-1;
	  if(tSelNo<0)
	  {
	  	   alert("��ѡ��һ���ⰸ��");
	  	   return;
	  }
	  var tCaseNo    = ClaimGrid.getRowColData(tSelNo,1);
	  var tInsuredNo = fm.CustomerNo.value;
	  var strUrl="../claim/LLDealUWsecondMain.jsp?CaseNo="+tCaseNo+"&InsuredNo="+tInsuredNo;    
	  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
}