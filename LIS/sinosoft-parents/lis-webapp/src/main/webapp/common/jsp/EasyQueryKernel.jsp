
<%
//�������ƣ�EasyQueryKernel.jsp
//�����ܣ�EasyQuery��ѯ���ܵĺ��ĺ���
//�������ڣ�2002-09-28
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����       
%>

<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%!
// ����EasyQueryUI����SQL����ύ�����ݿ���ң����ؽ���ַ���strResult
public String easyQueryKernel(String strSql, String strStart) { 
  String strResult = "";
  String strError = "";
  Integer intStart = new Integer( String.valueOf( strStart ));
  //loggerDebug("EasyQueryKernel","strSql:" + strSql +"|strStart:" +strStart);

  EasyQueryUI tEasyQueryUI = new EasyQueryUI();
  VData tVData = new VData();
  tVData.add( strSql );
  tVData.add( intStart );
  tEasyQueryUI.submitData( tVData, "QUERY||MAIN" );

  if( tEasyQueryUI.mErrors.needDealError()) {
	  strError = tEasyQueryUI.mErrors.getFirstError();
  } else {
	  tVData.clear() ;
	  tVData = tEasyQueryUI.getResult() ;
	  strResult = ( String )tVData.getObject( 0 );
	  loggerDebug("EasyQueryKernel",strResult);
  }
  
  return strResult;
}
%>
