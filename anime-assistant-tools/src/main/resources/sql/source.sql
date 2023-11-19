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

 Date: 16/11/2023 14:32:54
*/
CREATE SEQUENCE source_seq START 1;


-- ----------------------------
-- Table structure for source
-- ----------------------------
DROP TABLE IF EXISTS "public"."source";
CREATE TABLE "public"."source" (
  "id" int8 NOT NULL nextval('source_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "website" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."source"."id" IS '视频资源网站';
COMMENT ON COLUMN "public"."source"."name" IS '视频网站名称';
COMMENT ON COLUMN "public"."source"."description" IS '视频网站描述信息';
COMMENT ON COLUMN "public"."source"."website" IS '网站链接';

-- ----------------------------
-- Primary Key structure for table source
-- ----------------------------
ALTER TABLE "public"."source" ADD CONSTRAINT "anime_source_pkey" PRIMARY KEY ("id");
