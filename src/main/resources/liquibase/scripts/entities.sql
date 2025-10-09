-- liquibase formatted sql

-- changeset mikhail.belyaev:1

create table public.tbl_image(
	id_image        serial,
	nm_file_path    varchar(100) not null,
	nn_file_size    integer not null,
	nm_media_type   varchar(20) not null,
	vl_data         oid,
	constraint image_pk primary key (id_image)
);

create table public.tbl_user(
    id_user         serial,
    nm_email        varchar(30) not null,
    nm_firstname    varchar(20) not null,
    nm_lastname     varchar(20) not null,
    nm_phone        varchar(20) not null,
    nm_role         varchar(10) not null,
    id_image        integer,
    constraint user_pk primary key (id_user),
    constraint user_uk$1 unique (nm_firstname, nm_lastname),
    constraint user_ch$1 check (nm_role in ('USER', 'ADMIN')),
    constraint user_fk$1 foreign key (id_image) references public.tbl_image(id_image)
);

create index user_i$1 on public.tbl_user(id_image);

create table public.tbl_advertisement(
    id_advertisement    serial,
    nm_title            varchar(50) not null,
    nm_description      varchar(200) not null,
    nn_price            integer not null,
    id_user             integer not null,
    id_image            integer,
    constraint advertisement_pk primary key (id_advertisement),
    constraint advertisement_uk$1 unique (nm_title),
    constraint advertisement_ch$1 check (nn_price > 0),
    constraint advertisement_fk$1 foreign key (id_user) references public.tbl_user(id_user),
    constraint advertisement_fk$2 foreign key (id_image) references public.tbl_image(id_image)
);

create index advertisement_i$1 on public.tbl_advertisement(id_user);
create index advertisement_i$2 on public.tbl_advertisement(id_image);

create table public.tbl_comment(
    id_comment          serial,
    nm_text             varchar(200) not null,
    dt_create           timestamptz default current_timestamp,
    id_advertisement    integer not null,
    id_author           integer not null,
    constraint comment_pk primary key (id_comment),
    constraint comment_fk$1 foreign key (id_advertisement) references public.tbl_advertisement(id_advertisement),
    constraint comment_fk$2 foreign key (id_author) references public.tbl_user(id_user)
);

create index comment_i$1 on public.tbl_comment(id_advertisement);
create index comment_i$2 on public.tbl_comment(id_author);
