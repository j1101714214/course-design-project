/*
 Navicat Premium Data Transfer

 Source Server         : postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 150004
 Source Host           : localhost:5432
 Source Catalog        : xy_schedule_task
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150004
 File Encoding         : 65001

 Date: 16/09/2023 16:41:07
*/


-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_users";
CREATE TABLE "public"."tb_users" (
  "user_id" "pg_catalog"."int8" NOT NULL,
  "username" "pg_catalog"."varchar" COLLATE "pg_catalog"."default" NOT NULL,
  "password" "pg_catalog"."varchar" COLLATE "pg_catalog"."default" NOT NULL,
  "user_level" "pg_catalog"."varchar" COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_users"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."tb_users"."username" IS '用户名';
COMMENT ON COLUMN "public"."tb_users"."password" IS '用户密码';
COMMENT ON COLUMN "public"."tb_users"."user_level" IS '用户权限: 1 - 游客; 2 - 普通用户; 3 - 高级用户; 4 - 管理员; 5 - 超级管理员';

-- ----------------------------
-- Primary Key structure for table tb_users
-- ----------------------------
ALTER TABLE "public"."tb_users" ADD CONSTRAINT "tb_users_pkey" PRIMARY KEY ("user_id");
