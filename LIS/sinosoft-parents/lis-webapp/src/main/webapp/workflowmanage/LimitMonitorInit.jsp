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
    } // ������ĳ�ʼ��
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
            initInpBox();
            initSelBox();
            initMonitorGrid();
        }
        catch(re)
        {
            alert("PMInverstRuleInit_st.jsp-->InitForm�����з����쳣:��ʼ���������!");
        }
    } //ȫ��������Ϣ
    function initMonitorGrid()
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
            iArray[i][0] = "ҵ������CODE";
            iArray[i][1] = "0px";
            iArray[i][2] = 10;
            iArray[i++][3] = 3;
            iArray[i] = new Array();
            iArray[i][0] = "ҵ������";
            iArray[i][1] = "80px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            iArray[i] = new Array();
            iArray[i][0] = "�CODE";
            iArray[i][1] = "0px";
            iArray[i][2] = 10;
            iArray[i++][3] = 3;
            iArray[i] = new Array();
            iArray[i][0] = "�����";
            iArray[i][1] = "80px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            iArray[i] = new Array();
            iArray[i][0] = "ҵ���";
            iArray[i][1] = "80px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            iArray[i] = new Array();
            iArray[i][0] = "��ʼʱ��";
            iArray[i][1] = "100px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            iArray[i] = new Array();
            iArray[i][0] = "�涨��ҵ��ʱ";
            iArray[i][1] = "140px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            iArray[i] = new Array();
            iArray[i][0] = "ʣ����ҵʱ��";
            iArray[i][1] = "70px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;

            iArray[i] = new Array();
            iArray[i][0] = "������ָ��";
            iArray[i][1] = "70px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;

            iArray[i] = new Array();
            iArray[i][0] = "������";
            iArray[i][1] = "80px";
            iArray[i][2] = 10;
            iArray[i++][3] = 0;
            MonitorGrid = new MulLineEnter("fm", "MonitorGrid");
            //��Щ���Ա�����loadMulLineǰ
            MonitorGrid.mulLineCount = 5;
            MonitorGrid.displayTitle = 1;
            MonitorGrid.hiddenPlus = 1;
            MonitorGrid.hiddenSubtraction = 1;
            MonitorGrid.canChk = 0;
            MonitorGrid.selBoxEventFuncName = "";
            MonitorGrid.loadMulLine(iArray);
        }
        catch(ex)
        {
            alert(ex);
        }
    }


</script>
