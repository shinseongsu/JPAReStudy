DROP TABLE S_EMP IF EXISTS;

create TABLE S_EMP (
    id NUMBER(7) CONSTRAINT s_emp_id_nn NOT NULL,
    name VARCHAR2(25) CONSTRAINT s_emp_name_nn NOT NULL,
    start_date DATE,
    title VARCHAR2(25),
    dept_name VARCHAR2(25),
    salary NUMBER(11, 2),
    CONSTRAINT s_emp_id_pk PRIMARY KEY (id)
);