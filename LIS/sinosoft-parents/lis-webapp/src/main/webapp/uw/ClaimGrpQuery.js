//**************************************************************************************************
//�ļ����ƣ�ClaimGrpQuery.js
//�����ܣ��б�����-���屣��-�˹��˱�-���������ѯ��Ӧ���棬������Ϣ����ʾ��������˽��浥ѡ��ť
//          ʱ�����ӵ���ui/uw/ClaimQueryMain.jsp�����ղ�����ⰸ��ѯ��
//�������ڣ�2006-11-08
//������  ��zhaorx
//���¼�¼��
//**************************************************************************************************

var turnPage = new turnPageClass();
//��ʼ����ѯ
function initQuery()
{
	var sqlid826143908="DSHomeContSql826143908";
var mySql826143908=new SqlClass();
mySql826143908.setResourceName("uw.ClaimGrpQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql826143908.setSqlId(sqlid826143908);//ָ��ʹ�õ�Sql��id
mySql826143908.addSubPara(fm.GrpAppntNo.value);//ָ������Ĳ���
var strSQLA=mySql826143908.getString();
	
//	var strSQLA = " select g.rgtobjno,g.rgtno,r.rgtno,r.rgtdate,"
//	            + " (select d.name from ldperson d where d.customerno=c.customerno),"
//	            + " (select codename from ldcode where codetype='llclaimstate' and r.clmstate=code ), "
//	            + " c.customerno "
//	            + " from llgrpregister g,llregister r,llcase c where 1=1 and g.rgtno=r.rgtobjno " 
//	            + " and c.caseno=r.rgtno and exists "
//              + " (select 'X' from lcgrpcont c where g.rgtobjno=c.grpcontno and c.appntno='"+fm.GrpAppntNo.value+"') "
//              + " order by g.rgtobjno,g.rgtno,r.rgtno ";             
        
	  turnPage.pageLineNum = 10;    //ÿҳ10��
    turnPage.queryModal(strSQLA, LLClaimGrpQueryGrid);
}

//LLClaimGrpQueryGrid����Ӧ����
function ClaimQueryMainClick()
{
    var i = LLClaimGrpQueryGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tAppntNo  = LLClaimGrpQueryGrid.getRowColData(i,7);//�����˱���    
    }
    window.open("./ClaimQueryMain.jsp?CustomerNo="+tAppntNo,"window2","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

