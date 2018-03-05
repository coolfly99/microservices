create table if not exists PROPERTIES
(
	application varchar(255),
    profile varchar(255),
    label varchar(255),
    ky varchar(255),
    val varchar(255)
);



INSERT INTO PROPERTIES
VALUES('app1', 'default', '', 'msg', 'value1');
INSERT INTO PROPERTIES
VALUES('app1', 'default', 'master', 'msg', 'value2');
INSERT INTO PROPERTIES
VALUES('app1', 'dev', 'master', 'msg', 'value3');
INSERT INTO PROPERTIES
VALUES('demo-config-client-app', 'default', 'master', 'msg', 'value1');