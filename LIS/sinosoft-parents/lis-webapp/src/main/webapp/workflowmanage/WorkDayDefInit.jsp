<%/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */%><!--�û�У����-->
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("PMInverstRuleInit_st.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}
function initSelBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("PMInverstRuleInit_st.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}
function initForm()
{
    try
    {
        initWorkDayGrid();
        initSaveWorkDayGrid();
    }
    catch(re)
    {
        alert("PMInverstRuleInit_st.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
} //ȫ��������Ϣ
function initWorkDayGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i] = new Array();
        iArray[i][0] = "���";
        iArray[i][1] = "30px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "����������";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���к�";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;
        iArray[i] = new Array();
        iArray[i][0] = "���";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���繤����ʼʱ��";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���繤������ʱ��";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���繤����ʼʱ��";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���繤������ʱ��";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "����ʱ��";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "�Ƿ�����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i][3] = 2;
        iArray[i++][4] = "yesorno";
        iArray[i] = new Array();
        iArray[i][0] = "�������� ";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i][3] = 2;
        WorkDayGrid = new MulLineEnter("fm", "WorkDayGrid");
        //��Щ���Ա�����loadMulLineǰ
        WorkDayGrid.mulLineCount = 5;
        WorkDayGrid.displayTitle = 1;
        WorkDayGrid.hiddenPlus = 1
        WorkDayGrid.hiddenSubtraction = 1;
        WorkDayGrid.canChk = 1;
        WorkDayGrid.selBoxEventFuncName = "";
        WorkDayGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
} //ѡ��
function initSaveWorkDayGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i] = new Array();
        iArray[i][0] = "���";
        iArray[i][1] = "30px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "����������";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���к�";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;
        iArray[i] = new Array();
        iArray[i][0] = "���";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���繤����ʼʱ��";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���繤������ʱ��";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���繤����ʼʱ��";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "���繤������ʱ��";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "����ʱ��";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "�Ƿ�����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i][3] = 2;
        iArray[i++][4] = "yesorno";
        iArray[i] = new Array();
        iArray[i][0] = "�������� ";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i][3] = 2;
        SaveWorkDayGrid = new MulLineEnter("fm", "SaveWorkDayGrid");
        //��Щ���Ա�����loadMulLineǰ
        SaveWorkDayGrid.mulLineCount = 0;
        SaveWorkDayGrid.displayTitle = 1;
        SaveWorkDayGrid.hiddenPlus = 1;
        SaveWorkDayGrid.hiddenSubtraction = 1;
        SaveWorkDayGrid.canChk = 1;
        SaveWorkDayGrid.selBoxEventFuncName = "";
        SaveWorkDayGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
