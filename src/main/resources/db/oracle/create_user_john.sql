alter session set "_ORACLE_SCRIPT"=true ;
drop user john cascade ; 
create user john identified by a ; 
grant create any table, connect, resource, dba to john ;
commit;

