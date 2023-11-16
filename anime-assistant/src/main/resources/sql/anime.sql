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

 Date: 16/11/2023 14:26:29
*/
CREATE SEQUENCE anime_seq START 1;

-- ----------------------------
-- Table structure for anime
-- ----------------------------
DROP TABLE IF EXISTS "public"."anime";
CREATE TABLE "public"."anime" (
  "id" int8 NOT NULL DEFAULT nextval('anime_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(2550) COLLATE "pg_catalog"."default",
  "ani_initial_date" varchar(255) COLLATE "pg_catalog"."default",
  "ani_total_episodes" int4,
  "ani_season" int4,
  "download_info_id" int8,
  "filter_id" int8
)
;
COMMENT ON COLUMN "public"."anime"."id" IS '视频信息id';
COMMENT ON COLUMN "public"."anime"."name" IS '视频名称';
COMMENT ON COLUMN "public"."anime"."description" IS '视频详细介绍信息';
COMMENT ON COLUMN "public"."anime"."ani_initial_date" IS '视频发布时间';
COMMENT ON COLUMN "public"."anime"."ani_total_episodes" IS '视频剧集数';
COMMENT ON COLUMN "public"."anime"."ani_season" IS '视频季';
COMMENT ON COLUMN "public"."anime"."download_info_id" IS '视频下载信息';
COMMENT ON COLUMN "public"."anime"."filter_id" IS '订阅下载文件的过滤方法';

-- ----------------------------
-- Primary Key structure for table anime
-- ----------------------------
ALTER TABLE "public"."anime" ADD CONSTRAINT "anime_pkey" PRIMARY KEY ("id");
