create table download_info (
                      id                BIGSERIAL primary key ,
                      GID               varchar(200) not null,
                      title             varchar(200) not null,
                      dir               varchar(200) not null,
                      url               varchar(200) not null,
                      status            varchar(200) not null,
                      create_time       timestamp without time zone not null,
                      update_time       timestamp without time zone null
);
