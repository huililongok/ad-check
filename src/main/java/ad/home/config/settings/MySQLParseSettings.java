package ad.home.config.settings;

import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.toolkit.SqlParserUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.reflection.MetaObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义了sql parse
 * 默认方法使用了SqlParserUtils.getOriginalCountSql(selectStatement.toString())方法
 * 会导致派生表数据，性能较差
 * @author Sugar
 *
 */
@Slf4j
public class MySQLParseSettings extends JsqlParserCountOptimize {
    private static final List<SelectItem> COUNT_SELECT_ITEM = countSelectItem();

    @Override
    public SqlInfo parser(MetaObject metaObject, String sql) {
        if (log.isDebugEnabled()) {
            log.debug(" JsqlParserCountOptimize sql=" + sql);
        }
        SqlInfo sqlInfo = SqlInfo.newInstance();
        try {
            Select selectStatement = (Select) CCJSqlParserUtil.parse(sql);
            PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
            Distinct distinct = plainSelect.getDistinct();
            List<Expression> groupBy = plainSelect.getGroupByColumnReferences();
            List<OrderByElement> orderBy = plainSelect.getOrderByElements();

            // 添加包含groupBy 不去除orderBy
            if (CollectionUtils.isEmpty(groupBy) && CollectionUtils.isNotEmpty(orderBy)) {
                plainSelect.setOrderByElements(null);
                sqlInfo.setOrderBy(false);
            }
            //#95 Github, selectItems contains #{} ${}, which will be translated to ?, and it may be in a function: power(#{myInt},2)
            for (SelectItem item : plainSelect.getSelectItems()) {
                if (item.toString().contains(StringPool.QUESTION_MARK)) {
                    return sqlInfo.setSql(SqlParserUtils.getOriginalCountSql(selectStatement.toString()));
                }
            }
            // 包含distinct,自己定义方法解析count  sql
            if(distinct != null) {
                return sqlInfo.setSql(getDistinctCountSql(selectStatement.toString()));
            }
            // 包含groupBy不优化
            if (CollectionUtils.isNotEmpty(groupBy)) {
                return sqlInfo.setSql(SqlParserUtils.getOriginalCountSql(selectStatement.toString()));
            }
            // 优化 SQL
            plainSelect.setSelectItems(COUNT_SELECT_ITEM);
            return sqlInfo.setSql(selectStatement.toString());
        } catch (Throwable e) {
            // 无法优化使用原 SQL
            return sqlInfo.setSql(SqlParserUtils.getOriginalCountSql(sql));
        }
    }

    private String getDistinctCountSql(String originSql) {
        String lowerCase = originSql.toLowerCase();
        int indexOf = lowerCase.indexOf(",");
        int fromIndex = lowerCase.indexOf("from");
        int distinctIndex = lowerCase.indexOf("distinct");
        String countsql = "select count( " + lowerCase.substring(distinctIndex, indexOf) + " ) " + lowerCase.substring(fromIndex);
        if (log.isDebugEnabled()) {
            log.debug("自定义  distinctCountSql=" + countsql);
        }
        return countsql;
    }


    private static List<SelectItem> countSelectItem() {
        Function function = new Function();
        function.setName("COUNT");
        List<Expression> expressions = new ArrayList<>();
        LongValue longValue = new LongValue(1);
        ExpressionList expressionList = new ExpressionList();
        expressions.add(longValue);
        expressionList.setExpressions(expressions);
        function.setParameters(expressionList);
        List<SelectItem> selectItems = new ArrayList<>();
        SelectExpressionItem selectExpressionItem = new SelectExpressionItem(function);
        selectItems.add(selectExpressionItem);
        return selectItems;
    }

    /**
     * 简单测试下效果，应该比派生表的方式快5倍以上
     * @param args
     */
    public static void test(String[] args) {
        String sql = "select distinct (advertId),advert_name from tb_advert";
        String lowerCase = sql.toLowerCase();
        int indexOf = lowerCase.indexOf(",");
        int fromIndex = lowerCase.indexOf("from");
        int distinctIndex = sql.indexOf("distinct");
        String count = "select count(" + sql.substring(distinctIndex, indexOf) + " )" + sql.substring(fromIndex);
        System.out.println(count);
    }

}
