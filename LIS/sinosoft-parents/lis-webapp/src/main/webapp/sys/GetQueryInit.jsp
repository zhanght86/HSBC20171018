<%
//�������ƣ�ProposalQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

function initForm()
{
    //initInpBox();
    initGetGrid();
    initQuery();
}
function initQuery()
{
    initQueryGo();
}



// �����ĳ�ʼ��������¼���֣�




// ������Ϣ�б�ĳ�ʼ��
function initGetGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]=" �˻���";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ܸ������";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��ȡ��";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��ȡ�����֤��";
        iArray[4][1]="130px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="���б���";
        iArray[5][1]="80px";
        iArray[5][2]=150;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="�����˻�";
        iArray[6][1]="80px";
        iArray[6][2]=150;
        iArray[6][3]=0;


        GetGrid = new MulLineEnter("fm", "GetGrid");
        //��Щ���Ա�����loadMulLineǰ
        GetGrid.mulLineCount = 5;
        GetGrid.displayTitle = 1;
        GetGrid.locked = 1;
        GetGrid.canSel = 0;
        GetGrid.hiddenPlus = 1;
        GetGrid.hiddenSubtraction = 1;
        GetGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
