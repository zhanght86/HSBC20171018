//�������ƣ�EdorUWManuSpec.js
//�����ܣ��˹��˱���Լ�б�
//�������ڣ�2005-06-24 15:10:36
//������  ��liurongxiao
//���¼�¼��  ������ Ǯ����    ��������  2006-10-27   ����ԭ��/���� ����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;
var mDebug="0";
var flag;
var str = "";
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var operate = "";
var proposalno = "";
var serialno = "";

//�ύ�����水ť��Ӧ����
function submitForm(tflag)
{
    if(tflag=="1")
       {
        fm.operate.value = "INSERT"
       }
    else if(tflag=="2")
         {
            var tSelNo = UWLPSpecGrid.getSelNo()-1;
            if(tSelNo=="-1")
              {
                alert("��ѡ��Ҫ�޸ĵ���Լ��Ϣ��");
                return;
              }
            if(UWLPSpecGrid.getRowColData(tSelNo,3)=="0"||UWLPSpecGrid.getRowColData(tSelNo,3)=="1")
              {
                alert("ֻ����δ���ͻ����ѻ���״̬�����޸ģ�");
                return;
              }
      fm.proposalno.value = UWLPSpecGrid.getRowColData(tSelNo,5);
      fm.serialno.value = UWLPSpecGrid.getRowColData(tSelNo,6);
            fm.operate.value = "UPDATE";
         }
  else
     {
            var tSelNo = UWLPSpecGrid.getSelNo()-1;
            if(tSelNo=="-1")
              {
                alert("��ѡ��Ҫɾ������Լ��Ϣ��");
                return;
              }
            if(UWLPSpecGrid.getRowColData(tSelNo,3)=="0"||UWLPSpecGrid.getRowColData(tSelNo,3)=="1")
              {
                alert("ֻ����δ���ͻ����ѻ���״̬����ɾ����");
                return;
              }
      fm.proposalno.value = UWLPSpecGrid.getRowColData(tSelNo,5);
      fm.serialno.value = UWLPSpecGrid.getRowColData(tSelNo,6);
      fm.operate.value   = "DELETE";
     }

  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.submit(); //�ύ
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=200;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            var sEdorNo = document.getElementsByName("EdorNo")[0].value;
            var sPolNo = document.getElementsByName("PolNo")[0].value;
            QueryUWLPSpecGrid(sEdorNo, sPolNo);
        }
        catch (ex) {}
    }
}

//��ѯ������Ϣ
function QueryPolGrid(tContNo,tEdorNo,tPolNo)
{
//   var strSql = "select EdorNo,ContNo,PolNo,RiskCode,RiskVersion,AppntName,InsuredName,EdorType from LPPol where "
//             + "  ContNo ='"+tContNo+"'"+" and EdorNo='"+tEdorNo+"' and PolNo='"+tPolNo+"'";
   var strSql = "";
   var sqlid1="EdorUWManuSpecSql1";
   var mySql1=new SqlClass();
   mySql1.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
   mySql1.addSubPara(tContNo);//ָ������Ĳ���
   mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
   mySql1.addSubPara(tPolNo);//ָ������Ĳ���
   strSql=mySql1.getString();   
   var brr = easyExecSql(strSql);

   if(brr)
   {
     turnPage.queryModal(strSql,PolGrid);
   }
   else
   {
     alert("û��������Ҫ�˱���");
     return;
   }
}

//ѡ��һ��P���Ѵ���Լ���޸�
function getOneToChg()
{
    var tContent = fm.Remark.value;
    var tSelNo = UWLPSpecGrid.getSelNo()-1;
    var tRemark = UWLPSpecGrid.getRowColData(tSelNo,1);
  fm.Remark.value = tContent + tRemark;
}

//ѡ��һ����Լģ�����޸�
function getOneModel()
{
    var tSelNo = UWModelGrid.getSelNo()-1;
    var tRemark = UWModelGrid.getRowColData(tSelNo,1);
  fm.Remark.value = tRemark ;
}

//��ѯLCSpec���е���Լ��Ϣ
function QueryUWSpecGrid(tPolNo)
{
     var strSQL = "";
//             strSQL = "select a,b,c,case c when 'x' then 'δ����' "
//                      + " when '0' then '�ѷ���δ��ӡ'"
//                      + " when '1' then '�Ѵ�ӡδ����'"
//                + " when '2' then '�ѻ���'"
//                + " end,"
//                + " d,e"
//                + "   from (select s.speccontent as a,"
//                + "                s.prtseq as b,"
//                + "                (case when (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) is not null then (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) else 'x' end) as c,"
//                + "                s.proposalno as d,"
//                + "                s.serialno as e"
//                + "                from lcspec s "
//                + "                where s.polno = '"+tPolNo+"')";
             var sqlid2="EdorUWManuSpecSql2";
             var mySql2=new SqlClass();
             mySql2.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
             mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
             mySql2.addSubPara(tPolNo);//ָ������Ĳ���
             strSQL=mySql2.getString();   
             
   var brr = easyExecSql(strSQL);

   if(brr)
   {
     turnPage.queryModal(strSQL,UWSpecGrid);
   }
   return true;
}

//��ѯLPSpec���е���Լ��Ϣ
function QueryUWLPSpecGrid(tEdorNo,tPolNo)
{
    var strSQL = "";
//             strSQL = "select a,b,c,case c when 'x' then 'δ����' "
//                      + " when '0' then '�ѷ���δ��ӡ'"
//                      + " when '1' then '�Ѵ�ӡδ����'"
//                + " when '2' then '�ѻ���'"
//                + " end,"
//                + " d,e"
//                + "   from (select s.speccontent as a,"
//                + "                s.prtseq as b,"
//                + "                (case when (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) is not null then (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) else 'x' end) as c,"
//                + "                s.proposalno as d,"
//                + "                s.serialno as e"
//                + "                from lpspec s "
//                + "                where s.polno = '"+tPolNo+"' and s.edorno='"+tEdorNo+"') h";

             var sqlid3="EdorUWManuSpecSql3";
             var mySql3=new SqlClass();
             mySql3.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
             mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
             mySql3.addSubPara(tPolNo);//ָ������Ĳ���
             mySql3.addSubPara(tEdorNo);//ָ������Ĳ���
             strSQL=mySql3.getString();   
   var brr = easyExecSql(strSQL);

   if(brr)
   {
     turnPage.queryModal(strSQL,UWLPSpecGrid);
   }
   return true;
}
//��ѯ��Լģ��
function QueryUWModelGrid(tContNo)
{
        var strSQL2 = "";
//        strSQL = "select substr(managecom,1,4) from lccont where contno=(select ContNo from LCCont where PrtNo = '"+tContNo+"')";
        var sqlid4="EdorUWManuSpecSql4";
        var mySql4=new SqlClass();
        mySql4.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
        mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
        mySql4.addSubPara(tContNo);//ָ������Ĳ���
        strSQL2=mySql4.getString();   
        var managecom = easyExecSql(strSQL2);

//        strSQL = "select substr(managecom,1,6) from lccont where contno=(select ContNo from LCCont where PrtNo = '"+tContNo+"')";
        var strSQL1="";
        var sqlid5="EdorUWManuSpecSql5";
        var mySql5=new SqlClass();
        mySql5.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
        mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
        mySql5.addSubPara(tContNo);//ָ������Ĳ���
        strSQL1=mySql5.getString();   
        var managecom2 = easyExecSql(strSQL1);
        var strSQL = "";
        if(managecom!=null){
            //����Ǳ��֡��Ϻ������ݵ�Ͷ��������ʾʮ��
          if(managecom=="8621"||managecom=="8622"){
//       strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype2'";
	       var sqlid6="EdorUWManuSpecSql6";
	       var mySql6=new SqlClass();
	       mySql6.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
	       mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	       mySql6.addSubPara(k);//ָ������Ĳ���
	       mySql6.addSubPara(k);//ָ������Ĳ���
	       strSQL=mySql6.getString();   
          }
        else if (managecom=="8623"){
             if(managecom2!=null&&managecom2=="862300"){
//             strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype2'";
           var sqlid7="EdorUWManuSpecSql7";
  	       var mySql7=new SqlClass();
  	       mySql7.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
  	       mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
  	       mySql7.addSubPara(k);//ָ������Ĳ���
  	       mySql7.addSubPara(k);//ָ������Ĳ���
  	       strSQL=mySql7.getString();   
                }
             else {
//       strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype'";
       	   var sqlid8="EdorUWManuSpecSql8";
	       var mySql8=new SqlClass();
	       sqlid8.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
	       sqlid8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	       sqlid8.addSubPara(k);//ָ������Ĳ���
	       sqlid8.addSubPara(k);//ָ������Ĳ���
	       strSQL=sqlid8.getString();
                }
            }
          else{
//       strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype'";
       var sqlid9="EdorUWManuSpecSql9";
       var mySql9=new SqlClass();
       mySql9.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
       mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
       mySql9.addSubPara(k);//ָ������Ĳ���
       mySql9.addSubPara(k);//ָ������Ĳ���
       strSQL=mySql9.getString();
          }
        }
     else{
//       strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype'";
       var sqlid10="EdorUWManuSpecSql10";
       var mySql10=new SqlClass();
       mySql10.setResourceName("bq.EdorUWManuSpecSql"); //ָ��ʹ�õ�properties�ļ���
       mySql10.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
       mySql10.addSubPara(k);//ָ������Ĳ���
       mySql10.addSubPara(k);//ָ������Ĳ���
       strSQL=mySql10.getString();
     }

       //��ȡԭ������Ϣ
       // strSQL = "select cont from ldcodemod where 1 =1 and codetype='spectype'";

   var brr = easyExecSql(strSQL);

   if(brr)
   {
     turnPage.queryModal(strSQL,UWModelGrid);
   }
   return true;
}


/**
 * ����������
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

