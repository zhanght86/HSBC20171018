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
    
    
    //Get the path of VTS file from LDSysVar table	
    LDSysVarDB tLDSysVarDB = new LDSysVarDB();
    //获取临时文件名
    String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
    LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);    
    LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
    String strFilePath = tLDSysVarSchema.getV("SysVarValue");
    String strVFFileName2 = strFilePath + FileQueue.getFileName()+".vts";
    String strVFFileName1 = strFilePath + FileQueue.getFileName();
    
    //获取存放临时文件的路径
    String strRealPath = application.getRealPath("/").replace('\\','/');
    //String strVFPathName = strRealPath + strVFFileName;    
    //String strSql2 = "select * from ldsysvar where Sysvar='VTSRealPath'";
    //tLDSysVarSet = tLDSysVarDB.executeQuery(strSql2);    
    //tLDSysVarSchema = tLDSysVarSet.get(1);
    //String strRealPath = tLDSysVarSchema.getV("SysVarValue");
    //String strVFPathName = strRealPath + strVFFileName;
    String strName = strRealPath + strVFFileName2;
    
    String strTemplatePath = "";
  
    if( strEdorAcceptNo != null && !strEdorAcceptNo.equals("") ) {  // 合并VTS模板文件与数据文件存入服务器磁盘中
        // 建立数据库连接
        ExeSQL aExeSQL = new ExeSQL();
        SSRS aSSRS = new SSRS();
        aSSRS = aExeSQL.execSQL("select distinct edorno from lpedoritem where edoracceptno = '" + strEdorAcceptNo + "'");
        int a = aSSRS.getMaxRow();
        if(a > 1){
            //多个批单(一个模板)合并
            loggerDebug("AppEndorsementUptF1PJ1","============= 多个批单合并 ===========");
            loggerDebug("AppEndorsementUptF1PJ1","=========== 合并个数 ：" + a);
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
                      strErrInfo = "连接数据库失败";
                  } 
                  else{
                      stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                      rs = stmt.executeQuery("SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + aSSRS.GetText(j, 1) + "'");
                      loggerDebug("AppEndorsementUptF1PJ1","SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + aSSRS.GetText(j, 1) + "'");             
                      if( rs.next() )
                      {
                          //输出数据文件
                          COracleBlob tCOracleBlob = new COracleBlob();
                          Blob tBlob = null;                 
                          String tSQL = " and EdorNo = '" + aSSRS.GetText(j, 1) + "'";

	                        loggerDebug("AppEndorsementUptF1PJ1","==> 个人批单");
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
                                        
                       //合并VTS文件 
                      strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
                      loggerDebug("AppEndorsementUptF1PJ1",strTemplatePath); 
                      tcombineVts = new CombineVts((InputStream)o,strTemplatePath);
                        
                      ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                      tcombineVts.output(dataStream);
                        
                       //把dataStream存储到磁盘文件
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
	        response.sendRedirect("../uw/GetF1PrintEndorse.jsp");    
        }
        else{
            //单个模板
            loggerDebug("AppEndorsementUptF1PJ1","单个模板");
            CombineVts tcombineVts=null;
            InputStream ins = null;
            try
            {
                Connection conn = DBConnPool.getConnection();
                Statement stmt = null;
                ResultSet rs = null;                
                if( conn == null ) 
                { 
                   strErrInfo = "连接数据库失败"; 
                }
                else{
                   stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                   rs = stmt.executeQuery("SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + aSSRS.GetText(1, 1) + "'");
                   loggerDebug("AppEndorsementUptF1PJ1","SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + aSSRS.GetText(1, 1) + "'");  
                   if( rs.next() )
                   {
                       //输出数据文件
                       COracleBlob tCOracleBlob = new COracleBlob();
                       Blob tBlob = null;                 
                       String tSQL = " and EdorNo = '" + aSSRS.GetText(1, 1) + "'";

	                     loggerDebug("AppEndorsementUptF1PJ1","==> 个人批单");
                       tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT","edorinfo",tSQL,conn);
                       
                       ins=tBlob.getBinaryStream();
                       loggerDebug("AppEndorsementUptF1PJ1","get stream object");              
                   } 
                   else{
                       loggerDebug("AppEndorsementUptF1PJ1","can't get stream object");
                   }
                   //合并VTS文件 
                   strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
                   loggerDebug("AppEndorsementUptF1PJ1",strTemplatePath); 
                   tcombineVts = new CombineVts(ins,strTemplatePath);
                     
                   ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                   tcombineVts.output(dataStream);
                     
                    //把dataStream存储到磁盘文件
                   AccessVtsFile.saveToFile(dataStream,strName); 
                   loggerDebug("AppEndorsementUptF1PJ1","==> Write VTS file to disk ");   
                   session.putValue("RealPath", strName);
           	       session.putValue("PrintNo",strEdorAcceptNo );
	               session.putValue("Code","appendorsement"); 
	               response.sendRedirect("../uw/GetF1PrintEndorse.jsp");            
                }
            }
            catch(Exception ex)
            {
               ex.printStackTrace();
            }            
        }
    }    
    else{
        strErrInfo = "没有输入保全号";
    }
%>
<body>
<%= strErrInfo %>
</body>
</html>
