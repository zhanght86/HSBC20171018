/*******************************************************************************

 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/
var showInfo;                                      //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();                //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageEdorItemGrid = new turnPageClass();    //ȫ�ֱ���, ������ȫ������Ŀ��Ϣ��ѯ�����ҳ
/*============================================================================*/

/*============================================================================*/

/**
 * �ͻ���Ϣ��ѯ
 */
function queryCustomerInfo()
{
    var sCustomerNo;
    var brr;
    var QuerySQL;
    try
    {
        sCustomerNo = document.all("CustomerNo").value;
    }
    catch (ex) {}
    if (sCustomerNo == null || trim(sCustomerNo) == "")
    {
        alert("�޷���ȡ�ͻ��š���ѯ�ͻ���Ϣʧ�ܣ� ");
        return;
    }
    else
    {
        var sqlid826143228="DSHomeContSql826143228";
var mySql826143228=new SqlClass();
mySql826143228.setResourceName("bq.GEdorAgoQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826143228.setSqlId(sqlid826143228);//ָ��ʹ�õ�Sql��id
mySql826143228.addSubPara(sCustomerNo);//ָ������Ĳ���
QuerySQL=mySql826143228.getString();
        
//        QuerySQL = "select GrpName"
//                     +  " from LDGrp"
//                     +  " where CustomerNo ='" + sCustomerNo + "'";
        try
        {
          brr = easyExecSql(QuerySQL);
          //alert(brr);
        }
        catch (ex)
        {
            alert("���棺��ѯ�ͻ���Ϣ�����쳣�� ");
            return;
        }
        if (brr != null)
        {
           document.all("GrpName").value = brr[0][0];
        }
    }
}

/*============================================================================*/

/**
 * ������ȫ������Ŀ��Ϣ��ѯ
 */
function queryEdorItem()
{
	   try{
	      var customerNo = document.all("CustomerNo").value;
	   }catch(ex){}  	 
       
       	var sqlid826143338="DSHomeContSql826143338";
var mySql826143338=new SqlClass();
mySql826143338.setResourceName("bq.GEdorAgoQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826143338.setSqlId(sqlid826143338);//ָ��ʹ�õ�Sql��id
mySql826143338.addSubPara(customerNo);//ָ������Ĳ���
var sql=mySql826143338.getString();
        
//        var sql = "select edoracceptno,"
//                +" edorno,"
//                +" edortype,"
//                +" grpcontno,"
//                +" edorappdate,"
//                +" edorvalidate,"
//                +" getmoney,"
//                +" getinterest"
//                +" from lpgrpedoritem p"
//                +" where '" + customerNo+ "' in "
//                +" (select appntno from lcgrpcont where grpcontno = p.grpcontno)"
//                +" and edorstate = '0'"
//                +" order by makedate asc"
//                +"";
        try
        {
            turnPageEdorItemGrid.pageDivName = "divTurnPageEdorItemGrid";
            turnPageEdorItemGrid.queryModal(sql, EdorItemGrid);
        }
        catch (ex)
        {
            alert("���棺��ѯ�ͻ�������ȫ������Ŀ��Ϣ�����쳣�� ");
            return;
        }
} 
/*============================================================================*/
     
/**
 * Ӱ�����ϲ�ѯ
 */
function showImage()
{
        var sEdorAcceptNo;
        try
        {
            sEdorAcceptNo = document.all("EdorAcceptNo").value;
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("����ѡ��Ҫ�鿴�ı�ȫ��Ŀ��");
            return;
        }
        else
        {
        	var tUrl="../bq/ImageQueryMain.jsp?BussNo="+sEdorAcceptNo+"&BussType=BQ";
        	OpenWindowNew(tUrl,"��ȫɨ��Ӱ��","left");
        } //sEdorAcceptNo != null
}

/**
 *  ����������
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

/*****************************************************************************
 *  ����EdorAcceptNo����������Ϣ
 *****************************************************************************/
function EndorseDetail()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
        return;
    }
    else
    {
        document.forms(0).action = "../f1print/AppEndorsementF1PJ1.jsp";
        document.forms(0).target = "_blank";
        document.forms(0).submit();
    }
}

//��ѡ��ȫ��Ŀ���������ֵ
function initHide()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
        return;
    }
    else
    {
      document.all("EdorAcceptNo").value = EdorItemGrid.getRowColData(nSelNo, 1);
      document.all("EdorNo").value = EdorItemGrid.getRowColData(nSelNo, 2);
    }
}

/**
 * ���� EdorNo ���������嵥��Ϣ
 * Added by XinYQ on 2006-09-21
 */
function NamesBill()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
        return;
    }
    else
    {
        var sEdorNo;
        try
        {
            sEdorNo = document.getElementsByName("EdorNo")[0].value;
        }
        catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "")
        {
            alert("�޷���ȡ�����š���ѯ�����嵥ʧ�ܣ� ");
            return;
        }
        else
        {
            var QuerySQL, arrResult;
            
            var sqlid826143523="DSHomeContSql826143523";
var mySql826143523=new SqlClass();
mySql826143523.setResourceName("bq.GEdorAgoQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826143523.setSqlId(sqlid826143523);//ָ��ʹ�õ�Sql��id
mySql826143523.addSubPara(sEdorNo);//ָ������Ĳ���
QuerySQL=mySql826143523.getString();
            
//            QuerySQL = "select 'X' from LPEdorPrint2 where EdorNo = '" + sEdorNo + "'";
            //alert(QuerySQL);
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("���棺��ѯ�����嵥��Ϣ�����쳣�� ");
                return;
            }
            if (arrResult == null)
            {
                alert("�ñ����˴�������Ŀû�������嵥��Ϣ�� ");
                return;
            }
            else
            {
                document.forms[0].action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + sEdorNo + "&type=Bill";
                document.forms[0].target = "_blank";
                document.forms[0].submit();
            }
        }
    } //nSelNo != null
}

/*============================================================================*/


//<!-- JavaScript Document END -->
