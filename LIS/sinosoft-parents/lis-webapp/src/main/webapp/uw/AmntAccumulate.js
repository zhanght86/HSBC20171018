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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

	}
	if (FlagStr == "Succ")
	{
  	showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

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
//   var aSQL =
//   
//          "SELECT "+
//          "  case" +
//          "    when s_RiskSortValue = '1' then" +
//          "    '����'" +
//          "   when s_RiskSortValue = '2' then" +
//          "   '�ؼ�'" +
//          "  when s_RiskSortValue = '3' then" +
//          "  '���ڽ�����'" +
//          "  when s_RiskSortValue = '4' then" +
//          "  '�����˺�'" +
//          " when s_RiskSortValue = '5' then" +
//          "   'Ͷ��'" +
//          "  when s_RiskSortValue = '6' then" +
//          "  '���'" +
//          "  when s_RiskSortValue = '7' then" +
//          "   '��ʽ'" +
//          "  when s_RiskSortValue = '0' then" +
//          "   '�޷���'" +
//          " end ," +
//              " HEALTHYAMNT3('"+customerNo+"', '', s_RiskSortValue),"+
//              " s_Mult,"+
//              " HEALTHYAMNTFB2('"+customerNo+"', '', s_RiskSortValue)"+
//         " FROM (select distinct RiskSortValue as s_RiskSortValue,"+
//                               " sum(c.mult) as s_Mult"+
//                 " from lmrisksort a, lcpol c"+
//                " where RiskSortType = '3'"+
//                  " and a.riskcode = c.riskcode"+
//               " and (c.insuredno = '"+customerNo+"'"+
//  /***  
// " or exists"+
//         " (select 'X'"+
//            " from lcpol f"+
//           " where trim(f.appntno) = '"+customerNo+"'"+
//            "  and f.riskcode in ('00115000'))"+
// **/
// " or exists"+
//        " (select 'X'"+
//           " from lcinsuredrelated"+
//          " where lcinsuredrelated.customerno = '" + customerNo + "'"+
//            " and lcinsuredrelated.polno = c.PolNo))"+
//                  " and a.risksortvalue not in ('0')"+
//                  " and c.uwflag not in ('1','2','a')"+
//                  " and not exists(select 'X' from lccont where ContNo = c.contno and uwflag in ('1', '2', 'a'))"+
//                " group by risksortvalue)";
// 

		  var sqlid1="AmntAccumulateSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.AmntAccumulateSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
	    var aSQL=mySql1.getString();	

// var aSQL = "SELECT case"
//          + " when s_RiskSortValue = '1' then"
//          + " '����'"
//          + " when s_RiskSortValue = '2' then"
//          + " '�ؼ�'"
//          + " when s_RiskSortValue = '3' then"
//          + " '���ڽ�����'"
//          + " when s_RiskSortValue = '4' then"
//          + " '�����˺�'"
//          + " when s_RiskSortValue = '5' then"
//          + "  'Ͷ��'"
//          + " when s_RiskSortValue = '12' then"
//          + "  '���'"
//          + " when s_RiskSortValue = '7' then"
//          + "  '��ʽ'"
//          + " when s_RiskSortValue = '0' then"
//          + "  '�޷���'"
//          + " end,"
//          + " HEALTHYAMNT3('"+customerNo+"', '', s_RiskSortValue),"
//          + " s_Mult,"
//          + " HEALTHYAMNTFB2('"+customerNo+"', '', s_RiskSortValue)"
//          + " FROM (select distinct RiskSortValue as s_RiskSortValue,"
//          + " sum(c.mult) as s_Mult"
//          + " from lmrisksort a,"
//          + " (select *"
//          + " from lcpol"
//          + " where polno in (select a.polno"
//          + " from lcpol a"
//          + " where a.insuredno = '"+customerNo+"'"
//          + " or (a.appntno = '"+customerNo+"' and a.riskcode in ('00115000','00115001'))"
//          + " union"
//          + " select b.polno"
//          + " from lcinsuredrelated b"
//          + " where b.customerno = '"+customerNo+"')) c"
//          + " where '1' = '1'"
//          + " and RiskSortType = '3'"
//          + " and a.riskcode = c.riskcode"
//          + " and a.risksortvalue not in ('0')"
//          + " and c.uwflag not in ('1', '2', 'a')"
//          + " and c.appflag <> '4'"
//          //+ " and exists (select 'x' from lccontstate where ((contno=c.contno and polno='000000') or polno=c.polno) and statetype='Available' and State = '0' and enddate is null)"  
//          + " and not exists (select 'X'"
//          + " from lccont"
//          + " where ContNo = c.contno"
//          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
//          + " group by risksortvalue)";



//prompt("asql=",aSQL);
  turnPage.queryModal(aSQL, AmntAccuGrid);

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
//  var aSQL="select a.riskcode,"+
//       " b.riskname,"+
//       " (select HEALTHYAMNTRISK('" + customerNo + "',"+
//                               " a.riskcode,"+
//                               " (select d.RiskSortValue"+
//                                  " from lmrisksort d"+
//                                 " where d.riskcode = a.riskcode"+
//                                   " and d.RiskSortType = '3'))"+
//          " from dual"+
//         " where 1 = 1),"+
//       " sum(a.mult),"+
//       " (select HEALTHYAMNTRISKFB('" + customerNo + "',"+
//                                 " a.riskcode,"+
//                                 " (select c.RiskSortValue"+
//                                    " from lmrisksort c"+
//                                   " where c.riskcode = a.riskcode"+
//                                     " and c.RiskSortType = '3'))"+
//          " from dual"+
//         " where 1 = 1)"+
//  " from lcpol a, lmrisk b"+
// " where (a.InsuredNo = '" + customerNo + "' "+
// " or exists"+
//         " (select 'X'"+
//            " from lcpol f"+
//           " where trim(f.appntno) = '"+customerNo+"'"+
//            "  and f.riskcode in ('00115000'))"+
// " or exists"+
//        " (select 'X'"+
//           " from lcinsuredrelated"+
//          " where lcinsuredrelated.customerno = '" + customerNo + "'"+
//            " and lcinsuredrelated.polno = a.PolNo))"+
//   " and b.riskcode = a.riskcode"+
//   " and a.uwflag not in ('1','2','a')"+
//   " and not exists(select 'X' from lccont where ContNo = a.contno and uwflag in ('1', '2', 'a'))"+
// " group by a.riskcode,"+
//         " b.riskname";
         
		 var sqlid2="AmntAccumulateSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.AmntAccumulateSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(customerNo);//ָ������Ĳ���
		mySql2.addSubPara(customerNo);//ָ������Ĳ���
		mySql2.addSubPara(customerNo);//ָ������Ĳ���
		mySql2.addSubPara(customerNo);//ָ������Ĳ���
		mySql2.addSubPara(customerNo);//ָ������Ĳ���
	    var aSQL=mySql2.getString();	
		 
		 
//       var aSQL = " select a.riskcode,"
//                + " b.riskname,"
//                + " HEALTHYAMNTRISK2('"+customerNo+"',"
//                + " a.riskcode,"
//                + " (select d.RiskSortValue"
//                + " from lmrisksort d"
//                + " where '1' = '1'"
//                + " and d.riskcode = a.riskcode"
//                + " and d.RiskSortType = '3')),"
//                + " sum(a.mult),"
//                + " HEALTHYAMNTRISKFB2('"+customerNo+"',"
//                + " a.riskcode,"
//                + " (select c.RiskSortValue"
//                + " from lmrisksort c"
//                + " where c.riskcode = a.riskcode"
//                + " and c.RiskSortType = '3'))"
//                + " from lcpol a, lmrisk b"
//                + " where a.polno in"
//                + " (select polno"
//                + " from lcpol i"
//                + " where i.InsuredNo = '"+customerNo+"'"
//                + " or (i.appntno = '"+customerNo+"' and i.riskcode in ('00115000','00115001'))"
//                + " union"
//                + " select polno"
//                + " from lcinsuredrelated"
//                + " where lcinsuredrelated.customerno = '"+customerNo+"')"
//                + " and b.riskcode = a.riskcode"
//                + " and a.uwflag not in ('1', '2', 'a')"
//                + " and a.appflag <> '4'"
//                //+ " and exists (select 'x' from lccontstate where ((contno=a.contno and polno='000000') or polno=a.polno) and statetype='Available' and State = '0' and enddate is null)"
//                + " and not exists (select 'X'"
//                + " from lccont"
//                + " where ContNo = a.contno"
//                + " and (uwflag in ('1', '2', 'a') or appflag ='4' or (state is not null and substr(state,0,4) in ('1002', '1003')) ))"
//                + " group by a.riskcode, b.riskname"
         
  //alert("asql="+aSQL);
  turnPage2.queryModal(aSQL, AmntAccuDetailGrid);
}


function queryPersonInfo(){
  //alert(customerNo);
  
  		 var sqlid3="AmntAccumulateSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.AmntAccumulateSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(customerNo);//ָ������Ĳ���
	    var aSQL=mySql3.getString();	
  
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
  var arrResult = easyExecSql(aSQL);
  if(arrResult==null)
  {
  document.all('CustomerNo').value = " ";
  document.all('CustomerName').value =" ";
  alert("û�иÿͻ���¼");
  }
  else
  {
	  document.all('CustomerNo').value = arrResult[0][0];
	  document.all('CustomerName').value = arrResult[0][1];
  }
}
