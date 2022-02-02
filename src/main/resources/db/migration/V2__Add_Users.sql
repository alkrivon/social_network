insert into roles (id, name)
    values ("1", "ROLE_USER");

insert into users (id, login, password, username, role_id)
    values ("1", "User", "$2a$10$ACN0vUVAgOmbynMAS5rWxeOcnzV7H1McJU.62tEXmKJZ0A8Pxa5u6", "Alex", "1");
