DROP TABLE countries;

DROP TABLE continents;

DROP TABLE cities;

CREATE TABLE countries (
	id SERIAL UNIQUE,
	name VARCHAR(100) NOT NULL,
	code VARCHAR(100),
	continent VARCHAR(100) NOT NULL
);

CREATE TABLE continents (
	id SERIAL UNIQUE,
  	name VARCHAR(100) NOT NULL
);

CREATE TABLE cities (
	id SERIAL UNIQUE,
	name VARCHAR(100) NOT NULL,
	country VARCHAR(100) NOT NULL,	
	capital BOOLEAN,
	latitude DOUBLE PRECISION,
  	longitude DOUBLE PRECISION
);