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

-- .___________.    ___      .______    __       _______
-- |           |   /   \     |   _  \  |  |     |   ____|
-- `---|  |----`  /  ^  \    |  |_)  | |  |     |  |__
--     |  |      /  /_\  \   |   _  <  |  |     |   __|
--     |  |     /  _____  \  |  |_)  | |  `----.|  |____
--     |__|    /__/     \__\ |______/  |_______||_______|
alter table if exists account.account_follow drop constraint if exists FK_ACCOUNT_FOLLOW_FOLLOWER;
alter table if exists account.account_follow drop constraint if exists FK_ACCOUNT_FOLLOW_FOLLOWING;
alter table if exists account.account_hashtag drop constraint if exists FK_ACCOUNT_HASHTAG_ACCOUNT;
alter table if exists account.account_hashtag drop constraint if exists FK_ACCOUNT_HASHTAG_HASHTAG;
alter table if exists account.account_info drop constraint if exists FK_ACCOUNT_INFO_PROFILE;
alter table if exists account.account_notification drop constraint if exists FKokqslem7bhjdxd524qkedasei;
alter table if exists account.account_notification drop constraint if exists FK_ACCOUNT_NOTIFICATION_ACCOUNT;
alter table if exists account.account_notification_account drop constraint if exists FK_ACCOUNT_NOTIFICATION_ACCOUNT_ACCOUNT;
alter table if exists account.account_notification_account drop constraint if exists FPK_ACCOUNT_NOTIFICATION_ACCOUNT;
alter table if exists account.account_notification_blog drop constraint if exists FK_ACCOUNT_NOTIFICATION_BLOG_BLOG;
alter table if exists account.account_notification_blog drop constraint if exists FPK_ACCOUNT_NOTIFICATION_BLOG;
alter table if exists account.account_notification_post drop constraint if exists FK_ACCOUNT_NOTIFICATION_POST_POST;
alter table if exists account.account_notification_post drop constraint if exists FPK_ACCOUNT_NOTIFICATION_POST;
alter table if exists account.account_notification_post_comment drop constraint if exists FK_ACCOUNT_NOTIFICATION_POST_COMMENT_POST_COMMENT;
alter table if exists account.account_notification_post_comment drop constraint if exists FPK_ACCOUNT_NOTIFICATION_POST_COMMENT;
drop table if exists account.account_follow cascade;
drop table if exists account.account_hashtag cascade;
drop table if exists account.account_info cascade;
drop table if exists account.account_notification cascade;
drop table if exists account.account_notification_account cascade;
drop table if exists account.account_notification_blog cascade;
drop table if exists account.account_notification_post cascade;
drop table if exists account.account_notification_post_comment cascade;
alter table if exists blog.blog_info drop constraint if exists FK_BLOG_INFO_CREATOR;
alter table if exists blog.blog_info drop constraint if exists FK_BLOG_INFO_UPDATER;
alter table if exists blog.blog_info drop constraint if exists FK_BLOG_INFO_CHANNEL;
alter table if exists blog.blog_info drop constraint if exists FK_BLOG_INFO_COVER;
alter table if exists blog.blog_info_hashtag drop constraint if exists FK_BLOG_INFO_HASHTAG_BLOG;
alter table if exists blog.blog_info_hashtag drop constraint if exists FK_BLOG_INFO_HASHTAG_HASHTAG;
alter table if exists blog.blog_participate drop constraint if exists FK_BLOG_PARTICIPATE_ACCOUNT;
alter table if exists blog.blog_participate drop constraint if exists FK_BLOG_PARTICIPATE_BLOG;
alter table if exists blog.blog_participate_request drop constraint if exists FK_BLOG_PARTICIPATE_REQUEST_ACCOUNT;
alter table if exists blog.blog_participate_request drop constraint if exists FK_BLOG_PARTICIPATE_REQUEST_BLOG;
alter table if exists blog.blog_post drop constraint if exists FK_BLOG_POST_CREATOR;
alter table if exists blog.blog_post drop constraint if exists FK_BLOG_POST_UPDATER;
alter table if exists blog.blog_post drop constraint if exists FK_BLOG_POST_BLOG;
alter table if exists blog.blog_post_attach drop constraint if exists FK_BLOG_POST_ATTACH_ATTACH;
alter table if exists blog.blog_post_attach drop constraint if exists FK_BLOG_POST_ATTACH_POST;
alter table if exists blog.blog_post_comment drop constraint if exists FK_BLOG_POST_COMMENT_CREATOR;
alter table if exists blog.blog_post_comment drop constraint if exists FK_BLOG_POST_COMMENT_UPDATER;
alter table if exists blog.blog_post_comment drop constraint if exists FK_BLOG_POST_COMMENT_BLOG;
alter table if exists blog.blog_post_comment_reaction drop constraint if exists FK_BLOG_POST_COMMENT_REACTION_ACCOUNT;
alter table if exists blog.blog_post_comment_reaction drop constraint if exists FK_BLOG_POST_COMMENT_REACTION_POST_COMMENT;
alter table if exists blog.blog_post_comment_ward drop constraint if exists FK_BLOG_POST_COMMENT_WARD_ACCOUNT;
alter table if exists blog.blog_post_comment_ward drop constraint if exists FK_BLOG_POST_COMMENT_WARD_POST;
alter table if exists blog.blog_post_hashtag drop constraint if exists FK_BLOG_POST_HASHTAG_HASHTAG;
alter table if exists blog.blog_post_hashtag drop constraint if exists FK_BLOG_POST_HASHTAG_POST;
alter table if exists blog.blog_post_reaction drop constraint if exists FK_BLOG_POST_REACTION_ACCOUNT;
alter table if exists blog.blog_post_reaction drop constraint if exists FK_BLOG_POST_REACTION_POST;
alter table if exists blog.blog_subscribe drop constraint if exists FK_BLOG_SUBSCRIBE_ACCOUNT;
alter table if exists blog.blog_subscribe drop constraint if exists FK_BLOG_SUBSCRIBE_BLOG;
drop table if exists blog.blog_info cascade;
drop table if exists blog.blog_info_hashtag cascade;
drop table if exists blog.blog_participate cascade;
drop table if exists blog.blog_participate_request cascade;
drop table if exists blog.blog_post cascade;
drop table if exists blog.blog_post_attach cascade;
drop table if exists blog.blog_post_comment cascade;
drop table if exists blog.blog_post_comment_reaction cascade;
drop table if exists blog.blog_post_comment_ward cascade;
drop table if exists blog.blog_post_hashtag cascade;
drop table if exists blog.blog_post_reaction cascade;
drop table if exists blog.blog_subscribe cascade;
alter table if exists conversation.conversation_channel_account drop constraint if exists FK_CONVERSATION_CHANNEL_ACCOUNT_ACCOUNT;
alter table if exists conversation.conversation_channel_account drop constraint if exists FK_CONVERSATION_CHANNEL_ACCOUNT_CHANNEL;
drop table if exists conversation.conversation_channel cascade;
drop table if exists conversation.conversation_channel_account cascade;
alter table if exists predefined.predefined_attach drop constraint if exists FK_PREDEFINED_ATTACH_CREATOR;
alter table if exists predefined.predefined_attach drop constraint if exists FK_PREDEFINED_ATTACH_UPDATER;
alter table if exists predefined.predefined_basic_image drop constraint if exists FK_PREDEFINED_BASIC_IMAGE_CREATOR;
alter table if exists predefined.predefined_basic_image drop constraint if exists FK_PREDEFINED_BASIC_IMAGE_UPDATER;
alter table if exists predefined.predefined_basic_image drop constraint if exists FK_PREDEFINED_BASIC_IMAGE_ATTACH;
drop table if exists predefined.predefined_attach cascade;
drop table if exists predefined.predefined_basic_image cascade;
drop table if exists predefined.predefined_hashtag cascade;
create table account.account_follow ( create_at timestamp(6), follower_id bigint not null, following_id bigint not null, primary key (follower_id, following_id) );
comment on table account.account_follow is '사용자 관리 > 팔로우 관리';
comment on column account.account_follow.create_at is '등록 날짜 시간';
create table account.account_hashtag ( account_id bigint not null, create_at timestamp(6), hashtag_id varchar(255) not null, primary key (account_id, hashtag_id) );
comment on table account.account_hashtag is '사용자 관리 > 해시태그 관리';
comment on column account.account_hashtag.create_at is '등록 날짜 시간';
create table account.account_info ( birth date, delete_flag boolean default false, use_flag boolean default true, create_at timestamp(6), id bigserial not null, profile_id bigint, remove_at timestamp(6), sign_at timestamp(6), update_at timestamp(6), gender varchar(255) check (gender in ('MALE','FEMALE','OTHERS')), introduce varchar(255), nickname varchar(255), password varchar(255), previous_password varchar(255), privilege varchar(255) not null check (privilege in ('MANAGER_MAJOR','MANAGER_MINOR','USER','ANONYMOUS')), status varchar(255) not null check (status in ('INACTIVE','ACTIVE','DORMANT','WITHDRAWAL')), username varchar(255) not null, uuid varchar(255) not null, primary key (id), constraint UK_ACCOUNT_INFO_UUID unique (uuid), constraint UK_ACCOUNT_INFO_USERNAME unique (username), constraint UK_ACCOUNT_INFO_NICKNAME unique (nickname) );
comment on table account.account_info is '사용자 관리 > 계정 관리';
comment on column account.account_info.birth is '생일';
comment on column account.account_info.delete_flag is '삭제 여부';
comment on column account.account_info.use_flag is '사용 여부';
comment on column account.account_info.create_at is '등록 날짜 시간';
comment on column account.account_info.id is '일련 번호';
comment on column account.account_info.profile_id is '프로필 이미지 정보';
comment on column account.account_info.remove_at is '탈퇴 날짜 시간';
comment on column account.account_info.sign_at is '최근 접속 날짜 시간';
comment on column account.account_info.update_at is '마지막 수정 날짜 시간';
comment on column account.account_info.gender is '성별';
comment on column account.account_info.introduce is '소개';
comment on column account.account_info.nickname is '닉네임';
comment on column account.account_info.password is '비밀번호';
comment on column account.account_info.previous_password is '이전 비밀번호';
comment on column account.account_info.privilege is '권한';
comment on column account.account_info.status is '상태';
comment on column account.account_info.username is '아이디 - 이메일';
comment on column account.account_info.uuid is '아이디 - 고유 아이디';
create table account.account_notification ( read boolean default false, account_id bigint, create_at timestamp(6), creator_id bigint, division varchar(31) not null, id varchar(255) not null, type varchar(255) not null check (type in ('ACCOUNT_FOLLOW','BLOG_SUBSCRIBE','POST_NEW','POST_REACTION','POST_COMMENT_NEW','POST_COMMENT_REACTION')), primary key (id) );
comment on table account.account_notification is '사용자 관리 > 알림 관리';
comment on column account.account_notification.read is '읽음 여부';
comment on column account.account_notification.account_id is '계정 정보';
comment on column account.account_notification.create_at is '등록 날짜 시간';
comment on column account.account_notification.creator_id is '등록자 일련 번호';
comment on column account.account_notification.id is '일련 번호';
comment on column account.account_notification.type is '유형';
create table account.account_notification_account ( origin_id bigint, id varchar(255) not null, primary key (id) );
comment on table account.account_notification_account is '사용자 관리 > 알림 관리 > 사용자 알림 관리';
comment on column account.account_notification_account.origin_id is '대상 계정 정보';
create table account.account_notification_blog ( origin_id bigint, id varchar(255) not null, primary key (id) );
comment on table account.account_notification_blog is '사용자 관리 > 알림 관리 > 블로그 알림 관리';
comment on column account.account_notification_blog.origin_id is '대상 블로그 정보';
create table account.account_notification_post ( origin_id bigint, id varchar(255) not null, primary key (id) );
comment on table account.account_notification_post is '사용자 관리 > 알림 관리 > 포스팅 알림 관리';
comment on column account.account_notification_post.origin_id is '대상 블로그 정보';
create table account.account_notification_post_comment ( origin_id bigint, id varchar(255) not null, primary key (id) );
comment on table account.account_notification_post_comment is '사용자 관리 > 알림 관리 > 블로그 알림 관리';
comment on column account.account_notification_post_comment.origin_id is '대상 블로그 정보';
create index IDX_ACCOUNT_FOLLOW_DEFAULT_AT on account.account_follow (create_at);
create index IDX_ACCOUNT_HASHTAG_DEFAULT_AT on account.account_hashtag (create_at);
create index IDX_ACCOUNT_INFO_PRIVILEGE on account.account_info (privilege);
create index IDX_ACCOUNT_INFO_DEFAULT_FLAG on account.account_info (use_flag, delete_flag);
create index IDX_ACCOUNT_INFO_DEFAULT_AT on account.account_info (create_at);
create index IDX_ACCOUNT_NOTIFICATION_READ on account.account_notification (read);
create index IDX_ACCOUNT_NOTIFICATION_DEFAULT_AT on account.account_notification (create_at);
create table blog.blog_info ( cover_flag boolean not null, delete_flag boolean default false, use_flag boolean default true, channel_id bigint, cover_id bigint, create_at timestamp(6), creator_id bigint, id bigserial not null, update_at timestamp(6), updater_id bigint, key varchar(20) not null, title varchar(100) not null, description varchar(255) not null, uuid varchar(255) not null, visibility varchar(255) not null check (visibility in ('PUBLIC','PRIVATE')), primary key (id), constraint UK_BLOG_INFO_UUID unique (uuid), constraint UK_BLOG_INFO_KEY unique (key) );
comment on table blog.blog_info is '블로그 관리 > 블로그 정보 관리';
comment on column blog.blog_info.cover_flag is '커버 사용 여부';
comment on column blog.blog_info.delete_flag is '삭제 여부';
comment on column blog.blog_info.use_flag is '사용 여부';
comment on column blog.blog_info.channel_id is '채널';
comment on column blog.blog_info.cover_id is '커버 이미지';
comment on column blog.blog_info.create_at is '등록 날짜 시간';
comment on column blog.blog_info.creator_id is '등록자 일련 번호';
comment on column blog.blog_info.id is '일련 번호';
comment on column blog.blog_info.update_at is '마지막 수정 날짜 시간';
comment on column blog.blog_info.updater_id is '수정자 일련 번호';
comment on column blog.blog_info.key is '키 - 고유';
comment on column blog.blog_info.title is '제목';
comment on column blog.blog_info.description is '설명';
comment on column blog.blog_info.uuid is '아이디 - 고유 아이디';
comment on column blog.blog_info.visibility is '공개 유형';
create table blog.blog_info_hashtag ( blog_id bigint not null, create_at timestamp(6), hashtag_id varchar(255) not null, primary key (blog_id, hashtag_id) );
comment on table blog.blog_info_hashtag is '블로그 관리 > 블로그 해시태그 관리';
comment on column blog.blog_info_hashtag.create_at is '등록 날짜 시간';
create table blog.blog_participate ( account_id bigint not null, blog_id bigint not null, create_at timestamp(6), role varchar(255) not null check (role in ('OWNER','MAINTAINER','REPORTER','VIEWER')), primary key (account_id, blog_id) );
comment on table blog.blog_participate is '블로그 관리 > 참여자 관리';
comment on column blog.blog_participate.create_at is '등록 날짜 시간';
comment on column blog.blog_participate.role is '권한';
create table blog.blog_participate_request ( account_id bigint not null, blog_id bigint not null, create_at timestamp(6), primary key (account_id, blog_id) );
comment on table blog.blog_participate_request is '블로그 관리 > 참여 요청 관리';
comment on column blog.blog_participate_request.create_at is '등록 날짜 시간';
create table blog.blog_post ( delete_flag boolean default false, use_flag boolean default true, views integer default 0, blog_id bigint not null, create_at timestamp(6), creator_id bigint, id bigserial not null, update_at timestamp(6), updater_id bigint, title varchar(100) not null, visibility varchar(255) not null check (visibility in ('PUBLIC','PRIVATE')), contents oid not null, primary key (id) );
comment on table blog.blog_post is '블로그 관리 > 포스팅 관리';
comment on column blog.blog_post.delete_flag is '삭제 여부';
comment on column blog.blog_post.use_flag is '사용 여부';
comment on column blog.blog_post.views is '조회수';
comment on column blog.blog_post.blog_id is '블로그 일련 번호';
comment on column blog.blog_post.create_at is '등록 날짜 시간';
comment on column blog.blog_post.creator_id is '등록자 일련 번호';
comment on column blog.blog_post.id is '일련 번호';
comment on column blog.blog_post.update_at is '마지막 수정 날짜 시간';
comment on column blog.blog_post.updater_id is '수정자 일련 번호';
comment on column blog.blog_post.title is '제목';
comment on column blog.blog_post.visibility is '공개 유형';
comment on column blog.blog_post.contents is '내용';
create table blog.blog_post_attach ( attach_id bigint not null, create_at timestamp(6), post_id bigint not null, primary key (attach_id, post_id) );
comment on table blog.blog_post_attach is '블로그 관리 > 포스팅 첨부파일 관리';
comment on column blog.blog_post_attach.create_at is '등록 날짜 시간';
create table blog.blog_post_comment ( delete_flag boolean default false, use_flag boolean default true, create_at timestamp(6), creator_id bigint, id bigserial not null, post_id bigint not null, update_at timestamp(6), updater_id bigint, contents varchar(255) not null, primary key (id) );
comment on table blog.blog_post_comment is '블로그 관리 > 포스팅 댓글 관리';
comment on column blog.blog_post_comment.delete_flag is '삭제 여부';
comment on column blog.blog_post_comment.use_flag is '사용 여부';
comment on column blog.blog_post_comment.create_at is '등록 날짜 시간';
comment on column blog.blog_post_comment.creator_id is '등록자 일련 번호';
comment on column blog.blog_post_comment.id is '일련 번호';
comment on column blog.blog_post_comment.post_id is '포스팅 일련 번호';
comment on column blog.blog_post_comment.update_at is '마지막 수정 날짜 시간';
comment on column blog.blog_post_comment.updater_id is '수정자 일련 번호';
comment on column blog.blog_post_comment.contents is '내용';
create table blog.blog_post_comment_reaction ( account_id bigint not null, create_at timestamp(6), post_comment_id bigint not null, reaction varchar(255) not null check (reaction in ('LIKE','HATE')), primary key (account_id, post_comment_id) );
comment on table blog.blog_post_comment_reaction is '블로그 관리 > 포스팅 댓글 반응 관리';
comment on column blog.blog_post_comment_reaction.create_at is '등록 날짜 시간';
comment on column blog.blog_post_comment_reaction.reaction is '반응';
create table blog.blog_post_comment_ward ( account_id bigint not null, create_at timestamp(6), post_id bigint not null, primary key (account_id, post_id) );
comment on table blog.blog_post_comment_ward is '블로그 관리 > 포스팅 댓글 알림 수신 관리';
comment on column blog.blog_post_comment_ward.create_at is '등록 날짜 시간';
create table blog.blog_post_hashtag ( create_at timestamp(6), post_id bigint not null, hashtag_id varchar(255) not null, primary key (post_id, hashtag_id) );
comment on table blog.blog_post_hashtag is '블로그 관리 > 포스팅 해시태그 관리';
comment on column blog.blog_post_hashtag.create_at is '등록 날짜 시간';
create table blog.blog_post_reaction ( account_id bigint not null, create_at timestamp(6), post_id bigint not null, reaction varchar(255) not null check (reaction in ('LIKE','HATE')), primary key (account_id, post_id) );
comment on table blog.blog_post_reaction is '블로그 관리 > 포스팅 리액션 관리';
comment on column blog.blog_post_reaction.create_at is '등록 날짜 시간';
comment on column blog.blog_post_reaction.reaction is '반응';
create table blog.blog_subscribe ( account_id bigint not null, blog_id bigint not null, create_at timestamp(6), primary key (account_id, blog_id) );
comment on table blog.blog_subscribe is '블로그 관리 > 구독 관리';
comment on column blog.blog_subscribe.create_at is '등록 날짜 시간';
create index IDX_BLOG_INFO_VISIBILITY on blog.blog_info (visibility);
create index IDX_BLOG_INFO_DEFAULT_FLAG on blog.blog_info (use_flag, delete_flag);
create index IDX_BLOG_INFO_DEFAULT_AT on blog.blog_info (create_at);
create index IDX_BLOG_INFO_HASHTAG_DEFAULT_AT on blog.blog_info_hashtag (create_at);
create index IDX_BLOG_PARTICIPATE_DEFAULT_AT on blog.blog_participate (create_at);
create index IDX_BLOG_PARTICIPATE_REQUEST_DEFAULT_AT on blog.blog_participate_request (create_at);
create index IDX_BLOG_POST_VISIBILITY on blog.blog_post (visibility);
create index IDX_BLOG_POST_DEFAULT_FLAG on blog.blog_post (use_flag, delete_flag);
create index IDX_BLOG_POST_DEFAULT_AT on blog.blog_post (create_at);
create index IDX_BLOG_POST_ATTACH_DEFAULT_AT on blog.blog_post_attach (create_at);
create index IDX_BLOG_POST_COMMENT_DEFAULT_FLAG on blog.blog_post_comment (use_flag, delete_flag);
create index IDX_BLOG_POST_COMMENT_DEFAULT_AT on blog.blog_post_comment (create_at);
create index IDX_BLOG_POST_COMMENT_REACTION_DEFAULT_AT on blog.blog_post_comment_reaction (create_at);
create index IDX_BLOG_POST_COMMENT_WARD_DEFAULT_AT on blog.blog_post_comment_ward (create_at);
create index IDX_BLOG_POST_HASHTAG_DEFAULT_AT on blog.blog_post_hashtag (create_at);
create index IDX_BLOG_POST_REACTION_DEFAULT_AT on blog.blog_post_reaction (create_at);
create index IDX_BLOG_SUBSCRIBE_DEFAULT_AT on blog.blog_subscribe (create_at);
create table conversation.conversation_channel ( delete_flag boolean default false, use_flag boolean default true, create_at timestamp(6), id bigserial not null, update_at timestamp(6), type varchar(255) not null check (type in ('DIRECT','GROUP','TEAM','CHANNEL')), uuid varchar(255) not null, primary key (id), constraint UK_CONVERSATION_CHANNEL_UUID unique (uuid) );
comment on table conversation.conversation_channel is '대화 관리 > 채널 관리';
comment on column conversation.conversation_channel.delete_flag is '삭제 여부';
comment on column conversation.conversation_channel.use_flag is '사용 여부';
comment on column conversation.conversation_channel.create_at is '등록 날짜 시간';
comment on column conversation.conversation_channel.id is '일련 번호';
comment on column conversation.conversation_channel.update_at is '마지막 수정 날짜 시간';
comment on column conversation.conversation_channel.type is '유형';
comment on column conversation.conversation_channel.uuid is '아이디 - 고유 아이디';
create table conversation.conversation_channel_account ( account_id bigint not null, channel_id bigint not null, create_at timestamp(6), primary key (account_id, channel_id) );
comment on table conversation.conversation_channel_account is '대화 관리 > 채널 참여자 관리';
comment on column conversation.conversation_channel_account.create_at is '등록 날짜 시간';
create index IDX_CONVERSATION_CHANNEL_DEFAULT_FLAG on conversation.conversation_channel (use_flag, delete_flag);
create index IDX_CONVERSATION_CHANNEL_DEFAULT_AT on conversation.conversation_channel (create_at);
create index IDX_CONVERSATION_CHANNEL_ACCOUNT_DEFAULT_AT on conversation.conversation_channel_account (create_at);
create table predefined.predefined_attach ( delete_flag boolean default false, is_public boolean default false, use_flag boolean default true, create_at timestamp(6), creator_id bigint, id bigserial not null, size bigint not null, update_at timestamp(6), updater_id bigint, alt varchar(255), content_type varchar(255), filename varchar(255) not null, key varchar(255) not null, title varchar(255), primary key (id) );
comment on table predefined.predefined_attach is '사전 정의 관리 > 파일 관리';
comment on column predefined.predefined_attach.delete_flag is '삭제 여부';
comment on column predefined.predefined_attach.is_public is '공개 여부';
comment on column predefined.predefined_attach.use_flag is '사용 여부';
comment on column predefined.predefined_attach.create_at is '등록 날짜 시간';
comment on column predefined.predefined_attach.creator_id is '등록자 일련 번호';
comment on column predefined.predefined_attach.id is '일련 번호';
comment on column predefined.predefined_attach.size is '파일 크기';
comment on column predefined.predefined_attach.update_at is '마지막 수정 날짜 시간';
comment on column predefined.predefined_attach.updater_id is '수정자 일련 번호';
comment on column predefined.predefined_attach.alt is '대체 문구';
comment on column predefined.predefined_attach.content_type is '파일 유형';
comment on column predefined.predefined_attach.filename is '파일 이름';
comment on column predefined.predefined_attach.key is '파일 키';
comment on column predefined.predefined_attach.title is '제목';
create table predefined.predefined_basic_image ( delete_flag boolean default false, sort integer default 0, use_flag boolean default true, create_at timestamp(6), creator_id bigint, id bigserial not null, image_id bigint, update_at timestamp(6), updater_id bigint, type varchar(255) not null check (type in ('PROFILE','BLOG')), primary key (id) );
comment on table predefined.predefined_basic_image is '사전 정의 관리 > 기본 이미지 관리';
comment on column predefined.predefined_basic_image.delete_flag is '삭제 여부';
comment on column predefined.predefined_basic_image.sort is '정렬 순서';
comment on column predefined.predefined_basic_image.use_flag is '사용 여부';
comment on column predefined.predefined_basic_image.create_at is '등록 날짜 시간';
comment on column predefined.predefined_basic_image.creator_id is '등록자 일련 번호';
comment on column predefined.predefined_basic_image.id is '일련 번호';
comment on column predefined.predefined_basic_image.image_id is '이미지';
comment on column predefined.predefined_basic_image.update_at is '마지막 수정 날짜 시간';
comment on column predefined.predefined_basic_image.updater_id is '수정자 일련 번호';
comment on column predefined.predefined_basic_image.type is '유형';
create table predefined.predefined_hashtag ( create_at timestamp(6), id varchar(255) not null, primary key (id) );
comment on table predefined.predefined_hashtag is '사전 정의 관리 > 해시태그 관리';
comment on column predefined.predefined_hashtag.create_at is '등록 날짜 시간';
comment on column predefined.predefined_hashtag.id is '일련 번호';
create index IDX_PREDEFINED_ATTACH_DEFAULT_FLAG on predefined.predefined_attach (use_flag, delete_flag);
create index IDX_PREDEFINED_ATTACH_DEFAULT_AT on predefined.predefined_attach (create_at);
create index IDX_PREDEFINED_BASIC_IMAGE_DEFAULT_FLAG on predefined.predefined_basic_image (use_flag, delete_flag);
create index IDX_PREDEFINED_BASIC_IMAGE_DEFAULT_AT on predefined.predefined_basic_image (create_at);
create index IDX_PREDEFINED_HASHTAG_DEFAULT_AT on predefined.predefined_hashtag (create_at);
alter table if exists account.account_follow add constraint FK_ACCOUNT_FOLLOW_FOLLOWER foreign key (follower_id) references account.account_info;
alter table if exists account.account_follow add constraint FK_ACCOUNT_FOLLOW_FOLLOWING foreign key (following_id) references account.account_info;
alter table if exists account.account_hashtag add constraint FK_ACCOUNT_HASHTAG_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists account.account_hashtag add constraint FK_ACCOUNT_HASHTAG_HASHTAG foreign key (hashtag_id) references predefined.predefined_hashtag;
alter table if exists account.account_info add constraint FK_ACCOUNT_INFO_PROFILE foreign key (profile_id) references predefined.predefined_attach;
alter table if exists account.account_notification add constraint FKokqslem7bhjdxd524qkedasei foreign key (creator_id) references account.account_info;
alter table if exists account.account_notification add constraint FK_ACCOUNT_NOTIFICATION_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists account.account_notification_account add constraint FK_ACCOUNT_NOTIFICATION_ACCOUNT_ACCOUNT foreign key (origin_id) references account.account_info;
alter table if exists account.account_notification_account add constraint FPK_ACCOUNT_NOTIFICATION_ACCOUNT foreign key (id) references account.account_notification;
alter table if exists account.account_notification_blog add constraint FK_ACCOUNT_NOTIFICATION_BLOG_BLOG foreign key (origin_id) references blog.blog_info;
alter table if exists account.account_notification_blog add constraint FPK_ACCOUNT_NOTIFICATION_BLOG foreign key (id) references account.account_notification;
alter table if exists account.account_notification_post add constraint FK_ACCOUNT_NOTIFICATION_POST_POST foreign key (origin_id) references blog.blog_post;
alter table if exists account.account_notification_post add constraint FPK_ACCOUNT_NOTIFICATION_POST foreign key (id) references account.account_notification;
alter table if exists account.account_notification_post_comment add constraint FK_ACCOUNT_NOTIFICATION_POST_COMMENT_POST_COMMENT foreign key (origin_id) references blog.blog_post_comment;
alter table if exists account.account_notification_post_comment add constraint FPK_ACCOUNT_NOTIFICATION_POST_COMMENT foreign key (id) references account.account_notification;
alter table if exists blog.blog_info add constraint FK_BLOG_INFO_CREATOR foreign key (creator_id) references account.account_info;
alter table if exists blog.blog_info add constraint FK_BLOG_INFO_UPDATER foreign key (updater_id) references account.account_info;
alter table if exists blog.blog_info add constraint FK_BLOG_INFO_CHANNEL foreign key (channel_id) references conversation.conversation_channel;
alter table if exists blog.blog_info add constraint FK_BLOG_INFO_COVER foreign key (cover_id) references predefined.predefined_attach;
alter table if exists blog.blog_info_hashtag add constraint FK_BLOG_INFO_HASHTAG_BLOG foreign key (blog_id) references blog.blog_info;
alter table if exists blog.blog_info_hashtag add constraint FK_BLOG_INFO_HASHTAG_HASHTAG foreign key (hashtag_id) references predefined.predefined_hashtag;
alter table if exists blog.blog_participate add constraint FK_BLOG_PARTICIPATE_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists blog.blog_participate add constraint FK_BLOG_PARTICIPATE_BLOG foreign key (blog_id) references blog.blog_info;
alter table if exists blog.blog_participate_request add constraint FK_BLOG_PARTICIPATE_REQUEST_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists blog.blog_participate_request add constraint FK_BLOG_PARTICIPATE_REQUEST_BLOG foreign key (blog_id) references blog.blog_info;
alter table if exists blog.blog_post add constraint FK_BLOG_POST_CREATOR foreign key (creator_id) references account.account_info;
alter table if exists blog.blog_post add constraint FK_BLOG_POST_UPDATER foreign key (updater_id) references account.account_info;
alter table if exists blog.blog_post add constraint FK_BLOG_POST_BLOG foreign key (blog_id) references blog.blog_info;
alter table if exists blog.blog_post_attach add constraint FK_BLOG_POST_ATTACH_ATTACH foreign key (attach_id) references predefined.predefined_attach;
alter table if exists blog.blog_post_attach add constraint FK_BLOG_POST_ATTACH_POST foreign key (post_id) references blog.blog_post;
alter table if exists blog.blog_post_comment add constraint FK_BLOG_POST_COMMENT_CREATOR foreign key (creator_id) references account.account_info;
alter table if exists blog.blog_post_comment add constraint FK_BLOG_POST_COMMENT_UPDATER foreign key (updater_id) references account.account_info;
alter table if exists blog.blog_post_comment add constraint FK_BLOG_POST_COMMENT_BLOG foreign key (post_id) references blog.blog_post;
alter table if exists blog.blog_post_comment_reaction add constraint FK_BLOG_POST_COMMENT_REACTION_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists blog.blog_post_comment_reaction add constraint FK_BLOG_POST_COMMENT_REACTION_POST_COMMENT foreign key (post_comment_id) references blog.blog_post_comment;
alter table if exists blog.blog_post_comment_ward add constraint FK_BLOG_POST_COMMENT_WARD_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists blog.blog_post_comment_ward add constraint FK_BLOG_POST_COMMENT_WARD_POST foreign key (post_id) references blog.blog_post;
alter table if exists blog.blog_post_hashtag add constraint FK_BLOG_POST_HASHTAG_HASHTAG foreign key (hashtag_id) references predefined.predefined_hashtag;
alter table if exists blog.blog_post_hashtag add constraint FK_BLOG_POST_HASHTAG_POST foreign key (post_id) references blog.blog_post;
alter table if exists blog.blog_post_reaction add constraint FK_BLOG_POST_REACTION_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists blog.blog_post_reaction add constraint FK_BLOG_POST_REACTION_POST foreign key (post_id) references blog.blog_post;
alter table if exists blog.blog_subscribe add constraint FK_BLOG_SUBSCRIBE_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists blog.blog_subscribe add constraint FK_BLOG_SUBSCRIBE_BLOG foreign key (blog_id) references blog.blog_info;
alter table if exists conversation.conversation_channel_account add constraint FK_CONVERSATION_CHANNEL_ACCOUNT_ACCOUNT foreign key (account_id) references account.account_info;
alter table if exists conversation.conversation_channel_account add constraint FK_CONVERSATION_CHANNEL_ACCOUNT_CHANNEL foreign key (channel_id) references conversation.conversation_channel;
alter table if exists predefined.predefined_attach add constraint FK_PREDEFINED_ATTACH_CREATOR foreign key (creator_id) references account.account_info;
alter table if exists predefined.predefined_attach add constraint FK_PREDEFINED_ATTACH_UPDATER foreign key (updater_id) references account.account_info;
alter table if exists predefined.predefined_basic_image add constraint FK_PREDEFINED_BASIC_IMAGE_CREATOR foreign key (creator_id) references account.account_info;
alter table if exists predefined.predefined_basic_image add constraint FK_PREDEFINED_BASIC_IMAGE_UPDATER foreign key (updater_id) references account.account_info;
alter table if exists predefined.predefined_basic_image add constraint FK_PREDEFINED_BASIC_IMAGE_ATTACH foreign key (image_id) references predefined.predefined_attach;

--  _______       ___   .___________.    ___
-- |       \     /   \  |           |   /   \
-- |  .--.  |   /  ^  \ `---|  |----`  /  ^  \
-- |  |  |  |  /  /_\  \    |  |      /  /_\  \
-- |  '--'  | /  _____  \   |  |     /  _____  \
-- |_______/ /__/     \__\  |__|    /__/     \__\
DO
'
DECLARE
    padding varchar(3);
BEGIN
    FOR i IN 1..15
        LOOP
            SELECT  TO_CHAR(i, ''fm000'')
            INTO  padding;

            INSERT
                INTO  predefined.predefined_attach
            (
                content_type,
                filename,
                key,
                size,
                alt,
                title,
                is_public,
                create_at,
                update_at
            )
            VALUES  (
                        ''image/png'',
                        ''dummy''||padding||''.png'',
                        ''temp/dummy''||padding||''.png'',
                        ''2048'',
                        ''alt ''||padding,
                        ''title ''||padding,
                        true,
                        CURRENT_TIMESTAMP,
                        CURRENT_TIMESTAMP
                    );
        END LOOP;
END;
';

DO
'
DECLARE
    padding varchar(3);
BEGIN
    FOR i IN 1..10
        LOOP
            SELECT  TO_CHAR(i, ''fm000'')
            INTO  padding;

            INSERT
                INTO  predefined.predefined_hashtag
            (
                id,
                create_at
            )
            VALUES  (
                            ''hashtag''||padding,
                            CURRENT_TIMESTAMP
                    );
        END LOOP;
END;
';

INSERT
INTO  account.account_info
(
    privilege,
    status,
    uuid,
    username,
    nickname,
    password,
    birth,
    gender,
    introduce,
    profile_id,
    create_at,
    update_at
)
VALUES  (
            'MANAGER_MAJOR',
            'ACTIVE',
            REPLACE(GEN_RANDOM_UUID()::TEXT, '-', ''),
            'major',
            'major nickname',
            '$2a$10$ZL59W4.qVeWo7uu3qsRFnegZpi2O.U6qiDtHXz6Y3jQfDeuIr1dri',
            CURRENT_DATE - (365 * 19),
            'OTHERS',
            'major introduce',
            1,
            CURRENT_TIMESTAMP,
            CURRENT_TIMESTAMP
        );

DO
'
DECLARE
    padding varchar(3);
BEGIN
    FOR i IN 1..10
        LOOP
            SELECT  TO_CHAR(i, ''fm000'')
            INTO  padding;

            INSERT
                INTO  account.account_info
            (
                privilege,
                status,
                uuid,
                username,
                nickname,
                password,
                birth,
                gender,
                introduce,
                profile_id,
                create_at,
                update_at
            )
            VALUES  (
                        ''MANAGER_MINOR'',
                        ''ACTIVE'',
                        REPLACE(GEN_RANDOM_UUID()::TEXT, ''-'', ''''),
                        ''minor''||padding,
                        ''minor ''||padding||'' nickname'',
                        ''$2a$10$ZL59W4.qVeWo7uu3qsRFnegZpi2O.U6qiDtHXz6Y3jQfDeuIr1dri'',
                        CURRENT_DATE - (365 * 19 + i),
                        ''OTHERS'',
                        ''minor ''||padding||'' introduce'',
                        i,
                        CURRENT_TIMESTAMP,
                        CURRENT_TIMESTAMP
                    );

            INSERT
                INTO  account.account_info
            (
                privilege,
                status,
                uuid,
                username,
                nickname,
                password,
                birth,
                gender,
                introduce,
                profile_id,
                create_at,
                update_at
            )
            VALUES  (
                        ''USER'',
                        ''ACTIVE'',
                        REPLACE(GEN_RANDOM_UUID()::TEXT, ''-'', ''''),
                        ''user''||padding,
                        ''user ''||padding||'' nickname'',
                        ''$2a$10$ZL59W4.qVeWo7uu3qsRFnegZpi2O.U6qiDtHXz6Y3jQfDeuIr1dri'',
                        CURRENT_DATE - (365 * 19 + i),
                        ''OTHERS'',
                        ''user ''||padding||'' introduce'',
                        i,
                        CURRENT_TIMESTAMP,
                        CURRENT_TIMESTAMP
                    );
        END LOOP;
END;
';

DO
'
DECLARE
    padding varchar(3);
BEGIN
    FOR i IN 1..10
        LOOP
            SELECT  TO_CHAR(i, ''fm000'')
            INTO  padding;

            INSERT
                INTO  predefined.predefined_basic_image
            (
                type,
                sort,
                image_id,
                creator_id,
                updater_id,
                create_at,
                update_at
            )
            VALUES  (
                        ''PROFILE'',
                        i - 1,
                        i,
                        1,
                        1,
                        CURRENT_TIMESTAMP,
                        CURRENT_TIMESTAMP
                    ),
                    (
                        ''BLOG'',
                        i - 1,
                        i + 5,
                        1,
                        1,
                        CURRENT_TIMESTAMP,
                        CURRENT_TIMESTAMP
                    );
        END LOOP;
END;
';

DO
'
DECLARE
    padding varchar(3);
    sequence_id varchar(22);
    public_channel_id bigint;
    private_channel_id bigint;
    public_blog_id bigint;
    private_blog_id bigint;
BEGIN
    FOR i IN 1..21
        LOOP
            FOR j IN 1..3
                LOOP
                    SELECT  TO_CHAR((j - 1) % 10 + 1, ''fm000'')
                    INTO  padding;

                    INSERT
                        INTO  account.account_hashtag
                    (
                        account_id,
                        hashtag_id,
                        create_at
                    )
                    VALUES  (
                                i,
                                ''hashtag''||padding,
                                CURRENT_TIMESTAMP
                            );
                END LOOP;

            FOR j IN 1..21
                LOOP
                    CONTINUE WHEN i = j;

                    SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                    INTO  sequence_id;

                    INSERT
                        INTO  account.account_follow
                    (
                        following_id,
                        follower_id,
                        create_at
                    )
                    VALUES  (
                                i,
                                j,
                                CURRENT_TIMESTAMP
                            );

                    INSERT
                        INTO  account.account_notification
                    (
                        id,
                        division,
                        type,
                        account_id,
                        creator_id,
                        create_at
                    )
                    VALUES  (
                                sequence_id,
                                ''ACCOUNT'',
                                ''ACCOUNT_FOLLOW'',
                                i,
                                j,
                                CURRENT_TIMESTAMP
                            );

                    INSERT
                        INTO  account.account_notification_account
                    (
                        id,
                        origin_id
                    )
                    VALUES  (
                                sequence_id,
                                j
                            );

                    INSERT
                        INTO  conversation.conversation_channel
                    (
                        uuid,
                        type,
                        create_at,
                        update_at
                    )
                    VALUES  (
                                REPLACE(GEN_RANDOM_UUID()::TEXT, ''-'', ''''),
                                ''DIRECT'',
                                CURRENT_TIMESTAMP,
                                CURRENT_TIMESTAMP
                            );

                    INSERT
                        INTO  conversation.conversation_channel_account
                    (
                        account_id,
                        channel_id,
                        create_at
                    )
                    VALUES  (
                                i,
                                CURRVAL(PG_GET_SERIAL_SEQUENCE(''"conversation".conversation_channel'', ''id'')),
                                CURRENT_TIMESTAMP
                            ),
                            (
                                j,
                                CURRVAL(PG_GET_SERIAL_SEQUENCE(''"conversation".conversation_channel'', ''id'')),
                                CURRENT_TIMESTAMP
                            );
                END LOOP;

            FOR j IN 1..10
                LOOP
                    INSERT
                        INTO  conversation.conversation_channel
                    (
                        uuid,
                        type,
                        create_at,
                        update_at
                    )
                    VALUES  (
                                REPLACE(GEN_RANDOM_UUID()::TEXT, ''-'', ''''),
                                ''GROUP'',
                                CURRENT_TIMESTAMP,
                                CURRENT_TIMESTAMP
                            );

                    SELECT  CURRVAL(PG_GET_SERIAL_SEQUENCE(''"conversation".conversation_channel'', ''id''))
                    INTO  public_channel_id;

                    INSERT
                        INTO  conversation.conversation_channel
                    (
                        uuid,
                        type,
                        create_at,
                        update_at
                    )
                    VALUES  (
                                REPLACE(GEN_RANDOM_UUID()::TEXT, ''-'', ''''),
                                ''GROUP'',
                                CURRENT_TIMESTAMP,
                                CURRENT_TIMESTAMP
                            );

                    SELECT  CURRVAL(PG_GET_SERIAL_SEQUENCE(''"conversation".conversation_channel'', ''id''))
                    INTO  private_channel_id;

                    INSERT
                        INTO  conversation.conversation_channel_account
                    (
                        account_id,
                        channel_id,
                        create_at
                    )
                    VALUES  (
                                i,
                                public_channel_id,
                                CURRENT_TIMESTAMP
                            ),
                            (
                                i,
                                private_channel_id,
                                CURRENT_TIMESTAMP
                            );

                    INSERT
                        INTO  blog.blog_info
                    (
                        uuid,
                        key,
                        visibility,
                        title,
                        description,
                        cover_flag,
                        cover_id,
                        channel_id,
                        creator_id,
                        updater_id,
                        create_at,
                        update_at
                    )
                    VALUES  (
                                REPLACE(GEN_RANDOM_UUID()::TEXT, ''-'', ''''),
                                ''BLOG-PUBLIC-''||TO_CHAR(i, ''fm000'')||TO_CHAR(j, ''fm000''),
                                ''PUBLIC'',
                                ''public blog ''||TO_CHAR(i, ''fm000'')||TO_CHAR(j, ''fm000'')||'' title'',
                                ''public blog ''||TO_CHAR(i, ''fm000'')||TO_CHAR(j, ''fm000'')||'' description'',
                                ''true'',
                                (j - 1) % 15 + 1,
                                public_channel_id,
                                i,
                                i,
                                CURRENT_TIMESTAMP,
                                CURRENT_TIMESTAMP
                            );

                    SELECT  CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_info'', ''id''))
                    INTO  public_blog_id;

                    INSERT
                    INTO  blog.blog_info
                    (
                        uuid,
                        key,
                        visibility,
                        title,
                        description,
                        cover_flag,
                        cover_id,
                        channel_id,
                        creator_id,
                        updater_id,
                        create_at,
                        update_at
                    )
                    VALUES  (
                                REPLACE(GEN_RANDOM_UUID()::TEXT, ''-'', ''''),
                                ''BLOG-PRIVATE-''||TO_CHAR(i, ''fm000'')||TO_CHAR(j, ''fm000''),
                                ''PRIVATE'',
                                ''private blog ''||TO_CHAR(i, ''fm000'')||TO_CHAR(j, ''fm000'')||'' title'',
                                ''private blog ''||TO_CHAR(i, ''fm000'')||TO_CHAR(j, ''fm000'')||'' description'',
                                ''true'',
                                (j - 1) % 15 + 1,
                                private_channel_id,
                                i,
                                i,
                                CURRENT_TIMESTAMP,
                                CURRENT_TIMESTAMP
                            );

                    SELECT  CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_info'', ''id''))
                    INTO  private_blog_id;

                    INSERT
                        INTO  blog.blog_participate
                    (
                        blog_id,
                        account_id,
                        role,
                        create_at
                    )
                    VALUES  (
                                public_blog_id,
                                i,
                                ''OWNER'',
                                CURRENT_TIMESTAMP
                            ),
                            (
                                private_blog_id,
                                i,
                                ''OWNER'',
                                CURRENT_TIMESTAMP
                            );

                    FOR k IN 1..3
                        LOOP
                            SELECT  TO_CHAR((k - 1) % 10 + 1, ''fm000'')
                            INTO  padding;

                            INSERT
                            INTO  blog.blog_info_hashtag
                            (
                                blog_id,
                                hashtag_id,
                                create_at
                            )
                            VALUES  (
                                        public_blog_id,
                                        ''hashtag''||padding,
                                        CURRENT_TIMESTAMP
                                    ),
                                    (
                                        private_blog_id,
                                        ''hashtag''||padding,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  conversation.conversation_channel_account
                            (
                                account_id,
                                channel_id,
                                create_at
                            )
                            VALUES  (
                                                (i - 1 + k) % 21 + 1,
                                                public_channel_id,
                                                CURRENT_TIMESTAMP
                                    ),
                                    (
                                                (i - 1 + k) % 21 + 1,
                                                private_channel_id,
                                                CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  blog.blog_participate
                            (
                                blog_id,
                                account_id,
                                role,
                                create_at
                            )
                            VALUES  (
                                        public_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        ''MAINTAINER'',
                                        CURRENT_TIMESTAMP
                                    ),
                                    (
                                        private_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        ''MAINTAINER'',
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  blog.blog_subscribe
                            (
                                blog_id,
                                account_id,
                                create_at
                            )
                            VALUES  (
                                        public_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    ),
                                    (
                                        private_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                            INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''BLOG'',
                                        ''BLOG_SUBSCRIBE'',
                                        i,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_blog
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        public_blog_id
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''BLOG'',
                                        ''BLOG_SUBSCRIBE'',
                                        i,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_blog
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        private_blog_id
                                    );
                        END LOOP;

                    FOR k IN 4..6
                        LOOP
                            INSERT
                                INTO  conversation.conversation_channel_account
                            (
                                account_id,
                                channel_id,
                                create_at
                            )
                            VALUES  (
                                                (i - 1 + k) % 21 + 1,
                                                public_channel_id,
                                                CURRENT_TIMESTAMP
                                    ),
                                    (
                                                (i - 1 + k) % 21 + 1,
                                                private_channel_id,
                                                CURRENT_TIMESTAMP
                                    );

                            INSERT
                            INTO  blog.blog_participate
                            (
                                blog_id,
                                account_id,
                                role,
                                create_at
                            )
                            VALUES  (
                                        public_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        ''REPORTER'',
                                        CURRENT_TIMESTAMP
                                    ),
                                    (
                                        private_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        ''REPORTER'',
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  blog.blog_subscribe
                            (
                                blog_id,
                                account_id,
                                create_at
                            )
                            VALUES  (
                                        public_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    ),
                                    (
                                        private_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''BLOG'',
                                        ''BLOG_SUBSCRIBE'',
                                        i,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                            INTO  account.account_notification_blog
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        public_blog_id
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''BLOG'',
                                        ''BLOG_SUBSCRIBE'',
                                        i,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_blog
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        private_blog_id
                                    );
                        END LOOP;

                    FOR k IN 7..9
                        LOOP
                            INSERT
                                INTO  blog.blog_participate
                            (
                                blog_id,
                                account_id,
                                role,
                                create_at
                            )
                            VALUES  (
                                        private_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        ''VIEWER'',
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                            INTO  blog.blog_subscribe
                            (
                                blog_id,
                                account_id,
                                create_at
                            )
                            VALUES  (
                                        public_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    ),
                                    (
                                        private_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''BLOG'',
                                        ''BLOG_SUBSCRIBE'',
                                        i,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_blog
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        public_blog_id
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                            INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''BLOG'',
                                        ''BLOG_SUBSCRIBE'',
                                        i,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_blog
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        private_blog_id
                                    );
                        END LOOP;

                    FOR k IN 10..12
                        LOOP
                            INSERT
                            INTO  blog.blog_participate_request
                            (
                                blog_id,
                                account_id,
                                create_at
                            )
                            VALUES  (
                                        private_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  blog.blog_subscribe
                            (
                                blog_id,
                                account_id,
                                create_at
                            )
                            VALUES  (
                                        public_blog_id,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''BLOG'',
                                        ''BLOG_SUBSCRIBE'',
                                        i,
                                        (i - 1 + k) % 21 + 1,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_blog
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        public_blog_id
                                    );
                        END LOOP;
                END LOOP;
        END LOOP;
END;
';

DO
'
DECLARE
    post_id bigint;
    creator bigint;
    padding varchar(3);
    sequence_id varchar(22);
    item record;
BEGIN
    FOR i IN 1..420
        LOOP
            FOR j IN 1..10
                LOOP
                    SELECT  creator_id
                    FROM  blog.blog_info
                    WHERE  id = i
                    INTO  creator;

                    SELECT  TO_CHAR(j, ''fm000'')
                    INTO  padding;

                    INSERT
                    INTO  blog.blog_post
                    (
                        visibility,
                        title,
                        contents,
                        blog_id,
                        creator_id,
                        updater_id,
                        create_at,
                        update_at
                    )
                    SELECT  ''PUBLIC'',
                            title||'' ''||padding||'' title'',
                            LO_FROM_BYTEA(0, (title || '' '' || padding || '' content'')::bytea),
                            id,
                            creator_id,
                            creator_id,
                            CURRENT_TIMESTAMP,
                            CURRENT_TIMESTAMP
                    FROM  blog.blog_info
                    WHERE  id = i;

                    SELECT  CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_post'', ''id''))
                    INTO  post_id;

                    FOR k IN 1..3
                        LOOP
                            SELECT  TO_CHAR((k - 1) % 10 + 1, ''fm000'')
                            INTO  padding;

                            INSERT
                                INTO  blog.blog_post_hashtag
                            (
                                post_id,
                                hashtag_id,
                                create_at
                            )
                            VALUES  (
                                        post_id,
                                        ''hashtag''||padding,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  blog.blog_post_attach
                            (
                                post_id,
                                attach_id,
                                create_at
                            )
                            VALUES  (
                                        post_id,
                                        (i + j + k - 1) % 15 + 1,
                                        CURRENT_TIMESTAMP
                                    );
                        END LOOP;

                    FOR item IN
                        SELECT  account_id
                        FROM  blog.blog_participate
                        WHERE  blog_id = i
                        LOOP
                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''POST'',
                                        ''POST_NEW'',
                                        item.account_id,
                                        creator,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_post
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_post'', ''id''))
                                    );

                            INSERT
                                INTO  blog.blog_post_reaction
                            (
                                post_id,
                                account_id,
                                reaction,
                                create_at
                            )
                            VALUES  (
                                        CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_post'', ''id'')),
                                        item.account_id,
                                        CASE
                                            WHEN (item.account_id + j) % 2 = 0 THEN ''LIKE''
                                            ELSE ''HATE''
                                            END,
                                        CURRENT_TIMESTAMP
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''POST'',
                                        ''POST_REACTION'',
                                        creator,
                                        item.account_id,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                            INTO  account.account_notification_post
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_post'', ''id''))
                                    );

                            INSERT
                                INTO  blog.blog_post_comment_ward
                            (
                                post_id,
                                account_id
                            )
                            VALUES  (
                                        CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_post'', ''id'')),
                                        item.account_id
                                    );
                        END LOOP;
                END LOOP;
        END LOOP;
END;
';

DO
'
DECLARE
    creator bigint;
    padding varchar(3);
    sequence_id varchar(22);
    item record;
BEGIN
    FOR i IN 1..9450
        LOOP
            FOR j IN 1..10
                LOOP
                    SELECT  creator_id
                    FROM  blog.blog_post
                    WHERE  id = i
                    INTO  creator;

                    SELECT  TO_CHAR(j, ''fm000'')
                    INTO  padding;

                    INSERT
                        INTO  blog.blog_post_comment
                    (
                        contents,
                        post_id,
                        creator_id,
                        updater_id,
                        create_at,
                        update_at
                    )
                    SELECT  title||'' ''||padding||'' comment'',
                            id,
                            creator_id,
                            creator_id,
                            CURRENT_TIMESTAMP,
                            CURRENT_TIMESTAMP
                    FROM  blog.blog_post
                    WHERE  id = i;

                    FOR item IN
                        SELECT  PARTICIPATE.account_id
                        FROM  blog.blog_post POST
                                  INNER JOIN  blog.blog_participate PARTICIPATE
                                              ON  PARTICIPATE.blog_id = POST.blog_id
                        WHERE  POST.id = i
                        LOOP
                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''POST_COMMENT'',
                                        ''POST_COMMENT_NEW'',
                                        item.account_id,
                                        creator,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_post_comment
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_post_comment'', ''id''))
                                    );

                            INSERT
                                INTO  blog.blog_post_comment_reaction
                            (
                                post_comment_id,
                                account_id,
                                reaction,
                                create_at
                            )
                            VALUES  (
                                        CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_post_comment'', ''id'')),
                                        item.account_id,
                                        CASE
                                            WHEN (item.account_id + j) % 2 = 0 THEN ''LIKE''
                                            ELSE ''HATE''
                                            END,
                                        CURRENT_TIMESTAMP
                                    );

                            SELECT  TO_CHAR(NEXTVAL(''"account".seq_cycle''), ''fm000000000'')||TRUNC(EXTRACT(EPOCH FROM CURRENT_DATE + TIME ''00:00:00.000'') * 1000)
                            INTO  sequence_id;

                            INSERT
                                INTO  account.account_notification
                            (
                                id,
                                division,
                                type,
                                account_id,
                                creator_id,
                                create_at
                            )
                            VALUES  (
                                        sequence_id,
                                        ''POST_COMMENT'',
                                        ''POST_COMMENT_REACTION'',
                                        creator,
                                        item.account_id,
                                        CURRENT_TIMESTAMP
                                    );

                            INSERT
                                INTO  account.account_notification_post_comment
                            (
                                id,
                                origin_id
                            )
                            VALUES  (
                                        sequence_id,
                                        CURRVAL(PG_GET_SERIAL_SEQUENCE(''"blog".blog_post_comment'', ''id''))
                                    );
                        END LOOP;
                END LOOP;
        END LOOP;
END;
';
