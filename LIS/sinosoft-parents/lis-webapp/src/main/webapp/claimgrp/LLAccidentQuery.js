//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
    try
    {
        initForm();
    }
    catch(re)
    {
        alert("��LLLdPersonQuery.js-->resetForm�����з����쳣:��ʼ���������!");
    }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
}

//�ύǰ��У�顢����
function beforeSubmit()
{
}

//��ѯ��ť
function easyQueryClick()
{
    initLLAccidentGrid();
   /* var strSQL = "select AccNo,AccDate,AccType,AccSubject,AccDesc,AccPlace,AccGrade,Operator,MngCom from LLAccident where "
                + "AccDate = to_date('"+tAccDate
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + " and CustomerNo="+tCustomerNo
//                + " and AccType="+tAccType
                + ")";*/
//    alert(strSQL);
mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLAccidentQueryInputSql");
mySql.setSqlId("LLAccidentQuerySql1");
mySql.addSubPara(tAccDate);
mySql.addSubPara(tCustomerNo);  
    turnPage.queryModal(mySql.getString(),LLAccidentGrid);
}

//��ӦRadioBox�ĵ���¼����
function returnParent()
{
    var i = LLAccidentGrid.getSelNo();
    if (i != 0)
    {
        i = i - 1;
        var tAccNo = LLAccidentGrid.getRowColData(i,1);
        var tOccurReason = LLAccidentGrid.getRowColData(i,3);
        var tAccDesc = LLAccidentGrid.getRowColData(i,5);
        var tAccPlace = LLAccidentGrid.getRowColData(i,6);
    }
    if (tAccNo)
    {
       top.opener.afterQueryLL2(tAccNo,tOccurReason,tAccDesc,tAccPlace);
    }
    top.close();
}

////����Ϊ��������,��1980-5-9
//function getAge(birth)
//{
//    var now = new Date();
//    var nowYear = now.getFullYear();
//    var oneYear = birth.substring(0,4);
//    var age = nowYear - oneYear;
//    if (age <= 0)
//    {
//        age = 0
//    }
//    return age;
//}
