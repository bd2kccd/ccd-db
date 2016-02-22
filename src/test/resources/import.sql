insert into person (id,email,first_name,last_name,middle_name,workspace) values (1,'bspears@localhost','britney','spears',null,'/home/bspears')

insert into user_login(id,last_login_date,last_login_location,login_date,login_location) values (1,CURRENT_TIMESTAMP,2130706433,CURRENT_TIMESTAMP,2130706433)

insert into user_login_attempt (id,attempt_count,attempt_date,attempt_location) values (1,1,CURRENT_TIMESTAMP,2130706433)

insert into user_account(id,account,active,disabled,password,registration_date,registration_location,username,person_id,user_login_id,user_login_attempt_id) values (1,'56755429-351c-4fc5-888f-94679818d061',true,false,'$shiro1$SHA-256$500000$J/+mLlMDC7XRWVc8TfSl5w==$CrpVSMPiw8AGT7nGfbs3ajWCUZ8tjX1vmuOxXPGy6cM=',CURRENT_TIMESTAMP,2130706433,'bspears',1,1,1)
