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

 Date: 16/11/2023 14:26:40
*/
CREATE SEQUENCE api_seq START 1;

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS "public"."api";
CREATE TABLE "public"."api" (
  "id" int8 NOT NULL DEFAULT nextval('api_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "method" int4,
  "template" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "source" int8
)
;
COMMENT ON COLUMN "public"."api"."id" IS 'api的id';
COMMENT ON COLUMN "public"."api"."name" IS 'api名称';
COMMENT ON COLUMN "public"."api"."methed" IS '调用方法（1-GET, 2-POST, 3-PUT, 4-DEL)';
COMMENT ON COLUMN "public"."api"."template" IS '调用api的模板';
COMMENT ON COLUMN "public"."api"."description" IS 'api的详细信息';
COMMENT ON COLUMN "public"."api"."source" IS 'api来源';

-- ----------------------------
-- Records of api
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table api
-- ----------------------------
ALTER TABLE "public"."api" ADD CONSTRAINT "tmdb_api_pkey" PRIMARY KEY ("id");
