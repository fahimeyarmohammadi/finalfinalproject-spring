insert into base_service(id,name)
values (1,'HomeAppliance');

insert into sub_service(id,base_price,description,sub_name,base_service_id)
values (1,20e5,'kitchenAppliance','kitchen',1);

insert into expert(id,credit,email,family_name,name,password,username,expert_condition,path,score)
values (1,10000,'sara@gmail.com','kohan','sara','Sara1234','sara@gmail.com','ACCEPTED','image/aa.jpg',10);

insert into offers(id,accept_offer,duration,offer_date,offer_price,start_work,expert_id)
values (1,false ,18000000000000,'2023-01-29 11:53:22.123000',3100000,'2023-02-12 12:30:00.000000',1);