<%
//�������ƣ�LLInqFeeQueryInit.jsp
//�����ܣ����������Ϣ��ѯҳ���ʼ��
//�������ڣ�2005-06-23
//������  ��yuejw
//���¼�¼��
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            
<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('InqNo').value = nullToEmpty("<%= tInqNo %>");
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initInpBox()
{ 
    try
    {         
	   fm.ClmNo1.value = "";            
	   fm.InqNo1.value = "";   
	   fm.InqDept.value = "";   
	   fm.FeeType.value = "";   
	   fm.FeeTypeName.value = "";   
	   fm.FeeDate.value = "";   
	   fm.FeeSum.value = "";   
	   fm.Payee.value = "";   
	   fm.PayeeType.value = "";   
	   fm.PayeeTypeName.value = "";   
	   fm.Remark.value =""; 	                          
    }
    catch(ex)
    {
        alert("��LLInqFeeQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

                             
function initForm()
{
    try
    {
        initInpBox(); 
        initLLInqFeeGrid();
        initParam();
        LLInqFeeQuery();
     }
    catch(re)
    {
        alert("LLInqFeeQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������۱�ĳ�ʼ��
function initLLInqFeeGrid()
{                             
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                //�п�
        iArray[0][2]=10;                  //�����ֵ
        iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="������";             //����
        iArray[1][1]="160px";                //�п�
        iArray[1][2]=160;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�������";             //����
        iArray[2][1]="80px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3]=new Array();
        iArray[3][0]="�������";             //����
        iArray[3][1]="60px";                //�п�
        iArray[3][2]=100;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="��������";             //����
        iArray[4][1]="80px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="����ʱ��";             //����
        iArray[5][1]="80px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=0; 

        iArray[6]=new Array();
        iArray[6][0]="���ý��";             //����
        iArray[6][1]="80px";                //�п�
        iArray[6][2]=200;                  //�����ֵ
        iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="�����";             //����
        iArray[7][1]="100px";                //�п�
        iArray[7][2]=100;                  //�����ֵ
        iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="��ʽ";             //����
        iArray[8][1]="0px";                //�п�
        iArray[8][2]=100;                  //�����ֵ
        iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9]=new Array();
        iArray[9][0]="��ע";             //����
        iArray[9][1]="0px";                //�п�
        iArray[9][2]=100;                  //�����ֵ
        iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[10]=new Array();
        iArray[10][0]="������";             //����
        iArray[10][1]="80px";                //�п�
        iArray[10][2]=100;                  //�����ֵ
        iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������        
                            
        LLInqFeeGrid = new MulLineEnter( "fm" , "LLInqFeeGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        LLInqFeeGrid.mulLineCount = 0;   
        LLInqFeeGrid.displayTitle = 1;
        LLInqFeeGrid.locked = 1;
        LLInqFeeGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLInqFeeGrid.selBoxEventFuncName = "LLInqFeeGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLInqFeeGrid.hiddenPlus=1;
        LLInqFeeGrid.hiddenSubtraction=1;
        LLInqFeeGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
