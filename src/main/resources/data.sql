-- Inserimento dati di test per i clienti
INSERT INTO customers (name, email, points, created_at, updated_at) VALUES 
('Mario Rossi', 'mario.rossi@email.com', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Giulia Bianchi', 'giulia.bianchi@email.com', 250, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Luca Verdi', 'luca.verdi@email.com', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Anna Neri', 'anna.neri@email.com', 500, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserimento transazioni di test
INSERT INTO point_transactions (customer_id, transaction_type, points, description, created_at) VALUES 
(1, 'ADD', 50, 'Acquisto prodotto', CURRENT_TIMESTAMP),
(1, 'ADD', 50, 'Bonus registrazione', CURRENT_TIMESTAMP),
(2, 'ADD', 100, 'Acquisto prodotto', CURRENT_TIMESTAMP),
(2, 'ADD', 150, 'Bonus fedeltà', CURRENT_TIMESTAMP),
(4, 'ADD', 300, 'Acquisto prodotto premium', CURRENT_TIMESTAMP),
(4, 'ADD', 200, 'Referral bonus', CURRENT_TIMESTAMP);