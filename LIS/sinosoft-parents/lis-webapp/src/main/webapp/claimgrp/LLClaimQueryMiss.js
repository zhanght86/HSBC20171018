//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

// ��ʼ�����1
function queryGrid()
{
    initLLClaimQueryGrid();
    var strSQL = "";
    
    //��ѯ�����Ժ���Ϣ
    strSQL = "(select a.rgtno,(case a.clmstate when '10' then '����' when '20' then '����' when '30' then '���' when '40' then '����' when '50' then '�᰸' when '60' then '���' when '70' then '�ر�' end),b.customerno,(select c.name from ldperson c where c.customerno = b.customerno),(case b.customersex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),b.AccDate "
           + " from llregister a,llcase b "
           + " where a.rgtno = b.caseno "
           + getWherePart( 'a.rgtno' ,'RptNo') //�ⰸ��
           + getWherePart( 'a.clmstate' ,'ClmState') //�ⰸ״̬
           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //�����¹ʷ�������
           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //�����˱���
           + getWherePart( 'b.AccDate' ,'AccidentDate2') //��������
           + getWherePart( 'a.RgtDate' ,'RgtDate') //��������
           + getWherePart( 'a.EndCaseDate' ,'EndDate'); //�᰸����
           if (fm.CustomerName.value != "")  //����������
           {
           strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '%%" + fm.CustomerName.value + "%%') ";
           }
           if (fm.ContNo.value != "")  //��ͬ��
           {
           strSQL = strSQL + " and b.rgtno in (select ClmNo from LLClaimPolicy where ContNo ='" + fm.ContNo.value + "') ";
           }
           strSQL = strSQL + " )";
           
    //���ϲ�ѯ������Ϣ
    if ((fm.ClmState.value == "" || fm.ClmState.value == "10") && fm.RgtDate.value == "" && fm.EndDate.value == "" && fm.ContNo.value == "")
    {
    strSQL = strSQL + " union "
           + "(select a.rptno,'����',b.customerno,c.name,(case c.sex when '0' then '��' when '1' then 'Ů' when '2' then '����' end),b.AccDate "
           + " from llreport a,llsubreport b,ldperson c "
           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
           + getWherePart( 'a.rptno' ,'RptNo') //�ⰸ��
           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //�����¹ʷ�������
           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //�����˱���
           + getWherePart( 'b.AccDate' ,'AccidentDate2'); //��������
           if (fm.CustomerName.value != "")  //����������
           {
           strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '%%" + fm.CustomerName.value + "%%') ";
           }
           strSQL = strSQL + " )";
    }
    //��������
    strSQL = strSQL + " order by 1";

    turnPage.queryModal(strSQL,LLClaimQueryGrid);
}

//LLClaimQueryGrid����¼�
function LLClaimQueryGridClick()
{
    var i = LLClaimQueryGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
        var tPhase = LLClaimQueryGrid.getRowColData(i,2);
        if (tPhase != '����')
        {
//            location.href="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
            var strUrl="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
        }
        else
        {
//            location.href="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
            var strUrl="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
        }
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}
