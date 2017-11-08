//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
    try
    {
        initForm();
    }
    catch(re)
    {
        alert("在LLLdPersonQuery.js-->resetForm函数中发生异常:初始化界面错误!");
    }
}

//取消按钮对应操作
function cancelForm()
{
}

//提交前的校验、计算
function beforeSubmit()
{
}

//查询按钮
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

//对应RadioBox的单记录返回
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

////参数为出生年月,如1980-5-9
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
