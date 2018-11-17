package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchMetricNameFromGrafanaExpr {
    public static void main(String... args) {
        String[] exprs = {"sum(log_query_task_cache_hit_rate_bucket)by(le)",
                "sum(log_search_by_service_total {service_name!~\"\"}) by (service_name, operator)",
                "log_request_total",
                " sum(delta(log_request_total[5m])) by (args, user_id)",
                "log_request_total{methodName=~\"getAppDynamicsGraphMetrics|getAppDynamicsMetrics\"}",
                "sum(delta(log_request_total{className=~\".*ProductDashboardController\",methodName=~\"getDashboardConfig|updateMaintainers|addQuickLink|deleteQuickLink|addDependentMiddleware|addDependentService|updateErrorThreshold\"}[5m])) by (user_id)",
                "sum(log_request_total{methodName=\"getInstanceNames\"}) by (user_id)",
                "sum(log_request_total{methodName=\"getVpcCardInfo\",user_id!~\"${user}\"}) by (envName)",
                "count_scalar(sum(log_query_request_total) by (user_id))",
                "avg(log_waiting_time_average) by (exported_tenant, exported_landscape)",
                "avg(task_processing_time_average{app=\"athena\"})",
                "avg(log_queue_time_average) by (log_type)",
                "sum(delta(product_dashboard_service_sum[2m]))",
                "ceil(delta(product_dashboard_service_count[5m]))]"
        };
        String[] expected = {
                "log_query_task_cache_hit_rate_bucket",
                "log_search_by_service_total",
                "log_request_total",
                "log_request_total",
                "log_request_total",
                "log_request_total",
                "log_request_total",
                "log_request_total",
                "log_query_request_total",
                "log_waiting_time_average",
                "task_processing_time_average",
                "log_queue_time_average",
                "product_dashboard_service_sum",
                "product_dashboard_service_count"
        };
        Pattern pattern = Pattern.compile(".*?\\(?([\\w|_]+)\\{?\\[?.*");
        testPattern(exprs, expected, pattern);
        pattern = Pattern.compile(".*\\(?([\\w|_]+)\\{?\\[?.*");
        testPattern(exprs, expected, pattern);
        pattern = Pattern.compile(".*?\\(?([\\w|_]+)\\{?\\[?.*");
        testPattern(exprs, expected, pattern);
    }

    private static void testPattern(String[] exprs, String[] expected, Pattern pattern) {
        System.out.println("\n********** Pattern Match Test *********\n");
        for (int i = 0; i < exprs.length; ++i) {
            String expr = exprs[i];
            Matcher matcher = pattern.matcher(expr);
            if (matcher.find()) {
                System.out.println("\nThe Original Expr: " + expr);
                System.out.println(String.format("Expected:\t %-40s Matched:\t %-40s", expected[i], matcher.group(1)));
            } else {
                System.out.println("expected: " + expected[i] + " not matched");
            }
        }
    }


}
