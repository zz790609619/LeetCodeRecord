package com.example.demo.config.plugin;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.util.*;

@Intercepts({@Signature(
        args = {MappedStatement.class, Object.class},
        method = "update",
        type = Executor.class
), @Signature(
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class},
        method = "query",
        type = Executor.class
), @Signature(
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class},
        method = "query",
        type = Executor.class
)})
public class SqlMonitorInterceptor implements Interceptor {
    public SqlMonitorInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        String classname = "";
        String method = "";
        String sql = "";
        String sql_param = "";
        long duration = -1L;
        long beginTime = System.currentTimeMillis();

        try {
            MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
            String[] strArr = mappedStatement.getId().split("\\.");
            classname = strArr[strArr.length - 2];
            method = strArr[strArr.length - 1];
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }

            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            sql = boundSql.getSql();
            sql_param = JSON.toJSONString(parameter);
        } catch (Exception var14) {
            var14.printStackTrace();
        }

        Object returnObj = invocation.proceed();
        long endTime = System.currentTimeMillis();
        duration = endTime - beginTime;
        return returnObj;
    }

    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", this.getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                Iterator var8 = parameterMappings.iterator();

                while(var8.hasNext()) {
                    ParameterMapping parameterMapping = (ParameterMapping)var8.next();
                    String propertyName = parameterMapping.getProperty();
                    Object obj;
                    if (metaObject.hasGetter(propertyName)) {
                        obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", this.getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", this.getParameterValue(obj));
                    }
                }
            }
        }

        return sql;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(2, 2, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else if (obj != null) {
            value = obj.toString();
        } else {
            value = "";
        }

        return value;
    }

    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }

    public void setProperties(Properties properties) {
    }
}
