<%
//�������ƣ�PEdorTypeFMInit.jsp
//�����ܣ����˱�ȫ-�ɷѷ�ʽ�����ޱ��
//�������ڣ�2005-05-16 09:05:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">

function initForm()
{
    try
    {
        initInpBox();
        initPolGrid();
        getPolGrid();
        QueryEdorInfo();
    }
    catch (ex)
    {
        alert("�� PEdorTypeFMInit.jsp --> initForm �����з����쳣:��ʼ��������� ");
    }
}

function initInpBox()
{
    try
    {
        EdorQuery();
        fm.EdorAcceptNo.value   = top.opener.fm.EdorAcceptNo.value;
        fm.EdorType.value       = top.opener.fm.EdorType.value;
        fm.EdorNo.value         = top.opener.fm.EdorNo.value;
        fm.ContNo.value         = top.opener.fm.ContNo.value;
        fm.InsuredNo.value      = top.opener.fm.InsuredNo.value;
        fm.PolNo.value          = top.opener.fm.PolNo.value;
        fm.EdorItemAppDate.value    = top.opener.fm.EdorItemAppDate.value;
    }
    catch (ex)
    {
        alert("�� PEdorTypeFMInit.jsp --> initInpBox �����з����쳣:��ʼ��������� ");
    }
}

function initPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ֺ�";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ִ���";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="Ͷ����";
        iArray[4][1]="0px";
        iArray[4][2]=100;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="������";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="Ͷ������";
        iArray[6][1]="60px";
        iArray[6][2]=100;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="���ѱ�׼";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=7;
        iArray[7][23]="0";
        
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="60px";
        iArray[8][2]=100;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="����";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=7;
        iArray[9][23]="0";
        
        iArray[10]=new Array();
				iArray[10][0]="����";
				iArray[10][1]="60px";
				iArray[10][2]=100;
				iArray[10][3]=2;
				iArray[10][4]="currency";

        PolGrid = new MulLineEnter("fm", "PolGrid");
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 0;
        PolGrid.displayTitle = 1;
        PolGrid.canSel =1;
        PolGrid.selBoxEventFuncName ="displayPolGrid" ;     //���RadioBoxʱ��Ӧ��JS����
        PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� PEdorTypeFMInit.jsp --> initPolGrid �����з����쳣:��ʼ��������� ");
    }
}

</script>