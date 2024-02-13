--      _______.  ______  __    __   _______ .___  ___.      ___
--     /       | /      ||  |  |  | |   ____||   \/   |     /   \
--    |   (----`|  ,----'|  |__|  | |  |__   |  \  /  |    /  ^  \
--     \   \    |  |     |   __   | |   __|  |  |\/|  |   /  /_\  \
-- .----)   |   |  `----.|  |  |  | |  |____ |  |  |  |  /  _____  \
-- |_______/     \______||__|  |__| |_______||__|  |__| /__/     \__\
CREATE SCHEMA IF NOT EXISTS account;
CREATE SCHEMA IF NOT EXISTS predefined;
CREATE SCHEMA IF NOT EXISTS blog;
CREATE SCHEMA IF NOT EXISTS conversation;
CREATE SCHEMA IF NOT EXISTS customer;
CREATE SCHEMA IF NOT EXISTS toy;
CREATE SCHEMA IF NOT EXISTS task;

CREATE SEQUENCE IF NOT EXISTS account.seq_cycle MAXVALUE 100000000 CYCLE;
CREATE SEQUENCE IF NOT EXISTS predefined.seq_cycle MAXVALUE 100000000 CYCLE;
CREATE SEQUENCE IF NOT EXISTS blog.seq_cycle MAXVALUE 100000000 CYCLE;
CREATE SEQUENCE IF NOT EXISTS conversation.seq_cycle MAXVALUE 100000000 CYCLE;
CREATE SEQUENCE IF NOT EXISTS customer.seq_cycle MAXVALUE 100000000 CYCLE;
CREATE SEQUENCE IF NOT EXISTS toy.seq_cycle MAXVALUE 100000000 CYCLE;
CREATE SEQUENCE IF NOT EXISTS task.seq_cycle MAXVALUE 100000000 CYCLE;
