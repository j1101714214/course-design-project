
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

 Date: 16/11/2023 20:22:16
*/


-- ----------------------------
-- Table structure for authorization
-- ----------------------------
DROP TABLE IF EXISTS "public"."authorization";
CREATE TABLE "public"."authorization" (
                                          "user_id" int8 NOT NULL,
                                          "api_key" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                          "api_token" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                          "source" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."authorization"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."authorization"."api_key" IS 'db源api密钥';
COMMENT ON COLUMN "public"."authorization"."api_token" IS 'db源api访问令牌';
COMMENT ON COLUMN "public"."authorization"."source" IS 'db源';

-- ----------------------------
-- Primary Key structure for table authorization
-- ----------------------------
ALTER TABLE "public"."authorization" ADD CONSTRAINT "authorization_pkey" PRIMARY KEY ("user_id", "source");
