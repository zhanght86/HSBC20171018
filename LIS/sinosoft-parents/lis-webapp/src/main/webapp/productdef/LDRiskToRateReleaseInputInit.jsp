<%@include file="../i18n/language.jsp"%>
<%
//�������ƣ�WFEdorNoscanAppInit.jsp
//�����ܣ���ȫ������-��ȫ��ɨ������
//�������ڣ�2005-04-27 15:13:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//            XinYQ     2006-11-08   ��ʽ����
//
%>
<%@page contentType="text/html;charset=GBK" %>

<script type="text/javascript">

var CollectivityGrid;
 var CollectivityGrid2;
function initForm()
{
    try
    {
        initCollectivityGrid();          //��ʼ���۸���Ϣ�б�
        
	    fm.all('RiskCode').value = '';
	    fm.all('RateType').value = '';
	    fm.all('RiskCodeName').value = '';
	    fm.all('RateTypeName').value = '';
	   fm.all('changeReason').value = '';
	   fm.all('auditConclusion').value = '';
        init();    
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
        iArray[1][1]="25px";                    //�п�
        iArray[1][2]=30;                        //�����ֵ
        iArray[1][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������


        iArray[2]=new Array();
        iArray[2][0]="��Ʒ����";
        iArray[2][1]="80px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="25px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="���ʱ���";
        iArray[4][1]="40px";
        iArray[4][2]=100;
        iArray[4][3]=0;
         
        iArray[5]=new Array();
        iArray[5][0]="���һ���޸�����";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="���һ���޸���";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="���һ�������";
        iArray[7][1]="50px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="���һ���������";
        iArray[8][1]="50px";
        iArray[8][2]=100;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="���״̬";
        iArray[9][1]="50px";
        iArray[9][2]=100;
        iArray[9][3]=0;
       
        
        CollectivityGrid = new MulLineEnter("fm", "CollectivityGrid");
        //��Щ���Ա�����loadMulLineǰ
        CollectivityGrid.mulLineCount = 5;
        CollectivityGrid.displayTitle = 1;
        CollectivityGrid.locked = 1;				//��������������+����"--"���
        CollectivityGrid.canSel = 1;				//Radio ��ѡ��1 ��ʾ ��0 ���أ�ȱʡֵ��
        CollectivityGrid.canChk = 0;				// 1Ϊ��ʾCheckBox�У�0Ϊ����ʾ (ȱʡֵ)
        CollectivityGrid.selBoxEventFuncName = "ShowPlan";  
        CollectivityGrid.hiddenPlus = 1;
        CollectivityGrid.hiddenSubtraction = 1;
        CollectivityGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        myAlert("�� LOAccInputInit.jsp --> initCollectivityGrid �����з����쳣:��ʼ��������� ");
    }
}


//function initForm1()
//{
//    try
//    {
//        initCollectivityGrid();          //��ʼ���۸���Ϣ�б�
//        
//	    fm.all('RiskCode').value = '';
//	    fm.all('RateType').value = '';
//	 
 //       easyQueryClick();
//        
//    }
//    catch (ex)
//    {
//        alert("�� LOAccInputInit.jsp  initForm �����з����쳣:��ʼ��������� ");
//    }
//}

</script>
