//***********************************************
//�������ƣ�AmntAccumulate.js
//�����ܣ������ۼ�
//�������ڣ�2005-06-01 11:10:36
//������  ��HL
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();

var sqlresourcename = "uwgrp.AmntAccumulateSql";

/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	if (FlagStr == "Fail" )
	{             
  	showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		//[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
	}
	if (FlagStr == "Succ")
	{
  	showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		//[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
	}
	
}



/*********************************************************************
 *  ������һҳ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}



/*********************************************************************
 *  ��ѯ���ౣ���ۼ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryAmntAccu(){
//  var aSQL="select (select c.RiskSortValue from lmrisksort c where c.riskcode=a.riskcode and c.RiskSortType='3' ),sum(a.amnt),sum(a.mult),sum(a.amnt) from lcpol a, lmriskapp b where a.insuredno='"+customerNo+"' and b.riskcode=a.riskcode group by a.riskcode ";	
//  var aSQL="select a.riskcode,sum(a.amnt),sum(a.mult),SUM((select HEALTHYAMNT2('" + customerNo + "',a.riskcode) from dual where 1=1)) from lcpol a, lmriskapp b where a.insuredno='"+customerNo+"' and b.riskcode=a.riskcode group by c.RiskSortValue ";	
  //var aSQL="select a.riskcode,sum(a.amnt),sum(a.mult),SUM((select HEALTHYAMNT2('" + customerNo + "',a.riskcode) from lcpol a, lmriskapp b where a.insuredno='"+customerNo+"' and b.riskcode=a.riskcode group by a.riskcode";	
  //var aSQL="select c.RiskSortValue,sum(HEALTHYAMNT2('"+customerNo+"',a.riskcode,c.RiskSortValue)),sum(a.mult),sum(HEALTHYAMNTFB('"+customerNo+"',a.riskcode,c.RiskSortValue)) from lcpol a, lmriskapp b,lmrisksort c where a.insuredno='"+customerNo+"' and b.riskcode=a.riskcode and c.riskcode=a.riskcode and c.RiskSortType='3' group by c.RiskSortValue"
/******
  var aSQL="SELECT RiskSortValue,"+
       " HEALTHYAMNT2('"+customerNo+"', '', RiskSortValue),"+
       " nvl(s_s_Mult, 0),"+
       " HEALTHYAMNTFB('"+customerNo+"', '', RiskSortValue)"+
  " FROM (select RiskSortValue, sum(s_Mult) as s_s_Mult"+
          " from (select distinct RiskSortValue,"+
                                " (select sum(Mult)"+
                                   " from LCPol"+
                                  " where RiskCode = a.RiskCode"+
                                    " and insuredno = '"+customerNo+"') as s_Mult"+
                  " from lmrisksort a"+
                 " where RiskSortType = '3' and risksortvalue not in ('0'))"+
         " group by RiskSortValue)";
         
************/   







/*      
   var aSQL =
   
          "SELECT s_RiskSortValue,"+
              " HEALTHYAMNT3('"+customerNo+"', '', s_RiskSortValue),"+
              " s_Mult,"+
              " HEALTHYAMNTFB2('"+customerNo+"', '', s_RiskSortValue)"+
         " FROM (select distinct RiskSortValue as s_RiskSortValue,"+
                               " sum(c.mult) as s_Mult"+
                 " from lmrisksort a, lcpol c"+
                " where RiskSortType = '3'"+
                  " and a.riskcode = c.riskcode"+
               " and (c.insuredno = '"+customerNo+"'"+
  /***  
 " or exists"+
         " (select 'X'"+
            " from lcpol f"+
           " where trim(f.appntno) = '"+customerNo+"'"+
            "  and f.riskcode in ('00115000'))"+
 **/
 /*
 " or exists"+
        " (select 'X'"+
           " from lcinsuredrelated"+
          " where lcinsuredrelated.customerno = '" + customerNo + "'"+
            " and lcinsuredrelated.polno = c.PolNo))"+
                  " and a.risksortvalue not in ('0')"+
                  " and c.uwflag not in ('1','2','a')"+
                " group by risksortvalue)";
         
         
         
         */      
		var sqlid1="AmntAccumulateSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(customerNo);
		mySql1.addSubPara(customerNo);
		mySql1.addSubPara(customerNo);
		mySql1.addSubPara(customerNo);

//alert("asql="+aSQL);
  turnPage.queryModal(mySql1.getString(), AmntAccuGrid);

}


/*********************************************************************
 *  ��ѯ�����ۼ���ϸ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryAmntAccuDetail(){
  //alert("customerno="+customerNo);
  //var aSQL="select a.riskcode,b.riskname,(select HEALTHYAMNTRISK('" + customerNo + "',a.riskcode,(select d.RiskSortValue from lmrisksort d where d.riskcode=a.riskcode and d.RiskSortType='3')) from dual where 1=1),sum(a.mult),(select HEALTHYAMNTRISKFB('" + customerNo + "',a.riskcode,(select c.RiskSortValue from lmrisksort c where c.riskcode=a.riskcode and c.RiskSortType='3')) from dual where 1=1) from lcpol a,lmrisk b where a.InsuredNo='"+customerNo+"' and b.riskcode=a.riskcode group by a.riskcode,b.riskname";	
 /*
  var aSQL="select a.riskcode,"+
       " b.riskname,"+
       " (select HEALTHYAMNTRISK('" + customerNo + "',"+
                               " a.riskcode,"+
                               " (select d.RiskSortValue"+
                                  " from lmrisksort d"+
                                 " where d.riskcode = a.riskcode"+
                                   " and d.RiskSortType = '3'))"+
          " from dual"+
         " where 1 = 1),"+
       " sum(a.mult),"+
       " (select HEALTHYAMNTRISKFB('" + customerNo + "',"+
                                 " a.riskcode,"+
                                 " (select c.RiskSortValue"+
                                    " from lmrisksort c"+
                                   " where c.riskcode = a.riskcode"+
                                     " and c.RiskSortType = '3'))"+
          " from dual"+
         " where 1 = 1)"+
  " from lcpol a, lmrisk b"+
 " where (a.InsuredNo = '" + customerNo + "' "+
 " or exists"+
         " (select 'X'"+
            " from lcpol f"+
           " where trim(f.appntno) = '"+customerNo+"'"+
            "  and f.riskcode in ('00115000'))"+
 " or exists"+
        " (select 'X'"+
           " from lcinsuredrelated"+
          " where lcinsuredrelated.customerno = '" + customerNo + "'"+
            " and lcinsuredrelated.polno = a.PolNo))"+
   " and b.riskcode = a.riskcode"+
   " and a.uwflag not in ('1','2','a')"+
 " group by a.riskcode,"+
         " b.riskname";
  //alert("asql="+aSQL);
 */ 
  var sqlid2="AmntAccumulateSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(customerNo);
		mySql2.addSubPara(customerNo);
		mySql2.addSubPara(customerNo);
		mySql2.addSubPara(customerNo);
		mySql2.addSubPara(customerNo);
  
  
  turnPage2.queryModal(mySql2.getString(), AmntAccuDetailGrid);
}


function queryPersonInfo(){
  //alert(customerNo);
  //var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
   var sqlid3="AmntAccumulateSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(customerNo);
		
  var arrResult = easyExecSql(mySql3.getString());
  if(arrResult==null)
  {
  fm.all('CustomerNo').value = " ";
  fm.all('CustomerName').value =" ";
  alert("û�иÿͻ���¼");
  }
  else
  {
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
  }
}