create table if not exists EMPLOYEE
(
    employee_number BIGSERIAL primary key,
    name varchar(255),
    lastName varchar(255),
    salary numeric,
    assigned boolean
);

create table if not exists PROJECT
(
    project_number BIGSERIAL primary key,
    title varchar(255),
    description varchar(255),
    status numeric
);

create table if not exists PROJECT_EMPLOYEE
(
    project_number bigint,
    employee_number bigint,
    primary key (project_number, employee_number),
    foreign key (project_number) references PROJECT (project_number),
    foreign key (employee_number) references EMPLOYEE (employee_number)
);