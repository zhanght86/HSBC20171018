package com.sinosoft.lis.fininterface.utility;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.sinosoft.lis.db.FITableColumnDefDB;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.lis.vschema.FITableColumnDefSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.lis.vschema.FIAboriginalDataForDealSet;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;
import com.sinosoft.lis.schema.FIAboriginalDataForDealSchema;
import com.sinosoft.utility.ExeSQL;

/*******************************************************************************
 * 针对任何类型的SQL查询处理 该类仅仅支持针对表FIAboriginalData的数据处理
 *
 *
 * @author lijs
 * @createTime 2008-09-09
 *
 */

public class ExeSQLForInterFace
{
private static Logger logger = Logger.getLogger(ExeSQLForInterFace.class);


	private Connection con;

	/*****
	 * 保存对应的字段转换信息
	 */
	private FITableColumnDefSet m_oFITableColumnDefSet = null;

        private String mType ;

	public CErrors mErrors = new CErrors(); // 错误信息


	public ExeSQLForInterFace(String DataFlag) throws Exception
        {
            try
            {
                FITableColumnDefDB oFITableColumnDefDB = new FITableColumnDefDB();
                if (DataFlag.equals("Normal")) {
                    oFITableColumnDefDB.setTableID("FIAboriginalData");
                    mType = "01";
                }
                if (DataFlag.equals("ForDeal")) {
                    oFITableColumnDefDB.setTableID("FIAboriginalDataForDeal");
                    mType = "02";
                }
                m_oFITableColumnDefSet = oFITableColumnDefDB.query();
                if (oFITableColumnDefDB.mErrors.needDealError()) {
                    Exception tException = new Exception(
                            "ExeSQLForInterFace类初始化失败，数据查询出错" +
                            oFITableColumnDefDB.mErrors.getFirstError());
                    throw tException;
                }
                if (m_oFITableColumnDefSet == null ||
                    m_oFITableColumnDefSet.size() < 1) {
                    Exception tException = new Exception(
                            "ExeSQLForInterFace类初始化失败，未查询到" +
                            oFITableColumnDefDB.getTableID() + "表数据字段定义记录");
                    throw tException;
                }

                FIAboriginalDataSchema tFIAboriginalDataSchema = new FIAboriginalDataSchema();
                FIAboriginalDataForDealSchema tFIAboriginalDataForDealSchema = new FIAboriginalDataForDealSchema();
                for (int i = 1; i <= m_oFITableColumnDefSet.size(); i++)
                {
                    if(m_oFITableColumnDefSet.get(i).getTableID().equals("FIAboriginalData"))
                    {
                        if (tFIAboriginalDataSchema.getFieldIndex(m_oFITableColumnDefSet.get(i).getColumnID()) == -1)
                        {
                            Exception tException = new Exception("ExeSQLForInterFace类初始化出错，FIAboriginalData表中没有字段" + m_oFITableColumnDefSet.get(i).getColumnID());
                            throw tException;
                        }
                    }
                    if(m_oFITableColumnDefSet.get(i).getTableID().equals("FIAboriginalDataForDeal"))
                    {
                        if (tFIAboriginalDataForDealSchema.getFieldIndex(m_oFITableColumnDefSet.get(i).getColumnID()) == -1)
                        {
                            Exception tException = new Exception("ExeSQLForInterFace类初始化出错，FIAboriginalDataForDeal表中没有字段" + m_oFITableColumnDefSet.get(i).getColumnID());
                            throw tException;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Exception tException = new Exception("ExeSQLForInterFace类初始化异常，信息为：" + ex.getMessage());
                throw tException;
            }

	}





	/****
	 * 通过SQL脚本得到对应的Schema的SET对象值
	 * SQL中对应的别名一定要是该表中包含的字段方可,至于顺序不用考虑.
	 * @param sql
	 * @return FIAboriginalDataSet
	 */
	public FIAboriginalDataSet executeQueryA(String sql,FIDataBaseLinkSchema tFIDataBaseLinkSchema) throws Exception
        {
            if(!mType.equals("01"))
            {
                Exception tException = new Exception("方法调用与初始化标志不匹配");
                throw tException;
            }
            Statement stmt = null;
            ResultSet rs = null;
            con = null;
            FIAboriginalDataSet mFIAboriginalDataSet = new FIAboriginalDataSet();;
            this.mErrors.clearErrors();

            try
            {

                con = DBConnPool.getConnection(tFIDataBaseLinkSchema);
                stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
                rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));

                while (rs.next())
                {
                    if (!setAboriginalSchema(rs, mFIAboriginalDataSet)) {
                        Exception tException = new Exception("FIAboriginalDataSchema生成错误" + this.mErrors.getFirstError());
                        throw tException;
                    }
                }

                try {
                    rs.close();
                } catch (Exception ex) {
                    Exception tException = new Exception("关闭rs出现异常，信息为：" + ex.getMessage());
                    throw tException;
                }

                try {
                    stmt.close();
                } catch (Exception ex) {
                    Exception tException = new Exception("关闭stmt出现异常，信息为：" + ex.getMessage());
                    throw tException;
                }

                try {
                	if(con != null){
                        con.close();
                	}
                } catch (Exception ex) {
                    Exception tException = new Exception("关闭con出现异常，信息为：" + ex.getMessage());
                    throw tException;
                }
            }
            catch (Exception e)
            {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        // 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                stmt.close();
                            } catch (SQLException ex) {}
                        }
                    }
                	if(con != null){
                        con.close();
                	}
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        // 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                stmt.close();
                            } catch (SQLException ex) {}
                        }
                    }
                	if(con != null){
                        con.close();
                	}
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return mFIAboriginalDataSet;
        }

        public FIAboriginalDataForDealSet executeQueryB(String sql,FIDataBaseLinkSchema tFIDataBaseLinkSchema)throws Exception
        {
            if(!mType.equals("02"))
            {
                Exception tException = new Exception("方法调用与初始化标志不匹配");
                throw tException;
            }
            Statement stmt = null;
            ResultSet rs = null;
            con = null;
            FIAboriginalDataForDealSet tFIAboriginalDataForDealSet =  new FIAboriginalDataForDealSet();
            this.mErrors.clearErrors();

            try
            {

                con = DBConnPool.getConnection(tFIDataBaseLinkSchema);
                stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
                rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));

                while (rs.next())
                {
                    if (!setDataForDealSchema(rs, tFIAboriginalDataForDealSet)) {
                        Exception tException = new Exception("FIAboriginalDataForDealSchema生成错误" + this.mErrors.getFirstError());
                        throw tException;
                    }
                }

                try {
                    rs.close();
                } catch (Exception ex) {
                    Exception tException = new Exception("关闭rs出现异常，信息为：" + ex.getMessage());
                    throw tException;
                }

                try {
                    stmt.close();
                } catch (Exception ex) {
                    Exception tException = new Exception("关闭stmt出现异常，信息为：" + ex.getMessage());
                    throw tException;
                }

                try {
                	if(con != null){
                        con.close();
                	}
                } catch (Exception ex) {
                    Exception tException = new Exception("关闭con出现异常，信息为：" + ex.getMessage());
                    throw tException;
                }
            }
            catch (Exception e)
            {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        // 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                stmt.close();
                            } catch (SQLException ex) {}
                        }
                    }
                	if(con != null){
                        con.close();
                	}
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        // 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                stmt.close();
                            } catch (SQLException ex) {}
                        }
                    }
                	if(con != null){
                        con.close();
                	}
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return tFIAboriginalDataForDealSet;
       }


	public boolean setAboriginalSchema(ResultSet rs,FIAboriginalDataSet tFIAboriginalDataSet)
        {
		try {
			FIAboriginalDataSchema oFIAboriginalDataSchema = new FIAboriginalDataSchema();
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			//如果配置了对应的内外部转换则走转字段含义赋值
			if (m_oFITableColumnDefSet != null && m_oFITableColumnDefSet.size() > 1)
                        {
				for (int J = 1; J <= numberOfColumns; J++)
                                {
					for (int k = 1; k <= m_oFITableColumnDefSet.size(); k++)
                                        {
						if (m_oFITableColumnDefSet.get(k).getColumnMark().equalsIgnoreCase(rsmd.getColumnName(J)))
                                                {
                                                    oFIAboriginalDataSchema.setV(m_oFITableColumnDefSet.get(k).getColumnID(), rs.getString(rsmd.getColumnName(J)));
                                                    break;
						}
                                                if(k == m_oFITableColumnDefSet.size())
                                                {
                                                    buildError("ExeSQLForInterFace","setAboriginalSchema","FIAboriginalData表字段配置表中无记录对应外部标识" + rsmd.getColumnName(J)  );
                                                    return false;
                                                }
					}
				}
			}

			tFIAboriginalDataSet.add(oFIAboriginalDataSchema);
		}
                catch (SQLException sqle)
                {
                    buildError("ExeSQLForInterFace","setAboriginalSchema","生成FIAboriginalDataSchema字段数据出错，信息为：" + sqle.getMessage()  );
                    return false;
		}
		return true;
	}

        public boolean setDataForDealSchema(ResultSet rs,FIAboriginalDataForDealSet tFIAboriginalDataForDealSet)
        {
                try {
                        FIAboriginalDataForDealSchema oFIAboriginalDataForDealSchema = new FIAboriginalDataForDealSchema();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int numberOfColumns = rsmd.getColumnCount();
                        // logger.debug(numberOfColumns);
                        //如果配置了对应的内外部转换则走转字段含义赋值
                        if (m_oFITableColumnDefSet != null && m_oFITableColumnDefSet.size() > 1)
                        {
                                for (int J = 1; J <= numberOfColumns; J++)
                                {
                                        for (int k = 1; k <= m_oFITableColumnDefSet.size(); k++)
                                        {
                                                if (m_oFITableColumnDefSet.get(k).getColumnMark().equalsIgnoreCase(rsmd.getColumnName(J)))
                                                {
                                                    oFIAboriginalDataForDealSchema.setV(m_oFITableColumnDefSet.get(k).getColumnID(), rs.getString(rsmd.getColumnName(J)));
                                                    break;
                                                }
                                                if(k == m_oFITableColumnDefSet.size())
                                                {
                                                    buildError("ExeSQLForInterFace","setDataForDealSchema","FIAboriginalDataForDeal表字段配置表中无记录对应外部标识" + rsmd.getColumnName(J)  );
                                                    return false;
                                                }
                                        }
                                }
                        }

                        tFIAboriginalDataForDealSet.add(oFIAboriginalDataForDealSchema);
                }
                catch (SQLException sqle)
                {
                    buildError("ExeSQLForInterFace","setDataForDealSchema","生成FIAboriginalDataSchema字段数据出错，信息为：" + sqle.getMessage()  );
                    return false;
                }
                return true;
	}


        private void buildError(String szModuleName, String szFunc, String szErrMsg)
        {
            CError cError = new CError();
            cError.moduleName = szModuleName;
            cError.functionName = szFunc;
            cError.errorMessage = szErrMsg;
            this.mErrors.addOneError(cError);
            logger.debug(szErrMsg);
       }

	public static void main(String[] args)
        {

         try
         {
             FIDataBaseLinkSchema tFIDataBaseLinkSchema = new FIDataBaseLinkSchema();
             tFIDataBaseLinkSchema.setDBName("LIS");
             tFIDataBaseLinkSchema.setServerName("LIS");
             tFIDataBaseLinkSchema.setDBType("ORACLE");
             tFIDataBaseLinkSchema.setIP("127.0.0.1");
             tFIDataBaseLinkSchema.setUserName("lis");
             tFIDataBaseLinkSchema.setPassWord("a");
             tFIDataBaseLinkSchema.setPort("1521");
             FIAboriginalDataSet oAboriginalDataSet = new FIAboriginalDataSet();
             ExeSQLForInterFace oExeSQLForInterFace = new ExeSQLForInterFace("Normal");
             String aa = "select tempfeeno as tempfeeno from ljtempfee where tempfeeno = 'N0000000000000000028'";
             ExeSQL tExeSQL = new ExeSQL();


             for(int i=0; i<=1000;i++)
             {
               oAboriginalDataSet = oExeSQLForInterFace.executeQueryA(aa,tFIDataBaseLinkSchema);
               logger.debug(i);
             }

         }
         catch (Exception ex) {
             ex.printStackTrace();
         }


	}
}
