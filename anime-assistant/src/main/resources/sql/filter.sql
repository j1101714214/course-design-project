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

 Date: 16/11/2023 14:32:29
*/
CREATE SEQUENCE filter_seq START 1;

-- ----------------------------
-- Table structure for filter
-- ----------------------------
DROP TABLE IF EXISTS "public"."filter";
CREATE TABLE "public"."filter" (
  "id" int8 NOT NULL nextval('anime_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "origin" varchar(255) COLLATE "pg_catalog"."default",
  "dest" varchar(255) COLLATE "pg_catalog"."default",
  "include" varchar(255) COLLATE "pg_catalog"."default",
  "exclude" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."filter"."id" IS '过滤器id';
COMMENT ON COLUMN "public"."filter"."name" IS '过滤器名称';
COMMENT ON COLUMN "public"."filter"."dest" IS '替换目标';
COMMENT ON COLUMN "public"."filter"."include" IS '包含字段';
COMMENT ON COLUMN "public"."filter"."exclude" IS '排除字段';

-- ----------------------------
-- Primary Key structure for table filter
-- ----------------------------
ALTER TABLE "public"."filter" ADD CONSTRAINT "filter_pkey" PRIMARY KEY ("id");
