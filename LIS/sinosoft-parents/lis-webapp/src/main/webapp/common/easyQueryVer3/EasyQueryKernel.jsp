<%
//�������ƣ�EasyQueryKernel.jsp
//�����ܣ�EasyQuery��ѯ���ܵĺ��ĺ���
//�������ڣ�2002-09-28
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>


<%!
//����EasyQueryUI����SQL����ύ�����ݿ���ң����ؽ���ַ���strResult
public String easyQueryKernel(String strSql, String strStart, String strLargeFlag, String strLimitFlag)
{
    String strResult = "";
    String strError = "";
    Integer intStart;
    Integer intLargeFlag;
    Integer intLimitFlag;

    //strStart
    if (strStart == null || strStart.trim().equals("") || strStart.equals("undefined"))
    {
        intStart = new Integer(String.valueOf("1"));
    }
    else
    {
        intStart = new Integer(strStart);
    }

    //strLargeFlag
    if (strLargeFlag == null || strLargeFlag.trim().equals("") || strLargeFlag.equals("undefined"))
    {
        intLargeFlag = new Integer(String.valueOf("0"));
    }
    else
    {
        intLargeFlag = new Integer(strLargeFlag);
    }

    //strLimitFlag
    if (strLimitFlag == null || strLimitFlag.trim().equals("") || strLimitFlag.equals("undefined"))
    {
        intLimitFlag = new Integer(String.valueOf("0"));
    }
    else
    {
        intLimitFlag = new Integer(strLimitFlag);
    }

    VData tVData = new VData();
    tVData.add(strSql);
    tVData.add(intStart);
    tVData.add(intLargeFlag);    //2005-04-18 zhuxf ��Ӵ���������ѯ��־
    tVData.add(intLimitFlag);    //2006-09-29 XinYQ ���ͻ�� 200 �����ƵĲ�ѯ��־
/******
    EasyQueryUI tEasyQueryUI = new EasyQueryUI();
    tEasyQueryUI.submitData(tVData, "QUERY||MAIN");
    if(tEasyQueryUI.mErrors.needDealError())
    {
        strError = tEasyQueryUI.mErrors.getFirstError();
    }
    else
    {
        tVData.clear() ;
        tVData = tEasyQueryUI.getResult() ;
        strResult = (String)tVData.getObject(0);
    }
****/
       BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   if(!tBusinessDelegate.submitData(tVData,"QUERY||MAIN","EasyQueryUI"))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   String Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   loggerDebug("EasyQueryKernel",Content);
				}
   }
	else
	{
		tVData.clear() ;
		tVData = tBusinessDelegate.getResult() ;
		strResult = (String)tVData.getObject(0);
	}
	
    return strResult;
}
%>
