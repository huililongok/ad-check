-- 用户表
DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user(
  user_id INT(8) DEFAULT NULL comment '主键自增',
  user_name varchar(64) DEFAULT NULL comment '用户名称',
  password varchar(128) DEFAULT NULL comment '密码',
  user_real_name varchar(64) DEFAULT NULL comment '真实姓名',
  user_phone varchar(21) DEFAULT NULL comment '手机号',
  user_email varchar(64) DEFAULT NULL comment '邮箱',
  is_truename_verify INT(2) DEFAULT NULL comment '是否实名认证；0：否；1：是',
  chat_head_icon varchar(128) DEFAULT NULL comment '头像',
  signature varchar(255) DEFAULT NULL comment '个性签名',
  user_status INT(4) DEFAULT NULL comment '状态：1：正常；0：无效；2：删除',
  is_admin INT(4) DEFAULT NULL comment '前台用户/后台用户：1：前台；2：后台',
  last_login_time DATETIME DEFAULT NULL comment '最后登录时间',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_user ADD CONSTRAINT PK_tb_user_user_id PRIMARY KEY (user_id);
ALTER TABLE tb_user CHANGE user_id user_id INT(8) NOT NULL AUTO_INCREMENT;
CREATE INDEX user_name_index ON tb_user (user_name);

-- 黑词表
DROP TABLE IF EXISTS tb_blocklist_dic;
CREATE TABLE tb_blocklist_dic(
  dic_id INT(8) DEFAULT NULL comment '主键自增',
  dic_words varchar(128) DEFAULT NULL comment '黑词内容',
  category_id INT(8) DEFAULT NULL comment '黑词行业类型',
  dic_level INT(4) DEFAULT NULL comment '黑词级别',
  dic_status INT(4) DEFAULT NULL comment '黑词状态；1：正常；0：弃用',
  dic_desc varchar(255) DEFAULT NULL comment '描述',
  dic_source INT(2) DEFAULT NULL comment '来源：0：外部导入；1：系统导入；2：系统添加；',
  create_user_id INT(8) DEFAULT NULL comment '创建人或者导入人',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_blocklist_dic ADD CONSTRAINT PK_tb_blocklist_dic_dic_id PRIMARY KEY (dic_id);
ALTER TABLE tb_blocklist_dic CHANGE dic_id dic_id INT(8) NOT NULL AUTO_INCREMENT;
CREATE INDEX dic_words_index ON tb_blocklist_dic (dic_words);

-- 系统角色表
DROP TABLE IF EXISTS tb_sys_role;
CREATE TABLE tb_sys_role(
  role_id INT(8) DEFAULT NULL comment '主键自增',
  role_name varchar(128) DEFAULT NULL comment '角色名称',
  role_en_name varchar(64) DEFAULT NULL comment '英文别名',
  role_status INT(4) DEFAULT NULL comment '状态；1：正常；0：弃用',
  role_desc varchar(255) DEFAULT NULL comment '描述',
  create_user_id INT(8) DEFAULT NULL comment '创建人',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_sys_role ADD CONSTRAINT PK_tb_sys_role_role_id PRIMARY KEY (role_id);
ALTER TABLE tb_sys_role CHANGE role_id role_id INT(8) NOT NULL AUTO_INCREMENT;

-- 系统资源表
DROP TABLE IF EXISTS tb_sys_resource;
CREATE TABLE tb_sys_resource(
  resource_id INT(8) DEFAULT NULL comment '主键自增',
  resource_name varchar(128) DEFAULT NULL comment '资源名称',
  resource_route varchar(128) DEFAULT NULL comment '资源路由',
  resource_style varchar(128) DEFAULT NULL comment '资源样式',
  resource_icon varchar(128) DEFAULT NULL comment '资源图标',
  resource_status INT(4) DEFAULT NULL comment '状态；1：正常；0：弃用',
  resource_desc varchar(255) DEFAULT NULL comment '描述',
  create_user_id INT(8) DEFAULT NULL comment '创建人',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_sys_resource ADD CONSTRAINT PK_tb_sys_resource_resource_id PRIMARY KEY (resource_id);
ALTER TABLE tb_sys_resource CHANGE resource_id resource_id INT(8) NOT NULL AUTO_INCREMENT;

-- 系统用户角色关联表
DROP TABLE IF EXISTS tb_sys_user_role;
CREATE TABLE tb_sys_user_role(
  role_user_id INT(8) DEFAULT NULL comment '主键自增',
  role_id INT(4) DEFAULT NULL comment '角色外键',
  user_id INT(4) DEFAULT NULL comment '用户外键',
  create_user_id INT(8) DEFAULT NULL comment '创建人',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_sys_user_role ADD CONSTRAINT PK_tb_sys_user_role_role_user_id PRIMARY KEY (role_user_id);
ALTER TABLE tb_sys_user_role CHANGE role_user_id role_user_id INT(8) NOT NULL AUTO_INCREMENT;

-- 系统角色资源关联表
DROP TABLE IF EXISTS tb_sys_role_resource;
CREATE TABLE tb_sys_role_resource(
  role_resource_id INT(8) DEFAULT NULL comment '主键自增',
  role_id INT(4) DEFAULT NULL comment '角色外键',
  resource_id INT(4) DEFAULT NULL comment '资源外键',
  create_user_id INT(8) DEFAULT NULL comment '创建人',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_sys_role_resource ADD CONSTRAINT PK_tb_sys_role_resource_role_resource_id_id PRIMARY KEY (role_resource_id);
ALTER TABLE tb_sys_role_resource CHANGE role_resource_id role_resource_id INT(8) NOT NULL AUTO_INCREMENT;

-- 内容验证记录表
DROP TABLE IF EXISTS tb_check_post;
CREATE TABLE tb_check_post(
  post_id INT(8) DEFAULT NULL comment '主键自增',
  post_content TEXT DEFAULT NULL comment '上传内容-文本方式',
  post_type INT(4) DEFAULT NULL comment '验证类型：1：文本；2：图片：3：文件上传：4：url',
  check_filter INT(4) DEFAULT NULL comment '验证过滤器：比如：黑词、图片搜索...',
  check_result INT(2) DEFAULT NULL comment '验证成功：1；验证失败：0',
  resource_desc varchar(255) DEFAULT NULL comment '描述',
  check_user_id INT(8) DEFAULT NULL comment '创建人',
  check_time DATETIME DEFAULT NULL comment '验证时间',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_check_post ADD CONSTRAINT PK_tb_check_post_post_id PRIMARY KEY (post_id);
ALTER TABLE tb_check_post CHANGE post_id post_id INT(8) NOT NULL AUTO_INCREMENT;

-- 验证结果详情表
DROP TABLE IF EXISTS tb_check_details;
CREATE TABLE tb_check_details(
  details_id INT(8) DEFAULT NULL comment '主键自增',
  post_id INT(8) DEFAULT NULL comment '所属的验证内容外键',
  result_text VARCHAR(255) DEFAULT NULL comment '验证结果。如包含黑词：你大爷',
  position INT(32) DEFAULT NULL comment '在文本中的起始位置',
  check_filter_id INT(4) DEFAULT NULL comment '过滤链条中的哪个环节',
  check_result INT(2) DEFAULT NULL comment '验证成功：1；验证失败：0',
  result_desc varchar(255) DEFAULT NULL comment '描述',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_check_details ADD CONSTRAINT PK_tb_check_details_details_id PRIMARY KEY (details_id);
ALTER TABLE tb_check_details CHANGE details_id details_id INT(8) NOT NULL AUTO_INCREMENT;

-- 系统日志表
DROP TABLE IF EXISTS tb_sys_logs;
CREATE TABLE tb_sys_logs(
  logs_id INT(8) DEFAULT NULL comment '主键自增',
  logs_level INT(8) DEFAULT NULL comment '日志级别',
  logs_type INT(8) DEFAULT NULL comment '日志类型',
  logs_content TEXT DEFAULT NULL comment '日志内容，可以是JSON格式',
  logs_user_id INT(8) DEFAULT NULL comment '被记录用户',
  create_time DATETIME DEFAULT NULL comment '这条记录入表时间',
  update_time DATETIME DEFAULT NULL comment '这条记录最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tb_sys_logs ADD CONSTRAINT PK_tb_sys_logs_logs_id PRIMARY KEY (logs_id);
ALTER TABLE tb_sys_logs CHANGE logs_id logs_id INT(8) NOT NULL AUTO_INCREMENT;
