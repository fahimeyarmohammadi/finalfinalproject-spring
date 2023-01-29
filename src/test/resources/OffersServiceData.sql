insert into base_service(id,name)
values (1,'HomeAppliance');

insert into sub_service(id,base_price,description,sub_name,base_service_id)
values (1,20e5,'kitchenAppliance','kitchen',1);

insert into address(id,alley,city,house_number,street)
values (1,'ghadiyani','tehran','124','satarkhan');

INSERT into customer_order(id,description,order_condition,prefer_date,proposed_price,address_id,sub_service_id)
values (1,'my first order','WAITING_EXPERT_SUGGESTION','2023-02-11 10:30:00.000000',3000000,1,1);

insert into expert(id,credit,email,family_name,name,password,username,expert_condition,path,score)
values (1,0,'fahime@gmail.com','yarmohammadi','fahime','Fy123456','fahime@gmail.com','ACCEPTED','image/valid.jpg',20);

insert into expert(id,credit,email,family_name,name,password,username,expert_condition,path,score)
values (2,10000,'sara@gmail.com','kohan','sara','Sara1234','sara@gmail.com','ACCEPTED','image/valid.jpg',10);

insert into offers(id,accept_offer,duration,offer_date,offer_price,start_work,expert_id)
values (2,false ,18000000000000,'2023-01-29 11:53:22.123000',3100000,'2023-02-12 12:30:00.000000',2);