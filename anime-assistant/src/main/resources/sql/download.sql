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

 Date: 16/11/2023 14:32:17
*/
CREATE SEQUENCE download_seq START 1;

-- ----------------------------
-- Table structure for download
-- ----------------------------
DROP TABLE IF EXISTS "public"."download";
CREATE TABLE "public"."download" (
  "id" int8 NOT NULL nextval('download_seq'::regclass),
  "search_name" varchar(255) COLLATE "pg_catalog"."default",
  "sub_method" int4,
  "download_method" int4,
  "dest" varchar(255) COLLATE "pg_catalog"."default",
  "last_episode" int4
)
;
COMMENT ON COLUMN "public"."download"."id" IS '下载信息id';
COMMENT ON COLUMN "public"."download"."search_name" IS '搜索名称';
COMMENT ON COLUMN "public"."download"."sub_method" IS '订阅方法';
COMMENT ON COLUMN "public"."download"."download_method" IS '下载方法';
COMMENT ON COLUMN "public"."download"."dest" IS '下载路径';
COMMENT ON COLUMN "public"."download"."last_episode" IS '最新剧集';

-- ----------------------------
-- Primary Key structure for table download
-- ----------------------------
ALTER TABLE "public"."download" ADD CONSTRAINT "download_pkey" PRIMARY KEY ("id");
