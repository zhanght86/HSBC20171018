<%
//Name��LLLcContQueryInit.jsp
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
        initLcContStateGrid();
        initQuery();
    }
    catch(re)
    {
        alert("LLLcContQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//������Ϣ��
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
        iArray[1][0]="���屣����";      //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[1][1]="150px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="���˱�����";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[2][1]="130px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
 
        iArray[3]=new Array();
        iArray[3][0]="�������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[3][1]="70px";                //�п�
        iArray[3][2]=70;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="��Ч����";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[4][1]="80px";                //�п�
        iArray[4][2]=60;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="��������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[5][1]="80px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6]=new Array();
        iArray[6][0]="ǩ������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[6][1]="60px";                //�п�
        iArray[6][2]=60;                  //�����ֵ
        iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="ǩ������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[7][1]="60px";                //�п�
        iArray[7][2]=60;                  //�����ֵ
        iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������


        iArray[8]=new Array();
        iArray[8][0]="��������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[8][1]="60px";                //�п�
        iArray[8][2]=60;                  //�����ֵ
        iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9]=new Array();
        iArray[9][0]="����״̬";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[9][1]="60px";                //�п�
        iArray[9][2]=60;                  //�����ֵ
        iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[10]=new Array();
        iArray[10][0]="prtno";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[10][1]="60px";                //�п�
        iArray[10][2]=60;                  //�����ֵ
        iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

/*        iArray[10]=new Array();
        iArray[10][0]="״̬";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[10][1]="100px";                //�п�
        iArray[10][2]=150;                  //�����ֵ
        iArray[10][3]=2;                    //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[10][4]='llcontsuspendstate';      //����Ҫ����LDcode�еĴ���
        iArray[10][5]="10|11";             //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
        iArray[10][6]="1|0";              //��������з������ô����еڼ�λֵ*/
                
/*        iArray[11]=new Array();                //�����ʾ����Ϊ����������
        iArray[11][0]="״̬���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[11][1]="50px";                //�п�
        iArray[11][2]=100;                  //�����ֵ
        iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������*/

        LLLcContSuspendGrid = new MulLineEnter( "document" , "LLLcContSuspendGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LLLcContSuspendGrid.mulLineCount = 5;
        LLLcContSuspendGrid.displayTitle = 1;
        LLLcContSuspendGrid.canChk = 0;
        LLLcContSuspendGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLLcContSuspendGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
//        LLLcContSuspendGrid.selBoxEventFuncName = "LLLcContSuspendGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLLcContSuspendGrid.hiddenSubtraction=1; 
        LLLcContSuspendGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
  }

//����״̬��Ϣ��
function initLcContStateGrid()
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
        iArray[1][0]="��ͬ��";             //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[1][1]="150px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�����˺���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[2][1]="130px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
 
        iArray[3]=new Array();
        iArray[3][0]="�������ֺ�";            //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[3][1]="150px";                //�п�
        iArray[3][2]=70;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="״̬����";            //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[4][1]="80px";                //�п�
        iArray[4][2]=60;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="״̬";            //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[5][1]="60px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6]=new Array();
        iArray[6][0]="״̬ԭ��";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[6][1]="80px";                //�п�
        iArray[6][2]=60;                  //�����ֵ
        iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="״̬��Чʱ��";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[7][1]="100px";                //�п�
        iArray[7][2]=60;                  //�����ֵ
        iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="״̬����ʱ��";        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[8][1]="100px";                //�п�
        iArray[8][2]=60;                  //�����ֵ
        iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9]=new Array();
        iArray[9][0]="��ע";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[9][1]="100px";                //�п�
        iArray[9][2]=60;                  //�����ֵ
        iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[10]=new Array();
        iArray[10][0]="����Ա";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[10][1]="80px";                //�п�
        iArray[10][2]=60;                  //�����ֵ
        iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[11]=new Array();
        iArray[11][0]="��������";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[11][1]="80px";                //�п�
        iArray[11][2]=60;                  //�����ֵ
        iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        LcContStateGrid = new MulLineEnter( "document" , "LcContStateGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LcContStateGrid.mulLineCount = 5;
        LcContStateGrid.displayTitle = 1;
        LcContStateGrid.canChk = 0;
        LcContStateGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LcContStateGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
//        LcContStateGrid.selBoxEventFuncName = "LcContStateGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LcContStateGrid.hiddenSubtraction=1; 
        LcContStateGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
  
</script>
