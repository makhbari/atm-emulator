INSERT INTO "customer" ("id", "address", "created_at", "customer_no", "email", "enable", "full_name", "mobile", "national_code", "updated_at") 
VALUES (1, 'tehran', NULL, '2040', 'akhbarimahnaz@gmail.com', 't', 'مهناز اخباری', '09126853259', '0082386390', NULL);

INSERT INTO "card" ("id", "cvv2", "deposit_number", "exp_date", "pan", "pin", "status", "type", "customer_id") 
VALUES (1, '215', '12345678', '0211', '624121147470236', '1234', 'OK', 'DEBIT', 1);

INSERT INTO "transaction" ("id", "date_time", "pan", "reference_number", "transaction_type", "value") 
VALUES (1000000000000000, '2022-05-11 15:45:09', '624121147470236', 'f84d70e1-ac81-43a3-961d-c53398ff2968', 'DEPOSIT', '10000000.00');
