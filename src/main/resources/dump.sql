--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)
-- Dumped by pg_dump version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: id-talher; Type: SEQUENCE; Schema: public; Owner: ti2cc
--

CREATE SEQUENCE public."id-talher"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE public."id-talher" OWNER TO ti2cc;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: talher; Type: TABLE; Schema: public; Owner: ti2cc
--

CREATE TABLE public.talher (
    id integer DEFAULT nextval('public."id-talher"'::regclass) NOT NULL,
    descricao text,
    preco double precision,
    quantidade integer,
    datafabricacao timestamp without time zone,
    datavalidade date
);


ALTER TABLE public.talher OWNER TO ti2cc;

--
-- Name: talher talher_pkey; Type: CONSTRAINT; Schema: public; Owner: ti2cc
--

ALTER TABLE ONLY public.talher
    ADD CONSTRAINT talher_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--    