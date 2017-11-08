package com.sinosoft.lis.fininterface.utility;
import org.apache.log4j.Logger;


import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.CBlob;
import com.sinosoft.utility.Schema;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;


public class PubSubmitForInterface
{
private static Logger logger = Logger.getLogger(PubSubmitForInterface.class);

    // 传输数据类
    private VData mInputData;
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 数据库连接 **/
    private Connection conn = null;
    /** 立即提交标志 **/
    private final boolean commitFlag = true;

    public PubSubmitForInterface()
    {}


    public boolean submitData(VData cInputData, FIDataBaseLinkSchema tFIDataBaseLinkSchema)
    {
        try
        {
            // 首先将数据在本类中做一个备份
            mInputData = (VData) cInputData.clone();

            if (!this.saveData(tFIDataBaseLinkSchema)) {
                return false;
            }
            mInputData = null;
            return true;
        }
        catch (Exception ex)
        {
            CError.buildErr(this, "保存数据异常" + ex.getMessage());
            return false;
        }
    }

    private boolean saveData(FIDataBaseLinkSchema tFIDataBaseLinkSchema)
    {

        try
        {
            // 建立数据库连接
            conn = null;
            conn = DBConnPool.getConnection(tFIDataBaseLinkSchema);
            if (conn == null)
            {
                // @@错误处理
                CError.buildErr(this, "数据库连接失败");
                return false;
            }

            // 开始事务，锁表
            conn.setAutoCommit(false);
            String action = ""; // 操作方式，INSERT\UPDATE\DELETE
            String className = ""; // 类名
            Object o = null; // Schema或Set对象
            Object DBObject = null; // DB或DBSet对象
            Method m = null; // 方法
            Constructor constructor = null; // 构造函数
            Class[] parameterC = new Class[1]; // 调用方法的参数数组
            Object[] parameterO = new Object[1]; // 调用方法的对象数组
            // logger.debug("mInputData.size():" + mInputData.size());
            // logger.debug("mInputData :" + mInputData);
            // 通过MMap来传递每个Schema或Set的数据库操作方式，约定使用
            MMap map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
            if (map != null && map.keySet().size() != 0) {
                Set set = map.keySet();

                for (int j = 0; j < set.size(); j++) {
                    // 获取操作对象Schema或Set或SQL
                    // o = iterator.next();
                    o = map.getOrder().get(String.valueOf(j + 1));
                    // 获取操作方式
                    action = (String) map.get(o);
                    if (action == null) {
                        continue;
                    }


                    // 构造相应的DB类名
                    className = o.getClass().getName();

                    if (className.endsWith("String")) {
                        if (action.equals("UPDATE")) {
                            className = "com.sinosoft.lis.db."
                                        +
                                        className.substring(className.
                                    lastIndexOf(".") + 1,
                                        className.lastIndexOf("S"))
                                        + "DB";
                            SQLwithBindVariables sqlbva = new SQLwithBindVariables();
                            if(o instanceof SQLwithBindVariables){
                            	sqlbva = (SQLwithBindVariables)o;
                            }else{
                            	String tSQL = (String) o;
                            	sqlbva.sql(tSQL);
                            }
                            
                            ExeSQL tExeSQL = new ExeSQL(conn);
                            // logger.debug("执行SQL语句:" + tSQL);
                            if (!tExeSQL.execUpdateSQL(sqlbva)) {
                                CError.buildErr(this, "执行更新语句失败");
                                conn.rollback();
                                conn.close();
                                return false;
                            }
                            continue;
                        }
                        if (action.equals("DELETE")) {
                            className = "com.sinosoft.lis.db."
                                        +
                                        className.substring(className.
                                    lastIndexOf(".") + 1,
                                        className.lastIndexOf("S"))
                                        + "DB";
                            SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
                            if(o instanceof SQLwithBindVariables){
                            	sqlbvb = (SQLwithBindVariables)o;
                            }else{
                            	String tSQL = (String) o;
                            	sqlbvb.sql(tSQL);
                            }
                            ExeSQL tExeSQL = new ExeSQL(conn);
                            // logger.debug("执行SQL语句:" + tSQL);
                            if (!tExeSQL.execUpdateSQL(sqlbvb)) {
                                CError.buildErr(this, "执行删除语句失败");
                                conn.rollback();
                                conn.close();
                                return false;
                            }
                            continue;
                        }
                        if (action.equals("INSERT")) {
                            className = "com.sinosoft.lis.db."
                                        +
                                        className.substring(className.
                                    lastIndexOf(".") + 1,
                                        className.lastIndexOf("S"))
                                        + "DB";
                            SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
                            if(o instanceof SQLwithBindVariables){
                            	sqlbvb = (SQLwithBindVariables)o;
                            }else{
                            	String tSQL = (String) o;
                            	sqlbvb.sql(tSQL);
                            }
                            ExeSQL tExeSQL = new ExeSQL(conn);
                            // logger.debug("执行SQL语句:" + tSQL);
                            if (!tExeSQL.execUpdateSQL(sqlbvb)) {
                                CError.buildErr(this,
                                                "执行插入语句失败原因：" +
                                                tExeSQL.mErrors.getError(0).
                                                errorMessage);
                                conn.rollback();
                                conn.close();
                                return false;
                            }
                            continue;
                        }
                        if (action.equals("SELECT")) // 加入select方法，
                        // 如果查出则表示有重复操作并报错退出
                        {
                            className = "com.sinosoft.lis.db."
                                        +
                                        className.substring(className.
                                    lastIndexOf(".") + 1,
                                        className.lastIndexOf("S"))
                                        + "DB";
                            String tSQL = (String) o;
                            ExeSQL tExeSQL = new ExeSQL(conn);
                            SSRS tSSRS = new SSRS();
                            logger.debug("执行SQL语句:" + tSQL);
                            tSSRS = tExeSQL.execSQL(tSQL);

                            if (tSSRS.getMaxRow() > 0) {
                                CError.buildErr(this, "该操作已经执行，请检查数据！");
                                conn.rollback();
                                conn.close();
                                return false;
                            }
                            continue;
                        }
                    } else if (className.endsWith("Schema")) {
                        className = "com.sinosoft.lis.db."
                                    +
                                    className.substring(className.lastIndexOf(".") +
                                1, className.lastIndexOf("S"))
                                    + "DB";
                    } else if (className.endsWith("BLSet")) {
                        className = "com.sinosoft.lis.vdb."
                                    +
                                    className.substring(className.lastIndexOf(".") +
                                1, className.lastIndexOf("B"))
                                    + "DBSet";
                    } else if (className.endsWith("Set")) {
                        className = "com.sinosoft.lis.vdb."
                                    +
                                    className.substring(className.lastIndexOf(".") +
                                1, className.lastIndexOf("S"))
                                    + "DBSet";
                    }
                    Class DBClass = Class.forName(className);

                    // 选择构造函数，构造相同事务的DB或DBSet对象
                    parameterC[0] = Connection.class;
                    constructor = DBClass.getConstructor(parameterC);
                    parameterO[0] = conn;
                    DBObject = constructor.newInstance(parameterO);

                    // 给DB对象付值，将传入的Schema或Set对象的内容复制到DB中
                    parameterC[0] = o.getClass();
                    if (o.getClass().getName().endsWith("Schema")) {
                        m = DBObject.getClass().getMethod("setSchema",
                                parameterC);
                    } else if (o.getClass().getName().endsWith("Set")) {
                        m = DBObject.getClass().getMethod("set", parameterC);
                    }
                    parameterO[0] = o;
                    m.invoke(DBObject, parameterO);

                    // 进行数据库操作
                    if (action.equals("INSERT")) {
                        m = DBObject.getClass().getMethod("insert", null);
                        Boolean b = (Boolean) m.invoke(DBObject, null);

                        if (!b.booleanValue()) {
                            CError.buildErr(this, "执行插入语句失败");
                            try {
                                conn.rollback();
                            } catch (Exception e) {}
                            conn.close();
                            logger.debug(DBObject.getClass().getName() +
                                               " " + action + " Failed");
                            return false;
                        }
                    } else if (action.equals("UPDATE")) {
                        m = DBObject.getClass().getMethod("update", null);
                        Boolean b = (Boolean) m.invoke(DBObject, null);

                        if (!b.booleanValue()) {
                            CError.buildErr(this, "执行更新语句失败");
                            try {
                                conn.rollback();
                            } catch (Exception e) {}
                            conn.close();
                            logger.debug(DBObject.getClass().getName() +
                                               " " + action + " Failed");
                            return false;
                        }
                    } else if (action.equals("DELETE")) {
                        m = DBObject.getClass().getMethod("delete", null);
                        Boolean b = (Boolean) m.invoke(DBObject, null);

                        if (!b.booleanValue()) {
                            CError.buildErr(this, "执行删除语句失败");
                            try {
                                conn.rollback();
                            } catch (Exception e) {}
                            conn.close();
                            logger.debug(DBObject.getClass().getName() +
                                               " " + action + " Failed");
                            return false;
                        }
                    } else if (action.equals("DELETE&INSERT")) {
                        // DELETE
                        m = DBObject.getClass().getMethod("delete", null);
                        Boolean b = (Boolean) m.invoke(DBObject, null);

                        if (!b.booleanValue()) {
                            CError.buildErr(this, "执行删除，插入语句失败");
                            try {
                                conn.rollback();
                            } catch (Exception e) {}
                            conn.close();
                            logger.debug(DBObject.getClass().getName() +
                                               " " + action + " Failed");
                            return false;
                        }

                        // INSERT
                        m = DBObject.getClass().getMethod("insert", null);
                        b = (Boolean) m.invoke(DBObject, null);

                        if (!b.booleanValue()) {
                            CError.buildErr(this, "执行插入语句失败");
                            try {
                                conn.rollback();
                            } catch (Exception e) {}
                            conn.close();
                            logger.debug(DBObject.getClass().getName() +
                                               " " + action + " Failed");
                            return false;
                        }
                    } else if (action.equals("BLOBINSERT"))
                    {
                        String tSQL = "";
                        // 用于第二步update时的参数
                        String pWhereSQL = " and ";
                        String pTabName = "";
                        String pUpdateField = "";

                        String[] parmStrArr = getBlobInsertStr(o, tSQL,
                                pWhereSQL, pTabName, pUpdateField);

                        m = o.getClass().getMethod("get" + parmStrArr[2], null);
                        InputStream ins = (InputStream) m.invoke(o, null);

                        if (!CBlob.BlobInsert(ins, parmStrArr, conn)) {
                            CError.buildErr(this, "执行更新blob语句失败");
                            try {
                                conn.rollback();
                            } catch (Exception e) {}
                            conn.close();
                            ins.close();
                            logger.debug("tCBlob.BlobInsert" + " " +
                                               action + " Failed");
                            return false;
                        }
                        ins.close();
                    } else if (action.equals("BLOBUPDATE"))
                    { // add by Alex at 2005.1.12
                        // first,prepare the param for the UpdateBlob
                        String tSQL = "";
                        // 用于第二步update时的参数
                        String pWhereSQL = " and ";
                        String pTabName = "";
                        String pUpdateField = "";

                        String[] parmStrArr = getBlobInsertStr(o, tSQL,
                                pWhereSQL, pTabName, pUpdateField);


                        m = o.getClass().getMethod("get" + parmStrArr[2], null);
                        InputStream ins = (InputStream) m.invoke(o, null);

                        if (!CBlob.BlobUpdate(ins, parmStrArr, conn)) {
                            CError.buildErr(this, "执行更新blob语句失败");
                            try {
                                conn.rollback();
                            } catch (Exception e) {}
                            conn.close();
                            ins.close();
                            logger.debug("tCBlob.BlobUpdate" + " " +
                                               action + " Failed");
                            return false;
                        }
                        ins.close();
                    } else if (action.equals("BLOBDELETE")) { // add by Alex at 2005.1.12
                        // logger.debug(SysConst.DBTYPE);
                        m = DBObject.getClass().getMethod("delete", null);
                        Boolean b = (Boolean) m.invoke(DBObject, null);

                        if (!b.booleanValue()) {
                            CError.buildErr(this, "执行删除语句失败");
                            try {
                                conn.rollback();
                            } catch (Exception e) {}
                            conn.close();
                            logger.debug(DBObject.getClass().getName() +
                                               " " + action + " Failed");
                            return false;
                        }

                    }

                } // end of while
            }

            // 数据提交:为保正事务一致性所有数据准备完毕后一次性提交.
            if (commitFlag) {
                conn.commit();
                conn.close();
                conn = null;
                // logger.debug("---End Committed---");
            } else {
                logger.debug(
                        "---End Datebase Operation, but not Commit in AutoBLS---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // @@错误处理
            CError.buildErr(this, e.toString());
            try {
                conn.rollback();
            } catch (Exception ex) {}

            try {
                conn.close();
            } catch (Exception ex) {}

            conn = null;
            return false;
        } finally {
        	if(conn!=null){
        		try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return true;
    }




    /**
     * 获取Blob操作所需信息公共方法
     * @param o Object
     * @param tSQL String
     * @param pTabName String
     * @param pUpdateField String
     * @param pWhereSQL String
     * @return String[]
     */
    private static String[] getBlobInsertStr(Object o, String tSQL,
                                             String pTabName,
                                             String pUpdateField,
                                             String pWhereSQL) {
        String aClassName = o.getClass().getName();

        if (aClassName.endsWith("Schema")) {
            Schema s = (Schema) o;
            String[] pk = s.getPK();
            pTabName = aClassName.substring(aClassName.lastIndexOf(".") + 1,
                                            aClassName.lastIndexOf("S"));

            // String ColPart = "( ";
            String ValPart = "values(";
            String mark = "'";

            int nFieldCount = s.getFieldCount();
            int jj = 0;

            for (int i = 0; i < nFieldCount - 1; i++) { // 只循环（nFieldCount-1）次，使blob字段不加入循环
                String strFieldName = s.getFieldName(i);
                String strFieldValue = s.getV(i);
                for (int ii = 0; ii < pk.length; ii++) {
                    if (strFieldName.equals(pk[ii])) {
                        pWhereSQL += strFieldName + " = '" + strFieldValue +
                                "' and ";
                    }
                }
                int nFieldType = s.getFieldType(i);
                boolean bFlag = false;

                switch (nFieldType) {
                case Schema.TYPE_STRING:
                case Schema.TYPE_DATE:
                    if (!strFieldValue.equals("null")) {
                        strFieldValue = mark + strFieldValue + mark;
                        bFlag = true;
                    }
                    break;
                case Schema.TYPE_DOUBLE:
                case Schema.TYPE_FLOAT:
                case Schema.TYPE_INT:
                    bFlag = true;
                    break;
                default:
                    bFlag = false;
                    break;
                }

                if (bFlag) {
                    jj++;
                    if (jj != 1) {
                        // ColPart += ",";
                        ValPart += ",";
                    }
                    // ColPart += strFieldName;
                    ValPart += strFieldValue;
                }
            } // end of for
            // ColPart += " )";
            ValPart += ",";
            ValPart += "empty_blob()"; // 添加empty_blob
            ValPart += ")";
            tSQL = "insert into " + pTabName + " " + ValPart;
            if (jj == 0) {
                tSQL = "";
            }
            pUpdateField = s.getFieldName(nFieldCount - 1);
            if (pWhereSQL.lastIndexOf("and") != -1) {
                pWhereSQL = "and " +
                            pWhereSQL.substring(0, pWhereSQL.lastIndexOf("and"));

            }
        } else {
            return null;
        }
        String[] returnStr = new String[4];
        returnStr[0] = tSQL;
        returnStr[1] = pTabName;
        returnStr[2] = pUpdateField;
        returnStr[3] = pWhereSQL;
        return returnStr;
    }

    public static void main(String[] args) {

    }
}
