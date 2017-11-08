<html>
<%@page contentType="text/html; charset=GBK" %>
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

<%
    String strErrInfo = "";
    String strPrtseq = request.getParameter("EdorNo");
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");   
    String strType = "Endorsement";
    System.out.println("EdorNo: " + strPrtseq);
    System.out.println("prtType: " + strType);
    
    //Get the path of VTS file from LDSysVar table	
    LDSysVarDB tLDSysVarDB = new LDSysVarDB();

    String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
    LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);    
    LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
    String strFilePath = tLDSysVarSchema.getV("SysVarValue");
    String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
    
    //��ȡ�����ʱ�ļ���·��
    String strRealPath = application.getRealPath("/").replace('\\','/');
    String strVFPathName = strRealPath + strVFFileName;
    
    CombineVts tcombineVts=null;
    InputStream ins=null;
  
    if( strPrtseq != null && !strPrtseq.equals("") ) // �ϲ�VTSģ���ļ��������ļ����������������
    {  
        // �������ݿ�����
       try
       {
          Connection conn = DBConnPool.getConnection();
          Statement stmt = null;
          ResultSet rs = null;
    
          if( conn == null ) 
          { 
             strErrInfo = "�������ݿ�ʧ��"; 
          } 
          else
          {
             stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             String strSQL = "SELECT * FROM LOREPORTMANAGER WHERE PRTSEQ = '" + strPrtseq + "'";
             
             rs = stmt.executeQuery(strSQL);
               
             if(rs.next())
             {              
                //��������ļ�
                COracleBlob tCOracleBlob = new COracleBlob();
                Blob tBlob = null;
                   
                String tSQL = " and Prtseq = '" + strPrtseq + "'";
                System.out.println("==> ��ȫ����");
                tBlob = tCOracleBlob.SelectBlob("LOREPORTMANAGER","reportinfo",tSQL,conn);
                ins=tBlob.getBinaryStream();
                System.out.println("get stream object");
             } 
             else
             {              
                System.out.println("can't get stream object");
             }
             rs.close();
             stmt.close();
             conn.close();                

             //�ϲ�VTS�ļ� 
             String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
             tcombineVts = new CombineVts(ins,strTemplatePath);
               
             ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
             tcombineVts.output(dataStream);
              
             //��dataStream�洢�������ļ�
             AccessVtsFile.saveToFile(dataStream,strVFPathName);
             System.out.println("==> Write VTS file to disk ");
             
  					 System.out.println("===strVFFileName : "+strVFFileName);
  					 session.putValue("RealPath", strVFPathName);
  					 request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);

             //session.putValue("RealPath", strVFPathName);
	         	 //session.putValue("PrintNo",strPrtseq );
	           //session.putValue("Code","endorsement"); 
	           //System.out.println("put session value....");
	         	 //request.getRequestDispatcher("../uw/GetF1PrintEndorse.jsp").forward(request,response);
          }
       }
       catch(Exception ex)
       {
          ex.printStackTrace();
       }
   }    
   else
   {
      strErrInfo = "�������ݴ���ʧ��";
   }
%>
<body>
<%= strErrInfo %>
</body>
</html>
