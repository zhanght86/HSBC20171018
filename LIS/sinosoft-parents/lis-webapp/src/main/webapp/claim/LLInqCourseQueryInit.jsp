<%
//�������ƣ�LLInqCourseQueryInit.jsp
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
    
    fm.BarCodePrint.disabled = true; //��ӡ������
   	fm.ColQueryImage.disabled = true; //Ӱ���ѯ
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
	   fm.CouNo.value = ""; 	    
	   fm.InqDate.value = "";            
	   fm.InqMode.value = ""; 
	   fm.InqModeName.value = "";              
	   fm.InqSite.value = "";   
	   fm.InqByPer.value = "";  	   
	   fm.InqCourse.value = "";                 
	   fm.InqPer1.value = "";           
	   fm.InqPer2.value = "";           
	   fm.Remark.value = "";       	                          
    }
    catch(ex)
    {
        alert("��LLInqCourseQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

                             
function initForm()
{
    try
    {
        initInpBox(); 
        initLLInqCourseGrid();
        initLLInqCertificateGrid();
        initParam();
        LLInqCourseQuery();
     }
    catch(re)
    {
        alert("LLInqCourseQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������۱�ĳ�ʼ��
function initLLInqCourseGrid()
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
        iArray[1][0]="�ⰸ��";             //����
        iArray[1][1]="0px";                //�п�
        iArray[1][2]=100;                  //�����ֵ
        iArray[1][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�������";             //����
        iArray[2][1]="0px";                //�п�
        iArray[2][2]=100;                  //�����ֵ
        iArray[2][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3]=new Array();
        iArray[3][0]="�������";             //����
        iArray[3][1]="120px";                //�п�
        iArray[3][2]=120;                  //�����ֵ
        iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="��������";             //����
        iArray[4][1]="80px";                //�п�
        iArray[4][2]=100;                  //�����ֵ
        iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5]=new Array();
        iArray[5][0]="���鷽ʽ";             //����
        iArray[5][1]="60px";                //�п�
        iArray[5][2]=100;                  //�����ֵ
        iArray[5][3]=0; 

        iArray[6]=new Array();
        iArray[6][0]="����ص�";             //����
        iArray[6][1]="100px";                //�п�
        iArray[6][2]=200;                  //�����ֵ
        iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7]=new Array();
        iArray[7][0]="��������";             //����
        iArray[7][1]="100px";                //�п�
        iArray[7][2]=100;                  //�����ֵ
        iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="�������";             //����
        iArray[8][1]="0px";                //�п�
        iArray[8][2]=100;                  //�����ֵ
        iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9]=new Array();
        iArray[9][0]="�������";             //����
        iArray[9][1]="50px";                //�п�
        iArray[9][2]=100;                  //�����ֵ
        iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[10]=new Array();
        iArray[10][0]="��һ������";             //����
        iArray[10][1]="80px";                //�п�
        iArray[10][2]=100;                  //�����ֵ
        iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[11]=new Array();
        iArray[11][0]="";             //����
        iArray[11][1]="0px";                //�п�
        iArray[11][2]=100;                  //�����ֵ
        iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[12]=new Array();
        iArray[12][0]="";             //����
        iArray[12][1]="0px";                //�п�
        iArray[12][2]=100;                  //�����ֵ
        iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[13]=new Array();
        iArray[13][0]="������";             //����
        iArray[13][1]="80px";                //�п�
        iArray[13][2]=100;                  //�����ֵ
        iArray[13][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������                

        iArray[14]=new Array();
        iArray[14][0]="��ע";             //����
        iArray[14][1]="0px";                //�п�
        iArray[14][2]=100;                  //�����ֵ
        iArray[14][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
                             
        LLInqCourseGrid = new MulLineEnter( "document" , "LLInqCourseGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        LLInqCourseGrid.mulLineCount = 5;   
        LLInqCourseGrid.displayTitle = 1;
        LLInqCourseGrid.locked = 1;
        LLInqCourseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLInqCourseGrid.selBoxEventFuncName = "LLInqCourseGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
        LLInqCourseGrid.hiddenPlus=1;
        LLInqCourseGrid.hiddenSubtraction=1;
        LLInqCourseGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}


//������̵�֤��Ϣ
function initLLInqCertificateGrid()
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
        iArray[1][0]="��֤���";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;
           
        iArray[2]=new Array();
        iArray[2][0]="��֤����";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

		iArray[3]=new Array();
        iArray[3][0]="��֤����";
        iArray[3][1]="200px";
        iArray[3][2]=100;
        iArray[3][3]=0;
            
        iArray[4]=new Array()
        iArray[4][0]="ԭ����־";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;
//		iArray[4][10]="OriFlag";
//        iArray[4][11]="0|^0|ԭ��^1|��ӡ��";  
//        iArray[4][14]="0";
        
        iArray[5]=new Array();
        iArray[5][0]="����";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
//		iArray[5][14]=1;

		iArray[6]=new Array();
        iArray[6][0]="��ע��Ϣ";
        iArray[6][1]="200px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        LLInqCertificateGrid = new MulLineEnter("document","LLInqCertificateGrid");
        LLInqCertificateGrid.mulLineCount = 5;
        LLInqCertificateGrid.displayTitle = 1;
        LLInqCertificateGrid.locked = 0;
        LLInqCertificateGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLInqCertificateGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLInqCertificateGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLInqCertificateGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}
</script>
