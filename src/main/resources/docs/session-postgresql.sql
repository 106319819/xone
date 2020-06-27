create table sessions (
	primary_id char(36) not null,
	session_id char(36) not null,
	creation_time bigint not null,
	last_access_time bigint not null,
	max_inactive_interval int not null,
	expiry_time bigint not null,
	principal_name varchar(100),
	constraint sessions_pk primary key (primary_id)
);

create unique index sessions_ix1 on sessions (session_id);
create index sessions_ix2 on sessions (expiry_time);
create index sessions_ix3 on sessions (principal_name);

create table sessions_attributes (
	session_primary_id char(36) not null,
	attribute_name varchar(200) not null,
	attribute_bytes bytea not null,
	constraint sessions_attributes_pk primary key (session_primary_id, attribute_name),
	constraint sessions_attributes_fk foreign key (session_primary_id) references sessions(primary_id) on delete cascade
);
