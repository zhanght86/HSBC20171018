<%
//Name��LLLClaimQueryInit.jsp
//Function��������ѯҳ���ʼ��
//Date��2005.06.21
//Author��quyang
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
//����ǰ��ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('AppntNo').value = nullToEmpty("<%= tAppntNo %>");
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");

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
function initForm()
{
    try
    {
    	initParam();
        initLLLcContSuspendGrid();
        initQuery();
    }
    catch(re)
    {
        alert("LLLClaimQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
//����������Ϣ
function initLLLcContSuspendGrid()
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
        iArray[1][0]="����������";      //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[1][1]="100px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="���屣����";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[2][1]="100px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
 
        iArray[3]=new Array();
        iArray[3][0]="���˿ͻ���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[3][1]="80px";                //�п�
        iArray[3][2]=80;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="����������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[4][1]="60px";                //�п�
        iArray[4][2]=60;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="��������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[5][1]="70px";                //�п�
        iArray[5][2]=80;                  //�����ֵ
        iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6]=new Array();
        iArray[6][0]="����״̬";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[6][1]="50px";                //�п�
        iArray[6][2]=80;                  //�����ֵ
        iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="�᰸����";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[7][1]="80px";                //�п�
        iArray[7][2]=80;                  //�����ֵ
        iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="���ִ���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[8][1]="60px";                //�п�
        iArray[8][2]=60;                  //�����ֵ
        iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[9]=new Array();
      iArray[9][0]="����";      	   		//����
      iArray[9][1]="80px";            			//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[10]=new Array();
        iArray[10][0]="Ӧ�⸶���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[10][1]="60px";                //�п�
        iArray[10][2]=60;                  //�����ֵ
        iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������        
        
        iArray[11]=new Array();
        iArray[11][0]="ʵ���⸶���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[11][1]="60px";                //�п�
        iArray[11][2]=60;                  //�����ֵ
        iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������        

        LLLcContSuspendGrid = new MulLineEnter( "document" , "LLLcContSuspendGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLLcContSuspendGrid.mulLineCount = 5;
        LLLcContSuspendGrid.displayTitle = 1;
        LLLcContSuspendGrid.canChk = 0;
        LLLcContSuspendGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLLcContSuspendGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLLcContSuspendGrid.selBoxEventFuncName = "LLLClaimQueryGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLLcContSuspendGrid.hiddenSubtraction=1; 
        LLLcContSuspendGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
  }

function initSubReportGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�����˱���"; //ԭ�¹��߿ͻ���
        iArray[1][1]="150px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="����������"; //�¹�������
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�Ա�";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;

//        iArray[3]=new Array();
//        iArray[3][0]="ҽԺ����";
//        iArray[3][1]="0px";
//        iArray[3][2]=60;
//        iArray[3][3]=3;
//        iArray[3][4]="HospitalCode";
//        iArray[3][5]="3|4";                                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
//        iArray[3][6]="0|1";                        //��������з������ô����еڼ�λֵ
//        iArray[3][9]="ҽԺ����|NOTNULL";

//    iArray[4]=new Array()
//    iArray[4][0]="ҽԺ����";
//    iArray[4][1]="0px";
//    iArray[4][2]=100;
//    iArray[4][3]=3;

//    iArray[5]=new Array()
//    iArray[5][0]="��Ժ����";
//    iArray[5][1]="0px";
//    iArray[5][2]=100;
//    iArray[5][3]=3;

        iArray[5]=new Array()
        iArray[5][0]="VIP��־";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;

//        iArray[6]=new Array();
//        iArray[6][0]="����ϸ��";
//        iArray[6][1]="0px";
//        iArray[6][2]=100;
//        iArray[6][3]=3;
//
//        iArray[7]=new Array();
//        iArray[7][0]="����ҽԺ";
//        iArray[7][1]="0px";
//        iArray[7][2]=100;
//        iArray[7][3]=3;
//
//        iArray[8]=new Array();
//        iArray[8][0]="�������";
//        iArray[8][1]="0px";
//        iArray[8][2]=100;
//        iArray[8][3]=3;
//
//        iArray[9]=new Array();
//        iArray[9][0]="������ʶ";
//        iArray[9][1]="0px";
//        iArray[9][2]=100;
//        iArray[9][3]=3;
//
//        iArray[10]=new Array();
//        iArray[10][0]="��ע��Ϣ";
//        iArray[10][1]="0px";
//        iArray[10][2]=100;
//        iArray[10][3]=3;

        SubReportGrid = new MulLineEnter("document","SubReportGrid");
        SubReportGrid.mulLineCount = 5;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
//        SubReportGrid.canChk =1;
        SubReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //��Ӧ�ĺ���������������
        SubReportGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���
        SubReportGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        SubReportGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        SubReportGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
