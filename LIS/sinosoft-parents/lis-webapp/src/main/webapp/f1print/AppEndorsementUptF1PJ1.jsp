<html>
<%@page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.bl.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="org.jdom.input.DOMBuilder"%>
<%@page import="org.jdom.Document"%>
<%@page import="com.f1j.util.F1Exception"%>
<%@page import="com.f1j.ss.BookModelImpl"%>



<%
    String strEdorAcceptNo = request.getParameter("EdorAcceptNo");
    String strErrInfo = "";
    
    //String strType = request.getParameter("type");
    String strType = "Endorsement";
    loggerDebug("AppEndorsementUptF1PJ1","prtType: " + strType);
    loggerDebug("AppEndorsementUptF1PJ1",strEdorAcceptNo);
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");      
    
    //Get the path of VTS file from LDSysVar table	
    LDSysVarDB tLDSysVarDB = new LDSysVarDB();
    //��ȡ��ʱ�ļ���
    String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
    LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);    
    LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
    String strFilePath = tLDSysVarSchema.getV("SysVarValue");
    String strVFFileName2 = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
    String strVFFileName1 = strFilePath + tG.Operator + "_" + FileQueue.getFileName();
    
    //��ȡ�����ʱ�ļ���·��
    String strRealPath = application.getRealPath("/").replace('\\','/');
    //String strVFPathName = strRealPath + strVFFileName;    
    //String strSql2 = "select * from ldsysvar where Sysvar='VTSRealPath'";
    //tLDSysVarSet = tLDSysVarDB.executeQuery(strSql2);    
    //tLDSysVarSchema = tLDSysVarSet.get(1);
    //String strRealPath = tLDSysVarSchema.getV("SysVarValue");
    //String strVFPathName = strRealPath + strVFFileName;
    String strName = strRealPath + strVFFileName2;
    
    String strTemplatePath = "";
  
    if( strEdorAcceptNo != null && !strEdorAcceptNo.equals("") ) {  // �ϲ�VTSģ���ļ��������ļ����������������
        // �������ݿ�����
        ExeSQL aExeSQL = new ExeSQL();
        SSRS aSSRS = new SSRS();
        aSSRS = aExeSQL.execSQL("select distinct edorno from lpgrpedoritem where edoracceptno = '" + strEdorAcceptNo + "'");
        if(aSSRS == null || aSSRS.getMaxRow() < 1)
        {
        	aSSRS = aExeSQL.execSQL("select distinct edorno from lpedoritem where edoracceptno = '" + strEdorAcceptNo + "'");
        }
        int a = aSSRS.getMaxRow();
        if(a > 1){
            //�������(һ��ģ��)�ϲ�
            loggerDebug("AppEndorsementUptF1PJ1","============= ��������ϲ� ===========");
            loggerDebug("AppEndorsementUptF1PJ1","=========== �ϲ����� ��" + a);
            String[] strVFPathName = new String[a];
            Object o = null;
            for (int j = 1; j <= aSSRS.getMaxRow(); j++) 
            {
               try{
                  CombineVts tcombineVts=null;
                  
                  Connection conn = DBConnPool.getConnection();
                  Statement stmt = null;
                  ResultSet rs = null;  
                  InputStream ins = null;
                  if( conn == null ) 
                  {
                      strErrInfo = "�������ݿ�ʧ��";
                  } 
                  else{
                      stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                      rs = stmt.executeQuery("SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + aSSRS.GetText(j, 1) + "'");
                      loggerDebug("AppEndorsementUptF1PJ1","SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + aSSRS.GetText(j, 1) + "'");             
                      if( rs.next() )
                      {
                          //��������ļ�
                          COracleBlob tCOracleBlob = new COracleBlob();
                          Blob tBlob = null;                 
                          String tSQL = " and EdorNo = '" + aSSRS.GetText(j, 1) + "'";

	                        loggerDebug("AppEndorsementUptF1PJ1","==> ��������");
                          tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT","edorinfo",tSQL,conn);
                          
                          o = new Object();
                          o = (InputStream)tBlob.getBinaryStream();
                          //ins=tBlob.getBinaryStream();
                          loggerDebug("AppEndorsementUptF1PJ1","get stream object");              
                      } 
                      else{
                          loggerDebug("AppEndorsementUptF1PJ1","can't get stream object");
                      }
                      rs.close();
                      stmt.close();
                      conn.close();                
                                        
                       //�ϲ�VTS�ļ� 
                      strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
                      loggerDebug("AppEndorsementUptF1PJ1",strTemplatePath); 
                      tcombineVts = new CombineVts((InputStream)o,strTemplatePath);
                        
                      ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                      tcombineVts.output(dataStream);
                        
                       //��dataStream�洢�������ļ�
                      strVFPathName[j-1] = String.valueOf(j-1) + "Endorsement.vts";
                      AccessVtsFile.saveToFile(dataStream,strVFPathName[j-1]);
                  }
               }
               catch(Exception ex)
               {
                  ex.printStackTrace();
               }
            }
            VtsFileCombine vtsfilecombine = new VtsFileCombine();
            BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
            try
            {  
               loggerDebug("AppEndorsementUptF1PJ1","========79679=========" + strName);
               vtsfilecombine.write(tb, strName);
            }
            catch (IOException ex)
            {
            }
            catch (F1Exception ex)
            {
            }
            session.putValue("RealPath", strName);
           	session.putValue("PrintNo",strEdorAcceptNo );
	        session.putValue("Code","appendorsement"); 
	        //response.sendRedirect("../uw/GetF1PrintEndorse.jsp");    
	        request.getRequestDispatcher("../uw/GetF1PrintEndorse.jsp").forward(request,response); 
        }
        else{
            //����ģ��
            loggerDebug("AppEndorsementUptF1PJ1","����ģ��");
            CombineVts tcombineVts=null;
            InputStream ins = null;
            try
            {
                Connection conn = DBConnPool.getConnection();
                Statement stmt = null;
                ResultSet rs = null;                
                if( conn == null ) 
                { 
                   strErrInfo = "�������ݿ�ʧ��"; 
                }
                else{
                   stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                   rs = stmt.executeQuery("SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + aSSRS.GetText(1, 1) + "'");
                   loggerDebug("AppEndorsementUptF1PJ1","SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + aSSRS.GetText(1, 1) + "'");  
                   if( rs.next() )
                   {
                       //��������ļ�
                       COracleBlob tCOracleBlob = new COracleBlob();
                       Blob tBlob = null;                 
                       String tSQL = " and EdorNo = '" + aSSRS.GetText(1, 1) + "'";

	                     loggerDebug("AppEndorsementUptF1PJ1","==> ��������");
                       tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT","edorinfo",tSQL,conn);
                       
                       ins=tBlob.getBinaryStream();
                       loggerDebug("AppEndorsementUptF1PJ1","get stream object");              
                   } 
                   else{
                       loggerDebug("AppEndorsementUptF1PJ1","can't get stream object");
                   }
                   //�ϲ�VTS�ļ� 
                   strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
                   loggerDebug("AppEndorsementUptF1PJ1",strTemplatePath); 
                   tcombineVts = new CombineVts(ins,strTemplatePath);
                     
                   ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                   tcombineVts.output(dataStream);
                     
                    //��dataStream�洢�������ļ�
                   AccessVtsFile.saveToFile(dataStream,strName); 
                   loggerDebug("AppEndorsementUptF1PJ1","==> Write VTS file to disk ");   
                   session.putValue("RealPath", strName);
           	       session.putValue("PrintNo",strEdorAcceptNo );
	               session.putValue("Code","appendorsement"); 
	               //response.sendRedirect("../uw/GetF1PrintEndorse.jsp"); 
	               request.getRequestDispatcher("../uw/GetF1PrintEndorse.jsp").forward(request,response);           
                }
            }
            catch(Exception ex)
            {
               ex.printStackTrace();
            }            
        }
    }    
    else{
        strErrInfo = "û�����뱣ȫ��";
    }
%>
<body>
<%= strErrInfo %>
</body>
</html>
