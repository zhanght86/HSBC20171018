<%
//�������ƣ�LLGrpLdPersonQueryInput.jsp
//�����ܣ�����ͻ���Ϣ��ѯ
//�������ڣ� 2008-10-27
//������  ��zhangzheng
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
String GrpContNo     = request.getParameter("GrpContNo");
String GrpCustomerNo = request.getParameter("GrpCustomerNo");
String GrpName       = StrTool.unicodeToGBK(request.getParameter("GrpName"));
String ManageCom     = request.getParameter("ManageCom");
%>

<script language="JavaScript">
var GrpContNo = '<%=GrpContNo%>';
GrpContNo= (GrpContNo=='null'?"":GrpContNo);
var GrpCustomerNo = '<%=GrpCustomerNo%>';
GrpCustomerNo= (GrpCustomerNo=='null'?"":GrpCustomerNo);
var GrpName = '<%=GrpName%>';
GrpName = (GrpName=='null'?"":GrpName);
var ManageCom = '<%=ManageCom%>';
ManageCom = (ManageCom=='null'?"":ManageCom);

function initInpBox()
{ 
    try
    {                                   
				fm.GrpContNo.value = GrpContNo;
				fm.CustomerNo.value = GrpCustomerNo;
				fm.GrpContName.value = GrpName;
				fm.ManageCom.value = ManageCom;
    }
    catch(ex)
    {
        alert("��LLGrpLDPersonQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
       alert("��LLGrpLDPersonQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initPersonGrid();  
    }
    catch(re)
    {
        alert("LLGrpLDPersonQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������Ϣ�б�ĳ�ʼ��
function initPersonGrid()
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
      iArray[1][0]="�����˿ͻ���";             //����
      iArray[1][1]="130px";                //�п�
      iArray[1][2]=100;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����������";             //����
      iArray[2][1]="100px";                //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ա����";             //����
      iArray[3][1]="0px";                //�п�
      iArray[3][2]=0;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�Ա�";             //����
      iArray[4][1]="60px";                //�п�
      iArray[4][2]=0;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��������";             //����
      iArray[5][1]="80px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֤������";             //����
      iArray[6][1]="80px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="֤������";             //����
      iArray[7][1]="120px";                //�п�
      iArray[7][2]=200;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="֤������ֵ";             //����
      iArray[8][1]="80px";                //�п�
      iArray[8][2]=200;                  //�����ֵ
      iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="����Ͷ���˿ͻ���";             //����
      iArray[9][1]="0px";                //�п�
      iArray[9][2]=0;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="����Ͷ���˿ͻ�����";             //����
      iArray[10][1]="0px";                //�п�
      iArray[10][2]=0;                  //�����ֵ
      iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="�����ͬ��";             //����
      iArray[11][1]="0px";                //�п�
      iArray[11][2]=0;                  //�����ֵ
      iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      
      PersonGrid = new MulLineEnter( "fm" , "PersonGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PersonGrid.mulLineCount = 0;   
      PersonGrid.displayTitle = 1;
      PersonGrid.locked = 1;
      PersonGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      PersonGrid.hiddenPlus=1;
      PersonGrid.hiddenSubtraction=1;
      PersonGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
