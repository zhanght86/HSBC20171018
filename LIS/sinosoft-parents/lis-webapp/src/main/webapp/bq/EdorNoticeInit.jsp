<%
//�������ƣ�EdorNoticeInit.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 15:20:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

//ҳ���ʼ��
function initForm()
{
    try
    {
        initInpBox();
        initEdorItemGrid();
        initQuery();
        InitApproveContent();
    }
    catch (ex)
    {
        alert("�� EdorNoticeInit.jsp --> initForm �����з����쳣:��ʼ���������");
    }
}

//��ʼ��ҳ��Ԫ��
function initInpBox()
{
    try
    {
    	  document.all('ApproveFlag').value     = "1";
    	  document.all('edornoticeideaName').value     = "��ȫ���֪ͨ��";
    	  document.all("divPayPassNotice").style.display = "";
        document.all('EdorAcceptNo').value     = NullToEmpty("<%= tEdorAcceptNo %>");
        document.all('MissionID').value        = NullToEmpty("<%= tMissionID %>");
        document.all('SubMissionID').value     = NullToEmpty("<%= tSubMissionID %>");
    }
    catch (ex)
    {
        alert("�� EdorNoticeInit.jsp --> initInpBox �����з����쳣:��ʼ���������");
    }
}

// ��ȫ��Ŀ�б�ĳ�ʼ��
function initEdorItemGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";                  //�п�
        iArray[0][2] = 30;                      //�����ֵ
        iArray[0][3] = 0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "����������";
        iArray[1][1] = "0px";
        iArray[1][2] = 0;
        iArray[1][3] = 3;

        iArray[2] = new Array();
        iArray[2][0] = "��������";
        iArray[2][1] = "100px";
        iArray[2][2] = 100;
        iArray[2][3] = 0;

        iArray[3] = new Array();
        iArray[3][0] = "������";
        iArray[3][1] = "110px";
        iArray[3][2] = 150;
        iArray[3][3] = 0;

        iArray[4] = new Array();
        iArray[4][0] = "�ͻ���";
        iArray[4][1] = "70px";
        iArray[4][2] = 100;
        iArray[4][3] = 0;

        iArray[5] = new Array();
        iArray[5][0] = "���ֺ�";
        iArray[5][1] = "110px";
        iArray[5][2] = 150;
        iArray[5][3] = 0;

        iArray[6] = new Array();
        iArray[6][0] = "������������";
        iArray[6][1] = "100px";
        iArray[6][2] = 150;
        iArray[6][3] = 0;


        iArray[7] = new Array();
        iArray[7][0] = "��Ч����";
        iArray[7][1] = "0px";
        iArray[7][2] = 0;
        iArray[7][3] = 3;
        iArray[7][21] = 3;

        iArray[8] = new Array();
        iArray[8][0] = "���˷ѽ��";
        iArray[8][1] = "0px";
        iArray[8][2] = 100;
        iArray[8][3] = 0;
        iArray[8][21] = 3;

        iArray[9] = new Array();
        iArray[9][0] = "���˷���Ϣ";
        iArray[9][1] = "0px";
        iArray[9][2] = 100;
        iArray[9][3] = 0;
        iArray[9][21] = 3;

        iArray[10] = new Array();
        iArray[10][0] = "����״̬";
        iArray[10][1] = "70px";
        iArray[10][2] = 100;
        iArray[10][3] = 0;

        iArray[11] = new Array();
        iArray[11][0] = "����״̬����";
        iArray[11][1] = "0px";
        iArray[11][2] = 0;
        iArray[11][3] = 3;

        iArray[12] = new Array();
        iArray[12][0] = "��������";
        iArray[12][1] = "0px";
        iArray[12][2] = 0;
        iArray[12][3] = 3;
        iArray[12][21] = 3;

        iArray[13] = new Array();
        iArray[13][0] = "����ʱ��";
        iArray[13][1] = "0px";
        iArray[13][2] = 0;
        iArray[13][3] = 3;
        iArray[13][21] = 3;

        iArray[14] = new Array();
        iArray[14][0] = "�������ͱ���";
        iArray[14][1] = "0px";
        iArray[14][2] = 0;
        iArray[14][3] = 3;


        EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
        //��Щ���Ա�����loadMulLineǰ
        EdorItemGrid.mulLineCount = 1;
        EdorItemGrid.displayTitle = 1;
        EdorItemGrid.locked = 1;
        EdorItemGrid.canSel = 1;
        EdorItemGrid.hiddenPlus = 1;
        EdorItemGrid.hiddenSubtraction=1;
        EdorItemGrid.selBoxEventFuncName = "getEdorItemInfo";
        EdorItemGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� EdorNoticeInit.jsp --> initEdorItemGrid �����з����쳣:��ʼ���������");
    }
}

</script>
