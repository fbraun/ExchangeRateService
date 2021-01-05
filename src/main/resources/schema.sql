DROP TABLE IF EXISTS TBL_RATES;
  
CREATE TABLE TBL_RATES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  currency_code_from char(3) NOT NULL,
  currency_code_to char(3) NOT NULL,
  conversion_rate decimal(10, 4) NOT NULL
);


DROP TABLE IF EXISTS SUPPORTED_CURRENCIES;
  
CREATE TABLE SUPPORTED_CURRENCIES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  currency_code char(3) NOT NULL
);
