CREATE SEQUENCE table_person_id_seq;

CREATE TABLE person  (
    person_id integer NOT NULL DEFAULT nextval('table_person_id_seq'),
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    email VARCHAR(100),
    age INT
);
ALTER SEQUENCE table_person_id_seq OWNED BY person.person_id;
ALTER TABLE public.person
    OWNER to postgres;