package com.wxx.demo.mbg.comment;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * mybatis generator 自定义comment生成器.
 * 注意：该类需要单独出一个项目，在插件引入，否则会报ClassNotFoundException
 * @author wangxiaoxian
 *
 */
public class MyCommentGenerator implements CommentGenerator {

    private static final String COMPANY_RIGHT_INFO = "Copyright (c) 2017 Aspire Tech,Inc. All rights reserved.";
    private static final String VERSION = "2.0.0.001";

    private Properties properties;
    private Properties systemPro;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private String currentDateStr;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    /**
     * package上面的注释，一般写公司版权信息等。
     */
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        if (suppressAllComments) {
            return;
        }


        StringBuilder sb = new StringBuilder();

        sb.append("/* ").append("\n").append(" * ").append(COMPANY_RIGHT_INFO).append("\n").append(" */");

        compilationUnit.addFileCommentLine(sb.toString());
    }

    /**
     * 给生成的XML文件加注释。
     */
    public void addComment(XmlElement xmlElement) {
    }

    public void addRootComment(XmlElement rootElement) {
    }

    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        addClassComment(innerClass, introspectedTable, false);
    }

    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**");
        //      addJavadocTag(innerEnum, false);
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());
        innerEnum.addJavaDocLine(" */");
    }

    /**
     * Java属性注释。
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("/** ").append(introspectedColumn.getRemarks()).append(" */");

        field.addJavaDocLine(sb.toString());
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        addFieldComment(field, introspectedTable, new IntrospectedColumn());
    }

    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        topLevelClass.addJavaDocLine("/** ");

        StringBuilder sb = new StringBuilder();

        sb.append(" * ").append(introspectedTable.getRemarks()).append("\n");
        sb.append(" * 表名：").append(introspectedTable.getFullyQualifiedTable()).append("\n");
        sb.append(" * @author ").append(systemPro.getProperty("user.name")).append(" ").append(currentDateStr);
        sb.append(" * @version ").append(VERSION);
        sb.append(" * @since ").append(VERSION);

        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" */");
    }

    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    /**
     * getters方法注释
     */
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }

    /**
     * setters方法注释
     */
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }

    /**
     * 类级别的注释请使用addModelClassComment方法
     */
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {

    }
}