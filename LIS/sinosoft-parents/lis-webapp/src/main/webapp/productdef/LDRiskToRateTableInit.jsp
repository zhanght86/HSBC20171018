<%@include file="../i18n/language.jsp"%>
<%
//�������ƣ�LDRiskToRateTableInit.jsp
//�������ڣ�2012-09-19 15:13:22
//������  ��Guxin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<script type="text/javascript">
var CollectivityGrid;
function initForm()
{
    try
    {
        initCollectivityGrid();          //��ʼ���۸���Ϣ�б�
	    fm.all('RiskCode').value = '';
	    fm.all('RateType').value = '';
	    fm.all('RiskCodeName').value = '';
	    fm.all('RateTypeName').value = '';
	    fm.all('Remark').value = '';
    //    easyQueryClick();
    }
    catch (ex)
    {
        myAlert("�� LOAccInputInit.jsp --> initForm �����з����쳣:��ʼ��������� ");
    }
}

function initCollectivityGrid()
{
    var iArray = new Array();
    try
    {
    	
    	iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="35px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;
    
        iArray[1]=new Array();
        iArray[1][0]="��Ʒ����";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[1][1]="50px";                    //�п�
        iArray[1][2]=30;                        //�����ֵ
        iArray[1][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="��Ʒ����";
        iArray[2][1]="100px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="���ʱ���";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
         
        CollectivityGrid = new MulLineEnter("fm", "CollectivityGrid");
        //��Щ���Ա�����loadMulLineǰ
        CollectivityGrid.mulLineCount = 5;
        CollectivityGrid.displayTitle = 1;
        CollectivityGrid.locked = 1;				//��������������+����"--"���
        CollectivityGrid.canSel = 1;				//Radio ��ѡ��1 ��ʾ ��0 ���أ�ȱʡֵ��
        CollectivityGrid.canChk = 0;				// 1Ϊ��ʾCheckBox�У�0Ϊ����ʾ (ȱʡֵ)
        CollectivityGrid.selBoxEventFuncName = "ShowRemark";  
        CollectivityGrid.hiddenPlus = 1;
        CollectivityGrid.hiddenSubtraction = 1;
        CollectivityGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        myAlert("�� LOAccInputInit.jsp --> initCollectivityGrid �����з����쳣:��ʼ��������� ");
    }
}
</script>
