create table download_info (
                      id                BIGSERIAL primary key ,
                      GID               varchar(200) not null,
                      title             varchar(200) null,
                      url               varchar(200) not null,
                      status            varchar(200) not null
);
