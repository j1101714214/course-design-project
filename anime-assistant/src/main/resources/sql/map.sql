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

 Date: 16/11/2023 14:32:38
*/


-- ----------------------------
-- Table structure for map
-- ----------------------------
DROP TABLE IF EXISTS "public"."map";
CREATE TABLE "public"."map" (
  "anime_id" int8 NOT NULL,
  "cate_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."map"."anime_id" IS '视频id';
COMMENT ON COLUMN "public"."map"."cate_id" IS '分类信息id';

-- ----------------------------
-- Primary Key structure for table map
-- ----------------------------
ALTER TABLE "public"."map" ADD CONSTRAINT "map_pkey" PRIMARY KEY ("anime_id", "cate_id");
