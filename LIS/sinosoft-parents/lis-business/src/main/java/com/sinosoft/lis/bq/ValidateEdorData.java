package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.util.*;
import java.sql.*;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: P表和C数据交换，使保全数据生效</p>
 * <p>Description: 在保全确认时调用，只需在传入数组中指定要操作的表名即可</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author QiuYang
 * @version 1.0
 */

public class ValidateEdorData
{
private static Logger logger = Logger.getLogger(ValidateEdorData.class);

    /** 错误处理类 */
    public CErrors mErrors = new CErrors();

    private MMap mMap = new MMap();

    /** C表表名列表 */
    private List mLCTables = new ArrayList();

    /** P表表名列表 */
    private List mLPTables = new ArrayList();

    /** OB表是临时表 */
    private List mLOBTables = new ArrayList();

    /** 保全号 */
    private String mEdorNo = null;

    /** 保全类型 */
    private String mEdorType = null;

    /**
     * 构造函数
     * @param tables String[] 要更新数据的表名
     * @param edorNo String
     * @param edorType String
     */
    public ValidateEdorData(String[] tables, String edorNo, String edorType)
    {
        initTables(tables);
        this.mEdorNo = edorNo;
        this.mEdorType = edorType;
    }

    /**
     * 提交数据
     * @return boolean
     */
    public boolean submitData()
    {
        if (!dealData())
        {
            return false;
        }
        return true;
    }

    /**
     * 返回待提交的数据
     * @return MMap
     */
    public MMap getMap()
    {
        return mMap;
    }

    /**
     * 初始化交换数据要用到的表名
     * @param tables String[]
     */
    private void initTables(String[] tables)
    {
        for (int i = 0; i < tables.length; i++)
        {
            String tableName = tables[i];
            String subTableName = tableName.substring(2);
            mLCTables.add(tableName);
            mLPTables.add("LP" + subTableName);
            mLOBTables.add("LOB" + subTableName);
        }
    }

    /**
     * 得到表的主键
     * @param dbmd DatabaseMetaData
     * @param tableName String
     * @return List
     */
    private List getPrimaryKeys(DatabaseMetaData dbmd, String tableName)
    {
        List primaryKeys = new ArrayList();
        try
        {
            ResultSet rs = dbmd.getPrimaryKeys(null, null, tableName.toUpperCase());
            while (rs.next())
            {
                primaryKeys.add(rs.getString("COLUMN_NAME"));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return primaryKeys;
    }

    /**
     * 得到表的字段，把EdorNo和EdorType除去
     * @param dbmd DatabaseMetaData
     * @param tableName String
     * @return List
     */
    private List getColumns(DatabaseMetaData dbmd, String tableName)
    {
        List columns = new ArrayList();
        try
        {
            ResultSet rs = dbmd.getColumns(null, null, tableName.toUpperCase(), null);
            while (rs.next())
            {
                String columnName = rs.getString("COLUMN_NAME");
                if ((columnName != null) &&
                        (!columnName.equalsIgnoreCase("EdorNo")) &&
                        (!columnName.equalsIgnoreCase("EdorType")))
                {
                    columns.add(columnName);
                }
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return columns;
    }

    /**
     * 根据字段名得到SQL语句的查询字段
     * @param columns List
     * @return String
     */
    private String getFields(List columns)
    {
        StringBuffer fields = new StringBuffer();
        for (int i = 0; i < columns.size(); i++)
        {
            fields.append(columns.get(i));
            if (i < columns.size() - 1)
            {
                fields.append(",");
            }
            fields.append(" ");
        }
        return fields.toString();
    }

    /**
     * 根据表的主键得到SQL语句的查询条件
     * @param primaryKeys List
     * @return String
     */
    private String getConditions(List primaryKeys)
    {
        StringBuffer conditions = new StringBuffer();
        for (int j = 0; j < primaryKeys.size(); j++) {
             conditions.append(" and c.").append(primaryKeys.get(j))
                .append(" = ")
                .append("p.").append(primaryKeys.get(j)).append(" ");
         }
        return conditions.toString();
    }

    /**
     * 处理业务逻辑
     * @return boolean
     */
    private boolean dealData()
    {
        try
        {
            Connection conn = DBConnPool.getConnection();
            DatabaseMetaData dbmd = conn.getMetaData();
            for (int i = 0; i < mLCTables.size(); i++)
            {
                String tableName = (String) mLCTables.get(i);
                String fields = getFields(getColumns(dbmd, tableName));
                String conditions = getConditions(getPrimaryKeys(dbmd, tableName));

                setLOBTable(i, conditions);
                setLCTable(i, fields);
                setLPTable(i, fields, conditions, conn);
                deleteLOBTable(i, conditions);
            }
            conn.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return true;
    }

    /**
     * 备份C表的数据到OB表
     */
    private void setLOBTable(int i, String conditions)
    {
        StringBuffer sql;

        sql = new StringBuffer();
        sql.append("delete from ")
                .append(mLOBTables.get(i)).append(" c ")
                .append("where exists (select * from ")
                .append(mLPTables.get(i)).append(" p ")
                .append("where 1 = 1 ")
                .append(conditions)
                .append("and p.EdorNo = '").append("?mEdorNo?").append("' ")
                .append("and p.EdorType = '").append("?mEdorType?").append("')");
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql.toString());
        sqlbv.put("mEdorNo", mEdorNo);
        sqlbv.put("mEdorType", mEdorType);
        mMap.put(sqlbv, "DELETE");

        sql = new StringBuffer();
        sql.append("insert into ").append(mLOBTables.get(i))
                .append(" (select c.* from ")
                .append(mLCTables.get(i)).append(" c, ")
                .append(mLPTables.get(i)).append(" p ")
                .append("where 1 = 1 ")
                .append(conditions)
                .append("and p.EdorNo = '").append("?mEdorNo?").append("' ")
                .append("and p.EdorType = '").append("?mEdorType?").append("')");
        
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();

        sqlbv1.sql(sql.toString());
        sqlbv1.put("mEdorNo", mEdorNo);
        sqlbv1.put("mEdorType", mEdorType);
        mMap.put(sqlbv1, "INSERT");

        sql = new StringBuffer();
        sql.append("delete from ")
                .append(mLCTables.get(i)).append(" c ")
                .append("where exists (select * from ")
                .append(mLPTables.get(i)).append(" p ")
                .append("where 1 = 1 ")
                .append(conditions)
                .append("and p.EdorNo = '").append("?mEdorNo?").append("' ")
                .append("and p.EdorType = '").append("?mEdorType?").append("')");
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(sql.toString());
        sqlbv3.put("mEdorNo", mEdorNo);
        sqlbv3.put("mEdorType", mEdorType);
        mMap.put(sqlbv3, "DELETE");

    }

    /**
     * 把保全P表数据放入C表
     */
    private void setLCTable(int i, String fields)
    {
        StringBuffer sql;
        sql = new StringBuffer();
        sql.append("insert into ").append(mLCTables.get(i))
                .append(" (select ").append(fields)
                .append("from ").append(mLPTables.get(i))
                .append(" where EdorNo = '").append("?mEdorNo?").append("' ")
                .append("and EdorType = '").append("?mEdorType?").append("')");
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql(sql.toString());
        sqlbv4.put("mEdorNo", mEdorNo);
        sqlbv4.put("mEdorType", mEdorType);
        mMap.put(sqlbv4, "INSERT");
    }

    /**
     * 根据主键得到Update语句的约束条件
     * @param param String
     * @param rs ResultSet
     * @param primaryKeys List
     * @throws SQLException
     * @return String
     */
    private String getAddConditions(String param, ResultSet rs, List primaryKeys)
            throws SQLException
    {
        StringBuffer addConditions = new StringBuffer();
        for (int i = 0; i < primaryKeys.size(); i++)
        {
            String value = rs.getString((String) primaryKeys.get(i));
            addConditions.append("and ").append(param).append(primaryKeys.get(i))
                    .append(" = '").append(value.trim()).append("' ");
        }
        return addConditions.toString();
    }

    /**
     * 把OB表的数据放入P表，Update语句只能逐条执行
     */
    private void setLPTable(int i, String fields, String conditions, Connection conn)
    {
        try
        {
            List primaryKeys = getPrimaryKeys(conn.getMetaData(), (String)mLOBTables.get(i));
            StringBuffer selectSql = new StringBuffer();
            selectSql.append("select * from ")
                    .append(mLCTables.get(i)).append(" c, ")
                    .append(mLPTables.get(i)).append(" p ")
                    .append("where 1 = 1 ")
                    .append(conditions)
                    .append("and p.EdorNo = '").append(mEdorNo).append("' ")
                    .append("and p.EdorType = '").append(mEdorType).append("' ");
//            sqlbv5.sql(selectSql.toString());
//            sqlbv5.put("mEdorNo", mEdorNo);
//            sqlbv5.put("mEdorType", mEdorType);
//            .append("and p.EdorNo = '").append("?mEdorNo?").append("' ")
//            .append("and p.EdorType = '").append("?mEdorType?").append("' ");
            Statement stmt = conn.createStatement();
            logger.debug(selectSql.toString());
            ResultSet rs = stmt.executeQuery(selectSql.toString());
            while (rs.next())
            {
                String addConditions1 = getAddConditions("p.", rs, primaryKeys);
                String addConditions2 = getAddConditions("", rs, primaryKeys);
                StringBuffer sql = new StringBuffer();
                sql.append("update ").append(mLPTables.get(i)).append(" set ")
                        .append("(").append(fields).append(") = ")
                        .append("(select c.* from (select distinct c.* from ")
                        .append(mLOBTables.get(i)).append(" c, ")
                        .append(mLPTables.get(i)).append(" p ")
                        .append("where 1 = 1 ")
                        .append(conditions)
                        .append("and p.EdorNo = '").append("?mEdorNo?").append("' ")
                        .append("and p.EdorType = '").append("?mEdorType?").append("' ")
                        .append(addConditions1).append(") t)")
                        .append("where EdorNo = '").append("?mEdorNo?").append("' ")
                        .append("and EdorType = '").append("?mEdorType?").append("' ")
                        .append(addConditions2);
                SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
                sqlbv6.sql(sql.toString());
                sqlbv6.put("mEdorNo", mEdorNo);
                sqlbv6.put("mEdorType", mEdorType);
                mMap.put(sqlbv6, "UPDATE");
            }
            rs.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 删除临时表
     * @param i int
     * @param conditions String
     */
    private void deleteLOBTable(int i, String conditions)
    {
        //这个sql用了两遍，把delete大写避免与前面的冲突
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE from  ")
                .append(mLOBTables.get(i)).append(" c ")
                .append("where exists (select * from ")
                .append(mLPTables.get(i)).append(" p ")
                .append("where 1 = 1 ")
                .append(conditions)
                .append("and p.EdorNo = '").append("?mEdorNo?").append("' ")
                .append("and p.EdorType = '").append("?mEdorType?").append("')");
        SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
        sqlbv7.sql(sql.toString());
        sqlbv7.put("mEdorNo", mEdorNo);
        sqlbv7.put("mEdorType", mEdorType);
        mMap.put(sqlbv7, "DELETE");
    }

    static public void main(String args[])
    {
        String[] lcTables = {"LCPol"};
        String edorNo = "20051011000003";
        ValidateEdorData validate = new ValidateEdorData(lcTables, edorNo, "AE");
        if (!validate.submitData())
        {
            logger.debug("validate error");
        }
        MMap map = new MMap();
       // map.add(validate.getMap());
        map = validate.getMap();

        VData data = new VData();
        data.add(map);
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(data, ""))
        {
        }
    }
}
