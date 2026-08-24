package com.vfort.repository;

import com.vfort.model.entity.OrdersMobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrdersMobile, String> {

    @Query(value =
            "SELECT * FROM ( " +

                    "   SELECT " +
                    "       tboa.customer_id AS \"customerId\", " +
                    "       tcus.customer_type AS \"customerType\", " +
                    "       tcus.sub_type AS \"subType\", " +
                    "       tboa.ap_id AS \"apId\", " +
                    "       tboa.order_id AS \"orderId\", " +
                    "       tboa.order_unit_id AS \"orderUnitId\", " +
                    "       tboa.ctdb_cre_datetime AS \"creDateTime\", " +
                    "       tboa.ctdb_upd_datetime AS \"updDateTime\", " +
                    "       tboa.status AS \"status\", " +
                    "       tboa.action_type AS \"actionType\", " +
                    "       tboa.sales_channel AS \"salesChannel\", " +
                    "       tboa.reason_id AS \"reasonId\", " +
                    "       tboa.cancel_allowed AS \"cancelAllowed\", " +
                    "       tboa.amend_allowed_ind AS \"amendAllowedInd\", " +
                    "       tbass.form_id AS \"formId\", " +
                    "       tbass.state AS \"state\", " +
                    "       tbass.execution_date AS \"executionDate\", " +
                    "       tbass.completion_date AS \"completionDate\", " +
                    "       tbass.step_instance_id AS \"stepInstanceId\", " +
                    "       tbass.is_exception AS \"isException\", " +
                    "       tbexc.message_text AS \"messageText\" " +
                    "   FROM tborder_action tboa " +
                    "   JOIN tbcustomer tcus " +
                    "       ON tboa.customer_id = tcus.customer_id " +
                    "   JOIN tbassignment tbass " +
                    "       ON tbass.order_action_id = tboa.order_unit_id " +
                    "      AND tbass.state NOT IN ('CO', 'CN') " +
                    "   LEFT JOIN tbassign_exc tbexc " +
                    "       ON tbexc.step_instance_id = tbass.step_instance_id " +
                    "   WHERE tboa.ctdb_cre_datetime > NOW() - INTERVAL '12 HOURS' " +
                    "     AND tboa.status NOT IN ('CA', 'DO') " +
                    "     AND EXISTS ( " +
                    "         SELECT 1 " +
                    "         FROM tbap_item it " +
                    "         WHERE tboa.order_unit_id = it.order_action_id " +
                    "           AND it.product_def_id = '8360374' " +
                    "           AND tboa.ap_id = it.ap_id " +
                    "           AND tboa.ap_version_id = it.ap_version_id " +
                    "           AND it.partition_date IN (DATE '1901-01-01', DATE '1900-01-01') " +
                    "           AND it.main_ind = '1' " +
                    "     ) " +

                    "   UNION ALL " +

                    "   SELECT " +
                    "       tboa.customer_id AS \"customerId\", " +
                    "       tcus.customer_type AS \"customerType\", " +
                    "       tcus.sub_type AS \"subType\", " +
                    "       tboa.ap_id AS \"apId\", " +
                    "       tboa.order_id AS \"orderId\", " +
                    "       tboa.order_unit_id AS \"orderUnitId\", " +
                    "       tboa.ctdb_cre_datetime AS \"creDateTime\", " +
                    "       tboa.ctdb_upd_datetime AS \"updDateTime\", " +
                    "       tboa.status AS \"status\", " +
                    "       tboa.action_type AS \"actionType\", " +
                    "       tboa.sales_channel AS \"salesChannel\", " +
                    "       tboa.reason_id AS \"reasonId\", " +
                    "       tboa.cancel_allowed AS \"cancelAllowed\", " +
                    "       tboa.amend_allowed_ind AS \"amendAllowedInd\", " +
                    "       CAST(NULL AS VARCHAR(100)) AS \"formId\", " +
                    "       CAST(NULL AS VARCHAR(100)) AS \"state\", " +
                    "       CAST(NULL AS TIMESTAMP) AS \"executionDate\", " +
                    "       CAST(NULL AS TIMESTAMP) AS \"completionDate\", " +
                    "       CAST(NULL AS VARCHAR(100)) AS \"stepInstanceId\", " +
                    "       CAST(NULL AS BOOLEAN) AS \"isException\", " +
                    "       CAST(NULL AS TEXT) AS \"messageText\" " +
                    "   FROM tborder_action tboa " +
                    "   JOIN tbcustomer tcus " +
                    "       ON tboa.customer_id = tcus.customer_id " +
                    "   WHERE tboa.ctdb_cre_datetime > NOW() - INTERVAL '3 DAYS' " +
                    "     AND tboa.status IN ('CA', 'DO') " +
                    "     AND EXISTS ( " +
                    "         SELECT 1 " +
                    "         FROM tbap_item it " +
                    "         WHERE tboa.order_unit_id = it.order_action_id " +
                    "           AND it.product_def_id = '8360374' " +
                    "           AND tboa.ap_id = it.ap_id " +
                    "           AND it.end_date > NOW() " +
                    "           AND it.partition_date = DATE '1901-01-01' " +
                    "           AND it.main_ind = '1' " +
                    "     ) " +

                    ") result " +
                    "ORDER BY \"creDateTime\" DESC " +
                    "LIMIT 200",
            nativeQuery = true)
    List<OrdersMobileRepository> findMobileOrders();
}