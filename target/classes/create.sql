create table if not exists EMPLOYEE
(
    employee_number BIGSERIAL primary key,
    name varchar(255),
    lastName varchar(255),
    salary numeric,
    assigned boolean
);

INSERT INTO EMPLOYEE (employee_number, name, lastName, salary, assigned) VALUES (-1,'Annonimous', '', 0, false);

create table if not exists PROJECT
(
    project_number BIGSERIAL primary key,
    title varchar(255),
    description varchar(255),
    status numeric
);

INSERT INTO PROJECT (project_number, title, description, status) VALUES (-1,'TEST PROJECT', 'UNVALID PROJECT', 0);

create table if not exists PROJECT_EMPLOYEE
(
    project_number bigint,
    employee_number bigint,
    primary key (project_number, employee_number),
    foreign key (project_number) references PROJECT (project_number),
    foreign key (employee_number) references EMPLOYEE (employee_number)
);

create table if not exists TASK
(
    task_number BIGSERIAL primary key,
    title varchar(255),
    description varchar(255),
    status numeric,
    estimated_time numeric,
    worked_time numeric,
    project_number bigint,
    assigned_employee bigint,
    foreign key (assigned_employee) references EMPLOYEE (employee_number),
    foreign key (project_number) references PROJECT (project_number)
);

create table if not exists TASK_STATUS_HISTORIC
(
    id          bigSerial primary key,
    task_number bigint,
    status      numeric,
    descripcion varchar(255),
    date        timestamp,
    employee    bigint
);