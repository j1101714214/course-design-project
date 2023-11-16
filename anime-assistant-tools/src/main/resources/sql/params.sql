/*
 Navicat Premium Data Transfer

 Source Server         : lzynbnb
 Source Server Type    : PostgreSQL
 Source Server Version : 150002
 Source Host           : lzynb.com.cn:15432
 Source Catalog        : yhy-yyds
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150002
 File Encoding         : 65001

 Date: 16/11/2023 14:32:44
*/

CREATE SEQUENCE params_seq START 1;
-- ----------------------------
-- Table structure for params
-- ----------------------------
DROP TABLE IF EXISTS "public"."params";
CREATE TABLE "public"."params" (
  "id" int8 NOT NULL nextval('params_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "pos" int4 NOT NULL,
  "api_id" int8 NOT NULL,
  "db_id" int4
)
;
COMMENT ON COLUMN "public"."params"."id" IS '参数id';
COMMENT ON COLUMN "public"."params"."name" IS '参数名称';
COMMENT ON COLUMN "public"."params"."description" IS '参数的详细信息';
COMMENT ON COLUMN "public"."params"."type" IS '参数类型';
COMMENT ON COLUMN "public"."params"."pos" IS 'params位置（0-path params, 1-query params, 2-body)';
COMMENT ON COLUMN "public"."params"."api_id" IS 'params对应api';
COMMENT ON COLUMN "public"."params"."db_id" IS 'params对应api源';

-- ----------------------------
-- Primary Key structure for table params
-- ----------------------------
ALTER TABLE "public"."params" ADD CONSTRAINT "params_pkey" PRIMARY KEY ("id");
