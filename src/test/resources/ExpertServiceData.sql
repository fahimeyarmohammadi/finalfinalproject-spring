insert into base_service(id,name)
values (1,'HomeAppliance');

insert into sub_service(id,base_price,description,sub_name,base_service_id)
values (1,20e5,'kitchenAppliance','kitchen',1);

insert into sub_service(id,base_price,description,sub_name,base_service_id)
values (2,15e5,'bathroom','bathroom',1);