<%
//**************************************************************************************************
//�ļ����ƣ�ClaimGrpQueryInit.jsp
//�����ܣ��б�����-���屣��-�˹��˱�-���������ѯ��Ӧ���棬������Ϣ����ʾ��������˽��浥ѡ��ť
//          ʱ�����ӵ���ui/uw/ClaimQueryMain.jsp�����ղ�����ⰸ��ѯ��
//�������ڣ�2006-11-08
//������  ��zhaorx
//���¼�¼��
//**************************************************************************************************
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
//����ǰ��ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('GrpAppntNo').value = nullToEmpty("<%= tGrpAppntNo %>");
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
        initLLClaimGrpGrid();
        initQuery();
    }
    catch(re)
    {
        alert("ClaimGrpQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//������Ϣ��
function initLLClaimGrpGrid()
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
        iArray[1][0]="�����ͬ����";      //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[1][1]="100px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�����ⰸ��";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[2][1]="100px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
 
        iArray[3]=new Array();
        iArray[3][0]="�ⰸ�ְ���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[3][1]="80px";                //�п�
        iArray[3][2]=70;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="��������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[4][1]="80px";                //�п�
        iArray[4][2]=60;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="����������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[5][1]="80px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6]=new Array();
        iArray[6][0]="�ⰸ״̬";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[6][1]="60px";                //�п�
        iArray[6][2]=60;                  //�����ֵ
        iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="ǩ������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[7][1]="60px";                //�п�
        iArray[7][2]=60;                  //�����ֵ
        iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������


//        iArray[8]=new Array();
//        iArray[8][0]="��������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
//        iArray[8][1]="60px";                //�п�
//        iArray[8][2]=60;                  //�����ֵ
//        iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[9]=new Array();
//        iArray[9][0]="����״̬";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
//        iArray[9][1]="60px";                //�п�
//        iArray[9][2]=60;                  //�����ֵ
//        iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        

        LLClaimGrpQueryGrid = new MulLineEnter( "fm" , "LLClaimGrpQueryGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLClaimGrpQueryGrid.mulLineCount        = 5;
        LLClaimGrpQueryGrid.displayTitle        = 1;
        LLClaimGrpQueryGrid.canChk              = 0;
        LLClaimGrpQueryGrid.canSel              = 1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLClaimGrpQueryGrid.hiddenPlus          = 1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLClaimGrpQueryGrid.selBoxEventFuncName = "ClaimQueryMainClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLClaimGrpQueryGrid.hiddenSubtraction   = 1; 
        LLClaimGrpQueryGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
  }
  
</script>
