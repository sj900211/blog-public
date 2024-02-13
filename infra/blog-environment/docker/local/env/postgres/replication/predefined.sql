-- SERVER SETTING
ALTER SYSTEM SET wal_level = logical;
ALTER SYSTEM SET max_replication_slots = 20;
ALTER SYSTEM SET max_wal_senders = 20;

-- CLIENT SETTING
ALTER SYSTEM SET max_logical_replication_workers = 20;
ALTER SYSTEM SET max_worker_processes = 60;

-- RESTART DATABASE
SHOW wal_level;

CREATE PUBLICATION publication_predefined FOR TABLES IN SCHEMA predefined;
CREATE USER replica_predefined WITH PASSWORD 'replica_predefined' REPLICATION;

GRANT USAGE ON SCHEMA predefined TO replica_predefined;
GRANT SELECT ON ALL TABLES IN SCHEMA predefined TO replica_predefined;
GRANT SELECT, USAGE ON ALL SEQUENCES IN SCHEMA predefined TO replica_predefined;

CREATE SUBSCRIPTION subscription_account_to_predefined
    CONNECTION 'host=postgres-gateway port=40603 user=replica_account password=replica_account dbname=postgres'
    PUBLICATION publication_account;

-- CREATE SUBSCRIPTION subscription_predefined_to_predefined
--     CONNECTION 'host=postgres-gateway port=40604 user=replica_predefined password=replica_predefined dbname=postgres'
--     PUBLICATION publication_predefined;

CREATE SUBSCRIPTION subscription_blog_to_predefined
    CONNECTION 'host=postgres-gateway port=40605 user=replica_blog password=replica_blog dbname=postgres'
    PUBLICATION publication_blog;

CREATE SUBSCRIPTION subscription_conversation_to_predefined
    CONNECTION 'host=postgres-gateway port=40606 user=replica_conversation password=replica_conversation dbname=postgres'
    PUBLICATION publication_conversation;

CREATE SUBSCRIPTION subscription_customer_to_predefined
    CONNECTION 'host=postgres-gateway port=40607 user=replica_customer password=replica_customer dbname=postgres'
    PUBLICATION publication_customer;

CREATE SUBSCRIPTION subscription_toy_to_predefined
    CONNECTION 'host=postgres-gateway port=40608 user=replica_toy password=replica_toy dbname=postgres'
    PUBLICATION publication_toy;

CREATE SUBSCRIPTION subscription_task_to_predefined
    CONNECTION 'host=postgres-gateway port=40609 user=replica_task password=replica_task dbname=postgres'
    PUBLICATION publication_task;

-- DROP SUBSCRIPTION subscription_account_to_predefined;
-- DROP SUBSCRIPTION subscription_predefined_to_predefined;
-- DROP SUBSCRIPTION subscription_blog_to_predefined;
-- DROP SUBSCRIPTION subscription_conversation_to_predefined;
-- DROP SUBSCRIPTION subscription_customer_to_predefined;
-- DROP SUBSCRIPTION subscription_toy_to_predefined;
-- DROP SUBSCRIPTION subscription_task_to_predefined;
