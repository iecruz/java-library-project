create table books
(
    id              integer             not null,
    title           varchar(255)        not null,
    author          varchar(255)        not null,
    description     varchar(500)        not null,
    status          varchar(255)        default 'On shelf',
    
    constraint book_pk primary key(id)
);

create table users
(
    id              integer             not null,
    first_name      varchar(255)        not null,
    last_name       varchar(255)        not null,
    username        varchar(255)        not null,
    password        varchar(255)        not null,
    
    constraint user_pk primary key(id)
);

create table transactions
(
    id              integer             not null,
    book_id         integer             not null,
    user_id         integer             not null,
    description     varchar(255)        not null,
    log_time        timestamp           default systimestamp,
    
    constraint transaction_pk primary key(id),
    constraint transaction_fk1 foreign key (book_id) references books(id),
    constraint transaction_fk2 foreign key (user_id) references users(id)
);

create table logs
(
    id              integer             not null,
    user_id         integer             not null,
    log_time        timestamp           default systimestamp,

    constraint log_pk primary key(id),
    constraint log_fk1 foreign key (user_id) references users(id)
);

insert into users values(1, 'admin', 'admin', 'admin', 'password');